package kotlin.sequences

import kotlin.jvm.functions.Function2

internal class TransformingIndexedSequence<T, R>(sequence: Sequence<Any>, transformer: (Int, Any) -> Any) : Sequence<R> {
   private final val sequence: Sequence<Any>
   private final val transformer: (Int, Any) -> Any

   init {
      this.sequence = var1;
      this.transformer = var2;
   }

   public override operator fun iterator(): Iterator<Any> {
      return new java.util.Iterator<R>(this) {
         private int index;
         private final java.util.Iterator<T> iterator;
         final TransformingIndexedSequence<T, R> this$0;

         {
            this.this$0 = var1;
            this.iterator = TransformingIndexedSequence.access$getSequence$p(var1).iterator();
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

         @Override
         public R next() {
            val var2: Function2 = TransformingIndexedSequence.access$getTransformer$p(this.this$0);
            val var1: Int = this.index++;
            if (var1 < 0) {
               CollectionsKt.throwIndexOverflow();
            }

            return (R)var2.invoke(var1, this.iterator.next());
         }

         @Override
         public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
         }

         public final void setIndex(int var1) {
            this.index = var1;
         }
      };
   }
}
