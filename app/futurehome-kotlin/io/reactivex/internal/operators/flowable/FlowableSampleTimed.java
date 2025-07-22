package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableSampleTimed<T> extends AbstractFlowableWithUpstream<T, T> {
   final boolean emitLast;
   final long period;
   final Scheduler scheduler;
   final TimeUnit unit;

   public FlowableSampleTimed(Flowable<T> var1, long var2, TimeUnit var4, Scheduler var5, boolean var6) {
      super(var1);
      this.period = var2;
      this.unit = var4;
      this.scheduler = var5;
      this.emitLast = var6;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      SerializedSubscriber var2 = new SerializedSubscriber(var1);
      if (this.emitLast) {
         this.source.subscribe(new FlowableSampleTimed.SampleTimedEmitLast<>(var2, this.period, this.unit, this.scheduler));
      } else {
         this.source.subscribe(new FlowableSampleTimed.SampleTimedNoLast<>(var2, this.period, this.unit, this.scheduler));
      }
   }

   static final class SampleTimedEmitLast<T> extends FlowableSampleTimed.SampleTimedSubscriber<T> {
      private static final long serialVersionUID = -7139995637533111443L;
      final AtomicInteger wip = new AtomicInteger(1);

      SampleTimedEmitLast(Subscriber<? super T> var1, long var2, TimeUnit var4, Scheduler var5) {
         super(var1, var2, var4, var5);
      }

      @Override
      void complete() {
         this.emit();
         if (this.wip.decrementAndGet() == 0) {
            this.downstream.onComplete();
         }
      }

      @Override
      public void run() {
         if (this.wip.incrementAndGet() == 2) {
            this.emit();
            if (this.wip.decrementAndGet() == 0) {
               this.downstream.onComplete();
            }
         }
      }
   }

   static final class SampleTimedNoLast<T> extends FlowableSampleTimed.SampleTimedSubscriber<T> {
      private static final long serialVersionUID = -7139995637533111443L;

      SampleTimedNoLast(Subscriber<? super T> var1, long var2, TimeUnit var4, Scheduler var5) {
         super(var1, var2, var4, var5);
      }

      @Override
      void complete() {
         this.downstream.onComplete();
      }

      @Override
      public void run() {
         this.emit();
      }
   }

   abstract static class SampleTimedSubscriber<T> extends AtomicReference<T> implements FlowableSubscriber<T>, Subscription, Runnable {
      private static final long serialVersionUID = -3517602651313910099L;
      final Subscriber<? super T> downstream;
      final long period;
      final AtomicLong requested = new AtomicLong();
      final Scheduler scheduler;
      final SequentialDisposable timer = new SequentialDisposable();
      final TimeUnit unit;
      Subscription upstream;

      SampleTimedSubscriber(Subscriber<? super T> var1, long var2, TimeUnit var4, Scheduler var5) {
         this.downstream = var1;
         this.period = var2;
         this.unit = var4;
         this.scheduler = var5;
      }

      public void cancel() {
         this.cancelTimer();
         this.upstream.cancel();
      }

      void cancelTimer() {
         DisposableHelper.dispose(this.timer);
      }

      abstract void complete();

      void emit() {
         Object var1 = this.getAndSet(null);
         if (var1 != null) {
            if (this.requested.get() != 0L) {
               this.downstream.onNext(var1);
               BackpressureHelper.produced(this.requested, 1L);
            } else {
               this.cancel();
               this.downstream.onError(new MissingBackpressureException("Couldn't emit value due to lack of requests!"));
            }
         }
      }

      public void onComplete() {
         this.cancelTimer();
         this.complete();
      }

      public void onError(Throwable var1) {
         this.cancelTimer();
         this.downstream.onError(var1);
      }

      public void onNext(T var1) {
         this.lazySet((T)var1);
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
            SequentialDisposable var4 = this.timer;
            Scheduler var5 = this.scheduler;
            long var2 = this.period;
            var4.replace(var5.schedulePeriodicallyDirect(this, var2, var2, this.unit));
            var1.request(Long.MAX_VALUE);
         }
      }

      public void request(long var1) {
         if (SubscriptionHelper.validate(var1)) {
            BackpressureHelper.add(this.requested, var1);
         }
      }
   }
}
