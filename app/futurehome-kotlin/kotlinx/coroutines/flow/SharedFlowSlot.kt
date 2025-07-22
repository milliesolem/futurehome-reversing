package kotlinx.coroutines.flow

import kotlin.coroutines.Continuation
import kotlinx.coroutines.DebugKt
import kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot

internal class SharedFlowSlot : AbstractSharedFlowSlot<SharedFlowImpl<?>> {
   public final var cont: Continuation<Unit>?
      private set

   public final var index: Long = -1L
      private set

   public open fun allocateLocked(flow: SharedFlowImpl<*>): Boolean {
      if (this.index >= 0L) {
         return false;
      } else {
         this.index = var1.updateNewCollectorIndexLocked$kotlinx_coroutines_core();
         return true;
      }
   }

   public open fun freeLocked(flow: SharedFlowImpl<*>): Array<Continuation<Unit>?> {
      if (DebugKt.getASSERTIONS_ENABLED() && this.index < 0L) {
         throw new AssertionError();
      } else {
         val var2: Long = this.index;
         this.index = -1L;
         this.cont = null;
         return var1.updateCollectorIndexLocked$kotlinx_coroutines_core(var2);
      }
   }
}
