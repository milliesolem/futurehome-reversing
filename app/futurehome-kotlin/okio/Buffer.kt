package okio

import java.io.Closeable
import java.io.EOFException
import java.io.InputStream
import java.io.OutputStream
import java.nio.ByteBuffer
import java.nio.channels.ByteChannel
import java.nio.charset.Charset
import java.security.InvalidKeyException
import java.security.MessageDigest
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import okio.internal._Buffer

public class Buffer : BufferedSource, BufferedSink, Cloneable, ByteChannel {
   public open val buffer: Buffer
      public open get() {
         return this;
      }


   internal final var head: Segment?
      private set

   public final var size: Long
      public final get() {
         return this.size;
      }

      public final set(<set-?>) {
         this.size = var1;
      }


   private fun digest(algorithm: String): ByteString {
      val var2: MessageDigest = MessageDigest.getInstance(var1);
      val var3: Segment = this.head;
      if (this.head != null) {
         var2.update(this.head.data, this.head.pos, this.head.limit - this.head.pos);
         var var4: Segment = var3.next;

         while (var4 != var3) {
            var2.update(var4.data, var4.pos, var4.limit - var4.pos);
            var4 = var4.next;
         }
      }

      val var5: ByteArray = var2.digest();
      return new ByteString(var5);
   }

   private fun hmac(algorithm: String, key: ByteString): ByteString {
      var var3: Mac;
      try {
         var3 = Mac.getInstance(var1);
         var3.init(new SecretKeySpec(var2.internalArray$okio(), var1));
         var11 = this.head;
      } catch (var8: InvalidKeyException) {
         throw new IllegalArgumentException(var8);
      }

      if (var11 != null) {
         try {
            var3.update(var11.data, var11.pos, var11.limit - var11.pos);
            var9 = var11.next;
         } catch (var7: InvalidKeyException) {
            throw new IllegalArgumentException(var7);
         }

         while (var9 != var11) {
            try {
               var3.update(var9.data, var9.pos, var9.limit - var9.pos);
               var9 = var9.next;
            } catch (var6: InvalidKeyException) {
               throw new IllegalArgumentException(var6);
            }
         }
      }

      try {
         val var10: ByteArray = var3.doFinal();
         return new ByteString(var10);
      } catch (var5: InvalidKeyException) {
         throw new IllegalArgumentException(var5);
      }
   }

   @Throws(java/io/IOException::class)
   private fun readFrom(input: InputStream, byteCount: Long, forever: Boolean) {
      while (var2 > 0L || var4) {
         val var10: Segment = this.writableSegment$okio(1);
         val var11: Int = var1.read(var10.data, var10.limit, (int)Math.min(var2, (long)(8192 - var10.limit)));
         if (var11 == -1) {
            if (var10.pos == var10.limit) {
               this.head = var10.pop();
               SegmentPool.recycle(var10);
            }

            if (var4) {
               return;
            }

            throw new EOFException();
         }

         var10.limit += var11;
         this.size += var11;
         var2 -= var11;
      }
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to operator function", replaceWith = @ReplaceWith(expression = "this[index]", imports = []))
   public fun getByte(index: Long): Byte {
      return this.getByte(var1);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "size", imports = []))
   public fun size(): Long {
      return this.size;
   }

   public override fun buffer(): Buffer {
      return this;
   }

   public fun clear() {
      this.skip(this.size());
   }

   public open fun clone(): Buffer {
      return this.copy();
   }

   public override fun close() {
   }

   public fun completeSegmentByteCount(): Long {
      val var3: Long = this.size();
      var var1: Long = 0L;
      if (var3 != 0L) {
         val var5: Segment = this.head;
         val var6: Segment = var5.prev;
         var1 = var3;
         if (var6.limit < 8192) {
            var1 = var3;
            if (var6.owner) {
               var1 = var3 - (var6.limit - var6.pos);
            }
         }
      }

      return var1;
   }

   public fun copy(): Buffer {
      val var2: Buffer = new Buffer();
      if (this.size() != 0L) {
         val var4: Segment = this.head;
         val var3: Segment = var4.sharedCopy();
         var2.head = var3;
         var3.prev = var3;
         var3.next = var3.prev;

         for (Segment var1 = var4.next; var1 != var4; var1 = var1.next) {
            val var5: Segment = var3.prev;
            var5.push(var1.sharedCopy());
         }

         var2.setSize$okio(this.size());
      }

      return var2;
   }

   @Throws(java/io/IOException::class)
   fun copyTo(var1: OutputStream): Buffer {
      return copyTo$default(this, var1, 0L, 0L, 6, null);
   }

   @Throws(java/io/IOException::class)
   fun copyTo(var1: OutputStream, var2: Long): Buffer {
      return copyTo$default(this, var1, var2, 0L, 4, null);
   }

   @Throws(java/io/IOException::class)
   public fun copyTo(out: OutputStream, offset: Long = 0L, byteCount: Long = var0.size - var2): Buffer {
      _SegmentedByteString.checkOffsetAndCount(this.size, var2, var4);
      if (var4 == 0L) {
         return this;
      } else {
         var var12: Segment = this.head;

         while (true) {
            var var13: Segment = var12;
            var var8: Long = var2;
            var var10: Long = var4;
            if (var2 < var12.limit - var12.pos) {
               while (var10 > 0L) {
                  val var6: Int = (int)(var13.pos + var8);
                  val var7: Int = (int)Math.min((long)(var13.limit - (int)((long)var13.pos + var8)), var10);
                  var1.write(var13.data, var6, var7);
                  var10 -= var7;
                  var13 = var13.next;
                  var8 = 0L;
               }

               return this;
            }

            var2 -= var12.limit - var12.pos;
            var12 = var12.next;
         }
      }
   }

   public fun copyTo(out: Buffer, offset: Long = 0L): Buffer {
      return this.copyTo(var1, var2, this.size - var2);
   }

   public fun copyTo(out: Buffer, offset: Long = 0L, byteCount: Long): Buffer {
      _SegmentedByteString.checkOffsetAndCount(this.size(), var2, var4);
      if (var4 != 0L) {
         var1.setSize$okio(var1.size() + var4);
         var var10: Segment = this.head;

         while (true) {
            var var11: Segment = var10;
            var var6: Long = var2;
            var var8: Long = var4;
            if (var2 < var10.limit - var10.pos) {
               while (var8 > 0L) {
                  var10 = var11.sharedCopy();
                  var10.pos += (int)var6;
                  var10.limit = Math.min(var10.pos + (int)var8, var10.limit);
                  val var12: Segment = var1.head;
                  if (var1.head == null) {
                     var10.prev = var10;
                     var10.next = var10.prev;
                     var1.head = var10.next;
                  } else {
                     val var14: Segment = var12.prev;
                     var14.push(var10);
                  }

                  var8 -= var10.limit - var10.pos;
                  var11 = var11.next;
                  var6 = 0L;
               }
               break;
            }

            var2 -= var10.limit - var10.pos;
            var10 = var10.next;
         }
      }

      return this;
   }

   public open fun emit(): Buffer {
      return this;
   }

   public open fun emitCompleteSegments(): Buffer {
      return this;
   }

   public override operator fun equals(other: Any?): Boolean {
      val var11: Boolean;
      if (this === var1) {
         var11 = true;
      } else {
         if (var1 is Buffer) {
            var var5: Long = this.size();
            var1 = var1;
            if (var5 == var1.size()) {
               if (this.size() == 0L) {
                  return true;
               }

               var var14: Segment = this.head;
               var var16: Segment = var1.head;
               var var3: Int = var14.pos;
               var var2: Int = var16.pos;
               var5 = 0L;

               label43:
               while (true) {
                  if (var5 >= this.size()) {
                     return true;
                  }

                  val var9: Long = Math.min(var14.limit - var3, var16.limit - var2);
                  var var7: Long = 0L;

                  var var4: Int;
                  for (var4 = var3; var7 < var9; var2++) {
                     if (var14.data[var4] != var16.data[var2]) {
                        break label43;
                     }

                     var7++;
                     var4++;
                  }

                  var var13: Segment = var14;
                  var3 = var4;
                  if (var4 == var14.limit) {
                     var13 = var14.next;
                     var3 = var13.pos;
                  }

                  var14 = var16;
                  var4 = var2;
                  if (var2 == var16.limit) {
                     var14 = var16.next;
                     var4 = var14.pos;
                  }

                  var5 += var9;
                  var16 = var14;
                  var14 = var13;
                  var2 = var4;
               }
            }
         }

         var11 = false;
      }

      return var11;
   }

