package io.reactivex.internal.operators.maybe;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.Callable;

public final class MaybeDefer<T> extends Maybe<T> {
   final Callable<? extends MaybeSource<? extends T>> maybeSupplier;

   public MaybeDefer(Callable<? extends MaybeSource<? extends T>> var1) {
      this.maybeSupplier = var1;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   protected void subscribeActual(MaybeObserver<? super T> var1) {
      MaybeSource var2;
      try {
         var2 = ObjectHelper.requireNonNull(this.maybeSupplier.call(), "The maybeSupplier returned a null MaybeSource");
      } catch (Throwable var4) {
         Exceptions.throwIfFatal(var4);
         EmptyDisposable.error(var4, var1);
         return;
      }

      var2.subscribe(var1);
   }
}
