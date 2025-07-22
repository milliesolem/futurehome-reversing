package kotlinx.coroutines

import kotlin.contracts.InvocationKind
import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.DebugProbesKt
import kotlinx.coroutines.intrinsics.UndispatchedKt

public fun SupervisorJob(parent: Job? = null): CompletableJob {
   return new SupervisorJobImpl(var0);
}

@Deprecated(level = DeprecationLevel.HIDDEN, message = "Since 1.2.0, binary compatibility with versions <= 1.1.x")
@JvmSynthetic
public fun SupervisorJob0(parent: Job? = ...): Job {
   return SupervisorJob(var0);
}

@JvmSynthetic
fun `SupervisorJob$default`(var0: Job, var1: Int, var2: Any): CompletableJob {
   if ((var1 and 1) != 0) {
      var0 = null;
   }

   return SupervisorJob(var0);
}

@JvmSynthetic
fun `SupervisorJob$default`(var0: Job, var1: Int, var2: Any): Job {
   if ((var1 and 1) != 0) {
      var0 = null;
   }

   return SupervisorJob(var0);
}

public suspend fun <R> supervisorScope(block: (CoroutineScope, Continuation<R>) -> Any?): R {
   contract {
      callsInPlace(block, InvocationKind.EXACTLY_ONCE)
   }

   val var2: SupervisorCoroutine = new SupervisorCoroutine(var1.getContext(), var1);
   val var3: Any = UndispatchedKt.startUndispatchedOrReturn(var2, var2, var0);
   if (var3 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      DebugProbesKt.probeCoroutineSuspended(var1);
   }

   return var3;
}
