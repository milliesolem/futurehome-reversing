package kotlin.jvm.internal

import java.util.NoSuchElementException

private class ArrayDoubleIterator(array: DoubleArray) : DoubleIterator {
   private final val array: DoubleArray
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

   public override fun nextDouble(): Double {
      var var3: Int;
      var var4: DoubleArray;
      try {
         var4 = this.array;
         var3 = this.index++;
      } catch (var5: ArrayIndexOutOfBoundsException) {
         this.index--;
         throw new NoSuchElementException(var5.getMessage());
      }

      return var4[var3];
   }
}
