package io.reactivex.subjects;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.Observer;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.AppendOnlyLinkedArrayList;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.plugins.RxJavaPlugins;
import java.lang.reflect.Array;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public final class BehaviorSubject<T> extends Subject<T> {
   static final BehaviorSubject.BehaviorDisposable[] EMPTY = new BehaviorSubject.BehaviorDisposable[0];
   private static final Object[] EMPTY_ARRAY = new Object[0];
   static final BehaviorSubject.BehaviorDisposable[] TERMINATED = new BehaviorSubject.BehaviorDisposable[0];
   long index;
   final ReadWriteLock lock;
   final Lock readLock;
   final AtomicReference<BehaviorSubject.BehaviorDisposable<T>[]> subscribers;
   final AtomicReference<Throwable> terminalEvent;
   final AtomicReference<Object> value;
   final Lock writeLock;

   BehaviorSubject() {
      ReentrantReadWriteLock var1 = new ReentrantReadWriteLock();
      this.lock = var1;
      this.readLock = var1.readLock();
      this.writeLock = var1.writeLock();
      this.subscribers = new AtomicReference<>(EMPTY);
      this.value = new AtomicReference<>();
      this.terminalEvent = new AtomicReference<>();
   }

   BehaviorSubject(T var1) {
      this();
      this.value.lazySet(ObjectHelper.requireNonNull(var1, "defaultValue is null"));
   }

   @CheckReturnValue
   public static <T> BehaviorSubject<T> create() {
      return new BehaviorSubject<>();
   }

   @CheckReturnValue
   public static <T> BehaviorSubject<T> createDefault(T var0) {
      return new BehaviorSubject<>((T)var0);
   }

   boolean add(BehaviorSubject.BehaviorDisposable<T> var1) {
      BehaviorSubject.BehaviorDisposable[] var3;
      BehaviorSubject.BehaviorDisposable[] var4;
      do {
         var4 = this.subscribers.get();
         if (var4 == TERMINATED) {
            return false;
         }

         int var2 = var4.length;
         var3 = new BehaviorSubject.BehaviorDisposable[var2 + 1];
         System.arraycopy(var4, 0, var3, 0, var2);
         var3[var2] = var1;
      } while (!ExternalSyntheticBackportWithForwarding0.m(this.subscribers, var4, var3));

      return true;
   }

   @Override
   public Throwable getThrowable() {
      Object var1 = this.value.get();
      return NotificationLite.isError(var1) ? NotificationLite.getError(var1) : null;
   }

   public T getValue() {
      Object var1 = this.value.get();
      return !NotificationLite.isComplete(var1) && !NotificationLite.isError(var1) ? NotificationLite.getValue(var1) : null;
   }

   @Deprecated
   public Object[] getValues() {
      Object[] var1 = EMPTY_ARRAY;
      Object[] var2 = this.getValues((T[])var1);
      return var2 == var1 ? new Object[0] : var2;
   }

   @Deprecated
   public T[] getValues(T[] var1) {
      Object var2 = this.value.get();
      if (var2 != null && !NotificationLite.isComplete(var2) && !NotificationLite.isError(var2)) {
         Object var3 = NotificationLite.getValue(var2);
         if (var1.length != 0) {
            var1[0] = var3;
            var2 = var1;
            if (var1.length != 1) {
               var1[1] = null;
               var2 = var1;
            }
         } else {
            var2 = (Object[])Array.newInstance(var1.getClass().getComponentType(), 1);
            ((Object[])var2)[0] = var3;
         }

         return (T[])var2;
      } else {
         if (var1.length != 0) {
            var1[0] = null;
         }

         return (T[])var1;
      }
   }

   @Override
   public boolean hasComplete() {
      return NotificationLite.isComplete(this.value.get());
   }

   @Override
   public boolean hasObservers() {
      boolean var1;
      if (((BehaviorSubject.BehaviorDisposable[])this.subscribers.get()).length != 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public boolean hasThrowable() {
      return NotificationLite.isError(this.value.get());
   }

   public boolean hasValue() {
      Object var2 = this.value.get();
      boolean var1;
      if (var2 != null && !NotificationLite.isComplete(var2) && !NotificationLite.isError(var2)) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public void onComplete() {
      if (ExternalSyntheticBackportWithForwarding0.m(this.terminalEvent, null, ExceptionHelper.TERMINATED)) {
         Object var4 = NotificationLite.complete();
         BehaviorSubject.BehaviorDisposable[] var3 = this.terminate(var4);
         int var2 = var3.length;

         for (int var1 = 0; var1 < var2; var1++) {
            var3[var1].emitNext(var4, this.index);
         }
      }
   }

   @Override
   public void onError(Throwable var1) {
      ObjectHelper.requireNonNull(var1, "onError called with null. Null values are generally not allowed in 2.x operators and sources.");
      if (!ExternalSyntheticBackportWithForwarding0.m(this.terminalEvent, null, var1)) {
         RxJavaPlugins.onError(var1);
      } else {
         Object var5 = NotificationLite.error(var1);
         BehaviorSubject.BehaviorDisposable[] var4 = this.terminate(var5);
         int var3 = var4.length;

         for (int var2 = 0; var2 < var3; var2++) {
            var4[var2].emitNext(var5, this.index);
         }
      }
   }

   @Override
   public void onNext(T var1) {
      ObjectHelper.requireNonNull(var1, "onNext called with null. Null values are generally not allowed in 2.x operators and sources.");
      if (this.terminalEvent.get() == null) {
         Object var4 = NotificationLite.next(var1);
         this.setCurrent(var4);
         var1 = this.subscribers.get();
         int var3 = var1.length;

         for (int var2 = 0; var2 < var3; var2++) {
            var1[var2].emitNext(var4, this.index);
         }
      }
   }

   @Override
   public void onSubscribe(Disposable var1) {
      if (this.terminalEvent.get() != null) {
         var1.dispose();
      }
   }

   void remove(BehaviorSubject.BehaviorDisposable<T> var1) {
      BehaviorSubject.BehaviorDisposable[] var4;
      BehaviorSubject.BehaviorDisposable[] var5;
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
            var4 = new BehaviorSubject.BehaviorDisposable[var3 - 1];
            System.arraycopy(var5, 0, var4, 0, var2);
            System.arraycopy(var5, var2 + 1, var4, var2, var3 - var2 - 1);
         }
      } while (!ExternalSyntheticBackportWithForwarding0.m(this.subscribers, var5, var4));
   }

   void setCurrent(Object var1) {
      this.writeLock.lock();
      this.index++;
      this.value.lazySet(var1);
      this.writeLock.unlock();
   }

   @Override
   protected void subscribeActual(Observer<? super T> var1) {
      BehaviorSubject.BehaviorDisposable var2 = new BehaviorSubject.BehaviorDisposable<>(var1, this);
      var1.onSubscribe(var2);
      if (this.add(var2)) {
         if (var2.cancelled) {
            this.remove(var2);
         } else {
            var2.emitFirst();
         }
      } else {
         Throwable var3 = this.terminalEvent.get();
         if (var3 == ExceptionHelper.TERMINATED) {
            var1.onComplete();
         } else {
            var1.onError(var3);
         }
      }
   }

   int subscriberCount() {
      return ((BehaviorSubject.BehaviorDisposable[])this.subscribers.get()).length;
   }

   BehaviorSubject.BehaviorDisposable<T>[] terminate(Object var1) {
      AtomicReference var3 = this.subscribers;
      BehaviorSubject.BehaviorDisposable[] var2 = TERMINATED;
      BehaviorSubject.BehaviorDisposable[] var4 = var3.getAndSet(var2);
      if (var4 != var2) {
         this.setCurrent(var1);
      }

      return var4;
   }

   static final class BehaviorDisposable<T> implements Disposable, AppendOnlyLinkedArrayList.NonThrowingPredicate<Object> {
      volatile boolean cancelled;
      final Observer<? super T> downstream;
      boolean emitting;
      boolean fastPath;
      long index;
      boolean next;
      AppendOnlyLinkedArrayList<Object> queue;
      final BehaviorSubject<T> state;

      BehaviorDisposable(Observer<? super T> var1, BehaviorSubject<T> var2) {
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
         // 01: getfield io/reactivex/subjects/BehaviorSubject$BehaviorDisposable.cancelled Z
         // 04: ifeq 08
         // 07: return
         // 08: aload 0
         // 09: monitorenter
         // 0a: aload 0
         // 0b: getfield io/reactivex/subjects/BehaviorSubject$BehaviorDisposable.cancelled Z
         // 0e: ifeq 14
         // 11: aload 0
         // 12: monitorexit
         // 13: return
         // 14: aload 0
         // 15: getfield io/reactivex/subjects/BehaviorSubject$BehaviorDisposable.next Z
         // 18: ifeq 1e
         // 1b: aload 0
         // 1c: monitorexit
         // 1d: return
         // 1e: aload 0
         // 1f: getfield io/reactivex/subjects/BehaviorSubject$BehaviorDisposable.state Lio/reactivex/subjects/BehaviorSubject;
         // 22: astore 3
         // 23: aload 3
         // 24: getfield io/reactivex/subjects/BehaviorSubject.readLock Ljava/util/concurrent/locks/Lock;
         // 27: astore 2
         // 28: aload 2
         // 29: invokeinterface java/util/concurrent/locks/Lock.lock ()V 1
         // 2e: aload 0
         // 2f: aload 3
         // 30: getfield io/reactivex/subjects/BehaviorSubject.index J
         // 33: putfield io/reactivex/subjects/BehaviorSubject$BehaviorDisposable.index J
         // 36: aload 3
         // 37: getfield io/reactivex/subjects/BehaviorSubject.value Ljava/util/concurrent/atomic/AtomicReference;
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
         // 51: putfield io/reactivex/subjects/BehaviorSubject$BehaviorDisposable.emitting Z
         // 54: aload 0
         // 55: bipush 1
         // 56: putfield io/reactivex/subjects/BehaviorSubject$BehaviorDisposable.next Z
         // 59: aload 0
         // 5a: monitorexit
         // 5b: aload 3
         // 5c: ifnull 6c
         // 5f: aload 0
         // 60: aload 3
         // 61: invokevirtual io/reactivex/subjects/BehaviorSubject$BehaviorDisposable.test (Ljava/lang/Object;)Z
         // 64: ifeq 68
         // 67: return
         // 68: aload 0
         // 69: invokevirtual io/reactivex/subjects/BehaviorSubject$BehaviorDisposable.emitLoop ()V
         // 6c: return
         // 6d: astore 2
         // 6e: aload 0
         // 6f: monitorexit
         // 70: aload 2
         // 71: athrow
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
         // 01: getfield io/reactivex/subjects/BehaviorSubject$BehaviorDisposable.cancelled Z
         // 04: ifeq 08
         // 07: return
         // 08: aload 0
         // 09: monitorenter
         // 0a: aload 0
         // 0b: getfield io/reactivex/subjects/BehaviorSubject$BehaviorDisposable.queue Lio/reactivex/internal/util/AppendOnlyLinkedArrayList;
         // 0e: astore 1
         // 0f: aload 1
         // 10: ifnonnull 1b
         // 13: aload 0
         // 14: bipush 0
         // 15: putfield io/reactivex/subjects/BehaviorSubject$BehaviorDisposable.emitting Z
         // 18: aload 0
         // 19: monitorexit
         // 1a: return
         // 1b: aload 0
         // 1c: aconst_null
         // 1d: putfield io/reactivex/subjects/BehaviorSubject$BehaviorDisposable.queue Lio/reactivex/internal/util/AppendOnlyLinkedArrayList;
         // 20: aload 0
         // 21: monitorexit
         // 22: aload 1
         // 23: aload 0
         // 24: invokevirtual io/reactivex/internal/util/AppendOnlyLinkedArrayList.forEachWhile (Lio/reactivex/internal/util/AppendOnlyLinkedArrayList$NonThrowingPredicate;)V
         // 27: goto 00
         // 2a: astore 1
         // 2b: aload 0
         // 2c: monitorexit
         // 2d: aload 1
         // 2e: athrow
      }

      void emitNext(Object param1, long param2) {
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
         // 01: getfield io/reactivex/subjects/BehaviorSubject$BehaviorDisposable.cancelled Z
         // 04: ifeq 08
         // 07: return
         // 08: aload 0
         // 09: getfield io/reactivex/subjects/BehaviorSubject$BehaviorDisposable.fastPath Z
         // 0c: ifne 6b
         // 0f: aload 0
         // 10: monitorenter
         // 11: aload 0
         // 12: getfield io/reactivex/subjects/BehaviorSubject$BehaviorDisposable.cancelled Z
         // 15: ifeq 1b
         // 18: aload 0
         // 19: monitorexit
         // 1a: return
         // 1b: aload 0
         // 1c: getfield io/reactivex/subjects/BehaviorSubject$BehaviorDisposable.index J
         // 1f: lload 2
         // 20: lcmp
         // 21: ifne 27
         // 24: aload 0
         // 25: monitorexit
         // 26: return
         // 27: aload 0
         // 28: getfield io/reactivex/subjects/BehaviorSubject$BehaviorDisposable.emitting Z
         // 2b: ifeq 57
         // 2e: aload 0
         // 2f: getfield io/reactivex/subjects/BehaviorSubject$BehaviorDisposable.queue Lio/reactivex/internal/util/AppendOnlyLinkedArrayList;
         // 32: astore 5
         // 34: aload 5
         // 36: astore 4
         // 38: aload 5
         // 3a: ifnonnull 4e
         // 3d: new io/reactivex/internal/util/AppendOnlyLinkedArrayList
         // 40: astore 4
         // 42: aload 4
         // 44: bipush 4
         // 45: invokespecial io/reactivex/internal/util/AppendOnlyLinkedArrayList.<init> (I)V
         // 48: aload 0
         // 49: aload 4
         // 4b: putfield io/reactivex/subjects/BehaviorSubject$BehaviorDisposable.queue Lio/reactivex/internal/util/AppendOnlyLinkedArrayList;
         // 4e: aload 4
         // 50: aload 1
         // 51: invokevirtual io/reactivex/internal/util/AppendOnlyLinkedArrayList.add (Ljava/lang/Object;)V
         // 54: aload 0
         // 55: monitorexit
         // 56: return
         // 57: aload 0
         // 58: bipush 1
         // 59: putfield io/reactivex/subjects/BehaviorSubject$BehaviorDisposable.next Z
         // 5c: aload 0
         // 5d: monitorexit
         // 5e: aload 0
         // 5f: bipush 1
         // 60: putfield io/reactivex/subjects/BehaviorSubject$BehaviorDisposable.fastPath Z
         // 63: goto 6b
         // 66: astore 1
         // 67: aload 0
         // 68: monitorexit
         // 69: aload 1
         // 6a: athrow
         // 6b: aload 0
         // 6c: aload 1
         // 6d: invokevirtual io/reactivex/subjects/BehaviorSubject$BehaviorDisposable.test (Ljava/lang/Object;)Z
         // 70: pop
         // 71: return
      }

      @Override
      public boolean isDisposed() {
         return this.cancelled;
      }

      @Override
      public boolean test(Object var1) {
         boolean var2;
         if (!this.cancelled && !NotificationLite.accept(var1, this.downstream)) {
            var2 = false;
         } else {
            var2 = true;
         }

         return var2;
      }
   }
}
