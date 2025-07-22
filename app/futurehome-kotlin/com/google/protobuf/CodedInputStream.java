package com.google.protobuf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public abstract class CodedInputStream {
   private static final int DEFAULT_BUFFER_SIZE = 4096;
   private static final int DEFAULT_SIZE_LIMIT = Integer.MAX_VALUE;
   private static volatile int defaultRecursionLimit;
   int recursionDepth;
   int recursionLimit;
   private boolean shouldDiscardUnknownFields;
   int sizeLimit;
   CodedInputStreamReader wrapper;

   private CodedInputStream() {
      this.recursionLimit = defaultRecursionLimit;
      this.sizeLimit = Integer.MAX_VALUE;
      this.shouldDiscardUnknownFields = false;
   }

   public static int decodeZigZag32(int var0) {
      return -(var0 & 1) ^ var0 >>> 1;
   }

   public static long decodeZigZag64(long var0) {
      return -(var0 & 1L) ^ var0 >>> 1;
   }

   public static CodedInputStream newInstance(InputStream var0) {
      return newInstance(var0, 4096);
   }

   public static CodedInputStream newInstance(InputStream var0, int var1) {
      if (var1 > 0) {
         return (CodedInputStream)(var0 == null ? newInstance(Internal.EMPTY_BYTE_ARRAY) : new CodedInputStream.StreamDecoder(var0, var1));
      } else {
         throw new IllegalArgumentException("bufferSize must be > 0");
      }
   }

   public static CodedInputStream newInstance(Iterable<ByteBuffer> var0) {
      return !CodedInputStream.UnsafeDirectNioDecoder.isSupported() ? newInstance(new IterableByteBufferInputStream(var0)) : newInstance(var0, false);
   }

   static CodedInputStream newInstance(Iterable<ByteBuffer> var0, boolean var1) {
      Iterator var4 = var0.iterator();
      byte var2 = 0;
      int var3 = 0;

      while (var4.hasNext()) {
         ByteBuffer var5 = (ByteBuffer)var4.next();
         var3 += var5.remaining();
         if (var5.hasArray()) {
            var2 |= 1;
         } else if (var5.isDirect()) {
            var2 |= 2;
         } else {
            var2 |= 4;
         }
      }

      return (CodedInputStream)(var2 == 2
         ? new CodedInputStream.IterableDirectByteBufferDecoder(var0, var3, var1)
         : newInstance(new IterableByteBufferInputStream(var0)));
   }

   public static CodedInputStream newInstance(ByteBuffer var0) {
      return newInstance(var0, false);
   }

   static CodedInputStream newInstance(ByteBuffer var0, boolean var1) {
      if (var0.hasArray()) {
         return newInstance(var0.array(), var0.arrayOffset() + var0.position(), var0.remaining(), var1);
      } else if (var0.isDirect() && CodedInputStream.UnsafeDirectNioDecoder.isSupported()) {
         return new CodedInputStream.UnsafeDirectNioDecoder(var0, var1);
      } else {
         int var2 = var0.remaining();
         byte[] var3 = new byte[var2];
         var0.duplicate().get(var3);
         return newInstance(var3, 0, var2, true);
      }
   }

   public static CodedInputStream newInstance(byte[] var0) {
      return newInstance(var0, 0, var0.length);
   }

   public static CodedInputStream newInstance(byte[] var0, int var1, int var2) {
      return newInstance(var0, var1, var2, false);
   }

   static CodedInputStream newInstance(byte[] var0, int var1, int var2, boolean var3) {
      CodedInputStream.ArrayDecoder var5 = new CodedInputStream.ArrayDecoder(var0, var1, var2, var3);

      try {
         var5.pushLimit(var2);
         return var5;
      } catch (InvalidProtocolBufferException var4) {
         throw new IllegalArgumentException(var4);
      }
   }

   public static int readRawVarint32(int var0, InputStream var1) throws IOException {
      if ((var0 & 128) == 0) {
         return var0;
      } else {
         int var2 = var0 & 127;
         int var4 = 7;

         while (true) {
            int var3 = var4;
            if (var4 >= 32) {
               while (var3 < 64) {
                  var4 = var1.read();
                  if (var4 == -1) {
                     throw InvalidProtocolBufferException.truncatedMessage();
                  }

                  if ((var4 & 128) == 0) {
                     return var2;
                  }

                  var3 += 7;
               }

               throw InvalidProtocolBufferException.malformedVarint();
            }

            var3 = var1.read();
            if (var3 == -1) {
               throw InvalidProtocolBufferException.truncatedMessage();
            }

            var2 |= (var3 & 127) << var4;
            if ((var3 & 128) == 0) {
               return var2;
            }

            var4 += 7;
         }
      }
   }

   static int readRawVarint32(InputStream var0) throws IOException {
      int var1 = var0.read();
      if (var1 != -1) {
         return readRawVarint32(var1, var0);
      } else {
         throw InvalidProtocolBufferException.truncatedMessage();
      }
   }

   public abstract void checkLastTagWas(int var1) throws InvalidProtocolBufferException;

   public void checkRecursionLimit() throws InvalidProtocolBufferException {
      if (this.recursionDepth >= this.recursionLimit) {
         throw InvalidProtocolBufferException.recursionLimitExceeded();
      }
   }

   final void discardUnknownFields() {
      this.shouldDiscardUnknownFields = true;
   }

   public abstract void enableAliasing(boolean var1);

   public abstract int getBytesUntilLimit();

   public abstract int getLastTag();

   public abstract int getTotalBytesRead();

   public abstract boolean isAtEnd() throws IOException;

   public abstract void popLimit(int var1);

   public abstract int pushLimit(int var1) throws InvalidProtocolBufferException;

   public abstract boolean readBool() throws IOException;

   public abstract byte[] readByteArray() throws IOException;

   public abstract ByteBuffer readByteBuffer() throws IOException;

   public abstract ByteString readBytes() throws IOException;

   public abstract double readDouble() throws IOException;

   public abstract int readEnum() throws IOException;

   public abstract int readFixed32() throws IOException;

   public abstract long readFixed64() throws IOException;

   public abstract float readFloat() throws IOException;

   public abstract <T extends MessageLite> T readGroup(int var1, Parser<T> var2, ExtensionRegistryLite var3) throws IOException;

   public abstract void readGroup(int var1, MessageLite.Builder var2, ExtensionRegistryLite var3) throws IOException;

   public abstract int readInt32() throws IOException;

   public abstract long readInt64() throws IOException;

   public abstract <T extends MessageLite> T readMessage(Parser<T> var1, ExtensionRegistryLite var2) throws IOException;

   public abstract void readMessage(MessageLite.Builder var1, ExtensionRegistryLite var2) throws IOException;

   public abstract byte readRawByte() throws IOException;

   public abstract byte[] readRawBytes(int var1) throws IOException;

   public abstract int readRawLittleEndian32() throws IOException;

   public abstract long readRawLittleEndian64() throws IOException;

   public abstract int readRawVarint32() throws IOException;

   public abstract long readRawVarint64() throws IOException;

   abstract long readRawVarint64SlowPath() throws IOException;

   public abstract int readSFixed32() throws IOException;

   public abstract long readSFixed64() throws IOException;

   public abstract int readSInt32() throws IOException;

   public abstract long readSInt64() throws IOException;

   public abstract String readString() throws IOException;

   public abstract String readStringRequireUtf8() throws IOException;

   public abstract int readTag() throws IOException;

   public abstract int readUInt32() throws IOException;

   public abstract long readUInt64() throws IOException;

   @Deprecated
   public abstract void readUnknownGroup(int var1, MessageLite.Builder var2) throws IOException;

   public abstract void resetSizeCounter();

   public final int setRecursionLimit(int var1) {
      if (var1 >= 0) {
         int var2 = this.recursionLimit;
         this.recursionLimit = var1;
         return var2;
      } else {
         StringBuilder var3 = new StringBuilder("Recursion limit cannot be negative: ");
         var3.append(var1);
         throw new IllegalArgumentException(var3.toString());
      }
   }

   public final int setSizeLimit(int var1) {
      if (var1 >= 0) {
         int var2 = this.sizeLimit;
         this.sizeLimit = var1;
         return var2;
      } else {
         StringBuilder var3 = new StringBuilder("Size limit cannot be negative: ");
         var3.append(var1);
         throw new IllegalArgumentException(var3.toString());
      }
   }

   final boolean shouldDiscardUnknownFields() {
      return this.shouldDiscardUnknownFields;
   }

   public abstract boolean skipField(int var1) throws IOException;

   @Deprecated
   public abstract boolean skipField(int var1, CodedOutputStream var2) throws IOException;

   public abstract void skipMessage() throws IOException;

   public abstract void skipMessage(CodedOutputStream var1) throws IOException;

   public abstract void skipRawBytes(int var1) throws IOException;

   final void unsetDiscardUnknownFields() {
      this.shouldDiscardUnknownFields = false;
   }

   private static final class ArrayDecoder extends CodedInputStream {
      private final byte[] buffer;
      private int bufferSizeAfterLimit;
      private int currentLimit = Integer.MAX_VALUE;
      private boolean enableAliasing;
      private final boolean immutable;
      private int lastTag;
      private int limit;
      private int pos;
      private int startPos;

      private ArrayDecoder(byte[] var1, int var2, int var3, boolean var4) {
         this.buffer = var1;
         this.limit = var3 + var2;
         this.pos = var2;
         this.startPos = var2;
         this.immutable = var4;
      }

      private void recomputeBufferSizeAfterLimit() {
         int var1 = this.limit + this.bufferSizeAfterLimit;
         this.limit = var1;
         int var3 = var1 - this.startPos;
         int var2 = this.currentLimit;
         if (var3 > var2) {
            var2 = var3 - var2;
            this.bufferSizeAfterLimit = var2;
            this.limit = var1 - var2;
         } else {
            this.bufferSizeAfterLimit = 0;
         }
      }

      private void skipRawVarint() throws IOException {
         if (this.limit - this.pos >= 10) {
            this.skipRawVarintFastPath();
         } else {
            this.skipRawVarintSlowPath();
         }
      }

      private void skipRawVarintFastPath() throws IOException {
         for (int var1 = 0; var1 < 10; var1++) {
            byte[] var3 = this.buffer;
            int var2 = this.pos++;
            if (var3[var2] >= 0) {
               return;
            }
         }

         throw InvalidProtocolBufferException.malformedVarint();
      }

      private void skipRawVarintSlowPath() throws IOException {
         for (int var1 = 0; var1 < 10; var1++) {
            if (this.readRawByte() >= 0) {
               return;
            }
         }

         throw InvalidProtocolBufferException.malformedVarint();
      }

      @Override
      public void checkLastTagWas(int var1) throws InvalidProtocolBufferException {
         if (this.lastTag != var1) {
            throw InvalidProtocolBufferException.invalidEndTag();
         }
      }

      @Override
      public void enableAliasing(boolean var1) {
         this.enableAliasing = var1;
      }

      @Override
      public int getBytesUntilLimit() {
         int var1 = this.currentLimit;
         return var1 == Integer.MAX_VALUE ? -1 : var1 - this.getTotalBytesRead();
      }

      @Override
      public int getLastTag() {
         return this.lastTag;
      }

      @Override
      public int getTotalBytesRead() {
         return this.pos - this.startPos;
      }

      @Override
      public boolean isAtEnd() throws IOException {
         boolean var1;
         if (this.pos == this.limit) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      @Override
      public void popLimit(int var1) {
         this.currentLimit = var1;
         this.recomputeBufferSizeAfterLimit();
      }

      @Override
      public int pushLimit(int var1) throws InvalidProtocolBufferException {
         if (var1 >= 0) {
            int var2 = var1 + this.getTotalBytesRead();
            if (var2 >= 0) {
               var1 = this.currentLimit;
               if (var2 <= var1) {
                  this.currentLimit = var2;
                  this.recomputeBufferSizeAfterLimit();
                  return var1;
               } else {
                  throw InvalidProtocolBufferException.truncatedMessage();
               }
            } else {
               throw InvalidProtocolBufferException.parseFailure();
            }
         } else {
            throw InvalidProtocolBufferException.negativeSize();
         }
      }

      @Override
      public boolean readBool() throws IOException {
         boolean var1;
         if (this.readRawVarint64() != 0L) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      @Override
      public byte[] readByteArray() throws IOException {
         return this.readRawBytes(this.readRawVarint32());
      }

      @Override
      public ByteBuffer readByteBuffer() throws IOException {
         int var3 = this.readRawVarint32();
         if (var3 > 0) {
            int var1 = this.limit;
            int var2 = this.pos;
            if (var3 <= var1 - var2) {
               ByteBuffer var4;
               if (!this.immutable && this.enableAliasing) {
                  var4 = ByteBuffer.wrap(this.buffer, var2, var3).slice();
               } else {
                  var4 = ByteBuffer.wrap(Arrays.copyOfRange(this.buffer, var2, var2 + var3));
               }

               this.pos += var3;
               return var4;
            }
         }

         if (var3 == 0) {
            return Internal.EMPTY_BYTE_BUFFER;
         } else if (var3 < 0) {
            throw InvalidProtocolBufferException.negativeSize();
         } else {
            throw InvalidProtocolBufferException.truncatedMessage();
         }
      }

      @Override
      public ByteString readBytes() throws IOException {
         int var3 = this.readRawVarint32();
         if (var3 > 0) {
            int var1 = this.limit;
            int var2 = this.pos;
            if (var3 <= var1 - var2) {
               ByteString var4;
               if (this.immutable && this.enableAliasing) {
                  var4 = ByteString.wrap(this.buffer, var2, var3);
               } else {
                  var4 = ByteString.copyFrom(this.buffer, var2, var3);
               }

               this.pos += var3;
               return var4;
            }
         }

         return var3 == 0 ? ByteString.EMPTY : ByteString.wrap(this.readRawBytes(var3));
      }

      @Override
      public double readDouble() throws IOException {
         return Double.longBitsToDouble(this.readRawLittleEndian64());
      }

      @Override
      public int readEnum() throws IOException {
         return this.readRawVarint32();
      }

      @Override
      public int readFixed32() throws IOException {
         return this.readRawLittleEndian32();
      }

      @Override
      public long readFixed64() throws IOException {
         return this.readRawLittleEndian64();
      }

      @Override
      public float readFloat() throws IOException {
         return Float.intBitsToFloat(this.readRawLittleEndian32());
      }

      @Override
      public <T extends MessageLite> T readGroup(int var1, Parser<T> var2, ExtensionRegistryLite var3) throws IOException {
         this.checkRecursionLimit();
         this.recursionDepth++;
         MessageLite var4 = (MessageLite)var2.parsePartialFrom(this, var3);
         this.checkLastTagWas(WireFormat.makeTag(var1, 4));
         this.recursionDepth--;
         return (T)var4;
      }

      @Override
      public void readGroup(int var1, MessageLite.Builder var2, ExtensionRegistryLite var3) throws IOException {
         this.checkRecursionLimit();
         this.recursionDepth++;
         var2.mergeFrom(this, var3);
         this.checkLastTagWas(WireFormat.makeTag(var1, 4));
         this.recursionDepth--;
      }

      @Override
      public int readInt32() throws IOException {
         return this.readRawVarint32();
      }

      @Override
      public long readInt64() throws IOException {
         return this.readRawVarint64();
      }

      @Override
      public <T extends MessageLite> T readMessage(Parser<T> var1, ExtensionRegistryLite var2) throws IOException {
         int var3 = this.readRawVarint32();
         this.checkRecursionLimit();
         var3 = this.pushLimit(var3);
         this.recursionDepth++;
         MessageLite var4 = (MessageLite)var1.parsePartialFrom(this, var2);
         this.checkLastTagWas(0);
         this.recursionDepth--;
         if (this.getBytesUntilLimit() == 0) {
            this.popLimit(var3);
            return (T)var4;
         } else {
            throw InvalidProtocolBufferException.truncatedMessage();
         }
      }

      @Override
      public void readMessage(MessageLite.Builder var1, ExtensionRegistryLite var2) throws IOException {
         int var3 = this.readRawVarint32();
         this.checkRecursionLimit();
         var3 = this.pushLimit(var3);
         this.recursionDepth++;
         var1.mergeFrom(this, var2);
         this.checkLastTagWas(0);
         this.recursionDepth--;
         if (this.getBytesUntilLimit() == 0) {
            this.popLimit(var3);
         } else {
            throw InvalidProtocolBufferException.truncatedMessage();
         }
      }

      @Override
      public byte readRawByte() throws IOException {
         int var1 = this.pos;
         if (var1 != this.limit) {
            byte[] var2 = this.buffer;
            this.pos = var1 + 1;
            return var2[var1];
         } else {
            throw InvalidProtocolBufferException.truncatedMessage();
         }
      }

      @Override
      public byte[] readRawBytes(int var1) throws IOException {
         if (var1 > 0) {
            int var3 = this.limit;
            int var2 = this.pos;
            if (var1 <= var3 - var2) {
               var1 += var2;
               this.pos = var1;
               return Arrays.copyOfRange(this.buffer, var2, var1);
            }
         }

         if (var1 <= 0) {
            if (var1 == 0) {
               return Internal.EMPTY_BYTE_ARRAY;
            } else {
               throw InvalidProtocolBufferException.negativeSize();
            }
         } else {
            throw InvalidProtocolBufferException.truncatedMessage();
         }
      }

      @Override
      public int readRawLittleEndian32() throws IOException {
         int var2 = this.pos;
         if (this.limit - var2 >= 4) {
            byte[] var5 = this.buffer;
            this.pos = var2 + 4;
            byte var1 = var5[var2];
            byte var4 = var5[var2 + 1];
            byte var3 = var5[var2 + 2];
            return (var5[var2 + 3] & 0xFF) << 24 | var1 & 0xFF | (var4 & 0xFF) << 8 | (var3 & 0xFF) << 16;
         } else {
            throw InvalidProtocolBufferException.truncatedMessage();
         }
      }

      @Override
      public long readRawLittleEndian64() throws IOException {
         int var1 = this.pos;
         if (this.limit - var1 >= 8) {
            byte[] var16 = this.buffer;
            this.pos = var1 + 8;
            long var10 = var16[var1];
            long var6 = var16[var1 + 1];
            long var2 = var16[var1 + 2];
            long var8 = var16[var1 + 3];
            long var12 = var16[var1 + 4];
            long var4 = var16[var1 + 5];
            long var14 = var16[var1 + 6];
            return (var16[var1 + 7] & 255L) << 56
               | var10 & 255L
               | (var6 & 255L) << 8
               | (var2 & 255L) << 16
               | (var8 & 255L) << 24
               | (var12 & 255L) << 32
               | (var4 & 255L) << 40
               | (var14 & 255L) << 48;
         } else {
            throw InvalidProtocolBufferException.truncatedMessage();
         }
      }

      @Override
      public int readRawVarint32() throws IOException {
         int var5 = this.pos;
         int var2 = this.limit;
         if (var2 != var5) {
            byte[] var6 = this.buffer;
            int var1 = var5 + 1;
            int var3 = var6[var5];
            if (var3 >= 0) {
               this.pos = var1;
               return var3;
            }

            if (var2 - var1 >= 9) {
               var2 = var5 + 2;
               var1 = var6[var1] << 7 ^ var3;
               if (var1 < 0) {
                  var1 ^= -128;
               } else {
                  label66: {
                     var3 = var5 + 3;
                     var2 = var6[var2] << 14 ^ var1;
                     if (var2 >= 0) {
                        var2 ^= 16256;
                        var1 = var3;
                     } else {
                        label63: {
                           var1 = var5 + 4;
                           var2 ^= var6[var3] << 21;
                           if (var2 < 0) {
                              var2 = -2080896 ^ var2;
                           } else {
                              var3 = var5 + 5;
                              int var4 = var6[var1];
                              var2 = var2 ^ var4 << 28 ^ 266354560;
                              var1 = var3;
                              if (var4 >= 0) {
                                 break label63;
                              }

                              var4 = var5 + 6;
                              var1 = var4;
                              if (var6[var3] < 0) {
                                 var3 = var5 + 7;
                                 var1 = var3;
                                 if (var6[var4] >= 0) {
                                    break label63;
                                 }

                                 var4 = var5 + 8;
                                 var1 = var4;
                                 if (var6[var3] < 0) {
                                    var3 = var5 + 9;
                                    var1 = var3;
                                    if (var6[var4] < 0) {
                                       if (var6[var3] < 0) {
                                          return (int)this.readRawVarint64SlowPath();
                                       }

                                       var3 = var5 + 10;
                                       var1 = var2;
                                       var2 = var3;
                                       break label66;
                                    }
                                    break label63;
                                 }
                              }
                           }

                           var1 = var2;
                           var2 = var1;
                           break label66;
                        }
                     }

                     var1 = var2;
                     var2 = var1;
                  }
               }

               this.pos = var2;
               return var1;
            }
         }

         return (int)this.readRawVarint64SlowPath();
      }

      @Override
      public long readRawVarint64() throws IOException {
         int var4 = this.pos;
         int var2 = this.limit;
         if (var2 != var4) {
            byte[] var9 = this.buffer;
            int var1 = var4 + 1;
            int var3 = var9[var4];
            if (var3 >= 0) {
               this.pos = var1;
               return var3;
            }

            if (var2 - var1 >= 9) {
               var2 = var4 + 2;
               var3 = var9[var1] << 7 ^ var3;
               long var21;
               if (var3 < 0) {
                  var21 = var3 ^ -128;
                  var1 = var2;
               } else {
                  var1 = var4 + 3;
                  var3 = var9[var2] << 14 ^ var3;
                  if (var3 >= 0) {
                     var21 = var3 ^ 16256;
                  } else {
                     var2 = var4 + 4;
                     var1 = var3 ^ var9[var1] << 21;
                     if (var1 < 0) {
                        var21 = -2080896 ^ var1;
                        var1 = var2;
                     } else {
                        label73: {
                           var21 = var1;
                           var1 = var4 + 5;
                           var21 ^= (long)var9[var2] << 28;
                           long var22;
                           if (var21 >= 0L) {
                              var22 = 266354560L;
                           } else {
                              label70: {
                                 var2 = var4 + 6;
                                 var22 = var21 ^ (long)var9[var1] << 35;
                                 if (var22 < 0L) {
                                    var21 = -34093383808L;
                                    var1 = var2;
                                 } else {
                                    var3 = var4 + 7;
                                    var21 = var22 ^ (long)var9[var2] << 42;
                                    if (var21 >= 0L) {
                                       var22 = 4363953127296L;
                                       var1 = var3;
                                       break label70;
                                    }

                                    var1 = var4 + 8;
                                    var22 = var21 ^ (long)var9[var3] << 49;
                                    if (var22 >= 0L) {
                                       var2 = var4 + 9;
                                       var21 = var22 ^ (long)var9[var1] << 56 ^ 71499008037633920L;
                                       var1 = var2;
                                       if (var21 < 0L) {
                                          if (var9[var2] < 0L) {
                                             return this.readRawVarint64SlowPath();
                                          }

                                          var1 = var4 + 10;
                                       }
                                       break label73;
                                    }

                                    var21 = -558586000294016L;
                                 }

                                 var21 = var22 ^ var21;
                                 break label73;
                              }
                           }

                           var21 ^= var22;
                        }
                     }
                  }
               }

               this.pos = var1;
               return var21;
            }
         }

         return this.readRawVarint64SlowPath();
      }

      @Override
      long readRawVarint64SlowPath() throws IOException {
         long var3 = 0L;

         for (byte var1 = 0; var1 < 64; var1 += 7) {
            byte var2 = this.readRawByte();
            var3 |= (long)(var2 & 127) << var1;
            if ((var2 & 128) == 0) {
               return var3;
            }
         }

         throw InvalidProtocolBufferException.malformedVarint();
      }

      @Override
      public int readSFixed32() throws IOException {
         return this.readRawLittleEndian32();
      }

      @Override
      public long readSFixed64() throws IOException {
         return this.readRawLittleEndian64();
      }

      @Override
      public int readSInt32() throws IOException {
         return decodeZigZag32(this.readRawVarint32());
      }

      @Override
      public long readSInt64() throws IOException {
         return decodeZigZag64(this.readRawVarint64());
      }

      @Override
      public String readString() throws IOException {
         int var1 = this.readRawVarint32();
         if (var1 > 0 && var1 <= this.limit - this.pos) {
            String var2 = new String(this.buffer, this.pos, var1, Internal.UTF_8);
            this.pos += var1;
            return var2;
         } else if (var1 == 0) {
            return "";
         } else if (var1 < 0) {
            throw InvalidProtocolBufferException.negativeSize();
         } else {
            throw InvalidProtocolBufferException.truncatedMessage();
         }
      }

      @Override
      public String readStringRequireUtf8() throws IOException {
         int var2 = this.readRawVarint32();
         if (var2 > 0) {
            int var1 = this.limit;
            int var3 = this.pos;
            if (var2 <= var1 - var3) {
               String var4 = Utf8.decodeUtf8(this.buffer, var3, var2);
               this.pos += var2;
               return var4;
            }
         }

         if (var2 == 0) {
            return "";
         } else if (var2 <= 0) {
            throw InvalidProtocolBufferException.negativeSize();
         } else {
            throw InvalidProtocolBufferException.truncatedMessage();
         }
      }

      @Override
      public int readTag() throws IOException {
         if (this.isAtEnd()) {
            this.lastTag = 0;
            return 0;
         } else {
            int var1 = this.readRawVarint32();
            this.lastTag = var1;
            if (WireFormat.getTagFieldNumber(var1) != 0) {
               return this.lastTag;
            } else {
               throw InvalidProtocolBufferException.invalidTag();
            }
         }
      }

      @Override
      public int readUInt32() throws IOException {
         return this.readRawVarint32();
      }

      @Override
      public long readUInt64() throws IOException {
         return this.readRawVarint64();
      }

      @Deprecated
      @Override
      public void readUnknownGroup(int var1, MessageLite.Builder var2) throws IOException {
         this.readGroup(var1, var2, ExtensionRegistryLite.getEmptyRegistry());
      }

      @Override
      public void resetSizeCounter() {
         this.startPos = this.pos;
      }

      @Override
      public boolean skipField(int var1) throws IOException {
         int var2 = WireFormat.getTagWireType(var1);
         if (var2 != 0) {
            if (var2 != 1) {
               if (var2 != 2) {
                  if (var2 != 3) {
                     if (var2 != 4) {
                        if (var2 == 5) {
                           this.skipRawBytes(4);
                           return true;
                        } else {
                           throw InvalidProtocolBufferException.invalidWireType();
                        }
                     } else {
                        return false;
                     }
                  } else {
                     this.skipMessage();
                     this.checkLastTagWas(WireFormat.makeTag(WireFormat.getTagFieldNumber(var1), 4));
                     return true;
                  }
               } else {
                  this.skipRawBytes(this.readRawVarint32());
                  return true;
               }
            } else {
               this.skipRawBytes(8);
               return true;
            }
         } else {
            this.skipRawVarint();
            return true;
         }
      }

      @Override
      public boolean skipField(int var1, CodedOutputStream var2) throws IOException {
         int var3 = WireFormat.getTagWireType(var1);
         if (var3 != 0) {
            if (var3 != 1) {
               if (var3 != 2) {
                  if (var3 != 3) {
                     if (var3 != 4) {
                        if (var3 == 5) {
                           var3 = this.readRawLittleEndian32();
                           var2.writeUInt32NoTag(var1);
                           var2.writeFixed32NoTag(var3);
                           return true;
                        } else {
                           throw InvalidProtocolBufferException.invalidWireType();
                        }
                     } else {
                        return false;
                     }
                  } else {
                     var2.writeUInt32NoTag(var1);
                     this.skipMessage(var2);
                     var1 = WireFormat.makeTag(WireFormat.getTagFieldNumber(var1), 4);
                     this.checkLastTagWas(var1);
                     var2.writeUInt32NoTag(var1);
                     return true;
                  }
               } else {
                  ByteString var6 = this.readBytes();
                  var2.writeUInt32NoTag(var1);
                  var2.writeBytesNoTag(var6);
                  return true;
               }
            } else {
               long var9 = this.readRawLittleEndian64();
               var2.writeUInt32NoTag(var1);
               var2.writeFixed64NoTag(var9);
               return true;
            }
         } else {
            long var4 = this.readInt64();
            var2.writeUInt32NoTag(var1);
            var2.writeUInt64NoTag(var4);
            return true;
         }
      }

      @Override
      public void skipMessage() throws IOException {
         int var1;
         do {
            var1 = this.readTag();
         } while (var1 != 0 && this.skipField(var1));
      }

      @Override
      public void skipMessage(CodedOutputStream var1) throws IOException {
         int var2;
         do {
            var2 = this.readTag();
         } while (var2 != 0 && this.skipField(var2, var1));
      }

      @Override
      public void skipRawBytes(int var1) throws IOException {
         if (var1 >= 0) {
            int var2 = this.limit;
            int var3 = this.pos;
            if (var1 <= var2 - var3) {
               this.pos = var3 + var1;
               return;
            }
         }

         if (var1 < 0) {
            throw InvalidProtocolBufferException.negativeSize();
         } else {
            throw InvalidProtocolBufferException.truncatedMessage();
         }
      }
   }

   private static final class IterableDirectByteBufferDecoder extends CodedInputStream {
      private int bufferSizeAfterCurrentLimit;
      private long currentAddress;
      private ByteBuffer currentByteBuffer;
      private long currentByteBufferLimit;
      private long currentByteBufferPos;
      private long currentByteBufferStartPos;
      private int currentLimit = Integer.MAX_VALUE;
      private boolean enableAliasing;
      private final boolean immutable;
      private final Iterable<ByteBuffer> input;
      private final Iterator<ByteBuffer> iterator;
      private int lastTag;
      private int startOffset;
      private int totalBufferSize;
      private int totalBytesRead;

      private IterableDirectByteBufferDecoder(Iterable<ByteBuffer> var1, int var2, boolean var3) {
         this.totalBufferSize = var2;
         this.input = var1;
         this.iterator = var1.iterator();
         this.immutable = var3;
         this.totalBytesRead = 0;
         this.startOffset = 0;
         if (var2 == 0) {
            this.currentByteBuffer = Internal.EMPTY_BYTE_BUFFER;
            this.currentByteBufferPos = 0L;
            this.currentByteBufferStartPos = 0L;
            this.currentByteBufferLimit = 0L;
            this.currentAddress = 0L;
         } else {
            this.tryGetNextByteBuffer();
         }
      }

      private long currentRemaining() {
         return this.currentByteBufferLimit - this.currentByteBufferPos;
      }

      private void getNextByteBuffer() throws InvalidProtocolBufferException {
         if (this.iterator.hasNext()) {
            this.tryGetNextByteBuffer();
         } else {
            throw InvalidProtocolBufferException.truncatedMessage();
         }
      }

      private void readRawBytesTo(byte[] var1, int var2, int var3) throws IOException {
         if (var3 >= 0 && var3 <= this.remaining()) {
            int var4 = var3;

            while (var4 > 0) {
               if (this.currentRemaining() == 0L) {
                  this.getNextByteBuffer();
               }

               int var5 = Math.min(var4, (int)this.currentRemaining());
               long var10 = this.currentByteBufferPos;
               long var8 = var3 - var4 + var2;
               long var6 = var5;
               UnsafeUtil.copyMemory(var10, var1, var8, var6);
               var4 -= var5;
               this.currentByteBufferPos += var6;
            }
         } else if (var3 <= 0) {
            if (var3 != 0) {
               throw InvalidProtocolBufferException.negativeSize();
            }
         } else {
            throw InvalidProtocolBufferException.truncatedMessage();
         }
      }

      private void recomputeBufferSizeAfterLimit() {
         int var1 = this.totalBufferSize + this.bufferSizeAfterCurrentLimit;
         this.totalBufferSize = var1;
         int var3 = var1 - this.startOffset;
         int var2 = this.currentLimit;
         if (var3 > var2) {
            var2 = var3 - var2;
            this.bufferSizeAfterCurrentLimit = var2;
            this.totalBufferSize = var1 - var2;
         } else {
            this.bufferSizeAfterCurrentLimit = 0;
         }
      }

      private int remaining() {
         return (int)(this.totalBufferSize - this.totalBytesRead - this.currentByteBufferPos + this.currentByteBufferStartPos);
      }

      private void skipRawVarint() throws IOException {
         for (int var1 = 0; var1 < 10; var1++) {
            if (this.readRawByte() >= 0) {
               return;
            }
         }

         throw InvalidProtocolBufferException.malformedVarint();
      }

      private ByteBuffer slice(int var1, int var2) throws IOException {
         int var3 = this.currentByteBuffer.position();
         int var4 = this.currentByteBuffer.limit();
         ByteBuffer var5 = this.currentByteBuffer;

         ByteBuffer var6;
         try {
            ((Buffer)var5).position(var1);
            ((Buffer)var5).limit(var2);
            var6 = this.currentByteBuffer.slice();
         } catch (IllegalArgumentException var9) {
            throw InvalidProtocolBufferException.truncatedMessage();
         } finally {
            ((Buffer)var5).position(var3);
            ((Buffer)var5).limit(var4);
         }

         return var6;
      }

      private void tryGetNextByteBuffer() {
         ByteBuffer var3 = this.iterator.next();
         this.currentByteBuffer = var3;
         this.totalBytesRead = this.totalBytesRead + (int)(this.currentByteBufferPos - this.currentByteBufferStartPos);
         long var1 = var3.position();
         this.currentByteBufferPos = var1;
         this.currentByteBufferStartPos = var1;
         this.currentByteBufferLimit = this.currentByteBuffer.limit();
         var1 = UnsafeUtil.addressOffset(this.currentByteBuffer);
         this.currentAddress = var1;
         this.currentByteBufferPos += var1;
         this.currentByteBufferStartPos += var1;
         this.currentByteBufferLimit += var1;
      }

      @Override
      public void checkLastTagWas(int var1) throws InvalidProtocolBufferException {
         if (this.lastTag != var1) {
            throw InvalidProtocolBufferException.invalidEndTag();
         }
      }

      @Override
      public void enableAliasing(boolean var1) {
         this.enableAliasing = var1;
      }

      @Override
      public int getBytesUntilLimit() {
         int var1 = this.currentLimit;
         return var1 == Integer.MAX_VALUE ? -1 : var1 - this.getTotalBytesRead();
      }

      @Override
      public int getLastTag() {
         return this.lastTag;
      }

      @Override
      public int getTotalBytesRead() {
         return (int)(this.totalBytesRead - this.startOffset + this.currentByteBufferPos - this.currentByteBufferStartPos);
      }

      @Override
      public boolean isAtEnd() throws IOException {
         boolean var1;
         if (this.totalBytesRead + this.currentByteBufferPos - this.currentByteBufferStartPos == this.totalBufferSize) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      @Override
      public void popLimit(int var1) {
         this.currentLimit = var1;
         this.recomputeBufferSizeAfterLimit();
      }

      @Override
      public int pushLimit(int var1) throws InvalidProtocolBufferException {
         if (var1 >= 0) {
            int var2 = var1 + this.getTotalBytesRead();
            var1 = this.currentLimit;
            if (var2 <= var1) {
               this.currentLimit = var2;
               this.recomputeBufferSizeAfterLimit();
               return var1;
            } else {
               throw InvalidProtocolBufferException.truncatedMessage();
            }
         } else {
            throw InvalidProtocolBufferException.negativeSize();
         }
      }

      @Override
      public boolean readBool() throws IOException {
         boolean var1;
         if (this.readRawVarint64() != 0L) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      @Override
      public byte[] readByteArray() throws IOException {
         return this.readRawBytes(this.readRawVarint32());
      }

      @Override
      public ByteBuffer readByteBuffer() throws IOException {
         int var1 = this.readRawVarint32();
         if (var1 > 0) {
            long var2 = var1;
            if (var2 <= this.currentRemaining()) {
               if (!this.immutable && this.enableAliasing) {
                  long var6 = this.currentByteBufferPos + var2;
                  this.currentByteBufferPos = var6;
                  long var4 = this.currentAddress;
                  return this.slice((int)(var6 - var4 - var2), (int)(var6 - var4));
               }

               byte[] var9 = new byte[var1];
               UnsafeUtil.copyMemory(this.currentByteBufferPos, var9, 0L, var2);
               this.currentByteBufferPos += var2;
               return ByteBuffer.wrap(var9);
            }
         }

         if (var1 > 0 && var1 <= this.remaining()) {
            byte[] var8 = new byte[var1];
            this.readRawBytesTo(var8, 0, var1);
            return ByteBuffer.wrap(var8);
         } else if (var1 == 0) {
            return Internal.EMPTY_BYTE_BUFFER;
         } else if (var1 < 0) {
            throw InvalidProtocolBufferException.negativeSize();
         } else {
            throw InvalidProtocolBufferException.truncatedMessage();
         }
      }

      @Override
      public ByteString readBytes() throws IOException {
         int var1 = this.readRawVarint32();
         if (var1 > 0) {
            long var4 = var1;
            long var6 = this.currentByteBufferLimit;
            long var8 = this.currentByteBufferPos;
            if (var4 <= var6 - var8) {
               if (this.immutable && this.enableAliasing) {
                  int var11 = (int)(var8 - this.currentAddress);
                  ByteString var14 = ByteString.wrap(this.slice(var11, var1 + var11));
                  this.currentByteBufferPos += var4;
                  return var14;
               }

               byte[] var13 = new byte[var1];
               UnsafeUtil.copyMemory(var8, var13, 0L, var4);
               this.currentByteBufferPos += var4;
               return ByteString.wrap(var13);
            }
         }

         if (var1 > 0 && var1 <= this.remaining()) {
            if (this.immutable && this.enableAliasing) {
               ArrayList var12 = new ArrayList();

               while (var1 > 0) {
                  if (this.currentRemaining() == 0L) {
                     this.getNextByteBuffer();
                  }

                  int var2 = Math.min(var1, (int)this.currentRemaining());
                  int var3 = (int)(this.currentByteBufferPos - this.currentAddress);
                  var12.add(ByteString.wrap(this.slice(var3, var3 + var2)));
                  var1 -= var2;
                  this.currentByteBufferPos += var2;
               }

               return ByteString.copyFrom(var12);
            } else {
               byte[] var10 = new byte[var1];
               this.readRawBytesTo(var10, 0, var1);
               return ByteString.wrap(var10);
            }
         } else if (var1 == 0) {
            return ByteString.EMPTY;
         } else if (var1 < 0) {
            throw InvalidProtocolBufferException.negativeSize();
         } else {
            throw InvalidProtocolBufferException.truncatedMessage();
         }
      }

      @Override
      public double readDouble() throws IOException {
         return Double.longBitsToDouble(this.readRawLittleEndian64());
      }

      @Override
      public int readEnum() throws IOException {
         return this.readRawVarint32();
      }

      @Override
      public int readFixed32() throws IOException {
         return this.readRawLittleEndian32();
      }

      @Override
      public long readFixed64() throws IOException {
         return this.readRawLittleEndian64();
      }

      @Override
      public float readFloat() throws IOException {
         return Float.intBitsToFloat(this.readRawLittleEndian32());
      }

      @Override
      public <T extends MessageLite> T readGroup(int var1, Parser<T> var2, ExtensionRegistryLite var3) throws IOException {
         this.checkRecursionLimit();
         this.recursionDepth++;
         MessageLite var4 = (MessageLite)var2.parsePartialFrom(this, var3);
         this.checkLastTagWas(WireFormat.makeTag(var1, 4));
         this.recursionDepth--;
         return (T)var4;
      }

      @Override
      public void readGroup(int var1, MessageLite.Builder var2, ExtensionRegistryLite var3) throws IOException {
         this.checkRecursionLimit();
         this.recursionDepth++;
         var2.mergeFrom(this, var3);
         this.checkLastTagWas(WireFormat.makeTag(var1, 4));
         this.recursionDepth--;
      }

      @Override
      public int readInt32() throws IOException {
         return this.readRawVarint32();
      }

      @Override
      public long readInt64() throws IOException {
         return this.readRawVarint64();
      }

      @Override
      public <T extends MessageLite> T readMessage(Parser<T> var1, ExtensionRegistryLite var2) throws IOException {
         int var3 = this.readRawVarint32();
         this.checkRecursionLimit();
         var3 = this.pushLimit(var3);
         this.recursionDepth++;
         MessageLite var4 = (MessageLite)var1.parsePartialFrom(this, var2);
         this.checkLastTagWas(0);
         this.recursionDepth--;
         if (this.getBytesUntilLimit() == 0) {
            this.popLimit(var3);
            return (T)var4;
         } else {
            throw InvalidProtocolBufferException.truncatedMessage();
         }
      }

      @Override
      public void readMessage(MessageLite.Builder var1, ExtensionRegistryLite var2) throws IOException {
         int var3 = this.readRawVarint32();
         this.checkRecursionLimit();
         var3 = this.pushLimit(var3);
         this.recursionDepth++;
         var1.mergeFrom(this, var2);
         this.checkLastTagWas(0);
         this.recursionDepth--;
         if (this.getBytesUntilLimit() == 0) {
            this.popLimit(var3);
         } else {
            throw InvalidProtocolBufferException.truncatedMessage();
         }
      }

      @Override
      public byte readRawByte() throws IOException {
         if (this.currentRemaining() == 0L) {
            this.getNextByteBuffer();
         }

         long var1 = (long)(this.currentByteBufferPos++);
         return UnsafeUtil.getByte(var1);
      }

      @Override
      public byte[] readRawBytes(int var1) throws IOException {
         if (var1 >= 0) {
            long var2 = var1;
            if (var2 <= this.currentRemaining()) {
               byte[] var5 = new byte[var1];
               UnsafeUtil.copyMemory(this.currentByteBufferPos, var5, 0L, var2);
               this.currentByteBufferPos += var2;
               return var5;
            }
         }

         if (var1 >= 0 && var1 <= this.remaining()) {
            byte[] var4 = new byte[var1];
            this.readRawBytesTo(var4, 0, var1);
            return var4;
         } else if (var1 <= 0) {
            if (var1 == 0) {
               return Internal.EMPTY_BYTE_ARRAY;
            } else {
               throw InvalidProtocolBufferException.negativeSize();
            }
         } else {
            throw InvalidProtocolBufferException.truncatedMessage();
         }
      }

      @Override
      public int readRawLittleEndian32() throws IOException {
         if (this.currentRemaining() >= 4L) {
            long var4 = this.currentByteBufferPos;
            this.currentByteBufferPos = 4L + var4;
            byte var2 = UnsafeUtil.getByte(var4);
            byte var1 = UnsafeUtil.getByte(1L + var4);
            byte var3 = UnsafeUtil.getByte(2L + var4);
            return (UnsafeUtil.getByte(var4 + 3L) & 0xFF) << 24 | var2 & 0xFF | (var1 & 0xFF) << 8 | (var3 & 0xFF) << 16;
         } else {
            return this.readRawByte() & 0xFF | (this.readRawByte() & 0xFF) << 8 | (this.readRawByte() & 0xFF) << 16 | (this.readRawByte() & 0xFF) << 24;
         }
      }

      @Override
      public long readRawLittleEndian64() throws IOException {
         if (this.currentRemaining() >= 8L) {
            long var5 = this.currentByteBufferPos;
            this.currentByteBufferPos = 8L + var5;
            long var7 = UnsafeUtil.getByte(var5);
            long var1 = UnsafeUtil.getByte(1L + var5);
            long var13 = UnsafeUtil.getByte(2L + var5);
            long var9 = UnsafeUtil.getByte(3L + var5);
            long var11 = UnsafeUtil.getByte(4L + var5);
            long var3 = UnsafeUtil.getByte(5L + var5);
            long var15 = UnsafeUtil.getByte(6L + var5);
            return (UnsafeUtil.getByte(var5 + 7L) & 255L) << 56
               | (var13 & 255L) << 16
               | var7 & 255L
               | (var1 & 255L) << 8
               | (var9 & 255L) << 24
               | (var11 & 255L) << 32
               | (var3 & 255L) << 40
               | (var15 & 255L) << 48;
         } else {
            return this.readRawByte() & 255L
               | (this.readRawByte() & 255L) << 8
               | (this.readRawByte() & 255L) << 16
               | (this.readRawByte() & 255L) << 24
               | (this.readRawByte() & 255L) << 32
               | (this.readRawByte() & 255L) << 40
               | (this.readRawByte() & 255L) << 48
               | (this.readRawByte() & 255L) << 56;
         }
      }

      @Override
      public int readRawVarint32() throws IOException {
         long var9 = this.currentByteBufferPos;
         if (this.currentByteBufferLimit != var9) {
            int var1 = UnsafeUtil.getByte(var9);
            if (var1 >= 0) {
               this.currentByteBufferPos++;
               return var1;
            }

            if (this.currentByteBufferLimit - this.currentByteBufferPos >= 10L) {
               long var5 = 2L + var9;
               var1 = UnsafeUtil.getByte(var9 + 1L) << 7 ^ var1;
               long var3;
               if (var1 < 0) {
                  var1 ^= -128;
                  var3 = var5;
               } else {
                  var3 = 3L + var9;
                  var1 = UnsafeUtil.getByte(var5) << 14 ^ var1;
                  if (var1 >= 0) {
                     var1 ^= 16256;
                  } else {
                     long var7 = 4L + var9;
                     var1 ^= UnsafeUtil.getByte(var3) << 21;
                     if (var1 < 0) {
                        var1 = -2080896 ^ var1;
                        var3 = var7;
                     } else {
                        var5 = 5L + var9;
                        byte var2 = UnsafeUtil.getByte(var7);
                        var1 = var1 ^ var2 << 28 ^ 266354560;
                        var3 = var5;
                        if (var2 < 0) {
                           var7 = 6L + var9;
                           var3 = var7;
                           if (UnsafeUtil.getByte(var5) < 0) {
                              var5 = 7L + var9;
                              var3 = var5;
                              if (UnsafeUtil.getByte(var7) < 0) {
                                 var7 = 8L + var9;
                                 var3 = var7;
                                 if (UnsafeUtil.getByte(var5) < 0) {
                                    var5 = 9L + var9;
                                    var3 = var5;
                                    if (UnsafeUtil.getByte(var7) < 0) {
                                       if (UnsafeUtil.getByte(var5) < 0) {
                                          return (int)this.readRawVarint64SlowPath();
                                       }

                                       var3 = var9 + 10L;
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }

               this.currentByteBufferPos = var3;
               return var1;
            }
         }

         return (int)this.readRawVarint64SlowPath();
      }

      @Override
      public long readRawVarint64() throws IOException {
         long var10 = this.currentByteBufferPos;
         if (this.currentByteBufferLimit != var10) {
            int var1 = UnsafeUtil.getByte(var10);
            if (var1 >= 0) {
               this.currentByteBufferPos++;
               return var1;
            }

            if (this.currentByteBufferLimit - this.currentByteBufferPos >= 10L) {
               long var4 = 2L + var10;
               var1 = UnsafeUtil.getByte(var10 + 1L) << 7 ^ var1;
               long var17;
               if (var1 < 0) {
                  var17 = var1 ^ -128;
               } else {
                  var17 = 3L + var10;
                  var1 = UnsafeUtil.getByte(var4) << 14 ^ var1;
                  if (var1 >= 0) {
                     long var25 = var1 ^ 16256;
                     var4 = var17;
                     var17 = var25;
                  } else {
                     var4 = 4L + var10;
                     var1 ^= UnsafeUtil.getByte(var17) << 21;
                     if (var1 < 0) {
                        var17 = -2080896 ^ var1;
                     } else {
                        label75: {
                           long var6 = var1;
                           var17 = 5L + var10;
                           var6 = (long)UnsafeUtil.getByte(var4) << 28 ^ var6;
                           if (var6 >= 0L) {
                              var4 = 266354560L;
                           } else {
                              label72: {
                                 var4 = 6L + var10;
                                 var6 ^= (long)UnsafeUtil.getByte(var17) << 35;
                                 if (var6 < 0L) {
                                    long var8 = -34093383808L;
                                    var17 = var4;
                                    var4 = var8;
                                 } else {
                                    var17 = 7L + var10;
                                    var6 ^= (long)UnsafeUtil.getByte(var4) << 42;
                                    if (var6 >= 0L) {
                                       var4 = 4363953127296L;
                                       break label72;
                                    }

                                    long var26 = 8L + var10;
                                    var6 ^= (long)UnsafeUtil.getByte(var17) << 49;
                                    if (var6 >= 0L) {
                                       var4 = 9L + var10;
                                       var17 = var6 ^ (long)UnsafeUtil.getByte(var26) << 56 ^ 71499008037633920L;
                                       if (var17 < 0L) {
                                          if (UnsafeUtil.getByte(var4) < 0L) {
                                             return this.readRawVarint64SlowPath();
                                          }

                                          var4 = var10 + 10L;
                                       }
                                       break label75;
                                    }

                                    var4 = -558586000294016L;
                                    var17 = var26;
                                 }

                                 var6 = var4 ^ var6;
                                 var4 = var17;
                                 var17 = var6;
                                 break label75;
                              }
                           }

                           var6 = var4 ^ var6;
                           var4 = var17;
                           var17 = var6;
                        }
                     }
                  }
               }

               this.currentByteBufferPos = var4;
               return var17;
            }
         }

         return this.readRawVarint64SlowPath();
      }

      @Override
      long readRawVarint64SlowPath() throws IOException {
         long var3 = 0L;

         for (byte var1 = 0; var1 < 64; var1 += 7) {
            byte var2 = this.readRawByte();
            var3 |= (long)(var2 & 127) << var1;
            if ((var2 & 128) == 0) {
               return var3;
            }
         }

         throw InvalidProtocolBufferException.malformedVarint();
      }

      @Override
      public int readSFixed32() throws IOException {
         return this.readRawLittleEndian32();
      }

      @Override
      public long readSFixed64() throws IOException {
         return this.readRawLittleEndian64();
      }

      @Override
      public int readSInt32() throws IOException {
         return decodeZigZag32(this.readRawVarint32());
      }

      @Override
      public long readSInt64() throws IOException {
         return decodeZigZag64(this.readRawVarint64());
      }

      @Override
      public String readString() throws IOException {
         int var1 = this.readRawVarint32();
         if (var1 > 0) {
            long var6 = var1;
            long var2 = this.currentByteBufferLimit;
            long var4 = this.currentByteBufferPos;
            if (var6 <= var2 - var4) {
               byte[] var9 = new byte[var1];
               UnsafeUtil.copyMemory(var4, var9, 0L, var6);
               String var10 = new String(var9, Internal.UTF_8);
               this.currentByteBufferPos += var6;
               return var10;
            }
         }

         if (var1 > 0 && var1 <= this.remaining()) {
            byte[] var8 = new byte[var1];
            this.readRawBytesTo(var8, 0, var1);
            return new String(var8, Internal.UTF_8);
         } else if (var1 == 0) {
            return "";
         } else if (var1 < 0) {
            throw InvalidProtocolBufferException.negativeSize();
         } else {
            throw InvalidProtocolBufferException.truncatedMessage();
         }
      }

      @Override
      public String readStringRequireUtf8() throws IOException {
         int var1 = this.readRawVarint32();
         if (var1 > 0) {
            long var7 = var1;
            long var3 = this.currentByteBufferLimit;
            long var5 = this.currentByteBufferPos;
            if (var7 <= var3 - var5) {
               int var2 = (int)(var5 - this.currentByteBufferStartPos);
               String var10 = Utf8.decodeUtf8(this.currentByteBuffer, var2, var1);
               this.currentByteBufferPos += var7;
               return var10;
            }
         }

         if (var1 >= 0 && var1 <= this.remaining()) {
            byte[] var9 = new byte[var1];
            this.readRawBytesTo(var9, 0, var1);
            return Utf8.decodeUtf8(var9, 0, var1);
         } else if (var1 == 0) {
            return "";
         } else if (var1 <= 0) {
            throw InvalidProtocolBufferException.negativeSize();
         } else {
            throw InvalidProtocolBufferException.truncatedMessage();
         }
      }

      @Override
      public int readTag() throws IOException {
         if (this.isAtEnd()) {
            this.lastTag = 0;
            return 0;
         } else {
            int var1 = this.readRawVarint32();
            this.lastTag = var1;
            if (WireFormat.getTagFieldNumber(var1) != 0) {
               return this.lastTag;
            } else {
               throw InvalidProtocolBufferException.invalidTag();
            }
         }
      }

      @Override
      public int readUInt32() throws IOException {
         return this.readRawVarint32();
      }

      @Override
      public long readUInt64() throws IOException {
         return this.readRawVarint64();
      }

      @Deprecated
      @Override
      public void readUnknownGroup(int var1, MessageLite.Builder var2) throws IOException {
         this.readGroup(var1, var2, ExtensionRegistryLite.getEmptyRegistry());
      }

      @Override
      public void resetSizeCounter() {
         this.startOffset = (int)(this.totalBytesRead + this.currentByteBufferPos - this.currentByteBufferStartPos);
      }

      @Override
      public boolean skipField(int var1) throws IOException {
         int var2 = WireFormat.getTagWireType(var1);
         if (var2 != 0) {
            if (var2 != 1) {
               if (var2 != 2) {
                  if (var2 != 3) {
                     if (var2 != 4) {
                        if (var2 == 5) {
                           this.skipRawBytes(4);
                           return true;
                        } else {
                           throw InvalidProtocolBufferException.invalidWireType();
                        }
                     } else {
                        return false;
                     }
                  } else {
                     this.skipMessage();
                     this.checkLastTagWas(WireFormat.makeTag(WireFormat.getTagFieldNumber(var1), 4));
                     return true;
                  }
               } else {
                  this.skipRawBytes(this.readRawVarint32());
                  return true;
               }
            } else {
               this.skipRawBytes(8);
               return true;
            }
         } else {
            this.skipRawVarint();
            return true;
         }
      }

      @Override
      public boolean skipField(int var1, CodedOutputStream var2) throws IOException {
         int var3 = WireFormat.getTagWireType(var1);
         if (var3 != 0) {
            if (var3 != 1) {
               if (var3 != 2) {
                  if (var3 != 3) {
                     if (var3 != 4) {
                        if (var3 == 5) {
                           var3 = this.readRawLittleEndian32();
                           var2.writeUInt32NoTag(var1);
                           var2.writeFixed32NoTag(var3);
                           return true;
                        } else {
                           throw InvalidProtocolBufferException.invalidWireType();
                        }
                     } else {
                        return false;
                     }
                  } else {
                     var2.writeUInt32NoTag(var1);
                     this.skipMessage(var2);
                     var1 = WireFormat.makeTag(WireFormat.getTagFieldNumber(var1), 4);
                     this.checkLastTagWas(var1);
                     var2.writeUInt32NoTag(var1);
                     return true;
                  }
               } else {
                  ByteString var6 = this.readBytes();
                  var2.writeUInt32NoTag(var1);
                  var2.writeBytesNoTag(var6);
                  return true;
               }
            } else {
               long var9 = this.readRawLittleEndian64();
               var2.writeUInt32NoTag(var1);
               var2.writeFixed64NoTag(var9);
               return true;
            }
         } else {
            long var4 = this.readInt64();
            var2.writeUInt32NoTag(var1);
            var2.writeUInt64NoTag(var4);
            return true;
         }
      }

      @Override
      public void skipMessage() throws IOException {
         int var1;
         do {
            var1 = this.readTag();
         } while (var1 != 0 && this.skipField(var1));
      }

      @Override
      public void skipMessage(CodedOutputStream var1) throws IOException {
         int var2;
         do {
            var2 = this.readTag();
         } while (var2 != 0 && this.skipField(var2, var1));
      }

      @Override
      public void skipRawBytes(int var1) throws IOException {
         if (var1 >= 0 && var1 <= this.totalBufferSize - this.totalBytesRead - this.currentByteBufferPos + this.currentByteBufferStartPos) {
            while (var1 > 0) {
               if (this.currentRemaining() == 0L) {
                  this.getNextByteBuffer();
               }

               int var2 = Math.min(var1, (int)this.currentRemaining());
               var1 -= var2;
               this.currentByteBufferPos += var2;
            }
         } else if (var1 < 0) {
            throw InvalidProtocolBufferException.negativeSize();
         } else {
            throw InvalidProtocolBufferException.truncatedMessage();
         }
      }
   }

   private static final class StreamDecoder extends CodedInputStream {
      private final byte[] buffer;
      private int bufferSize;
      private int bufferSizeAfterLimit;
      private int currentLimit = Integer.MAX_VALUE;
      private final InputStream input;
      private int lastTag;
      private int pos;
      private CodedInputStream.StreamDecoder.RefillCallback refillCallback = null;
      private int totalBytesRetired;

      private StreamDecoder(InputStream var1, int var2) {
         Internal.checkNotNull(var1, "input");
         this.input = var1;
         this.buffer = new byte[var2];
         this.bufferSize = 0;
         this.pos = 0;
         this.totalBytesRetired = 0;
      }

      private static int available(InputStream var0) throws IOException {
         try {
            return var0.available();
         } catch (InvalidProtocolBufferException var2) {
            var2.setThrownFromInputStream();
            throw var2;
         }
      }

      private static int read(InputStream var0, byte[] var1, int var2, int var3) throws IOException {
         try {
            return var0.read(var1, var2, var3);
         } catch (InvalidProtocolBufferException var4) {
            var4.setThrownFromInputStream();
            throw var4;
         }
      }

      private ByteString readBytesSlowPath(int var1) throws IOException {
         byte[] var5 = this.readRawBytesSlowPathOneChunk(var1);
         if (var5 != null) {
            return ByteString.copyFrom(var5);
         } else {
            int var3 = this.pos;
            int var4 = this.bufferSize;
            int var2 = var4 - var3;
            this.totalBytesRetired += var4;
            this.pos = 0;
            this.bufferSize = 0;
            List var6 = this.readRawBytesSlowPathRemainingChunks(var1 - var2);
            var5 = new byte[var1];
            System.arraycopy(this.buffer, var3, var5, 0, var2);
            Iterator var7 = var6.iterator();
            var1 = var2;

            while (var7.hasNext()) {
               byte[] var10 = (byte[])var7.next();
               System.arraycopy(var10, 0, var5, var1, var10.length);
               var1 += var10.length;
            }

            return ByteString.wrap(var5);
         }
      }

      private byte[] readRawBytesSlowPath(int var1, boolean var2) throws IOException {
         byte[] var7 = this.readRawBytesSlowPathOneChunk(var1);
         if (var7 != null) {
            byte[] var10 = var7;
            if (var2) {
               var10 = (byte[])var7.clone();
            }

            return var10;
         } else {
            int var4 = this.pos;
            int var5 = this.bufferSize;
            int var3 = var5 - var4;
            this.totalBytesRetired += var5;
            this.pos = 0;
            this.bufferSize = 0;
            List var11 = this.readRawBytesSlowPathRemainingChunks(var1 - var3);
            byte[] var6 = new byte[var1];
            System.arraycopy(this.buffer, var4, var6, 0, var3);
            Iterator var12 = var11.iterator();
            var1 = var3;

            while (var12.hasNext()) {
               byte[] var8 = (byte[])var12.next();
               System.arraycopy(var8, 0, var6, var1, var8.length);
               var1 += var8.length;
            }

            return var6;
         }
      }

      private byte[] readRawBytesSlowPathOneChunk(int var1) throws IOException {
         if (var1 == 0) {
            return Internal.EMPTY_BYTE_ARRAY;
         } else if (var1 >= 0) {
            int var3 = this.totalBytesRetired + this.pos + var1;
            if (var3 - this.sizeLimit <= 0) {
               int var2 = this.currentLimit;
               if (var3 <= var2) {
                  var2 = this.bufferSize - this.pos;
                  var3 = var1 - var2;
                  if (var3 >= 4096 && var3 > available(this.input)) {
                     return null;
                  } else {
                     byte[] var4 = new byte[var1];
                     System.arraycopy(this.buffer, this.pos, var4, 0, var2);
                     this.totalBytesRetired = this.totalBytesRetired + this.bufferSize;
                     this.pos = 0;
                     this.bufferSize = 0;

                     while (var2 < var1) {
                        var3 = read(this.input, var4, var2, var1 - var2);
                        if (var3 == -1) {
                           throw InvalidProtocolBufferException.truncatedMessage();
                        }

                        this.totalBytesRetired += var3;
                        var2 += var3;
                     }

                     return var4;
                  }
               } else {
                  this.skipRawBytes(var2 - this.totalBytesRetired - this.pos);
                  throw InvalidProtocolBufferException.truncatedMessage();
               }
            } else {
               throw InvalidProtocolBufferException.sizeLimitExceeded();
            }
         } else {
            throw InvalidProtocolBufferException.negativeSize();
         }
      }

      private List<byte[]> readRawBytesSlowPathRemainingChunks(int var1) throws IOException {
         ArrayList var5 = new ArrayList();

         while (var1 > 0) {
            int var3 = Math.min(var1, 4096);
            byte[] var6 = new byte[var3];
            int var2 = 0;

            while (var2 < var3) {
               int var4 = this.input.read(var6, var2, var3 - var2);
               if (var4 == -1) {
                  throw InvalidProtocolBufferException.truncatedMessage();
               }

               this.totalBytesRetired += var4;
               var2 += var4;
            }

            var1 -= var3;
            var5.add(var6);
         }

         return var5;
      }

      private void recomputeBufferSizeAfterLimit() {
         int var1 = this.bufferSize + this.bufferSizeAfterLimit;
         this.bufferSize = var1;
         int var2 = this.totalBytesRetired + var1;
         int var3 = this.currentLimit;
         if (var2 > var3) {
            var2 -= var3;
            this.bufferSizeAfterLimit = var2;
            this.bufferSize = var1 - var2;
         } else {
            this.bufferSizeAfterLimit = 0;
         }
      }

      private void refillBuffer(int var1) throws IOException {
         if (!this.tryRefillBuffer(var1)) {
            if (var1 > this.sizeLimit - this.totalBytesRetired - this.pos) {
               throw InvalidProtocolBufferException.sizeLimitExceeded();
            } else {
               throw InvalidProtocolBufferException.truncatedMessage();
            }
         }
      }

      private static long skip(InputStream var0, long var1) throws IOException {
         try {
            return var0.skip(var1);
         } catch (InvalidProtocolBufferException var3) {
            var3.setThrownFromInputStream();
            throw var3;
         }
      }

      private void skipRawBytesSlowPath(int param1) throws IOException {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 0
         //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
         //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
         //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
         //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
         //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1064)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:565)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
         //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
         //
         // Bytecode:
         // 000: iload 1
         // 001: iflt 134
         // 004: aload 0
         // 005: getfield com/google/protobuf/CodedInputStream$StreamDecoder.totalBytesRetired I
         // 008: istore 4
         // 00a: aload 0
         // 00b: getfield com/google/protobuf/CodedInputStream$StreamDecoder.pos I
         // 00e: istore 3
         // 00f: aload 0
         // 010: getfield com/google/protobuf/CodedInputStream$StreamDecoder.currentLimit I
         // 013: istore 2
         // 014: iload 4
         // 016: iload 3
         // 017: iadd
         // 018: iload 1
         // 019: iadd
         // 01a: iload 2
         // 01b: if_icmpgt 126
         // 01e: aload 0
         // 01f: getfield com/google/protobuf/CodedInputStream$StreamDecoder.refillCallback Lcom/google/protobuf/CodedInputStream$StreamDecoder$RefillCallback;
         // 022: astore 9
         // 024: bipush 0
         // 025: istore 2
         // 026: aload 9
         // 028: ifnonnull 0e3
         // 02b: aload 0
         // 02c: iload 4
         // 02e: iload 3
         // 02f: iadd
         // 030: putfield com/google/protobuf/CodedInputStream$StreamDecoder.totalBytesRetired I
         // 033: aload 0
         // 034: getfield com/google/protobuf/CodedInputStream$StreamDecoder.bufferSize I
         // 037: istore 2
         // 038: aload 0
         // 039: bipush 0
         // 03a: putfield com/google/protobuf/CodedInputStream$StreamDecoder.bufferSize I
         // 03d: aload 0
         // 03e: bipush 0
         // 03f: putfield com/google/protobuf/CodedInputStream$StreamDecoder.pos I
         // 042: iload 2
         // 043: iload 3
         // 044: isub
         // 045: istore 2
         // 046: iload 2
         // 047: iload 1
         // 048: if_icmpge 0d5
         // 04b: aload 0
         // 04c: getfield com/google/protobuf/CodedInputStream$StreamDecoder.input Ljava/io/InputStream;
         // 04f: astore 9
         // 051: iload 1
         // 052: iload 2
         // 053: isub
         // 054: i2l
         // 055: lstore 5
         // 057: aload 9
         // 059: lload 5
         // 05b: invokestatic com/google/protobuf/CodedInputStream$StreamDecoder.skip (Ljava/io/InputStream;J)J
         // 05e: lstore 7
         // 060: lload 7
         // 062: lconst_0
         // 063: lcmp
         // 064: istore 3
         // 065: iload 3
         // 066: iflt 081
         // 069: lload 7
         // 06b: lload 5
         // 06d: lcmp
         // 06e: ifgt 081
         // 071: iload 3
         // 072: ifne 078
         // 075: goto 0d5
         // 078: iload 2
         // 079: lload 7
         // 07b: l2i
         // 07c: iadd
         // 07d: istore 2
         // 07e: goto 046
         // 081: new java/lang/IllegalStateException
         // 084: astore 10
         // 086: new java/lang/StringBuilder
         // 089: astore 9
         // 08b: aload 9
         // 08d: invokespecial java/lang/StringBuilder.<init> ()V
         // 090: aload 9
         // 092: aload 0
         // 093: getfield com/google/protobuf/CodedInputStream$StreamDecoder.input Ljava/io/InputStream;
         // 096: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
         // 099: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
         // 09c: pop
         // 09d: aload 9
         // 09f: ldc "#skip returned invalid result: "
         // 0a1: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
         // 0a4: pop
         // 0a5: aload 9
         // 0a7: lload 7
         // 0a9: invokevirtual java/lang/StringBuilder.append (J)Ljava/lang/StringBuilder;
         // 0ac: pop
         // 0ad: aload 9
         // 0af: ldc "\nThe InputStream implementation is buggy."
         // 0b1: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
         // 0b4: pop
         // 0b5: aload 10
         // 0b7: aload 9
         // 0b9: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
         // 0bc: invokespecial java/lang/IllegalStateException.<init> (Ljava/lang/String;)V
         // 0bf: aload 10
         // 0c1: athrow
         // 0c2: astore 9
         // 0c4: aload 0
         // 0c5: aload 0
         // 0c6: getfield com/google/protobuf/CodedInputStream$StreamDecoder.totalBytesRetired I
         // 0c9: iload 2
         // 0ca: iadd
         // 0cb: putfield com/google/protobuf/CodedInputStream$StreamDecoder.totalBytesRetired I
         // 0ce: aload 0
         // 0cf: invokespecial com/google/protobuf/CodedInputStream$StreamDecoder.recomputeBufferSizeAfterLimit ()V
         // 0d2: aload 9
         // 0d4: athrow
         // 0d5: aload 0
         // 0d6: aload 0
         // 0d7: getfield com/google/protobuf/CodedInputStream$StreamDecoder.totalBytesRetired I
         // 0da: iload 2
         // 0db: iadd
         // 0dc: putfield com/google/protobuf/CodedInputStream$StreamDecoder.totalBytesRetired I
         // 0df: aload 0
         // 0e0: invokespecial com/google/protobuf/CodedInputStream$StreamDecoder.recomputeBufferSizeAfterLimit ()V
         // 0e3: iload 2
         // 0e4: iload 1
         // 0e5: if_icmpge 125
         // 0e8: aload 0
         // 0e9: getfield com/google/protobuf/CodedInputStream$StreamDecoder.bufferSize I
         // 0ec: istore 3
         // 0ed: iload 3
         // 0ee: aload 0
         // 0ef: getfield com/google/protobuf/CodedInputStream$StreamDecoder.pos I
         // 0f2: isub
         // 0f3: istore 2
         // 0f4: aload 0
         // 0f5: iload 3
         // 0f6: putfield com/google/protobuf/CodedInputStream$StreamDecoder.pos I
         // 0f9: aload 0
         // 0fa: bipush 1
         // 0fb: invokespecial com/google/protobuf/CodedInputStream$StreamDecoder.refillBuffer (I)V
         // 0fe: iload 1
         // 0ff: iload 2
         // 100: isub
         // 101: istore 4
         // 103: aload 0
         // 104: getfield com/google/protobuf/CodedInputStream$StreamDecoder.bufferSize I
         // 107: istore 3
         // 108: iload 4
         // 10a: iload 3
         // 10b: if_icmple 11f
         // 10e: iload 2
         // 10f: iload 3
         // 110: iadd
         // 111: istore 2
         // 112: aload 0
         // 113: iload 3
         // 114: putfield com/google/protobuf/CodedInputStream$StreamDecoder.pos I
         // 117: aload 0
         // 118: bipush 1
         // 119: invokespecial com/google/protobuf/CodedInputStream$StreamDecoder.refillBuffer (I)V
         // 11c: goto 0fe
         // 11f: aload 0
         // 120: iload 4
         // 122: putfield com/google/protobuf/CodedInputStream$StreamDecoder.pos I
         // 125: return
         // 126: aload 0
         // 127: iload 2
         // 128: iload 4
         // 12a: isub
         // 12b: iload 3
         // 12c: isub
         // 12d: invokevirtual com/google/protobuf/CodedInputStream$StreamDecoder.skipRawBytes (I)V
         // 130: invokestatic com/google/protobuf/InvalidProtocolBufferException.truncatedMessage ()Lcom/google/protobuf/InvalidProtocolBufferException;
         // 133: athrow
         // 134: invokestatic com/google/protobuf/InvalidProtocolBufferException.negativeSize ()Lcom/google/protobuf/InvalidProtocolBufferException;
         // 137: athrow
      }

      private void skipRawVarint() throws IOException {
         if (this.bufferSize - this.pos >= 10) {
            this.skipRawVarintFastPath();
         } else {
            this.skipRawVarintSlowPath();
         }
      }

      private void skipRawVarintFastPath() throws IOException {
         for (int var1 = 0; var1 < 10; var1++) {
            byte[] var3 = this.buffer;
            int var2 = this.pos++;
            if (var3[var2] >= 0) {
               return;
            }
         }

         throw InvalidProtocolBufferException.malformedVarint();
      }

      private void skipRawVarintSlowPath() throws IOException {
         for (int var1 = 0; var1 < 10; var1++) {
            if (this.readRawByte() >= 0) {
               return;
            }
         }

         throw InvalidProtocolBufferException.malformedVarint();
      }

      private boolean tryRefillBuffer(int var1) throws IOException {
         if (this.pos + var1 > this.bufferSize) {
            int var4 = this.sizeLimit;
            int var3 = this.totalBytesRetired;
            int var2 = this.pos;
            if (var1 > var4 - var3 - var2) {
               return false;
            } else if (var3 + var2 + var1 > this.currentLimit) {
               return false;
            } else {
               CodedInputStream.StreamDecoder.RefillCallback var12 = this.refillCallback;
               if (var12 != null) {
                  var12.onRefill();
               }

               var3 = this.pos;
               if (var3 > 0) {
                  var2 = this.bufferSize;
                  if (var2 > var3) {
                     byte[] var13 = this.buffer;
                     System.arraycopy(var13, var3, var13, 0, var2 - var3);
                  }

                  this.totalBytesRetired += var3;
                  this.bufferSize -= var3;
                  this.pos = 0;
               }

               InputStream var7 = this.input;
               byte[] var14 = this.buffer;
               var2 = this.bufferSize;
               var2 = read(var7, var14, var2, Math.min(var14.length - var2, this.sizeLimit - this.totalBytesRetired - this.bufferSize));
               if (var2 == 0 || var2 < -1 || var2 > this.buffer.length) {
                  StringBuilder var15 = new StringBuilder();
                  var15.append(this.input.getClass());
                  var15.append("#read(byte[]) returned invalid result: ");
                  var15.append(var2);
                  var15.append("\nThe InputStream implementation is buggy.");
                  throw new IllegalStateException(var15.toString());
               } else if (var2 > 0) {
                  this.bufferSize += var2;
                  this.recomputeBufferSizeAfterLimit();
                  boolean var5;
                  if (this.bufferSize >= var1) {
                     var5 = true;
                  } else {
                     var5 = this.tryRefillBuffer(var1);
                  }

                  return var5;
               } else {
                  return false;
               }
            }
         } else {
            StringBuilder var6 = new StringBuilder("refillBuffer() called when ");
            var6.append(var1);
            var6.append(" bytes were already available in buffer");
            throw new IllegalStateException(var6.toString());
         }
      }

      @Override
      public void checkLastTagWas(int var1) throws InvalidProtocolBufferException {
         if (this.lastTag != var1) {
            throw InvalidProtocolBufferException.invalidEndTag();
         }
      }

      @Override
      public void enableAliasing(boolean var1) {
      }

      @Override
      public int getBytesUntilLimit() {
         int var1 = this.currentLimit;
         return var1 == Integer.MAX_VALUE ? -1 : var1 - (this.totalBytesRetired + this.pos);
      }

      @Override
      public int getLastTag() {
         return this.lastTag;
      }

      @Override
      public int getTotalBytesRead() {
         return this.totalBytesRetired + this.pos;
      }

      @Override
      public boolean isAtEnd() throws IOException {
         if (this.pos == this.bufferSize) {
            boolean var1 = true;
            if (!this.tryRefillBuffer(1)) {
               return var1;
            }
         }

         return false;
      }

      @Override
      public void popLimit(int var1) {
         this.currentLimit = var1;
         this.recomputeBufferSizeAfterLimit();
      }

      @Override
      public int pushLimit(int var1) throws InvalidProtocolBufferException {
         if (var1 >= 0) {
            var1 += this.totalBytesRetired + this.pos;
            int var2 = this.currentLimit;
            if (var1 <= var2) {
               this.currentLimit = var1;
               this.recomputeBufferSizeAfterLimit();
               return var2;
            } else {
               throw InvalidProtocolBufferException.truncatedMessage();
            }
         } else {
            throw InvalidProtocolBufferException.negativeSize();
         }
      }

      @Override
      public boolean readBool() throws IOException {
         boolean var1;
         if (this.readRawVarint64() != 0L) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      @Override
      public byte[] readByteArray() throws IOException {
         int var1 = this.readRawVarint32();
         int var2 = this.bufferSize;
         int var3 = this.pos;
         if (var1 <= var2 - var3 && var1 > 0) {
            byte[] var4 = Arrays.copyOfRange(this.buffer, var3, var3 + var1);
            this.pos += var1;
            return var4;
         } else {
            return this.readRawBytesSlowPath(var1, false);
         }
      }

      @Override
      public ByteBuffer readByteBuffer() throws IOException {
         int var1 = this.readRawVarint32();
         int var3 = this.bufferSize;
         int var2 = this.pos;
         if (var1 <= var3 - var2 && var1 > 0) {
            ByteBuffer var4 = ByteBuffer.wrap(Arrays.copyOfRange(this.buffer, var2, var2 + var1));
            this.pos += var1;
            return var4;
         } else {
            return var1 == 0 ? Internal.EMPTY_BYTE_BUFFER : ByteBuffer.wrap(this.readRawBytesSlowPath(var1, true));
         }
      }

      @Override
      public ByteString readBytes() throws IOException {
         int var2 = this.readRawVarint32();
         int var3 = this.bufferSize;
         int var1 = this.pos;
         if (var2 <= var3 - var1 && var2 > 0) {
            ByteString var4 = ByteString.copyFrom(this.buffer, var1, var2);
            this.pos += var2;
            return var4;
         } else {
            return var2 == 0 ? ByteString.EMPTY : this.readBytesSlowPath(var2);
         }
      }

      @Override
      public double readDouble() throws IOException {
         return Double.longBitsToDouble(this.readRawLittleEndian64());
      }

      @Override
      public int readEnum() throws IOException {
         return this.readRawVarint32();
      }

      @Override
      public int readFixed32() throws IOException {
         return this.readRawLittleEndian32();
      }

      @Override
      public long readFixed64() throws IOException {
         return this.readRawLittleEndian64();
      }

      @Override
      public float readFloat() throws IOException {
         return Float.intBitsToFloat(this.readRawLittleEndian32());
      }

      @Override
      public <T extends MessageLite> T readGroup(int var1, Parser<T> var2, ExtensionRegistryLite var3) throws IOException {
         this.checkRecursionLimit();
         this.recursionDepth++;
         MessageLite var4 = (MessageLite)var2.parsePartialFrom(this, var3);
         this.checkLastTagWas(WireFormat.makeTag(var1, 4));
         this.recursionDepth--;
         return (T)var4;
      }

      @Override
      public void readGroup(int var1, MessageLite.Builder var2, ExtensionRegistryLite var3) throws IOException {
         this.checkRecursionLimit();
         this.recursionDepth++;
         var2.mergeFrom(this, var3);
         this.checkLastTagWas(WireFormat.makeTag(var1, 4));
         this.recursionDepth--;
      }

      @Override
      public int readInt32() throws IOException {
         return this.readRawVarint32();
      }

      @Override
      public long readInt64() throws IOException {
         return this.readRawVarint64();
      }

      @Override
      public <T extends MessageLite> T readMessage(Parser<T> var1, ExtensionRegistryLite var2) throws IOException {
         int var3 = this.readRawVarint32();
         this.checkRecursionLimit();
         var3 = this.pushLimit(var3);
         this.recursionDepth++;
         MessageLite var4 = (MessageLite)var1.parsePartialFrom(this, var2);
         this.checkLastTagWas(0);
         this.recursionDepth--;
         if (this.getBytesUntilLimit() == 0) {
            this.popLimit(var3);
            return (T)var4;
         } else {
            throw InvalidProtocolBufferException.truncatedMessage();
         }
      }

      @Override
      public void readMessage(MessageLite.Builder var1, ExtensionRegistryLite var2) throws IOException {
         int var3 = this.readRawVarint32();
         this.checkRecursionLimit();
         var3 = this.pushLimit(var3);
         this.recursionDepth++;
         var1.mergeFrom(this, var2);
         this.checkLastTagWas(0);
         this.recursionDepth--;
         if (this.getBytesUntilLimit() == 0) {
            this.popLimit(var3);
         } else {
            throw InvalidProtocolBufferException.truncatedMessage();
         }
      }

      @Override
      public byte readRawByte() throws IOException {
         if (this.pos == this.bufferSize) {
            this.refillBuffer(1);
         }

         byte[] var2 = this.buffer;
         int var1 = this.pos++;
         return var2[var1];
      }

      @Override
      public byte[] readRawBytes(int var1) throws IOException {
         int var2 = this.pos;
         if (var1 <= this.bufferSize - var2 && var1 > 0) {
            var1 += var2;
            this.pos = var1;
            return Arrays.copyOfRange(this.buffer, var2, var1);
         } else {
            return this.readRawBytesSlowPath(var1, false);
         }
      }

      @Override
      public int readRawLittleEndian32() throws IOException {
         int var2 = this.pos;
         int var1 = var2;
         if (this.bufferSize - var2 < 4) {
            this.refillBuffer(4);
            var1 = this.pos;
         }

         byte[] var5 = this.buffer;
         this.pos = var1 + 4;
         byte var4 = var5[var1];
         byte var3 = var5[var1 + 1];
         byte var6 = var5[var1 + 2];
         return (var5[var1 + 3] & 0xFF) << 24 | var4 & 0xFF | (var3 & 0xFF) << 8 | (var6 & 0xFF) << 16;
      }

      @Override
      public long readRawLittleEndian64() throws IOException {
         int var2 = this.pos;
         int var1 = var2;
         if (this.bufferSize - var2 < 8) {
            this.refillBuffer(8);
            var1 = this.pos;
         }

         byte[] var17 = this.buffer;
         this.pos = var1 + 8;
         long var3 = var17[var1];
         long var9 = var17[var1 + 1];
         long var15 = var17[var1 + 2];
         long var11 = var17[var1 + 3];
         long var7 = var17[var1 + 4];
         long var13 = var17[var1 + 5];
         long var5 = var17[var1 + 6];
         return (var17[var1 + 7] & 255L) << 56
            | var3 & 255L
            | (var9 & 255L) << 8
            | (var15 & 255L) << 16
            | (var11 & 255L) << 24
            | (var7 & 255L) << 32
            | (var13 & 255L) << 40
            | (var5 & 255L) << 48;
      }

      @Override
      public int readRawVarint32() throws IOException {
         int var6 = this.pos;
         int var2 = this.bufferSize;
         if (var2 != var6) {
            byte[] var7 = this.buffer;
            int var3 = var6 + 1;
            int var1 = var7[var6];
            if (var1 >= 0) {
               this.pos = var3;
               return var1;
            }

            if (var2 - var3 >= 9) {
               var2 = var6 + 2;
               var3 = var7[var3] << 7 ^ var1;
               if (var3 < 0) {
                  var1 = var3 ^ -128;
               } else {
                  label66: {
                     var1 = var6 + 3;
                     var3 = var7[var2] << 14 ^ var3;
                     if (var3 >= 0) {
                        var2 = var3 ^ 16256;
                     } else {
                        label63: {
                           var2 = var6 + 4;
                           var1 = var3 ^ var7[var1] << 21;
                           if (var1 < 0) {
                              var3 = -2080896 ^ var1;
                              var1 = var2;
                              var2 = var3;
                           } else {
                              var3 = var6 + 5;
                              int var4 = var7[var2];
                              var2 = var1 ^ var4 << 28 ^ 266354560;
                              var1 = var3;
                              if (var4 >= 0) {
                                 break label63;
                              }

                              var4 = var6 + 6;
                              var1 = var4;
                              if (var7[var3] < 0) {
                                 int var5 = var6 + 7;
                                 var1 = var5;
                                 if (var7[var4] >= 0) {
                                    break label63;
                                 }

                                 var3 = var6 + 8;
                                 var1 = var3;
                                 if (var7[var5] < 0) {
                                    var4 = var6 + 9;
                                    var1 = var4;
                                    if (var7[var3] < 0) {
                                       if (var7[var4] < 0) {
                                          return (int)this.readRawVarint64SlowPath();
                                       }

                                       var3 = var6 + 10;
                                       var1 = var2;
                                       var2 = var3;
                                       break label66;
                                    }
                                    break label63;
                                 }
                              }
                           }

                           var1 = var2;
                           var2 = var1;
                           break label66;
                        }
                     }

                     var1 = var2;
                     var2 = var1;
                  }
               }

               this.pos = var2;
               return var1;
            }
         }

         return (int)this.readRawVarint64SlowPath();
      }

      @Override
      public long readRawVarint64() throws IOException {
         int var3 = this.pos;
         int var1 = this.bufferSize;
         if (var1 != var3) {
            byte[] var9 = this.buffer;
            int var4 = var3 + 1;
            int var2 = var9[var3];
            if (var2 >= 0) {
               this.pos = var4;
               return var2;
            }

            if (var1 - var4 >= 9) {
               var1 = var3 + 2;
               var4 = var9[var4] << 7 ^ var2;
               long var20;
               if (var4 < 0) {
                  var20 = var4 ^ -128;
               } else {
                  var2 = var3 + 3;
                  var4 = var9[var1] << 14 ^ var4;
                  if (var4 >= 0) {
                     var20 = var4 ^ 16256;
                     var1 = var2;
                  } else {
                     var1 = var3 + 4;
                     var2 = var4 ^ var9[var2] << 21;
                     if (var2 < 0) {
                        var20 = -2080896 ^ var2;
                     } else {
                        label73: {
                           var20 = var2;
                           var2 = var3 + 5;
                           var20 ^= (long)var9[var1] << 28;
                           long var21;
                           if (var20 >= 0L) {
                              var21 = 266354560L;
                              var1 = var2;
                           } else {
                              label70: {
                                 var1 = var3 + 6;
                                 var20 ^= (long)var9[var2] << 35;
                                 if (var20 < 0L) {
                                    var21 = -34093383808L;
                                 } else {
                                    var2 = var3 + 7;
                                    var20 ^= (long)var9[var1] << 42;
                                    if (var20 >= 0L) {
                                       var21 = 4363953127296L;
                                       var1 = var2;
                                       break label70;
                                    }

                                    var1 = var3 + 8;
                                    var20 ^= (long)var9[var2] << 49;
                                    if (var20 >= 0L) {
                                       var2 = var3 + 9;
                                       var20 = var20 ^ (long)var9[var1] << 56 ^ 71499008037633920L;
                                       var1 = var2;
                                       if (var20 < 0L) {
                                          if (var9[var2] < 0L) {
                                             return this.readRawVarint64SlowPath();
                                          }

                                          var1 = var3 + 10;
                                       }
                                       break label73;
                                    }

                                    var21 = -558586000294016L;
                                 }

                                 var20 ^= var21;
                                 break label73;
                              }
                           }

                           var20 ^= var21;
                        }
                     }
                  }
               }

               this.pos = var1;
               return var20;
            }
         }

         return this.readRawVarint64SlowPath();
      }

      @Override
      long readRawVarint64SlowPath() throws IOException {
         long var3 = 0L;

         for (byte var1 = 0; var1 < 64; var1 += 7) {
            byte var2 = this.readRawByte();
            var3 |= (long)(var2 & 127) << var1;
            if ((var2 & 128) == 0) {
               return var3;
            }
         }

         throw InvalidProtocolBufferException.malformedVarint();
      }

      @Override
      public int readSFixed32() throws IOException {
         return this.readRawLittleEndian32();
      }

      @Override
      public long readSFixed64() throws IOException {
         return this.readRawLittleEndian64();
      }

      @Override
      public int readSInt32() throws IOException {
         return decodeZigZag32(this.readRawVarint32());
      }

      @Override
      public long readSInt64() throws IOException {
         return decodeZigZag64(this.readRawVarint64());
      }

      @Override
      public String readString() throws IOException {
         int var1 = this.readRawVarint32();
         if (var1 > 0 && var1 <= this.bufferSize - this.pos) {
            String var3 = new String(this.buffer, this.pos, var1, Internal.UTF_8);
            this.pos += var1;
            return var3;
         } else if (var1 == 0) {
            return "";
         } else if (var1 <= this.bufferSize) {
            this.refillBuffer(var1);
            String var2 = new String(this.buffer, this.pos, var1, Internal.UTF_8);
            this.pos += var1;
            return var2;
         } else {
            return new String(this.readRawBytesSlowPath(var1, false), Internal.UTF_8);
         }
      }

      @Override
      public String readStringRequireUtf8() throws IOException {
         int var2 = this.readRawVarint32();
         int var1 = this.pos;
         int var3 = this.bufferSize;
         byte[] var4;
         if (var2 <= var3 - var1 && var2 > 0) {
            var4 = this.buffer;
            this.pos = var1 + var2;
         } else {
            if (var2 == 0) {
               return "";
            }

            var1 = 0;
            if (var2 <= var3) {
               this.refillBuffer(var2);
               var4 = this.buffer;
               this.pos = var2;
            } else {
               var4 = this.readRawBytesSlowPath(var2, false);
            }
         }

         return Utf8.decodeUtf8(var4, var1, var2);
      }

      @Override
      public int readTag() throws IOException {
         if (this.isAtEnd()) {
            this.lastTag = 0;
            return 0;
         } else {
            int var1 = this.readRawVarint32();
            this.lastTag = var1;
            if (WireFormat.getTagFieldNumber(var1) != 0) {
               return this.lastTag;
            } else {
               throw InvalidProtocolBufferException.invalidTag();
            }
         }
      }

      @Override
      public int readUInt32() throws IOException {
         return this.readRawVarint32();
      }

      @Override
      public long readUInt64() throws IOException {
         return this.readRawVarint64();
      }

      @Deprecated
      @Override
      public void readUnknownGroup(int var1, MessageLite.Builder var2) throws IOException {
         this.readGroup(var1, var2, ExtensionRegistryLite.getEmptyRegistry());
      }

      @Override
      public void resetSizeCounter() {
         this.totalBytesRetired = -this.pos;
      }

      @Override
      public boolean skipField(int var1) throws IOException {
         int var2 = WireFormat.getTagWireType(var1);
         if (var2 != 0) {
            if (var2 != 1) {
               if (var2 != 2) {
                  if (var2 != 3) {
                     if (var2 != 4) {
                        if (var2 == 5) {
                           this.skipRawBytes(4);
                           return true;
                        } else {
                           throw InvalidProtocolBufferException.invalidWireType();
                        }
                     } else {
                        return false;
                     }
                  } else {
                     this.skipMessage();
                     this.checkLastTagWas(WireFormat.makeTag(WireFormat.getTagFieldNumber(var1), 4));
                     return true;
                  }
               } else {
                  this.skipRawBytes(this.readRawVarint32());
                  return true;
               }
            } else {
               this.skipRawBytes(8);
               return true;
            }
         } else {
            this.skipRawVarint();
            return true;
         }
      }

      @Override
      public boolean skipField(int var1, CodedOutputStream var2) throws IOException {
         int var3 = WireFormat.getTagWireType(var1);
         if (var3 != 0) {
            if (var3 != 1) {
               if (var3 != 2) {
                  if (var3 != 3) {
                     if (var3 != 4) {
                        if (var3 == 5) {
                           var3 = this.readRawLittleEndian32();
                           var2.writeUInt32NoTag(var1);
                           var2.writeFixed32NoTag(var3);
                           return true;
                        } else {
                           throw InvalidProtocolBufferException.invalidWireType();
                        }
                     } else {
                        return false;
                     }
                  } else {
                     var2.writeUInt32NoTag(var1);
                     this.skipMessage(var2);
                     var1 = WireFormat.makeTag(WireFormat.getTagFieldNumber(var1), 4);
                     this.checkLastTagWas(var1);
                     var2.writeUInt32NoTag(var1);
                     return true;
                  }
               } else {
                  ByteString var6 = this.readBytes();
                  var2.writeUInt32NoTag(var1);
                  var2.writeBytesNoTag(var6);
                  return true;
               }
            } else {
               long var9 = this.readRawLittleEndian64();
               var2.writeUInt32NoTag(var1);
               var2.writeFixed64NoTag(var9);
               return true;
            }
         } else {
            long var4 = this.readInt64();
            var2.writeUInt32NoTag(var1);
            var2.writeUInt64NoTag(var4);
            return true;
         }
      }

      @Override
      public void skipMessage() throws IOException {
         int var1;
         do {
            var1 = this.readTag();
         } while (var1 != 0 && this.skipField(var1));
      }

      @Override
      public void skipMessage(CodedOutputStream var1) throws IOException {
         int var2;
         do {
            var2 = this.readTag();
         } while (var2 != 0 && this.skipField(var2, var1));
      }

      @Override
      public void skipRawBytes(int var1) throws IOException {
         int var2 = this.bufferSize;
         int var3 = this.pos;
         if (var1 <= var2 - var3 && var1 >= 0) {
            this.pos = var3 + var1;
         } else {
            this.skipRawBytesSlowPath(var1);
         }
      }

      private interface RefillCallback {
         void onRefill();
      }

      private class SkippedDataSink implements CodedInputStream.StreamDecoder.RefillCallback {
         private ByteArrayOutputStream byteArrayStream;
         private int lastPos;
         final CodedInputStream.StreamDecoder this$0;

         private SkippedDataSink(CodedInputStream.StreamDecoder var1) {
            this.this$0 = var1;
            this.lastPos = var1.pos;
         }

         ByteBuffer getSkippedData() {
            ByteArrayOutputStream var1 = this.byteArrayStream;
            if (var1 == null) {
               return ByteBuffer.wrap(this.this$0.buffer, this.lastPos, this.this$0.pos - this.lastPos);
            } else {
               var1.write(this.this$0.buffer, this.lastPos, this.this$0.pos);
               return ByteBuffer.wrap(this.byteArrayStream.toByteArray());
            }
         }

         @Override
         public void onRefill() {
            if (this.byteArrayStream == null) {
               this.byteArrayStream = new ByteArrayOutputStream();
            }

            this.byteArrayStream.write(this.this$0.buffer, this.lastPos, this.this$0.pos - this.lastPos);
            this.lastPos = 0;
         }
      }
   }

   private static final class UnsafeDirectNioDecoder extends CodedInputStream {
      private final long address;
      private final ByteBuffer buffer;
      private int bufferSizeAfterLimit;
      private int currentLimit = Integer.MAX_VALUE;
      private boolean enableAliasing;
      private final boolean immutable;
      private int lastTag;
      private long limit;
      private long pos;
      private long startPos;

      private UnsafeDirectNioDecoder(ByteBuffer var1, boolean var2) {
         this.buffer = var1;
         long var3 = UnsafeUtil.addressOffset(var1);
         this.address = var3;
         this.limit = var1.limit() + var3;
         var3 += var1.position();
         this.pos = var3;
         this.startPos = var3;
         this.immutable = var2;
      }

      private int bufferPos(long var1) {
         return (int)(var1 - this.address);
      }

      static boolean isSupported() {
         return UnsafeUtil.hasUnsafeByteBufferOperations();
      }

      private void recomputeBufferSizeAfterLimit() {
         long var3 = this.limit + this.bufferSizeAfterLimit;
         this.limit = var3;
         int var1 = (int)(var3 - this.startPos);
         int var2 = this.currentLimit;
         if (var1 > var2) {
            var1 -= var2;
            this.bufferSizeAfterLimit = var1;
            this.limit = var3 - var1;
         } else {
            this.bufferSizeAfterLimit = 0;
         }
      }

      private int remaining() {
         return (int)(this.limit - this.pos);
      }

      private void skipRawVarint() throws IOException {
         if (this.remaining() >= 10) {
            this.skipRawVarintFastPath();
         } else {
            this.skipRawVarintSlowPath();
         }
      }

      private void skipRawVarintFastPath() throws IOException {
         for (int var1 = 0; var1 < 10; var1++) {
            long var2 = (long)(this.pos++);
            if (UnsafeUtil.getByte(var2) >= 0) {
               return;
            }
         }

         throw InvalidProtocolBufferException.malformedVarint();
      }

      private void skipRawVarintSlowPath() throws IOException {
         for (int var1 = 0; var1 < 10; var1++) {
            if (this.readRawByte() >= 0) {
               return;
            }
         }

         throw InvalidProtocolBufferException.malformedVarint();
      }

      private ByteBuffer slice(long var1, long var3) throws IOException {
         int var6 = this.buffer.position();
         int var5 = this.buffer.limit();
         ByteBuffer var7 = this.buffer;

         ByteBuffer var14;
         try {
            ((Buffer)var7).position(this.bufferPos(var1));
            ((Buffer)var7).limit(this.bufferPos(var3));
            var14 = this.buffer.slice();
         } catch (IllegalArgumentException var12) {
            InvalidProtocolBufferException var8 = InvalidProtocolBufferException.truncatedMessage();
            var8.initCause(var12);
            throw var8;
         } finally {
            ((Buffer)var7).position(var6);
            ((Buffer)var7).limit(var5);
         }

         return var14;
      }

      @Override
      public void checkLastTagWas(int var1) throws InvalidProtocolBufferException {
         if (this.lastTag != var1) {
            throw InvalidProtocolBufferException.invalidEndTag();
         }
      }

      @Override
      public void enableAliasing(boolean var1) {
         this.enableAliasing = var1;
      }

      @Override
      public int getBytesUntilLimit() {
         int var1 = this.currentLimit;
         return var1 == Integer.MAX_VALUE ? -1 : var1 - this.getTotalBytesRead();
      }

      @Override
      public int getLastTag() {
         return this.lastTag;
      }

      @Override
      public int getTotalBytesRead() {
         return (int)(this.pos - this.startPos);
      }

      @Override
      public boolean isAtEnd() throws IOException {
         boolean var1;
         if (this.pos == this.limit) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      @Override
      public void popLimit(int var1) {
         this.currentLimit = var1;
         this.recomputeBufferSizeAfterLimit();
      }

      @Override
      public int pushLimit(int var1) throws InvalidProtocolBufferException {
         if (var1 >= 0) {
            int var2 = var1 + this.getTotalBytesRead();
            var1 = this.currentLimit;
            if (var2 <= var1) {
               this.currentLimit = var2;
               this.recomputeBufferSizeAfterLimit();
               return var1;
            } else {
               throw InvalidProtocolBufferException.truncatedMessage();
            }
         } else {
            throw InvalidProtocolBufferException.negativeSize();
         }
      }

      @Override
      public boolean readBool() throws IOException {
         boolean var1;
         if (this.readRawVarint64() != 0L) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      @Override
      public byte[] readByteArray() throws IOException {
         return this.readRawBytes(this.readRawVarint32());
      }

      @Override
      public ByteBuffer readByteBuffer() throws IOException {
         int var1 = this.readRawVarint32();
         if (var1 > 0 && var1 <= this.remaining()) {
            if (!this.immutable && this.enableAliasing) {
               long var7 = this.pos;
               long var8 = var1;
               ByteBuffer var9 = this.slice(var7, var7 + var8);
               this.pos += var8;
               return var9;
            } else {
               byte[] var6 = new byte[var1];
               long var4 = this.pos;
               long var2 = var1;
               UnsafeUtil.copyMemory(var4, var6, 0L, var2);
               this.pos += var2;
               return ByteBuffer.wrap(var6);
            }
         } else if (var1 == 0) {
            return Internal.EMPTY_BYTE_BUFFER;
         } else if (var1 < 0) {
            throw InvalidProtocolBufferException.negativeSize();
         } else {
            throw InvalidProtocolBufferException.truncatedMessage();
         }
      }

      @Override
      public ByteString readBytes() throws IOException {
         int var1 = this.readRawVarint32();
         if (var1 > 0 && var1 <= this.remaining()) {
            if (this.immutable && this.enableAliasing) {
               long var8 = this.pos;
               long var7 = var1;
               ByteBuffer var9 = this.slice(var8, var8 + var7);
               this.pos += var7;
               return ByteString.wrap(var9);
            } else {
               byte[] var6 = new byte[var1];
               long var2 = this.pos;
               long var4 = var1;
               UnsafeUtil.copyMemory(var2, var6, 0L, var4);
               this.pos += var4;
               return ByteString.wrap(var6);
            }
         } else if (var1 == 0) {
            return ByteString.EMPTY;
         } else if (var1 < 0) {
            throw InvalidProtocolBufferException.negativeSize();
         } else {
            throw InvalidProtocolBufferException.truncatedMessage();
         }
      }

      @Override
      public double readDouble() throws IOException {
         return Double.longBitsToDouble(this.readRawLittleEndian64());
      }

      @Override
      public int readEnum() throws IOException {
         return this.readRawVarint32();
      }

      @Override
      public int readFixed32() throws IOException {
         return this.readRawLittleEndian32();
      }

      @Override
      public long readFixed64() throws IOException {
         return this.readRawLittleEndian64();
      }

      @Override
      public float readFloat() throws IOException {
         return Float.intBitsToFloat(this.readRawLittleEndian32());
      }

      @Override
      public <T extends MessageLite> T readGroup(int var1, Parser<T> var2, ExtensionRegistryLite var3) throws IOException {
         this.checkRecursionLimit();
         this.recursionDepth++;
         MessageLite var4 = (MessageLite)var2.parsePartialFrom(this, var3);
         this.checkLastTagWas(WireFormat.makeTag(var1, 4));
         this.recursionDepth--;
         return (T)var4;
      }

      @Override
      public void readGroup(int var1, MessageLite.Builder var2, ExtensionRegistryLite var3) throws IOException {
         this.checkRecursionLimit();
         this.recursionDepth++;
         var2.mergeFrom(this, var3);
         this.checkLastTagWas(WireFormat.makeTag(var1, 4));
         this.recursionDepth--;
      }

      @Override
      public int readInt32() throws IOException {
         return this.readRawVarint32();
      }

      @Override
      public long readInt64() throws IOException {
         return this.readRawVarint64();
      }

      @Override
      public <T extends MessageLite> T readMessage(Parser<T> var1, ExtensionRegistryLite var2) throws IOException {
         int var3 = this.readRawVarint32();
         this.checkRecursionLimit();
         var3 = this.pushLimit(var3);
         this.recursionDepth++;
         MessageLite var4 = (MessageLite)var1.parsePartialFrom(this, var2);
         this.checkLastTagWas(0);
         this.recursionDepth--;
         if (this.getBytesUntilLimit() == 0) {
            this.popLimit(var3);
            return (T)var4;
         } else {
            throw InvalidProtocolBufferException.truncatedMessage();
         }
      }

      @Override
      public void readMessage(MessageLite.Builder var1, ExtensionRegistryLite var2) throws IOException {
         int var3 = this.readRawVarint32();
         this.checkRecursionLimit();
         var3 = this.pushLimit(var3);
         this.recursionDepth++;
         var1.mergeFrom(this, var2);
         this.checkLastTagWas(0);
         this.recursionDepth--;
         if (this.getBytesUntilLimit() == 0) {
            this.popLimit(var3);
         } else {
            throw InvalidProtocolBufferException.truncatedMessage();
         }
      }

      @Override
      public byte readRawByte() throws IOException {
         long var1 = this.pos;
         if (var1 != this.limit) {
            this.pos = 1L + var1;
            return UnsafeUtil.getByte(var1);
         } else {
            throw InvalidProtocolBufferException.truncatedMessage();
         }
      }

      @Override
      public byte[] readRawBytes(int var1) throws IOException {
         if (var1 >= 0 && var1 <= this.remaining()) {
            byte[] var6 = new byte[var1];
            long var4 = this.pos;
            long var2 = var1;
            this.slice(var4, var4 + var2).get(var6);
            this.pos += var2;
            return var6;
         } else if (var1 <= 0) {
            if (var1 == 0) {
               return Internal.EMPTY_BYTE_ARRAY;
            } else {
               throw InvalidProtocolBufferException.negativeSize();
            }
         } else {
            throw InvalidProtocolBufferException.truncatedMessage();
         }
      }

      @Override
      public int readRawLittleEndian32() throws IOException {
         long var4 = this.pos;
         if (this.limit - var4 >= 4L) {
            this.pos = 4L + var4;
            byte var1 = UnsafeUtil.getByte(var4);
            byte var3 = UnsafeUtil.getByte(1L + var4);
            byte var2 = UnsafeUtil.getByte(2L + var4);
            return (UnsafeUtil.getByte(var4 + 3L) & 0xFF) << 24 | var1 & 0xFF | (var3 & 0xFF) << 8 | (var2 & 0xFF) << 16;
         } else {
            throw InvalidProtocolBufferException.truncatedMessage();
         }
      }

      @Override
      public long readRawLittleEndian64() throws IOException {
         long var15 = this.pos;
         if (this.limit - var15 >= 8L) {
            this.pos = 8L + var15;
            long var9 = UnsafeUtil.getByte(var15);
            long var13 = UnsafeUtil.getByte(1L + var15);
            long var11 = UnsafeUtil.getByte(2L + var15);
            long var3 = UnsafeUtil.getByte(3L + var15);
            long var1 = UnsafeUtil.getByte(4L + var15);
            long var5 = UnsafeUtil.getByte(5L + var15);
            long var7 = UnsafeUtil.getByte(6L + var15);
            return (UnsafeUtil.getByte(var15 + 7L) & 255L) << 56
               | var9 & 255L
               | (var13 & 255L) << 8
               | (var11 & 255L) << 16
               | (var3 & 255L) << 24
               | (var1 & 255L) << 32
               | (var5 & 255L) << 40
               | (var7 & 255L) << 48;
         } else {
            throw InvalidProtocolBufferException.truncatedMessage();
         }
      }

      @Override
      public int readRawVarint32() throws IOException {
         long var9 = this.pos;
         if (this.limit != var9) {
            long var5 = 1L + var9;
            int var1 = UnsafeUtil.getByte(var9);
            if (var1 >= 0) {
               this.pos = var5;
               return var1;
            }

            if (this.limit - var5 >= 9L) {
               long var3 = 2L + var9;
               var1 = UnsafeUtil.getByte(var5) << 7 ^ var1;
               if (var1 < 0) {
                  var1 ^= -128;
               } else {
                  var5 = 3L + var9;
                  var1 ^= UnsafeUtil.getByte(var3) << 14;
                  if (var1 >= 0) {
                     var1 ^= 16256;
                     var3 = var5;
                  } else {
                     var3 = 4L + var9;
                     var1 ^= UnsafeUtil.getByte(var5) << 21;
                     if (var1 < 0) {
                        var1 = -2080896 ^ var1;
                     } else {
                        var5 = 5L + var9;
                        byte var2 = UnsafeUtil.getByte(var3);
                        var1 = var1 ^ var2 << 28 ^ 266354560;
                        var3 = var5;
                        if (var2 < 0) {
                           long var7 = 6L + var9;
                           var3 = var7;
                           if (UnsafeUtil.getByte(var5) < 0) {
                              var5 = 7L + var9;
                              var3 = var5;
                              if (UnsafeUtil.getByte(var7) < 0) {
                                 var7 = 8L + var9;
                                 var3 = var7;
                                 if (UnsafeUtil.getByte(var5) < 0) {
                                    var5 = var9 + 9L;
                                    var3 = var5;
                                    if (UnsafeUtil.getByte(var7) < 0) {
                                       var3 = 10L + var9;
                                       if (UnsafeUtil.getByte(var5) < 0) {
                                          return (int)this.readRawVarint64SlowPath();
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }

               this.pos = var3;
               return var1;
            }
         }

         return (int)this.readRawVarint64SlowPath();
      }

      @Override
      public long readRawVarint64() throws IOException {
         long var10 = this.pos;
         if (this.limit != var10) {
            long var4 = 1L + var10;
            int var1 = UnsafeUtil.getByte(var10);
            if (var1 >= 0) {
               this.pos = var4;
               return var1;
            }

            if (this.limit - var4 >= 9L) {
               long var17;
               label58: {
                  var17 = 2L + var10;
                  var1 = UnsafeUtil.getByte(var4) << 7 ^ var1;
                  if (var1 < 0) {
                     var1 ^= -128;
                     var4 = var17;
                  } else {
                     var4 = 3L + var10;
                     var1 ^= UnsafeUtil.getByte(var17) << 14;
                     if (var1 >= 0) {
                        var17 = var1 ^ 16256;
                        break label58;
                     }

                     var17 = 4L + var10;
                     var1 ^= UnsafeUtil.getByte(var4) << 21;
                     if (var1 >= 0) {
                        long var6 = var1;
                        var4 = 5L + var10;
                        var6 ^= (long)UnsafeUtil.getByte(var17) << 28;
                        if (var6 >= 0L) {
                           long var28 = 266354560L;
                           var17 = var4;
                           var4 = var28;
                        } else {
                           label68: {
                              var17 = 6L + var10;
                              var6 ^= (long)UnsafeUtil.getByte(var4) << 35;
                              if (var6 < 0L) {
                                 var4 = -34093383808L;
                              } else {
                                 long var8 = 7L + var10;
                                 var6 ^= (long)UnsafeUtil.getByte(var17) << 42;
                                 if (var6 >= 0L) {
                                    var4 = 4363953127296L;
                                    var17 = var8;
                                    break label68;
                                 }

                                 var17 = 8L + var10;
                                 var6 ^= (long)UnsafeUtil.getByte(var8) << 49;
                                 if (var6 >= 0L) {
                                    var4 = var10 + 9L;
                                    var17 = var6 ^ (long)UnsafeUtil.getByte(var17) << 56 ^ 71499008037633920L;
                                    if (var17 < 0L) {
                                       if (UnsafeUtil.getByte(var4) < 0L) {
                                          return this.readRawVarint64SlowPath();
                                       }

                                       var4 = var10 + 10L;
                                    }
                                    break label58;
                                 }

                                 var4 = -558586000294016L;
                              }

                              var6 = var4 ^ var6;
                              var4 = var17;
                              var17 = var6;
                              break label58;
                           }
                        }

                        var6 = var4 ^ var6;
                        var4 = var17;
                        var17 = var6;
                        break label58;
                     }

                     var1 = -2080896 ^ var1;
                     var4 = var17;
                  }

                  var17 = var1;
               }

               this.pos = var4;
               return var17;
            }
         }

         return this.readRawVarint64SlowPath();
      }

      @Override
      long readRawVarint64SlowPath() throws IOException {
         long var3 = 0L;

         for (byte var1 = 0; var1 < 64; var1 += 7) {
            byte var2 = this.readRawByte();
            var3 |= (long)(var2 & 127) << var1;
            if ((var2 & 128) == 0) {
               return var3;
            }
         }

         throw InvalidProtocolBufferException.malformedVarint();
      }

      @Override
      public int readSFixed32() throws IOException {
         return this.readRawLittleEndian32();
      }

      @Override
      public long readSFixed64() throws IOException {
         return this.readRawLittleEndian64();
      }

      @Override
      public int readSInt32() throws IOException {
         return decodeZigZag32(this.readRawVarint32());
      }

      @Override
      public long readSInt64() throws IOException {
         return decodeZigZag64(this.readRawVarint64());
      }

      @Override
      public String readString() throws IOException {
         int var1 = this.readRawVarint32();
         if (var1 > 0 && var1 <= this.remaining()) {
            byte[] var6 = new byte[var1];
            long var4 = this.pos;
            long var2 = var1;
            UnsafeUtil.copyMemory(var4, var6, 0L, var2);
            String var7 = new String(var6, Internal.UTF_8);
            this.pos += var2;
            return var7;
         } else if (var1 == 0) {
            return "";
         } else if (var1 < 0) {
            throw InvalidProtocolBufferException.negativeSize();
         } else {
            throw InvalidProtocolBufferException.truncatedMessage();
         }
      }

      @Override
      public String readStringRequireUtf8() throws IOException {
         int var2 = this.readRawVarint32();
         if (var2 > 0 && var2 <= this.remaining()) {
            int var1 = this.bufferPos(this.pos);
            String var3 = Utf8.decodeUtf8(this.buffer, var1, var2);
            this.pos += var2;
            return var3;
         } else if (var2 == 0) {
            return "";
         } else if (var2 <= 0) {
            throw InvalidProtocolBufferException.negativeSize();
         } else {
            throw InvalidProtocolBufferException.truncatedMessage();
         }
      }

      @Override
      public int readTag() throws IOException {
         if (this.isAtEnd()) {
            this.lastTag = 0;
            return 0;
         } else {
            int var1 = this.readRawVarint32();
            this.lastTag = var1;
            if (WireFormat.getTagFieldNumber(var1) != 0) {
               return this.lastTag;
            } else {
               throw InvalidProtocolBufferException.invalidTag();
            }
         }
      }

      @Override
      public int readUInt32() throws IOException {
         return this.readRawVarint32();
      }

      @Override
      public long readUInt64() throws IOException {
         return this.readRawVarint64();
      }

      @Deprecated
      @Override
      public void readUnknownGroup(int var1, MessageLite.Builder var2) throws IOException {
         this.readGroup(var1, var2, ExtensionRegistryLite.getEmptyRegistry());
      }

      @Override
      public void resetSizeCounter() {
         this.startPos = this.pos;
      }

      @Override
      public boolean skipField(int var1) throws IOException {
         int var2 = WireFormat.getTagWireType(var1);
         if (var2 != 0) {
            if (var2 != 1) {
               if (var2 != 2) {
                  if (var2 != 3) {
                     if (var2 != 4) {
                        if (var2 == 5) {
                           this.skipRawBytes(4);
                           return true;
                        } else {
                           throw InvalidProtocolBufferException.invalidWireType();
                        }
                     } else {
                        return false;
                     }
                  } else {
                     this.skipMessage();
                     this.checkLastTagWas(WireFormat.makeTag(WireFormat.getTagFieldNumber(var1), 4));
                     return true;
                  }
               } else {
                  this.skipRawBytes(this.readRawVarint32());
                  return true;
               }
            } else {
               this.skipRawBytes(8);
               return true;
            }
         } else {
            this.skipRawVarint();
            return true;
         }
      }

      @Override
      public boolean skipField(int var1, CodedOutputStream var2) throws IOException {
         int var3 = WireFormat.getTagWireType(var1);
         if (var3 != 0) {
            if (var3 != 1) {
               if (var3 != 2) {
                  if (var3 != 3) {
                     if (var3 != 4) {
                        if (var3 == 5) {
                           var3 = this.readRawLittleEndian32();
                           var2.writeUInt32NoTag(var1);
                           var2.writeFixed32NoTag(var3);
                           return true;
                        } else {
                           throw InvalidProtocolBufferException.invalidWireType();
                        }
                     } else {
                        return false;
                     }
                  } else {
                     var2.writeUInt32NoTag(var1);
                     this.skipMessage(var2);
                     var1 = WireFormat.makeTag(WireFormat.getTagFieldNumber(var1), 4);
                     this.checkLastTagWas(var1);
                     var2.writeUInt32NoTag(var1);
                     return true;
                  }
               } else {
                  ByteString var6 = this.readBytes();
                  var2.writeUInt32NoTag(var1);
                  var2.writeBytesNoTag(var6);
                  return true;
               }
            } else {
               long var9 = this.readRawLittleEndian64();
               var2.writeUInt32NoTag(var1);
               var2.writeFixed64NoTag(var9);
               return true;
            }
         } else {
            long var4 = this.readInt64();
            var2.writeUInt32NoTag(var1);
            var2.writeUInt64NoTag(var4);
            return true;
         }
      }

      @Override
      public void skipMessage() throws IOException {
         int var1;
         do {
            var1 = this.readTag();
         } while (var1 != 0 && this.skipField(var1));
      }

      @Override
      public void skipMessage(CodedOutputStream var1) throws IOException {
         int var2;
         do {
            var2 = this.readTag();
         } while (var2 != 0 && this.skipField(var2, var1));
      }

      @Override
      public void skipRawBytes(int var1) throws IOException {
         if (var1 >= 0 && var1 <= this.remaining()) {
            this.pos += var1;
         } else if (var1 < 0) {
            throw InvalidProtocolBufferException.negativeSize();
         } else {
            throw InvalidProtocolBufferException.truncatedMessage();
         }
      }
   }
}
