package kotlin.text

import java.util.Arrays
import kotlin.text.HexFormat.BytesHexFormat
import kotlin.text.HexFormat.NumberHexFormat

private const val LOWER_CASE_HEX_DIGITS: String = "0123456789abcdef"
private const val UPPER_CASE_HEX_DIGITS: String = "0123456789ABCDEF"
internal final val BYTE_TO_LOWER_CASE_HEX_DIGITS: IntArray
private final val BYTE_TO_UPPER_CASE_HEX_DIGITS: IntArray
private final val HEX_DIGITS_TO_DECIMAL: IntArray
private final val HEX_DIGITS_TO_LONG_DECIMAL: LongArray

private fun charsPerSet(charsPerElement: Long, elementsPerSet: Int, elementSeparatorLength: Int): Long {
   if (var2 > 0) {
      return var0 * var2 + var3 * (var2 - 1L);
   } else {
      throw new IllegalArgumentException("Failed requirement.".toString());
   }
}

private inline fun String.checkContainsAt(index: Int, endIndex: Int, part: String, ignoreCase: Boolean, partName: String): Int {
   val var8: java.lang.CharSequence = var3;
   if (var3.length() == 0) {
      return var1;
   } else {
      val var7: Int = var8.length();

      for (int var6 = 0; var6 < var7; var6++) {
         if (!CharsKt.equals(var3.charAt(var6), var0.charAt(var1 + var6), var4)) {
            throwNotContainedAt(var0, var1, var2, var3, var5);
         }
      }

      return var1 + var3.length();
   }
}

private fun checkFormatLength(formatLength: Long): Int {
   if (0L <= var0 && var0 <= 2147483647L) {
      return (int)var0;
   } else {
      val var2: StringBuilder = new StringBuilder("The resulting string length is too big: ");
      var2.append(ULong.toString-impl(ULong.constructor-impl(var0)));
      throw new IllegalArgumentException(var2.toString());
   }
}

private fun String.checkNewLineAt(index: Int, endIndex: Int): Int {
   var var3: Int;
   if (var0.charAt(var1) == '\r') {
      val var4: Int = var1 + 1;
      var3 = var1 + 1;
      if (var4 < var2) {
         var3 = var4;
         if (var0.charAt(var4) == '\n') {
            var3 = var1 + 2;
         }
      }
   } else {
      if (var0.charAt(var1) != '\n') {
         val var5: StringBuilder = new StringBuilder("Expected a new line at index ");
         var5.append(var1);
         var5.append(", but was ");
         var5.append(var0.charAt(var1));
         throw new NumberFormatException(var5.toString());
      }

      var3 = var1 + 1;
   }

   return var3;
}

private fun String.checkNumberOfDigits(startIndex: Int, endIndex: Int, typeHexLength: Int) {
   val var4: Int = var2 - var1;
   if (var2 - var1 < 1) {
      throwInvalidNumberOfDigits(var0, var1, var2, "at least", 1);
   } else if (var4 > var3) {
      checkZeroDigits(var0, var1, var4 + var1 - var3);
   }
}

private fun String.checkPrefixSuffixNumberOfDigits(startIndex: Int, endIndex: Int, prefix: String, suffix: String, ignoreCase: Boolean, typeHexLength: Int) {
   if (var2 - var1 - var3.length() <= var4.length()) {
      throwInvalidPrefixSuffix(var0, var1, var2, var3, var4);
   }

   val var11: java.lang.CharSequence = var3;
   if (var3.length() != 0) {
      val var9: Int = var11.length();

      for (int var13 = 0; var13 < var9; var13++) {
         if (!CharsKt.equals(var3.charAt(var13), var0.charAt(var1 + var13), var5)) {
            throwNotContainedAt(var0, var1, var2, var3, "prefix");
         }
      }

      var1 += var3.length();
   }

   val var10: Int = var2 - var4.length();
   val var12: java.lang.CharSequence = var4;
   if (var4.length() != 0) {
      val var15: Int = var12.length();

      for (int var14 = 0; var14 < var15; var14++) {
         if (!CharsKt.equals(var4.charAt(var14), var0.charAt(var10 + var14), var5)) {
            throwNotContainedAt(var0, var10, var2, var4, "suffix");
         }
      }

      var4.length();
   }

   checkNumberOfDigits(var0, var1, var10, var6);
}

private fun String.checkZeroDigits(startIndex: Int, endIndex: Int) {
   while (var1 < var2) {
      if (var0.charAt(var1) != '0') {
         val var3: StringBuilder = new StringBuilder("Expected the hexadecimal digit '0' at index ");
         var3.append(var1);
         var3.append(", but was '");
         var3.append(var0.charAt(var1));
         var3.append("'.\nThe result won't fit the type being parsed.");
         throw new NumberFormatException(var3.toString());
      }

      var1++;
   }
}

private inline fun String.decimalFromHexDigitAt(index: Int): Int {
   var var2: Int = var0.charAt(var1);
   if (var2 ushr 8 == 0) {
      var2 = HEX_DIGITS_TO_DECIMAL[var2];
      if (HEX_DIGITS_TO_DECIMAL[var2] >= 0) {
         return var2;
      }
   }

   throwInvalidDigitAt(var0, var1);
   throw new KotlinNothingValueException();
}

private fun ByteArray.formatByteAt(index: Int, bytePrefix: String, byteSuffix: String, byteToDigits: IntArray, destination: CharArray, destinationOffset: Int): Int {
   return toCharArrayIfNotEmpty(var3, var5, formatByteAt(var0, var1, var4, var5, toCharArrayIfNotEmpty(var2, var5, var6)));
}

