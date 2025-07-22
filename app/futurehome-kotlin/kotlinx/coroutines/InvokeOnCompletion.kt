package kotlinx.coroutines

private class InvokeOnCompletion(handler: (Throwable?) -> Unit) : JobNode {
   private final val handler: (Throwable?) -> Unit

   init {
      this.handler = var1;
   }

   public override operator fun invoke(cause: Throwable?) {
      this.handler.invoke(var1);
   }
}
