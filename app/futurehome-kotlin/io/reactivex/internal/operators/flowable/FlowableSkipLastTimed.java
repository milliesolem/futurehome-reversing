package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableSkipLastTimed<T> extends AbstractFlowableWithUpstream<T, T> {
   final int bufferSize;
   final boolean delayError;
   final Scheduler scheduler;
   final long time;
   final TimeUnit unit;

   public FlowableSkipLastTimed(Flowable<T> var1, long var2, TimeUnit var4, Scheduler var5, int var6, boolean var7) {
      super(var1);
      this.time = var2;
      this.unit = var4;
      this.scheduler = var5;
      this.bufferSize = var6;
      this.delayError = var7;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      this.source.subscribe(new FlowableSkipLastTimed.SkipLastTimedSubscriber<>(var1, this.time, this.unit, this.scheduler, this.bufferSize, this.delayError));
   }

   static final class SkipLastTimedSubscriber<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
      private static final long serialVersionUID = -5677354903406201275L;
      volatile boolean cancelled;
      final boolean delayError;
      volatile boolean done;
      final Subscriber<? super T> downstream;
      Throwable error;
      final SpscLinkedArrayQueue<Object> queue;
      final AtomicLong requested = new AtomicLong();
      final Scheduler scheduler;
      final long time;
      final TimeUnit unit;
      Subscription upstream;

      SkipLastTimedSubscriber(Subscriber<? super T> var1, long var2, TimeUnit var4, Scheduler var5, int var6, boolean var7) {
         this.downstream = var1;
         this.time = var2;
         this.unit = var4;
         this.scheduler = var5;
         this.queue = new SpscLinkedArrayQueue<>(var6);
         this.delayError = var7;
      }

      public void cancel() {
         if (!this.cancelled) {
            this.cancelled = true;
            this.upstream.cancel();
            if (this.getAndIncrement() == 0) {
               this.queue.clear();
            }
         }
      }

      boolean checkTerminated(boolean var1, boolean var2, Subscriber<? super T> var3, boolean var4) {
         if (this.cancelled) {
            this.queue.clear();
            return true;
         } else {
            if (var1) {
               if (var4) {
                  if (var2) {
                     Throwable var5 = this.error;
                     if (var5 != null) {
                        var3.onError(var5);
                     } else {
                        var3.onComplete();
                     }

                     return true;
                  }
               } else {
                  Throwable var6 = this.error;
                  if (var6 != null) {
                     this.queue.clear();
                     var3.onError(var6);
                     return true;
                  }

                  if (var2) {
                     var3.onComplete();
                     return true;
                  }
               }
            }

            return false;
         }
      }

      void drain() {
         if (this.getAndIncrement() == 0) {
            Subscriber var16 = this.downstream;
            SpscLinkedArrayQueue var17 = this.queue;
            boolean var4 = this.delayError;
            TimeUnit var15 = this.unit;
            Scheduler var18 = this.scheduler;
            long var10 = this.time;
            int var1 = 1;

            int var2;
            do {
               long var8 = this.requested.get();

               long var6;
               for (var6 = 0L; var6 != var8; var6++) {
                  boolean var5 = this.done;
                  Long var14 = (Long)var17.peek();
                  boolean var3;
                  if (var14 == null) {
                     var3 = true;
                  } else {
                     var3 = false;
                  }

                  long var12 = var18.now(var15);
                  if (!var3 && var14 > var12 - var10) {
                     var3 = true;
                  }

                  if (this.checkTerminated(var5, var3, var16, var4)) {
                     return;
                  }

                  if (var3) {
                     break;
                  }

                  var17.poll();
                  var16.onNext(var17.poll());
               }

               if (var6 != 0L) {
                  BackpressureHelper.produced(this.requested, var6);
               }

               var2 = this.addAndGet(-var1);
               var1 = var2;
            } while (var2 != 0);
         }
      }

      public void onComplete() {
         this.done = true;
         this.drain();
      }

      public void onError(Throwable var1) {
         this.error = var1;
         this.done = true;
         this.drain();
      }

      public void onNext(T var1) {
         long var2 = this.scheduler.now(this.unit);
         this.queue.offer(var2, var1);
         this.drain();
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
            var1.request(Long.MAX_VALUE);
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
