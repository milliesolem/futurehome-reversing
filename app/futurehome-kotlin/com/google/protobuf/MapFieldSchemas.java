package com.google.protobuf;

@CheckReturnValue
final class MapFieldSchemas {
   private static final MapFieldSchema FULL_SCHEMA = loadSchemaForFullRuntime();
   private static final MapFieldSchema LITE_SCHEMA = new MapFieldSchemaLite();

   static MapFieldSchema full() {
      return FULL_SCHEMA;
   }

   static MapFieldSchema lite() {
      return LITE_SCHEMA;
   }

   private static MapFieldSchema loadSchemaForFullRuntime() {
      try {
         return (MapFieldSchema)Class.forName("com.google.protobuf.MapFieldSchemaFull").getDeclaredConstructor(null).newInstance(null);
      } catch (Exception var1) {
         return null;
      }
   }
}
