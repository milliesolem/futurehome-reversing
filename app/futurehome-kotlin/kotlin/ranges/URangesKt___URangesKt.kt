package kotlin.ranges

import java.util.NoSuchElementException
import kotlin.jvm.internal.Intrinsics
import kotlin.random.Random
import kotlin.random.URandomKt

internal class URangesKt___URangesKt {
   open fun URangesKt___URangesKt() {
   }

   @JvmStatic
   public fun UShort.coerceAtLeast(minimumValue: UShort): UShort {
      var var2: Short = var0;
      if (Intrinsics.compare(var0 and '\uffff', '\uffff' and var1) < 0) {
         var2 = var1;
      }

      return var2;
   }

   @JvmStatic
   public fun UInt.coerceAtLeast(minimumValue: UInt): UInt {
      var var2: Int = var0;
      if (UByte$$ExternalSyntheticBackport0.m$2(var0, var1) < 0) {
         var2 = var1;
      }

      return var2;
   }

   @JvmStatic
   public fun UByte.coerceAtLeast(minimumValue: UByte): UByte {
      var var2: Byte = var0;
      if (Intrinsics.compare(var0 and 255, var1 and 255) < 0) {
         var2 = var1;
      }

      return var2;
   }

   @JvmStatic
   public fun ULong.coerceAtLeast(minimumValue: ULong): ULong {
      var var4: Long = var0;
      if (UByte$$ExternalSyntheticBackport0.m(var0, var2) < 0) {
         var4 = var2;
      }

      return var4;
   }

   @JvmStatic
   public fun UShort.coerceAtMost(maximumValue: UShort): UShort {
      var var2: Short = var0;
      if (Intrinsics.compare(var0 and '\uffff', '\uffff' and var1) > 0) {
         var2 = var1;
      }

      return var2;
   }

   @JvmStatic
   public fun UInt.coerceAtMost(maximumValue: UInt): UInt {
      var var2: Int = var0;
      if (UByte$$ExternalSyntheticBackport0.m$2(var0, var1) > 0) {
         var2 = var1;
      }

      return var2;
   }

   @JvmStatic
   public fun UByte.coerceAtMost(maximumValue: UByte): UByte {
      var var2: Byte = var0;
      if (Intrinsics.compare(var0 and 255, var1 and 255) > 0) {
         var2 = var1;
      }

      return var2;
   }

   @JvmStatic
   public fun ULong.coerceAtMost(maximumValue: ULong): ULong {
      var var4: Long = var0;
      if (UByte$$ExternalSyntheticBackport0.m(var0, var2) > 0) {
         var4 = var2;
      }

      return var4;
   }

