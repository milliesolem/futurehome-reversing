package io.reactivex.internal.observers;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class SubscriberCompletableObserver<T> implements CompletableObserver, Subscription {
   final Subscriber<? super T> subscriber;
   Disposable upstream;

   public SubscriberCompletableObserver(Subscriber<? super T> var1) {
      this.subscriber = var1;
   }

   public void cancel() {
      this.upstream.dispose();
   }

   @Override
   public void onComplete() {
      this.subscriber.onComplete();
   }

   @Override
   public void onError(Throwable var1) {
      this.subscriber.onError(var1);
   }

   @Override
   public void onSubscribe(Disposable var1) {
      if (DisposableHelper.validate(this.upstream, var1)) {
         this.upstream = var1;
         this.subscriber.onSubscribe(this);
      }
   }

   public void request(long var1) {
   }
}
