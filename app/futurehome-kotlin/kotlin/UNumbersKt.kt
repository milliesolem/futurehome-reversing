package kotlin

public inline fun UByte.countLeadingZeroBits(): Int {
   return Integer.numberOfLeadingZeros(var0 and 255) - 24;
}

public inline fun ULong.countLeadingZeroBits(): Int {
   return java.lang.Long.numberOfLeadingZeros(var0);
}

public inline fun UInt.countLeadingZeroBits(): Int {
   return Integer.numberOfLeadingZeros(var0);
}

public inline fun UShort.countLeadingZeroBits(): Int {
   return Integer.numberOfLeadingZeros(var0 and 65535) - 16;
}

public inline fun UByte.countOneBits(): Int {
   return Integer.bitCount(UInt.constructor-impl(var0 and 255));
}

public inline fun ULong.countOneBits(): Int {
   return java.lang.Long.bitCount(var0);
}

public inline fun UInt.countOneBits(): Int {
   return Integer.bitCount(var0);
}

public inline fun UShort.countOneBits(): Int {
   return Integer.bitCount(UInt.constructor-impl(var0 and 65535));
}

public inline fun UByte.countTrailingZeroBits(): Int {
   return Integer.numberOfTrailingZeros(var0 or 256);
}

public inline fun ULong.countTrailingZeroBits(): Int {
   return java.lang.Long.numberOfTrailingZeros(var0);
}

public inline fun UInt.countTrailingZeroBits(): Int {
   return Integer.numberOfTrailingZeros(var0);
}

public inline fun UShort.countTrailingZeroBits(): Int {
   return Integer.numberOfTrailingZeros(var0 or 65536);
}

public inline fun ULong.rotateLeft(bitCount: Int): ULong {
   return ULong.constructor-impl(java.lang.Long.rotateLeft(var0, var2));
}

public inline fun UByte.rotateLeft(bitCount: Int): UByte {
   return UByte.constructor-impl(NumbersKt.rotateLeft(var0, var1));
}

public inline fun UInt.rotateLeft(bitCount: Int): UInt {
   return UInt.constructor-impl(Integer.rotateLeft(var0, var1));
}

public inline fun UShort.rotateLeft(bitCount: Int): UShort {
   return UShort.constructor-impl(NumbersKt.rotateLeft(var0, var1));
}

public inline fun ULong.rotateRight(bitCount: Int): ULong {
   return ULong.constructor-impl(java.lang.Long.rotateRight(var0, var2));
}

public inline fun UByte.rotateRight(bitCount: Int): UByte {
   return UByte.constructor-impl(NumbersKt.rotateRight(var0, var1));
}

public inline fun UInt.rotateRight(bitCount: Int): UInt {
   return UInt.constructor-impl(Integer.rotateRight(var0, var1));
}

public inline fun UShort.rotateRight(bitCount: Int): UShort {
   return UShort.constructor-impl(NumbersKt.rotateRight(var0, var1));
}

public inline fun UByte.takeHighestOneBit(): UByte {
   return UByte.constructor-impl((byte)Integer.highestOneBit(var0 and 255));
}

public inline fun ULong.takeHighestOneBit(): ULong {
   return ULong.constructor-impl(java.lang.Long.highestOneBit(var0));
}

public inline fun UInt.takeHighestOneBit(): UInt {
   return UInt.constructor-impl(Integer.highestOneBit(var0));
}

public inline fun UShort.takeHighestOneBit(): UShort {
   return UShort.constructor-impl((short)Integer.highestOneBit(var0 and '\uffff'));
}

public inline fun UByte.takeLowestOneBit(): UByte {
   return UByte.constructor-impl((byte)Integer.lowestOneBit(var0 and 255));
}

public inline fun ULong.takeLowestOneBit(): ULong {
   return ULong.constructor-impl(java.lang.Long.lowestOneBit(var0));
}

public inline fun UInt.takeLowestOneBit(): UInt {
   return UInt.constructor-impl(Integer.lowestOneBit(var0));
}

public inline fun UShort.takeLowestOneBit(): UShort {
   return UShort.constructor-impl((short)Integer.lowestOneBit(var0 and '\uffff'));
}
