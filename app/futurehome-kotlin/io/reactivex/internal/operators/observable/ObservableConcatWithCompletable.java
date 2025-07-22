package io.reactivex.internal.operators.observable;

import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableConcatWithCompletable<T> extends AbstractObservableWithUpstream<T, T> {
   final CompletableSource other;

   public ObservableConcatWithCompletable(Observable<T> var1, CompletableSource var2) {
      super(var1);
      this.other = var2;
   }

   @Override
   protected void subscribeActual(Observer<? super T> var1) {
      this.source.subscribe(new ObservableConcatWithCompletable.ConcatWithObserver<>(var1, this.other));
   }

   static final class ConcatWithObserver<T> extends AtomicReference<Disposable> implements Observer<T>, CompletableObserver, Disposable {
      private static final long serialVersionUID = -1953724749712440952L;
      final Observer<? super T> downstream;
      boolean inCompletable;
      CompletableSource other;

      ConcatWithObserver(Observer<? super T> var1, CompletableSource var2) {
         this.downstream = var1;
         this.other = var2;
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
         if (this.inCompletable) {
            this.downstream.onComplete();
         } else {
            this.inCompletable = true;
            DisposableHelper.replace(this, null);
            CompletableSource var1 = this.other;
            this.other = null;
            var1.subscribe(this);
         }
      }

      @Override
      public void onError(Throwable var1) {
         this.downstream.onError(var1);
      }

      @Override
      public void onNext(T var1) {
         this.downstream.onNext((T)var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.setOnce(this, var1) && !this.inCompletable) {
            this.downstream.onSubscribe(this);
         }
      }
   }
}
