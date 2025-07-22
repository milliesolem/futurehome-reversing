package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.internal.subscriptions.EmptySubscription;
import org.reactivestreams.Subscriber;

public final class FlowableNever extends Flowable<Object> {
   public static final Flowable<Object> INSTANCE = new FlowableNever();

   private FlowableNever() {
   }

   @Override
   public void subscribeActual(Subscriber<? super Object> var1) {
      var1.onSubscribe(EmptySubscription.INSTANCE);
   }
}
