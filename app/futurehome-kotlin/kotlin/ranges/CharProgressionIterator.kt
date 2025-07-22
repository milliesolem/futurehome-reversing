package kotlin.ranges

import java.util.NoSuchElementException
import kotlin.jvm.internal.Intrinsics

internal class CharProgressionIterator(first: Char, last: Char, step: Int) : CharIterator {
   public final val step: Int
   private final val finalElement: Int
   private final var hasNext: Boolean
   private final var next: Int

   init {
      this.step = var3;
      this.finalElement = var2;
      var var4: Boolean = true;
      if (if (var3 > 0) Intrinsics.compare((int)var1, (int)var2) > 0 else Intrinsics.compare((int)var1, (int)var2) < 0) {
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

   public override fun nextChar(): Char {
      val var1: Int = this.next;
      if (this.next == this.finalElement) {
         if (!this.hasNext) {
            throw new NoSuchElementException();
         }

         this.hasNext = false;
      } else {
         this.next = this.step + this.next;
      }

      return (char)var1;
   }
}
