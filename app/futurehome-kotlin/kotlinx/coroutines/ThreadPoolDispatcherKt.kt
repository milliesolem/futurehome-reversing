package kotlinx.coroutines

// $VF: Class flags could not be determined
internal class ThreadPoolDispatcherKt {
   @JvmStatic
   fun newFixedThreadPoolContext(var0: Int, var1: java.lang.String): ExecutorCoroutineDispatcher {
      return ThreadPoolDispatcherKt__ThreadPoolDispatcherKt.newFixedThreadPoolContext(var0, var1);
   }

   @JvmStatic
   fun newSingleThreadContext(var0: java.lang.String): ExecutorCoroutineDispatcher {
      return ThreadPoolDispatcherKt__MultithreadedDispatchers_commonKt.newSingleThreadContext(var0);
   }
}
