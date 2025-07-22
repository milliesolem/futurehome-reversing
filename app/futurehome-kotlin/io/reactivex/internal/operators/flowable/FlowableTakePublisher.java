package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

public final class FlowableTakePublisher<T> extends Flowable<T> {
   final long limit;
   final Publisher<T> source;

   public FlowableTakePublisher(Publisher<T> var1, long var2) {
      this.source = var1;
      this.limit = var2;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      this.source.subscribe(new FlowableTake.TakeSubscriber(var1, this.limit));
   }
}
