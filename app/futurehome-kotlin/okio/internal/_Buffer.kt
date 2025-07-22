package okio.internal

import java.io.EOFException
import okio.Buffer
import okio.ByteString
import okio.Options
import okio.Segment
import okio.SegmentPool
import okio.SegmentedByteString
import okio.Sink
import okio.Source
import okio._JvmPlatformKt
import okio.Buffer.UnsafeCursor

internal final val HEX_DIGIT_BYTES: ByteArray = _JvmPlatformKt.asUtf8ToByteArray("0123456789abcdef")
internal const val OVERFLOW_DIGIT_START: Long = -7L
internal const val OVERFLOW_ZONE: Long = -922337203685477580L
internal const val SEGMENTING_THRESHOLD: Int = 4096

internal inline fun Buffer.commonClear() {
   var0.skip(var0.size());
}

internal inline fun UnsafeCursor.commonClose() {
   if (var0.buffer != null) {
      var0.buffer = null;
      var0.setSegment$okio(null);
      var0.offset = -1L;
      var0.data = null;
      var0.start = -1;
      var0.end = -1;
   } else {
      throw new IllegalStateException("not attached to a buffer".toString());
   }
}

internal inline fun Buffer.commonCompleteSegmentByteCount(): Long {
   val var3: Long = var0.size();
   if (var3 == 0L) {
      return 0L;
   } else {
      val var5: Segment = var0.head;
      val var6: Segment = var5.prev;
      var var1: Long = var3;
      if (var6.limit < 8192) {
         var1 = var3;
         if (var6.owner) {
            var1 = var3 - (var6.limit - var6.pos);
         }
      }

      return var1;
   }
}

internal inline fun Buffer.commonCopy(): Buffer {
   val var2: Buffer = new Buffer();
   if (var0.size() == 0L) {
      return var2;
   } else {
      val var3: Segment = var0.head;
      val var4: Segment = var3.sharedCopy();
      var2.head = var4;
      var4.prev = var2.head;
      var4.next = var4.prev;

      for (Segment var1 = var3.next; var1 != var3; var1 = var1.next) {
         val var5: Segment = var4.prev;
         var5.push(var1.sharedCopy());
      }

      var2.setSize$okio(var0.size());
      return var2;
   }
}

internal inline fun Buffer.commonCopyTo(out: Buffer, offset: Long, byteCount: Long): Buffer {
   okio._SegmentedByteString.checkOffsetAndCount(var0.size(), var2, var4);
   if (var4 == 0L) {
      return var0;
   } else {
      var1.setSize$okio(var1.size() + var4);
      var var10: Segment = var0.head;

      while (true) {
         var var11: Segment = var10;
         var var6: Long = var2;
         var var8: Long = var4;
         if (var2 < var10.limit - var10.pos) {
            while (var8 > 0L) {
               var10 = var11.sharedCopy();
               var10.pos += (int)var6;
               var10.limit = Math.min(var10.pos + (int)var8, var10.limit);
               if (var1.head == null) {
                  var10.prev = var10;
                  var10.next = var10.prev;
                  var1.head = var10.next;
               } else {
                  val var12: Segment = var1.head;
                  val var14: Segment = var12.prev;
                  var14.push(var10);
               }

               var8 -= var10.limit - var10.pos;
               var11 = var11.next;
               var6 = 0L;
            }

            return var0;
         }

         var2 -= var10.limit - var10.pos;
         var10 = var10.next;
      }
   }
}

internal inline fun Buffer.commonEquals(other: Any?): Boolean {
   if (var0 === var1) {
      return true;
   } else if (var1 !is Buffer) {
      return false;
   } else {
      var var5: Long = var0.size();
      var1 = var1;
      if (var5 != var1.size()) {
         return false;
      } else if (var0.size() == 0L) {
         return true;
      } else {
         var var12: Segment = var0.head;
         var var11: Segment = var1.head;
         var var3: Int = var12.pos;
         var var2: Int = var11.pos;
         var5 = 0L;

         while (var5 < var0.size()) {
            val var9: Long = Math.min(var12.limit - var3, var11.limit - var2);
            var var7: Long = 0L;

            var var4: Int;
            for (var4 = var3; var7 < var9; var2++) {
               if (var12.data[var4] != var11.data[var2]) {
                  return false;
               }

               var7++;
               var4++;
            }

            var var14: Segment = var12;
            var3 = var4;
            if (var4 == var12.limit) {
               var14 = var12.next;
               var3 = var14.pos;
            }

            var12 = var11;
            var4 = var2;
            if (var2 == var11.limit) {
               var12 = var11.next;
               var4 = var12.pos;
            }

            var5 += var9;
            var11 = var12;
            var12 = var14;
            var2 = var4;
         }

         return true;
      }
   }
}

internal inline fun UnsafeCursor.commonExpandBuffer(minByteCount: Int): Long {
   if (var1 > 0) {
      if (var1 <= 8192) {
         val var7: Buffer = var0.buffer;
         if (var0.buffer != null) {
            if (var0.readWrite) {
               val var4: Long = var0.buffer.size();
               val var6: Segment = var7.writableSegment$okio(var1);
               var1 = 8192 - var6.limit;
               var6.limit = 8192;
               val var2: Long = var1;
               var7.setSize$okio(var4 + (long)var1);
               var0.setSegment$okio(var6);
               var0.offset = var4;
               var0.data = var6.data;
               var0.start = 8192 - var1;
               var0.end = 8192;
               return var2;
            } else {
               throw new IllegalStateException("expandBuffer() only permitted for read/write buffers".toString());
            }
         } else {
            throw new IllegalStateException("not attached to a buffer".toString());
         }
      } else {
         val var9: StringBuilder = new StringBuilder("minByteCount > Segment.SIZE: ");
         var9.append(var1);
         throw new IllegalArgumentException(var9.toString().toString());
      }
   } else {
      val var8: StringBuilder = new StringBuilder("minByteCount <= 0: ");
      var8.append(var1);
      throw new IllegalArgumentException(var8.toString().toString());
   }
}

internal inline fun Buffer.commonGet(pos: Long): Byte {
   okio._SegmentedByteString.checkOffsetAndCount(var0.size(), var1, 1L);
   var var7: Segment = var0.head;
   if (var0.head == null) {
      throw new NullPointerException();
   } else if (var0.size() - var1 < var1) {
      var var8: Long;
      for (var8 = var0.size(); var8 > var1; var8 -= var7.limit - var7.pos) {
         var7 = var7.prev;
      }

      return var7.data[(int)(var7.pos + var1 - var8)];
   } else {
      var var3: Long = 0L;

      while (true) {
         val var5: Long = var7.limit - var7.pos + var3;
         if (var7.limit - var7.pos + var3 > var1) {
            return var7.data[(int)(var7.pos + var1 - var3)];
         }

         var7 = var7.next;
         var3 = var5;
      }
   }
}

internal inline fun Buffer.commonHashCode(): Int {
   var var5: Segment = var0.head;
   if (var0.head == null) {
      return 0;
   } else {
      var var1: Int = 1;

      var var3: Int;
      val var6: Segment;
      do {
         var var2: Int = var5.pos;
         val var4: Int = var5.limit;

         for (var3 = var1; var2 < var4; var2++) {
            var3 = var3 * 31 + var5.data[var2];
         }

         var6 = var5.next;
         var5 = var6;
         var1 = var3;
      } while (var6 != var0.head);

      return var3;
   }
}

internal inline fun Buffer.commonIndexOf(b: Byte, fromIndex: Long, toIndex: Long): Long {
   if (0L <= var2 && var2 <= var4) {
      var var8: Long = var4;
      if (var4 > var0.size()) {
         var8 = var0.size();
      }

      if (var2 == var8) {
         return -1L;
      } else {
         val var13: Segment = var0.head;
         if (var0.head == null) {
            return -1L;
         } else {
            var4 = 0L;
            var var18: Segment = var0.head;
            if (var0.size() - var2 < var2) {
               var4 = var0.size();

               for (var18 = var13; var4 > var2; var4 -= var18.limit - var18.pos) {
                  var18 = var18.prev;
               }

               var var23: Segment = var18;
               if (var18 == null) {
                  return -1L;
               } else {
                  while (var4 < var8) {
                     val var21: ByteArray = var23.data;
                     val var16: Int = (int)Math.min((long)var23.limit, (long)var23.pos + var8 - var4);

                     for (int var22 = (int)(var23.pos + var2 - var4); var22 < var16; var22++) {
                        if (var21[var22] == var1) {
                           return var22 - var23.pos + var4;
                        }
                     }

                     var4 += var23.limit - var23.pos;
                     var23 = var23.next;
                     var2 = var4;
                  }

                  return -1L;
               }
            } else {
               while (true) {
                  val var17: Long = var18.limit - var18.pos + var4;
                  if (var18.limit - var18.pos + var4 > var2) {
                     var var14: Segment = var18;
                     if (var18 == null) {
                        return -1L;
                     }

                     while (var4 < var8) {
                        val var19: ByteArray = var14.data;
                        val var7: Int = (int)Math.min((long)var14.limit, (long)var14.pos + var8 - var4);

                        for (int var6 = (int)(var14.pos + var2 - var4); var6 < var7; var6++) {
                           if (var19[var6] == var1) {
                              return var6 - var14.pos + var4;
                           }
                        }

                        var4 += var14.limit - var14.pos;
                        var14 = var14.next;
                        var2 = var4;
                     }

                     return -1L;
                  }

                  var18 = var18.next;
                  var4 = var17;
               }
            }
         }
      }
   } else {
      val var12: StringBuilder = new StringBuilder("size=");
      var12.append(var0.size());
      var12.append(" fromIndex=");
      var12.append(var2);
      var12.append(" toIndex=");
      var12.append(var4);
      throw new IllegalArgumentException(var12.toString().toString());
   }
}

internal inline fun Buffer.commonIndexOf(bytes: ByteString, fromIndex: Long): Long {
   if (var1.size() <= 0) {
      throw new IllegalArgumentException("bytes is empty".toString());
   } else {
      var var8: Long = 0L;
      if (var2 >= 0L) {
         val var15: Segment = var0.head;
         if (var0.head == null) {
            return -1L;
         } else {
            var var14: Segment = var0.head;
            if (var0.size() - var2 < var2) {
               var8 = var0.size();

               for (var14 = var15; var8 > var2; var8 -= var14.limit - var14.pos) {
                  var14 = var14.prev;
               }

               if (var14 == null) {
                  return -1L;
               } else {
                  val var31: ByteArray = var1.internalArray$okio();
                  val var24: Byte = var31[0];
                  val var23: Int = var1.size();

                  for (long var27 = var0.size() - var23 + 1L; var8 < var27; var2 = var8) {
                     val var18: ByteArray = var14.data;
                     val var25: Int = (int)Math.min((long)var14.limit, (long)var14.pos + var27 - var8);

                     for (int var22 = (int)(var14.pos + var2 - var8); var22 < var25; var22++) {
                        if (var18[var22] == var24 && rangeEquals(var14, var22 + 1, var31, 1, var23)) {
                           return var22 - var14.pos + var8;
                        }
                     }

                     var8 += var14.limit - var14.pos;
                     var14 = var14.next;
                  }

                  return -1L;
               }
            } else {
               while (true) {
                  val var10: Long = var14.limit - var14.pos + var8;
                  if (var14.limit - var14.pos + var8 > var2) {
                     if (var14 == null) {
                        return -1L;
                     }

                     val var30: ByteArray = var1.internalArray$okio();
                     val var6: Byte = var30[0];
                     val var5: Int = var1.size();

                     for (long var26 = var0.size() - var5 + 1L; var8 < var26; var2 = var8) {
                        val var17: ByteArray = var14.data;
                        val var7: Int = (int)Math.min((long)var14.limit, (long)var14.pos + var26 - var8);

                        for (int var20 = (int)(var14.pos + var2 - var8); var20 < var7; var20++) {
                           if (var17[var20] == var6 && rangeEquals(var14, var20 + 1, var30, 1, var5)) {
                              return var20 - var14.pos + var8;
                           }
                        }

                        var8 += var14.limit - var14.pos;
                        var14 = var14.next;
                     }

                     return -1L;
                  }

                  var14 = var14.next;
                  var8 = var10;
               }
            }
         }
      } else {
         val var16: StringBuilder = new StringBuilder("fromIndex < 0: ");
         var16.append(var2);
         throw new IllegalArgumentException(var16.toString().toString());
      }
   }
}

