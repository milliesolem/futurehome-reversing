package kotlinx.coroutines.flow

import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlinx.atomicfu.AtomicBoolean
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.internal.ChannelFlow
import kotlinx.coroutines.flow.internal.SendingCollector

private class ChannelAsFlow<T>(channel: ReceiveChannel<Any>,
   consume: Boolean,
   context: CoroutineContext = EmptyCoroutineContext.INSTANCE as CoroutineContext,
   capacity: Int = -3,
   onBufferOverflow: BufferOverflow = BufferOverflow.SUSPEND
) : ChannelFlow(var3, var4, var5) {
   private final val channel: ReceiveChannel<Any>
   private final val consume: Boolean
   private final val consumed: AtomicBoolean

   init {
      this.channel = var1;
      this.consume = var2;
      this.consumed = 0;
   }

   private fun markConsumed() {
      if (this.consume && consumed$FU.getAndSet(this, 1) != 0) {
         throw new IllegalStateException("ReceiveChannel.consumeAsFlow can be collected just once".toString());
      }
   }

   protected override fun additionalToStringProps(): String {
      val var1: StringBuilder = new StringBuilder("channel=");
      var1.append(this.channel);
      return var1.toString();
   }

   public override suspend fun collect(collector: FlowCollector<Any>) {
      if (this.capacity == -3) {
         this.markConsumed();
         val var4: Any = FlowKt__ChannelsKt.access$emitAllImpl$FlowKt__ChannelsKt(var1, this.channel, this.consume, var2);
         return if (var4 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var4 else Unit.INSTANCE;
      } else {
         val var3: Any = super.collect(var1, var2);
         return if (var3 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var3 else Unit.INSTANCE;
      }
   }

   protected override suspend fun collectTo(scope: ProducerScope<Any>) {
      val var3: Any = FlowKt__ChannelsKt.access$emitAllImpl$FlowKt__ChannelsKt(new SendingCollector(var1), this.channel, this.consume, var2);
      return if (var3 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var3 else Unit.INSTANCE;
   }

   protected override fun create(context: CoroutineContext, capacity: Int, onBufferOverflow: BufferOverflow): ChannelFlow<Any> {
      return new ChannelAsFlow<>(this.channel, this.consume, var1, var2, var3);
   }

   public override fun dropChannelOperators(): Flow<Any> {
      return new ChannelAsFlow<>(this.channel, this.consume, null, 0, null, 28, null);
   }

   public override fun produceImpl(scope: CoroutineScope): ReceiveChannel<Any> {
      this.markConsumed();
      val var2: ReceiveChannel;
      if (this.capacity == -3) {
         var2 = this.channel;
      } else {
         var2 = super.produceImpl(var1);
      }

      return var2;
   }
}
