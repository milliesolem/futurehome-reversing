package io.reactivex.internal.subscribers;

import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.HalfSerializer;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class StrictSubscriber<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
   private static final long serialVersionUID = -4945028590049415624L;
   volatile boolean done;
   final Subscriber<? super T> downstream;
   final AtomicThrowable error;
   final AtomicBoolean once;
   final AtomicLong requested;
   final AtomicReference<Subscription> upstream;

   public StrictSubscriber(Subscriber<? super T> var1) {
      this.downstream = var1;
      this.error = new AtomicThrowable();
      this.requested = new AtomicLong();
      this.upstream = new AtomicReference<>();
      this.once = new AtomicBoolean();
   }

   public void cancel() {
      if (!this.done) {
         SubscriptionHelper.cancel(this.upstream);
      }
   }

   public void onComplete() {
      this.done = true;
      HalfSerializer.onComplete(this.downstream, this, this.error);
   }

   public void onError(Throwable var1) {
      this.done = true;
      HalfSerializer.onError(this.downstream, var1, this, this.error);
   }

   public void onNext(T var1) {
      HalfSerializer.onNext(this.downstream, var1, this, this.error);
   }

   @Override
   public void onSubscribe(Subscription var1) {
      if (this.once.compareAndSet(false, true)) {
         this.downstream.onSubscribe(this);
         SubscriptionHelper.deferredSetOnce(this.upstream, this.requested, var1);
      } else {
         var1.cancel();
         this.cancel();
         this.onError(new IllegalStateException("§2.12 violated: onSubscribe must be called at most once"));
      }
   }

   public void request(long var1) {
      if (var1 <= 0L) {
         this.cancel();
         StringBuilder var3 = new StringBuilder("§3.9 violated: positive request amount required but it was ");
         var3.append(var1);
         this.onError(new IllegalArgumentException(var3.toString()));
      } else {
         SubscriptionHelper.deferredRequest(this.upstream, this.requested, var1);
      }
   }
}
