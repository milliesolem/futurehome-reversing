package kotlin.io.encoding

import java.nio.charset.Charset

internal inline fun Base64.platformCharsToBytes(source: CharSequence, startIndex: Int, endIndex: Int): ByteArray {
   val var5: ByteArray;
   if (var1 is java.lang.String) {
      val var6: java.lang.String = var1 as java.lang.String;
      var0.checkSourceBounds$kotlin_stdlib((var1 as java.lang.String).length(), var2, var3);
      val var4: java.lang.String = var6.substring(var2, var3);
      val var7: Charset = Charsets.ISO_8859_1;
      var5 = var4.getBytes(var7);
   } else {
      var5 = var0.charsToBytesImpl$kotlin_stdlib(var1, var2, var3);
   }

   return var5;
}

internal inline fun Base64.platformEncodeIntoByteArray(source: ByteArray, destination: ByteArray, destinationOffset: Int, startIndex: Int, endIndex: Int): Int {
   return var0.encodeIntoByteArrayImpl$kotlin_stdlib(var1, var2, var3, var4, var5);
}

internal inline fun Base64.platformEncodeToByteArray(source: ByteArray, startIndex: Int, endIndex: Int): ByteArray {
   return var0.encodeToByteArrayImpl$kotlin_stdlib(var1, var2, var3);
}

internal inline fun Base64.platformEncodeToString(source: ByteArray, startIndex: Int, endIndex: Int): String {
   return new java.lang.String(var0.encodeToByteArrayImpl$kotlin_stdlib(var1, var2, var3), Charsets.ISO_8859_1);
}
