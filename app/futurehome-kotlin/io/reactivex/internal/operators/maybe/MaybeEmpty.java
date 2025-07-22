package io.reactivex.internal.operators.maybe;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.fuseable.ScalarCallable;

public final class MaybeEmpty extends Maybe<Object> implements ScalarCallable<Object> {
   public static final MaybeEmpty INSTANCE = new MaybeEmpty();

   @Override
   public Object call() {
      return null;
   }

   @Override
   protected void subscribeActual(MaybeObserver<? super Object> var1) {
      EmptyDisposable.complete(var1);
   }
}
