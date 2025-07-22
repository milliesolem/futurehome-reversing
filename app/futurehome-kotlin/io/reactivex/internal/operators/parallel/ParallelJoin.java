package io.reactivex.internal.operators.parallel;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.parallel.ParallelFlowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class ParallelJoin<T> extends Flowable<T> {
   final boolean delayErrors;
   final int prefetch;
   final ParallelFlowable<? extends T> source;

   public ParallelJoin(ParallelFlowable<? extends T> var1, int var2, boolean var3) {
      this.source = var1;
      this.prefetch = var2;
      this.delayErrors = var3;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      Object var2;
      if (this.delayErrors) {
         var2 = new ParallelJoin.JoinSubscriptionDelayError(var1, this.source.parallelism(), this.prefetch);
      } else {
         var2 = new ParallelJoin.JoinSubscription(var1, this.source.parallelism(), this.prefetch);
      }

      var1.onSubscribe((Subscription)var2);
      this.source.subscribe(((ParallelJoin.JoinSubscriptionBase)var2).subscribers);
   }

   static final class JoinInnerSubscriber<T> extends AtomicReference<Subscription> implements FlowableSubscriber<T> {
      private static final long serialVersionUID = 8410034718427740355L;
      final int limit;
      final ParallelJoin.JoinSubscriptionBase<T> parent;
      final int prefetch;
      long produced;
      volatile SimplePlainQueue<T> queue;

      JoinInnerSubscriber(ParallelJoin.JoinSubscriptionBase<T> var1, int var2) {
         this.parent = var1;
         this.prefetch = var2;
         this.limit = var2 - (var2 >> 2);
      }

      public boolean cancel() {
         return SubscriptionHelper.cancel(this);
      }

      SimplePlainQueue<T> getQueue() {
         SimplePlainQueue var2 = this.queue;
         Object var1 = var2;
         if (var2 == null) {
            var1 = new SpscArrayQueue(this.prefetch);
            this.queue = (SimplePlainQueue<T>)var1;
         }

         return (SimplePlainQueue<T>)var1;
      }

      public void onComplete() {
         this.parent.onComplete();
      }

      public void onError(Throwable var1) {
         this.parent.onError(var1);
      }

      public void onNext(T var1) {
         this.parent.onNext(this, (T)var1);
      }

      @Override
      public void onSubscribe(Subscription var1) {
         SubscriptionHelper.setOnce(this, var1, this.prefetch);
      }

      public void request(long var1) {
         var1 = this.produced + var1;
         if (var1 >= this.limit) {
            this.produced = 0L;
            this.get().request(var1);
         } else {
            this.produced = var1;
         }
      }

      public void requestOne() {
         long var1 = this.produced + 1L;
         if (var1 == this.limit) {
            this.produced = 0L;
            this.get().request(var1);
         } else {
            this.produced = var1;
         }
      }
   }

   static final class JoinSubscription<T> extends ParallelJoin.JoinSubscriptionBase<T> {
      private static final long serialVersionUID = 6312374661811000451L;

      JoinSubscription(Subscriber<? super T> var1, int var2, int var3) {
         super(var1, var2, var3);
      }

      @Override
      void drain() {
         if (this.getAndIncrement() == 0) {
            this.drainLoop();
         }
      }

      void drainLoop() {
         ParallelJoin.JoinInnerSubscriber[] var13 = this.subscribers;
         int var6 = var13.length;
         Subscriber var14 = this.downstream;
         int var2 = 1;

         while (true) {
            long var11 = this.requested.get();
            long var9 = 0L;

            boolean var5;
            long var7;
            label89:
            do {
               var7 = var9;
               if (var9 == var11) {
                  break;
               }

               if (this.cancelled) {
                  this.cleanup();
                  return;
               }

               Throwable var15 = this.errors.get();
               if (var15 != null) {
                  this.cleanup();
                  var14.onError(var15);
                  return;
               }

               boolean var1;
               if (this.done.get() == 0) {
                  var1 = true;
               } else {
                  var1 = false;
               }

               int var3 = 0;
               var5 = true;
               var7 = var9;

               while (var3 < var13.length) {
                  ParallelJoin.JoinInnerSubscriber var24 = var13[var3];
                  SimplePlainQueue var16 = var24.queue;
                  var9 = var7;
                  boolean var4 = var5;
                  if (var16 != null) {
                     Object var27 = var16.poll();
                     var9 = var7;
                     var4 = var5;
                     if (var27 != null) {
                        var14.onNext(var27);
                        var24.requestOne();
                        if (++var7 == var11) {
                           break label89;
                        }

                        var4 = false;
                        var9 = var7;
                     }
                  }

                  var3++;
                  var7 = var9;
                  var5 = var4;
               }

               if (var1 && var5) {
                  var14.onComplete();
                  return;
               }

               var9 = var7;
            } while (!var5);

            if (var7 == var11) {
               if (this.cancelled) {
                  this.cleanup();
                  return;
               }

               Throwable var25 = this.errors.get();
               if (var25 != null) {
                  this.cleanup();
                  var14.onError(var25);
                  return;
               }

               boolean var17;
               if (this.done.get() == 0) {
                  var17 = true;
               } else {
                  var17 = false;
               }

               int var20 = 0;

               while (true) {
                  if (var20 >= var6) {
                     var21 = true;
                     break;
                  }

                  SimplePlainQueue var26 = var13[var20].queue;
                  if (var26 != null && !var26.isEmpty()) {
                     var21 = false;
                     break;
                  }

                  var20++;
               }

               if (var17 && var21) {
                  var14.onComplete();
                  return;
               }
            }

            if (var7 != 0L && var11 != Long.MAX_VALUE) {
               this.requested.addAndGet(-var7);
            }

            int var22 = this.get();
            int var18 = var22;
            if (var22 == var2) {
               var2 = this.addAndGet(-var2);
               var18 = var2;
               if (var2 == 0) {
                  return;
               }
            }

            var2 = var18;
         }
      }

      @Override
      public void onComplete() {
         this.done.decrementAndGet();
         this.drain();
      }

      @Override
      public void onError(Throwable var1) {
         if (this.errors.compareAndSet(null, var1)) {
            this.cancelAll();
            this.drain();
         } else if (var1 != this.errors.get()) {
            RxJavaPlugins.onError(var1);
         }
      }

      @Override
      public void onNext(ParallelJoin.JoinInnerSubscriber<T> var1, T var2) {
         if (this.get() == 0 && this.compareAndSet(0, 1)) {
            if (this.requested.get() != 0L) {
               this.downstream.onNext(var2);
               if (this.requested.get() != Long.MAX_VALUE) {
                  this.requested.decrementAndGet();
               }

               var1.request(1L);
            } else if (!var1.getQueue().offer(var2)) {
               this.cancelAll();
               MissingBackpressureException var3 = new MissingBackpressureException("Queue full?!");
               if (this.errors.compareAndSet(null, var3)) {
                  this.downstream.onError(var3);
               } else {
                  RxJavaPlugins.onError(var3);
               }

               return;
            }

            if (this.decrementAndGet() == 0) {
               return;
            }
         } else {
            if (!var1.getQueue().offer(var2)) {
               this.cancelAll();
               this.onError(new MissingBackpressureException("Queue full?!"));
               return;
            }

            if (this.getAndIncrement() != 0) {
               return;
            }
         }

         this.drainLoop();
      }
   }

   abstract static class JoinSubscriptionBase<T> extends AtomicInteger implements Subscription {
      private static final long serialVersionUID = 3100232009247827843L;
      volatile boolean cancelled;
      final AtomicInteger done;
      final Subscriber<? super T> downstream;
      final AtomicThrowable errors = new AtomicThrowable();
      final AtomicLong requested = new AtomicLong();
      final ParallelJoin.JoinInnerSubscriber<T>[] subscribers;

      JoinSubscriptionBase(Subscriber<? super T> var1, int var2, int var3) {
         this.done = new AtomicInteger();
         this.downstream = var1;
         ParallelJoin.JoinInnerSubscriber[] var5 = new ParallelJoin.JoinInnerSubscriber[var2];

         for (int var4 = 0; var4 < var2; var4++) {
            var5[var4] = new ParallelJoin.JoinInnerSubscriber<>(this, var3);
         }

         this.subscribers = var5;
         this.done.lazySet(var2);
      }

      public void cancel() {
         if (!this.cancelled) {
            this.cancelled = true;
            this.cancelAll();
            if (this.getAndIncrement() == 0) {
               this.cleanup();
            }
         }
      }

      void cancelAll() {
         ParallelJoin.JoinInnerSubscriber[] var3 = this.subscribers;
         int var2 = var3.length;

         for (int var1 = 0; var1 < var2; var1++) {
            var3[var1].cancel();
         }
      }

      void cleanup() {
         ParallelJoin.JoinInnerSubscriber[] var3 = this.subscribers;
         int var2 = var3.length;

         for (int var1 = 0; var1 < var2; var1++) {
            var3[var1].queue = null;
         }
      }

      abstract void drain();

      abstract void onComplete();

      abstract void onError(Throwable var1);

      abstract void onNext(ParallelJoin.JoinInnerSubscriber<T> var1, T var2);

      public void request(long var1) {
         if (SubscriptionHelper.validate(var1)) {
            BackpressureHelper.add(this.requested, var1);
            this.drain();
         }
      }
   }

   static final class JoinSubscriptionDelayError<T> extends ParallelJoin.JoinSubscriptionBase<T> {
      private static final long serialVersionUID = -5737965195918321883L;

      JoinSubscriptionDelayError(Subscriber<? super T> var1, int var2, int var3) {
         super(var1, var2, var3);
      }

      @Override
      void drain() {
         if (this.getAndIncrement() == 0) {
            this.drainLoop();
         }
      }

      void drainLoop() {
         ParallelJoin.JoinInnerSubscriber[] var14 = this.subscribers;
         int var6 = var14.length;
         Subscriber var13 = this.downstream;
         int var2 = 1;

         while (true) {
            long var11 = this.requested.get();
            long var7 = 0L;

            boolean var5;
            long var9;
            label91:
            do {
               var9 = var7;
               if (var7 == var11) {
                  break;
               }

               if (this.cancelled) {
                  this.cleanup();
                  return;
               }

               boolean var1;
               if (this.done.get() == 0) {
                  var1 = true;
               } else {
                  var1 = false;
               }

               int var4 = 0;
               var5 = true;
               var9 = var7;

               while (var4 < var6) {
                  ParallelJoin.JoinInnerSubscriber var15 = var14[var4];
                  SimplePlainQueue var16 = var15.queue;
                  var7 = var9;
                  boolean var3 = var5;
                  if (var16 != null) {
                     Object var25 = var16.poll();
                     var7 = var9;
                     var3 = var5;
                     if (var25 != null) {
                        var13.onNext(var25);
                        var15.requestOne();
                        var7 = var9 + 1L;
                        if (var7 == var11) {
                           var9 = var7;
                           break label91;
                        }

                        var3 = false;
                     }
                  }

                  var4++;
                  var9 = var7;
                  var5 = var3;
               }

               if (var1 && var5) {
                  if (this.errors.get() != null) {
                     var13.onError(this.errors.terminate());
                  } else {
                     var13.onComplete();
                  }

                  return;
               }

               var7 = var9;
            } while (!var5);

            if (var9 == var11) {
               if (this.cancelled) {
                  this.cleanup();
                  return;
               }

               boolean var17;
               if (this.done.get() == 0) {
                  var17 = true;
               } else {
                  var17 = false;
               }

               int var20 = 0;

               while (true) {
                  if (var20 >= var6) {
                     var21 = true;
                     break;
                  }

                  SimplePlainQueue var24 = var14[var20].queue;
                  if (var24 != null && !var24.isEmpty()) {
                     var21 = false;
                     break;
                  }

                  var20++;
               }

               if (var17 && var21) {
                  if (this.errors.get() != null) {
                     var13.onError(this.errors.terminate());
                  } else {
                     var13.onComplete();
                  }

                  return;
               }
            }

            if (var9 != 0L && var11 != Long.MAX_VALUE) {
               this.requested.addAndGet(-var9);
            }

            int var22 = this.get();
            int var18 = var22;
            if (var22 == var2) {
               var2 = this.addAndGet(-var2);
               var18 = var2;
               if (var2 == 0) {
                  return;
               }
            }

            var2 = var18;
         }
      }

      @Override
      void onComplete() {
         this.done.decrementAndGet();
         this.drain();
      }

      @Override
      void onError(Throwable var1) {
         this.errors.addThrowable(var1);
         this.done.decrementAndGet();
         this.drain();
      }

      @Override
      void onNext(ParallelJoin.JoinInnerSubscriber<T> var1, T var2) {
         if (this.get() == 0 && this.compareAndSet(0, 1)) {
            if (this.requested.get() != 0L) {
               this.downstream.onNext(var2);
               if (this.requested.get() != Long.MAX_VALUE) {
                  this.requested.decrementAndGet();
               }

               var1.request(1L);
            } else if (!var1.getQueue().offer(var2)) {
               var1.cancel();
               this.errors.addThrowable(new MissingBackpressureException("Queue full?!"));
               this.done.decrementAndGet();
               this.drainLoop();
               return;
            }

            if (this.decrementAndGet() == 0) {
               return;
            }
         } else {
            if (!var1.getQueue().offer(var2) && var1.cancel()) {
               this.errors.addThrowable(new MissingBackpressureException("Queue full?!"));
               this.done.decrementAndGet();
            }

            if (this.getAndIncrement() != 0) {
               return;
            }
         }

         this.drainLoop();
      }
   }
}
