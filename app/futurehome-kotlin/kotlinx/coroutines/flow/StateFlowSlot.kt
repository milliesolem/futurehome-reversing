package kotlinx.coroutines.flow

import androidx.concurrent.futures.AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater
import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.DebugProbesKt
import kotlinx.atomicfu.AtomicRef
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.CancellableContinuationImpl
import kotlinx.coroutines.DebugKt
import kotlinx.coroutines.flow.internal.AbstractSharedFlowKt
import kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot

private class StateFlowSlot : AbstractSharedFlowSlot<StateFlowImpl<?>> {
   private final val _state: AtomicRef<Any?>

   fun `loop$atomicfu`(var1: AtomicReferenceFieldUpdater, var2: (Any?) -> Unit, var3: Any) {
      while (true) {
         var2.invoke(var1.get(var3));
      }
   }

   public open fun allocateLocked(flow: StateFlowImpl<*>): Boolean {
      val var2: AtomicReferenceFieldUpdater = _state$FU;
      if (_state$FU.get(this) != null) {
         return false;
      } else {
         var2.set(this, StateFlowKt.access$getNONE$p());
         return true;
      }
   }

   public suspend fun awaitPending() {
      val var2: CancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(var1), 1);
      var2.initCancellability();
      val var3: CancellableContinuation = var2;
      if (DebugKt.getASSERTIONS_ENABLED() && access$get_state$FU$p().get(this) is CancellableContinuationImpl) {
         throw new AssertionError();
      } else {
         if (!ExternalSyntheticBackportWithForwarding0.m(access$get_state$FU$p(), this, StateFlowKt.access$getNONE$p(), var3)) {
            if (DebugKt.getASSERTIONS_ENABLED() && access$get_state$FU$p().get(this) != StateFlowKt.access$getPENDING$p()) {
               throw new AssertionError();
            }

            val var6: Continuation = var3;
            val var4: Result.Companion = Result.Companion;
            var6.resumeWith(Result.constructor-impl(Unit.INSTANCE));
         }

         val var5: Any = var2.getResult();
         if (var5 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(var1);
         }

         return if (var5 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var5 else Unit.INSTANCE;
      }
   }

   public open fun freeLocked(flow: StateFlowImpl<*>): Array<Continuation<Unit>?> {
      _state$FU.set(this, null);
      return AbstractSharedFlowKt.EMPTY_RESUMES;
   }

   public fun makePending() {
      val var2: AtomicReferenceFieldUpdater = _state$FU;

      while (true) {
         var var1: Any = var2.get(this);
         if (var1 == null) {
            return;
         }

         if (var1 === StateFlowKt.access$getPENDING$p()) {
            return;
         }

         if (var1 === StateFlowKt.access$getNONE$p()) {
            if (ExternalSyntheticBackportWithForwarding0.m(_state$FU, this, var1, StateFlowKt.access$getPENDING$p())) {
               return;
            }
         } else if (ExternalSyntheticBackportWithForwarding0.m(_state$FU, this, var1, StateFlowKt.access$getNONE$p())) {
            val var4: Continuation = var1 as CancellableContinuationImpl;
            var1 = Result.Companion;
            var4.resumeWith(Result.constructor-impl(Unit.INSTANCE));
            return;
         }
      }
   }

   public fun takePending(): Boolean {
      val var2: Any = _state$FU.getAndSet(this, StateFlowKt.access$getNONE$p());
      if (DebugKt.getASSERTIONS_ENABLED() && var2 is CancellableContinuationImpl) {
         throw new AssertionError();
      } else {
         val var1: Boolean;
         if (var2 === StateFlowKt.access$getPENDING$p()) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }
   }
}
