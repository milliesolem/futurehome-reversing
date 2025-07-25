package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.fuseable.ScalarCallable;

public final class ObservableEmpty extends Observable<Object> implements ScalarCallable<Object> {
   public static final Observable<Object> INSTANCE = new ObservableEmpty();

   private ObservableEmpty() {
   }

   @Override
   public Object call() {
      return null;
   }

   @Override
   protected void subscribeActual(Observer<? super Object> var1) {
      EmptyDisposable.complete(var1);
   }
}
