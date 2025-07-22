package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.DeferredScalarSubscription;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import java.util.Collection;
import java.util.concurrent.Callable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableToList<T, U extends Collection<? super T>> extends AbstractFlowableWithUpstream<T, U> {
   final Callable<U> collectionSupplier;

   public FlowableToList(Flowable<T> var1, Callable<U> var2) {
      super(var1);
      this.collectionSupplier = var2;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   protected void subscribeActual(Subscriber<? super U> var1) {
      Collection var2;
      try {
         var2 = ObjectHelper.requireNonNull(
            this.collectionSupplier.call(),
            "The collectionSupplier returned a null collection. Null values are generally not allowed in 2.x operators and sources."
         );
      } catch (Throwable var4) {
         Exceptions.throwIfFatal(var4);
         EmptySubscription.error(var4, var1);
         return;
      }

      this.source.subscribe(new FlowableToList.ToListSubscriber<>(var1, (U)var2));
   }

   static final class ToListSubscriber<T, U extends Collection<? super T>> extends DeferredScalarSubscription<U> implements FlowableSubscriber<T>, Subscription {
      private static final long serialVersionUID = -8134157938864266736L;
      Subscription upstream;

      ToListSubscriber(Subscriber<? super U> var1, U var2) {
         super(var1);
         this.value = (U)var2;
      }

      @Override
      public void cancel() {
         super.cancel();
         this.upstream.cancel();
      }

      public void onComplete() {
         this.complete(this.value);
      }

      public void onError(Throwable var1) {
         this.value = null;
         this.downstream.onError(var1);
      }

      public void onNext(T var1) {
         Collection var2 = this.value;
         if (var2 != null) {
            var2.add(var1);
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
