package io.sentry.vendor;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

public class Base64 {
   static final boolean $assertionsDisabled = false;
   public static final int CRLF = 4;
   public static final int DEFAULT = 0;
   public static final int NO_CLOSE = 16;
   public static final int NO_PADDING = 1;
   public static final int NO_WRAP = 2;
   public static final int URL_SAFE = 8;

   private Base64() {
   }

   public static byte[] decode(String var0, int var1) {
      return decode(var0.getBytes(), var1);
   }

   public static byte[] decode(byte[] var0, int var1) {
      return decode(var0, 0, var0.length, var1);
   }

   public static byte[] decode(byte[] var0, int var1, int var2, int var3) {
      Base64.Decoder var4 = new Base64.Decoder(var3, new byte[var2 * 3 / 4]);
      if (var4.process(var0, var1, var2, true)) {
         if (var4.op == var4.output.length) {
            return var4.output;
         } else {
            var0 = new byte[var4.op];
            System.arraycopy(var4.output, 0, var0, 0, var4.op);
            return var0;
         }
      } else {
         throw new IllegalArgumentException("bad base-64");
      }
   }

   public static byte[] encode(byte[] var0, int var1) {
      return encode(var0, 0, var0.length, var1);
   }

   public static byte[] encode(byte[] var0, int var1, int var2, int var3) {
      Base64.Encoder var8 = new Base64.Encoder(var3, null);
      int var4 = var2 / 3 * 4;
      boolean var7 = var8.do_padding;
      byte var5 = 2;
      if (var7) {
         var3 = var4;
         if (var2 % 3 > 0) {
            var3 = var4 + 4;
         }
      } else {
         var3 = var2 % 3;
         if (var3 != 1) {
            if (var3 != 2) {
               var3 = var4;
            } else {
               var3 = var4 + 3;
            }
         } else {
            var3 = var4 + 2;
         }
      }

      var4 = var3;
      if (var8.do_newline) {
         var4 = var3;
         if (var2 > 0) {
            int var6 = (var2 - 1) / 57;
            byte var12;
            if (var8.do_cr) {
               var12 = var5;
            } else {
               var12 = 1;
            }

            var4 = var3 + (var6 + 1) * var12;
         }
      }

      var8.output = new byte[var4];
      var8.process(var0, var1, var2, true);
      return var8.output;
   }

   public static String encodeToString(byte[] var0, int var1) {
      try {
         return new String(encode(var0, var1), "US-ASCII");
      } catch (UnsupportedEncodingException var2) {
         throw new AssertionError(var2);
      }
   }

   public static String encodeToString(byte[] var0, int var1, int var2, int var3) {
      try {
         return new String(encode(var0, var1, var2, var3), "US-ASCII");
      } catch (UnsupportedEncodingException var4) {
         throw new AssertionError(var4);
      }
   }

   abstract static class Coder {
      public int op;
      public byte[] output;

      public abstract int maxOutputSize(int var1);

      public abstract boolean process(byte[] var1, int var2, int var3, boolean var4);
   }

   static class Decoder extends Base64.Coder {
      private static final int[] DECODE = $d2j$hex$48686141$decode_I(
         "ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff3e000000ffffffffffffffffffffffff3f0000003400000035000000360000003700000038000000390000003a0000003b0000003c0000003d000000fffffffffffffffffffffffffeffffffffffffffffffffffffffffff000000000100000002000000030000000400000005000000060000000700000008000000090000000a0000000b0000000c0000000d0000000e0000000f00000010000000110000001200000013000000140000001500000016000000170000001800000019000000ffffffffffffffffffffffffffffffffffffffffffffffff1a0000001b0000001c0000001d0000001e0000001f000000200000002100000022000000230000002400000025000000260000002700000028000000290000002a0000002b0000002c0000002d0000002e0000002f00000030000000310000003200000033000000ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff"
      );
      private static final int[] DECODE_WEBSAFE = $d2j$hex$48686141$decode_I(
         "ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff3e000000ffffffffffffffff3400000035000000360000003700000038000000390000003a0000003b0000003c0000003d000000fffffffffffffffffffffffffeffffffffffffffffffffffffffffff000000000100000002000000030000000400000005000000060000000700000008000000090000000a0000000b0000000c0000000d0000000e0000000f00000010000000110000001200000013000000140000001500000016000000170000001800000019000000ffffffffffffffffffffffffffffffff3f000000ffffffff1a0000001b0000001c0000001d0000001e0000001f000000200000002100000022000000230000002400000025000000260000002700000028000000290000002a0000002b0000002c0000002d0000002e0000002f00000030000000310000003200000033000000ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff"
      );
      private static final int EQUALS = -2;
      private static final int SKIP = -1;
      private final int[] alphabet;
      private int state;
      private int value;

