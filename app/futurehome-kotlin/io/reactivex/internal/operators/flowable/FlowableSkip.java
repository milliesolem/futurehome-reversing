package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableSkip<T> extends AbstractFlowableWithUpstream<T, T> {
   final long n;

   public FlowableSkip(Flowable<T> var1, long var2) {
      super(var1);
      this.n = var2;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      this.source.subscribe(new FlowableSkip.SkipSubscriber<>(var1, this.n));
   }

   static final class SkipSubscriber<T> implements FlowableSubscriber<T>, Subscription {
      final Subscriber<? super T> downstream;
      long remaining;
      Subscription upstream;

      SkipSubscriber(Subscriber<? super T> var1, long var2) {
         this.downstream = var1;
         this.remaining = var2;
      }

      public void cancel() {
         this.upstream.cancel();
      }

      public void onComplete() {
         this.downstream.onComplete();
      }

      public void onError(Throwable var1) {
         this.downstream.onError(var1);
      }

      public void onNext(T var1) {
         long var2 = this.remaining;
         if (var2 != 0L) {
            this.remaining = var2 - 1L;
         } else {
            this.downstream.onNext(var1);
         }
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            long var2 = this.remaining;
            this.upstream = var1;
            this.downstream.onSubscribe(this);
            var1.request(var2);
         }
      }

      public void request(long var1) {
         this.upstream.request(var1);
      }
   }
}
