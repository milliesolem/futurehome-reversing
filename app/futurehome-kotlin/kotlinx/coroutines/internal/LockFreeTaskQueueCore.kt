package kotlinx.coroutines.internal

import androidx.concurrent.futures.AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0
import java.util.ArrayList
import java.util.concurrent.atomic.AtomicLongFieldUpdater
import java.util.concurrent.atomic.AtomicReferenceArray
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater
import kotlinx.atomicfu.AtomicArray
import kotlinx.atomicfu.AtomicLong
import kotlinx.atomicfu.AtomicRef
import kotlinx.coroutines.DebugKt

internal class LockFreeTaskQueueCore<E>(capacity: Int, singleConsumer: Boolean) {
   private final val _next: AtomicRef<LockFreeTaskQueueCore<Any>?>
   private final val _state: AtomicLong
   private final val array: AtomicArray<Any?>
   private final val capacity: Int

   public final val isEmpty: Boolean
      public final get() {
         val var2: Long = _state$FU.get(this);
         val var1: Boolean;
         if ((int)(1073741823L and var2) == (int)((var2 and 1152921503533105152L) shr 30)) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }


   private final val mask: Int
   private final val singleConsumer: Boolean

   public final val size: Int
      public final get() {
         val var2: Long = _state$FU.get(this);
         return 1073741823 and (int)((var2 and 1152921503533105152L) shr 30) - (int)(1073741823L and var2);
      }


   init {
      this.capacity = var1;
      this.singleConsumer = var2;
      val var3: Int = var1 - 1;
      this.mask = var1 - 1;
      this.array = new AtomicReferenceArray(var1);
      if (var3 <= 1073741823) {
         if ((var1 and var3) != 0) {
            throw new IllegalStateException("Check failed.".toString());
         }
      } else {
         throw new IllegalStateException("Check failed.".toString());
      }
   }

   private fun allocateNextCopy(state: Long): LockFreeTaskQueueCore<Any> {
      val var8: LockFreeTaskQueueCore = new LockFreeTaskQueueCore(this.capacity * 2, this.singleConsumer);
      var var3: Int = (int)(1073741823L and var1);
      val var4: Int = (int)((1152921503533105152L and var1) shr 30);

      while (true) {
         if ((var3 and this.mask) == (var4 and this.mask)) {
            _state$FU.set(var8, Companion.wo(var1, 1152921504606846976L));
            return var8;
         }

         val var7: Any = this.array.get(this.mask and var3);
         var var6: Any = var7;
         if (var7 == null) {
            var6 = new LockFreeTaskQueueCore.Placeholder(var3);
         }

         var8.array.set(var8.mask and var3, var6);
         var3++;
      }
   }

   private fun allocateOrGetNextCopy(state: Long): LockFreeTaskQueueCore<Any> {
      val var4: AtomicReferenceFieldUpdater = _next$FU;

      while (true) {
         val var3: LockFreeTaskQueueCore = var4.get(this) as LockFreeTaskQueueCore;
         if (var3 != null) {
            return var3;
         }

         ExternalSyntheticBackportWithForwarding0.m(_next$FU, this, null, this.allocateNextCopy(var1));
      }
   }

   private fun fillPlaceholder(index: Int, element: Any): LockFreeTaskQueueCore<Any>? {
      val var3: Any = this.array.get(this.mask and var1);
      if (var3 is LockFreeTaskQueueCore.Placeholder && (var3 as LockFreeTaskQueueCore.Placeholder).index == var1) {
         this.array.set(var1 and this.mask, var2);
         return this;
      } else {
         return null;
      }
   }

   fun `loop$atomicfu`(var1: AtomicLongFieldUpdater, var2: (java.lang.Long?) -> Unit, var3: Any) {
      while (true) {
         var2.invoke(var1.get(var3));
      }
   }

