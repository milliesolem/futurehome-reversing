package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.Callable;

public final class CompletableErrorSupplier extends Completable {
   final Callable<? extends Throwable> errorSupplier;

   public CompletableErrorSupplier(Callable<? extends Throwable> var1) {
      this.errorSupplier = var1;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   protected void subscribeActual(CompletableObserver var1) {
      Throwable var2;
      label17:
      try {
         var2 = ObjectHelper.requireNonNull(this.errorSupplier.call(), "The error returned is null");
      } catch (Throwable var4) {
         var2 = var4;
         Exceptions.throwIfFatal(var4);
         break label17;
      }

      EmptyDisposable.error(var2, var1);
   }
}
