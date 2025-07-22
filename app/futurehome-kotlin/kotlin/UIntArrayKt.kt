package kotlin

public inline fun UIntArray(size: Int, init: (Int) -> UInt): UIntArray {
   val var3: IntArray = new int[var0];

   for (int var2 = 0; var2 < var0; var2++) {
      var3[var2] = (var1.invoke(var2) as UInt).unbox-impl();
   }

   return UIntArray.constructor-impl(var3);
}

public inline fun uintArrayOf(elements: UIntArray): UIntArray {
   return var0;
}
