package io.reactivex;

import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.schedulers.NewThreadWorker;
import io.reactivex.internal.schedulers.SchedulerWhen;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.SchedulerRunnableIntrospection;
import java.util.concurrent.TimeUnit;

public abstract class Scheduler {
   static final long CLOCK_DRIFT_TOLERANCE_NANOSECONDS = TimeUnit.MINUTES.toNanos(Long.getLong("rx2.scheduler.drift-tolerance", 15L));

   public static long clockDriftTolerance() {
      return CLOCK_DRIFT_TOLERANCE_NANOSECONDS;
   }

   public abstract Scheduler.Worker createWorker();

   public long now(TimeUnit var1) {
      return var1.convert(System.currentTimeMillis(), TimeUnit.MILLISECONDS);
   }

   public Disposable scheduleDirect(Runnable var1) {
      return this.scheduleDirect(var1, 0L, TimeUnit.NANOSECONDS);
   }

   public Disposable scheduleDirect(Runnable var1, long var2, TimeUnit var4) {
      Scheduler.Worker var5 = this.createWorker();
      Scheduler.DisposeTask var6 = new Scheduler.DisposeTask(RxJavaPlugins.onSchedule(var1), var5);
      var5.schedule(var6, var2, var4);
      return var6;
   }

   public Disposable schedulePeriodicallyDirect(Runnable var1, long var2, long var4, TimeUnit var6) {
      Scheduler.Worker var7 = this.createWorker();
      Scheduler.PeriodicDirectTask var8 = new Scheduler.PeriodicDirectTask(RxJavaPlugins.onSchedule(var1), var7);
      Disposable var9 = var7.schedulePeriodically(var8, var2, var4, var6);
      return (Disposable)(var9 == EmptyDisposable.INSTANCE ? var9 : var8);
   }

   public void shutdown() {
   }

   public void start() {
   }

   public <S extends Scheduler & Disposable> S when(Function<Flowable<Flowable<Completable>>, Completable> var1) {
      return (S)(new SchedulerWhen(var1, this));
   }

   static final class DisposeTask implements Disposable, Runnable, SchedulerRunnableIntrospection {
      final Runnable decoratedRun;
      Thread runner;
      final Scheduler.Worker w;

      DisposeTask(Runnable var1, Scheduler.Worker var2) {
         this.decoratedRun = var1;
         this.w = var2;
      }

      @Override
      public void dispose() {
         if (this.runner == Thread.currentThread()) {
            Scheduler.Worker var1 = this.w;
            if (var1 instanceof NewThreadWorker) {
               ((NewThreadWorker)var1).shutdown();
               return;
            }
         }

         this.w.dispose();
      }

      @Override
      public Runnable getWrappedRunnable() {
         return this.decoratedRun;
      }

      @Override
      public boolean isDisposed() {
         return this.w.isDisposed();
      }

      @Override
      public void run() {
         this.runner = Thread.currentThread();

         try {
            this.decoratedRun.run();
         } finally {
            this.dispose();
            this.runner = null;
         }
      }
   }

   static final class PeriodicDirectTask implements Disposable, Runnable, SchedulerRunnableIntrospection {
      volatile boolean disposed;
      final Runnable run;
      final Scheduler.Worker worker;

      PeriodicDirectTask(Runnable var1, Scheduler.Worker var2) {
         this.run = var1;
         this.worker = var2;
      }

      @Override
      public void dispose() {
         this.disposed = true;
         this.worker.dispose();
      }

      @Override
      public Runnable getWrappedRunnable() {
         return this.run;
      }

      @Override
      public boolean isDisposed() {
         return this.disposed;
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void run() {
         if (!this.disposed) {
            try {
               this.run.run();
            } catch (Throwable var3) {
               Exceptions.throwIfFatal(var3);
               this.worker.dispose();
               throw ExceptionHelper.wrapOrThrow(var3);
            }
         }
      }
   }

   public abstract static class Worker implements Disposable {
      public long now(TimeUnit var1) {
         return var1.convert(System.currentTimeMillis(), TimeUnit.MILLISECONDS);
      }

      public Disposable schedule(Runnable var1) {
         return this.schedule(var1, 0L, TimeUnit.NANOSECONDS);
      }

      public abstract Disposable schedule(Runnable var1, long var2, TimeUnit var4);

      public Disposable schedulePeriodically(Runnable var1, long var2, long var4, TimeUnit var6) {
         SequentialDisposable var9 = new SequentialDisposable();
         SequentialDisposable var10 = new SequentialDisposable(var9);
         var1 = RxJavaPlugins.onSchedule(var1);
         long var7 = var6.toNanos(var4);
         var4 = this.now(TimeUnit.NANOSECONDS);
         Disposable var12 = this.schedule(new Scheduler.Worker.PeriodicTask(this, var4 + var6.toNanos(var2), var1, var4, var10, var7), var2, var6);
         if (var12 == EmptyDisposable.INSTANCE) {
            return var12;
         } else {
            var9.replace(var12);
            return var10;
         }
      }

      final class PeriodicTask implements Runnable, SchedulerRunnableIntrospection {
         long count;
         final Runnable decoratedRun;
         long lastNowNanoseconds;
         final long periodInNanoseconds;
         final SequentialDisposable sd;
         long startInNanoseconds;
         final Scheduler.Worker this$0;

         PeriodicTask(Scheduler.Worker var1, long var2, Runnable var4, long var5, SequentialDisposable var7, long var8) {
            this.this$0 = var1;
            this.decoratedRun = var4;
            this.sd = var7;
            this.periodInNanoseconds = var8;
            this.lastNowNanoseconds = var5;
            this.startInNanoseconds = var2;
         }

         @Override
         public Runnable getWrappedRunnable() {
            return this.decoratedRun;
         }

         @Override
         public void run() {
            this.decoratedRun.run();
            if (!this.sd.isDisposed()) {
               long var3 = this.this$0.now(TimeUnit.NANOSECONDS);
               long var1 = Scheduler.CLOCK_DRIFT_TOLERANCE_NANOSECONDS;
               long var5 = this.lastNowNanoseconds;
               if (var1 + var3 >= var5 && var3 < var5 + this.periodInNanoseconds + Scheduler.CLOCK_DRIFT_TOLERANCE_NANOSECONDS) {
                  var1 = this.startInNanoseconds;
                  var5 = this.count + 1L;
                  this.count = var5;
                  var1 += var5 * this.periodInNanoseconds;
               } else {
                  var5 = this.periodInNanoseconds;
                  var1 = var3 + var5;
                  long var7 = this.count + 1L;
                  this.count = var7;
                  this.startInNanoseconds = var1 - var5 * var7;
               }

               this.lastNowNanoseconds = var3;
               this.sd.replace(this.this$0.schedule(this, var1 - var3, TimeUnit.NANOSECONDS));
            }
         }
      }
   }
}
