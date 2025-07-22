package kotlinx.coroutines

import java.util.concurrent.Future

@JvmSynthetic
internal class JobKt__FutureKt {
   @JvmStatic
   public fun CancellableContinuation<*>.cancelFutureOnCancellation(future: Future<*>) {
      var0.invokeOnCancellation(new CancelFutureOnCancel(var1));
   }

   @JvmStatic
   public fun Job.cancelFutureOnCompletion(future: Future<*>): DisposableHandle {
      return var0.invokeOnCompletion(new CancelFutureOnCompletion(var1));
   }
}
