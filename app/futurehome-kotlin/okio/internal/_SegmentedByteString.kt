package okio.internal

import okio.Buffer
import okio.ByteString
import okio.Segment
import okio.SegmentedByteString

internal fun IntArray.binarySearch(value: Int, fromIndex: Int, toIndex: Int): Int {
   var3--;

   while (var2 <= var3) {
      val var5: Int = var2 + var3 ushr 1;
      val var4: Int = var0[var2 + var3 ushr 1];
      if (var0[var2 + var3 ushr 1] < var1) {
         var2 = var5 + 1;
      } else {
         if (var4 <= var1) {
            return var5;
         }

         var3 = var5 - 1;
      }
   }

   return -var2 - 1;
}

internal inline fun SegmentedByteString.commonCopyInto(offset: Int, target: ByteArray, targetOffset: Int, byteCount: Int) {
   val var9: Long = var0.size();
   val var13: Long = var1;
   val var11: Long = var4;
   okio._SegmentedByteString.checkOffsetAndCount(var9, var13, (long)var4);
   okio._SegmentedByteString.checkOffsetAndCount((long)var2.length, (long)var3, var11);
   val var6: Int = var4 + var1;
   var var5: Int = segment(var0, var1);
   var4 = var3;
   var3 = var1;

   for (int var15 = var5; var3 < var6; var15++) {
      if (var15 == 0) {
         var5 = 0;
      } else {
         var5 = var0.getDirectory$okio()[var15 - 1];
      }

      var var7: Int = var0.getDirectory$okio()[var15];
      val var8: Int = var0.getDirectory$okio()[(var0.getSegments$okio() as Array<Any>).length + var15];
      var7 = Math.min(var6, var7 - var5 + var5) - var3;
      ArraysKt.copyInto(var0.getSegments$okio()[var15], var2, var4, var8 + (var3 - var5), var8 + (var3 - var5) + var7);
      var4 += var7;
      var3 += var7;
   }
}

internal inline fun SegmentedByteString.commonEquals(other: Any?): Boolean {
   var var2: Boolean = true;
   if (var1 != var0) {
      if (var1 is ByteString && (var1 as ByteString).size() == var0.size() && var0.rangeEquals(0, var1 as ByteString, 0, var0.size())) {
         return true;
      }

      var2 = false;
   }

   return var2;
}

internal inline fun SegmentedByteString.commonGetSize(): Int {
   return var0.getDirectory$okio()[(var0.getSegments$okio() as Array<Any>).length - 1];
}

internal inline fun SegmentedByteString.commonHashCode(): Int {
   val var1: Int = var0.getHashCode$okio();
   if (var1 != 0) {
      return var1;
   } else {
      val var7: Int = (var0.getSegments$okio() as Array<Any>).length;
      var var2: Int = 0;
      var var3: Int = 0;
      var var4: Int = 1;

      while (var2 < var7) {
         val var6: Int = var0.getDirectory$okio()[var7 + var2];
         val var5: Int = var0.getDirectory$okio()[var2];
         val var8: ByteArray = var0.getSegments$okio()[var2];

         for (int var9 = var6; var9 < var5 - var3 + var6; var9++) {
            var4 = var4 * 31 + var8[var9];
         }

         var2++;
         var3 = var5;
      }

      var0.setHashCode$okio(var4);
      return var4;
   }
}

internal inline fun SegmentedByteString.commonInternalGet(pos: Int): Byte {
   okio._SegmentedByteString.checkOffsetAndCount((long)var0.getDirectory$okio()[(var0.getSegments$okio() as Array<Any>).length - 1], (long)var1, 1L);
   val var3: Int = segment(var0, var1);
   val var2: Int;
   if (var3 == 0) {
      var2 = 0;
   } else {
      var2 = var0.getDirectory$okio()[var3 - 1];
   }

   return var0.getSegments$okio()[var3][var1 - var2 + var0.getDirectory$okio()[(var0.getSegments$okio() as Array<Any>).length + var3]];
}

