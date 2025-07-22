package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.SequentialDisposable;
import java.util.concurrent.atomic.AtomicInteger;

public final class CompletableConcatArray extends Completable {
   final CompletableSource[] sources;

   public CompletableConcatArray(CompletableSource[] var1) {
      this.sources = var1;
   }

   @Override
   public void subscribeActual(CompletableObserver var1) {
      CompletableConcatArray.ConcatInnerObserver var2 = new CompletableConcatArray.ConcatInnerObserver(var1, this.sources);
      var1.onSubscribe(var2.sd);
      var2.next();
   }

   static final class ConcatInnerObserver extends AtomicInteger implements CompletableObserver {
      private static final long serialVersionUID = -7965400327305809232L;
      final CompletableObserver downstream;
      int index;
      final SequentialDisposable sd;
      final CompletableSource[] sources;

      ConcatInnerObserver(CompletableObserver var1, CompletableSource[] var2) {
         this.downstream = var1;
         this.sources = var2;
         this.sd = new SequentialDisposable();
      }

      void next() {
         if (!this.sd.isDisposed()) {
            if (this.getAndIncrement() == 0) {
               CompletableSource[] var2 = this.sources;

               while (!this.sd.isDisposed()) {
                  int var1 = this.index++;
                  if (var1 == var2.length) {
                     this.downstream.onComplete();
                     return;
                  }

                  var2[var1].subscribe(this);
                  if (this.decrementAndGet() == 0) {
                     return;
                  }
               }
            }
         }
      }

      @Override
      public void onComplete() {
         this.next();
      }

      @Override
      public void onError(Throwable var1) {
         this.downstream.onError(var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         this.sd.replace(var1);
      }
   }
}
