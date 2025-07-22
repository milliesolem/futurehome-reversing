package kotlin.io

import java.io.File

@JvmSynthetic
fun `access$constructMessage`(var0: File, var1: File, var2: java.lang.String): java.lang.String {
   return constructMessage(var0, var1, var2);
}

private fun constructMessage(file: File, other: File?, reason: String?): String {
   val var4: StringBuilder = new StringBuilder(var0.toString());
   if (var1 != null) {
      val var3: StringBuilder = new StringBuilder(" -> ");
      var3.append(var1);
      var4.append(var3.toString());
   }

   if (var2 != null) {
      val var6: StringBuilder = new StringBuilder(": ");
      var6.append(var2);
      var4.append(var6.toString());
   }

   val var5: java.lang.String = var4.toString();
   return var5;
}
