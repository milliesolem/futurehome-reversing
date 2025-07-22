package io.reactivex.internal.operators.mixed;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.QueueDisposable;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.ErrorMode;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableConcatMapCompletable<T> extends Completable {
   final ErrorMode errorMode;
   final Function<? super T, ? extends CompletableSource> mapper;
   final int prefetch;
   final Observable<T> source;

   public ObservableConcatMapCompletable(Observable<T> var1, Function<? super T, ? extends CompletableSource> var2, ErrorMode var3, int var4) {
      this.source = var1;
      this.mapper = var2;
      this.errorMode = var3;
      this.prefetch = var4;
   }

   @Override
   protected void subscribeActual(CompletableObserver var1) {
      if (!ScalarXMapZHelper.tryAsCompletable(this.source, this.mapper, var1)) {
         this.source.subscribe(new ObservableConcatMapCompletable.ConcatMapCompletableObserver<>(var1, this.mapper, this.errorMode, this.prefetch));
      }
   }

   static final class ConcatMapCompletableObserver<T> extends AtomicInteger implements Observer<T>, Disposable {
      private static final long serialVersionUID = 3610901111000061034L;
      volatile boolean active;
      volatile boolean disposed;
      volatile boolean done;
      final CompletableObserver downstream;
      final ErrorMode errorMode;
      final AtomicThrowable errors;
      final ObservableConcatMapCompletable.ConcatMapCompletableObserver.ConcatMapInnerObserver inner;
      final Function<? super T, ? extends CompletableSource> mapper;
      final int prefetch;
      SimpleQueue<T> queue;
      Disposable upstream;

      ConcatMapCompletableObserver(CompletableObserver var1, Function<? super T, ? extends CompletableSource> var2, ErrorMode var3, int var4) {
         this.downstream = var1;
         this.mapper = var2;
         this.errorMode = var3;
         this.prefetch = var4;
         this.errors = new AtomicThrowable();
         this.inner = new ObservableConcatMapCompletable.ConcatMapCompletableObserver.ConcatMapInnerObserver(this);
      }

      @Override
      public void dispose() {
         this.disposed = true;
         this.upstream.dispose();
         this.inner.dispose();
         if (this.getAndIncrement() == 0) {
            this.queue.clear();
         }
      }

      // $VF: Duplicated exception handlers to handle obfuscated exceptions
      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      void drain() {
         if (this.getAndIncrement() == 0) {
            AtomicThrowable var4 = this.errors;
            ErrorMode var5 = this.errorMode;

            while (!this.disposed) {
               if (!this.active) {
                  if (var5 == ErrorMode.BOUNDARY && var4.get() != null) {
                     this.disposed = true;
                     this.queue.clear();
                     Throwable var16 = var4.terminate();
                     this.downstream.onError(var16);
                     return;
                  }

                  boolean var2 = this.done;

                  Object var12;
                  try {
                     var12 = this.queue.poll();
                  } catch (Throwable var11) {
                     Exceptions.throwIfFatal(var11);
                     this.disposed = true;
                     this.queue.clear();
                     this.upstream.dispose();
                     var4.addThrowable(var11);
                     var12 = var4.terminate();
                     this.downstream.onError((Throwable)var12);
                     return;
                  }

                  boolean var1;
                  if (var12 != null) {
                     try {
                        var12 = ObjectHelper.requireNonNull(this.mapper.apply((T)var12), "The mapper returned a null CompletableSource");
                     } catch (Throwable var10) {
                        Exceptions.throwIfFatal(var10);
                        this.disposed = true;
                        this.queue.clear();
                        this.upstream.dispose();
                        var4.addThrowable(var10);
                        Throwable var13 = var4.terminate();
                        this.downstream.onError(var13);
                        return;
                     }

                     var1 = false;
                  } else {
                     var12 = null;
                     var1 = true;
                  }

                  if (var2 && var1) {
                     this.disposed = true;
                     Throwable var15 = var4.terminate();
                     if (var15 != null) {
                        this.downstream.onError(var15);
                     } else {
                        this.downstream.onComplete();
                     }

                     return;
                  }

                  if (!var1) {
                     this.active = true;
                     var12.subscribe(this.inner);
                  }
               }

               if (this.decrementAndGet() == 0) {
                  return;
               }
            }

            this.queue.clear();
         }
      }

      void innerComplete() {
         this.active = false;
         this.drain();
      }

      void innerError(Throwable var1) {
         if (this.errors.addThrowable(var1)) {
            if (this.errorMode == ErrorMode.IMMEDIATE) {
               this.disposed = true;
               this.upstream.dispose();
               var1 = this.errors.terminate();
               if (var1 != ExceptionHelper.TERMINATED) {
                  this.downstream.onError(var1);
               }

               if (this.getAndIncrement() == 0) {
                  this.queue.clear();
               }
            } else {
               this.active = false;
               this.drain();
            }
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      @Override
      public boolean isDisposed() {
         return this.disposed;
      }

      @Override
      public void onComplete() {
         this.done = true;
         this.drain();
      }

      @Override
      public void onError(Throwable var1) {
         if (this.errors.addThrowable(var1)) {
            if (this.errorMode == ErrorMode.IMMEDIATE) {
               this.disposed = true;
               this.inner.dispose();
               var1 = this.errors.terminate();
               if (var1 != ExceptionHelper.TERMINATED) {
                  this.downstream.onError(var1);
               }

               if (this.getAndIncrement() == 0) {
                  this.queue.clear();
               }
            } else {
               this.done = true;
               this.drain();
            }
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      @Override
      public void onNext(T var1) {
         if (var1 != null) {
            this.queue.offer((T)var1);
         }

         this.drain();
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            if (var1 instanceof QueueDisposable) {
               QueueDisposable var3 = (QueueDisposable)var1;
               int var2 = var3.requestFusion(3);
               if (var2 == 1) {
                  this.queue = var3;
                  this.done = true;
                  this.downstream.onSubscribe(this);
                  this.drain();
                  return;
               }

               if (var2 == 2) {
                  this.queue = var3;
                  this.downstream.onSubscribe(this);
                  return;
               }
            }

            this.queue = new SpscLinkedArrayQueue<>(this.prefetch);
            this.downstream.onSubscribe(this);
         }
      }

      static final class ConcatMapInnerObserver extends AtomicReference<Disposable> implements CompletableObserver {
         private static final long serialVersionUID = 5638352172918776687L;
         final ObservableConcatMapCompletable.ConcatMapCompletableObserver<?> parent;

         ConcatMapInnerObserver(ObservableConcatMapCompletable.ConcatMapCompletableObserver<?> var1) {
            this.parent = var1;
         }

         void dispose() {
            DisposableHelper.dispose(this);
         }

         @Override
         public void onComplete() {
            this.parent.innerComplete();
         }

         @Override
         public void onError(Throwable var1) {
            this.parent.innerError(var1);
         }

         @Override
         public void onSubscribe(Disposable var1) {
            DisposableHelper.replace(this, var1);
         }
      }
   }
}
