package io.reactivex.internal.operators.observable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.fuseable.FuseToObservable;
import io.reactivex.plugins.RxJavaPlugins;

public final class ObservableIgnoreElementsCompletable<T> extends Completable implements FuseToObservable<T> {
   final ObservableSource<T> source;

   public ObservableIgnoreElementsCompletable(ObservableSource<T> var1) {
      this.source = var1;
   }

   @Override
   public Observable<T> fuseToObservable() {
      return RxJavaPlugins.onAssembly(new ObservableIgnoreElements<>(this.source));
   }

   @Override
   public void subscribeActual(CompletableObserver var1) {
      this.source.subscribe(new ObservableIgnoreElementsCompletable.IgnoreObservable<>(var1));
   }

   static final class IgnoreObservable<T> implements Observer<T>, Disposable {
      final CompletableObserver downstream;
      Disposable upstream;

      IgnoreObservable(CompletableObserver var1) {
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
