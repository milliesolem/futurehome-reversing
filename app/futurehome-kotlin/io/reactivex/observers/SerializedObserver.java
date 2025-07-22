package io.reactivex.observers;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.util.AppendOnlyLinkedArrayList;

public final class SerializedObserver<T> implements Observer<T>, Disposable {
   static final int QUEUE_LINK_SIZE = 4;
   final boolean delayError;
   volatile boolean done;
   final Observer<? super T> downstream;
   boolean emitting;
   AppendOnlyLinkedArrayList<Object> queue;
   Disposable upstream;

   public SerializedObserver(Observer<? super T> var1) {
      this(var1, false);
   }

   public SerializedObserver(Observer<? super T> var1, boolean var2) {
      this.downstream = var1;
      this.delayError = var2;
   }

   @Override
   public void dispose() {
      this.upstream.dispose();
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
      // 01: monitorenter
      // 02: aload 0
      // 03: getfield io/reactivex/observers/SerializedObserver.queue Lio/reactivex/internal/util/AppendOnlyLinkedArrayList;
      // 06: astore 1
      // 07: aload 1
      // 08: ifnonnull 13
      // 0b: aload 0
      // 0c: bipush 0
      // 0d: putfield io/reactivex/observers/SerializedObserver.emitting Z
      // 10: aload 0
      // 11: monitorexit
      // 12: return
      // 13: aload 0
      // 14: aconst_null
      // 15: putfield io/reactivex/observers/SerializedObserver.queue Lio/reactivex/internal/util/AppendOnlyLinkedArrayList;
      // 18: aload 0
      // 19: monitorexit
      // 1a: aload 1
      // 1b: aload 0
      // 1c: getfield io/reactivex/observers/SerializedObserver.downstream Lio/reactivex/Observer;
      // 1f: invokevirtual io/reactivex/internal/util/AppendOnlyLinkedArrayList.accept (Lio/reactivex/Observer;)Z
      // 22: ifeq 00
      // 25: return
      // 26: astore 1
      // 27: aload 0
      // 28: monitorexit
      // 29: aload 1
      // 2a: athrow
   }

   @Override
   public boolean isDisposed() {
      return this.upstream.isDisposed();
   }

   @Override
   public void onComplete() {
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
      // 01: getfield io/reactivex/observers/SerializedObserver.done Z
      // 04: ifeq 08
      // 07: return
      // 08: aload 0
      // 09: monitorenter
      // 0a: aload 0
      // 0b: getfield io/reactivex/observers/SerializedObserver.done Z
      // 0e: ifeq 14
      // 11: aload 0
      // 12: monitorexit
      // 13: return
      // 14: aload 0
      // 15: getfield io/reactivex/observers/SerializedObserver.emitting Z
      // 18: ifeq 3e
      // 1b: aload 0
      // 1c: getfield io/reactivex/observers/SerializedObserver.queue Lio/reactivex/internal/util/AppendOnlyLinkedArrayList;
      // 1f: astore 2
      // 20: aload 2
      // 21: astore 1
      // 22: aload 2
      // 23: ifnonnull 34
      // 26: new io/reactivex/internal/util/AppendOnlyLinkedArrayList
      // 29: astore 1
      // 2a: aload 1
      // 2b: bipush 4
      // 2c: invokespecial io/reactivex/internal/util/AppendOnlyLinkedArrayList.<init> (I)V
      // 2f: aload 0
      // 30: aload 1
      // 31: putfield io/reactivex/observers/SerializedObserver.queue Lio/reactivex/internal/util/AppendOnlyLinkedArrayList;
      // 34: aload 1
      // 35: invokestatic io/reactivex/internal/util/NotificationLite.complete ()Ljava/lang/Object;
      // 38: invokevirtual io/reactivex/internal/util/AppendOnlyLinkedArrayList.add (Ljava/lang/Object;)V
      // 3b: aload 0
      // 3c: monitorexit
      // 3d: return
      // 3e: aload 0
      // 3f: bipush 1
      // 40: putfield io/reactivex/observers/SerializedObserver.done Z
      // 43: aload 0
      // 44: bipush 1
      // 45: putfield io/reactivex/observers/SerializedObserver.emitting Z
      // 48: aload 0
      // 49: monitorexit
      // 4a: aload 0
      // 4b: getfield io/reactivex/observers/SerializedObserver.downstream Lio/reactivex/Observer;
      // 4e: invokeinterface io/reactivex/Observer.onComplete ()V 1
      // 53: return
      // 54: astore 1
      // 55: aload 0
      // 56: monitorexit
      // 57: aload 1
      // 58: athrow
   }

