package io.reactivex.internal.util;

public final class Pow2 {
   private Pow2() {
      throw new IllegalStateException("No instances!");
   }

   public static boolean isPowerOfTwo(int var0) {
      boolean var1;
      if ((var0 & var0 - 1) == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static int roundToPowerOfTwo(int var0) {
      return 1 << 32 - Integer.numberOfLeadingZeros(var0 - 1);
   }
}
