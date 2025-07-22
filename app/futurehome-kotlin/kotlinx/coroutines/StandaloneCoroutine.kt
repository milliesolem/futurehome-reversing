package kotlinx.coroutines

import kotlin.coroutines.CoroutineContext

private open class StandaloneCoroutine(parentContext: CoroutineContext, active: Boolean) : AbstractCoroutine(var1, true, var2) {
   protected override fun handleJobException(exception: Throwable): Boolean {
      CoroutineExceptionHandlerKt.handleCoroutineException(this.getContext(), var1);
      return true;
   }
}
