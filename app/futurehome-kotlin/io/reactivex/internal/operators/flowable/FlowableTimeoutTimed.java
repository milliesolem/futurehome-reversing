package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.subscriptions.SubscriptionArbiter;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableTimeoutTimed<T> extends AbstractFlowableWithUpstream<T, T> {
   final Publisher<? extends T> other;
   final Scheduler scheduler;
   final long timeout;
   final TimeUnit unit;

   public FlowableTimeoutTimed(Flowable<T> var1, long var2, TimeUnit var4, Scheduler var5, Publisher<? extends T> var6) {
      super(var1);
      this.timeout = var2;
      this.unit = var4;
      this.scheduler = var5;
      this.other = var6;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      if (this.other == null) {
         FlowableTimeoutTimed.TimeoutSubscriber var2 = new FlowableTimeoutTimed.TimeoutSubscriber(var1, this.timeout, this.unit, this.scheduler.createWorker());
         var1.onSubscribe(var2);
         var2.startTimeout(0L);
         this.source.subscribe(var2);
      } else {
         FlowableTimeoutTimed.TimeoutFallbackSubscriber var3 = new FlowableTimeoutTimed.TimeoutFallbackSubscriber(
            var1, this.timeout, this.unit, this.scheduler.createWorker(), this.other
         );
         var1.onSubscribe(var3);
         var3.startTimeout(0L);
         this.source.subscribe(var3);
      }
   }

   static final class FallbackSubscriber<T> implements FlowableSubscriber<T> {
      final SubscriptionArbiter arbiter;
      final Subscriber<? super T> downstream;

      FallbackSubscriber(Subscriber<? super T> var1, SubscriptionArbiter var2) {
         this.downstream = var1;
         this.arbiter = var2;
      }

      public void onComplete() {
         this.downstream.onComplete();
      }

      public void onError(Throwable var1) {
         this.downstream.onError(var1);
      }

      public void onNext(T var1) {
         this.downstream.onNext(var1);
      }

      @Override
      public void onSubscribe(Subscription var1) {
         this.arbiter.setSubscription(var1);
      }
   }

   static final class TimeoutFallbackSubscriber<T> extends SubscriptionArbiter implements FlowableSubscriber<T>, FlowableTimeoutTimed.TimeoutSupport {
      private static final long serialVersionUID = 3764492702657003550L;
      long consumed;
      final Subscriber<? super T> downstream;
      Publisher<? extends T> fallback;
      final AtomicLong index;
      final SequentialDisposable task;
      final long timeout;
      final TimeUnit unit;
      final AtomicReference<Subscription> upstream;
      final Scheduler.Worker worker;

      TimeoutFallbackSubscriber(Subscriber<? super T> var1, long var2, TimeUnit var4, Scheduler.Worker var5, Publisher<? extends T> var6) {
         super(true);
         this.downstream = var1;
         this.timeout = var2;
         this.unit = var4;
         this.worker = var5;
         this.fallback = var6;
         this.task = new SequentialDisposable();
         this.upstream = new AtomicReference<>();
         this.index = new AtomicLong();
      }

      @Override
      public void cancel() {
         super.cancel();
         this.worker.dispose();
      }

      public void onComplete() {
         if (this.index.getAndSet(Long.MAX_VALUE) != Long.MAX_VALUE) {
            this.task.dispose();
            this.downstream.onComplete();
            this.worker.dispose();
         }
      }

      public void onError(Throwable var1) {
         if (this.index.getAndSet(Long.MAX_VALUE) != Long.MAX_VALUE) {
            this.task.dispose();
            this.downstream.onError(var1);
            this.worker.dispose();
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      public void onNext(T var1) {
         long var2 = this.index.get();
         if (var2 != Long.MAX_VALUE) {
            AtomicLong var6 = this.index;
            long var4 = var2 + 1L;
            if (var6.compareAndSet(var2, var4)) {
               this.task.get().dispose();
               this.consumed++;
               this.downstream.onNext(var1);
               this.startTimeout(var4);
            }
         }
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.setOnce(this.upstream, var1)) {
            this.setSubscription(var1);
         }
      }

      @Override
      public void onTimeout(long var1) {
         if (this.index.compareAndSet(var1, Long.MAX_VALUE)) {
            SubscriptionHelper.cancel(this.upstream);
            var1 = this.consumed;
            if (var1 != 0L) {
               this.produced(var1);
            }

            Publisher var3 = this.fallback;
            this.fallback = null;
            var3.subscribe(new FlowableTimeoutTimed.FallbackSubscriber(this.downstream, this));
            this.worker.dispose();
         }
      }

      void startTimeout(long var1) {
         this.task.replace(this.worker.schedule(new FlowableTimeoutTimed.TimeoutTask(var1, this), this.timeout, this.unit));
      }
   }

   static final class TimeoutSubscriber<T> extends AtomicLong implements FlowableSubscriber<T>, Subscription, FlowableTimeoutTimed.TimeoutSupport {
      private static final long serialVersionUID = 3764492702657003550L;
      final Subscriber<? super T> downstream;
      final AtomicLong requested;
      final SequentialDisposable task;
      final long timeout;
      final TimeUnit unit;
      final AtomicReference<Subscription> upstream;
      final Scheduler.Worker worker;

      TimeoutSubscriber(Subscriber<? super T> var1, long var2, TimeUnit var4, Scheduler.Worker var5) {
         this.downstream = var1;
         this.timeout = var2;
         this.unit = var4;
         this.worker = var5;
         this.task = new SequentialDisposable();
         this.upstream = new AtomicReference<>();
         this.requested = new AtomicLong();
      }

      public void cancel() {
         SubscriptionHelper.cancel(this.upstream);
         this.worker.dispose();
      }

      public void onComplete() {
         if (this.getAndSet(Long.MAX_VALUE) != Long.MAX_VALUE) {
            this.task.dispose();
            this.downstream.onComplete();
            this.worker.dispose();
         }
      }

      public void onError(Throwable var1) {
         if (this.getAndSet(Long.MAX_VALUE) != Long.MAX_VALUE) {
            this.task.dispose();
            this.downstream.onError(var1);
            this.worker.dispose();
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      public void onNext(T var1) {
         long var2 = this.get();
         if (var2 != Long.MAX_VALUE) {
            long var4 = 1L + var2;
            if (this.compareAndSet(var2, var4)) {
               this.task.get().dispose();
               this.downstream.onNext(var1);
               this.startTimeout(var4);
            }
         }
      }

      @Override
      public void onSubscribe(Subscription var1) {
         SubscriptionHelper.deferredSetOnce(this.upstream, this.requested, var1);
      }

      @Override
      public void onTimeout(long var1) {
         if (this.compareAndSet(var1, Long.MAX_VALUE)) {
            SubscriptionHelper.cancel(this.upstream);
            this.downstream.onError(new TimeoutException(ExceptionHelper.timeoutMessage(this.timeout, this.unit)));
            this.worker.dispose();
         }
      }

      public void request(long var1) {
         SubscriptionHelper.deferredRequest(this.upstream, this.requested, var1);
      }

      void startTimeout(long var1) {
         this.task.replace(this.worker.schedule(new FlowableTimeoutTimed.TimeoutTask(var1, this), this.timeout, this.unit));
      }
   }

   interface TimeoutSupport {
      void onTimeout(long var1);
   }

   static final class TimeoutTask implements Runnable {
      final long idx;
      final FlowableTimeoutTimed.TimeoutSupport parent;

      TimeoutTask(long var1, FlowableTimeoutTimed.TimeoutSupport var3) {
         this.idx = var1;
         this.parent = var3;
      }

      @Override
      public void run() {
         this.parent.onTimeout(this.idx);
      }
   }
}
