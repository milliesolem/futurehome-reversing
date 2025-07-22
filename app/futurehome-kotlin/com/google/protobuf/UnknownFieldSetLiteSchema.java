package com.google.protobuf;

import java.io.IOException;

@CheckReturnValue
class UnknownFieldSetLiteSchema extends UnknownFieldSchema<UnknownFieldSetLite, UnknownFieldSetLite> {
   void addFixed32(UnknownFieldSetLite var1, int var2, int var3) {
      var1.storeField(WireFormat.makeTag(var2, 5), var3);
   }

   void addFixed64(UnknownFieldSetLite var1, int var2, long var3) {
      var1.storeField(WireFormat.makeTag(var2, 1), var3);
   }

   void addGroup(UnknownFieldSetLite var1, int var2, UnknownFieldSetLite var3) {
      var1.storeField(WireFormat.makeTag(var2, 3), var3);
   }

   void addLengthDelimited(UnknownFieldSetLite var1, int var2, ByteString var3) {
      var1.storeField(WireFormat.makeTag(var2, 2), var3);
   }

   void addVarint(UnknownFieldSetLite var1, int var2, long var3) {
      var1.storeField(WireFormat.makeTag(var2, 0), var3);
   }

   UnknownFieldSetLite getBuilderFromMessage(Object var1) {
      UnknownFieldSetLite var3 = this.getFromMessage(var1);
      UnknownFieldSetLite var2 = var3;
      if (var3 == UnknownFieldSetLite.getDefaultInstance()) {
         var2 = UnknownFieldSetLite.newInstance();
         this.setToMessage(var1, var2);
      }

      return var2;
   }

   UnknownFieldSetLite getFromMessage(Object var1) {
      return ((GeneratedMessageLite)var1).unknownFields;
   }

   int getSerializedSize(UnknownFieldSetLite var1) {
      return var1.getSerializedSize();
   }

   int getSerializedSizeAsMessageSet(UnknownFieldSetLite var1) {
      return var1.getSerializedSizeAsMessageSet();
   }

   @Override
   void makeImmutable(Object var1) {
      this.getFromMessage(var1).makeImmutable();
   }

   UnknownFieldSetLite merge(UnknownFieldSetLite var1, UnknownFieldSetLite var2) {
      if (UnknownFieldSetLite.getDefaultInstance().equals(var2)) {
         return var1;
      } else {
         return UnknownFieldSetLite.getDefaultInstance().equals(var1) ? UnknownFieldSetLite.mutableCopyOf(var1, var2) : var1.mergeFrom(var2);
      }
   }

   UnknownFieldSetLite newBuilder() {
      return UnknownFieldSetLite.newInstance();
   }

   void setBuilderToMessage(Object var1, UnknownFieldSetLite var2) {
      this.setToMessage(var1, var2);
   }

   void setToMessage(Object var1, UnknownFieldSetLite var2) {
      ((GeneratedMessageLite)var1).unknownFields = var2;
   }

   @Override
   boolean shouldDiscardUnknownFields(Reader var1) {
      return false;
   }

   UnknownFieldSetLite toImmutable(UnknownFieldSetLite var1) {
      var1.makeImmutable();
      return var1;
   }

   void writeAsMessageSetTo(UnknownFieldSetLite var1, Writer var2) throws IOException {
      var1.writeAsMessageSetTo(var2);
   }

   void writeTo(UnknownFieldSetLite var1, Writer var2) throws IOException {
      var1.writeTo(var2);
   }
}
