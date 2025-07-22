package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.ArrayCompositeDisposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.observers.SerializedObserver;

public final class ObservableSkipUntil<T, U> extends AbstractObservableWithUpstream<T, T> {
   final ObservableSource<U> other;

   public ObservableSkipUntil(ObservableSource<T> var1, ObservableSource<U> var2) {
      super(var1);
      this.other = var2;
   }

   @Override
   public void subscribeActual(Observer<? super T> var1) {
      SerializedObserver var4 = new SerializedObserver(var1);
      ArrayCompositeDisposable var3 = new ArrayCompositeDisposable(2);
      var4.onSubscribe(var3);
      ObservableSkipUntil.SkipUntilObserver var2 = new ObservableSkipUntil.SkipUntilObserver<>(var4, var3);
      this.other.subscribe(new ObservableSkipUntil.SkipUntil(this, var3, var2, var4));
      this.source.subscribe(var2);
   }

   final class SkipUntil implements Observer<U> {
      final ArrayCompositeDisposable frc;
      final SerializedObserver<T> serial;
      final ObservableSkipUntil.SkipUntilObserver<T> sus;
      final ObservableSkipUntil this$0;
      Disposable upstream;

      SkipUntil(ArrayCompositeDisposable var1, ObservableSkipUntil.SkipUntilObserver<T> var2, SerializedObserver<T> var3, SerializedObserver var4) {
         this.this$0 = var1;
         this.frc = var2;
         this.sus = var3;
         this.serial = var4;
      }

      @Override
      public void onComplete() {
         this.sus.notSkipping = true;
      }

      @Override
      public void onError(Throwable var1) {
         this.frc.dispose();
         this.serial.onError(var1);
      }

      @Override
      public void onNext(U var1) {
         this.upstream.dispose();
         this.sus.notSkipping = true;
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.frc.setResource(1, var1);
         }
      }
   }

   static final class SkipUntilObserver<T> implements Observer<T> {
      final Observer<? super T> downstream;
      final ArrayCompositeDisposable frc;
      volatile boolean notSkipping;
      boolean notSkippingLocal;
      Disposable upstream;

      SkipUntilObserver(Observer<? super T> var1, ArrayCompositeDisposable var2) {
         this.downstream = var1;
         this.frc = var2;
      }

      @Override
      public void onComplete() {
         this.frc.dispose();
         this.downstream.onComplete();
      }

      @Override
      public void onError(Throwable var1) {
         this.frc.dispose();
         this.downstream.onError(var1);
      }

      @Override
      public void onNext(T var1) {
         if (this.notSkippingLocal) {
            this.downstream.onNext((T)var1);
         } else if (this.notSkipping) {
            this.notSkippingLocal = true;
            this.downstream.onNext((T)var1);
         }
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.frc.setResource(0, var1);
         }
      }
   }
}
