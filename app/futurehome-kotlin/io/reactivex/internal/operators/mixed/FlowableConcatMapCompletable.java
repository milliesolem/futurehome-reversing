package io.reactivex.internal.operators.mixed;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.ErrorMode;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;

public final class FlowableConcatMapCompletable<T> extends Completable {
   final ErrorMode errorMode;
   final Function<? super T, ? extends CompletableSource> mapper;
   final int prefetch;
   final Flowable<T> source;

   public FlowableConcatMapCompletable(Flowable<T> var1, Function<? super T, ? extends CompletableSource> var2, ErrorMode var3, int var4) {
      this.source = var1;
      this.mapper = var2;
      this.errorMode = var3;
      this.prefetch = var4;
   }

   @Override
   protected void subscribeActual(CompletableObserver var1) {
      this.source.subscribe(new FlowableConcatMapCompletable.ConcatMapCompletableObserver<>(var1, this.mapper, this.errorMode, this.prefetch));
   }

   static final class ConcatMapCompletableObserver<T> extends AtomicInteger implements FlowableSubscriber<T>, Disposable {
      private static final long serialVersionUID = 3610901111000061034L;
      volatile boolean active;
      int consumed;
      volatile boolean disposed;
      volatile boolean done;
      final CompletableObserver downstream;
      final ErrorMode errorMode;
      final AtomicThrowable errors;
      final FlowableConcatMapCompletable.ConcatMapCompletableObserver.ConcatMapInnerObserver inner;
      final Function<? super T, ? extends CompletableSource> mapper;
      final int prefetch;
      final SimplePlainQueue<T> queue;
      Subscription upstream;

      ConcatMapCompletableObserver(CompletableObserver var1, Function<? super T, ? extends CompletableSource> var2, ErrorMode var3, int var4) {
         this.downstream = var1;
         this.mapper = var2;
         this.errorMode = var3;
         this.prefetch = var4;
         this.errors = new AtomicThrowable();
         this.inner = new FlowableConcatMapCompletable.ConcatMapCompletableObserver.ConcatMapInnerObserver(this);
         this.queue = new SpscArrayQueue<>(var4);
      }

      @Override
      public void dispose() {
         this.disposed = true;
         this.upstream.cancel();
         this.inner.dispose();
         if (this.getAndIncrement() == 0) {
            this.queue.clear();
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      void drain() {
         if (this.getAndIncrement() == 0) {
            while (!this.disposed) {
               if (!this.active) {
                  if (this.errorMode == ErrorMode.BOUNDARY && this.errors.get() != null) {
                     this.queue.clear();
                     Throwable var12 = this.errors.terminate();
                     this.downstream.onError(var12);
                     return;
                  }

                  boolean var3 = this.done;
                  CompletableSource var4 = this.queue.poll();
                  boolean var1;
                  if (var4 == null) {
                     var1 = 1;
                  } else {
                     var1 = 0;
                  }

                  if (var3 && var1) {
                     var4 = this.errors.terminate();
                     if (var4 != null) {
                        this.downstream.onError((Throwable)var4);
                     } else {
                        this.downstream.onComplete();
                     }

                     return;
                  }

                  if (!var1) {
                     var1 = this.prefetch;
                     var1 -= var1 >> 1;
                     int var2 = this.consumed + 1;
                     if (var2 == var1) {
                        this.consumed = 0;
                        this.upstream.request(var1);
                     } else {
                        this.consumed = var2;
                     }

                     try {
                        var4 = ObjectHelper.requireNonNull(this.mapper.apply((T)var4), "The mapper returned a null CompletableSource");
                     } catch (Throwable var6) {
                        Exceptions.throwIfFatal(var6);
                        this.queue.clear();
                        this.upstream.cancel();
                        this.errors.addThrowable(var6);
                        Throwable var9 = this.errors.terminate();
                        this.downstream.onError(var9);
                        return;
                     }

                     this.active = true;
                     var4.subscribe(this.inner);
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
               this.upstream.cancel();
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

      public void onComplete() {
         this.done = true;
         this.drain();
      }

      public void onError(Throwable var1) {
         if (this.errors.addThrowable(var1)) {
            if (this.errorMode == ErrorMode.IMMEDIATE) {
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

      public void onNext(T var1) {
         if (this.queue.offer((T)var1)) {
            this.drain();
         } else {
            this.upstream.cancel();
            this.onError(new MissingBackpressureException("Queue full?!"));
         }
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
            var1.request(this.prefetch);
         }
      }

      static final class ConcatMapInnerObserver extends AtomicReference<Disposable> implements CompletableObserver {
         private static final long serialVersionUID = 5638352172918776687L;
         final FlowableConcatMapCompletable.ConcatMapCompletableObserver<?> parent;

         ConcatMapInnerObserver(FlowableConcatMapCompletable.ConcatMapCompletableObserver<?> var1) {
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
