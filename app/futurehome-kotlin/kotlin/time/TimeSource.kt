package kotlin.time

public interface TimeSource {
   public abstract fun markNow(): TimeMark {
   }

   public companion object

   public object Monotonic : TimeSource.WithComparableMarks {
      public open fun markNow(): kotlin.time.TimeSource.Monotonic.ValueTimeMark {
         return MonotonicTimeSource.INSTANCE.markNow-z9LOYto();
      }

      public override fun toString(): String {
         return MonotonicTimeSource.INSTANCE.toString();
      }

      @JvmInline
      public inline class ValueTimeMark : ComparableTimeMark {
         internal final val reading: Long

         @JvmStatic
         public operator fun compareTo(other: kotlin.time.TimeSource.Monotonic.ValueTimeMark): Int {
            return Duration.compareTo-LRDsOJo(minus-6eNON_k(var0, var2), Duration.Companion.getZERO-UwyO8pc());
         }

         @JvmStatic
         fun `compareTo-impl`(var0: Long, var2: ComparableTimeMark): Int {
            return box-impl(var0).compareTo(var2);
         }

         @JvmStatic
         fun `constructor-impl`(var0: Long): Long {
            return var0;
         }

         @JvmStatic
         public open fun elapsedNow(): Duration {
            return MonotonicTimeSource.INSTANCE.elapsedFrom-6eNON_k(var0);
         }

         @JvmStatic
         fun `equals-impl`(var0: Long, var2: Any): Boolean {
            if (var2 !is TimeSource.Monotonic.ValueTimeMark) {
               return false;
            } else {
               return var0 == (var2 as TimeSource.Monotonic.ValueTimeMark).unbox-impl();
            }
         }

         @JvmStatic
         fun `equals-impl0`(var0: Long, var2: Long): Boolean {
            val var4: Boolean;
            if (var0 == var2) {
               var4 = true;
            } else {
               var4 = false;
            }

            return var4;
         }

         @JvmStatic
         public open fun hasNotPassedNow(): Boolean {
            return Duration.isNegative-impl(elapsedNow-UwyO8pc(var0));
         }

         @JvmStatic
         public open fun hasPassedNow(): Boolean {
            return Duration.isNegative-impl(elapsedNow-UwyO8pc(var0)) xor true;
         }

         @JvmStatic
         fun `hashCode-impl`(var0: Long): Int {
            return UByte$$ExternalSyntheticBackport0.m(var0);
         }

         @JvmStatic
         public operator fun minus(other: kotlin.time.TimeSource.Monotonic.ValueTimeMark): Duration {
            return MonotonicTimeSource.INSTANCE.differenceBetween-fRLX17w(var0, var2);
         }

         @JvmStatic
         public open operator fun minus(duration: Duration): kotlin.time.TimeSource.Monotonic.ValueTimeMark {
            return MonotonicTimeSource.INSTANCE.adjustReading-6QKq23U(var0, Duration.unaryMinus-UwyO8pc(var2));
         }

         @JvmStatic
         public open operator fun minus(other: ComparableTimeMark): Duration {
            if (var2 is TimeSource.Monotonic.ValueTimeMark) {
               return minus-6eNON_k(var0, (var2 as TimeSource.Monotonic.ValueTimeMark).unbox-impl());
            } else {
               val var3: StringBuilder = new StringBuilder("Subtracting or comparing time marks from different time sources is not possible: ");
               var3.append(toString-impl(var0));
               var3.append(" and ");
               var3.append(var2);
               throw new IllegalArgumentException(var3.toString());
            }
         }

         @JvmStatic
         public open operator fun plus(duration: Duration): kotlin.time.TimeSource.Monotonic.ValueTimeMark {
            return MonotonicTimeSource.INSTANCE.adjustReading-6QKq23U(var0, var2);
         }

         @JvmStatic
         fun `toString-impl`(var0: Long): java.lang.String {
            val var2: StringBuilder = new StringBuilder("ValueTimeMark(reading=");
            var2.append(var0);
            var2.append(')');
            return var2.toString();
         }

         override fun compareTo(var1: ComparableTimeMark): Int {
            return ComparableTimeMark.DefaultImpls.compareTo(this, var1);
         }

         override fun `elapsedNow-UwyO8pc`(): Long {
            return elapsedNow-UwyO8pc(this.reading);
         }

         public override operator fun equals(other: Any?): Boolean {
            return equals-impl(this.reading, var1);
         }

         override fun hasNotPassedNow(): Boolean {
            return hasNotPassedNow-impl(this.reading);
         }

         override fun hasPassedNow(): Boolean {
            return hasPassedNow-impl(this.reading);
         }

         public override fun hashCode(): Int {
            return hashCode-impl(this.reading);
         }

         fun `minus-LRDsOJo`(var1: Long): Long {
            return minus-LRDsOJo(this.reading, var1);
         }

         override fun `minus-UwyO8pc`(var1: ComparableTimeMark): Long {
            return minus-UwyO8pc(this.reading, var1);
         }

         fun `plus-LRDsOJo`(var1: Long): Long {
            return plus-LRDsOJo(this.reading, var1);
         }

         public override fun toString(): String {
            return toString-impl(this.reading);
         }
      }
   }

   public interface WithComparableMarks : TimeSource {
      public abstract fun markNow(): ComparableTimeMark {
      }
   }
}
