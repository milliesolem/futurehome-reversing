package kotlinx.coroutines

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.selects.SelectClause1

public interface Deferred<T> : Job {
   public val onAwait: SelectClause1<Any>

   public abstract suspend fun await(): Any {
   }

   public abstract fun getCompleted(): Any {
   }

   public abstract fun getCompletionExceptionOrNull(): Throwable? {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun <T, R> fold(var0: Deferred<? extends T>, var1: R, var2: (R?, CoroutineContext.Element?) -> R): R {
         return Job.DefaultImpls.fold(var0, (R)var1, var2);
      }

      @JvmStatic
      fun <T, E extends CoroutineContext.Element> get(var0: Deferred<? extends T>, var1: CoroutineContextKey<E>): E {
         return Job.DefaultImpls.get(var0, var1);
      }

      @JvmStatic
      fun <T> minusKey(var0: Deferred<? extends T>, var1: CoroutineContextKey<?>): CoroutineContext {
         return Job.DefaultImpls.minusKey(var0, var1);
      }

      @JvmStatic
      fun <T> plus(var0: Deferred<? extends T>, var1: CoroutineContext): CoroutineContext {
         return Job.DefaultImpls.plus(var0, var1);
      }

      @Deprecated(level = DeprecationLevel.ERROR, message = "Operator '+' on two Job objects is meaningless. Job is a coroutine context element and `+` is a set-sum operator for coroutine contexts. The job to the right of `+` just replaces the job the left of `+`.")
      @JvmStatic
      fun <T> plus(var0: Deferred<? extends T>, var1: Job): Job {
         return Job.DefaultImpls.plus(var0, var1);
      }
   }
}
