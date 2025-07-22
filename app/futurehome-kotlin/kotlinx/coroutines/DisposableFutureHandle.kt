package kotlinx.coroutines

import java.util.concurrent.Future

private class DisposableFutureHandle(future: Future<*>) : DisposableHandle {
   private final val future: Future<*>

   init {
      this.future = var1;
   }

   public override fun dispose() {
      this.future.cancel(false);
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder("DisposableFutureHandle[");
      var1.append(this.future);
      var1.append(']');
      return var1.toString();
   }
}
