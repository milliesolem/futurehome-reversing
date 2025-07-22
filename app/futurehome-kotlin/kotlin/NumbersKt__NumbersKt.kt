package kotlin

internal class NumbersKt__NumbersKt : NumbersKt__NumbersJVMKt {
   open fun NumbersKt__NumbersKt() {
   }

   @JvmStatic
   public inline fun Byte.countLeadingZeroBits(): Int {
      return Integer.numberOfLeadingZeros(var0 and 255) - 24;
   }

   @JvmStatic
   public inline fun Short.countLeadingZeroBits(): Int {
      return Integer.numberOfLeadingZeros(var0 and 65535) - 16;
   }

   @JvmStatic
   public inline fun Byte.countOneBits(): Int {
      return Integer.bitCount(var0 and 255);
   }

   @JvmStatic
   public inline fun Short.countOneBits(): Int {
      return Integer.bitCount(var0 and 65535);
   }

   @JvmStatic
   public inline fun Byte.countTrailingZeroBits(): Int {
      return Integer.numberOfTrailingZeros(var0 or 256);
   }

   @JvmStatic
   public inline fun Short.countTrailingZeroBits(): Int {
      return Integer.numberOfTrailingZeros(var0 or 65536);
   }

   @JvmStatic
   public fun Byte.rotateLeft(bitCount: Int): Byte {
      return (byte)((var0 and 255) ushr 8 - (var1 and 7) or var0 shl (var1 and 7));
   }

   @JvmStatic
   public fun Short.rotateLeft(bitCount: Int): Short {
      return (short)((var0 and '\uffff') ushr 16 - (var1 and 15) or var0 shl (var1 and 15));
   }

   @JvmStatic
   public fun Byte.rotateRight(bitCount: Int): Byte {
      return (byte)((var0 and 255) ushr (var1 and 7) or var0 shl 8 - (var1 and 7));
   }

   @JvmStatic
   public fun Short.rotateRight(bitCount: Int): Short {
      return (short)((var0 and '\uffff') ushr (var1 and 15) or var0 shl 16 - (var1 and 15));
   }

   @JvmStatic
   public inline fun Byte.takeHighestOneBit(): Byte {
      return (byte)Integer.highestOneBit(var0 and 255);
   }

   @JvmStatic
   public inline fun Short.takeHighestOneBit(): Short {
      return (short)Integer.highestOneBit(var0 and '\uffff');
   }

   @JvmStatic
   public inline fun Byte.takeLowestOneBit(): Byte {
      return (byte)Integer.lowestOneBit(var0);
   }

   @JvmStatic
   public inline fun Short.takeLowestOneBit(): Short {
      return (short)Integer.lowestOneBit(var0);
   }
}
