package kotlin.ranges

import java.util.NoSuchElementException
import kotlin.jvm.internal.Intrinsics
import kotlin.random.Random
import kotlin.random.RandomKt

internal class RangesKt___RangesKt : RangesKt__RangesKt {
   open fun RangesKt___RangesKt() {
   }

   @JvmStatic
   public operator fun ClosedRange<Byte>.contains(value: Int): Boolean {
      val var3: java.lang.Byte = RangesKt.toByteExactOrNull(var1);
      val var2: Boolean;
      if (var3 != null) {
         var2 = var0.contains(var3);
      } else {
         var2 = false;
      }

      return var2;
   }

   @JvmStatic
   public operator fun ClosedRange<Byte>.contains(value: Long): Boolean {
      val var4: java.lang.Byte = RangesKt.toByteExactOrNull(var1);
      val var3: Boolean;
      if (var4 != null) {
         var3 = var0.contains(var4);
      } else {
         var3 = false;
      }

      return var3;
   }

   @JvmStatic
   public operator fun ClosedRange<Byte>.contains(value: Short): Boolean {
      val var3: java.lang.Byte = RangesKt.toByteExactOrNull(var1);
      val var2: Boolean;
      if (var3 != null) {
         var2 = var0.contains(var3);
      } else {
         var2 = false;
      }

      return var2;
   }

   @JvmStatic
   public operator fun OpenEndRange<Byte>.contains(value: Int): Boolean {
      val var3: java.lang.Byte = RangesKt.toByteExactOrNull(var1);
      val var2: Boolean;
      if (var3 != null) {
         var2 = var0.contains(var3);
      } else {
         var2 = false;
      }

      return var2;
   }

   @JvmStatic
   public operator fun OpenEndRange<Byte>.contains(value: Long): Boolean {
      val var4: java.lang.Byte = RangesKt.toByteExactOrNull(var1);
      val var3: Boolean;
      if (var4 != null) {
         var3 = var0.contains(var4);
      } else {
         var3 = false;
      }

      return var3;
   }

   @JvmStatic
   public operator fun OpenEndRange<Byte>.contains(value: Short): Boolean {
      val var3: java.lang.Byte = RangesKt.toByteExactOrNull(var1);
      val var2: Boolean;
      if (var3 != null) {
         var2 = var0.contains(var3);
      } else {
         var2 = false;
      }

      return var2;
   }

   @JvmStatic
   public fun Byte.coerceAtLeast(minimumValue: Byte): Byte {
      var var2: Byte = var0;
      if (var0 < var1) {
         var2 = var1;
      }

      return var2;
   }

   @JvmStatic
   public fun Double.coerceAtLeast(minimumValue: Double): Double {
      var var4: Double = var0;
      if (var0 < var2) {
         var4 = var2;
      }

      return var4;
   }

   @JvmStatic
   public fun Float.coerceAtLeast(minimumValue: Float): Float {
      var var2: Float = var0;
      if (var0 < var1) {
         var2 = var1;
      }

      return var2;
   }

   @JvmStatic
   public fun Int.coerceAtLeast(minimumValue: Int): Int {
      var var2: Int = var0;
      if (var0 < var1) {
         var2 = var1;
      }

      return var2;
   }

   @JvmStatic
   public fun Long.coerceAtLeast(minimumValue: Long): Long {
      var var4: Long = var0;
      if (var0 < var2) {
         var4 = var2;
      }

      return var4;
   }

   @JvmStatic
   public fun <T : Comparable<T>> T.coerceAtLeast(minimumValue: T): T {
      var var2: java.lang.Comparable = var0;
      if (var0.compareTo(var1) < 0) {
         var2 = var1;
      }

      return (T)var2;
   }

   @JvmStatic
   public fun Short.coerceAtLeast(minimumValue: Short): Short {
      var var2: Short = var0;
      if (var0 < var1) {
         var2 = var1;
      }

      return var2;
   }

   @JvmStatic
   public fun Byte.coerceAtMost(maximumValue: Byte): Byte {
      var var2: Byte = var0;
      if (var0 > var1) {
         var2 = var1;
      }

      return var2;
   }

   @JvmStatic
   public fun Double.coerceAtMost(maximumValue: Double): Double {
      var var4: Double = var0;
      if (var0 > var2) {
         var4 = var2;
      }

      return var4;
   }

   @JvmStatic
   public fun Float.coerceAtMost(maximumValue: Float): Float {
      var var2: Float = var0;
      if (var0 > var1) {
         var2 = var1;
      }

      return var2;
   }

