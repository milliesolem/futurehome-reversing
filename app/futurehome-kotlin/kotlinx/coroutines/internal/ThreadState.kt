package kotlinx.coroutines.internal

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.ThreadContextElement

private class ThreadState(context: CoroutineContext, n: Int) {
   public final val context: CoroutineContext
   private final val elements: Array<ThreadContextElement<Any?>?>
   private final var i: Int
   private final val values: Array<Any?>

   init {
      this.context = var1;
      this.values = new Object[var2];
      this.elements = new ThreadContextElement[var2];
   }

   public fun append(element: ThreadContextElement<*>, value: Any?) {
      val var3: Int = this.i;
      this.values[this.i] = var2;
      var2 = this.elements;
      this.i++;
      var2[var3] = var1;
   }

   public fun restore(context: CoroutineContext) {
      var var2: Int = this.elements.length - 1;
      if (this.elements.length - 1 >= 0) {
         while (true) {
            val var3: Int = var2 - 1;
            val var4: ThreadContextElement = this.elements[var2];
            var4.restoreThreadContext(var1, this.values[var2]);
            if (var3 < 0) {
               break;
            }

            var2 = var3;
         }
      }
   }
}
