package io.reactivex.internal.operators.observable;

import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.HalfSerializer;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableMergeWithCompletable<T> extends AbstractObservableWithUpstream<T, T> {
   final CompletableSource other;

   public ObservableMergeWithCompletable(Observable<T> var1, CompletableSource var2) {
      super(var1);
      this.other = var2;
   }

   @Override
   protected void subscribeActual(Observer<? super T> var1) {
      ObservableMergeWithCompletable.MergeWithObserver var2 = new ObservableMergeWithCompletable.MergeWithObserver(var1);
      var1.onSubscribe(var2);
      this.source.subscribe(var2);
      this.other.subscribe(var2.otherObserver);
   }

   static final class MergeWithObserver<T> extends AtomicInteger implements Observer<T>, Disposable {
      private static final long serialVersionUID = -4592979584110982903L;
      final Observer<? super T> downstream;
      final AtomicThrowable error;
      final AtomicReference<Disposable> mainDisposable;
      volatile boolean mainDone;
      volatile boolean otherDone;
      final ObservableMergeWithCompletable.MergeWithObserver.OtherObserver otherObserver;

      MergeWithObserver(Observer<? super T> var1) {
         this.downstream = var1;
         this.mainDisposable = new AtomicReference<>();
         this.otherObserver = new ObservableMergeWithCompletable.MergeWithObserver.OtherObserver(this);
         this.error = new AtomicThrowable();
      }

      @Override
      public void dispose() {
         DisposableHelper.dispose(this.mainDisposable);
         DisposableHelper.dispose(this.otherObserver);
      }

      @Override
      public boolean isDisposed() {
         return DisposableHelper.isDisposed(this.mainDisposable.get());
      }

      @Override
      public void onComplete() {
         this.mainDone = true;
         if (this.otherDone) {
            HalfSerializer.onComplete(this.downstream, this, this.error);
         }
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
         DisposableHelper.setOnce(this.mainDisposable, var1);
      }

      void otherComplete() {
         this.otherDone = true;
         if (this.mainDone) {
            HalfSerializer.onComplete(this.downstream, this, this.error);
         }
      }

      void otherError(Throwable var1) {
         DisposableHelper.dispose(this.mainDisposable);
         HalfSerializer.onError(this.downstream, var1, this, this.error);
      }

      static final class OtherObserver extends AtomicReference<Disposable> implements CompletableObserver {
         private static final long serialVersionUID = -2935427570954647017L;
         final ObservableMergeWithCompletable.MergeWithObserver<?> parent;

         OtherObserver(ObservableMergeWithCompletable.MergeWithObserver<?> var1) {
            this.parent = var1;
         }

         @Override
         public void onComplete() {
            this.parent.otherComplete();
         }

         @Override
         public void onError(Throwable var1) {
            this.parent.otherError(var1);
         }

         @Override
         public void onSubscribe(Disposable var1) {
            DisposableHelper.setOnce(this, var1);
         }
      }
   }
}