      public Decoder(int var1, byte[] var2) {
         this.output = var2;
         int[] var3;
         if ((var1 & 8) == 0) {
            var3 = DECODE;
         } else {
            var3 = DECODE_WEBSAFE;
         }

         this.alphabet = var3;
         this.state = 0;
         this.value = 0;
      }

      @Override
      public int maxOutputSize(int var1) {
         return var1 * 3 / 4 + 10;
      }

      @Override
      public boolean process(byte[] var1, int var2, int var3, boolean var4) {
         int var9 = this.state;
         if (var9 == 6) {
            return false;
         } else {
            int var10 = var3 + var2;
            int var6 = this.value;
            byte[] var11 = this.output;
            int[] var12 = this.alphabet;
            var3 = 0;
            int var5 = var2;
            var2 = var6;

            int var7;
            while (true) {
               var7 = var2;
               var6 = var3;
               if (var5 >= var10) {
                  break;
               }

               int var8 = var5;
               var6 = var2;
               var7 = var3;
               if (var9 == 0) {
                  while (true) {
                     var7 = var5 + 4;
                     if (var7 > var10) {
                        break;
                     }

                     var6 = var12[var1[var5] & 255] << 18 | var12[var1[var5 + 1] & 255] << 12 | var12[var1[var5 + 2] & 255] << 6 | var12[var1[var5 + 3] & 255];
                     var2 = var6;
                     if (var6 < 0) {
                        break;
                     }

                     var11[var3 + 2] = (byte)var6;
                     var11[var3 + 1] = (byte)(var6 >> 8);
                     var11[var3] = (byte)(var6 >> 16);
                     var3 += 3;
                     var5 = var7;
                     var2 = var6;
                  }

                  var8 = var5;
                  var6 = var2;
                  var7 = var3;
                  if (var5 >= var10) {
                     var7 = var2;
                     var6 = var3;
                     break;
                  }
               }

               label97: {
                  var5 = var12[var1[var8] & 255];
                  if (var9 != 0) {
                     if (var9 != 1) {
                        if (var9 != 2) {
                           if (var9 != 3) {
                              if (var9 != 4) {
                                 if (var9 != 5) {
                                    var2 = var9;
                                    var3 = var7;
                                 } else {
                                    var2 = var9;
                                    var3 = var7;
                                    if (var5 != -1) {
                                       this.state = 6;
                                       return false;
                                    }
                                 }
                              } else if (var5 == -2) {
                                 var2 = var9 + 1;
                                 var3 = var7;
                              } else {
                                 var2 = var9;
                                 var3 = var7;
                                 if (var5 != -1) {
                                    this.state = 6;
                                    return false;
                                 }
                              }
                           } else if (var5 >= 0) {
                              var6 = var5 | var6 << 6;
                              var11[var7 + 2] = (byte)var6;
                              var11[var7 + 1] = (byte)(var6 >> 8);
                              var11[var7] = (byte)(var6 >> 16);
                              var3 = var7 + 3;
                              var2 = 0;
                           } else if (var5 == -2) {
                              var11[var7 + 1] = (byte)(var6 >> 2);
                              var11[var7] = (byte)(var6 >> 10);
                              var3 = var7 + 2;
                              var2 = 5;
                           } else {
                              var2 = var9;
                              var3 = var7;
                              if (var5 != -1) {
                                 this.state = 6;
                                 return false;
                              }
                           }
                           break label97;
                        }

                        if (var5 < 0) {
                           if (var5 == -2) {
                              var11[var7] = (byte)(var6 >> 4);
                              var3 = var7 + 1;
                              var2 = 4;
                           } else {
                              var2 = var9;
                              var3 = var7;
                              if (var5 != -1) {
                                 this.state = 6;
                                 return false;
                              }
                           }
                           break label97;
                        }
                     } else if (var5 < 0) {
                        var2 = var9;
                        var3 = var7;
                        if (var5 != -1) {
                           this.state = 6;
                           return false;
                        }
                        break label97;
                     }

                     var2 = var5 | var6 << 6;
                  } else {
                     if (var5 < 0) {
                        var2 = var9;
                        var3 = var7;
                        if (var5 != -1) {
                           this.state = 6;
                           return false;
                        }
                        break label97;
                     }

                     var2 = var5;
                  }

                  var3 = var9 + 1;
                  var6 = var2;
                  var2 = var3;
                  var3 = var7;
               }

               var5 = var8 + 1;
               var9 = var2;
               var2 = var6;
            }

            if (!var4) {
               this.state = var9;
               this.value = var7;
               this.op = var6;
               return true;
            } else if (var9 != 1) {
               if (var9 != 2) {
                  if (var9 != 3) {
                     if (var9 == 4) {
                        this.state = 6;
                        return false;
                     }
                  } else {
                     var11[var6] = (byte)(var7 >> 10);
                     var2 = var6 + 2;
                     var11[var6 + 1] = (byte)(var7 >> 2);
                     var6 = var2;
                  }
               } else {
                  var11[var6] = (byte)(var7 >> 4);
                  var6++;
               }

               this.state = var9;
               this.op = var6;
               return true;
            } else {
               this.state = 6;
               return false;
            }
         }
      }

