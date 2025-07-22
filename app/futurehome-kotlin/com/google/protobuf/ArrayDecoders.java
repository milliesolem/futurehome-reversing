package com.google.protobuf;

import java.io.IOException;

@CheckReturnValue
final class ArrayDecoders {
   private ArrayDecoders() {
   }

   static int decodeBoolList(int var0, byte[] var1, int var2, int var3, Internal.ProtobufList<?> var4, ArrayDecoders.Registers var5) {
      BooleanArrayList var9 = (BooleanArrayList)var4;
      var2 = decodeVarint64(var1, var2, var5);
      boolean var7;
      if (var5.long1 != 0L) {
         var7 = true;
      } else {
         var7 = false;
      }

      var9.addBoolean(var7);

      while (var2 < var3) {
         int var6 = decodeVarint32(var1, var2, var5);
         if (var0 != var5.int1) {
            break;
         }

         var2 = decodeVarint64(var1, var6, var5);
         if (var5.long1 != 0L) {
            var7 = true;
         } else {
            var7 = false;
         }

         var9.addBoolean(var7);
      }

      return var2;
   }

   static int decodeBytes(byte[] var0, int var1, ArrayDecoders.Registers var2) throws InvalidProtocolBufferException {
      var1 = decodeVarint32(var0, var1, var2);
      int var3 = var2.int1;
      if (var3 >= 0) {
         if (var3 <= var0.length - var1) {
            if (var3 == 0) {
               var2.object1 = ByteString.EMPTY;
               return var1;
            } else {
               var2.object1 = ByteString.copyFrom(var0, var1, var3);
               return var1 + var3;
            }
         } else {
            throw InvalidProtocolBufferException.truncatedMessage();
         }
      } else {
         throw InvalidProtocolBufferException.negativeSize();
      }
   }

   // $VF: Irreducible bytecode was duplicated to produce valid code
   static int decodeBytesList(int var0, byte[] var1, int var2, int var3, Internal.ProtobufList<?> var4, ArrayDecoders.Registers var5) throws InvalidProtocolBufferException {
      var2 = decodeVarint32(var1, var2, var5);
      int var6 = var5.int1;
      if (var6 >= 0) {
         if (var6 > var1.length - var2) {
            throw InvalidProtocolBufferException.truncatedMessage();
         } else {
            if (var6 == 0) {
               var4.add(ByteString.EMPTY);
            } else {
               var4.add(ByteString.copyFrom(var1, var2, var6));
               var2 += var6;
            }

            while (var2 < var3) {
               var6 = decodeVarint32(var1, var2, var5);
               if (var0 != var5.int1) {
                  break;
               }

               var2 = decodeVarint32(var1, var6, var5);
               var6 = var5.int1;
               if (var6 < 0) {
                  throw InvalidProtocolBufferException.negativeSize();
               }

               if (var6 > var1.length - var2) {
                  throw InvalidProtocolBufferException.truncatedMessage();
               }

               if (var6 == 0) {
                  var4.add(ByteString.EMPTY);
               } else {
                  var4.add(ByteString.copyFrom(var1, var2, var6));
                  var2 += var6;
               }
            }

            return var2;
         }
      } else {
         throw InvalidProtocolBufferException.negativeSize();
      }
   }

   static double decodeDouble(byte[] var0, int var1) {
      return Double.longBitsToDouble(decodeFixed64(var0, var1));
   }

   static int decodeDoubleList(int var0, byte[] var1, int var2, int var3, Internal.ProtobufList<?> var4, ArrayDecoders.Registers var5) {
      DoubleArrayList var8 = (DoubleArrayList)var4;
      var8.addDouble(decodeDouble(var1, var2));
      var2 += 8;

      while (var2 < var3) {
         int var6 = decodeVarint32(var1, var2, var5);
         if (var0 != var5.int1) {
            break;
         }

         var8.addDouble(decodeDouble(var1, var6));
         var2 = var6 + 8;
      }

      return var2;
   }

