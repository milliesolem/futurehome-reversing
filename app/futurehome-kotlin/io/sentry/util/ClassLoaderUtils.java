package io.sentry.util;

public final class ClassLoaderUtils {
   public static ClassLoader classLoaderOrDefault(ClassLoader var0) {
      ClassLoader var1 = var0;
      if (var0 == null) {
         var1 = ClassLoader.getSystemClassLoader();
      }

      return var1;
   }
}
