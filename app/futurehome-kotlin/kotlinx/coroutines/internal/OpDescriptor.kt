package kotlinx.coroutines.internal

import kotlinx.coroutines.DebugStringsKt

public abstract class OpDescriptor {
   public abstract val atomicOp: AtomicOp<*>?

   public abstract fun perform(affected: Any?): Any? {
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder();
      var1.append(DebugStringsKt.getClassSimpleName(this));
      var1.append('@');
      var1.append(DebugStringsKt.getHexAddress(this));
      return var1.toString();
   }
}
