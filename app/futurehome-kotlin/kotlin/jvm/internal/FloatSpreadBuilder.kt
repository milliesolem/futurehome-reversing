package kotlin.jvm.internal

public class FloatSpreadBuilder(size: Int) : PrimitiveSpreadBuilder(var1) {
   private final val values: FloatArray

   init {
      this.values = new float[var1];
   }

   public fun add(value: Float) {
      val var3: FloatArray = this.values;
      val var2: Int = this.getPosition();
      this.setPosition(var2 + 1);
      var3[var2] = var1;
   }

   protected open fun FloatArray.getSize(): Int {
      return var1.length;
   }

   public fun toArray(): FloatArray {
      return this.toArray(this.values, new float[this.size()]);
   }
}
