package io.reactivex.disposables;

import org.reactivestreams.Subscription;

final class SubscriptionDisposable extends ReferenceDisposable<Subscription> {
   private static final long serialVersionUID = -707001650852963139L;

   SubscriptionDisposable(Subscription var1) {
      super(var1);
   }

   protected void onDisposed(Subscription var1) {
      var1.cancel();
   }
}
