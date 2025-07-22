package kotlin.ranges

import kotlin.internal.UProgressionUtilKt
import kotlin.jvm.internal.markers.KMappedMarker

public open class UIntProgression internal constructor(start: UInt, endInclusive: UInt, step: Int) : UIntProgression(var1, var2, var3),
   java.lang.Iterable<UInt>,
   KMappedMarker {
   public final val first: UInt
   public final val last: UInt
   public final val step: Int

   fun UIntProgression(var1: Int, var2: Int, var3: Int) {
      if (var3 != 0) {
         if (var3 != Integer.MIN_VALUE) {
            this.first = var1;
            this.last = UProgressionUtilKt.getProgressionLastElement-Nkh28Cs(var1, var2, var3);
            this.step = var3;
         } else {
            throw new IllegalArgumentException("Step must be greater than Int.MIN_VALUE to avoid overflow on negation.");
         }
      } else {
         throw new IllegalArgumentException("Step must be non-zero.");
      }
   }

   public override operator fun equals(other: Any?): Boolean {
      if (var1 is UIntProgression) {
         if (this.isEmpty() && (var1 as UIntProgression).isEmpty()) {
            return true;
         }

         if (this.first == (var1 as UIntProgression).first && this.last == (var1 as UIntProgression).last && this.step == (var1 as UIntProgression).step) {
            return true;
         }
      }

      return false;
   }

   public override fun hashCode(): Int {
      val var1: Int;
      if (this.isEmpty()) {
         var1 = -1;
      } else {
         var1 = (this.first * 31 + this.last) * 31 + this.step;
      }

      return var1;
   }

   public open fun isEmpty(): Boolean {
      var var2: Boolean = true;
      if (if (this.step > 0)
         UByte$$ExternalSyntheticBackport0.m$2(this.first, this.last) <= 0
         else
         UByte$$ExternalSyntheticBackport0.m$2(this.first, this.last) >= 0) {
         var2 = false;
      }

      return var2;
   }

   public override operator fun iterator(): Iterator<UInt> {
      return new UIntProgressionIterator(this.first, this.last, this.step, null);
   }

   public override fun toString(): String {
      val var1: Int;
      val var2: StringBuilder;
      if (this.step > 0) {
         var2 = new StringBuilder();
         var2.append(UInt.toString-impl(this.first));
         var2.append("..");
         var2.append(UInt.toString-impl(this.last));
         var2.append(" step ");
         var1 = this.step;
      } else {
         var2 = new StringBuilder();
         var2.append(UInt.toString-impl(this.first));
         var2.append(" downTo ");
         var2.append(UInt.toString-impl(this.last));
         var2.append(" step ");
         var1 = -this.step;
      }

      var2.append(var1);
      return var2.toString();
   }

   public companion object {
      public fun fromClosedRange(rangeStart: UInt, rangeEnd: UInt, step: Int): UIntProgression {
         return new UIntProgression(var1, var2, var3, null);
      }
   }
}
