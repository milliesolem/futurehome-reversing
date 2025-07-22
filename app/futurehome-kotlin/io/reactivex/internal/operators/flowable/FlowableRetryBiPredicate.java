package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiPredicate;
import io.reactivex.internal.subscriptions.SubscriptionArbiter;
import java.util.concurrent.atomic.AtomicInteger;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableRetryBiPredicate<T> extends AbstractFlowableWithUpstream<T, T> {
   final BiPredicate<? super Integer, ? super Throwable> predicate;

   public FlowableRetryBiPredicate(Flowable<T> var1, BiPredicate<? super Integer, ? super Throwable> var2) {
      super(var1);
      this.predicate = var2;
   }

   @Override
   public void subscribeActual(Subscriber<? super T> var1) {
      SubscriptionArbiter var2 = new SubscriptionArbiter(false);
      var1.onSubscribe(var2);
      new FlowableRetryBiPredicate.RetryBiSubscriber(var1, this.predicate, var2, this.source).subscribeNext();
   }

   static final class RetryBiSubscriber<T> extends AtomicInteger implements FlowableSubscriber<T> {
      private static final long serialVersionUID = -7098360935104053232L;
      final Subscriber<? super T> downstream;
      final BiPredicate<? super Integer, ? super Throwable> predicate;
      long produced;
      int retries;
      final SubscriptionArbiter sa;
      final Publisher<? extends T> source;

      RetryBiSubscriber(Subscriber<? super T> var1, BiPredicate<? super Integer, ? super Throwable> var2, SubscriptionArbiter var3, Publisher<? extends T> var4) {
         this.downstream = var1;
         this.sa = var3;
         this.source = var4;
         this.predicate = var2;
      }

      public void onComplete() {
         this.downstream.onComplete();
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void onError(Throwable var1) {
         boolean var3;
         try {
            BiPredicate var4 = this.predicate;
            int var2 = this.retries + 1;
            this.retries = var2;
            var3 = var4.test(var2, var1);
         } catch (Throwable var6) {
            Exceptions.throwIfFatal(var6);
            this.downstream.onError(new CompositeException(var1, var6));
            return;
         }

         if (!var3) {
            this.downstream.onError(var1);
         } else {
            this.subscribeNext();
         }
      }

      public void onNext(T var1) {
         this.produced++;
         this.downstream.onNext(var1);
      }

      @Override
      public void onSubscribe(Subscription var1) {
         this.sa.setSubscription(var1);
      }

      void subscribeNext() {
         if (this.getAndIncrement() == 0) {
            int var1 = 1;

            int var2;
            do {
               if (this.sa.isCancelled()) {
                  return;
               }

               long var3 = this.produced;
               if (var3 != 0L) {
                  this.produced = 0L;
                  this.sa.produced(var3);
               }

               this.source.subscribe(this);
               var2 = this.addAndGet(-var1);
               var1 = var2;
            } while (var2 != 0);
         }
      }
   }
}
