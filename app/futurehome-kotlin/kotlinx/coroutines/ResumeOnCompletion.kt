package kotlinx.coroutines

import kotlin.coroutines.Continuation

private class ResumeOnCompletion(continuation: Continuation<Unit>) : JobNode {
   private final val continuation: Continuation<Unit>

   init {
      this.continuation = var1;
   }

   public override operator fun invoke(cause: Throwable?) {
      val var3: Result.Companion = Result.Companion;
      this.continuation.resumeWith(Result.constructor-impl(Unit.INSTANCE));
   }
}
