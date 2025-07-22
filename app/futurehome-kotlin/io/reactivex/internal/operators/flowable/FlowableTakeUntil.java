package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.HalfSerializer;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableTakeUntil<T, U> extends AbstractFlowableWithUpstream<T, T> {
   final Publisher<? extends U> other;

   public FlowableTakeUntil(Flowable<T> var1, Publisher<? extends U> var2) {
      super(var1);
      this.other = var2;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      FlowableTakeUntil.TakeUntilMainSubscriber var2 = new FlowableTakeUntil.TakeUntilMainSubscriber(var1);
      var1.onSubscribe(var2);
      this.other.subscribe(var2.other);
      this.source.subscribe(var2);
   }

   static final class TakeUntilMainSubscriber<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
      private static final long serialVersionUID = -4945480365982832967L;
      final Subscriber<? super T> downstream;
      final AtomicThrowable error;
      final FlowableTakeUntil.TakeUntilMainSubscriber<T>.OtherSubscriber other;
      final AtomicLong requested;
      final AtomicReference<Subscription> upstream;

      TakeUntilMainSubscriber(Subscriber<? super T> var1) {
         this.downstream = var1;
         this.requested = new AtomicLong();
         this.upstream = new AtomicReference<>();
         this.other = new FlowableTakeUntil.TakeUntilMainSubscriber.OtherSubscriber(this);
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
         HalfSerializer.onNext(this.downstream, var1, this, this.error);
      }

      @Override
      public void onSubscribe(Subscription var1) {
         SubscriptionHelper.deferredSetOnce(this.upstream, this.requested, var1);
      }

      public void request(long var1) {
         SubscriptionHelper.deferredRequest(this.upstream, this.requested, var1);
      }

      final class OtherSubscriber extends AtomicReference<Subscription> implements FlowableSubscriber<Object> {
         private static final long serialVersionUID = -3592821756711087922L;
         final FlowableTakeUntil.TakeUntilMainSubscriber this$0;

         OtherSubscriber(FlowableTakeUntil.TakeUntilMainSubscriber var1) {
            this.this$0 = var1;
         }

         public void onComplete() {
            SubscriptionHelper.cancel(this.this$0.upstream);
            Subscriber var1 = this.this$0.downstream;
            FlowableTakeUntil.TakeUntilMainSubscriber var2 = this.this$0;
            HalfSerializer.onComplete(var1, var2, var2.error);
         }

         public void onError(Throwable var1) {
            SubscriptionHelper.cancel(this.this$0.upstream);
            Subscriber var2 = this.this$0.downstream;
            FlowableTakeUntil.TakeUntilMainSubscriber var3 = this.this$0;
            HalfSerializer.onError(var2, var1, var3, var3.error);
         }

         public void onNext(Object var1) {
            SubscriptionHelper.cancel(this);
            this.onComplete();
         }

         @Override
         public void onSubscribe(Subscription var1) {
            SubscriptionHelper.setOnce(this, var1, Long.MAX_VALUE);
         }
      }
   }
}
