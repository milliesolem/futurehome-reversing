package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;

public final class ObservableTakeLastOne<T> extends AbstractObservableWithUpstream<T, T> {
   public ObservableTakeLastOne(ObservableSource<T> var1) {
      super(var1);
   }

   @Override
   public void subscribeActual(Observer<? super T> var1) {
      this.source.subscribe(new ObservableTakeLastOne.TakeLastOneObserver<>(var1));
   }

   static final class TakeLastOneObserver<T> implements Observer<T>, Disposable {
      final Observer<? super T> downstream;
      Disposable upstream;
      T value;

      TakeLastOneObserver(Observer<? super T> var1) {
         this.downstream = var1;
      }

      @Override
      public void dispose() {
         this.value = null;
         this.upstream.dispose();
      }

      void emit() {
         Object var1 = this.value;
         if (var1 != null) {
            this.value = null;
            this.downstream.onNext((T)var1);
         }

         this.downstream.onComplete();
      }

      @Override
      public boolean isDisposed() {
         return this.upstream.isDisposed();
      }

      @Override
      public void onComplete() {
         this.emit();
      }

      @Override
      public void onError(Throwable var1) {
         this.value = null;
         this.downstream.onError(var1);
      }

      @Override
      public void onNext(T var1) {
         this.value = (T)var1;
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
