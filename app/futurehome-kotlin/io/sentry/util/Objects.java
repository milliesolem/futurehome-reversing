package io.sentry.util;

import java.util.Arrays;

public final class Objects {
   private Objects() {
   }

   public static boolean equals(Object var0, Object var1) {
      boolean var2;
      if (var0 == var1 || var0 != null && var0.equals(var1)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public static int hash(Object... var0) {
      return Arrays.hashCode(var0);
   }

   public static <T> T requireNonNull(T var0, String var1) {
      if (var0 != null) {
         return (T)var0;
      } else {
         throw new IllegalArgumentException(var1);
      }
   }
}