   static int decodeExtension(
      int var0,
      byte[] var1,
      int var2,
      int var3,
      GeneratedMessageLite.ExtendableMessage<?, ?> var4,
      GeneratedMessageLite.GeneratedExtension<?, ?> var5,
      UnknownFieldSchema<UnknownFieldSetLite, UnknownFieldSetLite> var6,
      ArrayDecoders.Registers var7
   ) throws IOException {
      FieldSet var11 = var4.extensions;
      int var8 = var0 >>> 3;
      if (var5.descriptor.isRepeated() && var5.descriptor.isPacked()) {
         switch (<unrepresentable>.$SwitchMap$com$google$protobuf$WireFormat$FieldType[var5.getLiteType().ordinal()]) {
            case 1:
               DoubleArrayList var30 = new DoubleArrayList();
               var0 = decodePackedDoubleList(var1, var2, var30, var7);
               var11.setField(var5.descriptor, var30);
               break;
            case 2:
               FloatArrayList var29 = new FloatArrayList();
               var0 = decodePackedFloatList(var1, var2, var29, var7);
               var11.setField(var5.descriptor, var29);
               break;
            case 3:
            case 4:
               LongArrayList var28 = new LongArrayList();
               var0 = decodePackedVarint64List(var1, var2, var28, var7);
               var11.setField(var5.descriptor, var28);
               break;
            case 5:
            case 6:
               IntArrayList var27 = new IntArrayList();
               var0 = decodePackedVarint32List(var1, var2, var27, var7);
               var11.setField(var5.descriptor, var27);
               break;
            case 7:
            case 8:
               LongArrayList var26 = new LongArrayList();
               var0 = decodePackedFixed64List(var1, var2, var26, var7);
               var11.setField(var5.descriptor, var26);
               break;
            case 9:
            case 10:
               IntArrayList var25 = new IntArrayList();
               var0 = decodePackedFixed32List(var1, var2, var25, var7);
               var11.setField(var5.descriptor, var25);
               break;
            case 11:
               BooleanArrayList var24 = new BooleanArrayList();
               var0 = decodePackedBoolList(var1, var2, var24, var7);
               var11.setField(var5.descriptor, var24);
               break;
            case 12:
               IntArrayList var23 = new IntArrayList();
               var0 = decodePackedSInt32List(var1, var2, var23, var7);
               var11.setField(var5.descriptor, var23);
               break;
            case 13:
               LongArrayList var22 = new LongArrayList();
               var0 = decodePackedSInt64List(var1, var2, var22, var7);
               var11.setField(var5.descriptor, var22);
               break;
            case 14:
               IntArrayList var35 = new IntArrayList();
               var0 = decodePackedVarint32List(var1, var2, var35, var7);
               SchemaUtil.filterUnknownEnumList(var4, var8, var35, var5.descriptor.getEnumType(), null, var6);
               var11.setField(var5.descriptor, var35);
               break;
            default:
               StringBuilder var19 = new StringBuilder("Type cannot be packed: ");
               var19.append(var5.descriptor.getLiteType());
               throw new IllegalStateException(var19.toString());
         }
      } else {
         WireFormat.FieldType var13 = var5.getLiteType();
         WireFormat.FieldType var12 = WireFormat.FieldType.ENUM;
         Schema var10 = null;
         Object var18;
         if (var13 == var12) {
            var2 = decodeVarint32(var1, var2, var7);
            if (var5.descriptor.getEnumType().findValueByNumber(var7.int1) == null) {
               SchemaUtil.storeUnknownEnum(var4, var8, var7.int1, null, var6);
               return var2;
            }

            var18 = var7.int1;
         } else {
            label138: {
               label102: {
                  switch (<unrepresentable>.$SwitchMap$com$google$protobuf$WireFormat$FieldType[var5.getLiteType().ordinal()]) {
                     case 1:
                        var18 = decodeDouble(var1, var2);
                        break;
                     case 2:
                        var18 = decodeFloat(var1, var2);
                        break label102;
                     case 3:
                     case 4:
                        var2 = decodeVarint64(var1, var2, var7);
                        var18 = var7.long1;
                        break label138;
                     case 5:
                     case 6:
                        var2 = decodeVarint32(var1, var2, var7);
                        var18 = var7.int1;
                        break label138;
                     case 7:
                     case 8:
                        var18 = decodeFixed64(var1, var2);
                        break;
                     case 9:
                     case 10:
                        var18 = decodeFixed32(var1, var2);
                        break label102;
                     case 11:
                        var2 = decodeVarint64(var1, var2, var7);
                        boolean var9;
                        if (var7.long1 != 0L) {
                           var9 = true;
                        } else {
                           var9 = false;
                        }

                        var18 = var9;
                        break label138;
                     case 12:
                        var2 = decodeVarint32(var1, var2, var7);
                        var18 = CodedInputStream.decodeZigZag32(var7.int1);
                        break label138;
                     case 13:
                        var2 = decodeVarint64(var1, var2, var7);
                        var18 = CodedInputStream.decodeZigZag64(var7.long1);
                        break label138;
                     case 14:
                        throw new IllegalStateException("Shouldn't reach here.");
                     case 15:
                        var2 = decodeBytes(var1, var2, var7);
                        var18 = var7.object1;
                        break label138;
                     case 16:
                        var2 = decodeString(var1, var2, var7);
                        var18 = var7.object1;
                        break label138;
                     case 17:
                        var0 = var8 << 3 | 4;
                        var10 = Protobuf.getInstance().schemaFor(var5.getMessageDefaultInstance().getClass());
                        if (var5.isRepeated()) {
                           var0 = decodeGroupField(var10, var1, var2, var3, var0, var7);
                           var11.addRepeatedField(var5.descriptor, var7.object1);
                        } else {
                           Object var32 = var11.getField(var5.descriptor);
                           Object var21 = var32;
                           if (var32 == null) {
                              var21 = var10.newInstance();
                              var11.setField(var5.descriptor, var21);
                           }

                           var0 = mergeGroupField(var21, var10, var1, var2, var3, var0, var7);
                        }

                        return var0;
                     case 18:
                        var10 = Protobuf.getInstance().schemaFor(var5.getMessageDefaultInstance().getClass());
                        if (var5.isRepeated()) {
                           var0 = decodeMessageField(var10, var1, var2, var3, var7);
                           var11.addRepeatedField(var5.descriptor, var7.object1);
                        } else {
                           Object var31 = var11.getField(var5.descriptor);
                           Object var20 = var31;
                           if (var31 == null) {
                              var20 = var10.newInstance();
                              var11.setField(var5.descriptor, var20);
                           }

                           var0 = mergeMessageField(var20, var10, var1, var2, var3, var7);
                        }

                        return var0;
                     default:
                        var18 = var10;
                        break label138;
                  }

                  var2 += 8;
                  break label138;
               }

               var2 += 4;
            }
         }

         if (var5.isRepeated()) {
            var11.addRepeatedField(var5.descriptor, var18);
         } else {
            var11.setField(var5.descriptor, var18);
         }

         var0 = var2;
      }

      return var0;
   }