   @Override
   public void onError(Throwable param1) {
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
      // 01: getfield io/reactivex/observers/SerializedObserver.done Z
      // 04: ifeq 0c
      // 07: aload 1
      // 08: invokestatic io/reactivex/plugins/RxJavaPlugins.onError (Ljava/lang/Throwable;)V
      // 0b: return
      // 0c: aload 0
      // 0d: monitorenter
      // 0e: aload 0
      // 0f: getfield io/reactivex/observers/SerializedObserver.done Z
      // 12: istore 3
      // 13: bipush 1
      // 14: istore 2
      // 15: iload 3
      // 16: ifeq 1c
      // 19: goto 72
      // 1c: aload 0
      // 1d: getfield io/reactivex/observers/SerializedObserver.emitting Z
      // 20: ifeq 66
      // 23: aload 0
      // 24: bipush 1
      // 25: putfield io/reactivex/observers/SerializedObserver.done Z
      // 28: aload 0
      // 29: getfield io/reactivex/observers/SerializedObserver.queue Lio/reactivex/internal/util/AppendOnlyLinkedArrayList;
      // 2c: astore 5
      // 2e: aload 5
      // 30: astore 4
      // 32: aload 5
      // 34: ifnonnull 48
      // 37: new io/reactivex/internal/util/AppendOnlyLinkedArrayList
      // 3a: astore 4
      // 3c: aload 4
      // 3e: bipush 4
      // 3f: invokespecial io/reactivex/internal/util/AppendOnlyLinkedArrayList.<init> (I)V
      // 42: aload 0
      // 43: aload 4
      // 45: putfield io/reactivex/observers/SerializedObserver.queue Lio/reactivex/internal/util/AppendOnlyLinkedArrayList;
      // 48: aload 1
      // 49: invokestatic io/reactivex/internal/util/NotificationLite.error (Ljava/lang/Throwable;)Ljava/lang/Object;
      // 4c: astore 1
      // 4d: aload 0
      // 4e: getfield io/reactivex/observers/SerializedObserver.delayError Z
      // 51: ifeq 5d
      // 54: aload 4
      // 56: aload 1
      // 57: invokevirtual io/reactivex/internal/util/AppendOnlyLinkedArrayList.add (Ljava/lang/Object;)V
      // 5a: goto 63
      // 5d: aload 4
      // 5f: aload 1
      // 60: invokevirtual io/reactivex/internal/util/AppendOnlyLinkedArrayList.setFirst (Ljava/lang/Object;)V
      // 63: aload 0
      // 64: monitorexit
      // 65: return
      // 66: aload 0
      // 67: bipush 1
      // 68: putfield io/reactivex/observers/SerializedObserver.done Z
      // 6b: aload 0
      // 6c: bipush 1
      // 6d: putfield io/reactivex/observers/SerializedObserver.emitting Z
      // 70: bipush 0
      // 71: istore 2
      // 72: aload 0
      // 73: monitorexit
      // 74: iload 2
      // 75: ifeq 7d
      // 78: aload 1
      // 79: invokestatic io/reactivex/plugins/RxJavaPlugins.onError (Ljava/lang/Throwable;)V
      // 7c: return
      // 7d: aload 0
      // 7e: getfield io/reactivex/observers/SerializedObserver.downstream Lio/reactivex/Observer;
      // 81: aload 1
      // 82: invokeinterface io/reactivex/Observer.onError (Ljava/lang/Throwable;)V 2
      // 87: return
      // 88: astore 1
      // 89: aload 0
      // 8a: monitorexit
      // 8b: aload 1
      // 8c: athrow
   }

