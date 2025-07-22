package kotlin.text

import kotlin.jvm.internal.Intrinsics

internal class StringsKt__StringNumberConversionsKt : StringsKt__StringNumberConversionsJVMKt {
   open fun StringsKt__StringNumberConversionsKt() {
   }

   @JvmStatic
   internal fun numberFormatError(input: String): Nothing {
      val var1: StringBuilder = new StringBuilder("Invalid number format: '");
      var1.append(var0);
      var1.append('\'');
      throw new NumberFormatException(var1.toString());
   }

   @JvmStatic
   public fun String.toByteOrNull(): Byte? {
      return StringsKt.toByteOrNull(var0, 10);
   }

   @JvmStatic
   public fun String.toByteOrNull(radix: Int): Byte? {
      val var2: Int = StringsKt.toIntOrNull(var0, var1);
      if (var2 != null) {
         var1 = var2;
         if (var1 >= -128 && var1 <= 127) {
            return (byte)var1;
         }
      }

      return null;
   }

   @JvmStatic
   public fun String.toIntOrNull(): Int? {
      return StringsKt.toIntOrNull(var0, 10);
   }

   @JvmStatic
   public fun String.toIntOrNull(radix: Int): Int? {
      CharsKt.checkRadix(var1);
      val var8: Int = var0.length();
      if (var8 == 0) {
         return null;
      } else {
         var var6: Int = 0;
         val var2: Char = var0.charAt(0);
         var var3: Int = Intrinsics.compare(var2, 48);
         var var4: Int = -2147483647;
         val var11: Boolean;
         if (var3 < 0) {
            var3 = 1;
            if (var8 == 1) {
               return null;
            }

            if (var2 != '+') {
               if (var2 != '-') {
                  return null;
               }

               var4 = Integer.MIN_VALUE;
               var11 = true;
            } else {
               var11 = false;
            }
         } else {
            var11 = false;
            var3 = 0;
         }

         var var7: Int = -59652323;

         while (true) {
            if (var3 >= var8) {
               val var10: Int;
               if (var11) {
                  var10 = var6;
               } else {
                  var10 = -var6;
               }

               return var10;
            }

            val var9: Int = CharsKt.digitOf(var0.charAt(var3), var1);
            if (var9 < 0) {
               return null;
            }

            var var5: Int = var7;
            if (var6 < var7) {
               if (var7 != -59652323) {
                  break;
               }

               var7 = var4 / var1;
               var5 = var4 / var1;
               if (var6 < var7) {
                  break;
               }
            }

            var6 = var6 * var1;
            if (var6 * var1 < var4 + var9) {
               return null;
            }

            var6 = var6 - var9;
            var3++;
            var7 = var5;
         }

         return null;
      }
   }

   @JvmStatic
   public fun String.toLongOrNull(): Long? {
      return StringsKt.toLongOrNull(var0, 10);
   }

   @JvmStatic
   public fun String.toLongOrNull(radix: Int): Long? {
      CharsKt.checkRadix(var1);
      val var4: Int = var0.length();
      if (var4 == 0) {
         return null;
      } else {
         var var2: Int;
         var var6: Long;
         var var15: Int;
         label56: {
            var2 = 0;
            val var5: Char = var0.charAt(0);
            var15 = Intrinsics.compare(var5, 48);
            var6 = -9223372036854775807L;
            if (var15 < 0) {
               var15 = 1;
               if (var4 == 1) {
                  return null;
               }

               if (var5 != '+') {
                  if (var5 != '-') {
                     return null;
                  }

                  var6 = java.lang.Long.MIN_VALUE;
                  var2 = 1;
                  break label56;
               }

               var2 = 1;
            }

            var15 = 0;
         }

         var var10: Long = 0L;
         var var12: Long = -256204778801521550L;

         while (true) {
            if (var2 >= var4) {
               val var14: java.lang.Long;
               if (var15) {
                  var14 = var10;
               } else {
                  var14 = -var10;
               }

               return var14;
            }

            val var16: Int = CharsKt.digitOf(var0.charAt(var2), var1);
            if (var16 < 0) {
               return null;
            }

            var var8: Long = var12;
            if (var10 < var12) {
               if (var12 != -256204778801521550L) {
                  break;
               }

               var12 = var6 / var1;
               var8 = var6 / var1;
               if (var10 < var12) {
                  break;
               }
            }

            var10 = var10 * var1;
            var12 = var16;
            if (var10 < var6 + var16) {
               return null;
            }

            var10 = var10 - var12;
            var2++;
            var12 = var8;
         }

         return null;
      }
   }

   @JvmStatic
   public fun String.toShortOrNull(): Short? {
      return StringsKt.toShortOrNull(var0, 10);
   }

   @JvmStatic
   public fun String.toShortOrNull(radix: Int): Short? {
      val var2: Int = StringsKt.toIntOrNull(var0, var1);
      if (var2 != null) {
         var1 = var2;
         if (var1 >= -32768 && var1 <= 32767) {
            return (short)var1;
         }
      }

      return null;
   }
}
