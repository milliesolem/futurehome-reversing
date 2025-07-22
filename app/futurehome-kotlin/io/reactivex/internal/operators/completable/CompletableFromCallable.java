package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;

public final class CompletableFromCallable extends Completable {
   final Callable<?> callable;

   public CompletableFromCallable(Callable<?> var1) {
      this.callable = var1;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   protected void subscribeActual(CompletableObserver var1) {
      Disposable var2 = Disposables.empty();
      var1.onSubscribe(var2);

      try {
         this.callable.call();
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
         var1.onComplete();
      }
   }
}
