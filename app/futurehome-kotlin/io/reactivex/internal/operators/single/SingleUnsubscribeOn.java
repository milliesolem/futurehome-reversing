package io.reactivex.internal.operators.single;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class SingleUnsubscribeOn<T> extends Single<T> {
   final Scheduler scheduler;
   final SingleSource<T> source;

   public SingleUnsubscribeOn(SingleSource<T> var1, Scheduler var2) {
      this.source = var1;
      this.scheduler = var2;
   }

   @Override
   protected void subscribeActual(SingleObserver<? super T> var1) {
      this.source.subscribe(new SingleUnsubscribeOn.UnsubscribeOnSingleObserver<>(var1, this.scheduler));
   }

   static final class UnsubscribeOnSingleObserver<T> extends AtomicReference<Disposable> implements SingleObserver<T>, Disposable, Runnable {
      private static final long serialVersionUID = 3256698449646456986L;
      final SingleObserver<? super T> downstream;
      Disposable ds;
      final Scheduler scheduler;

      UnsubscribeOnSingleObserver(SingleObserver<? super T> var1, Scheduler var2) {
         this.downstream = var1;
         this.scheduler = var2;
      }

      @Override
      public void dispose() {
         Disposable var1 = this.getAndSet(DisposableHelper.DISPOSED);
         if (var1 != DisposableHelper.DISPOSED) {
            this.ds = var1;
            this.scheduler.scheduleDirect(this);
         }
      }

      @Override
      public boolean isDisposed() {
         return DisposableHelper.isDisposed(this.get());
      }

      @Override
      public void onError(Throwable var1) {
         this.downstream.onError(var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.setOnce(this, var1)) {
            this.downstream.onSubscribe(this);
         }
      }

      @Override
      public void onSuccess(T var1) {
         this.downstream.onSuccess((T)var1);
      }

      @Override
      public void run() {
         this.ds.dispose();
      }
   }
}
