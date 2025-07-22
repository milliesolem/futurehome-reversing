package com.google.protobuf;

import java.io.IOException;

@CheckReturnValue
abstract class UnknownFieldSchema<T, B> {
   abstract void addFixed32(B var1, int var2, int var3);

   abstract void addFixed64(B var1, int var2, long var3);

   abstract void addGroup(B var1, int var2, T var3);

   abstract void addLengthDelimited(B var1, int var2, ByteString var3);

   abstract void addVarint(B var1, int var2, long var3);

   abstract B getBuilderFromMessage(Object var1);

   abstract T getFromMessage(Object var1);

   abstract int getSerializedSize(T var1);

   abstract int getSerializedSizeAsMessageSet(T var1);

   abstract void makeImmutable(Object var1);

   abstract T merge(T var1, T var2);

   final void mergeFrom(B var1, Reader var2) throws IOException {
      while (var2.getFieldNumber() != Integer.MAX_VALUE && this.mergeOneFieldFrom((B)var1, var2)) {
      }
   }

   final boolean mergeOneFieldFrom(B var1, Reader var2) throws IOException {
      int var4 = var2.getTag();
      int var3 = WireFormat.getTagFieldNumber(var4);
      var4 = WireFormat.getTagWireType(var4);
      if (var4 != 0) {
         if (var4 != 1) {
            if (var4 != 2) {
               if (var4 != 3) {
                  if (var4 != 4) {
                     if (var4 == 5) {
                        this.addFixed32((B)var1, var3, var2.readFixed32());
                        return true;
                     } else {
                        throw InvalidProtocolBufferException.invalidWireType();
                     }
                  } else {
                     return false;
                  }
               } else {
                  Object var5 = this.newBuilder();
                  var4 = WireFormat.makeTag(var3, 4);
                  this.mergeFrom((B)var5, var2);
                  if (var4 == var2.getTag()) {
                     this.addGroup((B)var1, var3, this.toImmutable((B)var5));
                     return true;
                  } else {
                     throw InvalidProtocolBufferException.invalidEndTag();
                  }
               }
            } else {
               this.addLengthDelimited((B)var1, var3, var2.readBytes());
               return true;
            }
         } else {
            this.addFixed64((B)var1, var3, var2.readFixed64());
            return true;
         }
      } else {
         this.addVarint((B)var1, var3, var2.readInt64());
         return true;
      }
   }

   abstract B newBuilder();

   abstract void setBuilderToMessage(Object var1, B var2);

   abstract void setToMessage(Object var1, T var2);

   abstract boolean shouldDiscardUnknownFields(Reader var1);

   abstract T toImmutable(B var1);

   abstract void writeAsMessageSetTo(T var1, Writer var2) throws IOException;

   abstract void writeTo(T var1, Writer var2) throws IOException;
}
