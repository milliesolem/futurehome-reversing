package okhttp3.internal.http2

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.IntBuffer
import java.nio.LongBuffer
import java.nio.ShortBuffer
import kotlin.jvm.internal.Intrinsics
import okhttp3.internal.Util
import okio.BufferedSink
import okio.BufferedSource
import okio.ByteString

public object Huffman {
   private final val CODES: IntArray =
      $d2j$hex$d887653d$decode_I(
         "f81f0000d8ff7f00e2ffff0fe3ffff0fe4ffff0fe5ffff0fe6ffff0fe7ffff0fe8ffff0feaffff00fcffff3fe9ffff0feaffff0ffdffff3febffff0fecffff0fedffff0feeffff0fefffff0ff0ffff0ff1ffff0ff2ffff0ffeffff3ff3ffff0ff4ffff0ff5ffff0ff6ffff0ff7ffff0ff8ffff0ff9ffff0ffaffff0ffbffff0f14000000f8030000f9030000fa0f0000f91f000015000000f8000000fa070000fa030000fb030000f9000000fb070000fa000000160000001700000018000000000000000100000002000000190000001a0000001b0000001c0000001d0000001e0000001f0000005c000000fb000000fc7f000020000000fb0f0000fc030000fa1f0000210000005d0000005e0000005f000000600000006100000062000000630000006400000065000000660000006700000068000000690000006a0000006b0000006c0000006d0000006e0000006f000000700000007100000072000000fc00000073000000fd000000fb1f0000f0ff0700fc1f0000fc3f000022000000fd7f0000030000002300000004000000240000000500000025000000260000002700000006000000740000007500000028000000290000002a000000070000002b000000760000002c00000008000000090000002d0000007700000078000000790000007a0000007b000000fe7f0000fc070000fd3f0000fd1f0000fcffff0fe6ff0f00d2ff3f00e7ff0f00e8ff0f00d3ff3f00d4ff3f00d5ff3f00d9ff7f00d6ff3f00daff7f00dbff7f00dcff7f00ddff7f00deff7f00ebffff00dfff7f00ecffff00edffff00d7ff3f00e0ff7f00eeffff00e1ff7f00e2ff7f00e3ff7f00e4ff7f00dcff1f00d8ff3f00e5ff7f00d9ff3f00e6ff7f00e7ff7f00efffff00daff3f00ddff1f00e9ff0f00dbff3f00dcff3f00e8ff7f00e9ff7f00deff1f00eaff7f00ddff3f00deff3f00f0ffff00dfff1f00dfff3f00ebff7f00ecff7f00e0ff1f00e1ff1f00e0ff3f00e2ff1f00edff7f00e1ff3f00eeff7f00efff7f00eaff0f00e2ff3f00e3ff3f00e4ff3f00f0ff7f00e5ff3f00e6ff3f00f1ff7f00e0ffff03e1ffff03ebff0f00f1ff0700e7ff3f00f2ff7f00e8ff3f00ecffff01e2ffff03e3ffff03e4ffff03deffff07dfffff07e5ffff03f1ffff00edffff01f2ff0700e3ff1f00e6ffff03e0ffff07e1ffff07e7ffff03e2ffff07f2ffff00e4ff1f00e5ff1f00e8ffff03e9ffff03fdffff0fe3ffff07e4ffff07e5ffff07ecff0f00f3ffff00edff0f00e6ff1f00e9ff3f00e7ff1f00e8ff1f00f3ff7f00eaff3f00ebff3f00eeffff01efffff01f4ffff00f5ffff00eaffff03f4ff7f00ebffff03e6ffff07ecffff03edffff03e7ffff07e8ffff07e9ffff07eaffff07ebffff07feffff0fecffff07edffff07eeffff07efffff07f0ffff07eeffff03"
      )
      private final val CODE_BIT_COUNTS: ByteArray
   private final val root: okhttp3.internal.http2.Huffman.Node = new Huffman.Node()

