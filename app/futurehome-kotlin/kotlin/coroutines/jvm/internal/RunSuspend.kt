package kotlin.coroutines.jvm.internal

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

private class RunSuspend : Continuation<Unit> {
   public open val context: CoroutineContext
      public open get() {
         return EmptyCoroutineContext.INSTANCE;
      }


   public final var result: Result<Unit>?
      internal set

   public fun await() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.insertSemaphore(FinallyProcessor.java:350)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:99)
      //
      // Bytecode:
      // 00: aload 0
      // 01: monitorenter
      // 02: aload 0
      // 03: getfield kotlin/coroutines/jvm/internal/RunSuspend.result Lkotlin/Result;
      // 06: astore 1
      // 07: aload 1
      // 08: ifnonnull 1b
      // 0b: aload 0
      // 0c: ldc "null cannot be cast to non-null type java.lang.Object"
      // 0e: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 11: aload 0
      // 12: checkcast java/lang/Object
      // 15: invokevirtual java/lang/Object.wait ()V
      // 18: goto 02
      // 1b: aload 1
      // 1c: invokevirtual kotlin/Result.unbox-impl ()Ljava/lang/Object;
      // 1f: invokestatic kotlin/ResultKt.throwOnFailure (Ljava/lang/Object;)V
      // 22: aload 0
      // 23: monitorexit
      // 24: return
      // 25: astore 1
      // 26: aload 0
      // 27: monitorexit
      // 28: aload 1
      // 29: athrow
   }

   public override fun resumeWith(result: Result<Unit>) {
      label13: {
         synchronized (this){} // $VF: monitorenter 

         try {
            this.result = Result.box-impl(var1);
            this.notifyAll();
         } catch (var2: java.lang.Throwable) {
            // $VF: monitorexit
         }

         // $VF: monitorexit
      }
   }
}
