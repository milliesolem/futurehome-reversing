package kotlinx.coroutines

import java.util.concurrent.CancellationException
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.selects.SelectClause0

public interface Job : CoroutineContext.Element {
   public val children: Sequence<Job>
   public val isActive: Boolean
   public val isCancelled: Boolean
   public val isCompleted: Boolean
   public val onJoin: SelectClause0
   public val parent: Job?

   public abstract fun attachChild(child: ChildJob): ChildHandle {
   }

   public abstract fun cancel(cause: CancellationException? = ...) {
   }

   public abstract fun getCancellationException(): CancellationException {
   }

   public abstract fun invokeOnCompletion(handler: (Throwable?) -> Unit): DisposableHandle {
   }

   public abstract fun invokeOnCompletion(onCancelling: Boolean = ..., invokeImmediately: Boolean = ..., handler: (Throwable?) -> Unit): DisposableHandle {
   }

   public abstract suspend fun join() {
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Operator '+' on two Job objects is meaningless. Job is a coroutine context element and `+` is a set-sum operator for coroutine contexts. The job to the right of `+` just replaces the job the left of `+`.")
   public open operator fun plus(other: Job): Job {
   }

   public abstract fun start(): Boolean {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun <R> fold(var0: Job, var1: R, var2: (R?, CoroutineContext.Element?) -> R): R {
         return CoroutineContext.Element.DefaultImpls.fold(var0, (R)var1, var2);
      }

      @JvmStatic
      fun <E extends CoroutineContext.Element> get(var0: Job, var1: CoroutineContextKey<E>): E {
         return CoroutineContext.Element.DefaultImpls.get(var0, var1);
      }

      @JvmStatic
      fun minusKey(var0: Job, var1: CoroutineContextKey<?>): CoroutineContext {
         return CoroutineContext.Element.DefaultImpls.minusKey(var0, var1);
      }

      @JvmStatic
      fun plus(var0: Job, var1: CoroutineContext): CoroutineContext {
         return CoroutineContext.Element.DefaultImpls.plus(var0, var1);
      }

      @Deprecated(level = DeprecationLevel.ERROR, message = "Operator '+' on two Job objects is meaningless. Job is a coroutine context element and `+` is a set-sum operator for coroutine contexts. The job to the right of `+` just replaces the job the left of `+`.")
      @JvmStatic
      fun plus(var0: Job, var1: Job): Job {
         return var1;
      }
   }

   public companion object Key : CoroutineContext.Key<Job>
}
