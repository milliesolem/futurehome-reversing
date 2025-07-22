package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

public final class FlowableFromPublisher<T> extends Flowable<T> {
   final Publisher<? extends T> publisher;

   public FlowableFromPublisher(Publisher<? extends T> var1) {
      this.publisher = var1;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      this.publisher.subscribe(var1);
   }
}
