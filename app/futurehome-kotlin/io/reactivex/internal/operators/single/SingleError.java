package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.Callable;

public final class SingleError<T> extends Single<T> {
   final Callable<? extends Throwable> errorSupplier;

   public SingleError(Callable<? extends Throwable> var1) {
      this.errorSupplier = var1;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   protected void subscribeActual(SingleObserver<? super T> var1) {
      Throwable var2;
      label17:
      try {
         var2 = ObjectHelper.requireNonNull(
            this.errorSupplier.call(), "Callable returned null throwable. Null values are generally not allowed in 2.x operators and sources."
         );
      } catch (Throwable var4) {
         var2 = var4;
         Exceptions.throwIfFatal(var4);
         break label17;
      }

      EmptyDisposable.error(var2, var1);
   }
}
