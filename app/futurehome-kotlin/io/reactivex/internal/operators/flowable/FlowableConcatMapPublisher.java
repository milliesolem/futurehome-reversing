package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import io.reactivex.internal.util.ErrorMode;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

public final class FlowableConcatMapPublisher<T, R> extends Flowable<R> {
   final ErrorMode errorMode;
   final Function<? super T, ? extends Publisher<? extends R>> mapper;
   final int prefetch;
   final Publisher<T> source;

   public FlowableConcatMapPublisher(Publisher<T> var1, Function<? super T, ? extends Publisher<? extends R>> var2, int var3, ErrorMode var4) {
      this.source = var1;
      this.mapper = var2;
      this.prefetch = var3;
      this.errorMode = var4;
   }

   @Override
   protected void subscribeActual(Subscriber<? super R> var1) {
      if (!FlowableScalarXMap.tryScalarXMapSubscribe(this.source, var1, this.mapper)) {
         this.source.subscribe(FlowableConcatMap.subscribe(var1, this.mapper, this.prefetch, this.errorMode));
      }
   }
}
