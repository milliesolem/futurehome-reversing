package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.HalfSerializer;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableSkipUntil<T, U> extends AbstractFlowableWithUpstream<T, T> {
   final Publisher<U> other;

   public FlowableSkipUntil(Flowable<T> var1, Publisher<U> var2) {
      super(var1);
      this.other = var2;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      FlowableSkipUntil.SkipUntilMainSubscriber var2 = new FlowableSkipUntil.SkipUntilMainSubscriber(var1);
      var1.onSubscribe(var2);
      this.other.subscribe(var2.other);
      this.source.subscribe(var2);
   }

   static final class SkipUntilMainSubscriber<T> extends AtomicInteger implements ConditionalSubscriber<T>, Subscription {
      private static final long serialVersionUID = -6270983465606289181L;
      final Subscriber<? super T> downstream;
      final AtomicThrowable error;
      volatile boolean gate;
      final FlowableSkipUntil.SkipUntilMainSubscriber<T>.OtherSubscriber other;
      final AtomicLong requested;
      final AtomicReference<Subscription> upstream;

      SkipUntilMainSubscriber(Subscriber<? super T> var1) {
         this.downstream = var1;
         this.upstream = new AtomicReference<>();
         this.requested = new AtomicLong();
         this.other = new FlowableSkipUntil.SkipUntilMainSubscriber.OtherSubscriber(this);
         this.error = new AtomicThrowable();
      }

      public void cancel() {
         SubscriptionHelper.cancel(this.upstream);
         SubscriptionHelper.cancel(this.other);
      }

      public void onComplete() {
         SubscriptionHelper.cancel(this.other);
         HalfSerializer.onComplete(this.downstream, this, this.error);
      }

      public void onError(Throwable var1) {
         SubscriptionHelper.cancel(this.other);
         HalfSerializer.onError(this.downstream, var1, this, this.error);
      }

      public void onNext(T var1) {
         if (!this.tryOnNext((T)var1)) {
            this.upstream.get().request(1L);
         }
      }

      @Override
      public void onSubscribe(Subscription var1) {
         SubscriptionHelper.deferredSetOnce(this.upstream, this.requested, var1);
      }

      public void request(long var1) {
         SubscriptionHelper.deferredRequest(this.upstream, this.requested, var1);
      }

      @Override
      public boolean tryOnNext(T var1) {
         if (this.gate) {
            HalfSerializer.onNext(this.downstream, var1, this, this.error);
            return true;
         } else {
            return false;
         }
      }

      final class OtherSubscriber extends AtomicReference<Subscription> implements FlowableSubscriber<Object> {
         private static final long serialVersionUID = -5592042965931999169L;
         final FlowableSkipUntil.SkipUntilMainSubscriber this$0;

         OtherSubscriber(FlowableSkipUntil.SkipUntilMainSubscriber var1) {
            this.this$0 = var1;
         }

         public void onComplete() {
            this.this$0.gate = true;
         }

         public void onError(Throwable var1) {
            SubscriptionHelper.cancel(this.this$0.upstream);
            Subscriber var2 = this.this$0.downstream;
            FlowableSkipUntil.SkipUntilMainSubscriber var3 = this.this$0;
            HalfSerializer.onError(var2, var1, var3, var3.error);
         }

         public void onNext(Object var1) {
            this.this$0.gate = true;
            this.get().cancel();
         }

         @Override
         public void onSubscribe(Subscription var1) {
            SubscriptionHelper.setOnce(this, var1, Long.MAX_VALUE);
         }
      }
   }
}
