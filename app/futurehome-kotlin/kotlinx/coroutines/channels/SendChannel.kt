package kotlinx.coroutines.channels

import kotlinx.coroutines.internal.StackTraceRecoveryKt
import kotlinx.coroutines.selects.SelectClause2

public interface SendChannel<E> {
   public val isClosedForSend: Boolean
   public val onSend: SelectClause2<Any, SendChannel<Any>>

   public abstract fun close(cause: Throwable? = ...): Boolean {
   }

   public abstract fun invokeOnClose(handler: (Throwable?) -> Unit) {
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated in the favour of 'trySend' method", replaceWith = @ReplaceWith(expression = "trySend(element).isSuccess", imports = []))
   public open fun offer(element: Any): Boolean {
   }

   public abstract suspend fun send(element: Any) {
   }

   public abstract fun trySend(element: Any): ChannelResult<Unit> {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated in the favour of 'trySend' method", replaceWith = @ReplaceWith(expression = "trySend(element).isSuccess", imports = []))
      @JvmStatic
      fun <E> offer(var0: SendChannel<? super E>, var1: E): Boolean {
         var var2: Any = var0.trySend-JP2dKIU(var1);
         if (ChannelResult.isSuccess-impl(var2)) {
            return true;
         } else {
            var2 = ChannelResult.exceptionOrNull-impl(var2);
            if (var2 == null) {
               return false;
            } else {
               throw StackTraceRecoveryKt.recoverStackTrace((E)var2);
            }
         }
      }
   }
}
