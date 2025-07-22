package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.DeferredScalarSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableCount<T> extends AbstractFlowableWithUpstream<T, Long> {
   public FlowableCount(Flowable<T> var1) {
      super(var1);
   }

   @Override
   protected void subscribeActual(Subscriber<? super Long> var1) {
      this.source.subscribe(new FlowableCount.CountSubscriber(var1));
   }

   static final class CountSubscriber extends DeferredScalarSubscription<Long> implements FlowableSubscriber<Object> {
      private static final long serialVersionUID = 4973004223787171406L;
      long count;
      Subscription upstream;

      CountSubscriber(Subscriber<? super Long> var1) {
         super(var1);
      }

      @Override
      public void cancel() {
         super.cancel();
         this.upstream.cancel();
      }

      public void onComplete() {
         this.complete(this.count);
      }

      public void onError(Throwable var1) {
         this.downstream.onError(var1);
      }

      public void onNext(Object var1) {
         this.count++;
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
