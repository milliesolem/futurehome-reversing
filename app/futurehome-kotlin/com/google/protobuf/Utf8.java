package com.google.protobuf;

import java.nio.ByteBuffer;
import java.util.Arrays;

final class Utf8 {
   private static final long ASCII_MASK_LONG = -9187201950435737472L;
   static final int COMPLETE = 0;
   static final int MALFORMED = -1;
   static final int MAX_BYTES_PER_CHAR = 3;
   private static final int UNSAFE_COUNT_ASCII_THRESHOLD = 16;
   private static final Utf8.Processor processor;

   static {
      Object var0;
      if (Utf8.UnsafeProcessor.isAvailable() && !Android.isOnAndroidDevice()) {
         var0 = new Utf8.UnsafeProcessor();
      } else {
         var0 = new Utf8.SafeProcessor();
      }

      processor = (Utf8.Processor)var0;
   }

   private Utf8() {
   }

   static String decodeUtf8(ByteBuffer var0, int var1, int var2) throws InvalidProtocolBufferException {
      return processor.decodeUtf8(var0, var1, var2);
   }

   static String decodeUtf8(byte[] var0, int var1, int var2) throws InvalidProtocolBufferException {
      return processor.decodeUtf8(var0, var1, var2);
   }

   static int encode(CharSequence var0, byte[] var1, int var2, int var3) {
      return processor.encodeUtf8(var0, var1, var2, var3);
   }

   static void encodeUtf8(CharSequence var0, ByteBuffer var1) {
      processor.encodeUtf8(var0, var1);
   }

   static int encodedLength(CharSequence var0) {
      int var4 = var0.length();
      int var2 = 0;

      while (var2 < var4 && var0.charAt(var2) < 128) {
         var2++;
      }

      int var1 = var4;

      int var3;
      while (true) {
         var3 = var1;
         if (var2 >= var4) {
            break;
         }

         char var6 = var0.charAt(var2);
         if (var6 >= 2048) {
            var3 = var1 + encodedLengthGeneral(var0, var2);
            break;
         }

         var1 += 127 - var6 >>> 31;
         var2++;
      }

      if (var3 >= var4) {
         return var3;
      } else {
         StringBuilder var5 = new StringBuilder("UTF-8 length does not fit in int: ");
         var5.append(var3 + 4294967296L);
         throw new IllegalArgumentException(var5.toString());
      }
   }

   private static int encodedLengthGeneral(CharSequence var0, int var1) {
      int var5 = var0.length();
      int var2 = 0;

      while (var1 < var5) {
         char var6 = var0.charAt(var1);
         int var3;
         if (var6 < 2048) {
            var2 += 127 - var6 >>> 31;
            var3 = var1;
         } else {
            int var4 = var2 + 2;
            var2 = var4;
            var3 = var1;
            if ('\ud800' <= var6) {
               var2 = var4;
               var3 = var1;
               if (var6 <= '\udfff') {
                  if (Character.codePointAt(var0, var1) < 65536) {
                     throw new Utf8.UnpairedSurrogateException(var1, var5);
                  }

                  var3 = var1 + 1;
                  var2 = var4;
               }
            }
         }

         var1 = var3 + 1;
      }

      return var2;
   }

   private static int estimateConsecutiveAscii(ByteBuffer var0, int var1, int var2) {
      int var3 = var1;

      while (var3 < var2 - 7 && (var0.getLong(var3) & -9187201950435737472L) == 0L) {
         var3 += 8;
      }

      return var3 - var1;
   }

   private static int incompleteStateFor(int var0) {
      int var1 = var0;
      if (var0 > -12) {
         var1 = -1;
      }

      return var1;
   }

   private static int incompleteStateFor(int var0, int var1) {
      if (var0 <= -12 && var1 <= -65) {
         var0 ^= var1 << 8;
      } else {
         var0 = -1;
      }

      return var0;
   }

   private static int incompleteStateFor(int var0, int var1, int var2) {
      if (var0 <= -12 && var1 <= -65 && var2 <= -65) {
         var0 = var0 ^ var1 << 8 ^ var2 << 16;
      } else {
         var0 = -1;
      }

      return var0;
   }

   private static int incompleteStateFor(ByteBuffer var0, int var1, int var2, int var3) {
      if (var3 != 0) {
         if (var3 != 1) {
            if (var3 == 2) {
               return incompleteStateFor(var1, var0.get(var2), var0.get(var2 + 1));
            } else {
               throw new AssertionError();
            }
         } else {
            return incompleteStateFor(var1, var0.get(var2));
         }
      } else {
         return incompleteStateFor(var1);
      }
   }

   private static int incompleteStateFor(byte[] var0, int var1, int var2) {
      byte var3 = var0[var1 - 1];
      var2 -= var1;
      if (var2 != 0) {
         if (var2 != 1) {
            if (var2 == 2) {
               return incompleteStateFor(var3, var0[var1], var0[var1 + 1]);
            } else {
               throw new AssertionError();
            }
         } else {
            return incompleteStateFor(var3, var0[var1]);
         }
      } else {
         return incompleteStateFor(var3);
      }
   }

   static boolean isValidUtf8(ByteBuffer var0) {
      return processor.isValidUtf8(var0, var0.position(), var0.remaining());
   }

   static boolean isValidUtf8(byte[] var0) {
      return processor.isValidUtf8(var0, 0, var0.length);
   }

   static boolean isValidUtf8(byte[] var0, int var1, int var2) {
      return processor.isValidUtf8(var0, var1, var2);
   }

   static int partialIsValidUtf8(int var0, ByteBuffer var1, int var2, int var3) {
      return processor.partialIsValidUtf8(var0, var1, var2, var3);
   }

   static int partialIsValidUtf8(int var0, byte[] var1, int var2, int var3) {
      return processor.partialIsValidUtf8(var0, var1, var2, var3);
   }

   private static class DecodeUtil {
      private static void handleFourBytes(byte var0, byte var1, byte var2, byte var3, char[] var4, int var5) throws InvalidProtocolBufferException {
         if (!isNotTrailingByte(var1) && (var0 << 28) + var1 + 112 >> 30 == 0 && !isNotTrailingByte(var2) && !isNotTrailingByte(var3)) {
            var0 = (var0 & 7) << 18 | trailingByteValue(var1) << 12 | trailingByteValue(var2) << 6 | trailingByteValue(var3);
            var4[var5] = highSurrogate(var0);
            var4[var5 + 1] = lowSurrogate(var0);
         } else {
            throw InvalidProtocolBufferException.invalidUtf8();
         }
      }

      private static void handleOneByte(byte var0, char[] var1, int var2) {
         var1[var2] = (char)var0;
      }

      private static void handleThreeBytes(byte var0, byte var1, byte var2, char[] var3, int var4) throws InvalidProtocolBufferException {
         if (!isNotTrailingByte(var1) && (var0 != -32 || var1 >= -96) && (var0 != -19 || var1 < -96) && !isNotTrailingByte(var2)) {
            var3[var4] = (char)((var0 & 15) << 12 | trailingByteValue(var1) << 6 | trailingByteValue(var2));
         } else {
            throw InvalidProtocolBufferException.invalidUtf8();
         }
      }

