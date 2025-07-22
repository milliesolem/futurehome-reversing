package kotlinx.coroutines.flow

import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.internal.ChannelFlowOperatorImpl
import kotlinx.coroutines.flow.internal.FusibleFlow

@JvmSynthetic
internal class FlowKt__ContextKt {
   @JvmStatic
   public fun <T> Flow<T>.buffer(capacity: Int = -2, onBufferOverflow: BufferOverflow = BufferOverflow.SUSPEND): Flow<T> {
      if (var1 < 0 && var1 != -2 && var1 != -1) {
         val var4: StringBuilder = new StringBuilder("Buffer size should be non-negative, BUFFERED, or CONFLATED, but was ");
         var4.append(var1);
         throw new IllegalArgumentException(var4.toString().toString());
      } else if (var1 == -1 && var2 != BufferOverflow.SUSPEND) {
         throw new IllegalArgumentException("CONFLATED capacity cannot be used with non-default onBufferOverflow".toString());
      } else {
         if (var1 == -1) {
            var2 = BufferOverflow.DROP_OLDEST;
            var1 = 0;
         }

         if (var0 is FusibleFlow) {
            var0 = FusibleFlow.DefaultImpls.fuse$default(var0 as FusibleFlow, null, var1, var2, 1, null);
         } else {
            var0 = new ChannelFlowOperatorImpl(var0, null, var1, var2, 2, null);
         }

         return var0;
      }
   }

   @JvmStatic
   public fun <T> Flow<T>.cancellable(): Flow<T> {
      if (var0 !is CancellableFlow) {
         var0 = new CancellableFlowImpl(var0);
      }

      return var0;
   }

   @JvmStatic
   private fun checkFlowContext(context: CoroutineContext) {
      if (var0.get(Job.Key) != null) {
         val var1: StringBuilder = new StringBuilder("Flow context cannot contain job in it. Had ");
         var1.append(var0);
         throw new IllegalArgumentException(var1.toString().toString());
      }
   }

   @JvmStatic
   public fun <T> Flow<T>.conflate(): Flow<T> {
      return FlowKt.buffer$default(var0, -1, null, 2, null);
   }

   @JvmStatic
   public fun <T> Flow<T>.flowOn(context: CoroutineContext): Flow<T> {
      checkFlowContext$FlowKt__ContextKt(var1);
      if (!(var1 == EmptyCoroutineContext.INSTANCE)) {
         if (var0 is FusibleFlow) {
            var0 = FusibleFlow.DefaultImpls.fuse$default(var0 as FusibleFlow, var1, 0, null, 6, null);
         } else {
            var0 = new ChannelFlowOperatorImpl(var0, var1, 0, null, 12, null);
         }
      }

      return var0;
   }
}
