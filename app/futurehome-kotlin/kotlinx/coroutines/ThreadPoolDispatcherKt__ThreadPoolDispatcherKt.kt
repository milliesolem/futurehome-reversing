package kotlinx.coroutines

import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

@JvmSynthetic
internal class ThreadPoolDispatcherKt__ThreadPoolDispatcherKt {
   @JvmStatic
   public fun newFixedThreadPoolContext(nThreads: Int, name: String): ExecutorCoroutineDispatcher {
      if (var0 >= 1) {
         return ExecutorsKt.from(
            Executors.newScheduledThreadPool(
               var0, new ThreadPoolDispatcherKt__ThreadPoolDispatcherKt$$ExternalSyntheticLambda0(var0, var1, new AtomicInteger())
            )
         );
      } else {
         val var2: StringBuilder = new StringBuilder("Expected at least one thread, but ");
         var2.append(var0);
         var2.append(" specified");
         throw new IllegalArgumentException(var2.toString().toString());
      }
   }

   @JvmStatic
   fun `newFixedThreadPoolContext$lambda$1$ThreadPoolDispatcherKt__ThreadPoolDispatcherKt`(
      var0: Int, var1: java.lang.String, var2: AtomicInteger, var3: Runnable
   ): Thread {
      if (var0 != 1) {
         val var4: StringBuilder = new StringBuilder();
         var4.append(var1);
         var4.append('-');
         var4.append(var2.incrementAndGet());
         var1 = var4.toString();
      }

      val var5: Thread = new Thread(var3, var1);
      var5.setDaemon(true);
      return var5;
   }
}
