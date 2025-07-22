package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.internal.disposables.EmptyDisposable;

public final class CompletableError extends Completable {
   final Throwable error;

   public CompletableError(Throwable var1) {
      this.error = var1;
   }

   @Override
   protected void subscribeActual(CompletableObserver var1) {
      EmptyDisposable.error(this.error, var1);
   }
}
