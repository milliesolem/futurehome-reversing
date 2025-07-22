package kotlinx.coroutines.internal

// $VF: Class flags could not be determined
internal class SystemPropsKt {
   @JvmStatic
   fun getAVAILABLE_PROCESSORS(): Int {
      return SystemPropsKt__SystemPropsKt.getAVAILABLE_PROCESSORS();
   }

   @JvmStatic
   fun systemProp(var0: java.lang.String, var1: Int, var2: Int, var3: Int): Int {
      return SystemPropsKt__SystemProps_commonKt.systemProp(var0, var1, var2, var3);
   }

   @JvmStatic
   fun systemProp(var0: java.lang.String, var1: Long, var3: Long, var5: Long): Long {
      return SystemPropsKt__SystemProps_commonKt.systemProp(var0, var1, var3, var5);
   }

   @JvmStatic
   fun systemProp(var0: java.lang.String): java.lang.String {
      return SystemPropsKt__SystemPropsKt.systemProp(var0);
   }

   @JvmStatic
   fun systemProp(var0: java.lang.String, var1: java.lang.String): java.lang.String {
      return SystemPropsKt__SystemProps_commonKt.systemProp(var0, var1);
   }

   @JvmStatic
   fun systemProp(var0: java.lang.String, var1: Boolean): Boolean {
      return SystemPropsKt__SystemProps_commonKt.systemProp(var0, var1);
   }
}
