package kotlinx.coroutines.internal

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineExceptionHandlerKt

internal fun handleUncaughtCoroutineException(context: CoroutineContext, exception: Throwable) {
   for (CoroutineExceptionHandler var3 : CoroutineExceptionHandlerImplKt.getPlatformExceptionHandlers()) {
      try {
         try {
            var3.handleException((CoroutineContext)var0, var1);
         } catch (var4: ExceptionSuccessfullyProcessed) {
            var0 = var4;
            return;
         }
      } catch (var6: java.lang.Throwable) {
         CoroutineExceptionHandlerImplKt.propagateExceptionFinalResort(CoroutineExceptionHandlerKt.handlerException(var1, var6));
         continue;
      }
   }

   label21:
   try {
      ExceptionsKt.addSuppressed(var1, new DiagnosticCoroutineContextException((CoroutineContext)var0));
   } catch (var5: java.lang.Throwable) {
      ;
   }
}
