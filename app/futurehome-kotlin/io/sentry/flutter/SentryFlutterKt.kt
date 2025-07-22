package io.sentry.flutter

import kotlin.jvm.functions.Function1

@JvmSynthetic
fun `access$getIfNotNull`(var0: java.util.Map, var1: java.lang.String, var2: Function1) {
   getIfNotNull(var0, var1, var2);
}

private fun <T> Map<String, Any>.getIfNotNull(key: String, callback: (T) -> Unit) {
   val var4: Any = var0.get(var1);
   var var3: Any = var4;
   if (var4 == null) {
      var3 = null;
   }

   if (var3 != null) {
      var2.invoke(var3);
   }
}
