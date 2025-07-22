package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOperator;
import io.reactivex.SingleSource;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;

public final class SingleLift<T, R> extends Single<R> {
   final SingleOperator<? extends R, ? super T> onLift;
   final SingleSource<T> source;

   public SingleLift(SingleSource<T> var1, SingleOperator<? extends R, ? super T> var2) {
      this.source = var1;
      this.onLift = var2;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   protected void subscribeActual(SingleObserver<? super R> var1) {
      SingleObserver var2;
      try {
         var2 = ObjectHelper.requireNonNull(this.onLift.apply(var1), "The onLift returned a null SingleObserver");
      } catch (Throwable var4) {
         Exceptions.throwIfFatal(var4);
         EmptyDisposable.error(var4, var1);
         return;
      }

      this.source.subscribe(var2);
   }
}
