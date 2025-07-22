package io.reactivex.internal.operators.maybe;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public final class MaybeTimer extends Maybe<Long> {
   final long delay;
   final Scheduler scheduler;
   final TimeUnit unit;

   public MaybeTimer(long var1, TimeUnit var3, Scheduler var4) {
      this.delay = var1;
      this.unit = var3;
      this.scheduler = var4;
   }

   @Override
   protected void subscribeActual(MaybeObserver<? super Long> var1) {
      MaybeTimer.TimerDisposable var2 = new MaybeTimer.TimerDisposable(var1);
      var1.onSubscribe(var2);
      var2.setFuture(this.scheduler.scheduleDirect(var2, this.delay, this.unit));
   }

   static final class TimerDisposable extends AtomicReference<Disposable> implements Disposable, Runnable {
      private static final long serialVersionUID = 2875964065294031672L;
      final MaybeObserver<? super Long> downstream;

      TimerDisposable(MaybeObserver<? super Long> var1) {
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
         this.downstream.onSuccess(0L);
      }

      void setFuture(Disposable var1) {
         DisposableHelper.replace(this, var1);
      }
   }
}
