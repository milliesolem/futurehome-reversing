package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiPredicate;

public final class SingleContains<T> extends Single<Boolean> {
   final BiPredicate<Object, Object> comparer;
   final SingleSource<T> source;
   final Object value;

   public SingleContains(SingleSource<T> var1, Object var2, BiPredicate<Object, Object> var3) {
      this.source = var1;
      this.value = var2;
      this.comparer = var3;
   }

   @Override
   protected void subscribeActual(SingleObserver<? super Boolean> var1) {
      this.source.subscribe(new SingleContains.ContainsSingleObserver(this, var1));
   }

   final class ContainsSingleObserver implements SingleObserver<T> {
      private final SingleObserver<? super Boolean> downstream;
      final SingleContains this$0;

      ContainsSingleObserver(SingleObserver<? super Boolean> var1, SingleObserver var2) {
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
         boolean var2;
         try {
            var2 = this.this$0.comparer.test(var1, this.this$0.value);
         } catch (Throwable var4) {
            Exceptions.throwIfFatal(var4);
            this.downstream.onError(var4);
            return;
         }

         this.downstream.onSuccess(var2);
      }
   }
}
