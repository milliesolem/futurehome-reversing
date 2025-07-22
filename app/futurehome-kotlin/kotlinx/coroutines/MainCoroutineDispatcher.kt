package kotlinx.coroutines

import kotlinx.coroutines.internal.LimitedDispatcherKt

public abstract class MainCoroutineDispatcher : CoroutineDispatcher {
   public abstract val immediate: MainCoroutineDispatcher

   public override fun limitedParallelism(parallelism: Int): CoroutineDispatcher {
      LimitedDispatcherKt.checkParallelism(var1);
      return this;
   }

   public override fun toString(): String {
      val var2: java.lang.String = this.toStringInternalImpl();
      var var1: java.lang.String = var2;
      if (var2 == null) {
         val var3: StringBuilder = new StringBuilder();
         var3.append(DebugStringsKt.getClassSimpleName(this));
         var3.append('@');
         var3.append(DebugStringsKt.getHexAddress(this));
         var1 = var3.toString();
      }

      return var1;
   }

   protected fun toStringInternalImpl(): String? {
      var var1: MainCoroutineDispatcher = Dispatchers.getMain();
      if (this === var1) {
         return "Dispatchers.Main";
      } else {
         try {
            var1 = var1.getImmediate();
         } catch (var2: UnsupportedOperationException) {
            var1 = null;
         }

         return if (this === var1) "Dispatchers.Main.immediate" else null;
      }
   }
}
