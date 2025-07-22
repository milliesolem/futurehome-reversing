package com.google.protobuf;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@CheckReturnValue
final class CodedInputStreamReader implements Reader {
   private static final int FIXED32_MULTIPLE_MASK = 3;
   private static final int FIXED64_MULTIPLE_MASK = 7;
   private static final int NEXT_TAG_UNSET = 0;
   private int endGroupTag;
   private final CodedInputStream input;
   private int nextTag = 0;
   private int tag;

   private CodedInputStreamReader(CodedInputStream var1) {
      var1 = Internal.checkNotNull(var1, "input");
      this.input = var1;
      var1.wrapper = this;
   }

   public static CodedInputStreamReader forCodedInput(CodedInputStream var0) {
      return var0.wrapper != null ? var0.wrapper : new CodedInputStreamReader(var0);
   }

   private <T> void mergeGroupFieldInternal(T param1, Schema<T> param2, ExtensionRegistryLite param3) throws IOException {
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
      // 01: getfield com/google/protobuf/CodedInputStreamReader.endGroupTag I
      // 04: istore 5
      // 06: aload 0
      // 07: aload 0
      // 08: getfield com/google/protobuf/CodedInputStreamReader.tag I
      // 0b: invokestatic com/google/protobuf/WireFormat.getTagFieldNumber (I)I
      // 0e: bipush 4
      // 0f: invokestatic com/google/protobuf/WireFormat.makeTag (II)I
      // 12: putfield com/google/protobuf/CodedInputStreamReader.endGroupTag I
      // 15: aload 2
      // 16: aload 1
      // 17: aload 0
      // 18: aload 3
      // 19: invokeinterface com/google/protobuf/Schema.mergeFrom (Ljava/lang/Object;Lcom/google/protobuf/Reader;Lcom/google/protobuf/ExtensionRegistryLite;)V 4
      // 1e: aload 0
      // 1f: getfield com/google/protobuf/CodedInputStreamReader.tag I
      // 22: istore 6
      // 24: aload 0
      // 25: getfield com/google/protobuf/CodedInputStreamReader.endGroupTag I
      // 28: istore 4
      // 2a: iload 6
      // 2c: iload 4
      // 2e: if_icmpne 38
      // 31: aload 0
      // 32: iload 5
      // 34: putfield com/google/protobuf/CodedInputStreamReader.endGroupTag I
      // 37: return
      // 38: invokestatic com/google/protobuf/InvalidProtocolBufferException.parseFailure ()Lcom/google/protobuf/InvalidProtocolBufferException;
      // 3b: athrow
      // 3c: astore 1
      // 3d: aload 0
      // 3e: iload 5
      // 40: putfield com/google/protobuf/CodedInputStreamReader.endGroupTag I
      // 43: aload 1
      // 44: athrow
   }

   private <T> void mergeMessageFieldInternal(T var1, Schema<T> var2, ExtensionRegistryLite var3) throws IOException {
      int var4 = this.input.readUInt32();
      if (this.input.recursionDepth < this.input.recursionLimit) {
         var4 = this.input.pushLimit(var4);
         CodedInputStream var5 = this.input;
         var5.recursionDepth++;
         var2.mergeFrom(var1, this, var3);
         this.input.checkLastTagWas(0);
         var1 = this.input;
         var1.recursionDepth--;
         this.input.popLimit(var4);
      } else {
         throw InvalidProtocolBufferException.recursionLimitExceeded();
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
            throw new IllegalArgumentException("unsupported field type.");
      }
   }

   private <T> T readGroup(Schema<T> var1, ExtensionRegistryLite var2) throws IOException {
      Object var3 = var1.newInstance();
      this.mergeGroupFieldInternal(var3, var1, var2);
      var1.makeImmutable(var3);
      return (T)var3;
   }

   private <T> T readMessage(Schema<T> var1, ExtensionRegistryLite var2) throws IOException {
      Object var3 = var1.newInstance();
      this.mergeMessageFieldInternal(var3, var1, var2);
      var1.makeImmutable(var3);
      return (T)var3;
   }

   private void requirePosition(int var1) throws IOException {
      if (this.input.getTotalBytesRead() != var1) {
         throw InvalidProtocolBufferException.truncatedMessage();
      }
   }

   private void requireWireType(int var1) throws IOException {
      if (WireFormat.getTagWireType(this.tag) != var1) {
         throw InvalidProtocolBufferException.invalidWireType();
      }
   }

   private void verifyPackedFixed32Length(int var1) throws IOException {
      if ((var1 & 3) != 0) {
         throw InvalidProtocolBufferException.parseFailure();
      }
   }

