package kotlin.jvm.internal

import java.util.NoSuchElementException

private class ArrayByteIterator(array: ByteArray) : ByteIterator {
   private final val array: ByteArray
   private final var index: Int

   init {
      this.array = var1;
   }

   public override operator fun hasNext(): Boolean {
      val var1: Boolean;
      if (this.index < this.array.length) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public override fun nextByte(): Byte {
      var var2: Int;
      var var3: ByteArray;
      try {
         var3 = this.array;
         var2 = this.index++;
      } catch (var4: ArrayIndexOutOfBoundsException) {
         this.index--;
         throw new NoSuchElementException(var4.getMessage());
      }

      return var3[var2];
   }
}
