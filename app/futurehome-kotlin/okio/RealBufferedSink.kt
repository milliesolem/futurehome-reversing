package okio

import java.io.EOFException
import java.io.IOException
import java.io.OutputStream
import java.nio.ByteBuffer
import java.nio.charset.Charset

internal class RealBufferedSink(sink: Sink) : BufferedSink {
   public open val buffer: Buffer
      public open inline get() {
         return this.bufferField;
      }


   public final val bufferField: Buffer

   public final var closed: Boolean
      private set

   public final val sink: Sink

   init {
      this.sink = var1;
      this.bufferField = new Buffer();
   }

   public override fun buffer(): Buffer {
      return this.bufferField;
   }

   public override fun close() {
      if (!this.closed) {
         var var1: Buffer;
         label31:
         try {
            if (this.bufferField.size() > 0L) {
               var1 = this.bufferField;
               this.sink.write(this.bufferField, this.bufferField.size());
            }
         } catch (var5: java.lang.Throwable) {
            break label31;
         }

         var var10: Any;
         label25: {
            try {
               this.sink.close();
            } catch (var4: java.lang.Throwable) {
               var10 = var1;
               if (var1 == null) {
                  var10 = var4;
               }
               break label25;
            }

            var10 = var1;
         }

         this.closed = true;
         if (var10 != null) {
            throw var10;
         }
      }
   }

   public override fun emit(): BufferedSink {
      if (!this.closed) {
         val var1: Long = this.bufferField.size();
         if (var1 > 0L) {
            this.sink.write(this.bufferField, var1);
         }

         return this;
      } else {
         throw new IllegalStateException("closed".toString());
      }
   }

   public override fun emitCompleteSegments(): BufferedSink {
      if (!this.closed) {
         val var1: Long = this.bufferField.completeSegmentByteCount();
         if (var1 > 0L) {
            this.sink.write(this.bufferField, var1);
         }

         return this;
      } else {
         throw new IllegalStateException("closed".toString());
      }
   }

   public override fun flush() {
      if (!this.closed) {
         if (this.bufferField.size() > 0L) {
            this.sink.write(this.bufferField, this.bufferField.size());
         }

         this.sink.flush();
      } else {
         throw new IllegalStateException("closed".toString());
      }
   }

   public override fun isOpen(): Boolean {
      return this.closed xor true;
   }

   public override fun outputStream(): OutputStream {
      return new OutputStream(this) {
         final RealBufferedSink this$0;

         {
            this.this$0 = var1;
         }

         @Override
         public void close() {
            this.this$0.close();
         }

         @Override
         public void flush() {
            if (!this.this$0.closed) {
               this.this$0.flush();
            }
         }

         @Override
         public java.lang.String toString() {
            val var1: StringBuilder = new StringBuilder();
            var1.append(this.this$0);
            var1.append(".outputStream()");
            return var1.toString();
         }

         @Override
         public void write(int var1) {
            if (!this.this$0.closed) {
               this.this$0.bufferField.writeByte((byte)var1);
               this.this$0.emitCompleteSegments();
            } else {
               throw new IOException("closed");
            }
         }

         @Override
         public void write(byte[] var1, int var2, int var3) {
            if (!this.this$0.closed) {
               this.this$0.bufferField.write(var1, var2, var3);
               this.this$0.emitCompleteSegments();
            } else {
               throw new IOException("closed");
            }
         }
      };
   }

   public override fun timeout(): Timeout {
      return this.sink.timeout();
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder("buffer(");
      var1.append(this.sink);
      var1.append(')');
      return var1.toString();
   }

   public override fun write(source: ByteBuffer): Int {
      if (!this.closed) {
         val var2: Int = this.bufferField.write(var1);
         this.emitCompleteSegments();
         return var2;
      } else {
         throw new IllegalStateException("closed".toString());
      }
   }

   public override fun write(byteString: ByteString): BufferedSink {
      if (!this.closed) {
         this.bufferField.write(var1);
         return this.emitCompleteSegments();
      } else {
         throw new IllegalStateException("closed".toString());
      }
   }

   public override fun write(byteString: ByteString, offset: Int, byteCount: Int): BufferedSink {
      if (!this.closed) {
         this.bufferField.write(var1, var2, var3);
         return this.emitCompleteSegments();
      } else {
         throw new IllegalStateException("closed".toString());
      }
   }

   public override fun write(source: Source, byteCount: Long): BufferedSink {
      while (var2 > 0L) {
         val var4: Long = var1.read(this.bufferField, var2);
         if (var4 == -1L) {
            throw new EOFException();
         }

         var2 -= var4;
         this.emitCompleteSegments();
      }

      return this;
   }

