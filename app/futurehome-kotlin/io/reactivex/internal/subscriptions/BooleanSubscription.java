package io.reactivex.internal.subscriptions;

import java.util.concurrent.atomic.AtomicBoolean;
import org.reactivestreams.Subscription;

public final class BooleanSubscription extends AtomicBoolean implements Subscription {
   private static final long serialVersionUID = -8127758972444290902L;

   public void cancel() {
      this.lazySet(true);
   }

   public boolean isCancelled() {
      return this.get();
   }

   public void request(long var1) {
      SubscriptionHelper.validate(var1);
   }

   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("BooleanSubscription(cancelled=");
      var1.append(this.get());
      var1.append(")");
      return var1.toString();
   }
}
