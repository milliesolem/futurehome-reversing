package okio

import java.io.EOFException
import java.io.IOException
import java.io.InputStream
import java.nio.ByteBuffer
import java.nio.charset.Charset
import okio.internal._Buffer

internal class RealBufferedSource(source: Source) : BufferedSource {
   public open val buffer: Buffer
      public open inline get() {
         return this.bufferField;
      }


   public final val bufferField: Buffer

   public final var closed: Boolean
      private set

   public final val source: Source

   init {
      this.source = var1;
      this.bufferField = new Buffer();
   }

   public override fun buffer(): Buffer {
      return this.bufferField;
   }

   public override fun close() {
      if (!this.closed) {
         this.closed = true;
         this.source.close();
         this.bufferField.clear();
      }
   }

   public override fun exhausted(): Boolean {
      if (this.closed) {
         throw new IllegalStateException("closed".toString());
      } else {
         val var1: Boolean;
         if (this.bufferField.exhausted() && this.source.read(this.bufferField, 8192L) == -1L) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }
   }

   public override fun indexOf(b: Byte): Long {
      return this.indexOf(var1, 0L, java.lang.Long.MAX_VALUE);
   }

   public override fun indexOf(b: Byte, fromIndex: Long): Long {
      return this.indexOf(var1, var2, java.lang.Long.MAX_VALUE);
   }

   public override fun indexOf(b: Byte, fromIndex: Long, toIndex: Long): Long {
      if (this.closed) {
         throw new IllegalStateException("closed".toString());
      } else if (0L <= var2 && var2 <= var4) {
         var var6: Long;
         while (true) {
            var6 = -1L;
            if (var2 >= var4) {
               break;
            }

            var6 = this.bufferField.indexOf(var1, var2, var4);
            if (var6 != -1L) {
               break;
            }

            val var10: Long = this.bufferField.size();
            var6 = -1L;
            if (var10 >= var4) {
               break;
            }

            if (this.source.read(this.bufferField, 8192L) == -1L) {
               var6 = -1L;
               break;
            }

            var2 = Math.max(var2, var10);
         }

         return var6;
      } else {
         val var12: StringBuilder = new StringBuilder("fromIndex=");
         var12.append(var2);
         var12.append(" toIndex=");
         var12.append(var4);
         throw new IllegalArgumentException(var12.toString().toString());
      }
   }

   public override fun indexOf(bytes: ByteString): Long {
      return this.indexOf(var1, 0L);
   }

   public override fun indexOf(bytes: ByteString, fromIndex: Long): Long {
      if (this.closed) {
         throw new IllegalStateException("closed".toString());
      } else {
         while (true) {
            var var4: Long = this.bufferField.indexOf(var1, var2);
            if (var4 != -1L) {
               var2 = var4;
               break;
            }

            var4 = this.bufferField.size();
            if (this.source.read(this.bufferField, 8192L) == -1L) {
               var2 = -1L;
               break;
            }

            var2 = Math.max(var2, var4 - (long)var1.size() + 1L);
         }

         return var2;
      }
   }

   public override fun indexOfElement(targetBytes: ByteString): Long {
      return this.indexOfElement(var1, 0L);
   }

   public override fun indexOfElement(targetBytes: ByteString, fromIndex: Long): Long {
      if (this.closed) {
         throw new IllegalStateException("closed".toString());
      } else {
         while (true) {
            var var4: Long = this.bufferField.indexOfElement(var1, var2);
            if (var4 != -1L) {
               var2 = var4;
               break;
            }

            var4 = this.bufferField.size();
            if (this.source.read(this.bufferField, 8192L) == -1L) {
               var2 = -1L;
               break;
            }

            var2 = Math.max(var2, var4);
         }

         return var2;
      }
   }

