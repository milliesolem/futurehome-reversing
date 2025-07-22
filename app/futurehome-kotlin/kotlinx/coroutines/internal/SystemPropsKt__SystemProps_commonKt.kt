package kotlinx.coroutines.internal

@JvmSynthetic
internal class SystemPropsKt__SystemProps_commonKt {
   @JvmStatic
   internal fun systemProp(propertyName: String, defaultValue: Int, minValue: Int = 1, maxValue: Int = Integer.MAX_VALUE): Int {
      return (int)SystemPropsKt.systemProp(var0, (long)var1, (long)var2, (long)var3);
   }

   @JvmStatic
   internal fun systemProp(propertyName: String, defaultValue: Long, minValue: Long = 1L, maxValue: Long = java.lang.Long.MAX_VALUE): Long {
      val var7: java.lang.String = SystemPropsKt.systemProp(var0);
      if (var7 == null) {
         return var1;
      } else {
         val var8: java.lang.Long = StringsKt.toLongOrNull(var7);
         if (var8 != null) {
            var1 = var8;
            if (var3 <= var1 && var1 <= var5) {
               return var1;
            } else {
               val var10: StringBuilder = new StringBuilder("System property '");
               var10.append(var0);
               var10.append("' should be in range ");
               var10.append(var3);
               var10.append("..");
               var10.append(var5);
               var10.append(", but is '");
               var10.append(var1);
               var10.append('\'');
               throw new IllegalStateException(var10.toString().toString());
            }
         } else {
            val var11: StringBuilder = new StringBuilder("System property '");
            var11.append(var0);
            var11.append("' has unrecognized value '");
            var11.append(var7);
            var11.append('\'');
            throw new IllegalStateException(var11.toString().toString());
         }
      }
   }

   @JvmStatic
   internal fun systemProp(propertyName: String, defaultValue: String): String {
      var0 = SystemPropsKt.systemProp(var0);
      if (var0 == null) {
         var0 = var1;
      }

      return var0;
   }

   @JvmStatic
   internal fun systemProp(propertyName: String, defaultValue: Boolean): Boolean {
      var0 = SystemPropsKt.systemProp(var0);
      if (var0 != null) {
         var1 = java.lang.Boolean.parseBoolean(var0);
      }

      return var1;
   }
}
