package kotlin

import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

internal class NumbersKt__BigDecimalsKt {
   open fun NumbersKt__BigDecimalsKt() {
   }

   @JvmStatic
   public inline operator fun BigDecimal.dec(): BigDecimal {
      var0 = var0.subtract(BigDecimal.ONE);
      return var0;
   }

   @JvmStatic
   public inline operator fun BigDecimal.div(other: BigDecimal): BigDecimal {
      var0 = var0.divide(var1, RoundingMode.HALF_EVEN);
      return var0;
   }

   @JvmStatic
   public inline operator fun BigDecimal.inc(): BigDecimal {
      var0 = var0.add(BigDecimal.ONE);
      return var0;
   }

   @JvmStatic
   public inline operator fun BigDecimal.minus(other: BigDecimal): BigDecimal {
      var0 = var0.subtract(var1);
      return var0;
   }

   @JvmStatic
   public inline operator fun BigDecimal.plus(other: BigDecimal): BigDecimal {
      var0 = var0.add(var1);
      return var0;
   }

   @JvmStatic
   public inline operator fun BigDecimal.rem(other: BigDecimal): BigDecimal {
      var0 = var0.remainder(var1);
      return var0;
   }

   @JvmStatic
   public inline operator fun BigDecimal.times(other: BigDecimal): BigDecimal {
      var0 = var0.multiply(var1);
      return var0;
   }

   @JvmStatic
   public inline fun Double.toBigDecimal(): BigDecimal {
      return new BigDecimal(java.lang.String.valueOf(var0));
   }

   @JvmStatic
   public inline fun Double.toBigDecimal(mathContext: MathContext): BigDecimal {
      return new BigDecimal(java.lang.String.valueOf(var0), var2);
   }

   @JvmStatic
   public inline fun Float.toBigDecimal(): BigDecimal {
      return new BigDecimal(java.lang.String.valueOf(var0));
   }

   @JvmStatic
   public inline fun Float.toBigDecimal(mathContext: MathContext): BigDecimal {
      return new BigDecimal(java.lang.String.valueOf(var0), var1);
   }

   @JvmStatic
   public inline fun Int.toBigDecimal(): BigDecimal {
      val var1: BigDecimal = BigDecimal.valueOf((long)var0);
      return var1;
   }

   @JvmStatic
   public inline fun Int.toBigDecimal(mathContext: MathContext): BigDecimal {
      return new BigDecimal(var0, var1);
   }

   @JvmStatic
   public inline fun Long.toBigDecimal(): BigDecimal {
      val var2: BigDecimal = BigDecimal.valueOf(var0);
      return var2;
   }

   @JvmStatic
   public inline fun Long.toBigDecimal(mathContext: MathContext): BigDecimal {
      return new BigDecimal(var0, var2);
   }

   @JvmStatic
   public inline operator fun BigDecimal.unaryMinus(): BigDecimal {
      var0 = var0.negate();
      return var0;
   }
}