      private static long[] $d2j$hex$48686141$decode_J(String src) {
         byte[] d = $d2j$hex$48686141$decode_B(src);
         ByteBuffer b = ByteBuffer.wrap(d);
         b.order(ByteOrder.LITTLE_ENDIAN);
         LongBuffer s = b.asLongBuffer();
         long[] data = new long[d.length / 8];
         s.get(data);
         return data;
      }

      private static int[] $d2j$hex$48686141$decode_I(String src) {
         byte[] d = $d2j$hex$48686141$decode_B(src);
         ByteBuffer b = ByteBuffer.wrap(d);
         b.order(ByteOrder.LITTLE_ENDIAN);
         IntBuffer s = b.asIntBuffer();
         int[] data = new int[d.length / 4];
         s.get(data);
         return data;
      }

      private static short[] $d2j$hex$48686141$decode_S(String src) {
         byte[] d = $d2j$hex$48686141$decode_B(src);
         ByteBuffer b = ByteBuffer.wrap(d);
         b.order(ByteOrder.LITTLE_ENDIAN);
         ShortBuffer s = b.asShortBuffer();
         short[] data = new short[d.length / 2];
         s.get(data);
         return data;
      }

      private static byte[] $d2j$hex$48686141$decode_B(String src) {
         char[] d = src.toCharArray();
         byte[] ret = new byte[src.length() / 2];

         for (int i = 0; i < ret.length; i++) {
            char h = d[2 * i];
            char l = d[2 * i + 1];
            int hh;
            if (h >= '0' && h <= '9') {
               hh = h - '0';
            } else if (h >= 'a' && h <= 'f') {
               hh = h - 'a' + 10;
            } else {
               if (h < 'A' || h > 'F') {
                  throw new RuntimeException();
               }

               hh = h - 'A' + 10;
            }

            int ll;
            if (l >= '0' && l <= '9') {
               ll = l - '0';
            } else if (l >= 'a' && l <= 'f') {
               ll = l - 'a' + 10;
            } else {
               if (l < 'A' || l > 'F') {
                  throw new RuntimeException();
               }

               ll = l - 'A' + 10;
            }

            ret[i] = (byte)(hh << 4 | ll);
         }

         return ret;
      }
   }

