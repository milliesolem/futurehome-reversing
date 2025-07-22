package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableTimer extends Observable<Long> {
   final long delay;
   final Scheduler scheduler;
   final TimeUnit unit;

   public ObservableTimer(long var1, TimeUnit var3, Scheduler var4) {
      this.delay = var1;
      this.unit = var3;
      this.scheduler = var4;
   }

   @Override
   public void subscribeActual(Observer<? super Long> var1) {
      ObservableTimer.TimerObserver var2 = new ObservableTimer.TimerObserver(var1);
      var1.onSubscribe(var2);
      var2.setResource(this.scheduler.scheduleDirect(var2, this.delay, this.unit));
   }

   static final class TimerObserver extends AtomicReference<Disposable> implements Disposable, Runnable {
      private static final long serialVersionUID = -2809475196591179431L;
      final Observer<? super Long> downstream;

      TimerObserver(Observer<? super Long> var1) {
         this.downstream = var1;
      }

      @Override
      public void dispose() {
         DisposableHelper.dispose(this);
      }

      @Override
      public boolean isDisposed() {
         boolean var1;
         if (this.get() == DisposableHelper.DISPOSED) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      @Override
      public void run() {
         if (!this.isDisposed()) {
            this.downstream.onNext(0L);
            this.lazySet(EmptyDisposable.INSTANCE);
            this.downstream.onComplete();
         }
      }

      public void setResource(Disposable var1) {
         DisposableHelper.trySet(this, var1);
      }
   }
}
