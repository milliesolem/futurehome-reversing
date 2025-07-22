package kotlinx.coroutines

import kotlin.coroutines.CoroutineContext

public interface CompletableDeferred<T> : Deferred<T> {
   public abstract fun complete(value: Any): Boolean {
   }

   public abstract fun completeExceptionally(exception: Throwable): Boolean {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun <T, R> fold(var0: CompletableDeferred<T>, var1: R, var2: (R?, CoroutineContext.Element?) -> R): R {
         return Deferred.DefaultImpls.fold(var0, (R)var1, var2);
      }

      @JvmStatic
      fun <T, E extends CoroutineContext.Element> get(var0: CompletableDeferred<T>, var1: CoroutineContextKey<E>): E {
         return Deferred.DefaultImpls.get(var0, var1);
      }

      @JvmStatic
      fun <T> minusKey(var0: CompletableDeferred<T>, var1: CoroutineContextKey<?>): CoroutineContext {
         return Deferred.DefaultImpls.minusKey(var0, var1);
      }

      @JvmStatic
      fun <T> plus(var0: CompletableDeferred<T>, var1: CoroutineContext): CoroutineContext {
         return Deferred.DefaultImpls.plus(var0, var1);
      }

      @Deprecated(level = DeprecationLevel.ERROR, message = "Operator '+' on two Job objects is meaningless. Job is a coroutine context element and `+` is a set-sum operator for coroutine contexts. The job to the right of `+` just replaces the job the left of `+`.")
      @JvmStatic
      fun <T> plus(var0: CompletableDeferred<T>, var1: Job): Job {
         return Deferred.DefaultImpls.plus(var0, var1);
      }
   }
}
