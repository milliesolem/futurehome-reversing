package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

public final class CompletableTimeout extends Completable {
   final CompletableSource other;
   final Scheduler scheduler;
   final CompletableSource source;
   final long timeout;
   final TimeUnit unit;

   public CompletableTimeout(CompletableSource var1, long var2, TimeUnit var4, Scheduler var5, CompletableSource var6) {
      this.source = var1;
      this.timeout = var2;
      this.unit = var4;
      this.scheduler = var5;
      this.other = var6;
   }

   @Override
   public void subscribeActual(CompletableObserver var1) {
      CompositeDisposable var3 = new CompositeDisposable();
      var1.onSubscribe(var3);
      AtomicBoolean var2 = new AtomicBoolean();
      var3.add(this.scheduler.scheduleDirect(new CompletableTimeout.DisposeTask(this, var2, var3, var1), this.timeout, this.unit));
      this.source.subscribe(new CompletableTimeout.TimeOutObserver(var3, var2, var1));
   }

   final class DisposeTask implements Runnable {
      final CompletableObserver downstream;
      private final AtomicBoolean once;
      final CompositeDisposable set;
      final CompletableTimeout this$0;

      DisposeTask(CompletableTimeout var1, AtomicBoolean var2, CompositeDisposable var3, CompletableObserver var4) {
         this.this$0 = var1;
         this.once = var2;
         this.set = var3;
         this.downstream = var4;
      }

      @Override
      public void run() {
         if (this.once.compareAndSet(false, true)) {
            this.set.clear();
            if (this.this$0.other == null) {
               this.downstream.onError(new TimeoutException(ExceptionHelper.timeoutMessage(this.this$0.timeout, this.this$0.unit)));
            } else {
               this.this$0.other.subscribe(new CompletableTimeout.DisposeTask.DisposeObserver(this));
            }
         }
      }

      final class DisposeObserver implements CompletableObserver {
         final CompletableTimeout.DisposeTask this$1;

         DisposeObserver(CompletableTimeout.DisposeTask var1) {
            this.this$1 = var1;
         }

         @Override
         public void onComplete() {
            this.this$1.set.dispose();
            this.this$1.downstream.onComplete();
         }

         @Override
         public void onError(Throwable var1) {
            this.this$1.set.dispose();
            this.this$1.downstream.onError(var1);
         }

         @Override
         public void onSubscribe(Disposable var1) {
            this.this$1.set.add(var1);
         }
      }
   }

   static final class TimeOutObserver implements CompletableObserver {
      private final CompletableObserver downstream;
      private final AtomicBoolean once;
      private final CompositeDisposable set;

      TimeOutObserver(CompositeDisposable var1, AtomicBoolean var2, CompletableObserver var3) {
         this.set = var1;
         this.once = var2;
         this.downstream = var3;
      }

      @Override
      public void onComplete() {
         if (this.once.compareAndSet(false, true)) {
            this.set.dispose();
            this.downstream.onComplete();
         }
      }

      @Override
      public void onError(Throwable var1) {
         if (this.once.compareAndSet(false, true)) {
            this.set.dispose();
            this.downstream.onError(var1);
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      @Override
      public void onSubscribe(Disposable var1) {
         this.set.add(var1);
      }
   }
}
