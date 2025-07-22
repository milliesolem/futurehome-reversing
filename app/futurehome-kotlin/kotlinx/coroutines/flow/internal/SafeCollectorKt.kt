package kotlinx.coroutines.flow.internal

import kotlin.coroutines.Continuation
import kotlin.jvm.functions.Function3
import kotlinx.coroutines.flow.FlowCollector

private final val emitFun: (FlowCollector<Any?>, Any?, Continuation<Unit>) -> Any?

@JvmSynthetic
fun `access$getEmitFun$p`(): Function3 {
   return emitFun;
}
