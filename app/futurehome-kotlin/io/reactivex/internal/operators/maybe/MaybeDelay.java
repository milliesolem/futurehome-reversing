package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public final class MaybeDelay<T> extends AbstractMaybeWithUpstream<T, T> {
   final long delay;
   final Scheduler scheduler;
   final TimeUnit unit;

   public MaybeDelay(MaybeSource<T> var1, long var2, TimeUnit var4, Scheduler var5) {
      super(var1);
      this.delay = var2;
      this.unit = var4;
      this.scheduler = var5;
   }

   @Override
   protected void subscribeActual(MaybeObserver<? super T> var1) {
      this.source.subscribe(new MaybeDelay.DelayMaybeObserver<>(var1, this.delay, this.unit, this.scheduler));
   }

   static final class DelayMaybeObserver<T> extends AtomicReference<Disposable> implements MaybeObserver<T>, Disposable, Runnable {
      private static final long serialVersionUID = 5566860102500855068L;
      final long delay;
      final MaybeObserver<? super T> downstream;
      Throwable error;
      final Scheduler scheduler;
      final TimeUnit unit;
      T value;

      DelayMaybeObserver(MaybeObserver<? super T> var1, long var2, TimeUnit var4, Scheduler var5) {
         this.downstream = var1;
         this.delay = var2;
         this.unit = var4;
         this.scheduler = var5;
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
      public void onComplete() {
         this.schedule();
      }

      @Override
      public void onError(Throwable var1) {
         this.error = var1;
         this.schedule();
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.setOnce(this, var1)) {
            this.downstream.onSubscribe(this);
         }
      }

      @Override
      public void onSuccess(T var1) {
         this.value = (T)var1;
         this.schedule();
      }

      @Override
      public void run() {
         Throwable var1 = this.error;
         if (var1 != null) {
            this.downstream.onError(var1);
         } else {
            var1 = this.value;
            if (var1 != null) {
               this.downstream.onSuccess((T)var1);
            } else {
               this.downstream.onComplete();
            }
         }
      }

      void schedule() {
         DisposableHelper.replace(this, this.scheduler.scheduleDirect(this, this.delay, this.unit));
      }
   }
}
