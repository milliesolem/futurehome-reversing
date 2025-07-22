package kotlinx.coroutines

private class SupervisorJobImpl(parent: Job?) : JobImpl(var1) {
   public override fun childCancelled(cause: Throwable): Boolean {
      return false;
   }
}
