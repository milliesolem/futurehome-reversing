package kotlin

public inline fun Byte.toUInt(): UInt {
   return UInt.constructor-impl(var0);
}

public inline fun Double.toUInt(): UInt {
   return UnsignedKt.doubleToUInt(var0);
}

public inline fun Float.toUInt(): UInt {
   return UnsignedKt.doubleToUInt((double)var0);
}

public inline fun Int.toUInt(): UInt {
   return UInt.constructor-impl(var0);
}

public inline fun Long.toUInt(): UInt {
   return UInt.constructor-impl((int)var0);
}

public inline fun Short.toUInt(): UInt {
   return UInt.constructor-impl(var0);
}
