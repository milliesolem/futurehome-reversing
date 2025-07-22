package kotlin

public inline fun UByteArray(size: Int, init: (Int) -> UByte): UByteArray {
   val var3: ByteArray = new byte[var0];

   for (int var2 = 0; var2 < var0; var2++) {
      var3[var2] = (var1.invoke(var2) as UByte).unbox-impl();
   }

   return UByteArray.constructor-impl(var3);
}

public inline fun ubyteArrayOf(elements: UByteArray): UByteArray {
   return var0;
}
