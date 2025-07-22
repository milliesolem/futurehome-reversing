package io.reactivex.internal.operators.completable;

import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.observers.BasicQueueDisposable;

public final class CompletableToObservable<T> extends Observable<T> {
   final CompletableSource source;

   public CompletableToObservable(CompletableSource var1) {
      this.source = var1;
   }

   @Override
   protected void subscribeActual(Observer<? super T> var1) {
      this.source.subscribe(new CompletableToObservable.ObserverCompletableObserver(var1));
   }

   static final class ObserverCompletableObserver extends BasicQueueDisposable<Void> implements CompletableObserver {
      final Observer<?> observer;
      Disposable upstream;

      ObserverCompletableObserver(Observer<?> var1) {
         this.observer = var1;
      }

      @Override
      public void clear() {
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
      public boolean isEmpty() {
         return true;
      }

      @Override
      public void onComplete() {
         this.observer.onComplete();
      }

      @Override
      public void onError(Throwable var1) {
         this.observer.onError(var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.observer.onSubscribe(this);
         }
      }

      public Void poll() throws Exception {
         return null;
      }

      @Override
      public int requestFusion(int var1) {
         return var1 & 2;
      }
   }
}
