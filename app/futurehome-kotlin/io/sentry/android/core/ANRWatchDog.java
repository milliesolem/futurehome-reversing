package io.sentry.android.core;

import android.app.ActivityManager;
import android.app.ActivityManager.ProcessErrorStateInfo;
import android.content.Context;
import android.os.Debug;
import io.sentry.ILogger;
import io.sentry.SentryLevel;
import io.sentry.transport.ICurrentDateProvider;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

final class ANRWatchDog extends Thread {
   private final ANRWatchDog.ANRListener anrListener;
   private final Context context;
   private volatile long lastKnownActiveUiTimestampMs = 0L;
   private final ILogger logger;
   private long pollingIntervalMs;
   private final boolean reportInDebug;
   private final AtomicBoolean reported = new AtomicBoolean(false);
   private final Runnable ticker;
   private final ICurrentDateProvider timeProvider;
   private final long timeoutIntervalMillis;
   private final MainLooperHandler uiHandler;

   ANRWatchDog(long var1, boolean var3, ANRWatchDog.ANRListener var4, ILogger var5, Context var6) {
      this(new ANRWatchDog$$ExternalSyntheticLambda0(), var1, 500L, var3, var4, var5, new MainLooperHandler(), var6);
   }

   ANRWatchDog(ICurrentDateProvider var1, long var2, long var4, boolean var6, ANRWatchDog.ANRListener var7, ILogger var8, MainLooperHandler var9, Context var10) {
      super("|ANR-WatchDog|");
      this.timeProvider = var1;
      this.timeoutIntervalMillis = var2;
      this.pollingIntervalMs = var4;
      this.reportInDebug = var6;
      this.anrListener = var7;
      this.logger = var8;
      this.uiHandler = var9;
      this.context = var10;
      this.ticker = new ANRWatchDog$$ExternalSyntheticLambda1(this, var1);
      if (var2 < this.pollingIntervalMs * 2L) {
         throw new IllegalArgumentException(String.format("ANRWatchDog: timeoutIntervalMillis has to be at least %d ms", this.pollingIntervalMs * 2L));
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private boolean isProcessNotResponding() {
      ActivityManager var1 = (ActivityManager)this.context.getSystemService("activity");
      if (var1 != null) {
         label43:
         try {
            var4 = var1.getProcessesInErrorState();
         } catch (Throwable var3) {
            this.logger.log(SentryLevel.ERROR, "Error getting ActivityManager#getProcessesInErrorState.", var3);
            var4 = null;
            break label43;
         }

         if (var4 != null) {
            Iterator var5 = var4.iterator();

            while (var5.hasNext()) {
               if (((ProcessErrorStateInfo)var5.next()).condition == 2) {
                  return true;
               }
            }
         }

         return false;
      } else {
         return true;
      }
   }

   @Override
   public void run() {
      this.ticker.run();

      while (!this.isInterrupted()) {
         this.uiHandler.post(this.ticker);

         try {
            Thread.sleep(this.pollingIntervalMs);
         } catch (InterruptedException var4) {
            try {
               Thread.currentThread().interrupt();
            } catch (SecurityException var3) {
               this.logger.log(SentryLevel.WARNING, "Failed to interrupt due to SecurityException: %s", var4.getMessage());
               break;
            }

            this.logger.log(SentryLevel.WARNING, "Interrupted: %s", var4.getMessage());
            return;
         }

         if (this.timeProvider.getCurrentTimeMillis() - this.lastKnownActiveUiTimestampMs > this.timeoutIntervalMillis) {
            if (this.reportInDebug || !Debug.isDebuggerConnected() && !Debug.waitingForDebugger()) {
               if (this.isProcessNotResponding() && this.reported.compareAndSet(false, true)) {
                  StringBuilder var1 = new StringBuilder("Application Not Responding for at least ");
                  var1.append(this.timeoutIntervalMillis);
                  var1.append(" ms.");
                  ApplicationNotResponding var5 = new ApplicationNotResponding(var1.toString(), this.uiHandler.getThread());
                  this.anrListener.onAppNotResponding(var5);
               }
            } else {
               this.logger.log(SentryLevel.DEBUG, "An ANR was detected but ignored because the debugger is connected.");
               this.reported.set(true);
            }
         }
      }
   }

   public interface ANRListener {
      void onAppNotResponding(ApplicationNotResponding var1);
   }
}
