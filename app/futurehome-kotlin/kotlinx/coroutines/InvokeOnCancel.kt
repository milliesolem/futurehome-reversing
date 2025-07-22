package kotlinx.coroutines

private class InvokeOnCancel(handler: (Throwable?) -> Unit) : CancelHandler {
   private final val handler: (Throwable?) -> Unit

   init {
      this.handler = var1;
   }

   public override operator fun invoke(cause: Throwable?) {
      this.handler.invoke(var1);
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder("InvokeOnCancel[");
      var1.append(DebugStringsKt.getClassSimpleName(this.handler));
      var1.append('@');
      var1.append(DebugStringsKt.getHexAddress(this));
      var1.append(']');
      return var1.toString();
   }
}
