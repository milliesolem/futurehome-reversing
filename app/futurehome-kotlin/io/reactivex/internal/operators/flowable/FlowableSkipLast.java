package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import java.util.ArrayDeque;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableSkipLast<T> extends AbstractFlowableWithUpstream<T, T> {
   final int skip;

   public FlowableSkipLast(Flowable<T> var1, int var2) {
      super(var1);
      this.skip = var2;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      this.source.subscribe(new FlowableSkipLast.SkipLastSubscriber<>(var1, this.skip));
   }

   static final class SkipLastSubscriber<T> extends ArrayDeque<T> implements FlowableSubscriber<T>, Subscription {
      private static final long serialVersionUID = -3807491841935125653L;
      final Subscriber<? super T> downstream;
      final int skip;
      Subscription upstream;

      SkipLastSubscriber(Subscriber<? super T> var1, int var2) {
         super(var2);
         this.downstream = var1;
         this.skip = var2;
      }

      public void cancel() {
         this.upstream.cancel();
      }

      public void onComplete() {
         this.downstream.onComplete();
      }

      public void onError(Throwable var1) {
         this.downstream.onError(var1);
      }

      public void onNext(T var1) {
         if (this.skip == this.size()) {
            this.downstream.onNext(this.poll());
         } else {
            this.upstream.request(1L);
         }

         this.offer((T)var1);
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
