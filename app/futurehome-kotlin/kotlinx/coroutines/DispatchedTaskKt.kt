package kotlinx.coroutines

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.jvm.internal.CoroutineStackFrame
import kotlinx.coroutines.internal.DispatchedContinuation
import kotlinx.coroutines.internal.StackTraceRecoveryKt
import kotlinx.coroutines.internal.ThreadContextKt

internal const val MODE_ATOMIC: Int = 0
internal const val MODE_CANCELLABLE: Int = 1
internal const val MODE_CANCELLABLE_REUSABLE: Int = 2
internal const val MODE_UNDISPATCHED: Int = 4
internal const val MODE_UNINITIALIZED: Int = -1

internal final val isCancellableMode: Boolean
   internal final get() {
      var var1: Boolean = true;
      if (var0 != 1) {
         if (var0 == 2) {
            var1 = true;
         } else {
            var1 = false;
         }
      }

      return var1;
   }


internal final val isReusableMode: Boolean
   internal final get() {
      val var1: Boolean;
      if (var0 == 2) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }


internal fun <T> DispatchedTask<T>.dispatch(mode: Int) {
   if (DebugKt.getASSERTIONS_ENABLED() && var1 == -1) {
      throw new AssertionError();
   } else {
      val var4: Continuation = var0.getDelegate$kotlinx_coroutines_core();
      val var2: Boolean;
      if (var1 == 4) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (!var2 && var4 is DispatchedContinuation && isCancellableMode(var1) == isCancellableMode(var0.resumeMode)) {
         val var3: CoroutineDispatcher = (var4 as DispatchedContinuation).dispatcher;
         val var5: CoroutineContext = var4.getContext();
         if (var3.isDispatchNeeded(var5)) {
            var3.dispatch(var5, var0);
         } else {
            resumeUnconfined(var0);
         }
      } else {
         resume(var0, var4, var2);
      }
   }
}

internal fun <T> DispatchedTask<T>.resume(delegate: Continuation<T>, undispatched: Boolean) {
   var var3: Any = var0.takeState$kotlinx_coroutines_core();
   var var4: java.lang.Throwable = var0.getExceptionalResult$kotlinx_coroutines_core(var3);
   var var9: Any;
   if (var4 != null) {
      var9 = Result.Companion;
      var9 = ResultKt.createFailure(var4);
   } else {
      val var15: Result.Companion = Result.Companion;
      var9 = var0.getSuccessfulResult$kotlinx_coroutines_core(var3);
   }

   var4 = (java.lang.Throwable)Result.constructor-impl(var9);
   label37:
   if (var2) {
      val var5: DispatchedContinuation = var1 as DispatchedContinuation;
      var9 = (var1 as DispatchedContinuation).continuation;
      var3 = (var1 as DispatchedContinuation).countOrElement;
      val var12: CoroutineContext = (var1 as DispatchedContinuation).continuation.getContext();
      var3 = ThreadContextKt.updateThreadContext(var12, var3);
      if (var3 != ThreadContextKt.NO_THREAD_ELEMENTS) {
         var9 = CoroutineContextKt.updateUndispatchedCompletion((Continuation<?>)var9, var12, var3);
      } else {
         var9 = null;
      }

      try {
         var5.continuation.resumeWith(var4);
      } catch (var6: java.lang.Throwable) {
         if (var9 == null || ((UndispatchedCoroutine)var9).clearThreadContext()) {
            ThreadContextKt.restoreThreadContext(var12, var3);
         }
      }

      if (var9 == null || ((UndispatchedCoroutine)var9).clearThreadContext()) {
         ThreadContextKt.restoreThreadContext(var12, var3);
      }
   } else {
      var1.resumeWith(var4);
   }
}

