package kotlinx.coroutines.internal

import androidx.concurrent.futures.AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater
import kotlin.jvm.functions.Function0
import kotlin.jvm.internal.PropertyReference0Impl
import kotlinx.atomicfu.AtomicRef
import kotlinx.coroutines.DebugKt
import kotlinx.coroutines.DebugStringsKt

public open class LockFreeLinkedListNode {
   private final val _next: AtomicRef<Any>
   private final val _prev: AtomicRef<LockFreeLinkedListNode>
   private final val _removedRef: AtomicRef<Removed?>

   public open val isRemoved: Boolean
      public open get() {
         return this.getNext() is Removed;
      }


   public final val next: Any
      public final get() {
         val var1: AtomicReferenceFieldUpdater = _next$FU;

         while (true) {
            val var2: Any = var1.get(this);
            if (var2 !is OpDescriptor) {
               return var2;
            }

            (var2 as OpDescriptor).perform(this);
         }
      }


   public final val nextNode: LockFreeLinkedListNode
      public final get() {
         return LockFreeLinkedListKt.unwrap(this.getNext());
      }


   public final val prevNode: LockFreeLinkedListNode
      public final get() {
         val var2: LockFreeLinkedListNode = this.correctPrev(null);
         var var1: LockFreeLinkedListNode = var2;
         if (var2 == null) {
            var1 = this.findPrevNonRemoved(_prev$FU.get(this) as LockFreeLinkedListNode);
         }

         return var1;
      }


   private tailrec fun correctPrev(op: OpDescriptor?): LockFreeLinkedListNode? {
      label49:
      while (true) {
         val var4: LockFreeLinkedListNode = _prev$FU.get(this) as LockFreeLinkedListNode;
         var var2: LockFreeLinkedListNode = var4;

         while (true) {
            var var3: LockFreeLinkedListNode = null;

            while (true) {
               val var5: AtomicReferenceFieldUpdater = _next$FU;
               val var6: Any = _next$FU.get(var2);
               if (var6 === this) {
                  if (var4 === var2) {
                     return var2;
                  }

                  if (!ExternalSyntheticBackportWithForwarding0.m(_prev$FU, this, var4, var2)) {
                     continue label49;
                  }

                  return var2;
               }

               if (this.isRemoved()) {
                  return null;
               }

               if (var6 === var1) {
                  return var2;
               }

               if (var6 is OpDescriptor) {
                  (var6 as OpDescriptor).perform(var2);
                  continue label49;
               }

               if (var6 is Removed) {
                  if (var3 != null) {
                     if (!ExternalSyntheticBackportWithForwarding0.m(var5, var3, var2, (var6 as Removed).ref)) {
                        continue label49;
                     }

                     var2 = var3;
                     break;
                  }

                  var2 = _prev$FU.get(var2) as LockFreeLinkedListNode;
               } else {
                  val var7: LockFreeLinkedListNode = var6 as LockFreeLinkedListNode;
                  var3 = var2;
                  var2 = var7;
               }
            }
         }
      }
   }

   private tailrec fun findPrevNonRemoved(current: LockFreeLinkedListNode): LockFreeLinkedListNode {
      while (var1.isRemoved()) {
         var1 = _prev$FU.get(var1) as LockFreeLinkedListNode;
      }

      return var1;
   }

   private fun finishAdd(next: LockFreeLinkedListNode) {
      val var2: AtomicReferenceFieldUpdater = _prev$FU;

      val var3: LockFreeLinkedListNode;
      do {
         var3 = var2.get(var1) as LockFreeLinkedListNode;
         if (this.getNext() != var1) {
            return;
         }
      } while (!ExternalSyntheticBackportWithForwarding0.m(_prev$FU, var1, var3, this));

      if (this.isRemoved()) {
         var1.correctPrev(null);
      }
   }

   fun `loop$atomicfu`(var1: AtomicReferenceFieldUpdater, var2: (Any?) -> Unit, var3: Any) {
      while (true) {
         var2.invoke(var1.get(var3));
      }
   }

   private fun removed(): Removed {
      val var3: AtomicReferenceFieldUpdater = _removedRef$FU;
      val var2: Removed = _removedRef$FU.get(this) as Removed;
      var var1: Removed = var2;
      if (var2 == null) {
         var1 = new Removed(this);
         var3.lazySet(this, var1);
      }

      return var1;
   }

   public fun addLast(node: LockFreeLinkedListNode) {
      while (!this.getPrevNode().addNext(var1, this)) {
      }
   }

   public inline fun addLastIf(node: LockFreeLinkedListNode, crossinline condition: () -> Boolean): Boolean {
      val var4: LockFreeLinkedListNode.CondAddOp = new LockFreeLinkedListNode.CondAddOp(var1, var2) {
         final Function0<java.lang.Boolean> $condition;

         {
            super(var1);
            this.$condition = var2;
         }

         public Object prepare(LockFreeLinkedListNode var1) {
            val var2: Any;
            if (this.$condition.invoke()) {
               var2 = null;
            } else {
               var2 = LockFreeLinkedListKt.getCONDITION_FALSE();
            }

            return var2;
         }
      };

      val var3: Int;
      do {
         var3 = this.getPrevNode().tryCondAddNext(var1, this, var4);
         if (var3 == 1) {
            return true;
         }
      } while (var3 != 2);

      return false;
   }

