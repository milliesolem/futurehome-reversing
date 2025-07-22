package kotlin

@JvmInline
public inline class UInt : java.lang.Comparable<UInt> {
   internal final val data: Int

   @JvmStatic
   public inline infix fun and(other: UInt): UInt {
      return constructor-impl(var0 and var1);
   }

   @JvmStatic
   public inline operator fun compareTo(other: UByte): Int {
      return UByte$$ExternalSyntheticBackport0.m$2(var0, constructor-impl(var1 and 255));
   }

   @JvmStatic
   public inline operator fun compareTo(other: ULong): Int {
      return UByte$$ExternalSyntheticBackport0.m(ULong.constructor-impl((long)var0 and 4294967295L), var1);
   }

   fun `compareTo-WZ4Q5Ns`(var1: Int): Int {
      return UnsignedKt.uintCompare(this.unbox-impl(), var1);
   }

   @JvmStatic
   public open inline operator fun compareTo(other: UInt): Int {
      return UnsignedKt.uintCompare(var0, var1);
   }

   @JvmStatic
   public inline operator fun compareTo(other: UShort): Int {
      return UByte$$ExternalSyntheticBackport0.m$2(var0, constructor-impl(var1 and 65535));
   }

   @JvmStatic
   fun `constructor-impl`(var0: Int): Int {
      return var0;
   }

   @JvmStatic
   public inline operator fun dec(): UInt {
      return constructor-impl(var0 - 1);
   }

   @JvmStatic
   public inline operator fun div(other: UByte): UInt {
      return UByte$$ExternalSyntheticBackport0.m(var0, constructor-impl(var1 and 255));
   }

   @JvmStatic
   public inline operator fun div(other: ULong): ULong {
      return UByte$$ExternalSyntheticBackport0.m$1(ULong.constructor-impl((long)var0 and 4294967295L), var1);
   }

   @JvmStatic
   public inline operator fun div(other: UInt): UInt {
      return UnsignedKt.uintDivide-J1ME1BU(var0, var1);
   }

   @JvmStatic
   public inline operator fun div(other: UShort): UInt {
      return UByte$$ExternalSyntheticBackport0.m(var0, constructor-impl(var1 and 65535));
   }

   @JvmStatic
   fun `equals-impl`(var0: Int, var1: Any): Boolean {
      if (var1 !is UInt) {
         return false;
      } else {
         return var0 == (var1 as UInt).unbox-impl();
      }
   }

   @JvmStatic
   fun `equals-impl0`(var0: Int, var1: Int): Boolean {
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
      return UByte$$ExternalSyntheticBackport0.m(var0, constructor-impl(var1 and 255));
   }

   @JvmStatic
   public inline fun floorDiv(other: ULong): ULong {
      return UByte$$ExternalSyntheticBackport0.m$1(ULong.constructor-impl((long)var0 and 4294967295L), var1);
   }

   @JvmStatic
   public inline fun floorDiv(other: UInt): UInt {
      return UByte$$ExternalSyntheticBackport0.m(var0, var1);
   }

   @JvmStatic
   public inline fun floorDiv(other: UShort): UInt {
      return UByte$$ExternalSyntheticBackport0.m(var0, constructor-impl(var1 and 65535));
   }

   @JvmStatic
   fun `hashCode-impl`(var0: Int): Int {
      return var0;
   }

   @JvmStatic
   public inline operator fun inc(): UInt {
      return constructor-impl(var0 + 1);
   }

   @JvmStatic
   public inline fun inv(): UInt {
      return constructor-impl(var0.inv());
   }

   @JvmStatic
   public inline operator fun minus(other: UByte): UInt {
      return constructor-impl(var0 - constructor-impl(var1 and 255));
   }

   @JvmStatic
   public inline operator fun minus(other: ULong): ULong {
      return ULong.constructor-impl(ULong.constructor-impl((long)var0 and 4294967295L) - var1);
   }

   @JvmStatic
   public inline operator fun minus(other: UInt): UInt {
      return constructor-impl(var0 - var1);
   }

   @JvmStatic
   public inline operator fun minus(other: UShort): UInt {
      return constructor-impl(var0 - constructor-impl(var1 and 65535));
   }

   @JvmStatic
   public inline fun mod(other: UByte): UByte {
      return UByte.constructor-impl((byte)UByte$$ExternalSyntheticBackport0.m$1(var0, constructor-impl(var1 and 255)));
   }

   @JvmStatic
   public inline fun mod(other: ULong): ULong {
      return UByte$$ExternalSyntheticBackport0.m(ULong.constructor-impl((long)var0 and 4294967295L), var1);
   }

   @JvmStatic
   public inline fun mod(other: UInt): UInt {
      return UByte$$ExternalSyntheticBackport0.m$1(var0, var1);
   }

   @JvmStatic
   public inline fun mod(other: UShort): UShort {
      return UShort.constructor-impl((short)UByte$$ExternalSyntheticBackport0.m$1(var0, constructor-impl(var1 and '\uffff')));
   }

   @JvmStatic
   public inline infix fun or(other: UInt): UInt {
      return constructor-impl(var0 or var1);
   }

   @JvmStatic
   public inline operator fun plus(other: UByte): UInt {
      return constructor-impl(var0 + constructor-impl(var1 and 255));
   }

   @JvmStatic
   public inline operator fun plus(other: ULong): ULong {
      return ULong.constructor-impl(ULong.constructor-impl((long)var0 and 4294967295L) + var1);
   }

   @JvmStatic
   public inline operator fun plus(other: UInt): UInt {
      return constructor-impl(var0 + var1);
   }

   @JvmStatic
   public inline operator fun plus(other: UShort): UInt {
      return constructor-impl(var0 + constructor-impl(var1 and 65535));
   }

   @JvmStatic
   public inline operator fun rangeTo(other: UInt): UIntRange {
      return new UIntRange(var0, var1, null);
   }

   @JvmStatic
   public inline operator fun rangeUntil(other: UInt): UIntRange {
      return URangesKt.until-J1ME1BU(var0, var1);
   }

   @JvmStatic
   public inline operator fun rem(other: UByte): UInt {
      return UByte$$ExternalSyntheticBackport0.m$1(var0, constructor-impl(var1 and 255));
   }

   @JvmStatic
   public inline operator fun rem(other: ULong): ULong {
      return UByte$$ExternalSyntheticBackport0.m(ULong.constructor-impl((long)var0 and 4294967295L), var1);
   }

   @JvmStatic
   public inline operator fun rem(other: UInt): UInt {
      return UnsignedKt.uintRemainder-J1ME1BU(var0, var1);
   }

   @JvmStatic
   public inline operator fun rem(other: UShort): UInt {
      return UByte$$ExternalSyntheticBackport0.m$1(var0, constructor-impl(var1 and 65535));
   }

   @JvmStatic
   public inline infix fun shl(bitCount: Int): UInt {
      return constructor-impl(var0 shl var1);
   }

   @JvmStatic
   public inline infix fun shr(bitCount: Int): UInt {
      return constructor-impl(var0 ushr var1);
   }

   @JvmStatic
   public inline operator fun times(other: UByte): UInt {
      return constructor-impl(var0 * constructor-impl(var1 and 255));
   }

   @JvmStatic
   public inline operator fun times(other: ULong): ULong {
      return ULong.constructor-impl(ULong.constructor-impl((long)var0 and 4294967295L) * var1);
   }

   @JvmStatic
   public inline operator fun times(other: UInt): UInt {
      return constructor-impl(var0 * var1);
   }

   @JvmStatic
   public inline operator fun times(other: UShort): UInt {
      return constructor-impl(var0 * constructor-impl(var1 and 65535));
   }

   @JvmStatic
   public inline fun toByte(): Byte {
      return (byte)var0;
   }

   @JvmStatic
   public inline fun toDouble(): Double {
      return UnsignedKt.uintToDouble(var0);
   }

   @JvmStatic
   public inline fun toFloat(): Float {
      return (float)UnsignedKt.uintToDouble(var0);
   }

   @JvmStatic
   public inline fun toInt(): Int {
      return var0;
   }

   @JvmStatic
   public inline fun toLong(): Long {
      return var0 and 4294967295L;
   }

   @JvmStatic
   public inline fun toShort(): Short {
      return (short)var0;
   }

   @JvmStatic
   public open fun toString(): String {
      return java.lang.String.valueOf((long)var0 and 4294967295L);
   }

   @JvmStatic
   public inline fun toUByte(): UByte {
      return UByte.constructor-impl((byte)var0);
   }

   @JvmStatic
   public inline fun toUInt(): UInt {
      return var0;
   }

   @JvmStatic
   public inline fun toULong(): ULong {
      return ULong.constructor-impl((long)var0 and 4294967295L);
   }

   @JvmStatic
   public inline fun toUShort(): UShort {
      return UShort.constructor-impl((short)var0);
   }

   @JvmStatic
   public inline infix fun xor(other: UInt): UInt {
      return constructor-impl(var0 xor var1);
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
      public const val MIN_VALUE: UInt
      public const val MAX_VALUE: UInt
      public const val SIZE_BYTES: Int
      public const val SIZE_BITS: Int
   }
}
