package kotlinx.coroutines

import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.DebugProbesKt
import kotlinx.coroutines.internal.DispatchedContinuation

public fun CancellableContinuation<*>.disposeOnCancellation(handle: DisposableHandle) {
   var0.invokeOnCancellation(new DisposeOnCancel(var1));
}

internal fun <T> getOrCreateCancellableContinuation(delegate: Continuation<T>): CancellableContinuationImpl<T> {
   if (var0 !is DispatchedContinuation) {
      return new CancellableContinuationImpl(var0, 1);
   } else {
      var var1: CancellableContinuationImpl = (var0 as DispatchedContinuation).claimReusableCancellableContinuation$kotlinx_coroutines_core();
      if (var1 != null) {
         if (!var1.resetStateReusable()) {
            var1 = null;
         }

         if (var1 != null) {
            return var1;
         }
      }

      return new CancellableContinuationImpl(var0, 2);
   }
}

public suspend inline fun <T> suspendCancellableCoroutine(crossinline block: (CancellableContinuation<T>) -> Unit): T {
   val var2: CancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(var1), 1);
   var2.initCancellability();
   var0.invoke(var2);
   val var3: Any = var2.getResult();
   if (var3 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      DebugProbesKt.probeCoroutineSuspended(var1);
   }

   return var3;
}

fun <T> `suspendCancellableCoroutine$$forInline`(var0: (CancellableContinuation<? super T>?) -> Unit, var1: Continuation<? super T>): Any {
   val var2: Continuation = var1;
   val var4: CancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(var1), 1);
   var4.initCancellability();
   var0.invoke(var4);
   val var3: Any = var4.getResult();
   if (var3 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      DebugProbesKt.probeCoroutineSuspended(var1);
   }

   return var3;
}

internal suspend inline fun <T> suspendCancellableCoroutineReusable(crossinline block: (CancellableContinuationImpl<T>) -> Unit): T {
   label17: {
      val var2: CancellableContinuationImpl = getOrCreateCancellableContinuation(IntrinsicsKt.intercepted(var1));

      try {
         ;
      } catch (var3: java.lang.Throwable) {
         var2.releaseClaimedReusableContinuation$kotlinx_coroutines_core();
      }

      val var6: Any = var2.getResult();
      if (var6 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
         DebugProbesKt.probeCoroutineSuspended(var1);
      }

      return var6;
   }
}

// $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
// Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
fun <T> `suspendCancellableCoroutineReusable$$forInline`(var0: (CancellableContinuationImpl<? super T>?) -> Unit, var1: Continuation<? super T>): Any {
   label17: {
      val var2: Continuation = var1;
      val var7: CancellableContinuationImpl = getOrCreateCancellableContinuation(IntrinsicsKt.intercepted(var1));

      try {
         ;
      } catch (var3: java.lang.Throwable) {
         var7.releaseClaimedReusableContinuation$kotlinx_coroutines_core();
      }

      val var6: Any = var7.getResult();
      if (var6 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
         DebugProbesKt.probeCoroutineSuspended(var1);
      }

      return var6;
   }
}
