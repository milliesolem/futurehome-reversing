package kotlinx.coroutines.debug.internal

import kotlin.coroutines.jvm.internal.CoroutineStackFrame

internal class StackTraceFrame internal constructor(callerFrame: CoroutineStackFrame?, stackTraceElement: StackTraceElement) : CoroutineStackFrame {
   public open val callerFrame: CoroutineStackFrame?
   public final val stackTraceElement: StackTraceElement

   init {
      this.callerFrame = var1;
      this.stackTraceElement = var2;
   }

   public override fun getStackTraceElement(): StackTraceElement {
      return this.stackTraceElement;
   }
}