   static int decodeExtensionOrUnknownField(
      int var0,
      byte[] var1,
      int var2,
      int var3,
      Object var4,
      MessageLite var5,
      UnknownFieldSchema<UnknownFieldSetLite, UnknownFieldSetLite> var6,
      ArrayDecoders.Registers var7
   ) throws IOException {
      GeneratedMessageLite.GeneratedExtension var9 = var7.extensionRegistry.findLiteExtensionByNumber(var5, var0 >>> 3);
      if (var9 == null) {
         return decodeUnknownField(var0, var1, var2, var3, MessageSchema.getMutableUnknownFields(var4), var7);
      } else {
         var4 = var4;
         var4.ensureExtensionsAreMutable();
         return decodeExtension(var0, var1, var2, var3, var4, var9, var6, var7);
      }
   }

   static int decodeFixed32(byte[] var0, int var1) {
      byte var4 = var0[var1];
      byte var2 = var0[var1 + 1];
      byte var3 = var0[var1 + 2];
      return (var0[var1 + 3] & 0xFF) << 24 | var4 & 0xFF | (var2 & 0xFF) << 8 | (var3 & 0xFF) << 16;
   }

   static int decodeFixed32List(int var0, byte[] var1, int var2, int var3, Internal.ProtobufList<?> var4, ArrayDecoders.Registers var5) {
      IntArrayList var8 = (IntArrayList)var4;
      var8.addInt(decodeFixed32(var1, var2));
      var2 += 4;

      while (var2 < var3) {
         int var6 = decodeVarint32(var1, var2, var5);
         if (var0 != var5.int1) {
            break;
         }

         var8.addInt(decodeFixed32(var1, var6));
         var2 = var6 + 4;
      }

      return var2;
   }

   static long decodeFixed64(byte[] var0, int var1) {
      long var10 = var0[var1];
      long var8 = var0[var1 + 1];
      long var6 = var0[var1 + 2];
      long var14 = var0[var1 + 3];
      long var2 = var0[var1 + 4];
      long var4 = var0[var1 + 5];
      long var12 = var0[var1 + 6];
      return (var0[var1 + 7] & 255L) << 56
         | var10 & 255L
         | (var8 & 255L) << 8
         | (var6 & 255L) << 16
         | (var14 & 255L) << 24
         | (var2 & 255L) << 32
         | (var4 & 255L) << 40
         | (var12 & 255L) << 48;
   }

