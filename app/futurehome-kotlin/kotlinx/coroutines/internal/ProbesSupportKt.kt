package kotlinx.coroutines.internal

import kotlin.coroutines.Continuation
import kotlin.coroutines.jvm.internal.DebugProbesKt

internal inline fun <T> probeCoroutineCreated(completion: Continuation<T>): Continuation<T> {
   return DebugProbesKt.probeCoroutineCreated(var0);
}
