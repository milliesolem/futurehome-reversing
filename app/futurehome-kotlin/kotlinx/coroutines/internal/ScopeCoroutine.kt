package kotlinx.coroutines.internal

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.CoroutineStackFrame
import kotlinx.coroutines.AbstractCoroutine
import kotlinx.coroutines.CompletionStateKt

internal open class ScopeCoroutine<T>(context: CoroutineContext, uCont: Continuation<Any>) : AbstractCoroutine(var1, true, true), CoroutineStackFrame {
   public final val callerFrame: CoroutineStackFrame?
      public final get() {
         val var2: CoroutineStackFrame;
         if (this.uCont is CoroutineStackFrame) {
            var2 = this.uCont as CoroutineStackFrame;
         } else {
            var2 = null;
         }

         return var2;
      }


   protected final val isScopedCoroutine: Boolean
      protected final get() {
         return true;
      }


   public final val uCont: Continuation<Any>

   init {
      this.uCont = var2;
   }

   protected override fun afterCompletion(state: Any?) {
      DispatchedContinuationKt.resumeCancellableWith$default(
         IntrinsicsKt.intercepted(this.uCont), CompletionStateKt.recoverResult(var1, this.uCont), null, 2, null
      );
   }

   protected override fun afterResume(state: Any?) {
      this.uCont.resumeWith(CompletionStateKt.recoverResult(var1, this.uCont));
   }

   public override fun getStackTraceElement(): StackTraceElement? {
      return null;
   }
}
