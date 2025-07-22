package kotlinx.coroutines.flow.internal

import java.util.concurrent.CancellationException
import kotlinx.coroutines.DebugKt

internal class ChildCancelledException : CancellationException("Child of the scoped flow was cancelled") {
   public override fun fillInStackTrace(): Throwable {
      if (DebugKt.getDEBUG()) {
         return super.fillInStackTrace();
      } else {
         this.setStackTrace(new StackTraceElement[0]);
         return this;
      }
   }
}
