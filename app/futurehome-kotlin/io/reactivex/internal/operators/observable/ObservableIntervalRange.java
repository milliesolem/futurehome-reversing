package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.schedulers.TrampolineScheduler;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableIntervalRange extends Observable<Long> {
   final long end;
   final long initialDelay;
   final long period;
   final Scheduler scheduler;
   final long start;
   final TimeUnit unit;

   public ObservableIntervalRange(long var1, long var3, long var5, long var7, TimeUnit var9, Scheduler var10) {
      this.initialDelay = var5;
      this.period = var7;
      this.unit = var9;
      this.scheduler = var10;
      this.start = var1;
      this.end = var3;
   }

   @Override
   public void subscribeActual(Observer<? super Long> var1) {
      ObservableIntervalRange.IntervalRangeObserver var2 = new ObservableIntervalRange.IntervalRangeObserver(var1, this.start, this.end);
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

   static final class IntervalRangeObserver extends AtomicReference<Disposable> implements Disposable, Runnable {
      private static final long serialVersionUID = 1891866368734007884L;
      long count;
      final Observer<? super Long> downstream;
      final long end;

      IntervalRangeObserver(Observer<? super Long> var1, long var2, long var4) {
         this.downstream = var1;
         this.count = var2;
         this.end = var4;
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
            long var1 = this.count;
            this.downstream.onNext(var1);
            if (var1 == this.end) {
               DisposableHelper.dispose(this);
               this.downstream.onComplete();
               return;
            }

            this.count = var1 + 1L;
         }
      }

      public void setResource(Disposable var1) {
         DisposableHelper.setOnce(this, var1);
      }
   }
}
