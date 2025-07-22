package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.HalfSerializer;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableTakeUntil<T, U> extends AbstractObservableWithUpstream<T, T> {
   final ObservableSource<? extends U> other;

   public ObservableTakeUntil(ObservableSource<T> var1, ObservableSource<? extends U> var2) {
      super(var1);
      this.other = var2;
   }

   @Override
   public void subscribeActual(Observer<? super T> var1) {
      ObservableTakeUntil.TakeUntilMainObserver var2 = new ObservableTakeUntil.TakeUntilMainObserver(var1);
      var1.onSubscribe(var2);
      this.other.subscribe(var2.otherObserver);
      this.source.subscribe(var2);
   }

   static final class TakeUntilMainObserver<T, U> extends AtomicInteger implements Observer<T>, Disposable {
      private static final long serialVersionUID = 1418547743690811973L;
      final Observer<? super T> downstream;
      final AtomicThrowable error;
      final ObservableTakeUntil.TakeUntilMainObserver<T, U>.OtherObserver otherObserver;
      final AtomicReference<Disposable> upstream;

      TakeUntilMainObserver(Observer<? super T> var1) {
         this.downstream = var1;
         this.upstream = new AtomicReference<>();
         this.otherObserver = new ObservableTakeUntil.TakeUntilMainObserver.OtherObserver(this);
         this.error = new AtomicThrowable();
      }

      @Override
      public void dispose() {
         DisposableHelper.dispose(this.upstream);
         DisposableHelper.dispose(this.otherObserver);
      }

      @Override
      public boolean isDisposed() {
         return DisposableHelper.isDisposed(this.upstream.get());
      }

      @Override
      public void onComplete() {
         DisposableHelper.dispose(this.otherObserver);
         HalfSerializer.onComplete(this.downstream, this, this.error);
      }

      @Override
      public void onError(Throwable var1) {
         DisposableHelper.dispose(this.otherObserver);
         HalfSerializer.onError(this.downstream, var1, this, this.error);
      }

      @Override
      public void onNext(T var1) {
         HalfSerializer.onNext(this.downstream, (T)var1, this, this.error);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         DisposableHelper.setOnce(this.upstream, var1);
      }

      void otherComplete() {
         DisposableHelper.dispose(this.upstream);
         HalfSerializer.onComplete(this.downstream, this, this.error);
      }

      void otherError(Throwable var1) {
         DisposableHelper.dispose(this.upstream);
         HalfSerializer.onError(this.downstream, var1, this, this.error);
      }

      final class OtherObserver extends AtomicReference<Disposable> implements Observer<U> {
         private static final long serialVersionUID = -8693423678067375039L;
         final ObservableTakeUntil.TakeUntilMainObserver this$0;

         OtherObserver(ObservableTakeUntil.TakeUntilMainObserver var1) {
            this.this$0 = var1;
         }

         @Override
         public void onComplete() {
            this.this$0.otherComplete();
         }

         @Override
         public void onError(Throwable var1) {
            this.this$0.otherError(var1);
         }

         @Override
         public void onNext(U var1) {
            DisposableHelper.dispose(this);
            this.this$0.otherComplete();
         }

         @Override
         public void onSubscribe(Disposable var1) {
            DisposableHelper.setOnce(this, var1);
         }
      }
   }
}
