package kotlin.coroutines

import androidx.concurrent.futures.AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater
import kotlin.coroutines.intrinsics.CoroutineSingletons
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.CoroutineStackFrame

internal class SafeContinuation<T> internal constructor(delegate: Continuation<Any>, initialResult: Any?) : Continuation<T>, CoroutineStackFrame {
   private final val delegate: Continuation<Any>

   public open val context: CoroutineContext
      public open get() {
         return this.delegate.getContext();
      }


   private final var result: Any?

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


   internal constructor(delegate: Continuation<Any>) : this(var1, CoroutineSingletons.UNDECIDED)
   init {
      this.delegate = var1;
      this.result = var2;
   }

   internal fun getOrThrow(): Any? {
      var var1: Any = this.result;
      if (this.result === CoroutineSingletons.UNDECIDED) {
         if (ExternalSyntheticBackportWithForwarding0.m(RESULT, this, CoroutineSingletons.UNDECIDED, IntrinsicsKt.getCOROUTINE_SUSPENDED())) {
            return IntrinsicsKt.getCOROUTINE_SUSPENDED();
         }

         var1 = this.result;
      }

      if (var1 === CoroutineSingletons.RESUMED) {
         var1 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
      } else if (var1 is Result.Failure) {
         throw (var1 as Result.Failure).exception;
      }

      return var1;
   }

   public override fun getStackTraceElement(): StackTraceElement? {
      return null;
   }

   public override fun resumeWith(result: Result<Any>) {
      while (true) {
         if (this.result === CoroutineSingletons.UNDECIDED) {
            if (ExternalSyntheticBackportWithForwarding0.m(RESULT, this, CoroutineSingletons.UNDECIDED, var1)) {
               return;
            }
         } else {
            if (this.result === IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
               if (!ExternalSyntheticBackportWithForwarding0.m(RESULT, this, IntrinsicsKt.getCOROUTINE_SUSPENDED(), CoroutineSingletons.RESUMED)) {
                  continue;
               }

               this.delegate.resumeWith(var1);
               return;
            }

            throw new IllegalStateException("Already resumed");
         }
      }
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder("SafeContinuation for ");
      var1.append(this.delegate);
      return var1.toString();
   }

   private companion object {
      private final val RESULT: AtomicReferenceFieldUpdater<SafeContinuation<*>, Any>
   }
}