   static int decodeFixed64List(int var0, byte[] var1, int var2, int var3, Internal.ProtobufList<?> var4, ArrayDecoders.Registers var5) {
      LongArrayList var8 = (LongArrayList)var4;
      var8.addLong(decodeFixed64(var1, var2));
      var2 += 8;

      while (var2 < var3) {
         int var6 = decodeVarint32(var1, var2, var5);
         if (var0 != var5.int1) {
            break;
         }

         var8.addLong(decodeFixed64(var1, var6));
         var2 = var6 + 8;
      }

      return var2;
   }

   static float decodeFloat(byte[] var0, int var1) {
      return Float.intBitsToFloat(decodeFixed32(var0, var1));
   }

   static int decodeFloatList(int var0, byte[] var1, int var2, int var3, Internal.ProtobufList<?> var4, ArrayDecoders.Registers var5) {
      FloatArrayList var8 = (FloatArrayList)var4;
      var8.addFloat(decodeFloat(var1, var2));
      var2 += 4;

      while (var2 < var3) {
         int var6 = decodeVarint32(var1, var2, var5);
         if (var0 != var5.int1) {
            break;
         }

         var8.addFloat(decodeFloat(var1, var6));
         var2 = var6 + 4;
      }

      return var2;
   }

   static int decodeGroupField(Schema var0, byte[] var1, int var2, int var3, int var4, ArrayDecoders.Registers var5) throws IOException {
      Object var6 = var0.newInstance();
      var2 = mergeGroupField(var6, var0, var1, var2, var3, var4, var5);
      var0.makeImmutable(var6);
      var5.object1 = var6;
      return var2;
   }

   static int decodeGroupList(Schema var0, int var1, byte[] var2, int var3, int var4, Internal.ProtobufList<?> var5, ArrayDecoders.Registers var6) throws IOException {
      int var7 = var1 & -8 | 4;
      var3 = decodeGroupField(var0, var2, var3, var4, var7, var6);
      var5.add(var6.object1);

      while (var3 < var4) {
         int var8 = decodeVarint32(var2, var3, var6);
         if (var1 != var6.int1) {
            break;
         }

         var3 = decodeGroupField(var0, var2, var8, var4, var7, var6);
         var5.add(var6.object1);
      }

      return var3;
   }

   static int decodeMessageField(Schema var0, byte[] var1, int var2, int var3, ArrayDecoders.Registers var4) throws IOException {
      Object var5 = var0.newInstance();
      var2 = mergeMessageField(var5, var0, var1, var2, var3, var4);
      var0.makeImmutable(var5);
      var4.object1 = var5;
      return var2;
   }

   static int decodeMessageList(Schema<?> var0, int var1, byte[] var2, int var3, int var4, Internal.ProtobufList<?> var5, ArrayDecoders.Registers var6) throws IOException {
      var3 = decodeMessageField(var0, var2, var3, var4, var6);
      var5.add(var6.object1);

      while (var3 < var4) {
         int var7 = decodeVarint32(var2, var3, var6);
         if (var1 != var6.int1) {
            break;
         }

         var3 = decodeMessageField(var0, var2, var7, var4, var6);
         var5.add(var6.object1);
      }

      return var3;
   }

   static int decodePackedBoolList(byte[] var0, int var1, Internal.ProtobufList<?> var2, ArrayDecoders.Registers var3) throws IOException {
      BooleanArrayList var7 = (BooleanArrayList)var2;
      var1 = decodeVarint32(var0, var1, var3);
      int var4 = var3.int1 + var1;

      while (var1 < var4) {
         var1 = decodeVarint64(var0, var1, var3);
         boolean var5;
         if (var3.long1 != 0L) {
            var5 = true;
         } else {
            var5 = false;
         }

         var7.addBoolean(var5);
      }

      if (var1 == var4) {
         return var1;
      } else {
         throw InvalidProtocolBufferException.truncatedMessage();
      }
   }

   static int decodePackedDoubleList(byte[] var0, int var1, Internal.ProtobufList<?> var2, ArrayDecoders.Registers var3) throws IOException {
      DoubleArrayList var6 = (DoubleArrayList)var2;
      var1 = decodeVarint32(var0, var1, var3);

      int var4;
      for (var4 = var3.int1 + var1; var1 < var4; var1 += 8) {
         var6.addDouble(decodeDouble(var0, var1));
      }

      if (var1 == var4) {
         return var1;
      } else {
         throw InvalidProtocolBufferException.truncatedMessage();
      }
   }

