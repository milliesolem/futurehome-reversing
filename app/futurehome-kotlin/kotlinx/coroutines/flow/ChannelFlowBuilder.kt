package kotlinx.coroutines.flow

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.flow.internal.ChannelFlow

private open class ChannelFlowBuilder<T>(block: (ProducerScope<Any>, Continuation<Unit>) -> Any?,
   context: CoroutineContext = EmptyCoroutineContext.INSTANCE as CoroutineContext,
   capacity: Int = -2,
   onBufferOverflow: BufferOverflow = BufferOverflow.SUSPEND
) : ChannelFlow(var2, var3, var4) {
   private final val block: (ProducerScope<Any>, Continuation<Unit>) -> Any?

   init {
      this.block = var1;
   }

   protected override suspend fun collectTo(scope: ProducerScope<Any>) {
      return collectTo$suspendImpl(this, var1, var2);
   }

   protected override fun create(context: CoroutineContext, capacity: Int, onBufferOverflow: BufferOverflow): ChannelFlow<Any> {
      return new ChannelFlowBuilder<>(this.block, var1, var2, var3);
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder("block[");
      var1.append(this.block);
      var1.append("] -> ");
      var1.append(super.toString());
      return var1.toString();
   }
}
