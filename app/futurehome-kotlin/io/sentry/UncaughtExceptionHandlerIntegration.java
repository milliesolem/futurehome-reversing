package io.sentry;

import io.sentry.exception.ExceptionMechanismException;
import io.sentry.hints.BlockingFlushHint;
import io.sentry.hints.EventDropReason;
import io.sentry.hints.SessionEnd;
import io.sentry.hints.TransactionEnd;
import io.sentry.protocol.Mechanism;
import io.sentry.protocol.SentryId;
import io.sentry.util.HintUtils;
import io.sentry.util.IntegrationUtils;
import io.sentry.util.Objects;
import java.io.Closeable;
import java.util.concurrent.atomic.AtomicReference;

public final class UncaughtExceptionHandlerIntegration implements Integration, java.lang.Thread.UncaughtExceptionHandler, Closeable {
   private java.lang.Thread.UncaughtExceptionHandler defaultExceptionHandler;
   private IHub hub;
   private SentryOptions options;
   private boolean registered = false;
   private final UncaughtExceptionHandler threadAdapter;

   public UncaughtExceptionHandlerIntegration() {
      this(UncaughtExceptionHandler.Adapter.getInstance());
   }

   UncaughtExceptionHandlerIntegration(UncaughtExceptionHandler var1) {
      this.threadAdapter = Objects.requireNonNull(var1, "threadAdapter is required.");
   }

   static Throwable getUnhandledThrowable(Thread var0, Throwable var1) {
      Mechanism var2 = new Mechanism();
      var2.setHandled(false);
      var2.setType("UncaughtExceptionHandler");
      return new ExceptionMechanismException(var2, var1, var0);
   }

   @Override
   public void close() {
      if (this == this.threadAdapter.getDefaultUncaughtExceptionHandler()) {
         this.threadAdapter.setDefaultUncaughtExceptionHandler(this.defaultExceptionHandler);
         SentryOptions var1 = this.options;
         if (var1 != null) {
            var1.getLogger().log(SentryLevel.DEBUG, "UncaughtExceptionHandlerIntegration removed.");
         }
      }
   }

   @Override
   public void register(IHub var1, SentryOptions var2) {
      if (this.registered) {
         var2.getLogger().log(SentryLevel.ERROR, "Attempt to register a UncaughtExceptionHandlerIntegration twice.");
      } else {
         this.registered = true;
         this.hub = Objects.requireNonNull(var1, "Hub is required");
         SentryOptions var5 = Objects.requireNonNull(var2, "SentryOptions is required");
         this.options = var5;
         var5.getLogger().log(SentryLevel.DEBUG, "UncaughtExceptionHandlerIntegration enabled: %s", this.options.isEnableUncaughtExceptionHandler());
         if (this.options.isEnableUncaughtExceptionHandler()) {
            java.lang.Thread.UncaughtExceptionHandler var6 = this.threadAdapter.getDefaultUncaughtExceptionHandler();
            if (var6 != null) {
               ILogger var4 = this.options.getLogger();
               SentryLevel var7 = SentryLevel.DEBUG;
               StringBuilder var3 = new StringBuilder("default UncaughtExceptionHandler class='");
               var3.append(var6.getClass().getName());
               var3.append("'");
               var4.log(var7, var3.toString());
               if (var6 instanceof UncaughtExceptionHandlerIntegration) {
                  this.defaultExceptionHandler = ((UncaughtExceptionHandlerIntegration)var6).defaultExceptionHandler;
               } else {
                  this.defaultExceptionHandler = var6;
               }
            }

            this.threadAdapter.setDefaultUncaughtExceptionHandler(this);
            this.options.getLogger().log(SentryLevel.DEBUG, "UncaughtExceptionHandlerIntegration installed.");
            IntegrationUtils.addIntegrationToSdkVersion("UncaughtExceptionHandler");
         }
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void uncaughtException(Thread var1, Throwable var2) {
      SentryOptions var4 = this.options;
      if (var4 != null && this.hub != null) {
         var4.getLogger().log(SentryLevel.INFO, "Uncaught exception received.");

         label161: {
            UncaughtExceptionHandlerIntegration.UncaughtExceptionHint var5;
            try {
               var5 = new UncaughtExceptionHandlerIntegration.UncaughtExceptionHint(this.options.getFlushTimeoutMillis(), this.options.getLogger());
               Throwable var6 = getUnhandledThrowable(var1, var2);
               var27 = new SentryEvent(var6);
               var27.setLevel(SentryLevel.FATAL);
               if (this.hub.getTransaction() == null && var27.getEventId() != null) {
                  var5.setFlushable(var27.getEventId());
               }
            } catch (Throwable var26) {
               this.options.getLogger().log(SentryLevel.ERROR, "Error sending uncaught exception to Sentry.", var26);
               break label161;
            }

            boolean var3;
            EventDropReason var29;
            try {
               Hint var28 = HintUtils.createWithTypeCheckHint(var5);
               var3 = this.hub.captureEvent(var27, var28).equals(SentryId.EMPTY_ID);
               var29 = HintUtils.getEventDropReason(var28);
            } catch (Throwable var25) {
               this.options.getLogger().log(SentryLevel.ERROR, "Error sending uncaught exception to Sentry.", var25);
               break label161;
            }

            if (var3) {
               try {
                  if (!EventDropReason.MULTITHREADED_DEDUPLICATION.equals(var29)) {
                     break label161;
                  }
               } catch (Throwable var24) {
                  this.options.getLogger().log(SentryLevel.ERROR, "Error sending uncaught exception to Sentry.", var24);
                  break label161;
               }
            }

            label146:
            try {
               if (!var5.waitFlush()) {
                  this.options.getLogger().log(SentryLevel.WARNING, "Timed out waiting to flush event to disk before crashing. Event: %s", var27.getEventId());
               }
            } catch (Throwable var23) {
               this.options.getLogger().log(SentryLevel.ERROR, "Error sending uncaught exception to Sentry.", var23);
               break label146;
            }
         }

         if (this.defaultExceptionHandler != null) {
            this.options.getLogger().log(SentryLevel.INFO, "Invoking inner uncaught exception handler.");
            this.defaultExceptionHandler.uncaughtException(var1, var2);
         } else if (this.options.isPrintUncaughtStackTrace()) {
            var2.printStackTrace();
         }
      }
   }

   public static class UncaughtExceptionHint extends BlockingFlushHint implements SessionEnd, TransactionEnd {
      private final AtomicReference<SentryId> flushableEventId = new AtomicReference<>();

      public UncaughtExceptionHint(long var1, ILogger var3) {
         super(var1, var3);
      }

      @Override
      public boolean isFlushable(SentryId var1) {
         SentryId var3 = this.flushableEventId.get();
         boolean var2;
         if (var3 != null && var3.equals(var1)) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      @Override
      public void setFlushable(SentryId var1) {
         this.flushableEventId.set(var1);
      }
   }
}
