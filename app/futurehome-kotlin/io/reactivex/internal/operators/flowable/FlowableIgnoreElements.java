package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableIgnoreElements<T> extends AbstractFlowableWithUpstream<T, T> {
   public FlowableIgnoreElements(Flowable<T> var1) {
      super(var1);
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      this.source.subscribe(new FlowableIgnoreElements.IgnoreElementsSubscriber<>(var1));
   }

   static final class IgnoreElementsSubscriber<T> implements FlowableSubscriber<T>, QueueSubscription<T> {
      final Subscriber<? super T> downstream;
      Subscription upstream;

      IgnoreElementsSubscriber(Subscriber<? super T> var1) {
         this.downstream = var1;
      }

      public void cancel() {
         this.upstream.cancel();
      }

      @Override
      public void clear() {
      }

      @Override
      public boolean isEmpty() {
         return true;
      }

      @Override
      public boolean offer(T var1) {
         throw new UnsupportedOperationException("Should not be called!");
      }

      @Override
      public boolean offer(T var1, T var2) {
         throw new UnsupportedOperationException("Should not be called!");
      }

      public void onComplete() {
         this.downstream.onComplete();
      }

      public void onError(Throwable var1) {
         this.downstream.onError(var1);
      }

      public void onNext(T var1) {
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
            var1.request(Long.MAX_VALUE);
         }
      }

      @Override
      public T poll() {
         return null;
      }

      public void request(long var1) {
      }

      @Override
      public int requestFusion(int var1) {
         return var1 & 2;
      }
   }
}
