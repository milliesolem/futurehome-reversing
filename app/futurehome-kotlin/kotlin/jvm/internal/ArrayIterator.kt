package kotlin.jvm.internal

import java.util.NoSuchElementException
import kotlin.jvm.internal.markers.KMappedMarker

private class ArrayIterator<T>(vararg array: Any) : java.util.Iterator<T>, KMappedMarker {
   public final val array: Array<Any>
   private final var index: Int

   init {
      this.array = (T[])var1;
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

   public override operator fun next(): Any {
      var var1: Int;
      var var2: Array<Any>;
      try {
         var2 = this.array;
         var1 = this.index++;
      } catch (var3: ArrayIndexOutOfBoundsException) {
         this.index--;
         throw new NoSuchElementException(var3.getMessage());
      }

      return (T)var2[var1];
   }

   override fun remove() {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }
}