   static class Encoder extends Base64.Coder {
      static final boolean $assertionsDisabled = false;
      private static final byte[] ENCODE = new byte[]{
         65,
         66,
         67,
         68,
         69,
         70,
         71,
         72,
         73,
         74,
         75,
         76,
         77,
         78,
         79,
         80,
         81,
         82,
         83,
         84,
         85,
         86,
         87,
         88,
         89,
         90,
         97,
         98,
         99,
         100,
         101,
         102,
         103,
         104,
         105,
         106,
         107,
         108,
         109,
         110,
         111,
         112,
         113,
         114,
         115,
         116,
         117,
         118,
         119,
         120,
         121,
         122,
         48,
         49,
         50,
         51,
         52,
         53,
         54,
         55,
         56,
         57,
         43,
         47
      };
      private static final byte[] ENCODE_WEBSAFE = new byte[]{
         65,
         66,
         67,
         68,
         69,
         70,
         71,
         72,
         73,
         74,
         75,
         76,
         77,
         78,
         79,
         80,
         81,
         82,
         83,
         84,
         85,
         86,
         87,
         88,
         89,
         90,
         97,
         98,
         99,
         100,
         101,
         102,
         103,
         104,
         105,
         106,
         107,
         108,
         109,
         110,
         111,
         112,
         113,
         114,
         115,
         116,
         117,
         118,
         119,
         120,
         121,
         122,
         48,
         49,
         50,
         51,
         52,
         53,
         54,
         55,
         56,
         57,
         45,
         95
      };
      public static final int LINE_GROUPS = 19;
      private final byte[] alphabet;
      private int count;
      public final boolean do_cr;
      public final boolean do_newline;
      public final boolean do_padding;
      private final byte[] tail;
      int tailLen;

      public Encoder(int var1, byte[] var2) {
         this.output = var2;
         boolean var4 = true;
         boolean var3;
         if ((var1 & 1) == 0) {
            var3 = true;
         } else {
            var3 = false;
         }

         this.do_padding = var3;
         if ((var1 & 2) == 0) {
            var3 = true;
         } else {
            var3 = false;
         }

         this.do_newline = var3;
         if ((var1 & 4) == 0) {
            var4 = false;
         }

         this.do_cr = var4;
         if ((var1 & 8) == 0) {
            var2 = ENCODE;
         } else {
            var2 = ENCODE_WEBSAFE;
         }

         this.alphabet = var2;
         this.tail = new byte[2];
         this.tailLen = 0;
         byte var5;
         if (var3) {
            var5 = 19;
         } else {
            var5 = -1;
         }

         this.count = var5;
      }

      @Override
      public int maxOutputSize(int var1) {
         return var1 * 8 / 5 + 10;
      }

