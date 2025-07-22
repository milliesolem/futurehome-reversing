package kotlinx.coroutines

import kotlin.coroutines.CoroutineContext

public interface ThreadContextElement<S> : CoroutineContext.Element {
   public abstract fun restoreThreadContext(context: CoroutineContext, oldState: Any) {
   }

   public abstract fun updateThreadContext(context: CoroutineContext): Any {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun <S, R> fold(var0: ThreadContextElement<S>, var1: R, var2: (R?, CoroutineContext.Element?) -> R): R {
         return CoroutineContext.Element.DefaultImpls.fold(var0, (R)var1, var2);
      }

      @JvmStatic
      fun <S, E extends CoroutineContext.Element> get(var0: ThreadContextElement<S>, var1: CoroutineContextKey<E>): E {
         return CoroutineContext.Element.DefaultImpls.get(var0, var1);
      }

      @JvmStatic
      fun <S> minusKey(var0: ThreadContextElement<S>, var1: CoroutineContextKey<?>): CoroutineContext {
         return CoroutineContext.Element.DefaultImpls.minusKey(var0, var1);
      }

      @JvmStatic
      fun <S> plus(var0: ThreadContextElement<S>, var1: CoroutineContext): CoroutineContext {
         return CoroutineContext.Element.DefaultImpls.plus(var0, var1);
      }
   }
}