   public override fun inputStream(): InputStream {
      return new InputStream(this) {
         final RealBufferedSource this$0;

         {
            this.this$0 = var1;
         }

         @Override
         public int available() {
            if (!this.this$0.closed) {
               return (int)Math.min(this.this$0.bufferField.size(), (long)Integer.MAX_VALUE);
            } else {
               throw new IOException("closed");
            }
         }

         @Override
         public void close() {
            this.this$0.close();
         }

         @Override
         public int read() {
            if (!this.this$0.closed) {
               return if (this.this$0.bufferField.size() == 0L && this.this$0.source.read(this.this$0.bufferField, 8192L) == -1L)
                  -1
                  else
                  this.this$0.bufferField.readByte() and 255;
            } else {
               throw new IOException("closed");
            }
         }

         @Override
         public int read(byte[] var1, int var2, int var3) {
            if (!this.this$0.closed) {
               _SegmentedByteString.checkOffsetAndCount((long)var1.length, (long)var2, (long)var3);
               return if (this.this$0.bufferField.size() == 0L && this.this$0.source.read(this.this$0.bufferField, 8192L) == -1L)
                  -1
                  else
                  this.this$0.bufferField.read(var1, var2, var3);
            } else {
               throw new IOException("closed");
            }
         }

         @Override
         public java.lang.String toString() {
            val var1: StringBuilder = new StringBuilder();
            var1.append(this.this$0);
            var1.append(".inputStream()");
            return var1.toString();
         }
      };
   }

   public override fun isOpen(): Boolean {
      return this.closed xor true;
   }

   public override fun peek(): BufferedSource {
      return Okio.buffer(new PeekSource(this));
   }

   public override fun rangeEquals(offset: Long, bytes: ByteString): Boolean {
      return this.rangeEquals(var1, var3, 0, var3.size());
   }

   public override fun rangeEquals(offset: Long, bytes: ByteString, bytesOffset: Int, byteCount: Int): Boolean {
      if (this.closed) {
         throw new IllegalStateException("closed".toString());
      } else {
         var var7: Boolean = false;
         if (var1 >= 0L) {
            var7 = false;
            if (var4 >= 0) {
               var7 = false;
               if (var5 >= 0) {
                  if (var3.size() - var4 < var5) {
                     var7 = false;
                  } else {
                     var var6: Int = 0;

                     while (true) {
                        if (var6 >= var5) {
                           var7 = true;
                           break;
                        }

                        val var9: Long = var6 + var1;
                        if (!this.request(1L + (long)var6 + var1)) {
                           var7 = false;
                           break;
                        }

                        if (this.bufferField.getByte(var9) != var3.getByte(var4 + var6)) {
                           var7 = false;
                           break;
                        }

                        var6++;
                     }
                  }
               }
            }
         }

         return var7;
      }
   }

   public override fun read(sink: ByteBuffer): Int {
      return if (this.bufferField.size() == 0L && this.source.read(this.bufferField, 8192L) == -1L) -1 else this.bufferField.read(var1);
   }

   public override fun read(sink: ByteArray): Int {
      return this.read(var1, 0, var1.length);
   }

   public override fun read(sink: ByteArray, offset: Int, byteCount: Int): Int {
      val var4: Long = var1.length;
      val var8: Long = var2;
      val var6: Long = var3;
      _SegmentedByteString.checkOffsetAndCount(var4, var8, (long)var3);
      if (this.bufferField.size() == 0L && this.source.read(this.bufferField, 8192L) == -1L) {
         var2 = -1;
      } else {
         var2 = this.bufferField.read(var1, var2, (int)Math.min(var6, this.bufferField.size()));
      }

      return var2;
   }

   public override fun read(sink: Buffer, byteCount: Long): Long {
      if (var2 >= 0L) {
         label13:
         if (this.closed) {
            throw new IllegalStateException("closed".toString());
         } else {
            return if (this.bufferField.size() == 0L && this.source.read(this.bufferField, 8192L) == -1L)
               -1L
               else
               this.bufferField.read(var1, Math.min(var2, this.bufferField.size()));
         }
      } else {
         val var8: StringBuilder = new StringBuilder("byteCount < 0: ");
         var8.append(var2);
         throw new IllegalArgumentException(var8.toString().toString());
      }
   }

   public override fun readAll(sink: Sink): Long {
      var var2: Long = 0L;

      while (this.source.read(this.bufferField, 8192L) != -1L) {
         val var4: Long = this.bufferField.completeSegmentByteCount();
         if (var4 > 0L) {
            var2 += var4;
            var1.write(this.bufferField, var4);
         }
      }

      var var7: Long = var2;
      if (this.bufferField.size() > 0L) {
         var7 = var2 + this.bufferField.size();
         var1.write(this.bufferField, this.bufferField.size());
      }

      return var7;
   }

