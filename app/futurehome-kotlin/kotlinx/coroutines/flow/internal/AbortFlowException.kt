package kotlinx.coroutines.flow.internal

import java.util.concurrent.CancellationException
import kotlinx.coroutines.DebugKt
import kotlinx.coroutines.flow.FlowCollector

internal class AbortFlowException(owner: FlowCollector<*>) : CancellationException("Flow was aborted, no more elements needed") {
   public final val owner: FlowCollector<*>

   init {
      this.owner = var1;
   }

   public override fun fillInStackTrace(): Throwable {
      if (DebugKt.getDEBUG()) {
         return super.fillInStackTrace();
      } else {
         this.setStackTrace(new StackTraceElement[0]);
         return this;
      }
   }
}
