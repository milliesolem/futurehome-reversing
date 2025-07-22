package kotlin.math

internal class MathKt__MathJVMKt : MathKt__MathHKt {
   public final val absoluteValue: Double
      public final inline get() {
         return Math.abs(var0);
      }


   public final val sign: Double
      public final inline get() {
         return Math.signum(var0);
      }


   public final val ulp: Double
      public final inline get() {
         return Math.ulp(var0);
      }


   public final val absoluteValue: Float
      public final inline get() {
         return Math.abs(var0);
      }


   public final val sign: Float
      public final inline get() {
         return Math.signum(var0);
      }


   public final val ulp: Float
      public final inline get() {
         return Math.ulp(var0);
      }


   public final val absoluteValue: Int
      public final inline get() {
         return Math.abs(var0);
      }


   public final val sign: Int
      public final get() {
         return Integer.signum(var0);
      }


   public final val absoluteValue: Long
      public final inline get() {
         return Math.abs(var0);
      }


   public final val sign: Int
      public final get() {
         return java.lang.Long.signum(var0);
      }


   open fun MathKt__MathJVMKt() {
   }

   @JvmStatic
   public inline fun Double.IEEErem(divisor: Double): Double {
      return Math.IEEEremainder(var0, var2);
   }

   @JvmStatic
   public inline fun Float.IEEErem(divisor: Float): Float {
      return (float)Math.IEEEremainder((double)var0, (double)var1);
   }

   @JvmStatic
   public inline fun abs(x: Double): Double {
      return Math.abs(var0);
   }

   @JvmStatic
   public inline fun abs(x: Float): Float {
      return Math.abs(var0);
   }

   @JvmStatic
   public inline fun abs(n: Int): Int {
      return Math.abs(var0);
   }

   @JvmStatic
   public inline fun abs(n: Long): Long {
      return Math.abs(var0);
   }

   @JvmStatic
   public inline fun acos(x: Double): Double {
      return Math.acos(var0);
   }

   @JvmStatic
   public inline fun acos(x: Float): Float {
      return (float)Math.acos((double)var0);
   }

   @JvmStatic
   public fun acosh(x: Double): Double {
      if (var0 < 1.0) {
         var0 = java.lang.Double.NaN;
      } else if (var0 > Constants.upper_taylor_2_bound) {
         var0 = Math.log(var0) + Constants.LN2;
      } else {
         val var4: Double = 1;
         var var2: Double = var0 - 1;
         if (var0 - 1 >= Constants.taylor_n_bound) {
            var0 = Math.log(var0 + Math.sqrt(var0 * var0 - var4));
         } else {
            var2 = Math.sqrt(var2);
            var0 = var2;
            if (var2 >= Constants.taylor_2_bound) {
               var0 = var2 - var2 * var2 * var2 / 12;
            }

            var0 = var0 * Math.sqrt(2.0);
         }
      }

      return var0;
   }

   @JvmStatic
   public inline fun acosh(x: Float): Float {
      return (float)MathKt.acosh((double)var0);
   }

   @JvmStatic
   public inline fun asin(x: Double): Double {
      return Math.asin(var0);
   }

   @JvmStatic
   public inline fun asin(x: Float): Float {
      return (float)Math.asin((double)var0);
   }

   @JvmStatic
   public fun asinh(x: Double): Double {
      var var2: Double;
      if (var0 >= Constants.taylor_n_bound) {
         if (var0 > Constants.upper_taylor_n_bound) {
            if (var0 > Constants.upper_taylor_2_bound) {
               var2 = Math.log(var0) + Constants.LN2;
            } else {
               var2 = Math.log(var0 * (double)2 + (double)1 / (var0 * (double)2));
            }
         } else {
            var2 = Math.log(var0 + Math.sqrt(var0 * var0 + (double)1));
         }
      } else if (var0 <= -Constants.taylor_n_bound) {
         var2 = -MathKt.asinh(-var0);
      } else {
         var2 = var0;
         if (Math.abs(var0) >= Constants.taylor_2_bound) {
            var2 = var0 - var0 * var0 * var0 / 6;
         }
      }

      return var2;
   }

   @JvmStatic
   public inline fun asinh(x: Float): Float {
      return (float)MathKt.asinh((double)var0);
   }

   @JvmStatic
   public inline fun atan(x: Double): Double {
      return Math.atan(var0);
   }

   @JvmStatic
   public inline fun atan(x: Float): Float {
      return (float)Math.atan((double)var0);
   }

   @JvmStatic
   public inline fun atan2(y: Double, x: Double): Double {
      return Math.atan2(var0, var2);
   }

   @JvmStatic
   public inline fun atan2(y: Float, x: Float): Float {
      return (float)Math.atan2((double)var0, (double)var1);
   }

   @JvmStatic
   public fun atanh(x: Double): Double {
      if (Math.abs(var0) < Constants.taylor_n_bound) {
         var var4: Double = var0;
         if (Math.abs(var0) > Constants.taylor_2_bound) {
            var4 = var0 + var0 * var0 * var0 / 3;
         }

         return var4;
      } else {
         return Math.log(((double)1 + var0) / ((double)1 - var0)) / 2;
      }
   }

