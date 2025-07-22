package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableTimeoutTimed<T> extends AbstractObservableWithUpstream<T, T> {
   final ObservableSource<? extends T> other;
   final Scheduler scheduler;
   final long timeout;
   final TimeUnit unit;

   public ObservableTimeoutTimed(Observable<T> var1, long var2, TimeUnit var4, Scheduler var5, ObservableSource<? extends T> var6) {
      super(var1);
      this.timeout = var2;
      this.unit = var4;
      this.scheduler = var5;
      this.other = var6;
   }

   @Override
   protected void subscribeActual(Observer<? super T> var1) {
      if (this.other == null) {
         ObservableTimeoutTimed.TimeoutObserver var2 = new ObservableTimeoutTimed.TimeoutObserver(var1, this.timeout, this.unit, this.scheduler.createWorker());
         var1.onSubscribe(var2);
         var2.startTimeout(0L);
         this.source.subscribe(var2);
      } else {
         ObservableTimeoutTimed.TimeoutFallbackObserver var3 = new ObservableTimeoutTimed.TimeoutFallbackObserver<>(
            var1, this.timeout, this.unit, this.scheduler.createWorker(), this.other
         );
         var1.onSubscribe(var3);
         var3.startTimeout(0L);
         this.source.subscribe(var3);
      }
   }

   static final class FallbackObserver<T> implements Observer<T> {
      final AtomicReference<Disposable> arbiter;
      final Observer<? super T> downstream;

      FallbackObserver(Observer<? super T> var1, AtomicReference<Disposable> var2) {
         this.downstream = var1;
         this.arbiter = var2;
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
      public void onNext(T var1) {
         this.downstream.onNext((T)var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         DisposableHelper.replace(this.arbiter, var1);
      }
   }

   static final class TimeoutFallbackObserver<T> extends AtomicReference<Disposable> implements Observer<T>, Disposable, ObservableTimeoutTimed.TimeoutSupport {
      private static final long serialVersionUID = 3764492702657003550L;
      final Observer<? super T> downstream;
      ObservableSource<? extends T> fallback;
      final AtomicLong index;
      final SequentialDisposable task;
      final long timeout;
      final TimeUnit unit;
      final AtomicReference<Disposable> upstream;
      final Scheduler.Worker worker;

      TimeoutFallbackObserver(Observer<? super T> var1, long var2, TimeUnit var4, Scheduler.Worker var5, ObservableSource<? extends T> var6) {
         this.downstream = var1;
         this.timeout = var2;
         this.unit = var4;
         this.worker = var5;
         this.fallback = var6;
         this.task = new SequentialDisposable();
         this.index = new AtomicLong();
         this.upstream = new AtomicReference<>();
      }

      @Override
      public void dispose() {
         DisposableHelper.dispose(this.upstream);
         DisposableHelper.dispose(this);
         this.worker.dispose();
      }

      @Override
      public boolean isDisposed() {
         return DisposableHelper.isDisposed(this.get());
      }

      @Override
      public void onComplete() {
         if (this.index.getAndSet(Long.MAX_VALUE) != Long.MAX_VALUE) {
            this.task.dispose();
            this.downstream.onComplete();
            this.worker.dispose();
         }
      }

      @Override
      public void onError(Throwable var1) {
         if (this.index.getAndSet(Long.MAX_VALUE) != Long.MAX_VALUE) {
            this.task.dispose();
            this.downstream.onError(var1);
            this.worker.dispose();
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      @Override
      public void onNext(T var1) {
         long var4 = this.index.get();
         if (var4 != Long.MAX_VALUE) {
            AtomicLong var6 = this.index;
            long var2 = 1L + var4;
            if (var6.compareAndSet(var4, var2)) {
               this.task.get().dispose();
               this.downstream.onNext((T)var1);
               this.startTimeout(var2);
            }
         }
      }

      @Override
      public void onSubscribe(Disposable var1) {
         DisposableHelper.setOnce(this.upstream, var1);
      }

      @Override
      public void onTimeout(long var1) {
         if (this.index.compareAndSet(var1, Long.MAX_VALUE)) {
            DisposableHelper.dispose(this.upstream);
            ObservableSource var3 = this.fallback;
            this.fallback = null;
            var3.subscribe(new ObservableTimeoutTimed.FallbackObserver<>(this.downstream, this));
            this.worker.dispose();
         }
      }

      void startTimeout(long var1) {
         this.task.replace(this.worker.schedule(new ObservableTimeoutTimed.TimeoutTask(var1, this), this.timeout, this.unit));
      }
   }

   static final class TimeoutObserver<T> extends AtomicLong implements Observer<T>, Disposable, ObservableTimeoutTimed.TimeoutSupport {
      private static final long serialVersionUID = 3764492702657003550L;
      final Observer<? super T> downstream;
      final SequentialDisposable task;
      final long timeout;
      final TimeUnit unit;
      final AtomicReference<Disposable> upstream;
      final Scheduler.Worker worker;

      TimeoutObserver(Observer<? super T> var1, long var2, TimeUnit var4, Scheduler.Worker var5) {
         this.downstream = var1;
         this.timeout = var2;
         this.unit = var4;
         this.worker = var5;
         this.task = new SequentialDisposable();
         this.upstream = new AtomicReference<>();
      }

      @Override
      public void dispose() {
         DisposableHelper.dispose(this.upstream);
         this.worker.dispose();
      }

      @Override
      public boolean isDisposed() {
         return DisposableHelper.isDisposed(this.upstream.get());
      }

      @Override
      public void onComplete() {
         if (this.getAndSet(Long.MAX_VALUE) != Long.MAX_VALUE) {
            this.task.dispose();
            this.downstream.onComplete();
            this.worker.dispose();
         }
      }

      @Override
      public void onError(Throwable var1) {
         if (this.getAndSet(Long.MAX_VALUE) != Long.MAX_VALUE) {
            this.task.dispose();
            this.downstream.onError(var1);
            this.worker.dispose();
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      @Override
      public void onNext(T var1) {
         long var4 = this.get();
         if (var4 != Long.MAX_VALUE) {
            long var2 = 1L + var4;
            if (this.compareAndSet(var4, var2)) {
               this.task.get().dispose();
               this.downstream.onNext((T)var1);
               this.startTimeout(var2);
            }
         }
      }

      @Override
      public void onSubscribe(Disposable var1) {
         DisposableHelper.setOnce(this.upstream, var1);
      }

      @Override
      public void onTimeout(long var1) {
         if (this.compareAndSet(var1, Long.MAX_VALUE)) {
            DisposableHelper.dispose(this.upstream);
            this.downstream.onError(new TimeoutException(ExceptionHelper.timeoutMessage(this.timeout, this.unit)));
            this.worker.dispose();
         }
      }

      void startTimeout(long var1) {
         this.task.replace(this.worker.schedule(new ObservableTimeoutTimed.TimeoutTask(var1, this), this.timeout, this.unit));
      }
   }

   interface TimeoutSupport {
      void onTimeout(long var1);
   }

   static final class TimeoutTask implements Runnable {
      final long idx;
      final ObservableTimeoutTimed.TimeoutSupport parent;

      TimeoutTask(long var1, ObservableTimeoutTimed.TimeoutSupport var3) {
         this.idx = var1;
         this.parent = var3;
      }

      @Override
      public void run() {
         this.parent.onTimeout(this.idx);
      }
   }
}
