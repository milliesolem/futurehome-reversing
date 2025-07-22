package kotlin.ranges

private class ClosedDoubleRange(start: Double, endInclusive: Double) : ClosedFloatingPointRange<java.lang.Double> {
   private final val _start: Double
   private final val _endInclusive: Double

   public open val start: Double
      public open get() {
         return this._start;
      }


   public open val endInclusive: Double
      public open get() {
         return this._endInclusive;
      }


   init {
      this._start = var1;
      this._endInclusive = var3;
   }

   public open operator fun contains(value: Double): Boolean {
      val var3: Boolean;
      if (var1 >= this._start && var1 <= this._endInclusive) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public override operator fun equals(other: Any?): Boolean {
      if (var1 is ClosedDoubleRange) {
         if (this.isEmpty() && (var1 as ClosedDoubleRange).isEmpty()) {
            return true;
         }

         if (this._start == (var1 as ClosedDoubleRange)._start && this._endInclusive == (var1 as ClosedDoubleRange)._endInclusive) {
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
         var1 = UByte$$ExternalSyntheticBackport0.m(this._start) * 31 + UByte$$ExternalSyntheticBackport0.m(this._endInclusive);
      }

      return var1;
   }

   public override fun isEmpty(): Boolean {
      val var1: Boolean;
      if (!(this._start <= this._endInclusive)) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public open fun lessThanOrEquals(a: Double, b: Double): Boolean {
      val var5: Boolean;
      if (var1 <= var3) {
         var5 = true;
      } else {
         var5 = false;
      }

      return var5;
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder();
      var1.append(this._start);
      var1.append("..");
      var1.append(this._endInclusive);
      return var1.toString();
   }
}
