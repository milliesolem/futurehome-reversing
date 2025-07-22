package kotlinx.coroutines

import kotlin.coroutines.Continuation
import kotlinx.coroutines.internal.ScopeCoroutine

private class TimeoutCoroutine<U, T extends U>(time: Long, uCont: Continuation<Any>) : ScopeCoroutine(var3.getContext(), var3), Runnable {
   public final val time: Long

   init {
      this.time = var1;
   }

   internal override fun nameString(): String {
      val var1: StringBuilder = new StringBuilder();
      var1.append(super.nameString$kotlinx_coroutines_core());
      var1.append("(timeMillis=");
      var1.append(this.time);
      var1.append(')');
      return var1.toString();
   }

   public override fun run() {
      this.cancelCoroutine(TimeoutKt.TimeoutCancellationException(this.time, DelayKt.getDelay(this.getContext()), this));
   }
}
