package io.reactivex.internal.subscribers;

import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.DeferredScalarSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public abstract class DeferredScalarSubscriber<T, R> extends DeferredScalarSubscription<R> implements FlowableSubscriber<T> {
   private static final long serialVersionUID = 2984505488220891551L;
   protected boolean hasValue;
   protected Subscription upstream;

   public DeferredScalarSubscriber(Subscriber<? super R> var1) {
      super(var1);
   }

   @Override
   public void cancel() {
      super.cancel();
      this.upstream.cancel();
   }

   public void onComplete() {
      if (this.hasValue) {
         this.complete(this.value);
      } else {
         this.downstream.onComplete();
      }
   }

   public void onError(Throwable var1) {
      this.value = null;
      this.downstream.onError(var1);
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