   private void verifyPackedFixed64Length(int var1) throws IOException {
      if ((var1 & 7) != 0) {
         throw InvalidProtocolBufferException.parseFailure();
      }
   }

   @Override
   public int getFieldNumber() throws IOException {
      int var1 = this.nextTag;
      if (var1 != 0) {
         this.tag = var1;
         this.nextTag = 0;
      } else {
         this.tag = this.input.readTag();
      }

      var1 = this.tag;
      return var1 != 0 && var1 != this.endGroupTag ? WireFormat.getTagFieldNumber(var1) : Integer.MAX_VALUE;
   }

   @Override
   public int getTag() {
      return this.tag;
   }

   @Override
   public <T> void mergeGroupField(T var1, Schema<T> var2, ExtensionRegistryLite var3) throws IOException {
      this.requireWireType(3);
      this.mergeGroupFieldInternal(var1, var2, var3);
   }

   @Override
   public <T> void mergeMessageField(T var1, Schema<T> var2, ExtensionRegistryLite var3) throws IOException {
      this.requireWireType(2);
      this.mergeMessageFieldInternal(var1, var2, var3);
   }

   @Override
   public boolean readBool() throws IOException {
      this.requireWireType(0);
      return this.input.readBool();
   }

   @Override
   public void readBoolList(List<Boolean> var1) throws IOException {
      if (var1 instanceof BooleanArrayList) {
         BooleanArrayList var3 = (BooleanArrayList)var1;
         int var2 = WireFormat.getTagWireType(this.tag);
         if (var2 == 0) {
            do {
               var3.addBoolean(this.input.readBool());
               if (this.input.isAtEnd()) {
                  return;
               }

               var2 = this.input.readTag();
            } while (var2 == this.tag);

            this.nextTag = var2;
            return;
         }

         if (var2 != 2) {
            throw InvalidProtocolBufferException.invalidWireType();
         }

         var2 = this.input.readUInt32();
         var2 = this.input.getTotalBytesRead() + var2;

         do {
            var3.addBoolean(this.input.readBool());
         } while (this.input.getTotalBytesRead() < var2);

         this.requirePosition(var2);
      } else {
         int var7 = WireFormat.getTagWireType(this.tag);
         if (var7 == 0) {
            do {
               var1.add(this.input.readBool());
               if (this.input.isAtEnd()) {
                  return;
               }

               var7 = this.input.readTag();
            } while (var7 == this.tag);

            this.nextTag = var7;
            return;
         }

         if (var7 != 2) {
            throw InvalidProtocolBufferException.invalidWireType();
         }

         var7 = this.input.readUInt32();
         var7 = this.input.getTotalBytesRead() + var7;

         do {
            var1.add(this.input.readBool());
         } while (this.input.getTotalBytesRead() < var7);

         this.requirePosition(var7);
      }
   }

   @Override
   public ByteString readBytes() throws IOException {
      this.requireWireType(2);
      return this.input.readBytes();
   }

   @Override
   public void readBytesList(List<ByteString> var1) throws IOException {
      if (WireFormat.getTagWireType(this.tag) != 2) {
         throw InvalidProtocolBufferException.invalidWireType();
      } else {
         int var2;
         do {
            var1.add(this.readBytes());
            if (this.input.isAtEnd()) {
               return;
            }

            var2 = this.input.readTag();
         } while (var2 == this.tag);

         this.nextTag = var2;
      }
   }

   @Override
   public double readDouble() throws IOException {
      this.requireWireType(1);
      return this.input.readDouble();
   }

   @Override
   public void readDoubleList(List<Double> var1) throws IOException {
      if (var1 instanceof DoubleArrayList) {
         DoubleArrayList var4 = (DoubleArrayList)var1;
         int var2 = WireFormat.getTagWireType(this.tag);
         if (var2 == 1) {
            do {
               var4.addDouble(this.input.readDouble());
               if (this.input.isAtEnd()) {
                  return;
               }

               var2 = this.input.readTag();
            } while (var2 == this.tag);

            this.nextTag = var2;
            return;
         }

         if (var2 != 2) {
            throw InvalidProtocolBufferException.invalidWireType();
         }

         var2 = this.input.readUInt32();
         this.verifyPackedFixed64Length(var2);
         int var3 = this.input.getTotalBytesRead();

         do {
            var4.addDouble(this.input.readDouble());
         } while (this.input.getTotalBytesRead() < var3 + var2);
      } else {
         int var7 = WireFormat.getTagWireType(this.tag);
         if (var7 == 1) {
            do {
               var1.add(this.input.readDouble());
               if (this.input.isAtEnd()) {
                  return;
               }

               var7 = this.input.readTag();
            } while (var7 == this.tag);

            this.nextTag = var7;
            return;
         }

         if (var7 != 2) {
            throw InvalidProtocolBufferException.invalidWireType();
         }

         int var10 = this.input.readUInt32();
         this.verifyPackedFixed64Length(var10);
         var7 = this.input.getTotalBytesRead();

         do {
            var1.add(this.input.readDouble());
         } while (this.input.getTotalBytesRead() < var7 + var10);
      }
   }

