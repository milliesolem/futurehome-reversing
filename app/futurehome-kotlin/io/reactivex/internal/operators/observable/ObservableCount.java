package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;

public final class ObservableCount<T> extends AbstractObservableWithUpstream<T, Long> {
   public ObservableCount(ObservableSource<T> var1) {
      super(var1);
   }

   @Override
   public void subscribeActual(Observer<? super Long> var1) {
      this.source.subscribe(new ObservableCount.CountObserver(var1));
   }

   static final class CountObserver implements Observer<Object>, Disposable {
      long count;
      final Observer<? super Long> downstream;
      Disposable upstream;

      CountObserver(Observer<? super Long> var1) {
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
         this.downstream.onNext(this.count);
         this.downstream.onComplete();
      }

      @Override
      public void onError(Throwable var1) {
         this.downstream.onError(var1);
      }

      @Override
      public void onNext(Object var1) {
         this.count++;
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
