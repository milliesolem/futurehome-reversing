package kotlin.collections

import kotlin.jvm.internal.markers.KMappedMarker

internal class IndexingIterator<T>(iterator: Iterator<Any>) : java.util.Iterator<IndexedValue<? extends T>>, KMappedMarker {
   private final val iterator: Iterator<Any>
   private final var index: Int

   init {
      this.iterator = var1;
   }

   public override operator fun hasNext(): Boolean {
      return this.iterator.hasNext();
   }

   public operator fun next(): IndexedValue<Any> {
      val var1: Int = this.index++;
      if (var1 < 0) {
         CollectionsKt.throwIndexOverflow();
      }

      return new IndexedValue<>(var1, this.iterator.next());
   }

   override fun remove() {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }
}