   public override fun write(source: ByteArray): BufferedSink {
      if (!this.closed) {
         this.bufferField.write(var1);
         return this.emitCompleteSegments();
      } else {
         throw new IllegalStateException("closed".toString());
      }
   }

   public override fun write(source: ByteArray, offset: Int, byteCount: Int): BufferedSink {
      if (!this.closed) {
         this.bufferField.write(var1, var2, var3);
         return this.emitCompleteSegments();
      } else {
         throw new IllegalStateException("closed".toString());
      }
   }

   public override fun write(source: Buffer, byteCount: Long) {
      if (!this.closed) {
         this.bufferField.write(var1, var2);
         this.emitCompleteSegments();
      } else {
         throw new IllegalStateException("closed".toString());
      }
   }

   public override fun writeAll(source: Source): Long {
      var var2: Long = 0L;

      while (true) {
         val var4: Long = var1.read(this.bufferField, 8192L);
         if (var4 == -1L) {
            return var2;
         }

         var2 += var4;
         this.emitCompleteSegments();
      }
   }

   public override fun writeByte(b: Int): BufferedSink {
      if (!this.closed) {
         this.bufferField.writeByte(var1);
         return this.emitCompleteSegments();
      } else {
         throw new IllegalStateException("closed".toString());
      }
   }

   public override fun writeDecimalLong(v: Long): BufferedSink {
      if (!this.closed) {
         this.bufferField.writeDecimalLong(var1);
         return this.emitCompleteSegments();
      } else {
         throw new IllegalStateException("closed".toString());
      }
   }

   public override fun writeHexadecimalUnsignedLong(v: Long): BufferedSink {
      if (!this.closed) {
         this.bufferField.writeHexadecimalUnsignedLong(var1);
         return this.emitCompleteSegments();
      } else {
         throw new IllegalStateException("closed".toString());
      }
   }

   public override fun writeInt(i: Int): BufferedSink {
      if (!this.closed) {
         this.bufferField.writeInt(var1);
         return this.emitCompleteSegments();
      } else {
         throw new IllegalStateException("closed".toString());
      }
   }

   public override fun writeIntLe(i: Int): BufferedSink {
      if (!this.closed) {
         this.bufferField.writeIntLe(var1);
         return this.emitCompleteSegments();
      } else {
         throw new IllegalStateException("closed".toString());
      }
   }

   public override fun writeLong(v: Long): BufferedSink {
      if (!this.closed) {
         this.bufferField.writeLong(var1);
         return this.emitCompleteSegments();
      } else {
         throw new IllegalStateException("closed".toString());
      }
   }

   public override fun writeLongLe(v: Long): BufferedSink {
      if (!this.closed) {
         this.bufferField.writeLongLe(var1);
         return this.emitCompleteSegments();
      } else {
         throw new IllegalStateException("closed".toString());
      }
   }

   public override fun writeShort(s: Int): BufferedSink {
      if (!this.closed) {
         this.bufferField.writeShort(var1);
         return this.emitCompleteSegments();
      } else {
         throw new IllegalStateException("closed".toString());
      }
   }

   public override fun writeShortLe(s: Int): BufferedSink {
      if (!this.closed) {
         this.bufferField.writeShortLe(var1);
         return this.emitCompleteSegments();
      } else {
         throw new IllegalStateException("closed".toString());
      }
   }

   public override fun writeString(string: String, beginIndex: Int, endIndex: Int, charset: Charset): BufferedSink {
      if (!this.closed) {
         this.bufferField.writeString(var1, var2, var3, var4);
         return this.emitCompleteSegments();
      } else {
         throw new IllegalStateException("closed".toString());
      }
   }

   public override fun writeString(string: String, charset: Charset): BufferedSink {
      if (!this.closed) {
         this.bufferField.writeString(var1, var2);
         return this.emitCompleteSegments();
      } else {
         throw new IllegalStateException("closed".toString());
      }
   }

   public override fun writeUtf8(string: String): BufferedSink {
      if (!this.closed) {
         this.bufferField.writeUtf8(var1);
         return this.emitCompleteSegments();
      } else {
         throw new IllegalStateException("closed".toString());
      }
   }

   public override fun writeUtf8(string: String, beginIndex: Int, endIndex: Int): BufferedSink {
      if (!this.closed) {
         this.bufferField.writeUtf8(var1, var2, var3);
         return this.emitCompleteSegments();
      } else {
         throw new IllegalStateException("closed".toString());
      }
   }

   public override fun writeUtf8CodePoint(codePoint: Int): BufferedSink {
      if (!this.closed) {
         this.bufferField.writeUtf8CodePoint(var1);
         return this.emitCompleteSegments();
      } else {
         throw new IllegalStateException("closed".toString());
      }
   }
}
