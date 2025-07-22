package kotlin.ranges

import java.util.NoSuchElementException
import kotlin.jvm.internal.markers.KMappedMarker

private class UIntProgressionIterator(first: UInt, last: UInt, step: Int) : UIntProgressionIterator(var1, var2, var3), java.util.Iterator<UInt>, KMappedMarker {
   private final val finalElement: UInt
   private final var hasNext: Boolean
   private final val step: UInt
   private final var next: UInt

   fun UIntProgressionIterator(var1: Int, var2: Int, var3: Int) {
      this.finalElement = var2;
      var var4: Boolean = true;
      if (if (var3 > 0) UByte$$ExternalSyntheticBackport0.m$2(var1, var2) > 0 else UByte$$ExternalSyntheticBackport0.m$2(var1, var2) < 0) {
         var4 = false;
      }

      this.hasNext = var4;
      this.step = UInt.constructor-impl(var3);
      if (!this.hasNext) {
         var1 = var2;
      }

      this.next = var1;
   }

   public override operator fun hasNext(): Boolean {
      return this.hasNext;
   }

   public open operator fun next(): UInt {
      val var1: Int = this.next;
      if (this.next == this.finalElement) {
         if (!this.hasNext) {
            throw new NoSuchElementException();
         }

         this.hasNext = false;
      } else {
         this.next = UInt.constructor-impl(this.step + this.next);
      }

      return var1;
   }

   override fun remove() {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }
}
