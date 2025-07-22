package com.google.protobuf;

@CheckReturnValue
final class NewInstanceSchemaLite implements NewInstanceSchema {
   @Override
   public Object newInstance(Object var1) {
      return ((GeneratedMessageLite)var1).newMutableInstance();
   }
}
