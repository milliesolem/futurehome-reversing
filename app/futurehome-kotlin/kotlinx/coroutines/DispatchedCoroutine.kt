package kotlinx.coroutines

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlinx.atomicfu.AtomicInt
import kotlinx.coroutines.internal.DispatchedContinuationKt
import kotlinx.coroutines.internal.ScopeCoroutine

internal class DispatchedCoroutine<T> internal constructor(context: CoroutineContext, uCont: Continuation<Any>) : ScopeCoroutine(var1, var2) {
   public final val _decision: AtomicInt

   @JvmStatic
   fun `get_decision$FU`(): AtomicIntegerFieldUpdater {
      return _decision$FU;
   }

   fun `loop$atomicfu`(var1: AtomicIntegerFieldUpdater, var2: (Int?) -> Unit, var3: Any) {
      while (true) {
         var2.invoke(var1.get(var3));
      }
   }

   private fun tryResume(): Boolean {
      val var2: AtomicIntegerFieldUpdater = _decision$FU;

      do {
         val var1: Int = var2.get(this);
         if (var1 != 0) {
            if (var1 == 1) {
               return false;
            } else {
               throw new IllegalStateException("Already resumed".toString());
            }
         }
      } while (!_decision$FU.compareAndSet(this, 0, 2));

      return true;
   }

   private fun trySuspend(): Boolean {
      val var2: AtomicIntegerFieldUpdater = _decision$FU;

      do {
         val var1: Int = var2.get(this);
         if (var1 != 0) {
            if (var1 == 2) {
               return false;
            } else {
               throw new IllegalStateException("Already suspended".toString());
            }
         }
      } while (!_decision$FU.compareAndSet(this, 0, 1));

      return true;
   }

   protected override fun afterCompletion(state: Any?) {
      this.afterResume(var1);
   }

   protected override fun afterResume(state: Any?) {
      if (!this.tryResume()) {
         DispatchedContinuationKt.resumeCancellableWith$default(
            IntrinsicsKt.intercepted(this.uCont), CompletionStateKt.recoverResult(var1, this.uCont), null, 2, null
         );
      }
   }

   internal fun getResult(): Any? {
      if (this.trySuspend()) {
         return IntrinsicsKt.getCOROUTINE_SUSPENDED();
      } else {
         val var1: Any = JobSupportKt.unboxState(this.getState$kotlinx_coroutines_core());
         if (var1 !is CompletedExceptionally) {
            return var1;
         } else {
            throw (var1 as CompletedExceptionally).cause;
         }
      }
   }
}
