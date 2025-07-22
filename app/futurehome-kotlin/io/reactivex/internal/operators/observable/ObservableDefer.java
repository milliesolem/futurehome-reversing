package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.Callable;

public final class ObservableDefer<T> extends Observable<T> {
   final Callable<? extends ObservableSource<? extends T>> supplier;

   public ObservableDefer(Callable<? extends ObservableSource<? extends T>> var1) {
      this.supplier = var1;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void subscribeActual(Observer<? super T> var1) {
      ObservableSource var2;
      try {
         var2 = ObjectHelper.requireNonNull(this.supplier.call(), "null ObservableSource supplied");
      } catch (Throwable var4) {
         Exceptions.throwIfFatal(var4);
         EmptyDisposable.error(var4, var1);
         return;
      }

      var2.subscribe(var1);
   }
}
