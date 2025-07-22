package kotlin.jvm.internal

import java.util.NoSuchElementException

private class ArrayIntIterator(array: IntArray) : IntIterator {
   private final val array: IntArray
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

   public override fun nextInt(): Int {
      var var1: Int;
      var var2: IntArray;
      try {
         var2 = this.array;
         var1 = this.index++;
      } catch (var3: ArrayIndexOutOfBoundsException) {
         this.index--;
         throw new NoSuchElementException(var3.getMessage());
      }

      return var2[var1];
   }
}
