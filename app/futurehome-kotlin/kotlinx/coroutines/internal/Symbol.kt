package kotlinx.coroutines.internal

internal class Symbol(symbol: String) {
   public final val symbol: String

   init {
      this.symbol = var1;
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder("<");
      var1.append(this.symbol);
      var1.append('>');
      return var1.toString();
   }

   public inline fun <T> unbox(value: Any?): T {
      var var2: Any = var1;
      if (var1 === this) {
         var2 = null;
      }

      return (T)var2;
   }
}
