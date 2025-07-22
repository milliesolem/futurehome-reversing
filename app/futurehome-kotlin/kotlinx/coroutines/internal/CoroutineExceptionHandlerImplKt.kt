package kotlinx.coroutines.internal

import java.util.ServiceLoader
import kotlinx.coroutines.CoroutineExceptionHandler

internal final val platformExceptionHandlers: Collection<CoroutineExceptionHandler> =
   SequencesKt.toList(SequencesKt.asSequence(ServiceLoader.load(CoroutineExceptionHandler.class, CoroutineExceptionHandler.class.getClassLoader()).iterator())) as java.util.Collection

internal fun ensurePlatformExceptionHandlerLoaded(callback: CoroutineExceptionHandler) {
   if (!platformExceptionHandlers.contains(var0)) {
      throw new IllegalStateException("Exception handler was not found via a ServiceLoader".toString());
   }
}

internal fun propagateExceptionFinalResort(exception: Throwable) {
   val var1: Thread = Thread.currentThread();
   var1.getUncaughtExceptionHandler().uncaughtException(var1, var0);
}
