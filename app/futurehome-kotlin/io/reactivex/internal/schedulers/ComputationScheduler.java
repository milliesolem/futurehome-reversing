package io.reactivex.internal.schedulers;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.disposables.ListCompositeDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public final class ComputationScheduler extends Scheduler implements SchedulerMultiWorkerSupport {
   private static final String KEY_COMPUTATION_PRIORITY = "rx2.computation-priority";
   static final String KEY_MAX_THREADS = "rx2.computation-threads";
   static final int MAX_THREADS = cap(Runtime.getRuntime().availableProcessors(), Integer.getInteger("rx2.computation-threads", 0));
   static final ComputationScheduler.FixedSchedulerPool NONE;
   static final ComputationScheduler.PoolWorker SHUTDOWN_WORKER;
   static final RxThreadFactory THREAD_FACTORY;
   private static final String THREAD_NAME_PREFIX = "RxComputationThreadPool";
   final AtomicReference<ComputationScheduler.FixedSchedulerPool> pool;
   final ThreadFactory threadFactory;

   static {
      ComputationScheduler.PoolWorker var0 = new ComputationScheduler.PoolWorker(new RxThreadFactory("RxComputationShutdown"));
      SHUTDOWN_WORKER = var0;
      var0.dispose();
      RxThreadFactory var1 = new RxThreadFactory("RxComputationThreadPool", Math.max(1, Math.min(10, Integer.getInteger("rx2.computation-priority", 5))), true);
      THREAD_FACTORY = var1;
      ComputationScheduler.FixedSchedulerPool var2 = new ComputationScheduler.FixedSchedulerPool(0, var1);
      NONE = var2;
      var2.shutdown();
   }

   public ComputationScheduler() {
      this(THREAD_FACTORY);
   }

   public ComputationScheduler(ThreadFactory var1) {
      this.threadFactory = var1;
      this.pool = new AtomicReference<>(NONE);
      this.start();
   }

   static int cap(int var0, int var1) {
      int var2 = var0;
      if (var1 > 0) {
         if (var1 > var0) {
            var2 = var0;
         } else {
            var2 = var1;
         }
      }

      return var2;
   }

   @Override
   public Scheduler.Worker createWorker() {
      return new ComputationScheduler.EventLoopWorker(this.pool.get().getEventLoop());
   }

   @Override
   public void createWorkers(int var1, SchedulerMultiWorkerSupport.WorkerCallback var2) {
      ObjectHelper.verifyPositive(var1, "number > 0 required");
      this.pool.get().createWorkers(var1, var2);
   }

   @Override
   public Disposable scheduleDirect(Runnable var1, long var2, TimeUnit var4) {
      return this.pool.get().getEventLoop().scheduleDirect(var1, var2, var4);
   }

   @Override
   public Disposable schedulePeriodicallyDirect(Runnable var1, long var2, long var4, TimeUnit var6) {
      return this.pool.get().getEventLoop().schedulePeriodicallyDirect(var1, var2, var4, var6);
   }

   @Override
   public void shutdown() {
      ComputationScheduler.FixedSchedulerPool var1;
      ComputationScheduler.FixedSchedulerPool var2;
      do {
         var2 = this.pool.get();
         var1 = NONE;
         if (var2 == var1) {
            return;
         }
      } while (!ExternalSyntheticBackportWithForwarding0.m(this.pool, var2, var1));

      var2.shutdown();
   }

   @Override
   public void start() {
      ComputationScheduler.FixedSchedulerPool var1 = new ComputationScheduler.FixedSchedulerPool(MAX_THREADS, this.threadFactory);
      if (!ExternalSyntheticBackportWithForwarding0.m(this.pool, NONE, var1)) {
         var1.shutdown();
      }
   }

   static final class EventLoopWorker extends Scheduler.Worker {
      private final ListCompositeDisposable both;
      volatile boolean disposed;
      private final ComputationScheduler.PoolWorker poolWorker;
      private final ListCompositeDisposable serial;
      private final CompositeDisposable timed;

      EventLoopWorker(ComputationScheduler.PoolWorker var1) {
         this.poolWorker = var1;
         ListCompositeDisposable var2 = new ListCompositeDisposable();
         this.serial = var2;
         CompositeDisposable var4 = new CompositeDisposable();
         this.timed = var4;
         ListCompositeDisposable var3 = new ListCompositeDisposable();
         this.both = var3;
         var3.add(var2);
         var3.add(var4);
      }

      @Override
      public void dispose() {
         if (!this.disposed) {
            this.disposed = true;
            this.both.dispose();
         }
      }

      @Override
      public boolean isDisposed() {
         return this.disposed;
      }

      @Override
      public Disposable schedule(Runnable var1) {
         return (Disposable)(this.disposed ? EmptyDisposable.INSTANCE : this.poolWorker.scheduleActual(var1, 0L, TimeUnit.MILLISECONDS, this.serial));
      }

      @Override
      public Disposable schedule(Runnable var1, long var2, TimeUnit var4) {
         return (Disposable)(this.disposed ? EmptyDisposable.INSTANCE : this.poolWorker.scheduleActual(var1, var2, var4, this.timed));
      }
   }

   static final class FixedSchedulerPool implements SchedulerMultiWorkerSupport {
      final int cores;
      final ComputationScheduler.PoolWorker[] eventLoops;
      long n;

      FixedSchedulerPool(int var1, ThreadFactory var2) {
         this.cores = var1;
         this.eventLoops = new ComputationScheduler.PoolWorker[var1];

         for (int var3 = 0; var3 < var1; var3++) {
            this.eventLoops[var3] = new ComputationScheduler.PoolWorker(var2);
         }
      }

      @Override
      public void createWorkers(int var1, SchedulerMultiWorkerSupport.WorkerCallback var2) {
         int var6 = this.cores;
         int var3 = 0;
         if (var6 == 0) {
            while (var3 < var1) {
               var2.onWorker(var3, ComputationScheduler.SHUTDOWN_WORKER);
               var3++;
            }
         } else {
            var3 = (int)this.n % var6;

            for (int var4 = 0; var4 < var1; var4++) {
               var2.onWorker(var4, new ComputationScheduler.EventLoopWorker(this.eventLoops[var3]));
               int var5 = var3 + 1;
               var3 = var5;
               if (var5 == var6) {
                  var3 = 0;
               }
            }

            this.n = var3;
         }
      }

      public ComputationScheduler.PoolWorker getEventLoop() {
         int var1 = this.cores;
         if (var1 == 0) {
            return ComputationScheduler.SHUTDOWN_WORKER;
         } else {
            ComputationScheduler.PoolWorker[] var4 = this.eventLoops;
            int var2 = this.n++;
            return var4[(int)(var2 % var1)];
         }
      }

      public void shutdown() {
         ComputationScheduler.PoolWorker[] var3 = this.eventLoops;
         int var2 = var3.length;

         for (int var1 = 0; var1 < var2; var1++) {
            var3[var1].dispose();
         }
      }
   }

   static final class PoolWorker extends NewThreadWorker {
      PoolWorker(ThreadFactory var1) {
         super(var1);
      }
   }
}
