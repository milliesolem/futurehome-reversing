package kotlinx.coroutines.flow

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.channels.BufferOverflow

private class SharingConfig<T>(upstream: Flow<Any>, extraBufferCapacity: Int, onBufferOverflow: BufferOverflow, context: CoroutineContext) {
   public final val context: CoroutineContext
   public final val extraBufferCapacity: Int
   public final val onBufferOverflow: BufferOverflow
   public final val upstream: Flow<Any>

   init {
      this.upstream = var1;
      this.extraBufferCapacity = var2;
      this.onBufferOverflow = var3;
      this.context = var4;
   }
}
