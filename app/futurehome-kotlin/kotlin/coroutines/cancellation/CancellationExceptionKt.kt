package kotlin.coroutines.cancellation

import java.util.concurrent.CancellationException

public inline fun CancellationException(message: String?, cause: Throwable?): CancellationException {
   val var2: CancellationException = new CancellationException(var0);
   var2.initCause(var1);
   return var2;
}

public inline fun CancellationException(cause: Throwable?): CancellationException {
   val var1: java.lang.String;
   if (var0 != null) {
      var1 = var0.toString();
   } else {
      var1 = null;
   }

   val var2: CancellationException = new CancellationException(var1);
   var2.initCause(var0);
   return var2;
}

@JvmSynthetic
fun `CancellationException$annotations`() {
}
