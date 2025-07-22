package kotlin.sequences

import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.jvm.functions.Function2

internal class SequencesKt__SequenceBuilderKt {
   private const val State_NotReady: Int = 0
   private const val State_ManyNotReady: Int = 1
   private const val State_ManyReady: Int = 2
   private const val State_Ready: Int = 3
   private const val State_Done: Int = 4
   private const val State_Failed: Int = 5

   open fun SequencesKt__SequenceBuilderKt() {
   }

   @JvmStatic
   public fun <T> iterator(block: (SequenceScope<T>, Continuation<Unit>) -> Any?): Iterator<T> {
      val var1: SequenceBuilderIterator = new SequenceBuilderIterator();
      var1.setNextStep(IntrinsicsKt.createCoroutineUnintercepted(var0, var1, var1));
      return var1;
   }

   @JvmStatic
   public fun <T> sequence(block: (SequenceScope<T>, Continuation<Unit>) -> Any?): Sequence<T> {
      return new Sequence<T>(var0) {
         final Function2 $block$inlined;

         {
            this.$block$inlined = var1;
         }

         @Override
         public java.util.Iterator<T> iterator() {
            return SequencesKt.iterator(this.$block$inlined);
         }
      };
   }
}
