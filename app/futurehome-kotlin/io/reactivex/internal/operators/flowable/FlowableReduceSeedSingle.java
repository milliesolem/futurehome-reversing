package io.reactivex.internal.operators.flowable;

import io.reactivex.FlowableSubscriber;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

public final class FlowableReduceSeedSingle<T, R> extends Single<R> {
   final BiFunction<R, ? super T, R> reducer;
   final R seed;
   final Publisher<T> source;

   public FlowableReduceSeedSingle(Publisher<T> var1, R var2, BiFunction<R, ? super T, R> var3) {
      this.source = var1;
      this.seed = (R)var2;
      this.reducer = var3;
   }

   @Override
   protected void subscribeActual(SingleObserver<? super R> var1) {
      this.source.subscribe(new FlowableReduceSeedSingle.ReduceSeedObserver<>(var1, this.reducer, this.seed));
   }

   static final class ReduceSeedObserver<T, R> implements FlowableSubscriber<T>, Disposable {
      final SingleObserver<? super R> downstream;
      final BiFunction<R, ? super T, R> reducer;
      Subscription upstream;
      R value;

      ReduceSeedObserver(SingleObserver<? super R> var1, BiFunction<R, ? super T, R> var2, R var3) {
         this.downstream = var1;
         this.value = (R)var3;
         this.reducer = var2;
      }

      @Override
      public void dispose() {
         this.upstream.cancel();
         this.upstream = SubscriptionHelper.CANCELLED;
      }

      @Override
      public boolean isDisposed() {
         boolean var1;
         if (this.upstream == SubscriptionHelper.CANCELLED) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public void onComplete() {
         Object var1 = this.value;
         if (var1 != null) {
            this.value = null;
            this.upstream = SubscriptionHelper.CANCELLED;
            this.downstream.onSuccess((R)var1);
         }
      }

      public void onError(Throwable var1) {
         if (this.value != null) {
            this.value = null;
            this.upstream = SubscriptionHelper.CANCELLED;
            this.downstream.onError(var1);
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void onNext(T var1) {
         Object var2 = this.value;
         if (var2 != null) {
            try {
               this.value = ObjectHelper.requireNonNull(this.reducer.apply((R)var2, (T)var1), "The reducer returned a null value");
            } catch (Throwable var4) {
               Exceptions.throwIfFatal(var4);
               this.upstream.cancel();
               this.onError(var4);
               return;
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
