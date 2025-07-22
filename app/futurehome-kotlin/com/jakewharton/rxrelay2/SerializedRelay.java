package com.jakewharton.rxrelay2;

import io.reactivex.Observer;

final class SerializedRelay<T> extends Relay<T> {
   private final Relay<T> actual;
   private boolean emitting;
   private AppendOnlyLinkedArrayList<T> queue;

   SerializedRelay(Relay<T> var1) {
      this.actual = var1;
   }

   private void emitLoop() {
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
      // 03: getfield com/jakewharton/rxrelay2/SerializedRelay.queue Lcom/jakewharton/rxrelay2/AppendOnlyLinkedArrayList;
      // 06: astore 1
      // 07: aload 1
      // 08: ifnonnull 13
      // 0b: aload 0
      // 0c: bipush 0
      // 0d: putfield com/jakewharton/rxrelay2/SerializedRelay.emitting Z
      // 10: aload 0
      // 11: monitorexit
      // 12: return
      // 13: aload 0
      // 14: aconst_null
      // 15: putfield com/jakewharton/rxrelay2/SerializedRelay.queue Lcom/jakewharton/rxrelay2/AppendOnlyLinkedArrayList;
      // 18: aload 0
      // 19: monitorexit
      // 1a: aload 1
      // 1b: aload 0
      // 1c: getfield com/jakewharton/rxrelay2/SerializedRelay.actual Lcom/jakewharton/rxrelay2/Relay;
      // 1f: invokevirtual com/jakewharton/rxrelay2/AppendOnlyLinkedArrayList.accept (Lcom/jakewharton/rxrelay2/Relay;)V
      // 22: goto 00
      // 25: astore 1
      // 26: aload 0
      // 27: monitorexit
      // 28: aload 1
      // 29: athrow
   }

   @Override
   public void accept(T param1) {
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
      // 03: getfield com/jakewharton/rxrelay2/SerializedRelay.emitting Z
      // 06: ifeq 2a
      // 09: aload 0
      // 0a: getfield com/jakewharton/rxrelay2/SerializedRelay.queue Lcom/jakewharton/rxrelay2/AppendOnlyLinkedArrayList;
      // 0d: astore 3
      // 0e: aload 3
      // 0f: astore 2
      // 10: aload 3
      // 11: ifnonnull 22
      // 14: new com/jakewharton/rxrelay2/AppendOnlyLinkedArrayList
      // 17: astore 2
      // 18: aload 2
      // 19: bipush 4
      // 1a: invokespecial com/jakewharton/rxrelay2/AppendOnlyLinkedArrayList.<init> (I)V
      // 1d: aload 0
      // 1e: aload 2
      // 1f: putfield com/jakewharton/rxrelay2/SerializedRelay.queue Lcom/jakewharton/rxrelay2/AppendOnlyLinkedArrayList;
      // 22: aload 2
      // 23: aload 1
      // 24: invokevirtual com/jakewharton/rxrelay2/AppendOnlyLinkedArrayList.add (Ljava/lang/Object;)V
      // 27: aload 0
      // 28: monitorexit
      // 29: return
      // 2a: aload 0
      // 2b: bipush 1
      // 2c: putfield com/jakewharton/rxrelay2/SerializedRelay.emitting Z
      // 2f: aload 0
      // 30: monitorexit
      // 31: aload 0
      // 32: getfield com/jakewharton/rxrelay2/SerializedRelay.actual Lcom/jakewharton/rxrelay2/Relay;
      // 35: aload 1
      // 36: invokevirtual com/jakewharton/rxrelay2/Relay.accept (Ljava/lang/Object;)V
      // 39: aload 0
      // 3a: invokespecial com/jakewharton/rxrelay2/SerializedRelay.emitLoop ()V
      // 3d: return
      // 3e: astore 1
      // 3f: aload 0
      // 40: monitorexit
      // 41: aload 1
      // 42: athrow
   }

   @Override
   public boolean hasObservers() {
      return this.actual.hasObservers();
   }

   @Override
   protected void subscribeActual(Observer<? super T> var1) {
      this.actual.subscribe(var1);
   }
}
