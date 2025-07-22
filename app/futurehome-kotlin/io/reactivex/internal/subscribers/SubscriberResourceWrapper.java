package io.reactivex.internal.subscribers;

import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class SubscriberResourceWrapper<T> extends AtomicReference<Disposable> implements FlowableSubscriber<T>, Disposable, Subscription {
   private static final long serialVersionUID = -8612022020200669122L;
   final Subscriber<? super T> downstream;
   final AtomicReference<Subscription> upstream = new AtomicReference<>();

   public SubscriberResourceWrapper(Subscriber<? super T> var1) {
      this.downstream = var1;
   }

   public void cancel() {
      this.dispose();
   }

   @Override
   public void dispose() {
      SubscriptionHelper.cancel(this.upstream);
      DisposableHelper.dispose(this);
   }

   @Override
   public boolean isDisposed() {
      boolean var1;
      if (this.upstream.get() == SubscriptionHelper.CANCELLED) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void onComplete() {
      DisposableHelper.dispose(this);
      this.downstream.onComplete();
   }

   public void onError(Throwable var1) {
      DisposableHelper.dispose(this);
      this.downstream.onError(var1);
   }

   public void onNext(T var1) {
      this.downstream.onNext(var1);
   }

   @Override
   public void onSubscribe(Subscription var1) {
      if (SubscriptionHelper.setOnce(this.upstream, var1)) {
         this.downstream.onSubscribe(this);
      }
   }

   public void request(long var1) {
      if (SubscriptionHelper.validate(var1)) {
         this.upstream.get().request(var1);
      }
   }

   public void setResource(Disposable var1) {
      DisposableHelper.set(this, var1);
   }
}
