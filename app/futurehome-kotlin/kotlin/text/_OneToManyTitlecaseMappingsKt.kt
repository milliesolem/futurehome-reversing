package kotlin.text

import java.util.Locale

internal fun Char.titlecaseImpl(): String {
   var var1: java.lang.String = java.lang.String.valueOf(var0);
   var1 = var1.toUpperCase(Locale.ROOT);
   if (var1.length() > 1) {
      if (var0 != 329) {
         var0 = var1.charAt(0);
         var1 = var1.substring(1);
         var1 = var1.toLowerCase(Locale.ROOT);
         val var2: StringBuilder = new StringBuilder();
         var2.append(var0);
         var2.append(var1);
         var1 = var2.toString();
      }

      return var1;
   } else {
      return java.lang.String.valueOf(Character.toTitleCase(var0));
   }
}
