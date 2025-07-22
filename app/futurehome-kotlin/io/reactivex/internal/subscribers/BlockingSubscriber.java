package io.reactivex.internal.subscribers;

import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.NotificationLite;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;

public final class BlockingSubscriber<T> extends AtomicReference<Subscription> implements FlowableSubscriber<T>, Subscription {
   public static final Object TERMINATED = new Object();
   private static final long serialVersionUID = -4875965440900746268L;
   final Queue<Object> queue;

   public BlockingSubscriber(Queue<Object> var1) {
      this.queue = var1;
   }

   public void cancel() {
      if (SubscriptionHelper.cancel(this)) {
         this.queue.offer(TERMINATED);
      }
   }

   public boolean isCancelled() {
      boolean var1;
      if (this.get() == SubscriptionHelper.CANCELLED) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void onComplete() {
      this.queue.offer(NotificationLite.complete());
   }

   public void onError(Throwable var1) {
      this.queue.offer(NotificationLite.error(var1));
   }

   public void onNext(T var1) {
      this.queue.offer(NotificationLite.next(var1));
   }

   @Override
   public void onSubscribe(Subscription var1) {
      if (SubscriptionHelper.setOnce(this, var1)) {
         this.queue.offer(NotificationLite.subscription(this));
      }
   }

   public void request(long var1) {
      this.get().request(var1);
   }
}
