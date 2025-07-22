package kotlinx.coroutines.internal

import androidx.concurrent.futures.AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater
import kotlinx.atomicfu.AtomicRef

internal open class LockFreeTaskQueue<E>(singleConsumer: Boolean) {
   private final val _cur: AtomicRef<LockFreeTaskQueueCore<Any>>

   public final val isEmpty: Boolean
      public final get() {
         return (_cur$FU.get(this) as LockFreeTaskQueueCore).isEmpty();
      }


   public final val size: Int
      public final get() {
         return (_cur$FU.get(this) as LockFreeTaskQueueCore).getSize();
      }


   init {
      this._cur = new LockFreeTaskQueueCore(8, var1);
   }

   fun `loop$atomicfu`(var1: AtomicReferenceFieldUpdater, var2: (Any?) -> Unit, var3: Any) {
      while (true) {
         var2.invoke(var1.get(var3));
      }
   }

   public fun addLast(element: Any): Boolean {
      val var4: AtomicReferenceFieldUpdater = _cur$FU;

      while (true) {
         val var3: LockFreeTaskQueueCore = var4.get(this) as LockFreeTaskQueueCore;
         val var2: Int = var3.addLast(var1);
         if (var2 == 0) {
            return true;
         }

         if (var2 != 1) {
            if (var2 == 2) {
               return false;
            }
         } else {
            ExternalSyntheticBackportWithForwarding0.m(_cur$FU, this, var3, var3.next());
         }
      }
   }

   public fun close() {
      val var2: AtomicReferenceFieldUpdater = _cur$FU;

      while (true) {
         val var1: LockFreeTaskQueueCore = var2.get(this) as LockFreeTaskQueueCore;
         if (var1.close()) {
            return;
         }

         ExternalSyntheticBackportWithForwarding0.m(_cur$FU, this, var1, var1.next());
      }
   }

   public fun isClosed(): Boolean {
      return (_cur$FU.get(this) as LockFreeTaskQueueCore).isClosed();
   }

   public fun <R> map(transform: (Any) -> R): List<R> {
      return (_cur$FU.get(this) as LockFreeTaskQueueCore).map(var1);
   }

   public fun removeFirstOrNull(): Any? {
      val var3: AtomicReferenceFieldUpdater = _cur$FU;

      while (true) {
         val var1: LockFreeTaskQueueCore = var3.get(this) as LockFreeTaskQueueCore;
         val var2: Any = var1.removeFirstOrNull();
         if (var2 != LockFreeTaskQueueCore.REMOVE_FROZEN) {
            return (E)var2;
         }

         ExternalSyntheticBackportWithForwarding0.m(_cur$FU, this, var1, var1.next());
      }
   }
}
