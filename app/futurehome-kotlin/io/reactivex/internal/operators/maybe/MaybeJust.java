package io.reactivex.internal.operators.maybe;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposables;
import io.reactivex.internal.fuseable.ScalarCallable;

public final class MaybeJust<T> extends Maybe<T> implements ScalarCallable<T> {
   final T value;

   public MaybeJust(T var1) {
      this.value = (T)var1;
   }

   @Override
   public T call() {
      return this.value;
   }

   @Override
   protected void subscribeActual(MaybeObserver<? super T> var1) {
      var1.onSubscribe(Disposables.disposed());
      var1.onSuccess(this.value);
   }
}
