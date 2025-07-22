package kotlinx.coroutines.channels

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlinx.coroutines.intrinsics.CancellableKt

private class LazyBroadcastCoroutine<E>(parentContext: CoroutineContext, channel: BroadcastChannel<Any>, block: (ProducerScope<Any>, Continuation<Unit>) -> Any?) : BroadcastCoroutine(
      var1, var2, false
   ) {
   private final val continuation: Continuation<Unit>

   init {
      this.continuation = IntrinsicsKt.createCoroutineUnintercepted(var3, this, this);
   }

   protected override fun onStart() {
      CancellableKt.startCoroutineCancellable(this.continuation, this);
   }

   public override fun openSubscription(): ReceiveChannel<Any> {
      val var1: ReceiveChannel = this.get_channel().openSubscription();
      this.start();
      return var1;
   }
}
