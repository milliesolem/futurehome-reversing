package com.google.protobuf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

@CheckReturnValue
final class ExtensionSchemaLite extends ExtensionSchema<GeneratedMessageLite.ExtensionDescriptor> {
   @Override
   int extensionNumber(Entry<?, ?> var1) {
      return ((GeneratedMessageLite.ExtensionDescriptor)var1.getKey()).getNumber();
   }

   @Override
   Object findExtensionByNumber(ExtensionRegistryLite var1, MessageLite var2, int var3) {
      return var1.findLiteExtensionByNumber(var2, var3);
   }

   @Override
   FieldSet<GeneratedMessageLite.ExtensionDescriptor> getExtensions(Object var1) {
      return ((GeneratedMessageLite.ExtendableMessage)var1).extensions;
   }

   @Override
   FieldSet<GeneratedMessageLite.ExtensionDescriptor> getMutableExtensions(Object var1) {
      return ((GeneratedMessageLite.ExtendableMessage)var1).ensureExtensionsAreMutable();
   }

   @Override
   boolean hasExtensions(MessageLite var1) {
      return var1 instanceof GeneratedMessageLite.ExtendableMessage;
   }

   @Override
   void makeImmutable(Object var1) {
      this.getExtensions(var1).makeImmutable();
   }

   @Override
   <UT, UB> UB parseExtension(
      Object var1,
      Reader var2,
      Object var3,
      ExtensionRegistryLite var4,
      FieldSet<GeneratedMessageLite.ExtensionDescriptor> var5,
      UB var6,
      UnknownFieldSchema<UT, UB> var7
   ) throws IOException {
      GeneratedMessageLite.GeneratedExtension var10 = (GeneratedMessageLite.GeneratedExtension)var3;
      int var8 = var10.getNumber();
      if (var10.descriptor.isRepeated() && var10.descriptor.isPacked()) {
         switch (<unrepresentable>.$SwitchMap$com$google$protobuf$WireFormat$FieldType[var10.getLiteType().ordinal()]) {
            case 1:
               var1 = new ArrayList();
               var2.readDoubleList(var1);
               break;
            case 2:
               var1 = new ArrayList();
               var2.readFloatList(var1);
               break;
            case 3:
               var1 = new ArrayList();
               var2.readInt64List(var1);
               break;
            case 4:
               var1 = new ArrayList();
               var2.readUInt64List(var1);
               break;
            case 5:
               var1 = new ArrayList();
               var2.readInt32List(var1);
               break;
            case 6:
               var1 = new ArrayList();
               var2.readFixed64List(var1);
               break;
            case 7:
               var1 = new ArrayList();
               var2.readFixed32List(var1);
               break;
            case 8:
               var1 = new ArrayList();
               var2.readBoolList(var1);
               break;
            case 9:
               var1 = new ArrayList();
               var2.readUInt32List(var1);
               break;
            case 10:
               var1 = new ArrayList();
               var2.readSFixed32List(var1);
               break;
            case 11:
               var1 = new ArrayList();
               var2.readSFixed64List(var1);
               break;
            case 12:
               var1 = new ArrayList();
               var2.readSInt32List(var1);
               break;
            case 13:
               var1 = new ArrayList();
               var2.readSInt64List(var1);
               break;
            case 14:
               var3 = new ArrayList();
               var2.readEnumList(var3);
               var6 = SchemaUtil.filterUnknownEnumList(var1, var8, var3, var10.descriptor.getEnumType(), var6, var7);
               var1 = var3;
               break;
            default:
               var1 = new StringBuilder("Type cannot be packed: ");
               var1.append(var10.descriptor.getLiteType());
               throw new IllegalStateException(var1.toString());
         }

         var5.setField(var10.descriptor, var1);
      } else {
         if (var10.getLiteType() == WireFormat.FieldType.ENUM) {
            int var9 = var2.readInt32();
            if (var10.descriptor.getEnumType().findValueByNumber(var9) == null) {
               return SchemaUtil.storeUnknownEnum(var1, var8, var9, (UB)var6, var7);
            }

            var1 = var9;
         } else {
            switch (<unrepresentable>.$SwitchMap$com$google$protobuf$WireFormat$FieldType[var10.getLiteType().ordinal()]) {
               case 1:
                  var1 = var2.readDouble();
                  break;
               case 2:
                  var1 = var2.readFloat();
                  break;
               case 3:
                  var1 = var2.readInt64();
                  break;
               case 4:
                  var1 = var2.readUInt64();
                  break;
               case 5:
                  var1 = var2.readInt32();
                  break;
               case 6:
                  var1 = var2.readFixed64();
                  break;
               case 7:
                  var1 = var2.readFixed32();
                  break;
               case 8:
                  var1 = var2.readBool();
                  break;
               case 9:
                  var1 = var2.readUInt32();
                  break;
               case 10:
                  var1 = var2.readSFixed32();
                  break;
               case 11:
                  var1 = var2.readSFixed64();
                  break;
               case 12:
                  var1 = var2.readSInt32();
                  break;
               case 13:
                  var1 = var2.readSInt64();
                  break;
               case 14:
                  throw new IllegalStateException("Shouldn't reach here.");
               case 15:
                  var1 = var2.readBytes();
                  break;
               case 16:
                  var1 = var2.readString();
                  break;
               case 17:
                  if (!var10.isRepeated()) {
                     var3 = var5.getField(var10.descriptor);
                     if (var3 instanceof GeneratedMessageLite) {
                        Schema var22 = Protobuf.getInstance().schemaFor(var3);
                        var1 = var3;
                        if (!((GeneratedMessageLite)var3).isMutable()) {
                           var1 = var22.newInstance();
                           var22.mergeFrom(var1, var3);
                           var5.setField(var10.descriptor, var1);
                        }

                        var2.mergeGroupField(var1, var22, var4);
                        return (UB)var6;
                     }
                  }

                  var1 = var2.readGroup(var10.getMessageDefaultInstance().getClass(), var4);
                  break;
               case 18:
                  if (!var10.isRepeated()) {
                     var3 = var5.getField(var10.descriptor);
                     if (var3 instanceof GeneratedMessageLite) {
                        Schema var21 = Protobuf.getInstance().schemaFor(var3);
                        var1 = var3;
                        if (!((GeneratedMessageLite)var3).isMutable()) {
                           var1 = var21.newInstance();
                           var21.mergeFrom(var1, var3);
                           var5.setField(var10.descriptor, var1);
                        }

                        var2.mergeMessageField(var1, var21, var4);
                        return (UB)var6;
                     }
                  }

                  var1 = var2.readMessage(var10.getMessageDefaultInstance().getClass(), var4);
                  break;
               default:
                  var1 = null;
            }
         }

         if (var10.isRepeated()) {
            var5.addRepeatedField(var10.descriptor, var1);
         } else {
            var8 = <unrepresentable>.$SwitchMap$com$google$protobuf$WireFormat$FieldType[var10.getLiteType().ordinal()];
            Object var16;
            if (var8 != 17 && var8 != 18) {
               var16 = var1;
            } else {
               var3 = var5.getField(var10.descriptor);
               var16 = var1;
               if (var3 != null) {
                  var16 = Internal.mergeMessage(var3, var1);
               }
            }

            var5.setField(var10.descriptor, var16);
         }
      }

      return (UB)var6;
   }

