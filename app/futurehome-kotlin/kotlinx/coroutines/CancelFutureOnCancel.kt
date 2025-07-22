package kotlinx.coroutines

import java.util.concurrent.Future

private class CancelFutureOnCancel(future: Future<*>) : CancelHandler {
   private final val future: Future<*>

   init {
      this.future = var1;
   }

   public override operator fun invoke(cause: Throwable?) {
      if (var1 != null) {
         this.future.cancel(false);
      }
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder("CancelFutureOnCancel[");
      var1.append(this.future);
      var1.append(']');
      return var1.toString();
   }
}