   fun `loop$atomicfu`(var1: AtomicReferenceFieldUpdater, var2: (Any?) -> Unit, var3: Any) {
      while (true) {
         var2.invoke(var1.get(var3));
      }
   }

   private fun markFrozen(): Long {
      val var5: AtomicLongFieldUpdater = _state$FU;

      val var1: Long;
      do {
         var1 = var5.get(this);
         if ((var1 and 1152921504606846976L) != 0L) {
            return var1;
         }
      } while (!var5.compareAndSet(this, var1, var1 | 1152921504606846976L));

      return var1 or 1152921504606846976L;
   }

   private fun removeSlowPath(oldHead: Int, newHead: Int): LockFreeTaskQueueCore<Any>? {
      val var6: AtomicLongFieldUpdater = _state$FU;

      val var3: Int;
      val var4: Long;
      val var7: LockFreeTaskQueueCore.Companion;
      do {
         var4 = var6.get(this);
         var7 = Companion;
         var3 = (int)(1073741823L and var4);
         if (DebugKt.getASSERTIONS_ENABLED() && (int)(1073741823L and var4) != var1) {
            throw new AssertionError();
         }

         if ((1152921504606846976L and var4) != 0L) {
            return this.next();
         }
      } while (!_state$FU.compareAndSet(this, var4, var7.updateHead(var4, var2)));

      this.array.set(this.mask and var3, null);
      return null;
   }

   fun `update$atomicfu`(var1: AtomicLongFieldUpdater, var2: (java.lang.Long?) -> java.lang.Long, var3: Any) {
      val var4: Long;
      do {
         var4 = var1.get(var3);
      } while (!var1.compareAndSet(var3, var4, ((java.lang.Number)var2.invoke(var4)).longValue()));
   }

   fun `updateAndGet$atomicfu`(var1: AtomicLongFieldUpdater, var2: (java.lang.Long?) -> java.lang.Long, var3: Any): Long {
      val var4: Long;
      val var6: java.lang.Number;
      do {
         var4 = var1.get(var3);
         var6 = var2.invoke(var4) as java.lang.Number;
      } while (!var1.compareAndSet(var3, var4, var6.longValue()));

      return var6.longValue();
   }

   public fun addLast(element: Any): Int {
      val var8: AtomicLongFieldUpdater = _state$FU;

      while (true) {
         val var5: Long = var8.get(this);
         if ((3458764513820540928L and var5) != 0L) {
            return Companion.addFailReason(var5);
         }

         val var2: Int = (int)(1073741823L and var5);
         val var3: Int = (int)((1152921503533105152L and var5) shr 30);
         val var4: Int = this.mask;
         if ((var3 + 2 and this.mask) == (var2 and this.mask)) {
            return 1;
         }

         if (!this.singleConsumer && this.array.get(var3 and this.mask) != null) {
            if (this.capacity < 1024 || (var3 - var2 and 1073741823) > this.capacity shr 1) {
               return 1;
            }
         } else if (_state$FU.compareAndSet(this, var5, Companion.updateTail(var5, var3 + 1 and 1073741823))) {
            this.array.set(var3 and var4, var1);
            var var10: LockFreeTaskQueueCore = this;

            while ((_state$FU.get(var10) & 1152921504606846976L) != 0L) {
               val var11: LockFreeTaskQueueCore = var10.next().fillPlaceholder(var3, var1);
               var10 = var11;
               if (var11 == null) {
                  break;
               }
            }

            return 0;
         }
      }
   }

   public fun close(): Boolean {
      val var3: AtomicLongFieldUpdater = _state$FU;

      val var1: Long;
      do {
         var1 = var3.get(this);
         if ((var1 and 2305843009213693952L) != 0L) {
            return true;
         }

         if ((1152921504606846976L and var1) != 0L) {
            return false;
         }
      } while (!var3.compareAndSet(this, var1, var1 | 2305843009213693952L));

      return true;
   }

