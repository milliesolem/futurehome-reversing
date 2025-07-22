package io.reactivex.internal.subscribers;

import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BlockingHelper;
import io.reactivex.internal.util.ExceptionHelper;
import java.util.concurrent.CountDownLatch;
import org.reactivestreams.Subscription;

public abstract class BlockingBaseSubscriber<T> extends CountDownLatch implements FlowableSubscriber<T> {
   volatile boolean cancelled;
   Throwable error;
   Subscription upstream;
   T value;

   public BlockingBaseSubscriber() {
      super(1);
   }

   public final T blockingGet() {
      if (this.getCount() != 0L) {
         try {
            BlockingHelper.verifyNonBlocking();
            this.await();
         } catch (InterruptedException var3) {
            Subscription var1 = this.upstream;
            this.upstream = SubscriptionHelper.CANCELLED;
            if (var1 != null) {
               var1.cancel();
            }

            throw ExceptionHelper.wrapOrThrow(var3);
         }
      }

      Throwable var4 = this.error;
      if (var4 == null) {
         return this.value;
      } else {
         throw ExceptionHelper.wrapOrThrow(var4);
      }
   }

   public final void onComplete() {
      this.countDown();
   }

   @Override
   public final void onSubscribe(Subscription var1) {
      if (SubscriptionHelper.validate(this.upstream, var1)) {
         this.upstream = var1;
         if (!this.cancelled) {
            var1.request(Long.MAX_VALUE);
            if (this.cancelled) {
               this.upstream = SubscriptionHelper.CANCELLED;
               var1.cancel();
            }
         }
      }
   }
}
