package kotlin

import kotlin.contracts.InvocationKind

internal class StandardKt__SynchronizedKt : StandardKt__StandardKt {
   open fun StandardKt__SynchronizedKt() {
   }

   @JvmStatic
   public inline fun <R> synchronized(lock: Any, block: () -> R): R {
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
}
