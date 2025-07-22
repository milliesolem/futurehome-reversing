package kotlinx.coroutines.channels

import kotlinx.coroutines.internal.Symbol

private final val NO_ELEMENT: Symbol = new Symbol("NO_ELEMENT")

@Deprecated(level = DeprecationLevel.WARNING, message = "BroadcastChannel is deprecated in the favour of SharedFlow and StateFlow, and is no longer supported")
public fun <E> BroadcastChannel(capacity: Int): BroadcastChannel<E> {
   val var1: BroadcastChannel;
   if (var0 != -2) {
      if (var0 != -1) {
         if (var0 == 0) {
            throw new IllegalArgumentException("Unsupported 0 capacity for BroadcastChannel");
         }

         if (var0 == Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Unsupported UNLIMITED capacity for BroadcastChannel");
         }

         var1 = new BroadcastChannelImpl(var0);
      } else {
         var1 = new ConflatedBroadcastChannel();
      }
   } else {
      var1 = new BroadcastChannelImpl(Channel.Factory.getCHANNEL_DEFAULT_CAPACITY$kotlinx_coroutines_core());
   }

   return var1;
}

@JvmSynthetic
fun `access$getNO_ELEMENT$p`(): Symbol {
   return NO_ELEMENT;
}
