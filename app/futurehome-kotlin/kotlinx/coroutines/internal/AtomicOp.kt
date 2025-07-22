package kotlinx.coroutines.internal

import androidx.concurrent.futures.AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater
import kotlinx.atomicfu.AtomicRef
import kotlinx.coroutines.DebugKt

public abstract class AtomicOp<T> : OpDescriptor {
   private final val _consensus: AtomicRef<Any?>

   public open val atomicOp: AtomicOp<*>
      public open get() {
         return this;
      }


   private fun decide(decision: Any?): Any? {
      if (DebugKt.getASSERTIONS_ENABLED() && var1 === AtomicKt.NO_DECISION) {
         throw new AssertionError();
      } else {
         val var2: AtomicReferenceFieldUpdater = _consensus$FU;
         val var3: Any = _consensus$FU.get(this);
         if (var3 != AtomicKt.NO_DECISION) {
            return var3;
         } else {
            return if (ExternalSyntheticBackportWithForwarding0.m(var2, this, AtomicKt.NO_DECISION, var1)) var1 else var2.get(this);
         }
      }
   }

   public abstract fun complete(affected: Any, failure: Any?) {
   }

   public override fun perform(affected: Any?): Any? {
      val var3: Any = _consensus$FU.get(this);
      var var2: Any = var3;
      if (var3 === AtomicKt.NO_DECISION) {
         var2 = this.decide(this.prepare((T)var1));
      }

      this.complete((T)var1, var2);
      return var2;
   }

   public abstract fun prepare(affected: Any): Any? {
   }
}