   public fun isClosed(): Boolean {
      val var1: Boolean;
      if ((_state$FU.get(this) and 2305843009213693952L) != 0L) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public fun <R> map(transform: (Any) -> R): List<R> {
      val var8: ArrayList = new ArrayList(this.capacity);
      val var5: Long = _state$FU.get(this);
      var var2: Int = (int)(1073741823L and var5);
      val var3: Int = (int)((var5 and 1152921503533105152L) shr 30);

      while (true) {
         if ((var2 and this.mask) == (var3 and this.mask)) {
            return var8;
         }

         val var7: Any = this.array.get(this.mask and var2);
         if (var7 != null && var7 !is LockFreeTaskQueueCore.Placeholder) {
            var8.add(var1.invoke(var7));
         }

         var2++;
      }
   }

   public fun next(): LockFreeTaskQueueCore<Any> {
      return this.allocateOrGetNextCopy(this.markFrozen());
   }

   public fun removeFirstOrNull(): Any? {
      val var7: AtomicLongFieldUpdater = _state$FU;

      while (true) {
         val var4: Long = var7.get(this);
         if ((1152921504606846976L and var4) != 0L) {
            return REMOVE_FROZEN;
         }

         val var6: LockFreeTaskQueueCore.Companion = Companion;
         val var1: Int = (int)(1073741823L and var4);
         if (((int)((1152921503533105152L and var4) shr 30) and this.mask) == ((int)(1073741823L and var4) and this.mask)) {
            return null;
         }

         val var8: Any = this.array.get(this.mask and var1);
         if (var8 == null) {
            if (this.singleConsumer) {
               return null;
            }
         } else {
            if (var8 is LockFreeTaskQueueCore.Placeholder) {
               return null;
            }

            val var9: Int = var1 + 1 and 1073741823;
            if (_state$FU.compareAndSet(this, var4, var6.updateHead(var4, var1 + 1 and 1073741823))) {
               this.array.set(this.mask and var1, null);
               return var8;
            }

            if (this.singleConsumer) {
               var var10: LockFreeTaskQueueCore = this;

               do {
                  var11 = var10.removeSlowPath(var1, var9);
                  var10 = var11;
               } while (var11 != null);

               return var8;
            }
         }
      }
   }

   internal companion object {
      public const val ADD_CLOSED: Int
      public const val ADD_FROZEN: Int
      public const val ADD_SUCCESS: Int
      public const val CAPACITY_BITS: Int
      public const val CLOSED_MASK: Long
      public const val CLOSED_SHIFT: Int
      public const val FROZEN_MASK: Long
      public const val FROZEN_SHIFT: Int
      public const val HEAD_MASK: Long
      public const val HEAD_SHIFT: Int
      public const val INITIAL_CAPACITY: Int
      public const val MAX_CAPACITY_MASK: Int
      public const val MIN_ADD_SPIN_CAPACITY: Int
      public final val REMOVE_FROZEN: Symbol
      public const val TAIL_MASK: Long
      public const val TAIL_SHIFT: Int

      public fun Long.addFailReason(): Int {
         val var3: Byte;
         if ((var1 and 2305843009213693952L) != 0L) {
            var3 = 2;
         } else {
            var3 = 1;
         }

         return var3;
      }

      public fun Long.updateHead(newHead: Int): Long {
         return this.wo(var1, 1073741823L) or var3;
      }

      public fun Long.updateTail(newTail: Int): Long {
         return this.wo(var1, 1152921503533105152L) or (long)var3 shl 30;
      }

      public inline fun <T> Long.withState(block: (Int, Int) -> T): T {
         return (T)var3.invoke((int)(1073741823L and var1), (int)((var1 and 1152921503533105152L) shr 30));
      }

      public infix fun Long.wo(other: Long): Long {
         return var1 and var3.inv();
      }
   }

   internal class Placeholder(index: Int) {
      public final val index: Int

      init {
         this.index = var1;
      }
   }
}
