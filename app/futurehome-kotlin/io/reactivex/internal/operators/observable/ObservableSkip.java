package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;

public final class ObservableSkip<T> extends AbstractObservableWithUpstream<T, T> {
   final long n;

   public ObservableSkip(ObservableSource<T> var1, long var2) {
      super(var1);
      this.n = var2;
   }

   @Override
   public void subscribeActual(Observer<? super T> var1) {
      this.source.subscribe(new ObservableSkip.SkipObserver<>(var1, this.n));
   }

   static final class SkipObserver<T> implements Observer<T>, Disposable {
      final Observer<? super T> downstream;
      long remaining;
      Disposable upstream;

      SkipObserver(Observer<? super T> var1, long var2) {
         this.downstream = var1;
         this.remaining = var2;
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
         long var2 = this.remaining;
         if (var2 != 0L) {
            this.remaining = var2 - 1L;
         } else {
            this.downstream.onNext((T)var1);
         }
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
         }
      }
   }
}
