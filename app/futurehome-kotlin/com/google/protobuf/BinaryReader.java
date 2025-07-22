package com.google.protobuf;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

@CheckReturnValue
abstract class BinaryReader implements Reader {
   private static final int FIXED32_MULTIPLE_MASK = 3;
   private static final int FIXED64_MULTIPLE_MASK = 7;

   private BinaryReader() {
   }

   public static BinaryReader newInstance(ByteBuffer var0, boolean var1) {
      if (var0.hasArray()) {
         return new BinaryReader.SafeHeapReader(var0, var1);
      } else {
         throw new IllegalArgumentException("Direct buffers not yet supported");
      }
   }

   public abstract int getTotalBytesRead();

   @Override
   public boolean shouldDiscardUnknownFields() {
      return false;
   }

   private static final class SafeHeapReader extends BinaryReader {
      private final byte[] buffer;
      private final boolean bufferIsImmutable;
      private int endGroupTag;
      private final int initialPos;
      private int limit;
      private int pos;
      private int tag;

      public SafeHeapReader(ByteBuffer var1, boolean var2) {
         this.bufferIsImmutable = var2;
         this.buffer = var1.array();
         int var3 = var1.arrayOffset() + var1.position();
         this.pos = var3;
         this.initialPos = var3;
         this.limit = var1.arrayOffset() + var1.limit();
      }

