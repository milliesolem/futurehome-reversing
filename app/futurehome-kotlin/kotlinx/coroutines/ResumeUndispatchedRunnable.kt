package kotlinx.coroutines

private class ResumeUndispatchedRunnable(dispatcher: CoroutineDispatcher, continuation: CancellableContinuation<Unit>) : Runnable {
   private final val continuation: CancellableContinuation<Unit>
   private final val dispatcher: CoroutineDispatcher

   init {
      this.dispatcher = var1;
      this.continuation = var2;
   }

   public override fun run() {
      this.continuation.resumeUndispatched(this.dispatcher, Unit.INSTANCE);
   }
}
