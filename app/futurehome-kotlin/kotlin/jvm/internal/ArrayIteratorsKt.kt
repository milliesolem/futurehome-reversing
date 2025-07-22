package kotlin.jvm.internal

public fun iterator(array: BooleanArray): BooleanIterator {
   return new ArrayBooleanIterator(var0);
}

public fun iterator(array: ByteArray): ByteIterator {
   return new ArrayByteIterator(var0);
}

public fun iterator(array: CharArray): CharIterator {
   return new ArrayCharIterator(var0);
}

public fun iterator(array: DoubleArray): DoubleIterator {
   return new ArrayDoubleIterator(var0);
}

public fun iterator(array: FloatArray): FloatIterator {
   return new ArrayFloatIterator(var0);
}

public fun iterator(array: IntArray): IntIterator {
   return new ArrayIntIterator(var0);
}

public fun iterator(array: LongArray): LongIterator {
   return new ArrayLongIterator(var0);
}

public fun iterator(array: ShortArray): ShortIterator {
   return new ArrayShortIterator(var0);
}
