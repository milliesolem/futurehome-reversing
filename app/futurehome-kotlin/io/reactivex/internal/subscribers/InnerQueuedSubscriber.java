package io.reactivex.internal.subscribers;

import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.QueueDrainHelper;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;

public final class InnerQueuedSubscriber<T> extends AtomicReference<Subscription> implements FlowableSubscriber<T>, Subscription {
   private static final long serialVersionUID = 22876611072430776L;
   volatile boolean done;
   int fusionMode;
   final int limit;
   final InnerQueuedSubscriberSupport<T> parent;
   final int prefetch;
   long produced;
   volatile SimpleQueue<T> queue;

   public InnerQueuedSubscriber(InnerQueuedSubscriberSupport<T> var1, int var2) {
      this.parent = var1;
      this.prefetch = var2;
      this.limit = var2 - (var2 >> 2);
   }

   public void cancel() {
      SubscriptionHelper.cancel(this);
   }

   public boolean isDone() {
      return this.done;
   }

   public void onComplete() {
      this.parent.innerComplete(this);
   }

   public void onError(Throwable var1) {
      this.parent.innerError(this, var1);
   }

   public void onNext(T var1) {
      if (this.fusionMode == 0) {
         this.parent.innerNext(this, (T)var1);
      } else {
         this.parent.drain();
      }
   }

   @Override
   public void onSubscribe(Subscription var1) {
      if (SubscriptionHelper.setOnce(this, var1)) {
         if (var1 instanceof QueueSubscription) {
            QueueSubscription var3 = (QueueSubscription)var1;
            int var2 = var3.requestFusion(3);
            if (var2 == 1) {
               this.fusionMode = var2;
               this.queue = var3;
               this.done = true;
               this.parent.innerComplete(this);
               return;
            }

            if (var2 == 2) {
               this.fusionMode = var2;
               this.queue = var3;
               QueueDrainHelper.request(var1, this.prefetch);
               return;
            }
         }

         this.queue = QueueDrainHelper.createQueue(this.prefetch);
         QueueDrainHelper.request(var1, this.prefetch);
      }
   }

   public SimpleQueue<T> queue() {
      return this.queue;
   }

   public void request(long var1) {
      if (this.fusionMode != 1) {
         var1 = this.produced + var1;
         if (var1 >= this.limit) {
            this.produced = 0L;
            this.get().request(var1);
         } else {
            this.produced = var1;
         }
      }
   }

   public void requestOne() {
      if (this.fusionMode != 1) {
         long var1 = this.produced + 1L;
         if (var1 == this.limit) {
            this.produced = 0L;
            this.get().request(var1);
         } else {
            this.produced = var1;
         }
      }
   }

   public void setDone() {
      this.done = true;
   }
}
