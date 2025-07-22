package kotlin.ranges

public class LongRange(start: Long, endInclusive: Long) : LongProgression(var1, var3, 1L), ClosedRange<java.lang.Long>, OpenEndRange<java.lang.Long> {
   public open val start: Long
      public open get() {
         return this.getFirst();
      }


   public open val endInclusive: Long
      public open get() {
         return this.getLast();
      }


   @Deprecated(
      message = "Can throw an exception when it's impossible to represent the value with Long type, for example, when the range includes MAX_VALUE. It's recommended to use 'endInclusive' property that doesn't throw."
   )
   public open val endExclusive: Long
      public open get() {
         if (this.getLast() != java.lang.Long.MAX_VALUE) {
            return this.getLast() + 1L;
         } else {
            throw new IllegalStateException("Cannot return the exclusive upper bound of a range that includes MAX_VALUE.".toString());
         }
      }


   public open operator fun contains(value: Long): Boolean {
      val var3: Boolean;
      if (this.getFirst() <= var1 && var1 <= this.getLast()) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public override operator fun equals(other: Any?): Boolean {
      if (var1 is LongRange) {
         if (this.isEmpty() && (var1 as LongRange).isEmpty()) {
            return true;
         }

         if (this.getFirst() == (var1 as LongRange).getFirst() && this.getLast() == (var1 as LongRange).getLast()) {
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
         var1 = (int)(31 * (this.getFirst() xor this.getFirst() ushr 32) + (this.getLast() xor this.getLast() ushr 32));
      }

      return var1;
   }

   public override fun isEmpty(): Boolean {
      val var1: Boolean;
      if (this.getFirst() > this.getLast()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder();
      var1.append(this.getFirst());
      var1.append("..");
      var1.append(this.getLast());
      return var1.toString();
   }

   public companion object {
      public final val EMPTY: LongRange
   }
}
