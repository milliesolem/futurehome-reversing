package io.reactivex.internal.operators.flowable;

import io.reactivex.FlowableSubscriber;
import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

public final class FlowableLastMaybe<T> extends Maybe<T> {
   final Publisher<T> source;

   public FlowableLastMaybe(Publisher<T> var1) {
      this.source = var1;
   }

   @Override
   protected void subscribeActual(MaybeObserver<? super T> var1) {
      this.source.subscribe(new FlowableLastMaybe.LastSubscriber(var1));
   }

   static final class LastSubscriber<T> implements FlowableSubscriber<T>, Disposable {
      final MaybeObserver<? super T> downstream;
      T item;
      Subscription upstream;

      LastSubscriber(MaybeObserver<? super T> var1) {
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
         Object var1 = this.item;
         if (var1 != null) {
            this.item = null;
            this.downstream.onSuccess((T)var1);
         } else {
            this.downstream.onComplete();
         }
      }

      public void onError(Throwable var1) {
         this.upstream = SubscriptionHelper.CANCELLED;
         this.item = null;
         this.downstream.onError(var1);
      }

      public void onNext(T var1) {
         this.item = (T)var1;
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
