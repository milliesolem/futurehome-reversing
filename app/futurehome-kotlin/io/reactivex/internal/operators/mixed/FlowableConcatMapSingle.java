package io.reactivex.internal.operators.mixed;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
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
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.ErrorMode;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableConcatMapSingle<T, R> extends Flowable<R> {
   final ErrorMode errorMode;
   final Function<? super T, ? extends SingleSource<? extends R>> mapper;
   final int prefetch;
   final Flowable<T> source;

   public FlowableConcatMapSingle(Flowable<T> var1, Function<? super T, ? extends SingleSource<? extends R>> var2, ErrorMode var3, int var4) {
      this.source = var1;
      this.mapper = var2;
      this.errorMode = var3;
      this.prefetch = var4;
   }

   @Override
   protected void subscribeActual(Subscriber<? super R> var1) {
      this.source.subscribe(new FlowableConcatMapSingle.ConcatMapSingleSubscriber<>(var1, this.mapper, this.prefetch, this.errorMode));
   }

   static final class ConcatMapSingleSubscriber<T, R> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
      static final int STATE_ACTIVE = 1;
      static final int STATE_INACTIVE = 0;
      static final int STATE_RESULT_VALUE = 2;
      private static final long serialVersionUID = -9140123220065488293L;
      volatile boolean cancelled;
      int consumed;
      volatile boolean done;
      final Subscriber<? super R> downstream;
      long emitted;
      final ErrorMode errorMode;
      final AtomicThrowable errors;
      final FlowableConcatMapSingle.ConcatMapSingleSubscriber.ConcatMapSingleObserver<R> inner;
      R item;
      final Function<? super T, ? extends SingleSource<? extends R>> mapper;
      final int prefetch;
      final SimplePlainQueue<T> queue;
      final AtomicLong requested;
      volatile int state;
      Subscription upstream;

      ConcatMapSingleSubscriber(Subscriber<? super R> var1, Function<? super T, ? extends SingleSource<? extends R>> var2, int var3, ErrorMode var4) {
         this.downstream = var1;
         this.mapper = var2;
         this.prefetch = var3;
         this.errorMode = var4;
         this.requested = new AtomicLong();
         this.errors = new AtomicThrowable();
         this.inner = new FlowableConcatMapSingle.ConcatMapSingleSubscriber.ConcatMapSingleObserver<>(this);
         this.queue = new SpscArrayQueue<>(var3);
      }

      public void cancel() {
         this.cancelled = true;
         this.upstream.cancel();
         this.inner.dispose();
         if (this.getAndIncrement() == 0) {
            this.queue.clear();
            this.item = null;
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      void drain() {
         if (this.getAndIncrement() == 0) {
            Subscriber var7 = this.downstream;
            ErrorMode var10 = this.errorMode;
            SimplePlainQueue var9 = this.queue;
            AtomicThrowable var8 = this.errors;
            AtomicLong var11 = this.requested;
            int var1 = this.prefetch;
            int var3 = var1 - (var1 >> 1);
            var1 = 1;

            while (true) {
               if (this.cancelled) {
                  var9.clear();
                  this.item = null;
               } else {
                  int var2 = this.state;
                  if (var8.get() != null && (var10 == ErrorMode.IMMEDIATE || var10 == ErrorMode.BOUNDARY && var2 == 0)) {
                     var9.clear();
                     this.item = null;
                     var7.onError(var8.terminate());
                     return;
                  }

                  if (var2 == 0) {
                     boolean var4 = this.done;
                     SingleSource var12 = (SingleSource)var9.poll();
                     boolean var16;
                     if (var12 == null) {
                        var16 = 1;
                     } else {
                        var16 = 0;
                     }

                     if (var4 && var16) {
                        Throwable var19 = var8.terminate();
                        if (var19 == null) {
                           var7.onComplete();
                        } else {
                           var7.onError(var19);
                        }

                        return;
                     }

                     if (!var16) {
                        var16 = this.consumed + 1;
                        if (var16 == var3) {
                           this.consumed = 0;
                           this.upstream.request(var3);
                        } else {
                           this.consumed = var16;
                        }

                        try {
                           var12 = ObjectHelper.requireNonNull(this.mapper.apply((T)var12), "The mapper returned a null SingleSource");
                        } catch (Throwable var14) {
                           Exceptions.throwIfFatal(var14);
                           this.upstream.cancel();
                           var9.clear();
                           var8.addThrowable(var14);
                           var7.onError(var8.terminate());
                           return;
                        }

                        this.state = 1;
                        var12.subscribe(this.inner);
                     }
                  } else if (var2 == 2) {
                     long var5 = this.emitted;
                     if (var5 != var11.get()) {
                        Object var21 = this.item;
                        this.item = null;
                        var7.onNext(var21);
                        this.emitted = var5 + 1L;
                        this.state = 0;
                        continue;
                     }
                  }
               }

               int var18 = this.addAndGet(-var1);
               var1 = var18;
               if (var18 == 0) {
                  return;
               }
            }
         }
      }

      void innerError(Throwable var1) {
         if (this.errors.addThrowable(var1)) {
            if (this.errorMode != ErrorMode.END) {
               this.upstream.cancel();
            }

            this.state = 0;
            this.drain();
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      void innerSuccess(R var1) {
         this.item = (R)var1;
         this.state = 2;
         this.drain();
      }

      public void onComplete() {
         this.done = true;
         this.drain();
      }

      public void onError(Throwable var1) {
         if (this.errors.addThrowable(var1)) {
            if (this.errorMode == ErrorMode.IMMEDIATE) {
               this.inner.dispose();
            }

            this.done = true;
            this.drain();
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      public void onNext(T var1) {
         if (!this.queue.offer((T)var1)) {
            this.upstream.cancel();
            this.onError(new MissingBackpressureException("queue full?!"));
         } else {
            this.drain();
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

      public void request(long var1) {
         BackpressureHelper.add(this.requested, var1);
         this.drain();
      }

      static final class ConcatMapSingleObserver<R> extends AtomicReference<Disposable> implements SingleObserver<R> {
         private static final long serialVersionUID = -3051469169682093892L;
         final FlowableConcatMapSingle.ConcatMapSingleSubscriber<?, R> parent;

         ConcatMapSingleObserver(FlowableConcatMapSingle.ConcatMapSingleSubscriber<?, R> var1) {
            this.parent = var1;
         }

         void dispose() {
            DisposableHelper.dispose(this);
         }

         @Override
         public void onError(Throwable var1) {
            this.parent.innerError(var1);
         }

         @Override
         public void onSubscribe(Disposable var1) {
            DisposableHelper.replace(this, var1);
         }

         @Override
         public void onSuccess(R var1) {
            this.parent.innerSuccess((R)var1);
         }
      }
   }
}
