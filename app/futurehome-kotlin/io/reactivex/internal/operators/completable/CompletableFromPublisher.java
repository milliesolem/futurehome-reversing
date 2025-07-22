package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

public final class CompletableFromPublisher<T> extends Completable {
   final Publisher<T> flowable;

   public CompletableFromPublisher(Publisher<T> var1) {
      this.flowable = var1;
   }

   @Override
   protected void subscribeActual(CompletableObserver var1) {
      this.flowable.subscribe(new CompletableFromPublisher.FromPublisherSubscriber(var1));
   }

   static final class FromPublisherSubscriber<T> implements FlowableSubscriber<T>, Disposable {
      final CompletableObserver downstream;
      Subscription upstream;

      FromPublisherSubscriber(CompletableObserver var1) {
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
         this.downstream.onComplete();
      }

      public void onError(Throwable var1) {
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
