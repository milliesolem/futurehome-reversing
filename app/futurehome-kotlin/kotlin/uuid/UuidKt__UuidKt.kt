package kotlin.uuid

internal class UuidKt__UuidKt : UuidKt__UuidJVMKt {
   open fun UuidKt__UuidKt() {
   }

   @JvmStatic
   private fun String.checkHyphenAt(index: Int) {
      if (var0.charAt(var1) != '-') {
         val var2: StringBuilder = new StringBuilder("Expected '-' (hyphen) at index ");
         var2.append(var1);
         var2.append(", but was '");
         var2.append(var0.charAt(var1));
         var2.append('\'');
         throw new IllegalArgumentException(var2.toString().toString());
      }
   }

   @JvmStatic
   private fun Long.formatBytesInto(dst: ByteArray, dstOffset: Int, count: Int) {
      var var5: Int = var3 + var4 * 2;

      for (int var7 = 0; var7 < var4; var7++) {
         val var8: Int = HexExtensionsKt.getBYTE_TO_LOWER_CASE_HEX_DIGITS()[(int)(255L and var0)];
         var2[var5 - 1] = (byte)var8;
         var5 -= 2;
         var2[var5] = (byte)(var8 shr 8);
         var0 >>= 8;
      }
   }

   @JvmStatic
   private fun Long.toByteArray(dst: ByteArray, dstOffset: Int) {
      for (int var4 = 0; var4 < 8; var4++) {
         var2[var3 + var4] = (byte)(var0 ushr (7 - var4) * 8);
      }
   }

   @JvmStatic
   private fun ByteArray.toLong(startIndex: Int): Long {
      return var0[var1 + 7] and 255L or (var0[var1] and 255L) shl 56 or (var0[var1 + 1] and 255L) shl 48 or (var0[var1 + 2] and 255L) shl 40 or (
         var0[var1 + 3] and 255L
      ) shl 32 or (var0[var1 + 4] and 255L) shl 24 or (var0[var1 + 5] and 255L) shl 16 or (var0[var1 + 6] and 255L) shl 8;
   }

   @JvmStatic
   internal fun uuidFromRandomBytes(randomBytes: ByteArray): Uuid {
      var0[6] = (byte)(var0[6] and 15);
      var0[6] = (byte)((byte)(var0[6] and 15) or 64);
      var0[8] = (byte)(var0[8] and 63);
      var0[8] = (byte)((byte)(var0[8] and 63) or 128);
      return Uuid.Companion.fromByteArray(var0);
   }
}
