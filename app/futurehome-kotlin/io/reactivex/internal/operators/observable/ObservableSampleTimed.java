package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.observers.SerializedObserver;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableSampleTimed<T> extends AbstractObservableWithUpstream<T, T> {
   final boolean emitLast;
   final long period;
   final Scheduler scheduler;
   final TimeUnit unit;

   public ObservableSampleTimed(ObservableSource<T> var1, long var2, TimeUnit var4, Scheduler var5, boolean var6) {
      super(var1);
      this.period = var2;
      this.unit = var4;
      this.scheduler = var5;
      this.emitLast = var6;
   }

   @Override
   public void subscribeActual(Observer<? super T> var1) {
      SerializedObserver var2 = new SerializedObserver(var1);
      if (this.emitLast) {
         this.source.subscribe(new ObservableSampleTimed.SampleTimedEmitLast<>(var2, this.period, this.unit, this.scheduler));
      } else {
         this.source.subscribe(new ObservableSampleTimed.SampleTimedNoLast<>(var2, this.period, this.unit, this.scheduler));
      }
   }

   static final class SampleTimedEmitLast<T> extends ObservableSampleTimed.SampleTimedObserver<T> {
      private static final long serialVersionUID = -7139995637533111443L;
      final AtomicInteger wip = new AtomicInteger(1);

      SampleTimedEmitLast(Observer<? super T> var1, long var2, TimeUnit var4, Scheduler var5) {
         super(var1, var2, var4, var5);
      }

      @Override
      void complete() {
         this.emit();
         if (this.wip.decrementAndGet() == 0) {
            this.downstream.onComplete();
         }
      }

      @Override
      public void run() {
         if (this.wip.incrementAndGet() == 2) {
            this.emit();
            if (this.wip.decrementAndGet() == 0) {
               this.downstream.onComplete();
            }
         }
      }
   }

   static final class SampleTimedNoLast<T> extends ObservableSampleTimed.SampleTimedObserver<T> {
      private static final long serialVersionUID = -7139995637533111443L;

      SampleTimedNoLast(Observer<? super T> var1, long var2, TimeUnit var4, Scheduler var5) {
         super(var1, var2, var4, var5);
      }

      @Override
      void complete() {
         this.downstream.onComplete();
      }

      @Override
      public void run() {
         this.emit();
      }
   }

   abstract static class SampleTimedObserver<T> extends AtomicReference<T> implements Observer<T>, Disposable, Runnable {
      private static final long serialVersionUID = -3517602651313910099L;
      final Observer<? super T> downstream;
      final long period;
      final Scheduler scheduler;
      final AtomicReference<Disposable> timer = new AtomicReference<>();
      final TimeUnit unit;
      Disposable upstream;

      SampleTimedObserver(Observer<? super T> var1, long var2, TimeUnit var4, Scheduler var5) {
         this.downstream = var1;
         this.period = var2;
         this.unit = var4;
         this.scheduler = var5;
      }

      void cancelTimer() {
         DisposableHelper.dispose(this.timer);
      }

      abstract void complete();

      @Override
      public void dispose() {
         this.cancelTimer();
         this.upstream.dispose();
      }

      void emit() {
         Object var1 = this.getAndSet(null);
         if (var1 != null) {
            this.downstream.onNext((T)var1);
         }
      }

      @Override
      public boolean isDisposed() {
         return this.upstream.isDisposed();
      }

      @Override
      public void onComplete() {
         this.cancelTimer();
         this.complete();
      }

      @Override
      public void onError(Throwable var1) {
         this.cancelTimer();
         this.downstream.onError(var1);
      }

      @Override
      public void onNext(T var1) {
         this.lazySet((T)var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
            Scheduler var4 = this.scheduler;
            long var2 = this.period;
            var1 = var4.schedulePeriodicallyDirect(this, var2, var2, this.unit);
            DisposableHelper.replace(this.timer, var1);
         }
      }
   }
}
