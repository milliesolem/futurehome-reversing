package okhttp3.internal

import java.net.IDN
import java.net.InetAddress
import java.util.Arrays
import java.util.Locale
import kotlin.jvm.internal.Intrinsics
import okio.Buffer

private fun String.containsInvalidHostnameAsciiCodes(): Boolean {
   val var3: Int = var0.length();

   for (int var2 = 0; var2 < var3; var2++) {
      val var1: Char = var0.charAt(var2);
      if (var1 <= 31 || var1 >= 127) {
         return true;
      }

      if (StringsKt.indexOf$default(" #%/:?@[\\]", var1, 0, false, 6, null) != -1) {
         return true;
      }
   }

   return false;
}

private fun decodeIpv4Suffix(input: String, pos: Int, limit: Int, address: ByteArray, addressOffset: Int): Boolean {
   var var6: Int = var4;
   var var5: Int = var1;

   while (true) {
      var var9: Boolean = false;
      if (var5 >= var2) {
         if (var6 == var4 + 4) {
            var9 = true;
         }

         return var9;
      }

      if (var6 == var3.length) {
         return false;
      }

      var1 = var5;
      if (var6 != var4) {
         if (var0.charAt(var5) != '.') {
            return false;
         }

         var1 = var5 + 1;
      }

      var5 = var1;

      var var7: Int;
      for (var7 = 0; var5 < var2; var5++) {
         val var8: Char = var0.charAt(var5);
         if (var8 < '0' || var8 > '9') {
            break;
         }

         if (var7 == 0 && var1 != var5) {
            return false;
         }

         var7 = var7 * 10 + var8 - 48;
         if (var7 > 255) {
            return false;
         }
      }

      if (var5 - var1 == 0) {
         return false;
      }

      var3[var6] = (byte)var7;
      var6++;
   }
}

private fun decodeIpv6(input: String, pos: Int, limit: Int): InetAddress? {
   val var8: ByteArray = new byte[16];
   var var3: Int = 0;
   var var4: Int = -1;
   var var6: Int = -1;
   var var5: Int = var1;

   var var7: Int;
   while (true) {
      var1 = var3;
      var7 = var4;
      if (var5 >= var2) {
         break;
      }

      if (var3 == 16) {
         return null;
      }

      var7 = var5 + 2;
      if (var5 + 2 <= var2 && StringsKt.startsWith$default(var0, "::", var5, false, 4, null)) {
         if (var4 != -1) {
            return null;
         }

         var1 = var3 + 2;
         if (var7 == var2) {
            var7 = var1;
            break;
         }

         var4 = var1;
         var3 = var1;
         var1 = var7;
      } else {
         var1 = var5;
         if (var3 != 0) {
            if (!StringsKt.startsWith$default(var0, ":", var5, false, 4, null)) {
               if (!StringsKt.startsWith$default(var0, ".", var5, false, 4, null)) {
                  return null;
               }

               if (!decodeIpv4Suffix(var0, var6, var2, var8, var3 - 2)) {
                  return null;
               }

               var1 = var3 + 2;
               var7 = var4;
               break;
            }

            var1 = var5 + 1;
         }
      }

      var5 = var1;

      for (var6 = 0; var5 < var2; var5++) {
         var7 = Util.parseHexDigit(var0.charAt(var5));
         if (var7 == -1) {
            break;
         }

         var6 = (var6 shl 4) + var7;
      }

      if (var5 - var1 == 0 || var5 - var1 > 4) {
         return null;
      }

      var8[var3] = (byte)(var6 ushr 8 and 255);
      var7 = var3 + 2;
      var8[var3 + 1] = (byte)(var6 and 255);
      var3 = var7;
      var6 = var1;
   }

   if (var1 != 16) {
      if (var7 == -1) {
         return null;
      }

      System.arraycopy(var8, var7, var8, 16 - (var1 - var7), var1 - var7);
      Arrays.fill(var8, var7, 16 - var1 + var7, (byte)0);
   }

   return InetAddress.getByAddress(var8);
}

private fun inet6AddressToAscii(address: ByteArray): String {
   var var3: Int = -1;
   var var1: Int = 0;
   var var2: Int = 0;

   while (var1 < var0.length) {
      var var4: Int = var1;

      while (var4 < 16 && var0[var4] == 0 && var0[var4 + 1] == 0) {
         var4 += 2;
      }

      val var8: Int = var4 - var1;
      var var6: Int = var3;
      var var5: Int = var2;
      if (var8 > var2) {
         var6 = var3;
         var5 = var2;
         if (var8 >= 4) {
            var5 = var8;
            var6 = var1;
         }
      }

      var1 = var4 + 2;
      var3 = var6;
      var2 = var5;
   }

   val var9: Buffer = new Buffer();
   var1 = 0;

   while (var1 < var0.length) {
      if (var1 == var3) {
         var9.writeByte(58);
         val var11: Int = var1 + var2;
         var1 += var2;
         if (var11 == 16) {
            var9.writeByte(58);
            var1 = var11;
         }
      } else {
         if (var1 > 0) {
            var9.writeByte(58);
         }

         var9.writeHexadecimalUnsignedLong((long)(Util.and(var0[var1], 255) shl 8 or Util.and(var0[var1 + 1], 255)));
         var1 += 2;
      }
   }

   return var9.readUtf8();
}

public fun String.toCanonicalHost(): String? {
   Intrinsics.checkParameterIsNotNull(var0, "$this$toCanonicalHost");
   if (StringsKt.contains$default(var0, ":", false, 2, null)) {
      val var11: InetAddress;
      if (StringsKt.startsWith$default(var0, "[", false, 2, null) && StringsKt.endsWith$default(var0, "]", false, 2, null)) {
         var11 = decodeIpv6(var0, 1, var0.length() - 1);
      } else {
         var11 = decodeIpv6(var0, 0, var0.length());
      }

      if (var11 != null) {
         val var14: ByteArray = var11.getAddress();
         if (var14.length == 16) {
            Intrinsics.checkExpressionValueIsNotNull(var14, "address");
            return inet6AddressToAscii(var14);
         } else if (var14.length == 4) {
            return var11.getHostAddress();
         } else {
            val var12: StringBuilder = new StringBuilder("Invalid IPv6 address: '");
            var12.append(var0);
            var12.append('\'');
            throw (new AssertionError(var12.toString())) as java.lang.Throwable;
         }
      } else {
         return null;
      }
   } else {
      var var13: Locale;
      try {
         var0 = IDN.toASCII(var0);
         Intrinsics.checkExpressionValueIsNotNull(var0, "IDN.toASCII(host)");
         var13 = Locale.US;
         Intrinsics.checkExpressionValueIsNotNull(Locale.US, "Locale.US");
      } catch (var6: IllegalArgumentException) {
         return null;
      }

      if (var0 != null) {
         try {
            var0 = var0.toLowerCase(var13);
            Intrinsics.checkExpressionValueIsNotNull(var0, "(this as java.lang.String).toLowerCase(locale)");
            if (var0.length() == 0) {
               return null;
            }
         } catch (var4: IllegalArgumentException) {
            return null;
         }

         try {
            if (!containsInvalidHostnameAsciiCodes(var0)) {
               return var0;
            }
         } catch (var7: IllegalArgumentException) {
            return null;
         }

         return null;
      } else {
         try {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
         } catch (var5: IllegalArgumentException) {
            return null;
         }
      }
   }
}
