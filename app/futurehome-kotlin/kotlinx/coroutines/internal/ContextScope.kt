package kotlinx.coroutines.internal

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope

internal class ContextScope(context: CoroutineContext) : CoroutineScope {
   public open val coroutineContext: CoroutineContext

   init {
      this.coroutineContext = var1;
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder("CoroutineScope(coroutineContext=");
      var1.append(this.getCoroutineContext());
      var1.append(')');
      return var1.toString();
   }
}
