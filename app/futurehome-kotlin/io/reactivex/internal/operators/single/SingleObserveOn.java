package io.reactivex.internal.operators.single;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class SingleObserveOn<T> extends Single<T> {
   final Scheduler scheduler;
   final SingleSource<T> source;

   public SingleObserveOn(SingleSource<T> var1, Scheduler var2) {
      this.source = var1;
      this.scheduler = var2;
   }

   @Override
   protected void subscribeActual(SingleObserver<? super T> var1) {
      this.source.subscribe(new SingleObserveOn.ObserveOnSingleObserver<>(var1, this.scheduler));
   }

   static final class ObserveOnSingleObserver<T> extends AtomicReference<Disposable> implements SingleObserver<T>, Disposable, Runnable {
      private static final long serialVersionUID = 3528003840217436037L;
      final SingleObserver<? super T> downstream;
      Throwable error;
      final Scheduler scheduler;
      T value;

      ObserveOnSingleObserver(SingleObserver<? super T> var1, Scheduler var2) {
         this.downstream = var1;
         this.scheduler = var2;
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
      public void onError(Throwable var1) {
         this.error = var1;
         DisposableHelper.replace(this, this.scheduler.scheduleDirect(this));
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.setOnce(this, var1)) {
            this.downstream.onSubscribe(this);
         }
      }

      @Override
      public void onSuccess(T var1) {
         this.value = (T)var1;
         DisposableHelper.replace(this, this.scheduler.scheduleDirect(this));
      }

      @Override
      public void run() {
         Throwable var1 = this.error;
         if (var1 != null) {
            this.downstream.onError(var1);
         } else {
            this.downstream.onSuccess(this.value);
         }
      }
   }
}
