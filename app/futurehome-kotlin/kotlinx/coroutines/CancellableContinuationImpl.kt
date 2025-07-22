package kotlinx.coroutines

import androidx.concurrent.futures.AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.CoroutineStackFrame
import kotlinx.atomicfu.AtomicInt
import kotlinx.atomicfu.AtomicRef
import kotlinx.coroutines.internal.DispatchedContinuation
import kotlinx.coroutines.internal.Segment
import kotlinx.coroutines.internal.StackTraceRecoveryKt
import kotlinx.coroutines.internal.Symbol

internal open class CancellableContinuationImpl<T>(delegate: Continuation<Any>, resumeMode: Int) : DispatchedTask(var2),
   CancellableContinuation<T>,
   CoroutineStackFrame,
   Waiter {
   private final val _decisionAndIndex: AtomicInt
   private final val _parentHandle: AtomicRef<DisposableHandle?>
   private final val _state: AtomicRef<Any?>

   public open val callerFrame: CoroutineStackFrame?
      public open get() {
         val var2: CoroutineStackFrame;
         if (this.delegate is CoroutineStackFrame) {
            var2 = this.delegate as CoroutineStackFrame;
         } else {
            var2 = null;
         }

         return var2;
      }


   public open val context: CoroutineContext
   internal final val delegate: Continuation<Any>

   public open val isActive: Boolean
      public open get() {
         return this.getState$kotlinx_coroutines_core() is NotCompleted;
      }


   public open val isCancelled: Boolean
      public open get() {
         return this.getState$kotlinx_coroutines_core() is CancelledContinuation;
      }


   public open val isCompleted: Boolean
      public open get() {
         return this.getState$kotlinx_coroutines_core() is NotCompleted xor true;
      }


   private final val parentHandle: DisposableHandle?
      private final get() {
         return _parentHandle$FU.get(this) as DisposableHandle;
      }


   internal final val state: Any?
      internal final get() {
         return _state$FU.get(this);
      }


   private final val stateDebugRepresentation: String
      private final get() {
         var var1: java.lang.String = (java.lang.String)this.getState$kotlinx_coroutines_core();
         if (var1 is NotCompleted) {
            var1 = "Active";
         } else if (var1 is CancelledContinuation) {
            var1 = "Cancelled";
         } else {
            var1 = "Completed";
         }

         return var1;
      }


   init {
      this.delegate = var1;
      if (DebugKt.getASSERTIONS_ENABLED() && var2 == -1) {
         throw new AssertionError();
      } else {
         this.context = var1.getContext();
         this._decisionAndIndex = 536870911;
         this._state = Active.INSTANCE;
      }
   }

   private fun alreadyResumedError(proposedUpdate: Any?): Nothing {
      val var2: StringBuilder = new StringBuilder("Already resumed, but proposed with update ");
      var2.append(var1);
      throw new IllegalStateException(var2.toString().toString());
   }

   private fun callCancelHandler(handler: (Throwable?) -> Unit, cause: Throwable?) {
      try {
         var1.invoke(var2);
      } catch (var4: java.lang.Throwable) {
         val var7: CoroutineContext = this.getContext();
         val var6: StringBuilder = new StringBuilder("Exception in invokeOnCancellation handler for ");
         var6.append(this);
         CoroutineExceptionHandlerKt.handleCoroutineException(var7, new CompletionHandlerException(var6.toString(), var4));
         return;
      }
   }

   private inline fun callCancelHandlerSafely(block: () -> Unit) {
      try {
         var1.invoke();
      } catch (var4: java.lang.Throwable) {
         val var2: CoroutineContext = this.getContext();
         val var6: StringBuilder = new StringBuilder("Exception in invokeOnCancellation handler for ");
         var6.append(this);
         CoroutineExceptionHandlerKt.handleCoroutineException(var2, new CompletionHandlerException(var6.toString(), var4));
         return;
      }
   }

   private fun callSegmentOnCancellation(segment: Segment<*>, cause: Throwable?) {
      val var3: Int = _decisionAndIndex$FU.get(this) and 536870911;
      if (var3 != 536870911) {
         try {
            var1.onCancellation(var3, var2, this.getContext());
         } catch (var5: java.lang.Throwable) {
            val var7: CoroutineContext = this.getContext();
            val var8: StringBuilder = new StringBuilder("Exception in invokeOnCancellation handler for ");
            var8.append(this);
            CoroutineExceptionHandlerKt.handleCoroutineException(var7, new CompletionHandlerException(var8.toString(), var5));
            return;
         }
      } else {
         throw new IllegalStateException("The index for Segment.onCancellation(..) is broken".toString());
      }
   }

   private fun cancelLater(cause: Throwable): Boolean {
      if (!this.isReusable()) {
         return false;
      } else {
         val var2: Continuation = this.delegate;
         return (var2 as DispatchedContinuation).postponeCancellation$kotlinx_coroutines_core(var1);
      }
   }

   private fun detachChildIfNonResuable() {
      if (!this.isReusable()) {
         this.detachChild$kotlinx_coroutines_core();
      }
   }

   private fun dispatchResume(mode: Int) {
      if (!this.tryResume()) {
         DispatchedTaskKt.dispatch(this, var1);
      }
   }

   private fun installParentHandle(): DisposableHandle? {
      val var1: Job = this.getContext().get(Job.Key);
      if (var1 == null) {
         return null;
      } else {
         val var2: DisposableHandle = Job.DefaultImpls.invokeOnCompletion$default(var1, true, false, new ChildContinuation(this), 2, null);
         ExternalSyntheticBackportWithForwarding0.m(_parentHandle$FU, this, null, var2);
         return var2;
      }
   }

   private fun invokeOnCancellationImpl(handler: Any) {
      if (DebugKt.getASSERTIONS_ENABLED() && var1 !is CancelHandler && var1 !is Segment) {
         throw new AssertionError();
      } else {
         val var3: AtomicReferenceFieldUpdater = _state$FU;

         while (true) {
            var var4: Any = var3.get(this);
            if (var4 is Active) {
               if (ExternalSyntheticBackportWithForwarding0.m(_state$FU, this, var4, var1)) {
                  return;
               }
            } else {
               var var2: Boolean;
               if (var4 is CancelHandler) {
                  var2 = true;
               } else {
                  var2 = var4 is Segment;
               }

               if (var2) {
                  this.multipleHandlersError(var1, var4);
               } else {
                  var2 = var4 is CompletedExceptionally;
                  if (var4 is CompletedExceptionally) {
                     var var8: CompletedExceptionally = var4 as CompletedExceptionally;
                     if (!(var4 as CompletedExceptionally).makeHandled()) {
                        this.multipleHandlersError(var1, var4);
                     }

                     if (var4 is CancelledContinuation) {
                        var4 = null;
                        if (!var2) {
                           var8 = null;
                        }

                        if (var8 != null) {
                           var4 = var8.cause;
                        }

                        if (var1 is CancelHandler) {
                           this.callCancelHandler(var1 as CancelHandler, (java.lang.Throwable)var4);
                        } else {
                           this.callSegmentOnCancellation(var1 as Segment<?>, (java.lang.Throwable)var4);
                        }
                     }

                     return;
                  }

                  if (var4 is CompletedContinuation) {
                     val var6: CompletedContinuation = var4 as CompletedContinuation;
                     if ((var4 as CompletedContinuation).cancelHandler != null) {
                        this.multipleHandlersError(var1, var4);
                     }

                     if (var1 is Segment) {
                        return;
                     }

                     val var5: CancelHandler = var1 as CancelHandler;
                     if (var6.getCancelled()) {
                        this.callCancelHandler(var5, var6.cancelCause);
                        return;
                     }

                     if (ExternalSyntheticBackportWithForwarding0.m(
                        _state$FU, this, var4, CompletedContinuation.copy$default(var6, null, var5, null, null, null, 29, null)
                     )) {
                        return;
                     }
                  } else {
                     if (var1 is Segment) {
                        return;
                     }

                     if (ExternalSyntheticBackportWithForwarding0.m(
                        _state$FU, this, var4, new CompletedContinuation(var4, var1 as CancelHandler, null, null, null, 28, null)
                     )) {
                        return;
                     }
                  }
               }
            }
         }
      }
   }

   private fun isReusable(): Boolean {
      if (DispatchedTaskKt.isReusableMode(this.resumeMode)) {
         val var2: Continuation = this.delegate;
         if ((var2 as DispatchedContinuation).isReusable$kotlinx_coroutines_core()) {
            return true;
         }
      }

      return false;
   }

   fun `loop$atomicfu`(var1: AtomicIntegerFieldUpdater, var2: (Int?) -> Unit, var3: Any) {
      while (true) {
         var2.invoke(var1.get(var3));
      }
   }

   fun `loop$atomicfu`(var1: AtomicReferenceFieldUpdater, var2: (Any?) -> Unit, var3: Any) {
      while (true) {
         var2.invoke(var1.get(var3));
      }
   }

   private fun makeCancelHandler(handler: (Throwable?) -> Unit): CancelHandler {
      val var2: CancelHandler;
      if (var1 is CancelHandler) {
         var2 = var1 as CancelHandler;
      } else {
         var2 = new InvokeOnCancel(var1);
      }

      return var2;
   }

   private fun multipleHandlersError(handler: Any, state: Any?) {
      val var3: StringBuilder = new StringBuilder("It's prohibited to register multiple handlers, tried to register ");
      var3.append(var1);
      var3.append(", already has ");
      var3.append(var2);
      throw new IllegalStateException(var3.toString().toString());
   }

   private fun resumeImpl(proposedUpdate: Any?, resumeMode: Int, onCancellation: ((Throwable) -> Unit)? = null) {
      val var5: AtomicReferenceFieldUpdater = _state$FU;

      val var6: Any;
      do {
         var6 = var5.get(this);
         if (var6 !is NotCompleted) {
            if (var6 is CancelledContinuation) {
               val var7: CancelledContinuation = var6 as CancelledContinuation;
               if ((var6 as CancelledContinuation).makeResumed()) {
                  if (var3 != null) {
                     this.callOnCancellation(var3, var7.cause);
                  }

                  return;
               }
            }

            this.alreadyResumedError(var1);
            throw new KotlinNothingValueException();
         }
      } while (!ExternalSyntheticBackportWithForwarding0.m(_state$FU, this, var6, this.resumedState((NotCompleted)var6, var1, var2, var3, null)));

      this.detachChildIfNonResuable();
      this.dispatchResume(var2);
   }

   private fun resumedState(state: NotCompleted, proposedUpdate: Any?, resumeMode: Int, onCancellation: ((Throwable) -> Unit)?, idempotent: Any?): Any? {
      var var6: Any;
      if (var2 is CompletedExceptionally) {
         if (DebugKt.getASSERTIONS_ENABLED() && var5 != null) {
            throw new AssertionError();
         }

         var6 = var2;
         if (DebugKt.getASSERTIONS_ENABLED()) {
            if (var4 != null) {
               throw new AssertionError();
            }

            var6 = var2;
         }
      } else if (!DispatchedTaskKt.isCancellableMode(var3) && var5 == null) {
         var6 = var2;
      } else {
         if (var4 == null && var1 !is CancelHandler && var5 == null) {
            return var2;
         }

         val var7: CancelHandler;
         if (var1 is CancelHandler) {
            var7 = var1 as CancelHandler;
         } else {
            var7 = null;
         }

         var6 = new CompletedContinuation(var2, var7, var4, var5, null, 16, null);
      }

      return var6;
   }

   private fun tryResume(): Boolean {
      val var3: AtomicIntegerFieldUpdater = _decisionAndIndex$FU;

      val var2: Int;
      do {
         var2 = var3.get(this);
         val var1: Int = var2 shr 29;
         if (var2 shr 29 != 0) {
            if (var1 == 1) {
               return false;
            } else {
               throw new IllegalStateException("Already resumed".toString());
            }
         }
      } while (!_decisionAndIndex$FU.compareAndSet(this, var2, 1073741824 + (536870911 & var2)));

      return true;
   }

   private fun tryResumeImpl(proposedUpdate: Any?, idempotent: Any?, onCancellation: ((Throwable) -> Unit)?): Symbol? {
      val var5: AtomicReferenceFieldUpdater = _state$FU;

      var var6: Any;
      do {
         var6 = var5.get(this);
         if (var6 !is NotCompleted) {
            val var4: Boolean = var6 is CompletedContinuation;
            var var8: Symbol = null;
            if (var4) {
               var8 = null;
               if (var2 != null) {
                  var6 = var6 as CompletedContinuation;
                  var8 = null;
                  if (((CompletedContinuation)var6).idempotentResume === var2) {
                     if (DebugKt.getASSERTIONS_ENABLED() && !(((CompletedContinuation)var6).result == var1)) {
                        throw new AssertionError();
                     }

                     var8 = CancellableContinuationImplKt.RESUME_TOKEN;
                  }
               }
            }

            return var8;
         }
      } while (!ExternalSyntheticBackportWithForwarding0.m(_state$FU, this, var6, this.resumedState((NotCompleted)var6, var1, this.resumeMode, var3, var2)));

      this.detachChildIfNonResuable();
      return CancellableContinuationImplKt.RESUME_TOKEN;
   }

   private fun trySuspend(): Boolean {
      val var3: AtomicIntegerFieldUpdater = _decisionAndIndex$FU;

      val var2: Int;
      do {
         var2 = var3.get(this);
         val var1: Int = var2 shr 29;
         if (var2 shr 29 != 0) {
            if (var1 == 2) {
               return false;
            } else {
               throw new IllegalStateException("Already suspended".toString());
            }
         }
      } while (!_decisionAndIndex$FU.compareAndSet(this, var2, 536870912 + (536870911 & var2)));

      return true;
   }

   fun `update$atomicfu`(var1: AtomicIntegerFieldUpdater, var2: (Int?) -> Int, var3: Any) {
      val var4: Int;
      do {
         var4 = var1.get(var3);
      } while (!var1.compareAndSet(var3, var4, ((java.lang.Number)var2.invoke(var4)).intValue()));
   }

   public fun callCancelHandler(handler: CancelHandler, cause: Throwable?) {
      try {
         var1.invoke(var2);
      } catch (var4: java.lang.Throwable) {
         val var6: CoroutineContext = this.getContext();
         val var3: StringBuilder = new StringBuilder("Exception in invokeOnCancellation handler for ");
         var3.append(this);
         CoroutineExceptionHandlerKt.handleCoroutineException(var6, new CompletionHandlerException(var3.toString(), var4));
         return;
      }
   }

   public fun callOnCancellation(onCancellation: (Throwable) -> Unit, cause: Throwable) {
      try {
         var1.invoke(var2);
      } catch (var4: java.lang.Throwable) {
         val var6: CoroutineContext = this.getContext();
         val var3: StringBuilder = new StringBuilder("Exception in resume onCancellation handler for ");
         var3.append(this);
         CoroutineExceptionHandlerKt.handleCoroutineException(var6, new CompletionHandlerException(var3.toString(), var4));
         return;
      }
   }

   public override fun cancel(cause: Throwable?): Boolean {
      val var5: AtomicReferenceFieldUpdater = _state$FU;

      var var2: Boolean;
      val var4: Any;
      val var6: Continuation;
      do {
         var4 = var5.get(this);
         val var3: Boolean = var4 is NotCompleted;
         var2 = false;
         if (!var3) {
            return false;
         }

         var6 = this;
         if (var4 is CancelHandler || var4 is Segment) {
            var2 = true;
         }
      } while (!ExternalSyntheticBackportWithForwarding0.m(_state$FU, this, var4, new CancelledContinuation(var6, var1, var2)));

      val var7: NotCompleted = var4 as NotCompleted;
      if (var4 as NotCompleted is CancelHandler) {
         this.callCancelHandler(var4 as CancelHandler, var1);
      } else if (var7 is Segment) {
         this.callSegmentOnCancellation(var4 as Segment<?>, var1);
      }

      this.detachChildIfNonResuable();
      this.dispatchResume(this.resumeMode);
      return true;
   }

   internal override fun cancelCompletedResult(takenState: Any?, cause: Throwable) {
      val var4: AtomicReferenceFieldUpdater = _state$FU;

      while (true) {
         val var5: Any = var4.get(this);
         if (var5 is NotCompleted) {
            throw new IllegalStateException("Not completed".toString());
         }

         if (var5 is CompletedExceptionally) {
            return;
         }

         if (var5 is CompletedContinuation) {
            val var3: CompletedContinuation = var5 as CompletedContinuation;
            if ((var5 as CompletedContinuation).getCancelled()) {
               throw new IllegalStateException("Must be called at most once".toString());
            }

            if (ExternalSyntheticBackportWithForwarding0.m(
               _state$FU, this, var5, CompletedContinuation.copy$default(var3, null, null, null, null, var2, 15, null)
            )) {
               var3.invokeHandlers(this, var2);
               return;
            }
         } else if (ExternalSyntheticBackportWithForwarding0.m(_state$FU, this, var5, new CompletedContinuation(var5, null, null, null, var2, 14, null))) {
            return;
         }
      }
   }

   public override fun completeResume(token: Any) {
      if (DebugKt.getASSERTIONS_ENABLED() && var1 != CancellableContinuationImplKt.RESUME_TOKEN) {
         throw new AssertionError();
      } else {
         this.dispatchResume(this.resumeMode);
      }
   }

   internal fun detachChild() {
      val var1: DisposableHandle = this.getParentHandle();
      if (var1 != null) {
         var1.dispose();
         _parentHandle$FU.set(this, NonDisposableHandle.INSTANCE);
      }
   }

   public open fun getContinuationCancellationCause(parent: Job): Throwable {
      return var1.getCancellationException();
   }

   internal override fun getExceptionalResult(state: Any?): Throwable? {
      val var2: java.lang.Throwable = super.getExceptionalResult$kotlinx_coroutines_core(var1);
      if (var2 != null) {
         val var3: Continuation = this.delegate;
         var1 = var2;
         if (DebugKt.getRECOVER_STACK_TRACES()) {
            if (var3 !is CoroutineStackFrame) {
               var1 = var2;
            } else {
               var1 = StackTraceRecoveryKt.access$recoverFromStackFrame(var2, var3 as CoroutineStackFrame);
            }
         }
      } else {
         var1 = null;
      }

      return var1;
   }

   internal fun getResult(): Any? {
      val var1: Boolean = this.isReusable();
      if (this.trySuspend()) {
         if (this.getParentHandle() == null) {
            this.installParentHandle();
         }

         if (var1) {
            this.releaseClaimedReusableContinuation$kotlinx_coroutines_core();
         }

         return IntrinsicsKt.getCOROUTINE_SUSPENDED();
      } else {
         if (var1) {
            this.releaseClaimedReusableContinuation$kotlinx_coroutines_core();
         }

         var var2: Any = this.getState$kotlinx_coroutines_core();
         if (var2 is CompletedExceptionally) {
            val var9: java.lang.Throwable = (var2 as CompletedExceptionally).cause;
            var2 = (var2 as CompletedExceptionally).cause;
            if (DebugKt.getRECOVER_STACK_TRACES()) {
               var2 = this;
               if (this !is CoroutineStackFrame) {
                  var2 = var9;
               } else {
                  var2 = StackTraceRecoveryKt.access$recoverFromStackFrame(var9, var2 as CoroutineStackFrame);
               }
            }

            throw var2;
         } else {
            if (DispatchedTaskKt.isCancellableMode(this.resumeMode)) {
               val var3: Job = this.getContext().get(Job.Key);
               if (var3 != null && !var3.isActive()) {
                  val var8: java.lang.Throwable = var3.getCancellationException();
                  this.cancelCompletedResult$kotlinx_coroutines_core(var2, var8);
                  var2 = var8;
                  if (DebugKt.getRECOVER_STACK_TRACES()) {
                     var2 = this;
                     if (this !is CoroutineStackFrame) {
                        var2 = var8;
                     } else {
                        var2 = StackTraceRecoveryKt.access$recoverFromStackFrame(var8, var2 as CoroutineStackFrame);
                     }
                  }

                  throw var2;
               }
            }

            return this.getSuccessfulResult$kotlinx_coroutines_core(var2);
         }
      }
   }

   public override fun getStackTraceElement(): StackTraceElement? {
      return null;
   }

   internal override fun <T> getSuccessfulResult(state: Any?): T {
      var var2: Any = var1;
      if (var1 is CompletedContinuation) {
         var2 = (var1 as CompletedContinuation).result;
      }

      return (T)var2;
   }

   public override fun initCancellability() {
      val var1: DisposableHandle = this.installParentHandle();
      if (var1 != null) {
         if (this.isCompleted()) {
            var1.dispose();
            _parentHandle$FU.set(this, NonDisposableHandle.INSTANCE);
         }
      }
   }

   public override fun invokeOnCancellation(handler: (Throwable?) -> Unit) {
      this.invokeOnCancellationImpl(this.makeCancelHandler(var1));
   }

   public override fun invokeOnCancellation(segment: Segment<*>, index: Int) {
      val var4: AtomicIntegerFieldUpdater = _decisionAndIndex$FU;

      val var3: Int;
      do {
         var3 = var4.get(this);
         if ((var3 and 536870911) != 536870911) {
            throw new IllegalStateException("invokeOnCancellation should be called at most once".toString());
         }
      } while (!var4.compareAndSet(this, var3, (var3 >> 29 << 29) + var2));

      this.invokeOnCancellationImpl(var1);
   }

   protected open fun nameString(): String {
      return "CancellableContinuation";
   }

   internal fun parentCancelled(cause: Throwable) {
      if (!this.cancelLater(var1)) {
         this.cancel(var1);
         this.detachChildIfNonResuable();
      }
   }

   internal fun releaseClaimedReusableContinuation() {
      val var2: DispatchedContinuation;
      if (this.delegate is DispatchedContinuation) {
         var2 = this.delegate as DispatchedContinuation;
      } else {
         var2 = null;
      }

      if (var2 != null) {
         val var3: java.lang.Throwable = var2.tryReleaseClaimedContinuation$kotlinx_coroutines_core(this);
         if (var3 != null) {
            this.detachChild$kotlinx_coroutines_core();
            this.cancel(var3);
         }
      }
   }

   internal fun resetStateReusable(): Boolean {
      if (DebugKt.getASSERTIONS_ENABLED() && this.resumeMode != 2) {
         throw new AssertionError();
      } else if (DebugKt.getASSERTIONS_ENABLED() && this.getParentHandle() === NonDisposableHandle.INSTANCE) {
         throw new AssertionError();
      } else {
         val var2: AtomicReferenceFieldUpdater = _state$FU;
         val var1: Any = _state$FU.get(this);
         if (DebugKt.getASSERTIONS_ENABLED() && var1 is NotCompleted) {
            throw new AssertionError();
         } else if (var1 is CompletedContinuation && (var1 as CompletedContinuation).idempotentResume != null) {
            this.detachChild$kotlinx_coroutines_core();
            return false;
         } else {
            _decisionAndIndex$FU.set(this, 536870911);
            var2.set(this, Active.INSTANCE);
            return true;
         }
      }
   }

   public override fun resume(value: Any, onCancellation: ((Throwable) -> Unit)?) {
      this.resumeImpl(var1, this.resumeMode, var2);
   }

   public override fun CoroutineDispatcher.resumeUndispatched(value: Any) {
      val var4: Boolean = this.delegate is DispatchedContinuation;
      var var6: CoroutineDispatcher = null;
      val var7: DispatchedContinuation;
      if (var4) {
         var7 = this.delegate as DispatchedContinuation;
      } else {
         var7 = null;
      }

      if (var7 != null) {
         var6 = var7.dispatcher;
      }

      val var3: Int;
      if (var6 === var1) {
         var3 = 4;
      } else {
         var3 = this.resumeMode;
      }

      resumeImpl$default(this, var2, var3, null, 4, null);
   }

   public override fun CoroutineDispatcher.resumeUndispatchedWithException(exception: Throwable) {
      val var9: DispatchedContinuation;
      if (this.delegate is DispatchedContinuation) {
         var9 = this.delegate as DispatchedContinuation;
      } else {
         var9 = null;
      }

      val var7: CompletedExceptionally = new CompletedExceptionally(var2, false, 2, null);
      var var8: CoroutineDispatcher = null;
      if (var9 != null) {
         var8 = var9.dispatcher;
      }

      val var3: Int;
      if (var8 === var1) {
         var3 = 4;
      } else {
         var3 = this.resumeMode;
      }

      resumeImpl$default(this, var7, var3, null, 4, null);
   }

   public override fun resumeWith(result: Result<Any>) {
      resumeImpl$default(this, CompletionStateKt.toState(var1, this), this.resumeMode, null, 4, null);
   }

   internal override fun takeState(): Any? {
      return this.getState$kotlinx_coroutines_core();
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder();
      var1.append(this.nameString());
      var1.append('(');
      var1.append(DebugStringsKt.toDebugString(this.delegate));
      var1.append("){");
      var1.append(this.getStateDebugRepresentation());
      var1.append("}@");
      var1.append(DebugStringsKt.getHexAddress(this));
      return var1.toString();
   }

   public override fun tryResume(value: Any, idempotent: Any?): Any? {
      return this.tryResumeImpl(var1, var2, null);
   }

   public override fun tryResume(value: Any, idempotent: Any?, onCancellation: ((Throwable) -> Unit)?): Any? {
      return this.tryResumeImpl(var1, var2, var3);
   }

   public override fun tryResumeWithException(exception: Throwable): Any? {
      return this.tryResumeImpl(new CompletedExceptionally(var1, false, 2, null), null, null);
   }
}
