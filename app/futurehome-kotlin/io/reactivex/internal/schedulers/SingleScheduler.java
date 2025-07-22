package io.reactivex.internal.schedulers;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public final class SingleScheduler extends Scheduler {
   private static final String KEY_SINGLE_PRIORITY = "rx2.single-priority";
   static final ScheduledExecutorService SHUTDOWN;
   static final RxThreadFactory SINGLE_THREAD_FACTORY = new RxThreadFactory(
      "RxSingleScheduler", Math.max(1, Math.min(10, Integer.getInteger("rx2.single-priority", 5))), true
   );
   private static final String THREAD_NAME_PREFIX = "RxSingleScheduler";
   final AtomicReference<ScheduledExecutorService> executor;
   final ThreadFactory threadFactory;

   static {
      ScheduledExecutorService var0 = Executors.newScheduledThreadPool(0);
      SHUTDOWN = var0;
      var0.shutdown();
   }

   public SingleScheduler() {
      this(SINGLE_THREAD_FACTORY);
   }

   public SingleScheduler(ThreadFactory var1) {
      AtomicReference var2 = new AtomicReference();
      this.executor = var2;
      this.threadFactory = var1;
      var2.lazySet(createExecutor(var1));
   }

   static ScheduledExecutorService createExecutor(ThreadFactory var0) {
      return SchedulerPoolFactory.create(var0);
   }

   @Override
   public Scheduler.Worker createWorker() {
      return new SingleScheduler.ScheduledWorker(this.executor.get());
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   @Override
   public Disposable scheduleDirect(Runnable var1, long var2, TimeUnit var4) {
      ScheduledDirectTask var5 = new ScheduledDirectTask(RxJavaPlugins.onSchedule(var1));
      Object var9;
      if (var2 <= 0L) {
         try {
            var9 = this.executor.get().submit(var5);
         } catch (RejectedExecutionException var8) {
            RxJavaPlugins.onError(var8);
            return EmptyDisposable.INSTANCE;
         }
      } else {
         try {
            var9 = this.executor.get().schedule(var5, var2, var4);
         } catch (RejectedExecutionException var7) {
            RxJavaPlugins.onError(var7);
            return EmptyDisposable.INSTANCE;
         }
      }

      try {
         var5.setFuture((Future<?>)var9);
         return var5;
      } catch (RejectedExecutionException var6) {
         RxJavaPlugins.onError(var6);
         return EmptyDisposable.INSTANCE;
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   @Override
   public Disposable schedulePeriodicallyDirect(Runnable var1, long var2, long var4, TimeUnit var6) {
      Runnable var7 = RxJavaPlugins.onSchedule(var1);
      if (var4 <= 0L) {
         ScheduledExecutorService var13 = this.executor.get();
         InstantPeriodicTask var15 = new InstantPeriodicTask(var7, var13);
         Object var14;
         if (var2 <= 0L) {
            try {
               var14 = var13.submit(var15);
            } catch (RejectedExecutionException var10) {
               RxJavaPlugins.onError(var10);
               return EmptyDisposable.INSTANCE;
            }
         } else {
            try {
               var14 = var13.schedule(var15, var2, var6);
            } catch (RejectedExecutionException var9) {
               RxJavaPlugins.onError(var9);
               return EmptyDisposable.INSTANCE;
            }
         }

         try {
            var15.setFirst((Future<?>)var14);
            return var15;
         } catch (RejectedExecutionException var8) {
            RxJavaPlugins.onError(var8);
            return EmptyDisposable.INSTANCE;
         }
      } else {
         ScheduledDirectPeriodicTask var12 = new ScheduledDirectPeriodicTask(var7);

         try {
            var12.setFuture(this.executor.get().scheduleAtFixedRate(var12, var2, var4, var6));
            return var12;
         } catch (RejectedExecutionException var11) {
            RxJavaPlugins.onError(var11);
            return EmptyDisposable.INSTANCE;
         }
      }
   }

   @Override
   public void shutdown() {
      ScheduledExecutorService var2 = this.executor.get();
      ScheduledExecutorService var1 = SHUTDOWN;
      if (var2 != var1) {
         var2 = this.executor.getAndSet(var1);
         if (var2 != var1) {
            var2.shutdownNow();
         }
      }
   }

   @Override
   public void start() {
      ScheduledExecutorService var2 = null;

      ScheduledExecutorService var1;
      ScheduledExecutorService var3;
      do {
         var3 = this.executor.get();
         if (var3 != SHUTDOWN) {
            if (var2 != null) {
               var2.shutdown();
            }

            return;
         }

         var1 = var2;
         if (var2 == null) {
            var1 = createExecutor(this.threadFactory);
         }

         var2 = var1;
      } while (!ExternalSyntheticBackportWithForwarding0.m(this.executor, var3, var1));
   }

   static final class ScheduledWorker extends Scheduler.Worker {
      volatile boolean disposed;
      final ScheduledExecutorService executor;
      final CompositeDisposable tasks;

      ScheduledWorker(ScheduledExecutorService var1) {
         this.executor = var1;
         this.tasks = new CompositeDisposable();
      }

      @Override
      public void dispose() {
         if (!this.disposed) {
            this.disposed = true;
            this.tasks.dispose();
         }
      }

      @Override
      public boolean isDisposed() {
         return this.disposed;
      }

      // $VF: Duplicated exception handlers to handle obfuscated exceptions
      @Override
      public Disposable schedule(Runnable var1, long var2, TimeUnit var4) {
         if (this.disposed) {
            return EmptyDisposable.INSTANCE;
         } else {
            ScheduledRunnable var5 = new ScheduledRunnable(RxJavaPlugins.onSchedule(var1), this.tasks);
            this.tasks.add(var5);
            Object var9;
            if (var2 <= 0L) {
               try {
                  var9 = this.executor.submit(var5);
               } catch (RejectedExecutionException var8) {
                  this.dispose();
                  RxJavaPlugins.onError(var8);
                  return EmptyDisposable.INSTANCE;
               }
            } else {
               try {
                  var9 = this.executor.schedule((Callable<Object>)var5, var2, var4);
               } catch (RejectedExecutionException var7) {
                  this.dispose();
                  RxJavaPlugins.onError(var7);
                  return EmptyDisposable.INSTANCE;
               }
            }

            try {
               var5.setFuture((Future<?>)var9);
               return var5;
            } catch (RejectedExecutionException var6) {
               this.dispose();
               RxJavaPlugins.onError(var6);
               return EmptyDisposable.INSTANCE;
            }
         }
      }
   }
}
