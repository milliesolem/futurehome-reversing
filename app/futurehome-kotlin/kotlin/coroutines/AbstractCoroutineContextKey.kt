package kotlin.coroutines

import kotlin.coroutines.CoroutineContext.Element
import kotlin.coroutines.CoroutineContext.Key

public abstract class AbstractCoroutineContextKey<B extends CoroutineContext.Element, E extends B> : CoroutineContext.Key<E> {
   private final val safeCast: (Element) -> Any?
   private final val topmostKey: Key<*>

   open fun AbstractCoroutineContextKey(var1: CoroutineContextKey<B>, var2: (CoroutineContext.Element?) -> E) {
      this.safeCast = var2;
      var var3: CoroutineContext.Key = var1;
      if (var1 is AbstractCoroutineContextKey) {
         var3 = (var1 as AbstractCoroutineContextKey).topmostKey;
      }

      this.topmostKey = var3;
   }

   internal fun isSubKey(key: Key<*>): Boolean {
      val var2: Boolean;
      if (var1 != this && this.topmostKey != var1) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   internal fun tryCast(element: Element): Any? {
      return (E)(this.safeCast.invoke(var1) as CoroutineContext.Element);
   }
}
