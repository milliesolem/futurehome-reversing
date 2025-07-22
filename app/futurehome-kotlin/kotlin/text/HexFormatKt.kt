package kotlin.text

import kotlin.jvm.internal.Intrinsics
import kotlin.text.HexFormat.Builder

public inline fun HexFormat(builderAction: (Builder) -> Unit): HexFormat {
   val var1: HexFormat.Builder = new HexFormat.Builder();
   var0.invoke(var1);
   return var1.build();
}

@JvmSynthetic
fun `access$isCaseSensitive`(var0: java.lang.String): Boolean {
   return isCaseSensitive(var0);
}

private fun String.isCaseSensitive(): Boolean {
   val var5: java.lang.CharSequence = var0;
   val var4: Boolean = false;
   var var2: Int = 0;

   var var3: Boolean;
   while (true) {
      var3 = var4;
      if (var2 >= var5.length()) {
         break;
      }

      val var1: Char = var5.charAt(var2);
      if (Intrinsics.compare(var1, 128) >= 0 || Character.isLetter(var1)) {
         var3 = true;
         break;
      }

      var2++;
   }

   return var3;
}