   @JvmStatic
   public inline fun atanh(x: Float): Float {
      return (float)MathKt.atanh((double)var0);
   }

   @JvmStatic
   public inline fun cbrt(x: Double): Double {
      return Math.cbrt(var0);
   }

   @JvmStatic
   public inline fun cbrt(x: Float): Float {
      return (float)Math.cbrt((double)var0);
   }

   @JvmStatic
   public inline fun ceil(x: Double): Double {
      return Math.ceil(var0);
   }

   @JvmStatic
   public inline fun ceil(x: Float): Float {
      return (float)Math.ceil((double)var0);
   }

   @JvmStatic
   public inline fun cos(x: Double): Double {
      return Math.cos(var0);
   }

   @JvmStatic
   public inline fun cos(x: Float): Float {
      return (float)Math.cos((double)var0);
   }

   @JvmStatic
   public inline fun cosh(x: Double): Double {
      return Math.cosh(var0);
   }

   @JvmStatic
   public inline fun cosh(x: Float): Float {
      return (float)Math.cosh((double)var0);
   }

   @JvmStatic
   public inline fun exp(x: Double): Double {
      return Math.exp(var0);
   }

   @JvmStatic
   public inline fun exp(x: Float): Float {
      return (float)Math.exp((double)var0);
   }

   @JvmStatic
   public inline fun expm1(x: Double): Double {
      return Math.expm1(var0);
   }

   @JvmStatic
   public inline fun expm1(x: Float): Float {
      return (float)Math.expm1((double)var0);
   }

   @JvmStatic
   public inline fun floor(x: Double): Double {
      return Math.floor(var0);
   }

   @JvmStatic
   public inline fun floor(x: Float): Float {
      return (float)Math.floor((double)var0);
   }

   @JvmStatic
   public inline fun hypot(x: Double, y: Double): Double {
      return Math.hypot(var0, var2);
   }

   @JvmStatic
   public inline fun hypot(x: Float, y: Float): Float {
      return (float)Math.hypot((double)var0, (double)var1);
   }

   @JvmStatic
   public inline fun ln(x: Double): Double {
      return Math.log(var0);
   }

   @JvmStatic
   public inline fun ln(x: Float): Float {
      return (float)Math.log((double)var0);
   }

   @JvmStatic
   public inline fun ln1p(x: Double): Double {
      return Math.log1p(var0);
   }

   @JvmStatic
   public inline fun ln1p(x: Float): Float {
      return (float)Math.log1p((double)var0);
   }

   @JvmStatic
   public fun log(x: Double, base: Double): Double {
      return if (!(var2 <= 0.0) && var2 != 1.0) Math.log(var0) / Math.log(var2) else java.lang.Double.NaN;
   }

   @JvmStatic
   public fun log(x: Float, base: Float): Float {
      return if (!(var1 <= 0.0F) && var1 != 1.0F) (float)(Math.log((double)var0) / Math.log((double)var1)) else java.lang.Float.NaN;
   }

   @JvmStatic
   public inline fun log10(x: Double): Double {
      return Math.log10(var0);
   }

   @JvmStatic
   public inline fun log10(x: Float): Float {
      return (float)Math.log10((double)var0);
   }

   @JvmStatic
   public fun log2(x: Double): Double {
      return Math.log(var0) / Constants.LN2;
   }

   @JvmStatic
   public fun log2(x: Float): Float {
      return (float)(Math.log((double)var0) / Constants.LN2);
   }

   @JvmStatic
   public inline fun max(a: Double, b: Double): Double {
      return Math.max(var0, var2);
   }

   @JvmStatic
   public inline fun max(a: Float, b: Float): Float {
      return Math.max(var0, var1);
   }

   @JvmStatic
   public inline fun max(a: Int, b: Int): Int {
      return Math.max(var0, var1);
   }

   @JvmStatic
   public inline fun max(a: Long, b: Long): Long {
      return Math.max(var0, var2);
   }

   @JvmStatic
   public inline fun min(a: Double, b: Double): Double {
      return Math.min(var0, var2);
   }

   @JvmStatic
   public inline fun min(a: Float, b: Float): Float {
      return Math.min(var0, var1);
   }

   @JvmStatic
   public inline fun min(a: Int, b: Int): Int {
      return Math.min(var0, var1);
   }

   @JvmStatic
   public inline fun min(a: Long, b: Long): Long {
      return Math.min(var0, var2);
   }

   @JvmStatic
   public inline fun Double.nextDown(): Double {
      return Math.nextAfter(var0, java.lang.Double.NEGATIVE_INFINITY);
   }

   @JvmStatic
   public inline fun Float.nextDown(): Float {
      return Math.nextAfter(var0, java.lang.Double.NEGATIVE_INFINITY);
   }

   @JvmStatic
   public inline fun Double.nextTowards(to: Double): Double {
      return Math.nextAfter(var0, var2);
   }

