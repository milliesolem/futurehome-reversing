package io.sentry.android.core;

import io.sentry.IHub;
import io.sentry.ILogger;
import io.sentry.ISentryExecutorService;
import io.sentry.Integration;
import io.sentry.OutboxSender;
import io.sentry.SentryLevel;
import io.sentry.SentryOptions;
import io.sentry.util.Objects;
import java.io.Closeable;

public abstract class EnvelopeFileObserverIntegration implements Integration, Closeable {
   private boolean isClosed = false;
   private ILogger logger;
   private EnvelopeFileObserver observer;
   private final Object startLock = new Object();

   public static EnvelopeFileObserverIntegration getOutboxFileObserver() {
      return new EnvelopeFileObserverIntegration.OutboxEnvelopeFileObserverIntegration();
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private void startOutboxSender(IHub var1, SentryOptions var2, String var3) {
      EnvelopeFileObserver var6 = new EnvelopeFileObserver(
         var3,
         new OutboxSender(var1, var2.getEnvelopeReader(), var2.getSerializer(), var2.getLogger(), var2.getFlushTimeoutMillis(), var2.getMaxQueueSize()),
         var2.getLogger(),
         var2.getFlushTimeoutMillis()
      );
      this.observer = var6;

      try {
         var6.startWatching();
         var2.getLogger().log(SentryLevel.DEBUG, "EnvelopeFileObserverIntegration installed.");
      } catch (Throwable var5) {
         var2.getLogger().log(SentryLevel.ERROR, "Failed to initialize EnvelopeFileObserverIntegration.", var5);
         return;
      }
   }

   @Override
   public void close() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield io/sentry/android/core/EnvelopeFileObserverIntegration.startLock Ljava/lang/Object;
      // 04: astore 2
      // 05: aload 2
      // 06: monitorenter
      // 07: aload 0
      // 08: bipush 1
      // 09: putfield io/sentry/android/core/EnvelopeFileObserverIntegration.isClosed Z
      // 0c: aload 2
      // 0d: monitorexit
      // 0e: aload 0
      // 0f: getfield io/sentry/android/core/EnvelopeFileObserverIntegration.observer Lio/sentry/android/core/EnvelopeFileObserver;
      // 12: astore 1
      // 13: aload 1
      // 14: ifnull 33
      // 17: aload 1
      // 18: invokevirtual io/sentry/android/core/EnvelopeFileObserver.stopWatching ()V
      // 1b: aload 0
      // 1c: getfield io/sentry/android/core/EnvelopeFileObserverIntegration.logger Lio/sentry/ILogger;
      // 1f: astore 1
      // 20: aload 1
      // 21: ifnull 33
      // 24: aload 1
      // 25: getstatic io/sentry/SentryLevel.DEBUG Lio/sentry/SentryLevel;
      // 28: ldc "EnvelopeFileObserverIntegration removed."
      // 2a: bipush 0
      // 2b: anewarray 4
      // 2e: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 33: return
      // 34: astore 1
      // 35: aload 2
      // 36: monitorexit
      // 37: aload 1
      // 38: athrow
   }

   abstract String getPath(SentryOptions var1);

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public final void register(IHub var1, SentryOptions var2) {
      Objects.requireNonNull(var1, "Hub is required");
      Objects.requireNonNull(var2, "SentryOptions is required");
      this.logger = var2.getLogger();
      String var3 = this.getPath(var2);
      if (var3 == null) {
         this.logger.log(SentryLevel.WARNING, "Null given as a path to EnvelopeFileObserverIntegration. Nothing will be registered.");
      } else {
         this.logger.log(SentryLevel.DEBUG, "Registering EnvelopeFileObserverIntegration for path: %s", var3);

         try {
            ISentryExecutorService var4 = var2.getExecutorService();
            EnvelopeFileObserverIntegration$$ExternalSyntheticLambda0 var5 = new EnvelopeFileObserverIntegration$$ExternalSyntheticLambda0(
               this, var1, var2, var3
            );
            var4.submit(var5);
         } catch (Throwable var7) {
            this.logger.log(SentryLevel.DEBUG, "Failed to start EnvelopeFileObserverIntegration on executor thread.", var7);
            return;
         }
      }
   }

   private static final class OutboxEnvelopeFileObserverIntegration extends EnvelopeFileObserverIntegration {
      private OutboxEnvelopeFileObserverIntegration() {
      }

      @Override
      protected String getPath(SentryOptions var1) {
         return var1.getOutboxPath();
      }
   }
}
