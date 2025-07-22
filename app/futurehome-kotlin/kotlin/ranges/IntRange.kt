package kotlin.ranges

public class IntRange(start: Int, endInclusive: Int) : IntProgression(var1, var2, 1), ClosedRange<Integer>, OpenEndRange<Integer> {
   public open val start: Int
      public open get() {
         return this.getFirst();
      }


   public open val endInclusive: Int
      public open get() {
         return this.getLast();
      }


   @Deprecated(
      message = "Can throw an exception when it's impossible to represent the value with Int type, for example, when the range includes MAX_VALUE. It's recommended to use 'endInclusive' property that doesn't throw."
   )
   public open val endExclusive: Int
      public open get() {
         if (this.getLast() != Integer.MAX_VALUE) {
            return this.getLast() + 1;
         } else {
            throw new IllegalStateException("Cannot return the exclusive upper bound of a range that includes MAX_VALUE.".toString());
         }
      }


   public open operator fun contains(value: Int): Boolean {
      val var2: Boolean;
      if (this.getFirst() <= var1 && var1 <= this.getLast()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public override operator fun equals(other: Any?): Boolean {
      if (var1 is IntRange) {
         if (this.isEmpty() && (var1 as IntRange).isEmpty()) {
            return true;
         }

         if (this.getFirst() == (var1 as IntRange).getFirst() && this.getLast() == (var1 as IntRange).getLast()) {
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
         var1 = this.getFirst() * 31 + this.getLast();
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
      public final val EMPTY: IntRange
   }
}
