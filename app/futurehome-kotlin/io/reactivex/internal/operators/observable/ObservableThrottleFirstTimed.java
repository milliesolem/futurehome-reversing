package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.observers.SerializedObserver;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableThrottleFirstTimed<T> extends AbstractObservableWithUpstream<T, T> {
   final Scheduler scheduler;
   final long timeout;
   final TimeUnit unit;

   public ObservableThrottleFirstTimed(ObservableSource<T> var1, long var2, TimeUnit var4, Scheduler var5) {
      super(var1);
      this.timeout = var2;
      this.unit = var4;
      this.scheduler = var5;
   }

   @Override
   public void subscribeActual(Observer<? super T> var1) {
      this.source
         .subscribe(
            new ObservableThrottleFirstTimed.DebounceTimedObserver<>(new SerializedObserver<>(var1), this.timeout, this.unit, this.scheduler.createWorker())
         );
   }

   static final class DebounceTimedObserver<T> extends AtomicReference<Disposable> implements Observer<T>, Disposable, Runnable {
      private static final long serialVersionUID = 786994795061867455L;
      boolean done;
      final Observer<? super T> downstream;
      volatile boolean gate;
      final long timeout;
      final TimeUnit unit;
      Disposable upstream;
      final Scheduler.Worker worker;

      DebounceTimedObserver(Observer<? super T> var1, long var2, TimeUnit var4, Scheduler.Worker var5) {
         this.downstream = var1;
         this.timeout = var2;
         this.unit = var4;
         this.worker = var5;
      }

      @Override
      public void dispose() {
         this.upstream.dispose();
         this.worker.dispose();
      }

      @Override
      public boolean isDisposed() {
         return this.worker.isDisposed();
      }

      @Override
      public void onComplete() {
         if (!this.done) {
            this.done = true;
            this.downstream.onComplete();
            this.worker.dispose();
         }
      }

      @Override
      public void onError(Throwable var1) {
         if (this.done) {
            RxJavaPlugins.onError(var1);
         } else {
            this.done = true;
            this.downstream.onError(var1);
            this.worker.dispose();
         }
      }

      @Override
      public void onNext(T var1) {
         if (!this.gate && !this.done) {
            this.gate = true;
            this.downstream.onNext((T)var1);
            var1 = this.get();
            if (var1 != null) {
               var1.dispose();
            }

            DisposableHelper.replace(this, this.worker.schedule(this, this.timeout, this.unit));
         }
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
         this.gate = false;
      }
   }
}
