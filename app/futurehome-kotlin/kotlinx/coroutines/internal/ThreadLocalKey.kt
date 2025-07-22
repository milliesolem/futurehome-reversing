package kotlinx.coroutines.internal

import kotlin.coroutines.CoroutineContext

internal data class ThreadLocalKey(threadLocal: ThreadLocal<*>) : CoroutineContext.Key<ThreadLocalElement<?>> {
   private final val threadLocal: ThreadLocal<*>

   init {
      this.threadLocal = var1;
   }

   private operator fun component1(): ThreadLocal<*> {
      return this.threadLocal;
   }

   public fun copy(threadLocal: ThreadLocal<*> = var0.threadLocal): ThreadLocalKey {
      return new ThreadLocalKey(var1);
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === var1) {
         return true;
      } else if (var1 !is ThreadLocalKey) {
         return false;
      } else {
         return this.threadLocal == (var1 as ThreadLocalKey).threadLocal;
      }
   }

   public override fun hashCode(): Int {
      return this.threadLocal.hashCode();
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder("ThreadLocalKey(threadLocal=");
      var1.append(this.threadLocal);
      var1.append(')');
      return var1.toString();
   }
}
