package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;

public final class CompletableFromSingle<T> extends Completable {
   final SingleSource<T> single;

   public CompletableFromSingle(SingleSource<T> var1) {
      this.single = var1;
   }

   @Override
   protected void subscribeActual(CompletableObserver var1) {
      this.single.subscribe(new CompletableFromSingle.CompletableFromSingleObserver<>(var1));
   }

   static final class CompletableFromSingleObserver<T> implements SingleObserver<T> {
      final CompletableObserver co;

      CompletableFromSingleObserver(CompletableObserver var1) {
         this.co = var1;
      }

      @Override
      public void onError(Throwable var1) {
         this.co.onError(var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         this.co.onSubscribe(var1);
      }

      @Override
      public void onSuccess(T var1) {
         this.co.onComplete();
      }
   }
}