   @Override
   public int readEnum() throws IOException {
      this.requireWireType(0);
      return this.input.readEnum();
   }

   @Override
   public void readEnumList(List<Integer> var1) throws IOException {
      if (var1 instanceof IntArrayList) {
         IntArrayList var3 = (IntArrayList)var1;
         int var2 = WireFormat.getTagWireType(this.tag);
         if (var2 == 0) {
            do {
               var3.addInt(this.input.readEnum());
               if (this.input.isAtEnd()) {
                  return;
               }

               var2 = this.input.readTag();
            } while (var2 == this.tag);

            this.nextTag = var2;
            return;
         }

         if (var2 != 2) {
            throw InvalidProtocolBufferException.invalidWireType();
         }

         var2 = this.input.readUInt32();
         var2 = this.input.getTotalBytesRead() + var2;

         do {
            var3.addInt(this.input.readEnum());
         } while (this.input.getTotalBytesRead() < var2);

         this.requirePosition(var2);
      } else {
         int var7 = WireFormat.getTagWireType(this.tag);
         if (var7 == 0) {
            do {
               var1.add(this.input.readEnum());
               if (this.input.isAtEnd()) {
                  return;
               }

               var7 = this.input.readTag();
            } while (var7 == this.tag);

            this.nextTag = var7;
            return;
         }

         if (var7 != 2) {
            throw InvalidProtocolBufferException.invalidWireType();
         }

         var7 = this.input.readUInt32();
         var7 = this.input.getTotalBytesRead() + var7;

         do {
            var1.add(this.input.readEnum());
         } while (this.input.getTotalBytesRead() < var7);

         this.requirePosition(var7);
      }
   }

   @Override
   public int readFixed32() throws IOException {
      this.requireWireType(5);
      return this.input.readFixed32();
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
               var4.addInt(this.input.readFixed32());
               if (this.input.isAtEnd()) {
                  return;
               }

               var2 = this.input.readTag();
            } while (var2 == this.tag);

