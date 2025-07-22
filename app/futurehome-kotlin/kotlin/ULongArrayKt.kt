package kotlin

public inline fun ULongArray(size: Int, init: (Int) -> ULong): ULongArray {
   val var3: LongArray = new long[var0];

   for (int var2 = 0; var2 < var0; var2++) {
      var3[var2] = (var1.invoke(var2) as ULong).unbox-impl();
   }

   return ULongArray.constructor-impl(var3);
}

public inline fun ulongArrayOf(elements: ULongArray): ULongArray {
   return var0;
}
