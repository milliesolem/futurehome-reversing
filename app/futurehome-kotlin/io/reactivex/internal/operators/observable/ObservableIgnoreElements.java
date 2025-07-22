package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public final class ObservableIgnoreElements<T> extends AbstractObservableWithUpstream<T, T> {
   public ObservableIgnoreElements(ObservableSource<T> var1) {
      super(var1);
   }

   @Override
   public void subscribeActual(Observer<? super T> var1) {
      this.source.subscribe(new ObservableIgnoreElements.IgnoreObservable<>(var1));
   }

   static final class IgnoreObservable<T> implements Observer<T>, Disposable {
      final Observer<? super T> downstream;
      Disposable upstream;

      IgnoreObservable(Observer<? super T> var1) {
         this.downstream = var1;
      }

      @Override
      public void dispose() {
         this.upstream.dispose();
      }

      @Override
      public boolean isDisposed() {
         return this.upstream.isDisposed();
      }

      @Override
      public void onComplete() {
         this.downstream.onComplete();
      }

      @Override
      public void onError(Throwable var1) {
         this.downstream.onError(var1);
      }

      @Override
      public void onNext(T var1) {
      }

      @Override
      public void onSubscribe(Disposable var1) {
         this.upstream = var1;
         this.downstream.onSubscribe(this);
      }
   }
}