private fun ByteArray.formatByteAt(index: Int, byteToDigits: IntArray, destination: CharArray, destinationOffset: Int): Int {
   var3[var4] = (char)(var2[var0[var1] and 255] shr 8);
   var3[var4 + 1] = (char)(var2[var0[var1] and 255] and 255);
   return var4 + 2;
}

private fun formattedStringLength(numberOfBytes: Int, byteSeparatorLength: Int, bytePrefixLength: Int, byteSuffixLength: Int): Int {
   if (var0 > 0) {
      return checkFormatLength((long)var0 * ((long)var2 + 2L + (long)var3 + (long)var1) - (long)var1);
   } else {
      throw new IllegalArgumentException("Failed requirement.".toString());
   }
}

internal fun formattedStringLength(
   numberOfBytes: Int,
   bytesPerLine: Int,
   bytesPerGroup: Int,
   groupSeparatorLength: Int,
   byteSeparatorLength: Int,
   bytePrefixLength: Int,
   byteSuffixLength: Int
): Int {
   if (var0 > 0) {
      val var8: Int = var0 - 1;
      val var9: Int = (var0 - 1) / var1;
      val var10: Int = (var1 - 1) / var2;
      val var7: Int = var0 % var1;
      if (var0 % var1 != 0) {
         var1 = var7;
      }

      return checkFormatLength(
         (long)var9
            + (long)(var10 * var9 + (var1 - 1) / var2) * (long)var3
            + (long)(var8 - var9 - (var10 * var9 + (var1 - 1) / var2)) * (long)var4
            + (long)var0 * ((long)var5 + 2L + (long)var6)
      );
   } else {
      throw new IllegalArgumentException("Failed requirement.".toString());
   }
}

private fun String.hexToByte(startIndex: Int = 0, endIndex: Int = var0.length(), format: HexFormat = HexFormat.Companion.getDefault()): Byte {
   return (byte)hexToIntImpl(var0, var1, var2, var3, 2);
}

public fun String.hexToByte(format: HexFormat = HexFormat.Companion.getDefault()): Byte {
   return hexToByte(var0, 0, var0.length(), var1);
}

@JvmSynthetic
fun `hexToByte$default`(var0: java.lang.String, var1: Int, var2: Int, var3: HexFormat, var4: Int, var5: Any): Byte {
   if ((var4 and 1) != 0) {
      var1 = 0;
   }

   if ((var4 and 2) != 0) {
      var2 = var0.length();
   }

   if ((var4 and 4) != 0) {
      var3 = HexFormat.Companion.getDefault();
   }

   return hexToByte(var0, var1, var2, var3);
}

@JvmSynthetic
fun `hexToByte$default`(var0: java.lang.String, var1: HexFormat, var2: Int, var3: Any): Byte {
   if ((var2 and 1) != 0) {
      var1 = HexFormat.Companion.getDefault();
   }

   return hexToByte(var0, var1);
}

private fun String.hexToByteArray(startIndex: Int = 0, endIndex: Int = var0.length(), format: HexFormat = HexFormat.Companion.getDefault()): ByteArray {
   AbstractList.Companion.checkBoundsIndexes$kotlin_stdlib(var1, var2, var0.length());
   if (var1 == var2) {
      return new byte[0];
   } else {
      val var5: HexFormat.BytesHexFormat = var3.getBytes();
      if (var5.getNoLineAndGroupSeparator$kotlin_stdlib()) {
         val var4: ByteArray = hexToByteArrayNoLineAndGroupSeparator(var0, var1, var2, var5);
         if (var4 != null) {
            return var4;
         }
      }

      return hexToByteArraySlowPath(var0, var1, var2, var5);
   }
}

public fun String.hexToByteArray(format: HexFormat = HexFormat.Companion.getDefault()): ByteArray {
   return hexToByteArray(var0, 0, var0.length(), var1);
}

@JvmSynthetic
fun `hexToByteArray$default`(var0: java.lang.String, var1: Int, var2: Int, var3: HexFormat, var4: Int, var5: Any): ByteArray {
   if ((var4 and 1) != 0) {
      var1 = 0;
   }

   if ((var4 and 2) != 0) {
      var2 = var0.length();
   }

   if ((var4 and 4) != 0) {
      var3 = HexFormat.Companion.getDefault();
   }

   return hexToByteArray(var0, var1, var2, var3);
}

@JvmSynthetic
fun `hexToByteArray$default`(var0: java.lang.String, var1: HexFormat, var2: Int, var3: Any): ByteArray {
   if ((var2 and 1) != 0) {
      var1 = HexFormat.Companion.getDefault();
   }

   return hexToByteArray(var0, var1);
}

private fun String.hexToByteArrayNoLineAndGroupSeparator(startIndex: Int, endIndex: Int, bytesFormat: BytesHexFormat): ByteArray? {
   return if (var3.getShortByteSeparatorNoPrefixAndSuffix$kotlin_stdlib())
      hexToByteArrayShortByteSeparatorNoPrefixAndSuffix(var0, var1, var2, var3)
      else
      hexToByteArrayNoLineAndGroupSeparatorSlowPath(var0, var1, var2, var3);
}

