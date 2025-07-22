package kotlin

public inline fun Byte.toULong(): ULong {
   return ULong.constructor-impl((long)var0);
}

public inline fun Double.toULong(): ULong {
   return UnsignedKt.doubleToULong(var0);
}

public inline fun Float.toULong(): ULong {
   return UnsignedKt.doubleToULong((double)var0);
}

public inline fun Int.toULong(): ULong {
   return ULong.constructor-impl((long)var0);
}

public inline fun Long.toULong(): ULong {
   return ULong.constructor-impl(var0);
}

public inline fun Short.toULong(): ULong {
   return ULong.constructor-impl((long)var0);
}
