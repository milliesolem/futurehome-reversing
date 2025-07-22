package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.subscribers.SerializedSubscriber;
import org.reactivestreams.Subscriber;

public final class FlowableSerialized<T> extends AbstractFlowableWithUpstream<T, T> {
   public FlowableSerialized(Flowable<T> var1) {
      super(var1);
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      this.source.subscribe(new SerializedSubscriber<>(var1));
   }
}
