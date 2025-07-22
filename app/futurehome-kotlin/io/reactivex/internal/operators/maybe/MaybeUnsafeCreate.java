package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;

public final class MaybeUnsafeCreate<T> extends AbstractMaybeWithUpstream<T, T> {
   public MaybeUnsafeCreate(MaybeSource<T> var1) {
      super(var1);
   }

   @Override
   protected void subscribeActual(MaybeObserver<? super T> var1) {
      this.source.subscribe(var1);
   }
}
