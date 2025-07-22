package kotlinx.coroutines.channels

import java.util.concurrent.CancellationException
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineExceptionHandlerKt
import kotlinx.coroutines.DebugStringsKt
import kotlinx.coroutines.ExceptionsKt
import kotlinx.coroutines.Job

private open class ActorCoroutine<E>(parentContext: CoroutineContext, channel: Channel<Any>, active: Boolean) : ChannelCoroutine(var1, var2, false, var3),
   ActorScope<E> {
   init {
      this.initParentJob(var1.get(Job.Key));
   }

   protected override fun handleJobException(exception: Throwable): Boolean {
      CoroutineExceptionHandlerKt.handleCoroutineException(this.getContext(), var1);
      return true;
   }

   protected override fun onCancelling(cause: Throwable?) {
      val var4: Channel = this.get_channel();
      var var2: CancellationException = null;
      var var3: CancellationException = null;
      if (var1 != null) {
         if (var1 is CancellationException) {
            var3 = var1 as CancellationException;
         }

         var2 = var3;
         if (var3 == null) {
            val var5: StringBuilder = new StringBuilder();
            var5.append(DebugStringsKt.getClassSimpleName(this));
            var5.append(" was cancelled");
            var2 = ExceptionsKt.CancellationException(var5.toString(), var1);
         }
      }

      var4.cancel(var2);
   }
}
