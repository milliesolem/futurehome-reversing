package okio

import kotlin.jvm.internal.Intrinsics

internal const val HIGH_SURROGATE_HEADER: Int = 55232
internal const val LOG_SURROGATE_HEADER: Int = 56320
internal const val MASK_2BYTES: Int = 3968
internal const val MASK_3BYTES: Int = -123008
internal const val MASK_4BYTES: Int = 3678080
internal const val REPLACEMENT_BYTE: Byte = 63
internal const val REPLACEMENT_CHARACTER: Char = '�'
internal const val REPLACEMENT_CODE_POINT: Int = 65533

internal inline fun isIsoControl(codePoint: Int): Boolean {
   val var1: Boolean;
   if ((var0 < 0 || var0 >= 32) && (127 > var0 || var0 >= 160)) {
      var1 = false;
   } else {
      var1 = true;
   }

   return var1;
}

internal inline fun isUtf8Continuation(byte: Byte): Boolean {
   val var1: Boolean;
   if ((var0 and 192) == 128) {
      var1 = true;
   } else {
      var1 = false;
   }

   return var1;
}

internal inline fun ByteArray.process2Utf8Bytes(beginIndex: Int, endIndex: Int, yield: (Int) -> Unit): Int {
   val var4: Int = var1 + 1;
   val var5: Int = 65533;
   if (var2 <= var4) {
      var3.invoke(var5);
      return 1;
   } else {
      var var6: Int = var0[var1];
      val var8: Byte = var0[var4];
      if ((var0[var4] and 192) == 128) {
         var6 = var8 xor 3968 xor var6 shl 6;
         if ((var8 xor 3968 xor var6 shl 6) < 128) {
            var3.invoke(var5);
         } else {
            var3.invoke(var6);
         }

         return 2;
      } else {
         var3.invoke(var5);
         return 1;
      }
   }
}

internal inline fun ByteArray.process3Utf8Bytes(beginIndex: Int, endIndex: Int, yield: (Int) -> Unit): Int {
   val var4: Int = var1 + 2;
   val var5: Int = 65533;
   if (var2 <= var4) {
      var3.invoke(var5);
      var1++;
      return if (var2 > var1 && (var0[var1] and 192) == 128) 2 else 1;
   } else {
      val var9: Byte = var0[var1];
      var var6: Int = var0[var1 + 1];
      if ((var0[var1 + 1] and 192) == 128) {
         val var10: Byte = var0[var4];
         if ((var0[var4] and 192) != 128) {
            var3.invoke(var5);
            return 2;
         } else {
            var6 = var10 xor -123008 xor var6 shl 6 xor var9 shl 12;
            if ((var10 xor -123008 xor var6 shl 6 xor var9 shl 12) < 2048) {
               var3.invoke(var5);
            } else if (55296 <= var6 && var6 < 57344) {
               var3.invoke(var5);
            } else {
               var3.invoke(var6);
            }

            return 3;
         }
      } else {
         var3.invoke(var5);
         return 1;
      }
   }
}

internal inline fun ByteArray.process4Utf8Bytes(beginIndex: Int, endIndex: Int, yield: (Int) -> Unit): Int {
   val var4: Int = var1 + 3;
   val var6: Int = 65533;
   if (var2 <= var4) {
      var3.invoke(var6);
      if (var2 > var1 + 1 && (var0[var1 + 1] and 192) == 128) {
         return if (var2 > var1 + 2 && (var0[var1 + 2] and 192) == 128) 3 else 2;
      } else {
         return 1;
      }
   } else {
      val var10: Byte = var0[var1];
      val var5: Byte = var0[var1 + 1];
      if ((var0[var1 + 1] and 192) != 128) {
         var3.invoke(var6);
         return 1;
      } else {
         var var7: Int = var0[var1 + 2];
         if ((var0[var1 + 2] and 192) == 128) {
            val var11: Byte = var0[var4];
            if ((var0[var4] and 192) != 128) {
               var3.invoke(var6);
               return 3;
            } else {
               var7 = var11 xor 3678080 xor var7 shl 6 xor var5 shl 12 xor var10 shl 18;
               if ((var11 xor 3678080 xor var7 shl 6 xor var5 shl 12 xor var10 shl 18) > 1114111) {
                  var3.invoke(var6);
               } else if (55296 <= var7 && var7 < 57344) {
                  var3.invoke(var6);
               } else if (var7 < 65536) {
                  var3.invoke(var6);
               } else {
                  var3.invoke(var7);
               }

               return 4;
            }
         } else {
            var3.invoke(var6);
            return 2;
         }
      }
   }
}

