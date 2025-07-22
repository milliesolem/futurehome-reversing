package io.reactivex.internal.operators.maybe;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import java.util.concurrent.atomic.AtomicReference;

public final class MaybeCache<T> extends Maybe<T> implements MaybeObserver<T> {
   static final MaybeCache.CacheDisposable[] EMPTY = new MaybeCache.CacheDisposable[0];
   static final MaybeCache.CacheDisposable[] TERMINATED = new MaybeCache.CacheDisposable[0];
   Throwable error;
   final AtomicReference<MaybeCache.CacheDisposable<T>[]> observers;
   final AtomicReference<MaybeSource<T>> source;
   T value;

   public MaybeCache(MaybeSource<T> var1) {
      this.source = new AtomicReference<>(var1);
      this.observers = new AtomicReference<>(EMPTY);
   }

   boolean add(MaybeCache.CacheDisposable<T> var1) {
      MaybeCache.CacheDisposable[] var3;
      MaybeCache.CacheDisposable[] var4;
      do {
         var3 = this.observers.get();
         if (var3 == TERMINATED) {
            return false;
         }

         int var2 = var3.length;
         var4 = new MaybeCache.CacheDisposable[var2 + 1];
         System.arraycopy(var3, 0, var4, 0, var2);
         var4[var2] = var1;
      } while (!ExternalSyntheticBackportWithForwarding0.m(this.observers, var3, var4));

      return true;
   }

   @Override
   public void onComplete() {
      for (MaybeCache.CacheDisposable var4 : this.observers.getAndSet(TERMINATED)) {
         if (!var4.isDisposed()) {
            var4.downstream.onComplete();
         }
      }
   }

   @Override
   public void onError(Throwable var1) {
      this.error = var1;

      for (MaybeCache.CacheDisposable var4 : this.observers.getAndSet(TERMINATED)) {
         if (!var4.isDisposed()) {
            var4.downstream.onError(var1);
         }
      }
   }

   @Override
   public void onSubscribe(Disposable var1) {
   }

   @Override
   public void onSuccess(T var1) {
      this.value = (T)var1;

      for (MaybeCache.CacheDisposable var5 : this.observers.getAndSet(TERMINATED)) {
         if (!var5.isDisposed()) {
            var5.downstream.onSuccess((T)var1);
         }
      }
   }

   void remove(MaybeCache.CacheDisposable<T> var1) {
      MaybeCache.CacheDisposable[] var4;
      MaybeCache.CacheDisposable[] var5;
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
            var4 = new MaybeCache.CacheDisposable[var3 - 1];
            System.arraycopy(var5, 0, var4, 0, var2);
            System.arraycopy(var5, var2 + 1, var4, var2, var3 - var2 - 1);
         }
      } while (!ExternalSyntheticBackportWithForwarding0.m(this.observers, var5, var4));
   }

   @Override
   protected void subscribeActual(MaybeObserver<? super T> var1) {
      MaybeCache.CacheDisposable var2 = new MaybeCache.CacheDisposable<>(var1, this);
      var1.onSubscribe(var2);
      if (this.add(var2)) {
         if (var2.isDisposed()) {
            this.remove(var2);
         } else {
            MaybeSource var3 = this.source.getAndSet(null);
            if (var3 != null) {
               var3.subscribe(this);
            }
         }
      } else {
         if (!var2.isDisposed()) {
            Throwable var4 = this.error;
            if (var4 != null) {
               var1.onError(var4);
            } else {
               var2 = this.value;
               if (var2 != null) {
                  var1.onSuccess(var2);
               } else {
                  var1.onComplete();
               }
            }
         }
      }
   }

   static final class CacheDisposable<T> extends AtomicReference<MaybeCache<T>> implements Disposable {
      private static final long serialVersionUID = -5791853038359966195L;
      final MaybeObserver<? super T> downstream;

      CacheDisposable(MaybeObserver<? super T> var1, MaybeCache<T> var2) {
         super(var2);
         this.downstream = var1;
      }

      @Override
      public void dispose() {
         MaybeCache var1 = this.getAndSet(null);
         if (var1 != null) {
            var1.remove(this);
         }
      }

      @Override
      public boolean isDisposed() {
         boolean var1;
         if (this.get() == null) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }
   }
}
