package kotlinx.coroutines

import java.util.concurrent.CancellationException
import java.util.concurrent.Future
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext

// $VF: Class flags could not be determined
internal class JobKt {
   @JvmStatic
   fun Job(var0: Job): CompletableJob {
      return JobKt__JobKt.Job(var0);
   }

   @JvmStatic
   fun cancel(var0: CoroutineContext, var1: CancellationException) {
      JobKt__JobKt.cancel(var0, var1);
   }

   @JvmStatic
   fun cancel(var0: Job, var1: java.lang.String, var2: java.lang.Throwable) {
      JobKt__JobKt.cancel(var0, var1, var2);
   }

   @JvmStatic
   fun cancelAndJoin(var0: Job, var1: Continuation<? super Unit>): Any {
      return JobKt__JobKt.cancelAndJoin(var0, var1);
   }

   @JvmStatic
   fun cancelChildren(var0: CoroutineContext, var1: CancellationException) {
      JobKt__JobKt.cancelChildren(var0, var1);
   }

   @JvmStatic
   fun cancelChildren(var0: Job, var1: CancellationException) {
      JobKt__JobKt.cancelChildren(var0, var1);
   }

   @JvmStatic
   fun cancelFutureOnCancellation(var0: CancellableContinuation<?>, var1: Future<?>) {
      JobKt__FutureKt.cancelFutureOnCancellation(var0, var1);
   }

   @JvmStatic
   fun cancelFutureOnCompletion(var0: Job, var1: Future<?>): DisposableHandle {
      return JobKt__FutureKt.cancelFutureOnCompletion(var0, var1);
   }

   @JvmStatic
   fun disposeOnCompletion(var0: Job, var1: DisposableHandle): DisposableHandle {
      return JobKt__JobKt.disposeOnCompletion(var0, var1);
   }

   @JvmStatic
   fun ensureActive(var0: CoroutineContext) {
      JobKt__JobKt.ensureActive(var0);
   }

   @JvmStatic
   fun ensureActive(var0: Job) {
      JobKt__JobKt.ensureActive(var0);
   }

   @JvmStatic
   fun getJob(var0: CoroutineContext): Job {
      return JobKt__JobKt.getJob(var0);
   }

   @JvmStatic
   fun isActive(var0: CoroutineContext): Boolean {
      return JobKt__JobKt.isActive(var0);
   }
}
