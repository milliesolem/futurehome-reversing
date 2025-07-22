package kotlin.collections

import java.util.NoSuchElementException
import kotlin.jvm.internal.markers.KMappedMarker

public abstract class AbstractIterator<T> : java.util.Iterator<T>, KMappedMarker {
   private final var state: Int
   private final var nextValue: Any?

   private fun tryToComputeNext(): Boolean {
      this.state = 3;
      this.computeNext();
      var var2: Boolean = true;
      if (this.state != 1) {
         var2 = false;
      }

      return var2;
   }

   protected abstract fun computeNext() {
   }

   protected fun done() {
      this.state = 2;
   }

   public override operator fun hasNext(): Boolean {
      var var2: Boolean;
      if (this.state != 0) {
         var2 = true;
         if (this.state != 1) {
            if (this.state != 2) {
               throw new IllegalArgumentException("hasNext called when the iterator is in the FAILED state.");
            }

            var2 = false;
         }
      } else {
         var2 = this.tryToComputeNext();
      }

      return var2;
   }

   public override operator fun next(): Any {
      if (this.state == 1) {
         this.state = 0;
         return this.nextValue;
      } else if (this.state != 2 && this.tryToComputeNext()) {
         this.state = 0;
         return this.nextValue;
      } else {
         throw new NoSuchElementException();
      }
   }

   override fun remove() {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   protected fun setNext(value: Any) {
      this.nextValue = (T)var1;
      this.state = 1;
   }
}
