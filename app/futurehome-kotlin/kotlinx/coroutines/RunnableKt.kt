package kotlinx.coroutines

import kotlin.jvm.functions.Function0

public inline fun Runnable(crossinline block: () -> Unit): Runnable {
   return new Runnable(var0) {
      final Function0<Unit> $block;

      {
         this.$block = var1;
      }

      @Override
      public final void run() {
         this.$block.invoke();
      }
   };
}
