package io.reactivex.subscribers;

import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.EndConsumerHelper;
import org.reactivestreams.Subscription;

public abstract class DefaultSubscriber<T> implements FlowableSubscriber<T> {
   Subscription upstream;

   protected final void cancel() {
      Subscription var1 = this.upstream;
      this.upstream = SubscriptionHelper.CANCELLED;
      var1.cancel();
   }

   protected void onStart() {
      this.request(Long.MAX_VALUE);
   }

   @Override
   public final void onSubscribe(Subscription var1) {
      if (EndConsumerHelper.validate(this.upstream, var1, this.getClass())) {
         this.upstream = var1;
         this.onStart();
      }
   }

   protected final void request(long var1) {
      Subscription var3 = this.upstream;
      if (var3 != null) {
         var3.request(var1);
      }
   }
}