   @JvmStatic
   public inline fun Float.nextTowards(to: Float): Float {
      return Math.nextAfter(var0, (double)var1);
   }

   @JvmStatic
   public inline fun Double.nextUp(): Double {
      return Math.nextUp(var0);
   }

   @JvmStatic
   public inline fun Float.nextUp(): Float {
      return Math.nextUp(var0);
   }

   @JvmStatic
   public inline fun Double.pow(x: Double): Double {
      return Math.pow(var0, var2);
   }

   @JvmStatic
   public inline fun Double.pow(n: Int): Double {
      return Math.pow(var0, (double)var2);
   }

   @JvmStatic
   public inline fun Float.pow(x: Float): Float {
      return (float)Math.pow((double)var0, (double)var1);
   }

   @JvmStatic
   public inline fun Float.pow(n: Int): Float {
      return (float)Math.pow((double)var0, (double)var1);
   }

   @JvmStatic
   public inline fun round(x: Double): Double {
      return Math.rint(var0);
   }

   @JvmStatic
   public inline fun round(x: Float): Float {
      return (float)Math.rint((double)var0);
   }

   @JvmStatic
   public fun Double.roundToInt(): Int {
      if (!java.lang.Double.isNaN(var0)) {
         val var2: Int;
         if (var0 > 2.147483647E9) {
            var2 = Integer.MAX_VALUE;
         } else if (var0 < -2.1474836E9F) {
            var2 = Integer.MIN_VALUE;
         } else {
            var2 = (int)Math.round(var0);
         }

         return var2;
      } else {
         throw new IllegalArgumentException("Cannot round NaN value.");
      }
   }

   @JvmStatic
   public fun Float.roundToInt(): Int {
      if (!java.lang.Float.isNaN(var0)) {
         return Math.round(var0);
      } else {
         throw new IllegalArgumentException("Cannot round NaN value.");
      }
   }

   @JvmStatic
   public fun Double.roundToLong(): Long {
      if (!java.lang.Double.isNaN(var0)) {
         return Math.round(var0);
      } else {
         throw new IllegalArgumentException("Cannot round NaN value.");
      }
   }

   @JvmStatic
   public fun Float.roundToLong(): Long {
      return MathKt.roundToLong((double)var0);
   }

   @JvmStatic
   public inline fun sign(x: Double): Double {
      return Math.signum(var0);
   }

   @JvmStatic
   public inline fun sign(x: Float): Float {
      return Math.signum(var0);
   }

   @JvmStatic
   public inline fun sin(x: Double): Double {
      return Math.sin(var0);
   }

   @JvmStatic
   public inline fun sin(x: Float): Float {
      return (float)Math.sin((double)var0);
   }

   @JvmStatic
   public inline fun sinh(x: Double): Double {
      return Math.sinh(var0);
   }

   @JvmStatic
   public inline fun sinh(x: Float): Float {
      return (float)Math.sinh((double)var0);
   }

   @JvmStatic
   public inline fun sqrt(x: Double): Double {
      return Math.sqrt(var0);
   }

   @JvmStatic
   public inline fun sqrt(x: Float): Float {
      return (float)Math.sqrt((double)var0);
   }

   @JvmStatic
   public inline fun tan(x: Double): Double {
      return Math.tan(var0);
   }

   @JvmStatic
   public inline fun tan(x: Float): Float {
      return (float)Math.tan((double)var0);
   }

   @JvmStatic
   public inline fun tanh(x: Double): Double {
      return Math.tanh(var0);
   }

   @JvmStatic
   public inline fun tanh(x: Float): Float {
      return (float)Math.tanh((double)var0);
   }

   @JvmStatic
   public fun truncate(x: Double): Double {
      var var2: Double = var0;
      if (!java.lang.Double.isNaN(var0)) {
         if (java.lang.Double.isInfinite(var0)) {
            var2 = var0;
         } else if (var0 > 0.0) {
            var2 = Math.floor(var0);
         } else {
            var2 = Math.ceil(var0);
         }
      }

      return var2;
   }

   @JvmStatic
   public fun truncate(x: Float): Float {
      var var3: Float = var0;
      if (!java.lang.Float.isNaN(var0)) {
         if (java.lang.Float.isInfinite(var0)) {
            var3 = var0;
         } else {
            val var1: Double;
            if (var0 > 0.0F) {
               var1 = Math.floor((double)var0);
            } else {
               var1 = Math.ceil((double)var0);
            }

            var3 = (float)var1;
         }
      }

      return var3;
   }

   @JvmStatic
   public inline fun Double.withSign(sign: Double): Double {
      return Math.copySign(var0, var2);
   }

   @JvmStatic
   public inline fun Double.withSign(sign: Int): Double {
      return Math.copySign(var0, (double)var2);
   }

   @JvmStatic
   public inline fun Float.withSign(sign: Float): Float {
      return Math.copySign(var0, var1);
   }

   @JvmStatic
   public inline fun Float.withSign(sign: Int): Float {
      return Math.copySign(var0, (float)var1);
   }
}