internal inline fun Buffer.commonIndexOfElement(targetBytes: ByteString, fromIndex: Long): Long {
   var var10: Long = 0L;
   if (var2 >= 0L) {
      val var15: Segment = var0.head;
      if (var0.head == null) {
         return -1L;
      } else {
         var var39: Int;
         label192: {
            var var14: Segment = var0.head;
            if (var0.size() - var2 < var2) {
               var10 = var0.size();

               for (var14 = var15; var10 > var2; var10 -= var14.limit - var14.pos) {
                  var14 = var14.prev;
               }

               if (var14 == null) {
                  return -1L;
               }

               if (var1.size() == 2) {
                  val var27: Byte = var1.getByte(0);

                  for (byte var30 = var1.getByte(1); var10 < var0.size(); var2 = var10) {
                     val var38: ByteArray = var14.data;
                     var var24: Int = (int)(var14.pos + var2 - var10);

                     for (int var33 = var14.limit; var24 < var33; var24++) {
                        val var34: Byte = var38[var24];
                        var2 = var10;
                        var18 = var14;
                        var39 = var24;
                        if (var34 == var27) {
                           break label192;
                        }

                        if (var34 == var30) {
                           var2 = var10;
                           var18 = var14;
                           var39 = var24;
                           break label192;
                        }
                     }

                     var10 += var14.limit - var14.pos;
                     var14 = var14.next;
                  }

                  return -1L;
               }

               val var19: ByteArray = var1.internalArray$okio();

               label137:
               while (true) {
                  if (var10 >= var0.size()) {
                     return -1L;
                  }

                  val var37: ByteArray = var14.data;
                  var39 = (int)(var14.pos + var2 - var10);

                  for (int var26 = var14.limit; var39 < var26; var39++) {
                     val var29: Byte = var37[var39];
                     val var32: Int = var19.length;

                     for (int var22 = 0; var22 < var32; var22++) {
                        if (var29 == var19[var22]) {
                           break label137;
                        }
                     }
                  }

                  var10 += var14.limit - var14.pos;
                  var14 = var14.next;
                  var2 = var10;
               }
            } else {
               label157:
               while (true) {
                  val var12: Long = var14.limit - var14.pos + var10;
                  if (var14.limit - var14.pos + var10 > var2) {
                     if (var14 == null) {
                        return -1L;
                     }

                     if (var1.size() == 2) {
                        val var28: Byte = var1.getByte(0);

                        for (byte var25 = var1.getByte(1); var10 < var0.size(); var2 = var10) {
                           val var36: ByteArray = var14.data;
                           var var21: Int = (int)(var14.pos + var2 - var10);

                           for (int var31 = var14.limit; var21 < var31; var21++) {
                              val var9: Byte = var36[var21];
                              var2 = var10;
                              var18 = var14;
                              var39 = var21;
                              if (var9 == var28) {
                                 break label192;
                              }

                              if (var9 == var25) {
                                 var2 = var10;
                                 var18 = var14;
                                 var39 = var21;
                                 break label192;
                              }
                           }

                           var10 += var14.limit - var14.pos;
                           var14 = var14.next;
                        }

                        return -1L;
                     }

                     for (byte[] var17 = var1.internalArray$okio(); var10 < var0.size(); var2 = var10) {
                        val var35: ByteArray = var14.data;
                        var39 = (int)(var14.pos + var2 - var10);

                        for (int var6 = var14.limit; var39 < var6; var39++) {
                           val var8: Byte = var35[var39];
                           val var7: Int = var17.length;

                           for (int var5 = 0; var5 < var7; var5++) {
                              if (var8 == var17[var5]) {
                                 break label157;
                              }
                           }
                        }

                        var10 += var14.limit - var14.pos;
                        var14 = var14.next;
                     }

                     return -1L;
                  }

                  var14 = var14.next;
                  var10 = var12;
               }
            }

            return var39 - var14.pos + var10;
         }

         return var39 - var18.pos + var2;
      }
   } else {
      val var16: StringBuilder = new StringBuilder("fromIndex < 0: ");
      var16.append(var2);
      throw new IllegalArgumentException(var16.toString().toString());
   }
}

internal inline fun UnsafeCursor.commonNext(): Int {
   var var1: Long = var0.offset;
   val var3: Buffer = var0.buffer;
   if (var1 != var3.size()) {
      if (var0.offset == -1L) {
         var1 = 0L;
      } else {
         var1 = var0.offset + (var0.end - var0.start);
      }

      return var0.seek(var1);
   } else {
      throw new IllegalStateException("no more bytes".toString());
   }
}

internal inline fun Buffer.commonRangeEquals(offset: Long, bytes: ByteString, bytesOffset: Int, byteCount: Int): Boolean {
   if (var1 >= 0L && var4 >= 0 && var5 >= 0 && var0.size() - var1 >= var5 && var3.size() - var4 >= var5) {
      for (int var6 = 0; var6 < var5; var6++) {
         if (var0.getByte((long)var6 + var1) != var3.getByte(var4 + var6)) {
            return false;
         }
      }

      return true;
   } else {
      return false;
   }
}

internal inline fun Buffer.commonRead(sink: ByteArray): Int {
   return var0.read(var1, 0, var1.length);
}

internal inline fun Buffer.commonRead(sink: ByteArray, offset: Int, byteCount: Int): Int {
   okio._SegmentedByteString.checkOffsetAndCount((long)var1.length, (long)var2, (long)var3);
   val var4: Segment = var0.head;
   if (var0.head == null) {
      return -1;
   } else {
      var3 = Math.min(var3, var0.head.limit - var0.head.pos);
      ArraysKt.copyInto(var4.data, var1, var2, var4.pos, var4.pos + var3);
      var4.pos += var3;
      var0.setSize$okio(var0.size() - (long)var3);
      if (var4.pos == var4.limit) {
         var0.head = var4.pop();
         SegmentPool.recycle(var4);
      }

      return var3;
   }
}

internal inline fun Buffer.commonRead(sink: Buffer, byteCount: Long): Long {
   if (var2 >= 0L) {
      if (var0.size() == 0L) {
         return -1L;
      } else {
         var var4: Long = var2;
         if (var2 > var0.size()) {
            var4 = var0.size();
         }

         var1.write(var0, var4);
         return var4;
      }
   } else {
      val var6: StringBuilder = new StringBuilder("byteCount < 0: ");
      var6.append(var2);
      throw new IllegalArgumentException(var6.toString().toString());
   }
}

internal inline fun Buffer.commonReadAll(sink: Sink): Long {
   val var2: Long = var0.size();
   if (var2 > 0L) {
      var1.write(var0, var2);
   }

   return var2;
}

internal fun Buffer.commonReadAndWriteUnsafe(unsafeCursor: UnsafeCursor): UnsafeCursor {
   var1 = okio._SegmentedByteString.resolveDefaultParameter(var1);
   if (var1.buffer == null) {
      var1.buffer = var0;
      var1.readWrite = true;
      return var1;
   } else {
      throw new IllegalStateException("already attached to a buffer".toString());
   }
}

internal inline fun Buffer.commonReadByte(): Byte {
   if (var0.size() != 0L) {
      val var6: Segment = var0.head;
      val var3: Int = var6.limit;
      val var2: Int = var6.pos + 1;
      val var1: Byte = var6.data[var6.pos];
      var0.setSize$okio(var0.size() - 1L);
      if (var2 == var3) {
         var0.head = var6.pop();
         SegmentPool.recycle(var6);
      } else {
         var6.pos = var2;
      }

      return var1;
   } else {
      throw new EOFException();
   }
}

internal inline fun Buffer.commonReadByteArray(): ByteArray {
   return var0.readByteArray(var0.size());
}

internal inline fun Buffer.commonReadByteArray(byteCount: Long): ByteArray {
   if (var1 >= 0L && var1 <= 2147483647L) {
      if (var0.size() >= var1) {
         val var3: ByteArray = new byte[(int)var1];
         var0.readFully(var3);
         return var3;
      } else {
         throw new EOFException();
      }
   } else {
      val var4: StringBuilder = new StringBuilder("byteCount: ");
      var4.append(var1);
      throw new IllegalArgumentException(var4.toString().toString());
   }
}

internal inline fun Buffer.commonReadByteString(): ByteString {
   return var0.readByteString(var0.size());
}

internal inline fun Buffer.commonReadByteString(byteCount: Long): ByteString {
   if (var1 >= 0L && var1 <= 2147483647L) {
      if (var0.size() < var1) {
         throw new EOFException();
      } else if (var1 >= 4096L) {
         val var3: ByteString = var0.snapshot((int)var1);
         var0.skip(var1);
         return var3;
      } else {
         return new ByteString(var0.readByteArray(var1));
      }
   } else {
      val var4: StringBuilder = new StringBuilder("byteCount: ");
      var4.append(var1);
      throw new IllegalArgumentException(var4.toString().toString());
   }
}

internal inline fun Buffer.commonReadDecimalLong(): Long {
   if (var0.size() == 0L) {
      throw new EOFException();
   } else {
      var var2: Int = 0;
      var var9: Long = 0L;
      var var11: Long = -7L;
      var var3: Boolean = false;
      var var1: Byte = 0;

      while (true) {
         val var13: Segment = var0.head;
         val var14: ByteArray = var13.data;
         var var4: Int = var13.pos;
         val var6: Int = var13.limit;

         while (true) {
            label89: {
               var var5: Byte = var1;
               if (var4 < var6) {
                  var5 = var14[var4];
                  if (var14[var4] >= 48 && var14[var4] <= 57) {
                     val var7: Int = 48 - var5;
                     if (var9 < -922337203685477580L || var9 == -922337203685477580L && 48 - var5 < var11) {
                        var0 = new Buffer().writeDecimalLong(var9).writeByte(var5);
                        if (!var3) {
                           var0.readByte();
                        }

                        val var19: StringBuilder = new StringBuilder("Number too large: ");
                        var19.append(var0.readUtf8());
                        throw new NumberFormatException(var19.toString());
                     }

                     var9 = var9 * 10L + var7;
                     break label89;
                  }

                  if (var5 == 45 && var2 == 0) {
                     var11--;
                     var3 = true;
                     break label89;
                  }

                  var5 = 1;
               }

               if (var4 == var6) {
                  var0.head = var13.pop();
                  SegmentPool.recycle(var13);
               } else {
                  var13.pos = var4;
               }

               if (!var5 && var0.head != null) {
                  var1 = var5;
                  break;
               }

               var0.setSize$okio(var0.size() - (long)var2);
               if (var3) {
                  var1 = 2;
               } else {
                  var1 = 1;
               }

               if (var2 < var1) {
                  if (var0.size() != 0L) {
                     val var18: java.lang.String;
                     if (var3) {
                        var18 = "Expected a digit";
                     } else {
                        var18 = "Expected a digit or '-'";
                     }

                     val var20: StringBuilder = new StringBuilder();
                     var20.append(var18);
                     var20.append(" but was 0x");
                     var20.append(okio._SegmentedByteString.toHexString(var0.getByte(0L)));
                     throw new NumberFormatException(var20.toString());
                  }

                  throw new EOFException();
               }

               if (!var3) {
                  var9 = -var9;
               }

               return var9;
            }

            var4++;
            var2++;
         }
      }
   }
}

