package io.reactivex.subscribers;

import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.EndConsumerHelper;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;

public abstract class DisposableSubscriber<T> implements FlowableSubscriber<T>, Disposable {
   final AtomicReference<Subscription> upstream = new AtomicReference<>();

   protected final void cancel() {
      this.dispose();
   }

   @Override
   public final void dispose() {
      SubscriptionHelper.cancel(this.upstream);
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
      this.upstream.get().request(Long.MAX_VALUE);
   }

   @Override
   public final void onSubscribe(Subscription var1) {
      if (EndConsumerHelper.setOnce(this.upstream, var1, this.getClass())) {
         this.onStart();
      }
   }

   protected final void request(long var1) {
      this.upstream.get().request(var1);
   }
}
