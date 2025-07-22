package kotlinx.coroutines.debug.internal

import java.lang.ref.ReferenceQueue
import java.lang.ref.WeakReference

internal class HashedWeakRef<T>(ref: Any, queue: ReferenceQueue<Any>?) : WeakReference((T)var1, var2) {
   public final val hash: Int

   init {
      val var3: Int;
      if (var1 != null) {
         var3 = var1.hashCode();
      } else {
         var3 = 0;
      }

      this.hash = var3;
   }
}