private fun String.hexToByteArrayNoLineAndGroupSeparatorSlowPath(startIndex: Int, endIndex: Int, bytesFormat: BytesHexFormat): ByteArray? {
   val var17: java.lang.String = var3.getBytePrefix();
   val var16: java.lang.String = var3.getByteSuffix();
   var var18: java.lang.String = var3.getByteSeparator();
   var var4: Int = var18.length();
   var var11: Long = var17.length();
   var var13: Long = var16.length();
   val var9: Long = var4;
   var11 = var11 + 2L + var13 + var4;
   var13 = var2 - var1;
   var var6: Int = (int)((var2 - var1 + var9) / var11);
   if ((int)((var2 - var1 + var9) / var11) * var11 - var9 != var13) {
      return null;
   } else {
      val var15: Boolean = var3.getIgnoreCase$kotlin_stdlib();
      val var22: ByteArray = new byte[var6];
      val var19: java.lang.CharSequence = var17;
      if (var17.length() != 0) {
         val var7: Int = var19.length();

         for (int var24 = 0; var24 < var7; var24++) {
            if (!CharsKt.equals(var17.charAt(var24), var0.charAt(var1 + var24), var15)) {
               throwNotContainedAt(var0, var1, var2, var17, "byte prefix");
            }
         }

         var1 += var17.length();
      }

      val var35: StringBuilder = new StringBuilder();
      var35.append(var16);
      var35.append(var18);
      var35.append(var17);
      var18 = var35.toString();
      val var29: Int = var6 - 1;

      for (int var25 = 0; var25 < var29; var25++) {
         var22[var25] = parseByteAt(var0, var1);
         var6 = var1 + 2;
         val var32: java.lang.CharSequence = var18;
         if (var18.length() == 0) {
            var1 = var6;
         } else {
            val var8: Int = var32.length();

            for (int var20 = 0; var20 < var8; var20++) {
               if (!CharsKt.equals(var18.charAt(var20), var0.charAt(var6 + var20), var15)) {
                  throwNotContainedAt(var0, var6, var2, var18, "byte suffix + byte separator + byte prefix");
               }
            }

            var1 = var6 + var18.length();
         }
      }

      var22[var29] = parseByteAt(var0, var1);
      var4 = var1 + 2;
      val var33: java.lang.CharSequence = var16;
      if (var16.length() != 0) {
         var6 = var33.length();

         for (int var21 = 0; var21 < var6; var21++) {
            if (!CharsKt.equals(var16.charAt(var21), var0.charAt(var4 + var21), var15)) {
               throwNotContainedAt(var0, var4, var2, var16, "byte suffix");
            }
         }

         var16.length();
      }

      return var22;
   }
}

private fun String.hexToByteArrayShortByteSeparatorNoPrefixAndSuffix(startIndex: Int, endIndex: Int, bytesFormat: BytesHexFormat): ByteArray? {
   var var6: Int = var3.getByteSeparator().length();
   if (var6 > 1) {
      throw new IllegalArgumentException("Failed requirement.".toString());
   } else {
      val var7: Int = var2 - var1;
      if (var6 == 0) {
         if ((var7 and 1) != 0) {
            return null;
         } else {
            val var20: Int = var7 shr 1;
            val var17: ByteArray = new byte[var7 shr 1];
            var1 = 0;

            for (int var16 = 0; var16 < var20; var16++) {
               var17[var16] = parseByteAt(var0, var1);
               var1 += 2;
            }

            return var17;
         }
      } else if (var7 % 3 != 2) {
         return null;
      } else {
         var6 = var7 / 3 + 1;
         val var12: ByteArray = new byte[var7 / 3 + 1];
         val var22: Char = var3.getByteSeparator().charAt(0);
         var12[0] = parseByteAt(var0, 0);
         var var18: Int = 2;

         for (int var14 = 1; var14 < var6; var14++) {
            if (var0.charAt(var18) != var22) {
               val var11: java.lang.String = var3.getByteSeparator();
               val var9: Boolean = var3.getIgnoreCase$kotlin_stdlib();
               val var10: java.lang.CharSequence = var11;
               if (var11.length() != 0) {
                  val var8: Int = var10.length();

                  for (int var19 = 0; var19 < var8; var19++) {
                     if (!CharsKt.equals(var11.charAt(var19), var0.charAt(var18 + var19), var9)) {
                        throwNotContainedAt(var0, var18, var2, var11, "byte separator");
                     }
                  }

                  var11.length();
               }
            }

            var12[var14] = parseByteAt(var0, var18 + 1);
            var18 += 3;
         }

         return var12;
      }
   }
}

