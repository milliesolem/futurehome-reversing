package com.jakewharton.rxrelay2;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.Observer;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.disposables.Disposable;
import java.lang.reflect.Array;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public final class BehaviorRelay<T> extends Relay<T> {
   static final BehaviorRelay.BehaviorDisposable[] EMPTY = new BehaviorRelay.BehaviorDisposable[0];
   private static final Object[] EMPTY_ARRAY = new Object[0];
   long index;
   final Lock readLock;
   final AtomicReference<BehaviorRelay.BehaviorDisposable<T>[]> subscribers;
   final AtomicReference<T> value;
   final Lock writeLock;

   BehaviorRelay() {
      ReentrantReadWriteLock var1 = new ReentrantReadWriteLock();
      this.readLock = var1.readLock();
      this.writeLock = var1.writeLock();
      this.subscribers = new AtomicReference<>(EMPTY);
      this.value = new AtomicReference<>();
   }

   BehaviorRelay(T var1) {
      this();
      if (var1 != null) {
         this.value.lazySet((T)var1);
      } else {
         throw new NullPointerException("defaultValue == null");
      }
   }

   @CheckReturnValue
   public static <T> BehaviorRelay<T> create() {
      return new BehaviorRelay<>();
   }

   @CheckReturnValue
   public static <T> BehaviorRelay<T> createDefault(T var0) {
      return new BehaviorRelay<>((T)var0);
   }

   @Override
   public void accept(T var1) {
      if (var1 == null) {
         throw new NullPointerException("value == null");
      } else {
         this.setCurrent((T)var1);
         BehaviorRelay.BehaviorDisposable[] var4 = this.subscribers.get();
         int var3 = var4.length;

         for (int var2 = 0; var2 < var3; var2++) {
            var4[var2].emitNext(var1, this.index);
         }
      }
   }

   void add(BehaviorRelay.BehaviorDisposable<T> var1) {
      BehaviorRelay.BehaviorDisposable[] var3;
      BehaviorRelay.BehaviorDisposable[] var4;
      do {
         var4 = this.subscribers.get();
         int var2 = var4.length;
         var3 = new BehaviorRelay.BehaviorDisposable[var2 + 1];
         System.arraycopy(var4, 0, var3, 0, var2);
         var3[var2] = var1;
      } while (!ExternalSyntheticBackportWithForwarding0.m(this.subscribers, var4, var3));
   }

   public T getValue() {
      return this.value.get();
   }

   @Deprecated
   public Object[] getValues() {
      Object[] var1 = EMPTY_ARRAY;
      Object[] var2 = this.getValues((T[])var1);
      return var2 == var1 ? new Object[0] : var2;
   }

   @Deprecated
   public T[] getValues(T[] var1) {
      Object var3 = this.value.get();
      if (var3 == null) {
         if (var1.length != 0) {
            var1[0] = null;
         }

         return (T[])var1;
      } else {
         Object[] var2;
         if (var1.length != 0) {
            var1[0] = var3;
            var2 = var1;
            if (var1.length != 1) {
               var1[1] = null;
               var2 = var1;
            }
         } else {
            var2 = (Object[])Array.newInstance(var1.getClass().getComponentType(), 1);
            var2[0] = var3;
         }

         return (T[])var2;
      }
   }

   @Override
   public boolean hasObservers() {
      boolean var1;
      if (((BehaviorRelay.BehaviorDisposable[])this.subscribers.get()).length != 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean hasValue() {
      boolean var1;
      if (this.value.get() != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   void remove(BehaviorRelay.BehaviorDisposable<T> var1) {
      BehaviorRelay.BehaviorDisposable[] var4;
      BehaviorRelay.BehaviorDisposable[] var5;
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
            var4 = new BehaviorRelay.BehaviorDisposable[var3 - 1];
            System.arraycopy(var5, 0, var4, 0, var2);
            System.arraycopy(var5, var2 + 1, var4, var2, var3 - var2 - 1);
         }
      } while (!ExternalSyntheticBackportWithForwarding0.m(this.subscribers, var5, var4));
   }

   void setCurrent(T var1) {
      this.writeLock.lock();
      this.index++;
      this.value.lazySet((T)var1);
      this.writeLock.unlock();
   }

   @Override
   protected void subscribeActual(Observer<? super T> var1) {
      BehaviorRelay.BehaviorDisposable var2 = new BehaviorRelay.BehaviorDisposable<>(var1, this);
      var1.onSubscribe(var2);
      this.add(var2);
      if (var2.cancelled) {
         this.remove(var2);
      } else {
         var2.emitFirst();
      }
   }

   int subscriberCount() {
      return ((BehaviorRelay.BehaviorDisposable[])this.subscribers.get()).length;
   }

   static final class BehaviorDisposable<T> implements Disposable, AppendOnlyLinkedArrayList.NonThrowingPredicate<T> {
      volatile boolean cancelled;
      final Observer<? super T> downstream;
      boolean emitting;
      boolean fastPath;
      long index;
      boolean next;
      AppendOnlyLinkedArrayList<T> queue;
      final BehaviorRelay<T> state;

      BehaviorDisposable(Observer<? super T> var1, BehaviorRelay<T> var2) {
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

      void emitFirst() {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
         //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
         //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
         //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
         //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
         //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
         //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
         //
         // Bytecode:
         // 00: aload 0
         // 01: getfield com/jakewharton/rxrelay2/BehaviorRelay$BehaviorDisposable.cancelled Z
         // 04: ifeq 08
         // 07: return
         // 08: aload 0
         // 09: monitorenter
         // 0a: aload 0
         // 0b: getfield com/jakewharton/rxrelay2/BehaviorRelay$BehaviorDisposable.cancelled Z
         // 0e: ifeq 14
         // 11: aload 0
         // 12: monitorexit
         // 13: return
         // 14: aload 0
         // 15: getfield com/jakewharton/rxrelay2/BehaviorRelay$BehaviorDisposable.next Z
         // 18: ifeq 1e
         // 1b: aload 0
         // 1c: monitorexit
         // 1d: return
         // 1e: aload 0
         // 1f: getfield com/jakewharton/rxrelay2/BehaviorRelay$BehaviorDisposable.state Lcom/jakewharton/rxrelay2/BehaviorRelay;
         // 22: astore 3
         // 23: aload 3
         // 24: getfield com/jakewharton/rxrelay2/BehaviorRelay.readLock Ljava/util/concurrent/locks/Lock;
         // 27: astore 2
         // 28: aload 2
         // 29: invokeinterface java/util/concurrent/locks/Lock.lock ()V 1
         // 2e: aload 0
         // 2f: aload 3
         // 30: getfield com/jakewharton/rxrelay2/BehaviorRelay.index J
         // 33: putfield com/jakewharton/rxrelay2/BehaviorRelay$BehaviorDisposable.index J
         // 36: aload 3
         // 37: getfield com/jakewharton/rxrelay2/BehaviorRelay.value Ljava/util/concurrent/atomic/AtomicReference;
         // 3a: invokevirtual java/util/concurrent/atomic/AtomicReference.get ()Ljava/lang/Object;
         // 3d: astore 3
         // 3e: aload 2
         // 3f: invokeinterface java/util/concurrent/locks/Lock.unlock ()V 1
         // 44: aload 3
         // 45: ifnull 4d
         // 48: bipush 1
         // 49: istore 1
         // 4a: goto 4f
         // 4d: bipush 0
         // 4e: istore 1
         // 4f: aload 0
         // 50: iload 1
         // 51: putfield com/jakewharton/rxrelay2/BehaviorRelay$BehaviorDisposable.emitting Z
         // 54: aload 0
         // 55: bipush 1
         // 56: putfield com/jakewharton/rxrelay2/BehaviorRelay$BehaviorDisposable.next Z
         // 59: aload 0
         // 5a: monitorexit
         // 5b: aload 3
         // 5c: ifnull 69
         // 5f: aload 0
         // 60: aload 3
         // 61: invokevirtual com/jakewharton/rxrelay2/BehaviorRelay$BehaviorDisposable.test (Ljava/lang/Object;)Z
         // 64: pop
         // 65: aload 0
         // 66: invokevirtual com/jakewharton/rxrelay2/BehaviorRelay$BehaviorDisposable.emitLoop ()V
         // 69: return
         // 6a: astore 2
         // 6b: aload 0
         // 6c: monitorexit
         // 6d: aload 2
         // 6e: athrow
      }

      void emitLoop() {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
         //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
         //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
         //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
         //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
         //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
         //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
         //
         // Bytecode:
         // 00: aload 0
         // 01: getfield com/jakewharton/rxrelay2/BehaviorRelay$BehaviorDisposable.cancelled Z
         // 04: ifeq 08
         // 07: return
         // 08: aload 0
         // 09: monitorenter
         // 0a: aload 0
         // 0b: getfield com/jakewharton/rxrelay2/BehaviorRelay$BehaviorDisposable.queue Lcom/jakewharton/rxrelay2/AppendOnlyLinkedArrayList;
         // 0e: astore 1
         // 0f: aload 1
         // 10: ifnonnull 1b
         // 13: aload 0
         // 14: bipush 0
         // 15: putfield com/jakewharton/rxrelay2/BehaviorRelay$BehaviorDisposable.emitting Z
         // 18: aload 0
         // 19: monitorexit
         // 1a: return
         // 1b: aload 0
         // 1c: aconst_null
         // 1d: putfield com/jakewharton/rxrelay2/BehaviorRelay$BehaviorDisposable.queue Lcom/jakewharton/rxrelay2/AppendOnlyLinkedArrayList;
         // 20: aload 0
         // 21: monitorexit
         // 22: aload 1
         // 23: aload 0
         // 24: invokevirtual com/jakewharton/rxrelay2/AppendOnlyLinkedArrayList.forEachWhile (Lcom/jakewharton/rxrelay2/AppendOnlyLinkedArrayList$NonThrowingPredicate;)V
         // 27: goto 00
         // 2a: astore 1
         // 2b: aload 0
         // 2c: monitorexit
         // 2d: aload 1
         // 2e: athrow
      }

      void emitNext(T param1, long param2) {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
         //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
         //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
         //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
         //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
         //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
         //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
         //
         // Bytecode:
         // 00: aload 0
         // 01: getfield com/jakewharton/rxrelay2/BehaviorRelay$BehaviorDisposable.cancelled Z
         // 04: ifeq 08
         // 07: return
         // 08: aload 0
         // 09: getfield com/jakewharton/rxrelay2/BehaviorRelay$BehaviorDisposable.fastPath Z
         // 0c: ifne 6b
         // 0f: aload 0
         // 10: monitorenter
         // 11: aload 0
         // 12: getfield com/jakewharton/rxrelay2/BehaviorRelay$BehaviorDisposable.cancelled Z
         // 15: ifeq 1b
         // 18: aload 0
         // 19: monitorexit
         // 1a: return
         // 1b: aload 0
         // 1c: getfield com/jakewharton/rxrelay2/BehaviorRelay$BehaviorDisposable.index J
         // 1f: lload 2
         // 20: lcmp
         // 21: ifne 27
         // 24: aload 0
         // 25: monitorexit
         // 26: return
         // 27: aload 0
         // 28: getfield com/jakewharton/rxrelay2/BehaviorRelay$BehaviorDisposable.emitting Z
         // 2b: ifeq 57
         // 2e: aload 0
         // 2f: getfield com/jakewharton/rxrelay2/BehaviorRelay$BehaviorDisposable.queue Lcom/jakewharton/rxrelay2/AppendOnlyLinkedArrayList;
         // 32: astore 5
         // 34: aload 5
         // 36: astore 4
         // 38: aload 5
         // 3a: ifnonnull 4e
         // 3d: new com/jakewharton/rxrelay2/AppendOnlyLinkedArrayList
         // 40: astore 4
         // 42: aload 4
         // 44: bipush 4
         // 45: invokespecial com/jakewharton/rxrelay2/AppendOnlyLinkedArrayList.<init> (I)V
         // 48: aload 0
         // 49: aload 4
         // 4b: putfield com/jakewharton/rxrelay2/BehaviorRelay$BehaviorDisposable.queue Lcom/jakewharton/rxrelay2/AppendOnlyLinkedArrayList;
         // 4e: aload 4
         // 50: aload 1
         // 51: invokevirtual com/jakewharton/rxrelay2/AppendOnlyLinkedArrayList.add (Ljava/lang/Object;)V
         // 54: aload 0
         // 55: monitorexit
         // 56: return
         // 57: aload 0
         // 58: bipush 1
         // 59: putfield com/jakewharton/rxrelay2/BehaviorRelay$BehaviorDisposable.next Z
         // 5c: aload 0
         // 5d: monitorexit
         // 5e: aload 0
         // 5f: bipush 1
         // 60: putfield com/jakewharton/rxrelay2/BehaviorRelay$BehaviorDisposable.fastPath Z
         // 63: goto 6b
         // 66: astore 1
         // 67: aload 0
         // 68: monitorexit
         // 69: aload 1
         // 6a: athrow
         // 6b: aload 0
         // 6c: aload 1
         // 6d: invokevirtual com/jakewharton/rxrelay2/BehaviorRelay$BehaviorDisposable.test (Ljava/lang/Object;)Z
         // 70: pop
         // 71: return
      }

      @Override
      public boolean isDisposed() {
         return this.cancelled;
      }

      @Override
      public boolean test(T var1) {
         if (!this.cancelled) {
            this.downstream.onNext((T)var1);
         }

         return false;
      }
   }
}
