package kotlinx.coroutines.channels

import kotlin.coroutines.Continuation
import kotlinx.coroutines.internal.SystemPropsKt
import kotlinx.coroutines.selects.SelectClause1

public interface Channel<E> : SendChannel<E>, ReceiveChannel<E> {
   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun <E> getOnReceiveOrNull(var0: Channel<E>): SelectClause1<E> {
         return ReceiveChannel.DefaultImpls.getOnReceiveOrNull(var0);
      }

      @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated in the favour of 'trySend' method", replaceWith = @ReplaceWith(expression = "trySend(element).isSuccess", imports = []))
      @JvmStatic
      fun <E> offer(var0: Channel<E>, var1: E): Boolean {
         return SendChannel.DefaultImpls.offer(var0, (E)var1);
      }

      @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated in the favour of 'tryReceive'. Please note that the provided replacement does not rethrow channel's close cause as 'poll' did, for the precise replacement please refer to the 'poll' documentation", replaceWith = @ReplaceWith(expression = "tryReceive().getOrNull()", imports = []))
      @JvmStatic
      fun <E> poll(var0: Channel<E>): E {
         return ReceiveChannel.DefaultImpls.poll(var0);
      }

      @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated in favor of 'receiveCatching'. Please note that the provided replacement does not rethrow channel's close cause as 'receiveOrNull' did, for the detailed replacement please refer to the 'receiveOrNull' documentation", replaceWith = @ReplaceWith(expression = "receiveCatching().getOrNull()", imports = []))
      @JvmStatic
      fun <E> receiveOrNull(var0: Channel<E>, var1: Continuation<? super E>): Any {
         return ReceiveChannel.DefaultImpls.receiveOrNull(var0, var1);
      }
   }

   public companion object Factory {
      public const val BUFFERED: Int = -2
      internal final val CHANNEL_DEFAULT_CAPACITY: Int = SystemPropsKt.systemProp("kotlinx.coroutines.channels.defaultBuffer", 64, 1, 2147483646)
      public const val CONFLATED: Int = -1
      public const val DEFAULT_BUFFER_PROPERTY_NAME: String = "kotlinx.coroutines.channels.defaultBuffer"
      internal const val OPTIONAL_CHANNEL: Int = -3
      public const val RENDEZVOUS: Int = 0
      public const val UNLIMITED: Int = Integer.MAX_VALUE
   }
}
