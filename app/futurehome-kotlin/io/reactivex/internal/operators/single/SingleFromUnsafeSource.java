package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;

public final class SingleFromUnsafeSource<T> extends Single<T> {
   final SingleSource<T> source;

   public SingleFromUnsafeSource(SingleSource<T> var1) {
      this.source = var1;
   }

   @Override
   protected void subscribeActual(SingleObserver<? super T> var1) {
      this.source.subscribe(var1);
   }
}