   @JvmStatic
   public fun Int.coerceAtMost(maximumValue: Int): Int {
      var var2: Int = var0;
      if (var0 > var1) {
         var2 = var1;
      }

      return var2;
   }

   @JvmStatic
   public fun Long.coerceAtMost(maximumValue: Long): Long {
      var var4: Long = var0;
      if (var0 > var2) {
         var4 = var2;
      }

      return var4;
   }

   @JvmStatic
   public fun <T : Comparable<T>> T.coerceAtMost(maximumValue: T): T {
      var var2: java.lang.Comparable = var0;
      if (var0.compareTo(var1) > 0) {
         var2 = var1;
      }

      return (T)var2;
   }

   @JvmStatic
   public fun Short.coerceAtMost(maximumValue: Short): Short {
      var var2: Short = var0;
      if (var0 > var1) {
         var2 = var1;
      }

      return var2;
   }

   @JvmStatic
   public fun Byte.coerceIn(minimumValue: Byte, maximumValue: Byte): Byte {
      if (var1 <= var2) {
         if (var0 < var1) {
            return var1;
         } else {
            return if (var0 > var2) var2 else var0;
         }
      } else {
         val var3: StringBuilder = new StringBuilder("Cannot coerce value to an empty range: maximum ");
         var3.append((int)var2);
         var3.append(" is less than minimum ");
         var3.append((int)var1);
         var3.append('.');
         throw new IllegalArgumentException(var3.toString());
      }
   }

   @JvmStatic
   public fun Double.coerceIn(minimumValue: Double, maximumValue: Double): Double {
      if (!(var2 > var4)) {
         if (var0 < var2) {
            return var2;
         } else {
            return if (var0 > var4) var4 else var0;
         }
      } else {
         val var6: StringBuilder = new StringBuilder("Cannot coerce value to an empty range: maximum ");
         var6.append(var4);
         var6.append(" is less than minimum ");
         var6.append(var2);
         var6.append('.');
         throw new IllegalArgumentException(var6.toString());
      }
   }

   @JvmStatic
   public fun Float.coerceIn(minimumValue: Float, maximumValue: Float): Float {
      if (!(var1 > var2)) {
         if (var0 < var1) {
            return var1;
         } else {
            return if (var0 > var2) var2 else var0;
         }
      } else {
         val var3: StringBuilder = new StringBuilder("Cannot coerce value to an empty range: maximum ");
         var3.append(var2);
         var3.append(" is less than minimum ");
         var3.append(var1);
         var3.append('.');
         throw new IllegalArgumentException(var3.toString());
      }
   }

   @JvmStatic
   public fun Int.coerceIn(minimumValue: Int, maximumValue: Int): Int {
      if (var1 <= var2) {
         if (var0 < var1) {
            return var1;
         } else {
            return if (var0 > var2) var2 else var0;
         }
      } else {
         val var3: StringBuilder = new StringBuilder("Cannot coerce value to an empty range: maximum ");
         var3.append(var2);
         var3.append(" is less than minimum ");
         var3.append(var1);
         var3.append('.');
         throw new IllegalArgumentException(var3.toString());
      }
   }

   @JvmStatic
   public fun Int.coerceIn(range: ClosedRange<Int>): Int {
      if (var1 is ClosedFloatingPointRange) {
         return RangesKt.coerceIn(var0, var1 as ClosedFloatingPointRange<Integer>).intValue();
      } else if (!var1.isEmpty()) {
         var var2: Int;
         if (var0 < (var1.getStart() as java.lang.Number).intValue()) {
            var2 = (var1.getStart() as java.lang.Number).intValue();
         } else {
            var2 = var0;
            if (var0 > (var1.getEndInclusive() as java.lang.Number).intValue()) {
               var2 = (var1.getEndInclusive() as java.lang.Number).intValue();
            }
         }

         return var2;
      } else {
         val var3: StringBuilder = new StringBuilder("Cannot coerce value to an empty range: ");
         var3.append(var1);
         var3.append('.');
         throw new IllegalArgumentException(var3.toString());
      }
   }

   @JvmStatic
   public fun Long.coerceIn(minimumValue: Long, maximumValue: Long): Long {
      if (var2 <= var4) {
         if (var0 < var2) {
            return var2;
         } else {
            return if (var0 > var4) var4 else var0;
         }
      } else {
         val var6: StringBuilder = new StringBuilder("Cannot coerce value to an empty range: maximum ");
         var6.append(var4);
         var6.append(" is less than minimum ");
         var6.append(var2);
         var6.append('.');
         throw new IllegalArgumentException(var6.toString());
      }
   }

