package kotlinx.coroutines.debug.internal

@JvmSynthetic
fun `access$repr`(var0: java.lang.String): java.lang.String {
   return repr(var0);
}

private fun String.repr(): String {
   val var4: StringBuilder = new StringBuilder("\"");
   val var3: Int = var0.length();

   for (int var2 = 0; var2 < var3; var2++) {
      val var1: Char = var0.charAt(var2);
      if (var1 == '"') {
         var4.append("\\\"");
      } else if (var1 == '\\') {
         var4.append("\\\\");
      } else if (var1 == '\b') {
         var4.append("\\b");
      } else if (var1 == '\n') {
         var4.append("\\n");
      } else if (var1 == '\r') {
         var4.append("\\r");
      } else if (var1 == '\t') {
         var4.append("\\t");
      } else {
         var4.append(var1);
      }
   }

   var4.append('"');
   var0 = var4.toString();
   return var0;
}
