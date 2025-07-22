package io.sentry.hints;

import io.sentry.ILogger;
import io.sentry.SentryLevel;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public abstract class BlockingFlushHint implements DiskFlushNotification, Flushable {
   private final long flushTimeoutMillis;
   private final CountDownLatch latch;
   private final ILogger logger;

   public BlockingFlushHint(long var1, ILogger var3) {
      this.flushTimeoutMillis = var1;
      this.latch = new CountDownLatch(1);
      this.logger = var3;
   }

   @Override
   public void markFlushed() {
      this.latch.countDown();
   }

   @Override
   public boolean waitFlush() {
      try {
         return this.latch.await(this.flushTimeoutMillis, TimeUnit.MILLISECONDS);
      } catch (InterruptedException var3) {
         Thread.currentThread().interrupt();
         this.logger.log(SentryLevel.ERROR, "Exception while awaiting for flush in BlockingFlushHint", var3);
         return false;
      }
   }
}