   @Override
   public void onNext(T param1) {
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
      // 01: getfield io/reactivex/observers/SerializedObserver.done Z
      // 04: ifeq 08
      // 07: return
      // 08: aload 1
      // 09: ifnonnull 23
      // 0c: aload 0
      // 0d: getfield io/reactivex/observers/SerializedObserver.upstream Lio/reactivex/disposables/Disposable;
      // 10: invokeinterface io/reactivex/disposables/Disposable.dispose ()V 1
      // 15: aload 0
      // 16: new java/lang/NullPointerException
      // 19: dup
      // 1a: ldc "onNext called with null. Null values are generally not allowed in 2.x operators and sources."
      // 1c: invokespecial java/lang/NullPointerException.<init> (Ljava/lang/String;)V
      // 1f: invokevirtual io/reactivex/observers/SerializedObserver.onError (Ljava/lang/Throwable;)V
      // 22: return
      // 23: aload 0
      // 24: monitorenter
      // 25: aload 0
      // 26: getfield io/reactivex/observers/SerializedObserver.done Z
      // 29: ifeq 2f
      // 2c: aload 0
      // 2d: monitorexit
      // 2e: return
      // 2f: aload 0
      // 30: getfield io/reactivex/observers/SerializedObserver.emitting Z
      // 33: ifeq 5a
      // 36: aload 0
      // 37: getfield io/reactivex/observers/SerializedObserver.queue Lio/reactivex/internal/util/AppendOnlyLinkedArrayList;
      // 3a: astore 3
      // 3b: aload 3
      // 3c: astore 2
      // 3d: aload 3
      // 3e: ifnonnull 4f
      // 41: new io/reactivex/internal/util/AppendOnlyLinkedArrayList
      // 44: astore 2
      // 45: aload 2
      // 46: bipush 4
      // 47: invokespecial io/reactivex/internal/util/AppendOnlyLinkedArrayList.<init> (I)V
      // 4a: aload 0
      // 4b: aload 2
      // 4c: putfield io/reactivex/observers/SerializedObserver.queue Lio/reactivex/internal/util/AppendOnlyLinkedArrayList;
      // 4f: aload 2
      // 50: aload 1
      // 51: invokestatic io/reactivex/internal/util/NotificationLite.next (Ljava/lang/Object;)Ljava/lang/Object;
      // 54: invokevirtual io/reactivex/internal/util/AppendOnlyLinkedArrayList.add (Ljava/lang/Object;)V
      // 57: aload 0
      // 58: monitorexit
      // 59: return
      // 5a: aload 0
      // 5b: bipush 1
      // 5c: putfield io/reactivex/observers/SerializedObserver.emitting Z
      // 5f: aload 0
      // 60: monitorexit
      // 61: aload 0
      // 62: getfield io/reactivex/observers/SerializedObserver.downstream Lio/reactivex/Observer;
      // 65: aload 1
      // 66: invokeinterface io/reactivex/Observer.onNext (Ljava/lang/Object;)V 2
      // 6b: aload 0
      // 6c: invokevirtual io/reactivex/observers/SerializedObserver.emitLoop ()V
      // 6f: return
      // 70: astore 1
      // 71: aload 0
      // 72: monitorexit
      // 73: aload 1
      // 74: athrow
   }

   @Override
   public void onSubscribe(Disposable var1) {
      if (DisposableHelper.validate(this.upstream, var1)) {
         this.upstream = var1;
         this.downstream.onSubscribe(this);
      }
   }
}
