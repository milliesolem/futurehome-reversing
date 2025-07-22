package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.subscriptions.DeferredScalarSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableAny<T> extends AbstractFlowableWithUpstream<T, Boolean> {
   final Predicate<? super T> predicate;

   public FlowableAny(Flowable<T> var1, Predicate<? super T> var2) {
      super(var1);
      this.predicate = var2;
   }

   @Override
   protected void subscribeActual(Subscriber<? super Boolean> var1) {
      this.source.subscribe(new FlowableAny.AnySubscriber<>(var1, this.predicate));
   }

   static final class AnySubscriber<T> extends DeferredScalarSubscription<Boolean> implements FlowableSubscriber<T> {
      private static final long serialVersionUID = -2311252482644620661L;
      boolean done;
      final Predicate<? super T> predicate;
      Subscription upstream;

      AnySubscriber(Subscriber<? super Boolean> var1, Predicate<? super T> var2) {
         super(var1);
         this.predicate = var2;
      }

      @Override
      public void cancel() {
         super.cancel();
         this.upstream.cancel();
      }

      public void onComplete() {
         if (!this.done) {
            this.done = true;
            this.complete(false);
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

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void onNext(T var1) {
         if (!this.done) {
            boolean var2;
            try {
               var2 = this.predicate.test((T)var1);
            } catch (Throwable var4) {
               Exceptions.throwIfFatal(var4);
               this.upstream.cancel();
               this.onError(var4);
               return;
            }

            if (var2) {
               this.done = true;
               this.upstream.cancel();
               this.complete(true);
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
