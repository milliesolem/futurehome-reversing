package kotlin

import kotlin.jvm.internal.Intrinsics

@JvmInline
public inline class UByte : java.lang.Comparable<UByte> {
   internal final val data: Byte

   @JvmStatic
   public inline infix fun and(other: UByte): UByte {
      return constructor-impl((byte)(var0 and var1));
   }

   fun `compareTo-7apg3OU`(var1: Byte): Int {
      return Intrinsics.compare(this.unbox-impl() and 255, var1 and 255);
   }

   @JvmStatic
   public open inline operator fun compareTo(other: UByte): Int {
      return Intrinsics.compare(var0 and 255, var1 and 255);
   }

   @JvmStatic
   public inline operator fun compareTo(other: ULong): Int {
      return UByte$$ExternalSyntheticBackport0.m(ULong.constructor-impl((long)var0 and 255L), var1);
   }

   @JvmStatic
   public inline operator fun compareTo(other: UInt): Int {
      return UByte$$ExternalSyntheticBackport0.m$2(UInt.constructor-impl(var0 and 255), var1);
   }

   @JvmStatic
   public inline operator fun compareTo(other: UShort): Int {
      return Intrinsics.compare(var0 and 255, var1 and 65535);
   }

   @JvmStatic
   fun `constructor-impl`(var0: Byte): Byte {
      return var0;
   }

   @JvmStatic
   public inline operator fun dec(): UByte {
      return constructor-impl((byte)(var0 - 1));
   }

   @JvmStatic
   public inline operator fun div(other: UByte): UInt {
      return UByte$$ExternalSyntheticBackport0.m(UInt.constructor-impl(var0 and 255), UInt.constructor-impl(var1 and 255));
   }

   @JvmStatic
   public inline operator fun div(other: ULong): ULong {
      return UByte$$ExternalSyntheticBackport0.m$1(ULong.constructor-impl((long)var0 and 255L), var1);
   }

   @JvmStatic
   public inline operator fun div(other: UInt): UInt {
      return UByte$$ExternalSyntheticBackport0.m(UInt.constructor-impl(var0 and 255), var1);
   }

   @JvmStatic
   public inline operator fun div(other: UShort): UInt {
      return UByte$$ExternalSyntheticBackport0.m(UInt.constructor-impl(var0 and 255), UInt.constructor-impl(var1 and 65535));
   }

   @JvmStatic
   fun `equals-impl`(var0: Byte, var1: Any): Boolean {
      if (var1 !is UByte) {
         return false;
      } else {
         return var0 == (var1 as UByte).unbox-impl();
      }
   }

   @JvmStatic
   fun `equals-impl0`(var0: Byte, var1: Byte): Boolean {
      val var2: Boolean;
      if (var0 == var1) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   @JvmStatic
   public inline fun floorDiv(other: UByte): UInt {
      return UByte$$ExternalSyntheticBackport0.m(UInt.constructor-impl(var0 and 255), UInt.constructor-impl(var1 and 255));
   }

   @JvmStatic
   public inline fun floorDiv(other: ULong): ULong {
      return UByte$$ExternalSyntheticBackport0.m$1(ULong.constructor-impl((long)var0 and 255L), var1);
   }

   @JvmStatic
   public inline fun floorDiv(other: UInt): UInt {
      return UByte$$ExternalSyntheticBackport0.m(UInt.constructor-impl(var0 and 255), var1);
   }

   @JvmStatic
   public inline fun floorDiv(other: UShort): UInt {
      return UByte$$ExternalSyntheticBackport0.m(UInt.constructor-impl(var0 and 255), UInt.constructor-impl(var1 and 65535));
   }

   @JvmStatic
   fun `hashCode-impl`(var0: Byte): Int {
      return var0;
   }

   @JvmStatic
   public inline operator fun inc(): UByte {
      return constructor-impl((byte)(var0 + 1));
   }

   @JvmStatic
   public inline fun inv(): UByte {
      return constructor-impl((byte)(var0.inv()));
   }

   @JvmStatic
   public inline operator fun minus(other: UByte): UInt {
      return UInt.constructor-impl(UInt.constructor-impl(var0 and 255) - UInt.constructor-impl(var1 and 255));
   }

   @JvmStatic
   public inline operator fun minus(other: ULong): ULong {
      return ULong.constructor-impl(ULong.constructor-impl((long)var0 and 255L) - var1);
   }

   @JvmStatic
   public inline operator fun minus(other: UInt): UInt {
      return UInt.constructor-impl(UInt.constructor-impl(var0 and 255) - var1);
   }

   @JvmStatic
   public inline operator fun minus(other: UShort): UInt {
      return UInt.constructor-impl(UInt.constructor-impl(var0 and 255) - UInt.constructor-impl(var1 and 65535));
   }

   @JvmStatic
   public inline fun mod(other: UByte): UByte {
      return constructor-impl((byte)UByte$$ExternalSyntheticBackport0.m$1(UInt.constructor-impl(var0 and 255), UInt.constructor-impl(var1 and 255)));
   }

   @JvmStatic
   public inline fun mod(other: ULong): ULong {
      return UByte$$ExternalSyntheticBackport0.m(ULong.constructor-impl((long)var0 and 255L), var1);
   }

   @JvmStatic
   public inline fun mod(other: UInt): UInt {
      return UByte$$ExternalSyntheticBackport0.m$1(UInt.constructor-impl(var0 and 255), var1);
   }

   @JvmStatic
   public inline fun mod(other: UShort): UShort {
      return UShort.constructor-impl(
         (short)UByte$$ExternalSyntheticBackport0.m$1(UInt.constructor-impl(var0 and 255), UInt.constructor-impl(var1 and '\uffff'))
      );
   }

   @JvmStatic
   public inline infix fun or(other: UByte): UByte {
      return constructor-impl((byte)(var0 or var1));
   }

   @JvmStatic
   public inline operator fun plus(other: UByte): UInt {
      return UInt.constructor-impl(UInt.constructor-impl(var0 and 255) + UInt.constructor-impl(var1 and 255));
   }

   @JvmStatic
   public inline operator fun plus(other: ULong): ULong {
      return ULong.constructor-impl(ULong.constructor-impl((long)var0 and 255L) + var1);
   }

   @JvmStatic
   public inline operator fun plus(other: UInt): UInt {
      return UInt.constructor-impl(UInt.constructor-impl(var0 and 255) + var1);
   }

   @JvmStatic
   public inline operator fun plus(other: UShort): UInt {
      return UInt.constructor-impl(UInt.constructor-impl(var0 and 255) + UInt.constructor-impl(var1 and 65535));
   }

   @JvmStatic
   public inline operator fun rangeTo(other: UByte): UIntRange {
      return new UIntRange(UInt.constructor-impl(var0 and 255), UInt.constructor-impl(var1 and 255), null);
   }

   @JvmStatic
   public inline operator fun rangeUntil(other: UByte): UIntRange {
      return URangesKt.until-J1ME1BU(UInt.constructor-impl(var0 and 255), UInt.constructor-impl(var1 and 255));
   }

   @JvmStatic
   public inline operator fun rem(other: UByte): UInt {
      return UByte$$ExternalSyntheticBackport0.m$1(UInt.constructor-impl(var0 and 255), UInt.constructor-impl(var1 and 255));
   }

   @JvmStatic
   public inline operator fun rem(other: ULong): ULong {
      return UByte$$ExternalSyntheticBackport0.m(ULong.constructor-impl((long)var0 and 255L), var1);
   }

   @JvmStatic
   public inline operator fun rem(other: UInt): UInt {
      return UByte$$ExternalSyntheticBackport0.m$1(UInt.constructor-impl(var0 and 255), var1);
   }

   @JvmStatic
   public inline operator fun rem(other: UShort): UInt {
      return UByte$$ExternalSyntheticBackport0.m$1(UInt.constructor-impl(var0 and 255), UInt.constructor-impl(var1 and 65535));
   }

   @JvmStatic
   public inline operator fun times(other: UByte): UInt {
      return UInt.constructor-impl(UInt.constructor-impl(var0 and 255) * UInt.constructor-impl(var1 and 255));
   }

   @JvmStatic
   public inline operator fun times(other: ULong): ULong {
      return ULong.constructor-impl(ULong.constructor-impl((long)var0 and 255L) * var1);
   }

   @JvmStatic
   public inline operator fun times(other: UInt): UInt {
      return UInt.constructor-impl(UInt.constructor-impl(var0 and 255) * var1);
   }

   @JvmStatic
   public inline operator fun times(other: UShort): UInt {
      return UInt.constructor-impl(UInt.constructor-impl(var0 and 255) * UInt.constructor-impl(var1 and 65535));
   }

   @JvmStatic
   public inline fun toByte(): Byte {
      return var0;
   }

   @JvmStatic
   public inline fun toDouble(): Double {
      return UnsignedKt.uintToDouble(var0 and 255);
   }

   @JvmStatic
   public inline fun toFloat(): Float {
      return (float)UnsignedKt.uintToDouble(var0 and 255);
   }

   @JvmStatic
   public inline fun toInt(): Int {
      return var0 and 255;
   }

   @JvmStatic
   public inline fun toLong(): Long {
      return var0 and 255L;
   }

   @JvmStatic
   public inline fun toShort(): Short {
      return (short)(var0 and 255);
   }

   @JvmStatic
   public open fun toString(): String {
      return java.lang.String.valueOf(var0 and 255);
   }

   @JvmStatic
   public inline fun toUByte(): UByte {
      return var0;
   }

   @JvmStatic
   public inline fun toUInt(): UInt {
      return UInt.constructor-impl(var0 and 255);
   }

   @JvmStatic
   public inline fun toULong(): ULong {
      return ULong.constructor-impl((long)var0 and 255L);
   }

   @JvmStatic
   public inline fun toUShort(): UShort {
      return UShort.constructor-impl((short)((short)var0 and 255));
   }

   @JvmStatic
   public inline infix fun xor(other: UByte): UByte {
      return constructor-impl((byte)(var0 xor var1));
   }

   public override operator fun equals(other: Any?): Boolean {
      return equals-impl(this.data, var1);
   }

   public override fun hashCode(): Int {
      return hashCode-impl(this.data);
   }

   override fun toString(): java.lang.String {
      return toString-impl(this.data);
   }

   public companion object {
      public const val MIN_VALUE: UByte
      public const val MAX_VALUE: UByte
      public const val SIZE_BYTES: Int
      public const val SIZE_BITS: Int
   }
}
