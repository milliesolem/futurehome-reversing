package kotlin.time

import java.util.concurrent.TimeUnit

internal class DurationUnitKt__DurationUnitJvmKt {
   open fun DurationUnitKt__DurationUnitJvmKt() {
   }

   @JvmStatic
   internal fun convertDurationUnit(value: Double, sourceUnit: DurationUnit, targetUnit: DurationUnit): Double {
      val var4: Long = var3.getTimeUnit$kotlin_stdlib().convert(1L, var2.getTimeUnit$kotlin_stdlib());
      return if (var4 > 0L) var0 * var4 else var0 / var2.getTimeUnit$kotlin_stdlib().convert(1L, var3.getTimeUnit$kotlin_stdlib());
   }

   @JvmStatic
   internal fun convertDurationUnit(value: Long, sourceUnit: DurationUnit, targetUnit: DurationUnit): Long {
      return var3.getTimeUnit$kotlin_stdlib().convert(var0, var2.getTimeUnit$kotlin_stdlib());
   }

   @JvmStatic
   internal fun convertDurationUnitOverflow(value: Long, sourceUnit: DurationUnit, targetUnit: DurationUnit): Long {
      return var3.getTimeUnit$kotlin_stdlib().convert(var0, var2.getTimeUnit$kotlin_stdlib());
   }

   @JvmStatic
   public fun TimeUnit.toDurationUnit(): DurationUnit {
      var var1: DurationUnit;
      switch (DurationUnitKt__DurationUnitJvmKt.WhenMappings.$EnumSwitchMapping$0[var0.ordinal()]) {
         case 1:
            var1 = DurationUnit.NANOSECONDS;
            break;
         case 2:
            var1 = DurationUnit.MICROSECONDS;
            break;
         case 3:
            var1 = DurationUnit.MILLISECONDS;
            break;
         case 4:
            var1 = DurationUnit.SECONDS;
            break;
         case 5:
            var1 = DurationUnit.MINUTES;
            break;
         case 6:
            var1 = DurationUnit.HOURS;
            break;
         case 7:
            var1 = DurationUnit.DAYS;
            break;
         default:
            throw new NoWhenBranchMatchedException();
      }

      return var1;
   }

   @JvmStatic
   public fun DurationUnit.toTimeUnit(): TimeUnit {
      return var0.getTimeUnit$kotlin_stdlib();
   }
}
