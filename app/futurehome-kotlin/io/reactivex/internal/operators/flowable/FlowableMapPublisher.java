package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

public final class FlowableMapPublisher<T, U> extends Flowable<U> {
   final Function<? super T, ? extends U> mapper;
   final Publisher<T> source;

   public FlowableMapPublisher(Publisher<T> var1, Function<? super T, ? extends U> var2) {
      this.source = var1;
      this.mapper = var2;
   }

   @Override
   protected void subscribeActual(Subscriber<? super U> var1) {
      this.source.subscribe(new FlowableMap.MapSubscriber<>(var1, this.mapper));
   }
}
