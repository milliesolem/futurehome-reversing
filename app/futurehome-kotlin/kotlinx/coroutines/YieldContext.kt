package kotlinx.coroutines

import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

internal class YieldContext : AbstractCoroutineContextElement(Key) {
   public final var dispatcherWasUnconfined: Boolean
      private set

   public companion object Key : CoroutineContext.Key<YieldContext>
}
