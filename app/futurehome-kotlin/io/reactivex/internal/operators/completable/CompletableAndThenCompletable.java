package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class CompletableAndThenCompletable extends Completable {
   final CompletableSource next;
   final CompletableSource source;

   public CompletableAndThenCompletable(CompletableSource var1, CompletableSource var2) {
      this.source = var1;
      this.next = var2;
   }

   @Override
   protected void subscribeActual(CompletableObserver var1) {
      this.source.subscribe(new CompletableAndThenCompletable.SourceObserver(var1, this.next));
   }

   static final class NextObserver implements CompletableObserver {
      final CompletableObserver downstream;
      final AtomicReference<Disposable> parent;

      NextObserver(AtomicReference<Disposable> var1, CompletableObserver var2) {
         this.parent = var1;
         this.downstream = var2;
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
         DisposableHelper.replace(this.parent, var1);
      }
   }

   static final class SourceObserver extends AtomicReference<Disposable> implements CompletableObserver, Disposable {
      private static final long serialVersionUID = -4101678820158072998L;
      final CompletableObserver actualObserver;
      final CompletableSource next;

      SourceObserver(CompletableObserver var1, CompletableSource var2) {
         this.actualObserver = var1;
         this.next = var2;
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
         this.next.subscribe(new CompletableAndThenCompletable.NextObserver(this, this.actualObserver));
      }

      @Override
      public void onError(Throwable var1) {
         this.actualObserver.onError(var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.setOnce(this, var1)) {
            this.actualObserver.onSubscribe(this);
         }
      }
   }
}
