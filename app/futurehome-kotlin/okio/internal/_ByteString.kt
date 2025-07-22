package okio.internal

import java.util.Arrays
import okio.Buffer
import okio.ByteString
import okio._Base64
import okio._JvmPlatformKt

internal final val HEX_DIGIT_CHARS: CharArray = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'}

@JvmSynthetic
fun `access$codePointIndexToCharIndex`(var0: ByteArray, var1: Int): Int {
   return codePointIndexToCharIndex(var0, var1);
}

@JvmSynthetic
fun `access$decodeHexDigit`(var0: Char): Int {
   return decodeHexDigit(var0);
}

private fun codePointIndexToCharIndex(s: ByteArray, codePointCount: Int): Int {
   val var8: Int = var0.length;
   var var2: Int = 0;
   var var3: Int = 0;
   var var4: Int = 0;

   label316:
   while (var2 < var8) {
      var var6: Int = var0[var2];
      if (var0[var2] >= 0) {
         var var25: Int = var4 + 1;
         if (var4 == var1) {
            return var3;
         }

         if ((var6 == 10 || var6 == 13 || (var6 < 0 || var6 >= 32) && (127 > var6 || var6 >= 160)) && var6 != 65533) {
            val var17: Byte;
            if (var6 < 65536) {
               var17 = 1;
            } else {
               var17 = 2;
            }

            var3 = var3 + var17;
            var6 = var2 + 1;
            var2 = var25;
            var25 = var3;

            while (true) {
               var2 = var6;
               var3 = var25;
               var4 = var2;
               if (var6 >= var8) {
                  continue label316;
               }

               val var34: Byte = var0[var6];
               var2 = var6;
               var3 = var25;
               var4 = var2;
               if (var34 < 0) {
                  continue label316;
               }

               var6++;
               var3 = var2 + 1;
               if (var2 == var1) {
                  return var25;
               }

               if (var34 != 10 && var34 != 13 && (var34 >= 0 && var34 < 32 || 127 <= var34 && var34 < 160) || var34 == '�') {
                  return -1;
               }

               val var12: Byte;
               if (var34 < 65536) {
                  var12 = 1;
               } else {
                  var12 = 2;
               }

               var25 += var12;
               var2 = var3;
            }
         }

         return -1;
      } else if (var6 shr 5 == -2) {
         val var21: Int = var2 + 1;
         if (var8 <= var2 + 1) {
            if (var4 == var1) {
               return var3;
            }

            return -1;
         }

         var var22: Int = var0[var21];
         if ((var0[var21] and 192) != 128) {
            if (var4 == var1) {
               return var3;
            }

            return -1;
         }

         var22 = var6 shl 6 xor var22 xor 3968;
         if ((var6 shl 6 xor var22 xor 3968) < 128) {
            if (var4 == var1) {
               return var3;
            }

            return -1;
         }

         if (var4 == var1) {
            return var3;
         }

         if (var22 != 10 && var22 != 13 && (var22 >= 0 && var22 < 32 || 127 <= var22 && var22 < 160) || var22 == 65533) {
            return -1;
         }

         val var24: Byte;
         if (var22 < 65536) {
            var24 = 1;
         } else {
            var24 = 2;
         }

         var3 += var24;
         var2 += 2;
         var4++;
      } else if (var6 shr 4 == -2) {
         val var30: Int = var2 + 2;
         if (var8 <= var2 + 2) {
            if (var4 == var1) {
               return var3;
            }

            return -1;
         }

         var var19: Int = var0[var2 + 1];
         if ((var0[var2 + 1] and 192) != 128) {
            if (var4 == var1) {
               return var3;
            }

            return -1;
         }

         val var31: Byte = var0[var30];
         if ((var0[var30] and 192) == 128) {
            var6 = var6 shl 12 xor var31 xor -123008 xor var19 shl 6;
            if ((var6 shl 12 xor var31 xor -123008 xor var19 shl 6) < 2048) {
               if (var4 == var1) {
                  return var3;
               }

               return -1;
            }

            if (55296 <= var6 && var6 < 57344) {
               if (var4 == var1) {
                  return var3;
               }

               return -1;
            }

            var19 = var4 + 1;
            if (var4 == var1) {
               return var3;
            }

            if ((var6 == 10 || var6 == 13 || (var6 < 0 || var6 >= 32) && (127 > var6 || var6 >= 160)) && var6 != 65533) {
               val var16: Byte;
               if (var6 < 65536) {
                  var16 = 1;
               } else {
                  var16 = 2;
               }

               var3 += var16;
               var2 += 3;
               var4 = var19;
               continue;
            }

            return -1;
         }

         if (var4 == var1) {
            return var3;
         }

         return -1;
      } else {
         if (var6 shr 3 != -2) {
            if (var4 == var1) {
               return var3;
            }

            return -1;
         }

         val var9: Int = var2 + 3;
         if (var8 <= var2 + 3) {
            if (var4 == var1) {
               return var3;
            }

            return -1;
         }

         var var5: Int = var0[var2 + 1];
         if ((var0[var2 + 1] and 192) != 128) {
            if (var4 == var1) {
               return var3;
            }

            return -1;
         }

         val var7: Byte = var0[var2 + 2];
         if ((var0[var2 + 2] and 192) != 128) {
            if (var4 == var1) {
               return var3;
            }

            return -1;
         }

         val var33: Byte = var0[var9];
         if ((var0[var9] and 192) != 128) {
            if (var4 == var1) {
               return var3;
            }

            return -1;
         }

         var6 = var6 shl 18 xor var33 xor 3678080 xor var7 shl 6 xor var5 shl 12;
         if ((var6 shl 18 xor var33 xor 3678080 xor var7 shl 6 xor var5 shl 12) > 1114111) {
            if (var4 == var1) {
               return var3;
            }

            return -1;
         }

         if (55296 <= var6 && var6 < 57344) {
            if (var4 == var1) {
               return var3;
            }

            return -1;
         }

         if (var6 < 65536) {
            if (var4 == var1) {
               return var3;
            }

            return -1;
         }

         var5 = var4 + 1;
         if (var4 == var1) {
            return var3;
         }

         if (var6 != 10 && var6 != 13 && (var6 >= 0 && var6 < 32 || 127 <= var6 && var6 < 160) || var6 == 65533) {
            return -1;
         }

         val var15: Byte;
         if (var6 < 65536) {
            var15 = 1;
         } else {
            var15 = 2;
         }

         var3 += var15;
         var2 += 4;
         var4 = var5;
      }
   }

   return var3;
}

