package okio

import java.util.Arrays

internal final val BASE64: ByteArray = ByteString.Companion.encodeUtf8("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/").getData$okio()
internal final val BASE64_URL_SAFE: ByteArray =
   ByteString.Companion.encodeUtf8("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_").getData$okio()

internal fun String.decodeBase64ToArray(): ByteArray? {
   var var3: Int;
   for (var3 = var0.length(); var3 > 0; var3--) {
      val var1: Char = var0.charAt(var3 - 1);
      if (var1 != '=' && var1 != '\n' && var1 != '\r' && var1 != ' ' && var1 != '\t') {
         break;
      }
   }

   val var9: Int = (int)(var3 * 6L / 8L);
   val var11: ByteArray = new byte[(int)(var3 * 6L / 8L)];
   var var4: Int = 0;
   var var6: Int = 0;
   var var5: Int = 0;
   var var2: Int = 0;

   while (var4 < var3) {
      var var7: Int;
      var var8: Int;
      var var14: Int;
      label111: {
         val var10: Char = var0.charAt(var4);
         if ('A' <= var10 && var10 < '[') {
            var14 = var10 - 'A';
         } else if ('a' <= var10 && var10 < '{') {
            var14 = var10 - 'G';
         } else if ('0' <= var10 && var10 < ':') {
            var14 = var10 + 4;
         } else if (var10 != '+' && var10 != '-') {
            if (var10 != '/' && var10 != '_') {
               var8 = var6;
               var14 = var5;
               var7 = var2;
               if (var10 != '\n') {
                  var8 = var6;
                  var14 = var5;
                  var7 = var2;
                  if (var10 != '\r') {
                     var8 = var6;
                     var14 = var5;
                     var7 = var2;
                     if (var10 != ' ') {
                        if (var10 != '\t') {
                           return null;
                        }

                        var8 = var6;
                        var14 = var5;
                        var7 = var2;
                     }
                  }
               }
               break label111;
            }

            var14 = 63;
         } else {
            var14 = 62;
         }

         var5 = var5 shl 6 or var14;
         var8 = ++var6;
         var14 = var5;
         var7 = var2;
         if (var6 % 4 == 0) {
            var11[var2] = (byte)(var5 shr 16);
            var11[var2 + 1] = (byte)(var5 shr 8);
            var7 = var2 + 3;
            var11[var2 + 2] = (byte)var5;
            var14 = var5;
            var8 = var6;
         }
      }

      var4++;
      var6 = var8;
      var5 = var14;
      var2 = var7;
   }

   var var15: Int = var6 % 4;
   if (var6 % 4 != 1) {
      if (var15 != 2) {
         if (var15 == 3) {
            var3 = var5 shl 6;
            var11[var2] = (byte)((var5 shl 6) shr 16);
            var15 = var2 + 2;
            var11[var2 + 1] = (byte)(var3 shr 8);
            var2 = var15;
         }
      } else {
         var11[var2] = (byte)((var5 shl 12) shr 16);
         var2++;
      }

      if (var2 == var9) {
         return var11;
      } else {
         val var12: ByteArray = Arrays.copyOf(var11, var2);
         return var12;
      }
   } else {
      return null;
   }
}

internal fun ByteArray.encodeBase64(map: ByteArray = BASE64): String {
   val var8: ByteArray = new byte[(var0.length + 2) / 3 * 4];
   val var6: Int = var0.length - var0.length % 3;
   var var2: Int = 0;
   var var3: Int = 0;

   while (var2 < var6) {
      val var7: Byte = var0[var2];
      var var5: Int = var0[var2 + 1];
      val var4: Int = var2 + 3;
      val var9: Byte = var0[var2 + 2];
      var8[var3] = var1[(var7 and 255) shr 2];
      var8[var3 + 1] = var1[(var7 and 3) shl 4 or (var5 and 255) shr 4];
      var8[var3 + 2] = var1[(var5 and 15) shl 2 or (var9 and 255) shr 6];
      var5 = var3 + 4;
      var8[var3 + 3] = var1[var9 and 63];
      var2 = var4;
      var3 = var5;
   }

   val var12: Int = var0.length - var6;
   if (var0.length - var6 != 1) {
      if (var12 == 2) {
         val var13: Byte = var0[var2];
         val var10: Byte = var0[var2 + 1];
         var8[var3] = var1[(var13 and 255) shr 2];
         var8[var3 + 1] = var1[(var13 and 3) shl 4 or (var10 and 255) shr 4];
         var8[var3 + 2] = var1[(var10 and 15) shl 2];
         var8[var3 + 3] = 61;
      }
   } else {
      var8[var3] = var1[(var0[var2] and 255) shr 2];
      var8[var3 + 1] = var1[(var0[var2] and 3) shl 4];
      var8[var3 + 2] = 61;
      var8[var3 + 3] = 61;
   }

   return _JvmPlatformKt.toUtf8String(var8);
}

@JvmSynthetic
fun `encodeBase64$default`(var0: ByteArray, var1: ByteArray, var2: Int, var3: Any): java.lang.String {
   if ((var2 and 1) != 0) {
      var1 = BASE64;
   }

   return encodeBase64(var0, var1);
}
