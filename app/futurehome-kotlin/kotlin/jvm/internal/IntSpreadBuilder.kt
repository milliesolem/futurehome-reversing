package kotlin.jvm.internal

public class IntSpreadBuilder(size: Int) : PrimitiveSpreadBuilder(var1) {
   private final val values: IntArray

   init {
      this.values = new int[var1];
   }

   public fun add(value: Int) {
      val var3: IntArray = this.values;
      val var2: Int = this.getPosition();
      this.setPosition(var2 + 1);
      var3[var2] = var1;
   }

   protected open fun IntArray.getSize(): Int {
      return var1.length;
   }

   public fun toArray(): IntArray {
      return this.toArray(this.values, new int[this.size()]);
   }
}
