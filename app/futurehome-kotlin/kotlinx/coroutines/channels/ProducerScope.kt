package kotlinx.coroutines.channels

import kotlinx.coroutines.CoroutineScope

public interface ProducerScope<E> : CoroutineScope, SendChannel<E> {
   public val channel: SendChannel<Any>

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated in the favour of 'trySend' method", replaceWith = @ReplaceWith(expression = "trySend(element).isSuccess", imports = []))
      @JvmStatic
      fun <E> offer(var0: ProducerScope<? super E>, var1: E): Boolean {
         return SendChannel.DefaultImpls.offer(var0, (E)var1);
      }
   }
}
