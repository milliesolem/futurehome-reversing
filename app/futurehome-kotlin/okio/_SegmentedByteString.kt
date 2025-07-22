package okio

import okio.Buffer.UnsafeCursor
import okio.internal._ByteString

internal final val DEFAULT__ByteString_size: Int = -1234567890
internal final val DEFAULT__new_UnsafeCursor: UnsafeCursor = new Buffer.UnsafeCursor()

internal inline infix fun Byte.and(other: Int): Int {
   return var0 and var1;
}

internal inline infix fun Byte.and(other: Long): Long {
   return var0 and var1;
}

internal inline infix fun Int.and(other: Long): Long {
   return var0 and var1;
}

internal fun arrayRangeEquals(a: ByteArray, aOffset: Int, b: ByteArray, bOffset: Int, byteCount: Int): Boolean {
   for (int var5 = 0; var5 < var4; var5++) {
      if (var0[var5 + var1] != var2[var5 + var3]) {
         return false;
      }
   }

   return true;
}

internal fun checkOffsetAndCount(size: Long, offset: Long, byteCount: Long) {
   if ((var2 or var4) < 0L || var2 > var0 || var0 - var2 < var4) {
      val var6: StringBuilder = new StringBuilder("size=");
      var6.append(var0);
      var6.append(" offset=");
      var6.append(var2);
      var6.append(" byteCount=");
      var6.append(var4);
      throw new ArrayIndexOutOfBoundsException(var6.toString());
   }
}

internal inline infix fun Int.leftRotate(bitCount: Int): Int {
   return var0 ushr 32 - var1 or var0 shl var1;
}

internal inline fun minOf(a: Int, b: Long): Long {
   return Math.min((long)var0, var1);
}

internal inline fun minOf(a: Long, b: Int): Long {
   return Math.min(var0, (long)var2);
}

internal fun ByteString.resolveDefaultParameter(position: Int): Int {
   return if (var1 == DEFAULT__ByteString_size) var0.size() else var1;
}

internal fun ByteArray.resolveDefaultParameter(sizeParam: Int): Int {
   return if (var1 == DEFAULT__ByteString_size) var0.length else var1;
}

internal fun resolveDefaultParameter(unsafeCursor: UnsafeCursor): UnsafeCursor {
   var var1: Buffer.UnsafeCursor = var0;
   if (var0 === DEFAULT__new_UnsafeCursor) {
      var1 = new Buffer.UnsafeCursor();
   }

   return var1;
}

internal fun Int.reverseBytes(): Int {
   return (var0 and 255) shl 24 or (-16777216 and var0) ushr 24 or (16711680 and var0) ushr 8 or (65280 and var0) shl 8;
}

internal fun Long.reverseBytes(): Long {
   return (var0 and 255L) shl 56 or (-72057594037927936L and var0) ushr 56 or (71776119061217280L and var0) ushr 40 or (280375465082880L and var0) ushr 24 or (
      1095216660480L and var0
   ) ushr 8 or (4278190080L and var0) shl 8 or (16711680L and var0) shl 24 or (65280L and var0) shl 40;
}

internal fun Short.reverseBytes(): Short {
   return (short)((var0 and 255) shl 8 or ('\uff00' and var0) ushr 8);
}

internal inline infix fun Long.rightRotate(bitCount: Int): Long {
   return var0 shl 64 - var2 or var0 ushr var2;
}

internal inline infix fun Byte.shl(other: Int): Int {
   return var0 shl var1;
}

internal inline infix fun Byte.shr(other: Int): Int {
   return var0 shr var1;
}

internal fun Byte.toHexString(): String {
   return StringsKt.concatToString(new char[]{_ByteString.getHEX_DIGIT_CHARS()[var0 shr 4 and 15], _ByteString.getHEX_DIGIT_CHARS()[var0 and 15]});
}

