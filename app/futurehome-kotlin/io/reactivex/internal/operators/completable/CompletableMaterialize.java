package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.Notification;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.internal.operators.mixed.MaterializeSingleObserver;

public final class CompletableMaterialize<T> extends Single<Notification<T>> {
   final Completable source;

   public CompletableMaterialize(Completable var1) {
      this.source = var1;
   }

   @Override
   protected void subscribeActual(SingleObserver<? super Notification<T>> var1) {
      this.source.subscribe(new MaterializeSingleObserver(var1));
   }
}
