package kotlinx.coroutines

import kotlin.coroutines.Continuation
import kotlinx.coroutines.scheduling.Task

internal abstract class DispatchedTask<T> : Task {
   internal abstract val delegate: Continuation<Any>

   public final var resumeMode: Int
      private set

   open fun DispatchedTask(var1: Int) {
      this.resumeMode = var1;
   }

   internal open fun cancelCompletedResult(takenState: Any?, cause: Throwable) {
   }

   internal open fun getExceptionalResult(state: Any?): Throwable? {
      val var2: Boolean = var1 is CompletedExceptionally;
      var var3: java.lang.Throwable = null;
      if (var2) {
         var1 = var1;
      } else {
         var1 = null;
      }

      if (var1 != null) {
         var3 = var1.cause;
      }

      return var3;
   }

   internal open fun <T> getSuccessfulResult(state: Any?): T {
      return (T)var1;
   }

   internal fun handleFatalException(exception: Throwable?, finallyException: Throwable?) {
      if (var1 != null || var2 != null) {
         if (var1 != null && var2 != null) {
            kotlin.ExceptionsKt.addSuppressed(var1, var2);
         }

         var var3: java.lang.Throwable = var1;
         if (var1 == null) {
            var3 = var2;
         }

         val var4: StringBuilder = new StringBuilder("Fatal exception in coroutines machinery for ");
         var4.append(this);
         var4.append(". Please read KDoc to 'handleFatalException' method and report this incident to maintainers");
         val var5: java.lang.String = var4.toString();
         CoroutineExceptionHandlerKt.handleCoroutineException(this.getDelegate$kotlinx_coroutines_core().getContext(), new CoroutinesInternalError(var5, var3));
      }
   }

