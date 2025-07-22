package kotlinx.coroutines.flow

import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.IntrinsicsKt

private class SafeFlow<T>(block: (FlowCollector<Any>, Continuation<Unit>) -> Any?) : AbstractFlow<T> {
   private final val block: (FlowCollector<Any>, Continuation<Unit>) -> Any?

   init {
      this.block = var1;
   }

   public override suspend fun collectSafely(collector: FlowCollector<Any>) {
      val var3: Any = this.block.invoke(var1, var2);
      return if (var3 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var3 else Unit.INSTANCE;
   }
}
