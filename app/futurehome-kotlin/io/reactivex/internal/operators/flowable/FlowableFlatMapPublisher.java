package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

public final class FlowableFlatMapPublisher<T, U> extends Flowable<U> {
   final int bufferSize;
   final boolean delayErrors;
   final Function<? super T, ? extends Publisher<? extends U>> mapper;
   final int maxConcurrency;
   final Publisher<T> source;

   public FlowableFlatMapPublisher(Publisher<T> var1, Function<? super T, ? extends Publisher<? extends U>> var2, boolean var3, int var4, int var5) {
      this.source = var1;
      this.mapper = var2;
      this.delayErrors = var3;
      this.maxConcurrency = var4;
      this.bufferSize = var5;
   }

   @Override
   protected void subscribeActual(Subscriber<? super U> var1) {
      if (!FlowableScalarXMap.tryScalarXMapSubscribe(this.source, var1, this.mapper)) {
         this.source.subscribe(FlowableFlatMap.subscribe(var1, this.mapper, this.delayErrors, this.maxConcurrency, this.bufferSize));
      }
   }
}
