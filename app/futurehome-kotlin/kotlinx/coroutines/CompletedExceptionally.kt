package kotlinx.coroutines

import kotlinx.atomicfu.AtomicBoolean

internal open class CompletedExceptionally(cause: Throwable, handled: Boolean = false) {
   private final val _handled: AtomicBoolean
   public final val cause: Throwable

   public final val handled: Boolean
      public final get() {
         val var1: Boolean;
         if (_handled$FU.get(this) != 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }


   init {
      this.cause = var1;
      this._handled = var2;
   }

   public fun makeHandled(): Boolean {
      return _handled$FU.compareAndSet(this, 0, 1);
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder();
      var1.append(DebugStringsKt.getClassSimpleName(this));
      var1.append('[');
      var1.append(this.cause);
      var1.append(']');
      return var1.toString();
   }
}
