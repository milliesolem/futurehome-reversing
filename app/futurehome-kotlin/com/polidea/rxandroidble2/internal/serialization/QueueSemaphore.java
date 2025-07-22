package com.polidea.rxandroidble2.internal.serialization;

import java.util.concurrent.atomic.AtomicBoolean;

class QueueSemaphore implements QueueReleaseInterface, QueueAwaitReleaseInterface {
   private final AtomicBoolean isReleased = new AtomicBoolean(false);

   @Override
   public void awaitRelease() throws InterruptedException {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: aload 0
      // 01: monitorenter
      // 02: aload 0
      // 03: getfield com/polidea/rxandroidble2/internal/serialization/QueueSemaphore.isReleased Ljava/util/concurrent/atomic/AtomicBoolean;
      // 06: invokevirtual java/util/concurrent/atomic/AtomicBoolean.get ()Z
      // 09: istore 1
      // 0a: iload 1
      // 0b: ifne 2d
      // 0e: aload 0
      // 0f: invokevirtual java/lang/Object.wait ()V
      // 12: goto 02
      // 15: astore 2
      // 16: aload 0
      // 17: getfield com/polidea/rxandroidble2/internal/serialization/QueueSemaphore.isReleased Ljava/util/concurrent/atomic/AtomicBoolean;
      // 1a: invokevirtual java/util/concurrent/atomic/AtomicBoolean.get ()Z
      // 1d: ifne 02
      // 20: aload 2
      // 21: ldc "Queue's awaitRelease() has been interrupted abruptly while it wasn't released by the release() method."
      // 23: bipush 0
      // 24: anewarray 4
      // 27: invokestatic com/polidea/rxandroidble2/internal/RxBleLog.w (Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V
      // 2a: goto 02
      // 2d: aload 0
      // 2e: monitorexit
      // 2f: return
      // 30: astore 2
      // 31: aload 0
      // 32: monitorexit
      // 33: aload 2
      // 34: athrow
   }

   @Override
   public void release() {
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
      // 03: getfield com/polidea/rxandroidble2/internal/serialization/QueueSemaphore.isReleased Ljava/util/concurrent/atomic/AtomicBoolean;
      // 06: bipush 0
      // 07: bipush 1
      // 08: invokevirtual java/util/concurrent/atomic/AtomicBoolean.compareAndSet (ZZ)Z
      // 0b: ifeq 12
      // 0e: aload 0
      // 0f: invokevirtual java/lang/Object.notify ()V
      // 12: aload 0
      // 13: monitorexit
      // 14: return
      // 15: astore 1
      // 16: aload 0
      // 17: monitorexit
      // 18: aload 1
      // 19: athrow
   }
}
