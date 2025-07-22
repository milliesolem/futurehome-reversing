package kotlinx.coroutines

import kotlinx.coroutines.internal.LockFreeLinkedListHead
import kotlinx.coroutines.internal.LockFreeLinkedListNode

internal class NodeList : LockFreeLinkedListHead, Incomplete {
   public open val isActive: Boolean
      public open get() {
         return true;
      }


   public open val list: NodeList
      public open get() {
         return this;
      }


   public fun getString(state: String): String {
      val var4: StringBuilder = new StringBuilder("List{");
      var4.append(var1);
      var4.append("}[");
      val var5: LockFreeLinkedListHead = this;
      var var7: LockFreeLinkedListNode = (LockFreeLinkedListNode)this.getNext();
      var7 = var7;
      var var2: Boolean = true;

      while (!(var7 == var5)) {
         var var3: Boolean = var2;
         if (var7 is JobNode) {
            val var6: JobNode = var7 as JobNode;
            if (var2) {
               var2 = false;
            } else {
               var4.append(", ");
            }

            var4.append(var6);
            var3 = var2;
         }

         var7 = var7.getNextNode();
         var2 = var3;
      }

      var4.append("]");
      val var9: java.lang.String = var4.toString();
      return var9;
   }

   public override fun toString(): String {
      val var1: java.lang.String;
      if (DebugKt.getDEBUG()) {
         var1 = this.getString("Active");
      } else {
         var1 = super.toString();
      }

      return var1;
   }
}
