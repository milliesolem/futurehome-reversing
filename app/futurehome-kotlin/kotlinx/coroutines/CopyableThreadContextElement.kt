package kotlinx.coroutines

import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.CoroutineContext.Element

public interface CopyableThreadContextElement<S> : ThreadContextElement<S> {
   public abstract fun copyForChild(): CopyableThreadContextElement<Any> {
   }

   public abstract fun mergeForChild(overwritingElement: Element): CoroutineContext {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun <S, R> fold(var0: CopyableThreadContextElement<S>, var1: R, var2: (R?, CoroutineContext.Element?) -> R): R {
         return ThreadContextElement.DefaultImpls.fold(var0, (R)var1, var2);
      }

      @JvmStatic
      fun <S, E extends CoroutineContext.Element> get(var0: CopyableThreadContextElement<S>, var1: CoroutineContextKey<E>): E {
         return ThreadContextElement.DefaultImpls.get(var0, var1);
      }

      @JvmStatic
      fun <S> minusKey(var0: CopyableThreadContextElement<S>, var1: CoroutineContextKey<?>): CoroutineContext {
         return ThreadContextElement.DefaultImpls.minusKey(var0, var1);
      }

      @JvmStatic
      fun <S> plus(var0: CopyableThreadContextElement<S>, var1: CoroutineContext): CoroutineContext {
         return ThreadContextElement.DefaultImpls.plus(var0, var1);
      }
   }
}
