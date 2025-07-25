package com.google.protobuf;

public interface ValueOrBuilder extends MessageLiteOrBuilder {
   boolean getBoolValue();

   Value.KindCase getKindCase();

   ListValue getListValue();

   NullValue getNullValue();

   int getNullValueValue();

   double getNumberValue();

   String getStringValue();

   ByteString getStringValueBytes();

   Struct getStructValue();

   boolean hasBoolValue();

   boolean hasListValue();

   boolean hasNullValue();

   boolean hasNumberValue();

   boolean hasStringValue();

   boolean hasStructValue();
}
