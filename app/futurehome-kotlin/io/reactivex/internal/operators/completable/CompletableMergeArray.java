package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public final class CompletableMergeArray extends Completable {
   final CompletableSource[] sources;

   public CompletableMergeArray(CompletableSource[] var1) {
      this.sources = var1;
   }

   @Override
   public void subscribeActual(CompletableObserver var1) {
      CompositeDisposable var4 = new CompositeDisposable();
      CompletableMergeArray.InnerCompletableObserver var5 = new CompletableMergeArray.InnerCompletableObserver(
         var1, new AtomicBoolean(), var4, this.sources.length + 1
      );
      var1.onSubscribe(var4);

      for (CompletableSource var6 : this.sources) {
         if (var4.isDisposed()) {
            return;
         }

         if (var6 == null) {
            var4.dispose();
            var5.onError(new NullPointerException("A completable source is null"));
            return;
         }

         var6.subscribe(var5);
      }

      var5.onComplete();
   }

   static final class InnerCompletableObserver extends AtomicInteger implements CompletableObserver {
      private static final long serialVersionUID = -8360547806504310570L;
      final CompletableObserver downstream;
      final AtomicBoolean once;
      final CompositeDisposable set;

      InnerCompletableObserver(CompletableObserver var1, AtomicBoolean var2, CompositeDisposable var3, int var4) {
         this.downstream = var1;
         this.once = var2;
         this.set = var3;
         this.lazySet(var4);
      }

      @Override
      public void onComplete() {
         if (this.decrementAndGet() == 0 && this.once.compareAndSet(false, true)) {
            this.downstream.onComplete();
         }
      }

      @Override
      public void onError(Throwable var1) {
         this.set.dispose();
         if (this.once.compareAndSet(false, true)) {
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
