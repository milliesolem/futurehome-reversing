package kotlinx.coroutines

import kotlinx.coroutines.internal.Symbol

private final val CLOSED_EMPTY: Symbol = new Symbol("CLOSED_EMPTY")
private final val DISPOSED_TASK: Symbol = new Symbol("REMOVED_TASK")
private const val MAX_DELAY_NS: Long = 4611686018427387903L
private const val MAX_MS: Long = 9223372036854L
private const val MS_TO_NS: Long = 1000000L
private const val SCHEDULE_COMPLETED: Int = 1
private const val SCHEDULE_DISPOSED: Int = 2
private const val SCHEDULE_OK: Int = 0

@JvmSynthetic
fun `access$getCLOSED_EMPTY$p`(): Symbol {
   return CLOSED_EMPTY;
}

@JvmSynthetic
fun `access$getDISPOSED_TASK$p`(): Symbol {
   return DISPOSED_TASK;
}

internal fun delayNanosToMillis(timeNanos: Long): Long {
   return var0 / 1000000L;
}

internal fun delayToNanos(timeMillis: Long): Long {
   if (var0 <= 0L) {
      var0 = 0L;
   } else if (var0 >= 9223372036854L) {
      var0 = java.lang.Long.MAX_VALUE;
   } else {
      var0 = 1000000L * var0;
   }

   return var0;
}
