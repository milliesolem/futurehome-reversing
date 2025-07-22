package kotlin.ranges

private class OpenEndFloatRange(start: Float, endExclusive: Float) : OpenEndRange<java.lang.Float> {
   private final val _start: Float
   private final val _endExclusive: Float

   public open val start: Float
      public open get() {
         return this._start;
      }


   public open val endExclusive: Float
      public open get() {
         return this._endExclusive;
      }


   init {
      this._start = var1;
      this._endExclusive = var2;
   }

   private fun lessThanOrEquals(a: Float, b: Float): Boolean {
      val var3: Boolean;
      if (var1 <= var2) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public open operator fun contains(value: Float): Boolean {
      val var2: Boolean;
      if (var1 >= this._start && var1 < this._endExclusive) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public override operator fun equals(other: Any?): Boolean {
      if (var1 is OpenEndFloatRange) {
         if (this.isEmpty() && (var1 as OpenEndFloatRange).isEmpty()) {
            return true;
         }

         if (this._start == (var1 as OpenEndFloatRange)._start && this._endExclusive == (var1 as OpenEndFloatRange)._endExclusive) {
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
         var1 = java.lang.Float.floatToIntBits(this._start) * 31 + java.lang.Float.floatToIntBits(this._endExclusive);
      }

      return var1;
   }

   public override fun isEmpty(): Boolean {
      val var1: Boolean;
      if (!(this._start < this._endExclusive)) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder();
      var1.append(this._start);
      var1.append("..<");
      var1.append(this._endExclusive);
      return var1.toString();
   }
}
