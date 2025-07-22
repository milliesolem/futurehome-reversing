package kotlinx.coroutines.time

import j..time.Duration
import j..time.temporal.ChronoUnit
import kotlin.contracts.InvocationKind
import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelayKt
import kotlinx.coroutines.TimeoutKt
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowKt
import kotlinx.coroutines.selects.OnTimeoutKt
import kotlinx.coroutines.selects.SelectBuilder

private fun Duration.coerceToMillis(): Long {
   if (var0.compareTo(Duration.ZERO) <= 0) {
      return 0L;
   } else if (var0.compareTo(ChronoUnit.MILLIS.getDuration()) <= 0) {
      return 1L;
   } else {
      val var1: Long;
      if (var0.getSeconds() < 9223372036854775L || var0.getSeconds() == 9223372036854775L && var0.getNano() < 807000000) {
         var1 = var0.toMillis();
      } else {
         var1 = java.lang.Long.MAX_VALUE;
      }

      return var1;
   }
}

public fun <T> Flow<Any>.debounce(timeout: Duration): Flow<Any> {
   return FlowKt.debounce(var0, coerceToMillis(var1));
}

public suspend fun delay(duration: Duration) {
   val var2: Any = DelayKt.delay(coerceToMillis(var0), var1);
   return if (var2 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var2 else Unit.INSTANCE;
}

public fun <R> SelectBuilder<Any>.onTimeout(duration: Duration, block: (Continuation<Any>) -> Any?) {
   OnTimeoutKt.onTimeout(var0, coerceToMillis(var1), var2);
}

public fun <T> Flow<Any>.sample(period: Duration): Flow<Any> {
   return FlowKt.sample(var0, coerceToMillis(var1));
}

public suspend fun <T> withTimeout(duration: Duration, block: (CoroutineScope, Continuation<Any>) -> Any?): Any {
   contract {
      callsInPlace(block, InvocationKind.EXACTLY_ONCE)
   }

   return TimeoutKt.withTimeout(coerceToMillis(var0), var1, var2);
}

public suspend fun <T> withTimeoutOrNull(duration: Duration, block: (CoroutineScope, Continuation<Any>) -> Any?): Any? {
   return TimeoutKt.withTimeoutOrNull(coerceToMillis(var0), var1, var2);
}
