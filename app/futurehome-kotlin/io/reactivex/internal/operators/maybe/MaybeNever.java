package io.reactivex.internal.operators.maybe;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.internal.disposables.EmptyDisposable;

public final class MaybeNever extends Maybe<Object> {
   public static final MaybeNever INSTANCE = new MaybeNever();

   @Override
   protected void subscribeActual(MaybeObserver<? super Object> var1) {
      var1.onSubscribe(EmptyDisposable.NEVER);
   }
}
