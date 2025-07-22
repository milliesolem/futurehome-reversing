package kotlinx.coroutines

import kotlin.coroutines.CoroutineContext
import kotlin.jvm.functions.Function2
import kotlinx.coroutines.internal.CoroutineExceptionHandlerImpl_commonKt

public inline fun CoroutineExceptionHandler(crossinline handler: (CoroutineContext, Throwable) -> Unit): CoroutineExceptionHandler {
   return new CoroutineExceptionHandler(var0, CoroutineExceptionHandler.Key) {
      final Function2<CoroutineContext, java.lang.Throwable, Unit> $handler;

      {
         super(var2);
         this.$handler = var1;
      }

      @Override
      public void handleException(CoroutineContext var1, java.lang.Throwable var2) {
         this.$handler.invoke(var1, var2);
      }
   };
}

public fun handleCoroutineException(context: CoroutineContext, exception: Throwable) {
   var var2: CoroutineExceptionHandler;
   try {
      var2 = var0.get(CoroutineExceptionHandler.Key);
   } catch (var4: java.lang.Throwable) {
      CoroutineExceptionHandlerImpl_commonKt.handleUncaughtCoroutineException(var0, handlerException(var1, var4));
      return;
   }

   if (var2 != null) {
      try {
         var2.handleException(var0, var1);
      } catch (var3: java.lang.Throwable) {
         CoroutineExceptionHandlerImpl_commonKt.handleUncaughtCoroutineException(var0, handlerException(var1, var3));
         return;
      }
   } else {
      CoroutineExceptionHandlerImpl_commonKt.handleUncaughtCoroutineException(var0, var1);
   }
}

internal fun handlerException(originalException: Throwable, thrownException: Throwable): Throwable {
   if (var0 === var1) {
      return var0;
   } else {
      var1 = new RuntimeException("Exception while trying to handle coroutine exception", var1);
      kotlin.ExceptionsKt.addSuppressed(var1, var0);
      return var1;
   }
}
