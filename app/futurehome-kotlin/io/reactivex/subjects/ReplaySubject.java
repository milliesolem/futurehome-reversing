package io.reactivex.subjects;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.plugins.RxJavaPlugins;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ReplaySubject<T> extends Subject<T> {
   static final ReplaySubject.ReplayDisposable[] EMPTY = new ReplaySubject.ReplayDisposable[0];
   private static final Object[] EMPTY_ARRAY = new Object[0];
   static final ReplaySubject.ReplayDisposable[] TERMINATED = new ReplaySubject.ReplayDisposable[0];
   final ReplaySubject.ReplayBuffer<T> buffer;
   boolean done;
   final AtomicReference<ReplaySubject.ReplayDisposable<T>[]> observers;

   ReplaySubject(ReplaySubject.ReplayBuffer<T> var1) {
      this.buffer = var1;
      this.observers = new AtomicReference<>(EMPTY);
   }

   @CheckReturnValue
   public static <T> ReplaySubject<T> create() {
      return new ReplaySubject<>(new ReplaySubject.UnboundedReplayBuffer<>(16));
   }

   @CheckReturnValue
   public static <T> ReplaySubject<T> create(int var0) {
      return new ReplaySubject<>(new ReplaySubject.UnboundedReplayBuffer<>(var0));
   }

   static <T> ReplaySubject<T> createUnbounded() {
      return new ReplaySubject<>(new ReplaySubject.SizeBoundReplayBuffer<>(Integer.MAX_VALUE));
   }

   @CheckReturnValue
   public static <T> ReplaySubject<T> createWithSize(int var0) {
      return new ReplaySubject<>(new ReplaySubject.SizeBoundReplayBuffer<>(var0));
   }

   @CheckReturnValue
   public static <T> ReplaySubject<T> createWithTime(long var0, TimeUnit var2, Scheduler var3) {
      return new ReplaySubject<>(new ReplaySubject.SizeAndTimeBoundReplayBuffer<>(Integer.MAX_VALUE, var0, var2, var3));
   }

   @CheckReturnValue
   public static <T> ReplaySubject<T> createWithTimeAndSize(long var0, TimeUnit var2, Scheduler var3, int var4) {
      return new ReplaySubject<>(new ReplaySubject.SizeAndTimeBoundReplayBuffer<>(var4, var0, var2, var3));
   }

   boolean add(ReplaySubject.ReplayDisposable<T> var1) {
      ReplaySubject.ReplayDisposable[] var3;
      ReplaySubject.ReplayDisposable[] var4;
      do {
         var3 = this.observers.get();
         if (var3 == TERMINATED) {
            return false;
         }

         int var2 = var3.length;
         var4 = new ReplaySubject.ReplayDisposable[var2 + 1];
         System.arraycopy(var3, 0, var4, 0, var2);
         var4[var2] = var1;
      } while (!ExternalSyntheticBackportWithForwarding0.m(this.observers, var3, var4));

      return true;
   }

   public void cleanupBuffer() {
      this.buffer.trimHead();
   }

   @Override
   public Throwable getThrowable() {
      Object var1 = this.buffer.get();
      return NotificationLite.isError(var1) ? NotificationLite.getError(var1) : null;
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
   public boolean hasComplete() {
      return NotificationLite.isComplete(this.buffer.get());
   }

   @Override
   public boolean hasObservers() {
      boolean var1;
      if (((ReplaySubject.ReplayDisposable[])this.observers.get()).length != 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public boolean hasThrowable() {
      return NotificationLite.isError(this.buffer.get());
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
      return ((ReplaySubject.ReplayDisposable[])this.observers.get()).length;
   }

   @Override
   public void onComplete() {
      if (!this.done) {
         this.done = true;
         Object var4 = NotificationLite.complete();
         ReplaySubject.ReplayBuffer var3 = this.buffer;
         var3.addFinal(var4);
         var4 = this.terminate(var4);
         int var2 = ((Object[])var4).length;

         for (int var1 = 0; var1 < var2; var1++) {
            var3.replay((ReplaySubject.ReplayDisposable<T>)((Object[])var4)[var1]);
         }
      }
   }

   @Override
   public void onError(Throwable var1) {
      ObjectHelper.requireNonNull(var1, "onError called with null. Null values are generally not allowed in 2.x operators and sources.");
      if (this.done) {
         RxJavaPlugins.onError(var1);
      } else {
         this.done = true;
         Object var4 = NotificationLite.error(var1);
         ReplaySubject.ReplayBuffer var5 = this.buffer;
         var5.addFinal(var4);
         var4 = this.terminate(var4);
         int var3 = ((Object[])var4).length;

         for (int var2 = 0; var2 < var3; var2++) {
            var5.replay((ReplaySubject.ReplayDisposable<T>)((Object[])var4)[var2]);
         }
      }
   }

   @Override
   public void onNext(T var1) {
      ObjectHelper.requireNonNull(var1, "onNext called with null. Null values are generally not allowed in 2.x operators and sources.");
      if (!this.done) {
         ReplaySubject.ReplayBuffer var4 = this.buffer;
         var4.add(var1);
         var1 = this.observers.get();
         int var3 = var1.length;

         for (int var2 = 0; var2 < var3; var2++) {
            var4.replay(var1[var2]);
         }
      }
   }

   @Override
   public void onSubscribe(Disposable var1) {
      if (this.done) {
         var1.dispose();
      }
   }

   void remove(ReplaySubject.ReplayDisposable<T> var1) {
      while (true) {
         ReplaySubject.ReplayDisposable[] var5 = this.observers.get();
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

            ReplaySubject.ReplayDisposable[] var4;
            if (var3 == 1) {
               var4 = EMPTY;
            } else {
               var4 = new ReplaySubject.ReplayDisposable[var3 - 1];
               System.arraycopy(var5, 0, var4, 0, var2);
               System.arraycopy(var5, var2 + 1, var4, var2, var3 - var2 - 1);
            }

            if (!ExternalSyntheticBackportWithForwarding0.m(this.observers, var5, var4)) {
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
   protected void subscribeActual(Observer<? super T> var1) {
      ReplaySubject.ReplayDisposable var2 = new ReplaySubject.ReplayDisposable<>(var1, this);
      var1.onSubscribe(var2);
      if (!var2.cancelled) {
         if (this.add(var2) && var2.cancelled) {
            this.remove(var2);
            return;
         }

         this.buffer.replay(var2);
      }
   }

   ReplaySubject.ReplayDisposable<T>[] terminate(Object var1) {
      return this.buffer.compareAndSet(null, var1) ? this.observers.getAndSet(TERMINATED) : TERMINATED;
   }

   static final class Node<T> extends AtomicReference<ReplaySubject.Node<T>> {
      private static final long serialVersionUID = 6404226426336033100L;
      final T value;

      Node(T var1) {
         this.value = (T)var1;
      }
   }

   interface ReplayBuffer<T> {
      void add(T var1);

      void addFinal(Object var1);

      boolean compareAndSet(Object var1, Object var2);

      Object get();

      T getValue();

      T[] getValues(T[] var1);

      void replay(ReplaySubject.ReplayDisposable<T> var1);

      int size();

      void trimHead();
   }

   static final class ReplayDisposable<T> extends AtomicInteger implements Disposable {
      private static final long serialVersionUID = 466549804534799122L;
      volatile boolean cancelled;
      final Observer<? super T> downstream;
      Object index;
      final ReplaySubject<T> state;

      ReplayDisposable(Observer<? super T> var1, ReplaySubject<T> var2) {
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

   static final class SizeAndTimeBoundReplayBuffer<T> extends AtomicReference<Object> implements ReplaySubject.ReplayBuffer<T> {
      private static final long serialVersionUID = -8056260896137901749L;
      volatile boolean done;
      volatile ReplaySubject.TimedNode<Object> head;
      final long maxAge;
      final int maxSize;
      final Scheduler scheduler;
      int size;
      ReplaySubject.TimedNode<Object> tail;
      final TimeUnit unit;

      SizeAndTimeBoundReplayBuffer(int var1, long var2, TimeUnit var4, Scheduler var5) {
         this.maxSize = ObjectHelper.verifyPositive(var1, "maxSize");
         this.maxAge = ObjectHelper.verifyPositive(var2, "maxAge");
         this.unit = ObjectHelper.requireNonNull(var4, "unit is null");
         this.scheduler = ObjectHelper.requireNonNull(var5, "scheduler is null");
         ReplaySubject.TimedNode var6 = new ReplaySubject.TimedNode(null, 0L);
         this.tail = var6;
         this.head = var6;
      }

      @Override
      public void add(T var1) {
         var1 = new ReplaySubject.TimedNode<>(var1, this.scheduler.now(this.unit));
         ReplaySubject.TimedNode var2 = this.tail;
         this.tail = var1;
         this.size++;
         var2.set(var1);
         this.trim();
      }

      @Override
      public void addFinal(Object var1) {
         var1 = new ReplaySubject.TimedNode<>(var1, Long.MAX_VALUE);
         ReplaySubject.TimedNode var2 = this.tail;
         this.tail = var1;
         this.size++;
         var2.lazySet(var1);
         this.trimFinal();
         this.done = true;
      }

      ReplaySubject.TimedNode<Object> getHead() {
         ReplaySubject.TimedNode var6 = this.head;
         long var1 = this.scheduler.now(this.unit);
         long var3 = this.maxAge;
         ReplaySubject.TimedNode var5 = var6.get();

         while (var5 != null && var5.time <= var1 - var3) {
            ReplaySubject.TimedNode var7 = var5.get();
            var6 = var5;
            var5 = var7;
         }

         return var6;
      }

      @Override
      public T getValue() {
         ReplaySubject.TimedNode var5 = this.head;
         ReplaySubject.TimedNode var6 = null;

         while (true) {
            ReplaySubject.TimedNode var7 = var5.get();
            if (var7 == null) {
               long var1 = this.scheduler.now(this.unit);
               long var3 = this.maxAge;
               if (var5.time < var1 - var3) {
                  return null;
               } else {
                  Object var8 = var5.value;
                  if (var8 == null) {
                     return null;
                  } else {
                     return (T)(!NotificationLite.isComplete(var8) && !NotificationLite.isError(var8) ? var8 : var6.value);
                  }
               }
            }

            var6 = var5;
            var5 = var7;
         }
      }

      @Override
      public T[] getValues(T[] var1) {
         ReplaySubject.TimedNode var7 = this.getHead();
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
            ReplaySubject.TimedNode var8 = var7;
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
      public void replay(ReplaySubject.ReplayDisposable<T> var1) {
         if (var1.getAndIncrement() == 0) {
            Observer var6 = var1.downstream;
            ReplaySubject.TimedNode var5 = (ReplaySubject.TimedNode)var1.index;
            ReplaySubject.TimedNode var4 = var5;
            if (var5 == null) {
               var4 = this.getHead();
            }

            int var2 = 1;

            while (!var1.cancelled) {
               if (var1.cancelled) {
                  var1.index = null;
                  return;
               }

               var5 = var4.get();
               if (var5 != null) {
                  Object var7 = var5.value;
                  if (this.done && var5.get() == null) {
                     if (NotificationLite.isComplete(var7)) {
                        var6.onComplete();
                     } else {
                        var6.onError(NotificationLite.getError(var7));
                     }

                     var1.index = null;
                     var1.cancelled = true;
                     return;
                  }

                  var6.onNext(var7);
                  var4 = var5;
               } else if (var4.get() == null) {
                  var1.index = var4;
                  int var3 = var1.addAndGet(-var2);
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

      int size(ReplaySubject.TimedNode<Object> var1) {
         int var2 = 0;

         int var3;
         while (true) {
            var3 = var2;
            if (var2 == Integer.MAX_VALUE) {
               break;
            }

            ReplaySubject.TimedNode var4 = var1.get();
            if (var4 == null) {
               Object var5 = var1.value;
               if (!NotificationLite.isComplete(var5)) {
                  var3 = var2;
                  if (!NotificationLite.isError(var5)) {
                     break;
                  }
               }

               var3 = var2 - 1;
               break;
            }

            var2++;
            var1 = var4;
         }

         return var3;
      }

      void trim() {
         int var1 = this.size;
         if (var1 > this.maxSize) {
            this.size = var1 - 1;
            this.head = this.head.get();
         }

         long var2 = this.scheduler.now(this.unit);
         long var4 = this.maxAge;
         ReplaySubject.TimedNode var6 = this.head;

         while (true) {
            if (this.size <= 1) {
               this.head = var6;
               break;
            }

            ReplaySubject.TimedNode var7 = var6.get();
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

      void trimFinal() {
         long var1 = this.scheduler.now(this.unit);
         long var3 = this.maxAge;
         ReplaySubject.TimedNode var5 = this.head;

         while (true) {
            ReplaySubject.TimedNode var6 = var5.get();
            if (var6.get() == null) {
               if (var5.value != null) {
                  var6 = new ReplaySubject.TimedNode(null, 0L);
                  var6.lazySet(var5.get());
                  this.head = var6;
               } else {
                  this.head = var5;
               }
               break;
            }

            if (var6.time > var1 - var3) {
               if (var5.value != null) {
                  var6 = new ReplaySubject.TimedNode(null, 0L);
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
         ReplaySubject.TimedNode var1 = this.head;
         if (var1.value != null) {
            ReplaySubject.TimedNode var2 = new ReplaySubject.TimedNode(null, 0L);
            var2.lazySet(var1.get());
            this.head = var2;
         }
      }
   }

   static final class SizeBoundReplayBuffer<T> extends AtomicReference<Object> implements ReplaySubject.ReplayBuffer<T> {
      private static final long serialVersionUID = 1107649250281456395L;
      volatile boolean done;
      volatile ReplaySubject.Node<Object> head;
      final int maxSize;
      int size;
      ReplaySubject.Node<Object> tail;

      SizeBoundReplayBuffer(int var1) {
         this.maxSize = ObjectHelper.verifyPositive(var1, "maxSize");
         ReplaySubject.Node var2 = new ReplaySubject.Node(null);
         this.tail = var2;
         this.head = var2;
      }

      @Override
      public void add(T var1) {
         ReplaySubject.Node var2 = new ReplaySubject.Node<>(var1);
         var1 = this.tail;
         this.tail = var2;
         this.size++;
         var1.set(var2);
         this.trim();
      }

      @Override
      public void addFinal(Object var1) {
         var1 = new ReplaySubject.Node<>(var1);
         ReplaySubject.Node var2 = this.tail;
         this.tail = var1;
         this.size++;
         var2.lazySet(var1);
         this.trimHead();
         this.done = true;
      }

      @Override
      public T getValue() {
         ReplaySubject.Node var1 = this.head;
         ReplaySubject.Node var2 = null;

         while (true) {
            ReplaySubject.Node var3 = var1.get();
            if (var3 == null) {
               Object var4 = var1.value;
               if (var4 == null) {
                  return null;
               } else {
                  return (T)(!NotificationLite.isComplete(var4) && !NotificationLite.isError(var4) ? var4 : var2.value);
               }
            }

            var2 = var1;
            var1 = var3;
         }
      }

      @Override
      public T[] getValues(T[] var1) {
         ReplaySubject.Node var7 = this.head;
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
            ReplaySubject.Node var8 = var7;
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
      public void replay(ReplaySubject.ReplayDisposable<T> var1) {
         if (var1.getAndIncrement() == 0) {
            Observer var6 = var1.downstream;
            ReplaySubject.Node var5 = (ReplaySubject.Node)var1.index;
            ReplaySubject.Node var4 = var5;
            if (var5 == null) {
               var4 = this.head;
            }

            int var2 = 1;

            while (!var1.cancelled) {
               var5 = var4.get();
               if (var5 == null) {
                  if (var4.get() == null) {
                     var1.index = var4;
                     int var3 = var1.addAndGet(-var2);
                     var2 = var3;
                     if (var3 == 0) {
                        return;
                     }
                  }
               } else {
                  Object var7 = var5.value;
                  if (this.done && var5.get() == null) {
                     if (NotificationLite.isComplete(var7)) {
                        var6.onComplete();
                     } else {
                        var6.onError(NotificationLite.getError(var7));
                     }

                     var1.index = null;
                     var1.cancelled = true;
                     return;
                  }

                  var6.onNext(var7);
                  var4 = var5;
               }
            }

            var1.index = null;
         }
      }

      @Override
      public int size() {
         ReplaySubject.Node var3 = this.head;
         int var1 = 0;

         int var2;
         while (true) {
            var2 = var1;
            if (var1 == Integer.MAX_VALUE) {
               break;
            }

            ReplaySubject.Node var4 = var3.get();
            if (var4 == null) {
               Object var5 = var3.value;
               if (!NotificationLite.isComplete(var5)) {
                  var2 = var1;
                  if (!NotificationLite.isError(var5)) {
                     break;
                  }
               }

               var2 = var1 - 1;
               break;
            }

            var1++;
            var3 = var4;
         }

         return var2;
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
         ReplaySubject.Node var1 = this.head;
         if (var1.value != null) {
            ReplaySubject.Node var2 = new ReplaySubject.Node(null);
            var2.lazySet(var1.get());
            this.head = var2;
         }
      }
   }

   static final class TimedNode<T> extends AtomicReference<ReplaySubject.TimedNode<T>> {
      private static final long serialVersionUID = 6404226426336033100L;
      final long time;
      final T value;

      TimedNode(T var1, long var2) {
         this.value = (T)var1;
         this.time = var2;
      }
   }

   static final class UnboundedReplayBuffer<T> extends AtomicReference<Object> implements ReplaySubject.ReplayBuffer<T> {
      private static final long serialVersionUID = -733876083048047795L;
      final List<Object> buffer;
      volatile boolean done;
      volatile int size;

      UnboundedReplayBuffer(int var1) {
         this.buffer = new ArrayList<>(ObjectHelper.verifyPositive(var1, "capacityHint"));
      }

      @Override
      public void add(T var1) {
         this.buffer.add(var1);
         this.size++;
      }

      @Override
      public void addFinal(Object var1) {
         this.buffer.add(var1);
         this.trimHead();
         this.size++;
         this.done = true;
      }

      @Override
      public T getValue() {
         int var1 = this.size;
         if (var1 != 0) {
            List var3 = this.buffer;
            Object var2 = var3.get(var1 - 1);
            if (!NotificationLite.isComplete(var2) && !NotificationLite.isError(var2)) {
               return (T)var2;
            } else {
               return (T)(var1 == 1 ? null : var3.get(var1 - 2));
            }
         } else {
            return null;
         }
      }

      @Override
      public T[] getValues(T[] var1) {
         int var3 = this.size;
         byte var4 = 0;
         if (var3 == 0) {
            if (var1.length != 0) {
               var1[0] = null;
            }

            return (T[])var1;
         } else {
            int var2;
            List var6;
            label39: {
               var6 = this.buffer;
               Object var5 = var6.get(var3 - 1);
               if (!NotificationLite.isComplete(var5)) {
                  var2 = var3;
                  if (!NotificationLite.isError(var5)) {
                     break label39;
                  }
               }

               var2 = --var3;
               if (var3 == 0) {
                  if (var1.length != 0) {
                     var1[0] = null;
                  }

                  return (T[])var1;
               }
            }

            var3 = var4;
            Object[] var9 = var1;
            if (var1.length < var2) {
               var9 = (Object[])Array.newInstance(var1.getClass().getComponentType(), var2);
               var3 = var4;
            }

            while (var3 < var2) {
               var9[var3] = var6.get(var3);
               var3++;
            }

            if (var9.length > var2) {
               var9[var2] = null;
            }

            return (T[])var9;
         }
      }

      @Override
      public void replay(ReplaySubject.ReplayDisposable<T> var1) {
         if (var1.getAndIncrement() == 0) {
            List var7 = this.buffer;
            Observer var8 = var1.downstream;
            Integer var9 = (Integer)var1.index;
            int var2;
            if (var9 != null) {
               var2 = var9;
            } else {
               var2 = 0;
               var1.index = 0;
            }

            int var3 = 1;

            while (!var1.cancelled) {
               int var5 = this.size;

               while (var5 != var2) {
                  if (var1.cancelled) {
                     var1.index = null;
                     return;
                  }

                  Object var12 = var7.get(var2);
                  int var4 = var5;
                  if (this.done) {
                     int var6 = var2 + 1;
                     var4 = var5;
                     if (var6 == var5) {
                        var5 = this.size;
                        var4 = var5;
                        if (var6 == var5) {
                           if (NotificationLite.isComplete(var12)) {
                              var8.onComplete();
                           } else {
                              var8.onError(NotificationLite.getError(var12));
                           }

                           var1.index = null;
                           var1.cancelled = true;
                           return;
                        }
                     }
                  }

                  var8.onNext(var12);
                  var2++;
                  var5 = var4;
               }

               if (var2 == this.size) {
                  var1.index = var2;
                  int var10 = var1.addAndGet(-var3);
                  var3 = var10;
                  if (var10 == 0) {
                     return;
                  }
               }
            }

            var1.index = null;
         }
      }

      @Override
      public int size() {
         int var2 = this.size;
         if (var2 != 0) {
            List var3 = this.buffer;
            int var1 = var2 - 1;
            Object var4 = var3.get(var1);
            return !NotificationLite.isComplete(var4) && !NotificationLite.isError(var4) ? var2 : var1;
         } else {
            return 0;
         }
      }

      @Override
      public void trimHead() {
      }
   }
}
