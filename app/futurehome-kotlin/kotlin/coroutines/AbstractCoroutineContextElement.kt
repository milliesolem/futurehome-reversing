package kotlin.coroutines

import kotlin.coroutines.CoroutineContext.Key

public abstract class AbstractCoroutineContextElement : CoroutineContext.Element {
   public open val key: Key<*>

   open fun AbstractCoroutineContextElement(var1: CoroutineContextKey<?>) {
      this.key = var1;
   }

   override fun <R> fold(var1: R, var2: (R?, CoroutineContext.Element?) -> R): R {
      return CoroutineContext.Element.DefaultImpls.fold(this, (R)var1, var2);
   }

   override fun <E extends CoroutineContext.Element> get(var1: CoroutineContextKey<E>): E {
      return CoroutineContext.Element.DefaultImpls.get(this, var1);
   }

   override fun minusKey(var1: CoroutineContextKey<?>): CoroutineContext {
      return CoroutineContext.Element.DefaultImpls.minusKey(this, var1);
   }

   override fun plus(var1: CoroutineContext): CoroutineContext {
      return CoroutineContext.Element.DefaultImpls.plus(this, var1);
   }
}
