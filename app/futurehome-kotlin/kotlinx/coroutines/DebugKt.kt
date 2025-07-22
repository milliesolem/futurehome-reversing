package kotlinx.coroutines

import java.util.concurrent.atomic.AtomicLong

internal final val ASSERTIONS_ENABLED: Boolean
internal final val COROUTINE_ID: AtomicLong
internal final val DEBUG: Boolean
public const val DEBUG_PROPERTY_NAME: String = "kotlinx.coroutines.debug"
public const val DEBUG_PROPERTY_VALUE_AUTO: String = "auto"
public const val DEBUG_PROPERTY_VALUE_OFF: String = "off"
public const val DEBUG_PROPERTY_VALUE_ON: String = "on"
internal final val RECOVER_STACK_TRACES: Boolean
internal const val STACKTRACE_RECOVERY_PROPERTY_NAME: String = "kotlinx.coroutines.stacktrace.recovery"

internal inline fun assert(value: () -> Boolean) {
   if (getASSERTIONS_ENABLED() && !var0.invoke() as java.lang.Boolean) {
      throw new AssertionError();
   }
}

internal fun resetCoroutineId() {
   COROUTINE_ID.set(0L);
}
