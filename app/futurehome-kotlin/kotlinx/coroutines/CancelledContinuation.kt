package kotlinx.coroutines

import java.util.concurrent.CancellationException
import kotlin.coroutines.Continuation
import kotlinx.atomicfu.AtomicBoolean

internal class CancelledContinuation(continuation: Continuation<*>, cause: Throwable?, handled: Boolean) : CompletedExceptionally {
   private final val _resumed: AtomicBoolean

   init {
      var var4: java.lang.Throwable = var2;
      if (var2 == null) {
         val var5: StringBuilder = new StringBuilder("Continuation ");
         var5.append(var1);
         var5.append(" was cancelled normally");
         var4 = new CancellationException(var5.toString());
      }

      super(var4, var3);
      this._resumed = 0;
   }

   public fun makeResumed(): Boolean {
      return _resumed$FU.compareAndSet(this, 0, 1);
   }
}
