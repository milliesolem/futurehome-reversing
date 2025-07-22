package kotlinx.coroutines.internal

public open class LockFreeLinkedListHead : LockFreeLinkedListNode {
   public final val isEmpty: Boolean
      public final get() {
         val var1: Boolean;
         if (this.getNext() === this) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }


   public open val isRemoved: Boolean
      public open get() {
         return false;
      }


   protected override fun nextIfRemoved(): LockFreeLinkedListNode? {
      return null;
   }

   public fun remove(): Nothing {
      throw new IllegalStateException("head cannot be removed".toString());
   }

   internal fun validate() {
      var var2: LockFreeLinkedListNode = this;
      var var1: LockFreeLinkedListNode = (LockFreeLinkedListNode)this.getNext();
      var1 = var1;

      while (!(var1 == this)) {
         val var3: LockFreeLinkedListNode = var1.getNextNode();
         var1.validateNode$kotlinx_coroutines_core(var2, var3);
         var2 = var1;
         var1 = var3;
      }

      var1 = (LockFreeLinkedListNode)this.getNext();
      this.validateNode$kotlinx_coroutines_core(var2, var1);
   }
}
