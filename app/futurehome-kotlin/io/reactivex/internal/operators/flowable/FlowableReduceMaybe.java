package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.FuseToFlowable;
import io.reactivex.internal.fuseable.HasUpstreamPublisher;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

public final class FlowableReduceMaybe<T> extends Maybe<T> implements HasUpstreamPublisher<T>, FuseToFlowable<T> {
   final BiFunction<T, T, T> reducer;
   final Flowable<T> source;

   public FlowableReduceMaybe(Flowable<T> var1, BiFunction<T, T, T> var2) {
      this.source = var1;
      this.reducer = var2;
   }

   @Override
   public Flowable<T> fuseToFlowable() {
      return RxJavaPlugins.onAssembly(new FlowableReduce<>(this.source, this.reducer));
   }

   @Override
   public Publisher<T> source() {
      return this.source;
   }

   @Override
   protected void subscribeActual(MaybeObserver<? super T> var1) {
      this.source.subscribe(new FlowableReduceMaybe.ReduceSubscriber<>(var1, this.reducer));
   }

   static final class ReduceSubscriber<T> implements FlowableSubscriber<T>, Disposable {
      boolean done;
      final MaybeObserver<? super T> downstream;
      final BiFunction<T, T, T> reducer;
      Subscription upstream;
      T value;

      ReduceSubscriber(MaybeObserver<? super T> var1, BiFunction<T, T, T> var2) {
         this.downstream = var1;
         this.reducer = var2;
      }

      @Override
      public void dispose() {
         this.upstream.cancel();
         this.done = true;
      }

      @Override
      public boolean isDisposed() {
         return this.done;
      }

      public void onComplete() {
         if (!this.done) {
            this.done = true;
            Object var1 = this.value;
            if (var1 != null) {
               this.downstream.onSuccess((T)var1);
            } else {
               this.downstream.onComplete();
            }
         }
      }

      public void onError(Throwable var1) {
         if (this.done) {
            RxJavaPlugins.onError(var1);
         } else {
            this.done = true;
            this.downstream.onError(var1);
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void onNext(T var1) {
         if (!this.done) {
            Object var2 = this.value;
            if (var2 == null) {
               this.value = (T)var1;
            } else {
               try {
                  this.value = ObjectHelper.requireNonNull(this.reducer.apply((T)var2, (T)var1), "The reducer returned a null value");
               } catch (Throwable var4) {
                  Exceptions.throwIfFatal(var4);
                  this.upstream.cancel();
                  this.onError(var4);
                  return;
               }
            }
         }
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
            var1.request(Long.MAX_VALUE);
         }
      }
   }
}
