package kotlin

import kotlin.Double.Companion

internal class NumbersKt__NumbersJVMKt : NumbersKt__FloorDivModKt {
   open fun NumbersKt__NumbersJVMKt() {
   }

   @JvmStatic
   public inline fun Int.countLeadingZeroBits(): Int {
      return Integer.numberOfLeadingZeros(var0);
   }

   @JvmStatic
   public inline fun Long.countLeadingZeroBits(): Int {
      return java.lang.Long.numberOfLeadingZeros(var0);
   }

   @JvmStatic
   public inline fun Int.countOneBits(): Int {
      return Integer.bitCount(var0);
   }

   @JvmStatic
   public inline fun Long.countOneBits(): Int {
      return java.lang.Long.bitCount(var0);
   }

   @JvmStatic
   public inline fun Int.countTrailingZeroBits(): Int {
      return Integer.numberOfTrailingZeros(var0);
   }

   @JvmStatic
   public inline fun Long.countTrailingZeroBits(): Int {
      return java.lang.Long.numberOfTrailingZeros(var0);
   }

   @JvmStatic
   public inline fun Companion.fromBits(bits: Long): Double {
      return java.lang.Double.longBitsToDouble(var1);
   }

   @JvmStatic
   public inline fun kotlin.Float.Companion.fromBits(bits: Int): Float {
      return java.lang.Float.intBitsToFloat(var1);
   }

   @JvmStatic
   public inline fun Double.isFinite(): Boolean {
      val var2: Boolean;
      if (!java.lang.Double.isInfinite(var0) && !java.lang.Double.isNaN(var0)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   @JvmStatic
   public inline fun Float.isFinite(): Boolean {
      val var1: Boolean;
      if (!java.lang.Float.isInfinite(var0) && !java.lang.Float.isNaN(var0)) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @JvmStatic
   public inline fun Double.isInfinite(): Boolean {
      return java.lang.Double.isInfinite(var0);
   }

   @JvmStatic
   public inline fun Float.isInfinite(): Boolean {
      return java.lang.Float.isInfinite(var0);
   }

   @JvmStatic
   public inline fun Double.isNaN(): Boolean {
      return java.lang.Double.isNaN(var0);
   }

   @JvmStatic
   public inline fun Float.isNaN(): Boolean {
      return java.lang.Float.isNaN(var0);
   }

   @JvmStatic
   public inline fun Int.rotateLeft(bitCount: Int): Int {
      return Integer.rotateLeft(var0, var1);
   }

   @JvmStatic
   public inline fun Long.rotateLeft(bitCount: Int): Long {
      return java.lang.Long.rotateLeft(var0, var2);
   }

   @JvmStatic
   public inline fun Int.rotateRight(bitCount: Int): Int {
      return Integer.rotateRight(var0, var1);
   }

   @JvmStatic
   public inline fun Long.rotateRight(bitCount: Int): Long {
      return java.lang.Long.rotateRight(var0, var2);
   }

   @JvmStatic
   public inline fun Int.takeHighestOneBit(): Int {
      return Integer.highestOneBit(var0);
   }

   @JvmStatic
   public inline fun Long.takeHighestOneBit(): Long {
      return java.lang.Long.highestOneBit(var0);
   }

   @JvmStatic
   public inline fun Int.takeLowestOneBit(): Int {
      return Integer.lowestOneBit(var0);
   }

   @JvmStatic
   public inline fun Long.takeLowestOneBit(): Long {
      return java.lang.Long.lowestOneBit(var0);
   }

   @JvmStatic
   public inline fun Float.toBits(): Int {
      return java.lang.Float.floatToIntBits(var0);
   }

   @JvmStatic
   public inline fun Double.toBits(): Long {
      return java.lang.Double.doubleToLongBits(var0);
   }

   @JvmStatic
   public inline fun Float.toRawBits(): Int {
      return java.lang.Float.floatToRawIntBits(var0);
   }

   @JvmStatic
   public inline fun Double.toRawBits(): Long {
      return java.lang.Double.doubleToRawLongBits(var0);
   }
}
