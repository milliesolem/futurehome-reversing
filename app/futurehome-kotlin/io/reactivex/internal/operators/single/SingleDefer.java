package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.Callable;

public final class SingleDefer<T> extends Single<T> {
   final Callable<? extends SingleSource<? extends T>> singleSupplier;

   public SingleDefer(Callable<? extends SingleSource<? extends T>> var1) {
      this.singleSupplier = var1;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   protected void subscribeActual(SingleObserver<? super T> var1) {
      SingleSource var2;
      try {
         var2 = ObjectHelper.requireNonNull(this.singleSupplier.call(), "The singleSupplier returned a null SingleSource");
      } catch (Throwable var4) {
         Exceptions.throwIfFatal(var4);
         EmptyDisposable.error(var4, var1);
         return;
      }

      var2.subscribe(var1);
   }
}
