package io.reactivex.internal.operators.parallel;

import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.schedulers.SchedulerMultiWorkerSupport;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.parallel.ParallelFlowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class ParallelRunOn<T> extends ParallelFlowable<T> {
   final int prefetch;
   final Scheduler scheduler;
   final ParallelFlowable<? extends T> source;

   public ParallelRunOn(ParallelFlowable<? extends T> var1, Scheduler var2, int var3) {
      this.source = var1;
      this.scheduler = var2;
      this.prefetch = var3;
   }

   void createSubscriber(int var1, Subscriber<? super T>[] var2, Subscriber<T>[] var3, Scheduler.Worker var4) {
      Subscriber var6 = var2[var1];
      SpscArrayQueue var5 = new SpscArrayQueue(this.prefetch);
      if (var6 instanceof ConditionalSubscriber) {
         var3[var1] = new ParallelRunOn.RunOnConditionalSubscriber((ConditionalSubscriber<? super T>)var6, this.prefetch, var5, var4);
      } else {
         var3[var1] = new ParallelRunOn.RunOnSubscriber(var6, this.prefetch, var5, var4);
      }
   }

   @Override
   public int parallelism() {
      return this.source.parallelism();
   }

   @Override
   public void subscribe(Subscriber<? super T>[] var1) {
      if (this.validate(var1)) {
         int var3 = var1.length;
         Subscriber[] var4 = new Subscriber[var3];
         Scheduler var5 = this.scheduler;
         if (var5 instanceof SchedulerMultiWorkerSupport) {
            ((SchedulerMultiWorkerSupport)var5).createWorkers(var3, new ParallelRunOn.MultiWorkerCallback(this, var1, var4));
         } else {
            for (int var2 = 0; var2 < var3; var2++) {
               this.createSubscriber(var2, var1, var4, this.scheduler.createWorker());
            }
         }

         this.source.subscribe(var4);
      }
   }

   abstract static class BaseRunOnSubscriber<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription, Runnable {
      private static final long serialVersionUID = 9222303586456402150L;
      volatile boolean cancelled;
      int consumed;
      volatile boolean done;
      Throwable error;
      final int limit;
      final int prefetch;
      final SpscArrayQueue<T> queue;
      final AtomicLong requested = new AtomicLong();
      Subscription upstream;
      final Scheduler.Worker worker;

      BaseRunOnSubscriber(int var1, SpscArrayQueue<T> var2, Scheduler.Worker var3) {
         this.prefetch = var1;
         this.queue = var2;
         this.limit = var1 - (var1 >> 2);
         this.worker = var3;
      }

      public final void cancel() {
         if (!this.cancelled) {
            this.cancelled = true;
            this.upstream.cancel();
            this.worker.dispose();
            if (this.getAndIncrement() == 0) {
               this.queue.clear();
            }
         }
      }

      public final void onComplete() {
         if (!this.done) {
            this.done = true;
            this.schedule();
         }
      }

      public final void onError(Throwable var1) {
         if (this.done) {
            RxJavaPlugins.onError(var1);
         } else {
            this.error = var1;
            this.done = true;
            this.schedule();
         }
      }

      public final void onNext(T var1) {
         if (!this.done) {
            if (!this.queue.offer((T)var1)) {
               this.upstream.cancel();
               this.onError(new MissingBackpressureException("Queue is full?!"));
            } else {
               this.schedule();
            }
         }
      }

      public final void request(long var1) {
         if (SubscriptionHelper.validate(var1)) {
            BackpressureHelper.add(this.requested, var1);
            this.schedule();
         }
      }

      final void schedule() {
         if (this.getAndIncrement() == 0) {
            this.worker.schedule(this);
         }
      }
   }

   final class MultiWorkerCallback implements SchedulerMultiWorkerSupport.WorkerCallback {
      final Subscriber<T>[] parents;
      final Subscriber<? super T>[] subscribers;
      final ParallelRunOn this$0;

      MultiWorkerCallback(Subscriber<? super T>[] var1, Subscriber<T>[] var2, Subscriber[] var3) {
         this.this$0 = var1;
         this.subscribers = var2;
         this.parents = var3;
      }

      @Override
      public void onWorker(int var1, Scheduler.Worker var2) {
         this.this$0.createSubscriber(var1, this.subscribers, this.parents, var2);
      }
   }

   static final class RunOnConditionalSubscriber<T> extends ParallelRunOn.BaseRunOnSubscriber<T> {
      private static final long serialVersionUID = 1075119423897941642L;
      final ConditionalSubscriber<? super T> downstream;

      RunOnConditionalSubscriber(ConditionalSubscriber<? super T> var1, int var2, SpscArrayQueue<T> var3, Scheduler.Worker var4) {
         super(var2, var3, var4);
         this.downstream = var1;
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
            var1.request(this.prefetch);
         }
      }

      @Override
      public void run() {
         int var2 = this.consumed;
         SpscArrayQueue var13 = this.queue;
         ConditionalSubscriber var14 = this.downstream;
         int var4 = this.limit;
         int var1 = 1;

         while (true) {
            long var10 = this.requested.get();
            long var8 = 0L;

            int var5;
            while (true) {
               long var21;
               var5 = (var21 = var8 - var10) == 0L ? 0 : (var21 < 0L ? -1 : 1);
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
                     this.worker.dispose();
                     return;
                  }
               }

               Object var19 = var13.poll();
               boolean var3;
               if (var19 == null) {
                  var3 = 1;
               } else {
                  var3 = 0;
               }

               if (var12 && var3) {
                  var14.onComplete();
                  this.worker.dispose();
                  return;
               }

               if (var3) {
                  break;
               }

               long var6 = var8;
               if (var14.tryOnNext(var19)) {
                  var6 = var8 + 1L;
               }

               var3 = var2 + 1;
               var2 = var3;
               var8 = var6;
               if (var3 == var4) {
                  this.upstream.request(var3);
                  var2 = 0;
                  var8 = var6;
               }
            }

            if (!var5) {
               if (this.cancelled) {
                  var13.clear();
                  return;
               }

               if (this.done) {
                  Throwable var20 = this.error;
                  if (var20 != null) {
                     var13.clear();
                     var14.onError(var20);
                     this.worker.dispose();
                     return;
                  }

                  if (var13.isEmpty()) {
                     var14.onComplete();
                     this.worker.dispose();
                     return;
                  }
               }
            }

            if (var8 != 0L && var10 != Long.MAX_VALUE) {
               this.requested.addAndGet(-var8);
            }

            int var17 = this.get();
            if (var17 == var1) {
               this.consumed = var2;
               var17 = this.addAndGet(-var1);
               var1 = var17;
               if (var17 == 0) {
                  return;
               }
            } else {
               var1 = var17;
            }
         }
      }
   }

   static final class RunOnSubscriber<T> extends ParallelRunOn.BaseRunOnSubscriber<T> {
      private static final long serialVersionUID = 1075119423897941642L;
      final Subscriber<? super T> downstream;

      RunOnSubscriber(Subscriber<? super T> var1, int var2, SpscArrayQueue<T> var3, Scheduler.Worker var4) {
         super(var2, var3, var4);
         this.downstream = var1;
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
            var1.request(this.prefetch);
         }
      }

      @Override
      public void run() {
         int var2 = this.consumed;
         SpscArrayQueue var14 = this.queue;
         Subscriber var13 = this.downstream;
         int var4 = this.limit;
         int var1 = 1;

         while (true) {
            long var10 = this.requested.get();
            long var6 = 0L;

            int var5;
            while (true) {
               long var21;
               var5 = (var21 = var6 - var10) == 0L ? 0 : (var21 < 0L ? -1 : 1);
               if (!var5) {
                  break;
               }

               if (this.cancelled) {
                  var14.clear();
                  return;
               }

               boolean var12 = this.done;
               if (var12) {
                  Throwable var15 = this.error;
                  if (var15 != null) {
                     var14.clear();
                     var13.onError(var15);
                     this.worker.dispose();
                     return;
                  }
               }

               Object var19 = var14.poll();
               boolean var3;
               if (var19 == null) {
                  var3 = 1;
               } else {
                  var3 = 0;
               }

               if (var12 && var3) {
                  var13.onComplete();
                  this.worker.dispose();
                  return;
               }

               if (var3) {
                  break;
               }

               var13.onNext(var19);
               long var8 = var6 + 1L;
               var3 = var2 + 1;
               var2 = var3;
               var6 = var8;
               if (var3 == var4) {
                  this.upstream.request(var3);
                  var2 = 0;
                  var6 = var8;
               }
            }

            if (!var5) {
               if (this.cancelled) {
                  var14.clear();
                  return;
               }

               if (this.done) {
                  Throwable var20 = this.error;
                  if (var20 != null) {
                     var14.clear();
                     var13.onError(var20);
                     this.worker.dispose();
                     return;
                  }

                  if (var14.isEmpty()) {
                     var13.onComplete();
                     this.worker.dispose();
                     return;
                  }
               }
            }

            if (var6 != 0L && var10 != Long.MAX_VALUE) {
               this.requested.addAndGet(-var6);
            }

            int var17 = this.get();
            if (var17 == var1) {
               this.consumed = var2;
               var17 = this.addAndGet(-var1);
               var1 = var17;
               if (var17 == 0) {
                  return;
               }
            } else {
               var1 = var17;
            }
         }
      }
   }
}
