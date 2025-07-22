package io.sentry.transport;

import io.sentry.DateUtils;
import io.sentry.ILogger;
import io.sentry.SentryDate;
import io.sentry.SentryDateProvider;
import io.sentry.SentryLevel;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

final class QueuedThreadPoolExecutor extends ThreadPoolExecutor {
   private static final long RECENT_THRESHOLD = DateUtils.millisToNanos(2000L);
   private final SentryDateProvider dateProvider;
   private SentryDate lastRejectTimestamp = null;
   private final ILogger logger;
   private final int maxQueueSize;
   private final ReusableCountLatch unfinishedTasksCount = new ReusableCountLatch();

   public QueuedThreadPoolExecutor(int var1, int var2, ThreadFactory var3, RejectedExecutionHandler var4, ILogger var5, SentryDateProvider var6) {
      super(var1, var1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), var3, var4);
      this.maxQueueSize = var2;
      this.logger = var5;
      this.dateProvider = var6;
   }

   @Override
   protected void afterExecute(Runnable var1, Throwable var2) {
      try {
         super.afterExecute(var1, var2);
      } finally {
         this.unfinishedTasksCount.decrement();
      }
   }

   public boolean didRejectRecently() {
      SentryDate var2 = this.lastRejectTimestamp;
      boolean var1 = false;
      if (var2 == null) {
         return false;
      } else {
         if (this.dateProvider.now().diff(var2) < RECENT_THRESHOLD) {
            var1 = true;
         }

         return var1;
      }
   }

   public boolean isSchedulingAllowed() {
      boolean var1;
      if (this.unfinishedTasksCount.getCount() < this.maxQueueSize) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public Future<?> submit(Runnable var1) {
      if (this.isSchedulingAllowed()) {
         this.unfinishedTasksCount.increment();
         return super.submit(var1);
      } else {
         this.lastRejectTimestamp = this.dateProvider.now();
         this.logger.log(SentryLevel.WARNING, "Submit cancelled");
         return new QueuedThreadPoolExecutor.CancelledFuture();
      }
   }

   void waitTillIdle(long var1) {
      try {
         this.unfinishedTasksCount.waitTillZero(var1, TimeUnit.MILLISECONDS);
      } catch (InterruptedException var4) {
         this.logger.log(SentryLevel.ERROR, "Failed to wait till idle", var4);
         Thread.currentThread().interrupt();
      }
   }

   static final class CancelledFuture<T> implements Future<T> {
      @Override
      public boolean cancel(boolean var1) {
         return true;
      }

      @Override
      public T get() {
         throw new CancellationException();
      }

      @Override
      public T get(long var1, TimeUnit var3) {
         throw new CancellationException();
      }

      @Override
      public boolean isCancelled() {
         return true;
      }

      @Override
      public boolean isDone() {
         return true;
      }
   }
}