   @JvmStatic
   public fun Long.coerceIn(range: ClosedRange<Long>): Long {
      if (var2 is ClosedFloatingPointRange) {
         return RangesKt.coerceIn(var0, var2 as ClosedFloatingPointRange<java.lang.Long>).longValue();
      } else if (!var2.isEmpty()) {
         var var3: Long;
         if (var0 < (var2.getStart() as java.lang.Number).longValue()) {
            var3 = (var2.getStart() as java.lang.Number).longValue();
         } else {
            var3 = var0;
            if (var0 > (var2.getEndInclusive() as java.lang.Number).longValue()) {
               var3 = (var2.getEndInclusive() as java.lang.Number).longValue();
            }
         }

         return var3;
      } else {
         val var5: StringBuilder = new StringBuilder("Cannot coerce value to an empty range: ");
         var5.append(var2);
         var5.append('.');
         throw new IllegalArgumentException(var5.toString());
      }
   }

   @JvmStatic
   public fun <T : Comparable<T>> T.coerceIn(minimumValue: T?, maximumValue: T?): T {
      if (var1 != null && var2 != null) {
         if (var1.compareTo(var2) > 0) {
            val var3: StringBuilder = new StringBuilder("Cannot coerce value to an empty range: maximum ");
            var3.append(var2);
            var3.append(" is less than minimum ");
            var3.append(var1);
            var3.append('.');
            throw new IllegalArgumentException(var3.toString());
         }

         if (var0.compareTo(var1) < 0) {
            return (T)var1;
         }

         if (var0.compareTo(var2) > 0) {
            return (T)var2;
         }
      } else {
         if (var1 != null && var0.compareTo(var1) < 0) {
            return (T)var1;
         }

         if (var2 != null && var0.compareTo(var2) > 0) {
            return (T)var2;
         }
      }

      return (T)var0;
   }

   @JvmStatic
   public fun <T : Comparable<T>> T.coerceIn(range: ClosedFloatingPointRange<T>): T {
      if (var1.isEmpty()) {
         val var3: StringBuilder = new StringBuilder("Cannot coerce value to an empty range: ");
         var3.append(var1);
         var3.append('.');
         throw new IllegalArgumentException(var3.toString());
      } else {
         var var2: java.lang.Comparable;
         if (var1.lessThanOrEquals(var0, var1.getStart()) && !var1.lessThanOrEquals(var1.getStart(), var0)) {
            var2 = var1.getStart();
         } else {
            var2 = var0;
            if (var1.lessThanOrEquals(var1.getEndInclusive(), var0)) {
               var2 = var0;
               if (!var1.lessThanOrEquals(var0, var1.getEndInclusive())) {
                  var2 = var1.getEndInclusive();
               }
            }
         }

         return (T)var2;
      }
   }

   @JvmStatic
   public fun <T : Comparable<T>> T.coerceIn(range: ClosedRange<T>): T {
      if (var1 is ClosedFloatingPointRange) {
         return (T)RangesKt.coerceIn(var0, var1 as ClosedFloatingPointRange);
      } else if (!var1.isEmpty()) {
         var var2: java.lang.Comparable;
         if (var0.compareTo(var1.getStart()) < 0) {
            var2 = var1.getStart();
         } else {
            var2 = var0;
            if (var0.compareTo(var1.getEndInclusive()) > 0) {
               var2 = var1.getEndInclusive();
            }
         }

         return (T)var2;
      } else {
         val var3: StringBuilder = new StringBuilder("Cannot coerce value to an empty range: ");
         var3.append(var1);
         var3.append('.');
         throw new IllegalArgumentException(var3.toString());
      }
   }

   @JvmStatic
   public fun Short.coerceIn(minimumValue: Short, maximumValue: Short): Short {
      if (var1 <= var2) {
         if (var0 < var1) {
            return var1;
         } else {
            return if (var0 > var2) var2 else var0;
         }
      } else {
         val var3: StringBuilder = new StringBuilder("Cannot coerce value to an empty range: maximum ");
         var3.append((int)var2);
         var3.append(" is less than minimum ");
         var3.append((int)var1);
         var3.append('.');
         throw new IllegalArgumentException(var3.toString());
      }
   }

