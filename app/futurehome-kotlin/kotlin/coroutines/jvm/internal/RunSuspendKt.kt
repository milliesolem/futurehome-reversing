package kotlin.coroutines.jvm.internal

import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationKt

internal fun runSuspend(block: (Continuation<Unit>) -> Any?) {
   val var1: RunSuspend = new RunSuspend();
   ContinuationKt.startCoroutine(var0, var1);
   var1.await();
}
