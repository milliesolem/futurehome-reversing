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

public final class FlowableTakeLastTimed<T> extends AbstractFlowableWithUpstream<T, T> {
   final int bufferSize;
   final long count;
   final boolean delayError;
   final Scheduler scheduler;
   final long time;
   final TimeUnit unit;

   public FlowableTakeLastTimed(Flowable<T> var1, long var2, long var4, TimeUnit var6, Scheduler var7, int var8, boolean var9) {
      super(var1);
      this.count = var2;
      this.time = var4;
      this.unit = var6;
      this.scheduler = var7;
      this.bufferSize = var8;
      this.delayError = var9;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      this.source
         .subscribe(
            new FlowableTakeLastTimed.TakeLastTimedSubscriber<>(var1, this.count, this.time, this.unit, this.scheduler, this.bufferSize, this.delayError)
         );
   }

   static final class TakeLastTimedSubscriber<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
      private static final long serialVersionUID = -5677354903406201275L;
      volatile boolean cancelled;
      final long count;
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

      TakeLastTimedSubscriber(Subscriber<? super T> var1, long var2, long var4, TimeUnit var6, Scheduler var7, int var8, boolean var9) {
         this.downstream = var1;
         this.count = var2;
         this.time = var4;
         this.unit = var6;
         this.scheduler = var7;
         this.queue = new SpscLinkedArrayQueue<>(var8);
         this.delayError = var9;
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

      boolean checkTerminated(boolean var1, Subscriber<? super T> var2, boolean var3) {
         if (this.cancelled) {
            this.queue.clear();
            return true;
         } else {
            if (var3) {
               if (var1) {
                  Throwable var4 = this.error;
                  if (var4 != null) {
                     var2.onError(var4);
                  } else {
                     var2.onComplete();
                  }

                  return true;
               }
            } else {
               Throwable var5 = this.error;
               if (var5 != null) {
                  this.queue.clear();
                  var2.onError(var5);
                  return true;
               }

               if (var1) {
                  var2.onComplete();
                  return true;
               }
            }

            return false;
         }
      }

      void drain() {
         if (this.getAndIncrement() == 0) {
            Subscriber var10 = this.downstream;
            SpscLinkedArrayQueue var9 = this.queue;
            boolean var8 = this.delayError;
            int var1 = 1;

            int var2;
            do {
               if (this.done) {
                  if (this.checkTerminated(var9.isEmpty(), var10, var8)) {
                     return;
                  }

                  long var5 = this.requested.get();
                  long var3 = 0L;

                  while (true) {
                     boolean var7;
                     if (var9.peek() == null) {
                        var7 = true;
                     } else {
                        var7 = false;
                     }

                     if (this.checkTerminated(var7, var10, var8)) {
                        return;
                     }

                     if (var5 == var3) {
                        if (var3 != 0L) {
                           BackpressureHelper.produced(this.requested, var3);
                        }
                        break;
                     }

                     var9.poll();
                     var10.onNext(var9.poll());
                     var3++;
                  }
               }

               var2 = this.addAndGet(-var1);
               var1 = var2;
            } while (var2 != 0);
         }
      }

      public void onComplete() {
         this.trim(this.scheduler.now(this.unit), this.queue);
         this.done = true;
         this.drain();
      }

      public void onError(Throwable var1) {
         if (this.delayError) {
            this.trim(this.scheduler.now(this.unit), this.queue);
         }

         this.error = var1;
         this.done = true;
         this.drain();
      }

      public void onNext(T var1) {
         SpscLinkedArrayQueue var4 = this.queue;
         long var2 = this.scheduler.now(this.unit);
         var4.offer(var2, (Long)var1);
         this.trim(var2, var4);
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

      void trim(long var1, SpscLinkedArrayQueue<Object> var3) {
         long var5 = this.time;
         long var7 = this.count;
         boolean var4;
         if (var7 == Long.MAX_VALUE) {
            var4 = true;
         } else {
            var4 = false;
         }

         while (!var3.isEmpty() && (var3.peek() < var1 - var5 || !var4 && var3.size() >> 1 > var7)) {
            var3.poll();
            var3.poll();
         }
      }
   }
}
