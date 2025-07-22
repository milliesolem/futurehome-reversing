package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeOperator;
import io.reactivex.MaybeSource;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;

public final class MaybeLift<T, R> extends AbstractMaybeWithUpstream<T, R> {
   final MaybeOperator<? extends R, ? super T> operator;

   public MaybeLift(MaybeSource<T> var1, MaybeOperator<? extends R, ? super T> var2) {
      super(var1);
      this.operator = var2;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   protected void subscribeActual(MaybeObserver<? super R> var1) {
      MaybeObserver var2;
      try {
         var2 = ObjectHelper.requireNonNull(this.operator.apply(var1), "The operator returned a null MaybeObserver");
      } catch (Throwable var4) {
         Exceptions.throwIfFatal(var4);
         EmptyDisposable.error(var4, var1);
         return;
      }

      this.source.subscribe(var2);
   }
}