internal inline fun ByteArray.processUtf16Chars(beginIndex: Int, endIndex: Int, yield: (Char) -> Unit) {
   while (var1 < var2) {
      var var5: Int = var0[var1];
      if (var0[var1] >= 0) {
         var3.invoke((char)var5);
         var5 = var1 + 1;

         while (true) {
            var1 = var5;
            if (var5 >= var2) {
               break;
            }

            val var20: Byte = var0[var5];
            var1 = var5;
            if (var20 < 0) {
               break;
            }

            var5++;
            var3.invoke((char)var20);
         }
      } else {
         label146: {
            if (var5 shr 5 == -2) {
               val var23: Int = var1 + 1;
               if (var2 > var1 + 1) {
                  val var24: Byte = var0[var23];
                  if ((var0[var23] and 192) == 128) {
                     var5 = var5 shl 6 xor var24 xor 3968;
                     val var11: Char;
                     if ((var5 shl 6 xor var24 xor 3968) < 128) {
                        var11 = (char)'�';
                     } else {
                        var11 = (char)var5;
                     }

                     var3.invoke(var11);
                     var13 = 2;
                     break label146;
                  }
               }

               var3.invoke((char)'�');
            } else {
               label144: {
                  if (var5 shr 4 == -2) {
                     val var8: Int = var1 + 2;
                     if (var2 <= var1 + 2) {
                        var3.invoke((char)'�');
                        if (var2 > var1 + 1 && (var0[var1 + 1] and 192) == 128) {
                           var13 = 2;
                           break label146;
                        }
                        break label144;
                     }

                     val var7: Byte = var0[var1 + 1];
                     if ((var0[var1 + 1] and 192) != 128) {
                        var3.invoke((char)'�');
                        break label144;
                     }

                     val var25: Byte = var0[var8];
                     if ((var0[var8] and 192) != 128) {
                        var3.invoke((char)'�');
                        var13 = 2;
                        break label146;
                     }

                     var5 = var5 shl 12 xor var25 xor -123008 xor var7 shl 6;
                     val var4: Char;
                     if ((var5 shl 12 xor var25 xor -123008 xor var7 shl 6) >= 2048
                        && (55296 > (var5 shl 12 xor var25 xor -123008 xor var7 shl 6) || (var5 shl 12 xor var25 xor -123008 xor var7 shl 6) >= 57344)) {
                        var4 = (char)var5;
                     } else {
                        var4 = (char)'�';
                     }

                     var3.invoke(var4);
                  } else {
                     if (var5 shr 3 != -2) {
                        var3.invoke('�');
                        var1++;
                        continue;
                     }

                     val var9: Int = var1 + 3;
                     if (var2 <= var1 + 3) {
                        var3.invoke('�');
                        if (var2 <= var1 + 1 || (var0[var1 + 1] and 192) != 128) {
                           break label144;
                        }

                        val var21: Int = var1 + 2;
                        var13 = 2;
                        if (var2 <= var21) {
                           break label146;
                        }

                        var13 = 2;
                        if ((var0[var21] and 192) != 128) {
                           break label146;
                        }
                     } else {
                        val var26: Byte = var0[var1 + 1];
                        if ((var0[var1 + 1] and 192) != 128) {
                           var3.invoke('�');
                           break label144;
                        }

                        val var22: Byte = var0[var1 + 2];
                        if ((var0[var1 + 2] and 192) != 128) {
                           var3.invoke('�');
                           var13 = 2;
                           break label146;
                        }

                        val var19: Byte = var0[var9];
                        if ((var0[var9] and 192) == 128) {
                           var5 = var5 shl 18 xor var19 xor 3678080 xor var22 shl 6 xor var26 shl 12;
                           if ((var5 shl 18 xor var19 xor 3678080 xor var22 shl 6 xor var26 shl 12) <= 1114111
                              && (
                                 55296 > (var5 shl 18 xor var19 xor 3678080 xor var22 shl 6 xor var26 shl 12)
                                    || (var5 shl 18 xor var19 xor 3678080 xor var22 shl 6 xor var26 shl 12) >= 57344
                              )
                              && (var5 shl 18 xor var19 xor 3678080 xor var22 shl 6 xor var26 shl 12) >= 65536
                              && (var5 shl 18 xor var19 xor 3678080 xor var22 shl 6 xor var26 shl 12) != 65533) {
                              var3.invoke((char)((var5 ushr 10) + 55232));
                              var3.invoke((char)((var5 and 1023) + 56320));
                           } else {
                              var3.invoke('�');
                           }

                           var13 = 4;
                           break label146;
                        }

                        var3.invoke('�');
                     }
                  }

                  var13 = 3;
                  break label146;
               }
            }

            var13 = 1;
         }

         var1 += var13;
      }
   }
}

