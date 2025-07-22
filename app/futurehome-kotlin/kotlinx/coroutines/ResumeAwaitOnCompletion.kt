package kotlinx.coroutines

import kotlin.coroutines.Continuation

private class ResumeAwaitOnCompletion<T>(continuation: CancellableContinuationImpl<Any>) : JobNode {
   private final val continuation: CancellableContinuationImpl<Any>

   init {
      this.continuation = var1;
   }

   public override operator fun invoke(cause: Throwable?) {
      val var4: Any = this.getJob().getState$kotlinx_coroutines_core();
      if (DebugKt.getASSERTIONS_ENABLED() && var4 is Incomplete) {
         throw new AssertionError();
      } else {
         if (var4 is CompletedExceptionally) {
            val var3: Continuation = this.continuation;
            val var2: Result.Companion = Result.Companion;
            var3.resumeWith(Result.constructor-impl(ResultKt.createFailure((var4 as CompletedExceptionally).cause)));
         } else {
            val var5: Continuation = this.continuation;
            val var6: Result.Companion = Result.Companion;
            var5.resumeWith(Result.constructor-impl(JobSupportKt.unboxState(var4)));
         }
      }
   }
}
