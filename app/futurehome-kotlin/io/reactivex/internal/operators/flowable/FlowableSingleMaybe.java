package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.fuseable.FuseToFlowable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscription;

public final class FlowableSingleMaybe<T> extends Maybe<T> implements FuseToFlowable<T> {
   final Flowable<T> source;

   public FlowableSingleMaybe(Flowable<T> var1) {
      this.source = var1;
   }

   @Override
   public Flowable<T> fuseToFlowable() {
      return RxJavaPlugins.onAssembly(new FlowableSingle<>(this.source, null, false));
   }

   @Override
   protected void subscribeActual(MaybeObserver<? super T> var1) {
      this.source.subscribe(new FlowableSingleMaybe.SingleElementSubscriber<>(var1));
   }

   static final class SingleElementSubscriber<T> implements FlowableSubscriber<T>, Disposable {
      boolean done;
      final MaybeObserver<? super T> downstream;
      Subscription upstream;
      T value;

      SingleElementSubscriber(MaybeObserver<? super T> var1) {
         this.downstream = var1;
      }

      @Override
      public void dispose() {
         this.upstream.cancel();
         this.upstream = SubscriptionHelper.CANCELLED;
      }

      @Override
      public boolean isDisposed() {
         boolean var1;
         if (this.upstream == SubscriptionHelper.CANCELLED) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public void onComplete() {
         if (!this.done) {
            this.done = true;
            this.upstream = SubscriptionHelper.CANCELLED;
            Object var1 = this.value;
            this.value = null;
            if (var1 == null) {
               this.downstream.onComplete();
            } else {
               this.downstream.onSuccess((T)var1);
            }
         }
      }

      public void onError(Throwable var1) {
         if (this.done) {
            RxJavaPlugins.onError(var1);
         } else {
            this.done = true;
            this.upstream = SubscriptionHelper.CANCELLED;
            this.downstream.onError(var1);
         }
      }

      public void onNext(T var1) {
         if (!this.done) {
            if (this.value != null) {
               this.done = true;
               this.upstream.cancel();
               this.upstream = SubscriptionHelper.CANCELLED;
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
