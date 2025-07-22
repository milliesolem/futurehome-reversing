package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import io.reactivex.internal.util.ErrorMode;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

public final class FlowableConcatMapEagerPublisher<T, R> extends Flowable<R> {
   final ErrorMode errorMode;
   final Function<? super T, ? extends Publisher<? extends R>> mapper;
   final int maxConcurrency;
   final int prefetch;
   final Publisher<T> source;

   public FlowableConcatMapEagerPublisher(Publisher<T> var1, Function<? super T, ? extends Publisher<? extends R>> var2, int var3, int var4, ErrorMode var5) {
      this.source = var1;
      this.mapper = var2;
      this.maxConcurrency = var3;
      this.prefetch = var4;
      this.errorMode = var5;
   }

   @Override
   protected void subscribeActual(Subscriber<? super R> var1) {
      this.source
         .subscribe(new FlowableConcatMapEager.ConcatMapEagerDelayErrorSubscriber<>(var1, this.mapper, this.maxConcurrency, this.prefetch, this.errorMode));
   }
}
