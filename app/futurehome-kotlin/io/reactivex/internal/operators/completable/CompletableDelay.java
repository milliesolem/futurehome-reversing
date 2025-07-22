package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public final class CompletableDelay extends Completable {
   final long delay;
   final boolean delayError;
   final Scheduler scheduler;
   final CompletableSource source;
   final TimeUnit unit;

   public CompletableDelay(CompletableSource var1, long var2, TimeUnit var4, Scheduler var5, boolean var6) {
      this.source = var1;
      this.delay = var2;
      this.unit = var4;
      this.scheduler = var5;
      this.delayError = var6;
   }

   @Override
   protected void subscribeActual(CompletableObserver var1) {
      this.source.subscribe(new CompletableDelay.Delay(var1, this.delay, this.unit, this.scheduler, this.delayError));
   }

   static final class Delay extends AtomicReference<Disposable> implements CompletableObserver, Runnable, Disposable {
      private static final long serialVersionUID = 465972761105851022L;
      final long delay;
      final boolean delayError;
      final CompletableObserver downstream;
      Throwable error;
      final Scheduler scheduler;
      final TimeUnit unit;

      Delay(CompletableObserver var1, long var2, TimeUnit var4, Scheduler var5, boolean var6) {
         this.downstream = var1;
         this.delay = var2;
         this.unit = var4;
         this.scheduler = var5;
         this.delayError = var6;
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
         DisposableHelper.replace(this, this.scheduler.scheduleDirect(this, this.delay, this.unit));
      }

      @Override
      public void onError(Throwable var1) {
         this.error = var1;
         Scheduler var4 = this.scheduler;
         long var2;
         if (this.delayError) {
            var2 = this.delay;
         } else {
            var2 = 0L;
         }

         DisposableHelper.replace(this, var4.scheduleDirect(this, var2, this.unit));
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.setOnce(this, var1)) {
            this.downstream.onSubscribe(this);
         }
      }

      @Override
      public void run() {
         Throwable var1 = this.error;
         this.error = null;
         if (var1 != null) {
            this.downstream.onError(var1);
         } else {
            this.downstream.onComplete();
         }
      }
   }
}
