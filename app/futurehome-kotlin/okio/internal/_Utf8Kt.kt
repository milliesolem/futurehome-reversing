package okio.internal

import java.util.Arrays
import kotlin.jvm.internal.Intrinsics

public fun String.commonAsUtf8ToByteArray(): ByteArray {
   val var8: ByteArray = new byte[var0.length() * 4];
   var var2: Int = var0.length();

   for (int var3 = 0; var3 < var2; var3++) {
      var var4: Int = var0.charAt(var3);
      if (Intrinsics.compare(var4, 128) >= 0) {
         val var6: Int = var0.length();
         var2 = var3;

         while (var3 < var6) {
            var var5: Int = var0.charAt(var3);
            if (Intrinsics.compare(var5, 128) < 0) {
               var var13: Byte = (byte)var5;
               var4 = var2 + 1;
               var8[var2] = var13;
               var3++;
               var2 = var4;
               var4 = var3;

               while (true) {
                  var5 = var2;
                  var3 = var4;
                  var2 = var2;
                  if (var4 >= var6) {
                     break;
                  }

                  var3 = var4;
                  var2 = var2;
                  if (Intrinsics.compare(var0.charAt(var4), 128) >= 0) {
                     break;
                  }

                  var13 = (byte)var0.charAt(var4);
                  var2++;
                  var8[var5] = var13;
                  var4++;
               }
            } else {
               if (Intrinsics.compare(var5, 2048) < 0) {
                  var8[var2] = (byte)(var5 shr 6 or 192);
                  val var1: Byte = (byte)(var5 and 63 or 128);
                  var4 = var2 + 2;
                  var8[var2 + 1] = var1;
                  var2 = var4;
               } else {
                  if (55296 <= var5 && var5 < 57344) {
                     if (Intrinsics.compare(var5, 56319) <= 0) {
                        val var7: Int = var3 + 1;
                        if (var6 > var3 + 1) {
                           var var20: Int = var0.charAt(var7);
                           if (56320 <= var20 && var20 < 57344) {
                              var20 = (var5 shl 10) + var0.charAt(var7) - 56613888;
                              var8[var2] = (byte)(var20 shr 18 or 240);
                              var8[var2 + 1] = (byte)(var20 shr 12 and 63 or 128);
                              var8[var2 + 2] = (byte)(var20 shr 6 and 63 or 128);
                              val var12: Byte = (byte)(var20 and 63 or 128);
                              var20 = var2 + 4;
                              var8[var2 + 3] = var12;
                              var3 += 2;
                              var2 = var20;
                              continue;
                           }
                        }
                     }

                     var8[var2] = 63;
                     var3++;
                     var2++;
                     continue;
                  }

                  var8[var2] = (byte)(var5 shr 12 or 224);
                  var8[var2 + 1] = (byte)(var5 shr 6 and 63 or 128);
                  val var11: Byte = (byte)(var5 and 63 or 128);
                  var4 = var2 + 3;
                  var8[var2 + 2] = var11;
                  var2 = var4;
               }

               var3++;
            }
         }

         val var9: ByteArray = Arrays.copyOf(var8, var2);
         return var9;
      }

      var8[var3] = (byte)var4;
   }

   val var10: ByteArray = Arrays.copyOf(var8, var0.length());
   return var10;
}

