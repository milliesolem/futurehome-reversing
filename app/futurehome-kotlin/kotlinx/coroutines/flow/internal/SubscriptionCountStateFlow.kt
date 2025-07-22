package kotlinx.coroutines.flow.internal

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.SharedFlowImpl
import kotlinx.coroutines.flow.StateFlow

private class SubscriptionCountStateFlow(initialValue: Int) : SharedFlowImpl(1, Integer.MAX_VALUE, BufferOverflow.DROP_OLDEST), StateFlow<Integer> {
   public open val value: Int
      public open get() {
         label13: {
            synchronized (this){} // $VF: monitorenter 

            try {
               val var1: Int = this.getLastReplayedLocked().intValue();
            } catch (var3: java.lang.Throwable) {
               // $VF: monitorexit
            }

            // $VF: monitorexit
         }
      }


   init {
      this.tryEmit(var1);
   }

   public fun increment(delta: Int): Boolean {
      label13: {
         synchronized (this){} // $VF: monitorenter 

         try {
            val var2: Boolean = this.tryEmit(this.getLastReplayedLocked().intValue() + var1);
         } catch (var4: java.lang.Throwable) {
            // $VF: monitorexit
         }

         // $VF: monitorexit
      }
   }
}
