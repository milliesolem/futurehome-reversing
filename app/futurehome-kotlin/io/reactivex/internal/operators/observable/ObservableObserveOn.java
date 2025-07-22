package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.QueueDisposable;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.observers.BasicIntQueueDisposable;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.schedulers.TrampolineScheduler;
import io.reactivex.plugins.RxJavaPlugins;

public final class ObservableObserveOn<T> extends AbstractObservableWithUpstream<T, T> {
   final int bufferSize;
   final boolean delayError;
   final Scheduler scheduler;

   public ObservableObserveOn(ObservableSource<T> var1, Scheduler var2, boolean var3, int var4) {
      super(var1);
      this.scheduler = var2;
      this.delayError = var3;
      this.bufferSize = var4;
   }

   @Override
   protected void subscribeActual(Observer<? super T> var1) {
      Scheduler var2 = this.scheduler;
      if (var2 instanceof TrampolineScheduler) {
         this.source.subscribe(var1);
      } else {
         Scheduler.Worker var3 = var2.createWorker();
         this.source.subscribe(new ObservableObserveOn.ObserveOnObserver<>(var1, var3, this.delayError, this.bufferSize));
      }
   }

   static final class ObserveOnObserver<T> extends BasicIntQueueDisposable<T> implements Observer<T>, Runnable {
      private static final long serialVersionUID = 6576896619930983584L;
      final int bufferSize;
      final boolean delayError;
      volatile boolean disposed;
      volatile boolean done;
      final Observer<? super T> downstream;
      Throwable error;
      boolean outputFused;
      SimpleQueue<T> queue;
      int sourceMode;
      Disposable upstream;
      final Scheduler.Worker worker;

      ObserveOnObserver(Observer<? super T> var1, Scheduler.Worker var2, boolean var3, int var4) {
         this.downstream = var1;
         this.worker = var2;
         this.delayError = var3;
         this.bufferSize = var4;
      }

      boolean checkTerminated(boolean var1, boolean var2, Observer<? super T> var3) {
         if (this.disposed) {
            this.queue.clear();
            return true;
         } else {
            if (var1) {
               Throwable var4 = this.error;
               if (this.delayError) {
                  if (var2) {
                     this.disposed = true;
                     if (var4 != null) {
                        var3.onError(var4);
                     } else {
                        var3.onComplete();
                     }

                     this.worker.dispose();
                     return true;
                  }
               } else {
                  if (var4 != null) {
                     this.disposed = true;
                     this.queue.clear();
                     var3.onError(var4);
                     this.worker.dispose();
                     return true;
                  }

                  if (var2) {
                     this.disposed = true;
                     var3.onComplete();
                     this.worker.dispose();
                     return true;
                  }
               }
            }

            return false;
         }
      }

      @Override
      public void clear() {
         this.queue.clear();
      }

      @Override
      public void dispose() {
         if (!this.disposed) {
            this.disposed = true;
            this.upstream.dispose();
            this.worker.dispose();
            if (!this.outputFused && this.getAndIncrement() == 0) {
               this.queue.clear();
            }
         }
      }

      void drainFused() {
         int var1 = 1;

         while (!this.disposed) {
            boolean var3 = this.done;
            Throwable var4 = this.error;
            if (!this.delayError && var3 && var4 != null) {
               this.disposed = true;
               this.downstream.onError(this.error);
               this.worker.dispose();
               return;
            }

            this.downstream.onNext(null);
            if (var3) {
               this.disposed = true;
               var4 = this.error;
               if (var4 != null) {
                  this.downstream.onError(var4);
               } else {
                  this.downstream.onComplete();
               }

               this.worker.dispose();
               return;
            }

            int var2 = this.addAndGet(-var1);
            var1 = var2;
            if (var2 == 0) {
               return;
            }
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      void drainNormal() {
         SimpleQueue var5 = this.queue;
         Observer var6 = this.downstream;
         int var1 = 1;

         while (!this.checkTerminated(this.done, var5.isEmpty(), var6)) {
            boolean var4 = this.done;

            Object var7;
            try {
               var7 = var5.poll();
            } catch (Throwable var9) {
               Exceptions.throwIfFatal(var9);
               this.disposed = true;
               this.upstream.dispose();
               var5.clear();
               var6.onError(var9);
               this.worker.dispose();
               return;
            }

            boolean var3;
            if (var7 == null) {
               var3 = true;
            } else {
               var3 = false;
            }

            if (this.checkTerminated(var4, var3, var6)) {
               return;
            }

            if (var3) {
               int var2 = this.addAndGet(-var1);
               var1 = var2;
               if (var2 == 0) {
                  return;
               }
            } else {
               var6.onNext(var7);
            }
         }
      }

      @Override
      public boolean isDisposed() {
         return this.disposed;
      }

      @Override
      public boolean isEmpty() {
         return this.queue.isEmpty();
      }

      @Override
      public void onComplete() {
         if (!this.done) {
            this.done = true;
            this.schedule();
         }
      }

      @Override
      public void onError(Throwable var1) {
         if (this.done) {
            RxJavaPlugins.onError(var1);
         } else {
            this.error = var1;
            this.done = true;
            this.schedule();
         }
      }

      @Override
      public void onNext(T var1) {
         if (!this.done) {
            if (this.sourceMode != 2) {
               this.queue.offer((T)var1);
            }

            this.schedule();
         }
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            if (var1 instanceof QueueDisposable) {
               QueueDisposable var3 = (QueueDisposable)var1;
               int var2 = var3.requestFusion(7);
               if (var2 == 1) {
                  this.sourceMode = var2;
                  this.queue = var3;
                  this.done = true;
                  this.downstream.onSubscribe(this);
                  this.schedule();
                  return;
               }

               if (var2 == 2) {
                  this.sourceMode = var2;
                  this.queue = var3;
                  this.downstream.onSubscribe(this);
                  return;
               }
            }

            this.queue = new SpscLinkedArrayQueue<>(this.bufferSize);
            this.downstream.onSubscribe(this);
         }
      }

      @Override
      public T poll() throws Exception {
         return this.queue.poll();
      }

      @Override
      public int requestFusion(int var1) {
         if ((var1 & 2) != 0) {
            this.outputFused = true;
            return 2;
         } else {
            return 0;
         }
      }

      @Override
      public void run() {
         if (this.outputFused) {
            this.drainFused();
         } else {
            this.drainNormal();
         }
      }

      void schedule() {
         if (this.getAndIncrement() == 0) {
            this.worker.schedule(this);
         }
      }
   }
}
