package okio.internal

import java.io.EOFException
import okio.Buffer
import okio.BufferedSource
import okio.ByteString
import okio.Okio
import okio.Options
import okio.PeekSource
import okio.RealBufferedSource
import okio.Sink
import okio.Timeout

internal inline fun RealBufferedSource.commonClose() {
   if (!var0.closed) {
      var0.closed = true;
      var0.source.close();
      var0.bufferField.clear();
   }
}

internal inline fun RealBufferedSource.commonExhausted(): Boolean {
   if (var0.closed) {
      throw new IllegalStateException("closed".toString());
   } else {
      val var1: Boolean;
      if (var0.bufferField.exhausted() && var0.source.read(var0.bufferField, 8192L) == -1L) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }
}

internal inline fun RealBufferedSource.commonIndexOf(b: Byte, fromIndex: Long, toIndex: Long): Long {
   if (var0.closed) {
      throw new IllegalStateException("closed".toString());
   } else if (0L <= var2 && var2 <= var4) {
      while (var2 < var4) {
         var var6: Long = var0.bufferField.indexOf(var1, var2, var4);
         if (var6 != -1L) {
            return var6;
         }

         var6 = var0.bufferField.size();
         if (var6 >= var4 || var0.source.read(var0.bufferField, 8192L) == -1L) {
            break;
         }

         var2 = Math.max(var2, var6);
      }

      return -1L;
   } else {
      val var8: StringBuilder = new StringBuilder("fromIndex=");
      var8.append(var2);
      var8.append(" toIndex=");
      var8.append(var4);
      throw new IllegalArgumentException(var8.toString().toString());
   }
}

internal inline fun RealBufferedSource.commonIndexOf(bytes: ByteString, fromIndex: Long): Long {
   if (var0.closed) {
      throw new IllegalStateException("closed".toString());
   } else {
      while (true) {
         var var4: Long = var0.bufferField.indexOf(var1, var2);
         if (var4 != -1L) {
            return var4;
         }

         var4 = var0.bufferField.size();
         if (var0.source.read(var0.bufferField, 8192L) == -1L) {
            return -1L;
         }

         var2 = Math.max(var2, var4 - (long)var1.size() + 1L);
      }
   }
}

internal inline fun RealBufferedSource.commonIndexOfElement(targetBytes: ByteString, fromIndex: Long): Long {
   if (var0.closed) {
      throw new IllegalStateException("closed".toString());
   } else {
      while (true) {
         var var4: Long = var0.bufferField.indexOfElement(var1, var2);
         if (var4 != -1L) {
            return var4;
         }

         var4 = var0.bufferField.size();
         if (var0.source.read(var0.bufferField, 8192L) == -1L) {
            return -1L;
         }

         var2 = Math.max(var2, var4);
      }
   }
}

internal inline fun RealBufferedSource.commonPeek(): BufferedSource {
   return Okio.buffer(new PeekSource(var0));
}

