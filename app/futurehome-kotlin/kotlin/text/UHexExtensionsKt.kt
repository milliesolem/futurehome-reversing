package kotlin.text

public inline fun String.hexToUByte(format: HexFormat = ...): UByte {
   return UByte.constructor-impl(HexExtensionsKt.hexToByte(var0, var1));
}

@JvmSynthetic
fun `hexToUByte$default`(var0: java.lang.String, var1: HexFormat, var2: Int, var3: Any): Byte {
   if ((var2 and 1) != 0) {
      var1 = HexFormat.Companion.getDefault();
   }

   return UByte.constructor-impl(HexExtensionsKt.hexToByte(var0, var1));
}

public inline fun String.hexToUByteArray(format: HexFormat = ...): UByteArray {
   return UByteArray.constructor-impl(HexExtensionsKt.hexToByteArray(var0, var1));
}

@JvmSynthetic
fun `hexToUByteArray$default`(var0: java.lang.String, var1: HexFormat, var2: Int, var3: Any): ByteArray {
   if ((var2 and 1) != 0) {
      var1 = HexFormat.Companion.getDefault();
   }

   return UByteArray.constructor-impl(HexExtensionsKt.hexToByteArray(var0, var1));
}

public inline fun String.hexToUInt(format: HexFormat = ...): UInt {
   return UInt.constructor-impl(HexExtensionsKt.hexToInt(var0, var1));
}

@JvmSynthetic
fun `hexToUInt$default`(var0: java.lang.String, var1: HexFormat, var2: Int, var3: Any): Int {
   if ((var2 and 1) != 0) {
      var1 = HexFormat.Companion.getDefault();
   }

   return UInt.constructor-impl(HexExtensionsKt.hexToInt(var0, var1));
}

public inline fun String.hexToULong(format: HexFormat = ...): ULong {
   return ULong.constructor-impl(HexExtensionsKt.hexToLong(var0, var1));
}

@JvmSynthetic
fun `hexToULong$default`(var0: java.lang.String, var1: HexFormat, var2: Int, var3: Any): Long {
   if ((var2 and 1) != 0) {
      var1 = HexFormat.Companion.getDefault();
   }

   return ULong.constructor-impl(HexExtensionsKt.hexToLong(var0, var1));
}

public inline fun String.hexToUShort(format: HexFormat = ...): UShort {
   return UShort.constructor-impl(HexExtensionsKt.hexToShort(var0, var1));
}

@JvmSynthetic
fun `hexToUShort$default`(var0: java.lang.String, var1: HexFormat, var2: Int, var3: Any): Short {
   if ((var2 and 1) != 0) {
      var1 = HexFormat.Companion.getDefault();
   }

   return UShort.constructor-impl(HexExtensionsKt.hexToShort(var0, var1));
}

public inline fun UInt.toHexString(format: HexFormat = ...): String {
   return HexExtensionsKt.toHexString(var0, var1);
}

@JvmSynthetic
fun `toHexString-8M7LxHw$default`(var0: Int, var1: HexFormat, var2: Int, var3: Any): java.lang.String {
   if ((var2 and 1) != 0) {
      var1 = HexFormat.Companion.getDefault();
   }

   return HexExtensionsKt.toHexString(var0, var1);
}

public inline fun ULong.toHexString(format: HexFormat = ...): String {
   return HexExtensionsKt.toHexString(var0, var2);
}

@JvmSynthetic
fun `toHexString-8UJCm-I$default`(var0: Long, var2: HexFormat, var3: Int, var4: Any): java.lang.String {
   if ((var3 and 1) != 0) {
      var2 = HexFormat.Companion.getDefault();
   }

   return HexExtensionsKt.toHexString(var0, var2);
}

public inline fun UByte.toHexString(format: HexFormat = ...): String {
   return HexExtensionsKt.toHexString(var0, var1);
}

@JvmSynthetic
fun `toHexString-ZQbaR00$default`(var0: Byte, var1: HexFormat, var2: Int, var3: Any): java.lang.String {
   if ((var2 and 1) != 0) {
      var1 = HexFormat.Companion.getDefault();
   }

   return HexExtensionsKt.toHexString(var0, var1);
}

public inline fun UByteArray.toHexString(startIndex: Int = ..., endIndex: Int = ..., format: HexFormat = ...): String {
   return HexExtensionsKt.toHexString(var0, var1, var2, var3);
}

@JvmSynthetic
fun `toHexString-lZCiFrA$default`(var0: ByteArray, var1: Int, var2: Int, var3: HexFormat, var4: Int, var5: Any): java.lang.String {
   if ((var4 and 1) != 0) {
      var1 = 0;
   }

   if ((var4 and 2) != 0) {
      var2 = UByteArray.getSize-impl(var0);
   }

   if ((var4 and 4) != 0) {
      var3 = HexFormat.Companion.getDefault();
   }

   return HexExtensionsKt.toHexString(var0, var1, var2, var3);
}

public inline fun UShort.toHexString(format: HexFormat = ...): String {
   return HexExtensionsKt.toHexString(var0, var1);
}

@JvmSynthetic
fun `toHexString-r3ox_E0$default`(var0: Short, var1: HexFormat, var2: Int, var3: Any): java.lang.String {
   if ((var2 and 1) != 0) {
      var1 = HexFormat.Companion.getDefault();
   }

   return HexExtensionsKt.toHexString(var0, var1);
}

public inline fun UByteArray.toHexString(format: HexFormat = ...): String {
   return HexExtensionsKt.toHexString(var0, var1);
}

@JvmSynthetic
fun `toHexString-zHuV2wU$default`(var0: ByteArray, var1: HexFormat, var2: Int, var3: Any): java.lang.String {
   if ((var2 and 1) != 0) {
      var1 = HexFormat.Companion.getDefault();
   }

   return HexExtensionsKt.toHexString(var0, var1);
}
