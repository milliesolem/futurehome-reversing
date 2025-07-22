package kotlin.sequences

import java.util.concurrent.atomic.AtomicReference

internal class ConstrainedOnceSequence<T>(sequence: Sequence<Any>) : Sequence<T> {
   private final val sequenceRef: AtomicReference<Sequence<Any>>

   init {
      this.sequenceRef = new AtomicReference<>(var1);
   }

   public override operator fun iterator(): Iterator<Any> {
      val var1: Sequence = this.sequenceRef.getAndSet(null);
      if (var1 != null) {
         return var1.iterator();
      } else {
         throw new IllegalStateException("This sequence can be consumed only once.");
      }
   }
}
