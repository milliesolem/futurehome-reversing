package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.HasUpstreamPublisher;
import org.reactivestreams.Publisher;

abstract class AbstractFlowableWithUpstream<T, R> extends Flowable<R> implements HasUpstreamPublisher<T> {
   protected final Flowable<T> source;

   AbstractFlowableWithUpstream(Flowable<T> var1) {
      this.source = ObjectHelper.requireNonNull(var1, "source is null");
   }

   @Override
   public final Publisher<T> source() {
      return this.source;
   }
}
