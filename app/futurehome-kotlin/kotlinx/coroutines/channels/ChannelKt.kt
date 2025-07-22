package kotlinx.coroutines.channels

import kotlin.contracts.InvocationKind
import kotlin.jvm.functions.Function1

@Deprecated(level = DeprecationLevel.HIDDEN, message = "Since 1.4.0, binary compatibility with earlier versions")
@JvmSynthetic
public fun <E> Channel(capacity: Int = 0): Channel<E> {
   return Channel$default(var0, null, null, 6, null);
}

public fun <E> Channel(capacity: Int = 0, onBufferOverflow: BufferOverflow = BufferOverflow.SUSPEND, onUndeliveredElement: ((E) -> Unit)? = null): Channel<E> {
   val var4: Channel;
   if (var0 != -2) {
      if (var0 != -1) {
         if (var0 != 0) {
            if (var0 != Integer.MAX_VALUE) {
               val var3: BufferedChannel;
               if (var1 === BufferOverflow.SUSPEND) {
                  var3 = new BufferedChannel(var0, var2);
               } else {
                  var3 = new ConflatedBufferedChannel(var0, var1, var2);
               }

               var4 = var3;
            } else {
               var4 = new BufferedChannel(Integer.MAX_VALUE, var2);
            }
         } else {
            val var5: BufferedChannel;
            if (var1 === BufferOverflow.SUSPEND) {
               var5 = new BufferedChannel(0, var2);
            } else {
               var5 = new ConflatedBufferedChannel(1, var1, var2);
            }

            var4 = var5;
         }
      } else {
         if (var1 != BufferOverflow.SUSPEND) {
            throw new IllegalArgumentException("CONFLATED capacity cannot be used with non-default onBufferOverflow".toString());
         }

         var4 = new ConflatedBufferedChannel(1, BufferOverflow.DROP_OLDEST, var2);
      }
   } else {
      val var6: BufferedChannel;
      if (var1 === BufferOverflow.SUSPEND) {
         var6 = new BufferedChannel(Channel.Factory.getCHANNEL_DEFAULT_CAPACITY$kotlinx_coroutines_core(), var2);
      } else {
         var6 = new ConflatedBufferedChannel(1, var1, var2);
      }

      var4 = var6;
   }

   return var4;
}

@JvmSynthetic
fun `Channel$default`(var0: Int, var1: Int, var2: Any): Channel {
   if ((var1 and 1) != 0) {
      var0 = 0;
   }

   return Channel(var0);
}

@JvmSynthetic
fun `Channel$default`(var0: Int, var1: BufferOverflow, var2: Function1, var3: Int, var4: Any): Channel {
   if ((var3 and 1) != 0) {
      var0 = 0;
   }

   if ((var3 and 2) != 0) {
      var1 = BufferOverflow.SUSPEND;
   }

   if ((var3 and 4) != 0) {
      var2 = null;
   }

   return Channel(var0, var1, var2);
}

public inline fun <T> ChannelResult<T>.getOrElse(onFailure: (Throwable?) -> T): T {
   contract {
      callsInPlace(onFailure, InvocationKind.AT_MOST_ONCE)
   }

   var var2: Any = var0;
   if (var0 is ChannelResult.Failed) {
      var2 = var1.invoke(ChannelResult.exceptionOrNull-impl(var0));
   }

   return (T)var2;
}

public inline fun <T> ChannelResult<T>.onClosed(action: (Throwable?) -> Unit): ChannelResult<T> {
   contract {
      callsInPlace(action, InvocationKind.AT_MOST_ONCE)
   }

   if (var0 is ChannelResult.Closed) {
      var1.invoke(ChannelResult.exceptionOrNull-impl(var0));
   }

   return var0;
}

public inline fun <T> ChannelResult<T>.onFailure(action: (Throwable?) -> Unit): ChannelResult<T> {
   contract {
      callsInPlace(action, InvocationKind.AT_MOST_ONCE)
   }

   if (var0 is ChannelResult.Failed) {
      var1.invoke(ChannelResult.exceptionOrNull-impl(var0));
   }

   return var0;
}

public inline fun <T> ChannelResult<T>.onSuccess(action: (T) -> Unit): ChannelResult<T> {
   contract {
      callsInPlace(action, InvocationKind.AT_MOST_ONCE)
   }

   if (var0 !is ChannelResult.Failed) {
      var1.invoke(var0);
   }

   return var0;
}
