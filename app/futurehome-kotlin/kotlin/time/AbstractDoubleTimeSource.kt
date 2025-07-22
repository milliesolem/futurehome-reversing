package kotlin.time

@Deprecated(message = "Using AbstractDoubleTimeSource is no longer recommended, use AbstractLongTimeSource instead.")
public abstract class AbstractDoubleTimeSource : TimeSource.WithComparableMarks {
   protected final val unit: DurationUnit

   open fun AbstractDoubleTimeSource(var1: DurationUnit) {
      this.unit = var1;
   }

   public override fun markNow(): ComparableTimeMark {
      return new AbstractDoubleTimeSource.DoubleTimeMark(this.read(), this, Duration.Companion.getZERO-UwyO8pc(), null);
   }

   protected abstract fun read(): Double {
   }

   private class DoubleTimeMark(startedAt: Double, timeSource: AbstractDoubleTimeSource, offset: Duration) : AbstractDoubleTimeSource.DoubleTimeMark(
            var1, var3, var4
         ),
      ComparableTimeMark {
      private final val startedAt: Double
      private final val timeSource: AbstractDoubleTimeSource
      private final val offset: Duration

      fun DoubleTimeMark(var1: Double, var3: AbstractDoubleTimeSource, var4: Long) {
         this.startedAt = var1;
         this.timeSource = var3;
         this.offset = var4;
      }

      override fun compareTo(var1: ComparableTimeMark): Int {
         return ComparableTimeMark.DefaultImpls.compareTo(this, var1);
      }

      public override fun elapsedNow(): Duration {
         return Duration.minus-LRDsOJo(DurationKt.toDuration(this.timeSource.read() - this.startedAt, this.timeSource.getUnit()), this.offset);
      }

      public override operator fun equals(other: Any?): Boolean {
         val var2: Boolean;
         if (var1 is AbstractDoubleTimeSource.DoubleTimeMark
            && this.timeSource == (var1 as AbstractDoubleTimeSource.DoubleTimeMark).timeSource
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
         return Duration.hashCode-impl(Duration.plus-LRDsOJo(DurationKt.toDuration(this.startedAt, this.timeSource.getUnit()), this.offset));
      }

      override fun `minus-LRDsOJo`(var1: Long): ComparableTimeMark {
         return ComparableTimeMark.DefaultImpls.minus-LRDsOJo(this, var1);
      }

      public override operator fun minus(other: ComparableTimeMark): Duration {
         if (var1 is AbstractDoubleTimeSource.DoubleTimeMark) {
            val var7: AbstractDoubleTimeSource.DoubleTimeMark = var1 as AbstractDoubleTimeSource.DoubleTimeMark;
            if (this.timeSource == (var1 as AbstractDoubleTimeSource.DoubleTimeMark).timeSource) {
               if (Duration.equals-impl0(this.offset, var7.offset) && Duration.isInfinite-impl(this.offset)) {
                  return Duration.Companion.getZERO-UwyO8pc();
               }

               val var4: Long = Duration.minus-LRDsOJo(this.offset, var7.offset);
               var var2: Long = DurationKt.toDuration(this.startedAt - var7.startedAt, this.timeSource.getUnit());
               if (Duration.equals-impl0(var2, Duration.unaryMinus-UwyO8pc(var4))) {
                  var2 = Duration.Companion.getZERO-UwyO8pc();
               } else {
                  var2 = Duration.plus-LRDsOJo(var2, var4);
               }

               return var2;
            }
         }

         val var9: StringBuilder = new StringBuilder("Subtracting or comparing time marks from different time sources is not possible: ");
         var9.append(this);
         var9.append(" and ");
         var9.append(var1);
         throw new IllegalArgumentException(var9.toString());
      }

      public override operator fun plus(duration: Duration): ComparableTimeMark {
         return new AbstractDoubleTimeSource.DoubleTimeMark(this.startedAt, this.timeSource, Duration.plus-LRDsOJo(this.offset, var1), null);
      }

      public override fun toString(): String {
         val var1: StringBuilder = new StringBuilder("DoubleTimeMark(");
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
