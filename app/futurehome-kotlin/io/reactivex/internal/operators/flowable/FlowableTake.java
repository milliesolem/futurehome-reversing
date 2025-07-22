package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableTake<T> extends AbstractFlowableWithUpstream<T, T> {
   final long limit;

   public FlowableTake(Flowable<T> var1, long var2) {
      super(var1);
      this.limit = var2;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      this.source.subscribe(new FlowableTake.TakeSubscriber<>(var1, this.limit));
   }

   static final class TakeSubscriber<T> extends AtomicBoolean implements FlowableSubscriber<T>, Subscription {
      private static final long serialVersionUID = -5636543848937116287L;
      boolean done;
      final Subscriber<? super T> downstream;
      final long limit;
      long remaining;
      Subscription upstream;

      TakeSubscriber(Subscriber<? super T> var1, long var2) {
         this.downstream = var1;
         this.limit = var2;
         this.remaining = var2;
      }

      public void cancel() {
         this.upstream.cancel();
      }

      public void onComplete() {
         if (!this.done) {
            this.done = true;
            this.downstream.onComplete();
         }
      }

      public void onError(Throwable var1) {
         if (!this.done) {
            this.done = true;
            this.upstream.cancel();
            this.downstream.onError(var1);
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      public void onNext(T var1) {
         if (!this.done) {
            long var3 = this.remaining;
            long var5 = var3 - 1L;
            this.remaining = var5;
            if (var3 > 0L) {
               boolean var2;
               if (var5 == 0L) {
                  var2 = true;
               } else {
                  var2 = false;
               }

               this.downstream.onNext(var1);
               if (var2) {
                  this.upstream.cancel();
                  this.onComplete();
               }
            }
         }
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            if (this.limit == 0L) {
               var1.cancel();
               this.done = true;
               EmptySubscription.complete(this.downstream);
            } else {
               this.downstream.onSubscribe(this);
            }
         }
      }

      public void request(long var1) {
         if (SubscriptionHelper.validate(var1)) {
            if (!this.get() && this.compareAndSet(false, true) && var1 >= this.limit) {
               this.upstream.request(Long.MAX_VALUE);
            } else {
               this.upstream.request(var1);
            }
         }
      }
   }
}
