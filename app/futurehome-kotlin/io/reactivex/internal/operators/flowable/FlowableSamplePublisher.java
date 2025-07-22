package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableSamplePublisher<T> extends Flowable<T> {
   final boolean emitLast;
   final Publisher<?> other;
   final Publisher<T> source;

   public FlowableSamplePublisher(Publisher<T> var1, Publisher<?> var2, boolean var3) {
      this.source = var1;
      this.other = var2;
      this.emitLast = var3;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      SerializedSubscriber var2 = new SerializedSubscriber(var1);
      if (this.emitLast) {
         this.source.subscribe(new FlowableSamplePublisher.SampleMainEmitLast(var2, this.other));
      } else {
         this.source.subscribe(new FlowableSamplePublisher.SampleMainNoLast(var2, this.other));
      }
   }

   static final class SampleMainEmitLast<T> extends FlowableSamplePublisher.SamplePublisherSubscriber<T> {
      private static final long serialVersionUID = -3029755663834015785L;
      volatile boolean done;
      final AtomicInteger wip = new AtomicInteger();

      SampleMainEmitLast(Subscriber<? super T> var1, Publisher<?> var2) {
         super(var1, var2);
      }

      @Override
      void completion() {
         this.done = true;
         if (this.wip.getAndIncrement() == 0) {
            this.emit();
            this.downstream.onComplete();
         }
      }

      @Override
      void run() {
         if (this.wip.getAndIncrement() == 0) {
            do {
               boolean var1 = this.done;
               this.emit();
               if (var1) {
                  this.downstream.onComplete();
                  return;
               }
            } while (this.wip.decrementAndGet() != 0);
         }
      }
   }

   static final class SampleMainNoLast<T> extends FlowableSamplePublisher.SamplePublisherSubscriber<T> {
      private static final long serialVersionUID = -3029755663834015785L;

      SampleMainNoLast(Subscriber<? super T> var1, Publisher<?> var2) {
         super(var1, var2);
      }

      @Override
      void completion() {
         this.downstream.onComplete();
      }

      @Override
      void run() {
         this.emit();
      }
   }

   abstract static class SamplePublisherSubscriber<T> extends AtomicReference<T> implements FlowableSubscriber<T>, Subscription {
      private static final long serialVersionUID = -3517602651313910099L;
      final Subscriber<? super T> downstream;
      final AtomicReference<Subscription> other;
      final AtomicLong requested = new AtomicLong();
      final Publisher<?> sampler;
      Subscription upstream;

      SamplePublisherSubscriber(Subscriber<? super T> var1, Publisher<?> var2) {
         this.other = new AtomicReference<>();
         this.downstream = var1;
         this.sampler = var2;
      }

      public void cancel() {
         SubscriptionHelper.cancel(this.other);
         this.upstream.cancel();
      }

      public void complete() {
         this.upstream.cancel();
         this.completion();
      }

      abstract void completion();

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

      public void error(Throwable var1) {
         this.upstream.cancel();
         this.downstream.onError(var1);
      }

      public void onComplete() {
         SubscriptionHelper.cancel(this.other);
         this.completion();
      }

      public void onError(Throwable var1) {
         SubscriptionHelper.cancel(this.other);
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
            if (this.other.get() == null) {
               this.sampler.subscribe(new FlowableSamplePublisher.SamplerSubscriber<>(this));
               var1.request(Long.MAX_VALUE);
            }
         }
      }

      public void request(long var1) {
         if (SubscriptionHelper.validate(var1)) {
            BackpressureHelper.add(this.requested, var1);
         }
      }

      abstract void run();

      void setOther(Subscription var1) {
         SubscriptionHelper.setOnce(this.other, var1, Long.MAX_VALUE);
      }
   }

   static final class SamplerSubscriber<T> implements FlowableSubscriber<Object> {
      final FlowableSamplePublisher.SamplePublisherSubscriber<T> parent;

      SamplerSubscriber(FlowableSamplePublisher.SamplePublisherSubscriber<T> var1) {
         this.parent = var1;
      }

      public void onComplete() {
         this.parent.complete();
      }

      public void onError(Throwable var1) {
         this.parent.error(var1);
      }

      public void onNext(Object var1) {
         this.parent.run();
      }

      @Override
      public void onSubscribe(Subscription var1) {
         this.parent.setOther(var1);
      }
   }
}
