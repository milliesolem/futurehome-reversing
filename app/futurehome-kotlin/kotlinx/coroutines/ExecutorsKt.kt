package kotlinx.coroutines

import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService

@JvmSynthetic
fun `CloseableCoroutineDispatcher$annotations`() {
}

public fun CoroutineDispatcher.asExecutor(): Executor {
   val var1: ExecutorCoroutineDispatcher;
   if (var0 is ExecutorCoroutineDispatcher) {
      var1 = var0 as ExecutorCoroutineDispatcher;
   } else {
      var1 = null;
   }

   if (var1 != null) {
      val var2: Executor = var1.getExecutor();
      if (var2 != null) {
         return var2;
      }
   }

   return new DispatcherExecutor(var0);
}

public fun Executor.asCoroutineDispatcher(): CoroutineDispatcher {
   label20: {
      val var1: DispatcherExecutor;
      if (var0 is DispatcherExecutor) {
         var1 = var0 as DispatcherExecutor;
      } else {
         var1 = null;
      }

      return if (var1 != null && var1.dispatcher != null) var1.dispatcher else new ExecutorCoroutineDispatcherImpl(var0);
   }
}

public fun ExecutorService.asCoroutineDispatcher(): ExecutorCoroutineDispatcher {
   return new ExecutorCoroutineDispatcherImpl(var0);
}
