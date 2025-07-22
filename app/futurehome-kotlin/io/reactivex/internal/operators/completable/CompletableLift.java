package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableOperator;
import io.reactivex.CompletableSource;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.plugins.RxJavaPlugins;

public final class CompletableLift extends Completable {
   final CompletableOperator onLift;
   final CompletableSource source;

   public CompletableLift(CompletableSource var1, CompletableOperator var2) {
      this.source = var1;
      this.onLift = var2;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   protected void subscribeActual(CompletableObserver var1) {
      try {
         try {
            var1 = this.onLift.apply(var1);
            this.source.subscribe(var1);
            return;
         } catch (NullPointerException var4) {
            var6 = var4;
         }
      } catch (Throwable var5) {
         Exceptions.throwIfFatal(var5);
         RxJavaPlugins.onError(var5);
         return;
      }

      throw var6;
   }
}
