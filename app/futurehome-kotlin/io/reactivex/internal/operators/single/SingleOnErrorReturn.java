package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;

public final class SingleOnErrorReturn<T> extends Single<T> {
   final SingleSource<? extends T> source;
   final T value;
   final Function<? super Throwable, ? extends T> valueSupplier;

   public SingleOnErrorReturn(SingleSource<? extends T> var1, Function<? super Throwable, ? extends T> var2, T var3) {
      this.source = var1;
      this.valueSupplier = var2;
      this.value = (T)var3;
   }

   @Override
   protected void subscribeActual(SingleObserver<? super T> var1) {
      this.source.subscribe(new SingleOnErrorReturn.OnErrorReturn(this, var1));
   }

   final class OnErrorReturn implements SingleObserver<T> {
      private final SingleObserver<? super T> observer;
      final SingleOnErrorReturn this$0;

      OnErrorReturn(SingleObserver<? super T> var1, SingleObserver var2) {
         this.this$0 = var1;
         this.observer = var2;
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onError(Throwable var1) {
         Object var2;
         if (this.this$0.valueSupplier != null) {
            try {
               var2 = this.this$0.valueSupplier.apply(var1);
            } catch (Throwable var4) {
               Exceptions.throwIfFatal(var4);
               this.observer.onError(new CompositeException(var1, var4));
               return;
            }
         } else {
            var2 = this.this$0.value;
         }

         if (var2 == null) {
            var2 = new NullPointerException("Value supplied was null");
            var2.initCause(var1);
            this.observer.onError(var2);
         } else {
            this.observer.onSuccess((T)var2);
         }
      }

      @Override
      public void onSubscribe(Disposable var1) {
         this.observer.onSubscribe(var1);
      }

      @Override
      public void onSuccess(T var1) {
         this.observer.onSuccess((T)var1);
      }
   }
}
