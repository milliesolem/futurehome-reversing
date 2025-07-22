package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.internal.fuseable.ScalarCallable;
import io.reactivex.internal.subscriptions.ScalarSubscription;
import org.reactivestreams.Subscriber;

public final class FlowableJust<T> extends Flowable<T> implements ScalarCallable<T> {
   private final T value;

   public FlowableJust(T var1) {
      this.value = (T)var1;
   }

   @Override
   public T call() {
      return this.value;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      var1.onSubscribe(new ScalarSubscription(var1, this.value));
   }
}
