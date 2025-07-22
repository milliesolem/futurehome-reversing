package kotlinx.coroutines

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.internal.ScopeCoroutine

private class SupervisorCoroutine<T>(context: CoroutineContext, uCont: Continuation<Any>) : ScopeCoroutine(var1, var2) {
   public override fun childCancelled(cause: Throwable): Boolean {
      return false;
   }
}
