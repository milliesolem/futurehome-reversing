package io.reactivex.subjects;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.util.AppendOnlyLinkedArrayList;
import io.reactivex.internal.util.NotificationLite;

final class SerializedSubject<T> extends Subject<T> implements AppendOnlyLinkedArrayList.NonThrowingPredicate<Object> {
   final Subject<T> actual;
   volatile boolean done;
   boolean emitting;
   AppendOnlyLinkedArrayList<Object> queue;

   SerializedSubject(Subject<T> var1) {
      this.actual = var1;
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
      // 03: getfield io/reactivex/subjects/SerializedSubject.queue Lio/reactivex/internal/util/AppendOnlyLinkedArrayList;
      // 06: astore 1
      // 07: aload 1
      // 08: ifnonnull 13
      // 0b: aload 0
      // 0c: bipush 0
      // 0d: putfield io/reactivex/subjects/SerializedSubject.emitting Z
      // 10: aload 0
      // 11: monitorexit
      // 12: return
      // 13: aload 0
      // 14: aconst_null
      // 15: putfield io/reactivex/subjects/SerializedSubject.queue Lio/reactivex/internal/util/AppendOnlyLinkedArrayList;
      // 18: aload 0
      // 19: monitorexit
      // 1a: aload 1
      // 1b: aload 0
      // 1c: invokevirtual io/reactivex/internal/util/AppendOnlyLinkedArrayList.forEachWhile (Lio/reactivex/internal/util/AppendOnlyLinkedArrayList$NonThrowingPredicate;)V
      // 1f: goto 00
      // 22: astore 1
      // 23: aload 0
      // 24: monitorexit
      // 25: aload 1
      // 26: athrow
   }

   @Override
   public Throwable getThrowable() {
      return this.actual.getThrowable();
   }

   @Override
   public boolean hasComplete() {
      return this.actual.hasComplete();
   }

   @Override
   public boolean hasObservers() {
      return this.actual.hasObservers();
   }

