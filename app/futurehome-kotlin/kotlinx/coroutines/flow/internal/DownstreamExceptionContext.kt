package kotlinx.coroutines.flow.internal

import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.CoroutineContext.Element
import kotlin.coroutines.CoroutineContext.Key

internal class DownstreamExceptionContext(e: Throwable, originalContext: CoroutineContext) : CoroutineContext {
   public final val e: Throwable

   init {
      this.e = var1;
      this.$$delegate_0 = var2;
   }

   public override fun <R> fold(initial: R, operation: (R, Element) -> R): R {
      return (R)this.$$delegate_0.fold(var1, var2);
   }

   public override operator fun <E : Element> get(key: Key<E>): E? {
      return (E)this.$$delegate_0.get(var1);
   }

   public override fun minusKey(key: Key<*>): CoroutineContext {
      return this.$$delegate_0.minusKey(var1);
   }

   public override operator fun plus(context: CoroutineContext): CoroutineContext {
      return this.$$delegate_0.plus(var1);
   }
}
