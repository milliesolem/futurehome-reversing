package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public final class CompletableTimer extends Completable {
   final long delay;
   final Scheduler scheduler;
   final TimeUnit unit;

   public CompletableTimer(long var1, TimeUnit var3, Scheduler var4) {
      this.delay = var1;
      this.unit = var3;
      this.scheduler = var4;
   }

   @Override
   protected void subscribeActual(CompletableObserver var1) {
      CompletableTimer.TimerDisposable var2 = new CompletableTimer.TimerDisposable(var1);
      var1.onSubscribe(var2);
      var2.setFuture(this.scheduler.scheduleDirect(var2, this.delay, this.unit));
   }

   static final class TimerDisposable extends AtomicReference<Disposable> implements Disposable, Runnable {
      private static final long serialVersionUID = 3167244060586201109L;
      final CompletableObserver downstream;

      TimerDisposable(CompletableObserver var1) {
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
      public void run() {
         this.downstream.onComplete();
      }

      void setFuture(Disposable var1) {
         DisposableHelper.replace(this, var1);
      }
   }
}
