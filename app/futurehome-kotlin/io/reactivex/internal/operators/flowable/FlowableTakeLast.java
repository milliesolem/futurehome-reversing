package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import java.util.ArrayDeque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableTakeLast<T> extends AbstractFlowableWithUpstream<T, T> {
   final int count;

   public FlowableTakeLast(Flowable<T> var1, int var2) {
      super(var1);
      this.count = var2;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      this.source.subscribe(new FlowableTakeLast.TakeLastSubscriber<>(var1, this.count));
   }

   static final class TakeLastSubscriber<T> extends ArrayDeque<T> implements FlowableSubscriber<T>, Subscription {
      private static final long serialVersionUID = 7240042530241604978L;
      volatile boolean cancelled;
      final int count;
      volatile boolean done;
      final Subscriber<? super T> downstream;
      final AtomicLong requested = new AtomicLong();
      Subscription upstream;
      final AtomicInteger wip = new AtomicInteger();

      TakeLastSubscriber(Subscriber<? super T> var1, int var2) {
         this.downstream = var1;
         this.count = var2;
      }

      public void cancel() {
         this.cancelled = true;
         this.upstream.cancel();
      }

      void drain() {
         if (this.wip.getAndIncrement() == 0) {
            Subscriber var8 = this.downstream;
            long var1 = this.requested.get();

            do {
               if (this.cancelled) {
                  return;
               }

               long var5 = var1;
               if (this.done) {
                  long var3;
                  for (var3 = 0L; var3 != var1; var3++) {
                     if (this.cancelled) {
                        return;
                     }

                     Object var7 = this.poll();
                     if (var7 == null) {
                        var8.onComplete();
                        return;
                     }

                     var8.onNext(var7);
                  }

                  var5 = var1;
                  if (var3 != 0L) {
                     var5 = var1;
                     if (var1 != Long.MAX_VALUE) {
                        var5 = this.requested.addAndGet(-var3);
                     }
                  }
               }

               var1 = var5;
            } while (this.wip.decrementAndGet() != 0);
         }
      }

      public void onComplete() {
         this.done = true;
         this.drain();
      }

      public void onError(Throwable var1) {
         this.downstream.onError(var1);
      }

      public void onNext(T var1) {
         if (this.count == this.size()) {
            this.poll();
         }

         this.offer((T)var1);
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
            var1.request(Long.MAX_VALUE);
         }
      }

      public void request(long var1) {
         if (SubscriptionHelper.validate(var1)) {
            BackpressureHelper.add(this.requested, var1);
            this.drain();
         }
      }
   }
}
