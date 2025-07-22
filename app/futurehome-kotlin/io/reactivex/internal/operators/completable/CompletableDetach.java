package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;

public final class CompletableDetach extends Completable {
   final CompletableSource source;

   public CompletableDetach(CompletableSource var1) {
      this.source = var1;
   }

   @Override
   protected void subscribeActual(CompletableObserver var1) {
      this.source.subscribe(new CompletableDetach.DetachCompletableObserver(var1));
   }

   static final class DetachCompletableObserver implements CompletableObserver, Disposable {
      CompletableObserver downstream;
      Disposable upstream;

      DetachCompletableObserver(CompletableObserver var1) {
         this.downstream = var1;
      }

      @Override
      public void dispose() {
         this.downstream = null;
         this.upstream.dispose();
         this.upstream = DisposableHelper.DISPOSED;
      }

      @Override
      public boolean isDisposed() {
         return this.upstream.isDisposed();
      }

      @Override
      public void onComplete() {
         this.upstream = DisposableHelper.DISPOSED;
         CompletableObserver var1 = this.downstream;
         if (var1 != null) {
            this.downstream = null;
            var1.onComplete();
         }
      }

      @Override
      public void onError(Throwable var1) {
         this.upstream = DisposableHelper.DISPOSED;
         CompletableObserver var2 = this.downstream;
         if (var2 != null) {
            this.downstream = null;
            var2.onError(var1);
         }
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
