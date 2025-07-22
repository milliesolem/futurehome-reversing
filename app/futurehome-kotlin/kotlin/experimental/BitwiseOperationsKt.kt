package kotlin.experimental

public inline infix fun Byte.and(other: Byte): Byte {
   return (byte)(var0 and var1);
}

public inline infix fun Short.and(other: Short): Short {
   return (short)(var0 and var1);
}

public inline fun Byte.inv(): Byte {
   return (byte)(var0.inv());
}

public inline fun Short.inv(): Short {
   return (short)(var0.inv());
}

public inline infix fun Byte.or(other: Byte): Byte {
   return (byte)(var0 or var1);
}

public inline infix fun Short.or(other: Short): Short {
   return (short)(var0 or var1);
}

public inline infix fun Byte.xor(other: Byte): Byte {
   return (byte)(var0 xor var1);
}

public inline infix fun Short.xor(other: Short): Short {
   return (short)(var0 xor var1);
}
