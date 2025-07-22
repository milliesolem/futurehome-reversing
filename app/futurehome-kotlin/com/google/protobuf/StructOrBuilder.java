package com.google.protobuf;

import java.util.Map;

public interface StructOrBuilder extends MessageLiteOrBuilder {
   boolean containsFields(String var1);

   @Deprecated
   Map<String, Value> getFields();

   int getFieldsCount();

   Map<String, Value> getFieldsMap();

   Value getFieldsOrDefault(String var1, Value var2);

   Value getFieldsOrThrow(String var1);
}
