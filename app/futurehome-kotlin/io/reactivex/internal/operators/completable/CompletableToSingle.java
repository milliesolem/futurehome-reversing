package io.reactivex.internal.operators.completable;

import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import java.util.concurrent.Callable;

public final class CompletableToSingle<T> extends Single<T> {
   final T completionValue;
   final Callable<? extends T> completionValueSupplier;
   final CompletableSource source;

   public CompletableToSingle(CompletableSource var1, Callable<? extends T> var2, T var3) {
      this.source = var1;
      this.completionValue = (T)var3;
      this.completionValueSupplier = var2;
   }

   @Override
   protected void subscribeActual(SingleObserver<? super T> var1) {
      this.source.subscribe(new CompletableToSingle.ToSingle(this, var1));
   }

   final class ToSingle implements CompletableObserver {
      private final SingleObserver<? super T> observer;
      final CompletableToSingle this$0;

      ToSingle(SingleObserver<? super T> var1, SingleObserver var2) {
         this.this$0 = var1;
         this.observer = var2;
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onComplete() {
         Object var1;
         if (this.this$0.completionValueSupplier != null) {
            try {
               var1 = this.this$0.completionValueSupplier.call();
            } catch (Throwable var3) {
               Exceptions.throwIfFatal(var3);
               this.observer.onError(var3);
               return;
            }
         } else {
            var1 = this.this$0.completionValue;
         }

         if (var1 == null) {
            this.observer.onError(new NullPointerException("The value supplied is null"));
         } else {
            this.observer.onSuccess((T)var1);
         }
      }

      @Override
      public void onError(Throwable var1) {
         this.observer.onError(var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         this.observer.onSubscribe(var1);
      }
   }
}