private fun String.hexToByteArraySlowPath(startIndex: Int, endIndex: Int, bytesFormat: BytesHexFormat): ByteArray {
   val var10: Int = var3.getBytesPerLine();
   val var9: Int = var3.getBytesPerGroup();
   val var15: java.lang.String = var3.getBytePrefix();
   val var13: java.lang.String = var3.getByteSuffix();
   val var16: java.lang.String = var3.getByteSeparator();
   val var14: java.lang.String = var3.getGroupSeparator();
   val var12: Boolean = var3.getIgnoreCase$kotlin_stdlib();
   val var11: Int = parsedByteArrayMaxSize(var2 - var1, var10, var9, var14.length(), var16.length(), var15.length(), var13.length());
   val var21: ByteArray = new byte[var11];
   var var4: Int = var1;
   var var6: Int = 0;
   var1 = 0;
   var var5: Int = 0;

   while (var4 < var2) {
      label85: {
         if (var1 == var10) {
            var4 = checkNewLineAt(var0, var4, var2);
            var1 = 0;
         } else {
            if (var5 != var9) {
               if (var5 == 0) {
                  break label85;
               }

               val var34: java.lang.CharSequence = var16;
               if (var16.length() == 0) {
                  break label85;
               }

               val var30: Int = var34.length();

               for (int var8 = 0; var8 < var30; var8++) {
                  if (!CharsKt.equals(var16.charAt(var8), var0.charAt(var4 + var8), var12)) {
                     throwNotContainedAt(var0, var4, var2, var16, "byte separator");
                  }
               }

               var4 += var16.length();
               break label85;
            }

            val var17: java.lang.CharSequence = var14;
            if (var14.length() != 0) {
               var5 = var17.length();

               for (int var7 = 0; var7 < var5; var7++) {
                  if (!CharsKt.equals(var14.charAt(var7), var0.charAt(var4 + var7), var12)) {
                     throwNotContainedAt(var0, var4, var2, var14, "group separator");
                  }
               }

               var4 += var14.length();
            }
         }

         var5 = 0;
      }

      val var33: Int = var1 + 1;
      var1 = var5 + 1;
      var var35: java.lang.CharSequence = var15;
      if (var15.length() == 0) {
         var5 = var4;
      } else {
         val var31: Int = var35.length();

         for (int var24 = 0; var24 < var31; var24++) {
            if (!CharsKt.equals(var15.charAt(var24), var0.charAt(var4 + var24), var12)) {
               throwNotContainedAt(var0, var4, var2, var15, "byte prefix");
            }
         }

         var5 = var4 + var15.length();
      }

      if (var2 - 2 < var5) {
         throwInvalidNumberOfDigits(var0, var5, var2, "exactly", 2);
      }

      var4 = var6 + 1;
      var21[var6] = parseByteAt(var0, var5);
      var6 = var5 + 2;
      var35 = var13;
      if (var13.length() == 0) {
         var5 = var4;
         var4 = var6;
      } else {
         val var32: Int = var35.length();

         for (int var26 = 0; var26 < var32; var26++) {
            if (!CharsKt.equals(var13.charAt(var26), var0.charAt(var6 + var26), var12)) {
               throwNotContainedAt(var0, var6, var2, var13, "byte suffix");
            }
         }

         var6 = var6 + var13.length();
         var5 = var4;
         var4 = var6;
      }

      var6 = var5;
      var5 = var1;
      var1 = var33;
   }

   val var18: ByteArray;
   if (var6 == var11) {
      var18 = var21;
   } else {
      var18 = Arrays.copyOf(var21, var6);
   }

   return var18;
}

private fun String.hexToInt(startIndex: Int = 0, endIndex: Int = var0.length(), format: HexFormat = HexFormat.Companion.getDefault()): Int {
   return hexToIntImpl(var0, var1, var2, var3, 8);
}

public fun String.hexToInt(format: HexFormat = HexFormat.Companion.getDefault()): Int {
   return hexToInt(var0, 0, var0.length(), var1);
}

@JvmSynthetic
fun `hexToInt$default`(var0: java.lang.String, var1: Int, var2: Int, var3: HexFormat, var4: Int, var5: Any): Int {
   if ((var4 and 1) != 0) {
      var1 = 0;
   }

   if ((var4 and 2) != 0) {
      var2 = var0.length();
   }

   if ((var4 and 4) != 0) {
      var3 = HexFormat.Companion.getDefault();
   }

   return hexToInt(var0, var1, var2, var3);
}

@JvmSynthetic
fun `hexToInt$default`(var0: java.lang.String, var1: HexFormat, var2: Int, var3: Any): Int {
   if ((var2 and 1) != 0) {
      var1 = HexFormat.Companion.getDefault();
   }

   return hexToInt(var0, var1);
}

private fun String.hexToIntImpl(startIndex: Int, endIndex: Int, format: HexFormat, typeHexLength: Int): Int {
   AbstractList.Companion.checkBoundsIndexes$kotlin_stdlib(var1, var2, var0.length());
   val var6: HexFormat.NumberHexFormat = var3.getNumber();
   if (var6.isDigitsOnly$kotlin_stdlib()) {
      checkNumberOfDigits(var0, var1, var2, var4);
      return parseInt(var0, var1, var2);
   } else {
      val var7: java.lang.String = var6.getPrefix();
      val var5: java.lang.String = var6.getSuffix();
      checkPrefixSuffixNumberOfDigits(var0, var1, var2, var7, var5, var6.getIgnoreCase$kotlin_stdlib(), var4);
      return parseInt(var0, var1 + var7.length(), var2 - var5.length());
   }
}

internal fun String.hexToLong(startIndex: Int = 0, endIndex: Int = var0.length(), format: HexFormat = HexFormat.Companion.getDefault()): Long {
   return hexToLongImpl(var0, var1, var2, var3, 16);
}

public fun String.hexToLong(format: HexFormat = HexFormat.Companion.getDefault()): Long {
   return hexToLong(var0, 0, var0.length(), var1);
}

@JvmSynthetic
fun `hexToLong$default`(var0: java.lang.String, var1: Int, var2: Int, var3: HexFormat, var4: Int, var5: Any): Long {
   if ((var4 and 1) != 0) {
      var1 = 0;
   }

   if ((var4 and 2) != 0) {
      var2 = var0.length();
   }

   if ((var4 and 4) != 0) {
      var3 = HexFormat.Companion.getDefault();
   }

   return hexToLong(var0, var1, var2, var3);
}

@JvmSynthetic
fun `hexToLong$default`(var0: java.lang.String, var1: HexFormat, var2: Int, var3: Any): Long {
   if ((var2 and 1) != 0) {
      var1 = HexFormat.Companion.getDefault();
   }

   return hexToLong(var0, var1);
}

private fun String.hexToLongImpl(startIndex: Int, endIndex: Int, format: HexFormat, typeHexLength: Int): Long {
   AbstractList.Companion.checkBoundsIndexes$kotlin_stdlib(var1, var2, var0.length());
   val var6: HexFormat.NumberHexFormat = var3.getNumber();
   if (var6.isDigitsOnly$kotlin_stdlib()) {
      checkNumberOfDigits(var0, var1, var2, var4);
      return parseLong(var0, var1, var2);
   } else {
      val var7: java.lang.String = var6.getPrefix();
      val var5: java.lang.String = var6.getSuffix();
      checkPrefixSuffixNumberOfDigits(var0, var1, var2, var7, var5, var6.getIgnoreCase$kotlin_stdlib(), var4);
      return parseLong(var0, var1 + var7.length(), var2 - var5.length());
   }
}

