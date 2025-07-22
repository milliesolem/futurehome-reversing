package kotlinx.coroutines

import kotlin.coroutines.Continuation
import kotlin.coroutines.jvm.internal.CoroutineStackFrame
import kotlin.jvm.functions.Function1
import kotlinx.coroutines.internal.StackTraceRecoveryKt

internal fun <T> recoverResult(state: Any?, uCont: Continuation<T>): Result<T> {
   if (var0 is CompletedExceptionally) {
      val var2: Result.Companion = Result.Companion;
      val var6: java.lang.Throwable = (var0 as CompletedExceptionally).cause;
      var0 = (var0 as CompletedExceptionally).cause;
      if (DebugKt.getRECOVER_STACK_TRACES()) {
         if (var1 !is CoroutineStackFrame) {
            var0 = var6;
         } else {
            var0 = StackTraceRecoveryKt.access$recoverFromStackFrame(var6, var1 as CoroutineStackFrame);
         }
      }

      var0 = Result.constructor-impl(ResultKt.createFailure(var0));
   } else {
      val var5: Result.Companion = Result.Companion;
      var0 = Result.constructor-impl(var0);
   }

   return var0;
}

internal fun <T> Result<T>.toState(onCancellation: ((Throwable) -> Unit)? = ...): Any? {
   var var2: java.lang.Throwable = Result.exceptionOrNull-impl(var0);
   if (var2 == null) {
      var2 = (java.lang.Throwable)var0;
      if (var1 != null) {
         var2 = new CompletedWithCancellation(var0, var1);
      }
   } else {
      var2 = new CompletedExceptionally(var2, false, 2, null);
   }

   return var2;
}

internal fun <T> Result<T>.toState(caller: CancellableContinuation<*>): Any? {
   val var2: java.lang.Throwable = Result.exceptionOrNull-impl(var0);
   if (var2 != null) {
      var0 = var2;
      if (DebugKt.getRECOVER_STACK_TRACES()) {
         val var4: Continuation = var1;
         if (var1 !is CoroutineStackFrame) {
            var0 = var2;
         } else {
            var0 = StackTraceRecoveryKt.access$recoverFromStackFrame(var2, var4 as CoroutineStackFrame);
         }
      }

      var0 = new CompletedExceptionally(var0, false, 2, null);
   }

   return var0;
}

@JvmSynthetic
fun `toState$default`(var0: Any, var1: Function1, var2: Int, var3: Any): Any {
   if ((var2 and 1) != 0) {
      var1 = null;
   }

   return toState(var0, var1);
}
