package kotlin

import androidx.concurrent.futures.AbstractResolvableFuture.SafeAtomicHelper..ExternalSyntheticBackportWithForwarding0
import java.io.Serializable
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater

private class SafePublicationLazyImpl<T>(initializer: () -> Any) : Lazy<T>, Serializable {
   private final var initializer: (() -> Any)?
   private final var _value: Any?
   private final val final: Any

   public open val value: Any
      public open get() {
         if (this._value != UNINITIALIZED_VALUE.INSTANCE) {
            return (T)this._value;
         } else {
            if (this.initializer != null) {
               val var3: Any = this.initializer.invoke();
               if (ExternalSyntheticBackportWithForwarding0.m(valueUpdater, this, UNINITIALIZED_VALUE.INSTANCE, var3)) {
                  this.initializer = null;
                  return (T)var3;
               }
            }

            return (T)this._value;
         }
      }


   init {
      this.initializer = var1;
      this._value = UNINITIALIZED_VALUE.INSTANCE;
      this.final = UNINITIALIZED_VALUE.INSTANCE;
   }

   private fun writeReplace(): Any {
      return new InitializedLazyImpl(this.getValue());
   }

   public override fun isInitialized(): Boolean {
      val var1: Boolean;
      if (this._value != UNINITIALIZED_VALUE.INSTANCE) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public override fun toString(): String {
      val var1: java.lang.String;
      if (this.isInitialized()) {
         var1 = java.lang.String.valueOf(this.getValue());
      } else {
         var1 = "Lazy value not initialized yet.";
      }

      return var1;
   }

   public companion object {
      private final val valueUpdater: AtomicReferenceFieldUpdater<SafePublicationLazyImpl<*>, Any>
   }
}
