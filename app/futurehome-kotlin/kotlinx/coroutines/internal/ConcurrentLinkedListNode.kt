package kotlinx.coroutines.internal

import androidx.concurrent.futures.AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater
import kotlinx.atomicfu.AtomicRef
import kotlinx.coroutines.DebugKt

internal abstract class ConcurrentLinkedListNode<N extends ConcurrentLinkedListNode<N>> {
   private final val _next: AtomicRef<Any?>
   private final val _prev: AtomicRef<Any?>

   private final val aliveSegmentLeft: Any?
      private final get() {
         var var1: ConcurrentLinkedListNode = this.getPrev();

         while (var1 != null && var1.isRemoved()) {
            var1 = _prev$FU.get(var1) as ConcurrentLinkedListNode;
         }

         return (N)var1;
      }


   private final val aliveSegmentRight: Any
      private final get() {
         if (DebugKt.getASSERTIONS_ENABLED() && this.isTail()) {
            throw new AssertionError();
         } else {
            var var1: ConcurrentLinkedListNode = this.getNext();

            while (var1.isRemoved()) {
               val var2: ConcurrentLinkedListNode = var1.getNext();
               if (var2 == null) {
                  return (N)var1;
               }

               var1 = var2;
            }

            return (N)var1;
         }
      }


   public abstract val isRemoved: Boolean

   public final val isTail: Boolean
      public final get() {
         val var1: Boolean;
         if (this.getNext() == null) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }


   public final val next: Any?
      public final get() {
         val var1: Any = access$getNextOrClosed(this);
         return (N)(if (var1 === ConcurrentLinkedListKt.access$getCLOSED$p()) null else var1);
      }


   private final val nextOrClosed: Any?
      private final get() {
         return _next$FU.get(this);
      }


   public final val prev: Any?
      public final get() {
         return (N)_prev$FU.get(this);
      }


   open fun ConcurrentLinkedListNode(var1: N) {
      this._prev = var1;
   }

   fun `update$atomicfu`(var1: AtomicReferenceFieldUpdater, var2: (Any?) -> Any, var3: Any) {
      val var4: Any;
      do {
         var4 = var1.get(var3);
      } while (!ExternalSyntheticBackportWithForwarding0.m(var1, var3, var4, var2.invoke(var4)));
   }

   public fun cleanPrev() {
      _prev$FU.lazySet(this, null);
   }

   public fun markAsClosed(): Boolean {
      return ExternalSyntheticBackportWithForwarding0.m(_next$FU, this, null, ConcurrentLinkedListKt.access$getCLOSED$p());
   }

   public inline fun nextOrIfClosed(onClosedAction: () -> Nothing): Any? {
      val var2: Any = access$getNextOrClosed(this);
      if (var2 != ConcurrentLinkedListKt.access$getCLOSED$p()) {
         return (N)var2;
      } else {
         var1.invoke();
         throw new KotlinNothingValueException();
      }
   }

   public fun remove() {
      if (DebugKt.getASSERTIONS_ENABLED() && !this.isRemoved() && !this.isTail()) {
         throw new AssertionError();
      } else if (!this.isTail()) {
         val var2: ConcurrentLinkedListNode;
         val var5: ConcurrentLinkedListNode;
         do {
            var2 = this.getAliveSegmentLeft();
            var5 = this.getAliveSegmentRight();
            val var4: AtomicReferenceFieldUpdater = _prev$FU;

            val var1: ConcurrentLinkedListNode;
            val var3: Any;
            do {
               var3 = var4.get(var5);
               if (var3 as ConcurrentLinkedListNode == null) {
                  var1 = null;
               } else {
                  var1 = var2;
               }
            } while (!ExternalSyntheticBackportWithForwarding0.m(var4, var5, var3, var1));

            if (var2 != null) {
               _next$FU.set(var2, var5);
            }
         } while (var5.isRemoved() && !var5.isTail() || var2 != null && var2.isRemoved());
      }
   }

   public fun trySetNext(value: Any): Boolean {
      return ExternalSyntheticBackportWithForwarding0.m(_next$FU, this, null, var1);
   }
}