   internal fun addNext(node: LockFreeLinkedListNode, next: LockFreeLinkedListNode): Boolean {
      _prev$FU.lazySet(var1, this);
      val var3: AtomicReferenceFieldUpdater = _next$FU;
      _next$FU.lazySet(var1, var2);
      if (!ExternalSyntheticBackportWithForwarding0.m(var3, this, var2, var1)) {
         return false;
      } else {
         var1.finishAdd(var2);
         return true;
      }
   }

   public fun addOneIfEmpty(node: LockFreeLinkedListNode): Boolean {
      _prev$FU.lazySet(var1, this);
      _next$FU.lazySet(var1, this);

      while (this.getNext() == this) {
         if (ExternalSyntheticBackportWithForwarding0.m(_next$FU, this, this, var1)) {
            var1.finishAdd(this);
            return true;
         }
      }

      return false;
   }

   internal inline fun makeCondAddOp(node: LockFreeLinkedListNode, crossinline condition: () -> Boolean): kotlinx.coroutines.internal.LockFreeLinkedListNode.CondAddOp {
      return new LockFreeLinkedListNode.CondAddOp(var1, var2) {
         final Function0<java.lang.Boolean> $condition;

         {
            super(var1);
            this.$condition = var2;
         }

         public Object prepare(LockFreeLinkedListNode var1) {
            val var2: Any;
            if (this.$condition.invoke()) {
               var2 = null;
            } else {
               var2 = LockFreeLinkedListKt.getCONDITION_FALSE();
            }

            return var2;
         }
      };
   }

   protected open fun nextIfRemoved(): LockFreeLinkedListNode? {
      var var2: Removed = (Removed)this.getNext();
      val var1: Boolean = var2 is Removed;
      var var3: LockFreeLinkedListNode = null;
      if (var1) {
         var2 = var2;
      } else {
         var2 = null;
      }

      if (var2 != null) {
         var3 = var2.ref;
      }

      return var3;
   }

   public open fun remove(): Boolean {
      val var1: Boolean;
      if (this.removeOrNext() == null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   internal fun removeOrNext(): LockFreeLinkedListNode? {
      val var2: Any;
      do {
         var2 = this.getNext();
         if (var2 is Removed) {
            return (var2 as Removed).ref;
         }

         if (var2 === this) {
            return var2 as LockFreeLinkedListNode;
         }
      } while (!ExternalSyntheticBackportWithForwarding0.m(_next$FU, this, var2, ((LockFreeLinkedListNode)var2).removed()));

      (var2 as LockFreeLinkedListNode).correctPrev(null);
      return null;
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder();
      var1.append(new PropertyReference0Impl(this) {
         {
            super(var1, DebugStringsKt::class.java, "classSimpleName", "getClassSimpleName(Ljava/lang/Object;)Ljava/lang/String;", 1);
         }

         @Override
         public Object get() {
            return DebugStringsKt.getClassSimpleName(this.receiver);
         }
      });
      var1.append('@');
      var1.append(DebugStringsKt.getHexAddress(this));
      return var1.toString();
   }

   internal fun tryCondAddNext(
      node: LockFreeLinkedListNode,
      next: LockFreeLinkedListNode,
      condAdd: kotlinx.coroutines.internal.LockFreeLinkedListNode.CondAddOp
   ): Int {
      _prev$FU.lazySet(var1, this);
      val var5: AtomicReferenceFieldUpdater = _next$FU;
      _next$FU.lazySet(var1, var2);
      var3.oldNext = var2;
      if (!ExternalSyntheticBackportWithForwarding0.m(var5, this, var2, var3)) {
         return 0;
      } else {
         val var4: Byte;
         if (var3.perform(this) == null) {
            var4 = 1;
         } else {
            var4 = 2;
         }

         return var4;
      }
   }

   internal fun validateNode(prev: LockFreeLinkedListNode, next: LockFreeLinkedListNode) {
      if (DebugKt.getASSERTIONS_ENABLED() && var1 != _prev$FU.get(this)) {
         throw new AssertionError();
      } else if (DebugKt.getASSERTIONS_ENABLED() && var2 != _next$FU.get(this)) {
         throw new AssertionError();
      }
   }

   internal abstract class CondAddOp : AtomicOp<LockFreeLinkedListNode> {
      public final val newNode: LockFreeLinkedListNode

      public final var oldNext: LockFreeLinkedListNode?
         private set

      open fun CondAddOp(var1: LockFreeLinkedListNode) {
         this.newNode = var1;
      }

      public open fun complete(affected: LockFreeLinkedListNode, failure: Any?) {
         val var3: Boolean;
         if (var2 == null) {
            var3 = true;
         } else {
            var3 = false;
         }

         if (var3) {
            var2 = this.newNode;
         } else {
            var2 = this.oldNext;
         }

         if (var2 != null && ExternalSyntheticBackportWithForwarding0.m(LockFreeLinkedListNode.access$get_next$FU$p(), var1, this, var2) && var3) {
            var1 = this.newNode;
            var2 = this.oldNext;
            LockFreeLinkedListNode.access$finishAdd(var1, var2);
         }
      }
   }
}
