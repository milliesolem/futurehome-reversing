package com.google.protobuf;

final class ExtensionRegistryFactory {
   static final Class<?> EXTENSION_REGISTRY_CLASS = reflectExtensionRegistry();
   static final String FULL_REGISTRY_CLASS_NAME = "com.google.protobuf.ExtensionRegistry";

   public static ExtensionRegistryLite create() {
      ExtensionRegistryLite var0 = invokeSubclassFactory("newInstance");
      if (var0 == null) {
         var0 = new ExtensionRegistryLite();
      }

      return var0;
   }

   public static ExtensionRegistryLite createEmpty() {
      ExtensionRegistryLite var0 = invokeSubclassFactory("getEmptyRegistry");
      if (var0 == null) {
         var0 = ExtensionRegistryLite.EMPTY_REGISTRY_LITE;
      }

      return var0;
   }

   private static final ExtensionRegistryLite invokeSubclassFactory(String var0) {
      Class var1 = EXTENSION_REGISTRY_CLASS;
      if (var1 == null) {
         return null;
      } else {
         try {
            return (ExtensionRegistryLite)var1.getDeclaredMethod(var0, null).invoke(null, null);
         } catch (Exception var2) {
            return null;
         }
      }
   }

   static boolean isFullRegistry(ExtensionRegistryLite var0) {
      Class var2 = EXTENSION_REGISTRY_CLASS;
      boolean var1;
      if (var2 != null && var2.isAssignableFrom(var0.getClass())) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   static Class<?> reflectExtensionRegistry() {
      try {
         return Class.forName("com.google.protobuf.ExtensionRegistry");
      } catch (ClassNotFoundException var1) {
         return null;
      }
   }
}
