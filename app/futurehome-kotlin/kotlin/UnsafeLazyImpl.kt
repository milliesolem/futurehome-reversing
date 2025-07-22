package kotlin

import java.io.Serializable
import kotlin.jvm.functions.Function0

internal class UnsafeLazyImpl<T>(initializer: () -> Any) : Lazy<T>, Serializable {
   private final var initializer: (() -> Any)?
   private final var _value: Any?

   public open val value: Any
      public open get() {
         if (this._value === UNINITIALIZED_VALUE.INSTANCE) {
            val var1: Function0 = this.initializer;
            this._value = var1.invoke();
            this.initializer = null;
         }

         return (T)this._value;
      }


   init {
      this.initializer = var1;
      this._value = UNINITIALIZED_VALUE.INSTANCE;
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
}
