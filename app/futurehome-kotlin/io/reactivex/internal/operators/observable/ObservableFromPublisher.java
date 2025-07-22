package io.reactivex.internal.operators.observable;

import io.reactivex.FlowableSubscriber;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

public final class ObservableFromPublisher<T> extends Observable<T> {
   final Publisher<? extends T> source;

   public ObservableFromPublisher(Publisher<? extends T> var1) {
      this.source = var1;
   }

   @Override
   protected void subscribeActual(Observer<? super T> var1) {
      this.source.subscribe(new ObservableFromPublisher.PublisherSubscriber(var1));
   }

   static final class PublisherSubscriber<T> implements FlowableSubscriber<T>, Disposable {
      final Observer<? super T> downstream;
      Subscription upstream;

      PublisherSubscriber(Observer<? super T> var1) {
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
         this.downstream.onNext((T)var1);
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
