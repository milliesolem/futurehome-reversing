package okio.internal

import java.io.EOFException
import okio.Buffer
import okio.BufferedSink
import okio.ByteString
import okio.RealBufferedSink
import okio.Source
import okio.Timeout

internal inline fun RealBufferedSink.commonClose() {
   if (!var0.closed) {
      label34:
      try {
         if (var0.bufferField.size() > 0L) {
            var0.sink.write(var0.bufferField, var0.bufferField.size());
         }
      } catch (var5: java.lang.Throwable) {
         break label34;
      }

      var var2: java.lang.Throwable;
      label28: {
         try {
            var0.sink.close();
         } catch (var4: java.lang.Throwable) {
            val var1: Any;
            var2 = (java.lang.Throwable)var1;
            if (var1 == null) {
               var2 = var4;
            }
            break label28;
         }

         val var10: Any;
         var2 = (java.lang.Throwable)var10;
      }

      var0.closed = true;
      if (var2 != null) {
         throw var2;
      }
   }
}

internal inline fun RealBufferedSink.commonEmit(): BufferedSink {
   if (!var0.closed) {
      val var1: Long = var0.bufferField.size();
      if (var1 > 0L) {
         var0.sink.write(var0.bufferField, var1);
      }

      return var0;
   } else {
      throw new IllegalStateException("closed".toString());
   }
}

internal inline fun RealBufferedSink.commonEmitCompleteSegments(): BufferedSink {
   if (!var0.closed) {
      val var1: Long = var0.bufferField.completeSegmentByteCount();
      if (var1 > 0L) {
         var0.sink.write(var0.bufferField, var1);
      }

      return var0;
   } else {
      throw new IllegalStateException("closed".toString());
   }
}

internal inline fun RealBufferedSink.commonFlush() {
   if (!var0.closed) {
      if (var0.bufferField.size() > 0L) {
         var0.sink.write(var0.bufferField, var0.bufferField.size());
      }

      var0.sink.flush();
   } else {
      throw new IllegalStateException("closed".toString());
   }
}

internal inline fun RealBufferedSink.commonTimeout(): Timeout {
   return var0.sink.timeout();
}

internal inline fun RealBufferedSink.commonToString(): String {
   val var1: StringBuilder = new StringBuilder("buffer(");
   var1.append(var0.sink);
   var1.append(')');
   return var1.toString();
}

internal inline fun RealBufferedSink.commonWrite(byteString: ByteString): BufferedSink {
   if (!var0.closed) {
      var0.bufferField.write(var1);
      return var0.emitCompleteSegments();
   } else {
      throw new IllegalStateException("closed".toString());
   }
}

internal inline fun RealBufferedSink.commonWrite(byteString: ByteString, offset: Int, byteCount: Int): BufferedSink {
   if (!var0.closed) {
      var0.bufferField.write(var1, var2, var3);
      return var0.emitCompleteSegments();
   } else {
      throw new IllegalStateException("closed".toString());
   }
}

internal inline fun RealBufferedSink.commonWrite(source: Source, byteCount: Long): BufferedSink {
   while (var2 > 0L) {
      val var4: Long = var1.read(var0.bufferField, var2);
      if (var4 == -1L) {
         throw new EOFException();
      }

      var2 -= var4;
      var0.emitCompleteSegments();
   }

   return var0;
}

internal inline fun RealBufferedSink.commonWrite(source: ByteArray): BufferedSink {
   if (!var0.closed) {
      var0.bufferField.write(var1);
      return var0.emitCompleteSegments();
   } else {
      throw new IllegalStateException("closed".toString());
   }
}

internal inline fun RealBufferedSink.commonWrite(source: ByteArray, offset: Int, byteCount: Int): BufferedSink {
   if (!var0.closed) {
      var0.bufferField.write(var1, var2, var3);
      return var0.emitCompleteSegments();
   } else {
      throw new IllegalStateException("closed".toString());
   }
}