private fun DispatchedTask<*>.resumeUnconfined() {
   // $VF: Couldn't be decompiled
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
   //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
   //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
   //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
   //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
   //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
   //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
   //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
   //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
   //
   // Bytecode:
   // 00: getstatic kotlinx/coroutines/ThreadLocalEventLoop.INSTANCE Lkotlinx/coroutines/ThreadLocalEventLoop;
   // 03: invokevirtual kotlinx/coroutines/ThreadLocalEventLoop.getEventLoop$kotlinx_coroutines_core ()Lkotlinx/coroutines/EventLoop;
   // 06: astore 2
   // 07: aload 2
   // 08: invokevirtual kotlinx/coroutines/EventLoop.isUnconfinedLoopActive ()Z
   // 0b: ifeq 16
   // 0e: aload 2
   // 0f: aload 0
   // 10: invokevirtual kotlinx/coroutines/EventLoop.dispatchUnconfined (Lkotlinx/coroutines/DispatchedTask;)V
   // 13: goto 3c
   // 16: aload 2
   // 17: bipush 1
   // 18: invokevirtual kotlinx/coroutines/EventLoop.incrementUseCount (Z)V
   // 1b: aload 0
   // 1c: aload 0
   // 1d: invokevirtual kotlinx/coroutines/DispatchedTask.getDelegate$kotlinx_coroutines_core ()Lkotlin/coroutines/Continuation;
   // 20: bipush 1
   // 21: invokestatic kotlinx/coroutines/DispatchedTaskKt.resume (Lkotlinx/coroutines/DispatchedTask;Lkotlin/coroutines/Continuation;Z)V
   // 24: aload 2
   // 25: invokevirtual kotlinx/coroutines/EventLoop.processUnconfinedEvent ()Z
   // 28: istore 1
   // 29: iload 1
   // 2a: ifne 24
   // 2d: goto 37
   // 30: astore 3
   // 31: aload 0
   // 32: aload 3
   // 33: aconst_null
   // 34: invokevirtual kotlinx/coroutines/DispatchedTask.handleFatalException$kotlinx_coroutines_core (Ljava/lang/Throwable;Ljava/lang/Throwable;)V
   // 37: aload 2
   // 38: bipush 1
   // 39: invokevirtual kotlinx/coroutines/EventLoop.decrementUseCount (Z)V
   // 3c: return
   // 3d: astore 0
   // 3e: aload 2
   // 3f: bipush 1
   // 40: invokevirtual kotlinx/coroutines/EventLoop.decrementUseCount (Z)V
   // 43: aload 0
   // 44: athrow
}

internal inline fun Continuation<*>.resumeWithStackTrace(exception: Throwable) {
   val var2: Result.Companion = Result.Companion;
   var var3: java.lang.Throwable = var1;
   if (DebugKt.getRECOVER_STACK_TRACES()) {
      if (var0 !is CoroutineStackFrame) {
         var3 = var1;
      } else {
         var3 = StackTraceRecoveryKt.access$recoverFromStackFrame(var1, var0 as CoroutineStackFrame);
      }
   }

   var0.resumeWith(Result.constructor-impl(ResultKt.createFailure(var3)));
}

internal inline fun DispatchedTask<*>.runUnconfinedEventLoop(eventLoop: EventLoop, block: () -> Unit) {
   // $VF: Couldn't be decompiled
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
   //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
   //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
   //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
   //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
   //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
   //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
   //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
   //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
   //
   // Bytecode:
   // 00: aload 1
   // 01: bipush 1
   // 02: invokevirtual kotlinx/coroutines/EventLoop.incrementUseCount (Z)V
   // 05: aload 2
   // 06: invokeinterface kotlin/jvm/functions/Function0.invoke ()Ljava/lang/Object; 1
   // 0b: pop
   // 0c: aload 1
   // 0d: invokevirtual kotlinx/coroutines/EventLoop.processUnconfinedEvent ()Z
   // 10: istore 3
   // 11: iload 3
   // 12: ifne 0c
   // 15: goto 1f
   // 18: astore 2
   // 19: aload 0
   // 1a: aload 2
   // 1b: aconst_null
   // 1c: invokevirtual kotlinx/coroutines/DispatchedTask.handleFatalException$kotlinx_coroutines_core (Ljava/lang/Throwable;Ljava/lang/Throwable;)V
   // 1f: aload 1
   // 20: bipush 1
   // 21: invokevirtual kotlinx/coroutines/EventLoop.decrementUseCount (Z)V
   // 24: return
   // 25: astore 0
   // 26: aload 1
   // 27: bipush 1
   // 28: invokevirtual kotlinx/coroutines/EventLoop.decrementUseCount (Z)V
   // 2b: aload 0
   // 2c: athrow
}