internal inline fun Buffer.commonReadFully(sink: Buffer, byteCount: Long) {
   if (var0.size() >= var2) {
      var1.write(var0, var2);
   } else {
      var1.write(var0, var0.size());
      throw new EOFException();
   }
}

internal inline fun Buffer.commonReadFully(sink: ByteArray) {
   var var2: Int = 0;

   while (var2 < var1.length) {
      val var3: Int = var0.read(var1, var2, var1.length - var2);
      if (var3 == -1) {
         throw new EOFException();
      }

      var2 += var3;
   }
}

internal inline fun Buffer.commonReadHexadecimalUnsignedLong(): Long {
   if (var0.size() == 0L) {
      throw new EOFException();
   } else {
      var var2: Int = 0;
      var var10: Long = 0L;
      var var3: Boolean = false;

      var var4: Int;
      var var8: Long;
      do {
         val var13: Segment = var0.head;
         val var12: ByteArray = var13.data;
         var var5: Int = var13.pos;
         val var7: Int = var13.limit;
         var8 = var10;
         var4 = var2;

         var var6: Boolean;
         while (true) {
            var6 = var3;
            if (var5 >= var7) {
               break;
            }

            val var1: Byte = var12[var5];
            if (var12[var5] >= 48 && var12[var5] <= 57) {
               var2 = var1 - 48;
            } else if (var1 >= 97 && var1 <= 102) {
               var2 = var1 - 87;
            } else {
               if (var1 < 65 || var1 > 70) {
                  if (var4 == 0) {
                     val var14: StringBuilder = new StringBuilder("Expected leading [0-9a-fA-F] character but was 0x");
                     var14.append(okio._SegmentedByteString.toHexString(var1));
                     throw new NumberFormatException(var14.toString());
                  }

                  var6 = true;
                  break;
               }

               var2 = var1 - 55;
            }

            if ((-1152921504606846976L and var8) != 0L) {
               var0 = new Buffer().writeHexadecimalUnsignedLong(var8).writeByte(var1);
               val var17: StringBuilder = new StringBuilder("Number too large: ");
               var17.append(var0.readUtf8());
               throw new NumberFormatException(var17.toString());
            }

            var8 = var8 shl 4 or var2;
            var5++;
            var4++;
         }

         if (var5 == var7) {
            var0.head = var13.pop();
            SegmentPool.recycle(var13);
         } else {
            var13.pos = var5;
         }

         if (var6) {
            break;
         }

         var2 = var4;
         var3 = var6;
         var10 = var8;
      } while (var0.head != null);

      var0.setSize$okio(var0.size() - (long)var4);
      return var8;
   }
}

internal inline fun Buffer.commonReadInt(): Int {
   if (var0.size() >= 4L) {
      val var8: Segment = var0.head;
      val var5: Int = var8.limit;
      if (var8.limit - var8.pos < 4L) {
         return var0.readByte() and 255 or (var0.readByte() and 255) shl 24 or (var0.readByte() and 255) shl 16 or (var0.readByte() and 255) shl 8;
      } else {
         val var3: Byte = var8.data[var8.pos];
         val var1: Byte = var8.data[var8.pos + 1];
         val var2: Byte = var8.data[var8.pos + 2];
         val var4: Int = var8.pos + 4;
         val var12: Byte = var8.data[var8.pos + 3];
         var0.setSize$okio(var0.size() - 4L);
         if (var4 == var5) {
            var0.head = var8.pop();
            SegmentPool.recycle(var8);
         } else {
            var8.pos = var4;
         }

         return var12 and 255 or (var1 and 255) shl 16 or (var3 and 255) shl 24 or (var2 and 255) shl 8;
      }
   } else {
      throw new EOFException();
   }
}

internal inline fun Buffer.commonReadLong(): Long {
   if (var0.size() >= 8L) {
      val var20: Segment = var0.head;
      val var2: Int = var20.limit;
      if (var20.limit - var20.pos < 8L) {
         return (var0.readInt() and 4294967295L) shl 32 or 4294967295L and var0.readInt();
      } else {
         val var10: Long = var20.data[var20.pos];
         val var18: Long = var20.data[var20.pos + 1];
         val var4: Long = var20.data[var20.pos + 2];
         val var6: Long = var20.data[var20.pos + 3];
         val var14: Long = var20.data[var20.pos + 4];
         val var8: Long = var20.data[var20.pos + 5];
         val var12: Long = var20.data[var20.pos + 6];
         val var1: Int = var20.pos + 8;
         val var16: Long = var20.data[var20.pos + 7];
         var0.setSize$okio(var0.size() - 8L);
         if (var1 == var2) {
            var0.head = var20.pop();
            SegmentPool.recycle(var20);
         } else {
            var20.pos = var1;
         }

         return (var6 and 255L) shl 32 or (var10 and 255L) shl 56 or (var18 and 255L) shl 48 or (var4 and 255L) shl 40 or (var14 and 255L) shl 24 or (
            var8 and 255L
         ) shl 16 or (var12 and 255L) shl 8 or var16 and 255L;
      }
   } else {
      throw new EOFException();
   }
}

internal inline fun Buffer.commonReadShort(): Short {
   if (var0.size() >= 2L) {
      val var6: Segment = var0.head;
      val var3: Int = var6.limit;
      if (var6.limit - var6.pos < 2) {
         return (short)(var0.readByte() and 255 or (var0.readByte() and 255) shl 8);
      } else {
         val var2: Byte = var6.data[var6.pos];
         val var1: Int = var6.pos + 2;
         val var8: Byte = var6.data[var6.pos + 1];
         var0.setSize$okio(var0.size() - 2L);
         if (var1 == var3) {
            var0.head = var6.pop();
            SegmentPool.recycle(var6);
         } else {
            var6.pos = var1;
         }

         return (short)(var8 and 255 or (var2 and 255) shl 8);
      }
   } else {
      throw new EOFException();
   }
}

internal fun Buffer.commonReadUnsafe(unsafeCursor: UnsafeCursor): UnsafeCursor {
   var1 = okio._SegmentedByteString.resolveDefaultParameter(var1);
   if (var1.buffer == null) {
      var1.buffer = var0;
      var1.readWrite = false;
      return var1;
   } else {
      throw new IllegalStateException("already attached to a buffer".toString());
   }
}

internal inline fun Buffer.commonReadUtf8(byteCount: Long): String {
   val var11: Long;
   val var3: Byte = (byte)(if ((var11 = var1 - 0L) == 0L) 0 else (if (var11 < 0L) -1 else 1));
   if (var1 >= 0L && var1 <= 2147483647L) {
      if (var0.size() < var1) {
         throw new EOFException();
      } else if (var3 == 0) {
         return "";
      } else {
         val var6: Segment = var0.head;
         if (var6.pos + var1 > var6.limit) {
            return _Utf8Kt.commonToUtf8String$default(var0.readByteArray(var1), 0, 0, 3, null);
         } else {
            val var10: java.lang.String = _Utf8Kt.commonToUtf8String(var6.data, var6.pos, var6.pos + (int)var1);
            var6.pos += (int)var1;
            var0.setSize$okio(var0.size() - var1);
            if (var6.pos == var6.limit) {
               var0.head = var6.pop();
               SegmentPool.recycle(var6);
            }

            return var10;
         }
      }
   } else {
      val var8: StringBuilder = new StringBuilder("byteCount: ");
      var8.append(var1);
      throw new IllegalArgumentException(var8.toString().toString());
   }
}

internal inline fun Buffer.commonReadUtf8CodePoint(): Int {
   if (var0.size() == 0L) {
      throw new EOFException();
   } else {
      val var1: Byte = var0.getByte(0L);
      var var5: Int = 1;
      var var2: Int;
      val var3: Byte;
      val var4: Int;
      if ((var1 and 128) == 0) {
         var2 = var1 and 127;
         var3 = 1;
         var4 = 0;
      } else if ((var1 and 224) == 192) {
         var2 = var1 and 31;
         var3 = 2;
         var4 = 128;
      } else if ((var1 and 240) == 224) {
         var2 = var1 and 15;
         var3 = 3;
         var4 = 2048;
      } else {
         if ((var1 and 248) != 240) {
            var0.skip(1L);
            return 65533;
         }

         var2 = var1 and 7;
         var3 = 4;
         var4 = 65536;
      }

      var var10: Long = var0.size();
      val var8: Long = var3;
      if (var10 < var3) {
         val var12: StringBuilder = new StringBuilder("size < ");
         var12.append((int)var3);
         var12.append(": ");
         var12.append(var0.size());
         var12.append(" (to read code point prefixed 0x");
         var12.append(okio._SegmentedByteString.toHexString(var1));
         var12.append(')');
         throw new EOFException(var12.toString());
      } else {
         while (var5 < var3) {
            var10 = var5;
            val var7: Byte = var0.getByte((long)var5);
            if ((var7 and 192) != 128) {
               var0.skip(var10);
               return 65533;
            }

            var2 = var2 shl 6 or var7 and 63;
            var5++;
         }

         var0.skip(var8);
         if (var2 > 1114111) {
            var2 = 65533;
         } else if (55296 <= var2 && var2 < 57344) {
            var2 = 65533;
         } else if (var2 < var4) {
            var2 = 65533;
         }

         return var2;
      }
   }
}

internal inline fun Buffer.commonReadUtf8Line(): String? {
   val var1: Long = var0.indexOf((byte)10);
   val var3: java.lang.String;
   if (var1 != -1L) {
      var3 = readUtf8Line(var0, var1);
   } else if (var0.size() != 0L) {
      var3 = var0.readUtf8(var0.size());
   } else {
      var3 = null;
   }

   return var3;
}

internal inline fun Buffer.commonReadUtf8LineStrict(limit: Long): String {
   if (var1 >= 0L) {
      var var3: Long = java.lang.Long.MAX_VALUE;
      if (var1 != java.lang.Long.MAX_VALUE) {
         var3 = var1 + 1L;
      }

      val var5: Long = var0.indexOf((byte)10, 0L, var3);
      if (var5 != -1L) {
         return readUtf8Line(var0, var5);
      } else if (var3 < var0.size() && var0.getByte(var3 - 1L) == 13 && var0.getByte(var3) == 10) {
         return readUtf8Line(var0, var3);
      } else {
         val var8: Buffer = new Buffer();
         var0.copyTo(var8, 0L, Math.min((long)32, var0.size()));
         val var7: StringBuilder = new StringBuilder("\\n not found: limit=");
         var7.append(Math.min(var0.size(), var1));
         var7.append(" content=");
         var7.append(var8.readByteString().hex());
         var7.append('…');
         throw new EOFException(var7.toString());
      }
   } else {
      val var9: StringBuilder = new StringBuilder("limit < 0: ");
      var9.append(var1);
      throw new IllegalArgumentException(var9.toString().toString());
   }
}

