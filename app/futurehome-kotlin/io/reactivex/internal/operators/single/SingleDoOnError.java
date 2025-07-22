package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Consumer;

public final class SingleDoOnError<T> extends Single<T> {
   final Consumer<? super Throwable> onError;
   final SingleSource<T> source;

   public SingleDoOnError(SingleSource<T> var1, Consumer<? super Throwable> var2) {
      this.source = var1;
      this.onError = var2;
   }

   @Override
   protected void subscribeActual(SingleObserver<? super T> var1) {
      this.source.subscribe(new SingleDoOnError.DoOnError(this, var1));
   }

   final class DoOnError implements SingleObserver<T> {
      private final SingleObserver<? super T> downstream;
      final SingleDoOnError this$0;

      DoOnError(SingleObserver<? super T> var1, SingleObserver var2) {
         this.this$0 = var1;
         this.downstream = var2;
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onError(Throwable var1) {
         label17:
         try {
            this.this$0.onError.accept((Throwable)var1);
         } catch (Throwable var4) {
            Exceptions.throwIfFatal(var4);
            var1 = new CompositeException((Throwable)var1, var4);
            break label17;
         }

         this.downstream.onError((Throwable)var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         this.downstream.onSubscribe(var1);
      }

      @Override
      public void onSuccess(T var1) {
         this.downstream.onSuccess((T)var1);
      }
   }
}
