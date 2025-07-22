package kotlin.coroutines.jvm.internal

public interface CoroutineStackFrame {
   public val callerFrame: CoroutineStackFrame?

   public abstract fun getStackTraceElement(): StackTraceElement? {
   }
}
