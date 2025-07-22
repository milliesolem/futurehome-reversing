package kotlinx.coroutines.channels

import kotlin.coroutines.Continuation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.selects.SelectClause1

public interface ActorScope<E> : CoroutineScope, ReceiveChannel<E> {
   public val channel: Channel<Any>

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun <E> getOnReceiveOrNull(var0: ActorScope<E>): SelectClause1<E> {
         return ReceiveChannel.DefaultImpls.getOnReceiveOrNull(var0);
      }

      @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated in the favour of 'tryReceive'. Please note that the provided replacement does not rethrow channel's close cause as 'poll' did, for the precise replacement please refer to the 'poll' documentation", replaceWith = @ReplaceWith(expression = "tryReceive().getOrNull()", imports = []))
      @JvmStatic
      fun <E> poll(var0: ActorScope<E>): E {
         return ReceiveChannel.DefaultImpls.poll(var0);
      }

      @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated in favor of 'receiveCatching'. Please note that the provided replacement does not rethrow channel's close cause as 'receiveOrNull' did, for the detailed replacement please refer to the 'receiveOrNull' documentation", replaceWith = @ReplaceWith(expression = "receiveCatching().getOrNull()", imports = []))
      @JvmStatic
      fun <E> receiveOrNull(var0: ActorScope<E>, var1: Continuation<? super E>): Any {
         return ReceiveChannel.DefaultImpls.receiveOrNull(var0, var1);
      }
   }
}
