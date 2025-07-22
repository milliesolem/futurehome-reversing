package com.google.protobuf;

final class Android {
   private static boolean ASSUME_ANDROID;
   private static final boolean IS_ROBOLECTRIC;
   private static final Class<?> MEMORY_CLASS = getClassForName("libcore.io.Memory");

   static {
      boolean var0;
      if (!ASSUME_ANDROID && getClassForName("org.robolectric.Robolectric") != null) {
         var0 = true;
      } else {
         var0 = false;
      }

      IS_ROBOLECTRIC = var0;
   }

   private Android() {
   }

   private static <T> Class<T> getClassForName(String var0) {
      try {
         return (Class<T>)Class.forName(var0);
      } finally {
         ;
      }
   }

   static Class<?> getMemoryClass() {
      return MEMORY_CLASS;
   }

   static boolean isOnAndroidDevice() {
      boolean var0;
      if (ASSUME_ANDROID || MEMORY_CLASS != null && !IS_ROBOLECTRIC) {
         var0 = true;
      } else {
         var0 = false;
      }

      return var0;
   }
}
