package kotlinx.coroutines.internal

import androidx.concurrent.futures.AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.jvm.internal.CoroutineStackFrame
import kotlinx.atomicfu.AtomicRef
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.CancellableContinuationImpl
import kotlinx.coroutines.CompletedWithCancellation
import kotlinx.coroutines.CoroutineContextKt
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.DebugKt
import kotlinx.coroutines.DebugStringsKt
import kotlinx.coroutines.DispatchedTask
import kotlinx.coroutines.Job
import kotlinx.coroutines.UndispatchedCoroutine

internal class DispatchedContinuation<T>(dispatcher: CoroutineDispatcher, continuation: Continuation<Any>) : DispatchedTask(-1),
   CoroutineStackFrame,
   Continuation<T> {
   private final val _reusableCancellableContinuation: AtomicRef<Any?>

   internal final var _state: Any?
      private set

   public open val callerFrame: CoroutineStackFrame?
      public open get() {
         val var2: CoroutineStackFrame;
         if (this.continuation is CoroutineStackFrame) {
            var2 = this.continuation as CoroutineStackFrame;
         } else {
            var2 = null;
         }

         return var2;
      }


   public open val context: CoroutineContext
   public final val continuation: Continuation<Any>
   internal final val countOrElement: Any

   internal open val delegate: Continuation<Any>
      internal open get() {
         return this;
      }


   internal final val dispatcher: CoroutineDispatcher

   private final val reusableCancellableContinuation: CancellableContinuationImpl<*>?
      private final get() {
         var var1: CancellableContinuationImpl = (CancellableContinuationImpl)_reusableCancellableContinuation$FU.get(this);
         if (var1 is CancellableContinuationImpl) {
            var1 = var1;
         } else {
            var1 = null;
         }

         return var1;
      }


   init {
      this.dispatcher = var1;
      this.continuation = var2;
      this._state = DispatchedContinuationKt.access$getUNDEFINED$p();
      this.countOrElement = ThreadContextKt.threadContextElements(this.getContext());
   }

   fun `loop$atomicfu`(var1: AtomicReferenceFieldUpdater, var2: (Any?) -> Unit, var3: Any) {
      while (true) {
         var2.invoke(var1.get(var3));
      }
   }

   internal fun awaitReusability() {
      val var1: AtomicReferenceFieldUpdater = _reusableCancellableContinuation$FU;

      while (var1.get(this) == DispatchedContinuationKt.REUSABLE_CLAIMED) {
      }
   }

   internal override fun cancelCompletedResult(takenState: Any?, cause: Throwable) {
      if (var1 is CompletedWithCancellation) {
         (var1 as CompletedWithCancellation).onCancellation.invoke(var2);
      }
   }

   internal fun claimReusableCancellableContinuation(): CancellableContinuationImpl<Any>? {
      val var2: AtomicReferenceFieldUpdater = _reusableCancellableContinuation$FU;

      while (true) {
         val var1: Any = var2.get(this);
         if (var1 == null) {
            _reusableCancellableContinuation$FU.set(this, DispatchedContinuationKt.REUSABLE_CLAIMED);
            return null;
         }

         if (var1 is CancellableContinuationImpl) {
            if (ExternalSyntheticBackportWithForwarding0.m(_reusableCancellableContinuation$FU, this, var1, DispatchedContinuationKt.REUSABLE_CLAIMED)) {
               return var1 as CancellableContinuationImpl<T>;
            }
         } else if (var1 != DispatchedContinuationKt.REUSABLE_CLAIMED && var1 !is java.lang.Throwable) {
            val var3: StringBuilder = new StringBuilder("Inconsistent state ");
            var3.append(var1);
            throw new IllegalStateException(var3.toString().toString());
         }
      }
   }

   internal fun dispatchYield(context: CoroutineContext, value: Any) {
      this._state = var2;
      this.resumeMode = 1;
      this.dispatcher.dispatchYield(var1, this);
   }

   public override fun getStackTraceElement(): StackTraceElement? {
      return null;
   }

   internal fun isReusable(): Boolean {
      val var1: Boolean;
      if (_reusableCancellableContinuation$FU.get(this) != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   internal fun postponeCancellation(cause: Throwable): Boolean {
      val var3: AtomicReferenceFieldUpdater = _reusableCancellableContinuation$FU;

      while (true) {
         val var2: Any = var3.get(this);
         if (var2 == DispatchedContinuationKt.REUSABLE_CLAIMED) {
            if (ExternalSyntheticBackportWithForwarding0.m(_reusableCancellableContinuation$FU, this, DispatchedContinuationKt.REUSABLE_CLAIMED, var1)) {
               return true;
            }
         } else {
            if (var2 is java.lang.Throwable) {
               return true;
            }

            if (ExternalSyntheticBackportWithForwarding0.m(_reusableCancellableContinuation$FU, this, var2, null)) {
               return false;
            }
         }
      }
   }

   internal fun release() {
      this.awaitReusability$kotlinx_coroutines_core();
      val var1: CancellableContinuationImpl = this.getReusableCancellableContinuation();
      if (var1 != null) {
         var1.detachChild$kotlinx_coroutines_core();
      }
   }

   internal inline fun resumeCancellableWith(result: Result<Any>, noinline onCancellation: ((Throwable) -> Unit)?) {
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
      // 000: aload 1
      // 001: aload 2
      // 002: invokestatic kotlinx/coroutines/CompletionStateKt.toState (Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;
      // 005: astore 2
      // 006: aload 0
      // 007: getfield kotlinx/coroutines/internal/DispatchedContinuation.dispatcher Lkotlinx/coroutines/CoroutineDispatcher;
      // 00a: aload 0
      // 00b: invokevirtual kotlinx/coroutines/internal/DispatchedContinuation.getContext ()Lkotlin/coroutines/CoroutineContext;
      // 00e: invokevirtual kotlinx/coroutines/CoroutineDispatcher.isDispatchNeeded (Lkotlin/coroutines/CoroutineContext;)Z
      // 011: ifeq 030
      // 014: aload 0
      // 015: aload 2
      // 016: putfield kotlinx/coroutines/internal/DispatchedContinuation._state Ljava/lang/Object;
      // 019: aload 0
      // 01a: bipush 1
      // 01b: putfield kotlinx/coroutines/internal/DispatchedContinuation.resumeMode I
      // 01e: aload 0
      // 01f: getfield kotlinx/coroutines/internal/DispatchedContinuation.dispatcher Lkotlinx/coroutines/CoroutineDispatcher;
      // 022: aload 0
      // 023: invokevirtual kotlinx/coroutines/internal/DispatchedContinuation.getContext ()Lkotlin/coroutines/CoroutineContext;
      // 026: aload 0
      // 027: checkcast java/lang/Runnable
      // 02a: invokevirtual kotlinx/coroutines/CoroutineDispatcher.dispatch (Lkotlin/coroutines/CoroutineContext;Ljava/lang/Runnable;)V
      // 02d: goto 132
      // 030: invokestatic kotlinx/coroutines/DebugKt.getASSERTIONS_ENABLED ()Z
      // 033: pop
      // 034: getstatic kotlinx/coroutines/ThreadLocalEventLoop.INSTANCE Lkotlinx/coroutines/ThreadLocalEventLoop;
      // 037: invokevirtual kotlinx/coroutines/ThreadLocalEventLoop.getEventLoop$kotlinx_coroutines_core ()Lkotlinx/coroutines/EventLoop;
      // 03a: astore 3
      // 03b: aload 3
      // 03c: invokevirtual kotlinx/coroutines/EventLoop.isUnconfinedLoopActive ()Z
      // 03f: ifeq 057
      // 042: aload 0
      // 043: aload 2
      // 044: putfield kotlinx/coroutines/internal/DispatchedContinuation._state Ljava/lang/Object;
      // 047: aload 0
      // 048: bipush 1
      // 049: putfield kotlinx/coroutines/internal/DispatchedContinuation.resumeMode I
      // 04c: aload 3
      // 04d: aload 0
      // 04e: checkcast kotlinx/coroutines/DispatchedTask
      // 051: invokevirtual kotlinx/coroutines/EventLoop.dispatchUnconfined (Lkotlinx/coroutines/DispatchedTask;)V
      // 054: goto 132
      // 057: aload 0
      // 058: checkcast kotlinx/coroutines/DispatchedTask
      // 05b: astore 4
      // 05d: aload 3
      // 05e: bipush 1
      // 05f: invokevirtual kotlinx/coroutines/EventLoop.incrementUseCount (Z)V
      // 062: aload 0
      // 063: invokevirtual kotlinx/coroutines/internal/DispatchedContinuation.getContext ()Lkotlin/coroutines/CoroutineContext;
      // 066: getstatic kotlinx/coroutines/Job.Key Lkotlinx/coroutines/Job$Key;
      // 069: checkcast kotlin/coroutines/CoroutineContext$Key
      // 06c: invokeinterface kotlin/coroutines/CoroutineContext.get (Lkotlin/coroutines/CoroutineContext$Key;)Lkotlin/coroutines/CoroutineContext$Element; 2
      // 071: checkcast kotlinx/coroutines/Job
      // 074: astore 5
      // 076: aload 5
      // 078: ifnull 0b4
      // 07b: aload 5
      // 07d: invokeinterface kotlinx/coroutines/Job.isActive ()Z 1
      // 082: ifne 0b4
      // 085: aload 5
      // 087: invokeinterface kotlinx/coroutines/Job.getCancellationException ()Ljava/util/concurrent/CancellationException; 1
      // 08c: astore 1
      // 08d: aload 0
      // 08e: aload 2
      // 08f: aload 1
      // 090: checkcast java/lang/Throwable
      // 093: invokevirtual kotlinx/coroutines/internal/DispatchedContinuation.cancelCompletedResult$kotlinx_coroutines_core (Ljava/lang/Object;Ljava/lang/Throwable;)V
      // 096: aload 0
      // 097: checkcast kotlin/coroutines/Continuation
      // 09a: astore 5
      // 09c: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 09f: astore 2
      // 0a0: aload 5
      // 0a2: aload 1
      // 0a3: checkcast java/lang/Throwable
      // 0a6: invokestatic kotlin/ResultKt.createFailure (Ljava/lang/Throwable;)Ljava/lang/Object;
      // 0a9: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 0ac: invokeinterface kotlin/coroutines/Continuation.resumeWith (Ljava/lang/Object;)V 2
      // 0b1: goto 106
      // 0b4: aload 0
      // 0b5: getfield kotlinx/coroutines/internal/DispatchedContinuation.continuation Lkotlin/coroutines/Continuation;
      // 0b8: astore 2
      // 0b9: aload 0
      // 0ba: getfield kotlinx/coroutines/internal/DispatchedContinuation.countOrElement Ljava/lang/Object;
      // 0bd: astore 6
      // 0bf: aload 2
      // 0c0: invokeinterface kotlin/coroutines/Continuation.getContext ()Lkotlin/coroutines/CoroutineContext; 1
      // 0c5: astore 5
      // 0c7: aload 5
      // 0c9: aload 6
      // 0cb: invokestatic kotlinx/coroutines/internal/ThreadContextKt.updateThreadContext (Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;)Ljava/lang/Object;
      // 0ce: astore 6
      // 0d0: aload 6
      // 0d2: getstatic kotlinx/coroutines/internal/ThreadContextKt.NO_THREAD_ELEMENTS Lkotlinx/coroutines/internal/Symbol;
      // 0d5: if_acmpeq 0e4
      // 0d8: aload 2
      // 0d9: aload 5
      // 0db: aload 6
      // 0dd: invokestatic kotlinx/coroutines/CoroutineContextKt.updateUndispatchedCompletion (Lkotlin/coroutines/Continuation;Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;)Lkotlinx/coroutines/UndispatchedCoroutine;
      // 0e0: astore 2
      // 0e1: goto 0e6
      // 0e4: aconst_null
      // 0e5: astore 2
      // 0e6: aload 0
      // 0e7: getfield kotlinx/coroutines/internal/DispatchedContinuation.continuation Lkotlin/coroutines/Continuation;
      // 0ea: aload 1
      // 0eb: invokeinterface kotlin/coroutines/Continuation.resumeWith (Ljava/lang/Object;)V 2
      // 0f0: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 0f3: astore 1
      // 0f4: aload 2
      // 0f5: ifnull 0ff
      // 0f8: aload 2
      // 0f9: invokevirtual kotlinx/coroutines/UndispatchedCoroutine.clearThreadContext ()Z
      // 0fc: ifeq 106
      // 0ff: aload 5
      // 101: aload 6
      // 103: invokestatic kotlinx/coroutines/internal/ThreadContextKt.restoreThreadContext (Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;)V
      // 106: aload 3
      // 107: invokevirtual kotlinx/coroutines/EventLoop.processUnconfinedEvent ()Z
      // 10a: ifne 106
      // 10d: goto 12d
      // 110: astore 1
      // 111: aload 2
      // 112: ifnull 11c
      // 115: aload 2
      // 116: invokevirtual kotlinx/coroutines/UndispatchedCoroutine.clearThreadContext ()Z
      // 119: ifeq 123
      // 11c: aload 5
      // 11e: aload 6
      // 120: invokestatic kotlinx/coroutines/internal/ThreadContextKt.restoreThreadContext (Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;)V
      // 123: aload 1
      // 124: athrow
      // 125: astore 1
      // 126: aload 4
      // 128: aload 1
      // 129: aconst_null
      // 12a: invokevirtual kotlinx/coroutines/DispatchedTask.handleFatalException$kotlinx_coroutines_core (Ljava/lang/Throwable;Ljava/lang/Throwable;)V
      // 12d: aload 3
      // 12e: bipush 1
      // 12f: invokevirtual kotlinx/coroutines/EventLoop.decrementUseCount (Z)V
      // 132: return
      // 133: astore 1
      // 134: aload 3
      // 135: bipush 1
      // 136: invokevirtual kotlinx/coroutines/EventLoop.decrementUseCount (Z)V
      // 139: aload 1
      // 13a: athrow
   }

   internal inline fun resumeCancelled(state: Any?): Boolean {
      val var2: Job = this.getContext().get(Job.Key);
      if (var2 != null && !var2.isActive()) {
         val var5: java.lang.Throwable = var2.getCancellationException();
         this.cancelCompletedResult$kotlinx_coroutines_core(var1, var5);
         var1 = this;
         val var3: Result.Companion = Result.Companion;
         var1.resumeWith(Result.constructor-impl(ResultKt.createFailure(var5)));
         return true;
      } else {
         return false;
      }
   }

   internal inline fun resumeUndispatchedWith(result: Result<Any>) {
      label46: {
         val var2: Continuation = this.continuation;
         var var4: Any = this.countOrElement;
         val var3: CoroutineContext = this.continuation.getContext();
         var4 = ThreadContextKt.updateThreadContext(var3, var4);
         val var8: UndispatchedCoroutine;
         if (var4 != ThreadContextKt.NO_THREAD_ELEMENTS) {
            var8 = CoroutineContextKt.updateUndispatchedCompletion(var2, var3, var4);
         } else {
            var8 = null;
         }

         try {
            this.continuation.resumeWith(var1);
         } catch (var5: java.lang.Throwable) {
            if (var8 == null || var8.clearThreadContext()) {
               ThreadContextKt.restoreThreadContext(var3, var4);
            }
         }

         if (var8 == null || var8.clearThreadContext()) {
            ThreadContextKt.restoreThreadContext(var3, var4);
         }
      }
   }

   public override fun resumeWith(result: Result<Any>) {
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
      // 00: aload 0
      // 01: getfield kotlinx/coroutines/internal/DispatchedContinuation.continuation Lkotlin/coroutines/Continuation;
      // 04: invokeinterface kotlin/coroutines/Continuation.getContext ()Lkotlin/coroutines/CoroutineContext; 1
      // 09: astore 2
      // 0a: aload 1
      // 0b: aconst_null
      // 0c: bipush 1
      // 0d: aconst_null
      // 0e: invokestatic kotlinx/coroutines/CompletionStateKt.toState$default (Ljava/lang/Object;Lkotlin/jvm/functions/Function1;ILjava/lang/Object;)Ljava/lang/Object;
      // 11: astore 3
      // 12: aload 0
      // 13: getfield kotlinx/coroutines/internal/DispatchedContinuation.dispatcher Lkotlinx/coroutines/CoroutineDispatcher;
      // 16: aload 2
      // 17: invokevirtual kotlinx/coroutines/CoroutineDispatcher.isDispatchNeeded (Lkotlin/coroutines/CoroutineContext;)Z
      // 1a: ifeq 36
      // 1d: aload 0
      // 1e: aload 3
      // 1f: putfield kotlinx/coroutines/internal/DispatchedContinuation._state Ljava/lang/Object;
      // 22: aload 0
      // 23: bipush 0
      // 24: putfield kotlinx/coroutines/internal/DispatchedContinuation.resumeMode I
      // 27: aload 0
      // 28: getfield kotlinx/coroutines/internal/DispatchedContinuation.dispatcher Lkotlinx/coroutines/CoroutineDispatcher;
      // 2b: aload 2
      // 2c: aload 0
      // 2d: checkcast java/lang/Runnable
      // 30: invokevirtual kotlinx/coroutines/CoroutineDispatcher.dispatch (Lkotlin/coroutines/CoroutineContext;Ljava/lang/Runnable;)V
      // 33: goto ad
      // 36: invokestatic kotlinx/coroutines/DebugKt.getASSERTIONS_ENABLED ()Z
      // 39: pop
      // 3a: getstatic kotlinx/coroutines/ThreadLocalEventLoop.INSTANCE Lkotlinx/coroutines/ThreadLocalEventLoop;
      // 3d: invokevirtual kotlinx/coroutines/ThreadLocalEventLoop.getEventLoop$kotlinx_coroutines_core ()Lkotlinx/coroutines/EventLoop;
      // 40: astore 2
      // 41: aload 2
      // 42: invokevirtual kotlinx/coroutines/EventLoop.isUnconfinedLoopActive ()Z
      // 45: ifeq 5d
      // 48: aload 0
      // 49: aload 3
      // 4a: putfield kotlinx/coroutines/internal/DispatchedContinuation._state Ljava/lang/Object;
      // 4d: aload 0
      // 4e: bipush 0
      // 4f: putfield kotlinx/coroutines/internal/DispatchedContinuation.resumeMode I
      // 52: aload 2
      // 53: aload 0
      // 54: checkcast kotlinx/coroutines/DispatchedTask
      // 57: invokevirtual kotlinx/coroutines/EventLoop.dispatchUnconfined (Lkotlinx/coroutines/DispatchedTask;)V
      // 5a: goto ad
      // 5d: aload 0
      // 5e: checkcast kotlinx/coroutines/DispatchedTask
      // 61: astore 3
      // 62: aload 2
      // 63: bipush 1
      // 64: invokevirtual kotlinx/coroutines/EventLoop.incrementUseCount (Z)V
      // 67: aload 0
      // 68: invokevirtual kotlinx/coroutines/internal/DispatchedContinuation.getContext ()Lkotlin/coroutines/CoroutineContext;
      // 6b: astore 4
      // 6d: aload 4
      // 6f: aload 0
      // 70: getfield kotlinx/coroutines/internal/DispatchedContinuation.countOrElement Ljava/lang/Object;
      // 73: invokestatic kotlinx/coroutines/internal/ThreadContextKt.updateThreadContext (Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;)Ljava/lang/Object;
      // 76: astore 5
      // 78: aload 0
      // 79: getfield kotlinx/coroutines/internal/DispatchedContinuation.continuation Lkotlin/coroutines/Continuation;
      // 7c: aload 1
      // 7d: invokeinterface kotlin/coroutines/Continuation.resumeWith (Ljava/lang/Object;)V 2
      // 82: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 85: astore 1
      // 86: aload 4
      // 88: aload 5
      // 8a: invokestatic kotlinx/coroutines/internal/ThreadContextKt.restoreThreadContext (Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;)V
      // 8d: aload 2
      // 8e: invokevirtual kotlinx/coroutines/EventLoop.processUnconfinedEvent ()Z
      // 91: ifne 8d
      // 94: goto a8
      // 97: astore 1
      // 98: aload 4
      // 9a: aload 5
      // 9c: invokestatic kotlinx/coroutines/internal/ThreadContextKt.restoreThreadContext (Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;)V
      // 9f: aload 1
      // a0: athrow
      // a1: astore 1
      // a2: aload 3
      // a3: aload 1
      // a4: aconst_null
      // a5: invokevirtual kotlinx/coroutines/DispatchedTask.handleFatalException$kotlinx_coroutines_core (Ljava/lang/Throwable;Ljava/lang/Throwable;)V
      // a8: aload 2
      // a9: bipush 1
      // aa: invokevirtual kotlinx/coroutines/EventLoop.decrementUseCount (Z)V
      // ad: return
      // ae: astore 1
      // af: aload 2
      // b0: bipush 1
      // b1: invokevirtual kotlinx/coroutines/EventLoop.decrementUseCount (Z)V
      // b4: aload 1
      // b5: athrow
   }

   internal override fun takeState(): Any? {
      val var1: Any = this._state;
      if (DebugKt.getASSERTIONS_ENABLED() && var1 === DispatchedContinuationKt.access$getUNDEFINED$p()) {
         throw new AssertionError();
      } else {
         this._state = DispatchedContinuationKt.access$getUNDEFINED$p();
         return var1;
      }
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder("DispatchedContinuation[");
      var1.append(this.dispatcher);
      var1.append(", ");
      var1.append(DebugStringsKt.toDebugString(this.continuation));
      var1.append(']');
      return var1.toString();
   }

   internal fun tryReleaseClaimedContinuation(continuation: CancellableContinuation<*>): Throwable? {
      val var3: AtomicReferenceFieldUpdater = _reusableCancellableContinuation$FU;

      do {
         val var2: Any = var3.get(this);
         if (var2 != DispatchedContinuationKt.REUSABLE_CLAIMED) {
            if (var2 is java.lang.Throwable) {
               if (ExternalSyntheticBackportWithForwarding0.m(_reusableCancellableContinuation$FU, this, var2, null)) {
                  return var2 as java.lang.Throwable;
               }

               throw new IllegalArgumentException("Failed requirement.".toString());
            }

            val var4: StringBuilder = new StringBuilder("Inconsistent state ");
            var4.append(var2);
            throw new IllegalStateException(var4.toString().toString());
         }
      } while (!ExternalSyntheticBackportWithForwarding0.m(_reusableCancellableContinuation$FU, this, DispatchedContinuationKt.REUSABLE_CLAIMED, var1));

      return null;
   }
}
