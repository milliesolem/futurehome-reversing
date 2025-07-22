package io.reactivex.internal.operators.single;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class SingleCache<T> extends Single<T> implements SingleObserver<T> {
   static final SingleCache.CacheDisposable[] EMPTY = new SingleCache.CacheDisposable[0];
   static final SingleCache.CacheDisposable[] TERMINATED = new SingleCache.CacheDisposable[0];
   Throwable error;
   final AtomicReference<SingleCache.CacheDisposable<T>[]> observers;
   final SingleSource<? extends T> source;
   T value;
   final AtomicInteger wip;

   public SingleCache(SingleSource<? extends T> var1) {
      this.source = var1;
      this.wip = new AtomicInteger();
      this.observers = new AtomicReference<>(EMPTY);
   }

   boolean add(SingleCache.CacheDisposable<T> var1) {
      SingleCache.CacheDisposable[] var3;
      SingleCache.CacheDisposable[] var4;
      do {
         var4 = this.observers.get();
         if (var4 == TERMINATED) {
            return false;
         }

         int var2 = var4.length;
         var3 = new SingleCache.CacheDisposable[var2 + 1];
         System.arraycopy(var4, 0, var3, 0, var2);
         var3[var2] = var1;
      } while (!ExternalSyntheticBackportWithForwarding0.m(this.observers, var4, var3));

      return true;
   }

   @Override
   public void onError(Throwable var1) {
      this.error = var1;

      for (SingleCache.CacheDisposable var5 : this.observers.getAndSet(TERMINATED)) {
         if (!var5.isDisposed()) {
            var5.downstream.onError(var1);
         }
      }
   }

   @Override
   public void onSubscribe(Disposable var1) {
   }

   @Override
   public void onSuccess(T var1) {
      this.value = (T)var1;

      for (SingleCache.CacheDisposable var5 : this.observers.getAndSet(TERMINATED)) {
         if (!var5.isDisposed()) {
            var5.downstream.onSuccess((T)var1);
         }
      }
   }

   void remove(SingleCache.CacheDisposable<T> var1) {
      SingleCache.CacheDisposable[] var4;
      SingleCache.CacheDisposable[] var5;
      do {
         var5 = this.observers.get();
         int var3 = var5.length;
         if (var3 == 0) {
            return;
         }

         int var2 = 0;

         while (true) {
            if (var2 >= var3) {
               var2 = -1;
               break;
            }

            if (var5[var2] == var1) {
               break;
            }

            var2++;
         }

         if (var2 < 0) {
            return;
         }

         if (var3 == 1) {
            var4 = EMPTY;
         } else {
            var4 = new SingleCache.CacheDisposable[var3 - 1];
            System.arraycopy(var5, 0, var4, 0, var2);
            System.arraycopy(var5, var2 + 1, var4, var2, var3 - var2 - 1);
         }
      } while (!ExternalSyntheticBackportWithForwarding0.m(this.observers, var5, var4));
   }

   @Override
   protected void subscribeActual(SingleObserver<? super T> var1) {
      SingleCache.CacheDisposable var2 = new SingleCache.CacheDisposable<>(var1, this);
      var1.onSubscribe(var2);
      if (this.add(var2)) {
         if (var2.isDisposed()) {
            this.remove(var2);
         }

         if (this.wip.getAndIncrement() == 0) {
            this.source.subscribe(this);
         }
      } else {
         Throwable var3 = this.error;
         if (var3 != null) {
            var1.onError(var3);
         } else {
            var1.onSuccess(this.value);
         }
      }
   }

   static final class CacheDisposable<T> extends AtomicBoolean implements Disposable {
      private static final long serialVersionUID = 7514387411091976596L;
      final SingleObserver<? super T> downstream;
      final SingleCache<T> parent;

      CacheDisposable(SingleObserver<? super T> var1, SingleCache<T> var2) {
         this.downstream = var1;
         this.parent = var2;
      }

      @Override
      public void dispose() {
         if (this.compareAndSet(false, true)) {
            this.parent.remove(this);
         }
      }

      @Override
      public boolean isDisposed() {
         return this.get();
      }
   }
}
