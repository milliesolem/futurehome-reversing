package io.reactivex.internal.schedulers;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.EmptyDisposable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public final class IoScheduler extends Scheduler {
   static final RxThreadFactory EVICTOR_THREAD_FACTORY;
   private static final String EVICTOR_THREAD_NAME_PREFIX = "RxCachedWorkerPoolEvictor";
   private static final long KEEP_ALIVE_TIME = Long.getLong("rx2.io-keep-alive-time", 60L);
   public static final long KEEP_ALIVE_TIME_DEFAULT = 60L;
   private static final TimeUnit KEEP_ALIVE_UNIT = TimeUnit.SECONDS;
   private static final String KEY_IO_PRIORITY = "rx2.io-priority";
   private static final String KEY_KEEP_ALIVE_TIME = "rx2.io-keep-alive-time";
   static final IoScheduler.CachedWorkerPool NONE;
   static final IoScheduler.ThreadWorker SHUTDOWN_THREAD_WORKER;
   static final RxThreadFactory WORKER_THREAD_FACTORY;
   private static final String WORKER_THREAD_NAME_PREFIX = "RxCachedThreadScheduler";
   final AtomicReference<IoScheduler.CachedWorkerPool> pool;
   final ThreadFactory threadFactory;

   static {
      IoScheduler.ThreadWorker var1 = new IoScheduler.ThreadWorker(new RxThreadFactory("RxCachedThreadSchedulerShutdown"));
      SHUTDOWN_THREAD_WORKER = var1;
      var1.dispose();
      int var0 = Math.max(1, Math.min(10, Integer.getInteger("rx2.io-priority", 5)));
      RxThreadFactory var2 = new RxThreadFactory("RxCachedThreadScheduler", var0);
      WORKER_THREAD_FACTORY = var2;
      EVICTOR_THREAD_FACTORY = new RxThreadFactory("RxCachedWorkerPoolEvictor", var0);
      IoScheduler.CachedWorkerPool var3 = new IoScheduler.CachedWorkerPool(0L, null, var2);
      NONE = var3;
      var3.shutdown();
   }

   public IoScheduler() {
      this(WORKER_THREAD_FACTORY);
   }

   public IoScheduler(ThreadFactory var1) {
      this.threadFactory = var1;
      this.pool = new AtomicReference<>(NONE);
      this.start();
   }

   @Override
   public Scheduler.Worker createWorker() {
      return new IoScheduler.EventLoopWorker(this.pool.get());
   }

   @Override
   public void shutdown() {
      IoScheduler.CachedWorkerPool var1;
      IoScheduler.CachedWorkerPool var2;
      do {
         var1 = this.pool.get();
         var2 = NONE;
         if (var1 == var2) {
            return;
         }
      } while (!ExternalSyntheticBackportWithForwarding0.m(this.pool, var1, var2));

      var1.shutdown();
   }

   public int size() {
      return this.pool.get().allWorkers.size();
   }

   @Override
   public void start() {
      IoScheduler.CachedWorkerPool var1 = new IoScheduler.CachedWorkerPool(KEEP_ALIVE_TIME, KEEP_ALIVE_UNIT, this.threadFactory);
      if (!ExternalSyntheticBackportWithForwarding0.m(this.pool, NONE, var1)) {
         var1.shutdown();
      }
   }

   static final class CachedWorkerPool implements Runnable {
      final CompositeDisposable allWorkers;
      private final ScheduledExecutorService evictorService;
      private final Future<?> evictorTask;
      private final ConcurrentLinkedQueue<IoScheduler.ThreadWorker> expiringWorkerQueue;
      private final long keepAliveTime;
      private final ThreadFactory threadFactory;

      CachedWorkerPool(long var1, TimeUnit var3, ThreadFactory var4) {
         if (var3 != null) {
            var1 = var3.toNanos(var1);
         } else {
            var1 = 0L;
         }

         this.keepAliveTime = var1;
         this.expiringWorkerQueue = new ConcurrentLinkedQueue<>();
         this.allWorkers = new CompositeDisposable();
         this.threadFactory = var4;
         ScheduledFuture var6;
         ScheduledExecutorService var7;
         if (var3 != null) {
            var7 = Executors.newScheduledThreadPool(1, IoScheduler.EVICTOR_THREAD_FACTORY);
            var6 = var7.scheduleWithFixedDelay(this, var1, var1, TimeUnit.NANOSECONDS);
         } else {
            var7 = null;
            var6 = null;
         }

         this.evictorService = var7;
         this.evictorTask = var6;
      }

      void evictExpiredWorkers() {
         if (!this.expiringWorkerQueue.isEmpty()) {
            long var1 = this.now();

            for (IoScheduler.ThreadWorker var4 : this.expiringWorkerQueue) {
               if (var4.getExpirationTime() > var1) {
                  break;
               }

               if (this.expiringWorkerQueue.remove(var4)) {
                  this.allWorkers.remove(var4);
               }
            }
         }
      }

      IoScheduler.ThreadWorker get() {
         if (this.allWorkers.isDisposed()) {
            return IoScheduler.SHUTDOWN_THREAD_WORKER;
         } else {
            while (!this.expiringWorkerQueue.isEmpty()) {
               IoScheduler.ThreadWorker var1 = this.expiringWorkerQueue.poll();
               if (var1 != null) {
                  return var1;
               }
            }

            IoScheduler.ThreadWorker var2 = new IoScheduler.ThreadWorker(this.threadFactory);
            this.allWorkers.add(var2);
            return var2;
         }
      }

      long now() {
         return System.nanoTime();
      }

      void release(IoScheduler.ThreadWorker var1) {
         var1.setExpirationTime(this.now() + this.keepAliveTime);
         this.expiringWorkerQueue.offer(var1);
      }

      @Override
      public void run() {
         this.evictExpiredWorkers();
      }

      void shutdown() {
         this.allWorkers.dispose();
         Future var1 = this.evictorTask;
         if (var1 != null) {
            var1.cancel(true);
         }

         ScheduledExecutorService var2 = this.evictorService;
         if (var2 != null) {
            var2.shutdownNow();
         }
      }
   }

   static final class EventLoopWorker extends Scheduler.Worker {
      final AtomicBoolean once = new AtomicBoolean();
      private final IoScheduler.CachedWorkerPool pool;
      private final CompositeDisposable tasks;
      private final IoScheduler.ThreadWorker threadWorker;

      EventLoopWorker(IoScheduler.CachedWorkerPool var1) {
         this.pool = var1;
         this.tasks = new CompositeDisposable();
         this.threadWorker = var1.get();
      }

      @Override
      public void dispose() {
         if (this.once.compareAndSet(false, true)) {
            this.tasks.dispose();
            this.pool.release(this.threadWorker);
         }
      }

      @Override
      public boolean isDisposed() {
         return this.once.get();
      }

      @Override
      public Disposable schedule(Runnable var1, long var2, TimeUnit var4) {
         return (Disposable)(this.tasks.isDisposed() ? EmptyDisposable.INSTANCE : this.threadWorker.scheduleActual(var1, var2, var4, this.tasks));
      }
   }

   static final class ThreadWorker extends NewThreadWorker {
      private long expirationTime = 0L;

      ThreadWorker(ThreadFactory var1) {
         super(var1);
      }

      public long getExpirationTime() {
         return this.expirationTime;
      }

      public void setExpirationTime(long var1) {
         this.expirationTime = var1;
      }
   }
}