   @JvmStatic
   public inline operator fun CharRange.contains(element: Char?): Boolean {
      val var2: Boolean;
      if (var1 != null && var0.contains(var1.charValue())) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   @JvmStatic
   public inline operator fun IntRange.contains(value: Byte): Boolean {
      return RangesKt.intRangeContains(var0, var1);
   }

   @JvmStatic
   public inline operator fun IntRange.contains(value: Long): Boolean {
      return RangesKt.intRangeContains(var0, var1);
   }

   @JvmStatic
   public inline operator fun IntRange.contains(element: Int?): Boolean {
      val var2: Boolean;
      if (var1 != null && var0.contains(var1.intValue())) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   @JvmStatic
   public inline operator fun IntRange.contains(value: Short): Boolean {
      return RangesKt.intRangeContains(var0, var1);
   }

   @JvmStatic
   public inline operator fun LongRange.contains(value: Byte): Boolean {
      return RangesKt.longRangeContains(var0, var1);
   }

   @JvmStatic
   public inline operator fun LongRange.contains(value: Int): Boolean {
      return RangesKt.longRangeContains(var0, var1);
   }

   @JvmStatic
   public inline operator fun LongRange.contains(element: Long?): Boolean {
      val var2: Boolean;
      if (var1 != null && var0.contains(var1.longValue())) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   @JvmStatic
   public inline operator fun LongRange.contains(value: Short): Boolean {
      return RangesKt.longRangeContains(var0, var1);
   }

   @JvmStatic
   public operator fun ClosedRange<Double>.contains(value: Float): Boolean {
      return var0.contains((double)var1);
   }

   @JvmStatic
   public operator fun OpenEndRange<Double>.contains(value: Float): Boolean {
      return var0.contains((double)var1);
   }

   @JvmStatic
   public infix fun Char.downTo(to: Char): CharProgression {
      return CharProgression.Companion.fromClosedRange(var0, var1, -1);
   }

   @JvmStatic
   public infix fun Byte.downTo(to: Byte): IntProgression {
      return IntProgression.Companion.fromClosedRange(var0, var1, -1);
   }

   @JvmStatic
   public infix fun Byte.downTo(to: Int): IntProgression {
      return IntProgression.Companion.fromClosedRange(var0, var1, -1);
   }

   @JvmStatic
   public infix fun Byte.downTo(to: Short): IntProgression {
      return IntProgression.Companion.fromClosedRange(var0, var1, -1);
   }

   @JvmStatic
   public infix fun Int.downTo(to: Byte): IntProgression {
      return IntProgression.Companion.fromClosedRange(var0, var1, -1);
   }

   @JvmStatic
   public infix fun Int.downTo(to: Int): IntProgression {
      return IntProgression.Companion.fromClosedRange(var0, var1, -1);
   }

   @JvmStatic
   public infix fun Int.downTo(to: Short): IntProgression {
      return IntProgression.Companion.fromClosedRange(var0, var1, -1);
   }

   @JvmStatic
   public infix fun Short.downTo(to: Byte): IntProgression {
      return IntProgression.Companion.fromClosedRange(var0, var1, -1);
   }

   @JvmStatic
   public infix fun Short.downTo(to: Int): IntProgression {
      return IntProgression.Companion.fromClosedRange(var0, var1, -1);
   }

   @JvmStatic
   public infix fun Short.downTo(to: Short): IntProgression {
      return IntProgression.Companion.fromClosedRange(var0, var1, -1);
   }

   @JvmStatic
   public infix fun Byte.downTo(to: Long): LongProgression {
      return LongProgression.Companion.fromClosedRange((long)var0, var1, -1L);
   }

   @JvmStatic
   public infix fun Int.downTo(to: Long): LongProgression {
      return LongProgression.Companion.fromClosedRange((long)var0, var1, -1L);
   }

   @JvmStatic
   public infix fun Long.downTo(to: Byte): LongProgression {
      return LongProgression.Companion.fromClosedRange(var0, (long)var2, -1L);
   }

   @JvmStatic
   public infix fun Long.downTo(to: Int): LongProgression {
      return LongProgression.Companion.fromClosedRange(var0, (long)var2, -1L);
   }

   @JvmStatic
   public infix fun Long.downTo(to: Long): LongProgression {
      return LongProgression.Companion.fromClosedRange(var0, var2, -1L);
   }

   @JvmStatic
   public infix fun Long.downTo(to: Short): LongProgression {
      return LongProgression.Companion.fromClosedRange(var0, (long)var2, -1L);
   }

   @JvmStatic
   public infix fun Short.downTo(to: Long): LongProgression {
      return LongProgression.Companion.fromClosedRange((long)var0, var1, -1L);
   }

   @JvmStatic
   public fun CharProgression.first(): Char {
      if (!var0.isEmpty()) {
         return var0.getFirst();
      } else {
         val var1: StringBuilder = new StringBuilder("Progression ");
         var1.append(var0);
         var1.append(" is empty.");
         throw new NoSuchElementException(var1.toString());
      }
   }

   @JvmStatic
   public fun IntProgression.first(): Int {
      if (!var0.isEmpty()) {
         return var0.getFirst();
      } else {
         val var1: StringBuilder = new StringBuilder("Progression ");
         var1.append(var0);
         var1.append(" is empty.");
         throw new NoSuchElementException(var1.toString());
      }
   }

   @JvmStatic
   public fun LongProgression.first(): Long {
      if (!var0.isEmpty()) {
         return var0.getFirst();
      } else {
         val var1: StringBuilder = new StringBuilder("Progression ");
         var1.append(var0);
         var1.append(" is empty.");
         throw new NoSuchElementException(var1.toString());
      }
   }

   @JvmStatic
   public fun CharProgression.firstOrNull(): Char? {
      val var1: Character;
      if (var0.isEmpty()) {
         var1 = null;
      } else {
         var1 = var0.getFirst();
      }

      return var1;
   }

   @JvmStatic
   public fun IntProgression.firstOrNull(): Int? {
      val var1: Int;
      if (var0.isEmpty()) {
         var1 = null;
      } else {
         var1 = var0.getFirst();
      }

      return var1;
   }

   @JvmStatic
   public fun LongProgression.firstOrNull(): Long? {
      val var1: java.lang.Long;
      if (var0.isEmpty()) {
         var1 = null;
      } else {
         var1 = var0.getFirst();
      }

      return var1;
   }

   @JvmStatic
   public operator fun ClosedRange<Float>.contains(value: Double): Boolean {
      return var0.contains((float)var1);
   }

   @JvmStatic
   public operator fun ClosedRange<Int>.contains(value: Byte): Boolean {
      return var0.contains(Integer.valueOf(var1));
   }

   @JvmStatic
   public operator fun ClosedRange<Int>.contains(value: Long): Boolean {
      val var4: Int = RangesKt.toIntExactOrNull(var1);
      val var3: Boolean;
      if (var4 != null) {
         var3 = var0.contains(var4);
      } else {
         var3 = false;
      }

      return var3;
   }

   @JvmStatic
   public operator fun ClosedRange<Int>.contains(value: Short): Boolean {
      return var0.contains(Integer.valueOf(var1));
   }

   @JvmStatic
   public operator fun OpenEndRange<Int>.contains(value: Byte): Boolean {
      return var0.contains(Integer.valueOf(var1));
   }

   @JvmStatic
   public operator fun OpenEndRange<Int>.contains(value: Long): Boolean {
      val var4: Int = RangesKt.toIntExactOrNull(var1);
      val var3: Boolean;
      if (var4 != null) {
         var3 = var0.contains(var4);
      } else {
         var3 = false;
      }

      return var3;
   }

   @JvmStatic
   public operator fun OpenEndRange<Int>.contains(value: Short): Boolean {
      return var0.contains(Integer.valueOf(var1));
   }

   @JvmStatic
   public fun CharProgression.last(): Char {
      if (!var0.isEmpty()) {
         return var0.getLast();
      } else {
         val var1: StringBuilder = new StringBuilder("Progression ");
         var1.append(var0);
         var1.append(" is empty.");
         throw new NoSuchElementException(var1.toString());
      }
   }

   @JvmStatic
   public fun IntProgression.last(): Int {
      if (!var0.isEmpty()) {
         return var0.getLast();
      } else {
         val var1: StringBuilder = new StringBuilder("Progression ");
         var1.append(var0);
         var1.append(" is empty.");
         throw new NoSuchElementException(var1.toString());
      }
   }

   @JvmStatic
   public fun LongProgression.last(): Long {
      if (!var0.isEmpty()) {
         return var0.getLast();
      } else {
         val var1: StringBuilder = new StringBuilder("Progression ");
         var1.append(var0);
         var1.append(" is empty.");
         throw new NoSuchElementException(var1.toString());
      }
   }

   @JvmStatic
   public fun CharProgression.lastOrNull(): Char? {
      val var1: Character;
      if (var0.isEmpty()) {
         var1 = null;
      } else {
         var1 = var0.getLast();
      }

      return var1;
   }

   @JvmStatic
   public fun IntProgression.lastOrNull(): Int? {
      val var1: Int;
      if (var0.isEmpty()) {
         var1 = null;
      } else {
         var1 = var0.getLast();
      }

      return var1;
   }

   @JvmStatic
   public fun LongProgression.lastOrNull(): Long? {
      val var1: java.lang.Long;
      if (var0.isEmpty()) {
         var1 = null;
      } else {
         var1 = var0.getLast();
      }

      return var1;
   }

   @JvmStatic
   public operator fun ClosedRange<Long>.contains(value: Byte): Boolean {
      return var0.contains((long)var1);
   }

   @JvmStatic
   public operator fun ClosedRange<Long>.contains(value: Int): Boolean {
      return var0.contains((long)var1);
   }

   @JvmStatic
   public operator fun ClosedRange<Long>.contains(value: Short): Boolean {
      return var0.contains((long)var1);
   }

   @JvmStatic
   public operator fun OpenEndRange<Long>.contains(value: Byte): Boolean {
      return var0.contains((long)var1);
   }

   @JvmStatic
   public operator fun OpenEndRange<Long>.contains(value: Int): Boolean {
      return var0.contains((long)var1);
   }

   @JvmStatic
   public operator fun OpenEndRange<Long>.contains(value: Short): Boolean {
      return var0.contains((long)var1);
   }

   @JvmStatic
   public inline fun CharRange.random(): Char {
      return RangesKt.random(var0, Random.Default);
   }

   @JvmStatic
   public fun CharRange.random(random: Random): Char {
      var var2: Int;
      try {
         var2 = var1.nextInt(var0.getFirst(), var0.getLast() + 1);
      } catch (var3: IllegalArgumentException) {
         throw new NoSuchElementException(var3.getMessage());
      }

      return (char)var2;
   }

   @JvmStatic
   public inline fun IntRange.random(): Int {
      return RangesKt.random(var0, Random.Default);
   }

   @JvmStatic
   public fun IntRange.random(random: Random): Int {
      try {
         return RandomKt.nextInt(var1, var0);
      } catch (var3: IllegalArgumentException) {
         throw new NoSuchElementException(var3.getMessage());
      }
   }

   @JvmStatic
   public inline fun LongRange.random(): Long {
      return RangesKt.random(var0, Random.Default);
   }

   @JvmStatic
   public fun LongRange.random(random: Random): Long {
      try {
         return RandomKt.nextLong(var1, var0);
      } catch (var4: IllegalArgumentException) {
         throw new NoSuchElementException(var4.getMessage());
      }
   }

   @JvmStatic
   public inline fun CharRange.randomOrNull(): Char? {
      return RangesKt.randomOrNull(var0, Random.Default);
   }

   @JvmStatic
   public fun CharRange.randomOrNull(random: Random): Char? {
      return if (var0.isEmpty()) null else (char)var1.nextInt(var0.getFirst(), var0.getLast() + 1);
   }

   @JvmStatic
   public inline fun IntRange.randomOrNull(): Int? {
      return RangesKt.randomOrNull(var0, Random.Default);
   }

   @JvmStatic
   public fun IntRange.randomOrNull(random: Random): Int? {
      return if (var0.isEmpty()) null else RandomKt.nextInt(var1, var0);
   }

   @JvmStatic
   public inline fun LongRange.randomOrNull(): Long? {
      return RangesKt.randomOrNull(var0, Random.Default);
   }

   @JvmStatic
   public fun LongRange.randomOrNull(random: Random): Long? {
      return if (var0.isEmpty()) null else RandomKt.nextLong(var1, var0);
   }

   @JvmStatic
   public fun CharProgression.reversed(): CharProgression {
      return CharProgression.Companion.fromClosedRange(var0.getLast(), var0.getFirst(), -var0.getStep());
   }

   @JvmStatic
   public fun IntProgression.reversed(): IntProgression {
      return IntProgression.Companion.fromClosedRange(var0.getLast(), var0.getFirst(), -var0.getStep());
   }

   @JvmStatic
   public fun LongProgression.reversed(): LongProgression {
      return LongProgression.Companion.fromClosedRange(var0.getLast(), var0.getFirst(), -var0.getStep());
   }

   @JvmStatic
   public operator fun ClosedRange<Short>.contains(value: Byte): Boolean {
      return var0.contains((short)var1);
   }

   @JvmStatic
   public operator fun ClosedRange<Short>.contains(value: Int): Boolean {
      val var3: java.lang.Short = RangesKt.toShortExactOrNull(var1);
      val var2: Boolean;
      if (var3 != null) {
         var2 = var0.contains(var3);
      } else {
         var2 = false;
      }

      return var2;
   }

   @JvmStatic
   public operator fun ClosedRange<Short>.contains(value: Long): Boolean {
      val var4: java.lang.Short = RangesKt.toShortExactOrNull(var1);
      val var3: Boolean;
      if (var4 != null) {
         var3 = var0.contains(var4);
      } else {
         var3 = false;
      }

      return var3;
   }

   @JvmStatic
   public operator fun OpenEndRange<Short>.contains(value: Byte): Boolean {
      return var0.contains((short)var1);
   }

   @JvmStatic
   public operator fun OpenEndRange<Short>.contains(value: Int): Boolean {
      val var3: java.lang.Short = RangesKt.toShortExactOrNull(var1);
      val var2: Boolean;
      if (var3 != null) {
         var2 = var0.contains(var3);
      } else {
         var2 = false;
      }

      return var2;
   }

   @JvmStatic
   public operator fun OpenEndRange<Short>.contains(value: Long): Boolean {
      val var4: java.lang.Short = RangesKt.toShortExactOrNull(var1);
      val var3: Boolean;
      if (var4 != null) {
         var3 = var0.contains(var4);
      } else {
         var3 = false;
      }

      return var3;
   }

   @JvmStatic
   public infix fun CharProgression.step(step: Int): CharProgression {
      val var4: Boolean;
      if (var1 > 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      RangesKt.checkStepIsPositive(var4, var1);
      val var5: CharProgression.Companion = CharProgression.Companion;
      val var3: Char = var0.getFirst();
      val var2: Char = var0.getLast();
      if (var0.getStep() <= 0) {
         var1 = -var1;
      }

      return var5.fromClosedRange(var3, var2, var1);
   }

   @JvmStatic
   public infix fun IntProgression.step(step: Int): IntProgression {
      val var4: Boolean;
      if (var1 > 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      RangesKt.checkStepIsPositive(var4, var1);
      val var5: IntProgression.Companion = IntProgression.Companion;
      val var3: Int = var0.getFirst();
      val var2: Int = var0.getLast();
      if (var0.getStep() <= 0) {
         var1 = -var1;
      }

      return var5.fromClosedRange(var3, var2, var1);
   }

   @JvmStatic
   public infix fun LongProgression.step(step: Long): LongProgression {
      val var7: Boolean;
      if (var1 > 0L) {
         var7 = true;
      } else {
         var7 = false;
      }

      RangesKt.checkStepIsPositive(var7, var1);
      val var8: LongProgression.Companion = LongProgression.Companion;
      val var5: Long = var0.getFirst();
      val var3: Long = var0.getLast();
      if (var0.getStep() <= 0L) {
         var1 = -var1;
      }

      return var8.fromClosedRange(var5, var3, var1);
   }

   @JvmStatic
   internal fun Double.toByteExactOrNull(): Byte? {
      val var2: java.lang.Byte;
      if (-128.0 <= var0 && var0 <= 127.0) {
         var2 = (byte)((int)var0);
      } else {
         var2 = null;
      }

      return var2;
   }

   @JvmStatic
   internal fun Float.toByteExactOrNull(): Byte? {
      val var1: java.lang.Byte;
      if (-128.0F <= var0 && var0 <= 127.0F) {
         var1 = (byte)((int)var0);
      } else {
         var1 = null;
      }

      return var1;
   }

   @JvmStatic
   internal fun Int.toByteExactOrNull(): Byte? {
      val var1: java.lang.Byte;
      if (-128 <= var0 && var0 < 128) {
         var1 = (byte)var0;
      } else {
         var1 = null;
      }

      return var1;
   }

   @JvmStatic
   internal fun Long.toByteExactOrNull(): Byte? {
      val var2: java.lang.Byte;
      if (-128L <= var0 && var0 < 128L) {
         var2 = (byte)((int)var0);
      } else {
         var2 = null;
      }

      return var2;
   }

   @JvmStatic
   internal fun Short.toByteExactOrNull(): Byte? {
      val var1: java.lang.Byte;
      if (-128 <= var0 && var0 < 128) {
         var1 = (byte)var0;
      } else {
         var1 = null;
      }

      return var1;
   }

   @JvmStatic
   internal fun Double.toIntExactOrNull(): Int? {
      val var2: Int;
      if (-2.1474836E9F <= var0 && var0 <= 2.147483647E9) {
         var2 = (int)var0;
      } else {
         var2 = null;
      }

      return var2;
   }

   @JvmStatic
   internal fun Float.toIntExactOrNull(): Int? {
      val var1: Int;
      if (-2.1474836E9F <= var0 && var0 <= 2.1474836E9F) {
         var1 = (int)var0;
      } else {
         var1 = null;
      }

      return var1;
   }

   @JvmStatic
   internal fun Long.toIntExactOrNull(): Int? {
      val var2: Int;
      if (-2147483648L <= var0 && var0 < 2147483648L) {
         var2 = (int)var0;
      } else {
         var2 = null;
      }

      return var2;
   }

   @JvmStatic
   internal fun Double.toLongExactOrNull(): Long? {
      val var2: java.lang.Long;
      if (-9.223372E18F <= var0 && var0 <= 9.223372E18F) {
         var2 = (long)var0;
      } else {
         var2 = null;
      }

      return var2;
   }

   @JvmStatic
   internal fun Float.toLongExactOrNull(): Long? {
      val var1: java.lang.Long;
      if (-9.223372E18F <= var0 && var0 <= 9.223372E18F) {
         var1 = (long)var0;
      } else {
         var1 = null;
      }

      return var1;
   }

   @JvmStatic
   internal fun Double.toShortExactOrNull(): Short? {
      val var2: java.lang.Short;
      if (-32768.0 <= var0 && var0 <= 32767.0) {
         var2 = (short)((int)var0);
      } else {
         var2 = null;
      }

      return var2;
   }

   @JvmStatic
   internal fun Float.toShortExactOrNull(): Short? {
      val var1: java.lang.Short;
      if (-32768.0F <= var0 && var0 <= 32767.0F) {
         var1 = (short)((int)var0);
      } else {
         var1 = null;
      }

      return var1;
   }

   @JvmStatic
   internal fun Int.toShortExactOrNull(): Short? {
      val var1: java.lang.Short;
      if (-32768 <= var0 && var0 < 32768) {
         var1 = (short)var0;
      } else {
         var1 = null;
      }

      return var1;
   }

   @JvmStatic
   internal fun Long.toShortExactOrNull(): Short? {
      val var2: java.lang.Short;
      if (-32768L <= var0 && var0 < 32768L) {
         var2 = (short)((int)var0);
      } else {
         var2 = null;
      }

      return var2;
   }

   @JvmStatic
   public infix fun Char.until(to: Char): CharRange {
      return if (Intrinsics.compare(var1, 0) <= 0) CharRange.Companion.getEMPTY() else new CharRange(var0, (char)(var1 - 1));
   }

   @JvmStatic
   public infix fun Byte.until(to: Byte): IntRange {
      return new IntRange(var0, var1 - 1);
   }

   @JvmStatic
   public infix fun Byte.until(to: Int): IntRange {
      return if (var1 <= Integer.MIN_VALUE) IntRange.Companion.getEMPTY() else new IntRange(var0, var1 - 1);
   }

   @JvmStatic
   public infix fun Byte.until(to: Short): IntRange {
      return new IntRange(var0, var1 - 1);
   }

   @JvmStatic
   public infix fun Int.until(to: Byte): IntRange {
      return new IntRange(var0, var1 - 1);
   }

   @JvmStatic
   public infix fun Int.until(to: Int): IntRange {
      return if (var1 <= Integer.MIN_VALUE) IntRange.Companion.getEMPTY() else new IntRange(var0, var1 - 1);
   }

   @JvmStatic
   public infix fun Int.until(to: Short): IntRange {
      return new IntRange(var0, var1 - 1);
   }

   @JvmStatic
   public infix fun Short.until(to: Byte): IntRange {
      return new IntRange(var0, var1 - 1);
   }

   @JvmStatic
   public infix fun Short.until(to: Int): IntRange {
      return if (var1 <= Integer.MIN_VALUE) IntRange.Companion.getEMPTY() else new IntRange(var0, var1 - 1);
   }

   @JvmStatic
   public infix fun Short.until(to: Short): IntRange {
      return new IntRange(var0, var1 - 1);
   }

   @JvmStatic
   public infix fun Byte.until(to: Long): LongRange {
      return if (var1 <= java.lang.Long.MIN_VALUE) LongRange.Companion.getEMPTY() else new LongRange(var0, var1 - 1L);
   }

   @JvmStatic
   public infix fun Int.until(to: Long): LongRange {
      return if (var1 <= java.lang.Long.MIN_VALUE) LongRange.Companion.getEMPTY() else new LongRange(var0, var1 - 1L);
   }

   @JvmStatic
   public infix fun Long.until(to: Byte): LongRange {
      return new LongRange(var0, var2 - 1L);
   }

   @JvmStatic
   public infix fun Long.until(to: Int): LongRange {
      return new LongRange(var0, var2 - 1L);
   }

   @JvmStatic
   public infix fun Long.until(to: Long): LongRange {
      return if (var2 <= java.lang.Long.MIN_VALUE) LongRange.Companion.getEMPTY() else new LongRange(var0, var2 - 1L);
   }

   @JvmStatic
   public infix fun Long.until(to: Short): LongRange {
      return new LongRange(var0, var2 - 1L);
   }

   @JvmStatic
   public infix fun Short.until(to: Long): LongRange {
      return if (var1 <= java.lang.Long.MIN_VALUE) LongRange.Companion.getEMPTY() else new LongRange(var0, var1 - 1L);
   }
}
