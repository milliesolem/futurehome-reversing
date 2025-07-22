package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableUnsubscribeOn<T> extends AbstractFlowableWithUpstream<T, T> {
   final Scheduler scheduler;

   public FlowableUnsubscribeOn(Flowable<T> var1, Scheduler var2) {
      super(var1);
      this.scheduler = var2;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      this.source.subscribe(new FlowableUnsubscribeOn.UnsubscribeSubscriber<>(var1, this.scheduler));
   }

   static final class UnsubscribeSubscriber<T> extends AtomicBoolean implements FlowableSubscriber<T>, Subscription {
      private static final long serialVersionUID = 1015244841293359600L;
      final Subscriber<? super T> downstream;
      final Scheduler scheduler;
      Subscription upstream;

      UnsubscribeSubscriber(Subscriber<? super T> var1, Scheduler var2) {
         this.downstream = var1;
         this.scheduler = var2;
      }

      public void cancel() {
         if (this.compareAndSet(false, true)) {
            this.scheduler.scheduleDirect(new FlowableUnsubscribeOn.UnsubscribeSubscriber.Cancellation(this));
         }
      }

      public void onComplete() {
         if (!this.get()) {
            this.downstream.onComplete();
         }
      }

      public void onError(Throwable var1) {
         if (this.get()) {
            RxJavaPlugins.onError(var1);
         } else {
            this.downstream.onError(var1);
         }
      }

      public void onNext(T var1) {
         if (!this.get()) {
            this.downstream.onNext(var1);
         }
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
         }
      }

      public void request(long var1) {
         this.upstream.request(var1);
      }

      final class Cancellation implements Runnable {
         final FlowableUnsubscribeOn.UnsubscribeSubscriber this$0;

         Cancellation(FlowableUnsubscribeOn.UnsubscribeSubscriber var1) {
            this.this$0 = var1;
         }

         @Override
         public void run() {
            this.this$0.upstream.cancel();
         }
      }
   }
}
