package io.reactivex.internal.operators.single;

import io.reactivex.FlowableSubscriber;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.NoSuchElementException;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

public final class SingleFromPublisher<T> extends Single<T> {
   final Publisher<? extends T> publisher;

   public SingleFromPublisher(Publisher<? extends T> var1) {
      this.publisher = var1;
   }

   @Override
   protected void subscribeActual(SingleObserver<? super T> var1) {
      this.publisher.subscribe(new SingleFromPublisher.ToSingleObserver(var1));
   }

   static final class ToSingleObserver<T> implements FlowableSubscriber<T>, Disposable {
      volatile boolean disposed;
      boolean done;
      final SingleObserver<? super T> downstream;
      Subscription upstream;
      T value;

      ToSingleObserver(SingleObserver<? super T> var1) {
         this.downstream = var1;
      }

      @Override
      public void dispose() {
         this.disposed = true;
         this.upstream.cancel();
      }

      @Override
      public boolean isDisposed() {
         return this.disposed;
      }

      public void onComplete() {
         if (!this.done) {
            this.done = true;
            Object var1 = this.value;
            this.value = null;
            if (var1 == null) {
               this.downstream.onError(new NoSuchElementException("The source Publisher is empty"));
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
            this.value = null;
            this.downstream.onError(var1);
         }
      }

      public void onNext(T var1) {
         if (!this.done) {
            if (this.value != null) {
               this.upstream.cancel();
               this.done = true;
               this.value = null;
               this.downstream.onError(new IndexOutOfBoundsException("Too many elements in the Publisher"));
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
