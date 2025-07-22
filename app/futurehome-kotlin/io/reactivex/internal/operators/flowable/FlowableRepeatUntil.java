package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.internal.subscriptions.SubscriptionArbiter;
import java.util.concurrent.atomic.AtomicInteger;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableRepeatUntil<T> extends AbstractFlowableWithUpstream<T, T> {
   final BooleanSupplier until;

   public FlowableRepeatUntil(Flowable<T> var1, BooleanSupplier var2) {
      super(var1);
      this.until = var2;
   }

   @Override
   public void subscribeActual(Subscriber<? super T> var1) {
      SubscriptionArbiter var2 = new SubscriptionArbiter(false);
      var1.onSubscribe(var2);
      new FlowableRepeatUntil.RepeatSubscriber(var1, this.until, var2, this.source).subscribeNext();
   }

   static final class RepeatSubscriber<T> extends AtomicInteger implements FlowableSubscriber<T> {
      private static final long serialVersionUID = -7098360935104053232L;
      final Subscriber<? super T> downstream;
      long produced;
      final SubscriptionArbiter sa;
      final Publisher<? extends T> source;
      final BooleanSupplier stop;

      RepeatSubscriber(Subscriber<? super T> var1, BooleanSupplier var2, SubscriptionArbiter var3, Publisher<? extends T> var4) {
         this.downstream = var1;
         this.sa = var3;
         this.source = var4;
         this.stop = var2;
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void onComplete() {
         boolean var1;
         try {
            var1 = this.stop.getAsBoolean();
         } catch (Throwable var4) {
            Exceptions.throwIfFatal(var4);
            this.downstream.onError(var4);
            return;
         }

         if (var1) {
            this.downstream.onComplete();
         } else {
            this.subscribeNext();
         }
      }

      public void onError(Throwable var1) {
         this.downstream.onError(var1);
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
