package io.reactivex.subscribers;

import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.ListCompositeDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.EndConsumerHelper;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;

public abstract class ResourceSubscriber<T> implements FlowableSubscriber<T>, Disposable {
   private final AtomicLong missedRequested;
   private final ListCompositeDisposable resources;
   private final AtomicReference<Subscription> upstream = new AtomicReference<>();

   public ResourceSubscriber() {
      this.resources = new ListCompositeDisposable();
      this.missedRequested = new AtomicLong();
   }

   public final void add(Disposable var1) {
      ObjectHelper.requireNonNull(var1, "resource is null");
      this.resources.add(var1);
   }

   @Override
   public final void dispose() {
      if (SubscriptionHelper.cancel(this.upstream)) {
         this.resources.dispose();
      }
   }

   @Override
   public final boolean isDisposed() {
      boolean var1;
      if (this.upstream.get() == SubscriptionHelper.CANCELLED) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   protected void onStart() {
      this.request(Long.MAX_VALUE);
   }

   @Override
   public final void onSubscribe(Subscription var1) {
      if (EndConsumerHelper.setOnce(this.upstream, var1, this.getClass())) {
         long var2 = this.missedRequested.getAndSet(0L);
         if (var2 != 0L) {
            var1.request(var2);
         }

         this.onStart();
      }
   }

   protected final void request(long var1) {
      SubscriptionHelper.deferredRequest(this.upstream, this.missedRequested, var1);
   }
}
