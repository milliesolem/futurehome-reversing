package kotlinx.coroutines

import kotlin.contracts.InvocationKind
import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@JvmSynthetic
internal class BuildersKt__BuildersKt {
   @Throws(java/lang/InterruptedException::class)
   @JvmStatic
   public fun <T> runBlocking(context: CoroutineContext = EmptyCoroutineContext.INSTANCE as CoroutineContext, block: (CoroutineScope, Continuation<T>) -> Any?): T {
      contract {
         callsInPlace(block, InvocationKind.EXACTLY_ONCE)
      }

      val var5: Thread = Thread.currentThread();
      val var3: ContinuationInterceptor = var0.get(ContinuationInterceptor.Key);
      var var8: EventLoop;
      if (var3 == null) {
         var8 = ThreadLocalEventLoop.INSTANCE.getEventLoop$kotlinx_coroutines_core();
         var0 = CoroutineContextKt.newCoroutineContext(GlobalScope.INSTANCE, var0.plus(var8));
      } else {
         val var2: Boolean = var3 is EventLoop;
         var var4: EventLoop = null;
         val var9: EventLoop;
         if (var2) {
            var9 = var3 as EventLoop;
         } else {
            var9 = null;
         }

         label24: {
            if (var9 != null) {
               if (var9.shouldBeProcessedFromContext()) {
                  var4 = var9;
               }

               if (var4 != null) {
                  var8 = var4;
                  break label24;
               }
            }

            var8 = ThreadLocalEventLoop.INSTANCE.currentOrNull$kotlinx_coroutines_core();
         }

         var0 = CoroutineContextKt.newCoroutineContext(GlobalScope.INSTANCE, var0);
      }

      val var7: BlockingCoroutine = new BlockingCoroutine(var0, var5, var8);
      var7.start(CoroutineStart.DEFAULT, var7, var1);
      return (T)var7.joinBlocking();
   }
}
