package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.plugins.RxJavaPlugins;

public final class CompletableFromRunnable extends Completable {
   final Runnable runnable;

   public CompletableFromRunnable(Runnable var1) {
      this.runnable = var1;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   protected void subscribeActual(CompletableObserver var1) {
      Disposable var3 = Disposables.empty();
      var1.onSubscribe(var3);

      try {
         this.runnable.run();
      } catch (Throwable var5) {
         Exceptions.throwIfFatal(var5);
         if (!var3.isDisposed()) {
            var1.onError(var5);
         } else {
            RxJavaPlugins.onError(var5);
         }

         return;
      }

      if (!var3.isDisposed()) {
         var1.onComplete();
      }
   }
}
