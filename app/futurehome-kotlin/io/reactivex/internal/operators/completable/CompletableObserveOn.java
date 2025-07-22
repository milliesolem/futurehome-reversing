package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class CompletableObserveOn extends Completable {
   final Scheduler scheduler;
   final CompletableSource source;

   public CompletableObserveOn(CompletableSource var1, Scheduler var2) {
      this.source = var1;
      this.scheduler = var2;
   }

   @Override
   protected void subscribeActual(CompletableObserver var1) {
      this.source.subscribe(new CompletableObserveOn.ObserveOnCompletableObserver(var1, this.scheduler));
   }

   static final class ObserveOnCompletableObserver extends AtomicReference<Disposable> implements CompletableObserver, Disposable, Runnable {
      private static final long serialVersionUID = 8571289934935992137L;
      final CompletableObserver downstream;
      Throwable error;
      final Scheduler scheduler;

      ObserveOnCompletableObserver(CompletableObserver var1, Scheduler var2) {
         this.downstream = var1;
         this.scheduler = var2;
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
         DisposableHelper.replace(this, this.scheduler.scheduleDirect(this));
      }

      @Override
      public void onError(Throwable var1) {
         this.error = var1;
         DisposableHelper.replace(this, this.scheduler.scheduleDirect(this));
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
         if (var1 != null) {
            this.error = null;
            this.downstream.onError(var1);
         } else {
            this.downstream.onComplete();
         }
      }
   }
}
