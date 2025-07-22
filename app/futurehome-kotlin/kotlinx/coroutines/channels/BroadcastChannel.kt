package kotlinx.coroutines.channels

import java.util.concurrent.CancellationException

@Deprecated(level = DeprecationLevel.WARNING, message = "BroadcastChannel is deprecated in the favour of SharedFlow and is no longer supported")
public interface BroadcastChannel<E> : SendChannel<E> {
   public abstract fun cancel(cause: CancellationException? = ...) {
   }

   public abstract fun openSubscription(): ReceiveChannel<Any> {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated in the favour of 'trySend' method", replaceWith = @ReplaceWith(expression = "trySend(element).isSuccess", imports = []))
      @JvmStatic
      fun <E> offer(var0: BroadcastChannel<E>, var1: E): Boolean {
         return SendChannel.DefaultImpls.offer(var0, (E)var1);
      }
   }
}
