package kotlinx.coroutines

import java.util.concurrent.CancellationException
import kotlin.contracts.InvocationKind
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.DebugProbesKt
import kotlinx.coroutines.internal.ContextScope
import kotlinx.coroutines.internal.ScopeCoroutine
import kotlinx.coroutines.intrinsics.UndispatchedKt

public final val isActive: Boolean
   public final get() {
      val var2: Job = var0.getCoroutineContext().get(Job.Key);
      val var1: Boolean;
      if (var2 != null) {
         var1 = var2.isActive();
      } else {
         var1 = true;
      }

      return var1;
   }


public fun CoroutineScope(context: CoroutineContext): CoroutineScope {
   if (var0.get(Job.Key) == null) {
      var0 = var0.plus(JobKt.Job$default(null, 1, null));
   }

   return new ContextScope(var0);
}

public fun MainScope(): CoroutineScope {
   return new ContextScope(SupervisorKt.SupervisorJob$default(null, 1, null).plus(Dispatchers.getMain()));
}

public fun CoroutineScope.cancel(message: String, cause: Throwable? = null) {
   cancel(var0, ExceptionsKt.CancellationException(var1, var2));
}

public fun CoroutineScope.cancel(cause: CancellationException? = null) {
   val var2: Job = var0.getCoroutineContext().get(Job.Key);
   if (var2 != null) {
      var2.cancel(var1);
   } else {
      val var3: StringBuilder = new StringBuilder("Scope cannot be cancelled because it does not have a job: ");
      var3.append(var0);
      throw new IllegalStateException(var3.toString().toString());
   }
}

@JvmSynthetic
fun `cancel$default`(var0: CoroutineScope, var1: java.lang.String, var2: java.lang.Throwable, var3: Int, var4: Any) {
   if ((var3 and 2) != 0) {
      var2 = null;
   }

   cancel(var0, var1, var2);
}

@JvmSynthetic
fun `cancel$default`(var0: CoroutineScope, var1: CancellationException, var2: Int, var3: Any) {
   if ((var2 and 1) != 0) {
      var1 = null;
   }

   cancel(var0, var1);
}

public suspend fun <R> coroutineScope(block: (CoroutineScope, Continuation<R>) -> Any?): R {
   contract {
      callsInPlace(block, InvocationKind.EXACTLY_ONCE)
   }

   val var2: ScopeCoroutine = new ScopeCoroutine(var1.getContext(), var1);
   val var3: Any = UndispatchedKt.startUndispatchedOrReturn(var2, var2, var0);
   if (var3 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      DebugProbesKt.probeCoroutineSuspended(var1);
   }

   return var3;
}

public suspend inline fun currentCoroutineContext(): CoroutineContext {
   return var0.getContext();
}

fun `currentCoroutineContext$$forInline`(var0: Continuation<? super CoroutineContext>): Any {
   throw new NullPointerException();
}

public fun CoroutineScope.ensureActive() {
   JobKt.ensureActive(var0.getCoroutineContext());
}

public operator fun CoroutineScope.plus(context: CoroutineContext): CoroutineScope {
   return new ContextScope(var0.getCoroutineContext().plus(var1));
}
