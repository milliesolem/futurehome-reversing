package kotlinx.coroutines.flow.internal

import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

internal class ChannelFlowOperatorImpl<T>(flow: Flow<Any>,
   context: CoroutineContext = EmptyCoroutineContext.INSTANCE as CoroutineContext,
   capacity: Int = -3,
   onBufferOverflow: BufferOverflow = BufferOverflow.SUSPEND
) : ChannelFlowOperator(var1, var2, var3, var4) {
   protected override fun create(context: CoroutineContext, capacity: Int, onBufferOverflow: BufferOverflow): ChannelFlow<Any> {
      return new ChannelFlowOperatorImpl<>(this.flow, var1, var2, var3);
   }

   public override fun dropChannelOperators(): Flow<Any> {
      return this.flow;
   }

   protected override suspend fun flowCollect(collector: FlowCollector<Any>) {
      val var3: Any = this.flow.collect(var1, var2);
      return if (var3 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var3 else Unit.INSTANCE;
   }
}
