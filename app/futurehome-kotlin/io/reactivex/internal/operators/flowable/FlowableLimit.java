package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableLimit<T> extends AbstractFlowableWithUpstream<T, T> {
   final long n;

   public FlowableLimit(Flowable<T> var1, long var2) {
      super(var1);
      this.n = var2;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      this.source.subscribe(new FlowableLimit.LimitSubscriber<>(var1, this.n));
   }

   static final class LimitSubscriber<T> extends AtomicLong implements FlowableSubscriber<T>, Subscription {
      private static final long serialVersionUID = 2288246011222124525L;
      final Subscriber<? super T> downstream;
      long remaining;
      Subscription upstream;

      LimitSubscriber(Subscriber<? super T> var1, long var2) {
         this.downstream = var1;
         this.remaining = var2;
         this.lazySet(var2);
      }

      public void cancel() {
         this.upstream.cancel();
      }

      public void onComplete() {
         if (this.remaining > 0L) {
            this.remaining = 0L;
            this.downstream.onComplete();
         }
      }

      public void onError(Throwable var1) {
         if (this.remaining > 0L) {
            this.remaining = 0L;
            this.downstream.onError(var1);
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      public void onNext(T var1) {
         long var2 = this.remaining;
         if (var2 > 0L) {
            this.remaining = --var2;
            this.downstream.onNext(var1);
            if (var2 == 0L) {
               this.upstream.cancel();
               this.downstream.onComplete();
            }
         }
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            if (this.remaining == 0L) {
               var1.cancel();
               EmptySubscription.complete(this.downstream);
            } else {
               this.upstream = var1;
               this.downstream.onSubscribe(this);
            }
         }
      }

      public void request(long var1) {
         if (SubscriptionHelper.validate(var1)) {
            while (true) {
               long var5 = this.get();
               if (var5 == 0L) {
                  break;
               }

               long var3;
               if (var5 <= var1) {
                  var3 = var5;
               } else {
                  var3 = var1;
               }

               if (this.compareAndSet(var5, var5 - var3)) {
                  this.upstream.request(var3);
                  break;
               }
            }
         }
      }
   }
}
