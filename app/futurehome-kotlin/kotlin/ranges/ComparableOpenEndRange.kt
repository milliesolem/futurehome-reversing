package kotlin.ranges

private open class ComparableOpenEndRange<T extends java.lang.Comparable<? super T>>(start: Any, endExclusive: Any) : OpenEndRange<T> {
   public open val start: Any
   public open val endExclusive: Any

   init {
      this.start = (T)var1;
      this.endExclusive = (T)var2;
   }

   override fun contains(var1: T): Boolean {
      return OpenEndRange.DefaultImpls.contains(this, (T)var1);
   }

   public override operator fun equals(other: Any?): Boolean {
      if (var1 is ComparableOpenEndRange) {
         if (this.isEmpty() && (var1 as ComparableOpenEndRange).isEmpty()) {
            return true;
         }

         if (this.getStart() == (var1 as ComparableOpenEndRange).getStart() && this.getEndExclusive() == (var1 as ComparableOpenEndRange).getEndExclusive()) {
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
         var1 = this.getStart().hashCode() * 31 + this.getEndExclusive().hashCode();
      }

      return var1;
   }

   override fun isEmpty(): Boolean {
      return OpenEndRange.DefaultImpls.isEmpty(this);
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder();
      var1.append(this.getStart());
      var1.append("..<");
      var1.append(this.getEndExclusive());
      return var1.toString();
   }
}
