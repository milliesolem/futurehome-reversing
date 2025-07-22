package kotlinx.coroutines.future

import j..util.function.BiFunction
import java.util.concurrent.CompletableFuture
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.AbstractCoroutine
import kotlinx.coroutines.Job
import okio.NioSystemFileSystem$$ExternalSyntheticApiModelOutline0

private class CompletableFutureCoroutine<T>(context: CoroutineContext, future: CompletableFuture<Any>) : AbstractCoroutine(var1, true, true),
   BiFunction<T, java.lang.Throwable, Unit> {
   private final val future: CompletableFuture<Any>

   init {
      this.future = var2;
   }

   public open fun apply(value: Any?, exception: Throwable?) {
      Job.DefaultImpls.cancel$default(this, null, 1, null);
   }

   protected override fun onCancelled(cause: Throwable, handled: Boolean) {
      NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(this.future, var1);
   }

   protected override fun onCompleted(value: Any) {
      NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(this.future, var1);
   }
}
