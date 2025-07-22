package io.sentry.util;

import java.net.URI;
import java.util.List;

public final class PropagationTargetsUtils {
   public static boolean contain(List<String> var0, String var1) {
      if (var0.isEmpty()) {
         return false;
      } else {
         for (String var3 : var0) {
            if (var1.contains(var3)) {
               return true;
            }

            boolean var2;
            try {
               var2 = var1.matches(var3);
            } catch (Exception var4) {
               continue;
            }

            if (var2) {
               return true;
            }
         }

         return false;
      }
   }

   public static boolean contain(List<String> var0, URI var1) {
      return contain(var0, var1.toString());
   }
}
