package io.reactivex.internal.subscriptions;

import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;

public final class AsyncSubscription extends AtomicLong implements Subscription, Disposable {
   private static final long serialVersionUID = 7028635084060361255L;
   final AtomicReference<Subscription> actual;
   final AtomicReference<Disposable> resource = new AtomicReference<>();

   public AsyncSubscription() {
      this.actual = new AtomicReference<>();
   }

   public AsyncSubscription(Disposable var1) {
      this();
      this.resource.lazySet(var1);
   }

   public void cancel() {
      this.dispose();
   }

   @Override
   public void dispose() {
      SubscriptionHelper.cancel(this.actual);
      DisposableHelper.dispose(this.resource);
   }

   @Override
   public boolean isDisposed() {
      boolean var1;
      if (this.actual.get() == SubscriptionHelper.CANCELLED) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean replaceResource(Disposable var1) {
      return DisposableHelper.replace(this.resource, var1);
   }

   public void request(long var1) {
      SubscriptionHelper.deferredRequest(this.actual, this, var1);
   }

   public boolean setResource(Disposable var1) {
      return DisposableHelper.set(this.resource, var1);
   }

   public void setSubscription(Subscription var1) {
      SubscriptionHelper.deferredSetOnce(this.actual, this, var1);
   }
}