   @Override
   void parseLengthPrefixedMessageSetItem(Reader var1, Object var2, ExtensionRegistryLite var3, FieldSet<GeneratedMessageLite.ExtensionDescriptor> var4) throws IOException {
      var2 = var2;
      Object var5 = var1.readMessage(var2.getMessageDefaultInstance().getClass(), var3);
      var4.setField(var2.descriptor, var5);
   }

   @Override
   void parseMessageSetItem(ByteString var1, Object var2, ExtensionRegistryLite var3, FieldSet<GeneratedMessageLite.ExtensionDescriptor> var4) throws IOException {
      GeneratedMessageLite.GeneratedExtension var5 = (GeneratedMessageLite.GeneratedExtension)var2;
      var2 = var5.getMessageDefaultInstance().newBuilderForType();
      CodedInputStream var6 = var1.newCodedInput();
      var2.mergeFrom(var6, var3);
      var4.setField(var5.descriptor, var2.buildPartial());
      var6.checkLastTagWas(0);
   }

   @Override
   void serializeExtension(Writer var1, Entry<?, ?> var2) throws IOException {
      GeneratedMessageLite.ExtensionDescriptor var3 = (GeneratedMessageLite.ExtensionDescriptor)var2.getKey();
      if (var3.isRepeated()) {
         switch (<unrepresentable>.$SwitchMap$com$google$protobuf$WireFormat$FieldType[var3.getLiteType().ordinal()]) {
            case 1:
               SchemaUtil.writeDoubleList(var3.getNumber(), (List<Double>)var2.getValue(), var1, var3.isPacked());
               break;
            case 2:
               SchemaUtil.writeFloatList(var3.getNumber(), (List<Float>)var2.getValue(), var1, var3.isPacked());
               break;
            case 3:
               SchemaUtil.writeInt64List(var3.getNumber(), (List<Long>)var2.getValue(), var1, var3.isPacked());
               break;
            case 4:
               SchemaUtil.writeUInt64List(var3.getNumber(), (List<Long>)var2.getValue(), var1, var3.isPacked());
               break;
            case 5:
               SchemaUtil.writeInt32List(var3.getNumber(), (List<Integer>)var2.getValue(), var1, var3.isPacked());
               break;
            case 6:
               SchemaUtil.writeFixed64List(var3.getNumber(), (List<Long>)var2.getValue(), var1, var3.isPacked());
               break;
            case 7:
               SchemaUtil.writeFixed32List(var3.getNumber(), (List<Integer>)var2.getValue(), var1, var3.isPacked());
               break;
            case 8:
               SchemaUtil.writeBoolList(var3.getNumber(), (List<Boolean>)var2.getValue(), var1, var3.isPacked());
               break;
            case 9:
               SchemaUtil.writeUInt32List(var3.getNumber(), (List<Integer>)var2.getValue(), var1, var3.isPacked());
               break;
            case 10:
               SchemaUtil.writeSFixed32List(var3.getNumber(), (List<Integer>)var2.getValue(), var1, var3.isPacked());
               break;
            case 11:
               SchemaUtil.writeSFixed64List(var3.getNumber(), (List<Long>)var2.getValue(), var1, var3.isPacked());
               break;
            case 12:
               SchemaUtil.writeSInt32List(var3.getNumber(), (List<Integer>)var2.getValue(), var1, var3.isPacked());
               break;
            case 13:
               SchemaUtil.writeSInt64List(var3.getNumber(), (List<Long>)var2.getValue(), var1, var3.isPacked());
               break;
            case 14:
               SchemaUtil.writeInt32List(var3.getNumber(), (List<Integer>)var2.getValue(), var1, var3.isPacked());
               break;
            case 15:
               SchemaUtil.writeBytesList(var3.getNumber(), (List<ByteString>)var2.getValue(), var1);
               break;
            case 16:
               SchemaUtil.writeStringList(var3.getNumber(), (List<String>)var2.getValue(), var1);
               break;
            case 17:
               List var5 = (List)var2.getValue();
               if (var5 != null && !var5.isEmpty()) {
                  SchemaUtil.writeGroupList(var3.getNumber(), (List<?>)var2.getValue(), var1, Protobuf.getInstance().schemaFor(var5.get(0).getClass()));
               }
               break;
            case 18:
               List var4 = (List)var2.getValue();
               if (var4 != null && !var4.isEmpty()) {
                  SchemaUtil.writeMessageList(var3.getNumber(), (List<?>)var2.getValue(), var1, Protobuf.getInstance().schemaFor(var4.get(0).getClass()));
               }
         }
      } else {
         switch (<unrepresentable>.$SwitchMap$com$google$protobuf$WireFormat$FieldType[var3.getLiteType().ordinal()]) {
            case 1:
               var1.writeDouble(var3.getNumber(), (Double)var2.getValue());
               break;
            case 2:
               var1.writeFloat(var3.getNumber(), (Float)var2.getValue());
               break;
            case 3:
               var1.writeInt64(var3.getNumber(), (Long)var2.getValue());
               break;
            case 4:
               var1.writeUInt64(var3.getNumber(), (Long)var2.getValue());
               break;
            case 5:
               var1.writeInt32(var3.getNumber(), (Integer)var2.getValue());
               break;
            case 6:
               var1.writeFixed64(var3.getNumber(), (Long)var2.getValue());
               break;
            case 7:
               var1.writeFixed32(var3.getNumber(), (Integer)var2.getValue());
               break;
            case 8:
               var1.writeBool(var3.getNumber(), (Boolean)var2.getValue());
               break;
            case 9:
               var1.writeUInt32(var3.getNumber(), (Integer)var2.getValue());
               break;
            case 10:
               var1.writeSFixed32(var3.getNumber(), (Integer)var2.getValue());
               break;
            case 11:
               var1.writeSFixed64(var3.getNumber(), (Long)var2.getValue());
               break;
            case 12:
               var1.writeSInt32(var3.getNumber(), (Integer)var2.getValue());
               break;
            case 13:
               var1.writeSInt64(var3.getNumber(), (Long)var2.getValue());
               break;
            case 14:
               var1.writeInt32(var3.getNumber(), (Integer)var2.getValue());
               break;
            case 15:
               var1.writeBytes(var3.getNumber(), (ByteString)var2.getValue());
               break;
            case 16:
               var1.writeString(var3.getNumber(), (String)var2.getValue());
               break;
            case 17:
               var1.writeGroup(var3.getNumber(), var2.getValue(), Protobuf.getInstance().schemaFor(var2.getValue().getClass()));
               break;
            case 18:
               var1.writeMessage(var3.getNumber(), var2.getValue(), Protobuf.getInstance().schemaFor(var2.getValue().getClass()));
         }
      }
   }

   @Override
   void setExtensions(Object var1, FieldSet<GeneratedMessageLite.ExtensionDescriptor> var2) {
      ((GeneratedMessageLite.ExtendableMessage)var1).extensions = var2;
   }
}
