package io.reactivex.internal.observers;

import io.reactivex.Observer;
import io.reactivex.plugins.RxJavaPlugins;

public class DeferredScalarDisposable<T> extends BasicIntQueueDisposable<T> {
   static final int DISPOSED = 4;
   static final int FUSED_CONSUMED = 32;
   static final int FUSED_EMPTY = 8;
   static final int FUSED_READY = 16;
   static final int TERMINATED = 2;
   private static final long serialVersionUID = -5502432239815349361L;
   protected final Observer<? super T> downstream;
   protected T value;

   public DeferredScalarDisposable(Observer<? super T> var1) {
      this.downstream = var1;
   }

   @Override
   public final void clear() {
      this.lazySet(32);
      this.value = null;
   }

   public final void complete() {
      if ((this.get() & 54) == 0) {
         this.lazySet(2);
         this.downstream.onComplete();
      }
   }

   public final void complete(T var1) {
      int var2 = this.get();
      if ((var2 & 54) == 0) {
         Observer var3 = this.downstream;
         if (var2 == 8) {
            this.value = (T)var1;
            this.lazySet(16);
            var3.onNext(null);
         } else {
            this.lazySet(2);
            var3.onNext(var1);
         }

         if (this.get() != 4) {
            var3.onComplete();
         }
      }
   }

   @Override
   public void dispose() {
      this.set(4);
      this.value = null;
   }

   public final void error(Throwable var1) {
      if ((this.get() & 54) != 0) {
         RxJavaPlugins.onError(var1);
      } else {
         this.lazySet(2);
         this.downstream.onError(var1);
      }
   }

   @Override
   public final boolean isDisposed() {
      boolean var1;
      if (this.get() == 4) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public final boolean isEmpty() {
      boolean var1;
      if (this.get() != 16) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public final T poll() throws Exception {
      if (this.get() == 16) {
         Object var1 = this.value;
         this.value = null;
         this.lazySet(32);
         return (T)var1;
      } else {
         return null;
      }
   }

   @Override
   public final int requestFusion(int var1) {
      if ((var1 & 2) != 0) {
         this.lazySet(8);
         return 2;
      } else {
         return 0;
      }
   }

   public final boolean tryDispose() {
      boolean var1;
      if (this.getAndSet(4) != 4) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }
}
