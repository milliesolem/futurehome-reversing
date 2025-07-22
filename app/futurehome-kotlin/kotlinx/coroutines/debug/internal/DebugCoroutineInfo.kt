package kotlinx.coroutines.debug.internal

import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.jvm.internal.CoroutineStackFrame

internal class DebugCoroutineInfo internal constructor(source: DebugCoroutineInfoImpl, context: CoroutineContext) {
   public final val context: CoroutineContext
   internal final val creationStackBottom: CoroutineStackFrame?
   public final val creationStackTrace: List<StackTraceElement>
   public final val lastObservedFrame: CoroutineStackFrame?

   public final val lastObservedStackTrace: List<StackTraceElement>
      public final get() {
         return this.lastObservedStackTrace;
      }


   public final val lastObservedThread: Thread?
   public final val sequenceNumber: Long
   public final val state: String

   init {
      this.context = var2;
      this.creationStackBottom = var1.getCreationStackBottom$kotlinx_coroutines_core();
      this.sequenceNumber = var1.sequenceNumber;
      this.creationStackTrace = var1.getCreationStackTrace();
      this.state = var1.getState$kotlinx_coroutines_core();
      this.lastObservedThread = var1.lastObservedThread;
      this.lastObservedFrame = var1.getLastObservedFrame$kotlinx_coroutines_core();
      this.lastObservedStackTrace = var1.lastObservedStackTrace$kotlinx_coroutines_core();
   }
}
