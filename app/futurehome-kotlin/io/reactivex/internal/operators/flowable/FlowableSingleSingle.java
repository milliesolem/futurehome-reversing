package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.fuseable.FuseToFlowable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.NoSuchElementException;
import org.reactivestreams.Subscription;

public final class FlowableSingleSingle<T> extends Single<T> implements FuseToFlowable<T> {
   final T defaultValue;
   final Flowable<T> source;

   public FlowableSingleSingle(Flowable<T> var1, T var2) {
      this.source = var1;
      this.defaultValue = (T)var2;
   }

   @Override
   public Flowable<T> fuseToFlowable() {
      return RxJavaPlugins.onAssembly(new FlowableSingle<>(this.source, this.defaultValue, true));
   }

   @Override
   protected void subscribeActual(SingleObserver<? super T> var1) {
      this.source.subscribe(new FlowableSingleSingle.SingleElementSubscriber<>(var1, this.defaultValue));
   }

   static final class SingleElementSubscriber<T> implements FlowableSubscriber<T>, Disposable {
      final T defaultValue;
      boolean done;
      final SingleObserver<? super T> downstream;
      Subscription upstream;
      T value;

      SingleElementSubscriber(SingleObserver<? super T> var1, T var2) {
         this.downstream = var1;
         this.defaultValue = (T)var2;
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
            Object var2 = this.value;
            this.value = null;
            Object var1 = var2;
            if (var2 == null) {
               var1 = this.defaultValue;
            }

            if (var1 != null) {
               this.downstream.onSuccess((T)var1);
            } else {
               this.downstream.onError(new NoSuchElementException());
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
