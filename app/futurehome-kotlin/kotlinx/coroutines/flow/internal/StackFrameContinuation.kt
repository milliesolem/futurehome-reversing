package kotlinx.coroutines.flow.internal

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.jvm.internal.CoroutineStackFrame

private class StackFrameContinuation<T>(uCont: Continuation<Any>, context: CoroutineContext) : Continuation<T>, CoroutineStackFrame {
   public open val callerFrame: CoroutineStackFrame?
      public open get() {
         val var2: CoroutineStackFrame;
         if (this.uCont is CoroutineStackFrame) {
            var2 = this.uCont as CoroutineStackFrame;
         } else {
            var2 = null;
         }

         return var2;
      }


   public open val context: CoroutineContext
   private final val uCont: Continuation<Any>

   init {
      this.uCont = var1;
      this.context = var2;
   }

   public override fun getStackTraceElement(): StackTraceElement? {
      return null;
   }

   public override fun resumeWith(result: Result<Any>) {
      this.uCont.resumeWith(var1);
   }
}
