package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Predicate;

public final class CompletableOnErrorComplete extends Completable {
   final Predicate<? super Throwable> predicate;
   final CompletableSource source;

   public CompletableOnErrorComplete(CompletableSource var1, Predicate<? super Throwable> var2) {
      this.source = var1;
      this.predicate = var2;
   }

   @Override
   protected void subscribeActual(CompletableObserver var1) {
      this.source.subscribe(new CompletableOnErrorComplete.OnError(this, var1));
   }

   final class OnError implements CompletableObserver {
      private final CompletableObserver downstream;
      final CompletableOnErrorComplete this$0;

      OnError(CompletableOnErrorComplete var1, CompletableObserver var2) {
         this.this$0 = var1;
         this.downstream = var2;
      }

      @Override
      public void onComplete() {
         this.downstream.onComplete();
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onError(Throwable var1) {
         boolean var2;
         try {
            var2 = this.this$0.predicate.test(var1);
         } catch (Throwable var5) {
            Exceptions.throwIfFatal(var5);
            this.downstream.onError(new CompositeException(var1, var5));
            return;
         }

         if (var2) {
            this.downstream.onComplete();
         } else {
            this.downstream.onError(var1);
         }
      }

      @Override
      public void onSubscribe(Disposable var1) {
         this.downstream.onSubscribe(var1);
      }
   }
}
