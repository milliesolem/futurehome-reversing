package io.reactivex.internal.operators.single;

import io.reactivex.Notification;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.internal.operators.mixed.MaterializeSingleObserver;

public final class SingleMaterialize<T> extends Single<Notification<T>> {
   final Single<T> source;

   public SingleMaterialize(Single<T> var1) {
      this.source = var1;
   }

   @Override
   protected void subscribeActual(SingleObserver<? super Notification<T>> var1) {
      this.source.subscribe(new MaterializeSingleObserver<>(var1));
   }
}