internal inline fun UnsafeCursor.commonResizeBuffer(newSize: Long): Long {
   val var12: Buffer = var0.buffer;
   if (var0.buffer != null) {
      if (!var0.readWrite) {
         throw new IllegalStateException("resizeBuffer() only permitted for read/write buffers".toString());
      } else {
         val var8: Long = var0.buffer.size();
         val var19: Long;
         val var3: Byte = (byte)(if ((var19 = var1 - var8) == 0L) 0 else (if (var19 < 0L) -1 else 1));
         if (var1 <= var8) {
            if (var1 < 0L) {
               val var14: StringBuilder = new StringBuilder("newSize < 0: ");
               var14.append(var1);
               throw new IllegalArgumentException(var14.toString().toString());
            }

            var var6: Long = var8 - var1;

            while (var6 > 0L) {
               val var13: Segment = var12.head;
               val var17: Segment = var13.prev;
               val var10: Long = var17.limit - var17.pos;
               if (var17.limit - var17.pos > var6) {
                  var17.limit -= (int)var6;
                  break;
               }

               var12.head = var17.pop();
               SegmentPool.recycle(var17);
               var6 -= var10;
            }

            var0.setSegment$okio(null);
            var0.offset = var1;
            var0.data = null;
            var0.start = -1;
            var0.end = -1;
         } else if (var3 > 0) {
            var var16: Long = var1 - var8;
            var var4: Boolean = true;

            while (var16 > 0L) {
               val var18: Segment = var12.writableSegment$okio(1);
               val var5: Int = (int)Math.min(var16, (long)(8192 - var18.limit));
               var18.limit += var5;
               var16 -= var5;
               var var15: Boolean = var4;
               if (var4) {
                  var0.setSegment$okio(var18);
                  var0.offset = var8;
                  var0.data = var18.data;
                  var0.start = var18.limit - var5;
                  var0.end = var18.limit;
                  var15 = false;
               }

               var4 = var15;
            }
         }

         var12.setSize$okio(var1);
         return var8;
      }
   } else {
      throw new IllegalStateException("not attached to a buffer".toString());
   }
}

internal inline fun UnsafeCursor.commonSeek(offset: Long): Int {
   val var16: Buffer = var0.buffer;
   if (var0.buffer == null) {
      throw new IllegalStateException("not attached to a buffer".toString());
   } else {
      val var29: Long;
      var var3: Int = if ((var29 = var1 - -1L) == 0L) 0 else (if (var29 < 0L) -1 else 1);
      if (var1 < -1L || var1 > var0.buffer.size()) {
         val var18: StringBuilder = new StringBuilder("offset=");
         var18.append(var1);
         var18.append(" > size=");
         var18.append(var16.size());
         throw new ArrayIndexOutOfBoundsException(var18.toString());
      } else if (var3 != 0 && var1 != var16.size()) {
         var var8: Long = var16.size();
         val var14: Segment = var16.head;
         val var15: Segment = var16.head;
         val var17: Segment = var0.getSegment$okio();
         var var4: Long = var8;
         var var12: Segment = var14;
         var var13: Segment = var15;
         var var6: Long = 0L;
         if (var17 != null) {
            var4 = var0.offset;
            var3 = var0.start;
            var12 = var0.getSegment$okio();
            var4 = var4 - (var3 - var12.pos);
            if (var4 - (var3 - var12.pos) > var1) {
               var13 = var0.getSegment$okio();
               var12 = var14;
               var6 = 0L;
            } else {
               var12 = var0.getSegment$okio();
               var6 = var4;
               var13 = var15;
               var4 = var8;
            }
         }

         var8 = var4;
         if (var4 - var1 > var1 - var6) {
            var13 = var12;

            while (true) {
               var12 = var13;
               var4 = var6;
               if (var1 < var13.limit - var13.pos + var6) {
                  break;
               }

               var6 += var13.limit - var13.pos;
               var13 = var13.next;
            }
         } else {
            while (var8 > var1) {
               var13 = var13.prev;
               var8 -= var13.limit - var13.pos;
            }

            var4 = var8;
            var12 = var13;
         }

         var13 = var12;
         if (var0.readWrite) {
            var13 = var12;
            if (var12.shared) {
               var13 = var12.unsharedCopy();
               if (var16.head === var12) {
                  var16.head = var13;
               }

               var13 = var12.push(var13);
               var12 = var13.prev;
               var12.pop();
            }
         }

         var0.setSegment$okio(var13);
         var0.offset = var1;
         var0.data = var13.data;
         var0.start = var13.pos + (int)(var1 - var4);
         var0.end = var13.limit;
         return var0.end - var0.start;
      } else {
         var0.setSegment$okio(null);
         var0.offset = var1;
         var0.data = null;
         var0.start = -1;
         var0.end = -1;
         return -1;
      }
   }
}

internal inline fun Buffer.commonSelect(options: Options): Int {
   val var2: Int = selectPrefix$default(var0, var1, false, 2, null);
   if (var2 == -1) {
      return -1;
   } else {
      var0.skip((long)var1.getByteStrings$okio()[var2].size());
      return var2;
   }
}

internal inline fun Buffer.commonSkip(byteCount: Long) {
   while (var1 > 0L) {
      val var8: Segment = var0.head;
      if (var0.head == null) {
         throw new EOFException();
      }

      val var3: Int = (int)Math.min(var1, (long)(var0.head.limit - var0.head.pos));
      val var6: Long = var0.size();
      var var4: Long = var3;
      var0.setSize$okio(var6 - (long)var3);
      var4 = var1 - var4;
      var8.pos += var3;
      var1 = var4;
      if (var8.pos == var8.limit) {
         var0.head = var8.pop();
         SegmentPool.recycle(var8);
         var1 = var4;
      }
   }
}

internal inline fun Buffer.commonSnapshot(): ByteString {
   if (var0.size() <= 2147483647L) {
      return var0.snapshot((int)var0.size());
   } else {
      val var1: StringBuilder = new StringBuilder("size > Int.MAX_VALUE: ");
      var1.append(var0.size());
      throw new IllegalStateException(var1.toString().toString());
   }
}

internal inline fun Buffer.commonSnapshot(byteCount: Int): ByteString {
   if (var1 == 0) {
      return ByteString.EMPTY;
   } else {
      okio._SegmentedByteString.checkOffsetAndCount(var0.size(), 0L, (long)var1);
      var var5: Segment = var0.head;
      var var3: Int = 0;

      var var2: Int;
      for (var2 = 0; var3 < var1; var5 = var5.next) {
         if (var5.limit == var5.pos) {
            throw new AssertionError("s.limit == s.pos");
         }

         var3 += var5.limit - var5.pos;
         var2++;
      }

      val var10: Array<ByteArray> = new byte[var2][];
      val var6: IntArray = new int[var2 * 2];
      var var7: Segment = var0.head;
      var2 = 0;

      for (int var9 = 0; var9 < var1; var7 = var7.next) {
         var10[var2] = var7.data;
         var9 += var7.limit - var7.pos;
         var6[var2] = Math.min(var9, var1);
         var6[(var10 as Array<Any>).length + var2] = var7.pos;
         var7.shared = true;
         var2++;
      }

      return new SegmentedByteString(var10, var6);
   }
}

internal inline fun Buffer.commonWritableSegment(minimumCapacity: Int): Segment {
   if (var1 < 1 || var1 > 8192) {
      throw new IllegalArgumentException("unexpected capacity".toString());
   } else if (var0.head == null) {
      val var5: Segment = SegmentPool.take();
      var0.head = var5;
      var5.prev = var5;
      var5.next = var5;
      return var5;
   } else {
      val var3: Segment = var0.head;
      val var2: Segment = var3.prev;
      return if (var2.limit + var1 <= 8192 && var2.owner) var2 else var2.push(SegmentPool.take());
   }
}

internal inline fun Buffer.commonWrite(byteString: ByteString, offset: Int = 0, byteCount: Int = var1.size()): Buffer {
   var1.write$okio(var0, var2, var3);
   return var0;
}

internal inline fun Buffer.commonWrite(source: Source, byteCount: Long): Buffer {
   while (var2 > 0L) {
      val var4: Long = var1.read(var0, var2);
      if (var4 == -1L) {
         throw new EOFException();
      }

      var2 -= var4;
   }

   return var0;
}

internal inline fun Buffer.commonWrite(source: ByteArray): Buffer {
   return var0.write(var1, 0, var1.length);
}

internal inline fun Buffer.commonWrite(source: ByteArray, offset: Int, byteCount: Int): Buffer {
   val var9: Long = var1.length;
   val var11: Long = var2;
   val var7: Long = var3;
   okio._SegmentedByteString.checkOffsetAndCount(var9, var11, (long)var3);
   val var4: Int = var3 + var2;

   while (var2 < var4) {
      val var13: Segment = var0.writableSegment$okio(1);
      val var5: Int = Math.min(var4 - var2, 8192 - var13.limit);
      var3 = var2 + var5;
      ArraysKt.copyInto(var1, var13.data, var13.limit, var2, var2 + var5);
      var13.limit += var5;
      var2 = var3;
   }

   var0.setSize$okio(var0.size() + var7);
   return var0;
}

internal inline fun Buffer.commonWrite(source: Buffer, byteCount: Long) {
   if (var1 != var0) {
      okio._SegmentedByteString.checkOffsetAndCount(var1.size(), 0L, var2);

      while (var2 > 0L) {
         var var7: Segment = var1.head;
         var var4: Int = var7.limit;
         var7 = var1.head;
         if (var2 < var4 - var7.pos) {
            if (var0.head != null) {
               var7 = var0.head;
               var7 = var7.prev;
            } else {
               var7 = null;
            }

            if (var7 != null && var7.owner) {
               val var5: Long = var7.limit;
               if (var7.shared) {
                  var4 = 0;
               } else {
                  var4 = var7.pos;
               }

               if (var5 + var2 - var4 <= 8192L) {
                  val var17: Segment = var1.head;
                  var17.writeTo(var7, (int)var2);
                  var1.setSize$okio(var1.size() - var2);
                  var0.setSize$okio(var0.size() + var2);
                  return;
               }
            }

            var7 = var1.head;
            var1.head = var7.split((int)var2);
         }

         var7 = var1.head;
         val var10: Long = var7.limit - var7.pos;
         var1.head = var7.pop();
         if (var0.head == null) {
            var0.head = var7;
            var7.prev = var7;
            var7.next = var7.prev;
         } else {
            val var8: Segment = var0.head;
            val var16: Segment = var8.prev;
            var16.push(var7).compact();
         }

         var1.setSize$okio(var1.size() - var10);
         var0.setSize$okio(var0.size() + var10);
         var2 -= var10;
      }
   } else {
      throw new IllegalArgumentException("source == this".toString());
   }
}

@JvmSynthetic
fun `commonWrite$default`(var0: Buffer, var1: ByteString, var2: Int, var3: Int, var4: Int, var5: Any): Buffer {
   if ((var4 and 2) != 0) {
      var2 = 0;
   }

   if ((var4 and 4) != 0) {
      var3 = var1.size();
   }

   var1.write$okio(var0, var2, var3);
   return var0;
}

internal inline fun Buffer.commonWriteAll(source: Source): Long {
   var var2: Long = 0L;

   while (true) {
      val var4: Long = var1.read(var0, 8192L);
      if (var4 == -1L) {
         return var2;
      }

      var2 += var4;
   }
}

internal inline fun Buffer.commonWriteByte(b: Int): Buffer {
   val var3: Segment = var0.writableSegment$okio(1);
   var3.data[var3.limit++] = (byte)var1;
   var0.setSize$okio(var0.size() + 1L);
   return var0;
}

