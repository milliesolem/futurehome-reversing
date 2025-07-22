package io.reactivex.internal.operators.maybe;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;

public final class MaybeFromCallable<T> extends Maybe<T> implements Callable<T> {
   final Callable<? extends T> callable;

   public MaybeFromCallable(Callable<? extends T> var1) {
      this.callable = var1;
   }

   @Override
   public T call() throws Exception {
      return (T)this.callable.call();
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   protected void subscribeActual(MaybeObserver<? super T> var1) {
      Disposable var2 = Disposables.empty();
      var1.onSubscribe(var2);
      if (!var2.isDisposed()) {
         Object var3;
         try {
            var3 = this.callable.call();
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
            if (var3 == null) {
               var1.onComplete();
            } else {
               var1.onSuccess(var3);
            }
         }
      }
   }
}
