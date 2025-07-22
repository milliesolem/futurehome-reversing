package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.SequentialDisposable;
import java.util.concurrent.atomic.AtomicReference;

public final class CompletableSubscribeOn extends Completable {
   final Scheduler scheduler;
   final CompletableSource source;

   public CompletableSubscribeOn(CompletableSource var1, Scheduler var2) {
      this.source = var1;
      this.scheduler = var2;
   }

   @Override
   protected void subscribeActual(CompletableObserver var1) {
      CompletableSubscribeOn.SubscribeOnObserver var2 = new CompletableSubscribeOn.SubscribeOnObserver(var1, this.source);
      var1.onSubscribe(var2);
      Disposable var3 = this.scheduler.scheduleDirect(var2);
      var2.task.replace(var3);
   }

   static final class SubscribeOnObserver extends AtomicReference<Disposable> implements CompletableObserver, Disposable, Runnable {
      private static final long serialVersionUID = 7000911171163930287L;
      final CompletableObserver downstream;
      final CompletableSource source;
      final SequentialDisposable task;

      SubscribeOnObserver(CompletableObserver var1, CompletableSource var2) {
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
      public void onComplete() {
         this.downstream.onComplete();
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
      public void run() {
         this.source.subscribe(this);
      }
   }
}
