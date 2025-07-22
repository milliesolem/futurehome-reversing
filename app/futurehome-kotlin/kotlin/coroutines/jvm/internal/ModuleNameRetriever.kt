package kotlin.coroutines.jvm.internal

import java.lang.reflect.Method

private object ModuleNameRetriever {
   private final val notOnJava9: kotlin.coroutines.jvm.internal.ModuleNameRetriever.Cache = new ModuleNameRetriever.Cache(null, null, null)
   private final var cache: kotlin.coroutines.jvm.internal.ModuleNameRetriever.Cache?

   private fun buildCache(continuation: BaseContinuationImpl): kotlin.coroutines.jvm.internal.ModuleNameRetriever.Cache {
      try {
         val var4: ModuleNameRetriever.Cache = new ModuleNameRetriever.Cache(
            Class.class.getDeclaredMethod("getModule", null),
            var1.getClass().getClassLoader().loadClass("java.lang.Module").getDeclaredMethod("getDescriptor", null),
            var1.getClass().getClassLoader().loadClass("java.lang.module.ModuleDescriptor").getDeclaredMethod("name", null)
         );
         cache = var4;
         return var4;
      } catch (var5: Exception) {
         val var6: ModuleNameRetriever.Cache = notOnJava9;
         cache = notOnJava9;
         return var6;
      }
   }

   public fun getModuleName(continuation: BaseContinuationImpl): String? {
      var var2: ModuleNameRetriever.Cache = cache;
      if (cache == null) {
         var2 = this.buildCache(var1);
      }

      if (var2 === notOnJava9) {
         return null;
      } else {
         var var11: java.lang.String = null;
         if (var2.getModuleMethod != null) {
            val var12: Any = var2.getModuleMethod.invoke(var1.getClass(), null);
            if (var12 == null) {
               var11 = null;
            } else {
               var11 = null;
               if (var2.getDescriptorMethod != null) {
                  var var7: Any = var2.getDescriptorMethod.invoke(var12, null);
                  if (var7 == null) {
                     var11 = null;
                  } else {
                     if (var2.nameMethod != null) {
                        var7 = var2.nameMethod.invoke(var7, null);
                     } else {
                        var7 = null;
                     }

                     var11 = null;
                     if (var7 is java.lang.String) {
                        var11 = var7 as java.lang.String;
                     }
                  }
               }
            }
         }

         return var11;
      }
   }

   private class Cache(getModuleMethod: Method?, getDescriptorMethod: Method?, nameMethod: Method?) {
      public final val getModuleMethod: Method?
      public final val getDescriptorMethod: Method?
      public final val nameMethod: Method?

      init {
         this.getModuleMethod = var1;
         this.getDescriptorMethod = var2;
         this.nameMethod = var3;
      }
   }
}
