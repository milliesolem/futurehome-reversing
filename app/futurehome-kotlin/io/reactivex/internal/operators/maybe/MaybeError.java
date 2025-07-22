package io.reactivex.internal.operators.maybe;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposables;

public final class MaybeError<T> extends Maybe<T> {
   final Throwable error;

   public MaybeError(Throwable var1) {
      this.error = var1;
   }

   @Override
   protected void subscribeActual(MaybeObserver<? super T> var1) {
      var1.onSubscribe(Disposables.disposed());
      var1.onError(this.error);
   }
}