internal inline fun Buffer.commonWriteDecimalLong(v: Long): Buffer {
   val var13: Long;
   val var4: Byte = (byte)(if ((var13 = var1 - 0L) == 0L) 0 else (if (var13 < 0L) -1 else 1));
   if (var1 == 0L) {
      return var0.writeByte(48);
   } else {
      var var3: Int = 1;
      val var12: Boolean;
      if (var4 < 0) {
         var1 = -var1;
         if (var1 < 0L) {
            return var0.writeUtf8("-9223372036854775808");
         }

         var12 = true;
      } else {
         var12 = false;
      }

      if (var1 < 100000000L) {
         if (var1 < 10000L) {
            if (var1 < 100L) {
               if (var1 >= 10L) {
                  var3 = 2;
               }
            } else if (var1 < 1000L) {
               var3 = 3;
            } else {
               var3 = 4;
            }
         } else if (var1 < 1000000L) {
            if (var1 < 100000L) {
               var3 = 5;
            } else {
               var3 = 6;
            }
         } else if (var1 < 10000000L) {
            var3 = 7;
         } else {
            var3 = 8;
         }
      } else if (var1 < 1000000000000L) {
         if (var1 < 10000000000L) {
            if (var1 < 1000000000L) {
               var3 = 9;
            } else {
               var3 = 10;
            }
         } else if (var1 < 100000000000L) {
            var3 = 11;
         } else {
            var3 = 12;
         }
      } else if (var1 < 1000000000000000L) {
         if (var1 < 10000000000000L) {
            var3 = 13;
         } else if (var1 < 100000000000000L) {
            var3 = 14;
         } else {
            var3 = 15;
         }
      } else if (var1 < 100000000000000000L) {
         if (var1 < 10000000000000000L) {
            var3 = 16;
         } else {
            var3 = 17;
         }
      } else if (var1 < 1000000000000000000L) {
         var3 = 18;
      } else {
         var3 = 19;
      }

      var var5: Int = var3;
      if (var12) {
         var5 = var3 + 1;
      }

      val var10: Segment = var0.writableSegment$okio(var5);
      val var9: ByteArray = var10.data;
      var3 = var10.limit + var5;

      while (var1 != 0L) {
         var9[--var3] = getHEX_DIGIT_BYTES()[(int)(var1 % 10)];
         var1 /= 10;
      }

      if (var12) {
         var9[var3 - 1] = 45;
      }

      var10.limit += var5;
      var0.setSize$okio(var0.size() + (long)var5);
      return var0;
   }
}

