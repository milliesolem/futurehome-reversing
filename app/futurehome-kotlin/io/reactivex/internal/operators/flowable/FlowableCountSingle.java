package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.fuseable.FuseToFlowable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscription;

public final class FlowableCountSingle<T> extends Single<Long> implements FuseToFlowable<Long> {
   final Flowable<T> source;

   public FlowableCountSingle(Flowable<T> var1) {
      this.source = var1;
   }

   @Override
   public Flowable<Long> fuseToFlowable() {
      return RxJavaPlugins.onAssembly(new FlowableCount<>(this.source));
   }

   @Override
   protected void subscribeActual(SingleObserver<? super Long> var1) {
      this.source.subscribe(new FlowableCountSingle.CountSubscriber(var1));
   }

   static final class CountSubscriber implements FlowableSubscriber<Object>, Disposable {
      long count;
      final SingleObserver<? super Long> downstream;
      Subscription upstream;

      CountSubscriber(SingleObserver<? super Long> var1) {
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
         this.downstream.onSuccess(this.count);
      }

      public void onError(Throwable var1) {
         this.upstream = SubscriptionHelper.CANCELLED;
         this.downstream.onError(var1);
      }

      public void onNext(Object var1) {
         this.count++;
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
