package kotlin.sequences

internal class MergingSequence<T1, T2, V>(sequence1: Sequence<Any>, sequence2: Sequence<Any>, transform: (Any, Any) -> Any) : Sequence<V> {
   private final val sequence1: Sequence<Any>
   private final val sequence2: Sequence<Any>
   private final val transform: (Any, Any) -> Any

   init {
      this.sequence1 = var1;
      this.sequence2 = var2;
      this.transform = var3;
   }

   public override operator fun iterator(): Iterator<Any> {
      return new java.util.Iterator<V>(this) {
         private final java.util.Iterator<T1> iterator1;
         private final java.util.Iterator<T2> iterator2;
         final MergingSequence<T1, T2, V> this$0;

         {
            this.this$0 = var1;
            this.iterator1 = MergingSequence.access$getSequence1$p(var1).iterator();
            this.iterator2 = MergingSequence.access$getSequence2$p(var1).iterator();
         }

         public final java.util.Iterator<T1> getIterator1() {
            return this.iterator1;
         }

         public final java.util.Iterator<T2> getIterator2() {
            return this.iterator2;
         }

         @Override
         public boolean hasNext() {
            val var1: Boolean;
            if (this.iterator1.hasNext() && this.iterator2.hasNext()) {
               var1 = true;
            } else {
               var1 = false;
            }

            return var1;
         }

         @Override
         public V next() {
            return (V)MergingSequence.access$getTransform$p(this.this$0).invoke(this.iterator1.next(), this.iterator2.next());
         }

         @Override
         public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
         }
      };
   }
}