   @JvmStatic
   fun {
      val var2: Huffman = new Huffman();
      INSTANCE = var2;
      val var3: ByteArray = new byte[]{
         13,
         23,
         28,
         28,
         28,
         28,
         28,
         28,
         28,
         24,
         30,
         28,
         28,
         30,
         28,
         28,
         28,
         28,
         28,
         28,
         28,
         28,
         30,
         28,
         28,
         28,
         28,
         28,
         28,
         28,
         28,
         28,
         6,
         10,
         10,
         12,
         13,
         6,
         8,
         11,
         10,
         10,
         8,
         11,
         8,
         6,
         6,
         6,
         5,
         5,
         5,
         6,
         6,
         6,
         6,
         6,
         6,
         6,
         7,
         8,
         15,
         6,
         12,
         10,
         13,
         6,
         7,
         7,
         7,
         7,
         7,
         7,
         7,
         7,
         7,
         7,
         7,
         7,
         7,
         7,
         7,
         7,
         7,
         7,
         7,
         7,
         7,
         7,
         8,
         7,
         8,
         13,
         19,
         13,
         14,
         6,
         15,
         5,
         6,
         5,
         6,
         5,
         6,
         6,
         6,
         5,
         7,
         7,
         6,
         6,
         6,
         5,
         6,
         7,
         6,
         5,
         5,
         6,
         7,
         7,
         7,
         7,
         7,
         15,
         11,
         14,
         13,
         28,
         20,
         22,
         20,
         20,
         22,
         22,
         22,
         23,
         22,
         23,
         23,
         23,
         23,
         23,
         24,
         23,
         24,
         24,
         22,
         23,
         24,
         23,
         23,
         23,
         23,
         21,
         22,
         23,
         22,
         23,
         23,
         24,
         22,
         21,
         20,
         22,
         22,
         23,
         23,
         21,
         23,
         22,
         22,
         24,
         21,
         22,
         23,
         23,
         21,
         21,
         22,
         21,
         23,
         22,
         23,
         23,
         20,
         22,
         22,
         22,
         23,
         22,
         22,
         23,
         26,
         26,
         20,
         19,
         22,
         23,
         22,
         25,
         26,
         26,
         26,
         27,
         27,
         26,
         24,
         25,
         19,
         21,
         26,
         27,
         27,
         26,
         27,
         24,
         21,
         21,
         26,
         26,
         28,
         27,
         27,
         27,
         20,
         24,
         20,
         21,
         22,
         21,
         21,
         23,
         22,
         22,
         25,
         25,
         24,
         24,
         26,
         23,
         26,
         27,
         26,
         26,
         27,
         27,
         27,
         27,
         27,
         28,
         27,
         27,
         27,
         27,
         27,
         26
      };
      CODE_BIT_COUNTS = var3;
      val var1: Int = var3.length;

      for (int var0 = 0; var0 < var1; var0++) {
         var2.addCode(var0, CODES[var0], CODE_BIT_COUNTS[var0]);
      }
   }

   private fun addCode(symbol: Int, code: Int, codeBitCount: Int) {
      val var6: Huffman.Node = new Huffman.Node(var1, var3);
      var var4: Huffman.Node = root;

      while (var3 > 8) {
         var3 -= 8;
         var1 = var2 ushr var3 and 255;
         val var7: Array<Huffman.Node> = var4.getChildren();
         if (var7 == null) {
            Intrinsics.throwNpe();
         }

         val var5: Huffman.Node = var7[var1];
         var4 = var7[var1];
         if (var5 == null) {
            var4 = new Huffman.Node();
            var7[var1] = var4;
         }
      }

      var1 = 8 - var3;
      var2 = var2 shl 8 - var3 and 255;
      val var11: Array<Huffman.Node> = var4.getChildren();
      if (var11 == null) {
         Intrinsics.throwNpe();
      }

      ArraysKt.fill(var11, var6, var2, (1 shl var1) + var2);
   }

   public fun decode(source: BufferedSource, byteCount: Long, sink: BufferedSink) {
      Intrinsics.checkParameterIsNotNull(var1, "source");
      Intrinsics.checkParameterIsNotNull(var4, "sink");
      var var10: Huffman.Node = root;
      var var6: Int = 0;
      var var8: Long = 0L;
      var var5: Int = 0;

      while (true) {
         var var11: Huffman.Node = var10;
         var var7: Int = var5;
         if (var8 >= var2) {
            while (var7 > 0) {
               val var12: Array<Huffman.Node> = var11.getChildren();
               if (var12 == null) {
                  Intrinsics.throwNpe();
               }

               val var13: Huffman.Node = var12[var6 shl 8 - var7 and 255];
               if (var12[var6 shl 8 - var7 and 255] == null) {
                  Intrinsics.throwNpe();
               }

               if (var13.getChildren() != null || var13.getTerminalBitCount() > var7) {
                  break;
               }

               var4.writeByte(var13.getSymbol());
               var7 -= var13.getTerminalBitCount();
               var11 = root;
            }

            return;
         }

         var6 = var6 shl 8 or Util.and(var1.readByte(), 255);
         var5 += 8;

         while (var5 >= 8) {
            var7 = var5 - 8;
            val var15: Array<Huffman.Node> = var10.getChildren();
            if (var15 == null) {
               Intrinsics.throwNpe();
            }

            var10 = var15[var6 ushr var7 and 255];
            if (var15[var6 ushr var7 and 255] == null) {
               Intrinsics.throwNpe();
            }

            if (var10.getChildren() == null) {
               var4.writeByte(var10.getSymbol());
               var5 -= var10.getTerminalBitCount();
               var10 = root;
            } else {
               var5 = var7;
            }
         }

         var8++;
      }
   }