   public override fun readByte(): Byte {
      this.require(1L);
      return this.bufferField.readByte();
   }

   public override fun readByteArray(): ByteArray {
      this.bufferField.writeAll(this.source);
      return this.bufferField.readByteArray();
   }

   public override fun readByteArray(byteCount: Long): ByteArray {
      this.require(var1);
      return this.bufferField.readByteArray(var1);
   }

   public override fun readByteString(): ByteString {
      this.bufferField.writeAll(this.source);
      return this.bufferField.readByteString();
   }

   public override fun readByteString(byteCount: Long): ByteString {
      this.require(var1);
      return this.bufferField.readByteString(var1);
   }

   public override fun readDecimalLong(): Long {
      this.require(1L);
      var var3: Long = 0L;

      while (true) {
         val var5: Long = var3 + 1L;
         if (!this.request(var3 + 1L)) {
            break;
         }

         val var1: Byte = this.bufferField.getByte(var3);
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

      return this.bufferField.readDecimalLong();
   }

   public override fun readFully(sink: Buffer, byteCount: Long) {
      try {
         this.require(var2);
      } catch (var5: EOFException) {
         var1.writeAll(this.bufferField);
         throw var5;
      }

      this.bufferField.readFully(var1, var2);
   }

   public override fun readFully(sink: ByteArray) {
      try {
         this.require((long)var1.length);
      } catch (var6: EOFException) {
         var var2: Int = 0;

         while (this.bufferField.size() > 0L) {
            val var3: Int = this.bufferField.read(var1, var2, (int)this.bufferField.size());
            if (var3 == -1) {
               throw new AssertionError();
            }

            var2 += var3;
         }

         throw var6;
      }

      this.bufferField.readFully(var1);
   }

   public override fun readHexadecimalUnsignedLong(): Long {
      this.require(1L);
      var var1: Int = 0;

      while (true) {
         val var2: Int = var1 + 1;
         if (!this.request((long)(var1 + 1))) {
            break;
         }

         val var3: Byte = this.bufferField.getByte((long)var1);
         if ((var3 < 48 || var3 > 57) && (var3 < 97 || var3 > 102) && (var3 < 65 || var3 > 70)) {
            if (var1 == 0) {
               val var5: StringBuilder = new StringBuilder("Expected leading [0-9a-fA-F] character but was 0x");
               val var4: java.lang.String = Integer.toString(var3, CharsKt.checkRadix(CharsKt.checkRadix(16)));
               var5.append(var4);
               throw new NumberFormatException(var5.toString());
            }
            break;
         }

         var1 = var2;
      }

      return this.bufferField.readHexadecimalUnsignedLong();
   }

   public override fun readInt(): Int {
      this.require(4L);
      return this.bufferField.readInt();
   }

   public override fun readIntLe(): Int {
      this.require(4L);
      return this.bufferField.readIntLe();
   }

   public override fun readLong(): Long {
      this.require(8L);
      return this.bufferField.readLong();
   }

   public override fun readLongLe(): Long {
      this.require(8L);
      return this.bufferField.readLongLe();
   }

   public override fun readShort(): Short {
      this.require(2L);
      return this.bufferField.readShort();
   }

   public override fun readShortLe(): Short {
      this.require(2L);
      return this.bufferField.readShortLe();
   }

   public override fun readString(byteCount: Long, charset: Charset): String {
      this.require(var1);
      return this.bufferField.readString(var1, var3);
   }

   public override fun readString(charset: Charset): String {
      this.bufferField.writeAll(this.source);
      return this.bufferField.readString(var1);
   }

   public override fun readUtf8(): String {
      this.bufferField.writeAll(this.source);
      return this.bufferField.readUtf8();
   }

   public override fun readUtf8(byteCount: Long): String {
      this.require(var1);
      return this.bufferField.readUtf8(var1);
   }

   public override fun readUtf8CodePoint(): Int {
      this.require(1L);
      val var1: Byte = this.bufferField.getByte(0L);
      if ((var1 and 224) == 192) {
         this.require(2L);
      } else if ((var1 and 240) == 224) {
         this.require(3L);
      } else if ((var1 and 248) == 240) {
         this.require(4L);
      }

      return this.bufferField.readUtf8CodePoint();
   }

   public override fun readUtf8Line(): String? {
      val var1: Long = this.indexOf((byte)10);
      val var3: java.lang.String;
      if (var1 == -1L) {
         if (this.bufferField.size() != 0L) {
            var3 = this.readUtf8(this.bufferField.size());
         } else {
            var3 = null;
         }
      } else {
         var3 = _Buffer.readUtf8Line(this.bufferField, var1);
      }

      return var3;
   }

   public override fun readUtf8LineStrict(): String {
      return this.readUtf8LineStrict(java.lang.Long.MAX_VALUE);
   }

   public override fun readUtf8LineStrict(limit: Long): String {
      if (var1 >= 0L) {
         val var3: Long;
         if (var1 == java.lang.Long.MAX_VALUE) {
            var3 = java.lang.Long.MAX_VALUE;
         } else {
            var3 = var1 + 1L;
         }

         val var5: Long = this.indexOf((byte)10, 0L, var3);
         val var10: java.lang.String;
         if (var5 != -1L) {
            var10 = _Buffer.readUtf8Line(this.bufferField, var5);
         } else {
            if (var3 >= java.lang.Long.MAX_VALUE
               || !this.request(var3)
               || this.bufferField.getByte(var3 - 1L) != 13
               || !this.request(1L + var3)
               || this.bufferField.getByte(var3) != 10) {
               val var11: Buffer = new Buffer();
               this.bufferField.copyTo(var11, 0L, Math.min((long)32, this.bufferField.size()));
               val var12: StringBuilder = new StringBuilder("\\n not found: limit=");
               var12.append(Math.min(this.bufferField.size(), var1));
               var12.append(" content=");
               var12.append(var11.readByteString().hex());
               var12.append('…');
               throw new EOFException(var12.toString());
            }

            var10 = _Buffer.readUtf8Line(this.bufferField, var3);
         }

         return var10;
      } else {
         val var7: StringBuilder = new StringBuilder("limit < 0: ");
         var7.append(var1);
         throw new IllegalArgumentException(var7.toString().toString());
      }
   }

   public override fun request(byteCount: Long): Boolean {
      if (var1 < 0L) {
         val var4: StringBuilder = new StringBuilder("byteCount < 0: ");
         var4.append(var1);
         throw new IllegalArgumentException(var4.toString().toString());
      } else if (this.closed) {
         throw new IllegalStateException("closed".toString());
      } else {
         var var3: Boolean;
         while (true) {
            if (this.bufferField.size() < var1) {
               if (this.source.read(this.bufferField, 8192L) != -1L) {
                  continue;
               }

               var3 = false;
               break;
            }

            var3 = true;
            break;
         }

         return var3;
      }
   }

   public override fun require(byteCount: Long) {
      if (!this.request(var1)) {
         throw new EOFException();
      }
   }

   public override fun select(options: Options): Int {
      if (this.closed) {
         throw new IllegalStateException("closed".toString());
      } else {
         var var2: Int;
         while (true) {
            var2 = _Buffer.selectPrefix(this.bufferField, var1, true);
            if (var2 != -2) {
               if (var2 != -1) {
                  this.bufferField.skip((long)var1.getByteStrings$okio()[var2].size());
                  break;
               }
            } else if (this.source.read(this.bufferField, 8192L) != -1L) {
               continue;
            }

            var2 = -1;
            break;
         }

         return var2;
      }
   }

   public override fun skip(byteCount: Long) {
      if (!this.closed) {
         while (var1 > 0L) {
            if (this.bufferField.size() == 0L && this.source.read(this.bufferField, 8192L) == -1L) {
               throw new EOFException();
            }

            val var3: Long = Math.min(var1, this.bufferField.size());
            this.bufferField.skip(var3);
            var1 -= var3;
         }
      } else {
         throw new IllegalStateException("closed".toString());
      }
   }

   public override fun timeout(): Timeout {
      return this.source.timeout();
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder("buffer(");
      var1.append(this.source);
      var1.append(')');
      return var1.toString();
   }
}
