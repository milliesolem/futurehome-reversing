package io.sentry;

public final class HttpStatusCodeRange {
   public static final int DEFAULT_MAX = 599;
   public static final int DEFAULT_MIN = 500;
   private final int max;
   private final int min;

   public HttpStatusCodeRange(int var1) {
      this.min = var1;
      this.max = var1;
   }

   public HttpStatusCodeRange(int var1, int var2) {
      this.min = var1;
      this.max = var2;
   }

   public boolean isInRange(int var1) {
      boolean var2;
      if (var1 >= this.min && var1 <= this.max) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }
}