internal inline fun Buffer.commonWriteHexadecimalUnsignedLong(v: Long): Buffer {
   if (var1 == 0L) {
      return var0.writeByte(48);
   } else {
      val var4: Int = (int)(
         (
               (
                     (
                           (
                                 (
                                       (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                             ) ushr 8 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8
                                             ) ushr 16 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8
                                                ) ushr 16
                                             ) ushr 32
                                          )
                                          - (
                                             (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8
                                                ) ushr 16 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16
                                                ) ushr 32
                                             ) ushr 1 and 6148914691236517205L
                                          ) ushr 2 and 3689348814741910323L
                                    )
                                    + (
                                       (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                             ) ushr 8 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8
                                             ) ushr 16 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8
                                                ) ushr 16
                                             ) ushr 32
                                          )
                                          - (
                                             (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8
                                                ) ushr 16 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16
                                                ) ushr 32
                                             ) ushr 1 and 6148914691236517205L
                                          ) and 3689348814741910323L
                                    ) ushr 4
                              )
                              + (
                                 (
                                       var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                          var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                       ) ushr 8 or (
                                          var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                          ) ushr 8
                                       ) ushr 16 or (
                                          var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                          ) ushr 8 or (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                             ) ushr 8
                                          ) ushr 16
                                       ) ushr 32
                                    )
                                    - (
                                       (
                                          var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                          ) ushr 8 or (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                             ) ushr 8
                                          ) ushr 16 or (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                             ) ushr 8 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8
                                             ) ushr 16
                                          ) ushr 32
                                       ) ushr 1 and 6148914691236517205L
                                    ) ushr 2 and 3689348814741910323L
                              )
                              + (
                                 (
                                       var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                          var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                       ) ushr 8 or (
                                          var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                          ) ushr 8
                                       ) ushr 16 or (
                                          var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                          ) ushr 8 or (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                             ) ushr 8
                                          ) ushr 16
                                       ) ushr 32
                                    )
                                    - (
                                       (
                                          var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                          ) ushr 8 or (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                             ) ushr 8
                                          ) ushr 16 or (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                             ) ushr 8 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8
                                             ) ushr 16
                                          ) ushr 32
                                       ) ushr 1 and 6148914691236517205L
                                    ) and 3689348814741910323L
                              ) and 1085102592571150095L
                        )
                        + (
                           (
                              (
                                    (
                                          (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8
                                                ) ushr 16 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16
                                                ) ushr 32
                                             )
                                             - (
                                                (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8
                                                      ) ushr 16
                                                   ) ushr 32
                                                ) ushr 1 and 6148914691236517205L
                                             ) ushr 2 and 3689348814741910323L
                                       )
                                       + (
                                          (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8
                                                ) ushr 16 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16
                                                ) ushr 32
                                             )
                                             - (
                                                (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8
                                                      ) ushr 16
                                                   ) ushr 32
                                                ) ushr 1 and 6148914691236517205L
                                             ) and 3689348814741910323L
                                       ) ushr 4
                                 )
                                 + (
                                    (
                                          var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                          ) ushr 8 or (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                             ) ushr 8
                                          ) ushr 16 or (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                             ) ushr 8 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8
                                             ) ushr 16
                                          ) ushr 32
                                       )
                                       - (
                                          (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                             ) ushr 8 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8
                                             ) ushr 16 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8
                                                ) ushr 16
                                             ) ushr 32
                                          ) ushr 1 and 6148914691236517205L
                                       ) ushr 2 and 3689348814741910323L
                                 )
                                 + (
                                    (
                                          var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                          ) ushr 8 or (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                             ) ushr 8
                                          ) ushr 16 or (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                             ) ushr 8 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8
                                             ) ushr 16
                                          ) ushr 32
                                       )
                                       - (
                                          (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                             ) ushr 8 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8
                                             ) ushr 16 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8
                                                ) ushr 16
                                             ) ushr 32
                                          ) ushr 1 and 6148914691236517205L
                                       ) and 3689348814741910323L
                                 ) and 1085102592571150095L
                           ) ushr 8
                        )
                        + (
                           (
                                 (
                                       (
                                             (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8
                                                      ) ushr 16
                                                   ) ushr 32
                                                )
                                                - (
                                                   (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8
                                                      ) ushr 16 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4
                                                            ) ushr 8
                                                         ) ushr 16
                                                      ) ushr 32
                                                   ) ushr 1 and 6148914691236517205L
                                                ) ushr 2 and 3689348814741910323L
                                          )
                                          + (
                                             (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8
                                                      ) ushr 16
                                                   ) ushr 32
                                                )
                                                - (
                                                   (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8
                                                      ) ushr 16 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4
                                                            ) ushr 8
                                                         ) ushr 16
                                                      ) ushr 32
                                                   ) ushr 1 and 6148914691236517205L
                                                ) and 3689348814741910323L
                                          ) ushr 4
                                    )
                                    + (
                                       (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                             ) ushr 8 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8
                                             ) ushr 16 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8
                                                ) ushr 16
                                             ) ushr 32
                                          )
                                          - (
                                             (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8
                                                ) ushr 16 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16
                                                ) ushr 32
                                             ) ushr 1 and 6148914691236517205L
                                          ) ushr 2 and 3689348814741910323L
                                    )
                                    + (
                                       (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                             ) ushr 8 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8
                                             ) ushr 16 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8
                                                ) ushr 16
                                             ) ushr 32
                                          )
                                          - (
                                             (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8
                                                ) ushr 16 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16
                                                ) ushr 32
                                             ) ushr 1 and 6148914691236517205L
                                          ) and 3689348814741910323L
                                    ) and 1085102592571150095L
                              )
                              + (
                                 (
                                    (
                                          (
                                                (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8
                                                      ) ushr 16 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4
                                                            ) ushr 8
                                                         ) ushr 16
                                                      ) ushr 32
                                                   )
                                                   - (
                                                      (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4
                                                            ) ushr 8
                                                         ) ushr 16 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4
                                                            ) ushr 8 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                     var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                                  ) ushr 4
                                                               ) ushr 8
                                                            ) ushr 16
                                                         ) ushr 32
                                                      ) ushr 1 and 6148914691236517205L
                                                   ) ushr 2 and 3689348814741910323L
                                             )
                                             + (
                                                (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8
                                                      ) ushr 16 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4
                                                            ) ushr 8
                                                         ) ushr 16
                                                      ) ushr 32
                                                   )
                                                   - (
                                                      (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4
                                                            ) ushr 8
                                                         ) ushr 16 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4
                                                            ) ushr 8 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                     var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                                  ) ushr 4
                                                               ) ushr 8
                                                            ) ushr 16
                                                         ) ushr 32
                                                      ) ushr 1 and 6148914691236517205L
                                                   ) and 3689348814741910323L
                                             ) ushr 4
                                       )
                                       + (
                                          (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8
                                                ) ushr 16 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16
                                                ) ushr 32
                                             )
                                             - (
                                                (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8
                                                      ) ushr 16
                                                   ) ushr 32
                                                ) ushr 1 and 6148914691236517205L
                                             ) ushr 2 and 3689348814741910323L
                                       )
                                       + (
                                          (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8
                                                ) ushr 16 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16
                                                ) ushr 32
                                             )
                                             - (
                                                (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8
                                                      ) ushr 16
                                                   ) ushr 32
                                                ) ushr 1 and 6148914691236517205L
                                             ) and 3689348814741910323L
                                       ) and 1085102592571150095L
                                 ) ushr 8
                              ) ushr 16
                        ) and 63L
                  )
                  + (
                     (
                           (
                                 (
                                       (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                             ) ushr 8 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8
                                             ) ushr 16 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8
                                                ) ushr 16
                                             ) ushr 32
                                          )
                                          - (
                                             (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8
                                                ) ushr 16 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16
                                                ) ushr 32
                                             ) ushr 1 and 6148914691236517205L
                                          ) ushr 2 and 3689348814741910323L
                                    )
                                    + (
                                       (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                             ) ushr 8 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8
                                             ) ushr 16 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8
                                                ) ushr 16
                                             ) ushr 32
                                          )
                                          - (
                                             (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8
                                                ) ushr 16 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16
                                                ) ushr 32
                                             ) ushr 1 and 6148914691236517205L
                                          ) and 3689348814741910323L
                                    ) ushr 4
                              )
                              + (
                                 (
                                       var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                          var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                       ) ushr 8 or (
                                          var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                          ) ushr 8
                                       ) ushr 16 or (
                                          var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                          ) ushr 8 or (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                             ) ushr 8
                                          ) ushr 16
                                       ) ushr 32
                                    )
                                    - (
                                       (
                                          var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                          ) ushr 8 or (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                             ) ushr 8
                                          ) ushr 16 or (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                             ) ushr 8 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8
                                             ) ushr 16
                                          ) ushr 32
                                       ) ushr 1 and 6148914691236517205L
                                    ) ushr 2 and 3689348814741910323L
                              )
                              + (
                                 (
                                       var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                          var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                       ) ushr 8 or (
                                          var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                          ) ushr 8
                                       ) ushr 16 or (
                                          var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                          ) ushr 8 or (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                             ) ushr 8
                                          ) ushr 16
                                       ) ushr 32
                                    )
                                    - (
                                       (
                                          var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                          ) ushr 8 or (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                             ) ushr 8
                                          ) ushr 16 or (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                             ) ushr 8 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8
                                             ) ushr 16
                                          ) ushr 32
                                       ) ushr 1 and 6148914691236517205L
                                    ) and 3689348814741910323L
                              ) and 1085102592571150095L
                        )
                        + (
                           (
                              (
                                    (
                                          (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8
                                                ) ushr 16 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16
                                                ) ushr 32
                                             )
                                             - (
                                                (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8
                                                      ) ushr 16
                                                   ) ushr 32
                                                ) ushr 1 and 6148914691236517205L
                                             ) ushr 2 and 3689348814741910323L
                                       )
                                       + (
                                          (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8
                                                ) ushr 16 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16
                                                ) ushr 32
                                             )
                                             - (
                                                (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8
                                                      ) ushr 16
                                                   ) ushr 32
                                                ) ushr 1 and 6148914691236517205L
                                             ) and 3689348814741910323L
                                       ) ushr 4
                                 )
                                 + (
                                    (
                                          var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                          ) ushr 8 or (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                             ) ushr 8
                                          ) ushr 16 or (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                             ) ushr 8 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8
                                             ) ushr 16
                                          ) ushr 32
                                       )
                                       - (
                                          (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                             ) ushr 8 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8
                                             ) ushr 16 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8
                                                ) ushr 16
                                             ) ushr 32
                                          ) ushr 1 and 6148914691236517205L
                                       ) ushr 2 and 3689348814741910323L
                                 )
                                 + (
                                    (
                                          var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                          ) ushr 8 or (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                             ) ushr 8
                                          ) ushr 16 or (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                             ) ushr 8 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8
                                             ) ushr 16
                                          ) ushr 32
                                       )
                                       - (
                                          (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                             ) ushr 8 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8
                                             ) ushr 16 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8
                                                ) ushr 16
                                             ) ushr 32
                                          ) ushr 1 and 6148914691236517205L
                                       ) and 3689348814741910323L
                                 ) and 1085102592571150095L
                           ) ushr 8
                        )
                        + (
                           (
                                 (
                                       (
                                             (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8
                                                      ) ushr 16
                                                   ) ushr 32
                                                )
                                                - (
                                                   (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8
                                                      ) ushr 16 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4
                                                            ) ushr 8
                                                         ) ushr 16
                                                      ) ushr 32
                                                   ) ushr 1 and 6148914691236517205L
                                                ) ushr 2 and 3689348814741910323L
                                          )
                                          + (
                                             (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8
                                                      ) ushr 16
                                                   ) ushr 32
                                                )
                                                - (
                                                   (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8
                                                      ) ushr 16 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4
                                                            ) ushr 8
                                                         ) ushr 16
                                                      ) ushr 32
                                                   ) ushr 1 and 6148914691236517205L
                                                ) and 3689348814741910323L
                                          ) ushr 4
                                    )
                                    + (
                                       (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                             ) ushr 8 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8
                                             ) ushr 16 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8
                                                ) ushr 16
                                             ) ushr 32
                                          )
                                          - (
                                             (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8
                                                ) ushr 16 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16
                                                ) ushr 32
                                             ) ushr 1 and 6148914691236517205L
                                          ) ushr 2 and 3689348814741910323L
                                    )
                                    + (
                                       (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                             ) ushr 8 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8
                                             ) ushr 16 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8
                                                ) ushr 16
                                             ) ushr 32
                                          )
                                          - (
                                             (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8
                                                ) ushr 16 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16
                                                ) ushr 32
                                             ) ushr 1 and 6148914691236517205L
                                          ) and 3689348814741910323L
                                    ) and 1085102592571150095L
                              )
                              + (
                                 (
                                    (
                                          (
                                                (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8
                                                      ) ushr 16 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4
                                                            ) ushr 8
                                                         ) ushr 16
                                                      ) ushr 32
                                                   )
                                                   - (
                                                      (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4
                                                            ) ushr 8
                                                         ) ushr 16 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4
                                                            ) ushr 8 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                     var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                                  ) ushr 4
                                                               ) ushr 8
                                                            ) ushr 16
                                                         ) ushr 32
                                                      ) ushr 1 and 6148914691236517205L
                                                   ) ushr 2 and 3689348814741910323L
                                             )
                                             + (
                                                (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8
                                                      ) ushr 16 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4
                                                            ) ushr 8
                                                         ) ushr 16
                                                      ) ushr 32
                                                   )
                                                   - (
                                                      (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4
                                                            ) ushr 8
                                                         ) ushr 16 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4
                                                            ) ushr 8 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                     var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                                  ) ushr 4
                                                               ) ushr 8
                                                            ) ushr 16
                                                         ) ushr 32
                                                      ) ushr 1 and 6148914691236517205L
                                                   ) and 3689348814741910323L
                                             ) ushr 4
                                       )
                                       + (
                                          (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8
                                                ) ushr 16 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16
                                                ) ushr 32
                                             )
                                             - (
                                                (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8
                                                      ) ushr 16
                                                   ) ushr 32
                                                ) ushr 1 and 6148914691236517205L
                                             ) ushr 2 and 3689348814741910323L
                                       )
                                       + (
                                          (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8
                                                ) ushr 16 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16
                                                ) ushr 32
                                             )
                                             - (
                                                (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8
                                                      ) ushr 16
                                                   ) ushr 32
                                                ) ushr 1 and 6148914691236517205L
                                             ) and 3689348814741910323L
                                       ) and 1085102592571150095L
                                 ) ushr 8
                              ) ushr 16
                        ) ushr 32 and 63L
                  )
                  + 3
            )
            / 4
      );
      val var8: Segment = var0.writableSegment$okio(
         (int)(
            (
                  (
                        (
                              (
                                    (
                                          (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8
                                                ) ushr 16 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16
                                                ) ushr 32
                                             )
                                             - (
                                                (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8
                                                      ) ushr 16
                                                   ) ushr 32
                                                ) ushr 1 and 6148914691236517205L
                                             ) ushr 2 and 3689348814741910323L
                                       )
                                       + (
                                          (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8
                                                ) ushr 16 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16
                                                ) ushr 32
                                             )
                                             - (
                                                (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8
                                                      ) ushr 16
                                                   ) ushr 32
                                                ) ushr 1 and 6148914691236517205L
                                             ) and 3689348814741910323L
                                       ) ushr 4
                                 )
                                 + (
                                    (
                                          var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                          ) ushr 8 or (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                             ) ushr 8
                                          ) ushr 16 or (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                             ) ushr 8 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8
                                             ) ushr 16
                                          ) ushr 32
                                       )
                                       - (
                                          (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                             ) ushr 8 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8
                                             ) ushr 16 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8
                                                ) ushr 16
                                             ) ushr 32
                                          ) ushr 1 and 6148914691236517205L
                                       ) ushr 2 and 3689348814741910323L
                                 )
                                 + (
                                    (
                                          var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                          ) ushr 8 or (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                             ) ushr 8
                                          ) ushr 16 or (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                             ) ushr 8 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8
                                             ) ushr 16
                                          ) ushr 32
                                       )
                                       - (
                                          (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                             ) ushr 8 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8
                                             ) ushr 16 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8
                                                ) ushr 16
                                             ) ushr 32
                                          ) ushr 1 and 6148914691236517205L
                                       ) and 3689348814741910323L
                                 ) and 1085102592571150095L
                           )
                           + (
                              (
                                 (
                                       (
                                             (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8
                                                      ) ushr 16
                                                   ) ushr 32
                                                )
                                                - (
                                                   (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8
                                                      ) ushr 16 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4
                                                            ) ushr 8
                                                         ) ushr 16
                                                      ) ushr 32
                                                   ) ushr 1 and 6148914691236517205L
                                                ) ushr 2 and 3689348814741910323L
                                          )
                                          + (
                                             (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8
                                                      ) ushr 16
                                                   ) ushr 32
                                                )
                                                - (
                                                   (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8
                                                      ) ushr 16 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4
                                                            ) ushr 8
                                                         ) ushr 16
                                                      ) ushr 32
                                                   ) ushr 1 and 6148914691236517205L
                                                ) and 3689348814741910323L
                                          ) ushr 4
                                    )
                                    + (
                                       (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                             ) ushr 8 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8
                                             ) ushr 16 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8
                                                ) ushr 16
                                             ) ushr 32
                                          )
                                          - (
                                             (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8
                                                ) ushr 16 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16
                                                ) ushr 32
                                             ) ushr 1 and 6148914691236517205L
                                          ) ushr 2 and 3689348814741910323L
                                    )
                                    + (
                                       (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                             ) ushr 8 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8
                                             ) ushr 16 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8
                                                ) ushr 16
                                             ) ushr 32
                                          )
                                          - (
                                             (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8
                                                ) ushr 16 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16
                                                ) ushr 32
                                             ) ushr 1 and 6148914691236517205L
                                          ) and 3689348814741910323L
                                    ) and 1085102592571150095L
                              ) ushr 8
                           )
                           + (
                              (
                                    (
                                          (
                                                (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8
                                                      ) ushr 16 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4
                                                            ) ushr 8
                                                         ) ushr 16
                                                      ) ushr 32
                                                   )
                                                   - (
                                                      (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4
                                                            ) ushr 8
                                                         ) ushr 16 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4
                                                            ) ushr 8 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                     var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                                  ) ushr 4
                                                               ) ushr 8
                                                            ) ushr 16
                                                         ) ushr 32
                                                      ) ushr 1 and 6148914691236517205L
                                                   ) ushr 2 and 3689348814741910323L
                                             )
                                             + (
                                                (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8
                                                      ) ushr 16 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4
                                                            ) ushr 8
                                                         ) ushr 16
                                                      ) ushr 32
                                                   )
                                                   - (
                                                      (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4
                                                            ) ushr 8
                                                         ) ushr 16 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4
                                                            ) ushr 8 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                     var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                                  ) ushr 4
                                                               ) ushr 8
                                                            ) ushr 16
                                                         ) ushr 32
                                                      ) ushr 1 and 6148914691236517205L
                                                   ) and 3689348814741910323L
                                             ) ushr 4
                                       )
                                       + (
                                          (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8
                                                ) ushr 16 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16
                                                ) ushr 32
                                             )
                                             - (
                                                (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8
                                                      ) ushr 16
                                                   ) ushr 32
                                                ) ushr 1 and 6148914691236517205L
                                             ) ushr 2 and 3689348814741910323L
                                       )
                                       + (
                                          (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8
                                                ) ushr 16 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16
                                                ) ushr 32
                                             )
                                             - (
                                                (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8
                                                      ) ushr 16
                                                   ) ushr 32
                                                ) ushr 1 and 6148914691236517205L
                                             ) and 3689348814741910323L
                                       ) and 1085102592571150095L
                                 )
                                 + (
                                    (
                                       (
                                             (
                                                   (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4
                                                            ) ushr 8
                                                         ) ushr 16 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4
                                                            ) ushr 8 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                     var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                                  ) ushr 4
                                                               ) ushr 8
                                                            ) ushr 16
                                                         ) ushr 32
                                                      )
                                                      - (
                                                         (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4
                                                            ) ushr 8 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                     var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                                  ) ushr 4
                                                               ) ushr 8
                                                            ) ushr 16 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                     var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                                  ) ushr 4
                                                               ) ushr 8 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                     var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                                  ) ushr 4 or (
                                                                     var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                        var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                                     ) ushr 4
                                                                  ) ushr 8
                                                               ) ushr 16
                                                            ) ushr 32
                                                         ) ushr 1 and 6148914691236517205L
                                                      ) ushr 2 and 3689348814741910323L
                                                )
                                                + (
                                                   (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4
                                                            ) ushr 8
                                                         ) ushr 16 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4
                                                            ) ushr 8 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                     var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                                  ) ushr 4
                                                               ) ushr 8
                                                            ) ushr 16
                                                         ) ushr 32
                                                      )
                                                      - (
                                                         (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4
                                                            ) ushr 8 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                     var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                                  ) ushr 4
                                                               ) ushr 8
                                                            ) ushr 16 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                     var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                                  ) ushr 4
                                                               ) ushr 8 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                     var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                                  ) ushr 4 or (
                                                                     var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                        var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                                     ) ushr 4
                                                                  ) ushr 8
                                                               ) ushr 16
                                                            ) ushr 32
                                                         ) ushr 1 and 6148914691236517205L
                                                      ) and 3689348814741910323L
                                                ) ushr 4
                                          )
                                          + (
                                             (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8
                                                      ) ushr 16
                                                   ) ushr 32
                                                )
                                                - (
                                                   (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8
                                                      ) ushr 16 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4
                                                            ) ushr 8
                                                         ) ushr 16
                                                      ) ushr 32
                                                   ) ushr 1 and 6148914691236517205L
                                                ) ushr 2 and 3689348814741910323L
                                          )
                                          + (
                                             (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8
                                                      ) ushr 16
                                                   ) ushr 32
                                                )
                                                - (
                                                   (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8
                                                      ) ushr 16 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4
                                                            ) ushr 8
                                                         ) ushr 16
                                                      ) ushr 32
                                                   ) ushr 1 and 6148914691236517205L
                                                ) and 3689348814741910323L
                                          ) and 1085102592571150095L
                                    ) ushr 8
                                 ) ushr 16
                           ) and 63L
                     )
                     + (
                        (
                              (
                                    (
                                          (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8
                                                ) ushr 16 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16
                                                ) ushr 32
                                             )
                                             - (
                                                (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8
                                                      ) ushr 16
                                                   ) ushr 32
                                                ) ushr 1 and 6148914691236517205L
                                             ) ushr 2 and 3689348814741910323L
                                       )
                                       + (
                                          (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8
                                                ) ushr 16 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16
                                                ) ushr 32
                                             )
                                             - (
                                                (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8
                                                      ) ushr 16
                                                   ) ushr 32
                                                ) ushr 1 and 6148914691236517205L
                                             ) and 3689348814741910323L
                                       ) ushr 4
                                 )
                                 + (
                                    (
                                          var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                          ) ushr 8 or (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                             ) ushr 8
                                          ) ushr 16 or (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                             ) ushr 8 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8
                                             ) ushr 16
                                          ) ushr 32
                                       )
                                       - (
                                          (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                             ) ushr 8 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8
                                             ) ushr 16 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8
                                                ) ushr 16
                                             ) ushr 32
                                          ) ushr 1 and 6148914691236517205L
                                       ) ushr 2 and 3689348814741910323L
                                 )
                                 + (
                                    (
                                          var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                          ) ushr 8 or (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                             ) ushr 8
                                          ) ushr 16 or (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                             ) ushr 8 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8
                                             ) ushr 16
                                          ) ushr 32
                                       )
                                       - (
                                          (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                             ) ushr 8 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8
                                             ) ushr 16 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8
                                                ) ushr 16
                                             ) ushr 32
                                          ) ushr 1 and 6148914691236517205L
                                       ) and 3689348814741910323L
                                 ) and 1085102592571150095L
                           )
                           + (
                              (
                                 (
                                       (
                                             (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8
                                                      ) ushr 16
                                                   ) ushr 32
                                                )
                                                - (
                                                   (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8
                                                      ) ushr 16 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4
                                                            ) ushr 8
                                                         ) ushr 16
                                                      ) ushr 32
                                                   ) ushr 1 and 6148914691236517205L
                                                ) ushr 2 and 3689348814741910323L
                                          )
                                          + (
                                             (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8
                                                      ) ushr 16
                                                   ) ushr 32
                                                )
                                                - (
                                                   (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8
                                                      ) ushr 16 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4
                                                            ) ushr 8
                                                         ) ushr 16
                                                      ) ushr 32
                                                   ) ushr 1 and 6148914691236517205L
                                                ) and 3689348814741910323L
                                          ) ushr 4
                                    )
                                    + (
                                       (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                             ) ushr 8 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8
                                             ) ushr 16 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8
                                                ) ushr 16
                                             ) ushr 32
                                          )
                                          - (
                                             (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8
                                                ) ushr 16 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16
                                                ) ushr 32
                                             ) ushr 1 and 6148914691236517205L
                                          ) ushr 2 and 3689348814741910323L
                                    )
                                    + (
                                       (
                                             var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                             ) ushr 8 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8
                                             ) ushr 16 or (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8
                                                ) ushr 16
                                             ) ushr 32
                                          )
                                          - (
                                             (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8
                                                ) ushr 16 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16
                                                ) ushr 32
                                             ) ushr 1 and 6148914691236517205L
                                          ) and 3689348814741910323L
                                    ) and 1085102592571150095L
                              ) ushr 8
                           )
                           + (
                              (
                                    (
                                          (
                                                (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8
                                                      ) ushr 16 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4
                                                            ) ushr 8
                                                         ) ushr 16
                                                      ) ushr 32
                                                   )
                                                   - (
                                                      (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4
                                                            ) ushr 8
                                                         ) ushr 16 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4
                                                            ) ushr 8 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                     var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                                  ) ushr 4
                                                               ) ushr 8
                                                            ) ushr 16
                                                         ) ushr 32
                                                      ) ushr 1 and 6148914691236517205L
                                                   ) ushr 2 and 3689348814741910323L
                                             )
                                             + (
                                                (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8
                                                      ) ushr 16 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4
                                                            ) ushr 8
                                                         ) ushr 16
                                                      ) ushr 32
                                                   )
                                                   - (
                                                      (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4
                                                            ) ushr 8
                                                         ) ushr 16 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4
                                                            ) ushr 8 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                     var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                                  ) ushr 4
                                                               ) ushr 8
                                                            ) ushr 16
                                                         ) ushr 32
                                                      ) ushr 1 and 6148914691236517205L
                                                   ) and 3689348814741910323L
                                             ) ushr 4
                                       )
                                       + (
                                          (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8
                                                ) ushr 16 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16
                                                ) ushr 32
                                             )
                                             - (
                                                (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8
                                                      ) ushr 16
                                                   ) ushr 32
                                                ) ushr 1 and 6148914691236517205L
                                             ) ushr 2 and 3689348814741910323L
                                       )
                                       + (
                                          (
                                                var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4
                                                ) ushr 8 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8
                                                ) ushr 16 or (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16
                                                ) ushr 32
                                             )
                                             - (
                                                (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8
                                                      ) ushr 16
                                                   ) ushr 32
                                                ) ushr 1 and 6148914691236517205L
                                             ) and 3689348814741910323L
                                       ) and 1085102592571150095L
                                 )
                                 + (
                                    (
                                       (
                                             (
                                                   (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4
                                                            ) ushr 8
                                                         ) ushr 16 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4
                                                            ) ushr 8 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                     var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                                  ) ushr 4
                                                               ) ushr 8
                                                            ) ushr 16
                                                         ) ushr 32
                                                      )
                                                      - (
                                                         (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4
                                                            ) ushr 8 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                     var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                                  ) ushr 4
                                                               ) ushr 8
                                                            ) ushr 16 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                     var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                                  ) ushr 4
                                                               ) ushr 8 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                     var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                                  ) ushr 4 or (
                                                                     var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                        var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                                     ) ushr 4
                                                                  ) ushr 8
                                                               ) ushr 16
                                                            ) ushr 32
                                                         ) ushr 1 and 6148914691236517205L
                                                      ) ushr 2 and 3689348814741910323L
                                                )
                                                + (
                                                   (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4
                                                            ) ushr 8
                                                         ) ushr 16 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4
                                                            ) ushr 8 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                     var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                                  ) ushr 4
                                                               ) ushr 8
                                                            ) ushr 16
                                                         ) ushr 32
                                                      )
                                                      - (
                                                         (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4
                                                            ) ushr 8 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                     var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                                  ) ushr 4
                                                               ) ushr 8
                                                            ) ushr 16 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                     var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                                  ) ushr 4
                                                               ) ushr 8 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                     var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                                  ) ushr 4 or (
                                                                     var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                        var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                                     ) ushr 4
                                                                  ) ushr 8
                                                               ) ushr 16
                                                            ) ushr 32
                                                         ) ushr 1 and 6148914691236517205L
                                                      ) and 3689348814741910323L
                                                ) ushr 4
                                          )
                                          + (
                                             (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8
                                                      ) ushr 16
                                                   ) ushr 32
                                                )
                                                - (
                                                   (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8
                                                      ) ushr 16 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4
                                                            ) ushr 8
                                                         ) ushr 16
                                                      ) ushr 32
                                                   ) ushr 1 and 6148914691236517205L
                                                ) ushr 2 and 3689348814741910323L
                                          )
                                          + (
                                             (
                                                   var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2) ushr 4 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4
                                                   ) ushr 8 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8
                                                   ) ushr 16 or (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8
                                                      ) ushr 16
                                                   ) ushr 32
                                                )
                                                - (
                                                   (
                                                      var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                      ) ushr 4 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4
                                                      ) ushr 8 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8
                                                      ) ushr 16 or (
                                                         var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                         ) ushr 4 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4
                                                         ) ushr 8 or (
                                                            var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                            ) ushr 4 or (
                                                               var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2 or (
                                                                  var1 ushr 1 or var1 or (var1 ushr 1 or var1) ushr 2
                                                               ) ushr 4
                                                            ) ushr 8
                                                         ) ushr 16
                                                      ) ushr 32
                                                   ) ushr 1 and 6148914691236517205L
                                                ) and 3689348814741910323L
                                          ) and 1085102592571150095L
                                    ) ushr 8
                                 ) ushr 16
                           ) ushr 32 and 63L
                     )
                     + (long)3
               )
               / (long)4
         )
      );
      val var9: ByteArray = var8.data;
      var var3: Int = var8.limit + var4 - 1;

      for (int var5 = var8.limit; var3 >= var5; var3--) {
         var9[var3] = getHEX_DIGIT_BYTES()[(int)(15L and var1)];
         var1 >>>= 4;
      }

      var8.limit += var4;
      var0.setSize$okio(var0.size() + (long)var4);
      return var0;
   }
}

