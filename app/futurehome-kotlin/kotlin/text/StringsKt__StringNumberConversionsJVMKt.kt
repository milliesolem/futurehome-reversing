package kotlin.text

import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext

internal class StringsKt__StringNumberConversionsJVMKt : StringsKt__StringBuilderKt {
   open fun StringsKt__StringNumberConversionsJVMKt() {
   }

   @JvmStatic
   private inline fun <T> screenFloatValue(str: String, parse: (String) -> T): T? {
      var var2: Any = null;

      try {
         if (ScreenFloatValueRegEx.value.matches(var0)) {
            var2 = var1.invoke(var0);
         }
      } catch (var4: NumberFormatException) {
         var2 = null;
      }

      return (T)var2;
   }

   @JvmStatic
   public inline fun String.toBigDecimal(): BigDecimal {
      return new BigDecimal(var0);
   }

   @JvmStatic
   public inline fun String.toBigDecimal(mathContext: MathContext): BigDecimal {
      return new BigDecimal(var0, var1);
   }

   @JvmStatic
   public fun String.toBigDecimalOrNull(): BigDecimal? {
      var var1: BigDecimal = null;

      try {
         if (ScreenFloatValueRegEx.value.matches(var0)) {
            var1 = new BigDecimal(var0);
         }
      } catch (var3: NumberFormatException) {
         var1 = null;
      }

      return var1;
   }

   @JvmStatic
   public fun String.toBigDecimalOrNull(mathContext: MathContext): BigDecimal? {
      var var2: BigDecimal = null;

      try {
         if (ScreenFloatValueRegEx.value.matches(var0)) {
            var2 = new BigDecimal(var0, var1);
         }
      } catch (var4: NumberFormatException) {
         var2 = null;
      }

      return var2;
   }

   @JvmStatic
   public inline fun String.toBigInteger(): BigInteger {
      return new BigInteger(var0);
   }

   @JvmStatic
   public inline fun String.toBigInteger(radix: Int): BigInteger {
      return new BigInteger(var0, CharsKt.checkRadix(var1));
   }

   @JvmStatic
   public fun String.toBigIntegerOrNull(): BigInteger? {
      return StringsKt.toBigIntegerOrNull(var0, 10);
   }

   @JvmStatic
   public fun String.toBigIntegerOrNull(radix: Int): BigInteger? {
      CharsKt.checkRadix(var1);
      val var3: Int = var0.length();
      if (var3 == 0) {
         return null;
      } else {
         var var2: Int = 0;
         if (var3 != 1) {
            if (var0.charAt(0) == '-') {
               var2 = 1;
            }

            while (var2 < var3) {
               if (CharsKt.digitOf(var0.charAt(var2), var1) < 0) {
                  return null;
               }

               var2++;
            }
         } else if (CharsKt.digitOf(var0.charAt(0), var1) < 0) {
            return null;
         }

         return new BigInteger(var0, CharsKt.checkRadix(var1));
      }
   }

   @JvmStatic
   public inline fun String?.toBoolean(): Boolean {
      return java.lang.Boolean.parseBoolean(var0);
   }

   @JvmStatic
   public inline fun String.toByte(): Byte {
      return java.lang.Byte.parseByte(var0);
   }

   @JvmStatic
   public inline fun String.toByte(radix: Int): Byte {
      return java.lang.Byte.parseByte(var0, CharsKt.checkRadix(var1));
   }

   @JvmStatic
   public inline fun String.toDouble(): Double {
      return java.lang.Double.parseDouble(var0);
   }

   @JvmStatic
   public fun String.toDoubleOrNull(): Double? {
      val var3: Any = null;

      var var1: Double;
      try {
         if (!ScreenFloatValueRegEx.value.matches(var0)) {
            return (java.lang.Double)var3;
         }

         var1 = java.lang.Double.parseDouble(var0);
      } catch (var5: NumberFormatException) {
         return null;
      }

      return var1;
   }

   @JvmStatic
   public inline fun String.toFloat(): Float {
      return java.lang.Float.parseFloat(var0);
   }

   @JvmStatic
   public fun String.toFloatOrNull(): Float? {
      val var2: Any = null;

      var var1: Float;
      try {
         if (!ScreenFloatValueRegEx.value.matches(var0)) {
            return (java.lang.Float)var2;
         }

         var1 = java.lang.Float.parseFloat(var0);
      } catch (var4: NumberFormatException) {
         return null;
      }

      return var1;
   }

   @JvmStatic
   public inline fun String.toInt(): Int {
      return Integer.parseInt(var0);
   }

   @JvmStatic
   public inline fun String.toInt(radix: Int): Int {
      return Integer.parseInt(var0, CharsKt.checkRadix(var1));
   }

   @JvmStatic
   public inline fun String.toLong(): Long {
      return java.lang.Long.parseLong(var0);
   }

   @JvmStatic
   public inline fun String.toLong(radix: Int): Long {
      return java.lang.Long.parseLong(var0, CharsKt.checkRadix(var1));
   }

   @JvmStatic
   public inline fun String.toShort(): Short {
      return java.lang.Short.parseShort(var0);
   }

   @JvmStatic
   public inline fun String.toShort(radix: Int): Short {
      return java.lang.Short.parseShort(var0, CharsKt.checkRadix(var1));
   }

   @JvmStatic
   public inline fun Byte.toString(radix: Int): String {
      val var2: java.lang.String = Integer.toString(var0, CharsKt.checkRadix(var1));
      return var2;
   }

   @JvmStatic
   public inline fun Int.toString(radix: Int): String {
      val var2: java.lang.String = Integer.toString(var0, CharsKt.checkRadix(var1));
      return var2;
   }

   @JvmStatic
   public inline fun Long.toString(radix: Int): String {
      val var3: java.lang.String = java.lang.Long.toString(var0, CharsKt.checkRadix(var2));
      return var3;
   }

   @JvmStatic
   public inline fun Short.toString(radix: Int): String {
      val var2: java.lang.String = Integer.toString(var0, CharsKt.checkRadix(var1));
      return var2;
   }
}