private fun String.hexToShort(startIndex: Int = 0, endIndex: Int = var0.length(), format: HexFormat = HexFormat.Companion.getDefault()): Short {
   return (short)hexToIntImpl(var0, var1, var2, var3, 4);
}

public fun String.hexToShort(format: HexFormat = HexFormat.Companion.getDefault()): Short {
   return hexToShort(var0, 0, var0.length(), var1);
}

@JvmSynthetic
fun `hexToShort$default`(var0: java.lang.String, var1: Int, var2: Int, var3: HexFormat, var4: Int, var5: Any): Short {
   if ((var4 and 1) != 0) {
      var1 = 0;
   }

   if ((var4 and 2) != 0) {
      var2 = var0.length();
   }

   if ((var4 and 4) != 0) {
      var3 = HexFormat.Companion.getDefault();
   }

   return hexToShort(var0, var1, var2, var3);
}

@JvmSynthetic
fun `hexToShort$default`(var0: java.lang.String, var1: HexFormat, var2: Int, var3: Any): Short {
   if ((var2 and 1) != 0) {
      var1 = HexFormat.Companion.getDefault();
   }

   return hexToShort(var0, var1);
}

private inline fun String.longDecimalFromHexDigitAt(index: Int): Long {
   val var2: Char = var0.charAt(var1);
   if (var2 ushr 8 == 0) {
      val var3: Long = HEX_DIGITS_TO_LONG_DECIMAL[var2];
      if (HEX_DIGITS_TO_LONG_DECIMAL[var2] >= 0L) {
         return var3;
      }
   }

   throwInvalidDigitAt(var0, var1);
   throw new KotlinNothingValueException();
}

private fun String.parseByteAt(index: Int): Byte {
   var var2: Int = var0.charAt(var1);
   if (var2 ushr 8 == 0) {
      val var4: IntArray = HEX_DIGITS_TO_DECIMAL;
      var2 = HEX_DIGITS_TO_DECIMAL[var2];
      if (HEX_DIGITS_TO_DECIMAL[var2] >= 0) {
         var var3: Int = var0.charAt(++var1);
         if (var3 ushr 8 == 0) {
            var3 = var4[var3];
            if (var4[var3] >= 0) {
               return (byte)(var2 shl 4 or var3);
            }
         }

         throwInvalidDigitAt(var0, var1);
         throw new KotlinNothingValueException();
      }
   }

   throwInvalidDigitAt(var0, var1);
   throw new KotlinNothingValueException();
}

private fun String.parseInt(startIndex: Int, endIndex: Int): Int {
   var var3: Int = 0;

   while (true) {
      if (var1 >= var2) {
         return var3;
      }

      var var4: Int = var0.charAt(var1);
      if (var4 ushr 8 != 0) {
         break;
      }

      var4 = HEX_DIGITS_TO_DECIMAL[var4];
      if (HEX_DIGITS_TO_DECIMAL[var4] < 0) {
         break;
      }

      var3 = var3 shl 4 or var4;
      var1++;
   }

   throwInvalidDigitAt(var0, var1);
   throw new KotlinNothingValueException();
}

private fun String.parseLong(startIndex: Int, endIndex: Int): Long {
   var var4: Long = 0L;

   while (true) {
      if (var1 >= var2) {
         return var4;
      }

      val var3: Char = var0.charAt(var1);
      if (var3 ushr 8 != 0) {
         break;
      }

      val var6: Long = HEX_DIGITS_TO_LONG_DECIMAL[var3];
      if (HEX_DIGITS_TO_LONG_DECIMAL[var3] < 0L) {
         break;
      }

      var4 = var4 shl 4 or var6;
      var1++;
   }

   throwInvalidDigitAt(var0, var1);
   throw new KotlinNothingValueException();
}

internal fun parsedByteArrayMaxSize(
   stringLength: Int,
   bytesPerLine: Int,
   bytesPerGroup: Int,
   groupSeparatorLength: Int,
   byteSeparatorLength: Int,
   bytePrefixLength: Int,
   byteSuffixLength: Int
): Int {
   if (var0 > 0) {
      val var11: Long = var5 + 2L + var6;
      val var13: Long = charsPerSet((long)var5 + 2L + (long)var6, var2, var4);
      var var7: Long;
      if (var1 <= var2) {
         var7 = charsPerSet(var11, var1, var4);
      } else {
         val var9: Long = charsPerSet(var13, var1 / var2, var3);
         var5 = var1 % var2;
         var7 = var9;
         if (var5 != 0) {
            var7 = var9 + var3 + charsPerSet(var11, var5, var4);
         }
      }

      var var15: Long = var0;
      val var20: Long = wholeElementsPerSet((long)var0, var7, 1);
      val var22: Long = var0 - (var7 + 1L) * var20;
      var7 = wholeElementsPerSet(var15 - (var7 + 1L) * var20, var13, var3);
      val var21: Long = var22 - (var13 + var3) * var7;
      var15 = wholeElementsPerSet(var22 - (var13 + (long)var3) * var7, var11, var4);
      val var17: Byte;
      if (var21 - (var11 + var4) * var15 > 0L) {
         var17 = 1;
      } else {
         var17 = 0;
      }

      return (int)(var20 * var1 + var7 * var2 + var15 + var17);
   } else {
      throw new IllegalArgumentException("Failed requirement.".toString());
   }
}

