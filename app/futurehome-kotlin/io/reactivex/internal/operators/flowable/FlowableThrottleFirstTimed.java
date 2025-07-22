package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableThrottleFirstTimed<T> extends AbstractFlowableWithUpstream<T, T> {
   final Scheduler scheduler;
   final long timeout;
   final TimeUnit unit;

   public FlowableThrottleFirstTimed(Flowable<T> var1, long var2, TimeUnit var4, Scheduler var5) {
      super(var1);
      this.timeout = var2;
      this.unit = var4;
      this.scheduler = var5;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      this.source
         .subscribe(
            new FlowableThrottleFirstTimed.DebounceTimedSubscriber<>(new SerializedSubscriber<>(var1), this.timeout, this.unit, this.scheduler.createWorker())
         );
   }

   static final class DebounceTimedSubscriber<T> extends AtomicLong implements FlowableSubscriber<T>, Subscription, Runnable {
      private static final long serialVersionUID = -9102637559663639004L;
      boolean done;
      final Subscriber<? super T> downstream;
      volatile boolean gate;
      final long timeout;
      final SequentialDisposable timer = new SequentialDisposable();
      final TimeUnit unit;
      Subscription upstream;
      final Scheduler.Worker worker;

      DebounceTimedSubscriber(Subscriber<? super T> var1, long var2, TimeUnit var4, Scheduler.Worker var5) {
         this.downstream = var1;
         this.timeout = var2;
         this.unit = var4;
         this.worker = var5;
      }

      public void cancel() {
         this.upstream.cancel();
         this.worker.dispose();
      }

      public void onComplete() {
         if (!this.done) {
            this.done = true;
            this.downstream.onComplete();
            this.worker.dispose();
         }
      }

      public void onError(Throwable var1) {
         if (this.done) {
            RxJavaPlugins.onError(var1);
         } else {
            this.done = true;
            this.downstream.onError(var1);
            this.worker.dispose();
         }
      }

      public void onNext(T var1) {
         if (!this.done) {
            if (!this.gate) {
               this.gate = true;
               if (this.get() != 0L) {
                  this.downstream.onNext(var1);
                  BackpressureHelper.produced(this, 1L);
                  var1 = this.timer.get();
                  if (var1 != null) {
                     var1.dispose();
                  }

                  this.timer.replace(this.worker.schedule(this, this.timeout, this.unit));
               } else {
                  this.done = true;
                  this.cancel();
                  this.downstream.onError(new MissingBackpressureException("Could not deliver value due to lack of requests"));
               }
            }
         }
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
            BackpressureHelper.add(this, var1);
         }
      }

      @Override
      public void run() {
         this.gate = false;
      }
   }
}
