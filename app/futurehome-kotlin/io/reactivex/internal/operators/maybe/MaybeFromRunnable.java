package io.reactivex.internal.operators.maybe;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;

public final class MaybeFromRunnable<T> extends Maybe<T> implements Callable<T> {
   final Runnable runnable;

   public MaybeFromRunnable(Runnable var1) {
      this.runnable = var1;
   }

   @Override
   public T call() throws Exception {
      this.runnable.run();
      return null;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   protected void subscribeActual(MaybeObserver<? super T> var1) {
      Disposable var3 = Disposables.empty();
      var1.onSubscribe(var3);
      if (!var3.isDisposed()) {
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
}
