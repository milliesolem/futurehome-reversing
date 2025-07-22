package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;

public final class CompletableHide extends Completable {
   final CompletableSource source;

   public CompletableHide(CompletableSource var1) {
      this.source = var1;
   }

   @Override
   protected void subscribeActual(CompletableObserver var1) {
      this.source.subscribe(new CompletableHide.HideCompletableObserver(var1));
   }

   static final class HideCompletableObserver implements CompletableObserver, Disposable {
      final CompletableObserver downstream;
      Disposable upstream;

      HideCompletableObserver(CompletableObserver var1) {
         this.downstream = var1;
      }

      @Override
      public void dispose() {
         this.upstream.dispose();
         this.upstream = DisposableHelper.DISPOSED;
      }

      @Override
      public boolean isDisposed() {
         return this.upstream.isDisposed();
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
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
         }
      }
   }
}
