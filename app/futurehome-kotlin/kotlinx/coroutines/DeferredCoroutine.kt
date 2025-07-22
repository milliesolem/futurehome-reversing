package kotlinx.coroutines

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.selects.SelectClause1

private open class DeferredCoroutine<T>(parentContext: CoroutineContext, active: Boolean) : AbstractCoroutine(var1, true, var2), Deferred<T> {
   public open val onAwait: SelectClause1<Any>
      public open get() {
         val var1: SelectClause1 = this.getOnAwaitInternal();
         return var1;
      }


   public override suspend fun await(): Any {
      return await$suspendImpl(this, var1);
   }

   public override fun getCompleted(): Any {
      return (T)this.getCompletedInternal$kotlinx_coroutines_core();
   }
}
