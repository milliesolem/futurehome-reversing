package kotlin.ranges

import kotlin.internal.UProgressionUtilKt
import kotlin.jvm.internal.markers.KMappedMarker

public open class ULongProgression internal constructor(start: ULong, endInclusive: ULong, step: Long) : ULongProgression(var1, var3, var5),
   java.lang.Iterable<ULong>,
   KMappedMarker {
   public final val first: ULong
   public final val last: ULong
   public final val step: Long

   fun ULongProgression(var1: Long, var3: Long, var5: Long) {
      if (var5 != 0L) {
         if (var5 != java.lang.Long.MIN_VALUE) {
            this.first = var1;
            this.last = UProgressionUtilKt.getProgressionLastElement-7ftBX0g(var1, var3, var5);
            this.step = var5;
         } else {
            throw new IllegalArgumentException("Step must be greater than Long.MIN_VALUE to avoid overflow on negation.");
         }
      } else {
         throw new IllegalArgumentException("Step must be non-zero.");
      }
   }

   public override operator fun equals(other: Any?): Boolean {
      if (var1 is ULongProgression) {
         if (this.isEmpty() && (var1 as ULongProgression).isEmpty()) {
            return true;
         }

         if (this.first == (var1 as ULongProgression).first && this.last == (var1 as ULongProgression).last && this.step == (var1 as ULongProgression).step) {
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
         var1 = (int)(this.step xor this.step ushr 32)
            + (
                  (int)ULong.constructor-impl(this.first xor ULong.constructor-impl(this.first ushr 32)) * 31
                     + (int)ULong.constructor-impl(this.last xor ULong.constructor-impl(this.last ushr 32))
               )
               * 31;
      }

      return var1;
   }

   public open fun isEmpty(): Boolean {
      var var7: Boolean = true;
      if (if (this.step > 0L)
         UByte$$ExternalSyntheticBackport0.m(this.first, this.last) <= 0
         else
         UByte$$ExternalSyntheticBackport0.m(this.first, this.last) >= 0) {
         var7 = false;
      }

      return var7;
   }

   public override operator fun iterator(): Iterator<ULong> {
      return new ULongProgressionIterator(this.first, this.last, this.step, null);
   }

   public override fun toString(): String {
      var var1: Long = this.step;
      val var3: StringBuilder = new StringBuilder;
      if (var1 > 0L) {
         var3./* $VF: Unable to resugar constructor */<init>();
         var3.append(ULong.toString-impl(this.first));
         var3.append("..");
         var3.append(ULong.toString-impl(this.last));
         var3.append(" step ");
         var1 = this.step;
      } else {
         var3./* $VF: Unable to resugar constructor */<init>();
         var3.append(ULong.toString-impl(this.first));
         var3.append(" downTo ");
         var3.append(ULong.toString-impl(this.last));
         var3.append(" step ");
         var1 = -this.step;
      }

      var3.append(var1);
      return var3.toString();
   }

   public companion object {
      public fun fromClosedRange(rangeStart: ULong, rangeEnd: ULong, step: Long): ULongProgression {
         return new ULongProgression(var1, var3, var5, null);
      }
   }
}
