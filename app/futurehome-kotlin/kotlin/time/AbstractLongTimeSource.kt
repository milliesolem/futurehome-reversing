package kotlin.time

import kotlin.math.MathKt

public abstract class AbstractLongTimeSource : TimeSource.WithComparableMarks {
   protected final val unit: DurationUnit

   private final val zero: Long
      private final get() {
         return (this.zero$delegate.getValue() as java.lang.Number).longValue();
      }


   open fun AbstractLongTimeSource(var1: DurationUnit) {
      this.unit = var1;
      this.zero$delegate = LazyKt.lazy(new AbstractLongTimeSource$$ExternalSyntheticLambda0(this));
   }

   private fun adjustedRead(): Long {
      return this.read() - this.getZero();
   }

   @JvmStatic
   fun `zero_delegate$lambda$0`(var0: AbstractLongTimeSource): Long {
      return var0.read();
   }

   public override fun markNow(): ComparableTimeMark {
      return new AbstractLongTimeSource.LongTimeMark(this.adjustedRead(), this, Duration.Companion.getZERO-UwyO8pc(), null);
   }

   protected abstract fun read(): Long {
   }

   private class LongTimeMark(startedAt: Long, timeSource: AbstractLongTimeSource, offset: Duration) : AbstractLongTimeSource.LongTimeMark(var1, var3, var4),
      ComparableTimeMark {
      private final val startedAt: Long
      private final val timeSource: AbstractLongTimeSource
      private final val offset: Duration

      fun LongTimeMark(var1: Long, var3: AbstractLongTimeSource, var4: Long) {
         this.startedAt = var1;
         this.timeSource = var3;
         this.offset = var4;
      }

      override fun compareTo(var1: ComparableTimeMark): Int {
         return ComparableTimeMark.DefaultImpls.compareTo(this, var1);
      }

      public override fun elapsedNow(): Duration {
         return Duration.minus-LRDsOJo(
            LongSaturatedMathKt.saturatingOriginsDiff(AbstractLongTimeSource.access$adjustedRead(this.timeSource), this.startedAt, this.timeSource.getUnit()),
            this.offset
         );
      }

      public override operator fun equals(other: Any?): Boolean {
         val var2: Boolean;
         if (var1 is AbstractLongTimeSource.LongTimeMark
            && this.timeSource == (var1 as AbstractLongTimeSource.LongTimeMark).timeSource
            && Duration.equals-impl0(this.minus-UwyO8pc(var1 as ComparableTimeMark), Duration.Companion.getZERO-UwyO8pc())) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      override fun hasNotPassedNow(): Boolean {
         return ComparableTimeMark.DefaultImpls.hasNotPassedNow(this);
      }

      override fun hasPassedNow(): Boolean {
         return ComparableTimeMark.DefaultImpls.hasPassedNow(this);
      }

      public override fun hashCode(): Int {
         return Duration.hashCode-impl(this.offset) * 37 + UByte$$ExternalSyntheticBackport0.m(this.startedAt);
      }

      override fun `minus-LRDsOJo`(var1: Long): ComparableTimeMark {
         return ComparableTimeMark.DefaultImpls.minus-LRDsOJo(this, var1);
      }

      public override operator fun minus(other: ComparableTimeMark): Duration {
         if (var1 is AbstractLongTimeSource.LongTimeMark) {
            val var2: AbstractLongTimeSource.LongTimeMark = var1 as AbstractLongTimeSource.LongTimeMark;
            if (this.timeSource == (var1 as AbstractLongTimeSource.LongTimeMark).timeSource) {
               return Duration.plus-LRDsOJo(
                  LongSaturatedMathKt.saturatingOriginsDiff(this.startedAt, var2.startedAt, this.timeSource.getUnit()),
                  Duration.minus-LRDsOJo(this.offset, var2.offset)
               );
            }
         }

         val var4: StringBuilder = new StringBuilder("Subtracting or comparing time marks from different time sources is not possible: ");
         var4.append(this);
         var4.append(" and ");
         var4.append(var1);
         throw new IllegalArgumentException(var4.toString());
      }

      public override operator fun plus(duration: Duration): ComparableTimeMark {
         val var11: DurationUnit = this.timeSource.getUnit();
         if (Duration.isInfinite-impl(var1)) {
            return new AbstractLongTimeSource.LongTimeMark(
               LongSaturatedMathKt.saturatingAdd-NuflL3o(this.startedAt, var11, var1), this.timeSource, Duration.Companion.getZERO-UwyO8pc(), null
            );
         } else {
            var var3: Long = Duration.truncateTo-UwyO8pc$kotlin_stdlib(var1, var11);
            var1 = Duration.plus-LRDsOJo(Duration.minus-LRDsOJo(var1, var3), this.offset);
            var var5: Long = LongSaturatedMathKt.saturatingAdd-NuflL3o(this.startedAt, var11, var3);
            var3 = Duration.truncateTo-UwyO8pc$kotlin_stdlib(var1, var11);
            var5 = LongSaturatedMathKt.saturatingAdd-NuflL3o(var5, var11, var3);
            val var7: Long = Duration.minus-LRDsOJo(var1, var3);
            val var9: Long = Duration.getInWholeNanoseconds-impl(var7);
            var3 = var5;
            var1 = var7;
            if (var5 != 0L) {
               var3 = var5;
               var1 = var7;
               if (var9 != 0L) {
                  var3 = var5;
                  var1 = var7;
                  if ((var5 xor var9) < 0L) {
                     var1 = DurationKt.toDuration(MathKt.getSign(var9), var11);
                     var3 = LongSaturatedMathKt.saturatingAdd-NuflL3o(var5, var11, var1);
                     var1 = Duration.minus-LRDsOJo(var7, var1);
                  }
               }
            }

            if ((1L or var3 - 1L) == java.lang.Long.MAX_VALUE) {
               var1 = Duration.Companion.getZERO-UwyO8pc();
            }

            return new AbstractLongTimeSource.LongTimeMark(var3, this.timeSource, var1, null);
         }
      }

      public override fun toString(): String {
         val var1: StringBuilder = new StringBuilder("LongTimeMark(");
         var1.append(this.startedAt);
         var1.append(DurationUnitKt.shortName(this.timeSource.getUnit()));
         var1.append(" + ");
         var1.append(Duration.toString-impl(this.offset));
         var1.append(", ");
         var1.append(this.timeSource);
         var1.append(')');
         return var1.toString();
      }
   }
}
