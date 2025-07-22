package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;

public final class SingleFromCallable<T> extends Single<T> {
   final Callable<? extends T> callable;

   public SingleFromCallable(Callable<? extends T> var1) {
      this.callable = var1;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   protected void subscribeActual(SingleObserver<? super T> var1) {
      Disposable var2 = Disposables.empty();
      var1.onSubscribe(var2);
      if (!var2.isDisposed()) {
         Object var3;
         try {
            var3 = ObjectHelper.requireNonNull((T)this.callable.call(), "The callable returned a null value");
         } catch (Throwable var5) {
            Exceptions.throwIfFatal(var5);
            if (!var2.isDisposed()) {
               var1.onError(var5);
            } else {
               RxJavaPlugins.onError(var5);
            }

            return;
         }

         if (!var2.isDisposed()) {
            var1.onSuccess(var3);
         }
      }
   }
}
