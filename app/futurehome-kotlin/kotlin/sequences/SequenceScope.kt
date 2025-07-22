package kotlin.sequences

import kotlin.coroutines.intrinsics.IntrinsicsKt

public abstract class SequenceScope<T> {
   public abstract suspend fun yield(value: Any) {
   }

   public suspend fun yieldAll(elements: Iterable<Any>) {
      if (var1 is java.util.Collection && (var1 as java.util.Collection).isEmpty()) {
         return Unit.INSTANCE;
      } else {
         val var3: Any = this.yieldAll(var1.iterator(), var2);
         return if (var3 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var3 else Unit.INSTANCE;
      }
   }

   public abstract suspend fun yieldAll(iterator: Iterator<Any>) {
   }

   public suspend fun yieldAll(sequence: Sequence<Any>) {
      val var3: Any = this.yieldAll(var1.iterator(), var2);
      return if (var3 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var3 else Unit.INSTANCE;
   }
}
