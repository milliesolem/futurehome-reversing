package io.reactivex.internal.operators.flowable;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableCache<T> extends AbstractFlowableWithUpstream<T, T> implements FlowableSubscriber<T> {
   static final FlowableCache.CacheSubscription[] EMPTY = new FlowableCache.CacheSubscription[0];
   static final FlowableCache.CacheSubscription[] TERMINATED = new FlowableCache.CacheSubscription[0];
   final int capacityHint;
   volatile boolean done;
   Throwable error;
   final FlowableCache.Node<T> head;
   final AtomicBoolean once;
   volatile long size;
   final AtomicReference<FlowableCache.CacheSubscription<T>[]> subscribers;
   FlowableCache.Node<T> tail;
   int tailOffset;

   public FlowableCache(Flowable<T> var1, int var2) {
      super(var1);
      this.capacityHint = var2;
      this.once = new AtomicBoolean();
      FlowableCache.Node var3 = new FlowableCache.Node(var2);
      this.head = var3;
      this.tail = var3;
      this.subscribers = new AtomicReference<>(EMPTY);
   }

   void add(FlowableCache.CacheSubscription<T> var1) {
      FlowableCache.CacheSubscription[] var3;
      FlowableCache.CacheSubscription[] var4;
      do {
         var4 = this.subscribers.get();
         if (var4 == TERMINATED) {
            return;
         }

         int var2 = var4.length;
         var3 = new FlowableCache.CacheSubscription[var2 + 1];
         System.arraycopy(var4, 0, var3, 0, var2);
         var3[var2] = var1;
      } while (!ExternalSyntheticBackportWithForwarding0.m(this.subscribers, var4, var3));
   }

   long cachedEventCount() {
      return this.size;
   }

   boolean hasSubscribers() {
      boolean var1;
      if (((FlowableCache.CacheSubscription[])this.subscribers.get()).length != 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   boolean isConnected() {
      return this.once.get();
   }

   public void onComplete() {
      this.done = true;
      FlowableCache.CacheSubscription[] var3 = this.subscribers.getAndSet(TERMINATED);
      int var2 = var3.length;

      for (int var1 = 0; var1 < var2; var1++) {
         this.replay(var3[var1]);
      }
   }

   public void onError(Throwable var1) {
      if (this.done) {
         RxJavaPlugins.onError(var1);
      } else {
         this.error = var1;
         this.done = true;
         FlowableCache.CacheSubscription[] var4 = this.subscribers.getAndSet(TERMINATED);
         int var3 = var4.length;

         for (int var2 = 0; var2 < var3; var2++) {
            this.replay(var4[var2]);
         }
      }
   }

   public void onNext(T var1) {
      int var3 = this.tailOffset;
      int var4 = this.capacityHint;
      int var2 = 0;
      if (var3 == var4) {
         FlowableCache.Node var5 = new FlowableCache.Node(var3);
         var5.values[0] = (T)var1;
         this.tailOffset = 1;
         this.tail.next = var5;
         this.tail = var5;
      } else {
         this.tail.values[var3] = (T)var1;
         this.tailOffset = var3 + 1;
      }

      this.size++;
      var1 = this.subscribers.get();

      for (int var7 = var1.length; var2 < var7; var2++) {
         this.replay(var1[var2]);
      }
   }

   @Override
   public void onSubscribe(Subscription var1) {
      var1.request(Long.MAX_VALUE);
   }

   void remove(FlowableCache.CacheSubscription<T> var1) {
      FlowableCache.CacheSubscription[] var4;
      FlowableCache.CacheSubscription[] var5;
      do {
         var5 = this.subscribers.get();
         int var3 = var5.length;
         if (var3 == 0) {
            return;
         }

         int var2 = 0;

         while (true) {
            if (var2 >= var3) {
               var2 = -1;
               break;
            }

            if (var5[var2] == var1) {
               break;
            }

            var2++;
         }

         if (var2 < 0) {
            return;
         }

         if (var3 == 1) {
            var4 = EMPTY;
         } else {
            var4 = new FlowableCache.CacheSubscription[var3 - 1];
            System.arraycopy(var5, 0, var4, 0, var2);
            System.arraycopy(var5, var2 + 1, var4, var2, var3 - var2 - 1);
         }
      } while (!ExternalSyntheticBackportWithForwarding0.m(this.subscribers, var5, var4));
   }

   void replay(FlowableCache.CacheSubscription<T> var1) {
      if (var1.getAndIncrement() == 0) {
         long var6 = var1.index;
         int var2 = var1.offset;
         FlowableCache.Node var11 = var1.node;
         AtomicLong var14 = var1.requested;
         Subscriber var13 = var1.downstream;
         int var5 = this.capacityHint;
         int var3 = 1;

         while (true) {
            boolean var10 = this.done;
            boolean var4;
            if (this.size == var6) {
               var4 = 1;
            } else {
               var4 = 0;
            }

            if (var10 && var4) {
               var1.node = null;
               Throwable var15 = this.error;
               if (var15 != null) {
                  var13.onError(var15);
               } else {
                  var13.onComplete();
               }

               return;
            }

            if (!var4) {
               long var8 = var14.get();
               if (var8 == Long.MIN_VALUE) {
                  var1.node = null;
                  return;
               }

               if (var8 != var6) {
                  var4 = var2;
                  FlowableCache.Node var12 = var11;
                  if (var2 == var5) {
                     var12 = var11.next;
                     var4 = 0;
                  }

                  var13.onNext(var12.values[var4]);
                  var2 = var4 + 1;
                  var6++;
                  var11 = var12;
                  continue;
               }
            }

            var1.index = var6;
            var1.offset = var2;
            var1.node = var11;
            var4 = var1.addAndGet(-var3);
            var3 = var4;
            if (var4 == 0) {
               return;
            }
         }
      }
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      FlowableCache.CacheSubscription var2 = new FlowableCache.CacheSubscription<>(var1, this);
      var1.onSubscribe(var2);
      this.add(var2);
      if (!this.once.get() && this.once.compareAndSet(false, true)) {
         this.source.subscribe(this);
      } else {
         this.replay(var2);
      }
   }

   static final class CacheSubscription<T> extends AtomicInteger implements Subscription {
      private static final long serialVersionUID = 6770240836423125754L;
      final Subscriber<? super T> downstream;
      long index;
      FlowableCache.Node<T> node;
      int offset;
      final FlowableCache<T> parent;
      final AtomicLong requested;

      CacheSubscription(Subscriber<? super T> var1, FlowableCache<T> var2) {
         this.downstream = var1;
         this.parent = var2;
         this.node = var2.head;
         this.requested = new AtomicLong();
      }

      public void cancel() {
         if (this.requested.getAndSet(Long.MIN_VALUE) != Long.MIN_VALUE) {
            this.parent.remove(this);
         }
      }

      public void request(long var1) {
         if (SubscriptionHelper.validate(var1)) {
            BackpressureHelper.addCancel(this.requested, var1);
            this.parent.replay(this);
         }
      }
   }

   static final class Node<T> {
      volatile FlowableCache.Node<T> next;
      final T[] values;

      Node(int var1) {
         this.values = (T[])(new Object[var1]);
      }
   }
}
