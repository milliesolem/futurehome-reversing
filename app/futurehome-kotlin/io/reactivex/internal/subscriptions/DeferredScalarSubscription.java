package io.reactivex.internal.subscriptions;

import org.reactivestreams.Subscriber;

public class DeferredScalarSubscription<T> extends BasicIntQueueSubscription<T> {
   static final int CANCELLED = 4;
   static final int FUSED_CONSUMED = 32;
   static final int FUSED_EMPTY = 8;
   static final int FUSED_READY = 16;
   static final int HAS_REQUEST_HAS_VALUE = 3;
   static final int HAS_REQUEST_NO_VALUE = 2;
   static final int NO_REQUEST_HAS_VALUE = 1;
   static final int NO_REQUEST_NO_VALUE = 0;
   private static final long serialVersionUID = -2151279923272604993L;
   protected final Subscriber<? super T> downstream;
   protected T value;

   public DeferredScalarSubscription(Subscriber<? super T> var1) {
      this.downstream = var1;
   }

   public void cancel() {
      this.set(4);
      this.value = null;
   }

   @Override
   public final void clear() {
      this.lazySet(32);
      this.value = null;
   }

   public final void complete(T var1) {
      int var2 = this.get();

      while (var2 != 8) {
         if ((var2 & -3) != 0) {
            return;
         }

         if (var2 == 2) {
            this.lazySet(3);
            Subscriber var4 = this.downstream;
            var4.onNext(var1);
            if (this.get() != 4) {
               var4.onComplete();
            }

            return;
         }

         this.value = (T)var1;
         if (this.compareAndSet(0, 1)) {
            return;
         }

         int var3 = this.get();
         var2 = var3;
         if (var3 == 4) {
            this.value = null;
            return;
         }
      }

      this.value = (T)var1;
      this.lazySet(16);
      Subscriber var5 = this.downstream;
      var5.onNext(var1);
      if (this.get() != 4) {
         var5.onComplete();
      }
   }

   public final boolean isCancelled() {
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
   public final T poll() {
      if (this.get() == 16) {
         this.lazySet(32);
         Object var1 = this.value;
         this.value = null;
         return (T)var1;
      } else {
         return null;
      }
   }

   public final void request(long var1) {
      if (SubscriptionHelper.validate(var1)) {
         do {
            int var3 = this.get();
            if ((var3 & -2) != 0) {
               return;
            }

            if (var3 == 1) {
               if (this.compareAndSet(1, 3)) {
                  Object var5 = this.value;
                  if (var5 != null) {
                     this.value = null;
                     Subscriber var4 = this.downstream;
                     var4.onNext(var5);
                     if (this.get() != 4) {
                        var4.onComplete();
                     }
                  }
               }

               return;
            }
         } while (!this.compareAndSet(0, 2));
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

   public final boolean tryCancel() {
      boolean var1;
      if (this.getAndSet(4) != 4) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }
}
