package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.internal.fuseable.HasUpstreamObservableSource;

abstract class AbstractObservableWithUpstream<T, U> extends Observable<U> implements HasUpstreamObservableSource<T> {
   protected final ObservableSource<T> source;

   AbstractObservableWithUpstream(ObservableSource<T> var1) {
      this.source = var1;
   }

   @Override
   public final ObservableSource<T> source() {
      return this.source;
   }
}
