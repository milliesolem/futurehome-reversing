package kotlin.system

import kotlin.contracts.InvocationKind

public inline fun measureNanoTime(block: () -> Unit): Long {
   contract {
      callsInPlace(block, InvocationKind.EXACTLY_ONCE)
   }

   val var1: Long = System.nanoTime();
   var0.invoke();
   return System.nanoTime() - var1;
}

public inline fun measureTimeMillis(block: () -> Unit): Long {
   contract {
      callsInPlace(block, InvocationKind.EXACTLY_ONCE)
   }

   val var1: Long = System.currentTimeMillis();
   var0.invoke();
   return System.currentTimeMillis() - var1;
}
