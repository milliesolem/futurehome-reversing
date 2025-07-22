package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.DeferredScalarSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableReduce<T> extends AbstractFlowableWithUpstream<T, T> {
   final BiFunction<T, T, T> reducer;

   public FlowableReduce(Flowable<T> var1, BiFunction<T, T, T> var2) {
      super(var1);
      this.reducer = var2;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      this.source.subscribe(new FlowableReduce.ReduceSubscriber<>(var1, this.reducer));
   }

   static final class ReduceSubscriber<T> extends DeferredScalarSubscription<T> implements FlowableSubscriber<T> {
      private static final long serialVersionUID = -4663883003264602070L;
      final BiFunction<T, T, T> reducer;
      Subscription upstream;

      ReduceSubscriber(Subscriber<? super T> var1, BiFunction<T, T, T> var2) {
         super(var1);
         this.reducer = var2;
      }

      @Override
      public void cancel() {
         super.cancel();
         this.upstream.cancel();
         this.upstream = SubscriptionHelper.CANCELLED;
      }

      public void onComplete() {
         if (this.upstream != SubscriptionHelper.CANCELLED) {
            this.upstream = SubscriptionHelper.CANCELLED;
            Object var1 = this.value;
            if (var1 != null) {
               this.complete((T)var1);
            } else {
               this.downstream.onComplete();
            }
         }
      }

      public void onError(Throwable var1) {
         if (this.upstream == SubscriptionHelper.CANCELLED) {
            RxJavaPlugins.onError(var1);
         } else {
            this.upstream = SubscriptionHelper.CANCELLED;
            this.downstream.onError(var1);
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void onNext(T var1) {
         if (this.upstream != SubscriptionHelper.CANCELLED) {
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