   static int decodePackedFixed32List(byte[] var0, int var1, Internal.ProtobufList<?> var2, ArrayDecoders.Registers var3) throws IOException {
      IntArrayList var6 = (IntArrayList)var2;
      var1 = decodeVarint32(var0, var1, var3);

      int var4;
      for (var4 = var3.int1 + var1; var1 < var4; var1 += 4) {
         var6.addInt(decodeFixed32(var0, var1));
      }

      if (var1 == var4) {
         return var1;
      } else {
         throw InvalidProtocolBufferException.truncatedMessage();
      }
   }

   static int decodePackedFixed64List(byte[] var0, int var1, Internal.ProtobufList<?> var2, ArrayDecoders.Registers var3) throws IOException {
      LongArrayList var6 = (LongArrayList)var2;
      var1 = decodeVarint32(var0, var1, var3);

      int var4;
      for (var4 = var3.int1 + var1; var1 < var4; var1 += 8) {
         var6.addLong(decodeFixed64(var0, var1));
      }

      if (var1 == var4) {
         return var1;
      } else {
         throw InvalidProtocolBufferException.truncatedMessage();
      }
   }

   static int decodePackedFloatList(byte[] var0, int var1, Internal.ProtobufList<?> var2, ArrayDecoders.Registers var3) throws IOException {
      FloatArrayList var6 = (FloatArrayList)var2;
      var1 = decodeVarint32(var0, var1, var3);

      int var4;
      for (var4 = var3.int1 + var1; var1 < var4; var1 += 4) {
         var6.addFloat(decodeFloat(var0, var1));
      }

      if (var1 == var4) {
         return var1;
      } else {
         throw InvalidProtocolBufferException.truncatedMessage();
      }
   }

   static int decodePackedSInt32List(byte[] var0, int var1, Internal.ProtobufList<?> var2, ArrayDecoders.Registers var3) throws IOException {
      IntArrayList var6 = (IntArrayList)var2;
      var1 = decodeVarint32(var0, var1, var3);
      int var4 = var3.int1 + var1;

      while (var1 < var4) {
         var1 = decodeVarint32(var0, var1, var3);
         var6.addInt(CodedInputStream.decodeZigZag32(var3.int1));
      }

      if (var1 == var4) {
         return var1;
      } else {
         throw InvalidProtocolBufferException.truncatedMessage();
      }
   }

   static int decodePackedSInt64List(byte[] var0, int var1, Internal.ProtobufList<?> var2, ArrayDecoders.Registers var3) throws IOException {
      LongArrayList var6 = (LongArrayList)var2;
      var1 = decodeVarint32(var0, var1, var3);
      int var4 = var3.int1 + var1;

      while (var1 < var4) {
         var1 = decodeVarint64(var0, var1, var3);
         var6.addLong(CodedInputStream.decodeZigZag64(var3.long1));
      }

      if (var1 == var4) {
         return var1;
      } else {
         throw InvalidProtocolBufferException.truncatedMessage();
      }
   }

   static int decodePackedVarint32List(byte[] var0, int var1, Internal.ProtobufList<?> var2, ArrayDecoders.Registers var3) throws IOException {
      IntArrayList var6 = (IntArrayList)var2;
      var1 = decodeVarint32(var0, var1, var3);
      int var4 = var3.int1 + var1;

      while (var1 < var4) {
         var1 = decodeVarint32(var0, var1, var3);
         var6.addInt(var3.int1);
      }

      if (var1 == var4) {
         return var1;
      } else {
         throw InvalidProtocolBufferException.truncatedMessage();
      }
   }

   static int decodePackedVarint64List(byte[] var0, int var1, Internal.ProtobufList<?> var2, ArrayDecoders.Registers var3) throws IOException {
      LongArrayList var6 = (LongArrayList)var2;
      var1 = decodeVarint32(var0, var1, var3);
      int var4 = var3.int1 + var1;

      while (var1 < var4) {
         var1 = decodeVarint64(var0, var1, var3);
         var6.addLong(var3.long1);
      }

      if (var1 == var4) {
         return var1;
      } else {
         throw InvalidProtocolBufferException.truncatedMessage();
      }
   }