internal inline fun String.processUtf8Bytes(beginIndex: Int, endIndex: Int, yield: (Byte) -> Unit) {
   while (var1 < var2) {
      var var4: Int = var0.charAt(var1);
      if (Intrinsics.compare(var4, 128) < 0) {
         var3.invoke((byte)var4);
         var4 = var1 + 1;

         while (true) {
            var1 = var4;
            if (var4 >= var2) {
               break;
            }

            var1 = var4;
            if (Intrinsics.compare(var0.charAt(var4), 128) >= 0) {
               break;
            }

            var3.invoke((byte)var0.charAt(var4));
            var4++;
         }
      } else {
         if (Intrinsics.compare(var4, 2048) < 0) {
            var3.invoke((byte)(var4 shr 6 or 192));
            var3.invoke((byte)(var4 and 63 or 128));
         } else if (55296 <= var4 && var4 < 57344) {
            if (Intrinsics.compare(var4, 56319) <= 0) {
               val var5: Int = var1 + 1;
               if (var2 > var1 + 1) {
                  val var6: Char = var0.charAt(var5);
                  if ('\udc00' <= var6 && var6 < '\ue000') {
                     var4 = (var4 shl 10) + var0.charAt(var5) - 56613888;
                     var3.invoke((byte)(var4 shr 18 or 240));
                     var3.invoke((byte)(var4 shr 12 and 63 or 128));
                     var3.invoke((byte)(var4 shr 6 and 63 or 128));
                     var3.invoke((byte)(var4 and 63 or 128));
                     var1 += 2;
                     continue;
                  }
               }
            }

            var3.invoke((byte)63);
         } else {
            var3.invoke((byte)(var4 shr 12 or 224));
            var3.invoke((byte)(var4 shr 6 and 63 or 128));
            var3.invoke((byte)(var4 and 63 or 128));
         }

         var1++;
      }
   }
}

internal inline fun ByteArray.processUtf8CodePoints(beginIndex: Int, endIndex: Int, yield: (Int) -> Unit) {
   while (var1 < var2) {
      var var4: Int = var0[var1];
      if (var0[var1] >= 0) {
         var3.invoke(var4);
         var4 = var1 + 1;

         while (true) {
            var1 = var4;
            if (var4 >= var2) {
               break;
            }

            val var18: Byte = var0[var4];
            var1 = var4;
            if (var18 < 0) {
               break;
            }

            var4++;
            var3.invoke(Integer.valueOf(var18));
         }
      } else {
         label144: {
            if (var4 shr 5 == -2) {
               val var21: Int = var1 + 1;
               if (var2 > var1 + 1) {
                  val var22: Byte = var0[var21];
                  if ((var0[var21] and 192) == 128) {
                     var4 = var4 shl 6 xor var22 xor 3968;
                     val var36: Int;
                     if ((var4 shl 6 xor var22 xor 3968) < 128) {
                        var36 = 65533;
                     } else {
                        var36 = var4;
                     }

                     var3.invoke(var36);
                     var11 = 2;
                     break label144;
                  }
               }

               var3.invoke(65533);
            } else {
               label142: {
                  if (var4 shr 4 == -2) {
                     val var7: Int = var1 + 2;
                     if (var2 <= var1 + 2) {
                        var3.invoke(65533);
                        if (var2 > var1 + 1 && (var0[var1 + 1] and 192) == 128) {
                           var11 = 2;
                           break label144;
                        }
                        break label142;
                     }

                     val var6: Byte = var0[var1 + 1];
                     if ((var0[var1 + 1] and 192) != 128) {
                        var3.invoke(65533);
                        break label142;
                     }

                     val var23: Byte = var0[var7];
                     if ((var0[var7] and 192) != 128) {
                        var3.invoke(65533);
                        var11 = 2;
                        break label144;
                     }

                     var4 = var4 shl 12 xor var23 xor -123008 xor var6 shl 6;
                     val var9: Int;
                     if ((var4 shl 12 xor var23 xor -123008 xor var6 shl 6) >= 2048
                        && (55296 > (var4 shl 12 xor var23 xor -123008 xor var6 shl 6) || (var4 shl 12 xor var23 xor -123008 xor var6 shl 6) >= 57344)) {
                        var9 = var4;
                     } else {
                        var9 = 65533;
                     }

                     var3.invoke(var9);
                  } else {
                     if (var4 shr 3 != -2) {
                        var3.invoke(65533);
                        var1++;
                        continue;
                     }

                     val var8: Int = var1 + 3;
                     if (var2 <= var1 + 3) {
                        var3.invoke(65533);
                        if (var2 <= var1 + 1 || (var0[var1 + 1] and 192) != 128) {
                           break label142;
                        }

                        val var19: Int = var1 + 2;
                        var11 = 2;
                        if (var2 <= var19) {
                           break label144;
                        }

                        var11 = 2;
                        if ((var0[var19] and 192) != 128) {
                           break label144;
                        }
                     } else {
                        val var20: Byte = var0[var1 + 1];
                        if ((var0[var1 + 1] and 192) != 128) {
                           var3.invoke(65533);
                           break label142;
                        }

                        val var24: Byte = var0[var1 + 2];
                        if ((var0[var1 + 2] and 192) != 128) {
                           var3.invoke(65533);
                           var11 = 2;
                           break label144;
                        }

                        val var17: Byte = var0[var8];
                        if ((var0[var8] and 192) == 128) {
                           var4 = var4 shl 18 xor var17 xor 3678080 xor var24 shl 6 xor var20 shl 12;
                           val var31: Int;
                           if ((var4 shl 18 xor var17 xor 3678080 xor var24 shl 6 xor var20 shl 12) <= 1114111
                              && (
                                 55296 > (var4 shl 18 xor var17 xor 3678080 xor var24 shl 6 xor var20 shl 12)
                                    || (var4 shl 18 xor var17 xor 3678080 xor var24 shl 6 xor var20 shl 12) >= 57344
                              )
                              && (var4 shl 18 xor var17 xor 3678080 xor var24 shl 6 xor var20 shl 12) >= 65536) {
                              var31 = var4;
                           } else {
                              var31 = 65533;
                           }

                           var3.invoke(var31);
                           var11 = 4;
                           break label144;
                        }

                        var3.invoke(65533);
                     }
                  }

                  var11 = 3;
                  break label144;
               }
            }

            var11 = 1;
         }

         var1 += var11;
      }
   }
}