      private boolean isAtEnd() {
         boolean var1;
         if (this.pos == this.limit) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      private byte readByte() throws IOException {
         int var1 = this.pos;
         if (var1 != this.limit) {
            byte[] var2 = this.buffer;
            this.pos = var1 + 1;
            return var2[var1];
         } else {
            throw InvalidProtocolBufferException.truncatedMessage();
         }
      }

      private Object readField(WireFormat.FieldType var1, Class<?> var2, ExtensionRegistryLite var3) throws IOException {
         switch (<unrepresentable>.$SwitchMap$com$google$protobuf$WireFormat$FieldType[var1.ordinal()]) {
            case 1:
               return this.readBool();
            case 2:
               return this.readBytes();
            case 3:
               return this.readDouble();
            case 4:
               return this.readEnum();
            case 5:
               return this.readFixed32();
            case 6:
               return this.readFixed64();
            case 7:
               return this.readFloat();
            case 8:
               return this.readInt32();
            case 9:
               return this.readInt64();
            case 10:
               return this.readMessage(var2, var3);
            case 11:
               return this.readSFixed32();
            case 12:
               return this.readSFixed64();
            case 13:
               return this.readSInt32();
            case 14:
               return this.readSInt64();
            case 15:
               return this.readStringRequireUtf8();
            case 16:
               return this.readUInt32();
            case 17:
               return this.readUInt64();
            default:
               throw new RuntimeException("unsupported field type.");
         }
      }

      private <T> T readGroup(Schema<T> var1, ExtensionRegistryLite var2) throws IOException {
         Object var3 = var1.newInstance();
         this.mergeGroupField(var3, var1, var2);
         var1.makeImmutable(var3);
         return (T)var3;
      }

      private int readLittleEndian32() throws IOException {
         this.requireBytes(4);
         return this.readLittleEndian32_NoCheck();
      }

      private int readLittleEndian32_NoCheck() {
         int var4 = this.pos;
         byte[] var5 = this.buffer;
         this.pos = var4 + 4;
         byte var3 = var5[var4];
         byte var2 = var5[var4 + 1];
         byte var1 = var5[var4 + 2];
         return (var5[var4 + 3] & 0xFF) << 24 | var3 & 0xFF | (var2 & 0xFF) << 8 | (var1 & 0xFF) << 16;
      }

      private long readLittleEndian64() throws IOException {
         this.requireBytes(8);
         return this.readLittleEndian64_NoCheck();
      }

      private long readLittleEndian64_NoCheck() {
         int var1 = this.pos;
         byte[] var16 = this.buffer;
         this.pos = var1 + 8;
         long var14 = var16[var1];
         long var4 = var16[var1 + 1];
         long var6 = var16[var1 + 2];
         long var10 = var16[var1 + 3];
         long var8 = var16[var1 + 4];
         long var12 = var16[var1 + 5];
         long var2 = var16[var1 + 6];
         return (var16[var1 + 7] & 255L) << 56
            | var14 & 255L
            | (var4 & 255L) << 8
            | (var6 & 255L) << 16
            | (var10 & 255L) << 24
            | (var8 & 255L) << 32
            | (var12 & 255L) << 40
            | (var2 & 255L) << 48;
      }

      private <T> T readMessage(Schema<T> var1, ExtensionRegistryLite var2) throws IOException {
         Object var3 = var1.newInstance();
         this.mergeMessageField(var3, var1, var2);
         var1.makeImmutable(var3);
         return (T)var3;
      }

      private int readVarint32() throws IOException {
         int var6 = this.pos;
         int var2 = this.limit;
         if (var2 == var6) {
            throw InvalidProtocolBufferException.truncatedMessage();
         } else {
            byte[] var7 = this.buffer;
            int var1 = var6 + 1;
            int var3 = var7[var6];
            if (var3 >= 0) {
               this.pos = var1;
               return var3;
            } else if (var2 - var1 < 9) {
               return (int)this.readVarint64SlowPath();
            } else {
               var2 = var6 + 2;
               var1 = var7[var1] << 7 ^ var3;
               if (var1 < 0) {
                  var1 ^= -128;
               } else {
                  label59: {
                     var3 = var6 + 3;
                     var2 = var7[var2] << 14 ^ var1;
                     if (var2 >= 0) {
                        var2 ^= 16256;
                        var1 = var3;
                     } else {
                        label58: {
                           var1 = var6 + 4;
                           var2 ^= var7[var3] << 21;
                           if (var2 < 0) {
                              var2 = -2080896 ^ var2;
                           } else {
                              var3 = var6 + 5;
                              int var4 = var7[var1];
                              var2 = var2 ^ var4 << 28 ^ 266354560;
                              var1 = var3;
                              if (var4 >= 0) {
                                 break label58;
                              }

                              var4 = var6 + 6;
                              var1 = var4;
                              if (var7[var3] < 0) {
                                 int var5 = var6 + 7;
                                 var1 = var5;
                                 if (var7[var4] >= 0) {
                                    break label58;
                                 }

                                 var3 = var6 + 8;
                                 var1 = var3;
                                 if (var7[var5] < 0) {
                                    var4 = var6 + 9;
                                    var1 = var4;
                                    if (var7[var3] < 0) {
                                       if (var7[var4] < 0) {
                                          throw InvalidProtocolBufferException.malformedVarint();
                                       }

                                       var3 = var6 + 10;
                                       var1 = var2;
                                       var2 = var3;
                                       break label59;
                                    }
                                    break label58;
                                 }
                              }
                           }

                           var1 = var2;
                           var2 = var1;
                           break label59;
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
      }

      private long readVarint64SlowPath() throws IOException {
         long var3 = 0L;

         for (byte var1 = 0; var1 < 64; var1 += 7) {
            byte var2 = this.readByte();
            var3 |= (long)(var2 & 127) << var1;
            if ((var2 & 128) == 0) {
               return var3;
            }
         }

         throw InvalidProtocolBufferException.malformedVarint();
      }

      private void requireBytes(int var1) throws IOException {
         if (var1 < 0 || var1 > this.limit - this.pos) {
            throw InvalidProtocolBufferException.truncatedMessage();
         }
      }

      private void requirePosition(int var1) throws IOException {
         if (this.pos != var1) {
            throw InvalidProtocolBufferException.truncatedMessage();
         }
      }

      private void requireWireType(int var1) throws IOException {
         if (WireFormat.getTagWireType(this.tag) != var1) {
            throw InvalidProtocolBufferException.invalidWireType();
         }
      }

      private void skipBytes(int var1) throws IOException {
         this.requireBytes(var1);
         this.pos += var1;
      }

      private void skipGroup() throws IOException {
         int var1 = this.endGroupTag;
         this.endGroupTag = WireFormat.makeTag(WireFormat.getTagFieldNumber(this.tag), 4);

         while (this.getFieldNumber() != Integer.MAX_VALUE && this.skipField()) {
         }

         if (this.tag == this.endGroupTag) {
            this.endGroupTag = var1;
         } else {
            throw InvalidProtocolBufferException.parseFailure();
         }
      }

      private void skipVarint() throws IOException {
         int var2 = this.limit;
         int var1 = this.pos;
         if (var2 - var1 >= 10) {
            byte[] var4 = this.buffer;
            var2 = 0;

            while (var2 < 10) {
               int var3 = var1 + 1;
               if (var4[var1] >= 0) {
                  this.pos = var3;
                  return;
               }

               var2++;
               var1 = var3;
            }
         }

         this.skipVarintSlowPath();
      }

      private void skipVarintSlowPath() throws IOException {
         for (int var1 = 0; var1 < 10; var1++) {
            if (this.readByte() >= 0) {
               return;
            }
         }

         throw InvalidProtocolBufferException.malformedVarint();
      }

      private void verifyPackedFixed32Length(int var1) throws IOException {
         this.requireBytes(var1);
         if ((var1 & 3) != 0) {
            throw InvalidProtocolBufferException.parseFailure();
         }
      }

      private void verifyPackedFixed64Length(int var1) throws IOException {
         this.requireBytes(var1);
         if ((var1 & 7) != 0) {
            throw InvalidProtocolBufferException.parseFailure();
         }
      }

      @Override
      public int getFieldNumber() throws IOException {
         if (this.isAtEnd()) {
            return Integer.MAX_VALUE;
         } else {
            int var1 = this.readVarint32();
            this.tag = var1;
            return var1 == this.endGroupTag ? Integer.MAX_VALUE : WireFormat.getTagFieldNumber(var1);
         }
      }

      @Override
      public int getTag() {
         return this.tag;
      }

      @Override
      public int getTotalBytesRead() {
         return this.pos - this.initialPos;
      }

      @Override
      public <T> void mergeGroupField(T param1, Schema<T> param2, ExtensionRegistryLite param3) throws IOException {
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
         // 00: aload 0
         // 01: getfield com/google/protobuf/BinaryReader$SafeHeapReader.endGroupTag I
         // 04: istore 6
         // 06: aload 0
         // 07: aload 0
         // 08: getfield com/google/protobuf/BinaryReader$SafeHeapReader.tag I
         // 0b: invokestatic com/google/protobuf/WireFormat.getTagFieldNumber (I)I
         // 0e: bipush 4
         // 0f: invokestatic com/google/protobuf/WireFormat.makeTag (II)I
         // 12: putfield com/google/protobuf/BinaryReader$SafeHeapReader.endGroupTag I
         // 15: aload 2
         // 16: aload 1
         // 17: aload 0
         // 18: aload 3
         // 19: invokeinterface com/google/protobuf/Schema.mergeFrom (Ljava/lang/Object;Lcom/google/protobuf/Reader;Lcom/google/protobuf/ExtensionRegistryLite;)V 4
         // 1e: aload 0
         // 1f: getfield com/google/protobuf/BinaryReader$SafeHeapReader.tag I
         // 22: istore 5
         // 24: aload 0
         // 25: getfield com/google/protobuf/BinaryReader$SafeHeapReader.endGroupTag I
         // 28: istore 4
         // 2a: iload 5
         // 2c: iload 4
         // 2e: if_icmpne 38
         // 31: aload 0
         // 32: iload 6
         // 34: putfield com/google/protobuf/BinaryReader$SafeHeapReader.endGroupTag I
         // 37: return
         // 38: invokestatic com/google/protobuf/InvalidProtocolBufferException.parseFailure ()Lcom/google/protobuf/InvalidProtocolBufferException;
         // 3b: athrow
         // 3c: astore 1
         // 3d: aload 0
         // 3e: iload 6
         // 40: putfield com/google/protobuf/BinaryReader$SafeHeapReader.endGroupTag I
         // 43: aload 1
         // 44: athrow
      }

      @Override
      public <T> void mergeMessageField(T param1, Schema<T> param2, ExtensionRegistryLite param3) throws IOException {
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
         // 00: aload 0
         // 01: invokespecial com/google/protobuf/BinaryReader$SafeHeapReader.readVarint32 ()I
         // 04: istore 5
         // 06: aload 0
         // 07: iload 5
         // 09: invokespecial com/google/protobuf/BinaryReader$SafeHeapReader.requireBytes (I)V
         // 0c: aload 0
         // 0d: getfield com/google/protobuf/BinaryReader$SafeHeapReader.limit I
         // 10: istore 4
         // 12: aload 0
         // 13: getfield com/google/protobuf/BinaryReader$SafeHeapReader.pos I
         // 16: iload 5
         // 18: iadd
         // 19: istore 6
         // 1b: aload 0
         // 1c: iload 6
         // 1e: putfield com/google/protobuf/BinaryReader$SafeHeapReader.limit I
         // 21: aload 2
         // 22: aload 1
         // 23: aload 0
         // 24: aload 3
         // 25: invokeinterface com/google/protobuf/Schema.mergeFrom (Ljava/lang/Object;Lcom/google/protobuf/Reader;Lcom/google/protobuf/ExtensionRegistryLite;)V 4
         // 2a: aload 0
         // 2b: getfield com/google/protobuf/BinaryReader$SafeHeapReader.pos I
         // 2e: istore 5
         // 30: iload 5
         // 32: iload 6
         // 34: if_icmpne 3e
         // 37: aload 0
         // 38: iload 4
         // 3a: putfield com/google/protobuf/BinaryReader$SafeHeapReader.limit I
         // 3d: return
         // 3e: invokestatic com/google/protobuf/InvalidProtocolBufferException.parseFailure ()Lcom/google/protobuf/InvalidProtocolBufferException;
         // 41: athrow
         // 42: astore 1
         // 43: aload 0
         // 44: iload 4
         // 46: putfield com/google/protobuf/BinaryReader$SafeHeapReader.limit I
         // 49: aload 1
         // 4a: athrow
      }

      @Override
      public boolean readBool() throws IOException {
         boolean var1 = false;
         this.requireWireType(0);
         if (this.readVarint32() != 0) {
            var1 = true;
         }

         return var1;
      }

      @Override
      public void readBoolList(List<Boolean> var1) throws IOException {
         if (var1 instanceof BooleanArrayList) {
            BooleanArrayList var4 = (BooleanArrayList)var1;
            int var2 = WireFormat.getTagWireType(this.tag);
            if (var2 == 0) {
               do {
                  var4.addBoolean(this.readBool());
                  if (this.isAtEnd()) {
                     return;
                  }

                  var2 = this.pos;
               } while (this.readVarint32() == this.tag);

               this.pos = var2;
               return;
            }

            if (var2 != 2) {
               throw InvalidProtocolBufferException.invalidWireType();
            }

            var2 = this.readVarint32();
            var2 = this.pos + var2;

            while (this.pos < var2) {
               boolean var3;
               if (this.readVarint32() != 0) {
                  var3 = true;
               } else {
                  var3 = false;
               }

               var4.addBoolean(var3);
            }

            this.requirePosition(var2);
         } else {
            int var8 = WireFormat.getTagWireType(this.tag);
            if (var8 == 0) {
               do {
                  var1.add(this.readBool());
                  if (this.isAtEnd()) {
                     return;
                  }

                  var8 = this.pos;
               } while (this.readVarint32() == this.tag);

               this.pos = var8;
               return;
            }

            if (var8 != 2) {
               throw InvalidProtocolBufferException.invalidWireType();
            }

            var8 = this.readVarint32();
            var8 = this.pos + var8;

            while (this.pos < var8) {
               boolean var12;
               if (this.readVarint32() != 0) {
                  var12 = true;
               } else {
                  var12 = false;
               }

               var1.add(var12);
            }

            this.requirePosition(var8);
         }
      }

      @Override
      public ByteString readBytes() throws IOException {
         this.requireWireType(2);
         int var1 = this.readVarint32();
         if (var1 == 0) {
            return ByteString.EMPTY;
         } else {
            this.requireBytes(var1);
            ByteString var2;
            if (this.bufferIsImmutable) {
               var2 = ByteString.wrap(this.buffer, this.pos, var1);
            } else {
               var2 = ByteString.copyFrom(this.buffer, this.pos, var1);
            }

            this.pos += var1;
            return var2;
         }
      }

      @Override
      public void readBytesList(List<ByteString> var1) throws IOException {
         if (WireFormat.getTagWireType(this.tag) != 2) {
            throw InvalidProtocolBufferException.invalidWireType();
         } else {
            int var2;
            do {
               var1.add(this.readBytes());
               if (this.isAtEnd()) {
                  return;
               }

               var2 = this.pos;
            } while (this.readVarint32() == this.tag);

            this.pos = var2;
         }
      }

      @Override
      public double readDouble() throws IOException {
         this.requireWireType(1);
         return Double.longBitsToDouble(this.readLittleEndian64());
      }

      @Override
      public void readDoubleList(List<Double> var1) throws IOException {
         if (var1 instanceof DoubleArrayList) {
            DoubleArrayList var4 = (DoubleArrayList)var1;
            int var2 = WireFormat.getTagWireType(this.tag);
            if (var2 == 1) {
               do {
                  var4.addDouble(this.readDouble());
                  if (this.isAtEnd()) {
                     return;
                  }

                  var2 = this.pos;
               } while (this.readVarint32() == this.tag);

               this.pos = var2;
               return;
            }

            if (var2 != 2) {
               throw InvalidProtocolBufferException.invalidWireType();
            }

            int var3 = this.readVarint32();
            this.verifyPackedFixed64Length(var3);
            var2 = this.pos;

            while (this.pos < var2 + var3) {
               var4.addDouble(Double.longBitsToDouble(this.readLittleEndian64_NoCheck()));
            }
         } else {
            int var7 = WireFormat.getTagWireType(this.tag);
            if (var7 == 1) {
               do {
                  var1.add(this.readDouble());
                  if (this.isAtEnd()) {
                     return;
                  }

                  var7 = this.pos;
               } while (this.readVarint32() == this.tag);

               this.pos = var7;
               return;
            }

            if (var7 != 2) {
               throw InvalidProtocolBufferException.invalidWireType();
            }

            int var10 = this.readVarint32();
            this.verifyPackedFixed64Length(var10);
            var7 = this.pos;

            while (this.pos < var7 + var10) {
               var1.add(Double.longBitsToDouble(this.readLittleEndian64_NoCheck()));
            }
         }
      }

      @Override
      public int readEnum() throws IOException {
         this.requireWireType(0);
         return this.readVarint32();
      }

      @Override
      public void readEnumList(List<Integer> var1) throws IOException {
         if (var1 instanceof IntArrayList) {
            IntArrayList var4 = (IntArrayList)var1;
            int var2 = WireFormat.getTagWireType(this.tag);
            if (var2 == 0) {
               do {
                  var4.addInt(this.readEnum());
                  if (this.isAtEnd()) {
                     return;
                  }

                  var2 = this.pos;
               } while (this.readVarint32() == this.tag);

               this.pos = var2;
               return;
            }

            if (var2 != 2) {
               throw InvalidProtocolBufferException.invalidWireType();
            }

            int var3 = this.readVarint32();
            var2 = this.pos;

            while (this.pos < var2 + var3) {
               var4.addInt(this.readVarint32());
            }
         } else {
            int var7 = WireFormat.getTagWireType(this.tag);
            if (var7 == 0) {
               do {
                  var1.add(this.readEnum());
                  if (this.isAtEnd()) {
                     return;
                  }

                  var7 = this.pos;
               } while (this.readVarint32() == this.tag);

               this.pos = var7;
               return;
            }

            if (var7 != 2) {
               throw InvalidProtocolBufferException.invalidWireType();
            }

            var7 = this.readVarint32();
            int var10 = this.pos;

            while (this.pos < var10 + var7) {
               var1.add(this.readVarint32());
            }
         }
      }

      @Override
      public int readFixed32() throws IOException {
         this.requireWireType(5);
         return this.readLittleEndian32();
      }

      @Override
      public void readFixed32List(List<Integer> var1) throws IOException {
         if (var1 instanceof IntArrayList) {
            IntArrayList var4 = (IntArrayList)var1;
            int var2 = WireFormat.getTagWireType(this.tag);
            if (var2 != 2) {
               if (var2 != 5) {
                  throw InvalidProtocolBufferException.invalidWireType();
               }

               do {
                  var4.addInt(this.readFixed32());
                  if (this.isAtEnd()) {
                     return;
                  }

                  var2 = this.pos;
               } while (this.readVarint32() == this.tag);

               this.pos = var2;
               return;
            }

            int var3 = this.readVarint32();
            this.verifyPackedFixed32Length(var3);
            var2 = this.pos;

            while (this.pos < var2 + var3) {
               var4.addInt(this.readLittleEndian32_NoCheck());
            }
         } else {
            int var7 = WireFormat.getTagWireType(this.tag);
            if (var7 != 2) {
               if (var7 != 5) {
                  throw InvalidProtocolBufferException.invalidWireType();
               }

               do {
                  var1.add(this.readFixed32());
                  if (this.isAtEnd()) {
                     return;
                  }

                  var7 = this.pos;
               } while (this.readVarint32() == this.tag);

               this.pos = var7;
               return;
            }

            int var10 = this.readVarint32();
            this.verifyPackedFixed32Length(var10);
            var7 = this.pos;

            while (this.pos < var7 + var10) {
               var1.add(this.readLittleEndian32_NoCheck());
            }
         }
      }

      @Override
      public long readFixed64() throws IOException {
         this.requireWireType(1);
         return this.readLittleEndian64();
      }

      @Override
      public void readFixed64List(List<Long> var1) throws IOException {
         if (var1 instanceof LongArrayList) {
            LongArrayList var4 = (LongArrayList)var1;
            int var2 = WireFormat.getTagWireType(this.tag);
            if (var2 == 1) {
               do {
                  var4.addLong(this.readFixed64());
                  if (this.isAtEnd()) {
                     return;
                  }

                  var2 = this.pos;
               } while (this.readVarint32() == this.tag);

               this.pos = var2;
               return;
            }

            if (var2 != 2) {
               throw InvalidProtocolBufferException.invalidWireType();
            }

            int var3 = this.readVarint32();
            this.verifyPackedFixed64Length(var3);
            var2 = this.pos;

            while (this.pos < var2 + var3) {
               var4.addLong(this.readLittleEndian64_NoCheck());
            }
         } else {
            int var7 = WireFormat.getTagWireType(this.tag);
            if (var7 == 1) {
               do {
                  var1.add(this.readFixed64());
                  if (this.isAtEnd()) {
                     return;
                  }

                  var7 = this.pos;
               } while (this.readVarint32() == this.tag);

               this.pos = var7;
               return;
            }

            if (var7 != 2) {
               throw InvalidProtocolBufferException.invalidWireType();
            }

            int var10 = this.readVarint32();
            this.verifyPackedFixed64Length(var10);
            var7 = this.pos;

            while (this.pos < var7 + var10) {
               var1.add(this.readLittleEndian64_NoCheck());
            }
         }
      }

      @Override
      public float readFloat() throws IOException {
         this.requireWireType(5);
         return Float.intBitsToFloat(this.readLittleEndian32());
      }

      @Override
      public void readFloatList(List<Float> var1) throws IOException {
         if (var1 instanceof FloatArrayList) {
            FloatArrayList var4 = (FloatArrayList)var1;
            int var2 = WireFormat.getTagWireType(this.tag);
            if (var2 != 2) {
               if (var2 != 5) {
                  throw InvalidProtocolBufferException.invalidWireType();
               }

               do {
                  var4.addFloat(this.readFloat());
                  if (this.isAtEnd()) {
                     return;
                  }

                  var2 = this.pos;
               } while (this.readVarint32() == this.tag);

               this.pos = var2;
               return;
            }

            var2 = this.readVarint32();
            this.verifyPackedFixed32Length(var2);
            int var3 = this.pos;

            while (this.pos < var3 + var2) {
               var4.addFloat(Float.intBitsToFloat(this.readLittleEndian32_NoCheck()));
            }
         } else {
            int var7 = WireFormat.getTagWireType(this.tag);
            if (var7 != 2) {
               if (var7 != 5) {
                  throw InvalidProtocolBufferException.invalidWireType();
               }

               do {
                  var1.add(this.readFloat());
                  if (this.isAtEnd()) {
                     return;
                  }

                  var7 = this.pos;
               } while (this.readVarint32() == this.tag);

               this.pos = var7;
               return;
            }

            int var10 = this.readVarint32();
            this.verifyPackedFixed32Length(var10);
            var7 = this.pos;

            while (this.pos < var7 + var10) {
               var1.add(Float.intBitsToFloat(this.readLittleEndian32_NoCheck()));
            }
         }
      }

      @Deprecated
      @Override
      public <T> T readGroup(Class<T> var1, ExtensionRegistryLite var2) throws IOException {
         this.requireWireType(3);
         return this.readGroup(Protobuf.getInstance().schemaFor(var1), var2);
      }

      @Deprecated
      @Override
      public <T> T readGroupBySchemaWithCheck(Schema<T> var1, ExtensionRegistryLite var2) throws IOException {
         this.requireWireType(3);
         return this.readGroup(var1, var2);
      }

      @Deprecated
      @Override
      public <T> void readGroupList(List<T> var1, Schema<T> var2, ExtensionRegistryLite var3) throws IOException {
         if (WireFormat.getTagWireType(this.tag) != 3) {
            throw InvalidProtocolBufferException.invalidWireType();
         } else {
            int var5 = this.tag;

            int var4;
            do {
               var1.add(this.readGroup(var2, var3));
               if (this.isAtEnd()) {
                  return;
               }

               var4 = this.pos;
            } while (this.readVarint32() == var5);

            this.pos = var4;
         }
      }

      @Deprecated
      @Override
      public <T> void readGroupList(List<T> var1, Class<T> var2, ExtensionRegistryLite var3) throws IOException {
         this.readGroupList(var1, Protobuf.getInstance().schemaFor(var2), var3);
      }

      @Override
      public int readInt32() throws IOException {
         this.requireWireType(0);
         return this.readVarint32();
      }

      @Override
      public void readInt32List(List<Integer> var1) throws IOException {
         if (var1 instanceof IntArrayList) {
            IntArrayList var3 = (IntArrayList)var1;
            int var2 = WireFormat.getTagWireType(this.tag);
            if (var2 == 0) {
               do {
                  var3.addInt(this.readInt32());
                  if (this.isAtEnd()) {
                     return;
                  }

                  var2 = this.pos;
               } while (this.readVarint32() == this.tag);

               this.pos = var2;
               return;
            }

            if (var2 != 2) {
               throw InvalidProtocolBufferException.invalidWireType();
            }

            var2 = this.readVarint32();
            var2 = this.pos + var2;

            while (this.pos < var2) {
               var3.addInt(this.readVarint32());
            }

            this.requirePosition(var2);
         } else {
            int var7 = WireFormat.getTagWireType(this.tag);
            if (var7 == 0) {
               do {
                  var1.add(this.readInt32());
                  if (this.isAtEnd()) {
                     return;
                  }

                  var7 = this.pos;
               } while (this.readVarint32() == this.tag);

               this.pos = var7;
               return;
            }

            if (var7 != 2) {
               throw InvalidProtocolBufferException.invalidWireType();
            }

            var7 = this.readVarint32();
            var7 = this.pos + var7;

            while (this.pos < var7) {
               var1.add(this.readVarint32());
            }

            this.requirePosition(var7);
         }
      }

      @Override
      public long readInt64() throws IOException {
         this.requireWireType(0);
         return this.readVarint64();
      }

      @Override
      public void readInt64List(List<Long> var1) throws IOException {
         if (var1 instanceof LongArrayList) {
            LongArrayList var3 = (LongArrayList)var1;
            int var2 = WireFormat.getTagWireType(this.tag);
            if (var2 == 0) {
               do {
                  var3.addLong(this.readInt64());
                  if (this.isAtEnd()) {
                     return;
                  }

                  var2 = this.pos;
               } while (this.readVarint32() == this.tag);

               this.pos = var2;
               return;
            }

            if (var2 != 2) {
               throw InvalidProtocolBufferException.invalidWireType();
            }

            var2 = this.readVarint32();
            var2 = this.pos + var2;

            while (this.pos < var2) {
               var3.addLong(this.readVarint64());
            }

            this.requirePosition(var2);
         } else {
            int var7 = WireFormat.getTagWireType(this.tag);
            if (var7 == 0) {
               do {
                  var1.add(this.readInt64());
                  if (this.isAtEnd()) {
                     return;
                  }

                  var7 = this.pos;
               } while (this.readVarint32() == this.tag);

               this.pos = var7;
               return;
            }

            if (var7 != 2) {
               throw InvalidProtocolBufferException.invalidWireType();
            }

            var7 = this.readVarint32();
            var7 = this.pos + var7;

            while (this.pos < var7) {
               var1.add(this.readVarint64());
            }

            this.requirePosition(var7);
         }
      }

      @Override
      public <K, V> void readMap(Map<K, V> param1, MapEntryLite.Metadata<K, V> param2, ExtensionRegistryLite param3) throws IOException {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.NullPointerException: Cannot invoke "org.jetbrains.java.decompiler.code.cfg.ExceptionRangeCFG.isCircular()" because "range" is null
         //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.graphToStatement(DomHelper.java:84)
         //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:203)
         //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
         //
         // Bytecode:
         // 00: aload 0
         // 01: bipush 2
         // 02: invokespecial com/google/protobuf/BinaryReader$SafeHeapReader.requireWireType (I)V
         // 05: aload 0
         // 06: invokespecial com/google/protobuf/BinaryReader$SafeHeapReader.readVarint32 ()I
         // 09: istore 5
         // 0b: aload 0
         // 0c: iload 5
         // 0e: invokespecial com/google/protobuf/BinaryReader$SafeHeapReader.requireBytes (I)V
         // 11: aload 0
         // 12: getfield com/google/protobuf/BinaryReader$SafeHeapReader.limit I
         // 15: istore 4
         // 17: aload 0
         // 18: aload 0
         // 19: getfield com/google/protobuf/BinaryReader$SafeHeapReader.pos I
         // 1c: iload 5
         // 1e: iadd
         // 1f: putfield com/google/protobuf/BinaryReader$SafeHeapReader.limit I
         // 22: aload 2
         // 23: getfield com/google/protobuf/MapEntryLite$Metadata.defaultKey Ljava/lang/Object;
         // 26: astore 6
         // 28: aload 2
         // 29: getfield com/google/protobuf/MapEntryLite$Metadata.defaultValue Ljava/lang/Object;
         // 2c: astore 7
         // 2e: aload 0
         // 2f: invokevirtual com/google/protobuf/BinaryReader$SafeHeapReader.getFieldNumber ()I
         // 32: istore 5
         // 34: iload 5
         // 36: ldc 2147483647
         // 38: if_icmpne 4d
         // 3b: aload 1
         // 3c: aload 6
         // 3e: aload 7
         // 40: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
         // 45: pop
         // 46: aload 0
         // 47: iload 4
         // 49: putfield com/google/protobuf/BinaryReader$SafeHeapReader.limit I
         // 4c: return
         // 4d: iload 5
         // 4f: bipush 1
         // 50: if_icmpeq 8c
         // 53: iload 5
         // 55: bipush 2
         // 56: if_icmpeq 73
         // 59: aload 0
         // 5a: invokevirtual com/google/protobuf/BinaryReader$SafeHeapReader.skipField ()Z
         // 5d: ifeq 63
         // 60: goto 2e
         // 63: new com/google/protobuf/InvalidProtocolBufferException
         // 66: astore 8
         // 68: aload 8
         // 6a: ldc_w "Unable to parse map entry."
         // 6d: invokespecial com/google/protobuf/InvalidProtocolBufferException.<init> (Ljava/lang/String;)V
         // 70: aload 8
         // 72: athrow
         // 73: aload 0
         // 74: aload 2
         // 75: getfield com/google/protobuf/MapEntryLite$Metadata.valueType Lcom/google/protobuf/WireFormat$FieldType;
         // 78: aload 2
         // 79: getfield com/google/protobuf/MapEntryLite$Metadata.defaultValue Ljava/lang/Object;
         // 7c: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
         // 7f: aload 3
         // 80: invokespecial com/google/protobuf/BinaryReader$SafeHeapReader.readField (Lcom/google/protobuf/WireFormat$FieldType;Ljava/lang/Class;Lcom/google/protobuf/ExtensionRegistryLite;)Ljava/lang/Object;
         // 83: astore 8
         // 85: aload 8
         // 87: astore 7
         // 89: goto 2e
         // 8c: aload 0
         // 8d: aload 2
         // 8e: getfield com/google/protobuf/MapEntryLite$Metadata.keyType Lcom/google/protobuf/WireFormat$FieldType;
         // 91: aconst_null
         // 92: aconst_null
         // 93: invokespecial com/google/protobuf/BinaryReader$SafeHeapReader.readField (Lcom/google/protobuf/WireFormat$FieldType;Ljava/lang/Class;Lcom/google/protobuf/ExtensionRegistryLite;)Ljava/lang/Object;
         // 96: astore 8
         // 98: aload 8
         // 9a: astore 6
         // 9c: goto 2e
         // 9f: astore 8
         // a1: aload 0
         // a2: invokevirtual com/google/protobuf/BinaryReader$SafeHeapReader.skipField ()Z
         // a5: ifeq ab
         // a8: goto 2e
         // ab: new com/google/protobuf/InvalidProtocolBufferException
         // ae: astore 1
         // af: aload 1
         // b0: ldc_w "Unable to parse map entry."
         // b3: invokespecial com/google/protobuf/InvalidProtocolBufferException.<init> (Ljava/lang/String;)V
         // b6: aload 1
         // b7: athrow
         // b8: astore 1
         // b9: aload 0
         // ba: iload 4
         // bc: putfield com/google/protobuf/BinaryReader$SafeHeapReader.limit I
         // bf: aload 1
         // c0: athrow
      }

      @Override
      public <T> T readMessage(Class<T> var1, ExtensionRegistryLite var2) throws IOException {
         this.requireWireType(2);
         return this.readMessage(Protobuf.getInstance().schemaFor(var1), var2);
      }

      @Override
      public <T> T readMessageBySchemaWithCheck(Schema<T> var1, ExtensionRegistryLite var2) throws IOException {
         this.requireWireType(2);
         return this.readMessage(var1, var2);
      }

      @Override
      public <T> void readMessageList(List<T> var1, Schema<T> var2, ExtensionRegistryLite var3) throws IOException {
         if (WireFormat.getTagWireType(this.tag) != 2) {
            throw InvalidProtocolBufferException.invalidWireType();
         } else {
            int var5 = this.tag;

            int var4;
            do {
               var1.add(this.readMessage(var2, var3));
               if (this.isAtEnd()) {
                  return;
               }

               var4 = this.pos;
            } while (this.readVarint32() == var5);

            this.pos = var4;
         }
      }

      @Override
      public <T> void readMessageList(List<T> var1, Class<T> var2, ExtensionRegistryLite var3) throws IOException {
         this.readMessageList(var1, Protobuf.getInstance().schemaFor(var2), var3);
      }

      @Override
      public int readSFixed32() throws IOException {
         this.requireWireType(5);
         return this.readLittleEndian32();
      }

      @Override
      public void readSFixed32List(List<Integer> var1) throws IOException {
         if (var1 instanceof IntArrayList) {
            IntArrayList var4 = (IntArrayList)var1;
            int var2 = WireFormat.getTagWireType(this.tag);
            if (var2 != 2) {
               if (var2 != 5) {
                  throw InvalidProtocolBufferException.invalidWireType();
               }

               do {
                  var4.addInt(this.readSFixed32());
                  if (this.isAtEnd()) {
                     return;
                  }

                  var2 = this.pos;
               } while (this.readVarint32() == this.tag);

               this.pos = var2;
               return;
            }

            int var3 = this.readVarint32();
            this.verifyPackedFixed32Length(var3);
            var2 = this.pos;

            while (this.pos < var2 + var3) {
               var4.addInt(this.readLittleEndian32_NoCheck());
            }
         } else {
            int var7 = WireFormat.getTagWireType(this.tag);
            if (var7 != 2) {
               if (var7 != 5) {
                  throw InvalidProtocolBufferException.invalidWireType();
               }

               do {
                  var1.add(this.readSFixed32());
                  if (this.isAtEnd()) {
                     return;
                  }

                  var7 = this.pos;
               } while (this.readVarint32() == this.tag);

               this.pos = var7;
               return;
            }

            int var10 = this.readVarint32();
            this.verifyPackedFixed32Length(var10);
            var7 = this.pos;

            while (this.pos < var7 + var10) {
               var1.add(this.readLittleEndian32_NoCheck());
            }
         }
      }

      @Override
      public long readSFixed64() throws IOException {
         this.requireWireType(1);
         return this.readLittleEndian64();
      }

      @Override
      public void readSFixed64List(List<Long> var1) throws IOException {
         if (var1 instanceof LongArrayList) {
            LongArrayList var4 = (LongArrayList)var1;
            int var2 = WireFormat.getTagWireType(this.tag);
            if (var2 == 1) {
               do {
                  var4.addLong(this.readSFixed64());
                  if (this.isAtEnd()) {
                     return;
                  }

                  var2 = this.pos;
               } while (this.readVarint32() == this.tag);

               this.pos = var2;
               return;
            }

            if (var2 != 2) {
               throw InvalidProtocolBufferException.invalidWireType();
            }

            int var3 = this.readVarint32();
            this.verifyPackedFixed64Length(var3);
            var2 = this.pos;

            while (this.pos < var2 + var3) {
               var4.addLong(this.readLittleEndian64_NoCheck());
            }
         } else {
            int var7 = WireFormat.getTagWireType(this.tag);
            if (var7 == 1) {
               do {
                  var1.add(this.readSFixed64());
                  if (this.isAtEnd()) {
                     return;
                  }

                  var7 = this.pos;
               } while (this.readVarint32() == this.tag);

               this.pos = var7;
               return;
            }

            if (var7 != 2) {
               throw InvalidProtocolBufferException.invalidWireType();
            }

            var7 = this.readVarint32();
            this.verifyPackedFixed64Length(var7);
            int var10 = this.pos;

            while (this.pos < var10 + var7) {
               var1.add(this.readLittleEndian64_NoCheck());
            }
         }
      }

      @Override
      public int readSInt32() throws IOException {
         this.requireWireType(0);
         return CodedInputStream.decodeZigZag32(this.readVarint32());
      }

      @Override
      public void readSInt32List(List<Integer> var1) throws IOException {
         if (var1 instanceof IntArrayList) {
            IntArrayList var4 = (IntArrayList)var1;
            int var2 = WireFormat.getTagWireType(this.tag);
            if (var2 == 0) {
               do {
                  var4.addInt(this.readSInt32());
                  if (this.isAtEnd()) {
                     return;
                  }

                  var2 = this.pos;
               } while (this.readVarint32() == this.tag);

               this.pos = var2;
               return;
            }

            if (var2 != 2) {
               throw InvalidProtocolBufferException.invalidWireType();
            }

            var2 = this.readVarint32();
            int var3 = this.pos;

            while (this.pos < var3 + var2) {
               var4.addInt(CodedInputStream.decodeZigZag32(this.readVarint32()));
            }
         } else {
            int var7 = WireFormat.getTagWireType(this.tag);
            if (var7 == 0) {
               do {
                  var1.add(this.readSInt32());
                  if (this.isAtEnd()) {
                     return;
                  }

                  var7 = this.pos;
               } while (this.readVarint32() == this.tag);

               this.pos = var7;
               return;
            }

            if (var7 != 2) {
               throw InvalidProtocolBufferException.invalidWireType();
            }

            int var10 = this.readVarint32();
            var7 = this.pos;

            while (this.pos < var7 + var10) {
               var1.add(CodedInputStream.decodeZigZag32(this.readVarint32()));
            }
         }
      }

      @Override
      public long readSInt64() throws IOException {
         this.requireWireType(0);
         return CodedInputStream.decodeZigZag64(this.readVarint64());
      }

      @Override
      public void readSInt64List(List<Long> var1) throws IOException {
         if (var1 instanceof LongArrayList) {
            LongArrayList var4 = (LongArrayList)var1;
            int var2 = WireFormat.getTagWireType(this.tag);
            if (var2 == 0) {
               do {
                  var4.addLong(this.readSInt64());
                  if (this.isAtEnd()) {
                     return;
                  }

                  var2 = this.pos;
               } while (this.readVarint32() == this.tag);

               this.pos = var2;
               return;
            }

            if (var2 != 2) {
               throw InvalidProtocolBufferException.invalidWireType();
            }

            var2 = this.readVarint32();
            int var3 = this.pos;

            while (this.pos < var3 + var2) {
               var4.addLong(CodedInputStream.decodeZigZag64(this.readVarint64()));
            }
         } else {
            int var7 = WireFormat.getTagWireType(this.tag);
            if (var7 == 0) {
               do {
                  var1.add(this.readSInt64());
                  if (this.isAtEnd()) {
                     return;
                  }

                  var7 = this.pos;
               } while (this.readVarint32() == this.tag);

               this.pos = var7;
               return;
            }

            if (var7 != 2) {
               throw InvalidProtocolBufferException.invalidWireType();
            }

            var7 = this.readVarint32();
            int var10 = this.pos;

            while (this.pos < var10 + var7) {
               var1.add(CodedInputStream.decodeZigZag64(this.readVarint64()));
            }
         }
      }

      @Override
      public String readString() throws IOException {
         return this.readStringInternal(false);
      }

      public String readStringInternal(boolean var1) throws IOException {
         this.requireWireType(2);
         int var3 = this.readVarint32();
         if (var3 == 0) {
            return "";
         } else {
            this.requireBytes(var3);
            if (var1) {
               byte[] var4 = this.buffer;
               int var2 = this.pos;
               if (!Utf8.isValidUtf8(var4, var2, var2 + var3)) {
                  throw InvalidProtocolBufferException.invalidUtf8();
               }
            }

            String var5 = new String(this.buffer, this.pos, var3, Internal.UTF_8);
            this.pos += var3;
            return var5;
         }
      }

      @Override
      public void readStringList(List<String> var1) throws IOException {
         this.readStringListInternal(var1, false);
      }

      public void readStringListInternal(List<String> var1, boolean var2) throws IOException {
         if (WireFormat.getTagWireType(this.tag) != 2) {
            throw InvalidProtocolBufferException.invalidWireType();
         } else if (var1 instanceof LazyStringList && !var2) {
            LazyStringList var4 = (LazyStringList)var1;

            int var5;
            do {
               var4.add(this.readBytes());
               if (this.isAtEnd()) {
                  return;
               }

               var5 = this.pos;
            } while (this.readVarint32() == this.tag);

            this.pos = var5;
         } else {
            int var3;
            do {
               var1.add(this.readStringInternal(var2));
               if (this.isAtEnd()) {
                  return;
               }

               var3 = this.pos;
            } while (this.readVarint32() == this.tag);

            this.pos = var3;
         }
      }

      @Override
      public void readStringListRequireUtf8(List<String> var1) throws IOException {
         this.readStringListInternal(var1, true);
      }

      @Override
      public String readStringRequireUtf8() throws IOException {
         return this.readStringInternal(true);
      }

      @Override
      public int readUInt32() throws IOException {
         this.requireWireType(0);
         return this.readVarint32();
      }

      @Override
      public void readUInt32List(List<Integer> var1) throws IOException {
         if (var1 instanceof IntArrayList) {
            IntArrayList var4 = (IntArrayList)var1;
            int var2 = WireFormat.getTagWireType(this.tag);
            if (var2 == 0) {
               do {
                  var4.addInt(this.readUInt32());
                  if (this.isAtEnd()) {
                     return;
                  }

                  var2 = this.pos;
               } while (this.readVarint32() == this.tag);

               this.pos = var2;
               return;
            }

            if (var2 != 2) {
               throw InvalidProtocolBufferException.invalidWireType();
            }

            var2 = this.readVarint32();
            int var3 = this.pos;

            while (this.pos < var3 + var2) {
               var4.addInt(this.readVarint32());
            }
         } else {
            int var7 = WireFormat.getTagWireType(this.tag);
            if (var7 == 0) {
               do {
                  var1.add(this.readUInt32());
                  if (this.isAtEnd()) {
                     return;
                  }

                  var7 = this.pos;
               } while (this.readVarint32() == this.tag);

               this.pos = var7;
               return;
            }

            if (var7 != 2) {
               throw InvalidProtocolBufferException.invalidWireType();
            }

            var7 = this.readVarint32();
            int var10 = this.pos;

            while (this.pos < var10 + var7) {
               var1.add(this.readVarint32());
            }
         }
      }

      @Override
      public long readUInt64() throws IOException {
         this.requireWireType(0);
         return this.readVarint64();
      }

      @Override
      public void readUInt64List(List<Long> var1) throws IOException {
         if (var1 instanceof LongArrayList) {
            LongArrayList var3 = (LongArrayList)var1;
            int var2 = WireFormat.getTagWireType(this.tag);
            if (var2 == 0) {
               do {
                  var3.addLong(this.readUInt64());
                  if (this.isAtEnd()) {
                     return;
                  }

                  var2 = this.pos;
               } while (this.readVarint32() == this.tag);

               this.pos = var2;
               return;
            }

            if (var2 != 2) {
               throw InvalidProtocolBufferException.invalidWireType();
            }

            var2 = this.readVarint32();
            var2 = this.pos + var2;

            while (this.pos < var2) {
               var3.addLong(this.readVarint64());
            }

            this.requirePosition(var2);
         } else {
            int var7 = WireFormat.getTagWireType(this.tag);
            if (var7 == 0) {
               do {
                  var1.add(this.readUInt64());
                  if (this.isAtEnd()) {
                     return;
                  }

                  var7 = this.pos;
               } while (this.readVarint32() == this.tag);

               this.pos = var7;
               return;
            }

            if (var7 != 2) {
               throw InvalidProtocolBufferException.invalidWireType();
            }

            var7 = this.readVarint32();
            var7 = this.pos + var7;

            while (this.pos < var7) {
               var1.add(this.readVarint64());
            }

            this.requirePosition(var7);
         }
      }

      public long readVarint64() throws IOException {
         int var3 = this.pos;
         int var1 = this.limit;
         if (var1 == var3) {
            throw InvalidProtocolBufferException.truncatedMessage();
         } else {
            byte[] var9 = this.buffer;
            int var4 = var3 + 1;
            int var2 = var9[var3];
            if (var2 >= 0) {
               this.pos = var4;
               return var2;
            } else if (var1 - var4 < 9) {
               return this.readVarint64SlowPath();
            } else {
               var1 = var3 + 2;
               var4 = var9[var4] << 7 ^ var2;
               long var5;
               if (var4 < 0) {
                  var5 = var4 ^ -128;
               } else {
                  var2 = var3 + 3;
                  var4 = var9[var1] << 14 ^ var4;
                  if (var4 >= 0) {
                     var5 = var4 ^ 16256;
                     var1 = var2;
                  } else {
                     var1 = var3 + 4;
                     var2 = var4 ^ var9[var2] << 21;
                     if (var2 < 0) {
                        var5 = -2080896 ^ var2;
                     } else {
                        label62: {
                           var5 = var2;
                           var2 = var3 + 5;
                           var5 ^= (long)var9[var1] << 28;
                           long var21;
                           if (var5 >= 0L) {
                              var21 = 266354560L;
                              var1 = var2;
                           } else {
                              label61: {
                                 var1 = var3 + 6;
                                 var21 = var5 ^ (long)var9[var2] << 35;
                                 if (var21 < 0L) {
                                    var5 = -34093383808L;
                                 } else {
                                    var2 = var3 + 7;
                                    var5 = var21 ^ (long)var9[var1] << 42;
                                    if (var5 >= 0L) {
                                       var21 = 4363953127296L;
                                       var1 = var2;
                                       break label61;
                                    }

                                    var1 = var3 + 8;
                                    var21 = var5 ^ (long)var9[var2] << 49;
                                    if (var21 >= 0L) {
                                       var2 = var3 + 9;
                                       var5 = var21 ^ (long)var9[var1] << 56 ^ 71499008037633920L;
                                       var1 = var2;
                                       if (var5 < 0L) {
                                          if (var9[var2] < 0L) {
                                             throw InvalidProtocolBufferException.malformedVarint();
                                          }

                                          var1 = var3 + 10;
                                       }
                                       break label62;
                                    }

                                    var5 = -558586000294016L;
                                 }

                                 var5 = var21 ^ var5;
                                 break label62;
                              }
                           }

                           var5 ^= var21;
                        }
                     }
                  }
               }

               this.pos = var1;
               return var5;
            }
         }
      }

      @Override
      public boolean skipField() throws IOException {
         if (!this.isAtEnd()) {
            int var1 = this.tag;
            if (var1 != this.endGroupTag) {
               var1 = WireFormat.getTagWireType(var1);
               if (var1 != 0) {
                  if (var1 != 1) {
                     if (var1 != 2) {
                        if (var1 != 3) {
                           if (var1 == 5) {
                              this.skipBytes(4);
                              return true;
                           }

                           throw InvalidProtocolBufferException.invalidWireType();
                        }

                        this.skipGroup();
                        return true;
                     }

                     this.skipBytes(this.readVarint32());
                     return true;
                  }

                  this.skipBytes(8);
                  return true;
               }

               this.skipVarint();
               return true;
            }
         }

         return false;
      }
   }
}
