package com.google.protobuf;

@CheckReturnValue
final class NewInstanceSchemas {
   private static final NewInstanceSchema FULL_SCHEMA = loadSchemaForFullRuntime();
   private static final NewInstanceSchema LITE_SCHEMA = new NewInstanceSchemaLite();

   static NewInstanceSchema full() {
      return FULL_SCHEMA;
   }

   static NewInstanceSchema lite() {
      return LITE_SCHEMA;
   }

   private static NewInstanceSchema loadSchemaForFullRuntime() {
      try {
         return (NewInstanceSchema)Class.forName("com.google.protobuf.NewInstanceSchemaFull").getDeclaredConstructor(null).newInstance(null);
      } catch (Exception var1) {
         return null;
      }
   }
}
