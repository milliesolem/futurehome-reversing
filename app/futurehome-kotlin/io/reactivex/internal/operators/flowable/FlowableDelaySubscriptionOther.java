package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableDelaySubscriptionOther<T, U> extends Flowable<T> {
   final Publisher<? extends T> main;
   final Publisher<U> other;

   public FlowableDelaySubscriptionOther(Publisher<? extends T> var1, Publisher<U> var2) {
      this.main = var1;
      this.other = var2;
   }

   @Override
   public void subscribeActual(Subscriber<? super T> var1) {
      FlowableDelaySubscriptionOther.MainSubscriber var2 = new FlowableDelaySubscriptionOther.MainSubscriber(var1, this.main);
      var1.onSubscribe(var2);
      this.other.subscribe(var2.other);
   }

   static final class MainSubscriber<T> extends AtomicLong implements FlowableSubscriber<T>, Subscription {
      private static final long serialVersionUID = 2259811067697317255L;
      final Subscriber<? super T> downstream;
      final Publisher<? extends T> main;
      final FlowableDelaySubscriptionOther.MainSubscriber<T>.OtherSubscriber other;
      final AtomicReference<Subscription> upstream;

      MainSubscriber(Subscriber<? super T> var1, Publisher<? extends T> var2) {
         this.downstream = var1;
         this.main = var2;
         this.other = new FlowableDelaySubscriptionOther.MainSubscriber.OtherSubscriber(this);
         this.upstream = new AtomicReference<>();
      }

      public void cancel() {
         SubscriptionHelper.cancel(this.other);
         SubscriptionHelper.cancel(this.upstream);
      }

      void next() {
         this.main.subscribe(this);
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
         SubscriptionHelper.deferredSetOnce(this.upstream, this, var1);
      }

      public void request(long var1) {
         if (SubscriptionHelper.validate(var1)) {
            SubscriptionHelper.deferredRequest(this.upstream, this, var1);
         }
      }

      final class OtherSubscriber extends AtomicReference<Subscription> implements FlowableSubscriber<Object> {
         private static final long serialVersionUID = -3892798459447644106L;
         final FlowableDelaySubscriptionOther.MainSubscriber this$0;

         OtherSubscriber(FlowableDelaySubscriptionOther.MainSubscriber var1) {
            this.this$0 = var1;
         }

         public void onComplete() {
            if (this.get() != SubscriptionHelper.CANCELLED) {
               this.this$0.next();
            }
         }

         public void onError(Throwable var1) {
            if (this.get() != SubscriptionHelper.CANCELLED) {
               this.this$0.downstream.onError(var1);
            } else {
               RxJavaPlugins.onError(var1);
            }
         }

         public void onNext(Object var1) {
            var1 = this.get();
            if (var1 != SubscriptionHelper.CANCELLED) {
               this.lazySet(SubscriptionHelper.CANCELLED);
               var1.cancel();
               this.this$0.next();
            }
         }

         @Override
         public void onSubscribe(Subscription var1) {
            if (SubscriptionHelper.setOnce(this, var1)) {
               var1.request(Long.MAX_VALUE);
            }
         }
      }
   }
}
