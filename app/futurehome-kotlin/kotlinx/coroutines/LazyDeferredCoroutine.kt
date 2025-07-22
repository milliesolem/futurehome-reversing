package kotlinx.coroutines

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlinx.coroutines.intrinsics.CancellableKt

private class LazyDeferredCoroutine<T>(parentContext: CoroutineContext, block: (CoroutineScope, Continuation<Any>) -> Any?) : DeferredCoroutine(var1, false) {
   private final val continuation: Continuation<Unit>

   init {
      this.continuation = IntrinsicsKt.createCoroutineUnintercepted(var2, this, this);
   }

   protected override fun onStart() {
      CancellableKt.startCoroutineCancellable(this.continuation, this);
   }
}
