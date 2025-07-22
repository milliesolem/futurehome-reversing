package io.reactivex.internal.operators.mixed;

import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class CompletableAndThenObservable<R> extends Observable<R> {
   final ObservableSource<? extends R> other;
   final CompletableSource source;

   public CompletableAndThenObservable(CompletableSource var1, ObservableSource<? extends R> var2) {
      this.source = var1;
      this.other = var2;
   }

   @Override
   protected void subscribeActual(Observer<? super R> var1) {
      CompletableAndThenObservable.AndThenObservableObserver var2 = new CompletableAndThenObservable.AndThenObservableObserver<>(var1, this.other);
      var1.onSubscribe(var2);
      this.source.subscribe(var2);
   }

   static final class AndThenObservableObserver<R> extends AtomicReference<Disposable> implements Observer<R>, CompletableObserver, Disposable {
      private static final long serialVersionUID = -8948264376121066672L;
      final Observer<? super R> downstream;
      ObservableSource<? extends R> other;

      AndThenObservableObserver(Observer<? super R> var1, ObservableSource<? extends R> var2) {
         this.other = var2;
         this.downstream = var1;
      }

      @Override
      public void dispose() {
         DisposableHelper.dispose(this);
      }

      @Override
      public boolean isDisposed() {
         return DisposableHelper.isDisposed(this.get());
      }

      @Override
      public void onComplete() {
         ObservableSource var1 = this.other;
         if (var1 == null) {
            this.downstream.onComplete();
         } else {
            this.other = null;
            var1.subscribe(this);
         }
      }

      @Override
      public void onError(Throwable var1) {
         this.downstream.onError(var1);
      }

      @Override
      public void onNext(R var1) {
         this.downstream.onNext((R)var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         DisposableHelper.replace(this, var1);
      }
   }
}