internal inline fun SegmentedByteString.commonRangeEquals(offset: Int, other: ByteString, otherOffset: Int, byteCount: Int): Boolean {
   if (var1 >= 0 && var1 <= var0.size() - var4) {
      val var6: Int = var4 + var1;

      for (int var9 = segment(var0, var1); var1 < var6; var9++) {
         val var5: Int;
         if (var9 == 0) {
            var5 = 0;
         } else {
            var5 = var0.getDirectory$okio()[var9 - 1];
         }

         var var7: Int = var0.getDirectory$okio()[var9];
         val var8: Int = var0.getDirectory$okio()[(var0.getSegments$okio() as Array<Any>).length + var9];
         var7 = Math.min(var6, var7 - var5 + var5) - var1;
         if (!var2.rangeEquals(var3, var0.getSegments$okio()[var9], var8 + (var1 - var5), var7)) {
            return false;
         }

         var3 += var7;
         var1 += var7;
      }

      return true;
   } else {
      return false;
   }
}

internal inline fun SegmentedByteString.commonRangeEquals(offset: Int, other: ByteArray, otherOffset: Int, byteCount: Int): Boolean {
   if (var1 >= 0 && var1 <= var0.size() - var4 && var3 >= 0 && var3 <= var2.length - var4) {
      val var6: Int = var4 + var1;
      var var5: Int = segment(var0, var1);
      var4 = var1;

      for (int var9 = var5; var4 < var6; var9++) {
         if (var9 == 0) {
            var5 = 0;
         } else {
            var5 = var0.getDirectory$okio()[var9 - 1];
         }

         var var7: Int = var0.getDirectory$okio()[var9];
         val var8: Int = var0.getDirectory$okio()[(var0.getSegments$okio() as Array<Any>).length + var9];
         var7 = Math.min(var6, var7 - var5 + var5) - var4;
         if (!okio._SegmentedByteString.arrayRangeEquals(var0.getSegments$okio()[var9], var8 + (var4 - var5), var2, var3, var7)) {
            return false;
         }

         var3 += var7;
         var4 += var7;
      }

      return true;
   } else {
      return false;
   }
}

internal inline fun SegmentedByteString.commonSubstring(beginIndex: Int, endIndex: Int): ByteString {
   val var8: ByteString = var0;
   var2 = okio._SegmentedByteString.resolveDefaultParameter(var0, var2);
   if (var1 < 0) {
      val var12: StringBuilder = new StringBuilder("beginIndex=");
      var12.append(var1);
      var12.append(" < 0");
      throw new IllegalArgumentException(var12.toString().toString());
   } else if (var2 <= var0.size()) {
      val var6: Int = var2 - var1;
      if (var2 - var1 < 0) {
         val var11: StringBuilder = new StringBuilder("endIndex=");
         var11.append(var2);
         var11.append(" < beginIndex=");
         var11.append(var1);
         throw new IllegalArgumentException(var11.toString().toString());
      } else if (var1 == 0 && var2 == var0.size()) {
         return var8;
      } else if (var1 == var2) {
         return ByteString.EMPTY;
      } else {
         val var4: Int = segment(var0, var1);
         val var7: Int = segment(var0, var2 - 1);
         val var9: Array<ByteArray> = ArraysKt.copyOfRange((byte[][])(var0.getSegments$okio() as Array<Any>), var4, var7 + 1);
         val var10: Array<Any> = var9 as Array<Any>;
         val var18: IntArray = new int[(var9 as Array<Any>).length * 2];
         if (var4 <= var7) {
            var2 = var4;
            var var3: Int = 0;

            while (true) {
               var18[var3] = Math.min(var0.getDirectory$okio()[var2] - var1, var6);
               var18[var3 + var10.length] = var0.getDirectory$okio()[(var0.getSegments$okio() as Array<Any>).length + var2];
               if (var2 == var7) {
                  break;
               }

               var2++;
               var3++;
            }
         }

         if (var4 == 0) {
            var2 = 0;
         } else {
            var2 = var0.getDirectory$okio()[var4 - 1];
         }

         var18[var10.length] = var18[var10.length] + (var1 - var2);
         return new SegmentedByteString(var9, var18);
      }
   } else {
      val var17: StringBuilder = new StringBuilder("endIndex=");
      var17.append(var2);
      var17.append(" > length(");
      var17.append(var0.size());
      var17.append(')');
      throw new IllegalArgumentException(var17.toString().toString());
   }
}

