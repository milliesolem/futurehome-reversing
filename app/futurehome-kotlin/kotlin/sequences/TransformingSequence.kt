package kotlin.sequences

internal class TransformingSequence<T, R>(sequence: Sequence<Any>, transformer: (Any) -> Any) : Sequence<R> {
   private final val sequence: Sequence<Any>
   private final val transformer: (Any) -> Any

   init {
      this.sequence = var1;
      this.transformer = var2;
   }

   internal fun <E> flatten(iterator: (Any) -> Iterator<E>): Sequence<E> {
      return new FlatteningSequence<>(this.sequence, this.transformer, var1);
   }

   public override operator fun iterator(): Iterator<Any> {
      return new java.util.Iterator<R>(this) {
         private final java.util.Iterator<T> iterator;
         final TransformingSequence<T, R> this$0;

         {
            this.this$0 = var1;
            this.iterator = TransformingSequence.access$getSequence$p(var1).iterator();
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
            return (R)TransformingSequence.access$getTransformer$p(this.this$0).invoke(this.iterator.next());
         }

         @Override
         public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
         }
      };
   }
}
