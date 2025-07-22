package kotlinx.coroutines

import java.util.concurrent.CancellationException
import kotlin.coroutines.CoroutineContext

@Deprecated(level = DeprecationLevel.ERROR, message = "This is internal API and may be removed in the future releases")
public interface ParentJob : Job {
   public abstract fun getChildJobCancellationCause(): CancellationException {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun <R> fold(var0: ParentJob, var1: R, var2: (R?, CoroutineContext.Element?) -> R): R {
         return Job.DefaultImpls.fold(var0, (R)var1, var2);
      }

      @JvmStatic
      fun <E extends CoroutineContext.Element> get(var0: ParentJob, var1: CoroutineContextKey<E>): E {
         return Job.DefaultImpls.get(var0, var1);
      }

      @JvmStatic
      fun minusKey(var0: ParentJob, var1: CoroutineContextKey<?>): CoroutineContext {
         return Job.DefaultImpls.minusKey(var0, var1);
      }

      @JvmStatic
      fun plus(var0: ParentJob, var1: CoroutineContext): CoroutineContext {
         return Job.DefaultImpls.plus(var0, var1);
      }

      @Deprecated(level = DeprecationLevel.ERROR, message = "Operator '+' on two Job objects is meaningless. Job is a coroutine context element and `+` is a set-sum operator for coroutine contexts. The job to the right of `+` just replaces the job the left of `+`.")
      @JvmStatic
      fun plus(var0: ParentJob, var1: Job): Job {
         return Job.DefaultImpls.plus(var0, var1);
      }
   }
}
