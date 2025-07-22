package kotlinx.coroutines

internal data class CompletedWithCancellation(result: Any?, onCancellation: (Throwable) -> Unit) {
   public final val onCancellation: (Throwable) -> Unit
   public final val result: Any?

   init {
      this.result = var1;
      this.onCancellation = var2;
   }

   public operator fun component1(): Any? {
      return this.result;
   }

   public operator fun component2(): (Throwable) -> Unit {
      return this.onCancellation;
   }

   public fun copy(result: Any? = var0.result, onCancellation: (Throwable) -> Unit = var0.onCancellation): CompletedWithCancellation {
      return new CompletedWithCancellation(var1, var2);
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === var1) {
         return true;
      } else if (var1 !is CompletedWithCancellation) {
         return false;
      } else {
         var1 = var1;
         if (!(this.result == var1.result)) {
            return false;
         } else {
            return this.onCancellation == var1.onCancellation;
         }
      }
   }

   public override fun hashCode(): Int {
      val var1: Int;
      if (this.result == null) {
         var1 = 0;
      } else {
         var1 = this.result.hashCode();
      }

      return var1 * 31 + this.onCancellation.hashCode();
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder("CompletedWithCancellation(result=");
      var1.append(this.result);
      var1.append(", onCancellation=");
      var1.append(this.onCancellation);
      var1.append(')');
      return var1.toString();
   }
}
