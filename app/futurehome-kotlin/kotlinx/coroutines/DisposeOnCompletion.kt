package kotlinx.coroutines

internal class DisposeOnCompletion(handle: DisposableHandle) : JobNode {
   private final val handle: DisposableHandle

   init {
      this.handle = var1;
   }

   public override operator fun invoke(cause: Throwable?) {
      this.handle.dispose();
   }
}
