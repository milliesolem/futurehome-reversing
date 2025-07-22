package kotlin.ranges

private class ClosedFloatRange(start: Float, endInclusive: Float) : ClosedFloatingPointRange<java.lang.Float> {
   private final val _start: Float
   private final val _endInclusive: Float

   public open val start: Float
      public open get() {
         return this._start;
      }


   public open val endInclusive: Float
      public open get() {
         return this._endInclusive;
      }


   init {
      this._start = var1;
      this._endInclusive = var2;
   }

   public open operator fun contains(value: Float): Boolean {
      val var2: Boolean;
      if (var1 >= this._start && var1 <= this._endInclusive) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public override operator fun equals(other: Any?): Boolean {
      if (var1 is ClosedFloatRange) {
         if (this.isEmpty() && (var1 as ClosedFloatRange).isEmpty()) {
            return true;
         }

         if (this._start == (var1 as ClosedFloatRange)._start && this._endInclusive == (var1 as ClosedFloatRange)._endInclusive) {
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
         var1 = java.lang.Float.floatToIntBits(this._start) * 31 + java.lang.Float.floatToIntBits(this._endInclusive);
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

   public open fun lessThanOrEquals(a: Float, b: Float): Boolean {
      val var3: Boolean;
      if (var1 <= var2) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder();
      var1.append(this._start);
      var1.append("..");
      var1.append(this._endInclusive);
      return var1.toString();
   }
}
