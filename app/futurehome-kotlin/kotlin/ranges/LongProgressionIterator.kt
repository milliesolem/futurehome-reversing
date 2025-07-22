package kotlin.ranges

import java.util.NoSuchElementException

internal class LongProgressionIterator(first: Long, last: Long, step: Long) : LongIterator {
   public final val step: Long
   private final val finalElement: Long
   private final var hasNext: Boolean
   private final var next: Long

   init {
      this.step = var5;
      this.finalElement = var3;
      var var8: Boolean = true;
      if (if (var5 > 0L) var1 > var3 else var1 < var3) {
         var8 = false;
      }

      this.hasNext = var8;
      if (!var8) {
         var1 = var3;
      }

      this.next = var1;
   }

   public override operator fun hasNext(): Boolean {
      return this.hasNext;
   }

   public override fun nextLong(): Long {
      val var1: Long = this.next;
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