            this.nextTag = var2;
            return;
         }

         var2 = this.input.readUInt32();
         this.verifyPackedFixed32Length(var2);
         int var3 = this.input.getTotalBytesRead();

         do {
            var4.addInt(this.input.readFixed32());
         } while (this.input.getTotalBytesRead() < var3 + var2);
      } else {
         int var7 = WireFormat.getTagWireType(this.tag);
         if (var7 != 2) {
            if (var7 != 5) {
               throw InvalidProtocolBufferException.invalidWireType();
            }

            do {
               var1.add(this.input.readFixed32());
               if (this.input.isAtEnd()) {
                  return;
               }

               var7 = this.input.readTag();
            } while (var7 == this.tag);

            this.nextTag = var7;
            return;
         }

         int var10 = this.input.readUInt32();
         this.verifyPackedFixed32Length(var10);
         var7 = this.input.getTotalBytesRead();

         do {
            var1.add(this.input.readFixed32());
         } while (this.input.getTotalBytesRead() < var7 + var10);
      }
   }

   @Override
   public long readFixed64() throws IOException {
      this.requireWireType(1);
      return this.input.readFixed64();
   }

   @Override
   public void readFixed64List(List<Long> var1) throws IOException {
      if (var1 instanceof LongArrayList) {
         LongArrayList var4 = (LongArrayList)var1;
         int var2 = WireFormat.getTagWireType(this.tag);
         if (var2 == 1) {
            do {
               var4.addLong(this.input.readFixed64());
               if (this.input.isAtEnd()) {
                  return;
               }

               var2 = this.input.readTag();
            } while (var2 == this.tag);

            this.nextTag = var2;
            return;
         }

         if (var2 != 2) {
            throw InvalidProtocolBufferException.invalidWireType();
         }

         int var3 = this.input.readUInt32();
         this.verifyPackedFixed64Length(var3);
         var2 = this.input.getTotalBytesRead();

         do {
            var4.addLong(this.input.readFixed64());
         } while (this.input.getTotalBytesRead() < var2 + var3);
      } else {
         int var7 = WireFormat.getTagWireType(this.tag);
         if (var7 == 1) {
            do {
               var1.add(this.input.readFixed64());
               if (this.input.isAtEnd()) {
                  return;
               }

               var7 = this.input.readTag();
            } while (var7 == this.tag);

            this.nextTag = var7;
            return;
         }

         if (var7 != 2) {
            throw InvalidProtocolBufferException.invalidWireType();
         }

         var7 = this.input.readUInt32();
         this.verifyPackedFixed64Length(var7);
         int var10 = this.input.getTotalBytesRead();

         do {
            var1.add(this.input.readFixed64());
         } while (this.input.getTotalBytesRead() < var10 + var7);
      }
   }

   @Override
   public float readFloat() throws IOException {
      this.requireWireType(5);
      return this.input.readFloat();
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
               var4.addFloat(this.input.readFloat());
               if (this.input.isAtEnd()) {
                  return;
               }

               var2 = this.input.readTag();
            } while (var2 == this.tag);

            this.nextTag = var2;
            return;
         }

         var2 = this.input.readUInt32();
         this.verifyPackedFixed32Length(var2);
         int var3 = this.input.getTotalBytesRead();

         do {
            var4.addFloat(this.input.readFloat());
         } while (this.input.getTotalBytesRead() < var3 + var2);
      } else {
         int var7 = WireFormat.getTagWireType(this.tag);
         if (var7 != 2) {
            if (var7 != 5) {
               throw InvalidProtocolBufferException.invalidWireType();
            }

            do {
               var1.add(this.input.readFloat());
               if (this.input.isAtEnd()) {
                  return;
               }

               var7 = this.input.readTag();
            } while (var7 == this.tag);

            this.nextTag = var7;
            return;
         }

         int var10 = this.input.readUInt32();
         this.verifyPackedFixed32Length(var10);
         var7 = this.input.getTotalBytesRead();

         do {
            var1.add(this.input.readFloat());
         } while (this.input.getTotalBytesRead() < var7 + var10);
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

         while (true) {
            var1.add(this.readGroup(var2, var3));
            if (this.input.isAtEnd() || this.nextTag != 0) {
               break;
            }

            int var4 = this.input.readTag();
            if (var4 != var5) {
               this.nextTag = var4;
               break;
            }
         }
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
      return this.input.readInt32();
   }

   @Override
   public void readInt32List(List<Integer> var1) throws IOException {
      if (var1 instanceof IntArrayList) {
         IntArrayList var3 = (IntArrayList)var1;
         int var2 = WireFormat.getTagWireType(this.tag);
         if (var2 == 0) {
            do {
               var3.addInt(this.input.readInt32());
               if (this.input.isAtEnd()) {
                  return;
               }

               var2 = this.input.readTag();
            } while (var2 == this.tag);

            this.nextTag = var2;
            return;
         }

         if (var2 != 2) {
            throw InvalidProtocolBufferException.invalidWireType();
         }

         var2 = this.input.readUInt32();
         var2 = this.input.getTotalBytesRead() + var2;

         do {
            var3.addInt(this.input.readInt32());
         } while (this.input.getTotalBytesRead() < var2);

         this.requirePosition(var2);
      } else {
         int var7 = WireFormat.getTagWireType(this.tag);
         if (var7 == 0) {
            do {
               var1.add(this.input.readInt32());
               if (this.input.isAtEnd()) {
                  return;
               }

               var7 = this.input.readTag();
            } while (var7 == this.tag);

            this.nextTag = var7;
            return;
         }

         if (var7 != 2) {
            throw InvalidProtocolBufferException.invalidWireType();
         }

         var7 = this.input.readUInt32();
         var7 = this.input.getTotalBytesRead() + var7;

         do {
            var1.add(this.input.readInt32());
         } while (this.input.getTotalBytesRead() < var7);

         this.requirePosition(var7);
      }
   }

   @Override
   public long readInt64() throws IOException {
      this.requireWireType(0);
      return this.input.readInt64();
   }

   @Override
   public void readInt64List(List<Long> var1) throws IOException {
      if (var1 instanceof LongArrayList) {
         LongArrayList var3 = (LongArrayList)var1;
         int var2 = WireFormat.getTagWireType(this.tag);
         if (var2 == 0) {
            do {
               var3.addLong(this.input.readInt64());
               if (this.input.isAtEnd()) {
                  return;
               }

               var2 = this.input.readTag();
            } while (var2 == this.tag);

            this.nextTag = var2;
            return;
         }

         if (var2 != 2) {
            throw InvalidProtocolBufferException.invalidWireType();
         }

         var2 = this.input.readUInt32();
         var2 = this.input.getTotalBytesRead() + var2;

         do {
            var3.addLong(this.input.readInt64());
         } while (this.input.getTotalBytesRead() < var2);

         this.requirePosition(var2);
      } else {
         int var7 = WireFormat.getTagWireType(this.tag);
         if (var7 == 0) {
            do {
               var1.add(this.input.readInt64());
               if (this.input.isAtEnd()) {
                  return;
               }

               var7 = this.input.readTag();
            } while (var7 == this.tag);

            this.nextTag = var7;
            return;
         }

         if (var7 != 2) {
            throw InvalidProtocolBufferException.invalidWireType();
         }

         var7 = this.input.readUInt32();
         var7 = this.input.getTotalBytesRead() + var7;

         do {
            var1.add(this.input.readInt64());
         } while (this.input.getTotalBytesRead() < var7);

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
      // 02: invokespecial com/google/protobuf/CodedInputStreamReader.requireWireType (I)V
      // 05: aload 0
      // 06: getfield com/google/protobuf/CodedInputStreamReader.input Lcom/google/protobuf/CodedInputStream;
      // 09: invokevirtual com/google/protobuf/CodedInputStream.readUInt32 ()I
      // 0c: istore 4
      // 0e: aload 0
      // 0f: getfield com/google/protobuf/CodedInputStreamReader.input Lcom/google/protobuf/CodedInputStream;
      // 12: iload 4
      // 14: invokevirtual com/google/protobuf/CodedInputStream.pushLimit (I)I
      // 17: istore 4
      // 19: aload 2
      // 1a: getfield com/google/protobuf/MapEntryLite$Metadata.defaultKey Ljava/lang/Object;
      // 1d: astore 7
      // 1f: aload 2
      // 20: getfield com/google/protobuf/MapEntryLite$Metadata.defaultValue Ljava/lang/Object;
      // 23: astore 8
      // 25: aload 0
      // 26: invokevirtual com/google/protobuf/CodedInputStreamReader.getFieldNumber ()I
      // 29: istore 5
      // 2b: iload 5
      // 2d: ldc 2147483647
      // 2f: if_icmpeq ae
      // 32: aload 0
      // 33: getfield com/google/protobuf/CodedInputStreamReader.input Lcom/google/protobuf/CodedInputStream;
      // 36: invokevirtual com/google/protobuf/CodedInputStream.isAtEnd ()Z
      // 39: istore 6
      // 3b: iload 6
      // 3d: ifeq 43
      // 40: goto ae
      // 43: iload 5
      // 45: bipush 1
      // 46: if_icmpeq 82
      // 49: iload 5
      // 4b: bipush 2
      // 4c: if_icmpeq 69
      // 4f: aload 0
      // 50: invokevirtual com/google/protobuf/CodedInputStreamReader.skipField ()Z
      // 53: ifeq 59
      // 56: goto 25
      // 59: new com/google/protobuf/InvalidProtocolBufferException
      // 5c: astore 9
      // 5e: aload 9
      // 60: ldc_w "Unable to parse map entry."
      // 63: invokespecial com/google/protobuf/InvalidProtocolBufferException.<init> (Ljava/lang/String;)V
      // 66: aload 9
      // 68: athrow
      // 69: aload 0
      // 6a: aload 2
      // 6b: getfield com/google/protobuf/MapEntryLite$Metadata.valueType Lcom/google/protobuf/WireFormat$FieldType;
      // 6e: aload 2
      // 6f: getfield com/google/protobuf/MapEntryLite$Metadata.defaultValue Ljava/lang/Object;
      // 72: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 75: aload 3
      // 76: invokespecial com/google/protobuf/CodedInputStreamReader.readField (Lcom/google/protobuf/WireFormat$FieldType;Ljava/lang/Class;Lcom/google/protobuf/ExtensionRegistryLite;)Ljava/lang/Object;
      // 79: astore 9
      // 7b: aload 9
      // 7d: astore 8
      // 7f: goto 25
      // 82: aload 0
      // 83: aload 2
      // 84: getfield com/google/protobuf/MapEntryLite$Metadata.keyType Lcom/google/protobuf/WireFormat$FieldType;
      // 87: aconst_null
      // 88: aconst_null
      // 89: invokespecial com/google/protobuf/CodedInputStreamReader.readField (Lcom/google/protobuf/WireFormat$FieldType;Ljava/lang/Class;Lcom/google/protobuf/ExtensionRegistryLite;)Ljava/lang/Object;
      // 8c: astore 9
      // 8e: aload 9
      // 90: astore 7
      // 92: goto 25
      // 95: astore 9
      // 97: aload 0
      // 98: invokevirtual com/google/protobuf/CodedInputStreamReader.skipField ()Z
      // 9b: ifeq a1
      // 9e: goto 25
      // a1: new com/google/protobuf/InvalidProtocolBufferException
      // a4: astore 1
      // a5: aload 1
      // a6: ldc_w "Unable to parse map entry."
      // a9: invokespecial com/google/protobuf/InvalidProtocolBufferException.<init> (Ljava/lang/String;)V
      // ac: aload 1
      // ad: athrow
      // ae: aload 1
      // af: aload 7
      // b1: aload 8
      // b3: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // b8: pop
      // b9: aload 0
      // ba: getfield com/google/protobuf/CodedInputStreamReader.input Lcom/google/protobuf/CodedInputStream;
      // bd: iload 4
      // bf: invokevirtual com/google/protobuf/CodedInputStream.popLimit (I)V
      // c2: return
      // c3: astore 1
      // c4: aload 0
      // c5: getfield com/google/protobuf/CodedInputStreamReader.input Lcom/google/protobuf/CodedInputStream;
      // c8: iload 4
      // ca: invokevirtual com/google/protobuf/CodedInputStream.popLimit (I)V
      // cd: aload 1
      // ce: athrow
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

         while (true) {
            var1.add(this.readMessage(var2, var3));
            if (this.input.isAtEnd() || this.nextTag != 0) {
               break;
            }

            int var4 = this.input.readTag();
            if (var4 != var5) {
               this.nextTag = var4;
               break;
            }
         }
      }
   }

   @Override
   public <T> void readMessageList(List<T> var1, Class<T> var2, ExtensionRegistryLite var3) throws IOException {
      this.readMessageList(var1, Protobuf.getInstance().schemaFor(var2), var3);
   }

   @Override
   public int readSFixed32() throws IOException {
      this.requireWireType(5);
      return this.input.readSFixed32();
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
               var4.addInt(this.input.readSFixed32());
               if (this.input.isAtEnd()) {
                  return;
               }

               var2 = this.input.readTag();
            } while (var2 == this.tag);

            this.nextTag = var2;
            return;
         }

         int var3 = this.input.readUInt32();
         this.verifyPackedFixed32Length(var3);
         var2 = this.input.getTotalBytesRead();

         do {
            var4.addInt(this.input.readSFixed32());
         } while (this.input.getTotalBytesRead() < var2 + var3);
      } else {
         int var7 = WireFormat.getTagWireType(this.tag);
         if (var7 != 2) {
            if (var7 != 5) {
               throw InvalidProtocolBufferException.invalidWireType();
            }

            do {
               var1.add(this.input.readSFixed32());
               if (this.input.isAtEnd()) {
                  return;
               }

               var7 = this.input.readTag();
            } while (var7 == this.tag);

            this.nextTag = var7;
            return;
         }

         int var10 = this.input.readUInt32();
         this.verifyPackedFixed32Length(var10);
         var7 = this.input.getTotalBytesRead();

         do {
            var1.add(this.input.readSFixed32());
         } while (this.input.getTotalBytesRead() < var7 + var10);
      }
   }

   @Override
   public long readSFixed64() throws IOException {
      this.requireWireType(1);
      return this.input.readSFixed64();
   }

   @Override
   public void readSFixed64List(List<Long> var1) throws IOException {
      if (var1 instanceof LongArrayList) {
         LongArrayList var4 = (LongArrayList)var1;
         int var2 = WireFormat.getTagWireType(this.tag);
         if (var2 == 1) {
            do {
               var4.addLong(this.input.readSFixed64());
               if (this.input.isAtEnd()) {
                  return;
               }

               var2 = this.input.readTag();
            } while (var2 == this.tag);

            this.nextTag = var2;
            return;
         }

         if (var2 != 2) {
            throw InvalidProtocolBufferException.invalidWireType();
         }

         var2 = this.input.readUInt32();
         this.verifyPackedFixed64Length(var2);
         int var3 = this.input.getTotalBytesRead();

         do {
            var4.addLong(this.input.readSFixed64());
         } while (this.input.getTotalBytesRead() < var3 + var2);
      } else {
         int var7 = WireFormat.getTagWireType(this.tag);
         if (var7 == 1) {
            do {
               var1.add(this.input.readSFixed64());
               if (this.input.isAtEnd()) {
                  return;
               }

               var7 = this.input.readTag();
            } while (var7 == this.tag);

            this.nextTag = var7;
            return;
         }

         if (var7 != 2) {
            throw InvalidProtocolBufferException.invalidWireType();
         }

         var7 = this.input.readUInt32();
         this.verifyPackedFixed64Length(var7);
         int var10 = this.input.getTotalBytesRead();

         do {
            var1.add(this.input.readSFixed64());
         } while (this.input.getTotalBytesRead() < var10 + var7);
      }
   }

   @Override
   public int readSInt32() throws IOException {
      this.requireWireType(0);
      return this.input.readSInt32();
   }

   @Override
   public void readSInt32List(List<Integer> var1) throws IOException {
      if (var1 instanceof IntArrayList) {
         IntArrayList var3 = (IntArrayList)var1;
         int var2 = WireFormat.getTagWireType(this.tag);
         if (var2 == 0) {
            do {
               var3.addInt(this.input.readSInt32());
               if (this.input.isAtEnd()) {
                  return;
               }

               var2 = this.input.readTag();
            } while (var2 == this.tag);

            this.nextTag = var2;
            return;
         }

         if (var2 != 2) {
            throw InvalidProtocolBufferException.invalidWireType();
         }

         var2 = this.input.readUInt32();
         var2 = this.input.getTotalBytesRead() + var2;

         do {
            var3.addInt(this.input.readSInt32());
         } while (this.input.getTotalBytesRead() < var2);

         this.requirePosition(var2);
      } else {
         int var7 = WireFormat.getTagWireType(this.tag);
         if (var7 == 0) {
            do {
               var1.add(this.input.readSInt32());
               if (this.input.isAtEnd()) {
                  return;
               }

               var7 = this.input.readTag();
            } while (var7 == this.tag);

            this.nextTag = var7;
            return;
         }

         if (var7 != 2) {
            throw InvalidProtocolBufferException.invalidWireType();
         }

         var7 = this.input.readUInt32();
         var7 = this.input.getTotalBytesRead() + var7;

         do {
            var1.add(this.input.readSInt32());
         } while (this.input.getTotalBytesRead() < var7);

         this.requirePosition(var7);
      }
   }

   @Override
   public long readSInt64() throws IOException {
      this.requireWireType(0);
      return this.input.readSInt64();
   }

   @Override
   public void readSInt64List(List<Long> var1) throws IOException {
      if (var1 instanceof LongArrayList) {
         LongArrayList var3 = (LongArrayList)var1;
         int var2 = WireFormat.getTagWireType(this.tag);
         if (var2 == 0) {
            do {
               var3.addLong(this.input.readSInt64());
               if (this.input.isAtEnd()) {
                  return;
               }

               var2 = this.input.readTag();
            } while (var2 == this.tag);

            this.nextTag = var2;
            return;
         }

         if (var2 != 2) {
            throw InvalidProtocolBufferException.invalidWireType();
         }

         var2 = this.input.readUInt32();
         var2 = this.input.getTotalBytesRead() + var2;

         do {
            var3.addLong(this.input.readSInt64());
         } while (this.input.getTotalBytesRead() < var2);

         this.requirePosition(var2);
      } else {
         int var7 = WireFormat.getTagWireType(this.tag);
         if (var7 == 0) {
            do {
               var1.add(this.input.readSInt64());
               if (this.input.isAtEnd()) {
                  return;
               }

               var7 = this.input.readTag();
            } while (var7 == this.tag);

            this.nextTag = var7;
            return;
         }

         if (var7 != 2) {
            throw InvalidProtocolBufferException.invalidWireType();
         }

         var7 = this.input.readUInt32();
         var7 = this.input.getTotalBytesRead() + var7;

         do {
            var1.add(this.input.readSInt64());
         } while (this.input.getTotalBytesRead() < var7);

         this.requirePosition(var7);
      }
   }

   @Override
   public String readString() throws IOException {
      this.requireWireType(2);
      return this.input.readString();
   }

   @Override
   public void readStringList(List<String> var1) throws IOException {
      this.readStringListInternal(var1, false);
   }

   public void readStringListInternal(List<String> var1, boolean var2) throws IOException {
      if (WireFormat.getTagWireType(this.tag) != 2) {
         throw InvalidProtocolBufferException.invalidWireType();
      } else if (var1 instanceof LazyStringList && !var2) {
         LazyStringList var5 = (LazyStringList)var1;

         int var6;
         do {
            var5.add(this.readBytes());
            if (this.input.isAtEnd()) {
               return;
            }

            var6 = this.input.readTag();
         } while (var6 == this.tag);

         this.nextTag = var6;
      } else {
         int var3;
         do {
            String var4;
            if (var2) {
               var4 = this.readStringRequireUtf8();
            } else {
               var4 = this.readString();
            }

            var1.add(var4);
            if (this.input.isAtEnd()) {
               return;
            }

            var3 = this.input.readTag();
         } while (var3 == this.tag);

         this.nextTag = var3;
      }
   }

   @Override
   public void readStringListRequireUtf8(List<String> var1) throws IOException {
      this.readStringListInternal(var1, true);
   }

   @Override
   public String readStringRequireUtf8() throws IOException {
      this.requireWireType(2);
      return this.input.readStringRequireUtf8();
   }

   @Override
   public int readUInt32() throws IOException {
      this.requireWireType(0);
      return this.input.readUInt32();
   }

   @Override
   public void readUInt32List(List<Integer> var1) throws IOException {
      if (var1 instanceof IntArrayList) {
         IntArrayList var3 = (IntArrayList)var1;
         int var2 = WireFormat.getTagWireType(this.tag);
         if (var2 == 0) {
            do {
               var3.addInt(this.input.readUInt32());
               if (this.input.isAtEnd()) {
                  return;
               }

               var2 = this.input.readTag();
            } while (var2 == this.tag);

            this.nextTag = var2;
            return;
         }

         if (var2 != 2) {
            throw InvalidProtocolBufferException.invalidWireType();
         }

         var2 = this.input.readUInt32();
         var2 = this.input.getTotalBytesRead() + var2;

         do {
            var3.addInt(this.input.readUInt32());
         } while (this.input.getTotalBytesRead() < var2);

         this.requirePosition(var2);
      } else {
         int var7 = WireFormat.getTagWireType(this.tag);
         if (var7 == 0) {
            do {
               var1.add(this.input.readUInt32());
               if (this.input.isAtEnd()) {
                  return;
               }

               var7 = this.input.readTag();
            } while (var7 == this.tag);

            this.nextTag = var7;
            return;
         }

         if (var7 != 2) {
            throw InvalidProtocolBufferException.invalidWireType();
         }

         var7 = this.input.readUInt32();
         var7 = this.input.getTotalBytesRead() + var7;

         do {
            var1.add(this.input.readUInt32());
         } while (this.input.getTotalBytesRead() < var7);

         this.requirePosition(var7);
      }
   }

   @Override
   public long readUInt64() throws IOException {
      this.requireWireType(0);
      return this.input.readUInt64();
   }

   @Override
   public void readUInt64List(List<Long> var1) throws IOException {
      if (var1 instanceof LongArrayList) {
         LongArrayList var3 = (LongArrayList)var1;
         int var2 = WireFormat.getTagWireType(this.tag);
         if (var2 == 0) {
            do {
               var3.addLong(this.input.readUInt64());
               if (this.input.isAtEnd()) {
                  return;
               }

               var2 = this.input.readTag();
            } while (var2 == this.tag);

            this.nextTag = var2;
            return;
         }

         if (var2 != 2) {
            throw InvalidProtocolBufferException.invalidWireType();
         }

         var2 = this.input.readUInt32();
         var2 = this.input.getTotalBytesRead() + var2;

         do {
            var3.addLong(this.input.readUInt64());
         } while (this.input.getTotalBytesRead() < var2);

         this.requirePosition(var2);
      } else {
         int var7 = WireFormat.getTagWireType(this.tag);
         if (var7 == 0) {
            do {
               var1.add(this.input.readUInt64());
               if (this.input.isAtEnd()) {
                  return;
               }

               var7 = this.input.readTag();
            } while (var7 == this.tag);

            this.nextTag = var7;
            return;
         }

         if (var7 != 2) {
            throw InvalidProtocolBufferException.invalidWireType();
         }

         var7 = this.input.readUInt32();
         var7 = this.input.getTotalBytesRead() + var7;

         do {
            var1.add(this.input.readUInt64());
         } while (this.input.getTotalBytesRead() < var7);

         this.requirePosition(var7);
      }
   }

   @Override
   public boolean shouldDiscardUnknownFields() {
      return this.input.shouldDiscardUnknownFields();
   }

   @Override
   public boolean skipField() throws IOException {
      if (!this.input.isAtEnd()) {
         int var1 = this.tag;
         if (var1 != this.endGroupTag) {
            return this.input.skipField(var1);
         }
      }

      return false;
   }
}
