package io.sentry.util;

public final class SampleRateUtils {
   public static boolean isValidProfilesSampleRate(Double var0) {
      return isValidRate(var0, true);
   }

   private static boolean isValidRate(Double var0, boolean var1) {
      if (var0 == null) {
         return var1;
      } else {
         if (!var0.isNaN() && var0 >= 0.0 && var0 <= 1.0) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }
   }

   public static boolean isValidSampleRate(Double var0) {
      return isValidRate(var0, true);
   }

   public static boolean isValidTracesSampleRate(Double var0) {
      return isValidTracesSampleRate(var0, true);
   }

   public static boolean isValidTracesSampleRate(Double var0, boolean var1) {
      return isValidRate(var0, var1);
   }
}