private fun String.throwInvalidDigitAt(index: Int): Nothing {
   val var2: StringBuilder = new StringBuilder("Expected a hexadecimal digit at index ");
   var2.append(var1);
   var2.append(", but was ");
   var2.append(var0.charAt(var1));
   throw new NumberFormatException(var2.toString());
}

private fun String.throwInvalidNumberOfDigits(startIndex: Int, endIndex: Int, specifier: String, expected: Int) {
   val var5: java.lang.String = var0.substring(var1, var2);
   val var6: StringBuilder = new StringBuilder("Expected ");
   var6.append(var3);
   var6.append(' ');
   var6.append(var4);
   var6.append(" hexadecimal digits at index ");
   var6.append(var1);
   var6.append(", but was \"");
   var6.append(var5);
   var6.append("\" of length ");
   var6.append(var2 - var1);
   throw new NumberFormatException(var6.toString());
}

private fun String.throwInvalidPrefixSuffix(startIndex: Int, endIndex: Int, prefix: String, suffix: String) {
   val var5: java.lang.String = var0.substring(var1, var2);
   val var6: StringBuilder = new StringBuilder("Expected a hexadecimal number with prefix \"");
   var6.append(var3);
   var6.append("\" and suffix \"");
   var6.append(var4);
   var6.append("\", but was ");
   var6.append(var5);
   throw new NumberFormatException(var6.toString());
}

private fun String.throwNotContainedAt(index: Int, endIndex: Int, part: String, partName: String) {
   var2 = RangesKt.coerceAtMost(var3.length() + var1, var2);
   var0 = var0.substring(var1, var2);
   val var5: StringBuilder = new StringBuilder("Expected ");
   var5.append(var4);
   var5.append(" \"");
   var5.append(var3);
   var5.append("\" at index ");
   var5.append(var1);
   var5.append(", but was ");
   var5.append(var0);
   throw new NumberFormatException(var5.toString());
}

private fun String.toCharArrayIfNotEmpty(destination: CharArray, destinationOffset: Int): Int {
   var var3: Int = var0.length();
   if (var3 != 0) {
      if (var3 != 1) {
         var3 = var0.length();
         var0.getChars(0, var3, var1, var2);
      } else {
         var1[var2] = var0.charAt(0);
      }
   }

   return var2 + var0.length();
}

public fun Byte.toHexString(format: HexFormat = HexFormat.Companion.getDefault()): String {
   val var4: java.lang.String;
   if (var1.getUpperCase()) {
      var4 = "0123456789ABCDEF";
   } else {
      var4 = "0123456789abcdef";
   }

   val var5: HexFormat.NumberHexFormat = var1.getNumber();
   if (var5.isDigitsOnlyAndNoPadding$kotlin_stdlib()) {
      val var7: CharArray = new char[]{var4.charAt(var0 shr 4 and 15), var4.charAt(var0 and 15)};
      val var6: java.lang.String;
      if (var5.getRemoveLeadingZeros()) {
         var6 = StringsKt.concatToString$default(var7, RangesKt.coerceAtMost(Integer.numberOfLeadingZeros(var0 and 255) - 24 shr 2, 1), 0, 2, null);
      } else {
         var6 = StringsKt.concatToString(var7);
      }

      return var6;
   } else {
      return toHexStringImpl((long)var0, var5, var4, 8);
   }
}

public fun Int.toHexString(format: HexFormat = HexFormat.Companion.getDefault()): String {
   val var10: java.lang.String;
   if (var1.getUpperCase()) {
      var10 = "0123456789ABCDEF";
   } else {
      var10 = "0123456789abcdef";
   }

   val var11: HexFormat.NumberHexFormat = var1.getNumber();
   if (var11.isDigitsOnlyAndNoPadding$kotlin_stdlib()) {
      val var13: CharArray = new char[]{
         var10.charAt(var0 shr 28 and 15),
         var10.charAt(var0 shr 24 and 15),
         var10.charAt(var0 shr 20 and 15),
         var10.charAt(var0 shr 16 and 15),
         var10.charAt(var0 shr 12 and 15),
         var10.charAt(var0 shr 8 and 15),
         var10.charAt(var0 shr 4 and 15),
         var10.charAt(var0 and 15)
      };
      val var12: java.lang.String;
      if (var11.getRemoveLeadingZeros()) {
         var12 = StringsKt.concatToString$default(var13, RangesKt.coerceAtMost(Integer.numberOfLeadingZeros(var0) shr 2, 7), 0, 2, null);
      } else {
         var12 = StringsKt.concatToString(var13);
      }

      return var12;
   } else {
      return toHexStringImpl((long)var0, var11, var10, 32);
   }
}

public fun Long.toHexString(format: HexFormat = HexFormat.Companion.getDefault()): String {
   val var19: java.lang.String;
   if (var2.getUpperCase()) {
      var19 = "0123456789ABCDEF";
   } else {
      var19 = "0123456789abcdef";
   }

   val var20: HexFormat.NumberHexFormat = var2.getNumber();
   if (var20.isDigitsOnlyAndNoPadding$kotlin_stdlib()) {
      val var22: CharArray = new char[]{
         var19.charAt((int)(var0 shr 60 and 15L)),
         var19.charAt((int)(var0 shr 56 and 15L)),
         var19.charAt((int)(var0 shr 52 and 15L)),
         var19.charAt((int)(var0 shr 48 and 15L)),
         var19.charAt((int)(var0 shr 44 and 15L)),
         var19.charAt((int)(var0 shr 40 and 15L)),
         var19.charAt((int)(var0 shr 36 and 15L)),
         var19.charAt((int)(var0 shr 32 and 15L)),
         var19.charAt((int)(var0 shr 28 and 15L)),
         var19.charAt((int)(var0 shr 24 and 15L)),
         var19.charAt((int)(var0 shr 20 and 15L)),
         var19.charAt((int)(var0 shr 16 and 15L)),
         var19.charAt((int)(var0 shr 12 and 15L)),
         var19.charAt((int)(var0 shr 8 and 15L)),
         var19.charAt((int)(var0 shr 4 and 15L)),
         var19.charAt((int)(15L and var0))
      };
      val var21: java.lang.String;
      if (var20.getRemoveLeadingZeros()) {
         var21 = StringsKt.concatToString$default(var22, RangesKt.coerceAtMost(java.lang.Long.numberOfLeadingZeros(var0) shr 2, 15), 0, 2, null);
      } else {
         var21 = StringsKt.concatToString(var22);
      }

      return var21;
   } else {
      return toHexStringImpl(var0, var20, var19, 64);
   }
}