   static int decodeSInt32List(int var0, byte[] var1, int var2, int var3, Internal.ProtobufList<?> var4, ArrayDecoders.Registers var5) {
      IntArrayList var8 = (IntArrayList)var4;
      var2 = decodeVarint32(var1, var2, var5);
      var8.addInt(CodedInputStream.decodeZigZag32(var5.int1));

      while (var2 < var3) {
         int var6 = decodeVarint32(var1, var2, var5);
         if (var0 != var5.int1) {
            break;
         }

         var2 = decodeVarint32(var1, var6, var5);
         var8.addInt(CodedInputStream.decodeZigZag32(var5.int1));
      }

      return var2;
   }

   static int decodeSInt64List(int var0, byte[] var1, int var2, int var3, Internal.ProtobufList<?> var4, ArrayDecoders.Registers var5) {
      LongArrayList var8 = (LongArrayList)var4;
      var2 = decodeVarint64(var1, var2, var5);
      var8.addLong(CodedInputStream.decodeZigZag64(var5.long1));

      while (var2 < var3) {
         int var6 = decodeVarint32(var1, var2, var5);
         if (var0 != var5.int1) {
            break;
         }

         var2 = decodeVarint64(var1, var6, var5);
         var8.addLong(CodedInputStream.decodeZigZag64(var5.long1));
      }

      return var2;
   }

   static int decodeString(byte[] var0, int var1, ArrayDecoders.Registers var2) throws InvalidProtocolBufferException {
      int var3 = decodeVarint32(var0, var1, var2);
      var1 = var2.int1;
      if (var1 >= 0) {
         if (var1 == 0) {
            var2.object1 = "";
            return var3;
         } else {
            var2.object1 = new String(var0, var3, var1, Internal.UTF_8);
            return var3 + var1;
         }
      } else {
         throw InvalidProtocolBufferException.negativeSize();
      }
   }

   // $VF: Irreducible bytecode was duplicated to produce valid code
   static int decodeStringList(int var0, byte[] var1, int var2, int var3, Internal.ProtobufList<?> var4, ArrayDecoders.Registers var5) throws InvalidProtocolBufferException {
      var2 = decodeVarint32(var1, var2, var5);
      int var6 = var5.int1;
      if (var6 < 0) {
         throw InvalidProtocolBufferException.negativeSize();
      } else {
         if (var6 == 0) {
            var4.add("");
         } else {
            var4.add(new String(var1, var2, var6, Internal.UTF_8));
            var2 += var6;
         }

         while (var2 < var3) {
            var6 = decodeVarint32(var1, var2, var5);
            if (var0 != var5.int1) {
               break;
            }

            var2 = decodeVarint32(var1, var6, var5);
            var6 = var5.int1;
            if (var6 < 0) {
               throw InvalidProtocolBufferException.negativeSize();
            }

            if (var6 == 0) {
               var4.add("");
            } else {
               var4.add(new String(var1, var2, var6, Internal.UTF_8));
               var2 += var6;
            }
         }

         return var2;
      }
   }

   static int decodeStringListRequireUtf8(int var0, byte[] var1, int var2, int var3, Internal.ProtobufList<?> var4, ArrayDecoders.Registers var5) throws InvalidProtocolBufferException {
      int var6 = decodeVarint32(var1, var2, var5);
      int var7 = var5.int1;
      if (var7 < 0) {
         throw InvalidProtocolBufferException.negativeSize();
      } else {
         if (var7 == 0) {
            var4.add("");
            var2 = var6;
         } else {
            var2 = var6 + var7;
            if (!Utf8.isValidUtf8(var1, var6, var2)) {
               throw InvalidProtocolBufferException.invalidUtf8();
            }

            var4.add(new String(var1, var6, var7, Internal.UTF_8));
         }

         while (var2 < var3) {
            var6 = decodeVarint32(var1, var2, var5);
            if (var0 != var5.int1) {
               break;
            }

            var2 = decodeVarint32(var1, var6, var5);
            var7 = var5.int1;
            if (var7 < 0) {
               throw InvalidProtocolBufferException.negativeSize();
            }

            if (var7 == 0) {
               var4.add("");
            } else {
               var6 = var2 + var7;
               if (!Utf8.isValidUtf8(var1, var2, var6)) {
                  throw InvalidProtocolBufferException.invalidUtf8();
               }

               var4.add(new String(var1, var2, var7, Internal.UTF_8));
               var2 = var6;
            }
         }

         return var2;
      }
   }

