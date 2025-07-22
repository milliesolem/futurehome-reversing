package io.reactivex.internal.operators.completable;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.disposables.Disposable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public final class CompletableCache extends Completable implements CompletableObserver {
   static final CompletableCache.InnerCompletableCache[] EMPTY = new CompletableCache.InnerCompletableCache[0];
   static final CompletableCache.InnerCompletableCache[] TERMINATED = new CompletableCache.InnerCompletableCache[0];
   Throwable error;
   final AtomicReference<CompletableCache.InnerCompletableCache[]> observers;
   final AtomicBoolean once;
   final CompletableSource source;

   public CompletableCache(CompletableSource var1) {
      this.source = var1;
      this.observers = new AtomicReference<>(EMPTY);
      this.once = new AtomicBoolean();
   }

   boolean add(CompletableCache.InnerCompletableCache var1) {
      CompletableCache.InnerCompletableCache[] var3;
      CompletableCache.InnerCompletableCache[] var4;
      do {
         var4 = this.observers.get();
         if (var4 == TERMINATED) {
            return false;
         }

         int var2 = var4.length;
         var3 = new CompletableCache.InnerCompletableCache[var2 + 1];
         System.arraycopy(var4, 0, var3, 0, var2);
         var3[var2] = var1;
      } while (!ExternalSyntheticBackportWithForwarding0.m(this.observers, var4, var3));

      return true;
   }

   @Override
   public void onComplete() {
      for (CompletableCache.InnerCompletableCache var3 : this.observers.getAndSet(TERMINATED)) {
         if (!var3.get()) {
            var3.downstream.onComplete();
         }
      }
   }

   @Override
   public void onError(Throwable var1) {
      this.error = var1;

      for (CompletableCache.InnerCompletableCache var5 : this.observers.getAndSet(TERMINATED)) {
         if (!var5.get()) {
            var5.downstream.onError(var1);
         }
      }
   }

   @Override
   public void onSubscribe(Disposable var1) {
   }

   void remove(CompletableCache.InnerCompletableCache var1) {
      CompletableCache.InnerCompletableCache[] var4;
      CompletableCache.InnerCompletableCache[] var5;
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
            var4 = new CompletableCache.InnerCompletableCache[var3 - 1];
            System.arraycopy(var5, 0, var4, 0, var2);
            System.arraycopy(var5, var2 + 1, var4, var2, var3 - var2 - 1);
         }
      } while (!ExternalSyntheticBackportWithForwarding0.m(this.observers, var5, var4));
   }

   @Override
   protected void subscribeActual(CompletableObserver var1) {
      CompletableCache.InnerCompletableCache var2 = new CompletableCache.InnerCompletableCache(this, var1);
      var1.onSubscribe(var2);
      if (this.add(var2)) {
         if (var2.isDisposed()) {
            this.remove(var2);
         }

         if (this.once.compareAndSet(false, true)) {
            this.source.subscribe(this);
         }
      } else {
         Throwable var3 = this.error;
         if (var3 != null) {
            var1.onError(var3);
         } else {
            var1.onComplete();
         }
      }
   }

   final class InnerCompletableCache extends AtomicBoolean implements Disposable {
      private static final long serialVersionUID = 8943152917179642732L;
      final CompletableObserver downstream;
      final CompletableCache this$0;

      InnerCompletableCache(CompletableCache var1, CompletableObserver var2) {
         this.this$0 = var1;
         this.downstream = var2;
      }

      @Override
      public void dispose() {
         if (this.compareAndSet(false, true)) {
            this.this$0.remove(this);
         }
      }

      @Override
      public boolean isDisposed() {
         return this.get();
      }
   }
}
