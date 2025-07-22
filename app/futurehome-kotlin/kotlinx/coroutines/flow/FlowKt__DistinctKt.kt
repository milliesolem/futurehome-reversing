package kotlinx.coroutines.flow

import kotlin.jvm.functions.Function1
import kotlin.jvm.functions.Function2
import kotlin.jvm.internal.TypeIntrinsics

@JvmSynthetic
internal class FlowKt__DistinctKt {
   private final val defaultAreEquivalent: (Any?, Any?) -> Boolean = <unrepresentable>.INSTANCE as Function2
   private final val defaultKeySelector: (Any?) -> Any? = <unrepresentable>.INSTANCE as Function1

   @JvmStatic
   public fun <T> Flow<T>.distinctUntilChanged(): Flow<T> {
      if (var0 !is StateFlow) {
         var0 = distinctUntilChangedBy$FlowKt__DistinctKt(var0, defaultKeySelector, defaultAreEquivalent);
      }

      return var0;
   }

   @JvmStatic
   public fun <T> Flow<T>.distinctUntilChanged(areEquivalent: (T, T) -> Boolean): Flow<T> {
      val var2: Function1 = defaultKeySelector;
      return distinctUntilChangedBy$FlowKt__DistinctKt(
         var0, var2, TypeIntrinsics.beforeCheckcastToFunctionOfArity(var1, 2) as (Any?, Any?) -> java.lang.Boolean
      );
   }

   @JvmStatic
   public fun <T, K> Flow<T>.distinctUntilChangedBy(keySelector: (T) -> K): Flow<T> {
      return distinctUntilChangedBy$FlowKt__DistinctKt(var0, var1, defaultAreEquivalent);
   }

   @JvmStatic
   private fun <T> Flow<T>.distinctUntilChangedBy(keySelector: (T) -> Any?, areEquivalent: (Any?, Any?) -> Boolean): Flow<T> {
      return (Flow<T>)(if (var0 is DistinctFlowImpl && (var0 as DistinctFlowImpl).keySelector === var1 && (var0 as DistinctFlowImpl).areEquivalent === var2)
         var0
         else
         new DistinctFlowImpl(var0, var1, var2));
   }
}
