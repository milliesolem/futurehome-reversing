package io.reactivex.internal.operators.parallel;

import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.parallel.ParallelFlowable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLongArray;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class ParallelFromPublisher<T> extends ParallelFlowable<T> {
   final int parallelism;
   final int prefetch;
   final Publisher<? extends T> source;

   public ParallelFromPublisher(Publisher<? extends T> var1, int var2, int var3) {
      this.source = var1;
      this.parallelism = var2;
      this.prefetch = var3;
   }

   @Override
   public int parallelism() {
      return this.parallelism;
   }

   @Override
   public void subscribe(Subscriber<? super T>[] var1) {
      if (this.validate(var1)) {
         this.source.subscribe(new ParallelFromPublisher.ParallelDispatcher(var1, this.prefetch));
      }
   }

   static final class ParallelDispatcher<T> extends AtomicInteger implements FlowableSubscriber<T> {
      private static final long serialVersionUID = -4470634016609963609L;
      volatile boolean cancelled;
      volatile boolean done;
      final long[] emissions;
      Throwable error;
      int index;
      final int limit;
      final int prefetch;
      int produced;
      SimpleQueue<T> queue;
      final AtomicLongArray requests;
      int sourceMode;
      final AtomicInteger subscriberCount = new AtomicInteger();
      final Subscriber<? super T>[] subscribers;
      Subscription upstream;

      ParallelDispatcher(Subscriber<? super T>[] var1, int var2) {
         this.subscribers = var1;
         this.prefetch = var2;
         this.limit = var2 - (var2 >> 2);
         var2 = var1.length;
         int var3 = var2 + var2;
         AtomicLongArray var4 = new AtomicLongArray(var3 + 1);
         this.requests = var4;
         var4.lazySet(var3, var2);
         this.emissions = new long[var2];
      }

      void cancel(int var1) {
         if (this.requests.decrementAndGet(var1) == 0L) {
            this.cancelled = true;
            this.upstream.cancel();
            if (this.getAndIncrement() == 0) {
               this.queue.clear();
            }
         }
      }

      void drain() {
         if (this.getAndIncrement() == 0) {
            if (this.sourceMode == 1) {
               this.drainSync();
            } else {
               this.drainAsync();
            }
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      void drainAsync() {
         SimpleQueue var19 = this.queue;
         Subscriber[] var18 = this.subscribers;
         AtomicLongArray var20 = this.requests;
         long[] var21 = this.emissions;
         int var11 = var21.length;
         int var3 = this.index;
         int var5 = this.produced;
         int var2 = 1;

         while (true) {
            byte var9 = 0;
            byte var10 = 0;
            byte var8 = 0;
            int var7 = 0;

            int var4;
            int var6;
            int var26;
            do {
               if (this.cancelled) {
                  var19.clear();
                  return;
               }

               boolean var17 = this.done;
               if (var17) {
                  Throwable var22 = this.error;
                  if (var22 != null) {
                     var19.clear();
                     var2 = var18.length;

                     for (int var28 = var8; var28 < var2; var28++) {
                        var18[var28].onError(var22);
                     }

                     return;
                  }
               }

               boolean var16 = var19.isEmpty();
               if (var17 && var16) {
                  var2 = var18.length;

                  for (int var27 = var9; var27 < var2; var27++) {
                     var18[var27].onComplete();
                  }

                  return;
               }

               if (var16) {
                  var26 = var3;
                  var4 = var5;
                  break;
               }

               long var12 = var20.get(var3);
               long var14 = var21[var3];
               if (var12 != var14 && var20.get(var11 + var3) == 0L) {
                  Object var36;
                  try {
                     var36 = var19.poll();
                  } catch (Throwable var24) {
                     Throwable var35 = var24;
                     Exceptions.throwIfFatal(var24);
                     this.upstream.cancel();
                     var2 = var18.length;

                     for (int var1 = var10; var1 < var2; var1++) {
                        var18[var1].onError(var35);
                     }

                     return;
                  }

                  if (var36 == null) {
                     var26 = var3;
                     var4 = var5;
                     break;
                  }

                  var18[var3].onNext(var36);
                  var21[var3] = var14 + 1L;
                  var26 = var5 + 1;
                  var4 = var26;
                  if (var26 == this.limit) {
                     this.upstream.request(var26);
                     var4 = 0;
                  }

                  var6 = 0;
               } else {
                  var6 = var7 + 1;
                  var4 = var5;
               }

               var26 = ++var3;
               if (var3 == var11) {
                  var26 = 0;
               }

               var3 = var26;
               var5 = var4;
               var7 = var6;
            } while (var6 != var11);

            var3 = this.get();
            if (var3 == var2) {
               this.index = var26;
               this.produced = var4;
               var6 = this.addAndGet(-var2);
               var3 = var26;
               var5 = var4;
               var2 = var6;
               if (var6 == 0) {
                  return;
               }
            } else {
               var2 = var3;
               var3 = var26;
               var5 = var4;
            }
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      void drainSync() {
         SimpleQueue var18 = this.queue;
         Subscriber[] var14 = this.subscribers;
         AtomicLongArray var17 = this.requests;
         long[] var16 = this.emissions;
         int var9 = var16.length;
         int var1 = this.index;
         int var2 = 1;

         label129:
         while (true) {
            byte var7 = 0;
            byte var8 = 0;
            byte var6 = 0;
            int var5 = 0;
            int var4 = var1;

            while (!this.cancelled) {
               if (var18.isEmpty()) {
                  var2 = var14.length;

                  for (int var23 = var6; var23 < var2; var23++) {
                     var14[var23].onComplete();
                  }

                  return;
               }

               long var12 = var17.get(var4);
               long var10 = var16[var4];
               int var3;
               if (var12 != var10 && var17.get(var9 + var4) == 0L) {
                  Object var30;
                  try {
                     var30 = var18.poll();
                  } catch (Throwable var20) {
                     var30 = var20;
                     Exceptions.throwIfFatal(var20);
                     this.upstream.cancel();
                     var2 = var14.length;

                     for (int var21 = var8; var21 < var2; var21++) {
                        var14[var21].onError((Throwable)var30);
                     }

                     return;
                  }

                  if (var30 == null) {
                     var2 = var14.length;

                     for (int var22 = var7; var22 < var2; var22++) {
                        var14[var22].onComplete();
                     }

                     return;
                  }

                  var14[var4].onNext(var30);
                  var16[var4] = var10 + 1L;
                  var3 = 0;
               } else {
                  var3 = var5 + 1;
               }

               var1 = ++var4;
               if (var4 == var9) {
                  var1 = 0;
               }

               var4 = var1;
               var5 = var3;
               if (var3 == var9) {
                  var3 = this.get();
                  if (var3 == var2) {
                     this.index = var1;
                     var3 = this.addAndGet(-var2);
                     var2 = var3;
                     if (var3 == 0) {
                        return;
                     }
                  } else {
                     var2 = var3;
                  }
                  continue label129;
               }
            }

            var18.clear();
            return;
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
         if (this.sourceMode == 0 && !this.queue.offer((T)var1)) {
            this.upstream.cancel();
            this.onError(new MissingBackpressureException("Queue is full?"));
         } else {
            this.drain();
         }
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            if (var1 instanceof QueueSubscription) {
               QueueSubscription var3 = (QueueSubscription)var1;
               int var2 = var3.requestFusion(7);
               if (var2 == 1) {
                  this.sourceMode = var2;
                  this.queue = var3;
                  this.done = true;
                  this.setupSubscribers();
                  this.drain();
                  return;
               }

               if (var2 == 2) {
                  this.sourceMode = var2;
                  this.queue = var3;
                  this.setupSubscribers();
                  var1.request(this.prefetch);
                  return;
               }
            }

            this.queue = new SpscArrayQueue<>(this.prefetch);
            this.setupSubscribers();
            var1.request(this.prefetch);
         }
      }

      void setupSubscribers() {
         Subscriber[] var4 = this.subscribers;
         int var3 = var4.length;
         int var1 = 0;

         while (var1 < var3) {
            if (this.cancelled) {
               return;
            }

            AtomicInteger var5 = this.subscriberCount;
            int var2 = var1 + 1;
            var5.lazySet(var2);
            var4[var1].onSubscribe(new ParallelFromPublisher.ParallelDispatcher.RailSubscription(this, var1, var3));
            var1 = var2;
         }
      }

      final class RailSubscription implements Subscription {
         final int j;
         final int m;
         final ParallelFromPublisher.ParallelDispatcher this$0;

         RailSubscription(ParallelFromPublisher.ParallelDispatcher var1, int var2, int var3) {
            this.this$0 = var1;
            this.j = var2;
            this.m = var3;
         }

         public void cancel() {
            AtomicLongArray var2 = this.this$0.requests;
            int var1 = this.m;
            if (var2.compareAndSet(this.j + var1, 0L, 1L)) {
               ParallelFromPublisher.ParallelDispatcher var4 = this.this$0;
               var1 = this.m;
               var4.cancel(var1 + var1);
            }
         }

         public void request(long var1) {
            if (SubscriptionHelper.validate(var1)) {
               AtomicLongArray var7 = this.this$0.requests;

               long var3;
               long var5;
               do {
                  var5 = var7.get(this.j);
                  if (var5 == Long.MAX_VALUE) {
                     return;
                  }

                  var3 = BackpressureHelper.addCap(var5, var1);
               } while (!var7.compareAndSet(this.j, var5, var3));

               if (this.this$0.subscriberCount.get() == this.m) {
                  this.this$0.drain();
               }
            }
         }
      }
   }
}
