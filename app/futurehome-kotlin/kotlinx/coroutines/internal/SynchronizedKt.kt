package kotlinx.coroutines.internal

@JvmSynthetic
fun `SynchronizedObject$annotations`() {
}

public inline fun <T> synchronizedImpl(lock: Any, block: () -> T): T {
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
