package kotlin

import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext

internal class NumbersKt__BigIntegersKt : NumbersKt__BigDecimalsKt {
   open fun NumbersKt__BigIntegersKt() {
   }

   @JvmStatic
   public inline infix fun BigInteger.and(other: BigInteger): BigInteger {
      var0 = var0.and(var1);
      return var0;
   }

   @JvmStatic
   public inline operator fun BigInteger.dec(): BigInteger {
      var0 = var0.subtract(BigInteger.ONE);
      return var0;
   }

   @JvmStatic
   public inline operator fun BigInteger.div(other: BigInteger): BigInteger {
      var0 = var0.divide(var1);
      return var0;
   }

   @JvmStatic
   public inline operator fun BigInteger.inc(): BigInteger {
      var0 = var0.add(BigInteger.ONE);
      return var0;
   }

   @JvmStatic
   public inline fun BigInteger.inv(): BigInteger {
      var0 = var0.not();
      return var0;
   }

   @JvmStatic
   public inline operator fun BigInteger.minus(other: BigInteger): BigInteger {
      var0 = var0.subtract(var1);
      return var0;
   }

   @JvmStatic
   public inline infix fun BigInteger.or(other: BigInteger): BigInteger {
      var0 = var0.or(var1);
      return var0;
   }

   @JvmStatic
   public inline operator fun BigInteger.plus(other: BigInteger): BigInteger {
      var0 = var0.add(var1);
      return var0;
   }

   @JvmStatic
   public inline operator fun BigInteger.rem(other: BigInteger): BigInteger {
      var0 = var0.remainder(var1);
      return var0;
   }

   @JvmStatic
   public inline infix fun BigInteger.shl(n: Int): BigInteger {
      var0 = var0.shiftLeft(var1);
      return var0;
   }

   @JvmStatic
   public inline infix fun BigInteger.shr(n: Int): BigInteger {
      var0 = var0.shiftRight(var1);
      return var0;
   }

   @JvmStatic
   public inline operator fun BigInteger.times(other: BigInteger): BigInteger {
      var0 = var0.multiply(var1);
      return var0;
   }

   @JvmStatic
   public inline fun BigInteger.toBigDecimal(): BigDecimal {
      return new BigDecimal(var0);
   }

   @JvmStatic
   public inline fun BigInteger.toBigDecimal(scale: Int = 0, mathContext: MathContext = MathContext.UNLIMITED): BigDecimal {
      return new BigDecimal(var0, var1, var2);
   }

   @JvmStatic
   public inline fun Int.toBigInteger(): BigInteger {
      val var1: BigInteger = BigInteger.valueOf((long)var0);
      return var1;
   }

   @JvmStatic
   public inline fun Long.toBigInteger(): BigInteger {
      val var2: BigInteger = BigInteger.valueOf(var0);
      return var2;
   }

   @JvmStatic
   public inline operator fun BigInteger.unaryMinus(): BigInteger {
      var0 = var0.negate();
      return var0;
   }

   @JvmStatic
   public inline infix fun BigInteger.xor(other: BigInteger): BigInteger {
      var0 = var0.xor(var1);
      return var0;
   }
}