   public override fun run() {
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
      // 000: invokestatic kotlinx/coroutines/DebugKt.getASSERTIONS_ENABLED ()Z
      // 003: ifeq 019
      // 006: aload 0
      // 007: getfield kotlinx/coroutines/DispatchedTask.resumeMode I
      // 00a: bipush -1
      // 00b: if_icmpeq 011
      // 00e: goto 019
      // 011: new java/lang/AssertionError
      // 014: dup
      // 015: invokespecial java/lang/AssertionError.<init> ()V
      // 018: athrow
      // 019: aload 0
      // 01a: getfield kotlinx/coroutines/DispatchedTask.taskContext Lkotlinx/coroutines/scheduling/TaskContext;
      // 01d: astore 3
      // 01e: aload 0
      // 01f: invokevirtual kotlinx/coroutines/DispatchedTask.getDelegate$kotlinx_coroutines_core ()Lkotlin/coroutines/Continuation;
      // 022: astore 1
      // 023: aload 1
      // 024: ldc "null cannot be cast to non-null type kotlinx.coroutines.internal.DispatchedContinuation<T of kotlinx.coroutines.DispatchedTask>"
      // 026: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;Ljava/lang/String;)V
      // 029: aload 1
      // 02a: checkcast kotlinx/coroutines/internal/DispatchedContinuation
      // 02d: astore 1
      // 02e: aload 1
      // 02f: getfield kotlinx/coroutines/internal/DispatchedContinuation.continuation Lkotlin/coroutines/Continuation;
      // 032: astore 5
      // 034: aload 1
      // 035: getfield kotlinx/coroutines/internal/DispatchedContinuation.countOrElement Ljava/lang/Object;
      // 038: astore 1
      // 039: aload 5
      // 03b: invokeinterface kotlin/coroutines/Continuation.getContext ()Lkotlin/coroutines/CoroutineContext; 1
      // 040: astore 4
      // 042: aload 4
      // 044: aload 1
      // 045: invokestatic kotlinx/coroutines/internal/ThreadContextKt.updateThreadContext (Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;)Ljava/lang/Object;
      // 048: astore 6
      // 04a: aload 6
      // 04c: getstatic kotlinx/coroutines/internal/ThreadContextKt.NO_THREAD_ELEMENTS Lkotlinx/coroutines/internal/Symbol;
      // 04f: if_acmpeq 05f
      // 052: aload 5
      // 054: aload 4
      // 056: aload 6
      // 058: invokestatic kotlinx/coroutines/CoroutineContextKt.updateUndispatchedCompletion (Lkotlin/coroutines/Continuation;Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;)Lkotlinx/coroutines/UndispatchedCoroutine;
      // 05b: astore 1
      // 05c: goto 061
      // 05f: aconst_null
      // 060: astore 1
      // 061: aload 5
      // 063: invokeinterface kotlin/coroutines/Continuation.getContext ()Lkotlin/coroutines/CoroutineContext; 1
      // 068: astore 2
      // 069: aload 0
      // 06a: invokevirtual kotlinx/coroutines/DispatchedTask.takeState$kotlinx_coroutines_core ()Ljava/lang/Object;
      // 06d: astore 7
      // 06f: aload 0
      // 070: aload 7
      // 072: invokevirtual kotlinx/coroutines/DispatchedTask.getExceptionalResult$kotlinx_coroutines_core (Ljava/lang/Object;)Ljava/lang/Throwable;
      // 075: astore 8
      // 077: aload 8
      // 079: ifnonnull 099
      // 07c: aload 0
      // 07d: getfield kotlinx/coroutines/DispatchedTask.resumeMode I
      // 080: invokestatic kotlinx/coroutines/DispatchedTaskKt.isCancellableMode (I)Z
      // 083: ifeq 099
      // 086: aload 2
      // 087: getstatic kotlinx/coroutines/Job.Key Lkotlinx/coroutines/Job$Key;
      // 08a: checkcast kotlin/coroutines/CoroutineContext$Key
      // 08d: invokeinterface kotlin/coroutines/CoroutineContext.get (Lkotlin/coroutines/CoroutineContext$Key;)Lkotlin/coroutines/CoroutineContext$Element; 2
      // 092: checkcast kotlinx/coroutines/Job
      // 095: astore 2
      // 096: goto 09b
      // 099: aconst_null
      // 09a: astore 2
      // 09b: aload 2
      // 09c: ifnull 0f5
      // 09f: aload 2
      // 0a0: invokeinterface kotlinx/coroutines/Job.isActive ()Z 1
      // 0a5: ifne 0f5
      // 0a8: aload 2
      // 0a9: invokeinterface kotlinx/coroutines/Job.getCancellationException ()Ljava/util/concurrent/CancellationException; 1
      // 0ae: astore 2
      // 0af: aload 0
      // 0b0: aload 7
      // 0b2: aload 2
      // 0b3: checkcast java/lang/Throwable
      // 0b6: invokevirtual kotlinx/coroutines/DispatchedTask.cancelCompletedResult$kotlinx_coroutines_core (Ljava/lang/Object;Ljava/lang/Throwable;)V
      // 0b9: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 0bc: astore 7
      // 0be: invokestatic kotlinx/coroutines/DebugKt.getRECOVER_STACK_TRACES ()Z
      // 0c1: ifeq 0df
      // 0c4: aload 5
      // 0c6: instanceof kotlin/coroutines/jvm/internal/CoroutineStackFrame
      // 0c9: ifne 0cf
      // 0cc: goto 0df
      // 0cf: aload 2
      // 0d0: checkcast java/lang/Throwable
      // 0d3: aload 5
      // 0d5: checkcast kotlin/coroutines/jvm/internal/CoroutineStackFrame
      // 0d8: invokestatic kotlinx/coroutines/internal/StackTraceRecoveryKt.access$recoverFromStackFrame (Ljava/lang/Throwable;Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;)Ljava/lang/Throwable;
      // 0db: astore 2
      // 0dc: goto 0e4
      // 0df: aload 2
      // 0e0: checkcast java/lang/Throwable
      // 0e3: astore 2
      // 0e4: aload 5
      // 0e6: aload 2
      // 0e7: invokestatic kotlin/ResultKt.createFailure (Ljava/lang/Throwable;)Ljava/lang/Object;
      // 0ea: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 0ed: invokeinterface kotlin/coroutines/Continuation.resumeWith (Ljava/lang/Object;)V 2
      // 0f2: goto 124
      // 0f5: aload 8
      // 0f7: ifnull 110
      // 0fa: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 0fd: astore 2
      // 0fe: aload 5
      // 100: aload 8
      // 102: invokestatic kotlin/ResultKt.createFailure (Ljava/lang/Throwable;)Ljava/lang/Object;
      // 105: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 108: invokeinterface kotlin/coroutines/Continuation.resumeWith (Ljava/lang/Object;)V 2
      // 10d: goto 124
      // 110: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 113: astore 2
      // 114: aload 5
      // 116: aload 0
      // 117: aload 7
      // 119: invokevirtual kotlinx/coroutines/DispatchedTask.getSuccessfulResult$kotlinx_coroutines_core (Ljava/lang/Object;)Ljava/lang/Object;
      // 11c: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 11f: invokeinterface kotlin/coroutines/Continuation.resumeWith (Ljava/lang/Object;)V 2
      // 124: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 127: astore 2
      // 128: aload 1
      // 129: ifnull 133
      // 12c: aload 1
      // 12d: invokevirtual kotlinx/coroutines/UndispatchedCoroutine.clearThreadContext ()Z
      // 130: ifeq 13a
      // 133: aload 4
      // 135: aload 6
      // 137: invokestatic kotlinx/coroutines/internal/ThreadContextKt.restoreThreadContext (Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;)V
      // 13a: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 13d: astore 1
      // 13e: aload 0
      // 13f: checkcast kotlinx/coroutines/DispatchedTask
      // 142: astore 1
      // 143: aload 3
      // 144: invokeinterface kotlinx/coroutines/scheduling/TaskContext.afterTask ()V 1
      // 149: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 14c: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 14f: astore 1
      // 150: goto 160
      // 153: astore 1
      // 154: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 157: astore 2
      // 158: aload 1
      // 159: invokestatic kotlin/ResultKt.createFailure (Ljava/lang/Throwable;)Ljava/lang/Object;
      // 15c: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 15f: astore 1
      // 160: aload 0
      // 161: aconst_null
      // 162: aload 1
      // 163: invokestatic kotlin/Result.exceptionOrNull-impl (Ljava/lang/Object;)Ljava/lang/Throwable;
      // 166: invokevirtual kotlinx/coroutines/DispatchedTask.handleFatalException$kotlinx_coroutines_core (Ljava/lang/Throwable;Ljava/lang/Throwable;)V
      // 169: goto 1ac
      // 16c: astore 2
      // 16d: aload 1
      // 16e: ifnull 178
      // 171: aload 1
      // 172: invokevirtual kotlinx/coroutines/UndispatchedCoroutine.clearThreadContext ()Z
      // 175: ifeq 17f
      // 178: aload 4
      // 17a: aload 6
      // 17c: invokestatic kotlinx/coroutines/internal/ThreadContextKt.restoreThreadContext (Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;)V
      // 17f: aload 2
      // 180: athrow
      // 181: astore 2
      // 182: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 185: astore 1
      // 186: aload 3
      // 187: invokeinterface kotlinx/coroutines/scheduling/TaskContext.afterTask ()V 1
      // 18c: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 18f: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 192: astore 1
      // 193: goto 1a3
      // 196: astore 3
      // 197: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 19a: astore 1
      // 19b: aload 3
      // 19c: invokestatic kotlin/ResultKt.createFailure (Ljava/lang/Throwable;)Ljava/lang/Object;
      // 19f: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 1a2: astore 1
      // 1a3: aload 0
      // 1a4: aload 2
      // 1a5: aload 1
      // 1a6: invokestatic kotlin/Result.exceptionOrNull-impl (Ljava/lang/Object;)Ljava/lang/Throwable;
      // 1a9: invokevirtual kotlinx/coroutines/DispatchedTask.handleFatalException$kotlinx_coroutines_core (Ljava/lang/Throwable;Ljava/lang/Throwable;)V
      // 1ac: return
   }

   internal abstract fun takeState(): Any? {
   }
}