internal inline fun ByteString.commonBase64(): String {
   return _Base64.encodeBase64$default(var0.getData$okio(), null, 1, null);
}

internal inline fun ByteString.commonBase64Url(): String {
   return _Base64.encodeBase64(var0.getData$okio(), _Base64.getBASE64_URL_SAFE());
}

internal inline fun ByteString.commonCompareTo(other: ByteString): Int {
   val var5: Int = var0.size();
   val var4: Int = var1.size();
   val var6: Int = Math.min(var5, var4);
   var var2: Int = 0;

   while (true) {
      var var3: Byte = -1;
      if (var2 >= var6) {
         if (var5 == var4) {
            return 0;
         }

         if (var5 >= var4) {
            var3 = 1;
         }

         return var3;
      }

      val var7: Int = var0.getByte(var2) and 255;
      val var8: Int = var1.getByte(var2) and 255;
      if (var7 != var8) {
         if (var7 >= var8) {
            var3 = 1;
         }

         return var3;
      }

      var2++;
   }
}

internal inline fun ByteString.commonCopyInto(offset: Int, target: ByteArray, targetOffset: Int, byteCount: Int) {
   ArraysKt.copyInto(var0.getData$okio(), var2, var3, var1, var4 + var1);
}

internal inline fun String.commonDecodeBase64(): ByteString? {
   val var1: ByteArray = _Base64.decodeBase64ToArray(var0);
   val var2: ByteString;
   if (var1 != null) {
      var2 = new ByteString(var1);
   } else {
      var2 = null;
   }

   return var2;
}

