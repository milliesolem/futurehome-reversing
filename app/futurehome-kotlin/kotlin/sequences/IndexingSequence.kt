package kotlin.sequences

internal class IndexingSequence<T>(sequence: Sequence<Any>) : Sequence<IndexedValue<? extends T>> {
   private final val sequence: Sequence<Any>

   init {
      this.sequence = var1;
   }

   public override operator fun iterator(): Iterator<IndexedValue<Any>> {
      return (java.util.Iterator<IndexedValue<T>>)(new java.util.Iterator<IndexedValue<? extends T>>(this) {
         private int index;
         private final java.util.Iterator<T> iterator;

         {
            this.iterator = IndexingSequence.access$getSequence$p(var1).iterator();
         }

         public final int getIndex() {
            return this.index;
         }

         public final java.util.Iterator<T> getIterator() {
            return this.iterator;
         }

         @Override
         public boolean hasNext() {
            return this.iterator.hasNext();
         }

         public IndexedValue<T> next() {
            val var1: Int = this.index++;
            if (var1 < 0) {
               CollectionsKt.throwIndexOverflow();
            }

            return new IndexedValue<>(var1, this.iterator.next());
         }

         @Override
         public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
         }

         public final void setIndex(int var1) {
            this.index = var1;
         }
      });
   }
}