   static int decodeStringRequireUtf8(byte[] var0, int var1, ArrayDecoders.Registers var2) throws InvalidProtocolBufferException {
      var1 = decodeVarint32(var0, var1, var2);
      int var3 = var2.int1;
      if (var3 >= 0) {
         if (var3 == 0) {
            var2.object1 = "";
            return var1;
         } else {
            var2.object1 = Utf8.decodeUtf8(var0, var1, var3);
            return var1 + var3;
         }
      } else {
         throw InvalidProtocolBufferException.negativeSize();
      }
   }

   static int decodeUnknownField(int var0, byte[] var1, int var2, int var3, UnknownFieldSetLite var4, ArrayDecoders.Registers var5) throws InvalidProtocolBufferException {
      if (WireFormat.getTagFieldNumber(var0) == 0) {
         throw InvalidProtocolBufferException.invalidTag();
      } else {
         int var6 = WireFormat.getTagWireType(var0);
         if (var6 == 0) {
            var2 = decodeVarint64(var1, var2, var5);
            var4.storeField(var0, var5.long1);
            return var2;
         } else if (var6 == 1) {
            var4.storeField(var0, decodeFixed64(var1, var2));
            return var2 + 8;
         } else if (var6 == 2) {
            var2 = decodeVarint32(var1, var2, var5);
            var3 = var5.int1;
            if (var3 >= 0) {
               if (var3 <= var1.length - var2) {
                  if (var3 == 0) {
                     var4.storeField(var0, ByteString.EMPTY);
                  } else {
                     var4.storeField(var0, ByteString.copyFrom(var1, var2, var3));
                  }

                  return var2 + var3;
               } else {
                  throw InvalidProtocolBufferException.truncatedMessage();
               }
            } else {
               throw InvalidProtocolBufferException.negativeSize();
            }
         } else if (var6 != 3) {
            if (var6 == 5) {
               var4.storeField(var0, decodeFixed32(var1, var2));
               return var2 + 4;
            } else {
               throw InvalidProtocolBufferException.invalidTag();
            }
         } else {
            UnknownFieldSetLite var9 = UnknownFieldSetLite.newInstance();
            int var8 = var0 & -8 | 4;
            var6 = 0;

            int var7;
            while (true) {
               var7 = var2;
               if (var2 >= var3) {
                  break;
               }

               var7 = decodeVarint32(var1, var2, var5);
               var6 = var5.int1;
               if (var6 == var8) {
                  break;
               }

               var2 = decodeUnknownField(var6, var1, var7, var3, var9, var5);
            }

            if (var7 <= var3 && var6 == var8) {
               var4.storeField(var0, var9);
               return var7;
            } else {
               throw InvalidProtocolBufferException.parseFailure();
            }
         }
      }
   }

   static int decodeVarint32(int var0, byte[] var1, int var2, ArrayDecoders.Registers var3) {
      var0 &= 127;
      int var4 = var2 + 1;
      int var5 = var1[var2];
      if (var5 >= 0) {
         var3.int1 = var0 | var5 << 7;
         return var4;
      } else {
         var5 = var0 | (var5 & 127) << 7;
         var0 = var2 + 2;
         int var11 = var1[var4];
         if (var11 >= 0) {
            var3.int1 = var5 | var11 << 14;
            return var0;
         } else {
            var11 = var5 | (var11 & 127) << 14;
            var5 = var2 + 3;
            int var8 = var1[var0];
            if (var8 >= 0) {
               var3.int1 = var11 | var8 << 21;
               return var5;
            } else {
               var11 |= (var8 & 127) << 21;
               var8 = var2 + 4;
               byte var16 = var1[var5];
               if (var16 >= 0) {
                  var3.int1 = var11 | var16 << 28;
                  return var8;
               } else {
                  while (true) {
                     var2 = var8 + 1;
                     if (var1[var8] >= 0) {
                        var3.int1 = var11 | (var16 & 127) << 28;
                        return var2;
                     }

                     var8 = var2;
                  }
               }
            }
         }
      }
   }

   static int decodeVarint32(byte[] var0, int var1, ArrayDecoders.Registers var2) {
      int var3 = var1 + 1;
      byte var4 = var0[var1];
      if (var4 >= 0) {
         var2.int1 = var4;
         return var3;
      } else {
         return decodeVarint32(var4, var0, var3, var2);
      }
   }

