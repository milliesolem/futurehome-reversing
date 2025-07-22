package kotlinx.coroutines.internal

@JvmSynthetic
internal class SystemPropsKt__SystemPropsKt {
   internal final val AVAILABLE_PROCESSORS: Int = Runtime.getRuntime().availableProcessors()

   @JvmStatic
   internal fun systemProp(propertyName: String): String? {
      try {
         var0 = System.getProperty(var0);
      } catch (var1: SecurityException) {
         var0 = null;
      }

      return var0;
   }
}
