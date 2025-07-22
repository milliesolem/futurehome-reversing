package io.reactivex.internal.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableContainer;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.SchedulerRunnableIntrospection;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ExecutorScheduler extends Scheduler {
   static final Scheduler HELPER = Schedulers.single();
   final Executor executor;
   final boolean interruptibleWorker;

   public ExecutorScheduler(Executor var1, boolean var2) {
      this.executor = var1;
      this.interruptibleWorker = var2;
   }

   @Override
   public Scheduler.Worker createWorker() {
      return new ExecutorScheduler.ExecutorWorker(this.executor, this.interruptibleWorker);
   }

   @Override
   public Disposable scheduleDirect(Runnable var1) {
      var1 = RxJavaPlugins.onSchedule(var1);

      try {
         if (this.executor instanceof ExecutorService) {
            ScheduledDirectTask var6 = new ScheduledDirectTask(var1);
            var6.setFuture(((ExecutorService)this.executor).submit(var6));
            return var6;
         } else if (this.interruptibleWorker) {
            ExecutorScheduler.ExecutorWorker.InterruptibleRunnable var5 = new ExecutorScheduler.ExecutorWorker.InterruptibleRunnable(var1, null);
            this.executor.execute(var5);
            return var5;
         } else {
            ExecutorScheduler.ExecutorWorker.BooleanRunnable var2 = new ExecutorScheduler.ExecutorWorker.BooleanRunnable(var1);
            this.executor.execute(var2);
            return var2;
         }
      } catch (RejectedExecutionException var3) {
         RxJavaPlugins.onError(var3);
         return EmptyDisposable.INSTANCE;
      }
   }

   @Override
   public Disposable scheduleDirect(Runnable var1, long var2, TimeUnit var4) {
      Runnable var5 = RxJavaPlugins.onSchedule(var1);
      if (this.executor instanceof ScheduledExecutorService) {
         try {
            ScheduledDirectTask var8 = new ScheduledDirectTask(var5);
            var8.setFuture(((ScheduledExecutorService)this.executor).schedule(var8, var2, var4));
            return var8;
         } catch (RejectedExecutionException var6) {
            RxJavaPlugins.onError(var6);
            return EmptyDisposable.INSTANCE;
         }
      } else {
         ExecutorScheduler.DelayedRunnable var7 = new ExecutorScheduler.DelayedRunnable(var5);
         Disposable var9 = HELPER.scheduleDirect(new ExecutorScheduler.DelayedDispose(this, var7), var2, var4);
         var7.timed.replace(var9);
         return var7;
      }
   }

   @Override
   public Disposable schedulePeriodicallyDirect(Runnable var1, long var2, long var4, TimeUnit var6) {
      if (this.executor instanceof ScheduledExecutorService) {
         Runnable var7 = RxJavaPlugins.onSchedule(var1);

         try {
            ScheduledDirectPeriodicTask var9 = new ScheduledDirectPeriodicTask(var7);
            var9.setFuture(((ScheduledExecutorService)this.executor).scheduleAtFixedRate(var9, var2, var4, var6));
            return var9;
         } catch (RejectedExecutionException var8) {
            RxJavaPlugins.onError(var8);
            return EmptyDisposable.INSTANCE;
         }
      } else {
         return super.schedulePeriodicallyDirect(var1, var2, var4, var6);
      }
   }

   final class DelayedDispose implements Runnable {
      private final ExecutorScheduler.DelayedRunnable dr;
      final ExecutorScheduler this$0;

      DelayedDispose(ExecutorScheduler var1, ExecutorScheduler.DelayedRunnable var2) {
         this.this$0 = var1;
         this.dr = var2;
      }

      @Override
      public void run() {
         this.dr.direct.replace(this.this$0.scheduleDirect(this.dr));
      }
   }

   static final class DelayedRunnable extends AtomicReference<Runnable> implements Runnable, Disposable, SchedulerRunnableIntrospection {
      private static final long serialVersionUID = -4101336210206799084L;
      final SequentialDisposable direct;
      final SequentialDisposable timed = new SequentialDisposable();

      DelayedRunnable(Runnable var1) {
         super(var1);
         this.direct = new SequentialDisposable();
      }

      @Override
      public void dispose() {
         if (this.getAndSet(null) != null) {
            this.timed.dispose();
            this.direct.dispose();
         }
      }

      @Override
      public Runnable getWrappedRunnable() {
         Runnable var1 = this.get();
         if (var1 == null) {
            var1 = Functions.EMPTY_RUNNABLE;
         }

         return var1;
      }

      @Override
      public boolean isDisposed() {
         boolean var1;
         if (this.get() == null) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      @Override
      public void run() {
         Runnable var1 = this.get();
         if (var1 != null) {
            try {
               var1.run();
            } finally {
               this.lazySet(null);
               this.timed.lazySet(DisposableHelper.DISPOSED);
               this.direct.lazySet(DisposableHelper.DISPOSED);
            }
         }
      }
   }

   public static final class ExecutorWorker extends Scheduler.Worker implements Runnable {
      volatile boolean disposed;
      final Executor executor;
      final boolean interruptibleWorker;
      final MpscLinkedQueue<Runnable> queue;
      final CompositeDisposable tasks;
      final AtomicInteger wip = new AtomicInteger();

      public ExecutorWorker(Executor var1, boolean var2) {
         this.tasks = new CompositeDisposable();
         this.executor = var1;
         this.queue = new MpscLinkedQueue<>();
         this.interruptibleWorker = var2;
      }

      @Override
      public void dispose() {
         if (!this.disposed) {
            this.disposed = true;
            this.tasks.dispose();
            if (this.wip.getAndIncrement() == 0) {
               this.queue.clear();
            }
         }
      }

      @Override
      public boolean isDisposed() {
         return this.disposed;
      }

      @Override
      public void run() {
         MpscLinkedQueue var3 = this.queue;
         int var1 = 1;

         while (!this.disposed) {
            Runnable var4 = (Runnable)var3.poll();
            if (var4 == null) {
               if (this.disposed) {
                  var3.clear();
                  return;
               }

               int var2 = this.wip.addAndGet(-var1);
               var1 = var2;
               if (var2 == 0) {
                  return;
               }
            } else {
               var4.run();
               if (this.disposed) {
                  var3.clear();
                  return;
               }
            }
         }

         var3.clear();
      }

      @Override
      public Disposable schedule(Runnable var1) {
         if (this.disposed) {
            return EmptyDisposable.INSTANCE;
         } else {
            var1 = RxJavaPlugins.onSchedule(var1);
            Object var4;
            if (this.interruptibleWorker) {
               var4 = new ExecutorScheduler.ExecutorWorker.InterruptibleRunnable(var1, this.tasks);
               this.tasks.add((Disposable)var4);
            } else {
               var4 = new ExecutorScheduler.ExecutorWorker.BooleanRunnable(var1);
            }

            this.queue.offer((Runnable)var4);
            if (this.wip.getAndIncrement() == 0) {
               try {
                  this.executor.execute(this);
               } catch (RejectedExecutionException var2) {
                  this.disposed = true;
                  this.queue.clear();
                  RxJavaPlugins.onError(var2);
                  return EmptyDisposable.INSTANCE;
               }
            }

            return (Disposable)var4;
         }
      }

      @Override
      public Disposable schedule(Runnable var1, long var2, TimeUnit var4) {
         if (var2 <= 0L) {
            return this.schedule(var1);
         } else if (this.disposed) {
            return EmptyDisposable.INSTANCE;
         } else {
            SequentialDisposable var5 = new SequentialDisposable();
            SequentialDisposable var6 = new SequentialDisposable(var5);
            ScheduledRunnable var9 = new ScheduledRunnable(
               new ExecutorScheduler.ExecutorWorker.SequentialDispose(this, var6, RxJavaPlugins.onSchedule(var1)), this.tasks
            );
            this.tasks.add(var9);
            Executor var7 = this.executor;
            if (var7 instanceof ScheduledExecutorService) {
               try {
                  var9.setFuture(((ScheduledExecutorService)var7).schedule((Callable<Object>)var9, var2, var4));
               } catch (RejectedExecutionException var8) {
                  this.disposed = true;
                  RxJavaPlugins.onError(var8);
                  return EmptyDisposable.INSTANCE;
               }
            } else {
               var9.setFuture(new DisposeOnCancel(ExecutorScheduler.HELPER.scheduleDirect(var9, var2, var4)));
            }

            var5.replace(var9);
            return var6;
         }
      }

      static final class BooleanRunnable extends AtomicBoolean implements Runnable, Disposable {
         private static final long serialVersionUID = -2421395018820541164L;
         final Runnable actual;

         BooleanRunnable(Runnable var1) {
            this.actual = var1;
         }

         @Override
         public void dispose() {
            this.lazySet(true);
         }

         @Override
         public boolean isDisposed() {
            return this.get();
         }

         @Override
         public void run() {
            if (!this.get()) {
               try {
                  this.actual.run();
               } finally {
                  this.lazySet(true);
               }
            }
         }
      }

      static final class InterruptibleRunnable extends AtomicInteger implements Runnable, Disposable {
         static final int FINISHED = 2;
         static final int INTERRUPTED = 4;
         static final int INTERRUPTING = 3;
         static final int READY = 0;
         static final int RUNNING = 1;
         private static final long serialVersionUID = -3603436687413320876L;
         final Runnable run;
         final DisposableContainer tasks;
         volatile Thread thread;

         InterruptibleRunnable(Runnable var1, DisposableContainer var2) {
            this.run = var1;
            this.tasks = var2;
         }

         void cleanup() {
            DisposableContainer var1 = this.tasks;
            if (var1 != null) {
               var1.delete(this);
            }
         }

         @Override
         public void dispose() {
            while (true) {
               int var1 = this.get();
               if (var1 < 2) {
                  if (var1 == 0) {
                     if (!this.compareAndSet(0, 4)) {
                        continue;
                     }

                     this.cleanup();
                  } else {
                     if (!this.compareAndSet(1, 3)) {
                        continue;
                     }

                     Thread var2 = this.thread;
                     if (var2 != null) {
                        var2.interrupt();
                        this.thread = null;
                     }

                     this.set(4);
                     this.cleanup();
                  }
               }

               return;
            }
         }

         @Override
         public boolean isDisposed() {
            boolean var1;
            if (this.get() >= 2) {
               var1 = true;
            } else {
               var1 = false;
            }

            return var1;
         }

         // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         @Override
         public void run() {
            if (this.get() == 0) {
               this.thread = Thread.currentThread();
               if (this.compareAndSet(0, 1)) {
                  boolean var3 = false /* VF: Semaphore variable */;

                  try {
                     var3 = true;
                     this.run.run();
                     var3 = false;
                  } finally {
                     if (var3) {
                        this.thread = null;
                        if (this.compareAndSet(1, 2)) {
                           this.cleanup();
                        } else {
                           while (this.get() == 3) {
                              Thread.yield();
                           }

                           Thread.interrupted();
                        }
                     }
                  }

                  this.thread = null;
                  if (this.compareAndSet(1, 2)) {
                     this.cleanup();
                  } else {
                     while (this.get() == 3) {
                        Thread.yield();
                     }

                     Thread.interrupted();
                  }
               } else {
                  this.thread = null;
               }
            }
         }
      }

      final class SequentialDispose implements Runnable {
         private final Runnable decoratedRun;
         private final SequentialDisposable mar;
         final ExecutorScheduler.ExecutorWorker this$0;

         SequentialDispose(ExecutorScheduler.ExecutorWorker var1, SequentialDisposable var2, Runnable var3) {
            this.this$0 = var1;
            this.mar = var2;
            this.decoratedRun = var3;
         }

         @Override
         public void run() {
            this.mar.replace(this.this$0.schedule(this.decoratedRun));
         }
      }
   }
}
