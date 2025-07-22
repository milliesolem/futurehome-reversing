package kotlin.sequences

import java.util.Enumeration

internal class SequencesKt__SequencesJVMKt : SequencesKt__SequenceBuilderKt {
   open fun SequencesKt__SequencesJVMKt() {
   }

   @JvmStatic
   public inline fun <T> Enumeration<T>.asSequence(): Sequence<T> {
      return SequencesKt.asSequence(CollectionsKt.iterator(var0));
   }
}
