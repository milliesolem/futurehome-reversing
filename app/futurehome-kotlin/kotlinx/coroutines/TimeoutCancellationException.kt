package kotlinx.coroutines

import java.util.concurrent.CancellationException

public class TimeoutCancellationException internal constructor(message: String, coroutine: Job?) : CancellationException(var1),
   CopyableThrowable<TimeoutCancellationException> {
   internal final val coroutine: Job?

   internal constructor(message: String) : this(var1, null)
   init {
      this.coroutine = var2;
   }

   public open fun createCopy(): TimeoutCancellationException {
      val var2: java.lang.String = this.getMessage();
      var var1: java.lang.String = var2;
      if (var2 == null) {
         var1 = "";
      }

      val var3: TimeoutCancellationException = new TimeoutCancellationException(var1, this.coroutine);
      var3.initCause(this);
      return var3;
   }
}
