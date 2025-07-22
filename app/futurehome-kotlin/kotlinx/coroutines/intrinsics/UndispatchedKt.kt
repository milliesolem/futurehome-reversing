package kotlinx.coroutines.intrinsics

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.CoroutineStackFrame
import kotlin.coroutines.jvm.internal.DebugProbesKt
import kotlin.jvm.functions.Function1
import kotlin.jvm.functions.Function2
import kotlin.jvm.internal.TypeIntrinsics
import kotlinx.coroutines.CompletedExceptionally
import kotlinx.coroutines.DebugKt
import kotlinx.coroutines.JobSupportKt
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.internal.ScopeCoroutine
import kotlinx.coroutines.internal.StackTraceRecoveryKt
import kotlinx.coroutines.internal.ThreadContextKt

internal fun <T> ((Continuation<T>) -> Any?).startCoroutineUndispatched(completion: Continuation<T>) {
   label108: {
      val var2: Continuation = DebugProbesKt.probeCoroutineCreated(var1);

      var var3: Any;
      try {
         var26 = var1.getContext();
         var3 = ThreadContextKt.updateThreadContext(var26, null);
      } catch (var7: java.lang.Throwable) {
         val var22: Result.Companion = Result.Companion;
         var2.resumeWith(Result.constructor-impl(ResultKt.createFailure(var7)));
         return;
      }

      try {
         var24 = (TypeIntrinsics.beforeCheckcastToFunctionOfArity(var0, 1) as Function1).invoke(var2);
      } catch (var6: java.lang.Throwable) {
         try {
            ThreadContextKt.restoreThreadContext(var26, var3);
         } catch (var5: java.lang.Throwable) {
            val var23: Result.Companion = Result.Companion;
            var2.resumeWith(Result.constructor-impl(ResultKt.createFailure(var5)));
            return;
         }
      }

      try {
         ThreadContextKt.restoreThreadContext(var26, var3);
      } catch (var4: java.lang.Throwable) {
         val var25: Result.Companion = Result.Companion;
         var2.resumeWith(Result.constructor-impl(ResultKt.createFailure(var4)));
         return;
      }

      if (var24 != IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
         val var27: Result.Companion = Result.Companion;
         var2.resumeWith(Result.constructor-impl(var24));
      }
   }
}

internal fun <R, T> ((R, Continuation<T>) -> Any?).startCoroutineUndispatched(receiver: R, completion: Continuation<T>) {
   label108: {
      val var3: Continuation = DebugProbesKt.probeCoroutineCreated(var2);

      var var4: CoroutineContext;
      try {
         var4 = var2.getContext();
         var28 = ThreadContextKt.updateThreadContext(var4, null);
      } catch (var8: java.lang.Throwable) {
         var1 = Result.Companion;
         var3.resumeWith(Result.constructor-impl(ResultKt.createFailure(var8)));
         return;
      }

      try {
         var23 = (TypeIntrinsics.beforeCheckcastToFunctionOfArity(var0, 2) as Function2).invoke(var1, var3);
      } catch (var7: java.lang.Throwable) {
         try {
            ThreadContextKt.restoreThreadContext(var4, var28);
         } catch (var6: java.lang.Throwable) {
            var1 = Result.Companion;
            var3.resumeWith(Result.constructor-impl(ResultKt.createFailure(var6)));
            return;
         }
      }

      try {
         ThreadContextKt.restoreThreadContext(var4, var28);
      } catch (var5: java.lang.Throwable) {
         var1 = Result.Companion;
         var3.resumeWith(Result.constructor-impl(ResultKt.createFailure(var5)));
         return;
      }

      if (var23 != IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
         var1 = Result.Companion;
         var3.resumeWith(Result.constructor-impl(var23));
      }
   }
}

internal fun <T> ((Continuation<T>) -> Any?).startCoroutineUnintercepted(completion: Continuation<T>) {
   var1 = DebugProbesKt.probeCoroutineCreated(var1);

   try {
      var6 = (TypeIntrinsics.beforeCheckcastToFunctionOfArity(var0, 1) as Function1).invoke(var1);
   } catch (var3: java.lang.Throwable) {
      val var5: Result.Companion = Result.Companion;
      var1.resumeWith(Result.constructor-impl(ResultKt.createFailure(var3)));
      return;
   }

   if (var6 != IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      val var2: Result.Companion = Result.Companion;
      var1.resumeWith(Result.constructor-impl(var6));
   }
}

private inline fun <T> startDirect(completion: Continuation<T>, block: (Continuation<T>) -> Any?) {
   var0 = DebugProbesKt.probeCoroutineCreated(var0);

   try {
      var6 = var1.invoke(var0);
   } catch (var3: java.lang.Throwable) {
      val var2: Result.Companion = Result.Companion;
      var0.resumeWith(Result.constructor-impl(ResultKt.createFailure(var3)));
      return;
   }

   if (var6 != IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      val var7: Result.Companion = Result.Companion;
      var0.resumeWith(Result.constructor-impl(var6));
   }
}