internal inline fun String.commonDecodeHex(): ByteString {
   if (var0.length() % 2 != 0) {
      val var5: StringBuilder = new StringBuilder("Unexpected hex string: ");
      var5.append(var0);
      throw new IllegalArgumentException(var5.toString().toString());
   } else {
      val var2: Int = var0.length() / 2;
      val var4: ByteArray = new byte[var2];

      for (int var1 = 0; var1 < var2; var1++) {
         var4[var1] = (byte)((access$decodeHexDigit(var0.charAt(var1 * 2)) shl 4) + access$decodeHexDigit(var0.charAt(var1 * 2 + 1)));
      }

      return new ByteString(var4);
   }
}

internal inline fun String.commonEncodeUtf8(): ByteString {
   val var1: ByteString = new ByteString(_JvmPlatformKt.asUtf8ToByteArray(var0));
   var1.setUtf8$okio(var0);
   return var1;
}

internal inline fun ByteString.commonEndsWith(suffix: ByteString): Boolean {
   return var0.rangeEquals(var0.size() - var1.size(), var1, 0, var1.size());
}

internal inline fun ByteString.commonEndsWith(suffix: ByteArray): Boolean {
   return var0.rangeEquals(var0.size() - var1.length, var1, 0, var1.length);
}

internal inline fun ByteString.commonEquals(other: Any?): Boolean {
   var var2: Boolean = true;
   if (var1 != var0) {
      if (var1 is ByteString
         && (var1 as ByteString).size() == var0.getData$okio().length
         && (var1 as ByteString).rangeEquals(0, var0.getData$okio(), 0, var0.getData$okio().length)) {
         return true;
      }

      var2 = false;
   }

   return var2;
}

internal inline fun ByteString.commonGetByte(pos: Int): Byte {
   return var0.getData$okio()[var1];
}

internal inline fun ByteString.commonGetSize(): Int {
   return var0.getData$okio().length;
}

internal inline fun ByteString.commonHashCode(): Int {
   var var1: Int = var0.getHashCode$okio();
   if (var1 != 0) {
      return var1;
   } else {
      var1 = Arrays.hashCode(var0.getData$okio());
      var0.setHashCode$okio(var1);
      return var1;
   }
}

internal inline fun ByteString.commonHex(): String {
   val var6: CharArray = new char[var0.getData$okio().length * 2];
   val var7: ByteArray = var0.getData$okio();
   val var4: Int = var7.length;
   var var2: Int = 0;
   var var1: Int = 0;

   while (true) {
      val var3: Int = var1;
      if (var2 >= var4) {
         return StringsKt.concatToString(var6);
      }

      val var5: Byte = var7[var2];
      var6[var1] = getHEX_DIGIT_CHARS()[var7[var2] shr 4 and 15];
      var1 += 2;
      var6[var3 + 1] = getHEX_DIGIT_CHARS()[var5 and 15];
      var2++;
   }
}

internal inline fun ByteString.commonIndexOf(other: ByteArray, fromIndex: Int): Int {
   val var3: Int = var0.getData$okio().length - var1.length;
   var2 = Math.max(var2, 0);
   if (var2 <= var3) {
      while (true) {
         if (okio._SegmentedByteString.arrayRangeEquals(var0.getData$okio(), var2, var1, 0, var1.length)) {
            return var2;
         }

         if (var2 == var3) {
            break;
         }

         var2++;
      }
   }

   return -1;
}

internal inline fun ByteString.commonInternalArray(): ByteArray {
   return var0.getData$okio();
}

internal inline fun ByteString.commonLastIndexOf(other: ByteString, fromIndex: Int): Int {
   return var0.lastIndexOf(var1.internalArray$okio(), var2);
}