internal inline fun RealBufferedSource.commonRangeEquals(offset: Long, bytes: ByteString, bytesOffset: Int, byteCount: Int): Boolean {
   if (!var0.closed) {
      if (var1 >= 0L && var4 >= 0 && var5 >= 0 && var3.size() - var4 >= var5) {
         for (int var6 = 0; var6 < var5; var6++) {
            val var7: Long = var6 + var1;
            if (!var0.request(1L + (long)var6 + var1)) {
               return false;
            }

            if (var0.bufferField.getByte(var7) != var3.getByte(var4 + var6)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   } else {
      throw new IllegalStateException("closed".toString());
   }
}

internal inline fun RealBufferedSource.commonRead(sink: ByteArray, offset: Int, byteCount: Int): Int {
   val var8: Long = var1.length;
   val var4: Long = var2;
   val var6: Long = var3;
   okio._SegmentedByteString.checkOffsetAndCount(var8, var4, (long)var3);
   return if (var0.bufferField.size() == 0L && var0.source.read(var0.bufferField, 8192L) == -1L)
      -1
      else
      var0.bufferField.read(var1, var2, (int)Math.min(var6, var0.bufferField.size()));
}

internal inline fun RealBufferedSource.commonRead(sink: Buffer, byteCount: Long): Long {
   if (var2 >= 0L) {
      label15:
      if (!var0.closed) {
         return if (var0.bufferField.size() == 0L && var0.source.read(var0.bufferField, 8192L) == -1L)
            -1L
            else
            var0.bufferField.read(var1, Math.min(var2, var0.bufferField.size()));
      } else {
         throw new IllegalStateException("closed".toString());
      }
   } else {
      val var4: StringBuilder = new StringBuilder("byteCount < 0: ");
      var4.append(var2);
      throw new IllegalArgumentException(var4.toString().toString());
   }
}

internal inline fun RealBufferedSource.commonReadAll(sink: Sink): Long {
   var var2: Long = 0L;

   while (var0.source.read(var0.bufferField, 8192L) != -1L) {
      val var4: Long = var0.bufferField.completeSegmentByteCount();
      if (var4 > 0L) {
         var2 += var4;
         var1.write(var0.bufferField, var4);
      }
   }

   var var6: Long = var2;
   if (var0.bufferField.size() > 0L) {
      var6 = var2 + var0.bufferField.size();
      var1.write(var0.bufferField, var0.bufferField.size());
   }

   return var6;
}

internal inline fun RealBufferedSource.commonReadByte(): Byte {
   var0.require(1L);
   return var0.bufferField.readByte();
}

internal inline fun RealBufferedSource.commonReadByteArray(): ByteArray {
   var0.bufferField.writeAll(var0.source);
   return var0.bufferField.readByteArray();
}

internal inline fun RealBufferedSource.commonReadByteArray(byteCount: Long): ByteArray {
   var0.require(var1);
   return var0.bufferField.readByteArray(var1);
}

internal inline fun RealBufferedSource.commonReadByteString(): ByteString {
   var0.bufferField.writeAll(var0.source);
   return var0.bufferField.readByteString();
}

internal inline fun RealBufferedSource.commonReadByteString(byteCount: Long): ByteString {
   var0.require(var1);
   return var0.bufferField.readByteString(var1);
}

internal inline fun RealBufferedSource.commonReadDecimalLong(): Long {
   var0.require(1L);
   var var3: Long = 0L;

   while (true) {
      val var5: Long = var3 + 1L;
      if (!var0.request(var3 + 1L)) {
         break;
      }

      val var1: Byte = var0.bufferField.getByte(var3);
      if (var1 < 48 || var1 > 57) {
         val var9: Long;
         val var2: Boolean = (boolean)(if ((var9 = var3 - 0L) == 0L) 0 else (if (var9 < 0L) -1 else 1));
         if (var3 != 0L || var1 != 45) {
            if (!var2) {
               val var8: StringBuilder = new StringBuilder("Expected a digit or '-' but was 0x");
               val var7: java.lang.String = Integer.toString(var1, CharsKt.checkRadix(CharsKt.checkRadix(16)));
               var8.append(var7);
               throw new NumberFormatException(var8.toString());
            }
            break;
         }
      }

      var3 = var5;
   }

   return var0.bufferField.readDecimalLong();
}

internal inline fun RealBufferedSource.commonReadFully(sink: Buffer, byteCount: Long) {
   try {
      var0.require(var2);
   } catch (var5: EOFException) {
      var1.writeAll(var0.bufferField);
      throw var5;
   }

   var0.bufferField.readFully(var1, var2);
}

internal inline fun RealBufferedSource.commonReadFully(sink: ByteArray) {
   try {
      var0.require((long)var1.length);
   } catch (var5: EOFException) {
      var var2: Int = 0;

      while (var0.bufferField.size() > 0L) {
         val var3: Int = var0.bufferField.read(var1, var2, (int)var0.bufferField.size());
         if (var3 == -1) {
            throw new AssertionError();
         }

         var2 += var3;
      }

      throw var5;
   }

   var0.bufferField.readFully(var1);
}

internal inline fun RealBufferedSource.commonReadHexadecimalUnsignedLong(): Long {
   var0.require(1L);
   var var1: Int = 0;

   while (true) {
      val var2: Int = var1 + 1;
      if (!var0.request((long)(var1 + 1))) {
         break;
      }

      val var3: Byte = var0.bufferField.getByte((long)var1);
      if ((var3 < 48 || var3 > 57) && (var3 < 97 || var3 > 102) && (var3 < 65 || var3 > 70)) {
         if (var1 == 0) {
            val var4: StringBuilder = new StringBuilder("Expected leading [0-9a-fA-F] character but was 0x");
            val var5: java.lang.String = Integer.toString(var3, CharsKt.checkRadix(CharsKt.checkRadix(16)));
            var4.append(var5);
            throw new NumberFormatException(var4.toString());
         }
         break;
      }

      var1 = var2;
   }

   return var0.bufferField.readHexadecimalUnsignedLong();
}

internal inline fun RealBufferedSource.commonReadInt(): Int {
   var0.require(4L);
   return var0.bufferField.readInt();
}

internal inline fun RealBufferedSource.commonReadIntLe(): Int {
   var0.require(4L);
   return var0.bufferField.readIntLe();
}

internal inline fun RealBufferedSource.commonReadLong(): Long {
   var0.require(8L);
   return var0.bufferField.readLong();
}

internal inline fun RealBufferedSource.commonReadLongLe(): Long {
   var0.require(8L);
   return var0.bufferField.readLongLe();
}

internal inline fun RealBufferedSource.commonReadShort(): Short {
   var0.require(2L);
   return var0.bufferField.readShort();
}

internal inline fun RealBufferedSource.commonReadShortLe(): Short {
   var0.require(2L);
   return var0.bufferField.readShortLe();
}

internal inline fun RealBufferedSource.commonReadUtf8(): String {
   var0.bufferField.writeAll(var0.source);
   return var0.bufferField.readUtf8();
}

internal inline fun RealBufferedSource.commonReadUtf8(byteCount: Long): String {
   var0.require(var1);
   return var0.bufferField.readUtf8(var1);
}

internal inline fun RealBufferedSource.commonReadUtf8CodePoint(): Int {
   var0.require(1L);
   val var1: Byte = var0.bufferField.getByte(0L);
   if ((var1 and 224) == 192) {
      var0.require(2L);
   } else if ((var1 and 240) == 224) {
      var0.require(3L);
   } else if ((var1 and 248) == 240) {
      var0.require(4L);
   }

   return var0.bufferField.readUtf8CodePoint();
}

internal inline fun RealBufferedSource.commonReadUtf8Line(): String? {
   val var1: Long = var0.indexOf((byte)10);
   val var3: java.lang.String;
   if (var1 == -1L) {
      if (var0.bufferField.size() != 0L) {
         var3 = var0.readUtf8(var0.bufferField.size());
      } else {
         var3 = null;
      }
   } else {
      var3 = _Buffer.readUtf8Line(var0.bufferField, var1);
   }

   return var3;
}

internal inline fun RealBufferedSource.commonReadUtf8LineStrict(limit: Long): String {
   if (var1 >= 0L) {
      val var3: Long;
      if (var1 == java.lang.Long.MAX_VALUE) {
         var3 = java.lang.Long.MAX_VALUE;
      } else {
         var3 = var1 + 1L;
      }

      val var5: Long = var0.indexOf((byte)10, 0L, var3);
      if (var5 != -1L) {
         return _Buffer.readUtf8Line(var0.bufferField, var5);
      } else if (var3 < java.lang.Long.MAX_VALUE
         && var0.request(var3)
         && var0.bufferField.getByte(var3 - 1L) == 13
         && var0.request(1L + var3)
         && var0.bufferField.getByte(var3) == 10) {
         return _Buffer.readUtf8Line(var0.bufferField, var3);
      } else {
         val var7: Buffer = new Buffer();
         var0.bufferField.copyTo(var7, 0L, Math.min((long)32, var0.bufferField.size()));
         val var11: StringBuilder = new StringBuilder("\\n not found: limit=");
         var11.append(Math.min(var0.bufferField.size(), var1));
         var11.append(" content=");
         var11.append(var7.readByteString().hex());
         var11.append('…');
         throw new EOFException(var11.toString());
      }
   } else {
      val var9: StringBuilder = new StringBuilder("limit < 0: ");
      var9.append(var1);
      throw new IllegalArgumentException(var9.toString().toString());
   }
}

internal inline fun RealBufferedSource.commonRequest(byteCount: Long): Boolean {
   if (var1 >= 0L) {
      if (!var0.closed) {
         while (var0.bufferField.size() < var1) {
            if (var0.source.read(var0.bufferField, 8192L) == -1L) {
               return false;
            }
         }

         return true;
      } else {
         throw new IllegalStateException("closed".toString());
      }
   } else {
      val var3: StringBuilder = new StringBuilder("byteCount < 0: ");
      var3.append(var1);
      throw new IllegalArgumentException(var3.toString().toString());
   }
}

internal inline fun RealBufferedSource.commonRequire(byteCount: Long) {
   if (!var0.request(var1)) {
      throw new EOFException();
   }
}

internal inline fun RealBufferedSource.commonSelect(options: Options): Int {
   if (var0.closed) {
      throw new IllegalStateException("closed".toString());
   } else {
      do {
         val var3: Int = _Buffer.selectPrefix(var0.bufferField, var1, true);
         if (var3 != -2) {
            if (var3 != -1) {
               var0.bufferField.skip((long)var1.getByteStrings$okio()[var3].size());
               return var3;
            } else {
               return -1;
            }
         }
      } while (var0.source.read(var0.bufferField, 8192L) != -1L);

      return -1;
   }
}

internal inline fun RealBufferedSource.commonSkip(byteCount: Long) {
   if (!var0.closed) {
      while (var1 > 0L) {
         if (var0.bufferField.size() == 0L && var0.source.read(var0.bufferField, 8192L) == -1L) {
            throw new EOFException();
         }

         val var3: Long = Math.min(var1, var0.bufferField.size());
         var0.bufferField.skip(var3);
         var1 -= var3;
      }
   } else {
      throw new IllegalStateException("closed".toString());
   }
}

internal inline fun RealBufferedSource.commonTimeout(): Timeout {
   return var0.source.timeout();
}

internal inline fun RealBufferedSource.commonToString(): String {
   val var1: StringBuilder = new StringBuilder("buffer(");
   var1.append(var0.source);
   var1.append(')');
   return var1.toString();
}
