package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.observers.BasicQueueDisposable;

public final class ObservableFromArray<T> extends Observable<T> {
   final T[] array;

   public ObservableFromArray(T[] var1) {
      this.array = (T[])var1;
   }

   @Override
   public void subscribeActual(Observer<? super T> var1) {
      ObservableFromArray.FromArrayDisposable var2 = new ObservableFromArray.FromArrayDisposable(var1, this.array);
      var1.onSubscribe(var2);
      if (!var2.fusionMode) {
         var2.run();
      }
   }

   static final class FromArrayDisposable<T> extends BasicQueueDisposable<T> {
      final T[] array;
      volatile boolean disposed;
      final Observer<? super T> downstream;
      boolean fusionMode;
      int index;

      FromArrayDisposable(Observer<? super T> var1, T[] var2) {
         this.downstream = var1;
         this.array = (T[])var2;
      }

      @Override
      public void clear() {
         this.index = this.array.length;
      }

      @Override
      public void dispose() {
         this.disposed = true;
      }

      @Override
      public boolean isDisposed() {
         return this.disposed;
      }

      @Override
      public boolean isEmpty() {
         boolean var1;
         if (this.index == this.array.length) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      @Override
      public T poll() {
         int var1 = this.index;
         Object[] var2 = this.array;
         if (var1 != var2.length) {
            this.index = var1 + 1;
            return ObjectHelper.requireNonNull((T)var2[var1], "The array element is null");
         } else {
            return null;
         }
      }

      @Override
      public int requestFusion(int var1) {
         if ((var1 & 1) != 0) {
            this.fusionMode = true;
            return 1;
         } else {
            return 0;
         }
      }

      void run() {
         Object[] var4 = this.array;
         int var2 = var4.length;

         for (int var1 = 0; var1 < var2 && !this.isDisposed(); var1++) {
            StringBuilder var3 = (StringBuilder)var4[var1];
            if (var3 == null) {
               Observer var6 = this.downstream;
               var3 = new StringBuilder("The element at index ");
               var3.append(var1);
               var3.append(" is null");
               var6.onError(new NullPointerException(var3.toString()));
               return;
            }

            this.downstream.onNext((T)var3);
         }

         if (!this.isDisposed()) {
            this.downstream.onComplete();
         }
      }
   }
}
