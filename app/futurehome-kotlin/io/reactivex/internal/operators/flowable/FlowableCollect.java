package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiConsumer;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.DeferredScalarSubscription;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableCollect<T, U> extends AbstractFlowableWithUpstream<T, U> {
   final BiConsumer<? super U, ? super T> collector;
   final Callable<? extends U> initialSupplier;

   public FlowableCollect(Flowable<T> var1, Callable<? extends U> var2, BiConsumer<? super U, ? super T> var3) {
      super(var1);
      this.initialSupplier = var2;
      this.collector = var3;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   protected void subscribeActual(Subscriber<? super U> var1) {
      Object var2;
      try {
         var2 = ObjectHelper.requireNonNull(this.initialSupplier.call(), "The initial value supplied is null");
      } catch (Throwable var4) {
         EmptySubscription.error(var4, var1);
         return;
      }

      this.source.subscribe(new FlowableCollect.CollectSubscriber<>(var1, var2, this.collector));
   }

   static final class CollectSubscriber<T, U> extends DeferredScalarSubscription<U> implements FlowableSubscriber<T> {
      private static final long serialVersionUID = -3589550218733891694L;
      final BiConsumer<? super U, ? super T> collector;
      boolean done;
      final U u;
      Subscription upstream;

      CollectSubscriber(Subscriber<? super U> var1, U var2, BiConsumer<? super U, ? super T> var3) {
         super(var1);
         this.collector = var3;
         this.u = (U)var2;
      }

      @Override
      public void cancel() {
         super.cancel();
         this.upstream.cancel();
      }

      public void onComplete() {
         if (!this.done) {
            this.done = true;
            this.complete(this.u);
         }
      }

      public void onError(Throwable var1) {
         if (this.done) {
            RxJavaPlugins.onError(var1);
         } else {
            this.done = true;
            this.downstream.onError(var1);
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void onNext(T var1) {
         if (!this.done) {
            try {
               this.collector.accept(this.u, (T)var1);
            } catch (Throwable var3) {
               Exceptions.throwIfFatal(var3);
               this.upstream.cancel();
               this.onError(var3);
               return;
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
