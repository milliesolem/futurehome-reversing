package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;

public final class CompletableDisposeOn extends Completable {
   final Scheduler scheduler;
   final CompletableSource source;

   public CompletableDisposeOn(CompletableSource var1, Scheduler var2) {
      this.source = var1;
      this.scheduler = var2;
   }

   @Override
   protected void subscribeActual(CompletableObserver var1) {
      this.source.subscribe(new CompletableDisposeOn.DisposeOnObserver(var1, this.scheduler));
   }

   static final class DisposeOnObserver implements CompletableObserver, Disposable, Runnable {
      volatile boolean disposed;
      final CompletableObserver downstream;
      final Scheduler scheduler;
      Disposable upstream;

      DisposeOnObserver(CompletableObserver var1, Scheduler var2) {
         this.downstream = var1;
         this.scheduler = var2;
      }

      @Override
      public void dispose() {
         this.disposed = true;
         this.scheduler.scheduleDirect(this);
      }

      @Override
      public boolean isDisposed() {
         return this.disposed;
      }

      @Override
      public void onComplete() {
         if (!this.disposed) {
            this.downstream.onComplete();
         }
      }

      @Override
      public void onError(Throwable var1) {
         if (this.disposed) {
            RxJavaPlugins.onError(var1);
         } else {
            this.downstream.onError(var1);
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
         this.upstream.dispose();
         this.upstream = DisposableHelper.DISPOSED;
      }
   }
}
