package kotlinx.coroutines

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.DebugProbesKt
import kotlinx.coroutines.internal.DispatchedContinuation
import kotlinx.coroutines.internal.DispatchedContinuationKt

public suspend fun yield() {
   val var2: CoroutineContext = var0.getContext();
   JobKt.ensureActive(var2);
   var var1: Continuation = IntrinsicsKt.intercepted(var0);
   val var4: DispatchedContinuation;
   if (var1 is DispatchedContinuation) {
      var4 = var1 as DispatchedContinuation;
   } else {
      var4 = null;
   }

   if (var4 == null) {
      var1 = Unit.INSTANCE;
   } else {
      label30: {
         if (var4.dispatcher.isDispatchNeeded(var2)) {
            var4.dispatchYield$kotlinx_coroutines_core(var2, Unit.INSTANCE);
         } else {
            val var3: YieldContext = new YieldContext();
            var4.dispatchYield$kotlinx_coroutines_core(var2.plus(var3), Unit.INSTANCE);
            if (var3.dispatcherWasUnconfined) {
               if (DispatchedContinuationKt.yieldUndispatched(var4)) {
                  var1 = (Continuation)IntrinsicsKt.getCOROUTINE_SUSPENDED();
               } else {
                  var1 = Unit.INSTANCE;
               }
               break label30;
            }
         }

         var1 = (Continuation)IntrinsicsKt.getCOROUTINE_SUSPENDED();
      }
   }

   if (var1 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      DebugProbesKt.probeCoroutineSuspended(var0);
   }

   return if (var1 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var1 else Unit.INSTANCE;
}
