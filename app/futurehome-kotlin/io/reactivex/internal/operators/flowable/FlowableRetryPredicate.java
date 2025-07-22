package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.subscriptions.SubscriptionArbiter;
import java.util.concurrent.atomic.AtomicInteger;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableRetryPredicate<T> extends AbstractFlowableWithUpstream<T, T> {
   final long count;
   final Predicate<? super Throwable> predicate;

   public FlowableRetryPredicate(Flowable<T> var1, long var2, Predicate<? super Throwable> var4) {
      super(var1);
      this.predicate = var4;
      this.count = var2;
   }

   @Override
   public void subscribeActual(Subscriber<? super T> var1) {
      SubscriptionArbiter var2 = new SubscriptionArbiter(false);
      var1.onSubscribe(var2);
      new FlowableRetryPredicate.RetrySubscriber(var1, this.count, this.predicate, var2, this.source).subscribeNext();
   }

   static final class RetrySubscriber<T> extends AtomicInteger implements FlowableSubscriber<T> {
      private static final long serialVersionUID = -7098360935104053232L;
      final Subscriber<? super T> downstream;
      final Predicate<? super Throwable> predicate;
      long produced;
      long remaining;
      final SubscriptionArbiter sa;
      final Publisher<? extends T> source;

      RetrySubscriber(Subscriber<? super T> var1, long var2, Predicate<? super Throwable> var4, SubscriptionArbiter var5, Publisher<? extends T> var6) {
         this.downstream = var1;
         this.sa = var5;
         this.source = var6;
         this.predicate = var4;
         this.remaining = var2;
      }

      public void onComplete() {
         this.downstream.onComplete();
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void onError(Throwable var1) {
         long var2 = this.remaining;
         if (var2 != Long.MAX_VALUE) {
            this.remaining = var2 - 1L;
         }

         if (var2 == 0L) {
            this.downstream.onError(var1);
         } else {
            boolean var4;
            try {
               var4 = this.predicate.test(var1);
            } catch (Throwable var7) {
               Exceptions.throwIfFatal(var7);
               this.downstream.onError(new CompositeException(var1, var7));
               return;
            }

            if (!var4) {
               this.downstream.onError(var1);
               return;
            }

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
