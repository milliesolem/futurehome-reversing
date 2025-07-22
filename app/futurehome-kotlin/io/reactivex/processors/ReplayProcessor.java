package io.reactivex.processors;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.Scheduler;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class ReplayProcessor<T> extends FlowableProcessor<T> {
   static final ReplayProcessor.ReplaySubscription[] EMPTY = new ReplayProcessor.ReplaySubscription[0];
   private static final Object[] EMPTY_ARRAY = new Object[0];
   static final ReplayProcessor.ReplaySubscription[] TERMINATED = new ReplayProcessor.ReplaySubscription[0];
   final ReplayProcessor.ReplayBuffer<T> buffer;
   boolean done;
   final AtomicReference<ReplayProcessor.ReplaySubscription<T>[]> subscribers;

   ReplayProcessor(ReplayProcessor.ReplayBuffer<T> var1) {
      this.buffer = var1;
      this.subscribers = new AtomicReference<>(EMPTY);
   }

   @CheckReturnValue
   public static <T> ReplayProcessor<T> create() {
      return new ReplayProcessor<>(new ReplayProcessor.UnboundedReplayBuffer<>(16));
   }

   @CheckReturnValue
   public static <T> ReplayProcessor<T> create(int var0) {
      return new ReplayProcessor<>(new ReplayProcessor.UnboundedReplayBuffer<>(var0));
   }

   static <T> ReplayProcessor<T> createUnbounded() {
      return new ReplayProcessor<>(new ReplayProcessor.SizeBoundReplayBuffer<>(Integer.MAX_VALUE));
   }

   @CheckReturnValue
   public static <T> ReplayProcessor<T> createWithSize(int var0) {
      return new ReplayProcessor<>(new ReplayProcessor.SizeBoundReplayBuffer<>(var0));
   }

   @CheckReturnValue
   public static <T> ReplayProcessor<T> createWithTime(long var0, TimeUnit var2, Scheduler var3) {
      return new ReplayProcessor<>(new ReplayProcessor.SizeAndTimeBoundReplayBuffer<>(Integer.MAX_VALUE, var0, var2, var3));
   }

   @CheckReturnValue
   public static <T> ReplayProcessor<T> createWithTimeAndSize(long var0, TimeUnit var2, Scheduler var3, int var4) {
      return new ReplayProcessor<>(new ReplayProcessor.SizeAndTimeBoundReplayBuffer<>(var4, var0, var2, var3));
   }

   boolean add(ReplayProcessor.ReplaySubscription<T> var1) {
      ReplayProcessor.ReplaySubscription[] var3;
      ReplayProcessor.ReplaySubscription[] var4;
      do {
         var4 = this.subscribers.get();
         if (var4 == TERMINATED) {
            return false;
         }

         int var2 = var4.length;
         var3 = new ReplayProcessor.ReplaySubscription[var2 + 1];
         System.arraycopy(var4, 0, var3, 0, var2);
         var3[var2] = var1;
      } while (!ExternalSyntheticBackportWithForwarding0.m(this.subscribers, var4, var3));

      return true;
   }

   public void cleanupBuffer() {
      this.buffer.trimHead();
   }

   @Override
   public Throwable getThrowable() {
      ReplayProcessor.ReplayBuffer var1 = this.buffer;
      return var1.isDone() ? var1.getError() : null;
   }

   public T getValue() {
      return this.buffer.getValue();
   }

   public Object[] getValues() {
      Object[] var1 = EMPTY_ARRAY;
      Object[] var2 = this.getValues((T[])var1);
      return var2 == var1 ? new Object[0] : var2;
   }

   public T[] getValues(T[] var1) {
      return this.buffer.getValues((T[])var1);
   }

   @Override
   public boolean hasComplete() {
      ReplayProcessor.ReplayBuffer var2 = this.buffer;
      boolean var1;
      if (var2.isDone() && var2.getError() == null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public boolean hasSubscribers() {
      boolean var1;
      if (((ReplayProcessor.ReplaySubscription[])this.subscribers.get()).length != 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public boolean hasThrowable() {
      ReplayProcessor.ReplayBuffer var2 = this.buffer;
      boolean var1;
      if (var2.isDone() && var2.getError() != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean hasValue() {
      boolean var1;
      if (this.buffer.size() != 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void onComplete() {
      if (!this.done) {
         this.done = true;
         ReplayProcessor.ReplayBuffer var4 = this.buffer;
         var4.complete();
         ReplayProcessor.ReplaySubscription[] var3 = this.subscribers.getAndSet(TERMINATED);
         int var2 = var3.length;

         for (int var1 = 0; var1 < var2; var1++) {
            var4.replay(var3[var1]);
         }
      }
   }

   public void onError(Throwable var1) {
      ObjectHelper.requireNonNull(var1, "onError called with null. Null values are generally not allowed in 2.x operators and sources.");
      if (this.done) {
         RxJavaPlugins.onError(var1);
      } else {
         this.done = true;
         ReplayProcessor.ReplayBuffer var4 = this.buffer;
         var4.error(var1);
         ReplayProcessor.ReplaySubscription[] var5 = this.subscribers.getAndSet(TERMINATED);
         int var3 = var5.length;

         for (int var2 = 0; var2 < var3; var2++) {
            var4.replay(var5[var2]);
         }
      }
   }

   public void onNext(T var1) {
      ObjectHelper.requireNonNull(var1, "onNext called with null. Null values are generally not allowed in 2.x operators and sources.");
      if (!this.done) {
         ReplayProcessor.ReplayBuffer var4 = this.buffer;
         var4.next(var1);
         var1 = this.subscribers.get();
         int var3 = var1.length;

         for (int var2 = 0; var2 < var3; var2++) {
            var4.replay(var1[var2]);
         }
      }
   }

   @Override
   public void onSubscribe(Subscription var1) {
      if (this.done) {
         var1.cancel();
      } else {
         var1.request(Long.MAX_VALUE);
      }
   }

   void remove(ReplayProcessor.ReplaySubscription<T> var1) {
      while (true) {
         ReplayProcessor.ReplaySubscription[] var5 = this.subscribers.get();
         if (var5 != TERMINATED && var5 != EMPTY) {
            int var3 = var5.length;
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

            ReplayProcessor.ReplaySubscription[] var4;
            if (var3 == 1) {
               var4 = EMPTY;
            } else {
               var4 = new ReplayProcessor.ReplaySubscription[var3 - 1];
               System.arraycopy(var5, 0, var4, 0, var2);
               System.arraycopy(var5, var2 + 1, var4, var2, var3 - var2 - 1);
            }

            if (!ExternalSyntheticBackportWithForwarding0.m(this.subscribers, var5, var4)) {
               continue;
            }
         }

         return;
      }
   }

   int size() {
      return this.buffer.size();
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      ReplayProcessor.ReplaySubscription var2 = new ReplayProcessor.ReplaySubscription<>(var1, this);
      var1.onSubscribe(var2);
      if (this.add(var2) && var2.cancelled) {
         this.remove(var2);
      } else {
         this.buffer.replay(var2);
      }
   }

   int subscriberCount() {
      return ((ReplayProcessor.ReplaySubscription[])this.subscribers.get()).length;
   }

   static final class Node<T> extends AtomicReference<ReplayProcessor.Node<T>> {
      private static final long serialVersionUID = 6404226426336033100L;
      final T value;

      Node(T var1) {
         this.value = (T)var1;
      }
   }

   interface ReplayBuffer<T> {
      void complete();

      void error(Throwable var1);

      Throwable getError();

      T getValue();

      T[] getValues(T[] var1);

      boolean isDone();

      void next(T var1);

      void replay(ReplayProcessor.ReplaySubscription<T> var1);

      int size();

      void trimHead();
   }

   static final class ReplaySubscription<T> extends AtomicInteger implements Subscription {
      private static final long serialVersionUID = 466549804534799122L;
      volatile boolean cancelled;
      final Subscriber<? super T> downstream;
      long emitted;
      Object index;
      final AtomicLong requested;
      final ReplayProcessor<T> state;

      ReplaySubscription(Subscriber<? super T> var1, ReplayProcessor<T> var2) {
         this.downstream = var1;
         this.state = var2;
         this.requested = new AtomicLong();
      }

      public void cancel() {
         if (!this.cancelled) {
            this.cancelled = true;
            this.state.remove(this);
         }
      }

      public void request(long var1) {
         if (SubscriptionHelper.validate(var1)) {
            BackpressureHelper.add(this.requested, var1);
            this.state.buffer.replay(this);
         }
      }
   }

   static final class SizeAndTimeBoundReplayBuffer<T> implements ReplayProcessor.ReplayBuffer<T> {
      volatile boolean done;
      Throwable error;
      volatile ReplayProcessor.TimedNode<T> head;
      final long maxAge;
      final int maxSize;
      final Scheduler scheduler;
      int size;
      ReplayProcessor.TimedNode<T> tail;
      final TimeUnit unit;

      SizeAndTimeBoundReplayBuffer(int var1, long var2, TimeUnit var4, Scheduler var5) {
         this.maxSize = ObjectHelper.verifyPositive(var1, "maxSize");
         this.maxAge = ObjectHelper.verifyPositive(var2, "maxAge");
         this.unit = ObjectHelper.requireNonNull(var4, "unit is null");
         this.scheduler = ObjectHelper.requireNonNull(var5, "scheduler is null");
         ReplayProcessor.TimedNode var6 = new ReplayProcessor.TimedNode(null, 0L);
         this.tail = var6;
         this.head = var6;
      }

      @Override
      public void complete() {
         this.trimFinal();
         this.done = true;
      }

      @Override
      public void error(Throwable var1) {
         this.trimFinal();
         this.error = var1;
         this.done = true;
      }

      @Override
      public Throwable getError() {
         return this.error;
      }

      ReplayProcessor.TimedNode<T> getHead() {
         ReplayProcessor.TimedNode var6 = this.head;
         long var1 = this.scheduler.now(this.unit);
         long var3 = this.maxAge;
         ReplayProcessor.TimedNode var5 = var6.get();

         while (var5 != null && var5.time <= var1 - var3) {
            ReplayProcessor.TimedNode var7 = var5.get();
            var6 = var5;
            var5 = var7;
         }

         return var6;
      }

      @Override
      public T getValue() {
         ReplayProcessor.TimedNode var5 = this.head;

         while (true) {
            ReplayProcessor.TimedNode var6 = var5.get();
            if (var6 == null) {
               long var3 = this.scheduler.now(this.unit);
               long var1 = this.maxAge;
               return var5.time < var3 - var1 ? null : var5.value;
            }

            var5 = var6;
         }
      }

      @Override
      public T[] getValues(T[] var1) {
         ReplayProcessor.TimedNode var7 = this.getHead();
         int var4 = this.size(var7);
         byte var3 = 0;
         Object[] var6;
         if (var4 == 0) {
            var6 = var1;
            if (var1.length != 0) {
               var1[0] = null;
               var6 = var1;
            }
         } else {
            ReplayProcessor.TimedNode var8 = var7;
            int var2 = var3;
            Object[] var5 = var1;
            if (var1.length < var4) {
               var5 = (Object[])Array.newInstance(var1.getClass().getComponentType(), var4);
               var2 = var3;
               var8 = var7;
            }

            while (var2 != var4) {
               var8 = var8.get();
               var5[var2] = var8.value;
               var2++;
            }

            var6 = var5;
            if (var5.length > var4) {
               var5[var4] = null;
               var6 = var5;
            }
         }

         return (T[])var6;
      }

      @Override
      public boolean isDone() {
         return this.done;
      }

      @Override
      public void next(T var1) {
         var1 = new ReplayProcessor.TimedNode<>(var1, this.scheduler.now(this.unit));
         ReplayProcessor.TimedNode var2 = this.tail;
         this.tail = var1;
         this.size++;
         var2.set(var1);
         this.trim();
      }

      @Override
      public void replay(ReplayProcessor.ReplaySubscription<T> var1) {
         if (var1.getAndIncrement() == 0) {
            Subscriber var12 = var1.downstream;
            ReplayProcessor.TimedNode var11 = (ReplayProcessor.TimedNode)var1.index;
            ReplayProcessor.TimedNode var10 = var11;
            if (var11 == null) {
               var10 = this.getHead();
            }

            long var5 = var1.emitted;
            int var2 = 1;

            int var15;
            do {
               long var7 = var1.requested.get();

               int var4;
               while (true) {
                  long var17;
                  var4 = (var17 = var5 - var7) == 0L ? 0 : (var17 < 0L ? -1 : 1);
                  if (!var4) {
                     break;
                  }

                  if (var1.cancelled) {
                     var1.index = null;
                     return;
                  }

                  boolean var9 = this.done;
                  var11 = var10.get();
                  boolean var3;
                  if (var11 == null) {
                     var3 = true;
                  } else {
                     var3 = false;
                  }

                  if (var9 && var3) {
                     var1.index = null;
                     var1.cancelled = true;
                     Throwable var13 = this.error;
                     if (var13 == null) {
                        var12.onComplete();
                     } else {
                        var12.onError(var13);
                     }

                     return;
                  }

                  if (var3) {
                     break;
                  }

                  var12.onNext(var11.value);
                  var5++;
                  var10 = var11;
               }

               if (!var4) {
                  if (var1.cancelled) {
                     var1.index = null;
                     return;
                  }

                  if (this.done && var10.get() == null) {
                     var1.index = null;
                     var1.cancelled = true;
                     Throwable var14 = this.error;
                     if (var14 == null) {
                        var12.onComplete();
                     } else {
                        var12.onError(var14);
                     }

                     return;
                  }
               }

               var1.index = var10;
               var1.emitted = var5;
               var15 = var1.addAndGet(-var2);
               var2 = var15;
            } while (var15 != 0);
         }
      }

      @Override
      public int size() {
         return this.size(this.getHead());
      }

      int size(ReplayProcessor.TimedNode<T> var1) {
         int var2;
         for (var2 = 0; var2 != Integer.MAX_VALUE; var2++) {
            var1 = var1.get();
            if (var1 == null) {
               break;
            }
         }

         return var2;
      }

      void trim() {
         int var1 = this.size;
         if (var1 > this.maxSize) {
            this.size = var1 - 1;
            this.head = this.head.get();
         }

         long var4 = this.scheduler.now(this.unit);
         long var2 = this.maxAge;
         ReplayProcessor.TimedNode var6 = this.head;

         while (true) {
            if (this.size <= 1) {
               this.head = var6;
               break;
            }

            ReplayProcessor.TimedNode var7 = var6.get();
            if (var7 == null) {
               this.head = var6;
               break;
            }

            if (var7.time > var4 - var2) {
               this.head = var6;
               break;
            }

            this.size--;
            var6 = var7;
         }
      }

      void trimFinal() {
         long var1 = this.scheduler.now(this.unit);
         long var3 = this.maxAge;
         ReplayProcessor.TimedNode var5 = this.head;

         while (true) {
            ReplayProcessor.TimedNode var6 = var5.get();
            if (var6 == null) {
               if (var5.value != null) {
                  this.head = new ReplayProcessor.TimedNode<>(null, 0L);
               } else {
                  this.head = var5;
               }
               break;
            }

            if (var6.time > var1 - var3) {
               if (var5.value != null) {
                  var6 = new ReplayProcessor.TimedNode(null, 0L);
                  var6.lazySet(var5.get());
                  this.head = var6;
               } else {
                  this.head = var5;
               }
               break;
            }

            var5 = var6;
         }
      }

      @Override
      public void trimHead() {
         if (this.head.value != null) {
            ReplayProcessor.TimedNode var1 = new ReplayProcessor.TimedNode(null, 0L);
            var1.lazySet(this.head.get());
            this.head = var1;
         }
      }
   }

   static final class SizeBoundReplayBuffer<T> implements ReplayProcessor.ReplayBuffer<T> {
      volatile boolean done;
      Throwable error;
      volatile ReplayProcessor.Node<T> head;
      final int maxSize;
      int size;
      ReplayProcessor.Node<T> tail;

      SizeBoundReplayBuffer(int var1) {
         this.maxSize = ObjectHelper.verifyPositive(var1, "maxSize");
         ReplayProcessor.Node var2 = new ReplayProcessor.Node(null);
         this.tail = var2;
         this.head = var2;
      }

      @Override
      public void complete() {
         this.trimHead();
         this.done = true;
      }

      @Override
      public void error(Throwable var1) {
         this.error = var1;
         this.trimHead();
         this.done = true;
      }

      @Override
      public Throwable getError() {
         return this.error;
      }

      @Override
      public T getValue() {
         ReplayProcessor.Node var1 = this.head;

         while (true) {
            ReplayProcessor.Node var2 = var1.get();
            if (var2 == null) {
               return var1.value;
            }

            var1 = var2;
         }
      }

      @Override
      public T[] getValues(T[] var1) {
         ReplayProcessor.Node var5 = this.head;
         byte var4 = 0;
         ReplayProcessor.Node var6 = var5;
         int var2 = 0;

         while (true) {
            var6 = var6.get();
            if (var6 == null) {
               ReplayProcessor.Node var7 = var5;
               int var3 = var4;
               Object[] var8 = var1;
               if (var1.length < var2) {
                  var8 = (Object[])Array.newInstance(var1.getClass().getComponentType(), var2);
                  var3 = var4;
                  var7 = var5;
               }

               while (var3 < var2) {
                  var7 = var7.get();
                  var8[var3] = var7.value;
                  var3++;
               }

               if (var8.length > var2) {
                  var8[var2] = null;
               }

               return (T[])var8;
            }

            var2++;
         }
      }

      @Override
      public boolean isDone() {
         return this.done;
      }

      @Override
      public void next(T var1) {
         ReplayProcessor.Node var2 = new ReplayProcessor.Node<>(var1);
         var1 = this.tail;
         this.tail = var2;
         this.size++;
         var1.set(var2);
         this.trim();
      }

      @Override
      public void replay(ReplayProcessor.ReplaySubscription<T> var1) {
         if (var1.getAndIncrement() == 0) {
            Subscriber var12 = var1.downstream;
            ReplayProcessor.Node var11 = (ReplayProcessor.Node)var1.index;
            ReplayProcessor.Node var10 = var11;
            if (var11 == null) {
               var10 = this.head;
            }

            long var5 = var1.emitted;
            int var2 = 1;

            int var15;
            do {
               long var7 = var1.requested.get();

               int var4;
               while (true) {
                  long var17;
                  var4 = (var17 = var5 - var7) == 0L ? 0 : (var17 < 0L ? -1 : 1);
                  if (!var4) {
                     break;
                  }

                  if (var1.cancelled) {
                     var1.index = null;
                     return;
                  }

                  boolean var9 = this.done;
                  var11 = var10.get();
                  boolean var3;
                  if (var11 == null) {
                     var3 = true;
                  } else {
                     var3 = false;
                  }

                  if (var9 && var3) {
                     var1.index = null;
                     var1.cancelled = true;
                     Throwable var13 = this.error;
                     if (var13 == null) {
                        var12.onComplete();
                     } else {
                        var12.onError(var13);
                     }

                     return;
                  }

                  if (var3) {
                     break;
                  }

                  var12.onNext(var11.value);
                  var5++;
                  var10 = var11;
               }

               if (!var4) {
                  if (var1.cancelled) {
                     var1.index = null;
                     return;
                  }

                  if (this.done && var10.get() == null) {
                     var1.index = null;
                     var1.cancelled = true;
                     Throwable var14 = this.error;
                     if (var14 == null) {
                        var12.onComplete();
                     } else {
                        var12.onError(var14);
                     }

                     return;
                  }
               }

               var1.index = var10;
               var1.emitted = var5;
               var15 = var1.addAndGet(-var2);
               var2 = var15;
            } while (var15 != 0);
         }
      }

      @Override
      public int size() {
         ReplayProcessor.Node var2 = this.head;

         int var1;
         for (var1 = 0; var1 != Integer.MAX_VALUE; var1++) {
            var2 = var2.get();
            if (var2 == null) {
               break;
            }
         }

         return var1;
      }

      void trim() {
         int var1 = this.size;
         if (var1 > this.maxSize) {
            this.size = var1 - 1;
            this.head = this.head.get();
         }
      }

      @Override
      public void trimHead() {
         if (this.head.value != null) {
            ReplayProcessor.Node var1 = new ReplayProcessor.Node(null);
            var1.lazySet(this.head.get());
            this.head = var1;
         }
      }
   }

   static final class TimedNode<T> extends AtomicReference<ReplayProcessor.TimedNode<T>> {
      private static final long serialVersionUID = 6404226426336033100L;
      final long time;
      final T value;

      TimedNode(T var1, long var2) {
         this.value = (T)var1;
         this.time = var2;
      }
   }

   static final class UnboundedReplayBuffer<T> implements ReplayProcessor.ReplayBuffer<T> {
      final List<T> buffer;
      volatile boolean done;
      Throwable error;
      volatile int size;

      UnboundedReplayBuffer(int var1) {
         this.buffer = new ArrayList<>(ObjectHelper.verifyPositive(var1, "capacityHint"));
      }

      @Override
      public void complete() {
         this.done = true;
      }

      @Override
      public void error(Throwable var1) {
         this.error = var1;
         this.done = true;
      }

      @Override
      public Throwable getError() {
         return this.error;
      }

      @Override
      public T getValue() {
         int var1 = this.size;
         return var1 == 0 ? null : this.buffer.get(var1 - 1);
      }

      @Override
      public T[] getValues(T[] var1) {
         int var4 = this.size;
         byte var3 = 0;
         if (var4 == 0) {
            if (var1.length != 0) {
               var1[0] = null;
            }

            return (T[])var1;
         } else {
            List var6 = this.buffer;
            int var2 = var3;
            Object[] var5 = var1;
            if (var1.length < var4) {
               var5 = (Object[])Array.newInstance(var1.getClass().getComponentType(), var4);
               var2 = var3;
            }

            while (var2 < var4) {
               var5[var2] = var6.get(var2);
               var2++;
            }

            if (var5.length > var4) {
               var5[var4] = null;
            }

            return (T[])var5;
         }
      }

      @Override
      public boolean isDone() {
         return this.done;
      }

      @Override
      public void next(T var1) {
         this.buffer.add((T)var1);
         this.size++;
      }

      @Override
      public void replay(ReplayProcessor.ReplaySubscription<T> var1) {
         if (var1.getAndIncrement() == 0) {
            List var12 = this.buffer;
            Subscriber var11 = var1.downstream;
            Integer var13 = (Integer)var1.index;
            int var2;
            if (var13 != null) {
               var2 = var13;
            } else {
               var2 = 0;
               var1.index = 0;
            }

            long var7 = var1.emitted;
            int var3 = 1;

            int var17;
            do {
               long var9 = var1.requested.get();

               while (true) {
                  long var19;
                  var17 = (var19 = var7 - var9) == 0L ? 0 : (var19 < 0L ? -1 : 1);
                  if (!var17) {
                     break;
                  }

                  if (var1.cancelled) {
                     var1.index = null;
                     return;
                  }

                  boolean var6 = this.done;
                  int var5 = this.size;
                  if (var6 && var2 == var5) {
                     var1.index = null;
                     var1.cancelled = true;
                     Throwable var14 = this.error;
                     if (var14 == null) {
                        var11.onComplete();
                     } else {
                        var11.onError(var14);
                     }

                     return;
                  }

                  if (var2 == var5) {
                     break;
                  }

                  var11.onNext(var12.get(var2));
                  var2++;
                  var7++;
               }

               if (!var17) {
                  if (var1.cancelled) {
                     var1.index = null;
                     return;
                  }

                  boolean var18 = this.done;
                  var17 = this.size;
                  if (var18 && var2 == var17) {
                     var1.index = null;
                     var1.cancelled = true;
                     Throwable var15 = this.error;
                     if (var15 == null) {
                        var11.onComplete();
                     } else {
                        var11.onError(var15);
                     }

                     return;
                  }
               }

               var1.index = var2;
               var1.emitted = var7;
               var17 = var1.addAndGet(-var3);
               var3 = var17;
            } while (var17 != 0);
         }
      }

      @Override
      public int size() {
         return this.size;
      }

      @Override
      public void trimHead() {
      }
   }
}