   @Throws(java/io/IOException::class)
   public fun encode(source: ByteString, sink: BufferedSink) {
      Intrinsics.checkParameterIsNotNull(var1, "source");
      Intrinsics.checkParameterIsNotNull(var2, "sink");
      val var5: Int = var1.size();
      var var8: Long = 0L;
      var var4: Int = 0;

      var var3: Int;
      for (var3 = 0; var4 < var5; var4++) {
         val var7: Int = Util.and(var1.getByte(var4), 255);
         var8 = var8 shl CODE_BIT_COUNTS[var7] or CODES[var7];
         var3 += CODE_BIT_COUNTS[var7];

         while (var3 >= 8) {
            var3 -= 8;
            var2.writeByte((int)(var8 shr var3));
         }
      }

      if (var3 > 0) {
         var2.writeByte((int)(var8 shl 8 - var3 or 255L ushr var3));
      }
   }

   public fun encodedLength(bytes: ByteString): Int {
      Intrinsics.checkParameterIsNotNull(var1, "bytes");
      val var3: Int = var1.size();
      var var5: Long = 0L;

      for (int var2 = 0; var2 < var3; var2++) {
         var5 += CODE_BIT_COUNTS[Util.and(var1.getByte(var2), 255)];
      }

      return (int)(var5 + 7 shr 3);
   }

   @JvmStatic
   fun `$d2j$hex$d887653d$decode_J`(src: java.lang.String): LongArray {
      val d: ByteArray = $d2j$hex$d887653d$decode_B(src);
      val b: ByteBuffer = ByteBuffer.wrap(d);
      b.order(ByteOrder.LITTLE_ENDIAN);
      val s: LongBuffer = b.asLongBuffer();
      val data: LongArray = new long[d.length / 8];
      s.get(data);
      return data;
   }

   @JvmStatic
   fun `$d2j$hex$d887653d$decode_I`(src: java.lang.String): IntArray {
      val d: ByteArray = $d2j$hex$d887653d$decode_B(src);
      val b: ByteBuffer = ByteBuffer.wrap(d);
      b.order(ByteOrder.LITTLE_ENDIAN);
      val s: IntBuffer = b.asIntBuffer();
      val data: IntArray = new int[d.length / 4];
      s.get(data);
      return data;
   }

   @JvmStatic
   fun `$d2j$hex$d887653d$decode_S`(src: java.lang.String): ShortArray {
      val d: ByteArray = $d2j$hex$d887653d$decode_B(src);
      val b: ByteBuffer = ByteBuffer.wrap(d);
      b.order(ByteOrder.LITTLE_ENDIAN);
      val s: ShortBuffer = b.asShortBuffer();
      val data: ShortArray = new short[d.length / 2];
      s.get(data);
      return data;
   }

   @JvmStatic
   fun `$d2j$hex$d887653d$decode_B`(src: java.lang.String): ByteArray {
      val d: CharArray = src.toCharArray();
      val ret: ByteArray = new byte[src.length() / 2];

      for (int i = 0; i < ret.length; i++) {
         val h: Char = d[2 * i];
         val l: Char = d[2 * i + 1];
         val hh: Int;
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

         val ll: Int;
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

         ret[i] = (byte)(hh shl 4 or ll);
      }

      return ret;
   }

   private class Node {
      public final val children: Array<okhttp3.internal.http2.Huffman.Node?>?
      public final val symbol: Int
      public final val terminalBitCount: Int

      public constructor()  {
         this.children = new Huffman.Node[256];
         this.symbol = 0;
         this.terminalBitCount = 0;
      }

      public constructor(symbol: Int, bits: Int)  {
         val var3: Array<Huffman.Node> = null as Array<Huffman.Node>;
         this.children = null;
         this.symbol = var1;
         val var5: Int = var2 and 7;
         var1 = var2 and 7;
         if (var5 == 0) {
            var1 = 8;
         }

         this.terminalBitCount = var1;
      }
   }
}