   @Override
   public boolean hasThrowable() {
      return this.actual.hasThrowable();
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
      // 01: getfield io/reactivex/subjects/SerializedSubject.done Z
      // 04: ifeq 08
      // 07: return
      // 08: aload 0
      // 09: monitorenter
      // 0a: aload 0
      // 0b: getfield io/reactivex/subjects/SerializedSubject.done Z
      // 0e: ifeq 14
      // 11: aload 0
      // 12: monitorexit
      // 13: return
      // 14: aload 0
      // 15: bipush 1
      // 16: putfield io/reactivex/subjects/SerializedSubject.done Z
      // 19: aload 0
      // 1a: getfield io/reactivex/subjects/SerializedSubject.emitting Z
      // 1d: ifeq 43
      // 20: aload 0
      // 21: getfield io/reactivex/subjects/SerializedSubject.queue Lio/reactivex/internal/util/AppendOnlyLinkedArrayList;
      // 24: astore 2
      // 25: aload 2
      // 26: astore 1
      // 27: aload 2
      // 28: ifnonnull 39
      // 2b: new io/reactivex/internal/util/AppendOnlyLinkedArrayList
      // 2e: astore 1
      // 2f: aload 1
      // 30: bipush 4
      // 31: invokespecial io/reactivex/internal/util/AppendOnlyLinkedArrayList.<init> (I)V
      // 34: aload 0
      // 35: aload 1
      // 36: putfield io/reactivex/subjects/SerializedSubject.queue Lio/reactivex/internal/util/AppendOnlyLinkedArrayList;
      // 39: aload 1
      // 3a: invokestatic io/reactivex/internal/util/NotificationLite.complete ()Ljava/lang/Object;
      // 3d: invokevirtual io/reactivex/internal/util/AppendOnlyLinkedArrayList.add (Ljava/lang/Object;)V
      // 40: aload 0
      // 41: monitorexit
      // 42: return
      // 43: aload 0
      // 44: bipush 1
      // 45: putfield io/reactivex/subjects/SerializedSubject.emitting Z
      // 48: aload 0
      // 49: monitorexit
      // 4a: aload 0
      // 4b: getfield io/reactivex/subjects/SerializedSubject.actual Lio/reactivex/subjects/Subject;
      // 4e: invokevirtual io/reactivex/subjects/Subject.onComplete ()V
      // 51: return
      // 52: astore 1
      // 53: aload 0
      // 54: monitorexit
      // 55: aload 1
      // 56: athrow
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
      // 01: getfield io/reactivex/subjects/SerializedSubject.done Z
      // 04: ifeq 0c
      // 07: aload 1
      // 08: invokestatic io/reactivex/plugins/RxJavaPlugins.onError (Ljava/lang/Throwable;)V
      // 0b: return
      // 0c: aload 0
      // 0d: monitorenter
      // 0e: aload 0
      // 0f: getfield io/reactivex/subjects/SerializedSubject.done Z
      // 12: istore 3
      // 13: bipush 1
      // 14: istore 2
      // 15: iload 3
      // 16: ifeq 1c
      // 19: goto 5b
      // 1c: aload 0
      // 1d: bipush 1
      // 1e: putfield io/reactivex/subjects/SerializedSubject.done Z
      // 21: aload 0
      // 22: getfield io/reactivex/subjects/SerializedSubject.emitting Z
      // 25: ifeq 54
      // 28: aload 0
      // 29: getfield io/reactivex/subjects/SerializedSubject.queue Lio/reactivex/internal/util/AppendOnlyLinkedArrayList;
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
      // 45: putfield io/reactivex/subjects/SerializedSubject.queue Lio/reactivex/internal/util/AppendOnlyLinkedArrayList;
      // 48: aload 4
      // 4a: aload 1
      // 4b: invokestatic io/reactivex/internal/util/NotificationLite.error (Ljava/lang/Throwable;)Ljava/lang/Object;
      // 4e: invokevirtual io/reactivex/internal/util/AppendOnlyLinkedArrayList.setFirst (Ljava/lang/Object;)V
      // 51: aload 0
      // 52: monitorexit
      // 53: return
      // 54: aload 0
      // 55: bipush 1
      // 56: putfield io/reactivex/subjects/SerializedSubject.emitting Z
      // 59: bipush 0
      // 5a: istore 2
      // 5b: aload 0
      // 5c: monitorexit
      // 5d: iload 2
      // 5e: ifeq 66
      // 61: aload 1
      // 62: invokestatic io/reactivex/plugins/RxJavaPlugins.onError (Ljava/lang/Throwable;)V
      // 65: return
      // 66: aload 0
      // 67: getfield io/reactivex/subjects/SerializedSubject.actual Lio/reactivex/subjects/Subject;
      // 6a: aload 1
      // 6b: invokevirtual io/reactivex/subjects/Subject.onError (Ljava/lang/Throwable;)V
      // 6e: return
      // 6f: astore 1
      // 70: aload 0
      // 71: monitorexit
      // 72: aload 1
      // 73: athrow
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
      // 01: getfield io/reactivex/subjects/SerializedSubject.done Z
      // 04: ifeq 08
      // 07: return
      // 08: aload 0
      // 09: monitorenter
      // 0a: aload 0
      // 0b: getfield io/reactivex/subjects/SerializedSubject.done Z
      // 0e: ifeq 14
      // 11: aload 0
      // 12: monitorexit
      // 13: return
      // 14: aload 0
      // 15: getfield io/reactivex/subjects/SerializedSubject.emitting Z
      // 18: ifeq 3f
      // 1b: aload 0
      // 1c: getfield io/reactivex/subjects/SerializedSubject.queue Lio/reactivex/internal/util/AppendOnlyLinkedArrayList;
      // 1f: astore 3
      // 20: aload 3
      // 21: astore 2
      // 22: aload 3
      // 23: ifnonnull 34
      // 26: new io/reactivex/internal/util/AppendOnlyLinkedArrayList
      // 29: astore 2
      // 2a: aload 2
      // 2b: bipush 4
      // 2c: invokespecial io/reactivex/internal/util/AppendOnlyLinkedArrayList.<init> (I)V
      // 2f: aload 0
      // 30: aload 2
      // 31: putfield io/reactivex/subjects/SerializedSubject.queue Lio/reactivex/internal/util/AppendOnlyLinkedArrayList;
      // 34: aload 2
      // 35: aload 1
      // 36: invokestatic io/reactivex/internal/util/NotificationLite.next (Ljava/lang/Object;)Ljava/lang/Object;
      // 39: invokevirtual io/reactivex/internal/util/AppendOnlyLinkedArrayList.add (Ljava/lang/Object;)V
      // 3c: aload 0
      // 3d: monitorexit
      // 3e: return
      // 3f: aload 0
      // 40: bipush 1
      // 41: putfield io/reactivex/subjects/SerializedSubject.emitting Z
      // 44: aload 0
      // 45: monitorexit
      // 46: aload 0
      // 47: getfield io/reactivex/subjects/SerializedSubject.actual Lio/reactivex/subjects/Subject;
      // 4a: aload 1
      // 4b: invokevirtual io/reactivex/subjects/Subject.onNext (Ljava/lang/Object;)V
      // 4e: aload 0
      // 4f: invokevirtual io/reactivex/subjects/SerializedSubject.emitLoop ()V
      // 52: return
      // 53: astore 1
      // 54: aload 0
      // 55: monitorexit
      // 56: aload 1
      // 57: athrow
   }