internal inline fun Buffer.commonWriteInt(i: Int): Buffer {
   val var3: Segment = var0.writableSegment$okio(4);
   var3.data[var3.limit] = (byte)(var1 ushr 24 and 255);
   var3.data[var3.limit + 1] = (byte)(var1 ushr 16 and 255);
   var3.data[var3.limit + 2] = (byte)(var1 ushr 8 and 255);
   var3.data[var3.limit + 3] = (byte)(var1 and 255);
   var3.limit += 4;
   var0.setSize$okio(var0.size() + 4L);
   return var0;
}

internal inline fun Buffer.commonWriteLong(v: Long): Buffer {
   val var4: Segment = var0.writableSegment$okio(8);
   var4.data[var4.limit] = (byte)(var1 ushr 56 and 255L);
   var4.data[var4.limit + 1] = (byte)(var1 ushr 48 and 255L);
   var4.data[var4.limit + 2] = (byte)(var1 ushr 40 and 255L);
   var4.data[var4.limit + 3] = (byte)(var1 ushr 32 and 255L);
   var4.data[var4.limit + 4] = (byte)(var1 ushr 24 and 255L);
   var4.data[var4.limit + 5] = (byte)(var1 ushr 16 and 255L);
   var4.data[var4.limit + 6] = (byte)(var1 ushr 8 and 255L);
   var4.data[var4.limit + 7] = (byte)(var1 and 255L);
   var4.limit += 8;
   var0.setSize$okio(var0.size() + 8L);
   return var0;
}

