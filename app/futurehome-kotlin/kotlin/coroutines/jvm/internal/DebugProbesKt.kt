package kotlin.coroutines.jvm.internal

import kotlin.coroutines.Continuation

internal fun <T> probeCoroutineCreated(completion: Continuation<T>): Continuation<T> {
   return var0;
}

internal fun probeCoroutineResumed(frame: Continuation<*>) {
}

internal fun probeCoroutineSuspended(frame: Continuation<*>) {
}
