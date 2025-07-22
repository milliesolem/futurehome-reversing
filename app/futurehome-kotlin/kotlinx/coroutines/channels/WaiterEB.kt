package kotlinx.coroutines.channels

import kotlinx.coroutines.Waiter

private class WaiterEB(waiter: Waiter) {
   public final val waiter: Waiter

   init {
      this.waiter = var1;
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder("WaiterEB(");
      var1.append(this.waiter);
      var1.append(')');
      return var1.toString();
   }
}
