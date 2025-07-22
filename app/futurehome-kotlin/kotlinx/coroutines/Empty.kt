package kotlinx.coroutines

private class Empty(isActive: Boolean) : Incomplete {
   public open val isActive: Boolean

   public open val list: NodeList?
      public open get() {
         return null;
      }


   init {
      this.isActive = var1;
   }

   public override fun toString(): String {
      val var2: StringBuilder = new StringBuilder("Empty{");
      val var1: java.lang.String;
      if (this.isActive()) {
         var1 = "Active";
      } else {
         var1 = "New";
      }

      var2.append(var1);
      var2.append('}');
      return var2.toString();
   }
}