internal fun Int.toHexString(): String {
   if (var0 == 0) {
      return "0";
   } else {
      val var3: Char = _ByteString.getHEX_DIGIT_CHARS()[var0 shr 28 and 15];
      val var5: Char = _ByteString.getHEX_DIGIT_CHARS()[var0 shr 24 and 15];
      val var1: Char = _ByteString.getHEX_DIGIT_CHARS()[var0 shr 20 and 15];
      val var6: Char = _ByteString.getHEX_DIGIT_CHARS()[var0 shr 16 and 15];
      val var2: Char = _ByteString.getHEX_DIGIT_CHARS()[var0 shr 12 and 15];
      val var8: Char = _ByteString.getHEX_DIGIT_CHARS()[var0 shr 8 and 15];
      val var7: Char = _ByteString.getHEX_DIGIT_CHARS()[var0 shr 4 and 15];
      val var4: Char = _ByteString.getHEX_DIGIT_CHARS()[var0 and 15];
      val var9: CharArray = new char[8];
      var0 = 0;
      var9[0] = var3;
      var9[1] = var5;
      var9[2] = var1;
      var9[3] = var6;
      var9[4] = var2;
      var9[5] = var8;
      var9[6] = var7;
      var9[7] = var4;

      while (var0 < 8 && var9[var0] == '0') {
         var0++;
      }

      return StringsKt.concatToString(var9, var0, 8);
   }
}

internal fun Long.toHexString(): String {
   if (var0 == 0L) {
      return "0";
   } else {
      val var17: Char = _ByteString.getHEX_DIGIT_CHARS()[(int)(var0 shr 60 and 15L)];
      val var13: Char = _ByteString.getHEX_DIGIT_CHARS()[(int)(var0 shr 56 and 15L)];
      val var9: Char = _ByteString.getHEX_DIGIT_CHARS()[(int)(var0 shr 52 and 15L)];
      val var5: Char = _ByteString.getHEX_DIGIT_CHARS()[(int)(var0 shr 48 and 15L)];
      val var10: Char = _ByteString.getHEX_DIGIT_CHARS()[(int)(var0 shr 44 and 15L)];
      val var6: Char = _ByteString.getHEX_DIGIT_CHARS()[(int)(var0 shr 40 and 15L)];
      val var8: Char = _ByteString.getHEX_DIGIT_CHARS()[(int)(var0 shr 36 and 15L)];
      val var16: Char = _ByteString.getHEX_DIGIT_CHARS()[(int)(var0 shr 32 and 15L)];
      val var12: Char = _ByteString.getHEX_DIGIT_CHARS()[(int)(var0 shr 28 and 15L)];
      val var4: Char = _ByteString.getHEX_DIGIT_CHARS()[(int)(var0 shr 24 and 15L)];
      val var2: Char = _ByteString.getHEX_DIGIT_CHARS()[(int)(var0 shr 20 and 15L)];
      val var15: Char = _ByteString.getHEX_DIGIT_CHARS()[(int)(var0 shr 16 and 15L)];
      val var11: Char = _ByteString.getHEX_DIGIT_CHARS()[(int)(var0 shr 12 and 15L)];
      val var7: Char = _ByteString.getHEX_DIGIT_CHARS()[(int)(var0 shr 8 and 15L)];
      val var3: Char = _ByteString.getHEX_DIGIT_CHARS()[(int)(var0 shr 4 and 15L)];
      val var14: Char = _ByteString.getHEX_DIGIT_CHARS()[(int)(var0 and 15L)];
      val var19: CharArray = new char[16];
      var var18: Int = 0;
      var19[0] = var17;
      var19[1] = var13;
      var19[2] = var9;
      var19[3] = var5;
      var19[4] = var10;
      var19[5] = var6;
      var19[6] = var8;
      var19[7] = var16;
      var19[8] = var12;
      var19[9] = var4;
      var19[10] = var2;
      var19[11] = var15;
      var19[12] = var11;
      var19[13] = var7;
      var19[14] = var3;
      var19[15] = var14;

      while (var18 < 16 && var19[var18] == '0') {
         var18++;
      }

      return StringsKt.concatToString(var19, var18, 16);
   }
}

internal inline infix fun Byte.xor(other: Byte): Byte {
   return (byte)(var0 xor var1);
}
