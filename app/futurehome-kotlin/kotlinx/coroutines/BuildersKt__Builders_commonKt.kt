package kotlinx.coroutines

import kotlin.contracts.InvocationKind
import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.DebugProbesKt
import kotlinx.coroutines.internal.ScopeCoroutine
import kotlinx.coroutines.internal.ThreadContextKt
import kotlinx.coroutines.intrinsics.CancellableKt
import kotlinx.coroutines.intrinsics.UndispatchedKt

@JvmSynthetic
internal class BuildersKt__Builders_commonKt {
   private const val RESUMED: Int = 2
   private const val SUSPENDED: Int = 1
   private const val UNDECIDED: Int = 0

   @JvmStatic
   public fun <T> CoroutineScope.async(
      context: CoroutineContext = EmptyCoroutineContext.INSTANCE as CoroutineContext,
      start: CoroutineStart = CoroutineStart.DEFAULT,
      block: (CoroutineScope, Continuation<T>) -> Any?
   ): Deferred<T> {
      val var4: CoroutineContext = CoroutineContextKt.newCoroutineContext(var0, var1);
      val var5: DeferredCoroutine;
      if (var2.isLazy()) {
         var5 = new LazyDeferredCoroutine(var4, var3);
      } else {
         var5 = new DeferredCoroutine(var4, true);
      }

      var5.start(var2, var5, var3);
      return var5;
   }

   @JvmStatic
   public suspend inline operator fun <T> CoroutineDispatcher.invoke(noinline block: (CoroutineScope, Continuation<T>) -> Any?): T {
      return BuildersKt.withContext(var0, var1, var2);
   }

   @JvmStatic
   fun <T> `invoke$$forInline`(var0: CoroutineDispatcher, var1: (CoroutineScope?, Continuation<? super T>?) -> Any, var2: Continuation<? super T>): Any {
      return BuildersKt.withContext(var0, var1, var2);
   }

   @JvmStatic
   public fun CoroutineScope.launch(
      context: CoroutineContext = EmptyCoroutineContext.INSTANCE as CoroutineContext,
      start: CoroutineStart = CoroutineStart.DEFAULT,
      block: (CoroutineScope, Continuation<Unit>) -> Any?
   ): Job {
      val var4: CoroutineContext = CoroutineContextKt.newCoroutineContext(var0, var1);
      val var5: StandaloneCoroutine;
      if (var2.isLazy()) {
         var5 = new LazyStandaloneCoroutine(var4, var3);
      } else {
         var5 = new StandaloneCoroutine(var4, true);
      }

      var5.start(var2, var5, var3);
      return var5;
   }

   @JvmStatic
   public suspend fun <T> withContext(context: CoroutineContext, block: (CoroutineScope, Continuation<T>) -> Any?): T {
      contract {
         callsInPlace(block, InvocationKind.EXACTLY_ONCE)
      }

      label25: {
         var var3: CoroutineContext = var2.getContext();
         var0 = CoroutineContextKt.newCoroutineContext(var3, var0);
         JobKt.ensureActive(var0);
         var var9: Any;
         if (var0 === var3) {
            var9 = new ScopeCoroutine(var0, var2);
            var9 = (UndispatchedCoroutine)UndispatchedKt.startUndispatchedOrReturn((ScopeCoroutine)var9, var9, var1);
         } else if (var0.get(ContinuationInterceptor.Key) == var3.get(ContinuationInterceptor.Key)) {
            var9 = new UndispatchedCoroutine(var0, var2);
            var3 = var9.getContext();
            val var4: Any = ThreadContextKt.updateThreadContext(var3, null);

            try {
               var9 = (UndispatchedCoroutine)UndispatchedKt.startUndispatchedOrReturn(var9, var9, var1);
            } catch (var5: java.lang.Throwable) {
               ThreadContextKt.restoreThreadContext(var3, var4);
            }

            ThreadContextKt.restoreThreadContext(var3, var4);
         } else {
            val var11: DispatchedCoroutine = new DispatchedCoroutine(var0, var2);
            CancellableKt.startCoroutineCancellable$default(var1, var11, var11, null, 4, null);
            var9 = (UndispatchedCoroutine)var11.getResult$kotlinx_coroutines_core();
         }

         if (var9 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(var2);
         }

         return var9;
      }
   }
}
