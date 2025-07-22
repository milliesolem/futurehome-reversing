package kotlin.concurrent

import kotlin.jvm.functions.Function0

public inline fun <T : Any> ThreadLocal<T>.getOrSet(default: () -> T): T {
   val var3: Any = var0.get();
   var var2: Any = var3;
   if (var3 == null) {
      var2 = var1.invoke();
      var0.set(var2);
   }

   return (T)var2;
}

public fun thread(
   start: Boolean = true,
   isDaemon: Boolean = false,
   contextClassLoader: ClassLoader? = null,
   name: String? = null,
   priority: Int = -1,
   block: () -> Unit
): Thread {
   val var6: Thread = new Thread(var5) {
      final Function0<Unit> $block;

      {
         this.$block = var1;
      }

      @Override
      public void run() {
         this.$block.invoke();
      }
   };
   if (var1) {
      var6.setDaemon(true);
   }

   if (var4 > 0) {
      var6.setPriority(var4);
   }

   if (var3 != null) {
      var6.setName(var3);
   }

   if (var2 != null) {
      var6.setContextClassLoader(var2);
   }

   if (var0) {
      var6.start();
   }

   return var6;
}

@JvmSynthetic
fun `thread$default`(var0: Boolean, var1: Boolean, var2: ClassLoader, var3: java.lang.String, var4: Int, var5: Function0, var6: Int, var7: Any): Thread {
   if ((var6 and 1) != 0) {
      var0 = true;
   }

   if ((var6 and 2) != 0) {
      var1 = false;
   }

   if ((var6 and 4) != 0) {
      var2 = null;
   }

   if ((var6 and 8) != 0) {
      var3 = null;
   }

   if ((var6 and 16) != 0) {
      var4 = -1;
   }

   return thread(var0, var1, var2, var3, var4, var5);
}
