package kotlin.jvm.internal

public class LongSpreadBuilder(size: Int) : PrimitiveSpreadBuilder(var1) {
   private final val values: LongArray

   init {
      this.values = new long[var1];
   }

   public fun add(value: Long) {
      val var4: LongArray = this.values;
      val var3: Int = this.getPosition();
      this.setPosition(var3 + 1);
      var4[var3] = var1;
   }

   protected open fun LongArray.getSize(): Int {
      return var1.length;
   }

   public fun toArray(): LongArray {
      return this.toArray(this.values, new long[this.size()]);
   }
}
