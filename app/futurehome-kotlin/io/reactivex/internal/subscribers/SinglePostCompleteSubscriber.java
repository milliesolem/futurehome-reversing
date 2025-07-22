package io.reactivex.internal.subscribers;

import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public abstract class SinglePostCompleteSubscriber<T, R> extends AtomicLong implements FlowableSubscriber<T>, Subscription {
   static final long COMPLETE_MASK = Long.MIN_VALUE;
   static final long REQUEST_MASK = Long.MAX_VALUE;
   private static final long serialVersionUID = 7917814472626990048L;
   protected final Subscriber<? super R> downstream;
   protected long produced;
   protected Subscription upstream;
   protected R value;

   public SinglePostCompleteSubscriber(Subscriber<? super R> var1) {
      this.downstream = var1;
   }

   public void cancel() {
      this.upstream.cancel();
   }

   protected final void complete(R var1) {
      long var2 = this.produced;
      if (var2 != 0L) {
         BackpressureHelper.produced(this, var2);
      }

      while (true) {
         var2 = this.get();
         if ((var2 & Long.MIN_VALUE) != 0L) {
            this.onDrop((R)var1);
            return;
         }

         if ((var2 & Long.MAX_VALUE) != 0L) {
            this.lazySet(-9223372036854775807L);
            this.downstream.onNext(var1);
            this.downstream.onComplete();
            return;
         }

         this.value = (R)var1;
         if (this.compareAndSet(0L, Long.MIN_VALUE)) {
            return;
         }

         this.value = null;
      }
   }

   protected void onDrop(R var1) {
   }

   @Override
   public void onSubscribe(Subscription var1) {
      if (SubscriptionHelper.validate(this.upstream, var1)) {
         this.upstream = var1;
         this.downstream.onSubscribe(this);
      }
   }

   public final void request(long var1) {
      if (SubscriptionHelper.validate(var1)) {
         while (true) {
            long var3 = this.get();
            if ((var3 & Long.MIN_VALUE) != 0L) {
               if (this.compareAndSet(Long.MIN_VALUE, -9223372036854775807L)) {
                  this.downstream.onNext(this.value);
                  this.downstream.onComplete();
               }
               break;
            }

            if (this.compareAndSet(var3, BackpressureHelper.addCap(var3, var1))) {
               this.upstream.request(var1);
               break;
            }
         }
      }
   }
}
