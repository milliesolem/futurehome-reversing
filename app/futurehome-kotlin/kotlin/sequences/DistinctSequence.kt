package kotlin.sequences

internal class DistinctSequence<T, K>(source: Sequence<Any>, keySelector: (Any) -> Any) : Sequence<T> {
   private final val source: Sequence<Any>
   private final val keySelector: (Any) -> Any

   init {
      this.source = var1;
      this.keySelector = var2;
   }

   public override operator fun iterator(): Iterator<Any> {
      return (new DistinctIterator<>(this.source.iterator(), this.keySelector)) as MutableIterator<T>;
   }
}
