package kotlin.sequences

import java.util.HashSet

private class DistinctIterator<T, K>(source: Iterator<Any>, keySelector: (Any) -> Any) : AbstractIterator<T> {
   private final val source: Iterator<Any>
   private final val keySelector: (Any) -> Any
   private final val observed: HashSet<Any>

   init {
      this.source = var1;
      this.keySelector = var2;
      this.observed = new HashSet<>();
   }

   protected override fun computeNext() {
      while (this.source.hasNext()) {
         val var1: Any = this.source.next();
         if (this.observed.add(this.keySelector.invoke((T)var1))) {
            this.setNext((T)var1);
            return;
         }
      }

      this.done();
   }
}
