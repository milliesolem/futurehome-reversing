package io.reactivex.internal.operators.maybe;

import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.internal.fuseable.HasUpstreamMaybeSource;

abstract class AbstractMaybeWithUpstream<T, R> extends Maybe<R> implements HasUpstreamMaybeSource<T> {
   protected final MaybeSource<T> source;

   AbstractMaybeWithUpstream(MaybeSource<T> var1) {
      this.source = var1;
   }

   @Override
   public final MaybeSource<T> source() {
      return this.source;
   }
}