   @JvmStatic
   public fun ULong.coerceIn(range: ClosedRange<ULong>): ULong {
      if (var2 is ClosedFloatingPointRange) {
         return RangesKt.coerceIn(ULong.box-impl(var0), var2 as ClosedFloatingPointRange<ULong>).unbox-impl();
      } else if (!var2.isEmpty()) {
         var var3: Long;
         if (UByte$$ExternalSyntheticBackport0.m(var0, (var2.getStart() as ULong).unbox-impl()) < 0) {
            var3 = (var2.getStart() as ULong).unbox-impl();
         } else {
            var3 = var0;
            if (UByte$$ExternalSyntheticBackport0.m(var0, (var2.getEndInclusive() as ULong).unbox-impl()) > 0) {
               var3 = (var2.getEndInclusive() as ULong).unbox-impl();
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
   public fun UShort.coerceIn(minimumValue: UShort, maximumValue: UShort): UShort {
      val var5: Int = var1 and '\uffff';
      val var3: Int = var2 and '\uffff';
      if (Intrinsics.compare(var5, var2 and '\uffff') <= 0) {
         val var4: Int = '\uffff' and var0;
         if (Intrinsics.compare('\uffff' and var0, var5) < 0) {
            return var1;
         } else {
            return if (Intrinsics.compare(var4, var3) > 0) var2 else var0;
         }
      } else {
         val var6: StringBuilder = new StringBuilder("Cannot coerce value to an empty range: maximum ");
         var6.append(UShort.toString-impl(var2));
         var6.append(" is less than minimum ");
         var6.append(UShort.toString-impl(var1));
         var6.append('.');
         throw new IllegalArgumentException(var6.toString());
      }
   }

   @JvmStatic
   public fun UInt.coerceIn(minimumValue: UInt, maximumValue: UInt): UInt {
      if (UByte$$ExternalSyntheticBackport0.m$2(var1, var2) <= 0) {
         if (UByte$$ExternalSyntheticBackport0.m$2(var0, var1) < 0) {
            return var1;
         } else {
            return if (UByte$$ExternalSyntheticBackport0.m$2(var0, var2) > 0) var2 else var0;
         }
      } else {
         val var3: StringBuilder = new StringBuilder("Cannot coerce value to an empty range: maximum ");
         var3.append(UInt.toString-impl(var2));
         var3.append(" is less than minimum ");
         var3.append(UInt.toString-impl(var1));
         var3.append('.');
         throw new IllegalArgumentException(var3.toString());
      }
   }

   @JvmStatic
   public fun UByte.coerceIn(minimumValue: UByte, maximumValue: UByte): UByte {
      val var5: Int = var1 and 255;
      val var4: Int = var2 and 255;
      if (Intrinsics.compare(var5, var2 and 255) <= 0) {
         val var3: Int = var0 and 255;
         if (Intrinsics.compare(var0 and 255, var5) < 0) {
            return var1;
         } else {
            return if (Intrinsics.compare(var3, var4) > 0) var2 else var0;
         }
      } else {
         val var6: StringBuilder = new StringBuilder("Cannot coerce value to an empty range: maximum ");
         var6.append(UByte.toString-impl(var2));
         var6.append(" is less than minimum ");
         var6.append(UByte.toString-impl(var1));
         var6.append('.');
         throw new IllegalArgumentException(var6.toString());
      }
   }

   @JvmStatic
   public fun ULong.coerceIn(minimumValue: ULong, maximumValue: ULong): ULong {
      if (UByte$$ExternalSyntheticBackport0.m(var2, var4) <= 0) {
         if (UByte$$ExternalSyntheticBackport0.m(var0, var2) < 0) {
            return var2;
         } else {
            return if (UByte$$ExternalSyntheticBackport0.m(var0, var4) > 0) var4 else var0;
         }
      } else {
         val var6: StringBuilder = new StringBuilder("Cannot coerce value to an empty range: maximum ");
         var6.append(ULong.toString-impl(var4));
         var6.append(" is less than minimum ");
         var6.append(ULong.toString-impl(var2));
         var6.append('.');
         throw new IllegalArgumentException(var6.toString());
      }
   }

   @JvmStatic
   public fun UInt.coerceIn(range: ClosedRange<UInt>): UInt {
      if (var1 is ClosedFloatingPointRange) {
         return RangesKt.coerceIn(UInt.box-impl(var0), var1 as ClosedFloatingPointRange<UInt>).unbox-impl();
      } else if (!var1.isEmpty()) {
         var var2: Int;
         if (UByte$$ExternalSyntheticBackport0.m$2(var0, (var1.getStart() as UInt).unbox-impl()) < 0) {
            var2 = (var1.getStart() as UInt).unbox-impl();
         } else {
            var2 = var0;
            if (UByte$$ExternalSyntheticBackport0.m$2(var0, (var1.getEndInclusive() as UInt).unbox-impl()) > 0) {
               var2 = (var1.getEndInclusive() as UInt).unbox-impl();
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
   public operator fun UIntRange.contains(value: UByte): Boolean {
      return var0.contains-WZ4Q5Ns(UInt.constructor-impl(var1 and 255));
   }

   @JvmStatic
   public inline operator fun ULongRange.contains(element: ULong?): Boolean {
      val var2: Boolean;
      if (var1 != null && var0.contains-VKZWuLQ(var1.unbox-impl())) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   @JvmStatic
   public operator fun ULongRange.contains(value: UInt): Boolean {
      return var0.contains-VKZWuLQ(ULong.constructor-impl((long)var1 and 4294967295L));
   }

   @JvmStatic
   public operator fun ULongRange.contains(value: UByte): Boolean {
      return var0.contains-VKZWuLQ(ULong.constructor-impl((long)var1 and 255L));
   }

   @JvmStatic
   public operator fun UIntRange.contains(value: UShort): Boolean {
      return var0.contains-WZ4Q5Ns(UInt.constructor-impl(var1 and '\uffff'));
   }

   @JvmStatic
   public inline operator fun UIntRange.contains(element: UInt?): Boolean {
      val var2: Boolean;
      if (var1 != null && var0.contains-WZ4Q5Ns(var1.unbox-impl())) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   @JvmStatic
   public operator fun UIntRange.contains(value: ULong): Boolean {
      val var3: Boolean;
      if (ULong.constructor-impl(var1 ushr 32) == 0L && var0.contains-WZ4Q5Ns(UInt.constructor-impl((int)var1))) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   @JvmStatic
   public operator fun ULongRange.contains(value: UShort): Boolean {
      return var0.contains-VKZWuLQ(ULong.constructor-impl((long)var1 and 65535L));
   }

   @JvmStatic
   public infix fun UShort.downTo(to: UShort): UIntProgression {
      return UIntProgression.Companion.fromClosedRange-Nkh28Cs(UInt.constructor-impl(var0 and '\uffff'), UInt.constructor-impl(var1 and '\uffff'), -1);
   }

   @JvmStatic
   public infix fun UInt.downTo(to: UInt): UIntProgression {
      return UIntProgression.Companion.fromClosedRange-Nkh28Cs(var0, var1, -1);
   }

   @JvmStatic
   public infix fun UByte.downTo(to: UByte): UIntProgression {
      return UIntProgression.Companion.fromClosedRange-Nkh28Cs(UInt.constructor-impl(var0 and 255), UInt.constructor-impl(var1 and 255), -1);
   }

   @JvmStatic
   public infix fun ULong.downTo(to: ULong): ULongProgression {
      return ULongProgression.Companion.fromClosedRange-7ftBX0g(var0, var2, -1L);
   }

   @JvmStatic
   public fun UIntProgression.first(): UInt {
      if (!var0.isEmpty()) {
         return var0.getFirst-pVg5ArA();
      } else {
         val var1: StringBuilder = new StringBuilder("Progression ");
         var1.append(var0);
         var1.append(" is empty.");
         throw new NoSuchElementException(var1.toString());
      }
   }

   @JvmStatic
   public fun ULongProgression.first(): ULong {
      if (!var0.isEmpty()) {
         return var0.getFirst-s-VKNKU();
      } else {
         val var1: StringBuilder = new StringBuilder("Progression ");
         var1.append(var0);
         var1.append(" is empty.");
         throw new NoSuchElementException(var1.toString());
      }
   }

   @JvmStatic
   public fun UIntProgression.firstOrNull(): UInt? {
      val var1: UInt;
      if (var0.isEmpty()) {
         var1 = null;
      } else {
         var1 = UInt.box-impl(var0.getFirst-pVg5ArA());
      }

      return var1;
   }

   @JvmStatic
   public fun ULongProgression.firstOrNull(): ULong? {
      val var1: ULong;
      if (var0.isEmpty()) {
         var1 = null;
      } else {
         var1 = ULong.box-impl(var0.getFirst-s-VKNKU());
      }

      return var1;
   }

   @JvmStatic
   public fun UIntProgression.last(): UInt {
      if (!var0.isEmpty()) {
         return var0.getLast-pVg5ArA();
      } else {
         val var1: StringBuilder = new StringBuilder("Progression ");
         var1.append(var0);
         var1.append(" is empty.");
         throw new NoSuchElementException(var1.toString());
      }
   }

   @JvmStatic
   public fun ULongProgression.last(): ULong {
      if (!var0.isEmpty()) {
         return var0.getLast-s-VKNKU();
      } else {
         val var1: StringBuilder = new StringBuilder("Progression ");
         var1.append(var0);
         var1.append(" is empty.");
         throw new NoSuchElementException(var1.toString());
      }
   }

   @JvmStatic
   public fun UIntProgression.lastOrNull(): UInt? {
      val var1: UInt;
      if (var0.isEmpty()) {
         var1 = null;
      } else {
         var1 = UInt.box-impl(var0.getLast-pVg5ArA());
      }

      return var1;
   }

   @JvmStatic
   public fun ULongProgression.lastOrNull(): ULong? {
      val var1: ULong;
      if (var0.isEmpty()) {
         var1 = null;
      } else {
         var1 = ULong.box-impl(var0.getLast-s-VKNKU());
      }

      return var1;
   }

   @JvmStatic
   public inline fun UIntRange.random(): UInt {
      return URangesKt.random(var0, Random.Default);
   }

   @JvmStatic
   public fun UIntRange.random(random: Random): UInt {
      try {
         return URandomKt.nextUInt(var1, var0);
      } catch (var3: IllegalArgumentException) {
         throw new NoSuchElementException(var3.getMessage());
      }
   }

   @JvmStatic
   public inline fun ULongRange.random(): ULong {
      return URangesKt.random(var0, Random.Default);
   }

   @JvmStatic
   public fun ULongRange.random(random: Random): ULong {
      try {
         return URandomKt.nextULong(var1, var0);
      } catch (var4: IllegalArgumentException) {
         throw new NoSuchElementException(var4.getMessage());
      }
   }

   @JvmStatic
   public inline fun UIntRange.randomOrNull(): UInt? {
      return URangesKt.randomOrNull(var0, Random.Default);
   }

   @JvmStatic
   public fun UIntRange.randomOrNull(random: Random): UInt? {
      return if (var0.isEmpty()) null else UInt.box-impl(URandomKt.nextUInt(var1, var0));
   }

   @JvmStatic
   public inline fun ULongRange.randomOrNull(): ULong? {
      return URangesKt.randomOrNull(var0, Random.Default);
   }

   @JvmStatic
   public fun ULongRange.randomOrNull(random: Random): ULong? {
      return if (var0.isEmpty()) null else ULong.box-impl(URandomKt.nextULong(var1, var0));
   }

   @JvmStatic
   public fun UIntProgression.reversed(): UIntProgression {
      return UIntProgression.Companion.fromClosedRange-Nkh28Cs(var0.getLast-pVg5ArA(), var0.getFirst-pVg5ArA(), -var0.getStep());
   }

   @JvmStatic
   public fun ULongProgression.reversed(): ULongProgression {
      return ULongProgression.Companion.fromClosedRange-7ftBX0g(var0.getLast-s-VKNKU(), var0.getFirst-s-VKNKU(), -var0.getStep());
   }

   @JvmStatic
   public infix fun UIntProgression.step(step: Int): UIntProgression {
      val var4: Boolean;
      if (var1 > 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      RangesKt.checkStepIsPositive(var4, var1);
      val var5: UIntProgression.Companion = UIntProgression.Companion;
      val var3: Int = var0.getFirst-pVg5ArA();
      val var2: Int = var0.getLast-pVg5ArA();
      if (var0.getStep() <= 0) {
         var1 = -var1;
      }

      return var5.fromClosedRange-Nkh28Cs(var3, var2, var1);
   }

   @JvmStatic
   public infix fun ULongProgression.step(step: Long): ULongProgression {
      val var7: Boolean;
      if (var1 > 0L) {
         var7 = true;
      } else {
         var7 = false;
      }

      RangesKt.checkStepIsPositive(var7, var1);
      val var8: ULongProgression.Companion = ULongProgression.Companion;
      val var5: Long = var0.getFirst-s-VKNKU();
      val var3: Long = var0.getLast-s-VKNKU();
      if (var0.getStep() <= 0L) {
         var1 = -var1;
      }

      return var8.fromClosedRange-7ftBX0g(var5, var3, var1);
   }

   @JvmStatic
   public infix fun UShort.until(to: UShort): UIntRange {
      return if (Intrinsics.compare(var1 and '\uffff', 0) <= 0)
         UIntRange.Companion.getEMPTY()
         else
         new UIntRange(UInt.constructor-impl(var0 and '\uffff'), UInt.constructor-impl(UInt.constructor-impl(var1 and '\uffff') - 1), null);
   }

   @JvmStatic
   public infix fun UInt.until(to: UInt): UIntRange {
      return if (UByte$$ExternalSyntheticBackport0.m$2(var1, 0) <= 0)
         UIntRange.Companion.getEMPTY()
         else
         new UIntRange(var0, UInt.constructor-impl(var1 - 1), null);
   }

   @JvmStatic
   public infix fun UByte.until(to: UByte): UIntRange {
      return if (Intrinsics.compare(var1 and 255, 0) <= 0)
         UIntRange.Companion.getEMPTY()
         else
         new UIntRange(UInt.constructor-impl(var0 and 255), UInt.constructor-impl(UInt.constructor-impl(var1 and 255) - 1), null);
   }

   @JvmStatic
   public infix fun ULong.until(to: ULong): ULongRange {
      return if (UByte$$ExternalSyntheticBackport0.m(var2, 0L) <= 0)
         ULongRange.Companion.getEMPTY()
         else
         new ULongRange(var0, ULong.constructor-impl(var2 - ULong.constructor-impl((long)1 and 4294967295L)), null);
   }
}
