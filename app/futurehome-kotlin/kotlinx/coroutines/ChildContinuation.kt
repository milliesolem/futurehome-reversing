package kotlinx.coroutines

internal class ChildContinuation(child: CancellableContinuationImpl<*>) : JobCancellingNode {
   public final val child: CancellableContinuationImpl<*>

   init {
      this.child = var1;
   }

   public override operator fun invoke(cause: Throwable?) {
      this.child.parentCancelled$kotlinx_coroutines_core(this.child.getContinuationCancellationCause(this.getJob()));
   }
}
