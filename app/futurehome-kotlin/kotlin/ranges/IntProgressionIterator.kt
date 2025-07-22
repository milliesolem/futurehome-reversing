package kotlin.ranges

import java.util.NoSuchElementException

internal class IntProgressionIterator(first: Int, last: Int, step: Int) : IntIterator {
   public final val step: Int
   private final val finalElement: Int
   private final var hasNext: Boolean
   private final var next: Int

   init {
      this.step = var3;
      this.finalElement = var2;
      var var4: Boolean = true;
      if (if (var3 > 0) var1 > var2 else var1 < var2) {
         var4 = false;
      }

      this.hasNext = var4;
      if (!var4) {
         var1 = var2;
      }

      this.next = var1;
   }

   public override operator fun hasNext(): Boolean {
      return this.hasNext;
   }

   public override fun nextInt(): Int {
      val var1: Int = this.next;
      if (this.next == this.finalElement) {
         if (!this.hasNext) {
            throw new NoSuchElementException();
         }

         this.hasNext = false;
      } else {
         this.next = this.step + this.next;
      }

      return var1;
   }
}
