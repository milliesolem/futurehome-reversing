package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.observers.SerializedObserver;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableDebounceTimed<T> extends AbstractObservableWithUpstream<T, T> {
   final Scheduler scheduler;
   final long timeout;
   final TimeUnit unit;

   public ObservableDebounceTimed(ObservableSource<T> var1, long var2, TimeUnit var4, Scheduler var5) {
      super(var1);
      this.timeout = var2;
      this.unit = var4;
      this.scheduler = var5;
   }

   @Override
   public void subscribeActual(Observer<? super T> var1) {
      this.source
         .subscribe(new ObservableDebounceTimed.DebounceTimedObserver<>(new SerializedObserver<>(var1), this.timeout, this.unit, this.scheduler.createWorker()));
   }

   static final class DebounceEmitter<T> extends AtomicReference<Disposable> implements Runnable, Disposable {
      private static final long serialVersionUID = 6812032969491025141L;
      final long idx;
      final AtomicBoolean once = new AtomicBoolean();
      final ObservableDebounceTimed.DebounceTimedObserver<T> parent;
      final T value;

      DebounceEmitter(T var1, long var2, ObservableDebounceTimed.DebounceTimedObserver<T> var4) {
         this.value = (T)var1;
         this.idx = var2;
         this.parent = var4;
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
         if (this.once.compareAndSet(false, true)) {
            this.parent.emit(this.idx, this.value, this);
         }
      }

      public void setResource(Disposable var1) {
         DisposableHelper.replace(this, var1);
      }
   }

   static final class DebounceTimedObserver<T> implements Observer<T>, Disposable {
      boolean done;
      final Observer<? super T> downstream;
      volatile long index;
      final long timeout;
      Disposable timer;
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

      void emit(long var1, T var3, ObservableDebounceTimed.DebounceEmitter<T> var4) {
         if (var1 == this.index) {
            this.downstream.onNext((T)var3);
            var4.dispose();
         }
      }

      @Override
      public boolean isDisposed() {
         return this.worker.isDisposed();
      }

      @Override
      public void onComplete() {
         if (!this.done) {
            this.done = true;
            Disposable var1 = this.timer;
            if (var1 != null) {
               var1.dispose();
            }

            ObservableDebounceTimed.DebounceEmitter var2 = (ObservableDebounceTimed.DebounceEmitter)var1;
            if (var2 != null) {
               var2.run();
            }

            this.downstream.onComplete();
            this.worker.dispose();
         }
      }

      @Override
      public void onError(Throwable var1) {
         if (this.done) {
            RxJavaPlugins.onError(var1);
         } else {
            Disposable var2 = this.timer;
            if (var2 != null) {
               var2.dispose();
            }

            this.done = true;
            this.downstream.onError(var1);
            this.worker.dispose();
         }
      }

      @Override
      public void onNext(T var1) {
         if (!this.done) {
            long var2 = this.index + 1L;
            this.index = var2;
            Disposable var4 = this.timer;
            if (var4 != null) {
               var4.dispose();
            }

            var1 = new ObservableDebounceTimed.DebounceEmitter<>(var1, var2, this);
            this.timer = var1;
            var1.setResource(this.worker.schedule(var1, this.timeout, this.unit));
         }
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
         }
      }
   }
}
