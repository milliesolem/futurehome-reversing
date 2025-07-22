package kotlin.jvm.internal

public class ShortSpreadBuilder(size: Int) : PrimitiveSpreadBuilder(var1) {
   private final val values: ShortArray

   init {
      this.values = new short[var1];
   }

   public fun add(value: Short) {
      val var3: ShortArray = this.values;
      val var2: Int = this.getPosition();
      this.setPosition(var2 + 1);
      var3[var2] = var1;
   }

   protected open fun ShortArray.getSize(): Int {
      return var1.length;
   }

   public fun toArray(): ShortArray {
      return this.toArray(this.values, new short[this.size()]);
   }
}