internal fun <T, R> ScopeCoroutine<T>.startUndispatchedOrReturn(receiver: R, block: (R, Continuation<T>) -> Any?): Any? {
   label30:
   try {
      var1 = (TypeIntrinsics.beforeCheckcastToFunctionOfArity(var2, 2) as Function2).invoke(var1, var0);
   } catch (var3: java.lang.Throwable) {
      var1 = new CompletedExceptionally(var3, false, 2, null);
      break label30;
   }

   var var5: Any;
   if (var1 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      var5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
   } else {
      var1 = var0.makeCompletingOnce$kotlinx_coroutines_core(var1);
      if (var1 === JobSupportKt.COMPLETING_WAITING_CHILDREN) {
         var5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
      } else {
         if (var1 is CompletedExceptionally) {
            val var9: CompletedExceptionally = var1 as CompletedExceptionally;
            val var11: java.lang.Throwable = (var1 as CompletedExceptionally).cause;
            var1 = (var1 as CompletedExceptionally).cause;
            val var12: Continuation = var0.uCont;
            var5 = var9.cause;
            if (DebugKt.getRECOVER_STACK_TRACES()) {
               if (var12 !is CoroutineStackFrame) {
                  var5 = var1;
               } else {
                  var5 = StackTraceRecoveryKt.access$recoverFromStackFrame(var1, var12 as CoroutineStackFrame);
               }
            }

            throw var5;
         }

         var5 = JobSupportKt.unboxState(var1);
      }
   }

   return var5;
}

internal fun <T, R> ScopeCoroutine<T>.startUndispatchedOrReturnIgnoreTimeout(receiver: R, block: (R, Continuation<T>) -> Any?): Any? {
   label54:
   try {
      var1 = (TypeIntrinsics.beforeCheckcastToFunctionOfArity(var2, 2) as Function2).invoke(var1, var0);
   } catch (var4: java.lang.Throwable) {
      var1 = new CompletedExceptionally(var4, false, 2, null);
      break label54;
   }

   var var6: Any;
   if (var1 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      var6 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
   } else {
      var var12: CompletedExceptionally = (CompletedExceptionally)var0.makeCompletingOnce$kotlinx_coroutines_core(var1);
      if (var12 === JobSupportKt.COMPLETING_WAITING_CHILDREN) {
         var6 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
      } else {
         if (var12 is CompletedExceptionally) {
            var12 = var12;
            if (var12.cause !is TimeoutCancellationException || (var12.cause as TimeoutCancellationException).coroutine != var0) {
               var1 = var12.cause;
               val var16: Continuation = var0.uCont;
               var6 = var12.cause;
               if (DebugKt.getRECOVER_STACK_TRACES()) {
                  if (var16 !is CoroutineStackFrame) {
                     var6 = var1;
                  } else {
                     var6 = StackTraceRecoveryKt.access$recoverFromStackFrame(var1, var16 as CoroutineStackFrame);
                  }
               }

               throw var6;
            }

            var12 = var1;
            if (var1 is CompletedExceptionally) {
               val var10: java.lang.Throwable = (var1 as CompletedExceptionally).cause;
               val var15: Continuation = var0.uCont;
               var6 = (var1 as CompletedExceptionally).cause;
               if (DebugKt.getRECOVER_STACK_TRACES()) {
                  if (var15 !is CoroutineStackFrame) {
                     var6 = var10;
                  } else {
                     var6 = StackTraceRecoveryKt.access$recoverFromStackFrame(var10, var15 as CoroutineStackFrame);
                  }
               }

               throw var6;
            }
         } else {
            var12 = JobSupportKt.unboxState(var12);
         }

         var6 = var12;
      }
   }

   return var6;
}

private inline fun <T> ScopeCoroutine<T>.undispatchedResult(shouldThrow: (Throwable) -> Boolean, startBlock: () -> Any?): Any? {
   label44:
   try {
      var11 = var2.invoke();
   } catch (var4: java.lang.Throwable) {
      var11 = new CompletedExceptionally(var4, false, 2, null);
      break label44;
   }

   if (var11 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      return IntrinsicsKt.getCOROUTINE_SUSPENDED();
   } else {
      var var3: CompletedExceptionally = (CompletedExceptionally)var0.makeCompletingOnce$kotlinx_coroutines_core(var11);
      if (var3 === JobSupportKt.COMPLETING_WAITING_CHILDREN) {
         return IntrinsicsKt.getCOROUTINE_SUSPENDED();
      } else {
         var var8: Any;
         if (var3 is CompletedExceptionally) {
            var3 = var3;
            if (var1.invoke(var3.cause) as java.lang.Boolean) {
               var8 = var3.cause;
               val var13: Continuation = var0.uCont;
               var var7: java.lang.Throwable = var3.cause;
               if (DebugKt.getRECOVER_STACK_TRACES()) {
                  if (var13 !is CoroutineStackFrame) {
                     var7 = (java.lang.Throwable)var8;
                  } else {
                     var7 = StackTraceRecoveryKt.access$recoverFromStackFrame((java.lang.Throwable)var8, var13 as CoroutineStackFrame);
                  }
               }

               throw var7;
            }

            var8 = var11;
            if (var11 is CompletedExceptionally) {
               var8 = (var11 as CompletedExceptionally).cause;
               val var12: Continuation = var0.uCont;
               var var6: java.lang.Throwable = (var11 as CompletedExceptionally).cause;
               if (DebugKt.getRECOVER_STACK_TRACES()) {
                  if (var12 !is CoroutineStackFrame) {
                     var6 = (java.lang.Throwable)var8;
                  } else {
                     var6 = StackTraceRecoveryKt.access$recoverFromStackFrame((java.lang.Throwable)var8, var12 as CoroutineStackFrame);
                  }
               }

               throw var6;
            }
         } else {
            var8 = JobSupportKt.unboxState(var3);
         }

         return var8;
      }
   }
}
