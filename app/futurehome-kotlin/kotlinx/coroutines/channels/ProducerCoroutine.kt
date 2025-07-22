package kotlinx.coroutines.channels

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineExceptionHandlerKt

private class ProducerCoroutine<E>(parentContext: CoroutineContext, channel: Channel<Any>) : ChannelCoroutine(var1, var2, true, true), ProducerScope<E> {
   public open val isActive: Boolean
      public open get() {
         return super.isActive();
      }


   protected override fun onCancelled(cause: Throwable, handled: Boolean) {
      if (!this.get_channel().close(var1) && !var2) {
         CoroutineExceptionHandlerKt.handleCoroutineException(this.getContext(), var1);
      }
   }

   protected open fun onCompleted(value: Unit) {
      SendChannel.DefaultImpls.close$default(this.get_channel(), null, 1, null);
   }
}
