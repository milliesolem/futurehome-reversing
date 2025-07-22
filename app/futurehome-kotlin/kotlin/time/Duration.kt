package kotlin.time

import kotlin.contracts.InvocationKind
import kotlin.jvm.internal.Intrinsics
import kotlin.math.MathKt

@JvmInline
public inline class Duration : java.lang.Comparable<Duration> {
   private final val rawValue: Long

   private final val value: Long
      private final get() {
         return var0 shr 1;
      }


   private final val unitDiscriminator: Int
      private final inline get() {
         return (int)var0 and 1;
      }


   private final val storageUnit: DurationUnit
      private final get() {
         val var2: DurationUnit;
         if (isInNanos-impl(var0)) {
            var2 = DurationUnit.NANOSECONDS;
         } else {
            var2 = DurationUnit.MILLISECONDS;
         }

         return var2;
      }


   public final val absoluteValue: Duration
      public final get() {
         var var2: Long = var0;
         if (isNegative-impl(var0)) {
            var2 = unaryMinus-UwyO8pc(var0);
         }

         return var2;
      }


   internal final val hoursComponent: Int
      internal final get() {
         val var2: Int;
         if (isInfinite-impl(var0)) {
            var2 = 0;
         } else {
            var2 = (int)(getInWholeHours-impl(var0) % 24);
         }

         return var2;
      }


   internal final val minutesComponent: Int
      internal final get() {
         val var2: Int;
         if (isInfinite-impl(var0)) {
            var2 = 0;
         } else {
            var2 = (int)(getInWholeMinutes-impl(var0) % 60);
         }

         return var2;
      }


   internal final val secondsComponent: Int
      internal final get() {
         val var2: Int;
         if (isInfinite-impl(var0)) {
            var2 = 0;
         } else {
            var2 = (int)(getInWholeSeconds-impl(var0) % 60);
         }

         return var2;
      }


   internal final val nanosecondsComponent: Int
      internal final get() {
         val var2: Int;
         if (isInfinite-impl(var0)) {
            var2 = 0;
         } else {
            if (isInMillis-impl(var0)) {
               var0 = DurationKt.access$millisToNanos(getValue-impl(var0) % (long)1000);
            } else {
               var0 = getValue-impl(var0) % 1000000000;
            }

            var2 = (int)var0;
         }

         return var2;
      }


   public final val inWholeDays: Long
      public final get() {
         return toLong-impl(var0, DurationUnit.DAYS);
      }


   public final val inWholeHours: Long
      public final get() {
         return toLong-impl(var0, DurationUnit.HOURS);
      }


   public final val inWholeMinutes: Long
      public final get() {
         return toLong-impl(var0, DurationUnit.MINUTES);
      }


   public final val inWholeSeconds: Long
      public final get() {
         return toLong-impl(var0, DurationUnit.SECONDS);
      }


   public final val inWholeMilliseconds: Long
      public final get() {
         if (isInMillis-impl(var0) && isFinite-impl(var0)) {
            var0 = getValue-impl(var0);
         } else {
            var0 = toLong-impl(var0, DurationUnit.MILLISECONDS);
         }

         return var0;
      }


   public final val inWholeMicroseconds: Long
      public final get() {
         return toLong-impl(var0, DurationUnit.MICROSECONDS);
      }


   public final val inWholeNanoseconds: Long
      public final get() {
         val var2: Long = getValue-impl(var0);
         if (isInNanos-impl(var0)) {
            var0 = var2;
         } else if (var2 > 9223372036854L) {
            var0 = java.lang.Long.MAX_VALUE;
         } else if (var2 < -9223372036854L) {
            var0 = java.lang.Long.MIN_VALUE;
         } else {
            var0 = DurationKt.access$millisToNanos(var2);
         }

         return var0;
      }


   @JvmStatic
   private fun addValuesMixedRanges(thisMillis: Long, otherNanos: Long): Duration {
      val var6: Long = DurationKt.access$nanosToMillis(var4);
      var0 = var2 + var6;
      if (-4611686018426L <= var2 + var6 && var2 + var6 < 4611686018427L) {
         var0 = DurationKt.access$durationOfNanos(DurationKt.access$millisToNanos(var0) + (var4 - DurationKt.access$millisToNanos(var6)));
      } else {
         var0 = DurationKt.access$durationOfMillis(RangesKt.coerceIn(var0, -4611686018427387903L, 4611686018427387903L));
      }

      return var0;
   }

   @JvmStatic
   private fun StringBuilder.appendFractional(whole: Int, fractional: Int, fractionalSize: Int, unit: String, isoZeroes: Boolean) {
      var2.append(var3);
      if (var4 != 0) {
         var2.append('.');
         val var8: java.lang.CharSequence = StringsKt.padStart(java.lang.String.valueOf(var4), var5, '0');
         var5 = var8.length() - 1;
         var3 = -1;
         if (var5 >= 0) {
            var3 = var5;

            while (true) {
               var5 = var3 - 1;
               if (var8.charAt(var3) != '0') {
                  break;
               }

               if (var5 < 0) {
                  var3 = -1;
                  break;
               }

               var3 = var5;
            }
         }

         var4 = var3 + 1;
         if (!var7 && var3 + 1 < 3) {
            var2.append(var8, 0, var4);
         } else {
            var2.append(var8, 0, (var3 + 3) / 3 * 3);
         }
      }

      var2.append(var6);
   }

   @JvmStatic
   public open operator fun compareTo(other: Duration): Int {
      if ((var0 xor var2) >= 0L && ((int)(var0 xor var2) and 1) != 0) {
         val var5: Int = ((int)var0 and 1) - ((int)var2 and 1);
         var var4: Int = ((int)var0 and 1) - ((int)var2 and 1);
         if (isNegative-impl(var0)) {
            var4 = -var5;
         }

         return var4;
      } else {
         return Intrinsics.compare(var0, var2);
      }
   }

   @JvmStatic
   fun `constructor-impl`(var0: Long): Long {
      if (DurationJvmKt.getDurationAssertionsEnabled()) {
         if (isInNanos-impl(var0)) {
            val var2: Long = getValue-impl(var0);
            if (-4611686018426999999L > var2 || var2 >= 4611686018427000000L) {
               val var4: StringBuilder = new StringBuilder();
               var4.append(getValue-impl(var0));
               var4.append(" ns is out of nanoseconds range");
               throw new AssertionError(var4.toString());
            }
         } else {
            var var5: Long = getValue-impl(var0);
            if (-4611686018427387903L > var5 || var5 >= 4611686018427387904L) {
               val var8: StringBuilder = new StringBuilder();
               var8.append(getValue-impl(var0));
               var8.append(" ms is out of milliseconds range");
               throw new AssertionError(var8.toString());
            }

            var5 = getValue-impl(var0);
            if (-4611686018426L <= var5 && var5 < 4611686018427L) {
               val var7: StringBuilder = new StringBuilder();
               var7.append(getValue-impl(var0));
               var7.append(" ms is denormalized");
               throw new AssertionError(var7.toString());
            }
         }
      }

      return var0;
   }

   @JvmStatic
   public operator fun div(other: Duration): Double {
      val var4: DurationUnit = ComparisonsKt.maxOf(getStorageUnit-impl(var0), getStorageUnit-impl(var2));
      return toDouble-impl(var0, var4) / toDouble-impl(var2, var4);
   }

   @JvmStatic
   public operator fun div(scale: Double): Duration {
      val var4: Int = MathKt.roundToInt(var2);
      if (var4 == var2 && var4 != 0) {
         return div-UwyO8pc(var0, var4);
      } else {
         val var5: DurationUnit = getStorageUnit-impl(var0);
         return DurationKt.toDuration(toDouble-impl(var0, var5) / var2, var5);
      }
   }

   @JvmStatic
   public operator fun div(scale: Int): Duration {
      if (var2 == 0) {
         if (isPositive-impl(var0)) {
            var0 = INFINITE;
         } else {
            if (!isNegative-impl(var0)) {
               throw new IllegalArgumentException("Dividing zero duration by zero yields an undefined result.");
            }

            var0 = NEG_INFINITE;
         }

         return var0;
      } else if (isInNanos-impl(var0)) {
         return DurationKt.access$durationOfNanos(getValue-impl(var0) / (long)var2);
      } else {
         label26:
         if (isInfinite-impl(var0)) {
            return times-UwyO8pc(var0, MathKt.getSign(var2));
         } else {
            val var3: Long = getValue-impl(var0);
            return if (-4611686018426L <= var3 / var2 && var3 / var2 < 4611686018427L)
               DurationKt.access$durationOfNanos(
                  DurationKt.access$millisToNanos(var3 / (long)var2)
                     + DurationKt.access$millisToNanos(getValue-impl(var0) - var3 / (long)var2 * (long)var2) / (long)var2
               )
               else
               DurationKt.access$durationOfMillis(var3 / (long)var2);
         }
      }
   }

   @JvmStatic
   fun `equals-impl`(var0: Long, var2: Any): Boolean {
      if (var2 !is Duration) {
         return false;
      } else {
         return var0 == (var2 as Duration).unbox-impl();
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
   fun `hashCode-impl`(var0: Long): Int {
      return UByte$$ExternalSyntheticBackport0.m(var0);
   }

   @JvmStatic
   public fun isFinite(): Boolean {
      return isInfinite-impl(var0) xor true;
   }

   @JvmStatic
   private fun isInMillis(): Boolean {
      val var2: Int = (int)var0;
      var var3: Boolean = true;
      if ((var2 and 1) != 1) {
         var3 = false;
      }

      return var3;
   }

   @JvmStatic
   private fun isInNanos(): Boolean {
      val var2: Int = (int)var0;
      var var3: Boolean = true;
      if ((var2 and 1) != 0) {
         var3 = false;
      }

      return var3;
   }

   @JvmStatic
   public fun isInfinite(): Boolean {
      val var2: Boolean;
      if (var0 != INFINITE && var0 != NEG_INFINITE) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   @JvmStatic
   public fun isNegative(): Boolean {
      val var2: Boolean;
      if (var0 < 0L) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   @JvmStatic
   public fun isPositive(): Boolean {
      val var2: Boolean;
      if (var0 > 0L) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   @JvmStatic
   public operator fun minus(other: Duration): Duration {
      return plus-LRDsOJo(var0, unaryMinus-UwyO8pc(var2));
   }

   @JvmStatic
   public operator fun plus(other: Duration): Duration {
      if (isInfinite-impl(var0)) {
         if (!isFinite-impl(var2) && (var2 xor var0) < 0L) {
            throw new IllegalArgumentException("Summing infinite durations of different signs yields an undefined result.");
         } else {
            return var0;
         }
      } else if (isInfinite-impl(var2)) {
         return var2;
      } else {
         if (((int)var0 and 1) == ((int)var2 and 1)) {
            var2 = getValue-impl(var0) + getValue-impl(var2);
            if (isInNanos-impl(var0)) {
               var0 = DurationKt.access$durationOfNanosNormalized(var2);
            } else {
               var0 = DurationKt.access$durationOfMillisNormalized(var2);
            }
         } else if (isInMillis-impl(var0)) {
            var0 = addValuesMixedRanges-UwyO8pc(var0, getValue-impl(var0), getValue-impl(var2));
         } else {
            var0 = addValuesMixedRanges-UwyO8pc(var0, getValue-impl(var2), getValue-impl(var0));
         }

         return var0;
      }
   }

   @JvmStatic
   public operator fun times(scale: Double): Duration {
      val var4: Int = MathKt.roundToInt(var2);
      if (var4 == var2) {
         return times-UwyO8pc(var0, var4);
      } else {
         val var5: DurationUnit = getStorageUnit-impl(var0);
         return DurationKt.toDuration(toDouble-impl(var0, var5) * var2, var5);
      }
   }

   @JvmStatic
   public operator fun times(scale: Int): Duration {
      if (isInfinite-impl(var0)) {
         if (var2 != 0) {
            if (var2 <= 0) {
               var0 = unaryMinus-UwyO8pc(var0);
            }

            return var0;
         } else {
            throw new IllegalArgumentException("Multiplying infinite duration by zero yields an undefined result.");
         }
      } else if (var2 == 0) {
         return ZERO;
      } else {
         val var5: Long = getValue-impl(var0);
         val var3: Long = var2;
         var var7: Long = var5 * var2;
         if (isInNanos-impl(var0)) {
            if (-2147483647L <= var5 && var5 < 2147483648L) {
               var0 = DurationKt.access$durationOfNanos(var7);
            } else if (var7 / var3 == var5) {
               var0 = DurationKt.access$durationOfNanosNormalized(var7);
            } else {
               var0 = DurationKt.access$nanosToMillis(var5);
               var var9: Long = DurationKt.access$millisToNanos(var0);
               var7 = var0 * var3;
               var9 = DurationKt.access$nanosToMillis((var5 - var9) * var3) + var0 * var3;
               if (var7 / var3 == var0 && (var9 xor var7) >= 0L) {
                  var0 = DurationKt.access$durationOfMillis(RangesKt.coerceIn(var9, new LongRange(-4611686018427387903L, 4611686018427387903L)));
               } else if (MathKt.getSign(var5) * MathKt.getSign(var2) > 0) {
                  var0 = INFINITE;
               } else {
                  var0 = NEG_INFINITE;
               }
            }
         } else if (var7 / var3 == var5) {
            var0 = DurationKt.access$durationOfMillis(RangesKt.coerceIn(var7, new LongRange(-4611686018427387903L, 4611686018427387903L)));
         } else if (MathKt.getSign(var5) * MathKt.getSign(var2) > 0) {
            var0 = INFINITE;
         } else {
            var0 = NEG_INFINITE;
         }

         return var0;
      }
   }

   @JvmStatic
   public inline fun <T> toComponents(action: (Long, Int) -> T): T {
      contract {
         callsInPlace(action, InvocationKind.EXACTLY_ONCE)
      }

      return (T)var2.invoke(getInWholeSeconds-impl(var0), getNanosecondsComponent-impl(var0));
   }

   @JvmStatic
   public inline fun <T> toComponents(action: (Long, Int, Int) -> T): T {
      contract {
         callsInPlace(action, InvocationKind.EXACTLY_ONCE)
      }

      return (T)var2.invoke(getInWholeMinutes-impl(var0), getSecondsComponent-impl(var0), getNanosecondsComponent-impl(var0));
   }

   @JvmStatic
   public inline fun <T> toComponents(action: (Long, Int, Int, Int) -> T): T {
      contract {
         callsInPlace(action, InvocationKind.EXACTLY_ONCE)
      }

      return (T)var2.invoke(getInWholeHours-impl(var0), getMinutesComponent-impl(var0), getSecondsComponent-impl(var0), getNanosecondsComponent-impl(var0));
   }

   @JvmStatic
   public inline fun <T> toComponents(action: (Long, Int, Int, Int, Int) -> T): T {
      contract {
         callsInPlace(action, InvocationKind.EXACTLY_ONCE)
      }

      return (T)var2.invoke(
         getInWholeDays-impl(var0),
         getHoursComponent-impl(var0),
         getMinutesComponent-impl(var0),
         getSecondsComponent-impl(var0),
         getNanosecondsComponent-impl(var0)
      );
   }

   @JvmStatic
   public fun toDouble(unit: DurationUnit): Double {
      val var3: Double;
      if (var0 == INFINITE) {
         var3 = java.lang.Double.POSITIVE_INFINITY;
      } else if (var0 == NEG_INFINITE) {
         var3 = java.lang.Double.NEGATIVE_INFINITY;
      } else {
         var3 = DurationUnitKt.convertDurationUnit((double)getValue-impl(var0), getStorageUnit-impl(var0), var2);
      }

      return var3;
   }

   @JvmStatic
   public fun toInt(unit: DurationUnit): Int {
      return (int)RangesKt.coerceIn(toLong-impl(var0, var2), -2147483648L, 2147483647L);
   }

   @JvmStatic
   public fun toIsoString(): String {
      val var13: StringBuilder = new StringBuilder();
      if (isNegative-impl(var0)) {
         var13.append('-');
      }

      var13.append("PT");
      val var11: Long = getAbsoluteValue-UwyO8pc(var0);
      var var9: Long = getInWholeHours-impl(var11);
      val var8: Int = getMinutesComponent-impl(var11);
      val var6: Int = getSecondsComponent-impl(var11);
      val var7: Int = getNanosecondsComponent-impl(var11);
      if (isInfinite-impl(var0)) {
         var9 = 9999999999999L;
      }

      val var2: Boolean;
      if (var9 != 0L) {
         var2 = true;
      } else {
         var2 = false;
      }

      val var3: Boolean;
      if (var6 == 0 && var7 == 0) {
         var3 = false;
      } else {
         var3 = true;
      }

      var var4: Boolean = true;
      if (var8 == 0) {
         if (var3 && var2) {
            var4 = true;
         } else {
            var4 = false;
         }
      }

      if (var2) {
         var13.append(var9);
         var13.append('H');
      }

      if (var4) {
         var13.append(var8);
         var13.append('M');
      }

      if (var3 || !var2 && !var4) {
         appendFractional-impl(var0, var13, var6, var7, 9, "S", true);
      }

      return var13.toString();
   }

   @JvmStatic
   public fun toLong(unit: DurationUnit): Long {
      if (var0 == INFINITE) {
         var0 = java.lang.Long.MAX_VALUE;
      } else if (var0 == NEG_INFINITE) {
         var0 = java.lang.Long.MIN_VALUE;
      } else {
         var0 = DurationUnitKt.convertDurationUnit(getValue-impl(var0), getStorageUnit-impl(var0), var2);
      }

      return var0;
   }

   @JvmStatic
   public open fun toString(): String {
      val var17: java.lang.String;
      if (var0 == 0L) {
         var17 = "0s";
      } else if (var0 == INFINITE) {
         var17 = "Infinity";
      } else if (var0 == NEG_INFINITE) {
         var17 = "-Infinity";
      } else {
         val var16: Boolean = isNegative-impl(var0);
         val var20: StringBuilder = new StringBuilder();
         if (var16) {
            var20.append('-');
         }

         val var14: Long = getAbsoluteValue-UwyO8pc(var0);
         val var12: Long = getInWholeDays-impl(var14);
         val var11: Int = getHoursComponent-impl(var14);
         val var10: Int = getMinutesComponent-impl(var14);
         val var8: Int = getSecondsComponent-impl(var14);
         val var9: Int = getNanosecondsComponent-impl(var14);
         var var3: Int = 0;
         val var4: Boolean;
         if (var12 != 0L) {
            var4 = true;
         } else {
            var4 = false;
         }

         val var5: Boolean;
         if (var11 != 0) {
            var5 = true;
         } else {
            var5 = false;
         }

         val var6: Boolean;
         if (var10 != 0) {
            var6 = true;
         } else {
            var6 = false;
         }

         val var7: Boolean;
         if (var8 == 0 && var9 == 0) {
            var7 = false;
         } else {
            var7 = true;
         }

         if (var4) {
            var20.append(var12);
            var20.append('d');
            var3 = 1;
         }

         var var2: Int;
         label120: {
            if (!var5) {
               var2 = var3;
               if (!var4) {
                  break label120;
               }

               if (!var6) {
                  var2 = var3;
                  if (!var7) {
                     break label120;
                  }
               }
            }

            if (var3 > 0) {
               var20.append(' ');
            }

            var20.append(var11);
            var20.append('h');
            var2 = var3 + 1;
         }

         label121: {
            if (!var6) {
               var3 = var2;
               if (!var7) {
                  break label121;
               }

               if (!var5) {
                  var3 = var2;
                  if (!var4) {
                     break label121;
                  }
               }
            }

            if (var2 > 0) {
               var20.append(' ');
            }

            var20.append(var10);
            var20.append('m');
            var3 = var2 + 1;
         }

         var2 = var3;
         if (var7) {
            if (var3 > 0) {
               var20.append(' ');
            }

            if (var8 != 0 || var4 || var5 || var6) {
               appendFractional-impl(var0, var20, var8, var9, 9, "s", false);
            } else if (var9 >= 1000000) {
               appendFractional-impl(var0, var20, var9 / 1000000, var9 % 1000000, 6, "ms", false);
            } else if (var9 >= 1000) {
               appendFractional-impl(var0, var20, var9 / 1000, var9 % 1000, 3, "us", false);
            } else {
               var20.append(var9);
               var20.append("ns");
            }

            var2 = var3 + 1;
         }

         if (var16 && var2 > 1) {
            var20.insert(1, '(').append(')');
         }

         var17 = var20.toString();
      }

      return var17;
   }

   @JvmStatic
   public fun toString(unit: DurationUnit, decimals: Int = ...): String {
      if (var3 >= 0) {
         val var4: Double = toDouble-impl(var0, var2);
         if (java.lang.Double.isInfinite(var4)) {
            return java.lang.String.valueOf(var4);
         } else {
            val var6: StringBuilder = new StringBuilder();
            var6.append(DurationJvmKt.formatToExactDecimals(var4, RangesKt.coerceAtMost(var3, 12)));
            var6.append(DurationUnitKt.shortName(var2));
            return var6.toString();
         }
      } else {
         val var7: StringBuilder = new StringBuilder("decimals must be not negative, but was ");
         var7.append(var3);
         throw new IllegalArgumentException(var7.toString().toString());
      }
   }

   @JvmStatic
   internal fun truncateTo(unit: DurationUnit): Duration {
      val var5: DurationUnit = getStorageUnit-impl(var0);
      var var3: Long = var0;
      if (var2.compareTo(var5) > 0) {
         if (isInfinite-impl(var0)) {
            var3 = var0;
         } else {
            var3 = DurationKt.toDuration(getValue-impl(var0) - getValue-impl(var0) % DurationUnitKt.convertDurationUnit(1L, var2, var5), var5);
         }
      }

      return var3;
   }

   @JvmStatic
   public operator fun unaryMinus(): Duration {
      return DurationKt.access$durationOf(-getValue-impl(var0), (int)var0 and 1);
   }

   fun `compareTo-LRDsOJo`(var1: Long): Int {
      return compareTo-LRDsOJo(this.rawValue, var1);
   }

   public override operator fun equals(other: Any?): Boolean {
      return equals-impl(this.rawValue, var1);
   }

   public override fun hashCode(): Int {
      return hashCode-impl(this.rawValue);
   }

   override fun toString(): java.lang.String {
      return toString-impl(this.rawValue);
   }

   public companion object {
      public final val ZERO: Duration
      public final val INFINITE: Duration
      internal final val NEG_INFINITE: Duration

      public final val nanoseconds: Duration
         public final inline get() {
            return DurationKt.toDuration(var1, DurationUnit.NANOSECONDS);
         }


      public final val nanoseconds: Duration
         public final inline get() {
            return DurationKt.toDuration(var1, DurationUnit.NANOSECONDS);
         }


      public final val nanoseconds: Duration
         public final inline get() {
            return DurationKt.toDuration(var1, DurationUnit.NANOSECONDS);
         }


      public final val microseconds: Duration
         public final inline get() {
            return DurationKt.toDuration(var1, DurationUnit.MICROSECONDS);
         }


      public final val microseconds: Duration
         public final inline get() {
            return DurationKt.toDuration(var1, DurationUnit.MICROSECONDS);
         }


      public final val microseconds: Duration
         public final inline get() {
            return DurationKt.toDuration(var1, DurationUnit.MICROSECONDS);
         }


      public final val milliseconds: Duration
         public final inline get() {
            return DurationKt.toDuration(var1, DurationUnit.MILLISECONDS);
         }


      public final val milliseconds: Duration
         public final inline get() {
            return DurationKt.toDuration(var1, DurationUnit.MILLISECONDS);
         }


      public final val milliseconds: Duration
         public final inline get() {
            return DurationKt.toDuration(var1, DurationUnit.MILLISECONDS);
         }


      public final val seconds: Duration
         public final inline get() {
            return DurationKt.toDuration(var1, DurationUnit.SECONDS);
         }


      public final val seconds: Duration
         public final inline get() {
            return DurationKt.toDuration(var1, DurationUnit.SECONDS);
         }


      public final val seconds: Duration
         public final inline get() {
            return DurationKt.toDuration(var1, DurationUnit.SECONDS);
         }


      public final val minutes: Duration
         public final inline get() {
            return DurationKt.toDuration(var1, DurationUnit.MINUTES);
         }


      public final val minutes: Duration
         public final inline get() {
            return DurationKt.toDuration(var1, DurationUnit.MINUTES);
         }


      public final val minutes: Duration
         public final inline get() {
            return DurationKt.toDuration(var1, DurationUnit.MINUTES);
         }


      public final val hours: Duration
         public final inline get() {
            return DurationKt.toDuration(var1, DurationUnit.HOURS);
         }


      public final val hours: Duration
         public final inline get() {
            return DurationKt.toDuration(var1, DurationUnit.HOURS);
         }


      public final val hours: Duration
         public final inline get() {
            return DurationKt.toDuration(var1, DurationUnit.HOURS);
         }


      public final val days: Duration
         public final inline get() {
            return DurationKt.toDuration(var1, DurationUnit.DAYS);
         }


      public final val days: Duration
         public final inline get() {
            return DurationKt.toDuration(var1, DurationUnit.DAYS);
         }


      public final val days: Duration
         public final inline get() {
            return DurationKt.toDuration(var1, DurationUnit.DAYS);
         }


      public fun convert(value: Double, sourceUnit: DurationUnit, targetUnit: DurationUnit): Double {
         return DurationUnitKt.convertDurationUnit(var1, var3, var4);
      }

      public fun parse(value: String): Duration {
         try {
            return DurationKt.access$parseDuration(var1, false);
         } catch (var6: IllegalArgumentException) {
            val var5: StringBuilder = new StringBuilder("Invalid duration string format: '");
            var5.append(var1);
            var5.append("'.");
            throw new IllegalArgumentException(var5.toString(), var6);
         }
      }

      public fun parseIsoString(value: String): Duration {
         try {
            return DurationKt.access$parseDuration(var1, true);
         } catch (var6: IllegalArgumentException) {
            val var5: StringBuilder = new StringBuilder("Invalid ISO duration string format: '");
            var5.append(var1);
            var5.append("'.");
            throw new IllegalArgumentException(var5.toString(), var6);
         }
      }

      public fun parseIsoStringOrNull(value: String): Duration? {
         try {
            var3 = Duration.box-impl(DurationKt.access$parseDuration(var1, true));
         } catch (var2: IllegalArgumentException) {
            var3 = null;
         }

         return var3;
      }

      public fun parseOrNull(value: String): Duration? {
         try {
            var3 = Duration.box-impl(DurationKt.access$parseDuration(var1, false));
         } catch (var2: IllegalArgumentException) {
            var3 = null;
         }

         return var3;
      }
   }
}