      // $VF: Irreducible bytecode was duplicated to produce valid code
      @Override
      public boolean process(byte[] var1, int var2, int var3, boolean var4) {
         int var5;
         byte var7;
         byte var8;
         int var9;
         byte[] var11;
         byte[] var12;
         label103: {
            var12 = this.alphabet;
            var11 = this.output;
            var5 = this.count;
            var9 = var3 + var2;
            var3 = this.tailLen;
            var8 = 0;
            var7 = 0;
            if (var3 != 1) {
               if (var3 == 2) {
                  var3 = var2 + 1;
                  if (var3 <= var9) {
                     byte[] var13 = this.tail;
                     byte var6 = var13[0];
                     var2 = (var13[1] & 255) << 8 | (var6 & 255) << 16 | var1[var2] & 255;
                     this.tailLen = 0;
                     break label103;
                  }
               }
            } else if (var2 + 2 <= var9) {
               byte var34 = this.tail[0];
               byte var10 = var1[var2];
               var3 = var2 + 2;
               var2 = var1[var2 + 1] & 255 | (var34 & 255) << 16 | (var10 & 255) << 8;
               this.tailLen = 0;
               break label103;
            }

            byte var33 = -1;
            var3 = var2;
            var2 = var33;
         }

         if (var2 != -1) {
            var11[0] = var12[var2 >> 18 & 63];
            var11[1] = var12[var2 >> 12 & 63];
            var11[2] = var12[var2 >> 6 & 63];
            var11[3] = var12[var2 & 63];
            if (--var5 == 0) {
               byte var16;
               if (this.do_cr) {
                  var11[4] = 13;
                  var16 = 5;
               } else {
                  var16 = 4;
               }

               var5 = var16 + 1;
               var11[var16] = 10;
               var2 = var5;
               var5 = 19;
            } else {
               var2 = 4;
            }
         } else {
            var2 = 0;
         }

         while (true) {
            int var35 = var3 + 3;
            if (var35 > var9) {
               if (var4) {
                  int var50 = this.tailLen;
                  if (var3 - var50 == var9 - 1) {
                     byte var26;
                     byte var36;
                     if (var50 > 0) {
                        var36 = this.tail[0];
                        var26 = 1;
                     } else {
                        var36 = var1[var3];
                        var26 = var7;
                     }

                     var7 = (var36 & 0xFF) << 4;
                     this.tailLen = var50 - var26;
                     var11[var2] = var12[var7 >> 6 & 63];
                     var36 = var2 + 2;
                     var11[var2 + 1] = var12[var7 & 63];
                     var26 = var36;
                     if (this.do_padding) {
                        var11[var36] = 61;
                        var26 = var2 + 4;
                        var11[var2 + 3] = 61;
                     }

                     var2 = var26;
                     if (this.do_newline) {
                        var2 = var26;
                        if (this.do_cr) {
                           var11[var26] = 13;
                           var2 = var26 + 1;
                        }

                        var11[var2] = 10;
                        var2++;
                     }

                     var35 = var2;
                  } else if (var3 - var50 == var9 - 2) {
                     byte var39;
                     byte var45;
                     if (var50 > 1) {
                        var45 = this.tail[0];
                        var39 = 1;
                     } else {
                        var45 = var1[var3];
                        var3++;
                        var39 = var8;
                     }

                     if (var50 > 0) {
                        var1 = this.tail;
                        var3 = var39 + 1;
                        var39 = var1[var39];
                     } else {
                        var8 = var1[var3];
                        var3 = var39;
                        var39 = var8;
                     }

                     var45 = (var45 & 0xFF) << 10 | (var39 & 255) << 2;
                     this.tailLen = var50 - var3;
                     var11[var2] = var12[var45 >> 12 & 63];
                     var11[var2 + 1] = var12[var45 >> 6 & 63];
                     var39 = var2 + 3;
                     var11[var2 + 2] = var12[var45 & 63];
                     var3 = var39;
                     if (this.do_padding) {
                        var11[var39] = 61;
                        var3 = var2 + 4;
                     }

                     var2 = var3;
                     if (this.do_newline) {
                        var2 = var3;
                        if (this.do_cr) {
                           var11[var3] = 13;
                           var2 = var3 + 1;
                        }

                        var11[var2] = 10;
                        var2++;
                     }

                     var35 = var2;
                  } else {
                     var35 = var2;
                     if (this.do_newline) {
                        var35 = var2;
                        if (var2 > 0) {
                           var35 = var2;
                           if (var5 != 19) {
                              var3 = var2;
                              if (this.do_cr) {
                                 var11[var2] = 13;
                                 var3 = var2 + 1;
                              }

                              var11[var3] = 10;
                              var35 = var3 + 1;
                           }
                        }
                     }
                  }
               } else if (var3 == var9 - 1) {
                  var11 = this.tail;
                  var35 = this.tailLen++;
                  var11[var35] = var1[var3];
                  var35 = var2;
               } else {
                  var35 = var2;
                  if (var3 == var9 - 2) {
                     var11 = this.tail;
                     var7 = this.tailLen;
                     var35 = var7 + 1;
                     this.tailLen = var35;
                     var11[var7] = var1[var3];
                     this.tailLen = var7 + 2;
                     var11[var35] = var1[var3 + 1];
                     var35 = var2;
                  }
               }

               this.op = var35;
               this.count = var5;
               return true;
            }

            byte var49 = var1[var3];
            var3 = (var1[var3 + 1] & 255) << 8 | (var49 & 255) << 16 | var1[var3 + 2] & 255;
            var11[var2] = var12[var3 >> 18 & 63];
            var11[var2 + 1] = var12[var3 >> 12 & 63];
            var11[var2 + 2] = var12[var3 >> 6 & 63];
            var11[var2 + 3] = var12[var3 & 63];
            var3 = var2 + 4;
            if (--var5 == 0) {
               var5 = var3;
               if (this.do_cr) {
                  var11[var3] = 13;
                  var5 = var2 + 5;
               }

               var2 = var5 + 1;
               var11[var5] = 10;
               var3 = var35;
               var5 = 19;
            } else {
               var2 = var3;
               var3 = var35;
            }
         }
      }
   }
}
