package io.reactivex.internal.subscriptions;

import io.reactivex.internal.fuseable.QueueSubscription;
import java.util.concurrent.atomic.AtomicInteger;
import org.reactivestreams.Subscriber;

public final class ScalarSubscription<T> extends AtomicInteger implements QueueSubscription<T> {
   static final int CANCELLED = 2;
   static final int NO_REQUEST = 0;
   static final int REQUESTED = 1;
   private static final long serialVersionUID = -3830916580126663321L;
   final Subscriber<? super T> subscriber;
   final T value;

   public ScalarSubscription(Subscriber<? super T> var1, T var2) {
      this.subscriber = var1;
      this.value = (T)var2;
   }

   public void cancel() {
      this.lazySet(2);
   }

   @Override
   public void clear() {
      this.lazySet(1);
   }

   public boolean isCancelled() {
      boolean var1;
      if (this.get() == 2) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public boolean isEmpty() {
      boolean var1;
      if (this.get() != 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public boolean offer(T var1) {
      throw new UnsupportedOperationException("Should not be called!");
   }

   @Override
   public boolean offer(T var1, T var2) {
      throw new UnsupportedOperationException("Should not be called!");
   }

   @Override
   public T poll() {
      if (this.get() == 0) {
         this.lazySet(1);
         return this.value;
      } else {
         return null;
      }
   }

   public void request(long var1) {
      if (SubscriptionHelper.validate(var1)) {
         if (this.compareAndSet(0, 1)) {
            Subscriber var3 = this.subscriber;
            var3.onNext(this.value);
            if (this.get() != 2) {
               var3.onComplete();
            }
         }
      }
   }

   @Override
   public int requestFusion(int var1) {
      return var1 & 1;
   }
}
