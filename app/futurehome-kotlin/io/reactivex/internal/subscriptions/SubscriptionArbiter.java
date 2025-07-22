package io.reactivex.internal.subscriptions;

import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.BackpressureHelper;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;

public class SubscriptionArbiter extends AtomicInteger implements Subscription {
   private static final long serialVersionUID = -2189523197179400958L;
   Subscription actual;
   final boolean cancelOnReplace;
   volatile boolean cancelled;
   final AtomicLong missedProduced;
   final AtomicLong missedRequested;
   final AtomicReference<Subscription> missedSubscription;
   long requested;
   protected boolean unbounded;

   public SubscriptionArbiter(boolean var1) {
      this.cancelOnReplace = var1;
      this.missedSubscription = new AtomicReference<>();
      this.missedRequested = new AtomicLong();
      this.missedProduced = new AtomicLong();
   }

   public void cancel() {
      if (!this.cancelled) {
         this.cancelled = true;
         this.drain();
      }
   }

   final void drain() {
      if (this.getAndIncrement() == 0) {
         this.drainLoop();
      }
   }

   final void drainLoop() {
      int var1 = 1;
      long var5 = 0L;
      Subscription var15 = null;

      int var2;
      long var18;
      Subscription var23;
      do {
         var23 = this.missedSubscription.get();
         Subscription var13 = var23;
         if (var23 != null) {
            var13 = this.missedSubscription.getAndSet(null);
         }

         var18 = this.missedRequested.get();
         long var7 = var18;
         if (var18 != 0L) {
            var7 = this.missedRequested.getAndSet(0L);
         }

         var18 = this.missedProduced.get();
         long var9 = var18;
         if (var18 != 0L) {
            var9 = this.missedProduced.getAndSet(0L);
         }

         Subscription var16 = this.actual;
         if (this.cancelled) {
            if (var16 != null) {
               var16.cancel();
               this.actual = null;
            }

            var18 = var5;
            var23 = var15;
            if (var13 != null) {
               var13.cancel();
               var18 = var5;
               var23 = var15;
            }
         } else {
            var18 = this.requested;
            long var11 = var18;
            if (var18 != Long.MAX_VALUE) {
               var11 = BackpressureHelper.addCap(var18, var7);
               var18 = var11;
               if (var11 != Long.MAX_VALUE) {
                  var9 = var11 - var9;
                  var18 = var9;
                  if (var9 < 0L) {
                     SubscriptionHelper.reportMoreProduced(var9);
                     var18 = 0L;
                  }
               }

               this.requested = var18;
               var11 = var18;
            }

            if (var13 != null) {
               if (var16 != null && this.cancelOnReplace) {
                  var16.cancel();
               }

               this.actual = var13;
               var18 = var5;
               var23 = var15;
               if (var11 != 0L) {
                  var18 = BackpressureHelper.addCap(var5, var11);
                  var23 = var13;
               }
            } else {
               var18 = var5;
               var23 = var15;
               if (var16 != null) {
                  var18 = var5;
                  var23 = var15;
                  if (var7 != 0L) {
                     var18 = BackpressureHelper.addCap(var5, var7);
                     var23 = var16;
                  }
               }
            }
         }

         var2 = this.addAndGet(-var1);
         var1 = var2;
         var5 = var18;
         var15 = var23;
      } while (var2 != 0);

      if (var18 != 0L) {
         var23.request(var18);
      }
   }

   public final boolean isCancelled() {
      return this.cancelled;
   }

   public final boolean isUnbounded() {
      return this.unbounded;
   }

   public final void produced(long var1) {
      if (!this.unbounded) {
         if (this.get() == 0 && this.compareAndSet(0, 1)) {
            long var3 = this.requested;
            if (var3 != Long.MAX_VALUE) {
               var3 -= var1;
               var1 = var3;
               if (var3 < 0L) {
                  SubscriptionHelper.reportMoreProduced(var3);
                  var1 = 0L;
               }

               this.requested = var1;
            }

            if (this.decrementAndGet() != 0) {
               this.drainLoop();
            }
         } else {
            BackpressureHelper.add(this.missedProduced, var1);
            this.drain();
         }
      }
   }

   public final void request(long var1) {
      if (SubscriptionHelper.validate(var1)) {
         if (this.unbounded) {
            return;
         }

         if (this.get() == 0 && this.compareAndSet(0, 1)) {
            long var3 = this.requested;
            if (var3 != Long.MAX_VALUE) {
               var3 = BackpressureHelper.addCap(var3, var1);
               this.requested = var3;
               if (var3 == Long.MAX_VALUE) {
                  this.unbounded = true;
               }
            }

            Subscription var5 = this.actual;
            if (this.decrementAndGet() != 0) {
               this.drainLoop();
            }

            if (var5 != null) {
               var5.request(var1);
            }

            return;
         }

         BackpressureHelper.add(this.missedRequested, var1);
         this.drain();
      }
   }

   public final void setSubscription(Subscription var1) {
      if (this.cancelled) {
         var1.cancel();
      } else {
         ObjectHelper.requireNonNull(var1, "s is null");
         if (this.get() == 0 && this.compareAndSet(0, 1)) {
            Subscription var4 = this.actual;
            if (var4 != null && this.cancelOnReplace) {
               var4.cancel();
            }

            this.actual = var1;
            long var2 = this.requested;
            if (this.decrementAndGet() != 0) {
               this.drainLoop();
            }

            if (var2 != 0L) {
               var1.request(var2);
            }
         } else {
            var1 = this.missedSubscription.getAndSet(var1);
            if (var1 != null && this.cancelOnReplace) {
               var1.cancel();
            }

            this.drain();
         }
      }
   }
}
