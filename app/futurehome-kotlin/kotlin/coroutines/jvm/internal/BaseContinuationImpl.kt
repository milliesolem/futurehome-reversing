package kotlin.coroutines.jvm.internal

import java.io.Serializable
import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.IntrinsicsKt

internal abstract class BaseContinuationImpl : Continuation<Object>, CoroutineStackFrame, Serializable {
   public final val completion: Continuation<Any?>?

   public open val callerFrame: CoroutineStackFrame?
      public open get() {
         val var2: CoroutineStackFrame;
         if (this.completion is CoroutineStackFrame) {
            var2 = this.completion as CoroutineStackFrame;
         } else {
            var2 = null;
         }

         return var2;
      }


   open fun BaseContinuationImpl(var1: Continuation<Object>) {
      this.completion = var1;
   }

   public open fun create(value: Any?, completion: Continuation<*>): Continuation<Unit> {
      throw new UnsupportedOperationException("create(Any?;Continuation) has not been overridden");
   }

   public open fun create(completion: Continuation<*>): Continuation<Unit> {
      throw new UnsupportedOperationException("create(Continuation) has not been overridden");
   }

   public override fun getStackTraceElement(): StackTraceElement? {
      return DebugMetadataKt.getStackTraceElement(this);
   }

   protected abstract fun invokeSuspend(result: Result<Any?>): Any? {
   }

   protected open fun releaseIntercepted() {
   }

   public override fun resumeWith(result: Result<Any?>) {
      var var2: Any = this;

      do {
         DebugProbesKt.probeCoroutineResumed(var2 as Continuation<?>);
         val var3: BaseContinuationImpl = var2 as BaseContinuationImpl;
         var2 = (var2 as BaseContinuationImpl).completion;

         label22:
         try {
            var1 = var3.invokeSuspend(var1);
            if (var1 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
               return;
            }

            val var4: Result.Companion = Result.Companion;
            var1 = (Result.Companion)Result.constructor-impl(var1);
         } catch (var5: java.lang.Throwable) {
            var1 = Result.Companion;
            var1 = (Result.Companion)Result.constructor-impl(ResultKt.createFailure(var5));
            break label22;
         }

         var3.releaseIntercepted();
      } while (var2 instanceof BaseContinuationImpl);

      ((Continuation)var2).resumeWith(var1);
   }

   public override fun toString(): String {
      val var2: StringBuilder = new StringBuilder("Continuation at ");
      var var1: Any = this.getStackTraceElement();
      if (var1 == null) {
         var1 = this.getClass().getName();
      }

      var2.append(var1 as Serializable);
      return var2.toString();
   }
}