public fun Short.toHexString(format: HexFormat = HexFormat.Companion.getDefault()): String {
   val var6: java.lang.String;
   if (var1.getUpperCase()) {
      var6 = "0123456789ABCDEF";
   } else {
      var6 = "0123456789abcdef";
   }

   val var7: HexFormat.NumberHexFormat = var1.getNumber();
   if (var7.isDigitsOnlyAndNoPadding$kotlin_stdlib()) {
      val var9: CharArray = new char[]{
         var6.charAt(var0 shr 12 and 15), var6.charAt(var0 shr 8 and 15), var6.charAt(var0 shr 4 and 15), var6.charAt(var0 and 15)
      };
      val var8: java.lang.String;
      if (var7.getRemoveLeadingZeros()) {
         var8 = StringsKt.concatToString$default(var9, RangesKt.coerceAtMost(Integer.numberOfLeadingZeros(var0 and '\uffff') - 16 shr 2, 3), 0, 2, null);
      } else {
         var8 = StringsKt.concatToString(var9);
      }

      return var8;
   } else {
      return toHexStringImpl((long)var0, var7, var6, 16);
   }
}

public fun ByteArray.toHexString(startIndex: Int = 0, endIndex: Int = var0.length, format: HexFormat = HexFormat.Companion.getDefault()): String {
   AbstractList.Companion.checkBoundsIndexes$kotlin_stdlib(var1, var2, var0.length);
   if (var1 == var2) {
      return "";
   } else {
      val var4: IntArray;
      if (var3.getUpperCase()) {
         var4 = BYTE_TO_UPPER_CASE_HEX_DIGITS;
      } else {
         var4 = BYTE_TO_LOWER_CASE_HEX_DIGITS;
      }

      val var5: HexFormat.BytesHexFormat = var3.getBytes();
      return if (var5.getNoLineAndGroupSeparator$kotlin_stdlib())
         toHexStringNoLineAndGroupSeparator(var0, var1, var2, var5, var4)
         else
         toHexStringSlowPath(var0, var1, var2, var5, var4);
   }
}

public fun ByteArray.toHexString(format: HexFormat = HexFormat.Companion.getDefault()): String {
   return toHexString(var0, 0, var0.length, var1);
}

@JvmSynthetic
fun `toHexString$default`(var0: Byte, var1: HexFormat, var2: Int, var3: Any): java.lang.String {
   if ((var2 and 1) != 0) {
      var1 = HexFormat.Companion.getDefault();
   }

   return toHexString(var0, var1);
}

@JvmSynthetic
fun `toHexString$default`(var0: Int, var1: HexFormat, var2: Int, var3: Any): java.lang.String {
   if ((var2 and 1) != 0) {
      var1 = HexFormat.Companion.getDefault();
   }

   return toHexString(var0, var1);
}

@JvmSynthetic
fun `toHexString$default`(var0: Long, var2: HexFormat, var3: Int, var4: Any): java.lang.String {
   if ((var3 and 1) != 0) {
      var2 = HexFormat.Companion.getDefault();
   }

   return toHexString(var0, var2);
}

@JvmSynthetic
fun `toHexString$default`(var0: Short, var1: HexFormat, var2: Int, var3: Any): java.lang.String {
   if ((var2 and 1) != 0) {
      var1 = HexFormat.Companion.getDefault();
   }

   return toHexString(var0, var1);
}

@JvmSynthetic
fun `toHexString$default`(var0: ByteArray, var1: Int, var2: Int, var3: HexFormat, var4: Int, var5: Any): java.lang.String {
   if ((var4 and 1) != 0) {
      var1 = 0;
   }

   if ((var4 and 2) != 0) {
      var2 = var0.length;
   }

   if ((var4 and 4) != 0) {
      var3 = HexFormat.Companion.getDefault();
   }

   return toHexString(var0, var1, var2, var3);
}

@JvmSynthetic
fun `toHexString$default`(var0: ByteArray, var1: HexFormat, var2: Int, var3: Any): java.lang.String {
   if ((var2 and 1) != 0) {
      var1 = HexFormat.Companion.getDefault();
   }

   return toHexString(var0, var1);
}

