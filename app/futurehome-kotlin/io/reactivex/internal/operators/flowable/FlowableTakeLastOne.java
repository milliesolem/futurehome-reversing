package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.DeferredScalarSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableTakeLastOne<T> extends AbstractFlowableWithUpstream<T, T> {
   public FlowableTakeLastOne(Flowable<T> var1) {
      super(var1);
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      this.source.subscribe(new FlowableTakeLastOne.TakeLastOneSubscriber<>(var1));
   }

   static final class TakeLastOneSubscriber<T> extends DeferredScalarSubscription<T> implements FlowableSubscriber<T> {
      private static final long serialVersionUID = -5467847744262967226L;
      Subscription upstream;

      TakeLastOneSubscriber(Subscriber<? super T> var1) {
         super(var1);
      }

      @Override
      public void cancel() {
         super.cancel();
         this.upstream.cancel();
      }

      public void onComplete() {
         Object var1 = this.value;
         if (var1 != null) {
            this.complete((T)var1);
         } else {
            this.downstream.onComplete();
         }
      }

      public void onError(Throwable var1) {
         this.value = null;
         this.downstream.onError(var1);
      }

      public void onNext(T var1) {
         this.value = (T)var1;
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