   public override fun exhausted(): Boolean {
      val var1: Boolean;
      if (this.size == 0L) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public override fun flush() {
   }

   public operator fun get(pos: Long): Byte {
      _SegmentedByteString.checkOffsetAndCount(this.size(), var1, 1L);
      var var8: Segment = this.head;
      if (this.head == null) {
         throw new NullPointerException();
      } else {
         val var3: Byte;
         if (this.size() - var1 < var1) {
            var var4: Long;
            for (var4 = this.size(); var4 > var1; var4 -= var8.limit - var8.pos) {
               var8 = var8.prev;
            }

            var3 = var8.data[(int)(var8.pos + var1 - var4)];
         } else {
            var var9: Long = 0L;

            while (true) {
               val var6: Long = var8.limit - var8.pos + var9;
               if (var8.limit - var8.pos + var9 > var1) {
                  var3 = var8.data[(int)(var8.pos + var1 - var9)];
                  break;
               }

               var8 = var8.next;
               var9 = var6;
            }
         }

         return var3;
      }
   }

   public override fun hashCode(): Int {
      var var5: Segment = this.head;
      var var1: Int;
      if (this.head == null) {
         var1 = 0;
      } else {
         var var2: Int = 1;

         val var6: Segment;
         do {
            var var3: Int = var5.pos;
            val var4: Int = var5.limit;

            for (var1 = var2; var3 < var4; var3++) {
               var1 = var1 * 31 + var5.data[var3];
            }

            var6 = var5.next;
            var5 = var6;
            var2 = var1;
         } while (var6 != this.head);
      }

      return var1;
   }

   public fun hmacSha1(key: ByteString): ByteString {
      return this.hmac("HmacSHA1", var1);
   }

   public fun hmacSha256(key: ByteString): ByteString {
      return this.hmac("HmacSHA256", var1);
   }

   public fun hmacSha512(key: ByteString): ByteString {
      return this.hmac("HmacSHA512", var1);
   }

   public override fun indexOf(b: Byte): Long {
      return this.indexOf(var1, 0L, java.lang.Long.MAX_VALUE);
   }

   public override fun indexOf(b: Byte, fromIndex: Long): Long {
      return this.indexOf(var1, var2, java.lang.Long.MAX_VALUE);
   }

   public override fun indexOf(b: Byte, fromIndex: Long, toIndex: Long): Long {
      if (0L <= var2 && var2 <= var4) {
         var var8: Long = var4;
         if (var4 > this.size()) {
            var8 = this.size();
         }

         if (var2 == var8) {
            var2 = -1L;
         } else {
            var var15: Segment = this.head;
            if (this.head == null) {
               var2 = -1L;
            } else {
               var4 = 0L;
               var var22: Segment = this.head;
               var var6: Int;
               if (this.size() - var2 < var2) {
                  for (var4 = this.size(); var4 > var2; var4 -= var15.limit - var15.pos) {
                     var15 = var15.prev;
                  }

                  var22 = var15;
                  var var21: Long = var2;
                  if (var15 == null) {
                     return -1L;
                  }

                  label68:
                  while (true) {
                     if (var4 >= var8) {
                        return -1L;
                     }

                     val var26: ByteArray = var22.data;
                     val var18: Int = (int)Math.min((long)var22.limit, (long)var22.pos + var8 - var4);

                     for (var6 = (int)(var22.pos + var21 - var4); var6 < var18; var6++) {
                        if (var26[var6] == var1) {
                           break label68;
                        }
                     }

                     var4 += var22.limit - var22.pos;
                     var22 = var22.next;
                     var21 = var4;
                  }
               } else {
                  label81:
                  while (true) {
                     var var19: Long = var22.limit - var22.pos + var4;
                     if (var22.limit - var22.pos + var4 > var2) {
                        var15 = var22;
                        var19 = var2;
                        if (var22 == null) {
                           return -1L;
                        }

                        while (true) {
                           if (var4 >= var8) {
                              return -1L;
                           }

                           val var23: ByteArray = var15.data;
                           val var7: Int = (int)Math.min((long)var15.limit, (long)var15.pos + var8 - var4);

                           for (var6 = (int)(var15.pos + var19 - var4); var6 < var7; var6++) {
                              if (var23[var6] == var1) {
                                 var22 = var15;
                                 break label81;
                              }
                           }

                           var4 += var15.limit - var15.pos;
                           var15 = var15.next;
                           var19 = var4;
                        }
                     }

                     var22 = var22.next;
                     var4 = var19;
                  }
               }

               var2 = var6 - var22.pos + var4;
            }
         }

         return var2;
      } else {
         val var14: StringBuilder = new StringBuilder("size=");
         var14.append(this.size());
         var14.append(" fromIndex=");
         var14.append(var2);
         var14.append(" toIndex=");
         var14.append(var4);
         throw new IllegalArgumentException(var14.toString().toString());
      }
   }

   @Throws(java/io/IOException::class)
   public override fun indexOf(bytes: ByteString): Long {
      return this.indexOf(var1, 0L);
   }

   @Throws(java/io/IOException::class)
   public override fun indexOf(bytes: ByteString, fromIndex: Long): Long {
      if (var1.size() > 0) {
         var var8: Long = 0L;
         if (var2 < 0L) {
            val var18: StringBuilder = new StringBuilder("fromIndex < 0: ");
            var18.append(var2);
            throw new IllegalArgumentException(var18.toString().toString());
         } else {
            val var15: Segment = this.head;
            if (this.head != null) {
               var var14: Segment = this.head;
               if (this.size() - var2 < var2) {
                  var8 = this.size();

                  for (var14 = var15; var8 > var2; var8 -= var14.limit - var14.pos) {
                     var14 = var14.prev;
                  }

                  if (var14 == null) {
                     return -1L;
                  } else {
                     val var29: ByteArray = var1.internalArray$okio();
                     val var22: Byte = var29[0];
                     val var23: Int = var1.size();

                     for (long var26 = this.size() - var23 + 1L; var8 < var26; var2 = var8) {
                        val var17: ByteArray = var14.data;
                        val var24: Int = (int)Math.min((long)var14.limit, (long)var14.pos + var26 - var8);

                        for (int var32 = (int)(var14.pos + var2 - var8); var32 < var24; var32++) {
                           if (var17[var32] == var22 && _Buffer.rangeEquals(var14, var32 + 1, var29, 1, var23)) {
                              return var32 - var14.pos + var8;
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

                        val var28: ByteArray = var1.internalArray$okio();
                        val var5: Byte = var28[0];
                        val var6: Int = var1.size();

                        for (long var25 = this.size() - var6 + 1L; var8 < var25; var2 = var8) {
                           val var16: ByteArray = var14.data;
                           val var7: Int = (int)Math.min((long)var14.limit, (long)var14.pos + var25 - var8);

                           for (int var20 = (int)(var14.pos + var2 - var8); var20 < var7; var20++) {
                              if (var16[var20] == var5 && _Buffer.rangeEquals(var14, var20 + 1, var28, 1, var6)) {
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
            } else {
               return -1L;
            }
         }
      } else {
         throw new IllegalArgumentException("bytes is empty".toString());
      }
   }

   public override fun indexOfElement(targetBytes: ByteString): Long {
      return this.indexOfElement(var1, 0L);
   }

   public override fun indexOfElement(targetBytes: ByteString, fromIndex: Long): Long {
      var var10: Long = 0L;
      if (var2 < 0L) {
         val var21: StringBuilder = new StringBuilder("fromIndex < 0: ");
         var21.append(var2);
         throw new IllegalArgumentException(var21.toString().toString());
      } else {
         val var17: Segment = this.head;
         if (this.head == null) {
            var2 = -1L;
         } else {
            var var4: Int;
            var var26: Int;
            label157: {
               label177: {
                  var var16: Segment = this.head;
                  if (this.size() - var2 < var2) {
                     var10 = this.size();

                     for (var16 = var17; var10 > var2; var10 -= var16.limit - var16.pos) {
                        var16 = var16.prev;
                     }

                     if (var16 == null) {
                        return -1L;
                     }

                     if (var1.size() == 2) {
                        val var30: Byte = var1.getByte(0);
                        val var33: Byte = var1.getByte(1);
                        var var41: Long = var2;

                        while (true) {
                           if (var10 >= this.size()) {
                              return -1L;
                           }

                           val var45: ByteArray = var16.data;
                           var26 = (int)(var16.pos + var41 - var10);

                           for (int var36 = var16.limit; var26 < var36; var26++) {
                              val var37: Byte = var45[var26];
                              var2 = var10;
                              var19 = var16;
                              var4 = var26;
                              if (var37 == var30) {
                                 break label177;
                              }

                              if (var37 == var33) {
                                 var2 = var10;
                                 var19 = var16;
                                 var4 = var26;
                                 break label177;
                              }
                           }

                           var10 += var16.limit - var16.pos;
                           var16 = var16.next;
                           var41 = var10;
                        }
                     }

                     val var20: ByteArray = var1.internalArray$okio();
                     var var40: Long = var2;

                     label134:
                     while (true) {
                        if (var10 >= this.size()) {
                           return -1L;
                        }

                        val var44: ByteArray = var16.data;
                        var4 = (int)(var16.pos + var40 - var10);

                        for (int var29 = var16.limit; var4 < var29; var4++) {
                           val var32: Byte = var44[var4];
                           val var35: Int = var20.length;

                           for (int var25 = 0; var25 < var35; var25++) {
                              if (var32 == var20[var25]) {
                                 break label134;
                              }
                           }
                        }

                        var10 += var16.limit - var16.pos;
                        var16 = var16.next;
                        var40 = var10;
                     }
                  } else {
                     label154:
                     while (true) {
                        var var12: Long = var16.limit - var16.pos + var10;
                        if (var16.limit - var16.pos + var10 > var2) {
                           if (var16 == null) {
                              return -1L;
                           }

                           if (var1.size() == 2) {
                              val var28: Byte = var1.getByte(0);
                              val var31: Byte = var1.getByte(1);
                              var12 = var2;

                              while (true) {
                                 if (var10 >= this.size()) {
                                    return -1L;
                                 }

                                 val var43: ByteArray = var16.data;
                                 var26 = (int)(var16.pos + var12 - var10);

                                 for (int var34 = var16.limit; var26 < var34; var26++) {
                                    val var9: Byte = var43[var26];
                                    var2 = var10;
                                    var19 = var16;
                                    var4 = var26;
                                    if (var9 == var28) {
                                       break label177;
                                    }

                                    if (var9 == var31) {
                                       var2 = var10;
                                       var19 = var16;
                                       var4 = var26;
                                       break label177;
                                    }
                                 }

                                 var10 += var16.limit - var16.pos;
                                 var16 = var16.next;
                                 var12 = var10;
                              }
                           }

                           val var18: ByteArray = var1.internalArray$okio();
                           var12 = var2;

                           while (true) {
                              if (var10 >= this.size()) {
                                 return -1L;
                              }

                              val var42: ByteArray = var16.data;
                              var4 = (int)(var16.pos + var12 - var10);

                              for (int var6 = var16.limit; var4 < var6; var4++) {
                                 val var8: Byte = var42[var4];
                                 val var7: Int = var18.length;

                                 for (int var5 = 0; var5 < var7; var5++) {
                                    if (var8 == var18[var5]) {
                                       break label154;
                                    }
                                 }
                              }

                              var10 += var16.limit - var16.pos;
                              var16 = var16.next;
                              var12 = var10;
                           }
                        }

                        var16 = var16.next;
                        var10 = var12;
                     }
                  }

                  var26 = var16.pos;
                  var2 = var10;
                  break label157;
               }

               var26 = var19.pos;
            }

            var2 = var4 - var26 + var2;
         }

         return var2;
      }
   }

   public override fun inputStream(): InputStream {
      return new InputStream(this) {
         final Buffer this$0;

         {
            this.this$0 = var1;
         }

         @Override
         public int available() {
            return (int)Math.min(this.this$0.size(), (long)Integer.MAX_VALUE);
         }

         @Override
         public void close() {
         }

         @Override
         public int read() {
            val var1: Int;
            if (this.this$0.size() > 0L) {
               var1 = this.this$0.readByte() and 255;
            } else {
               var1 = -1;
            }

            return var1;
         }

         @Override
         public int read(byte[] var1, int var2, int var3) {
            return this.this$0.read(var1, var2, var3);
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
      return true;
   }

   public fun md5(): ByteString {
      return this.digest("MD5");
   }

   public override fun outputStream(): OutputStream {
      return new OutputStream(this) {
         final Buffer this$0;

         {
            this.this$0 = var1;
         }

         @Override
         public void close() {
         }

         @Override
         public void flush() {
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
            this.this$0.writeByte(var1);
         }

         @Override
         public void write(byte[] var1, int var2, int var3) {
            this.this$0.write(var1, var2, var3);
         }
      };
   }

   public override fun peek(): BufferedSource {
      return Okio.buffer(new PeekSource(this));
   }

   public override fun rangeEquals(offset: Long, bytes: ByteString): Boolean {
      return this.rangeEquals(var1, var3, 0, var3.size());
   }

   public override fun rangeEquals(offset: Long, bytes: ByteString, bytesOffset: Int, byteCount: Int): Boolean {
      var var7: Boolean = false;
      if (var1 >= 0L) {
         var7 = false;
         if (var4 >= 0) {
            var7 = false;
            if (var5 >= 0) {
               var7 = false;
               if (this.size() - var1 >= var5) {
                  if (var3.size() - var4 < var5) {
                     var7 = false;
                  } else {
                     var var6: Int = 0;

                     while (true) {
                        if (var6 >= var5) {
                           var7 = true;
                           break;
                        }

                        if (this.getByte((long)var6 + var1) != var3.getByte(var4 + var6)) {
                           var7 = false;
                           break;
                        }

                        var6++;
                     }
                  }
               }
            }
         }
      }

      return var7;
   }

   @Throws(java/io/IOException::class)
   public override fun read(sink: ByteBuffer): Int {
      val var3: Segment = this.head;
      if (this.head == null) {
         return -1;
      } else {
         val var2: Int = Math.min(var1.remaining(), var3.limit - var3.pos);
         var1.put(var3.data, var3.pos, var2);
         var3.pos += var2;
         this.size -= var2;
         if (var3.pos == var3.limit) {
            this.head = var3.pop();
            SegmentPool.recycle(var3);
         }

         return var2;
      }
   }

   public override fun read(sink: ByteArray): Int {
      return this.read(var1, 0, var1.length);
   }

   public override fun read(sink: ByteArray, offset: Int, byteCount: Int): Int {
      _SegmentedByteString.checkOffsetAndCount((long)var1.length, (long)var2, (long)var3);
      val var4: Segment = this.head;
      if (this.head == null) {
         var2 = -1;
      } else {
         var3 = Math.min(var3, this.head.limit - this.head.pos);
         ArraysKt.copyInto(var4.data, var1, var2, var4.pos, var4.pos + var3);
         var4.pos += var3;
         this.setSize$okio(this.size() - (long)var3);
         if (var4.pos == var4.limit) {
            this.head = var4.pop();
            SegmentPool.recycle(var4);
         }

         var2 = var3;
      }

      return var2;
   }

   public override fun read(sink: Buffer, byteCount: Long): Long {
      if (var2 >= 0L) {
         var var4: Long;
         if (this.size() == 0L) {
            var4 = -1L;
         } else {
            var4 = var2;
            if (var2 > this.size()) {
               var4 = this.size();
            }

            var1.write(this, var4);
         }

         return var4;
      } else {
         val var6: StringBuilder = new StringBuilder("byteCount < 0: ");
         var6.append(var2);
         throw new IllegalArgumentException(var6.toString().toString());
      }
   }

   @Throws(java/io/IOException::class)
   public override fun readAll(sink: Sink): Long {
      val var2: Long = this.size();
      if (var2 > 0L) {
         var1.write(this, var2);
      }

      return var2;
   }

   fun readAndWriteUnsafe(): Buffer.UnsafeCursor {
      return readAndWriteUnsafe$default(this, null, 1, null);
   }

   public fun readAndWriteUnsafe(unsafeCursor: okio.Buffer.UnsafeCursor = _SegmentedByteString.getDEFAULT__new_UnsafeCursor()): okio.Buffer.UnsafeCursor {
      return _Buffer.commonReadAndWriteUnsafe(this, var1);
   }

   @Throws(java/io/EOFException::class)
   public override fun readByte(): Byte {
      if (this.size() != 0L) {
         val var5: Segment = this.head;
         val var4: Int = var5.limit;
         val var2: Int = var5.pos + 1;
         val var1: Byte = var5.data[var5.pos];
         this.setSize$okio(this.size() - 1L);
         if (var2 == var4) {
            this.head = var5.pop();
            SegmentPool.recycle(var5);
         } else {
            var5.pos = var2;
         }

         return var1;
      } else {
         throw new EOFException();
      }
   }

   public override fun readByteArray(): ByteArray {
      return this.readByteArray(this.size());
   }

   @Throws(java/io/EOFException::class)
   public override fun readByteArray(byteCount: Long): ByteArray {
      if (var1 >= 0L && var1 <= 2147483647L) {
         if (this.size() >= var1) {
            val var4: ByteArray = new byte[(int)var1];
            this.readFully(var4);
            return var4;
         } else {
            throw new EOFException();
         }
      } else {
         val var3: StringBuilder = new StringBuilder("byteCount: ");
         var3.append(var1);
         throw new IllegalArgumentException(var3.toString().toString());
      }
   }

   public override fun readByteString(): ByteString {
      return this.readByteString(this.size());
   }

   @Throws(java/io/EOFException::class)
   public override fun readByteString(byteCount: Long): ByteString {
      if (var1 >= 0L && var1 <= 2147483647L) {
         if (this.size() >= var1) {
            val var4: ByteString;
            if (var1 >= 4096L) {
               var4 = this.snapshot((int)var1);
               this.skip(var1);
            } else {
               var4 = new ByteString(this.readByteArray(var1));
            }

            return var4;
         } else {
            throw new EOFException();
         }
      } else {
         val var3: StringBuilder = new StringBuilder("byteCount: ");
         var3.append(var1);
         throw new IllegalArgumentException(var3.toString().toString());
      }
   }

   @Throws(java/io/EOFException::class)
   public override fun readDecimalLong(): Long {
      if (this.size() == 0L) {
         throw new EOFException();
      } else {
         var var2: Int = 0;
         var var9: Long = 0L;
         var var11: Long = -7L;
         var var3: Boolean = false;
         var var1: Byte = 0;

         while (true) {
            val var14: Segment = this.head;
            val var13: ByteArray = var14.data;
            var var4: Int = var14.pos;
            val var6: Int = var14.limit;

            while (true) {
               label89: {
                  var var5: Byte = var1;
                  if (var4 < var6) {
                     var5 = var13[var4];
                     if (var13[var4] >= 48 && var13[var4] <= 57) {
                        val var8: Int = 48 - var5;
                        if (var9 < -922337203685477580L || var9 == -922337203685477580L && 48 - var5 < var11) {
                           val var18: Buffer = new Buffer().writeDecimalLong(var9).writeByte(var5);
                           if (!var3) {
                              var18.readByte();
                           }

                           val var20: StringBuilder = new StringBuilder("Number too large: ");
                           var20.append(var18.readUtf8());
                           throw new NumberFormatException(var20.toString());
                        }

                        var9 = var9 * 10L + var8;
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
                     this.head = var14.pop();
                     SegmentPool.recycle(var14);
                  } else {
                     var14.pos = var4;
                  }

                  if (!var5 && this.head != null) {
                     var1 = var5;
                     break;
                  }

                  this.setSize$okio(this.size() - (long)var2);
                  if (var3) {
                     var1 = 2;
                  } else {
                     var1 = 1;
                  }

                  if (var2 < var1) {
                     if (this.size() != 0L) {
                        val var17: java.lang.String;
                        if (var3) {
                           var17 = "Expected a digit";
                        } else {
                           var17 = "Expected a digit or '-'";
                        }

                        val var19: StringBuilder = new StringBuilder();
                        var19.append(var17);
                        var19.append(" but was 0x");
                        var19.append(_SegmentedByteString.toHexString(this.getByte(0L)));
                        throw new NumberFormatException(var19.toString());
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

   @Throws(java/io/IOException::class)
   public fun readFrom(input: InputStream): Buffer {
      this.readFrom(var1, java.lang.Long.MAX_VALUE, true);
      return this;
   }

   @Throws(java/io/IOException::class)
   public fun readFrom(input: InputStream, byteCount: Long): Buffer {
      if (var2 >= 0L) {
         this.readFrom(var1, var2, false);
         return this;
      } else {
         val var4: StringBuilder = new StringBuilder("byteCount < 0: ");
         var4.append(var2);
         throw new IllegalArgumentException(var4.toString().toString());
      }
   }

   @Throws(java/io/EOFException::class)
   public override fun readFully(sink: Buffer, byteCount: Long) {
      if (this.size() >= var2) {
         var1.write(this, var2);
      } else {
         var1.write(this, this.size());
         throw new EOFException();
      }
   }

   @Throws(java/io/EOFException::class)
   public override fun readFully(sink: ByteArray) {
      var var2: Int = 0;

      while (var2 < var1.length) {
         val var3: Int = this.read(var1, var2, var1.length - var2);
         if (var3 == -1) {
            throw new EOFException();
         }

         var2 += var3;
      }
   }

   @Throws(java/io/EOFException::class)
   public override fun readHexadecimalUnsignedLong(): Long {
      if (this.size() == 0L) {
         throw new EOFException();
      } else {
         var var2: Int = 0;
         var var8: Long = 0L;
         var var3: Boolean = false;

         var var4: Int;
         var var10: Long;
         do {
            val var12: Segment = this.head;
            val var13: ByteArray = var12.data;
            var var5: Int = var12.pos;
            val var7: Int = var12.limit;
            var10 = var8;
            var4 = var2;

            var var6: Boolean;
            while (true) {
               var6 = var3;
               if (var5 >= var7) {
                  break;
               }

               val var1: Byte = var13[var5];
               if (var13[var5] >= 48 && var13[var5] <= 57) {
                  var2 = var1 - 48;
               } else if (var1 >= 97 && var1 <= 102) {
                  var2 = var1 - 87;
               } else {
                  if (var1 < 65 || var1 > 70) {
                     if (var4 == 0) {
                        val var15: StringBuilder = new StringBuilder("Expected leading [0-9a-fA-F] character but was 0x");
                        var15.append(_SegmentedByteString.toHexString(var1));
                        throw new NumberFormatException(var15.toString());
                     }

                     var6 = true;
                     break;
                  }

                  var2 = var1 - 55;
               }

               if ((-1152921504606846976L and var10) != 0L) {
                  val var16: Buffer = new Buffer().writeHexadecimalUnsignedLong(var10).writeByte(var1);
                  val var17: StringBuilder = new StringBuilder("Number too large: ");
                  var17.append(var16.readUtf8());
                  throw new NumberFormatException(var17.toString());
               }

               var10 = var10 shl 4 or var2;
               var5++;
               var4++;
            }

            if (var5 == var7) {
               this.head = var12.pop();
               SegmentPool.recycle(var12);
            } else {
               var12.pos = var5;
            }

            if (var6) {
               break;
            }

            var2 = var4;
            var3 = var6;
            var8 = var10;
         } while (this.head != null);

         this.setSize$okio(this.size() - (long)var4);
         return var10;
      }
   }

   @Throws(java/io/EOFException::class)
   public override fun readInt(): Int {
      if (this.size() >= 4L) {
         val var7: Segment = this.head;
         val var3: Int = var7.limit;
         val var1: Int;
         if (var7.limit - var7.pos < 4L) {
            var1 = (this.readByte() and 255) shl 24 or (this.readByte() and 255) shl 16 or (this.readByte() and 255) shl 8 or this.readByte() and 255;
         } else {
            val var9: Byte = var7.data[var7.pos];
            val var4: Byte = var7.data[var7.pos + 1];
            val var5: Byte = var7.data[var7.pos + 2];
            val var2: Int = var7.pos + 4;
            val var10: Byte = var7.data[var7.pos + 3];
            this.setSize$okio(this.size() - 4L);
            if (var2 == var3) {
               this.head = var7.pop();
               SegmentPool.recycle(var7);
            } else {
               var7.pos = var2;
            }

            var1 = var10 and 255 or (var4 and 255) shl 16 or (var9 and 255) shl 24 or (var5 and 255) shl 8;
         }

         return var1;
      } else {
         throw new EOFException();
      }
   }

   @Throws(java/io/EOFException::class)
   public override fun readIntLe(): Int {
      return _SegmentedByteString.reverseBytes(this.readInt());
   }

   @Throws(java/io/EOFException::class)
   public override fun readLong(): Long {
      if (this.size() >= 8L) {
         val var20: Segment = this.head;
         val var1: Int = var20.limit;
         var var4: Long;
         if (var20.limit - var20.pos < 8L) {
            var4 = (this.readInt() and 4294967295L) shl 32 or 4294967295L and this.readInt();
         } else {
            val var14: Long = var20.data[var20.pos];
            val var18: Long = var20.data[var20.pos + 1];
            val var8: Long = var20.data[var20.pos + 2];
            val var16: Long = var20.data[var20.pos + 3];
            val var10: Long = var20.data[var20.pos + 4];
            val var6: Long = var20.data[var20.pos + 5];
            val var12: Long = var20.data[var20.pos + 6];
            val var2: Int = var20.pos + 8;
            var4 = var20.data[var20.pos + 7];
            this.setSize$okio(this.size() - 8L);
            if (var2 == var1) {
               this.head = var20.pop();
               SegmentPool.recycle(var20);
            } else {
               var20.pos = var2;
            }

            var4 = (var16 and 255L) shl 32 or (var14 and 255L) shl 56 or (var18 and 255L) shl 48 or (var8 and 255L) shl 40 or (var10 and 255L) shl 24 or (
               var6 and 255L
            ) shl 16 or (var12 and 255L) shl 8 or var4 and 255L;
         }

         return var4;
      } else {
         throw new EOFException();
      }
   }

   @Throws(java/io/EOFException::class)
   public override fun readLongLe(): Long {
      return _SegmentedByteString.reverseBytes(this.readLong());
   }

   @Throws(java/io/EOFException::class)
   public override fun readShort(): Short {
      if (this.size() >= 2L) {
         val var7: Segment = this.head;
         val var3: Int = var7.limit;
         val var1: Short;
         if (var7.limit - var7.pos < 2) {
            var1 = (short)((this.readByte() and 255) shl 8 or this.readByte() and 255);
         } else {
            val var2: Byte = var7.data[var7.pos];
            val var4: Int = var7.pos + 2;
            val var8: Byte = var7.data[var7.pos + 1];
            this.setSize$okio(this.size() - 2L);
            if (var4 == var3) {
               this.head = var7.pop();
               SegmentPool.recycle(var7);
            } else {
               var7.pos = var4;
            }

            var1 = (short)(var8 and 255 or (var2 and 255) shl 8);
         }

         return var1;
      } else {
         throw new EOFException();
      }
   }

   @Throws(java/io/EOFException::class)
   public override fun readShortLe(): Short {
      return _SegmentedByteString.reverseBytes(this.readShort());
   }

   @Throws(java/io/EOFException::class)
   public override fun readString(byteCount: Long, charset: Charset): String {
      val var11: Long;
      val var4: Byte = (byte)(if ((var11 = var1 - 0L) == 0L) 0 else (if (var11 < 0L) -1 else 1));
      if (var1 < 0L || var1 > 2147483647L) {
         val var9: StringBuilder = new StringBuilder("byteCount: ");
         var9.append(var1);
         throw new IllegalArgumentException(var9.toString().toString());
      } else if (this.size < var1) {
         throw new EOFException();
      } else if (var4 == 0) {
         return "";
      } else {
         val var6: Segment = this.head;
         if (var6.pos + var1 > var6.limit) {
            return new java.lang.String(this.readByteArray(var1), var3);
         } else {
            val var8: java.lang.String = new java.lang.String(var6.data, var6.pos, (int)var1, var3);
            var6.pos += (int)var1;
            this.size -= var1;
            if (var6.pos == var6.limit) {
               this.head = var6.pop();
               SegmentPool.recycle(var6);
            }

            return var8;
         }
      }
   }

   public override fun readString(charset: Charset): String {
      return this.readString(this.size, var1);
   }

   fun readUnsafe(): Buffer.UnsafeCursor {
      return readUnsafe$default(this, null, 1, null);
   }

   public fun readUnsafe(unsafeCursor: okio.Buffer.UnsafeCursor = _SegmentedByteString.getDEFAULT__new_UnsafeCursor()): okio.Buffer.UnsafeCursor {
      return _Buffer.commonReadUnsafe(this, var1);
   }

   public override fun readUtf8(): String {
      return this.readString(this.size, Charsets.UTF_8);
   }

   @Throws(java/io/EOFException::class)
   public override fun readUtf8(byteCount: Long): String {
      return this.readString(var1, Charsets.UTF_8);
   }

   @Throws(java/io/EOFException::class)
   public override fun readUtf8CodePoint(): Int {
      if (this.size() == 0L) {
         throw new EOFException();
      } else {
         val var1: Byte = this.getByte(0L);
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
               this.skip(1L);
               return 65533;
            }

            var2 = var1 and 7;
            var3 = 4;
            var4 = 65536;
         }

         var var10: Long = this.size();
         val var8: Long = var3;
         if (var10 < var3) {
            val var12: StringBuilder = new StringBuilder("size < ");
            var12.append((int)var3);
            var12.append(": ");
            var12.append(this.size());
            var12.append(" (to read code point prefixed 0x");
            var12.append(_SegmentedByteString.toHexString(var1));
            var12.append(')');
            throw new EOFException(var12.toString());
         } else {
            while (true) {
               if (var5 >= var3) {
                  this.skip(var8);
                  if (var2 > 1114111) {
                     var2 = 65533;
                  } else if (55296 <= var2 && var2 < 57344) {
                     var2 = 65533;
                  } else if (var2 < var4) {
                     var2 = 65533;
                  }
                  break;
               }

               var10 = var5;
               val var7: Byte = this.getByte((long)var5);
               if ((var7 and 192) != 128) {
                  this.skip(var10);
                  var2 = 65533;
                  break;
               }

               var2 = var2 shl 6 or var7 and 63;
               var5++;
            }

            return var2;
         }
      }
   }

   @Throws(java/io/EOFException::class)
   public override fun readUtf8Line(): String? {
      val var1: Long = this.indexOf((byte)10);
      val var3: java.lang.String;
      if (var1 != -1L) {
         var3 = _Buffer.readUtf8Line(this, var1);
      } else if (this.size() != 0L) {
         var3 = this.readUtf8(this.size());
      } else {
         var3 = null;
      }

      return var3;
   }

   @Throws(java/io/EOFException::class)
   public override fun readUtf8LineStrict(): String {
      return this.readUtf8LineStrict(java.lang.Long.MAX_VALUE);
   }

   @Throws(java/io/EOFException::class)
   public override fun readUtf8LineStrict(limit: Long): String {
      if (var1 >= 0L) {
         var var3: Long = java.lang.Long.MAX_VALUE;
         if (var1 != java.lang.Long.MAX_VALUE) {
            var3 = var1 + 1L;
         }

         val var5: Long = this.indexOf((byte)10, 0L, var3);
         val var10: java.lang.String;
         if (var5 != -1L) {
            var10 = _Buffer.readUtf8Line(this, var5);
         } else {
            if (var3 >= this.size() || this.getByte(var3 - 1L) != 13 || this.getByte(var3) != 10) {
               val var8: Buffer = new Buffer();
               this.copyTo(var8, 0L, Math.min((long)32, this.size()));
               val var11: StringBuilder = new StringBuilder("\\n not found: limit=");
               var11.append(Math.min(this.size(), var1));
               var11.append(" content=");
               var11.append(var8.readByteString().hex());
               var11.append('…');
               throw new EOFException(var11.toString());
            }

            var10 = _Buffer.readUtf8Line(this, var3);
         }

         return var10;
      } else {
         val var7: StringBuilder = new StringBuilder("limit < 0: ");
         var7.append(var1);
         throw new IllegalArgumentException(var7.toString().toString());
      }
   }

   public override fun request(byteCount: Long): Boolean {
      val var3: Boolean;
      if (this.size >= var1) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   @Throws(java/io/EOFException::class)
   public override fun require(byteCount: Long) {
      if (this.size < var1) {
         throw new EOFException();
      }
   }

   public override fun select(options: Options): Int {
      var var2: Int = _Buffer.selectPrefix$default(this, var1, false, 2, null);
      if (var2 == -1) {
         var2 = -1;
      } else {
         this.skip((long)var1.getByteStrings$okio()[var2].size());
      }

      return var2;
   }

   public fun sha1(): ByteString {
      return this.digest("SHA-1");
   }

   public fun sha256(): ByteString {
      return this.digest("SHA-256");
   }

   public fun sha512(): ByteString {
      return this.digest("SHA-512");
   }

   @Throws(java/io/EOFException::class)
   public override fun skip(byteCount: Long) {
      while (var1 > 0L) {
         val var8: Segment = this.head;
         if (this.head == null) {
            throw new EOFException();
         }

         val var3: Int = (int)Math.min(var1, (long)(this.head.limit - this.head.pos));
         val var6: Long = this.size();
         var var4: Long = var3;
         this.setSize$okio(var6 - (long)var3);
         var4 = var1 - var4;
         var8.pos += var3;
         var1 = var4;
         if (var8.pos == var8.limit) {
            this.head = var8.pop();
            SegmentPool.recycle(var8);
            var1 = var4;
         }
      }
   }

   public fun snapshot(): ByteString {
      if (this.size() <= 2147483647L) {
         return this.snapshot((int)this.size());
      } else {
         val var1: StringBuilder = new StringBuilder("size > Int.MAX_VALUE: ");
         var1.append(this.size());
         throw new IllegalStateException(var1.toString().toString());
      }
   }

   public fun snapshot(byteCount: Int): ByteString {
      val var5: ByteString;
      if (var1 == 0) {
         var5 = ByteString.EMPTY;
      } else {
         _SegmentedByteString.checkOffsetAndCount(this.size(), 0L, (long)var1);
         var var10: Segment = this.head;
         var var3: Int = 0;

         var var2: Int;
         for (var2 = 0; var3 < var1; var10 = var10.next) {
            if (var10.limit == var10.pos) {
               throw new AssertionError("s.limit == s.pos");
            }

            var3 += var10.limit - var10.pos;
            var2++;
         }

         val var7: Array<ByteArray> = new byte[var2][];
         val var6: IntArray = new int[var2 * 2];
         var var11: Segment = this.head;
         var2 = 0;

         for (int var9 = 0; var9 < var1; var11 = var11.next) {
            var7[var2] = var11.data;
            var9 += var11.limit - var11.pos;
            var6[var2] = Math.min(var9, var1);
            var6[(var7 as Array<Any>).length + var2] = var11.pos;
            var11.shared = true;
            var2++;
         }

         var5 = new SegmentedByteString(var7, var6);
      }

      return var5;
   }

   public override fun timeout(): Timeout {
      return Timeout.NONE;
   }

   public override fun toString(): String {
      return this.snapshot().toString();
   }

   internal fun writableSegment(minimumCapacity: Int): Segment {
      if (var1 >= 1 && var1 <= 8192) {
         var var2: Segment = this.head;
         if (this.head == null) {
            var2 = SegmentPool.take();
            this.head = var2;
            var2.prev = var2;
            var2.next = var2;
         } else {
            var2 = var2.prev;
            if (var2.limit + var1 > 8192 || !var2.owner) {
               var2 = var2.push(SegmentPool.take());
            }
         }

         return var2;
      } else {
         throw new IllegalArgumentException("unexpected capacity".toString());
      }
   }

   @Throws(java/io/IOException::class)
   public override fun write(source: ByteBuffer): Int {
      val var3: Int = var1.remaining();
      var var2: Int = var3;

      while (var2 > 0) {
         val var5: Segment = this.writableSegment$okio(1);
         val var4: Int = Math.min(var2, 8192 - var5.limit);
         var1.get(var5.data, var5.limit, var4);
         var2 -= var4;
         var5.limit += var4;
      }

      this.size += var3;
      return var3;
   }

   public open fun write(byteString: ByteString): Buffer {
      var1.write$okio(this, 0, var1.size());
      return this;
   }

   public open fun write(byteString: ByteString, offset: Int, byteCount: Int): Buffer {
      var1.write$okio(this, var2, var3);
      return this;
   }

   @Throws(java/io/IOException::class)
   public open fun write(source: Source, byteCount: Long): Buffer {
      while (var2 > 0L) {
         val var4: Long = var1.read(this, var2);
         if (var4 == -1L) {
            throw new EOFException();
         }

         var2 -= var4;
      }

      return this;
   }

   public open fun write(source: ByteArray): Buffer {
      return this.write(var1, 0, var1.length);
   }

   public open fun write(source: ByteArray, offset: Int, byteCount: Int): Buffer {
      val var7: Long = var1.length;
      val var9: Long = var2;
      val var11: Long = var3;
      _SegmentedByteString.checkOffsetAndCount(var7, var9, (long)var3);
      val var4: Int = var3 + var2;

      while (var2 < var4) {
         val var13: Segment = this.writableSegment$okio(1);
         val var6: Int = Math.min(var4 - var2, 8192 - var13.limit);
         var3 = var2 + var6;
         ArraysKt.copyInto(var1, var13.data, var13.limit, var2, var2 + var6);
         var13.limit += var6;
         var2 = var3;
      }

      this.setSize$okio(this.size() + var11);
      return this;
   }

   public override fun write(source: Buffer, byteCount: Long) {
      if (var1 === this) {
         throw new IllegalArgumentException("source == this".toString());
      } else {
         _SegmentedByteString.checkOffsetAndCount(var1.size(), 0L, var2);

         while (var2 > 0L) {
            var var7: Segment = var1.head;
            var var4: Int = var7.limit;
            var7 = var1.head;
            if (var2 < var4 - var7.pos) {
               var7 = this.head;
               if (this.head != null) {
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
                     this.setSize$okio(this.size() + var2);
                     break;
                  }
               }

               var7 = var1.head;
               var1.head = var7.split((int)var2);
            }

            var7 = var1.head;
            val var10: Long = var7.limit - var7.pos;
            var1.head = var7.pop();
            val var8: Segment = this.head;
            if (this.head == null) {
               this.head = var7;
               var7.prev = var7;
               var7.next = var7.prev;
            } else {
               val var16: Segment = var8.prev;
               var16.push(var7).compact();
            }

            var1.setSize$okio(var1.size() - var10);
            this.setSize$okio(this.size() + var10);
            var2 -= var10;
         }
      }
   }

   @Throws(java/io/IOException::class)
   public override fun writeAll(source: Source): Long {
      var var2: Long = 0L;

      while (true) {
         val var4: Long = var1.read(this, 8192L);
         if (var4 == -1L) {
            return var2;
         }

         var2 += var4;
      }
   }

   public open fun writeByte(b: Int): Buffer {
      val var3: Segment = this.writableSegment$okio(1);
      var3.data[var3.limit++] = (byte)var1;
      this.setSize$okio(this.size() + 1L);
      return this;
   }

   public open fun writeDecimalLong(v: Long): Buffer {
      val var15: Long;
      val var4: Byte = (byte)(if ((var15 = var1 - 0L) == 0L) 0 else (if (var15 < 0L) -1 else 1));
      val var9: Buffer;
      if (var1 == 0L) {
         var9 = this.writeByte(48);
      } else {
         var var3: Int = 1;
         val var12: Boolean;
         if (var4 < 0) {
            var1 = -var1;
            if (var1 < 0L) {
               return this.writeUtf8("-9223372036854775808");
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

         val var13: Segment = this.writableSegment$okio(var5);
         val var10: ByteArray = var13.data;
         var3 = var13.limit + var5;

         while (var1 != 0L) {
            var10[--var3] = _Buffer.getHEX_DIGIT_BYTES()[(int)(var1 % 10)];
            var1 /= 10;
         }

         if (var12) {
            var10[var3 - 1] = 45;
         }

         var13.limit += var5;
         this.setSize$okio(this.size() + (long)var5);
         var9 = this;
      }

      return var9;
   }

   public open fun writeHexadecimalUnsignedLong(v: Long): Buffer {
      val var8: Buffer;
      if (var1 == 0L) {
         var8 = this.writeByte(48);
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
                     + 3
               )
               / 4
         );
         val var20: Segment = this.writableSegment$okio(
            (int)(
               (
                     (
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
         val var9: ByteArray = var20.data;
         var var3: Int = var20.limit + var4 - 1;

         for (int var5 = var20.limit; var3 >= var5; var3--) {
            var9[var3] = _Buffer.getHEX_DIGIT_BYTES()[(int)(15L and var1)];
            var1 >>>= 4;
         }

         var20.limit += var4;
         this.setSize$okio(this.size() + (long)var4);
         var8 = this;
      }

      return var8;
   }

   public open fun writeInt(i: Int): Buffer {
      val var3: Segment = this.writableSegment$okio(4);
      var3.data[var3.limit] = (byte)(var1 ushr 24 and 255);
      var3.data[var3.limit + 1] = (byte)(var1 ushr 16 and 255);
      var3.data[var3.limit + 2] = (byte)(var1 ushr 8 and 255);
      var3.data[var3.limit + 3] = (byte)(var1 and 255);
      var3.limit += 4;
      this.setSize$okio(this.size() + 4L);
      return this;
   }

   public open fun writeIntLe(i: Int): Buffer {
      return this.writeInt(_SegmentedByteString.reverseBytes(var1));
   }

   public open fun writeLong(v: Long): Buffer {
      val var5: Segment = this.writableSegment$okio(8);
      var5.data[var5.limit] = (byte)(var1 ushr 56 and 255L);
      var5.data[var5.limit + 1] = (byte)(var1 ushr 48 and 255L);
      var5.data[var5.limit + 2] = (byte)(var1 ushr 40 and 255L);
      var5.data[var5.limit + 3] = (byte)(var1 ushr 32 and 255L);
      var5.data[var5.limit + 4] = (byte)(var1 ushr 24 and 255L);
      var5.data[var5.limit + 5] = (byte)(var1 ushr 16 and 255L);
      var5.data[var5.limit + 6] = (byte)(var1 ushr 8 and 255L);
      var5.data[var5.limit + 7] = (byte)(var1 and 255L);
      var5.limit += 8;
      this.setSize$okio(this.size() + 8L);
      return this;
   }

   public open fun writeLongLe(v: Long): Buffer {
      return this.writeLong(_SegmentedByteString.reverseBytes(var1));
   }

   public open fun writeShort(s: Int): Buffer {
      val var3: Segment = this.writableSegment$okio(2);
      var3.data[var3.limit] = (byte)(var1 ushr 8 and 255);
      var3.data[var3.limit + 1] = (byte)(var1 and 255);
      var3.limit += 2;
      this.setSize$okio(this.size() + 2L);
      return this;
   }

   public open fun writeShortLe(s: Int): Buffer {
      return this.writeShort(_SegmentedByteString.reverseBytes((short)var1));
   }

   public open fun writeString(string: String, beginIndex: Int, endIndex: Int, charset: Charset): Buffer {
      if (var2 >= 0) {
         if (var3 >= var2) {
            if (var3 <= var1.length()) {
               if (var4 == Charsets.UTF_8) {
                  return this.writeUtf8(var1, var2, var3);
               } else {
                  var1 = var1.substring(var2, var3);
                  val var8: ByteArray = var1.getBytes(var4);
                  return this.write(var8, 0, var8.length);
               }
            } else {
               val var9: StringBuilder = new StringBuilder("endIndex > string.length: ");
               var9.append(var3);
               var9.append(" > ");
               var9.append(var1.length());
               throw new IllegalArgumentException(var9.toString().toString());
            }
         } else {
            val var6: StringBuilder = new StringBuilder("endIndex < beginIndex: ");
            var6.append(var3);
            var6.append(" < ");
            var6.append(var2);
            throw new IllegalArgumentException(var6.toString().toString());
         }
      } else {
         val var5: StringBuilder = new StringBuilder("beginIndex < 0: ");
         var5.append(var2);
         throw new IllegalArgumentException(var5.toString().toString());
      }
   }

   public open fun writeString(string: String, charset: Charset): Buffer {
      return this.writeString(var1, 0, var1.length(), var2);
   }

   @Throws(java/io/IOException::class)
   fun writeTo(var1: OutputStream): Buffer {
      return writeTo$default(this, var1, 0L, 2, null);
   }

   @Throws(java/io/IOException::class)
   public fun writeTo(out: OutputStream, byteCount: Long = var0.size): Buffer {
      _SegmentedByteString.checkOffsetAndCount(this.size, 0L, var2);
      var var9: Segment = this.head;

      while (var2 > 0L) {
         val var4: Int = (int)Math.min(var2, (long)(var9.limit - var9.pos));
         var1.write(var9.data, var9.pos, var4);
         var9.pos += var4;
         val var5: Long = var4;
         this.size -= var4;
         val var11: Long = var2 - var4;
         var2 -= var5;
         if (var9.pos == var9.limit) {
            val var10: Segment = var9.pop();
            this.head = var10;
            SegmentPool.recycle(var9);
            var9 = var10;
            var2 = var11;
         }
      }

      return this;
   }

   public open fun writeUtf8(string: String): Buffer {
      return this.writeUtf8(var1, 0, var1.length());
   }

   public open fun writeUtf8(string: String, beginIndex: Int, endIndex: Int): Buffer {
      if (var2 < 0) {
         val var11: StringBuilder = new StringBuilder("beginIndex < 0: ");
         var11.append(var2);
         throw new IllegalArgumentException(var11.toString().toString());
      } else if (var3 < var2) {
         val var10: StringBuilder = new StringBuilder("endIndex < beginIndex: ");
         var10.append(var3);
         var10.append(" < ");
         var10.append(var2);
         throw new IllegalArgumentException(var10.toString().toString());
      } else if (var3 > var1.length()) {
         val var21: StringBuilder = new StringBuilder("endIndex > string.length: ");
         var21.append(var3);
         var21.append(" > ");
         var21.append(var1.length());
         throw new IllegalArgumentException(var21.toString().toString());
      } else {
         while (var2 < var3) {
            var var7: Char = var1.charAt(var2);
            if (var7 < 128) {
               val var20: Segment = this.writableSegment$okio(1);
               val var9: ByteArray = var20.data;
               val var6: Int = var20.limit - var2;
               val var16: Int = Math.min(var3, 8192 - (var20.limit - var2));
               var var13: Int = var2 + 1;
               var9[var2 + var6] = (byte)var7;
               var2 = var13;

               while (var2 < var16) {
                  var7 = var1.charAt(var2);
                  if (var7 >= 128) {
                     break;
                  }

                  var13 = var2 + 1;
                  var9[var2 + var6] = (byte)var7;
                  var2 = var13;
               }

               var13 = var6 + var2 - var20.limit;
               var20.limit = var20.limit + (var6 + var2 - var20.limit);
               this.setSize$okio(this.size() + (long)var13);
            } else {
               if (var7 < 2048) {
                  val var8: Segment = this.writableSegment$okio(2);
                  var8.data[var8.limit] = (byte)(var7 shr 6 or 192);
                  var8.data[var8.limit + 1] = (byte)(var7 and 63 or 128);
                  var8.limit += 2;
                  this.setSize$okio(this.size() + 2L);
               } else {
                  if (var7 >= '\ud800' && var7 <= '\udfff') {
                     val var5: Int = var2 + 1;
                     var var4: Char;
                     if (var2 + 1 < var3) {
                        var4 = var1.charAt(var5);
                     } else {
                        var4 = 0;
                     }

                     if (var7 <= '\udbff' && 56320 <= var4 && var4 < 57344) {
                        var4 = ((var7 and 1023) shl 10 or var4 and 1023) + 65536;
                        val var19: Segment = this.writableSegment$okio(4);
                        var19.data[var19.limit] = (byte)(var4 shr 18 or 240);
                        var19.data[var19.limit + 1] = (byte)(var4 shr 12 and 63 or 128);
                        var19.data[var19.limit + 2] = (byte)(var4 shr 6 and 63 or 128);
                        var19.data[var19.limit + 3] = (byte)(var4 and 63 or 128);
                        var19.limit += 4;
                        this.setSize$okio(this.size() + 4L);
                        var2 += 2;
                        continue;
                     }

                     this.writeByte(63);
                     var2 = var5;
                     continue;
                  }

                  val var18: Segment = this.writableSegment$okio(3);
                  var18.data[var18.limit] = (byte)(var7 shr 12 or 224);
                  var18.data[var18.limit + 1] = (byte)(var7 shr 6 and 63 or 128);
                  var18.data[var18.limit + 2] = (byte)(var7 and 63 or 128);
                  var18.limit += 3;
                  this.setSize$okio(this.size() + 3L);
               }

               var2++;
            }
         }

         return this;
      }
   }

   public open fun writeUtf8CodePoint(codePoint: Int): Buffer {
      if (var1 < 128) {
         this.writeByte(var1);
      } else if (var1 < 2048) {
         val var2: Segment = this.writableSegment$okio(2);
         var2.data[var2.limit] = (byte)(var1 shr 6 or 192);
         var2.data[var2.limit + 1] = (byte)(var1 and 63 or 128);
         var2.limit += 2;
         this.setSize$okio(this.size() + 2L);
      } else if (55296 <= var1 && var1 < 57344) {
         this.writeByte(63);
      } else if (var1 < 65536) {
         val var3: Segment = this.writableSegment$okio(3);
         var3.data[var3.limit] = (byte)(var1 shr 12 or 224);
         var3.data[var3.limit + 1] = (byte)(var1 shr 6 and 63 or 128);
         var3.data[var3.limit + 2] = (byte)(var1 and 63 or 128);
         var3.limit += 3;
         this.setSize$okio(this.size() + 3L);
      } else {
         if (var1 > 1114111) {
            val var5: StringBuilder = new StringBuilder("Unexpected code point: 0x");
            var5.append(_SegmentedByteString.toHexString(var1));
            throw new IllegalArgumentException(var5.toString());
         }

         val var4: Segment = this.writableSegment$okio(4);
         var4.data[var4.limit] = (byte)(var1 shr 18 or 240);
         var4.data[var4.limit + 1] = (byte)(var1 shr 12 and 63 or 128);
         var4.data[var4.limit + 2] = (byte)(var1 shr 6 and 63 or 128);
         var4.data[var4.limit + 3] = (byte)(var1 and 63 or 128);
         var4.limit += 4;
         this.setSize$okio(this.size() + 4L);
      }

      return this;
   }

   public class UnsafeCursor : Closeable {
      public final var buffer: Buffer?
         private set

      public final var data: ByteArray?
         private set

      public final var end: Int
         private set

      public final var offset: Long = -1L
         private set

      public final var readWrite: Boolean
         private set

      internal final var segment: Segment?

      public final var start: Int = -1
         private set

      public override fun close() {
         if (this.buffer != null) {
            this.buffer = null;
            this.setSegment$okio(null);
            this.offset = -1L;
            this.data = null;
            this.start = -1;
            this.end = -1;
         } else {
            throw new IllegalStateException("not attached to a buffer".toString());
         }
      }

      public fun expandBuffer(minByteCount: Int): Long {
         if (var1 > 0) {
            if (var1 <= 8192) {
               val var7: Buffer = this.buffer;
               if (this.buffer != null) {
                  if (this.readWrite) {
                     val var4: Long = this.buffer.size();
                     val var10: Segment = var7.writableSegment$okio(var1);
                     var1 = 8192 - var10.limit;
                     var10.limit = 8192;
                     val var2: Long = var1;
                     var7.setSize$okio(var4 + (long)var1);
                     this.setSegment$okio(var10);
                     this.offset = var4;
                     this.data = var10.data;
                     this.start = 8192 - var1;
                     this.end = 8192;
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
            val var6: StringBuilder = new StringBuilder("minByteCount <= 0: ");
            var6.append(var1);
            throw new IllegalArgumentException(var6.toString().toString());
         }
      }

      public fun next(): Int {
         var var1: Long = this.offset;
         val var3: Buffer = this.buffer;
         if (var1 != var3.size()) {
            if (this.offset == -1L) {
               var1 = 0L;
            } else {
               var1 = this.offset + (this.end - this.start);
            }

            return this.seek(var1);
         } else {
            throw new IllegalStateException("no more bytes".toString());
         }
      }

      public fun resizeBuffer(newSize: Long): Long {
         val var12: Buffer = this.buffer;
         if (this.buffer != null) {
            if (!this.readWrite) {
               throw new IllegalStateException("resizeBuffer() only permitted for read/write buffers".toString());
            } else {
               val var8: Long = this.buffer.size();
               val var19: Long;
               val var3: Byte = (byte)(if ((var19 = var1 - var8) == 0L) 0 else (if (var19 < 0L) -1 else 1));
               if (var1 <= var8) {
                  if (var1 < 0L) {
                     val var16: StringBuilder = new StringBuilder("newSize < 0: ");
                     var16.append(var1);
                     throw new IllegalArgumentException(var16.toString().toString());
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

                  this.setSegment$okio(null);
                  this.offset = var1;
                  this.data = null;
                  this.start = -1;
                  this.end = -1;
               } else if (var3 > 0) {
                  var var15: Long = var1 - var8;
                  var var4: Boolean = true;

                  while (var15 > 0L) {
                     val var18: Segment = var12.writableSegment$okio(1);
                     val var5: Int = (int)Math.min(var15, (long)(8192 - var18.limit));
                     var18.limit += var5;
                     var15 -= var5;
                     var var14: Boolean = var4;
                     if (var4) {
                        this.setSegment$okio(var18);
                        this.offset = var8;
                        this.data = var18.data;
                        this.start = var18.limit - var5;
                        this.end = var18.limit;
                        var14 = false;
                     }

                     var4 = var14;
                  }
               }

               var12.setSize$okio(var1);
               return var8;
            }
         } else {
            throw new IllegalStateException("not attached to a buffer".toString());
         }
      }

      public fun seek(offset: Long): Int {
         val var16: Buffer = this.buffer;
         if (this.buffer == null) {
            throw new IllegalStateException("not attached to a buffer".toString());
         } else {
            val var31: Long;
            var var3: Int = if ((var31 = var1 - -1L) == 0L) 0 else (if (var31 < 0L) -1 else 1);
            if (var1 >= -1L && var1 <= this.buffer.size()) {
               if (var3 != 0 && var1 != var16.size()) {
                  var var8: Long = var16.size();
                  val var14: Segment = var16.head;
                  val var15: Segment = var16.head;
                  val var17: Segment = this.getSegment$okio();
                  var var6: Long = var8;
                  var var24: Segment = var14;
                  var var13: Segment = var15;
                  var var4: Long = 0L;
                  if (var17 != null) {
                     var4 = this.offset;
                     var3 = this.start;
                     var24 = this.getSegment$okio();
                     var4 = var4 - (var3 - var24.pos);
                     if (var4 - (var3 - var24.pos) > var1) {
                        var13 = this.getSegment$okio();
                        var6 = var4;
                        var24 = var14;
                        var4 = 0L;
                     } else {
                        var24 = this.getSegment$okio();
                        var13 = var15;
                        var6 = var8;
                     }
                  }

                  var8 = var6;
                  if (var6 - var1 > var1 - var4) {
                     var13 = var24;

                     while (true) {
                        var24 = var13;
                        var6 = var4;
                        if (var1 < var13.limit - var13.pos + var4) {
                           break;
                        }

                        var4 += var13.limit - var13.pos;
                        var13 = var13.next;
                     }
                  } else {
                     while (var8 > var1) {
                        var13 = var13.prev;
                        var8 -= var13.limit - var13.pos;
                     }

                     var6 = var8;
                     var24 = var13;
                  }

                  var13 = var24;
                  if (this.readWrite) {
                     var13 = var24;
                     if (var24.shared) {
                        var13 = var24.unsharedCopy();
                        if (var16.head === var24) {
                           var16.head = var13;
                        }

                        var13 = var24.push(var13);
                        var24 = var13.prev;
                        var24.pop();
                     }
                  }

                  this.setSegment$okio(var13);
                  this.offset = var1;
                  this.data = var13.data;
                  this.start = var13.pos + (int)(var1 - var6);
                  var3 = var13.limit;
                  this.end = var13.limit;
                  var3 = var3 - this.start;
               } else {
                  this.setSegment$okio(null);
                  this.offset = var1;
                  this.data = null;
                  var3 = -1;
                  this.start = -1;
                  this.end = -1;
               }

               return var3;
            } else {
               val var12: StringBuilder = new StringBuilder("offset=");
               var12.append(var1);
               var12.append(" > size=");
               var12.append(var16.size());
               throw new ArrayIndexOutOfBoundsException(var12.toString());
            }
         }
      }
   }
}
