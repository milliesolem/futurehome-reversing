package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.plugins.RxJavaPlugins;

public final class ObservableDelaySubscriptionOther<T, U> extends Observable<T> {
   final ObservableSource<? extends T> main;
   final ObservableSource<U> other;

   public ObservableDelaySubscriptionOther(ObservableSource<? extends T> var1, ObservableSource<U> var2) {
      this.main = var1;
      this.other = var2;
   }

   @Override
   public void subscribeActual(Observer<? super T> var1) {
      SequentialDisposable var2 = new SequentialDisposable();
      var1.onSubscribe(var2);
      ObservableDelaySubscriptionOther.DelayObserver var3 = new ObservableDelaySubscriptionOther.DelayObserver(this, var2, var1);
      this.other.subscribe(var3);
   }

   final class DelayObserver implements Observer<U> {
      final Observer<? super T> child;
      boolean done;
      final SequentialDisposable serial;
      final ObservableDelaySubscriptionOther this$0;

      DelayObserver(SequentialDisposable var1, Observer<? super T> var2, Observer var3) {
         this.this$0 = var1;
         this.serial = var2;
         this.child = var3;
      }

      @Override
      public void onComplete() {
         if (!this.done) {
            this.done = true;
            this.this$0.main.subscribe(new ObservableDelaySubscriptionOther.DelayObserver.OnComplete(this));
         }
      }

      @Override
      public void onError(Throwable var1) {
         if (this.done) {
            RxJavaPlugins.onError(var1);
         } else {
            this.done = true;
            this.child.onError(var1);
         }
      }

      @Override
      public void onNext(U var1) {
         this.onComplete();
      }

      @Override
      public void onSubscribe(Disposable var1) {
         this.serial.update(var1);
      }

      final class OnComplete implements Observer<T> {
         final ObservableDelaySubscriptionOther.DelayObserver this$1;

         OnComplete(ObservableDelaySubscriptionOther.DelayObserver var1) {
            this.this$1 = var1;
         }

         @Override
         public void onComplete() {
            this.this$1.child.onComplete();
         }

         @Override
         public void onError(Throwable var1) {
            this.this$1.child.onError(var1);
         }

         @Override
         public void onNext(T var1) {
            this.this$1.child.onNext((T)var1);
         }

         @Override
         public void onSubscribe(Disposable var1) {
            this.this$1.serial.update(var1);
         }
      }
   }
}
