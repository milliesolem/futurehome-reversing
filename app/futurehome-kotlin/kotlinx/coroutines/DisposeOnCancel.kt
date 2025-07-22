package kotlinx.coroutines

private class DisposeOnCancel(handle: DisposableHandle) : CancelHandler {
   private final val handle: DisposableHandle

   init {
      this.handle = var1;
   }

   public override operator fun invoke(cause: Throwable?) {
      this.handle.dispose();
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder("DisposeOnCancel[");
      var1.append(this.handle);
      var1.append(']');
      return var1.toString();
   }
}
