package io.reactivex.internal.operators.single;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

public final class SingleTimeout<T> extends Single<T> {
   final SingleSource<? extends T> other;
   final Scheduler scheduler;
   final SingleSource<T> source;
   final long timeout;
   final TimeUnit unit;

   public SingleTimeout(SingleSource<T> var1, long var2, TimeUnit var4, Scheduler var5, SingleSource<? extends T> var6) {
      this.source = var1;
      this.timeout = var2;
      this.unit = var4;
      this.scheduler = var5;
      this.other = var6;
   }

   @Override
   protected void subscribeActual(SingleObserver<? super T> var1) {
      SingleTimeout.TimeoutMainObserver var2 = new SingleTimeout.TimeoutMainObserver<>(var1, this.other, this.timeout, this.unit);
      var1.onSubscribe(var2);
      DisposableHelper.replace(var2.task, this.scheduler.scheduleDirect(var2, this.timeout, this.unit));
      this.source.subscribe(var2);
   }

   static final class TimeoutMainObserver<T> extends AtomicReference<Disposable> implements SingleObserver<T>, Runnable, Disposable {
      private static final long serialVersionUID = 37497744973048446L;
      final SingleObserver<? super T> downstream;
      final SingleTimeout.TimeoutMainObserver.TimeoutFallbackObserver<T> fallback;
      SingleSource<? extends T> other;
      final AtomicReference<Disposable> task;
      final long timeout;
      final TimeUnit unit;

      TimeoutMainObserver(SingleObserver<? super T> var1, SingleSource<? extends T> var2, long var3, TimeUnit var5) {
         this.downstream = var1;
         this.other = var2;
         this.timeout = var3;
         this.unit = var5;
         this.task = new AtomicReference<>();
         if (var2 != null) {
            this.fallback = new SingleTimeout.TimeoutMainObserver.TimeoutFallbackObserver<>(var1);
         } else {
            this.fallback = null;
         }
      }

      @Override
      public void dispose() {
         DisposableHelper.dispose(this);
         DisposableHelper.dispose(this.task);
         SingleTimeout.TimeoutMainObserver.TimeoutFallbackObserver var1 = this.fallback;
         if (var1 != null) {
            DisposableHelper.dispose(var1);
         }
      }

      @Override
      public boolean isDisposed() {
         return DisposableHelper.isDisposed(this.get());
      }

      @Override
      public void onError(Throwable var1) {
         Disposable var2 = this.get();
         if (var2 != DisposableHelper.DISPOSED && this.compareAndSet(var2, DisposableHelper.DISPOSED)) {
            DisposableHelper.dispose(this.task);
            this.downstream.onError(var1);
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      @Override
      public void onSubscribe(Disposable var1) {
         DisposableHelper.setOnce(this, var1);
      }

      @Override
      public void onSuccess(T var1) {
         Disposable var2 = this.get();
         if (var2 != DisposableHelper.DISPOSED && this.compareAndSet(var2, DisposableHelper.DISPOSED)) {
            DisposableHelper.dispose(this.task);
            this.downstream.onSuccess((T)var1);
         }
      }

      @Override
      public void run() {
         Disposable var1 = this.get();
         if (var1 != DisposableHelper.DISPOSED && this.compareAndSet(var1, DisposableHelper.DISPOSED)) {
            if (var1 != null) {
               var1.dispose();
            }

            SingleSource var2 = this.other;
            if (var2 == null) {
               this.downstream.onError(new TimeoutException(ExceptionHelper.timeoutMessage(this.timeout, this.unit)));
            } else {
               this.other = null;
               var2.subscribe(this.fallback);
            }
         }
      }

      static final class TimeoutFallbackObserver<T> extends AtomicReference<Disposable> implements SingleObserver<T> {
         private static final long serialVersionUID = 2071387740092105509L;
         final SingleObserver<? super T> downstream;

         TimeoutFallbackObserver(SingleObserver<? super T> var1) {
            this.downstream = var1;
         }

         @Override
         public void onError(Throwable var1) {
            this.downstream.onError(var1);
         }

         @Override
         public void onSubscribe(Disposable var1) {
            DisposableHelper.setOnce(this, var1);
         }

         @Override
         public void onSuccess(T var1) {
            this.downstream.onSuccess((T)var1);
         }
      }
   }
}
