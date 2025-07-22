package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableScan<T> extends AbstractFlowableWithUpstream<T, T> {
   final BiFunction<T, T, T> accumulator;

   public FlowableScan(Flowable<T> var1, BiFunction<T, T, T> var2) {
      super(var1);
      this.accumulator = var2;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      this.source.subscribe(new FlowableScan.ScanSubscriber<>(var1, this.accumulator));
   }

   static final class ScanSubscriber<T> implements FlowableSubscriber<T>, Subscription {
      final BiFunction<T, T, T> accumulator;
      boolean done;
      final Subscriber<? super T> downstream;
      Subscription upstream;
      T value;

      ScanSubscriber(Subscriber<? super T> var1, BiFunction<T, T, T> var2) {
         this.downstream = var1;
         this.accumulator = var2;
      }

      public void cancel() {
         this.upstream.cancel();
      }

      public void onComplete() {
         if (!this.done) {
            this.done = true;
            this.downstream.onComplete();
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
            Subscriber var2 = this.downstream;
            Object var3 = this.value;
            if (var3 == null) {
               this.value = (T)var1;
               var2.onNext(var1);
            } else {
               try {
                  var1 = ObjectHelper.requireNonNull(this.accumulator.apply((T)var3, (T)var1), "The value returned by the accumulator is null");
               } catch (Throwable var5) {
                  Exceptions.throwIfFatal(var5);
                  this.upstream.cancel();
                  this.onError(var5);
                  return;
               }

               this.value = (T)var1;
               var2.onNext(var1);
            }
         }
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
         }
      }

      public void request(long var1) {
         this.upstream.request(var1);
      }
   }
}
