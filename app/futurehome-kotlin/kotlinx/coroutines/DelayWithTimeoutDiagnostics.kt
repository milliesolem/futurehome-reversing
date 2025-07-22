package kotlinx.coroutines

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.time.Duration

internal interface DelayWithTimeoutDiagnostics : Delay {
   public abstract fun timeoutMessage(timeout: Duration): String {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated without replacement as an internal method never intended for public use")
      @JvmStatic
      fun delay(var0: DelayWithTimeoutDiagnostics, var1: Long, var3: Continuation<? super Unit>): Any {
         val var4: Any = Delay.DefaultImpls.delay(var0, var1, var3);
         return if (var4 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var4 else Unit.INSTANCE;
      }

      @JvmStatic
      fun invokeOnTimeout(var0: DelayWithTimeoutDiagnostics, var1: Long, var3: Runnable, var4: CoroutineContext): DisposableHandle {
         return Delay.DefaultImpls.invokeOnTimeout(var0, var1, var3, var4);
      }
   }
}
