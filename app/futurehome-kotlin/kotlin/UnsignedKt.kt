package kotlin

import kotlin.jvm.internal.Intrinsics

internal fun doubleToUInt(value: Double): UInt {
   val var3: Boolean = java.lang.Double.isNaN(var0);
   var var2: Int = 0;
   if (!var3 && !(var0 <= uintToDouble(0))) {
      var2 = -1;
      if (!(var0 >= uintToDouble(-1))) {
         if (var0 <= 2.147483647E9) {
            var2 = UInt.constructor-impl((int)var0);
         } else {
            var2 = UInt.constructor-impl(UInt.constructor-impl((int)(var0 - (double)Integer.MAX_VALUE)) + UInt.constructor-impl(Integer.MAX_VALUE));
         }
      }
   }

   return var2;
}

internal fun doubleToULong(value: Double): ULong {
   val var2: Boolean = java.lang.Double.isNaN(var0);
   var var3: Long = 0L;
   if (!var2 && !(var0 <= ulongToDouble(0L))) {
      var3 = -1L;
      if (!(var0 >= ulongToDouble(-1L))) {
         if (var0 < 9.223372E18F) {
            var3 = ULong.constructor-impl((long)var0);
         } else {
            var3 = ULong.constructor-impl(ULong.constructor-impl((long)(var0 - 9.223372E18F)) - java.lang.Long.MIN_VALUE);
         }
      }
   }

   return var3;
}

internal inline fun floatToUInt(value: Float): UInt {
   return doubleToUInt((double)var0);
}

internal inline fun floatToULong(value: Float): ULong {
   return doubleToULong((double)var0);
}

internal fun uintCompare(v1: Int, v2: Int): Int {
   return Intrinsics.compare(var0 xor Integer.MIN_VALUE, var1 xor Integer.MIN_VALUE);
}

internal fun uintDivide(v1: UInt, v2: UInt): UInt {
   return UInt.constructor-impl((int)(((long)var0 and 4294967295L) / ((long)var1 and 4294967295L)));
}

internal fun uintRemainder(v1: UInt, v2: UInt): UInt {
   return UInt.constructor-impl((int)(((long)var0 and 4294967295L) % ((long)var1 and 4294967295L)));
}

internal fun uintToDouble(value: Int): Double {
   return (Integer.MAX_VALUE and var0) + (double)((var0 ushr 31) shl 30) * 2;
}

internal inline fun uintToFloat(value: Int): Float {
   return (float)uintToDouble(var0);
}

internal inline fun uintToLong(value: Int): Long {
   return var0 and 4294967295L;
}

internal inline fun uintToString(value: Int): String {
   return java.lang.String.valueOf((long)var0 and 4294967295L);
}

internal inline fun uintToString(value: Int, base: Int): String {
   return ulongToString((long)var0 and 4294967295L, var1);
}

internal inline fun uintToULong(value: Int): ULong {
   return ULong.constructor-impl((long)var0 and 4294967295L);
}

internal fun ulongCompare(v1: Long, v2: Long): Int {
   return Intrinsics.compare(var0 xor java.lang.Long.MIN_VALUE, var2 xor java.lang.Long.MIN_VALUE);
}

internal fun ulongDivide(v1: ULong, v2: ULong): ULong {
   if (var2 < 0L) {
      if (UByte$$ExternalSyntheticBackport0.m(var0, var2) < 0) {
         var0 = ULong.constructor-impl(0L);
      } else {
         var0 = ULong.constructor-impl(1L);
      }

      return var0;
   } else if (var0 >= 0L) {
      return ULong.constructor-impl(var0 / var2);
   } else {
      var var4: Byte = 1;
      val var5: Long = (var0 ushr 1) / var2 shl 1;
      if (UByte$$ExternalSyntheticBackport0.m(ULong.constructor-impl(var0 - ((var0 ushr 1) / var2 shl 1) * var2), ULong.constructor-impl(var2)) < 0) {
         var4 = 0;
      }

      return ULong.constructor-impl(var5 + (long)var4);
   }
}

internal fun ulongRemainder(v1: ULong, v2: ULong): ULong {
   if (var2 < 0L) {
      if (UByte$$ExternalSyntheticBackport0.m(var0, var2) >= 0) {
         var0 = ULong.constructor-impl(var0 - var2);
      }

      return var0;
   } else if (var0 >= 0L) {
      return ULong.constructor-impl(var0 % var2);
   } else {
      var0 = var0 - ((var0 ushr 1) / var2 shl 1) * var2;
      if (UByte$$ExternalSyntheticBackport0.m(ULong.constructor-impl(var0 - ((var0 ushr 1) / var2 shl 1) * var2), ULong.constructor-impl(var2)) < 0) {
         var2 = 0L;
      }

      return ULong.constructor-impl(var0 - var2);
   }
}

internal fun ulongToDouble(value: Long): Double {
   return (double)(var0 ushr 11) * 2048 + (var0 and 2047L);
}

internal inline fun ulongToFloat(value: Long): Float {
   return (float)ulongToDouble(var0);
}

internal inline fun ulongToString(value: Long): String {
   return ulongToString(var0, 10);
}

internal fun ulongToString(value: Long, base: Int): String {
   if (var0 >= 0L) {
      val var14: java.lang.String = java.lang.Long.toString(var0, CharsKt.checkRadix(var2));
      return var14;
   } else {
      val var9: Long = var2;
      val var5: Long = (var0 ushr 1) / var2 shl 1;
      val var7: Long = var0 - ((var0 ushr 1) / var2 shl 1) * var2;
      var var3: Long = (var0 ushr 1) / var2 shl 1;
      var0 = var7;
      if (var7 >= var9) {
         var0 = var7 - var9;
         var3 = var5 + 1L;
      }

      val var11: StringBuilder = new StringBuilder();
      var var12: java.lang.String = java.lang.Long.toString(var3, CharsKt.checkRadix(var2));
      var11.append(var12);
      var12 = java.lang.Long.toString(var0, CharsKt.checkRadix(var2));
      var11.append(var12);
      return var11.toString();
   }
}
