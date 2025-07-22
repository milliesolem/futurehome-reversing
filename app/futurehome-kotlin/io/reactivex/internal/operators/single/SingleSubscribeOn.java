package io.reactivex.internal.operators.single;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.SequentialDisposable;
import java.util.concurrent.atomic.AtomicReference;

public final class SingleSubscribeOn<T> extends Single<T> {
   final Scheduler scheduler;
   final SingleSource<? extends T> source;

   public SingleSubscribeOn(SingleSource<? extends T> var1, Scheduler var2) {
      this.source = var1;
      this.scheduler = var2;
   }

   @Override
   protected void subscribeActual(SingleObserver<? super T> var1) {
      SingleSubscribeOn.SubscribeOnObserver var2 = new SingleSubscribeOn.SubscribeOnObserver<>(var1, this.source);
      var1.onSubscribe(var2);
      Disposable var3 = this.scheduler.scheduleDirect(var2);
      var2.task.replace(var3);
   }

   static final class SubscribeOnObserver<T> extends AtomicReference<Disposable> implements SingleObserver<T>, Disposable, Runnable {
      private static final long serialVersionUID = 7000911171163930287L;
      final SingleObserver<? super T> downstream;
      final SingleSource<? extends T> source;
      final SequentialDisposable task;

      SubscribeOnObserver(SingleObserver<? super T> var1, SingleSource<? extends T> var2) {
         this.downstream = var1;
         this.source = var2;
         this.task = new SequentialDisposable();
      }

      @Override
      public void dispose() {
         DisposableHelper.dispose(this);
         this.task.dispose();
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
         DisposableHelper.setOnce(this, var1);
      }

      @Override
      public void onSuccess(T var1) {
         this.downstream.onSuccess((T)var1);
      }

      @Override
      public void run() {
         this.source.subscribe(this);
      }
   }
}
