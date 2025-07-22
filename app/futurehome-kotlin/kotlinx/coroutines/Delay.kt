package kotlinx.coroutines

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.DebugProbesKt

public interface Delay {
   @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated without replacement as an internal method never intended for public use")
   public open suspend fun delay(time: Long) {
   }

   public open fun invokeOnTimeout(timeMillis: Long, block: Runnable, context: CoroutineContext): DisposableHandle {
   }

   public abstract fun scheduleResumeAfterDelay(timeMillis: Long, continuation: CancellableContinuation<Unit>) {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated without replacement as an internal method never intended for public use")
      @JvmStatic
      fun delay(var0: Delay, var1: Long, var3: Continuation<? super Unit>): Any {
         if (var1 <= 0L) {
            return Unit.INSTANCE;
         } else {
            val var4: CancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(var3), 1);
            var4.initCancellability();
            var0.scheduleResumeAfterDelay(var1, var4);
            val var5: Any = var4.getResult();
            if (var5 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
               DebugProbesKt.probeCoroutineSuspended(var3);
            }

            return if (var5 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var5 else Unit.INSTANCE;
         }
      }

      @JvmStatic
      fun invokeOnTimeout(var0: Delay, var1: Long, var3: Runnable, var4: CoroutineContext): DisposableHandle {
         return DefaultExecutorKt.getDefaultDelay().invokeOnTimeout(var1, var3, var4);
      }
   }
}
