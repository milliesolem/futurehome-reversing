package kotlin.text

public data class MatchGroup(value: String, range: IntRange) {
   public final val value: String
   public final val range: IntRange

   init {
      this.value = var1;
      this.range = var2;
   }

   public operator fun component1(): String {
      return this.value;
   }

   public operator fun component2(): IntRange {
      return this.range;
   }

   public fun copy(value: String = var0.value, range: IntRange = var0.range): MatchGroup {
      return new MatchGroup(var1, var2);
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === var1) {
         return true;
      } else if (var1 !is MatchGroup) {
         return false;
      } else {
         var1 = var1;
         if (!(this.value == var1.value)) {
            return false;
         } else {
            return this.range == var1.range;
         }
      }
   }

   public override fun hashCode(): Int {
      return this.value.hashCode() * 31 + this.range.hashCode();
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder("MatchGroup(value=");
      var1.append(this.value);
      var1.append(", range=");
      var1.append(this.range);
      var1.append(')');
      return var1.toString();
   }
}
