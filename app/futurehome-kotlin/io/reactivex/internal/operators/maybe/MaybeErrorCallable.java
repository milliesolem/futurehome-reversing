package io.reactivex.internal.operators.maybe;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposables;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.Callable;

public final class MaybeErrorCallable<T> extends Maybe<T> {
   final Callable<? extends Throwable> errorSupplier;

   public MaybeErrorCallable(Callable<? extends Throwable> var1) {
      this.errorSupplier = var1;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   protected void subscribeActual(MaybeObserver<? super T> var1) {
      var1.onSubscribe(Disposables.disposed());

      Throwable var2;
      label20:
      try {
         var2 = ObjectHelper.requireNonNull(
            this.errorSupplier.call(), "Callable returned null throwable. Null values are generally not allowed in 2.x operators and sources."
         );
      } catch (Throwable var4) {
         var2 = var4;
         Exceptions.throwIfFatal(var4);
         break label20;
      }

      var1.onError(var2);
   }
}
