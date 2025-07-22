package kotlinx.coroutines

import kotlin.coroutines.CoroutineContext

public interface CoroutineExceptionHandler : CoroutineContext.Element {
   public abstract fun handleException(context: CoroutineContext, exception: Throwable) {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun <R> fold(var0: CoroutineExceptionHandler, var1: R, var2: (R?, CoroutineContext.Element?) -> R): R {
         return CoroutineContext.Element.DefaultImpls.fold(var0, (R)var1, var2);
      }

      @JvmStatic
      fun <E extends CoroutineContext.Element> get(var0: CoroutineExceptionHandler, var1: CoroutineContextKey<E>): E {
         return CoroutineContext.Element.DefaultImpls.get(var0, var1);
      }

      @JvmStatic
      fun minusKey(var0: CoroutineExceptionHandler, var1: CoroutineContextKey<?>): CoroutineContext {
         return CoroutineContext.Element.DefaultImpls.minusKey(var0, var1);
      }

      @JvmStatic
      fun plus(var0: CoroutineExceptionHandler, var1: CoroutineContext): CoroutineContext {
         return CoroutineContext.Element.DefaultImpls.plus(var0, var1);
      }
   }

   public companion object Key : CoroutineContext.Key<CoroutineExceptionHandler>
}
