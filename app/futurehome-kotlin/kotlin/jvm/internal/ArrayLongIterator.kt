package kotlin.jvm.internal

import java.util.NoSuchElementException

private class ArrayLongIterator(array: LongArray) : LongIterator {
   private final val array: LongArray
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

   public override fun nextLong(): Long {
      var var1: Int;
      var var4: LongArray;
      try {
         var4 = this.array;
         var1 = this.index++;
      } catch (var5: ArrayIndexOutOfBoundsException) {
         this.index--;
         throw new NoSuchElementException(var5.getMessage());
      }

      return var4[var1];
   }
}