   @Override
   public void onSubscribe(Disposable param1) {
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
      // 01: getfield io/reactivex/subjects/SerializedSubject.done Z
      // 04: istore 4
      // 06: bipush 1
      // 07: istore 2
      // 08: bipush 1
      // 09: istore 3
      // 0a: iload 4
      // 0c: ifne 61
      // 0f: aload 0
      // 10: monitorenter
      // 11: aload 0
      // 12: getfield io/reactivex/subjects/SerializedSubject.done Z
      // 15: ifeq 1d
      // 18: iload 3
      // 19: istore 2
      // 1a: goto 57
      // 1d: aload 0
      // 1e: getfield io/reactivex/subjects/SerializedSubject.emitting Z
      // 21: ifeq 50
      // 24: aload 0
      // 25: getfield io/reactivex/subjects/SerializedSubject.queue Lio/reactivex/internal/util/AppendOnlyLinkedArrayList;
      // 28: astore 6
      // 2a: aload 6
      // 2c: astore 5
      // 2e: aload 6
      // 30: ifnonnull 44
      // 33: new io/reactivex/internal/util/AppendOnlyLinkedArrayList
      // 36: astore 5
      // 38: aload 5
      // 3a: bipush 4
      // 3b: invokespecial io/reactivex/internal/util/AppendOnlyLinkedArrayList.<init> (I)V
      // 3e: aload 0
      // 3f: aload 5
      // 41: putfield io/reactivex/subjects/SerializedSubject.queue Lio/reactivex/internal/util/AppendOnlyLinkedArrayList;
      // 44: aload 5
      // 46: aload 1
      // 47: invokestatic io/reactivex/internal/util/NotificationLite.disposable (Lio/reactivex/disposables/Disposable;)Ljava/lang/Object;
      // 4a: invokevirtual io/reactivex/internal/util/AppendOnlyLinkedArrayList.add (Ljava/lang/Object;)V
      // 4d: aload 0
      // 4e: monitorexit
      // 4f: return
      // 50: aload 0
      // 51: bipush 1
      // 52: putfield io/reactivex/subjects/SerializedSubject.emitting Z
      // 55: bipush 0
      // 56: istore 2
      // 57: aload 0
      // 58: monitorexit
      // 59: goto 61
      // 5c: astore 1
      // 5d: aload 0
      // 5e: monitorexit
      // 5f: aload 1
      // 60: athrow
      // 61: iload 2
      // 62: ifeq 6e
      // 65: aload 1
      // 66: invokeinterface io/reactivex/disposables/Disposable.dispose ()V 1
      // 6b: goto 7a
      // 6e: aload 0
      // 6f: getfield io/reactivex/subjects/SerializedSubject.actual Lio/reactivex/subjects/Subject;
      // 72: aload 1
      // 73: invokevirtual io/reactivex/subjects/Subject.onSubscribe (Lio/reactivex/disposables/Disposable;)V
      // 76: aload 0
      // 77: invokevirtual io/reactivex/subjects/SerializedSubject.emitLoop ()V
      // 7a: return
   }

   @Override
   protected void subscribeActual(Observer<? super T> var1) {
      this.actual.subscribe(var1);
   }

   @Override
   public boolean test(Object var1) {
      return NotificationLite.acceptFull(var1, this.actual);
   }
}
