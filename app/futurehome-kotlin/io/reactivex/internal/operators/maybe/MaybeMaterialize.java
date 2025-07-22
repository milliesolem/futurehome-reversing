package io.reactivex.internal.operators.maybe;

import io.reactivex.Maybe;
import io.reactivex.Notification;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.internal.operators.mixed.MaterializeSingleObserver;

public final class MaybeMaterialize<T> extends Single<Notification<T>> {
   final Maybe<T> source;

   public MaybeMaterialize(Maybe<T> var1) {
      this.source = var1;
   }

   @Override
   protected void subscribeActual(SingleObserver<? super Notification<T>> var1) {
      this.source.subscribe(new MaterializeSingleObserver<>(var1));
   }
}
