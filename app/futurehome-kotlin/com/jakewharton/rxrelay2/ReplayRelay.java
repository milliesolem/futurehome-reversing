package com.jakewharton.rxrelay2;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.disposables.Disposable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ReplayRelay<T> extends Relay<T> {
   static final ReplayRelay.ReplayDisposable[] EMPTY = new ReplayRelay.ReplayDisposable[0];
   private static final Object[] EMPTY_ARRAY = new Object[0];
   final ReplayRelay.ReplayBuffer<T> buffer;
   final AtomicReference<ReplayRelay.ReplayDisposable<T>[]> observers;

   ReplayRelay(ReplayRelay.ReplayBuffer<T> var1) {
      this.buffer = var1;
      this.observers = new AtomicReference<>(EMPTY);
   }

   @CheckReturnValue
   public static <T> ReplayRelay<T> create() {
      return new ReplayRelay<>(new ReplayRelay.UnboundedReplayBuffer<>(16));
   }

   @CheckReturnValue
   public static <T> ReplayRelay<T> create(int var0) {
      return new ReplayRelay<>(new ReplayRelay.UnboundedReplayBuffer<>(var0));
   }

   static <T> ReplayRelay<T> createUnbounded() {
      return new ReplayRelay<>(new ReplayRelay.SizeBoundReplayBuffer<>(Integer.MAX_VALUE));
   }

   @CheckReturnValue
   public static <T> ReplayRelay<T> createWithSize(int var0) {
      return new ReplayRelay<>(new ReplayRelay.SizeBoundReplayBuffer<>(var0));
   }

   @CheckReturnValue
   public static <T> ReplayRelay<T> createWithTime(long var0, TimeUnit var2, Scheduler var3) {
      return new ReplayRelay<>(new ReplayRelay.SizeAndTimeBoundReplayBuffer<>(Integer.MAX_VALUE, var0, var2, var3));
   }

   @CheckReturnValue
   public static <T> ReplayRelay<T> createWithTimeAndSize(long var0, TimeUnit var2, Scheduler var3, int var4) {
      return new ReplayRelay<>(new ReplayRelay.SizeAndTimeBoundReplayBuffer<>(var4, var0, var2, var3));
   }

   @Override
   public void accept(T var1) {
      if (var1 == null) {
         throw new NullPointerException("value == null");
      } else {
         ReplayRelay.ReplayBuffer var4 = this.buffer;
         var4.add(var1);
         var1 = this.observers.get();
         int var3 = var1.length;

         for (int var2 = 0; var2 < var3; var2++) {
            var4.replay(var1[var2]);
         }
      }
   }

   boolean add(ReplayRelay.ReplayDisposable<T> var1) {
      ReplayRelay.ReplayDisposable[] var3;
      ReplayRelay.ReplayDisposable[] var4;
      do {
         var3 = this.observers.get();
         int var2 = var3.length;
         var4 = new ReplayRelay.ReplayDisposable[var2 + 1];
         System.arraycopy(var3, 0, var4, 0, var2);
         var4[var2] = var1;
      } while (!ExternalSyntheticBackportWithForwarding0.m(this.observers, var3, var4));

      return true;
   }

   public void cleanupBuffer() {
      this.buffer.trimHead();
   }

   public T getValue() {
      return this.buffer.getValue();
   }

   public Object[] getValues() {
      Object[] var2 = EMPTY_ARRAY;
      Object[] var1 = this.getValues((T[])var2);
      return var1 == var2 ? new Object[0] : var1;
   }

   public T[] getValues(T[] var1) {
      return this.buffer.getValues((T[])var1);
   }

   @Override
   public boolean hasObservers() {
      boolean var1;
      if (((ReplayRelay.ReplayDisposable[])this.observers.get()).length != 0) {
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

   int observerCount() {
      return ((ReplayRelay.ReplayDisposable[])this.observers.get()).length;
   }

   void remove(ReplayRelay.ReplayDisposable<T> var1) {
      ReplayRelay.ReplayDisposable[] var4;
      ReplayRelay.ReplayDisposable[] var5;
      do {
         var5 = this.observers.get();
         if (var5 == EMPTY) {
            return;
         }

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

         if (var3 == 1) {
            var4 = EMPTY;
         } else {
            var4 = new ReplayRelay.ReplayDisposable[var3 - 1];
            System.arraycopy(var5, 0, var4, 0, var2);
            System.arraycopy(var5, var2 + 1, var4, var2, var3 - var2 - 1);
         }
      } while (!ExternalSyntheticBackportWithForwarding0.m(this.observers, var5, var4));
   }

   int size() {
      return this.buffer.size();
   }

   @Override
   protected void subscribeActual(Observer<? super T> var1) {
      ReplayRelay.ReplayDisposable var2 = new ReplayRelay.ReplayDisposable<>(var1, this);
      var1.onSubscribe(var2);
      if (!var2.cancelled) {
         if (this.add(var2) && var2.cancelled) {
            this.remove(var2);
            return;
         }

         this.buffer.replay(var2);
      }
   }

   static final class Node<T> extends AtomicReference<ReplayRelay.Node<T>> {
      private static final long serialVersionUID = 6404226426336033100L;
      final T value;

      Node(T var1) {
         this.value = (T)var1;
      }
   }

   interface ReplayBuffer<T> {
      void add(T var1);

      T getValue();

      T[] getValues(T[] var1);

      void replay(ReplayRelay.ReplayDisposable<T> var1);

      int size();

      void trimHead();
   }

   static final class ReplayDisposable<T> extends AtomicInteger implements Disposable {
      private static final long serialVersionUID = 466549804534799122L;
      volatile boolean cancelled;
      final Observer<? super T> downstream;
      Object index;
      final ReplayRelay<T> state;

      ReplayDisposable(Observer<? super T> var1, ReplayRelay<T> var2) {
         this.downstream = var1;
         this.state = var2;
      }

      @Override
      public void dispose() {
         if (!this.cancelled) {
            this.cancelled = true;
            this.state.remove(this);
         }
      }

      @Override
      public boolean isDisposed() {
         return this.cancelled;
      }
   }

   static final class SizeAndTimeBoundReplayBuffer<T> extends AtomicReference<Object> implements ReplayRelay.ReplayBuffer<T> {
      private static final long serialVersionUID = -8056260896137901749L;
      volatile ReplayRelay.TimedNode<T> head;
      final long maxAge;
      final int maxSize;
      final Scheduler scheduler;
      int size;
      ReplayRelay.TimedNode<T> tail;
      final TimeUnit unit;

      SizeAndTimeBoundReplayBuffer(int var1, long var2, TimeUnit var4, Scheduler var5) {
         if (var1 > 0) {
            if (var2 > 0L) {
               if (var4 != null) {
                  if (var5 != null) {
                     this.maxSize = var1;
                     this.maxAge = var2;
                     this.unit = var4;
                     this.scheduler = var5;
                     ReplayRelay.TimedNode var8 = new ReplayRelay.TimedNode(null, 0L);
                     this.tail = var8;
                     this.head = var8;
                  } else {
                     throw new NullPointerException("scheduler == null");
                  }
               } else {
                  throw new NullPointerException("unit == null");
               }
            } else {
               StringBuilder var7 = new StringBuilder("maxAge > 0 required but it was ");
               var7.append(var2);
               throw new IllegalArgumentException(var7.toString());
            }
         } else {
            StringBuilder var6 = new StringBuilder("maxSize > 0 required but it was ");
            var6.append(var1);
            throw new IllegalArgumentException(var6.toString());
         }
      }

      @Override
      public void add(T var1) {
         var1 = new ReplayRelay.TimedNode<>(var1, this.scheduler.now(this.unit));
         ReplayRelay.TimedNode var2 = this.tail;
         this.tail = var1;
         this.size++;
         var2.set(var1);
         this.trim();
      }

      ReplayRelay.TimedNode<T> getHead() {
         ReplayRelay.TimedNode var6 = this.head;
         long var3 = this.scheduler.now(this.unit);
         long var1 = this.maxAge;
         ReplayRelay.TimedNode var5 = var6.get();

         while (var5 != null && var5.time <= var3 - var1) {
            ReplayRelay.TimedNode var7 = var5.get();
            var6 = var5;
            var5 = var7;
         }

         return var6;
      }

      @Override
      public T getValue() {
         ReplayRelay.TimedNode var5 = this.head;

         while (true) {
            ReplayRelay.TimedNode var6 = var5.get();
            if (var6 == null) {
               long var1 = this.scheduler.now(this.unit);
               long var3 = this.maxAge;
               return var5.time < var1 - var3 ? null : var5.value;
            }

            var5 = var6;
         }
      }

      @Override
      public T[] getValues(T[] var1) {
         ReplayRelay.TimedNode var7 = this.getHead();
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
            ReplayRelay.TimedNode var8 = var7;
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
      public void replay(ReplayRelay.ReplayDisposable<T> var1) {
         if (var1.getAndIncrement() == 0) {
            Observer var6 = var1.downstream;
            ReplayRelay.TimedNode var5 = (ReplayRelay.TimedNode)var1.index;
            int var3 = 1;
            ReplayRelay.TimedNode var4 = var5;
            int var2 = var3;
            if (var5 == null) {
               var4 = this.getHead();
               var2 = var3;
            }

            while (!var1.cancelled) {
               if (var1.cancelled) {
                  var1.index = null;
                  return;
               }

               var5 = var4.get();
               if (var5 != null) {
                  var6.onNext(var5.value);
                  var4 = var5;
               } else if (var4.get() == null) {
                  var1.index = var4;
                  var3 = var1.addAndGet(-var2);
                  var2 = var3;
                  if (var3 == 0) {
                     return;
                  }
               }
            }

            var1.index = null;
         }
      }

      @Override
      public int size() {
         return this.size(this.getHead());
      }

      int size(ReplayRelay.TimedNode<T> var1) {
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

         long var2 = this.scheduler.now(this.unit);
         long var4 = this.maxAge;
         ReplayRelay.TimedNode var6 = this.head;

         while (true) {
            if (this.size <= 1) {
               this.head = var6;
               break;
            }

            ReplayRelay.TimedNode var7 = var6.get();
            if (var7 == null) {
               this.head = var6;
               break;
            }

            if (var7.time > var2 - var4) {
               this.head = var6;
               break;
            }

            this.size--;
            var6 = var7;
         }
      }

      @Override
      public void trimHead() {
         ReplayRelay.TimedNode var1 = this.head;
         if (var1.value != null) {
            ReplayRelay.TimedNode var2 = new ReplayRelay.TimedNode(null, 0L);
            var2.lazySet(var1.get());
            this.head = var2;
         }
      }
   }

   static final class SizeBoundReplayBuffer<T> extends AtomicReference<Object> implements ReplayRelay.ReplayBuffer<T> {
      private static final long serialVersionUID = 1107649250281456395L;
      volatile ReplayRelay.Node<T> head;
      final int maxSize;
      int size;
      ReplayRelay.Node<T> tail;

      SizeBoundReplayBuffer(int var1) {
         if (var1 > 0) {
            this.maxSize = var1;
            ReplayRelay.Node var3 = new ReplayRelay.Node(null);
            this.tail = var3;
            this.head = var3;
         } else {
            StringBuilder var2 = new StringBuilder("maxSize > 0 required but it was ");
            var2.append(var1);
            throw new IllegalArgumentException(var2.toString());
         }
      }

      @Override
      public void add(T var1) {
         var1 = new ReplayRelay.Node<>(var1);
         ReplayRelay.Node var2 = this.tail;
         this.tail = var1;
         this.size++;
         var2.set(var1);
         this.trim();
      }

      @Override
      public T getValue() {
         ReplayRelay.Node var1 = this.head;

         while (true) {
            ReplayRelay.Node var2 = var1.get();
            if (var2 == null) {
               return var1.value;
            }

            var1 = var2;
         }
      }

      @Override
      public T[] getValues(T[] var1) {
         ReplayRelay.Node var7 = this.head;
         int var4 = this.size();
         byte var3 = 0;
         Object[] var6;
         if (var4 == 0) {
            var6 = var1;
            if (var1.length != 0) {
               var1[0] = null;
               var6 = var1;
            }
         } else {
            ReplayRelay.Node var8 = var7;
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
      public void replay(ReplayRelay.ReplayDisposable<T> var1) {
         if (var1.getAndIncrement() == 0) {
            Observer var6 = var1.downstream;
            ReplayRelay.Node var5 = (ReplayRelay.Node)var1.index;
            int var3 = 1;
            ReplayRelay.Node var4 = var5;
            int var2 = var3;
            if (var5 == null) {
               var4 = this.head;
               var2 = var3;
            }

            while (!var1.cancelled) {
               var5 = var4.get();
               if (var5 == null) {
                  if (var4.get() == null) {
                     var1.index = var4;
                     var3 = var1.addAndGet(-var2);
                     var2 = var3;
                     if (var3 == 0) {
                        return;
                     }
                  }
               } else {
                  var6.onNext(var5.value);
                  var4 = var5;
               }
            }

            var1.index = null;
         }
      }

      @Override
      public int size() {
         ReplayRelay.Node var2 = this.head;

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
         ReplayRelay.Node var2 = this.head;
         if (var2.value != null) {
            ReplayRelay.Node var1 = new ReplayRelay.Node(null);
            var1.lazySet(var2.get());
            this.head = var1;
         }
      }
   }

   static final class TimedNode<T> extends AtomicReference<ReplayRelay.TimedNode<T>> {
      private static final long serialVersionUID = 6404226426336033100L;
      final long time;
      final T value;

      TimedNode(T var1, long var2) {
         this.value = (T)var1;
         this.time = var2;
      }
   }

   static final class UnboundedReplayBuffer<T> extends AtomicReference<Object> implements ReplayRelay.ReplayBuffer<T> {
      private static final long serialVersionUID = -733876083048047795L;
      final List<T> buffer;
      volatile int size;

      UnboundedReplayBuffer(int var1) {
         if (var1 > 0) {
            this.buffer = new ArrayList<>(var1);
         } else {
            throw new IllegalArgumentException("capacityHint <= 0");
         }
      }

      @Override
      public void add(T var1) {
         this.buffer.add((T)var1);
         this.size++;
      }

      @Override
      public T getValue() {
         int var1 = this.size;
         return var1 != 0 ? this.buffer.get(var1 - 1) : null;
      }

      @Override
      public T[] getValues(T[] var1) {
         int var3 = this.size;
         int var2 = 0;
         if (var3 == 0) {
            if (var1.length != 0) {
               var1[0] = null;
            }

            return (T[])var1;
         } else {
            Object[] var4 = var1;
            if (var1.length < var3) {
               var4 = (Object[])Array.newInstance(var1.getClass().getComponentType(), var3);
            }

            for (List var5 = this.buffer; var2 < var3; var2++) {
               var4[var2] = var5.get(var2);
            }

            if (var4.length > var3) {
               var4[var3] = null;
            }

            return (T[])var4;
         }
      }

      @Override
      public void replay(ReplayRelay.ReplayDisposable<T> var1) {
         if (var1.getAndIncrement() == 0) {
            List var6 = this.buffer;
            Observer var5 = var1.downstream;
            Integer var7 = (Integer)var1.index;
            int var3 = 1;
            int var2;
            if (var7 != null) {
               var2 = var7;
            } else {
               var2 = 0;
               var1.index = 0;
            }

            while (!var1.cancelled) {
               for (int var4 = this.size; var4 != var2; var2++) {
                  if (var1.cancelled) {
                     var1.index = null;
                     return;
                  }

                  var5.onNext(var6.get(var2));
               }

               if (var2 == this.size) {
                  var1.index = var2;
                  int var8 = var1.addAndGet(-var3);
                  var3 = var8;
                  if (var8 == 0) {
                     return;
                  }
               }
            }

            var1.index = null;
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
