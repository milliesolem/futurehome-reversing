package io.reactivex.internal.operators.flowable;

import io.reactivex.FlowableSubscriber;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import java.util.NoSuchElementException;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

public final class FlowableLastSingle<T> extends Single<T> {
   final T defaultItem;
   final Publisher<T> source;

   public FlowableLastSingle(Publisher<T> var1, T var2) {
      this.source = var1;
      this.defaultItem = (T)var2;
   }

   @Override
   protected void subscribeActual(SingleObserver<? super T> var1) {
      this.source.subscribe(new FlowableLastSingle.LastSubscriber(var1, this.defaultItem));
   }

   static final class LastSubscriber<T> implements FlowableSubscriber<T>, Disposable {
      final T defaultItem;
      final SingleObserver<? super T> downstream;
      T item;
      Subscription upstream;

      LastSubscriber(SingleObserver<? super T> var1, T var2) {
         this.downstream = var1;
         this.defaultItem = (T)var2;
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
            var1 = this.defaultItem;
            if (var1 != null) {
               this.downstream.onSuccess((T)var1);
            } else {
               this.downstream.onError(new NoSuchElementException());
            }
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
