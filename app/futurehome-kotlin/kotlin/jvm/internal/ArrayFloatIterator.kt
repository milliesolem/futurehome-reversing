package kotlin.jvm.internal

import java.util.NoSuchElementException

private class ArrayFloatIterator(array: FloatArray) : FloatIterator {
   private final val array: FloatArray
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

   public override fun nextFloat(): Float {
      var var2: Int;
      var var3: FloatArray;
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
