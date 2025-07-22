package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionArbiter;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.UnicastProcessor;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableRepeatWhen<T> extends AbstractFlowableWithUpstream<T, T> {
   final Function<? super Flowable<Object>, ? extends Publisher<?>> handler;

   public FlowableRepeatWhen(Flowable<T> var1, Function<? super Flowable<Object>, ? extends Publisher<?>> var2) {
      super(var1);
      this.handler = var2;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void subscribeActual(Subscriber<? super T> var1) {
      SerializedSubscriber var4 = new SerializedSubscriber(var1);
      FlowableProcessor var5 = UnicastProcessor.create(8).toSerialized();

      Publisher var3;
      try {
         var3 = ObjectHelper.requireNonNull(this.handler.apply(var5), "handler returned a null Publisher");
      } catch (Throwable var7) {
         Exceptions.throwIfFatal(var7);
         EmptySubscription.error(var7, var1);
         return;
      }

      FlowableRepeatWhen.WhenReceiver var2 = new FlowableRepeatWhen.WhenReceiver(this.source);
      FlowableRepeatWhen.RepeatWhenSubscriber var8 = new FlowableRepeatWhen.RepeatWhenSubscriber(var4, var5, var2);
      var2.subscriber = var8;
      var1.onSubscribe(var8);
      var3.subscribe(var2);
      var2.onNext(0);
   }

   static final class RepeatWhenSubscriber<T> extends FlowableRepeatWhen.WhenSourceSubscriber<T, Object> {
      private static final long serialVersionUID = -2680129890138081029L;

      RepeatWhenSubscriber(Subscriber<? super T> var1, FlowableProcessor<Object> var2, Subscription var3) {
         super(var1, var2, var3);
      }

      public void onComplete() {
         this.again(0);
      }

      public void onError(Throwable var1) {
         this.receiver.cancel();
         this.downstream.onError(var1);
      }
   }

   static final class WhenReceiver<T, U> extends AtomicInteger implements FlowableSubscriber<Object>, Subscription {
      private static final long serialVersionUID = 2827772011130406689L;
      final AtomicLong requested;
      final Publisher<T> source;
      FlowableRepeatWhen.WhenSourceSubscriber<T, U> subscriber;
      final AtomicReference<Subscription> upstream;

      WhenReceiver(Publisher<T> var1) {
         this.source = var1;
         this.upstream = new AtomicReference<>();
         this.requested = new AtomicLong();
      }

      public void cancel() {
         SubscriptionHelper.cancel(this.upstream);
      }

      public void onComplete() {
         this.subscriber.cancel();
         this.subscriber.downstream.onComplete();
      }

      public void onError(Throwable var1) {
         this.subscriber.cancel();
         this.subscriber.downstream.onError(var1);
      }

      public void onNext(Object var1) {
         if (this.getAndIncrement() == 0) {
            do {
               if (this.upstream.get() == SubscriptionHelper.CANCELLED) {
                  return;
               }

               this.source.subscribe(this.subscriber);
            } while (this.decrementAndGet() != 0);
         }
      }

      @Override
      public void onSubscribe(Subscription var1) {
         SubscriptionHelper.deferredSetOnce(this.upstream, this.requested, var1);
      }

      public void request(long var1) {
         SubscriptionHelper.deferredRequest(this.upstream, this.requested, var1);
      }
   }

   abstract static class WhenSourceSubscriber<T, U> extends SubscriptionArbiter implements FlowableSubscriber<T> {
      private static final long serialVersionUID = -5604623027276966720L;
      protected final Subscriber<? super T> downstream;
      protected final FlowableProcessor<U> processor;
      private long produced;
      protected final Subscription receiver;

      WhenSourceSubscriber(Subscriber<? super T> var1, FlowableProcessor<U> var2, Subscription var3) {
         super(false);
         this.downstream = var1;
         this.processor = var2;
         this.receiver = var3;
      }

      protected final void again(U var1) {
         this.setSubscription(EmptySubscription.INSTANCE);
         long var2 = this.produced;
         if (var2 != 0L) {
            this.produced = 0L;
            this.produced(var2);
         }

         this.receiver.request(1L);
         this.processor.onNext(var1);
      }

      @Override
      public final void cancel() {
         super.cancel();
         this.receiver.cancel();
      }

      public final void onNext(T var1) {
         this.produced++;
         this.downstream.onNext(var1);
      }

      @Override
      public final void onSubscribe(Subscription var1) {
         this.setSubscription(var1);
      }
   }
}
