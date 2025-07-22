package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.internal.disposables.EmptyDisposable;

public final class CompletableEmpty extends Completable {
   public static final Completable INSTANCE = new CompletableEmpty();

   private CompletableEmpty() {
   }

   @Override
   public void subscribeActual(CompletableObserver var1) {
      EmptyDisposable.complete(var1);
   }
}
