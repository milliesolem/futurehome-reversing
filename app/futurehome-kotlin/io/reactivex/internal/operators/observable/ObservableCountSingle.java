package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.FuseToObservable;
import io.reactivex.plugins.RxJavaPlugins;

public final class ObservableCountSingle<T> extends Single<Long> implements FuseToObservable<Long> {
   final ObservableSource<T> source;

   public ObservableCountSingle(ObservableSource<T> var1) {
      this.source = var1;
   }

   @Override
   public Observable<Long> fuseToObservable() {
      return RxJavaPlugins.onAssembly(new ObservableCount<>(this.source));
   }

   @Override
   public void subscribeActual(SingleObserver<? super Long> var1) {
      this.source.subscribe(new ObservableCountSingle.CountObserver(var1));
   }

   static final class CountObserver implements Observer<Object>, Disposable {
      long count;
      final SingleObserver<? super Long> downstream;
      Disposable upstream;

      CountObserver(SingleObserver<? super Long> var1) {
         this.downstream = var1;
      }

      @Override
      public void dispose() {
         this.upstream.dispose();
         this.upstream = DisposableHelper.DISPOSED;
      }

      @Override
      public boolean isDisposed() {
         return this.upstream.isDisposed();
      }

      @Override
      public void onComplete() {
         this.upstream = DisposableHelper.DISPOSED;
         this.downstream.onSuccess(this.count);
      }

      @Override
      public void onError(Throwable var1) {
         this.upstream = DisposableHelper.DISPOSED;
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
