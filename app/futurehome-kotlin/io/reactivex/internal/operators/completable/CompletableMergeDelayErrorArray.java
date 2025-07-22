package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;

public final class CompletableMergeDelayErrorArray extends Completable {
   final CompletableSource[] sources;

   public CompletableMergeDelayErrorArray(CompletableSource[] var1) {
      this.sources = var1;
   }

   @Override
   public void subscribeActual(CompletableObserver var1) {
      CompositeDisposable var4 = new CompositeDisposable();
      AtomicInteger var7 = new AtomicInteger(this.sources.length + 1);
      AtomicThrowable var5 = new AtomicThrowable();
      var1.onSubscribe(var4);

      for (CompletableSource var8 : this.sources) {
         if (var4.isDisposed()) {
            return;
         }

         if (var8 == null) {
            var5.addThrowable(new NullPointerException("A completable source is null"));
            var7.decrementAndGet();
         } else {
            var8.subscribe(new CompletableMergeDelayErrorArray.MergeInnerCompletableObserver(var1, var4, var5, var7));
         }
      }

      if (var7.decrementAndGet() == 0) {
         Throwable var9 = var5.terminate();
         if (var9 == null) {
            var1.onComplete();
         } else {
            var1.onError(var9);
         }
      }
   }

   static final class MergeInnerCompletableObserver implements CompletableObserver {
      final CompletableObserver downstream;
      final AtomicThrowable error;
      final CompositeDisposable set;
      final AtomicInteger wip;

      MergeInnerCompletableObserver(CompletableObserver var1, CompositeDisposable var2, AtomicThrowable var3, AtomicInteger var4) {
         this.downstream = var1;
         this.set = var2;
         this.error = var3;
         this.wip = var4;
      }

      @Override
      public void onComplete() {
         this.tryTerminate();
      }

      @Override
      public void onError(Throwable var1) {
         if (this.error.addThrowable(var1)) {
            this.tryTerminate();
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      @Override
      public void onSubscribe(Disposable var1) {
         this.set.add(var1);
      }

      void tryTerminate() {
         if (this.wip.decrementAndGet() == 0) {
            Throwable var1 = this.error.terminate();
            if (var1 == null) {
               this.downstream.onComplete();
            } else {
               this.downstream.onError(var1);
            }
         }
      }
   }
}