   static int decodeVarint32List(int var0, byte[] var1, int var2, int var3, Internal.ProtobufList<?> var4, ArrayDecoders.Registers var5) {
      IntArrayList var8 = (IntArrayList)var4;
      var2 = decodeVarint32(var1, var2, var5);
      var8.addInt(var5.int1);

      while (var2 < var3) {
         int var6 = decodeVarint32(var1, var2, var5);
         if (var0 != var5.int1) {
            break;
         }

         var2 = decodeVarint32(var1, var6, var5);
         var8.addInt(var5.int1);
      }

      return var2;
   }

   static int decodeVarint64(long var0, byte[] var2, int var3, ArrayDecoders.Registers var4) {
      int var5 = var3 + 1;
      byte var6 = var2[var3];
      var0 = var0 & 127L | (long)(var6 & 127) << 7;

      for (byte var8 = 7; var6 < 0; var5++) {
         var6 = var2[var5];
         var8 += 7;
         var0 |= (long)(var6 & 127) << var8;
      }

      var4.long1 = var0;
      return var5;
   }

   static int decodeVarint64(byte[] var0, int var1, ArrayDecoders.Registers var2) {
      int var3 = var1 + 1;
      long var4 = var0[var1];
      if (var4 >= 0L) {
         var2.long1 = var4;
         return var3;
      } else {
         return decodeVarint64(var4, var0, var3, var2);
      }
   }

   static int decodeVarint64List(int var0, byte[] var1, int var2, int var3, Internal.ProtobufList<?> var4, ArrayDecoders.Registers var5) {
      LongArrayList var8 = (LongArrayList)var4;
      var2 = decodeVarint64(var1, var2, var5);
      var8.addLong(var5.long1);

      while (var2 < var3) {
         int var6 = decodeVarint32(var1, var2, var5);
         if (var0 != var5.int1) {
            break;
         }

         var2 = decodeVarint64(var1, var6, var5);
         var8.addLong(var5.long1);
      }

      return var2;
   }

   static int mergeGroupField(Object var0, Schema var1, byte[] var2, int var3, int var4, int var5, ArrayDecoders.Registers var6) throws IOException {
      var3 = ((MessageSchema)var1).parseMessage(var0, var2, var3, var4, var5, var6);
      var6.object1 = var0;
      return var3;
   }

   static int mergeMessageField(Object var0, Schema var1, byte[] var2, int var3, int var4, ArrayDecoders.Registers var5) throws IOException {
      int var7 = var3 + 1;
      byte var8 = var2[var3];
      var3 = var7;
      int var6 = var8;
      if (var8 < 0) {
         var3 = decodeVarint32(var8, var2, var7, var5);
         var6 = var5.int1;
      }

      if (var6 >= 0 && var6 <= var4 - var3) {
         var4 = var6 + var3;
         var1.mergeFrom(var0, var2, var3, var4, var5);
         var5.object1 = var0;
         return var4;
      } else {
         throw InvalidProtocolBufferException.truncatedMessage();
      }
   }

   static int skipField(int var0, byte[] var1, int var2, int var3, ArrayDecoders.Registers var4) throws InvalidProtocolBufferException {
      if (WireFormat.getTagFieldNumber(var0) == 0) {
         throw InvalidProtocolBufferException.invalidTag();
      } else {
         int var5 = WireFormat.getTagWireType(var0);
         if (var5 == 0) {
            return decodeVarint64(var1, var2, var4);
         } else if (var5 == 1) {
            return var2 + 8;
         } else if (var5 == 2) {
            return decodeVarint32(var1, var2, var4) + var4.int1;
         } else if (var5 != 3) {
            if (var5 == 5) {
               return var2 + 4;
            } else {
               throw InvalidProtocolBufferException.invalidTag();
            }
         } else {
            int var6 = var0 & -8 | 4;
            var0 = 0;

            while (true) {
               var5 = var2;
               if (var2 >= var3) {
                  break;
               }

               var5 = decodeVarint32(var1, var2, var4);
               var0 = var4.int1;
               if (var0 == var6) {
                  break;
               }

               var2 = skipField(var0, var1, var5, var3, var4);
            }

            if (var5 <= var3 && var0 == var6) {
               return var5;
            } else {
               throw InvalidProtocolBufferException.parseFailure();
            }
         }
      }
   }

   static final class Registers {
      public final ExtensionRegistryLite extensionRegistry;
      public int int1;
      public long long1;
      public Object object1;

      Registers() {
         this.extensionRegistry = ExtensionRegistryLite.getEmptyRegistry();
      }

      Registers(ExtensionRegistryLite var1) {
         var1.getClass();
         this.extensionRegistry = var1;
      }
   }
}