internal inline fun RealBufferedSink.commonWrite(source: Buffer, byteCount: Long) {
   if (!var0.closed) {
      var0.bufferField.write(var1, var2);
      var0.emitCompleteSegments();
   } else {
      throw new IllegalStateException("closed".toString());
   }
}

internal inline fun RealBufferedSink.commonWriteAll(source: Source): Long {
   var var2: Long = 0L;

   while (true) {
      val var4: Long = var1.read(var0.bufferField, 8192L);
      if (var4 == -1L) {
         return var2;
      }

      var2 += var4;
      var0.emitCompleteSegments();
   }
}

internal inline fun RealBufferedSink.commonWriteByte(b: Int): BufferedSink {
   if (!var0.closed) {
      var0.bufferField.writeByte(var1);
      return var0.emitCompleteSegments();
   } else {
      throw new IllegalStateException("closed".toString());
   }
}

internal inline fun RealBufferedSink.commonWriteDecimalLong(v: Long): BufferedSink {
   if (!var0.closed) {
      var0.bufferField.writeDecimalLong(var1);
      return var0.emitCompleteSegments();
   } else {
      throw new IllegalStateException("closed".toString());
   }
}

internal inline fun RealBufferedSink.commonWriteHexadecimalUnsignedLong(v: Long): BufferedSink {
   if (!var0.closed) {
      var0.bufferField.writeHexadecimalUnsignedLong(var1);
      return var0.emitCompleteSegments();
   } else {
      throw new IllegalStateException("closed".toString());
   }
}

internal inline fun RealBufferedSink.commonWriteInt(i: Int): BufferedSink {
   if (!var0.closed) {
      var0.bufferField.writeInt(var1);
      return var0.emitCompleteSegments();
   } else {
      throw new IllegalStateException("closed".toString());
   }
}

internal inline fun RealBufferedSink.commonWriteIntLe(i: Int): BufferedSink {
   if (!var0.closed) {
      var0.bufferField.writeIntLe(var1);
      return var0.emitCompleteSegments();
   } else {
      throw new IllegalStateException("closed".toString());
   }
}

internal inline fun RealBufferedSink.commonWriteLong(v: Long): BufferedSink {
   if (!var0.closed) {
      var0.bufferField.writeLong(var1);
      return var0.emitCompleteSegments();
   } else {
      throw new IllegalStateException("closed".toString());
   }
}

internal inline fun RealBufferedSink.commonWriteLongLe(v: Long): BufferedSink {
   if (!var0.closed) {
      var0.bufferField.writeLongLe(var1);
      return var0.emitCompleteSegments();
   } else {
      throw new IllegalStateException("closed".toString());
   }
}

internal inline fun RealBufferedSink.commonWriteShort(s: Int): BufferedSink {
   if (!var0.closed) {
      var0.bufferField.writeShort(var1);
      return var0.emitCompleteSegments();
   } else {
      throw new IllegalStateException("closed".toString());
   }
}

internal inline fun RealBufferedSink.commonWriteShortLe(s: Int): BufferedSink {
   if (!var0.closed) {
      var0.bufferField.writeShortLe(var1);
      return var0.emitCompleteSegments();
   } else {
      throw new IllegalStateException("closed".toString());
   }
}

internal inline fun RealBufferedSink.commonWriteUtf8(string: String): BufferedSink {
   if (!var0.closed) {
      var0.bufferField.writeUtf8(var1);
      return var0.emitCompleteSegments();
   } else {
      throw new IllegalStateException("closed".toString());
   }
}

internal inline fun RealBufferedSink.commonWriteUtf8(string: String, beginIndex: Int, endIndex: Int): BufferedSink {
   if (!var0.closed) {
      var0.bufferField.writeUtf8(var1, var2, var3);
      return var0.emitCompleteSegments();
   } else {
      throw new IllegalStateException("closed".toString());
   }
}

internal inline fun RealBufferedSink.commonWriteUtf8CodePoint(codePoint: Int): BufferedSink {
   if (!var0.closed) {
      var0.bufferField.writeUtf8CodePoint(var1);
      return var0.emitCompleteSegments();
   } else {
      throw new IllegalStateException("closed".toString());
   }
}
