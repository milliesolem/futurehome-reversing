package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableThrottleLatest<T> extends AbstractFlowableWithUpstream<T, T> {
   final boolean emitLast;
   final Scheduler scheduler;
   final long timeout;
   final TimeUnit unit;

   public FlowableThrottleLatest(Flowable<T> var1, long var2, TimeUnit var4, Scheduler var5, boolean var6) {
      super(var1);
      this.timeout = var2;
      this.unit = var4;
      this.scheduler = var5;
      this.emitLast = var6;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      this.source.subscribe(new FlowableThrottleLatest.ThrottleLatestSubscriber<>(var1, this.timeout, this.unit, this.scheduler.createWorker(), this.emitLast));
   }

   static final class ThrottleLatestSubscriber<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription, Runnable {
      private static final long serialVersionUID = -8296689127439125014L;
      volatile boolean cancelled;
      volatile boolean done;
      final Subscriber<? super T> downstream;
      final boolean emitLast;
      long emitted;
      Throwable error;
      final AtomicReference<T> latest;
      final AtomicLong requested;
      final long timeout;
      volatile boolean timerFired;
      boolean timerRunning;
      final TimeUnit unit;
      Subscription upstream;
      final Scheduler.Worker worker;

      ThrottleLatestSubscriber(Subscriber<? super T> var1, long var2, TimeUnit var4, Scheduler.Worker var5, boolean var6) {
         this.downstream = var1;
         this.timeout = var2;
         this.unit = var4;
         this.worker = var5;
         this.emitLast = var6;
         this.latest = new AtomicReference<>();
         this.requested = new AtomicLong();
      }

      public void cancel() {
         this.cancelled = true;
         this.upstream.cancel();
         this.worker.dispose();
         if (this.getAndIncrement() == 0) {
            this.latest.lazySet(null);
         }
      }

      void drain() {
         if (this.getAndIncrement() == 0) {
            AtomicReference var9 = this.latest;
            AtomicLong var7 = this.requested;
            Subscriber var6 = this.downstream;
            int var1 = 1;

            while (!this.cancelled) {
               boolean var5 = this.done;
               if (var5 && this.error != null) {
                  var9.lazySet(null);
                  var6.onError(this.error);
                  this.worker.dispose();
                  return;
               }

               boolean var2;
               if (var9.get() == null) {
                  var2 = 1;
               } else {
                  var2 = 0;
               }

               if (var5) {
                  if (!var2 && this.emitLast) {
                     Object var12 = var9.getAndSet(null);
                     long var11 = this.emitted;
                     if (var11 != var7.get()) {
                        this.emitted = var11 + 1L;
                        var6.onNext(var12);
                        var6.onComplete();
                     } else {
                        var6.onError(new MissingBackpressureException("Could not emit final value due to lack of requests"));
                     }
                  } else {
                     var9.lazySet(null);
                     var6.onComplete();
                  }

                  this.worker.dispose();
                  return;
               }

               if (var2) {
                  if (this.timerFired) {
                     this.timerRunning = false;
                     this.timerFired = false;
                  }
               } else if (!this.timerRunning || this.timerFired) {
                  Object var8 = var9.getAndSet(null);
                  long var3 = this.emitted;
                  if (var3 == var7.get()) {
                     this.upstream.cancel();
                     var6.onError(new MissingBackpressureException("Could not emit value due to lack of requests"));
                     this.worker.dispose();
                     return;
                  }

                  var6.onNext(var8);
                  this.emitted = var3 + 1L;
                  this.timerFired = false;
                  this.timerRunning = true;
                  this.worker.schedule(this, this.timeout, this.unit);
                  continue;
               }

               var2 = this.addAndGet(-var1);
               var1 = var2;
               if (var2 == 0) {
                  return;
               }
            }

            var9.lazySet(null);
         }
      }

      public void onComplete() {
         this.done = true;
         this.drain();
      }

      public void onError(Throwable var1) {
         this.error = var1;
         this.done = true;
         this.drain();
      }

      public void onNext(T var1) {
         this.latest.set((T)var1);
         this.drain();
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
         }
      }

      @Override
      public void run() {
         this.timerFired = true;
         this.drain();
      }
   }
}
