package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableScanSeed<T, R> extends AbstractFlowableWithUpstream<T, R> {
   final BiFunction<R, ? super T, R> accumulator;
   final Callable<R> seedSupplier;

   public FlowableScanSeed(Flowable<T> var1, Callable<R> var2, BiFunction<R, ? super T, R> var3) {
      super(var1);
      this.accumulator = var3;
      this.seedSupplier = var2;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   protected void subscribeActual(Subscriber<? super R> var1) {
      Object var2;
      try {
         var2 = ObjectHelper.requireNonNull(this.seedSupplier.call(), "The seed supplied is null");
      } catch (Throwable var4) {
         Exceptions.throwIfFatal(var4);
         EmptySubscription.error(var4, var1);
         return;
      }

      this.source.subscribe(new FlowableScanSeed.ScanSeedSubscriber<>(var1, this.accumulator, (R)var2, bufferSize()));
   }

   static final class ScanSeedSubscriber<T, R> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
      private static final long serialVersionUID = -1776795561228106469L;
      final BiFunction<R, ? super T, R> accumulator;
      volatile boolean cancelled;
      int consumed;
      volatile boolean done;
      final Subscriber<? super R> downstream;
      Throwable error;
      final int limit;
      final int prefetch;
      final SimplePlainQueue<R> queue;
      final AtomicLong requested;
      Subscription upstream;
      R value;

      ScanSeedSubscriber(Subscriber<? super R> var1, BiFunction<R, ? super T, R> var2, R var3, int var4) {
         this.downstream = var1;
         this.accumulator = var2;
         this.value = (R)var3;
         this.prefetch = var4;
         this.limit = var4 - (var4 >> 2);
         SpscArrayQueue var5 = new SpscArrayQueue(var4);
         this.queue = var5;
         var5.offer(var3);
         this.requested = new AtomicLong();
      }

      public void cancel() {
         this.cancelled = true;
         this.upstream.cancel();
         if (this.getAndIncrement() == 0) {
            this.queue.clear();
         }
      }

      void drain() {
         if (this.getAndIncrement() == 0) {
            Subscriber var14 = this.downstream;
            SimplePlainQueue var13 = this.queue;
            int var4 = this.limit;
            int var1 = this.consumed;
            int var2 = 1;

            int var17;
            do {
               long var10 = this.requested.get();
               long var6 = 0L;

               int var5;
               while (true) {
                  long var20;
                  var5 = (var20 = var6 - var10) == 0L ? 0 : (var20 < 0L ? -1 : 1);
                  if (!var5) {
                     break;
                  }

                  if (this.cancelled) {
                     var13.clear();
                     return;
                  }

                  boolean var12 = this.done;
                  if (var12) {
                     Throwable var15 = this.error;
                     if (var15 != null) {
                        var13.clear();
                        var14.onError(var15);
                        return;
                     }
                  }

                  Object var18 = var13.poll();
                  boolean var3;
                  if (var18 == null) {
                     var3 = 1;
                  } else {
                     var3 = 0;
                  }

                  if (var12 && var3) {
                     var14.onComplete();
                     return;
                  }

                  if (var3) {
                     break;
                  }

                  var14.onNext(var18);
                  long var8 = var6 + 1L;
                  var3 = var1 + 1;
                  var1 = var3;
                  var6 = var8;
                  if (var3 == var4) {
                     this.upstream.request(var4);
                     var1 = 0;
                     var6 = var8;
                  }
               }

               if (!var5 && this.done) {
                  Throwable var19 = this.error;
                  if (var19 != null) {
                     var13.clear();
                     var14.onError(var19);
                     return;
                  }

                  if (var13.isEmpty()) {
                     var14.onComplete();
                     return;
                  }
               }

               if (var6 != 0L) {
                  BackpressureHelper.produced(this.requested, var6);
               }

               this.consumed = var1;
               var17 = this.addAndGet(-var2);
               var2 = var17;
            } while (var17 != 0);
         }
      }

      public void onComplete() {
         if (!this.done) {
            this.done = true;
            this.drain();
         }
      }

      public void onError(Throwable var1) {
         if (this.done) {
            RxJavaPlugins.onError(var1);
         } else {
            this.error = var1;
            this.done = true;
            this.drain();
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void onNext(T var1) {
         if (!this.done) {
            Object var2 = this.value;

            try {
               var1 = ObjectHelper.requireNonNull(this.accumulator.apply((R)var2, (T)var1), "The accumulator returned a null value");
            } catch (Throwable var4) {
               Exceptions.throwIfFatal(var4);
               this.upstream.cancel();
               this.onError(var4);
               return;
            }

            this.value = (R)var1;
            this.queue.offer((R)var1);
            this.drain();
         }
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
            var1.request(this.prefetch - 1);
         }
      }

      public void request(long var1) {
         if (SubscriptionHelper.validate(var1)) {
            BackpressureHelper.add(this.requested, var1);
            this.drain();
         }
      }
   }
}
