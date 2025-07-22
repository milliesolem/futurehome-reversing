package kotlinx.coroutines.flow.internal

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.internal.ScopeCoroutine

private class FlowCoroutine<T>(context: CoroutineContext, uCont: Continuation<Any>) : ScopeCoroutine(var1, var2) {
   public override fun childCancelled(cause: Throwable): Boolean {
      return var1 is ChildCancelledException || this.cancelImpl$kotlinx_coroutines_core(var1);
   }
}
