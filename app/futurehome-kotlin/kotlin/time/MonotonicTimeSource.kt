package kotlin.time

import kotlin.time.TimeSource.Monotonic.ValueTimeMark

internal object MonotonicTimeSource : TimeSource.WithComparableMarks {
   private final val zero: Long = System.nanoTime()

   private fun read(): Long {
      return System.nanoTime() - zero;
   }

   public fun adjustReading(timeMark: ValueTimeMark, duration: Duration): ValueTimeMark {
      return TimeSource.Monotonic.ValueTimeMark.constructor-impl(LongSaturatedMathKt.saturatingAdd-NuflL3o(var1, DurationUnit.NANOSECONDS, var3));
   }

   public fun differenceBetween(one: ValueTimeMark, another: ValueTimeMark): Duration {
      return LongSaturatedMathKt.saturatingOriginsDiff(var1, var3, DurationUnit.NANOSECONDS);
   }

   public fun elapsedFrom(timeMark: ValueTimeMark): Duration {
      return LongSaturatedMathKt.saturatingDiff(this.read(), var1, DurationUnit.NANOSECONDS);
   }

   public open fun markNow(): ValueTimeMark {
      return TimeSource.Monotonic.ValueTimeMark.constructor-impl(this.read());
   }

   public override fun toString(): String {
      return "TimeSource(System.nanoTime())";
   }
}
