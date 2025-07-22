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

public final class FlowableElementAtMaybe<T> extends Maybe<T> implements FuseToFlowable<T> {
   final long index;
   final Flowable<T> source;

   public FlowableElementAtMaybe(Flowable<T> var1, long var2) {
      this.source = var1;
      this.index = var2;
   }

   @Override
   public Flowable<T> fuseToFlowable() {
      return RxJavaPlugins.onAssembly(new FlowableElementAt<>(this.source, this.index, null, false));
   }

   @Override
   protected void subscribeActual(MaybeObserver<? super T> var1) {
      this.source.subscribe(new FlowableElementAtMaybe.ElementAtSubscriber<>(var1, this.index));
   }

   static final class ElementAtSubscriber<T> implements FlowableSubscriber<T>, Disposable {
      long count;
      boolean done;
      final MaybeObserver<? super T> downstream;
      final long index;
      Subscription upstream;

      ElementAtSubscriber(MaybeObserver<? super T> var1, long var2) {
         this.downstream = var1;
         this.index = var2;
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
         this.upstream = SubscriptionHelper.CANCELLED;
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
            this.upstream = SubscriptionHelper.CANCELLED;
            this.downstream.onError(var1);
         }
      }

      public void onNext(T var1) {
         if (!this.done) {
            long var2 = this.count;
            if (var2 == this.index) {
               this.done = true;
               this.upstream.cancel();
               this.upstream = SubscriptionHelper.CANCELLED;
               this.downstream.onSuccess((T)var1);
            } else {
               this.count = var2 + 1L;
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