fun size(var0: java.lang.String): Long {
   return size$default(var0, 0, 0, 3, null);
}

fun size(var0: java.lang.String, var1: Int): Long {
   return size$default(var0, var1, 0, 2, null);
}

public fun String.utf8Size(beginIndex: Int = ..., endIndex: Int = ...): Long {
   if (var1 < 0) {
      val var10: StringBuilder = new StringBuilder("beginIndex < 0: ");
      var10.append(var1);
      throw new IllegalArgumentException(var10.toString().toString());
   } else if (var2 < var1) {
      val var9: StringBuilder = new StringBuilder("endIndex < beginIndex: ");
      var9.append(var2);
      var9.append(" < ");
      var9.append(var1);
      throw new IllegalArgumentException(var9.toString().toString());
   } else if (var2 > var0.length()) {
      val var8: StringBuilder = new StringBuilder("endIndex > string.length: ");
      var8.append(var2);
      var8.append(" > ");
      var8.append(var0.length());
      throw new IllegalArgumentException(var8.toString().toString());
   } else {
      var var6: Long = 0L;

      while (var1 < var2) {
         val var5: Char = var0.charAt(var1);
         if (var5 < 128) {
            var6++;
         } else {
            val var3: Byte;
            if (var5 < 2048) {
               var3 = 2;
            } else {
               if (var5 >= '\ud800' && var5 <= '\udfff') {
                  val var4: Int = var1 + 1;
                  val var11: Char;
                  if (var1 + 1 < var2) {
                     var11 = var0.charAt(var4);
                  } else {
                     var11 = 0;
                  }

                  if (var5 <= '\udbff' && var11 >= '\udc00' && var11 <= '\udfff') {
                     var6 += 4;
                     var1 += 2;
                     continue;
                  }

                  var6++;
                  var1 = var4;
                  continue;
               }

               var3 = 3;
            }

            var6 += var3;
         }

         var1++;
      }

      return var6;
   }
}

@JvmSynthetic
fun `size$default`(var0: java.lang.String, var1: Int, var2: Int, var3: Int, var4: Any): Long {
   if ((var3 and 1) != 0) {
      var1 = 0;
   }

   if ((var3 and 2) != 0) {
      var2 = var0.length();
   }

   return size(var0, var1, var2);
}
