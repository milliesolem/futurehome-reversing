package kotlin.jvm.internal

public class CharSpreadBuilder(size: Int) : PrimitiveSpreadBuilder(var1) {
   private final val values: CharArray

   init {
      this.values = new char[var1];
   }

   public fun add(value: Char) {
      val var3: CharArray = this.values;
      val var2: Int = this.getPosition();
      this.setPosition(var2 + 1);
      var3[var2] = var1;
   }

   protected open fun CharArray.getSize(): Int {
      return var1.length;
   }

   public fun toArray(): CharArray {
      return this.toArray(this.values, new char[this.size()]);
   }
}
