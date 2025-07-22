package kotlin.ranges

private open class ComparableRange<T extends java.lang.Comparable<? super T>>(start: Any, endInclusive: Any) : ClosedRange<T> {
   public open val start: Any
   public open val endInclusive: Any

   init {
      this.start = (T)var1;
      this.endInclusive = (T)var2;
   }

   override fun contains(var1: T): Boolean {
      return ClosedRange.DefaultImpls.contains(this, (T)var1);
   }

   public override operator fun equals(other: Any?): Boolean {
      if (var1 is ComparableRange) {
         if (this.isEmpty() && (var1 as ComparableRange).isEmpty()) {
            return true;
         }

         if (this.getStart() == (var1 as ComparableRange).getStart() && this.getEndInclusive() == (var1 as ComparableRange).getEndInclusive()) {
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
         var1 = this.getStart().hashCode() * 31 + this.getEndInclusive().hashCode();
      }

      return var1;
   }

   override fun isEmpty(): Boolean {
      return ClosedRange.DefaultImpls.isEmpty(this);
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder();
      var1.append(this.getStart());
      var1.append("..");
      var1.append(this.getEndInclusive());
      return var1.toString();
   }
}
