package io.sentry.backpressure;

import io.sentry.IHub;
import io.sentry.ISentryExecutorService;
import io.sentry.SentryLevel;
import io.sentry.SentryOptions;

public final class BackpressureMonitor implements IBackpressureMonitor, Runnable {
   private static final int CHECK_INTERVAL_IN_MS = 10000;
   private static final int INITIAL_CHECK_DELAY_IN_MS = 500;
   static final int MAX_DOWNSAMPLE_FACTOR = 10;
   private int downsampleFactor = 0;
   private final IHub hub;
   private final SentryOptions sentryOptions;

   public BackpressureMonitor(SentryOptions var1, IHub var2) {
      this.sentryOptions = var1;
      this.hub = var2;
   }

   private boolean isHealthy() {
      return this.hub.isHealthy();
   }

   private void reschedule(int var1) {
      ISentryExecutorService var2 = this.sentryOptions.getExecutorService();
      if (!var2.isClosed()) {
         var2.schedule(this, var1);
      }
   }

   void checkHealth() {
      if (this.isHealthy()) {
         if (this.downsampleFactor > 0) {
            this.sentryOptions.getLogger().log(SentryLevel.DEBUG, "Health check positive, reverting to normal sampling.");
         }

         this.downsampleFactor = 0;
      } else {
         int var1 = this.downsampleFactor;
         if (var1 < 10) {
            this.downsampleFactor = var1 + 1;
            this.sentryOptions.getLogger().log(SentryLevel.DEBUG, "Health check negative, downsampling with a factor of %d", this.downsampleFactor);
         }
      }
   }

   @Override
   public int getDownsampleFactor() {
      return this.downsampleFactor;
   }

   @Override
   public void run() {
      this.checkHealth();
      this.reschedule(10000);
   }

   @Override
   public void start() {
      this.reschedule(500);
   }
}
