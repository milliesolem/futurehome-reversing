package kotlin.ranges

import kotlin.jvm.internal.markers.KMappedMarker

public open class IntProgression internal constructor(start: Int, endInclusive: Int, step: Int) : java.lang.Iterable<Integer>, KMappedMarker {
   public final val first: Int
   public final val last: Int
   public final val step: Int

   public override operator fun equals(other: Any?): Boolean {
      if (var1 is IntProgression) {
         if (this.isEmpty() && (var1 as IntProgression).isEmpty()) {
            return true;
         }

         if (this.first == (var1 as IntProgression).first && this.last == (var1 as IntProgression).last && this.step == (var1 as IntProgression).step) {
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
      if (if (this.step > 0) this.first <= this.last else this.first >= this.last) {
         var2 = false;
      }

      return var2;
   }

   public open operator fun iterator(): IntIterator {
      return new IntProgressionIterator(this.first, this.last, this.step);
   }

   public override fun toString(): String {
      val var1: Int;
      val var2: StringBuilder;
      if (this.step > 0) {
         var2 = new StringBuilder();
         var2.append(this.first);
         var2.append("..");
         var2.append(this.last);
         var2.append(" step ");
         var1 = this.step;
      } else {
         var2 = new StringBuilder();
         var2.append(this.first);
         var2.append(" downTo ");
         var2.append(this.last);
         var2.append(" step ");
         var1 = -this.step;
      }

      var2.append(var1);
      return var2.toString();
   }

   public companion object {
      public fun fromClosedRange(rangeStart: Int, rangeEnd: Int, step: Int): IntProgression {
         return new IntProgression(var1, var2, var3);
      }
   }
}
