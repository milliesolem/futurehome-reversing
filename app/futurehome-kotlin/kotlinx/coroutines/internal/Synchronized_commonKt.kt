package kotlinx.coroutines.internal

import kotlin.contracts.InvocationKind

public inline fun <T> synchronized(lock: Any, block: () -> T): T {
   contract {
      callsInPlace(block, InvocationKind.EXACTLY_ONCE)
   }

   label13: {
      synchronized (var0){} // $VF: monitorenter 

      try {
         val var4: Any = var1.invoke();
      } catch (var2: java.lang.Throwable) {
         // $VF: monitorexit
      }

      // $VF: monitorexit
   }
}
