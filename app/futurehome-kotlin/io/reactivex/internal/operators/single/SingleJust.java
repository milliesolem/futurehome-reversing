package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposables;

public final class SingleJust<T> extends Single<T> {
   final T value;

   public SingleJust(T var1) {
      this.value = (T)var1;
   }

   @Override
   protected void subscribeActual(SingleObserver<? super T> var1) {
      var1.onSubscribe(Disposables.disposed());
      var1.onSuccess(this.value);
   }
}
