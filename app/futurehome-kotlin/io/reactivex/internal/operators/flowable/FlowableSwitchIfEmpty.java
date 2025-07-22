package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionArbiter;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableSwitchIfEmpty<T> extends AbstractFlowableWithUpstream<T, T> {
   final Publisher<? extends T> other;

   public FlowableSwitchIfEmpty(Flowable<T> var1, Publisher<? extends T> var2) {
      super(var1);
      this.other = var2;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      FlowableSwitchIfEmpty.SwitchIfEmptySubscriber var2 = new FlowableSwitchIfEmpty.SwitchIfEmptySubscriber(var1, this.other);
      var1.onSubscribe(var2.arbiter);
      this.source.subscribe(var2);
   }

   static final class SwitchIfEmptySubscriber<T> implements FlowableSubscriber<T> {
      final SubscriptionArbiter arbiter;
      final Subscriber<? super T> downstream;
      boolean empty;
      final Publisher<? extends T> other;

      SwitchIfEmptySubscriber(Subscriber<? super T> var1, Publisher<? extends T> var2) {
         this.downstream = var1;
         this.other = var2;
         this.empty = true;
         this.arbiter = new SubscriptionArbiter(false);
      }

      public void onComplete() {
         if (this.empty) {
            this.empty = false;
            this.other.subscribe(this);
         } else {
            this.downstream.onComplete();
         }
      }

      public void onError(Throwable var1) {
         this.downstream.onError(var1);
      }

      public void onNext(T var1) {
         if (this.empty) {
            this.empty = false;
         }

         this.downstream.onNext(var1);
      }

      @Override
      public void onSubscribe(Subscription var1) {
         this.arbiter.setSubscription(var1);
      }
   }
}
