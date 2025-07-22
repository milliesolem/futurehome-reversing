package kotlinx.coroutines

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext

// $VF: Class flags could not be determined
internal class BuildersKt {
   @JvmStatic
   fun <T> async(var0: CoroutineScope, var1: CoroutineContext, var2: CoroutineStart, var3: (CoroutineScope?, Continuation<? super T>?) -> Any): Deferred<T> {
      return BuildersKt__Builders_commonKt.async(var0, var1, var2, var3);
   }

   @JvmStatic
   fun <T> invoke(var0: CoroutineDispatcher, var1: (CoroutineScope?, Continuation<? super T>?) -> Any, var2: Continuation<? super T>): Any {
      return BuildersKt__Builders_commonKt.invoke(var0, var1, var2);
   }

   @JvmStatic
   fun launch(var0: CoroutineScope, var1: CoroutineContext, var2: CoroutineStart, var3: (CoroutineScope?, Continuation<? super Unit>?) -> Any): Job {
      return BuildersKt__Builders_commonKt.launch(var0, var1, var2, var3);
   }

   @Throws(java/lang/InterruptedException::class)
   @JvmStatic
   fun <T> runBlocking(var0: CoroutineContext, var1: (CoroutineScope?, Continuation<? super T>?) -> Any): T {
      return BuildersKt__BuildersKt.runBlocking(var0, var1);
   }

   @JvmStatic
   fun <T> withContext(var0: CoroutineContext, var1: (CoroutineScope?, Continuation<? super T>?) -> Any, var2: Continuation<? super T>): Any {
      return BuildersKt__Builders_commonKt.withContext(var0, var1, var2);
   }
}
