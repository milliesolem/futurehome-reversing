package kotlinx.coroutines

import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationKt
import kotlinx.coroutines.intrinsics.CancellableKt
import kotlinx.coroutines.intrinsics.UndispatchedKt

public enum class CoroutineStart {
   ATOMIC,
   DEFAULT,
   LAZY,
   UNDISPATCHED

   public final val isLazy: Boolean
      public final get() {
         val var1: Boolean;
         if (this === LAZY) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

   @JvmStatic
   private CoroutineStart[] $VALUES = $values();

   public operator fun <T> invoke(block: (Continuation<T>) -> Any?, completion: Continuation<T>) {
      val var3: Int = CoroutineStart.WhenMappings.$EnumSwitchMapping$0[this.ordinal()];
      if (var3 != 1) {
         if (var3 != 2) {
            if (var3 != 3) {
               if (var3 != 4) {
                  throw new NoWhenBranchMatchedException();
               }
            } else {
               UndispatchedKt.startCoroutineUndispatched(var1, var2);
            }
         } else {
            ContinuationKt.startCoroutine(var1, var2);
         }
      } else {
         CancellableKt.startCoroutineCancellable(var1, var2);
      }
   }

   public operator fun <R, T> invoke(block: (R, Continuation<T>) -> Any?, receiver: R, completion: Continuation<T>) {
      val var4: Int = CoroutineStart.WhenMappings.$EnumSwitchMapping$0[this.ordinal()];
      if (var4 != 1) {
         if (var4 != 2) {
            if (var4 != 3) {
               if (var4 != 4) {
                  throw new NoWhenBranchMatchedException();
               }
            } else {
               UndispatchedKt.startCoroutineUndispatched(var1, var2, var3);
            }
         } else {
            ContinuationKt.startCoroutine(var1, var2, var3);
         }
      } else {
         CancellableKt.startCoroutineCancellable$default(var1, var2, var3, null, 4, null);
      }
   }
}
