package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableThrottleLatest<T> extends AbstractObservableWithUpstream<T, T> {
   final boolean emitLast;
   final Scheduler scheduler;
   final long timeout;
   final TimeUnit unit;

   public ObservableThrottleLatest(Observable<T> var1, long var2, TimeUnit var4, Scheduler var5, boolean var6) {
      super(var1);
      this.timeout = var2;
      this.unit = var4;
      this.scheduler = var5;
      this.emitLast = var6;
   }

   @Override
   protected void subscribeActual(Observer<? super T> var1) {
      this.source.subscribe(new ObservableThrottleLatest.ThrottleLatestObserver<>(var1, this.timeout, this.unit, this.scheduler.createWorker(), this.emitLast));
   }

   static final class ThrottleLatestObserver<T> extends AtomicInteger implements Observer<T>, Disposable, Runnable {
      private static final long serialVersionUID = -8296689127439125014L;
      volatile boolean cancelled;
      volatile boolean done;
      final Observer<? super T> downstream;
      final boolean emitLast;
      Throwable error;
      final AtomicReference<T> latest;
      final long timeout;
      volatile boolean timerFired;
      boolean timerRunning;
      final TimeUnit unit;
      Disposable upstream;
      final Scheduler.Worker worker;

      ThrottleLatestObserver(Observer<? super T> var1, long var2, TimeUnit var4, Scheduler.Worker var5, boolean var6) {
         this.downstream = var1;
         this.timeout = var2;
         this.unit = var4;
         this.worker = var5;
         this.emitLast = var6;
         this.latest = new AtomicReference<>();
      }

      @Override
      public void dispose() {
         this.cancelled = true;
         this.upstream.dispose();
         this.worker.dispose();
         if (this.getAndIncrement() == 0) {
            this.latest.lazySet(null);
         }
      }

      void drain() {
         if (this.getAndIncrement() == 0) {
            AtomicReference var5 = this.latest;
            Observer var4 = this.downstream;
            int var1 = 1;

            while (!this.cancelled) {
               boolean var3 = this.done;
               if (var3 && this.error != null) {
                  var5.lazySet(null);
                  var4.onError(this.error);
                  this.worker.dispose();
                  return;
               }

               boolean var2;
               if (var5.get() == null) {
                  var2 = 1;
               } else {
                  var2 = 0;
               }

               if (var3) {
                  Object var7 = var5.getAndSet(null);
                  if (!var2 && this.emitLast) {
                     var4.onNext(var7);
                  }

                  var4.onComplete();
                  this.worker.dispose();
                  return;
               }

               if (var2) {
                  if (this.timerFired) {
                     this.timerRunning = false;
                     this.timerFired = false;
                  }
               } else if (!this.timerRunning || this.timerFired) {
                  var4.onNext(var5.getAndSet(null));
                  this.timerFired = false;
                  this.timerRunning = true;
                  this.worker.schedule(this, this.timeout, this.unit);
                  continue;
               }

               var2 = this.addAndGet(-var1);
               var1 = var2;
               if (var2 == 0) {
                  return;
               }
            }

            var5.lazySet(null);
         }
      }

      @Override
      public boolean isDisposed() {
         return this.cancelled;
      }

      @Override
      public void onComplete() {
         this.done = true;
         this.drain();
      }

      @Override
      public void onError(Throwable var1) {
         this.error = var1;
         this.done = true;
         this.drain();
      }

      @Override
      public void onNext(T var1) {
         this.latest.set((T)var1);
         this.drain();
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
         }
      }

      @Override
      public void run() {
         this.timerFired = true;
         this.drain();
      }
   }
}
