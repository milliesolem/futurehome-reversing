package kotlinx.coroutines.sync

import com.google.common.util.concurrent.Striped.SmallLazyStriped..ExternalSyntheticBackportWithForwarding0
import java.util.concurrent.atomic.AtomicReferenceArray
import kotlin.coroutines.CoroutineContext
import kotlinx.atomicfu.AtomicArray
import kotlinx.coroutines.internal.Segment

private class SemaphoreSegment(id: Long, prev: SemaphoreSegment?, pointers: Int) : Segment(var1, var3, var4) {
   public final val acquirers: AtomicArray<Any?>

   public open val numberOfSlots: Int
      public open get() {
         return SemaphoreKt.access$getSEGMENT_SIZE$p();
      }


   public inline fun cas(index: Int, expected: Any?, value: Any?): Boolean {
      return ExternalSyntheticBackportWithForwarding0.m(this.getAcquirers(), var1, var2, var3);
   }

   public inline fun get(index: Int): Any? {
      return this.getAcquirers().get(var1);
   }

   fun getAcquirers(): AtomicReferenceArray {
      return this.acquirers;
   }

   public inline fun getAndSet(index: Int, value: Any?): Any? {
      return this.getAcquirers().getAndSet(var1, var2);
   }

   public override fun onCancellation(index: Int, cause: Throwable?, context: CoroutineContext) {
      this.getAcquirers().set(var1, SemaphoreKt.access$getCANCELLED$p());
      this.onSlotCleaned();
   }

   public inline fun set(index: Int, value: Any?) {
      this.getAcquirers().set(var1, var2);
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder("SemaphoreSegment[id=");
      var1.append(this.id);
      var1.append(", hashCode=");
      var1.append(this.hashCode());
      var1.append(']');
      return var1.toString();
   }
}
