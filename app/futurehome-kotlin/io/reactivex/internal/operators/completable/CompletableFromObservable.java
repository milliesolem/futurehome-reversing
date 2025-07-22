package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public final class CompletableFromObservable<T> extends Completable {
   final ObservableSource<T> observable;

   public CompletableFromObservable(ObservableSource<T> var1) {
      this.observable = var1;
   }

   @Override
   protected void subscribeActual(CompletableObserver var1) {
      this.observable.subscribe(new CompletableFromObservable.CompletableFromObservableObserver<>(var1));
   }

   static final class CompletableFromObservableObserver<T> implements Observer<T> {
      final CompletableObserver co;

      CompletableFromObservableObserver(CompletableObserver var1) {
         this.co = var1;
      }

      @Override
      public void onComplete() {
         this.co.onComplete();
      }

      @Override
      public void onError(Throwable var1) {
         this.co.onError(var1);
      }

      @Override
      public void onNext(T var1) {
      }

      @Override
      public void onSubscribe(Disposable var1) {
         this.co.onSubscribe(var1);
      }
   }
}
