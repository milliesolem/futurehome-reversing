package io.reactivex.internal.operators.flowable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.fuseable.FuseToFlowable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscription;

public final class FlowableIgnoreElementsCompletable<T> extends Completable implements FuseToFlowable<T> {
   final Flowable<T> source;

   public FlowableIgnoreElementsCompletable(Flowable<T> var1) {
      this.source = var1;
   }

   @Override
   public Flowable<T> fuseToFlowable() {
      return RxJavaPlugins.onAssembly(new FlowableIgnoreElements<>(this.source));
   }

   @Override
   protected void subscribeActual(CompletableObserver var1) {
      this.source.subscribe(new FlowableIgnoreElementsCompletable.IgnoreElementsSubscriber<>(var1));
   }

   static final class IgnoreElementsSubscriber<T> implements FlowableSubscriber<T>, Disposable {
      final CompletableObserver downstream;
      Subscription upstream;

      IgnoreElementsSubscriber(CompletableObserver var1) {
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
         this.upstream = SubscriptionHelper.CANCELLED;
         this.downstream.onComplete();
      }

      public void onError(Throwable var1) {
         this.upstream = SubscriptionHelper.CANCELLED;
         this.downstream.onError(var1);
      }

      public void onNext(T var1) {
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
