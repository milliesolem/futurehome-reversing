package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableDelay<T> extends AbstractFlowableWithUpstream<T, T> {
   final long delay;
   final boolean delayError;
   final Scheduler scheduler;
   final TimeUnit unit;

   public FlowableDelay(Flowable<T> var1, long var2, TimeUnit var4, Scheduler var5, boolean var6) {
      super(var1);
      this.delay = var2;
      this.unit = var4;
      this.scheduler = var5;
      this.delayError = var6;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      if (!this.delayError) {
         var1 = new SerializedSubscriber((Subscriber<? super T>)var1);
      }

      Scheduler.Worker var2 = this.scheduler.createWorker();
      this.source.subscribe(new FlowableDelay.DelaySubscriber<>((Subscriber<? super T>)var1, this.delay, this.unit, var2, this.delayError));
   }

   static final class DelaySubscriber<T> implements FlowableSubscriber<T>, Subscription {
      final long delay;
      final boolean delayError;
      final Subscriber<? super T> downstream;
      final TimeUnit unit;
      Subscription upstream;
      final Scheduler.Worker w;

      DelaySubscriber(Subscriber<? super T> var1, long var2, TimeUnit var4, Scheduler.Worker var5, boolean var6) {
         this.downstream = var1;
         this.delay = var2;
         this.unit = var4;
         this.w = var5;
         this.delayError = var6;
      }

      public void cancel() {
         this.upstream.cancel();
         this.w.dispose();
      }

      public void onComplete() {
         this.w.schedule(new FlowableDelay.DelaySubscriber.OnComplete(this), this.delay, this.unit);
      }

      public void onError(Throwable var1) {
         Scheduler.Worker var4 = this.w;
         FlowableDelay.DelaySubscriber.OnError var5 = new FlowableDelay.DelaySubscriber.OnError(this, var1);
         long var2;
         if (this.delayError) {
            var2 = this.delay;
         } else {
            var2 = 0L;
         }

         var4.schedule(var5, var2, this.unit);
      }

      public void onNext(T var1) {
         this.w.schedule(new FlowableDelay.DelaySubscriber.OnNext(this, var1), this.delay, this.unit);
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

      final class OnComplete implements Runnable {
         final FlowableDelay.DelaySubscriber this$0;

         OnComplete(FlowableDelay.DelaySubscriber var1) {
            this.this$0 = var1;
         }

         @Override
         public void run() {
            try {
               this.this$0.downstream.onComplete();
            } finally {
               this.this$0.w.dispose();
            }
         }
      }

      final class OnError implements Runnable {
         private final Throwable t;
         final FlowableDelay.DelaySubscriber this$0;

         OnError(FlowableDelay.DelaySubscriber var1, Throwable var2) {
            this.this$0 = var1;
            this.t = var2;
         }

         @Override
         public void run() {
            try {
               this.this$0.downstream.onError(this.t);
            } finally {
               this.this$0.w.dispose();
            }
         }
      }

      final class OnNext implements Runnable {
         private final T t;
         final FlowableDelay.DelaySubscriber this$0;

         OnNext(T var1, Object var2) {
            this.this$0 = var1;
            this.t = (T)var2;
         }

         @Override
         public void run() {
            this.this$0.downstream.onNext(this.t);
         }
      }
   }
}
