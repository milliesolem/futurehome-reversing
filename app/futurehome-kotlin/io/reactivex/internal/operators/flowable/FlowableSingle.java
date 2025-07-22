package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.DeferredScalarSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.NoSuchElementException;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableSingle<T> extends AbstractFlowableWithUpstream<T, T> {
   final T defaultValue;
   final boolean failOnEmpty;

   public FlowableSingle(Flowable<T> var1, T var2, boolean var3) {
      super(var1);
      this.defaultValue = (T)var2;
      this.failOnEmpty = var3;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      this.source.subscribe(new FlowableSingle.SingleElementSubscriber<>(var1, this.defaultValue, this.failOnEmpty));
   }

   static final class SingleElementSubscriber<T> extends DeferredScalarSubscription<T> implements FlowableSubscriber<T> {
      private static final long serialVersionUID = -5526049321428043809L;
      final T defaultValue;
      boolean done;
      final boolean failOnEmpty;
      Subscription upstream;

      SingleElementSubscriber(Subscriber<? super T> var1, T var2, boolean var3) {
         super(var1);
         this.defaultValue = (T)var2;
         this.failOnEmpty = var3;
      }

      @Override
      public void cancel() {
         super.cancel();
         this.upstream.cancel();
      }

      public void onComplete() {
         if (!this.done) {
            this.done = true;
            Object var2 = this.value;
            this.value = null;
            Object var1 = var2;
            if (var2 == null) {
               var1 = this.defaultValue;
            }

            if (var1 == null) {
               if (this.failOnEmpty) {
                  this.downstream.onError(new NoSuchElementException());
               } else {
                  this.downstream.onComplete();
               }
            } else {
               this.complete((T)var1);
            }
         }
      }

      public void onError(Throwable var1) {
         if (this.done) {
            RxJavaPlugins.onError(var1);
         } else {
            this.done = true;
            this.downstream.onError(var1);
         }
      }

      public void onNext(T var1) {
         if (!this.done) {
            if (this.value != null) {
               this.done = true;
               this.upstream.cancel();
               this.downstream.onError(new IllegalArgumentException("Sequence contains more than one element!"));
            } else {
               this.value = (T)var1;
            }
         }
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
            var1.request(Long.MAX_VALUE);
         }
      }
   }
}