private fun Long.toHexStringImpl(numberFormat: NumberHexFormat, digits: String, bits: Int): String {
   if ((var4 and 3) != 0) {
      throw new IllegalArgumentException("Failed requirement.".toString());
   } else {
      val var11: Int = var4 shr 2;
      val var10: Int = var2.getMinLength();
      var var8: Int = RangesKt.coerceAtLeast(var10 - var11, 0);
      val var14: java.lang.String = var2.getPrefix();
      val var15: java.lang.String = var2.getSuffix();
      var var13: Boolean = var2.getRemoveLeadingZeros();
      val var9: Int = checkFormatLength((long)var14.length() + (long)var8 + (long)var11 + (long)var15.length());
      val var16: CharArray = new char[var9];
      var var7: Int = toCharArrayIfNotEmpty(var14, var16, 0);
      var var6: Int = var7;
      if (var8 > 0) {
         val var5: Char = var3.charAt(0);
         var6 = var8 + var7;
         ArraysKt.fill(var16, var5, var7, var8 + var7);
      }

      var8 = var4;
      var4 = 0;

      while (var4 < var11) {
         var8 -= 4;
         val var12: Int = (int)(var0 shr var8 and 15L);
         if (var13 && (int)(var0 shr var8 and 15L) == 0 && var8 shr 2 >= var10) {
            var13 = true;
         } else {
            var13 = false;
         }

         var7 = var6;
         if (!var13) {
            var16[var6] = var3.charAt(var12);
            var7 = var6 + 1;
         }

         var4++;
         var6 = var7;
      }

      var4 = toCharArrayIfNotEmpty(var15, var16, var6);
      val var17: java.lang.String;
      if (var4 == var9) {
         var17 = StringsKt.concatToString(var16);
      } else {
         var17 = StringsKt.concatToString$default(var16, 0, var4, 1, null);
      }

      return var17;
   }
}

private fun ByteArray.toHexStringNoLineAndGroupSeparator(startIndex: Int, endIndex: Int, bytesFormat: BytesHexFormat, byteToDigits: IntArray): String {
   return if (var3.getShortByteSeparatorNoPrefixAndSuffix$kotlin_stdlib())
      toHexStringShortByteSeparatorNoPrefixAndSuffix(var0, var1, var2, var3, var4)
      else
      toHexStringNoLineAndGroupSeparatorSlowPath(var0, var1, var2, var3, var4);
}

private fun ByteArray.toHexStringNoLineAndGroupSeparatorSlowPath(startIndex: Int, endIndex: Int, bytesFormat: BytesHexFormat, byteToDigits: IntArray): String {
   val var7: java.lang.String = var3.getBytePrefix();
   val var8: java.lang.String = var3.getByteSuffix();
   val var9: java.lang.String = var3.getByteSeparator();
   val var11: CharArray = new char[formattedStringLength(var2 - var1, var9.length(), var7.length(), var8.length())];
   val var6: Int = formatByteAt(var0, var1, var7, var8, var4, var11, 0);
   val var5: Int = var1;
   var1 = var6;

   while (++var5 < var2) {
      var1 = formatByteAt(var0, var5, var7, var8, var4, var11, toCharArrayIfNotEmpty(var9, var11, var1));
   }

   return StringsKt.concatToString(var11);
}

private fun ByteArray.toHexStringShortByteSeparatorNoPrefixAndSuffix(startIndex: Int, endIndex: Int, bytesFormat: BytesHexFormat, byteToDigits: IntArray): String {
   val var8: Int = var3.getByteSeparator().length();
   if (var8 > 1) {
      throw new IllegalArgumentException("Failed requirement.".toString());
   } else {
      val var7: Int = var2 - var1;
      var var6: Int = 0;
      if (var8 == 0) {
         for (var11 = new char[checkFormatLength(var7 * 2L)]; var1 < var2; var1++) {
            var6 = formatByteAt(var0, var1, var4, var11, var6);
         }

         return StringsKt.concatToString(var11);
      } else {
         val var9: CharArray = new char[checkFormatLength((long)var7 * 3L - 1L)];
         val var5: Char = var3.getByteSeparator().charAt(0);
         var6 = formatByteAt(var0, var1, var4, var9, 0);
         var1++;

         while (var1 < var2) {
            var9[var6] = var5;
            var6 = formatByteAt(var0, var1, var4, var9, var6 + 1);
            var1++;
         }

         return StringsKt.concatToString(var9);
      }
   }
}

private fun ByteArray.toHexStringSlowPath(startIndex: Int, endIndex: Int, bytesFormat: BytesHexFormat, byteToDigits: IntArray): String {
   val var10: Int = var3.getBytesPerLine();
   val var11: Int = var3.getBytesPerGroup();
   val var14: java.lang.String = var3.getBytePrefix();
   val var13: java.lang.String = var3.getByteSuffix();
   val var12: java.lang.String = var3.getByteSeparator();
   val var15: java.lang.String = var3.getGroupSeparator();
   val var9: Int = formattedStringLength(var2 - var1, var10, var11, var15.length(), var12.length(), var14.length(), var13.length());
   val var17: CharArray = new char[var9];
   var var5: Int = 0;
   var var6: Int = 0;
   var var7: Int = var1;

   for (int var16 = 0; var7 < var2; var7++) {
      label27: {
         if (var16 == var10) {
            var17[var5] = '\n';
            var5++;
            var16 = 0;
         } else {
            if (var6 != var11) {
               break label27;
            }

            var5 = toCharArrayIfNotEmpty(var15, var17, var5);
         }

         var6 = 0;
      }

      var var18: Int = var5;
      if (var6 != 0) {
         var18 = toCharArrayIfNotEmpty(var12, var17, var5);
      }

      var5 = formatByteAt(var0, var7, var14, var13, var4, var17, var18);
      var6++;
      var16++;
   }

   if (var5 == var9) {
      return StringsKt.concatToString(var17);
   } else {
      throw new IllegalStateException("Check failed.");
   }
}

private fun wholeElementsPerSet(charsPerSet: Long, charsPerElement: Long, elementSeparatorLength: Int): Long {
   var var5: Long = 0L;
   if (var0 > 0L) {
      if (var2 <= 0L) {
         var5 = 0L;
      } else {
         var5 = (var0 + var4) / (var2 + var4);
      }
   }

   return var5;
}