internal inline fun ByteString.commonLastIndexOf(other: ByteArray, fromIndex: Int): Int {
   for (int var3 = Math.min(okio._SegmentedByteString.resolveDefaultParameter(var0, var2), var0.getData$okio().length - var1.length); -1 < var3; var3--) {
      if (okio._SegmentedByteString.arrayRangeEquals(var0.getData$okio(), var3, var1, 0, var1.length)) {
         return var3;
      }
   }

   return -1;
}

internal inline fun commonOf(data: ByteArray): ByteString {
   var0 = Arrays.copyOf(var0, var0.length);
   return new ByteString(var0);
}

internal inline fun ByteString.commonRangeEquals(offset: Int, other: ByteString, otherOffset: Int, byteCount: Int): Boolean {
   return var2.rangeEquals(var3, var0.getData$okio(), var1, var4);
}

internal inline fun ByteString.commonRangeEquals(offset: Int, other: ByteArray, otherOffset: Int, byteCount: Int): Boolean {
   val var5: Boolean;
   if (var1 >= 0
      && var1 <= var0.getData$okio().length - var4
      && var3 >= 0
      && var3 <= var2.length - var4
      && okio._SegmentedByteString.arrayRangeEquals(var0.getData$okio(), var1, var2, var3, var4)) {
      var5 = true;
   } else {
      var5 = false;
   }

   return var5;
}

internal inline fun ByteString.commonStartsWith(prefix: ByteString): Boolean {
   return var0.rangeEquals(0, var1, 0, var1.size());
}

internal inline fun ByteString.commonStartsWith(prefix: ByteArray): Boolean {
   return var0.rangeEquals(0, var1, 0, var1.length);
}

internal inline fun ByteString.commonSubstring(beginIndex: Int, endIndex: Int): ByteString {
   var2 = okio._SegmentedByteString.resolveDefaultParameter(var0, var2);
   if (var1 >= 0) {
      if (var2 <= var0.getData$okio().length) {
         if (var2 - var1 >= 0) {
            return if (var1 == 0 && var2 == var0.getData$okio().length) var0 else new ByteString(ArraysKt.copyOfRange(var0.getData$okio(), var1, var2));
         } else {
            throw new IllegalArgumentException("endIndex < beginIndex".toString());
         }
      } else {
         val var3: StringBuilder = new StringBuilder("endIndex > length(");
         var3.append(var0.getData$okio().length);
         var3.append(')');
         throw new IllegalArgumentException(var3.toString().toString());
      }
   } else {
      throw new IllegalArgumentException("beginIndex < 0".toString());
   }
}

internal inline fun ByteString.commonToAsciiLowercase(): ByteString {
   for (int var1 = 0; var1 < var0.getData$okio().length; var1++) {
      val var3: Byte = var0.getData$okio()[var1];
      if (var3 >= 65 && var3 <= 90) {
         val var4: ByteArray = var0.getData$okio();
         val var5: ByteArray = Arrays.copyOf(var4, var4.length);
         val var2: Int = var1 + 1;
         var5[var1] = (byte)(var3 + 32);

         for (int var6 = var2; var6 < var5.length; var6++) {
            val var7: Byte = var5[var6];
            if (var5[var6] >= 65 && var5[var6] <= 90) {
               var5[var6] = (byte)(var7 + 32);
            }
         }

         return new ByteString(var5);
      }
   }

   return var0;
}

internal inline fun ByteString.commonToAsciiUppercase(): ByteString {
   for (int var1 = 0; var1 < var0.getData$okio().length; var1++) {
      val var3: Byte = var0.getData$okio()[var1];
      if (var3 >= 97 && var3 <= 122) {
         val var4: ByteArray = var0.getData$okio();
         val var5: ByteArray = Arrays.copyOf(var4, var4.length);
         val var2: Int = var1 + 1;
         var5[var1] = (byte)(var3 - 32);

         for (int var6 = var2; var6 < var5.length; var6++) {
            val var7: Byte = var5[var6];
            if (var5[var6] >= 97 && var5[var6] <= 122) {
               var5[var6] = (byte)(var7 - 32);
            }
         }

         return new ByteString(var5);
      }
   }

   return var0;
}

