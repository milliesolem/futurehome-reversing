package com.google.protobuf;

import java.io.IOException;
import java.util.Map.Entry;

@CheckReturnValue
final class MessageSetSchema<T> implements Schema<T> {
   private final MessageLite defaultInstance;
   private final ExtensionSchema<?> extensionSchema;
   private final boolean hasExtensions;
   private final UnknownFieldSchema<?, ?> unknownFieldSchema;

   private MessageSetSchema(UnknownFieldSchema<?, ?> var1, ExtensionSchema<?> var2, MessageLite var3) {
      this.unknownFieldSchema = var1;
      this.hasExtensions = var2.hasExtensions(var3);
      this.extensionSchema = var2;
      this.defaultInstance = var3;
   }

   private <UT, UB> int getUnknownFieldsSerializedSize(UnknownFieldSchema<UT, UB> var1, T var2) {
      return var1.getSerializedSizeAsMessageSet(var1.getFromMessage(var2));
   }

   private <UT, UB, ET extends FieldSet.FieldDescriptorLite<ET>> void mergeFromHelper(
      UnknownFieldSchema<UT, UB> param1, ExtensionSchema<ET> param2, T param3, Reader param4, ExtensionRegistryLite param5
   ) throws IOException {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.insertSemaphore(FinallyProcessor.java:350)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:99)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: aload 1
      // 01: aload 3
      // 02: invokevirtual com/google/protobuf/UnknownFieldSchema.getBuilderFromMessage (Ljava/lang/Object;)Ljava/lang/Object;
      // 05: astore 8
      // 07: aload 2
      // 08: aload 3
      // 09: invokevirtual com/google/protobuf/ExtensionSchema.getMutableExtensions (Ljava/lang/Object;)Lcom/google/protobuf/FieldSet;
      // 0c: astore 9
      // 0e: aload 4
      // 10: invokeinterface com/google/protobuf/Reader.getFieldNumber ()I 1
      // 15: istore 6
      // 17: iload 6
      // 19: ldc 2147483647
      // 1b: if_icmpne 26
      // 1e: aload 1
      // 1f: aload 3
      // 20: aload 8
      // 22: invokevirtual com/google/protobuf/UnknownFieldSchema.setBuilderToMessage (Ljava/lang/Object;Ljava/lang/Object;)V
      // 25: return
      // 26: aload 0
      // 27: aload 4
      // 29: aload 5
      // 2b: aload 2
      // 2c: aload 9
      // 2e: aload 1
      // 2f: aload 8
      // 31: invokespecial com/google/protobuf/MessageSetSchema.parseMessageSetItemOrUnknownField (Lcom/google/protobuf/Reader;Lcom/google/protobuf/ExtensionRegistryLite;Lcom/google/protobuf/ExtensionSchema;Lcom/google/protobuf/FieldSet;Lcom/google/protobuf/UnknownFieldSchema;Ljava/lang/Object;)Z
      // 34: istore 7
      // 36: iload 7
      // 38: ifeq 3e
      // 3b: goto 0e
      // 3e: aload 1
      // 3f: aload 3
      // 40: aload 8
      // 42: invokevirtual com/google/protobuf/UnknownFieldSchema.setBuilderToMessage (Ljava/lang/Object;Ljava/lang/Object;)V
      // 45: return
      // 46: astore 2
      // 47: aload 1
      // 48: aload 3
      // 49: aload 8
      // 4b: invokevirtual com/google/protobuf/UnknownFieldSchema.setBuilderToMessage (Ljava/lang/Object;Ljava/lang/Object;)V
      // 4e: aload 2
      // 4f: athrow
   }

   static <T> MessageSetSchema<T> newSchema(UnknownFieldSchema<?, ?> var0, ExtensionSchema<?> var1, MessageLite var2) {
      return new MessageSetSchema<>(var0, var1, var2);
   }

   private <UT, UB, ET extends FieldSet.FieldDescriptorLite<ET>> boolean parseMessageSetItemOrUnknownField(
      Reader var1, ExtensionRegistryLite var2, ExtensionSchema<ET> var3, FieldSet<ET> var4, UnknownFieldSchema<UT, UB> var5, UB var6
   ) throws IOException {
      int var7 = var1.getTag();
      if (var7 != WireFormat.MESSAGE_SET_ITEM_TAG) {
         if (WireFormat.getTagWireType(var7) == 2) {
            Object var12 = var3.findExtensionByNumber(var2, this.defaultInstance, WireFormat.getTagFieldNumber(var7));
            if (var12 != null) {
               var3.parseLengthPrefixedMessageSetItem(var1, var12, var2, var4);
               return true;
            } else {
               return var5.mergeOneFieldFrom(var6, var1);
            }
         } else {
            return var1.skipField();
         }
      } else {
         Object var10 = null;
         ByteString var9 = null;
         var7 = 0;

         while (var1.getFieldNumber() != Integer.MAX_VALUE) {
            int var8 = var1.getTag();
            if (var8 == WireFormat.MESSAGE_SET_TYPE_ID_TAG) {
               var7 = var1.readUInt32();
               var10 = var3.findExtensionByNumber(var2, this.defaultInstance, var7);
            } else if (var8 == WireFormat.MESSAGE_SET_MESSAGE_TAG) {
               if (var10 != null) {
                  var3.parseLengthPrefixedMessageSetItem(var1, var10, var2, var4);
               } else {
                  var9 = var1.readBytes();
               }
            } else if (!var1.skipField()) {
               break;
            }
         }

         if (var1.getTag() == WireFormat.MESSAGE_SET_ITEM_END_TAG) {
            if (var9 != null) {
               if (var10 != null) {
                  var3.parseMessageSetItem(var9, var10, var2, var4);
               } else {
                  var5.addLengthDelimited(var6, var7, var9);
               }
            }

            return true;
         } else {
            throw InvalidProtocolBufferException.invalidEndTag();
         }
      }
   }

   private <UT, UB> void writeUnknownFieldsHelper(UnknownFieldSchema<UT, UB> var1, T var2, Writer var3) throws IOException {
      var1.writeAsMessageSetTo(var1.getFromMessage(var2), var3);
   }

   @Override
   public boolean equals(T var1, T var2) {
      if (!this.unknownFieldSchema.getFromMessage(var1).equals(this.unknownFieldSchema.getFromMessage(var2))) {
         return false;
      } else {
         return this.hasExtensions ? this.extensionSchema.getExtensions(var1).equals(this.extensionSchema.getExtensions(var2)) : true;
      }
   }

   @Override
   public int getSerializedSize(T var1) {
      int var3 = this.getUnknownFieldsSerializedSize(this.unknownFieldSchema, (T)var1);
      int var2 = var3;
      if (this.hasExtensions) {
         var2 = var3 + this.extensionSchema.getExtensions(var1).getMessageSetSerializedSize();
      }

      return var2;
   }

   @Override
   public int hashCode(T var1) {
      int var3 = this.unknownFieldSchema.getFromMessage(var1).hashCode();
      int var2 = var3;
      if (this.hasExtensions) {
         var2 = var3 * 53 + this.extensionSchema.getExtensions(var1).hashCode();
      }

      return var2;
   }

   @Override
   public final boolean isInitialized(T var1) {
      return this.extensionSchema.getExtensions(var1).isInitialized();
   }

   @Override
   public void makeImmutable(T var1) {
      this.unknownFieldSchema.makeImmutable(var1);
      this.extensionSchema.makeImmutable(var1);
   }

   @Override
   public void mergeFrom(T var1, Reader var2, ExtensionRegistryLite var3) throws IOException {
      this.mergeFromHelper(this.unknownFieldSchema, this.extensionSchema, (T)var1, var2, var3);
   }

   @Override
   public void mergeFrom(T var1, T var2) {
      SchemaUtil.mergeUnknownFields(this.unknownFieldSchema, var1, var2);
      if (this.hasExtensions) {
         SchemaUtil.mergeExtensions(this.extensionSchema, var1, var2);
      }
   }

   @Override
   public void mergeFrom(T var1, byte[] var2, int var3, int var4, ArrayDecoders.Registers var5) throws IOException {
      GeneratedMessageLite var12 = (GeneratedMessageLite)var1;
      UnknownFieldSetLite var11 = var12.unknownFields;
      UnknownFieldSetLite var10 = var11;
      if (var11 == UnknownFieldSetLite.getDefaultInstance()) {
         var10 = UnknownFieldSetLite.newInstance();
         var12.unknownFields = var10;
      }

      FieldSet var18 = ((GeneratedMessageLite.ExtendableMessage)var1).ensureExtensionsAreMutable();
      var1 = null;

      while (var3 < var4) {
         var3 = ArrayDecoders.decodeVarint32(var2, var3, var5);
         int var6 = var5.int1;
         if (var6 != WireFormat.MESSAGE_SET_ITEM_TAG) {
            if (WireFormat.getTagWireType(var6) == 2) {
               var1 = (GeneratedMessageLite.GeneratedExtension)this.extensionSchema
                  .findExtensionByNumber(var5.extensionRegistry, this.defaultInstance, WireFormat.getTagFieldNumber(var6));
               if (var1 != null) {
                  var3 = ArrayDecoders.decodeMessageField(Protobuf.getInstance().schemaFor(var1.getMessageDefaultInstance().getClass()), var2, var3, var4, var5);
                  var18.setField(var1.descriptor, var5.object1);
               } else {
                  var3 = ArrayDecoders.decodeUnknownField(var6, var2, var3, var4, var10, var5);
               }
            } else {
               var3 = ArrayDecoders.skipField(var6, var2, var3, var4, var5);
            }
         } else {
            int var7 = 0;
            ByteString var17 = null;

            while (true) {
               var6 = var3;
               if (var3 < var4) {
                  var6 = ArrayDecoders.decodeVarint32(var2, var3, var5);
                  var3 = var5.int1;
                  int var9 = WireFormat.getTagFieldNumber(var3);
                  int var8 = WireFormat.getTagWireType(var3);
                  if (var9 != 2) {
                     if (var9 == 3) {
                        if (var1 != null) {
                           var3 = ArrayDecoders.decodeMessageField(
                              Protobuf.getInstance().schemaFor(var1.getMessageDefaultInstance().getClass()), var2, var6, var4, var5
                           );
                           var18.setField(var1.descriptor, var5.object1);
                           continue;
                        }

                        if (var8 == 2) {
                           var3 = ArrayDecoders.decodeBytes(var2, var6, var5);
                           var17 = (ByteString)var5.object1;
                           continue;
                        }
                     }
                  } else if (var8 == 0) {
                     var3 = ArrayDecoders.decodeVarint32(var2, var6, var5);
                     var7 = var5.int1;
                     var1 = (GeneratedMessageLite.GeneratedExtension)this.extensionSchema
                        .findExtensionByNumber(var5.extensionRegistry, this.defaultInstance, var7);
                     continue;
                  }

                  if (var3 != WireFormat.MESSAGE_SET_ITEM_END_TAG) {
                     var3 = ArrayDecoders.skipField(var3, var2, var6, var4, var5);
                     continue;
                  }
               }

               if (var17 != null) {
                  var10.storeField(WireFormat.makeTag(var7, 2), var17);
               }

               var3 = var6;
               break;
            }
         }
      }

      if (var3 != var4) {
         throw InvalidProtocolBufferException.parseFailure();
      }
   }

   @Override
   public T newInstance() {
      MessageLite var1 = this.defaultInstance;
      return (T)(var1 instanceof GeneratedMessageLite ? ((GeneratedMessageLite)var1).newMutableInstance() : var1.newBuilderForType().buildPartial());
   }

   @Override
   public void writeTo(T var1, Writer var2) throws IOException {
      for (Entry var4 : this.extensionSchema.getExtensions(var1)) {
         FieldSet.FieldDescriptorLite var5 = (FieldSet.FieldDescriptorLite)var4.getKey();
         if (var5.getLiteJavaType() != WireFormat.JavaType.MESSAGE || var5.isRepeated() || var5.isPacked()) {
            throw new IllegalStateException("Found invalid MessageSet item.");
         }

         if (var4 instanceof LazyField.LazyEntry) {
            var2.writeMessageSetItem(var5.getNumber(), ((LazyField.LazyEntry)var4).getField().toByteString());
         } else {
            var2.writeMessageSetItem(var5.getNumber(), var4.getValue());
         }
      }

      this.writeUnknownFieldsHelper(this.unknownFieldSchema, (T)var1, var2);
   }
}
