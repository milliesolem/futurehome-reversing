package kotlinx.coroutines.flow

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.internal.FusibleFlow

private class ReadonlyStateFlow<T>(flow: StateFlow<Any>, job: Job?) : StateFlow<T>, CancellableFlow<T>, FusibleFlow<T> {
   private final val job: Job?
   public open val replayCache: List<Any>
   public open val value: Any

   init {
      this.job = var2;
      this.$$delegate_0 = var1;
   }

   public override suspend fun collect(collector: FlowCollector<Any>): Nothing {
      return this.$$delegate_0.collect(var1, var2);
   }

   public override fun fuse(context: CoroutineContext, capacity: Int, onBufferOverflow: BufferOverflow): Flow<Any> {
      return StateFlowKt.fuseStateFlow(this, var1, var2, var3);
   }
}
