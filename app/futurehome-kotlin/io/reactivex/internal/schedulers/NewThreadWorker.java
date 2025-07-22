package io.reactivex.internal.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableContainer;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class NewThreadWorker extends Scheduler.Worker implements Disposable {
   volatile boolean disposed;
   private final ScheduledExecutorService executor;

   public NewThreadWorker(ThreadFactory var1) {
      this.executor = SchedulerPoolFactory.create(var1);
   }

   @Override
   public void dispose() {
      if (!this.disposed) {
         this.disposed = true;
         this.executor.shutdownNow();
      }
   }

   @Override
   public boolean isDisposed() {
      return this.disposed;
   }

   @Override
   public Disposable schedule(Runnable var1) {
      return this.schedule(var1, 0L, null);
   }

   @Override
   public Disposable schedule(Runnable var1, long var2, TimeUnit var4) {
      return (Disposable)(this.disposed ? EmptyDisposable.INSTANCE : this.scheduleActual(var1, var2, var4, null));
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public ScheduledRunnable scheduleActual(Runnable var1, long var2, TimeUnit var4, DisposableContainer var5) {
      ScheduledRunnable var6 = new ScheduledRunnable(RxJavaPlugins.onSchedule(var1), var5);
      if (var5 != null && !var5.add(var6)) {
         return var6;
      } else {
         label38: {
            label37: {
               Object var11;
               if (var2 <= 0L) {
                  try {
                     var11 = this.executor.submit(var6);
                  } catch (RejectedExecutionException var9) {
                     var10 = var9;
                     if (var5 == null) {
                        break label38;
                     }
                     break label37;
                  }
               } else {
                  try {
                     var11 = this.executor.schedule((Callable<Object>)var6, var2, var4);
                  } catch (RejectedExecutionException var8) {
                     var10 = var8;
                     if (var5 == null) {
                        break label38;
                     }
                     break label37;
                  }
               }

               try {
                  var6.setFuture((Future<?>)var11);
                  return var6;
               } catch (RejectedExecutionException var7) {
                  var10 = var7;
                  if (var5 == null) {
                     break label38;
                  }
               }
            }

            var5.remove(var6);
         }

         RxJavaPlugins.onError(var10);
         return var6;
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public Disposable scheduleDirect(Runnable var1, long var2, TimeUnit var4) {
      ScheduledDirectTask var5 = new ScheduledDirectTask(RxJavaPlugins.onSchedule(var1));
      Object var9;
      if (var2 <= 0L) {
         try {
            var9 = this.executor.submit(var5);
         } catch (RejectedExecutionException var8) {
            RxJavaPlugins.onError(var8);
            return EmptyDisposable.INSTANCE;
         }
      } else {
         try {
            var9 = this.executor.schedule(var5, var2, var4);
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
   public Disposable schedulePeriodicallyDirect(Runnable var1, long var2, long var4, TimeUnit var6) {
      var1 = RxJavaPlugins.onSchedule(var1);
      if (var4 <= 0L) {
         InstantPeriodicTask var7 = new InstantPeriodicTask(var1, this.executor);
         Object var14;
         if (var2 <= 0L) {
            try {
               var14 = this.executor.submit(var7);
            } catch (RejectedExecutionException var10) {
               RxJavaPlugins.onError(var10);
               return EmptyDisposable.INSTANCE;
            }
         } else {
            try {
               var14 = this.executor.schedule(var7, var2, var6);
            } catch (RejectedExecutionException var9) {
               RxJavaPlugins.onError(var9);
               return EmptyDisposable.INSTANCE;
            }
         }

         try {
            var7.setFirst((Future<?>)var14);
            return var7;
         } catch (RejectedExecutionException var8) {
            RxJavaPlugins.onError(var8);
            return EmptyDisposable.INSTANCE;
         }
      } else {
         ScheduledDirectPeriodicTask var13 = new ScheduledDirectPeriodicTask(var1);

         try {
            var13.setFuture(this.executor.scheduleAtFixedRate(var13, var2, var4, var6));
            return var13;
         } catch (RejectedExecutionException var11) {
            RxJavaPlugins.onError(var11);
            return EmptyDisposable.INSTANCE;
         }
      }
   }

   public void shutdown() {
      if (!this.disposed) {
         this.disposed = true;
         this.executor.shutdown();
      }
   }
}