internal inline fun Buffer.commonWriteShort(s: Int): Buffer {
   val var3: Segment = var0.writableSegment$okio(2);
   var3.data[var3.limit] = (byte)(var1 ushr 8 and 255);
   var3.data[var3.limit + 1] = (byte)(var1 and 255);
   var3.limit += 2;
   var0.setSize$okio(var0.size() + 2L);
   return var0;
}

internal inline fun Buffer.commonWriteUtf8(string: String, beginIndex: Int, endIndex: Int): Buffer {
   if (var2 < 0) {
      val var12: StringBuilder = new StringBuilder("beginIndex < 0: ");
      var12.append(var2);
      throw new IllegalArgumentException(var12.toString().toString());
   } else if (var3 < var2) {
      val var11: StringBuilder = new StringBuilder("endIndex < beginIndex: ");
      var11.append(var3);
      var11.append(" < ");
      var11.append(var2);
      throw new IllegalArgumentException(var11.toString().toString());
   } else if (var3 > var1.length()) {
      val var10: StringBuilder = new StringBuilder("endIndex > string.length: ");
      var10.append(var3);
      var10.append(" > ");
      var10.append(var1.length());
      throw new IllegalArgumentException(var10.toString().toString());
   } else {
      while (var2 < var3) {
         var var6: Char = var1.charAt(var2);
         if (var6 < 128) {
            val var21: Segment = var0.writableSegment$okio(1);
            val var9: ByteArray = var21.data;
            val var7: Int = var21.limit - var2;
            val var17: Int = Math.min(var3, 8192 - (var21.limit - var2));
            var var14: Int = var2 + 1;
            var9[var2 + var7] = (byte)var6;
            var2 = var14;

            while (var2 < var17) {
               var6 = var1.charAt(var2);
               if (var6 >= 128) {
                  break;
               }

               var14 = var2 + 1;
               var9[var2 + var7] = (byte)var6;
               var2 = var14;
            }

            var14 = var7 + var2 - var21.limit;
            var21.limit = var21.limit + (var7 + var2 - var21.limit);
            var0.setSize$okio(var0.size() + (long)var14);
         } else {
            if (var6 < 2048) {
               val var8: Segment = var0.writableSegment$okio(2);
               var8.data[var8.limit] = (byte)(var6 shr 6 or 192);
               var8.data[var8.limit + 1] = (byte)(var6 and 63 or 128);
               var8.limit += 2;
               var0.setSize$okio(var0.size() + 2L);
            } else {
               if (var6 >= '\ud800' && var6 <= '\udfff') {
                  val var5: Int = var2 + 1;
                  var var4: Char;
                  if (var2 + 1 < var3) {
                     var4 = var1.charAt(var5);
                  } else {
                     var4 = 0;
                  }

                  if (var6 <= '\udbff' && 56320 <= var4 && var4 < 57344) {
                     var4 = ((var6 and 1023) shl 10 or var4 and 1023) + 65536;
                     val var20: Segment = var0.writableSegment$okio(4);
                     var20.data[var20.limit] = (byte)(var4 shr 18 or 240);
                     var20.data[var20.limit + 1] = (byte)(var4 shr 12 and 63 or 128);
                     var20.data[var20.limit + 2] = (byte)(var4 shr 6 and 63 or 128);
                     var20.data[var20.limit + 3] = (byte)(var4 and 63 or 128);
                     var20.limit += 4;
                     var0.setSize$okio(var0.size() + 4L);
                     var2 += 2;
                     continue;
                  }

                  var0.writeByte(63);
                  var2 = var5;
                  continue;
               }

               val var19: Segment = var0.writableSegment$okio(3);
               var19.data[var19.limit] = (byte)(var6 shr 12 or 224);
               var19.data[var19.limit + 1] = (byte)(var6 shr 6 and 63 or 128);
               var19.data[var19.limit + 2] = (byte)(var6 and 63 or 128);
               var19.limit += 3;
               var0.setSize$okio(var0.size() + 3L);
            }

            var2++;
         }
      }

      return var0;
   }
}

internal inline fun Buffer.commonWriteUtf8CodePoint(codePoint: Int): Buffer {
   if (var1 < 128) {
      var0.writeByte(var1);
   } else if (var1 < 2048) {
      val var2: Segment = var0.writableSegment$okio(2);
      var2.data[var2.limit] = (byte)(var1 shr 6 or 192);
      var2.data[var2.limit + 1] = (byte)(var1 and 63 or 128);
      var2.limit += 2;
      var0.setSize$okio(var0.size() + 2L);
   } else if (55296 <= var1 && var1 < 57344) {
      var0.writeByte(63);
   } else if (var1 < 65536) {
      val var4: Segment = var0.writableSegment$okio(3);
      var4.data[var4.limit] = (byte)(var1 shr 12 or 224);
      var4.data[var4.limit + 1] = (byte)(var1 shr 6 and 63 or 128);
      var4.data[var4.limit + 2] = (byte)(var1 and 63 or 128);
      var4.limit += 3;
      var0.setSize$okio(var0.size() + 3L);
   } else {
      if (var1 > 1114111) {
         val var3: StringBuilder = new StringBuilder("Unexpected code point: 0x");
         var3.append(okio._SegmentedByteString.toHexString(var1));
         throw new IllegalArgumentException(var3.toString());
      }

      val var5: Segment = var0.writableSegment$okio(4);
      var5.data[var5.limit] = (byte)(var1 shr 18 or 240);
      var5.data[var5.limit + 1] = (byte)(var1 shr 12 and 63 or 128);
      var5.data[var5.limit + 2] = (byte)(var1 shr 6 and 63 or 128);
      var5.data[var5.limit + 3] = (byte)(var1 and 63 or 128);
      var5.limit += 4;
      var0.setSize$okio(var0.size() + 4L);
   }

   return var0;
}

internal fun rangeEquals(segment: Segment, segmentPos: Int, bytes: ByteArray, bytesOffset: Int, bytesLimit: Int): Boolean {
   var var5: Int = var0.limit;
   var var9: ByteArray = var0.data;

   while (var3 < var4) {
      var var6: Int = var5;
      var var8: Segment = var0;
      var var7: Int = var1;
      if (var1 == var5) {
         var8 = var0.next;
         var9 = var8.data;
         var7 = var8.pos;
         var6 = var8.limit;
      }

      if (var9[var7] != var2[var3]) {
         return false;
      }

      var1 = var7 + 1;
      var3++;
      var5 = var6;
      var0 = var8;
   }

   return true;
}

internal fun Buffer.readUtf8Line(newline: Long): String {
   if (var1 > 0L) {
      val var3: Long = var1 - 1L;
      if (var0.getByte(var1 - 1L) == 13) {
         val var7: java.lang.String = var0.readUtf8(var3);
         var0.skip(2L);
         return var7;
      }
   }

   val var5: java.lang.String = var0.readUtf8(var1);
   var0.skip(1L);
   return var5;
}

internal inline fun <T> Buffer.seek(fromIndex: Long, lambda: (Segment?, Long) -> T): T {
   var var8: Segment = var0.head;
   if (var0.head == null) {
      return (T)var3.invoke(null, -1L);
   } else if (var0.size() - var1 < var1) {
      var var9: Long;
      for (var9 = var0.size(); var9 > var1; var9 -= var8.limit - var8.pos) {
         var8 = var8.prev;
      }

      return (T)var3.invoke(var8, var9);
   } else {
      var var4: Long = 0L;

      while (true) {
         val var6: Long = var8.limit - var8.pos + var4;
         if (var8.limit - var8.pos + var4 > var1) {
            return (T)var3.invoke(var8, var4);
         }

         var8 = var8.next;
         var4 = var6;
      }
   }
}

internal fun Buffer.selectPrefix(options: Options, selectTruncated: Boolean = false): Int {
   val var12: Segment = var0.head;
   var var3: Int = -2;
   if (var0.head == null) {
      if (!var2) {
         var3 = -1;
      }

      return var3;
   } else {
      val var15: ByteArray = var0.head.data;
      var3 = var0.head.pos;
      var var4: Int = var0.head.limit;
      val var14: IntArray = var1.getTrie$okio();
      var var11: Segment = var12;
      var var5: Int = 0;
      var var6: Int = -1;
      var var17: ByteArray = var15;

      while (true) {
         val var10: Int = var14[var5];
         var var7: Int = var5 + 2;
         var5 = var14[var5 + 1];
         if (var14[var5 + 1] != -1) {
            var6 = var5;
         }

         label75:
         if (var11 != null) {
            var var16: Segment;
            if (var10 < 0) {
               var5 = var7;

               while (true) {
                  val var9: Int = var3 + 1;
                  var var19: Int = var17[var3];
                  val var8: Int = var5 + 1;
                  if ((var19 and 255) != var14[var5]) {
                     return var6;
                  }

                  val var26: Boolean;
                  if (var8 == var7 + var10 * -1) {
                     var26 = true;
                  } else {
                     var26 = false;
                  }

                  if (var9 == var4) {
                     var16 = var11.next;
                     var4 = var16.pos;
                     var17 = var16.data;
                     var19 = var16.limit;
                     if (var16 === var12) {
                        if (!var26) {
                           break label75;
                        }

                        var16 = null;
                     }
                  } else {
                     var16 = var11;
                     var19 = var4;
                     var4 = var9;
                  }

                  if (var26) {
                     var7 = var14[var8];
                     var5 = var19;
                     var3 = var4;
                     break;
                  }

                  var3 = var4;
                  var4 = var19;
                  var5 = var8;
                  var11 = var16;
               }
            } else {
               val var32: Int = var3 + 1;
               val var29: Byte = var17[var3];
               var3 = var7;

               while (true) {
                  if (var3 == var7 + var10) {
                     return var6;
                  }

                  if ((var29 and 255) == var14[var3]) {
                     val var34: Int = var14[var3 + var10];
                     var7 = var14[var3 + var10];
                     var5 = var4;
                     var16 = var11;
                     var3 = var32;
                     if (var32 == var4) {
                        val var13: Segment = var11.next;
                        var17 = var13.data;
                        var7 = var34;
                        var5 = var13.limit;
                        var16 = var13;
                        var3 = var13.pos;
                        if (var13 === var12) {
                           var16 = null;
                           var3 = var13.pos;
                           var5 = var13.limit;
                           var7 = var34;
                           var17 = var13.data;
                        }
                     }
                     break;
                  }

                  var3++;
               }
            }

            if (var7 >= 0) {
               return var7;
            }

            var7 = -var7;
            var4 = var5;
            var5 = var7;
            var11 = var16;
            continue;
         }

         if (var2) {
            return -2;
         }

         return var6;
      }
   }
}

@JvmSynthetic
fun `selectPrefix$default`(var0: Buffer, var1: Options, var2: Boolean, var3: Int, var4: Any): Int {
   if ((var3 and 2) != 0) {
      var2 = false;
   }

   return selectPrefix(var0, var1, var2);
}
