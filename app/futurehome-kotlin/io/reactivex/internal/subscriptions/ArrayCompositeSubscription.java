package io.reactivex.internal.subscriptions;

import io.reactivex.disposables.Disposable;
import java.util.concurrent.atomic.AtomicReferenceArray;
import org.reactivestreams.Subscription;

public final class ArrayCompositeSubscription extends AtomicReferenceArray<Subscription> implements Disposable {
   private static final long serialVersionUID = 2746389416410565408L;

   public ArrayCompositeSubscription(int var1) {
      super(var1);
   }

   @Override
   public void dispose() {
      int var1 = 0;
      if (this.get(0) != SubscriptionHelper.CANCELLED) {
         for (int var2 = this.length(); var1 < var2; var1++) {
            if (this.get(var1) != SubscriptionHelper.CANCELLED) {
               Subscription var3 = this.getAndSet(var1, SubscriptionHelper.CANCELLED);
               if (var3 != SubscriptionHelper.CANCELLED && var3 != null) {
                  var3.cancel();
               }
            }
         }
      }
   }

   @Override
   public boolean isDisposed() {
      boolean var1 = false;
      if (this.get(0) == SubscriptionHelper.CANCELLED) {
         var1 = true;
      }

      return var1;
   }

   public Subscription replaceResource(int var1, Subscription var2) {
      Subscription var3;
      do {
         var3 = this.get(var1);
         if (var3 == SubscriptionHelper.CANCELLED) {
            if (var2 != null) {
               var2.cancel();
            }

            return null;
         }
      } while (!this.compareAndSet(var1, var3, var2));

      return var3;
   }

   public boolean setResource(int var1, Subscription var2) {
      Subscription var3;
      do {
         var3 = this.get(var1);
         if (var3 == SubscriptionHelper.CANCELLED) {
            if (var2 != null) {
               var2.cancel();
            }

            return false;
         }
      } while (!this.compareAndSet(var1, var3, var2));

      if (var3 != null) {
         var3.cancel();
      }

      return true;
   }
}
