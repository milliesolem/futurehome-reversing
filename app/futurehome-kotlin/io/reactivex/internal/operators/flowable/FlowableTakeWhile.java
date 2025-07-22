package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableTakeWhile<T> extends AbstractFlowableWithUpstream<T, T> {
   final Predicate<? super T> predicate;

   public FlowableTakeWhile(Flowable<T> var1, Predicate<? super T> var2) {
      super(var1);
      this.predicate = var2;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      this.source.subscribe(new FlowableTakeWhile.TakeWhileSubscriber<>(var1, this.predicate));
   }

   static final class TakeWhileSubscriber<T> implements FlowableSubscriber<T>, Subscription {
      boolean done;
      final Subscriber<? super T> downstream;
      final Predicate<? super T> predicate;
      Subscription upstream;

      TakeWhileSubscriber(Subscriber<? super T> var1, Predicate<? super T> var2) {
         this.downstream = var1;
         this.predicate = var2;
      }

      public void cancel() {
         this.upstream.cancel();
      }

      public void onComplete() {
         if (!this.done) {
            this.done = true;
            this.downstream.onComplete();
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

            if (!var2) {
               this.done = true;
               this.upstream.cancel();
               this.downstream.onComplete();
            } else {
               this.downstream.onNext(var1);
            }
         }
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
