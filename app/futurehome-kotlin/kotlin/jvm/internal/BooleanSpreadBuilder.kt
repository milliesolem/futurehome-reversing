package kotlin.jvm.internal

public class BooleanSpreadBuilder(size: Int) : PrimitiveSpreadBuilder(var1) {
   private final val values: BooleanArray

   init {
      this.values = new boolean[var1];
   }

   public fun add(value: Boolean) {
      val var3: BooleanArray = this.values;
      val var2: Int = this.getPosition();
      this.setPosition(var2 + 1);
      var3[var2] = var1;
   }

   protected open fun BooleanArray.getSize(): Int {
      return var1.length;
   }

   public fun toArray(): BooleanArray {
      return this.toArray(this.values, new boolean[this.size()]);
   }
}
