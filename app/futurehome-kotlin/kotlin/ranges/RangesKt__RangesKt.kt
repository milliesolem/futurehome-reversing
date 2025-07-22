package kotlin.ranges

internal class RangesKt__RangesKt {
   open fun RangesKt__RangesKt() {
   }

   @JvmStatic
   internal fun checkStepIsPositive(isPositive: Boolean, step: Number) {
      if (!var0) {
         val var2: StringBuilder = new StringBuilder("Step must be positive, was: ");
         var2.append(var1);
         var2.append('.');
         throw new IllegalArgumentException(var2.toString());
      }
   }

   @JvmStatic
   public inline operator fun <T : Any, R> R.contains(element: T?): Boolean where R : ClosedRange<T>, R : Iterable<T> {
      val var2: Boolean;
      if (var1 != null && var0.contains(var1 as java.lang.Comparable)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   @JvmStatic
   public inline operator fun <T : Any, R> R.contains(element: T?): Boolean where R : OpenEndRange<T>, R : Iterable<T> {
      val var2: Boolean;
      if (var1 != null && var0.contains(var1 as java.lang.Comparable)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   @JvmStatic
   public operator fun Double.rangeTo(that: Double): ClosedFloatingPointRange<Double> {
      return new ClosedDoubleRange(var0, var2);
   }

   @JvmStatic
   public operator fun Float.rangeTo(that: Float): ClosedFloatingPointRange<Float> {
      return new ClosedFloatRange(var0, var1);
   }

   @JvmStatic
   public operator fun <T : Comparable<T>> T.rangeTo(that: T): ClosedRange<T> {
      return new ComparableRange(var0, var1);
   }

   @JvmStatic
   public operator fun Double.rangeUntil(that: Double): OpenEndRange<Double> {
      return new OpenEndDoubleRange(var0, var2);
   }

   @JvmStatic
   public operator fun Float.rangeUntil(that: Float): OpenEndRange<Float> {
      return new OpenEndFloatRange(var0, var1);
   }

   @JvmStatic
   public operator fun <T : Comparable<T>> T.rangeUntil(that: T): OpenEndRange<T> {
      return new ComparableOpenEndRange(var0, var1);
   }
}
