package kotlinx.coroutines.selects

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.DisposableHandle

public sealed interface SelectInstance<R> {
   public val context: CoroutineContext

   public abstract fun disposeOnCompletion(disposableHandle: DisposableHandle) {
   }

   public abstract fun selectInRegistrationPhase(internalResult: Any?) {
   }

   public abstract fun trySelect(clauseObject: Any, result: Any?): Boolean {
   }
}
