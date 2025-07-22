package kotlin.ranges

import kotlin.jvm.internal.markers.KMappedMarker

public open class LongProgression internal constructor(start: Long, endInclusive: Long, step: Long) : java.lang.Iterable<java.lang.Long>, KMappedMarker {
   public final val first: Long
   public final val last: Long
   public final val step: Long

   public override operator fun equals(other: Any?): Boolean {
      if (var1 is LongProgression) {
         if (this.isEmpty() && (var1 as LongProgression).isEmpty()) {
            return true;
         }

         if (this.first == (var1 as LongProgression).first && this.last == (var1 as LongProgression).last && this.step == (var1 as LongProgression).step) {
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
         var1 = (int)(31 * ((this.first xor this.first ushr 32) * 31 + (this.last xor this.last ushr 32)) + (this.step xor this.step ushr 32));
      }

      return var1;
   }

   public open fun isEmpty(): Boolean {
      var var1: Boolean = true;
      if (if (this.step > 0L) this.first <= this.last else this.first >= this.last) {
         var1 = false;
      }

      return var1;
   }

   public open operator fun iterator(): LongIterator {
      return new LongProgressionIterator(this.first, this.last, this.step);
   }

   public override fun toString(): String {
      var var1: Long = this.step;
      val var3: StringBuilder = new StringBuilder;
      if (var1 > 0L) {
         var3./* $VF: Unable to resugar constructor */<init>();
         var3.append(this.first);
         var3.append("..");
         var3.append(this.last);
         var3.append(" step ");
         var1 = this.step;
      } else {
         var3./* $VF: Unable to resugar constructor */<init>();
         var3.append(this.first);
         var3.append(" downTo ");
         var3.append(this.last);
         var3.append(" step ");
         var1 = -this.step;
      }

      var3.append(var1);
      return var3.toString();
   }

   public companion object {
      public fun fromClosedRange(rangeStart: Long, rangeEnd: Long, step: Long): LongProgression {
         return new LongProgression(var1, var3, var5);
      }
   }
}
