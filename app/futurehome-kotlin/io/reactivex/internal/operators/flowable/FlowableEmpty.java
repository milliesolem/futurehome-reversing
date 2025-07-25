package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.internal.fuseable.ScalarCallable;
import io.reactivex.internal.subscriptions.EmptySubscription;
import org.reactivestreams.Subscriber;

public final class FlowableEmpty extends Flowable<Object> implements ScalarCallable<Object> {
   public static final Flowable<Object> INSTANCE = new FlowableEmpty();

   private FlowableEmpty() {
   }

   @Override
   public Object call() {
      return null;
   }

   @Override
   public void subscribeActual(Subscriber<? super Object> var1) {
      EmptySubscription.complete(var1);
   }
}
