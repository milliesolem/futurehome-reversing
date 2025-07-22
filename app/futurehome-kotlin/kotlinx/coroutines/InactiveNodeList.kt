package kotlinx.coroutines

internal class InactiveNodeList(list: NodeList) : Incomplete {
   public open val isActive: Boolean
      public open get() {
         return false;
      }


   public open val list: NodeList

   init {
      this.list = var1;
   }

   public override fun toString(): String {
      val var1: java.lang.String;
      if (DebugKt.getDEBUG()) {
         var1 = this.getList().getString("New");
      } else {
         var1 = super.toString();
      }

      return var1;
   }
}
