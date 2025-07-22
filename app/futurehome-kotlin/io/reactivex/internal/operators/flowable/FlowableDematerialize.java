package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Notification;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableDematerialize<T, R> extends AbstractFlowableWithUpstream<T, R> {
   final Function<? super T, ? extends Notification<R>> selector;

   public FlowableDematerialize(Flowable<T> var1, Function<? super T, ? extends Notification<R>> var2) {
      super(var1);
      this.selector = var2;
   }

   @Override
   protected void subscribeActual(Subscriber<? super R> var1) {
      this.source.subscribe(new FlowableDematerialize.DematerializeSubscriber<>(var1, this.selector));
   }

   static final class DematerializeSubscriber<T, R> implements FlowableSubscriber<T>, Subscription {
      boolean done;
      final Subscriber<? super R> downstream;
      final Function<? super T, ? extends Notification<R>> selector;
      Subscription upstream;

      DematerializeSubscriber(Subscriber<? super R> var1, Function<? super T, ? extends Notification<R>> var2) {
         this.downstream = var1;
         this.selector = var2;
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
         if (this.done) {
            if (var1 instanceof Notification) {
               var1 = var1;
               if (var1.isOnError()) {
                  RxJavaPlugins.onError(var1.getError());
               }
            }
         } else {
            try {
               var1 = ObjectHelper.requireNonNull(this.selector.apply((T)var1), "The selector returned a null Notification");
            } catch (Throwable var3) {
               Exceptions.throwIfFatal(var3);
               this.upstream.cancel();
               this.onError(var3);
               return;
            }

            if (var1.isOnError()) {
               this.upstream.cancel();
               this.onError(var1.getError());
            } else if (var1.isOnComplete()) {
               this.upstream.cancel();
               this.onComplete();
            } else {
               this.downstream.onNext(var1.getValue());
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
