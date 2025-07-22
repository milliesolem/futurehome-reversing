package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

public final class CompletableConcat extends Completable {
   final int prefetch;
   final Publisher<? extends CompletableSource> sources;

   public CompletableConcat(Publisher<? extends CompletableSource> var1, int var2) {
      this.sources = var1;
      this.prefetch = var2;
   }

   @Override
   public void subscribeActual(CompletableObserver var1) {
      this.sources.subscribe(new CompletableConcat.CompletableConcatSubscriber(var1, this.prefetch));
   }

   static final class CompletableConcatSubscriber extends AtomicInteger implements FlowableSubscriber<CompletableSource>, Disposable {
      private static final long serialVersionUID = 9032184911934499404L;
      volatile boolean active;
      int consumed;
      volatile boolean done;
      final CompletableObserver downstream;
      final CompletableConcat.CompletableConcatSubscriber.ConcatInnerObserver inner;
      final int limit;
      final AtomicBoolean once;
      final int prefetch;
      SimpleQueue<CompletableSource> queue;
      int sourceFused;
      Subscription upstream;

      CompletableConcatSubscriber(CompletableObserver var1, int var2) {
         this.downstream = var1;
         this.prefetch = var2;
         this.inner = new CompletableConcat.CompletableConcatSubscriber.ConcatInnerObserver(this);
         this.once = new AtomicBoolean();
         this.limit = var2 - (var2 >> 2);
      }

      @Override
      public void dispose() {
         this.upstream.cancel();
         DisposableHelper.dispose(this.inner);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      void drain() {
         if (this.getAndIncrement() == 0) {
            while (!this.isDisposed()) {
               if (!this.active) {
                  boolean var2 = this.done;

                  CompletableSource var3;
                  try {
                     var3 = this.queue.poll();
                  } catch (Throwable var5) {
                     Exceptions.throwIfFatal(var5);
                     this.innerError(var5);
                     return;
                  }

                  boolean var1;
                  if (var3 == null) {
                     var1 = true;
                  } else {
                     var1 = false;
                  }

                  if (var2 && var1) {
                     if (this.once.compareAndSet(false, true)) {
                        this.downstream.onComplete();
                     }

                     return;
                  }

                  if (!var1) {
                     this.active = true;
                     var3.subscribe(this.inner);
                     this.request();
                  }
               }

               if (this.decrementAndGet() == 0) {
                  return;
               }
            }
         }
      }

      void innerComplete() {
         this.active = false;
         this.drain();
      }

      void innerError(Throwable var1) {
         if (this.once.compareAndSet(false, true)) {
            this.upstream.cancel();
            this.downstream.onError(var1);
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      @Override
      public boolean isDisposed() {
         return DisposableHelper.isDisposed(this.inner.get());
      }

      public void onComplete() {
         this.done = true;
         this.drain();
      }

      public void onError(Throwable var1) {
         if (this.once.compareAndSet(false, true)) {
            DisposableHelper.dispose(this.inner);
            this.downstream.onError(var1);
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      public void onNext(CompletableSource var1) {
         if (this.sourceFused == 0 && !this.queue.offer(var1)) {
            this.onError(new MissingBackpressureException());
         } else {
            this.drain();
         }
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            int var2 = this.prefetch;
            long var3;
            if (var2 == Integer.MAX_VALUE) {
               var3 = Long.MAX_VALUE;
            } else {
               var3 = var2;
            }

            if (var1 instanceof QueueSubscription) {
               QueueSubscription var5 = (QueueSubscription)var1;
               var2 = var5.requestFusion(3);
               if (var2 == 1) {
                  this.sourceFused = var2;
                  this.queue = var5;
                  this.done = true;
                  this.downstream.onSubscribe(this);
                  this.drain();
                  return;
               }

               if (var2 == 2) {
                  this.sourceFused = var2;
                  this.queue = var5;
                  this.downstream.onSubscribe(this);
                  var1.request(var3);
                  return;
               }
            }

            if (this.prefetch == Integer.MAX_VALUE) {
               this.queue = new SpscLinkedArrayQueue<>(Flowable.bufferSize());
            } else {
               this.queue = new SpscArrayQueue<>(this.prefetch);
            }

            this.downstream.onSubscribe(this);
            var1.request(var3);
         }
      }

      void request() {
         if (this.sourceFused != 1) {
            int var1 = this.consumed + 1;
            if (var1 == this.limit) {
               this.consumed = 0;
               this.upstream.request(var1);
            } else {
               this.consumed = var1;
            }
         }
      }

      static final class ConcatInnerObserver extends AtomicReference<Disposable> implements CompletableObserver {
         private static final long serialVersionUID = -5454794857847146511L;
         final CompletableConcat.CompletableConcatSubscriber parent;

         ConcatInnerObserver(CompletableConcat.CompletableConcatSubscriber var1) {
            this.parent = var1;
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
