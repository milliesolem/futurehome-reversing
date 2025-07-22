package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;

public final class CompletableFromUnsafeSource extends Completable {
   final CompletableSource source;

   public CompletableFromUnsafeSource(CompletableSource var1) {
      this.source = var1;
   }

   @Override
   protected void subscribeActual(CompletableObserver var1) {
      this.source.subscribe(var1);
   }
}
