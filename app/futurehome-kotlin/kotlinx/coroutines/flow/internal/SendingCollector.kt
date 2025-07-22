package kotlinx.coroutines.flow.internal

import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.FlowCollector

public class SendingCollector<T>(channel: SendChannel<Any>) : FlowCollector<T> {
   private final val channel: SendChannel<Any>

   init {
      this.channel = var1;
   }

   public override suspend fun emit(value: Any) {
      var1 = this.channel.send((T)var1, var2);
      return if (var1 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var1 else Unit.INSTANCE;
   }
}
