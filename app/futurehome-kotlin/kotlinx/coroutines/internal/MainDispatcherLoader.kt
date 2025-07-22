package kotlinx.coroutines.internal

import java.util.ServiceLoader
import kotlinx.coroutines.MainCoroutineDispatcher

internal object MainDispatcherLoader {
   private final val FAST_SERVICE_LOADER_ENABLED: Boolean = SystemPropsKt.systemProp("kotlinx.coroutines.fast.service.loader", true)
   public final val dispatcher: MainCoroutineDispatcher

   @JvmStatic
   fun {
      val var0: MainDispatcherLoader = new MainDispatcherLoader();
      INSTANCE = var0;
      dispatcher = var0.loadMainDispatcher();
   }

   private fun loadMainDispatcher(): MainCoroutineDispatcher {
      var var5: java.util.List;
      label92: {
         try {
            if (FAST_SERVICE_LOADER_ENABLED) {
               var5 = FastServiceLoader.INSTANCE.loadMainDispatcherFactory$kotlinx_coroutines_core();
               break label92;
            }
         } catch (var18: java.lang.Throwable) {
            return MainDispatchersKt.createMissingDispatcher$default(var18, null, 2, null);
         }

         try {
            var5 = SequencesKt.toList(
               SequencesKt.asSequence(ServiceLoader.load(MainDispatcherFactory.class, MainDispatcherFactory.class.getClassLoader()).iterator())
            );
         } catch (var17: java.lang.Throwable) {
            return MainDispatchersKt.createMissingDispatcher$default(var17, null, 2, null);
         }
      }

      var var119: Any;
      label82: {
         label661: {
            var var8: java.util.Iterator;
            try {
               var8 = var5.iterator();
               if (!var8.hasNext()) {
                  break label661;
               }
            } catch (var16: java.lang.Throwable) {
               return MainDispatchersKt.createMissingDispatcher$default(var16, null, 2, null);
            }

            try {
               var119 = (MainCoroutineDispatcher)var8.next();
               if (!var8.hasNext()) {
                  break label82;
               }
            } catch (var15: java.lang.Throwable) {
               return MainDispatchersKt.createMissingDispatcher$default(var15, null, 2, null);
            }

            var var2: Int;
            try {
               var2 = (var119 as MainDispatcherFactory).getLoadPriority();
            } catch (var13: java.lang.Throwable) {
               return MainDispatchersKt.createMissingDispatcher$default(var13, null, 2, null);
            }

            var var6: Any = var119;

            while (true) {
               var var3: Int;
               var var7: Any;
               try {
                  var7 = var8.next();
                  var3 = (var7 as MainDispatcherFactory).getLoadPriority();
               } catch (var12: java.lang.Throwable) {
                  return MainDispatchersKt.createMissingDispatcher$default(var12, null, 2, null);
               }

               var119 = (MainCoroutineDispatcher)var6;
               var var1: Int = var2;
               if (var2 < var3) {
                  var119 = (MainCoroutineDispatcher)var7;
                  var1 = var3;
               }

               var6 = var119;
               var2 = var1;

               try {
                  if (!var8.hasNext()) {
                     break label82;
                  }
               } catch (var14: java.lang.Throwable) {
                  return MainDispatchersKt.createMissingDispatcher$default(var14, null, 2, null);
               }
            }
         }

         var119 = null;
      }

      try {
         var119 = var119 as MainDispatcherFactory;
      } catch (var11: java.lang.Throwable) {
         return MainDispatchersKt.createMissingDispatcher$default(var11, null, 2, null);
      }

      if (var119 != null) {
         try {
            var121 = MainDispatchersKt.tryCreateDispatcher((MainDispatcherFactory)var119, var5);
         } catch (var10: java.lang.Throwable) {
            return MainDispatchersKt.createMissingDispatcher$default(var10, null, 2, null);
         }

         if (var121 != null) {
            return var121;
         }
      }

      try {
         var119 = MainDispatchersKt.createMissingDispatcher$default(null, null, 3, null);
      } catch (var9: java.lang.Throwable) {
         return MainDispatchersKt.createMissingDispatcher$default(var9, null, 2, null);
      }

      return var119;
   }
}
