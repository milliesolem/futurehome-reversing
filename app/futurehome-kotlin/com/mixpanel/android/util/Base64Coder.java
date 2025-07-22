package com.mixpanel.android.util;

public class Base64Coder {
   private static final char[] map1 = new char[64];
   private static final byte[] map2;

   static {
      byte var3 = 0;
      char var0 = 'A';

      int var1;
      for (var1 = 0; var0 <= 'Z'; var1++) {
         map1[var1] = var0++;
      }

      for (char var5 = 'a'; var5 <= 'z'; var1++) {
         map1[var1] = var5++;
      }

      for (char var6 = '0'; var6 <= '9'; var1++) {
         map1[var1] = var6++;
      }

      char[] var4 = map1;
      var4[var1] = '+';
      var4[var1 + 1] = '/';
      map2 = new byte[128];
      var1 = 0;

      while (true) {
         byte[] var8 = map2;
         int var2 = var3;
         if (var1 >= var8.length) {
            while (var2 < 64) {
               map2[map1[var2]] = (byte)var2;
               var2++;
            }

            return;
         }

         var8[var1] = -1;
         var1++;
      }
   }

   public static byte[] decode(String var0) {
      return decode(var0.toCharArray());
   }

   public static byte[] decode(char[] var0) {
      int var4 = var0.length;
      if (var4 % 4 != 0) {
         throw new IllegalArgumentException("Length of Base64 encoded input string is not a multiple of 4.");
      } else {
         while (var4 > 0 && var0[var4 - 1] == '=') {
            var4--;
         }

         int var7 = var4 * 3 / 4;
         byte[] var10 = new byte[var7];
         int var2 = 0;
         int var1 = 0;

         while (var2 < var4) {
            char var9 = var0[var2];
            int var3 = var2 + 2;
            char var8 = var0[var2 + 1];
            char var6 = 'A';
            char var5;
            if (var3 < var4) {
               var2 += 3;
               var5 = var0[var3];
               var3 = var2;
            } else {
               var5 = 65;
            }

            var2 = var3;
            if (var3 < var4) {
               var6 = var0[var3];
               var2 = var3 + 1;
            }

            if (var9 <= 127 && var8 <= 127 && var5 <= 127 && var6 <= 127) {
               byte[] var11 = map2;
               int var13 = var11[var9];
               byte var18 = var11[var8];
               byte var17 = var11[var5];
               byte var16 = var11[var6];
               if (var13 >= 0 && var18 >= 0 && var17 >= 0 && var16 >= 0) {
                  var5 = var1 + 1;
                  var10[var1] = (byte)(var13 << 2 | var18 >>> 4);
                  var13 = var5;
                  if (var5 < var7) {
                     var10[var5] = (byte)((var18 & 15) << 4 | var17 >>> 2);
                     var13 = var1 + 2;
                  }

                  if (var13 < var7) {
                     var1 = var13 + 1;
                     var10[var13] = (byte)((var17 & 3) << 6 | var16);
                  } else {
                     var1 = var13;
                  }
                  continue;
               }

               throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
            }

            throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
         }

         return var10;
      }
   }

   public static String decodeString(String var0) {
      return new String(decode(var0));
   }

   public static char[] encode(byte[] var0) {
      return encode(var0, var0.length);
   }

   public static char[] encode(byte[] var0, int var1) {
      int var9 = (var1 * 4 + 2) / 3;
      char[] var11 = new char[(var1 + 2) / 3 * 4];
      int var4 = 0;

      for (byte var5 = 0; var4 < var1; var5 += 4) {
         int var6 = var4 + 1;
         byte var10 = var0[var4];
         if (var6 < var1) {
            var4 += 2;
            var6 = var0[var6] & 255;
         } else {
            var4 = var6;
            var6 = 0;
         }

         int var7;
         if (var4 < var1) {
            int var8 = var4 + 1;
            var7 = var0[var4] & 255;
            var4 = var8;
         } else {
            var7 = 0;
         }

         char[] var12 = map1;
         var11[var5] = var12[(var10 & 255) >>> 2];
         int var16 = var5 + 2;
         var11[var5 + 1] = var12[(var10 & 3) << 4 | var6 >>> 4];
         byte var3 = 61;
         char var2;
         if (var16 < var9) {
            var2 = var12[(var6 & 15) << 2 | var7 >>> 6];
         } else {
            var2 = '=';
         }

         var11[var16] = var2;
         var6 = var5 + 3;
         var2 = (char)var3;
         if (var6 < var9) {
            var2 = var12[var7 & 63];
         }

         var11[var6] = var2;
      }

      return var11;
   }

   public static String encodeString(String var0) {
      return new String(encode(var0.getBytes()));
   }
}
