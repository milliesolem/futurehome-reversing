package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.internal.fuseable.ScalarCallable;

public final class ObservableJust<T> extends Observable<T> implements ScalarCallable<T> {
   private final T value;

   public ObservableJust(T var1) {
      this.value = (T)var1;
   }

   @Override
   public T call() {
      return this.value;
   }

   @Override
   protected void subscribeActual(Observer<? super T> var1) {
      ObservableScalarXMap.ScalarDisposable var2 = new ObservableScalarXMap.ScalarDisposable(var1, this.value);
      var1.onSubscribe(var2);
      var2.run();
   }
}
