package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Consumer;

public final class SingleDoOnSuccess<T> extends Single<T> {
   final Consumer<? super T> onSuccess;
   final SingleSource<T> source;

   public SingleDoOnSuccess(SingleSource<T> var1, Consumer<? super T> var2) {
      this.source = var1;
      this.onSuccess = var2;
   }

   @Override
   protected void subscribeActual(SingleObserver<? super T> var1) {
      this.source.subscribe(new SingleDoOnSuccess.DoOnSuccess(this, var1));
   }

   final class DoOnSuccess implements SingleObserver<T> {
      final SingleObserver<? super T> downstream;
      final SingleDoOnSuccess this$0;

      DoOnSuccess(SingleObserver<? super T> var1, SingleObserver var2) {
         this.this$0 = var1;
         this.downstream = var2;
      }

      @Override
      public void onError(Throwable var1) {
         this.downstream.onError(var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         this.downstream.onSubscribe(var1);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onSuccess(T var1) {
         try {
            this.this$0.onSuccess.accept((T)var1);
         } catch (Throwable var3) {
            Exceptions.throwIfFatal(var3);
            this.downstream.onError(var3);
            return;
         }

         this.downstream.onSuccess((T)var1);
      }
   }
}
