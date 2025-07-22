package io.sentry.android.core.internal.util;

public class ClassUtil {
   public static String getClassName(Object var0) {
      if (var0 == null) {
         return null;
      } else {
         String var1 = var0.getClass().getCanonicalName();
         return var1 != null ? var1 : var0.getClass().getSimpleName();
      }
   }
}
