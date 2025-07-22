package kotlinx.coroutines

import java.util.concurrent.Future

private class CancelFutureOnCompletion(future: Future<*>) : JobNode {
   private final val future: Future<*>

   init {
      this.future = var1;
   }

   public override operator fun invoke(cause: Throwable?) {
      if (var1 != null) {
         this.future.cancel(false);
      }
   }
}