      private static void handleTwoBytes(byte var0, byte var1, char[] var2, int var3) throws InvalidProtocolBufferException {
         if (var0 >= -62 && !isNotTrailingByte(var1)) {
            var2[var3] = (char)((var0 & 31) << 6 | trailingByteValue(var1));
         } else {
            throw InvalidProtocolBufferException.invalidUtf8();
         }
      }

      private static char highSurrogate(int var0) {
         return (char)((var0 >>> 10) + 55232);
      }

      private static boolean isNotTrailingByte(byte var0) {
         boolean var1;
         if (var0 > -65) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      private static boolean isOneByte(byte var0) {
         boolean var1;
         if (var0 >= 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      private static boolean isThreeBytes(byte var0) {
         boolean var1;
         if (var0 < -16) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      private static boolean isTwoBytes(byte var0) {
         boolean var1;
         if (var0 < -32) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      private static char lowSurrogate(int var0) {
         return (char)((var0 & 1023) + 56320);
      }

      private static int trailingByteValue(byte var0) {
         return var0 & 63;
      }
   }

   abstract static class Processor {
      private static int partialIsValidUtf8(ByteBuffer var0, int var1, int var2) {
         var1 += Utf8.estimateConsecutiveAscii(var0, var1, var2);

         while (true) {
            int var3 = var1;
            if (var1 >= var2) {
               return 0;
            }

            var1++;
            byte var4 = var0.get(var3);
            if (var4 < 0) {
               if (var4 < -32) {
                  if (var1 >= var2) {
                     return var4;
                  }

                  if (var4 < -62 || var0.get(var1) > -65) {
                     return -1;
                  }

                  var1 = var3 + 2;
               } else if (var4 < -16) {
                  if (var1 >= var2 - 1) {
                     return Utf8.incompleteStateFor(var0, var4, var1, var2 - var1);
                  }

                  byte var7 = var0.get(var1);
                  if (var7 > -65 || var4 == -32 && var7 < -96 || var4 == -19 && var7 >= -96 || var0.get(var3 + 2) > -65) {
                     return -1;
                  }

                  var1 = var3 + 3;
               } else {
                  if (var1 >= var2 - 2) {
                     return Utf8.incompleteStateFor(var0, var4, var1, var2 - var1);
                  }

                  byte var6 = var0.get(var1);
                  if (var6 <= -65 && (var4 << 28) + var6 + 112 >> 30 == 0 && var0.get(var3 + 2) <= -65) {
                     var1 = var3 + 4;
                     if (var0.get(var3 + 3) <= -65) {
                        continue;
                     }
                  }

                  return -1;
               }
            }
         }
      }

      final String decodeUtf8(ByteBuffer var1, int var2, int var3) throws InvalidProtocolBufferException {
         if (var1.hasArray()) {
            int var4 = var1.arrayOffset();
            return this.decodeUtf8(var1.array(), var4 + var2, var3);
         } else {
            return var1.isDirect() ? this.decodeUtf8Direct(var1, var2, var3) : this.decodeUtf8Default(var1, var2, var3);
         }
      }

      abstract String decodeUtf8(byte[] var1, int var2, int var3) throws InvalidProtocolBufferException;

      final String decodeUtf8Default(ByteBuffer var1, int var2, int var3) throws InvalidProtocolBufferException {
         if ((var2 | var3 | var1.limit() - var2 - var3) < 0) {
            throw new ArrayIndexOutOfBoundsException(String.format("buffer limit=%d, index=%d, limit=%d", var1.limit(), var2, var3));
         } else {
            int var8 = var2 + var3;
            char[] var9 = new char[var3];

            for (var3 = 0; var2 < var8; var3++) {
               byte var4 = var1.get(var2);
               if (!Utf8.DecodeUtil.isOneByte(var4)) {
                  break;
               }

               var2++;
               Utf8.DecodeUtil.handleOneByte(var4, var9, var3);
            }

            while (true) {
               int var7 = var2;
               if (var2 >= var8) {
                  return new String(var9, 0, var3);
               }

               var2++;
               byte var11 = var1.get(var7);
               if (Utf8.DecodeUtil.isOneByte(var11)) {
                  var7 = var3 + 1;
                  Utf8.DecodeUtil.handleOneByte(var11, var9, var3);

                  for (var3 = var7; var2 < var8; var3++) {
                     var11 = var1.get(var2);
                     if (!Utf8.DecodeUtil.isOneByte(var11)) {
                        break;
                     }

                     var2++;
                     Utf8.DecodeUtil.handleOneByte(var11, var9, var3);
                  }
               } else if (Utf8.DecodeUtil.isTwoBytes(var11)) {
                  if (var2 >= var8) {
                     throw InvalidProtocolBufferException.invalidUtf8();
                  }

                  var7 += 2;
                  Utf8.DecodeUtil.handleTwoBytes(var11, var1.get(var2), var9, var3);
                  var3++;
                  var2 = var7;
               } else if (Utf8.DecodeUtil.isThreeBytes(var11)) {
                  if (var2 >= var8 - 1) {
                     throw InvalidProtocolBufferException.invalidUtf8();
                  }

                  byte var5 = var1.get(var2);
                  var2 = var7 + 3;
                  Utf8.DecodeUtil.handleThreeBytes(var11, var5, var1.get(var7 + 2), var9, var3);
                  var3++;
               } else {
                  if (var2 >= var8 - 2) {
                     throw InvalidProtocolBufferException.invalidUtf8();
                  }

                  byte var6 = var1.get(var2);
                  byte var13 = var1.get(var7 + 2);
                  var2 = var7 + 4;
                  Utf8.DecodeUtil.handleFourBytes(var11, var6, var13, var1.get(var7 + 3), var9, var3);
                  var3 += 2;
               }
            }
         }
      }

      abstract String decodeUtf8Direct(ByteBuffer var1, int var2, int var3) throws InvalidProtocolBufferException;

      abstract int encodeUtf8(CharSequence var1, byte[] var2, int var3, int var4);

      final void encodeUtf8(CharSequence var1, ByteBuffer var2) {
         if (var2.hasArray()) {
            int var3 = var2.arrayOffset();
            Java8Compatibility.position(var2, Utf8.encode(var1, var2.array(), var2.position() + var3, var2.remaining()) - var3);
         } else if (var2.isDirect()) {
            this.encodeUtf8Direct(var1, var2);
         } else {
            this.encodeUtf8Default(var1, var2);
         }
      }

      // $VF: Duplicated exception handlers to handle obfuscated exceptions
      final void encodeUtf8Default(CharSequence var1, ByteBuffer var2) {
         int var11 = var1.length();
         int var7 = var2.position();
         int var6 = 0;

         int var9;
         label208:
         while (true) {
            if (var6 < var11) {
               var9 = var7;

               char var10;
               try {
                  var10 = var1.charAt(var6);
               } catch (IndexOutOfBoundsException var33) {
                  var6 = var6;
                  break;
               }

               if (var10 < 128) {
                  var9 = var7;

                  try {
                     var2.put(var7 + var6, (byte)var10);
                  } catch (IndexOutOfBoundsException var13) {
                     var6 = var6;
                     break;
                  }

                  var6++;
                  continue;
               }
            }

            if (var6 == var11) {
               var9 = var7;

               try {
                  Java8Compatibility.position(var2, var7 + var6);
                  return;
               } catch (IndexOutOfBoundsException var14) {
                  var6 = var6;
                  break;
               }
            } else {
               var7 += var6;

               while (true) {
                  if (var6 < var11) {
                     var9 = var7;

                     char var5;
                     try {
                        var5 = var1.charAt(var6);
                     } catch (IndexOutOfBoundsException var26) {
                        var6 = var6;
                        break label208;
                     }

                     int var8;
                     label224: {
                        if (var5 < 128) {
                           var9 = var7;

                           try {
                              var2.put(var7, (byte)var5);
                           } catch (IndexOutOfBoundsException var25) {
                              var6 = var6;
                              break label208;
                           }
                        } else if (var5 < 2048) {
                           var9 = var7 + 1;
                           byte var39 = (byte)(var5 >>> 6 | 192);

                           try {
                              var2.put(var7, var39);
                           } catch (IndexOutOfBoundsException var24) {
                              var9 = var9;
                              break label208;
                           }

                           try {
                              var2.put(var9, (byte)(var5 & '?' | 128));
                           } catch (IndexOutOfBoundsException var23) {
                              var9 = var9;
                              break label208;
                           }

                           var7 = var9;
                        } else {
                           label188:
                           if (var5 >= '\ud800' && '\udfff' >= var5) {
                              var8 = var6 + 1;
                              if (var8 != var11) {
                                 var6 = var7;

                                 char var4;
                                 try {
                                    var4 = var1.charAt(var8);
                                 } catch (IndexOutOfBoundsException var31) {
                                    break label224;
                                 }

                                 var6 = var7;

                                 label220: {
                                    try {
                                       if (!Character.isSurrogatePair(var5, var4)) {
                                          break label220;
                                       }
                                    } catch (IndexOutOfBoundsException var32) {
                                       break label224;
                                    }

                                    var6 = var7;

                                    int var46;
                                    try {
                                       var46 = Character.toCodePoint(var5, var4);
                                    } catch (IndexOutOfBoundsException var30) {
                                       break label224;
                                    }

                                    var6 = var7 + 1;
                                    byte var36 = (byte)(var46 >>> 18 | 240);

                                    try {
                                       var2.put(var7, var36);
                                    } catch (IndexOutOfBoundsException var29) {
                                       break label224;
                                    }

                                    var9 = var7 + 2;
                                    var36 = (byte)(var46 >>> 12 & 63 | 128);

                                    try {
                                       var2.put(var6, var36);
                                    } catch (IndexOutOfBoundsException var22) {
                                       var6 = var8;
                                       break label208;
                                    }

                                    var7 += 3;
                                    var36 = (byte)(var46 >>> 6 & 63 | 128);
                                    var6 = var7;

                                    try {
                                       var2.put(var9, var36);
                                    } catch (IndexOutOfBoundsException var28) {
                                       break label224;
                                    }

                                    var6 = var7;

                                    try {
                                       var2.put(var7, (byte)(var46 & 63 | 128));
                                    } catch (IndexOutOfBoundsException var27) {
                                       break label224;
                                    }

                                    var6 = var8;
                                    break label188;
                                 }

                                 var6 = var8;
                              }

                              var9 = var7;

                              Utf8.UnpairedSurrogateException var12;
                              try {
                                 var12 = new Utf8.UnpairedSurrogateException;
                              } catch (IndexOutOfBoundsException var17) {
                                 var6 = var6;
                                 break label208;
                              }

                              var9 = var7;

                              try {
                                 var12./* $VF: Unable to resugar constructor */<init>(var6, var11);
                              } catch (IndexOutOfBoundsException var16) {
                                 var6 = var6;
                                 break label208;
                              }

                              var9 = var7;

                              try {
                                 throw var12;
                              } catch (IndexOutOfBoundsException var15) {
                                 var6 = var6;
                                 break label208;
                              }
                           } else {
                              int var45 = var7 + 1;
                              byte var3 = (byte)(var5 >>> '\f' | 224);

                              try {
                                 var2.put(var7, var3);
                              } catch (IndexOutOfBoundsException var21) {
                                 var9 = var45;
                                 break label208;
                              }

                              var7 += 2;
                              var3 = (byte)(var5 >>> 6 & 63 | 128);
                              var9 = var7;

                              try {
                                 var2.put(var45, var3);
                              } catch (IndexOutOfBoundsException var20) {
                                 var6 = var6;
                                 break label208;
                              }

                              var9 = var7;

                              try {
                                 var2.put(var7, (byte)(var5 & '?' | 128));
                              } catch (IndexOutOfBoundsException var19) {
                                 var6 = var6;
                                 break label208;
                              }
                           }
                        }

                        var6++;
                        var7++;
                        continue;
                     }

                     var9 = var6;
                     var6 = var8;
                     break label208;
                  }

                  var9 = var7;

                  try {
                     Java8Compatibility.position(var2, var7);
                     return;
                  } catch (IndexOutOfBoundsException var18) {
                     var6 = var6;
                     break label208;
                  }
               }
            }
         }

         var7 = var2.position();
         int var43 = Math.max(var6, var9 - var2.position() + 1);
         StringBuilder var34 = new StringBuilder("Failed writing ");
         var34.append(var1.charAt(var6));
         var34.append(" at index ");
         var34.append(var7 + var43);
         throw new ArrayIndexOutOfBoundsException(var34.toString());
      }

      abstract void encodeUtf8Direct(CharSequence var1, ByteBuffer var2);

      final boolean isValidUtf8(ByteBuffer var1, int var2, int var3) {
         boolean var4 = false;
         if (this.partialIsValidUtf8(0, var1, var2, var3) == 0) {
            var4 = true;
         }

         return var4;
      }

      final boolean isValidUtf8(byte[] var1, int var2, int var3) {
         boolean var4 = false;
         if (this.partialIsValidUtf8(0, var1, var2, var3) == 0) {
            var4 = true;
         }

         return var4;
      }

      final int partialIsValidUtf8(int var1, ByteBuffer var2, int var3, int var4) {
         if (var2.hasArray()) {
            int var5 = var2.arrayOffset();
            return this.partialIsValidUtf8(var1, var2.array(), var3 + var5, var5 + var4);
         } else {
            return var2.isDirect() ? this.partialIsValidUtf8Direct(var1, var2, var3, var4) : this.partialIsValidUtf8Default(var1, var2, var3, var4);
         }
      }

      abstract int partialIsValidUtf8(int var1, byte[] var2, int var3, int var4);

      final int partialIsValidUtf8Default(int var1, ByteBuffer var2, int var3, int var4) {
         int var5 = var3;
         if (var1 != 0) {
            if (var3 >= var4) {
               return var1;
            }

            byte var8 = (byte)var1;
            if (var8 < -32) {
               if (var8 < -62) {
                  return -1;
               }

               var1 = var3 + 1;
               if (var2.get(var3) > -65) {
                  return -1;
               }
            } else {
               if (var8 >= -16) {
                  int var14 = (byte)(~(var1 >> 8));
                  byte var12;
                  if (var14 == 0) {
                     var1 = var3 + 1;
                     var14 = var2.get(var3);
                     if (var1 >= var4) {
                        return Utf8.incompleteStateFor(var8, var14);
                     }

                     var12 = 0;
                  } else {
                     byte var15 = (byte)(var1 >> 16);
                     var1 = var3;
                     var12 = var15;
                  }

                  int var16 = var1;
                  byte var7 = var12;
                  if (var12 == 0) {
                     var16 = var1 + 1;
                     var7 = var2.get(var1);
                     if (var16 >= var4) {
                        return Utf8.incompleteStateFor(var8, var14, var7);
                     }
                  }

                  if (var14 > -65 || (var8 << 28) + var14 + 112 >> 30 != 0 || var7 > -65) {
                     return -1;
                  }

                  var14 = var16 + 1;
                  if (var2.get(var16) > -65) {
                     return -1;
                  }

                  return partialIsValidUtf8(var2, var14, var4);
               }

               byte var6 = (byte)(~(var1 >> 8));
               byte var10 = var6;
               var5 = var3;
               if (var6 == 0) {
                  var5 = var3 + 1;
                  var10 = var2.get(var3);
                  if (var5 >= var4) {
                     return Utf8.incompleteStateFor(var8, var10);
                  }
               }

               if (var10 > -65 || var8 == -32 && var10 < -96 || var8 == -19 && var10 >= -96) {
                  return -1;
               }

               var1 = var5 + 1;
               if (var2.get(var5) > -65) {
                  return -1;
               }
            }

            var5 = var1;
         }

         return partialIsValidUtf8(var2, var5, var4);
      }

      abstract int partialIsValidUtf8Direct(int var1, ByteBuffer var2, int var3, int var4);
   }

   static final class SafeProcessor extends Utf8.Processor {
      private static int partialIsValidUtf8(byte[] var0, int var1, int var2) {
         while (var1 < var2 && var0[var1] >= 0) {
            var1++;
         }

         if (var1 >= var2) {
            var1 = 0;
         } else {
            var1 = partialIsValidUtf8NonAscii(var0, var1, var2);
         }

         return var1;
      }

      private static int partialIsValidUtf8NonAscii(byte[] var0, int var1, int var2) {
         while (true) {
            int var3 = var1;
            if (var1 >= var2) {
               return 0;
            }

            int var4 = var1 + 1;
            byte var5 = var0[var1];
            if (var5 < 0) {
               if (var5 < -32) {
                  if (var4 >= var2) {
                     return var5;
                  }

                  if (var5 >= -62) {
                     var1 += 2;
                     if (var0[var4] <= -65) {
                        continue;
                     }
                  }

                  return -1;
               } else if (var5 < -16) {
                  if (var4 >= var2 - 1) {
                     return Utf8.incompleteStateFor(var0, var4, var2);
                  }

                  byte var7 = var0[var4];
                  if (var7 <= -65 && (var5 != -32 || var7 >= -96) && (var5 != -19 || var7 < -96)) {
                     var1 += 3;
                     if (var0[var3 + 2] <= -65) {
                        continue;
                     }
                  }

                  return -1;
               } else {
                  if (var4 >= var2 - 2) {
                     return Utf8.incompleteStateFor(var0, var4, var2);
                  }

                  byte var6 = var0[var4];
                  if (var6 <= -65 && (var5 << 28) + var6 + 112 >> 30 == 0 && var0[var1 + 2] <= -65) {
                     var1 += 4;
                     if (var0[var3 + 3] <= -65) {
                        continue;
                     }
                  }

                  return -1;
               }
            } else {
               var1 = var4;
            }
         }
      }

      @Override
      String decodeUtf8(byte[] var1, int var2, int var3) throws InvalidProtocolBufferException {
         if ((var2 | var3 | var1.length - var2 - var3) < 0) {
            throw new ArrayIndexOutOfBoundsException(String.format("buffer length=%d, index=%d, size=%d", var1.length, var2, var3));
         } else {
            int var8 = var2 + var3;
            char[] var9 = new char[var3];

            for (var3 = 0; var2 < var8; var3++) {
               byte var4 = var1[var2];
               if (!Utf8.DecodeUtil.isOneByte(var4)) {
                  break;
               }

               var2++;
               Utf8.DecodeUtil.handleOneByte(var4, var9, var3);
            }

            while (var2 < var8) {
               int var7 = var2 + 1;
               byte var13 = var1[var2];
               if (Utf8.DecodeUtil.isOneByte(var13)) {
                  var2 = var3 + 1;
                  Utf8.DecodeUtil.handleOneByte(var13, var9, var3);

                  for (var3 = var7; var3 < var8; var2++) {
                     var13 = var1[var3];
                     if (!Utf8.DecodeUtil.isOneByte(var13)) {
                        break;
                     }

                     var3++;
                     Utf8.DecodeUtil.handleOneByte(var13, var9, var2);
                  }

                  var3 = var2;
                  var2 = var3;
               } else if (Utf8.DecodeUtil.isTwoBytes(var13)) {
                  if (var7 >= var8) {
                     throw InvalidProtocolBufferException.invalidUtf8();
                  }

                  var2 += 2;
                  Utf8.DecodeUtil.handleTwoBytes(var13, var1[var7], var9, var3);
                  var3++;
               } else if (Utf8.DecodeUtil.isThreeBytes(var13)) {
                  if (var7 >= var8 - 1) {
                     throw InvalidProtocolBufferException.invalidUtf8();
                  }

                  byte var5 = var1[var7];
                  var7 = var2 + 3;
                  Utf8.DecodeUtil.handleThreeBytes(var13, var5, var1[var2 + 2], var9, var3);
                  var3++;
                  var2 = var7;
               } else {
                  if (var7 >= var8 - 2) {
                     throw InvalidProtocolBufferException.invalidUtf8();
                  }

                  byte var15 = var1[var7];
                  byte var6 = var1[var2 + 2];
                  var7 = var2 + 4;
                  Utf8.DecodeUtil.handleFourBytes(var13, var15, var6, var1[var2 + 3], var9, var3);
                  var3 += 2;
                  var2 = var7;
               }
            }

            return new String(var9, 0, var3);
         }
      }

      @Override
      String decodeUtf8Direct(ByteBuffer var1, int var2, int var3) throws InvalidProtocolBufferException {
         return this.decodeUtf8Default(var1, var2, var3);
      }

      @Override
      int encodeUtf8(CharSequence var1, byte[] var2, int var3, int var4) {
         int var9 = var1.length();
         int var10 = var4 + var3;

         for (var4 = 0; var4 < var9; var4++) {
            int var8 = var4 + var3;
            if (var8 >= var10) {
               break;
            }

            char var7 = var1.charAt(var4);
            if (var7 >= 128) {
               break;
            }

            var2[var8] = (byte)var7;
         }

         if (var4 == var9) {
            return var3 + var9;
         } else {
            var3 += var4;

            while (true) {
               if (var4 >= var9) {
                  return var3;
               }

               char var5 = var1.charAt(var4);
               if (var5 < 128 && var3 < var10) {
                  var2[var3] = (byte)var5;
                  var3++;
               } else if (var5 < 2048 && var3 <= var10 - 2) {
                  var2[var3] = (byte)(var5 >>> 6 | 960);
                  int var18 = var3 + 2;
                  var2[var3 + 1] = (byte)(var5 & '?' | 128);
                  var3 = var18;
               } else if ((var5 < '\ud800' || '\udfff' < var5) && var3 <= var10 - 3) {
                  var2[var3] = (byte)(var5 >>> '\f' | 480);
                  var2[var3 + 1] = (byte)(var5 >>> 6 & 63 | 128);
                  int var17 = var3 + 3;
                  var2[var3 + 2] = (byte)(var5 & '?' | 128);
                  var3 = var17;
               } else {
                  if (var3 > var10 - 4) {
                     if ('\ud800' <= var5 && var5 <= '\udfff') {
                        int var16 = var4 + 1;
                        if (var16 == var1.length() || !Character.isSurrogatePair(var5, var1.charAt(var16))) {
                           throw new Utf8.UnpairedSurrogateException(var4, var9);
                        }
                     }

                     StringBuilder var11 = new StringBuilder("Failed writing ");
                     var11.append(var5);
                     var11.append(" at index ");
                     var11.append(var3);
                     throw new ArrayIndexOutOfBoundsException(var11.toString());
                  }

                  int var15 = var4 + 1;
                  if (var15 == var1.length()) {
                     break;
                  }

                  char var6 = var1.charAt(var15);
                  if (!Character.isSurrogatePair(var5, var6)) {
                     var4 = var15;
                     break;
                  }

                  var4 = Character.toCodePoint(var5, var6);
                  var2[var3] = (byte)(var4 >>> 18 | 240);
                  var2[var3 + 1] = (byte)(var4 >>> 12 & 63 | 128);
                  var2[var3 + 2] = (byte)(var4 >>> 6 & 63 | 128);
                  int var19 = var3 + 4;
                  var2[var3 + 3] = (byte)(var4 & 63 | 128);
                  var4 = var15;
                  var3 = var19;
               }

               var4++;
            }

            throw new Utf8.UnpairedSurrogateException(var4 - 1, var9);
         }
      }

      @Override
      void encodeUtf8Direct(CharSequence var1, ByteBuffer var2) {
         this.encodeUtf8Default(var1, var2);
      }

      @Override
      int partialIsValidUtf8(int var1, byte[] var2, int var3, int var4) {
         int var5 = var3;
         if (var1 != 0) {
            if (var3 >= var4) {
               return var1;
            }

            byte var8 = (byte)var1;
            if (var8 < -32) {
               if (var8 < -62) {
                  return -1;
               }

               var1 = var3 + 1;
               if (var2[var3] > -65) {
                  return -1;
               }
            } else {
               if (var8 >= -16) {
                  int var14 = (byte)(~(var1 >> 8));
                  byte var12;
                  if (var14 == 0) {
                     var1 = var3 + 1;
                     var14 = var2[var3];
                     if (var1 >= var4) {
                        return Utf8.incompleteStateFor(var8, var14);
                     }

                     var12 = 0;
                  } else {
                     byte var15 = (byte)(var1 >> 16);
                     var1 = var3;
                     var12 = var15;
                  }

                  int var16 = var1;
                  byte var7 = var12;
                  if (var12 == 0) {
                     var16 = var1 + 1;
                     var7 = var2[var1];
                     if (var16 >= var4) {
                        return Utf8.incompleteStateFor(var8, var14, var7);
                     }
                  }

                  if (var14 > -65 || (var8 << 28) + var14 + 112 >> 30 != 0 || var7 > -65) {
                     return -1;
                  }

                  var14 = var16 + 1;
                  if (var2[var16] > -65) {
                     return -1;
                  }

                  return partialIsValidUtf8(var2, var14, var4);
               }

               byte var6 = (byte)(~(var1 >> 8));
               byte var10 = var6;
               var5 = var3;
               if (var6 == 0) {
                  var5 = var3 + 1;
                  var10 = var2[var3];
                  if (var5 >= var4) {
                     return Utf8.incompleteStateFor(var8, var10);
                  }
               }

               if (var10 > -65 || var8 == -32 && var10 < -96 || var8 == -19 && var10 >= -96) {
                  return -1;
               }

               var1 = var5 + 1;
               if (var2[var5] > -65) {
                  return -1;
               }
            }

            var5 = var1;
         }

         return partialIsValidUtf8(var2, var5, var4);
      }

      @Override
      int partialIsValidUtf8Direct(int var1, ByteBuffer var2, int var3, int var4) {
         return this.partialIsValidUtf8Default(var1, var2, var3, var4);
      }
   }

   static class UnpairedSurrogateException extends IllegalArgumentException {
      UnpairedSurrogateException(int var1, int var2) {
         StringBuilder var3 = new StringBuilder("Unpaired surrogate at index ");
         var3.append(var1);
         var3.append(" of ");
         var3.append(var2);
         super(var3.toString());
      }
   }

   static final class UnsafeProcessor extends Utf8.Processor {
      static boolean isAvailable() {
         boolean var0;
         if (UnsafeUtil.hasUnsafeArrayOperations() && UnsafeUtil.hasUnsafeByteBufferOperations()) {
            var0 = true;
         } else {
            var0 = false;
         }

         return var0;
      }

      private static int partialIsValidUtf8(long var0, int var2) {
         int var3 = unsafeEstimateConsecutiveAscii(var0, var2);
         var0 += var3;
         var2 -= var3;

         while (true) {
            int var4 = 0;
            var3 = var2;
            byte var9 = (byte)var4;

            long var5;
            while (true) {
               var5 = var0;
               if (var3 <= 0) {
                  break;
               }

               var5 = var0 + 1L;
               var9 = UnsafeUtil.getByte(var0);
               if (var9 < 0) {
                  break;
               }

               var3--;
               var0 = var5;
            }

            if (var3 == 0) {
               return 0;
            }

            var4 = var3 - 1;
            if (var9 < -32) {
               if (var4 == 0) {
                  return var9;
               }

               var3 -= 2;
               if (var9 < -62 || UnsafeUtil.getByte(var5) > -65) {
                  return -1;
               }

               var0 = 1L + var5;
               var2 = var3;
            } else if (var9 < -16) {
               if (var4 < 2) {
                  return unsafeIncompleteStateFor(var5, var9, var4);
               }

               var3 -= 3;
               byte var16 = UnsafeUtil.getByte(var5);
               if (var16 <= -65 && (var9 != -32 || var16 >= -96) && (var9 != -19 || var16 < -96)) {
                  var0 = var5 + 2L;
                  var2 = var3;
                  if (UnsafeUtil.getByte(1L + var5) <= -65) {
                     continue;
                  }
               }

               return -1;
            } else {
               if (var4 < 3) {
                  return unsafeIncompleteStateFor(var5, var9, var4);
               }

               var3 -= 4;
               byte var15 = UnsafeUtil.getByte(var5);
               if (var15 <= -65 && (var9 << 28) + var15 + 112 >> 30 == 0 && UnsafeUtil.getByte(1L + var5) <= -65) {
                  var0 = var5 + 3L;
                  var2 = var3;
                  if (UnsafeUtil.getByte(2L + var5) <= -65) {
                     continue;
                  }
               }

               return -1;
            }
         }
      }

      private static int partialIsValidUtf8(byte[] var0, long var1, int var3) {
         int var4 = unsafeEstimateConsecutiveAscii(var0, var1, var3);
         var3 -= var4;
         var1 += var4;

         while (true) {
            int var5 = 0;
            var4 = var3;
            byte var10 = (byte)var5;

            long var6;
            while (true) {
               var6 = var1;
               if (var4 <= 0) {
                  break;
               }

               var6 = var1 + 1L;
               var10 = UnsafeUtil.getByte(var0, var1);
               if (var10 < 0) {
                  break;
               }

               var4--;
               var1 = var6;
            }

            if (var4 == 0) {
               return 0;
            }

            var5 = var4 - 1;
            if (var10 < -32) {
               if (var5 == 0) {
                  return var10;
               }

               var4 -= 2;
               if (var10 < -62 || UnsafeUtil.getByte(var0, var6) > -65) {
                  return -1;
               }

               var1 = 1L + var6;
               var3 = var4;
            } else if (var10 < -16) {
               if (var5 < 2) {
                  return unsafeIncompleteStateFor(var0, var10, var6, var5);
               }

               var4 -= 3;
               byte var17 = UnsafeUtil.getByte(var0, var6);
               if (var17 <= -65 && (var10 != -32 || var17 >= -96) && (var10 != -19 || var17 < -96)) {
                  var1 = var6 + 2L;
                  var3 = var4;
                  if (UnsafeUtil.getByte(var0, 1L + var6) <= -65) {
                     continue;
                  }
               }

               return -1;
            } else {
               if (var5 < 3) {
                  return unsafeIncompleteStateFor(var0, var10, var6, var5);
               }

               var4 -= 4;
               byte var16 = UnsafeUtil.getByte(var0, var6);
               if (var16 <= -65 && (var10 << 28) + var16 + 112 >> 30 == 0 && UnsafeUtil.getByte(var0, 1L + var6) <= -65) {
                  var1 = var6 + 3L;
                  var3 = var4;
                  if (UnsafeUtil.getByte(var0, 2L + var6) <= -65) {
                     continue;
                  }
               }

               return -1;
            }
         }
      }

      private static int unsafeEstimateConsecutiveAscii(long var0, int var2) {
         if (var2 < 16) {
            return 0;
         } else {
            int var4 = (int)(-var0 & 7L);

            for (int var3 = var4; var3 > 0; var0++) {
               if (UnsafeUtil.getByte(var0) < 0) {
                  return var4 - var3;
               }

               var3--;
            }

            int var5;
            for (var5 = var2 - var4; var5 >= 8 && (UnsafeUtil.getLong(var0) & -9187201950435737472L) == 0L; var5 -= 8) {
               var0 += 8L;
            }

            return var2 - var5;
         }
      }

      private static int unsafeEstimateConsecutiveAscii(byte[] var0, long var1, int var3) {
         int var5 = 0;
         if (var3 < 16) {
            return 0;
         } else {
            int var6 = (int)var1;
            long var7 = var1;

            while (true) {
               int var4 = var5;
               var1 = var7;
               if (var5 >= 8 - (var6 & 7)) {
                  while (true) {
                     var6 = var4 + 8;
                     var5 = var4;
                     var7 = var1;
                     if (var6 > var3) {
                        break;
                     }

                     if ((UnsafeUtil.getLong(var0, UnsafeUtil.BYTE_ARRAY_BASE_OFFSET + var1) & -9187201950435737472L) != 0L) {
                        var5 = var4;
                        var7 = var1;
                        break;
                     }

                     var1 += 8L;
                     var4 = var6;
                  }

                  while (var5 < var3) {
                     if (UnsafeUtil.getByte(var0, var7) < 0) {
                        return var5;
                     }

                     var5++;
                     var7++;
                  }

                  return var3;
               }

               if (UnsafeUtil.getByte(var0, var7) < 0) {
                  return var5;
               }

               var5++;
               var7++;
            }
         }
      }

      private static int unsafeIncompleteStateFor(long var0, int var2, int var3) {
         if (var3 != 0) {
            if (var3 != 1) {
               if (var3 == 2) {
                  return Utf8.incompleteStateFor(var2, UnsafeUtil.getByte(var0), UnsafeUtil.getByte(var0 + 1L));
               } else {
                  throw new AssertionError();
               }
            } else {
               return Utf8.incompleteStateFor(var2, UnsafeUtil.getByte(var0));
            }
         } else {
            return Utf8.incompleteStateFor(var2);
         }
      }

      private static int unsafeIncompleteStateFor(byte[] var0, int var1, long var2, int var4) {
         if (var4 != 0) {
            if (var4 != 1) {
               if (var4 == 2) {
                  return Utf8.incompleteStateFor(var1, UnsafeUtil.getByte(var0, var2), UnsafeUtil.getByte(var0, var2 + 1L));
               } else {
                  throw new AssertionError();
               }
            } else {
               return Utf8.incompleteStateFor(var1, UnsafeUtil.getByte(var0, var2));
            }
         } else {
            return Utf8.incompleteStateFor(var1);
         }
      }

      @Override
      String decodeUtf8(byte[] var1, int var2, int var3) throws InvalidProtocolBufferException {
         String var4 = new String(var1, var2, var3, Internal.UTF_8);
         if (!var4.contains("�")) {
            return var4;
         } else if (Arrays.equals(var4.getBytes(Internal.UTF_8), Arrays.copyOfRange(var1, var2, var3 + var2))) {
            return var4;
         } else {
            throw InvalidProtocolBufferException.invalidUtf8();
         }
      }

      @Override
      String decodeUtf8Direct(ByteBuffer var1, int var2, int var3) throws InvalidProtocolBufferException {
         if ((var2 | var3 | var1.limit() - var2 - var3) < 0) {
            throw new ArrayIndexOutOfBoundsException(String.format("buffer limit=%d, index=%d, limit=%d", var1.limit(), var2, var3));
         } else {
            long var9 = UnsafeUtil.addressOffset(var1) + var2;
            long var11 = var3 + var9;
            char[] var13 = new char[var3];
            var3 = 0;

            long var7;
            while (true) {
               var2 = var3;
               var7 = var9;
               if (var9 >= var11) {
                  break;
               }

               byte var4 = UnsafeUtil.getByte(var9);
               if (!Utf8.DecodeUtil.isOneByte(var4)) {
                  var2 = var3;
                  var7 = var9;
                  break;
               }

               var9++;
               Utf8.DecodeUtil.handleOneByte(var4, var13, var3);
               var3++;
            }

            while (var7 < var11) {
               var9 = var7 + 1L;
               byte var17 = UnsafeUtil.getByte(var7);
               if (Utf8.DecodeUtil.isOneByte(var17)) {
                  var3 = var2 + 1;
                  Utf8.DecodeUtil.handleOneByte(var17, var13, var2);
                  var7 = var9;

                  for (var2 = var3; var7 < var11; var2++) {
                     var17 = UnsafeUtil.getByte(var7);
                     if (!Utf8.DecodeUtil.isOneByte(var17)) {
                        break;
                     }

                     var7++;
                     Utf8.DecodeUtil.handleOneByte(var17, var13, var2);
                  }
               } else if (Utf8.DecodeUtil.isTwoBytes(var17)) {
                  if (var9 >= var11) {
                     throw InvalidProtocolBufferException.invalidUtf8();
                  }

                  var7 += 2L;
                  Utf8.DecodeUtil.handleTwoBytes(var17, UnsafeUtil.getByte(var9), var13, var2);
                  var2++;
               } else if (Utf8.DecodeUtil.isThreeBytes(var17)) {
                  if (var9 >= var11 - 1L) {
                     throw InvalidProtocolBufferException.invalidUtf8();
                  }

                  byte var5 = UnsafeUtil.getByte(var9);
                  var9 = var7 + 3L;
                  Utf8.DecodeUtil.handleThreeBytes(var17, var5, UnsafeUtil.getByte(2L + var7), var13, var2);
                  var2++;
                  var7 = var9;
               } else {
                  if (var9 >= var11 - 2L) {
                     throw InvalidProtocolBufferException.invalidUtf8();
                  }

                  byte var19 = UnsafeUtil.getByte(var9);
                  byte var6 = UnsafeUtil.getByte(2L + var7);
                  var9 = var7 + 4L;
                  Utf8.DecodeUtil.handleFourBytes(var17, var19, var6, UnsafeUtil.getByte(var7 + 3L), var13, var2);
                  var2 += 2;
                  var7 = var9;
               }
            }

            return new String(var13, 0, var2);
         }
      }

      @Override
      int encodeUtf8(CharSequence var1, byte[] var2, int var3, int var4) {
         long var10 = var3;
         long var12 = var4 + var10;
         int var7 = var1.length();
         String var16 = " at index ";
         String var17 = "Failed writing ";
         if (var7 <= var4 && var2.length - var4 >= var3) {
            var4 = 0;

            long var8;
            while (true) {
               var8 = 1L;
               if (var4 >= var7) {
                  break;
               }

               char var20 = var1.charAt(var4);
               if (var20 >= 128) {
                  break;
               }

               UnsafeUtil.putByte(var2, var10, (byte)var20);
               var4++;
               var10++;
            }

            var3 = var4;
            long var14 = var10;
            if (var4 == var7) {
               return (int)var10;
            } else {
               while (true) {
                  if (var3 >= var7) {
                     return (int)var14;
                  }

                  char var6 = var1.charAt(var3);
                  if (var6 < 128 && var14 < var12) {
                     UnsafeUtil.putByte(var2, var14, (byte)var6);
                     var10 = var8;
                     var8 = var14 + var8;
                  } else {
                     label97: {
                        if (var6 < 2048 && var14 <= var12 - 2L) {
                           UnsafeUtil.putByte(var2, var14, (byte)(var6 >>> 6 | 960));
                           var10 = var14 + 2L;
                           UnsafeUtil.putByte(var2, var14 + var8, (byte)(var6 & '?' | 128));
                           var8 = var10;
                        } else {
                           if (var6 >= '\ud800' && '\udfff' >= var6 || var14 > var12 - 3L) {
                              if (var14 > var12 - 4L) {
                                 if ('\ud800' <= var6 && var6 <= '\udfff') {
                                    var4 = var3 + 1;
                                    if (var4 == var7 || !Character.isSurrogatePair(var6, var1.charAt(var4))) {
                                       throw new Utf8.UnpairedSurrogateException(var3, var7);
                                    }
                                 }

                                 StringBuilder var18 = new StringBuilder(var17);
                                 var18.append(var6);
                                 var18.append(var16);
                                 var18.append(var14);
                                 throw new ArrayIndexOutOfBoundsException(var18.toString());
                              }

                              var4 = var3 + 1;
                              if (var4 == var7) {
                                 break;
                              }

                              char var5 = var1.charAt(var4);
                              if (!Character.isSurrogatePair(var6, var5)) {
                                 var3 = var4;
                                 break;
                              }

                              var3 = Character.toCodePoint(var6, var5);
                              var10 = 1L;
                              UnsafeUtil.putByte(var2, var14, (byte)(var3 >>> 18 | 240));
                              UnsafeUtil.putByte(var2, var14 + 1L, (byte)(var3 >>> 12 & 63 | 128));
                              UnsafeUtil.putByte(var2, var14 + 2L, (byte)(var3 >>> 6 & 63 | 128));
                              var8 = var14 + 4L;
                              UnsafeUtil.putByte(var2, var14 + 3L, (byte)(var3 & 63 | 128));
                              var3 = var4;
                              break label97;
                           }

                           UnsafeUtil.putByte(var2, var14, (byte)(var6 >>> '\f' | 480));
                           UnsafeUtil.putByte(var2, var14 + 1L, (byte)(var6 >>> 6 & 63 | 128));
                           var8 = var14 + 3L;
                           UnsafeUtil.putByte(var2, var14 + 2L, (byte)(var6 & '?' | 128));
                        }

                        var10 = 1L;
                     }
                  }

                  var3++;
                  var14 = var8;
                  var8 = var10;
               }

               throw new Utf8.UnpairedSurrogateException(var3 - 1, var7);
            }
         } else {
            StringBuilder var19 = new StringBuilder("Failed writing ");
            var19.append(var1.charAt(var7 - 1));
            var19.append(" at index ");
            var19.append(var3 + var4);
            throw new ArrayIndexOutOfBoundsException(var19.toString());
         }
      }

      @Override
      void encodeUtf8Direct(CharSequence var1, ByteBuffer var2) {
         long var18 = UnsafeUtil.addressOffset(var2);
         long var10 = var2.position() + var18;
         long var14 = var2.limit() + var18;
         int var7 = var1.length();
         if (var7 > var14 - var10) {
            StringBuilder var20 = new StringBuilder("Failed writing ");
            var20.append(var1.charAt(var7 - 1));
            var20.append(" at index ");
            var20.append(var2.limit());
            throw new ArrayIndexOutOfBoundsException(var20.toString());
         } else {
            int var6 = 0;

            long var8;
            while (true) {
               var8 = 1L;
               if (var6 >= var7) {
                  break;
               }

               char var5 = var1.charAt(var6);
               if (var5 >= 128) {
                  break;
               }

               UnsafeUtil.putByte(var10, (byte)var5);
               var6++;
               var10++;
            }

            long var16 = var10;
            int var22 = var6;
            if (var6 == var7) {
               Java8Compatibility.position(var2, (int)(var10 - var18));
            } else {
               while (true) {
                  if (var22 >= var7) {
                     Java8Compatibility.position(var2, (int)(var16 - var18));
                     return;
                  }

                  label95: {
                     char var3 = var1.charAt(var22);
                     if (var3 < 128 && var16 < var14) {
                        UnsafeUtil.putByte(var16, (byte)var3);
                        var10 = var16 + var8;
                     } else {
                        if (var3 >= 2048 || var16 > var14 - 2L) {
                           if ((var3 < '\ud800' || '\udfff' < var3) && var16 <= var14 - 3L) {
                              UnsafeUtil.putByte(var16, (byte)(var3 >>> '\f' | 480));
                              UnsafeUtil.putByte(var16 + var8, (byte)(var3 >>> 6 & 63 | 128));
                              var8 = var16 + 3L;
                              UnsafeUtil.putByte(var16 + 2L, (byte)(var3 & '?' | 128));
                              var10 = 1L;
                              break label95;
                           }

                           if (var16 > var14 - 4L) {
                              if ('\ud800' <= var3 && var3 <= '\udfff') {
                                 var6 = var22 + 1;
                                 if (var6 == var7 || !Character.isSurrogatePair(var3, var1.charAt(var6))) {
                                    throw new Utf8.UnpairedSurrogateException(var22, var7);
                                 }
                              }

                              StringBuilder var21 = new StringBuilder("Failed writing ");
                              var21.append(var3);
                              var21.append(" at index ");
                              var21.append(var16);
                              throw new ArrayIndexOutOfBoundsException(var21.toString());
                           }

                           var6 = var22 + 1;
                           if (var6 == var7) {
                              break;
                           }

                           char var4 = var1.charAt(var6);
                           if (!Character.isSurrogatePair(var3, var4)) {
                              var22 = var6;
                              break;
                           }

                           var22 = Character.toCodePoint(var3, var4);
                           var10 = 1L;
                           UnsafeUtil.putByte(var16, (byte)(var22 >>> 18 | 240));
                           UnsafeUtil.putByte(var16 + 1L, (byte)(var22 >>> 12 & 63 | 128));
                           UnsafeUtil.putByte(var16 + 2L, (byte)(var22 >>> 6 & 63 | 128));
                           var8 = var16 + 4L;
                           UnsafeUtil.putByte(var16 + 3L, (byte)(var22 & 63 | 128));
                           var22 = var6;
                           break label95;
                        }

                        UnsafeUtil.putByte(var16, (byte)(var3 >>> 6 | 960));
                        var10 = var16 + 2L;
                        UnsafeUtil.putByte(var16 + var8, (byte)(var3 & '?' | 128));
                     }

                     var8 = var10;
                     var10 = var8;
                  }

                  var22++;
                  var16 = var8;
                  var8 = var10;
               }

               throw new Utf8.UnpairedSurrogateException(var22 - 1, var7);
            }
         }
      }

      @Override
      int partialIsValidUtf8(int var1, byte[] var2, int var3, int var4) {
         int var6 = var2.length;
         byte var5 = 0;
         if ((var3 | var4 | var6 - var4) < 0) {
            throw new ArrayIndexOutOfBoundsException(String.format("Array length=%d, index=%d, limit=%d", var2.length, var3, var4));
         } else {
            long var7 = var3;
            long var11 = var4;
            long var9 = var7;
            if (var1 != 0) {
               if (var7 >= var11) {
                  return var1;
               }

               byte var18 = (byte)var1;
               if (var18 < -32) {
                  if (var18 < -62 || UnsafeUtil.getByte(var2, var7) > -65) {
                     return -1;
                  }

                  var9 = 1L + var7;
               } else {
                  if (var18 < -16) {
                     byte var15 = (byte)(~(var1 >> 8));
                     var9 = var7;
                     byte var13 = var15;
                     if (var15 == 0) {
                        var9 = var7 + 1L;
                        var13 = UnsafeUtil.getByte(var2, var7);
                        if (var9 >= var11) {
                           return Utf8.incompleteStateFor(var18, var13);
                        }
                     }

                     if (var13 > -65 || var18 == -32 && var13 < -96 || var18 == -19 && var13 >= -96) {
                        return -1;
                     }

                     var7 = var9 + 1L;
                     if (UnsafeUtil.getByte(var2, var9) > -65) {
                        return -1;
                     }
                  } else {
                     byte var16 = (byte)(~(var1 >> 8));
                     byte var14;
                     if (var16 == 0) {
                        var9 = var7 + 1L;
                        var16 = UnsafeUtil.getByte(var2, var7);
                        if (var9 >= var11) {
                           return Utf8.incompleteStateFor(var18, var16);
                        }

                        var7 = var9;
                        var14 = var5;
                     } else {
                        var14 = (byte)(var1 >> 16);
                     }

                     byte var17 = var14;
                     var9 = var7;
                     if (var14 == 0) {
                        var9 = var7 + 1L;
                        var17 = UnsafeUtil.getByte(var2, var7);
                        if (var9 >= var11) {
                           return Utf8.incompleteStateFor(var18, var16, var17);
                        }
                     }

                     if (var16 > -65 || (var18 << 28) + var16 + 112 >> 30 != 0 || var17 > -65) {
                        return -1;
                     }

                     var7 = var9 + 1L;
                     if (UnsafeUtil.getByte(var2, var9) > -65) {
                        return -1;
                     }
                  }

                  var9 = var7;
               }
            }

            return partialIsValidUtf8(var2, var9, (int)(var11 - var9));
         }
      }

      @Override
      int partialIsValidUtf8Direct(int var1, ByteBuffer var2, int var3, int var4) {
         int var6 = var2.limit();
         byte var5 = 0;
         if ((var3 | var4 | var6 - var4) < 0) {
            throw new ArrayIndexOutOfBoundsException(String.format("buffer limit=%d, index=%d, limit=%d", var2.limit(), var3, var4));
         } else {
            long var7 = UnsafeUtil.addressOffset(var2) + var3;
            long var11 = var4 - var3 + var7;
            long var9 = var7;
            if (var1 != 0) {
               if (var7 >= var11) {
                  return var1;
               }

               byte var18 = (byte)var1;
               if (var18 < -32) {
                  if (var18 < -62 || UnsafeUtil.getByte(var7) > -65) {
                     return -1;
                  }

                  var9 = 1L + var7;
               } else {
                  if (var18 < -16) {
                     byte var15 = (byte)(~(var1 >> 8));
                     var9 = var7;
                     byte var13 = var15;
                     if (var15 == 0) {
                        var9 = var7 + 1L;
                        var13 = UnsafeUtil.getByte(var7);
                        if (var9 >= var11) {
                           return Utf8.incompleteStateFor(var18, var13);
                        }
                     }

                     if (var13 > -65 || var18 == -32 && var13 < -96 || var18 == -19 && var13 >= -96) {
                        return -1;
                     }

                     var7 = var9 + 1L;
                     if (UnsafeUtil.getByte(var9) > -65) {
                        return -1;
                     }
                  } else {
                     byte var16 = (byte)(~(var1 >> 8));
                     byte var14;
                     if (var16 == 0) {
                        var9 = var7 + 1L;
                        var16 = UnsafeUtil.getByte(var7);
                        if (var9 >= var11) {
                           return Utf8.incompleteStateFor(var18, var16);
                        }

                        var7 = var9;
                        var14 = var5;
                     } else {
                        var14 = (byte)(var1 >> 16);
                     }

                     byte var17 = var14;
                     var9 = var7;
                     if (var14 == 0) {
                        var9 = var7 + 1L;
                        var17 = UnsafeUtil.getByte(var7);
                        if (var9 >= var11) {
                           return Utf8.incompleteStateFor(var18, var16, var17);
                        }
                     }

                     if (var16 > -65 || (var18 << 28) + var16 + 112 >> 30 != 0 || var17 > -65) {
                        return -1;
                     }

                     var7 = var9 + 1L;
                     if (UnsafeUtil.getByte(var9) > -65) {
                        return -1;
                     }
                  }

                  var9 = var7;
               }
            }

            return partialIsValidUtf8(var9, (int)(var11 - var9));
         }
      }
   }
}
