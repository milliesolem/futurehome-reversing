package kotlin

import java.io.Serializable
import kotlin.jvm.functions.Function0

private class SynchronizedLazyImpl<T>(initializer: () -> Any, lock: Any? = null) : Lazy<T>, Serializable {
   private final var initializer: (() -> Any)?
   private final var _value: Any?
   private final val lock: Any

   public open val value: Any
      public open get() {
         if (this._value != UNINITIALIZED_VALUE.INSTANCE) {
            return (T)this._value;
         } else {
            label36: {
               synchronized (this.lock){} // $VF: monitorenter 

               try {
                  if (this._value === UNINITIALIZED_VALUE.INSTANCE) {
                     val var6: Function0 = this.initializer;
                     this._value = var6.invoke();
                     this.initializer = null;
                  }
               } catch (var3: java.lang.Throwable) {
                  // $VF: monitorexit
               }

               // $VF: monitorexit
            }
         }
      }


   init {
      this.initializer = var1;
      this._value = UNINITIALIZED_VALUE.INSTANCE;
      var var3: Any = var2;
      if (var2 == null) {
         var3 = this;
      }

      this.lock = var3;
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
