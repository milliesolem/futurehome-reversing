package kotlinx.coroutines

import kotlinx.atomicfu.AtomicInt

private class InvokeOnCancelling(handler: (Throwable?) -> Unit) : JobCancellingNode {
   private final val _invoked: AtomicInt
   private final val handler: (Throwable?) -> Unit

   init {
      this.handler = var1;
   }

   public override operator fun invoke(cause: Throwable?) {
      if (_invoked$FU.compareAndSet(this, 0, 1)) {
         this.handler.invoke(var1);
      }
   }
}
