package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.EmptyComponent;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableDetach<T> extends AbstractFlowableWithUpstream<T, T> {
   public FlowableDetach(Flowable<T> var1) {
      super(var1);
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      this.source.subscribe(new FlowableDetach.DetachSubscriber<>(var1));
   }

   static final class DetachSubscriber<T> implements FlowableSubscriber<T>, Subscription {
      Subscriber<? super T> downstream;
      Subscription upstream;

      DetachSubscriber(Subscriber<? super T> var1) {
         this.downstream = var1;
      }

      public void cancel() {
         Subscription var1 = this.upstream;
         this.upstream = EmptyComponent.INSTANCE;
         this.downstream = EmptyComponent.asSubscriber();
         var1.cancel();
      }

      public void onComplete() {
         Subscriber var1 = this.downstream;
         this.upstream = EmptyComponent.INSTANCE;
         this.downstream = EmptyComponent.asSubscriber();
         var1.onComplete();
      }

      public void onError(Throwable var1) {
         Subscriber var2 = this.downstream;
         this.upstream = EmptyComponent.INSTANCE;
         this.downstream = EmptyComponent.asSubscriber();
         var2.onError(var1);
      }

      public void onNext(T var1) {
         this.downstream.onNext(var1);
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
   }
}
