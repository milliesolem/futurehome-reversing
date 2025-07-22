package kotlinx.coroutines.internal

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater
import kotlin.coroutines.CoroutineContext
import kotlinx.atomicfu.AtomicInt
import kotlinx.coroutines.NotCompleted

internal abstract class Segment<S extends Segment<S>> : ConcurrentLinkedListNode<S>, NotCompleted {
   private final val cleanedAndPointers: AtomicInt
   public final val id: Long

   public open val isRemoved: Boolean
      public open get() {
         val var1: Boolean;
         if (cleanedAndPointers$FU.get(this) == this.getNumberOfSlots() && !this.isTail()) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }


   public abstract val numberOfSlots: Int

   open fun Segment(var1: Long, var3: S, var4: Int) {
      super((S)var3);
      this.id = var1;
      this.cleanedAndPointers = var4 shl 16;
   }

   internal fun decPointers(): Boolean {
      val var1: Boolean;
      if (cleanedAndPointers$FU.addAndGet(this, -65536) == this.getNumberOfSlots() && !this.isTail()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public abstract fun onCancellation(index: Int, cause: Throwable?, context: CoroutineContext) {
   }

   public fun onSlotCleaned() {
      if (cleanedAndPointers$FU.incrementAndGet(this) == this.getNumberOfSlots()) {
         this.remove();
      }
   }

   internal fun tryIncPointers(): Boolean {
      val var3: AtomicIntegerFieldUpdater = cleanedAndPointers$FU;

      var var2: Boolean;
      while (true) {
         val var1: Int = var3.get(this);
         if (var1 == this.getNumberOfSlots() && !this.isTail()) {
            var2 = false;
            break;
         }

         if (var3.compareAndSet(this, var1, 65536 + var1)) {
            var2 = true;
            break;
         }
      }

      return var2;
   }
}
