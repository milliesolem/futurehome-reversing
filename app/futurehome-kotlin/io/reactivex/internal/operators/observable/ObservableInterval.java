package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.schedulers.TrampolineScheduler;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableInterval extends Observable<Long> {
   final long initialDelay;
   final long period;
   final Scheduler scheduler;
   final TimeUnit unit;

   public ObservableInterval(long var1, long var3, TimeUnit var5, Scheduler var6) {
      this.initialDelay = var1;
      this.period = var3;
      this.unit = var5;
      this.scheduler = var6;
   }

   @Override
   public void subscribeActual(Observer<? super Long> var1) {
      ObservableInterval.IntervalObserver var2 = new ObservableInterval.IntervalObserver(var1);
      var1.onSubscribe(var2);
      Scheduler var3 = this.scheduler;
      if (var3 instanceof TrampolineScheduler) {
         Scheduler.Worker var4 = var3.createWorker();
         var2.setResource(var4);
         var4.schedulePeriodically(var2, this.initialDelay, this.period, this.unit);
      } else {
         var2.setResource(var3.schedulePeriodicallyDirect(var2, this.initialDelay, this.period, this.unit));
      }
   }

   static final class IntervalObserver extends AtomicReference<Disposable> implements Disposable, Runnable {
      private static final long serialVersionUID = 346773832286157679L;
      long count;
      final Observer<? super Long> downstream;

      IntervalObserver(Observer<? super Long> var1) {
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
         if (this.get() != DisposableHelper.DISPOSED) {
            Observer var3 = this.downstream;
            long var1 = (long)(this.count++);
            var3.onNext(var1);
         }
      }

      public void setResource(Disposable var1) {
         DisposableHelper.setOnce(this, var1);
      }
   }
}
