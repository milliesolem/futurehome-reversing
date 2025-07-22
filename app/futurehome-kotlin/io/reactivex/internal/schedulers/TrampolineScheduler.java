package io.reactivex.internal.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public final class TrampolineScheduler extends Scheduler {
   private static final TrampolineScheduler INSTANCE = new TrampolineScheduler();

   TrampolineScheduler() {
   }

   public static TrampolineScheduler instance() {
      return INSTANCE;
   }

   @Override
   public Scheduler.Worker createWorker() {
      return new TrampolineScheduler.TrampolineWorker();
   }

   @Override
   public Disposable scheduleDirect(Runnable var1) {
      RxJavaPlugins.onSchedule(var1).run();
      return EmptyDisposable.INSTANCE;
   }

   @Override
   public Disposable scheduleDirect(Runnable var1, long var2, TimeUnit var4) {
      try {
         var4.sleep(var2);
         RxJavaPlugins.onSchedule(var1).run();
      } catch (InterruptedException var5) {
         Thread.currentThread().interrupt();
         RxJavaPlugins.onError(var5);
      }

      return EmptyDisposable.INSTANCE;
   }

   static final class SleepingRunnable implements Runnable {
      private final long execTime;
      private final Runnable run;
      private final TrampolineScheduler.TrampolineWorker worker;

      SleepingRunnable(Runnable var1, TrampolineScheduler.TrampolineWorker var2, long var3) {
         this.run = var1;
         this.worker = var2;
         this.execTime = var3;
      }

      @Override
      public void run() {
         if (!this.worker.disposed) {
            long var3 = this.worker.now(TimeUnit.MILLISECONDS);
            long var1 = this.execTime;
            if (var1 > var3) {
               try {
                  Thread.sleep(var1 - var3);
               } catch (InterruptedException var6) {
                  Thread.currentThread().interrupt();
                  RxJavaPlugins.onError(var6);
                  return;
               }
            }

            if (!this.worker.disposed) {
               this.run.run();
            }
         }
      }
   }

   static final class TimedRunnable implements Comparable<TrampolineScheduler.TimedRunnable> {
      final int count;
      volatile boolean disposed;
      final long execTime;
      final Runnable run;

      TimedRunnable(Runnable var1, Long var2, int var3) {
         this.run = var1;
         this.execTime = var2;
         this.count = var3;
      }

      public int compareTo(TrampolineScheduler.TimedRunnable var1) {
         int var2 = ObjectHelper.compare(this.execTime, var1.execTime);
         return var2 == 0 ? ObjectHelper.compare(this.count, var1.count) : var2;
      }
   }

   static final class TrampolineWorker extends Scheduler.Worker implements Disposable {
      final AtomicInteger counter;
      volatile boolean disposed;
      final PriorityBlockingQueue<TrampolineScheduler.TimedRunnable> queue = new PriorityBlockingQueue<>();
      private final AtomicInteger wip = new AtomicInteger();

      TrampolineWorker() {
         this.counter = new AtomicInteger();
      }

      @Override
      public void dispose() {
         this.disposed = true;
      }

      Disposable enqueue(Runnable var1, long var2) {
         if (this.disposed) {
            return EmptyDisposable.INSTANCE;
         } else {
            TrampolineScheduler.TimedRunnable var6 = new TrampolineScheduler.TimedRunnable(var1, var2, this.counter.incrementAndGet());
            this.queue.add(var6);
            if (this.wip.getAndIncrement() == 0) {
               int var4 = 1;

               while (!this.disposed) {
                  TrampolineScheduler.TimedRunnable var7 = this.queue.poll();
                  if (var7 == null) {
                     int var5 = this.wip.addAndGet(-var4);
                     var4 = var5;
                     if (var5 == 0) {
                        return EmptyDisposable.INSTANCE;
                     }
                  } else if (!var7.disposed) {
                     var7.run.run();
                  }
               }

               this.queue.clear();
               return EmptyDisposable.INSTANCE;
            } else {
               return Disposables.fromRunnable(new TrampolineScheduler.TrampolineWorker.AppendToQueueTask(this, var6));
            }
         }
      }

      @Override
      public boolean isDisposed() {
         return this.disposed;
      }

      @Override
      public Disposable schedule(Runnable var1) {
         return this.enqueue(var1, this.now(TimeUnit.MILLISECONDS));
      }

      @Override
      public Disposable schedule(Runnable var1, long var2, TimeUnit var4) {
         var2 = this.now(TimeUnit.MILLISECONDS) + var4.toMillis(var2);
         return this.enqueue(new TrampolineScheduler.SleepingRunnable(var1, this, var2), var2);
      }

      final class AppendToQueueTask implements Runnable {
         final TrampolineScheduler.TrampolineWorker this$0;
         final TrampolineScheduler.TimedRunnable timedRunnable;

         AppendToQueueTask(TrampolineScheduler.TrampolineWorker var1, TrampolineScheduler.TimedRunnable var2) {
            this.this$0 = var1;
            this.timedRunnable = var2;
         }

         @Override
         public void run() {
            this.timedRunnable.disposed = true;
            this.this$0.queue.remove(this.timedRunnable);
         }
      }
   }
}
