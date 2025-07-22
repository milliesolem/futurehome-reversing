package kotlin.text

import kotlin.jvm.internal.Intrinsics

public fun ULong.toString(radix: Int): String {
   return UnsignedKt.ulongToString(var0, CharsKt.checkRadix(var2));
}

public fun UByte.toString(radix: Int): String {
   val var2: java.lang.String = Integer.toString(var0 and 255, CharsKt.checkRadix(var1));
   return var2;
}

public fun UInt.toString(radix: Int): String {
   return UnsignedKt.ulongToString((long)var0 and 4294967295L, CharsKt.checkRadix(var1));
}

public fun UShort.toString(radix: Int): String {
   val var2: java.lang.String = Integer.toString(var0 and '\uffff', CharsKt.checkRadix(var1));
   return var2;
}

public fun String.toUByte(): UByte {
   val var1: UByte = toUByteOrNull(var0);
   if (var1 != null) {
      return var1.unbox-impl();
   } else {
      StringsKt.numberFormatError(var0);
      throw new KotlinNothingValueException();
   }
}

public fun String.toUByte(radix: Int): UByte {
   val var2: UByte = toUByteOrNull(var0, var1);
   if (var2 != null) {
      return var2.unbox-impl();
   } else {
      StringsKt.numberFormatError(var0);
      throw new KotlinNothingValueException();
   }
}

public fun String.toUByteOrNull(): UByte? {
   return toUByteOrNull(var0, 10);
}

public fun String.toUByteOrNull(radix: Int): UByte? {
   val var2: UInt = toUIntOrNull(var0, var1);
   if (var2 != null) {
      var1 = var2.unbox-impl();
      return if (UByte$$ExternalSyntheticBackport0.m$2(var1, UInt.constructor-impl(255)) > 0) null else UByte.box-impl(UByte.constructor-impl((byte)var1));
   } else {
      return null;
   }
}

public fun String.toUInt(): UInt {
   val var1: UInt = toUIntOrNull(var0);
   if (var1 != null) {
      return var1.unbox-impl();
   } else {
      StringsKt.numberFormatError(var0);
      throw new KotlinNothingValueException();
   }
}

public fun String.toUInt(radix: Int): UInt {
   val var2: UInt = toUIntOrNull(var0, var1);
   if (var2 != null) {
      return var2.unbox-impl();
   } else {
      StringsKt.numberFormatError(var0);
      throw new KotlinNothingValueException();
   }
}

public fun String.toUIntOrNull(): UInt? {
   return toUIntOrNull(var0, 10);
}

public fun String.toUIntOrNull(radix: Int): UInt? {
   CharsKt.checkRadix(var1);
   val var6: Int = var0.length();
   if (var6 == 0) {
      return null;
   } else {
      var var4: Int = 0;
      var var3: Int = var0.charAt(0);
      var var2: Byte;
      if (Intrinsics.compare(var3, 48) < 0) {
         var2 = 1;
         if (var6 == 1 || var3 != 43) {
            return null;
         }
      } else {
         var2 = 0;
      }

      val var7: Int = UInt.constructor-impl(var1);
      var var5: Int = 119304647;
      var3 = var2;

      while (true) {
         if (var3 >= var6) {
            return UInt.box-impl(var4);
         }

         val var8: Int = CharsKt.digitOf(var0.charAt(var3), var1);
         if (var8 < 0) {
            return null;
         }

         var2 = var5;
         if (UByte$$ExternalSyntheticBackport0.m$2(var4, var5) > 0) {
            if (var5 != 119304647) {
               break;
            }

            var5 = UByte$$ExternalSyntheticBackport0.m(-1, var7);
            var2 = var5;
            if (UByte$$ExternalSyntheticBackport0.m$2(var4, var5) > 0) {
               break;
            }
         }

         var5 = UInt.constructor-impl(var4 * var7);
         var4 = UInt.constructor-impl(UInt.constructor-impl(var8) + var5);
         if (UByte$$ExternalSyntheticBackport0.m$2(var4, var5) < 0) {
            return null;
         }

         var3++;
         var5 = var2;
      }

      return null;
   }
}

public fun String.toULong(): ULong {
   val var1: ULong = toULongOrNull(var0);
   if (var1 != null) {
      return var1.unbox-impl();
   } else {
      StringsKt.numberFormatError(var0);
      throw new KotlinNothingValueException();
   }
}

public fun String.toULong(radix: Int): ULong {
   val var2: ULong = toULongOrNull(var0, var1);
   if (var2 != null) {
      return var2.unbox-impl();
   } else {
      StringsKt.numberFormatError(var0);
      throw new KotlinNothingValueException();
   }
}

public fun String.toULongOrNull(): ULong? {
   return toULongOrNull(var0, 10);
}

public fun String.toULongOrNull(radix: Int): ULong? {
   CharsKt.checkRadix(var1);
   val var3: Int = var0.length();
   if (var3 == 0) {
      return null;
   } else {
      var var2: Int = 0;
      var var4: Int = var0.charAt(0);
      if (Intrinsics.compare(var4, 48) < 0) {
         var2 = 1;
         if (var3 == 1 || var4 != 43) {
            return null;
         }
      }

      val var11: Long = ULong.constructor-impl((long)var1);
      var var7: Long = 0L;
      var var9: Long = 512409557603043100L;

      while (true) {
         if (var2 >= var3) {
            return ULong.box-impl(var7);
         }

         var4 = CharsKt.digitOf(var0.charAt(var2), var1);
         if (var4 < 0) {
            return null;
         }

         var var5: Long = var9;
         if (UByte$$ExternalSyntheticBackport0.m(var7, var9) > 0) {
            if (var9 != 512409557603043100L) {
               break;
            }

            var9 = UByte$$ExternalSyntheticBackport0.m$1(-1L, var11);
            var5 = var9;
            if (UByte$$ExternalSyntheticBackport0.m(var7, var9) > 0) {
               break;
            }
         }

         var9 = ULong.constructor-impl(var7 * var11);
         var7 = ULong.constructor-impl(ULong.constructor-impl((long)UInt.constructor-impl(var4) and 4294967295L) + var9);
         if (UByte$$ExternalSyntheticBackport0.m(var7, var9) < 0) {
            return null;
         }

         var2++;
         var9 = var5;
      }

      return null;
   }
}

public fun String.toUShort(): UShort {
   val var1: UShort = toUShortOrNull(var0);
   if (var1 != null) {
      return var1.unbox-impl();
   } else {
      StringsKt.numberFormatError(var0);
      throw new KotlinNothingValueException();
   }
}

public fun String.toUShort(radix: Int): UShort {
   val var2: UShort = toUShortOrNull(var0, var1);
   if (var2 != null) {
      return var2.unbox-impl();
   } else {
      StringsKt.numberFormatError(var0);
      throw new KotlinNothingValueException();
   }
}

public fun String.toUShortOrNull(): UShort? {
   return toUShortOrNull(var0, 10);
}

public fun String.toUShortOrNull(radix: Int): UShort? {
   val var2: UInt = toUIntOrNull(var0, var1);
   if (var2 != null) {
      var1 = var2.unbox-impl();
      return if (UByte$$ExternalSyntheticBackport0.m$2(var1, UInt.constructor-impl(65535)) > 0) null else UShort.box-impl(UShort.constructor-impl((short)var1));
   } else {
      return null;
   }
}
