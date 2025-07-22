package kotlinx.coroutines.flow

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.internal.ChannelFlowOperatorImpl
import kotlinx.coroutines.internal.Symbol

internal final val NO_VALUE: Symbol = new Symbol("NO_VALUE")

public fun <T> MutableSharedFlow(replay: Int = 0, extraBufferCapacity: Int = 0, onBufferOverflow: BufferOverflow = BufferOverflow.SUSPEND): MutableSharedFlow<
      T
   > {
   if (var0 >= 0) {
      if (var1 >= 0) {
         if (var0 <= 0 && var1 <= 0 && var2 != BufferOverflow.SUSPEND) {
            val var4: StringBuilder = new StringBuilder("replay or extraBufferCapacity must be positive with non-default onBufferOverflow strategy ");
            var4.append(var2);
            throw new IllegalArgumentException(var4.toString().toString());
         } else {
            val var3: Int = var1 + var0;
            var1 = var1 + var0;
            if (var3 < 0) {
               var1 = Integer.MAX_VALUE;
            }

            return new SharedFlowImpl(var0, var1, var2);
         }
      } else {
         val var7: StringBuilder = new StringBuilder("extraBufferCapacity cannot be negative, but was ");
         var7.append(var1);
         throw new IllegalArgumentException(var7.toString().toString());
      }
   } else {
      val var6: StringBuilder = new StringBuilder("replay cannot be negative, but was ");
      var6.append(var0);
      throw new IllegalArgumentException(var6.toString().toString());
   }
}

@JvmSynthetic
fun `MutableSharedFlow$default`(var0: Int, var1: Int, var2: BufferOverflow, var3: Int, var4: Any): MutableSharedFlow {
   if ((var3 and 1) != 0) {
      var0 = 0;
   }

   if ((var3 and 2) != 0) {
      var1 = 0;
   }

   if ((var3 and 4) != 0) {
      var2 = BufferOverflow.SUSPEND;
   }

   return MutableSharedFlow(var0, var1, var2);
}

@JvmSynthetic
fun `access$getBufferAt`(var0: Array<Any>, var1: Long): Any {
   return getBufferAt(var0, var1);
}

@JvmSynthetic
fun `access$setBufferAt`(var0: Array<Any>, var1: Long, var3: Any) {
   setBufferAt(var0, var1, var3);
}

internal fun <T> SharedFlow<T>.fuseSharedFlow(context: CoroutineContext, capacity: Int, onBufferOverflow: BufferOverflow): Flow<T> {
   return (Flow<T>)(if ((var2 == 0 || var2 == -3) && var3 === BufferOverflow.SUSPEND) var0 else new ChannelFlowOperatorImpl(var0, var1, var2, var3));
}

private fun Array<Any?>.getBufferAt(index: Long): Any? {
   return var0[var0.length - 1 and (int)var1];
}

private fun Array<Any?>.setBufferAt(index: Long, item: Any?) {
   var0[var0.length - 1 and (int)var1] = var3;
}
