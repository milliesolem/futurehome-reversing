package com.google.protobuf;

@CheckReturnValue
final class ExtensionSchemas {
   private static final ExtensionSchema<?> FULL_SCHEMA = loadSchemaForFullRuntime();
   private static final ExtensionSchema<?> LITE_SCHEMA = new ExtensionSchemaLite();

   static ExtensionSchema<?> full() {
      ExtensionSchema var0 = FULL_SCHEMA;
      if (var0 != null) {
         return var0;
      } else {
         throw new IllegalStateException("Protobuf runtime is not correctly loaded.");
      }
   }

   static ExtensionSchema<?> lite() {
      return LITE_SCHEMA;
   }

   private static ExtensionSchema<?> loadSchemaForFullRuntime() {
      try {
         return (ExtensionSchema<?>)Class.forName("com.google.protobuf.ExtensionSchemaFull").getDeclaredConstructor(null).newInstance(null);
      } catch (Exception var1) {
         return null;
      }
   }
}
