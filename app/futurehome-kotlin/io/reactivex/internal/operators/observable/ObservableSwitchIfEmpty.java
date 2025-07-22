package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.SequentialDisposable;

public final class ObservableSwitchIfEmpty<T> extends AbstractObservableWithUpstream<T, T> {
   final ObservableSource<? extends T> other;

   public ObservableSwitchIfEmpty(ObservableSource<T> var1, ObservableSource<? extends T> var2) {
      super(var1);
      this.other = var2;
   }

   @Override
   public void subscribeActual(Observer<? super T> var1) {
      ObservableSwitchIfEmpty.SwitchIfEmptyObserver var2 = new ObservableSwitchIfEmpty.SwitchIfEmptyObserver<>(var1, this.other);
      var1.onSubscribe(var2.arbiter);
      this.source.subscribe(var2);
   }

   static final class SwitchIfEmptyObserver<T> implements Observer<T> {
      final SequentialDisposable arbiter;
      final Observer<? super T> downstream;
      boolean empty;
      final ObservableSource<? extends T> other;

      SwitchIfEmptyObserver(Observer<? super T> var1, ObservableSource<? extends T> var2) {
         this.downstream = var1;
         this.other = var2;
         this.empty = true;
         this.arbiter = new SequentialDisposable();
      }

      @Override
      public void onComplete() {
         if (this.empty) {
            this.empty = false;
            this.other.subscribe(this);
         } else {
            this.downstream.onComplete();
         }
      }

      @Override
      public void onError(Throwable var1) {
         this.downstream.onError(var1);
      }

      @Override
      public void onNext(T var1) {
         if (this.empty) {
            this.empty = false;
         }

         this.downstream.onNext((T)var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         this.arbiter.update(var1);
      }
   }
}
