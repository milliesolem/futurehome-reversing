package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;

public final class ObservableFromUnsafeSource<T> extends Observable<T> {
   final ObservableSource<T> source;

   public ObservableFromUnsafeSource(ObservableSource<T> var1) {
      this.source = var1;
   }

   @Override
   protected void subscribeActual(Observer<? super T> var1) {
      this.source.subscribe(var1);
   }
}
