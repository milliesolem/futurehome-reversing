package kotlin.time

import java.math.RoundingMode
import java.text.DecimalFormat

internal final val durationAssertionsEnabled: Boolean = false
private final val precisionFormats: Array<ThreadLocal<DecimalFormat>>
private ThreadLocal<DecimalFormat>[] precisionFormats;

private fun createFormatForDecimals(decimals: Int): DecimalFormat {
   val var1: DecimalFormat = new DecimalFormat("0");
   if (var0 > 0) {
      var1.setMinimumFractionDigits(var0);
   }

   var1.setRoundingMode(RoundingMode.HALF_UP);
   return var1;
}

internal fun formatToExactDecimals(value: Double, decimals: Int): String {
   val var7: DecimalFormat;
   if (var2 < precisionFormats.length) {
      val var5: ThreadLocal = precisionFormats[var2];
      val var4: Any = precisionFormats[var2].get();
      var var6: Any = var4;
      if (var4 == null) {
         var6 = createFormatForDecimals(var2);
         var5.set(var6);
      }

      var7 = var6 as DecimalFormat;
   } else {
      var7 = createFormatForDecimals(var2);
   }

   val var8: java.lang.String = var7.format(var0);
   return var8;
}