public fun ByteArray.commonToUtf8String(beginIndex: Int = 0, endIndex: Int = var0.length): String {
   var var4: Int = var1;
   if (var1 >= 0 && var2 <= var0.length && var1 <= var2) {
      val var59: CharArray = new char[var2 - var1];
      var1 = 0;

      while (var4 < var2) {
         var var5: Int = var0[var4];
         if (var0[var4] >= 0) {
            var var22: Char = (char)var5;
            var5 = var1 + 1;
            var59[var1] = var22;
            var4++;
            var1 = var5;
            var5 = var4;

            while (true) {
               val var54: Int = var1;
               var4 = var5;
               var1 = var1;
               if (var5 >= var2) {
                  break;
               }

               val var57: Byte = var0[var5];
               var4 = var5;
               var1 = var1;
               if (var57 < 0) {
                  break;
               }

               var5++;
               var22 = (char)var57;
               var1++;
               var59[var54] = var22;
            }
         } else {
            label136: {
               label168: {
                  if (var5 shr 5 == -2) {
                     val var52: Int = var4 + 1;
                     if (var2 <= var4 + 1) {
                        val var18: Char = (char)'�';
                        var5 = var1 + 1;
                        var59[var1] = var18;
                        var1 = var5;
                        break label168;
                     } else {
                        val var53: Byte = var0[var52];
                        if ((var0[var52] and 192) != 128) {
                           val var19: Char = (char)'�';
                           var5 = var1 + 1;
                           var59[var1] = var19;
                           var1 = var5;
                           break label168;
                        }

                        var5 = var5 shl 6 xor var53 xor 3968;
                        if ((var5 shl 6 xor var53 xor 3968) < 128) {
                           val var20: Char = (char)'�';
                           var5 = var1 + 1;
                           var59[var1] = var20;
                           var1 = var5;
                        } else {
                           val var21: Char = (char)var5;
                           var5 = var1 + 1;
                           var59[var1] = var21;
                           var1 = var5;
                        }
                     }
                  } else {
                     label165: {
                        if (var5 shr 4 == -2) {
                           val var7: Int = var4 + 2;
                           if (var2 <= var4 + 2) {
                              val var17: Char = (char)'�';
                              var5 = var1 + 1;
                              var59[var1] = var17;
                              val var48: Int = var4 + 1;
                              var1 = var5;
                              if (var2 <= var48) {
                                 break label168;
                              }

                              var1 = var5;
                              if ((var0[var48] and 192) != 128) {
                                 break label168;
                              }

                              var1 = var5;
                              break label165;
                           }

                           val var6: Byte = var0[var4 + 1];
                           if ((var0[var4 + 1] and 192) != 128) {
                              val var16: Char = (char)'�';
                              var5 = var1 + 1;
                              var59[var1] = var16;
                              var1 = var5;
                              break label168;
                           }

                           val var55: Byte = var0[var7];
                           if ((var0[var7] and 192) != 128) {
                              val var15: Char = (char)'�';
                              var5 = var1 + 1;
                              var59[var1] = var15;
                              var1 = var5;
                              break label165;
                           }

                           var5 = var5 shl 12 xor var55 xor -123008 xor var6 shl 6;
                           if ((var5 shl 12 xor var55 xor -123008 xor var6 shl 6) < 2048) {
                              val var14: Char = (char)'�';
                              var5 = var1 + 1;
                              var59[var1] = var14;
                              var1 = var5;
                           } else if (55296 <= var5 && var5 < 57344) {
                              val var13: Char = (char)'�';
                              var5 = var1 + 1;
                              var59[var1] = var13;
                              var1 = var5;
                           } else {
                              val var3: Char = (char)var5;
                              var5 = var1 + 1;
                              var59[var1] = var3;
                              var1 = var5;
                           }
                        } else {
                           if (var5 shr 3 != -2) {
                              var59[var1] = '�';
                              var4++;
                              var1++;
                              continue;
                           }

                           val var8: Int = var4 + 3;
                           if (var2 <= var4 + 3) {
                              var5 = var1 + 1;
                              var59[var1] = '�';
                              var var49: Int = var4 + 1;
                              var1 = var5;
                              if (var2 <= var49) {
                                 break label168;
                              }

                              var1 = var5;
                              if ((var0[var49] and 192) != 128) {
                                 break label168;
                              }

                              var49 = var4 + 2;
                              var1 = var5;
                              if (var2 <= var49) {
                                 break label165;
                              }

                              var1 = var5;
                              if ((var0[var49] and 192) != 128) {
                                 break label165;
                              }
                           } else {
                              val var51: Byte = var0[var4 + 1];
                              if ((var0[var4 + 1] and 192) != 128) {
                                 var5 = var1 + 1;
                                 var59[var1] = '�';
                                 var1 = var5;
                                 break label168;
                              }

                              val var56: Byte = var0[var4 + 2];
                              if ((var0[var4 + 2] and 192) != 128) {
                                 var5 = var1 + 1;
                                 var59[var1] = '�';
                                 var1 = var5;
                                 break label165;
                              }

                              val var58: Byte = var0[var8];
                              if ((var0[var8] and 192) == 128) {
                                 var5 = var5 shl 18 xor var58 xor 3678080 xor var56 shl 6 xor var51 shl 12;
                                 if ((var5 shl 18 xor var58 xor 3678080 xor var56 shl 6 xor var51 shl 12) > 1114111) {
                                    var5 = var1 + 1;
                                    var59[var1] = '�';
                                    var1 = var5;
                                 } else if (55296 <= var5 && var5 < 57344) {
                                    var5 = var1 + 1;
                                    var59[var1] = '�';
                                    var1 = var5;
                                 } else if (var5 < 65536) {
                                    var5 = var1 + 1;
                                    var59[var1] = '�';
                                    var1 = var5;
                                 } else if (var5 != 65533) {
                                    var59[var1] = (char)((var5 ushr 10) + 55232);
                                    var59[var1 + 1] = (char)((var5 and 1023) + 56320);
                                    var1 += 2;
                                 } else {
                                    var5 = var1 + 1;
                                    var59[var1] = '�';
                                    var1 = var5;
                                 }

                                 var33 = 4;
                                 break label136;
                              }

                              var5 = var1 + 1;
                              var59[var1] = '�';
                           }

                           var1 = var5;
                        }

                        var33 = 3;
                        break label136;
                     }
                  }

                  var33 = 2;
                  break label136;
               }

               var33 = 1;
            }

            var4 += var33;
         }
      }

      return StringsKt.concatToString(var59, 0, var1);
   } else {
      val var9: StringBuilder = new StringBuilder("size=");
      var9.append(var0.length);
      var9.append(" beginIndex=");
      var9.append(var1);
      var9.append(" endIndex=");
      var9.append(var2);
      throw new ArrayIndexOutOfBoundsException(var9.toString());
   }
}

@JvmSynthetic
fun `commonToUtf8String$default`(var0: ByteArray, var1: Int, var2: Int, var3: Int, var4: Any): java.lang.String {
   if ((var3 and 1) != 0) {
      var1 = 0;
   }

   if ((var3 and 2) != 0) {
      var2 = var0.length;
   }

   return commonToUtf8String(var0, var1, var2);
}
