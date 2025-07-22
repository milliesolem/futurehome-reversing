package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.schedulers.Timed;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableTimeInterval<T> extends AbstractFlowableWithUpstream<T, Timed<T>> {
   final Scheduler scheduler;
   final TimeUnit unit;

   public FlowableTimeInterval(Flowable<T> var1, TimeUnit var2, Scheduler var3) {
      super(var1);
      this.scheduler = var3;
      this.unit = var2;
   }

   @Override
   protected void subscribeActual(Subscriber<? super Timed<T>> var1) {
      this.source.subscribe(new FlowableTimeInterval.TimeIntervalSubscriber<>(var1, this.unit, this.scheduler));
   }

   static final class TimeIntervalSubscriber<T> implements FlowableSubscriber<T>, Subscription {
      final Subscriber<? super Timed<T>> downstream;
      long lastTime;
      final Scheduler scheduler;
      final TimeUnit unit;
      Subscription upstream;

      TimeIntervalSubscriber(Subscriber<? super Timed<T>> var1, TimeUnit var2, Scheduler var3) {
         this.downstream = var1;
         this.scheduler = var3;
         this.unit = var2;
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
         long var4 = this.scheduler.now(this.unit);
         long var2 = this.lastTime;
         this.lastTime = var4;
         this.downstream.onNext(new Timed<>(var1, var4 - var2, this.unit));
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            this.lastTime = this.scheduler.now(this.unit);
            this.upstream = var1;
            this.downstream.onSubscribe(this);
         }
      }

      public void request(long var1) {
         this.upstream.request(var1);
      }
   }
}
