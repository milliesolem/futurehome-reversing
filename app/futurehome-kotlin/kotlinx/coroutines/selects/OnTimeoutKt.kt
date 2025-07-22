package kotlinx.coroutines.selects

import kotlin.coroutines.Continuation
import kotlin.time.Duration
import kotlinx.coroutines.DelayKt

public fun <R> SelectBuilder<R>.onTimeout(timeMillis: Long, block: (Continuation<R>) -> Any?) {
   var0.invoke(new OnTimeout(var1).getSelectClause(), var3);
}

public fun <R> SelectBuilder<R>.onTimeout(timeout: Duration, block: (Continuation<R>) -> Any?) {
   onTimeout(var0, DelayKt.toDelayMillis-LRDsOJo(var1), var3);
}
