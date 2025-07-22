package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.observers.SerializedObserver;

public final class ObservableSerialized<T> extends AbstractObservableWithUpstream<T, T> {
   public ObservableSerialized(Observable<T> var1) {
      super(var1);
   }

   @Override
   protected void subscribeActual(Observer<? super T> var1) {
      this.source.subscribe(new SerializedObserver<>(var1));
   }
}
