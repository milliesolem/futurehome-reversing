package kotlin

@JvmInline
public inline class ULong : java.lang.Comparable<ULong> {
   internal final val data: Long

   @JvmStatic
   public inline infix fun and(other: ULong): ULong {
      return constructor-impl(var0 and var2);
   }

   @JvmStatic
   public inline operator fun compareTo(other: UByte): Int {
      return UByte$$ExternalSyntheticBackport0.m(var0, constructor-impl((long)var2 and 255L));
   }

   fun `compareTo-VKZWuLQ`(var1: Long): Int {
      return UnsignedKt.ulongCompare(this.unbox-impl(), var1);
   }

   @JvmStatic
   public open inline operator fun compareTo(other: ULong): Int {
      return UnsignedKt.ulongCompare(var0, var2);
   }

   @JvmStatic
   public inline operator fun compareTo(other: UInt): Int {
      return UByte$$ExternalSyntheticBackport0.m(var0, constructor-impl((long)var2 and 4294967295L));
   }

   @JvmStatic
   public inline operator fun compareTo(other: UShort): Int {
      return UByte$$ExternalSyntheticBackport0.m(var0, constructor-impl((long)var2 and 65535L));
   }

   @JvmStatic
   fun `constructor-impl`(var0: Long): Long {
      return var0;
   }

   @JvmStatic
   public inline operator fun dec(): ULong {
      return constructor-impl(var0 - 1L);
   }

   @JvmStatic
   public inline operator fun div(other: UByte): ULong {
      return UByte$$ExternalSyntheticBackport0.m$1(var0, constructor-impl((long)var2 and 255L));
   }

   @JvmStatic
   public inline operator fun div(other: ULong): ULong {
      return UnsignedKt.ulongDivide-eb3DHEI(var0, var2);
   }

   @JvmStatic
   public inline operator fun div(other: UInt): ULong {
      return UByte$$ExternalSyntheticBackport0.m$1(var0, constructor-impl((long)var2 and 4294967295L));
   }

   @JvmStatic
   public inline operator fun div(other: UShort): ULong {
      return UByte$$ExternalSyntheticBackport0.m$1(var0, constructor-impl((long)var2 and 65535L));
   }

   @JvmStatic
   fun `equals-impl`(var0: Long, var2: Any): Boolean {
      if (var2 !is ULong) {
         return false;
      } else {
         return var0 == (var2 as ULong).unbox-impl();
      }
   }

   @JvmStatic
   fun `equals-impl0`(var0: Long, var2: Long): Boolean {
      val var4: Boolean;
      if (var0 == var2) {
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }

   @JvmStatic
   public inline fun floorDiv(other: UByte): ULong {
      return UByte$$ExternalSyntheticBackport0.m$1(var0, constructor-impl((long)var2 and 255L));
   }

   @JvmStatic
   public inline fun floorDiv(other: ULong): ULong {
      return UByte$$ExternalSyntheticBackport0.m$1(var0, var2);
   }

   @JvmStatic
   public inline fun floorDiv(other: UInt): ULong {
      return UByte$$ExternalSyntheticBackport0.m$1(var0, constructor-impl((long)var2 and 4294967295L));
   }

   @JvmStatic
   public inline fun floorDiv(other: UShort): ULong {
      return UByte$$ExternalSyntheticBackport0.m$1(var0, constructor-impl((long)var2 and 65535L));
   }

   @JvmStatic
   fun `hashCode-impl`(var0: Long): Int {
      return UByte$$ExternalSyntheticBackport0.m(var0);
   }

   @JvmStatic
   public inline operator fun inc(): ULong {
      return constructor-impl(var0 + 1L);
   }

   @JvmStatic
   public inline fun inv(): ULong {
      return constructor-impl(var0.inv());
   }

   @JvmStatic
   public inline operator fun minus(other: UByte): ULong {
      return constructor-impl(var0 - constructor-impl((long)var2 and 255L));
   }

   @JvmStatic
   public inline operator fun minus(other: ULong): ULong {
      return constructor-impl(var0 - var2);
   }

   @JvmStatic
   public inline operator fun minus(other: UInt): ULong {
      return constructor-impl(var0 - constructor-impl((long)var2 and 4294967295L));
   }

   @JvmStatic
   public inline operator fun minus(other: UShort): ULong {
      return constructor-impl(var0 - constructor-impl((long)var2 and 65535L));
   }

   @JvmStatic
   public inline fun mod(other: UByte): UByte {
      return UByte.constructor-impl((byte)((int)UByte$$ExternalSyntheticBackport0.m(var0, constructor-impl((long)var2 and 255L))));
   }

   @JvmStatic
   public inline fun mod(other: ULong): ULong {
      return UByte$$ExternalSyntheticBackport0.m(var0, var2);
   }

   @JvmStatic
   public inline fun mod(other: UInt): UInt {
      return UInt.constructor-impl((int)UByte$$ExternalSyntheticBackport0.m(var0, constructor-impl((long)var2 and 4294967295L)));
   }

   @JvmStatic
   public inline fun mod(other: UShort): UShort {
      return UShort.constructor-impl((short)((int)UByte$$ExternalSyntheticBackport0.m(var0, constructor-impl((long)var2 and 65535L))));
   }

   @JvmStatic
   public inline infix fun or(other: ULong): ULong {
      return constructor-impl(var0 or var2);
   }

   @JvmStatic
   public inline operator fun plus(other: UByte): ULong {
      return constructor-impl(var0 + constructor-impl((long)var2 and 255L));
   }

   @JvmStatic
   public inline operator fun plus(other: ULong): ULong {
      return constructor-impl(var0 + var2);
   }

   @JvmStatic
   public inline operator fun plus(other: UInt): ULong {
      return constructor-impl(var0 + constructor-impl((long)var2 and 4294967295L));
   }

   @JvmStatic
   public inline operator fun plus(other: UShort): ULong {
      return constructor-impl(var0 + constructor-impl((long)var2 and 65535L));
   }

   @JvmStatic
   public inline operator fun rangeTo(other: ULong): ULongRange {
      return new ULongRange(var0, var2, null);
   }

   @JvmStatic
   public inline operator fun rangeUntil(other: ULong): ULongRange {
      return URangesKt.until-eb3DHEI(var0, var2);
   }

   @JvmStatic
   public inline operator fun rem(other: UByte): ULong {
      return UByte$$ExternalSyntheticBackport0.m(var0, constructor-impl((long)var2 and 255L));
   }

   @JvmStatic
   public inline operator fun rem(other: ULong): ULong {
      return UnsignedKt.ulongRemainder-eb3DHEI(var0, var2);
   }

   @JvmStatic
   public inline operator fun rem(other: UInt): ULong {
      return UByte$$ExternalSyntheticBackport0.m(var0, constructor-impl((long)var2 and 4294967295L));
   }

   @JvmStatic
   public inline operator fun rem(other: UShort): ULong {
      return UByte$$ExternalSyntheticBackport0.m(var0, constructor-impl((long)var2 and 65535L));
   }

   @JvmStatic
   public inline infix fun shl(bitCount: Int): ULong {
      return constructor-impl(var0 shl var2);
   }

   @JvmStatic
   public inline infix fun shr(bitCount: Int): ULong {
      return constructor-impl(var0 ushr var2);
   }

   @JvmStatic
   public inline operator fun times(other: UByte): ULong {
      return constructor-impl(var0 * constructor-impl((long)var2 and 255L));
   }

   @JvmStatic
   public inline operator fun times(other: ULong): ULong {
      return constructor-impl(var0 * var2);
   }

   @JvmStatic
   public inline operator fun times(other: UInt): ULong {
      return constructor-impl(var0 * constructor-impl((long)var2 and 4294967295L));
   }

   @JvmStatic
   public inline operator fun times(other: UShort): ULong {
      return constructor-impl(var0 * constructor-impl((long)var2 and 65535L));
   }

   @JvmStatic
   public inline fun toByte(): Byte {
      return (byte)var0;
   }

   @JvmStatic
   public inline fun toDouble(): Double {
      return UnsignedKt.ulongToDouble(var0);
   }

   @JvmStatic
   public inline fun toFloat(): Float {
      return (float)UnsignedKt.ulongToDouble(var0);
   }

   @JvmStatic
   public inline fun toInt(): Int {
      return (int)var0;
   }

   @JvmStatic
   public inline fun toLong(): Long {
      return var0;
   }

   @JvmStatic
   public inline fun toShort(): Short {
      return (short)var0;
   }

   @JvmStatic
   public open fun toString(): String {
      return UnsignedKt.ulongToString(var0, 10);
   }

   @JvmStatic
   public inline fun toUByte(): UByte {
      return UByte.constructor-impl((byte)((int)var0));
   }

   @JvmStatic
   public inline fun toUInt(): UInt {
      return UInt.constructor-impl((int)var0);
   }

   @JvmStatic
   public inline fun toULong(): ULong {
      return var0;
   }

   @JvmStatic
   public inline fun toUShort(): UShort {
      return UShort.constructor-impl((short)((int)var0));
   }

   @JvmStatic
   public inline infix fun xor(other: ULong): ULong {
      return constructor-impl(var0 xor var2);
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
      public const val MIN_VALUE: ULong
      public const val MAX_VALUE: ULong
      public const val SIZE_BYTES: Int
      public const val SIZE_BITS: Int
   }
}
