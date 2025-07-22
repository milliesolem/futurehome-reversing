package kotlinx.coroutines.internal

import java.lang.reflect.Method
import java.util.Collections
import java.util.IdentityHashMap
import java.util.concurrent.Executor
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

private final val REMOVE_FUTURE_ON_CANCEL: Method?

@JvmSynthetic
fun `ReentrantLock$annotations`() {
}

internal inline fun <E> identitySet(expectedSize: Int): MutableSet<E> {
   return Collections.newSetFromMap(new IdentityHashMap(var0));
}

internal fun removeFutureOnCancel(executor: Executor): Boolean {
   // $VF: Couldn't be decompiled
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   // java.lang.IndexOutOfBoundsException: Index 2 out of bounds for length 0
   //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
   //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
   //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
   //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
   //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
   //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1051)
   //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:501)
   //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
   //
   // Bytecode:
   // 00: aload 0
   // 01: instanceof java/util/concurrent/ScheduledThreadPoolExecutor
   // 04: ifeq 0f
   // 07: aload 0
   // 08: checkcast java/util/concurrent/ScheduledThreadPoolExecutor
   // 0b: astore 0
   // 0c: goto 11
   // 0f: aconst_null
   // 10: astore 0
   // 11: aload 0
   // 12: ifnonnull 17
   // 15: bipush 0
   // 16: ireturn
   // 17: getstatic kotlinx/coroutines/internal/ConcurrentKt.REMOVE_FUTURE_ON_CANCEL Ljava/lang/reflect/Method;
   // 1a: astore 1
   // 1b: aload 1
   // 1c: ifnonnull 21
   // 1f: bipush 0
   // 20: ireturn
   // 21: aload 1
   // 22: aload 0
   // 23: bipush 1
   // 24: anewarray 4
   // 27: dup
   // 28: bipush 0
   // 29: bipush 1
   // 2a: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
   // 2d: aastore
   // 2e: invokevirtual java/lang/reflect/Method.invoke (Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
   // 31: pop
   // 32: bipush 1
   // 33: ireturn
   // 34: astore 0
   // 35: bipush 0
   // 36: ireturn
}

internal inline fun <T> ReentrantLock.withLock(action: () -> T): T {
   label13: {
      val var4: Lock = var0;
      var0.lock();

      try {
         val var5: Any = var1.invoke();
      } catch (var2: java.lang.Throwable) {
         var4.unlock();
      }

      var4.unlock();
   }
}
