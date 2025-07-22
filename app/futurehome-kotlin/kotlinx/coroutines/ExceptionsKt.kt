package kotlinx.coroutines

import java.util.concurrent.CancellationException

public fun CancellationException(message: String?, cause: Throwable?): CancellationException {
   val var2: CancellationException = new CancellationException(var0);
   var2.initCause(var1);
   return var2;
}

internal inline fun Throwable.addSuppressedThrowable(other: Throwable) {
   kotlin.ExceptionsKt.addSuppressed(var0, var1);
}
