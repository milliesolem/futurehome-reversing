package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.internal.disposables.EmptyDisposable;

public final class CompletableNever extends Completable {
   public static final Completable INSTANCE = new CompletableNever();

   private CompletableNever() {
   }

   @Override
   protected void subscribeActual(CompletableObserver var1) {
      var1.onSubscribe(EmptyDisposable.NEVER);
   }
}
