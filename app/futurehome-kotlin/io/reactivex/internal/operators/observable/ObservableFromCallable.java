package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.observers.DeferredScalarDisposable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;

public final class ObservableFromCallable<T> extends Observable<T> implements Callable<T> {
   final Callable<? extends T> callable;

   public ObservableFromCallable(Callable<? extends T> var1) {
      this.callable = var1;
   }

   @Override
   public T call() throws Exception {
      return ObjectHelper.requireNonNull((T)this.callable.call(), "The callable returned a null value");
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void subscribeActual(Observer<? super T> var1) {
      DeferredScalarDisposable var2 = new DeferredScalarDisposable(var1);
      var1.onSubscribe(var2);
      if (!var2.isDisposed()) {
         Object var3;
         try {
            var3 = ObjectHelper.requireNonNull((T)this.callable.call(), "Callable returned null");
         } catch (Throwable var5) {
            Exceptions.throwIfFatal(var5);
            if (!var2.isDisposed()) {
               var1.onError(var5);
            } else {
               RxJavaPlugins.onError(var5);
            }

            return;
         }

         var2.complete(var3);
      }
   }
}
