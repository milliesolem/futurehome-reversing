package kotlin.jvm.internal

public class ByteSpreadBuilder(size: Int) : PrimitiveSpreadBuilder(var1) {
   private final val values: ByteArray

   init {
      this.values = new byte[var1];
   }

   public fun add(value: Byte) {
      val var3: ByteArray = this.values;
      val var2: Int = this.getPosition();
      this.setPosition(var2 + 1);
      var3[var2] = var1;
   }

   protected open fun ByteArray.getSize(): Int {
      return var1.length;
   }

   public fun toArray(): ByteArray {
      return this.toArray(this.values, new byte[this.size()]);
   }
}