internal inline fun SegmentedByteString.commonToByteArray(): ByteArray {
   val var8: ByteArray = new byte[var0.size()];
   val var5: Int = (var0.getSegments$okio() as Array<Any>).length;
   var var2: Int = 0;
   var var3: Int = 0;
   var var1: Int = 0;

   while (var2 < var5) {
      val var6: Int = var0.getDirectory$okio()[var5 + var2];
      val var4: Int = var0.getDirectory$okio()[var2];
      val var7: ByteArray = var0.getSegments$okio()[var2];
      val var9: Int = var4 - var3;
      ArraysKt.copyInto(var7, var8, var1, var6, var6 + (var4 - var3));
      var1 += var9;
      var2++;
      var3 = var4;
   }

   return var8;
}

internal inline fun SegmentedByteString.commonWrite(buffer: Buffer, offset: Int, byteCount: Int) {
   val var6: Int = var2 + var3;
   var var5: Int = segment(var0, var2);
   var var4: Int = var2;

   for (int var11 = var5; var4 < var6; var11++) {
      if (var11 == 0) {
         var5 = 0;
      } else {
         var5 = var0.getDirectory$okio()[var11 - 1];
      }

      var var8: Int = var0.getDirectory$okio()[var11];
      val var7: Int = var0.getDirectory$okio()[(var0.getSegments$okio() as Array<Any>).length + var11];
      var8 = Math.min(var6, var8 - var5 + var5) - var4;
      val var9: Segment = new Segment(var0.getSegments$okio()[var11], var7 + (var4 - var5), var7 + (var4 - var5) + var8, true, false);
      if (var1.head == null) {
         var9.prev = var9;
         var9.next = var9.prev;
         var1.head = var9.next;
      } else {
         val var10: Segment = var1.head;
         val var15: Segment = var10.prev;
         var15.push(var9);
      }

      var4 += var8;
   }

   var1.setSize$okio(var1.size() + (long)var3);
}

private inline fun SegmentedByteString.forEachSegment(beginIndex: Int, endIndex: Int, action: (ByteArray, Int, Int) -> Unit) {
   var var5: Int = segment(var0, var1);
   var var4: Int = var1;

   for (int var8 = var5; var4 < var2; var8++) {
      if (var8 == 0) {
         var5 = 0;
      } else {
         var5 = var0.getDirectory$okio()[var8 - 1];
      }

      var var7: Int = var0.getDirectory$okio()[var8];
      val var6: Int = var0.getDirectory$okio()[(var0.getSegments$okio() as Array<Any>).length + var8];
      var7 = Math.min(var2, var7 - var5 + var5) - var4;
      var3.invoke(var0.getSegments$okio()[var8], var6 + (var4 - var5), var7);
      var4 += var7;
   }
}

internal inline fun SegmentedByteString.forEachSegment(action: (ByteArray, Int, Int) -> Unit) {
   val var5: Int = (var0.getSegments$okio() as Array<Any>).length;
   var var2: Int = 0;
   var var3: Int = 0;

   while (var2 < var5) {
      val var6: Int = var0.getDirectory$okio()[var5 + var2];
      val var4: Int = var0.getDirectory$okio()[var2];
      var1.invoke(var0.getSegments$okio()[var2], var6, var4 - var3);
      var2++;
      var3 = var4;
   }
}

internal fun SegmentedByteString.segment(pos: Int): Int {
   var1 = binarySearch(var0.getDirectory$okio(), var1 + 1, 0, (var0.getSegments$okio() as Array<Any>).length);
   if (var1 < 0) {
      var1 = var1.inv();
   }

   return var1;
}