internal inline fun ByteString.commonToByteArray(): ByteArray {
   val var1: ByteArray = var0.getData$okio();
   val var2: ByteArray = Arrays.copyOf(var1, var1.length);
   return var2;
}

internal inline fun ByteArray.commonToByteString(offset: Int, byteCount: Int): ByteString {
   var2 = okio._SegmentedByteString.resolveDefaultParameter(var0, var2);
   okio._SegmentedByteString.checkOffsetAndCount((long)var0.length, (long)var1, (long)var2);
   return new ByteString(ArraysKt.copyOfRange(var0, var1, var2 + var1));
}

internal inline fun ByteString.commonToString(): String {
   var var2: ByteString = var0;
   if (var0.getData$okio().length == 0) {
      return "[size=0]";
   } else {
      var var1: Int = access$codePointIndexToCharIndex(var0.getData$okio(), 64);
      if (var1 == -1) {
         val var6: java.lang.String;
         if (var0.getData$okio().length <= 64) {
            val var10: StringBuilder = new StringBuilder("[hex=");
            var10.append(var0.hex());
            var10.append(']');
            var6 = var10.toString();
         } else {
            val var13: StringBuilder = new StringBuilder("[size=");
            var13.append(var0.getData$okio().length);
            var13.append(" hex=");
            var1 = okio._SegmentedByteString.resolveDefaultParameter(var0, 64);
            if (var1 > var0.getData$okio().length) {
               val var11: StringBuilder = new StringBuilder("endIndex > length(");
               var11.append(var0.getData$okio().length);
               var11.append(')');
               throw new IllegalArgumentException(var11.toString().toString());
            }

            if (var1 < 0) {
               throw new IllegalArgumentException("endIndex < beginIndex".toString());
            }

            if (var1 != var0.getData$okio().length) {
               var2 = new ByteString(ArraysKt.copyOfRange(var0.getData$okio(), 0, var1));
            }

            var13.append(var2.hex());
            var13.append("…]");
            var6 = var13.toString();
         }

         return var6;
      } else {
         val var3: java.lang.String = var0.utf8();
         val var8: java.lang.String = var3.substring(0, var1);
         val var9: java.lang.String = StringsKt.replace$default(
            StringsKt.replace$default(StringsKt.replace$default(var8, "\\", "\\\\", false, 4, null), "\n", "\\n", false, 4, null), "\r", "\\r", false, 4, null
         );
         val var4: java.lang.String;
         if (var1 < var3.length()) {
            val var12: StringBuilder = new StringBuilder("[size=");
            var12.append(var0.getData$okio().length);
            var12.append(" text=");
            var12.append(var9);
            var12.append("…]");
            var4 = var12.toString();
         } else {
            val var5: StringBuilder = new StringBuilder("[text=");
            var5.append(var9);
            var5.append(']');
            var4 = var5.toString();
         }

         return var4;
      }
   }
}

internal inline fun ByteString.commonUtf8(): String {
   val var2: java.lang.String = var0.getUtf8$okio();
   var var1: java.lang.String = var2;
   if (var2 == null) {
      var1 = _JvmPlatformKt.toUtf8String(var0.internalArray$okio());
      var0.setUtf8$okio(var1);
   }

   return var1;
}

internal fun ByteString.commonWrite(buffer: Buffer, offset: Int, byteCount: Int) {
   var1.write(var0.getData$okio(), var2, var3);
}

private fun decodeHexDigit(c: Char): Int {
   val var1: Int;
   if ('0' <= var0 && var0 < ':') {
      var1 = var0 - '0';
   } else if ('a' <= var0 && var0 < 'g') {
      var1 = var0 - 'W';
   } else {
      if ('A' > var0 || var0 >= 'G') {
         val var2: StringBuilder = new StringBuilder("Unexpected hex digit: ");
         var2.append(var0);
         throw new IllegalArgumentException(var2.toString());
      }

      var1 = var0 - '7';
   }

   return var1;
}
