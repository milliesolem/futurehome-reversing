package kotlin

public inline fun Byte.toUByte(): UByte {
   return UByte.constructor-impl(var0);
}

public inline fun Int.toUByte(): UByte {
   return UByte.constructor-impl((byte)var0);
}

public inline fun Long.toUByte(): UByte {
   return UByte.constructor-impl((byte)((int)var0));
}

public inline fun Short.toUByte(): UByte {
   return UByte.constructor-impl((byte)var0);
}
