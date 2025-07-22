package kotlin.ranges

import java.util.NoSuchElementException
import kotlin.jvm.internal.markers.KMappedMarker

private class ULongProgressionIterator(first: ULong, last: ULong, step: Long) : ULongProgressionIterator(var1, var3, var5),
   java.util.Iterator<ULong>,
   KMappedMarker {
   private final val finalElement: ULong
   private final var hasNext: Boolean
   private final val step: ULong
   private final var next: ULong

   fun ULongProgressionIterator(var1: Long, var3: Long, var5: Long) {
      this.finalElement = var3;
      var var7: Boolean = true;
      if (if (var5 > 0L) UByte$$ExternalSyntheticBackport0.m(var1, var3) > 0 else UByte$$ExternalSyntheticBackport0.m(var1, var3) < 0) {
         var7 = false;
      }

      this.hasNext = var7;
      this.step = ULong.constructor-impl(var5);
      if (!this.hasNext) {
         var1 = var3;
      }

      this.next = var1;
   }

   public override operator fun hasNext(): Boolean {
      return this.hasNext;
   }

   public open operator fun next(): ULong {
      val var1: Long = this.next;
      if (this.next == this.finalElement) {
         if (!this.hasNext) {
            throw new NoSuchElementException();
         }

         this.hasNext = false;
      } else {
         this.next = ULong.constructor-impl(this.step + this.next);
      }

      return var1;
   }

   override fun remove() {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }
}
