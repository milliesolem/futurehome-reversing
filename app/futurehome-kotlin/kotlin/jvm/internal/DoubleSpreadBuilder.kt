package kotlin.jvm.internal

public class DoubleSpreadBuilder(size: Int) : PrimitiveSpreadBuilder(var1) {
   private final val values: DoubleArray

   init {
      this.values = new double[var1];
   }

   public fun add(value: Double) {
      val var4: DoubleArray = this.values;
      val var3: Int = this.getPosition();
      this.setPosition(var3 + 1);
      var4[var3] = var1;
   }

   protected open fun DoubleArray.getSize(): Int {
      return var1.length;
   }

   public fun toArray(): DoubleArray {
      return this.toArray(this.values, new double[this.size()]);
   }
}
