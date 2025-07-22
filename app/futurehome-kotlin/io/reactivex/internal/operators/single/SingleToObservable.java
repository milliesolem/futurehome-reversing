package io.reactivex.internal.operators.single;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.observers.DeferredScalarDisposable;

public final class SingleToObservable<T> extends Observable<T> {
   final SingleSource<? extends T> source;

   public SingleToObservable(SingleSource<? extends T> var1) {
      this.source = var1;
   }

   public static <T> SingleObserver<T> create(Observer<? super T> var0) {
      return new SingleToObservable.SingleToObservableObserver<>(var0);
   }

   @Override
   public void subscribeActual(Observer<? super T> var1) {
      this.source.subscribe(create(var1));
   }

   static final class SingleToObservableObserver<T> extends DeferredScalarDisposable<T> implements SingleObserver<T> {
      private static final long serialVersionUID = 3786543492451018833L;
      Disposable upstream;

      SingleToObservableObserver(Observer<? super T> var1) {
         super(var1);
      }

      @Override
      public void dispose() {
         super.dispose();
         this.upstream.dispose();
      }

      @Override
      public void onError(Throwable var1) {
         this.error(var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
         }
      }

      @Override
      public void onSuccess(T var1) {
         this.complete((T)var1);
      }
   }
}
