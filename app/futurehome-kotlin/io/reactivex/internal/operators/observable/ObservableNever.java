package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.internal.disposables.EmptyDisposable;

public final class ObservableNever extends Observable<Object> {
   public static final Observable<Object> INSTANCE = new ObservableNever();

   private ObservableNever() {
   }

   @Override
   protected void subscribeActual(Observer<? super Object> var1) {
      var1.onSubscribe(EmptyDisposable.NEVER);
   }
}
