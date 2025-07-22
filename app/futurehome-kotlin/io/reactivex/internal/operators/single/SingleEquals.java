package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;

public final class SingleEquals<T> extends Single<Boolean> {
   final SingleSource<? extends T> first;
   final SingleSource<? extends T> second;

   public SingleEquals(SingleSource<? extends T> var1, SingleSource<? extends T> var2) {
      this.first = var1;
      this.second = var2;
   }

   @Override
   protected void subscribeActual(SingleObserver<? super Boolean> var1) {
      AtomicInteger var2 = new AtomicInteger();
      Object[] var4 = new Object[]{null, null};
      CompositeDisposable var3 = new CompositeDisposable();
      var1.onSubscribe(var3);
      this.first.subscribe(new SingleEquals.InnerObserver<>(0, var3, var4, var1, var2));
      this.second.subscribe(new SingleEquals.InnerObserver<>(1, var3, var4, var1, var2));
   }

   static class InnerObserver<T> implements SingleObserver<T> {
      final AtomicInteger count;
      final SingleObserver<? super Boolean> downstream;
      final int index;
      final CompositeDisposable set;
      final Object[] values;

      InnerObserver(int var1, CompositeDisposable var2, Object[] var3, SingleObserver<? super Boolean> var4, AtomicInteger var5) {
         this.index = var1;
         this.set = var2;
         this.values = var3;
         this.downstream = var4;
         this.count = var5;
      }

      @Override
      public void onError(Throwable var1) {
         int var2;
         do {
            var2 = this.count.get();
            if (var2 >= 2) {
               RxJavaPlugins.onError(var1);
               return;
            }
         } while (!this.count.compareAndSet(var2, 2));

         this.set.dispose();
         this.downstream.onError(var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         this.set.add(var1);
      }

      @Override
      public void onSuccess(T var1) {
         this.values[this.index] = var1;
         if (this.count.incrementAndGet() == 2) {
            SingleObserver var2 = this.downstream;
            var1 = this.values;
            var2.onSuccess(ObjectHelper.equals(var1[0], var1[1]));
         }
      }
   }
}
