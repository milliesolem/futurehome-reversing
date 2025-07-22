package kotlin

public inline fun UShortArray(size: Int, init: (Int) -> UShort): UShortArray {
   val var3: ShortArray = new short[var0];

   for (int var2 = 0; var2 < var0; var2++) {
      var3[var2] = (var1.invoke(var2) as UShort).unbox-impl();
   }

   return UShortArray.constructor-impl(var3);
}

public inline fun ushortArrayOf(elements: UShortArray): UShortArray {
   return var0;
}
