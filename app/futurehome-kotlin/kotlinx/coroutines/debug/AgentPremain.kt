package kotlinx.coroutines.debug

import java.lang.instrument.ClassFileTransformer
import java.lang.instrument.Instrumentation
import java.security.ProtectionDomain
import kotlinx.coroutines.debug.internal.AgentInstallationType
import kotlinx.coroutines.debug.internal.DebugProbesImpl
import sun.misc.Signal

internal object AgentPremain {
   private final val enableCreationStackTraces: Boolean

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @JvmStatic
   fun {
      var var16: Any;
      label42: {
         try {
            var16 = Result.Companion;
            var16 = System.getProperty("kotlinx.coroutines.debug.enable.creation.stack.trace");
         } catch (var6: java.lang.Throwable) {
            var16 = Result.Companion;
            var16 = (java.lang.Boolean)Result.constructor-impl(ResultKt.createFailure(var6));
            break label42;
         }

         if (var16 != null) {
            try {
               var16 = java.lang.Boolean.parseBoolean((java.lang.String)var16);
            } catch (var5: java.lang.Throwable) {
               var16 = Result.Companion;
               var16 = (java.lang.Boolean)Result.constructor-impl(ResultKt.createFailure(var5));
               break label42;
            }
         } else {
            var16 = null;
         }

         label30:
         try {
            var16 = (java.lang.Boolean)Result.constructor-impl(var16);
         } catch (var4: java.lang.Throwable) {
            var16 = Result.Companion;
            var16 = (java.lang.Boolean)Result.constructor-impl(ResultKt.createFailure(var4));
            break label30;
         }
      }

      if (Result.isFailure-impl(var16)) {
         var16 = null;
      }

      var16 = var16;
      val var0: Boolean;
      if (var16 != null) {
         var0 = var16;
      } else {
         var0 = DebugProbesImpl.INSTANCE.getEnableCreationStackTraces$kotlinx_coroutines_core();
      }

      enableCreationStackTraces = var0;
   }

   private fun installSignalHandler() {
      label10:
      try {
         Signal.handle(new Signal("TRAP"), new AgentPremain$$ExternalSyntheticLambda0());
      } catch (var3: java.lang.Throwable) {
         ;
      }
   }

   @JvmStatic
   fun `installSignalHandler$lambda$1`(var0: Signal) {
      if (DebugProbesImpl.INSTANCE.isInstalled$kotlinx_coroutines_debug()) {
         DebugProbesImpl.INSTANCE.dumpCoroutines(System.out);
      } else {
         System.out.println("Cannot perform coroutines dump, debug probes are disabled");
      }
   }

   @JvmStatic
   public fun premain(args: String?, instrumentation: Instrumentation) {
      AgentInstallationType.INSTANCE.setInstalledStatically$kotlinx_coroutines_core(true);
      var1.addTransformer(AgentPremain.DebugProbesTransformer.INSTANCE);
      DebugProbesImpl.INSTANCE.setEnableCreationStackTraces$kotlinx_coroutines_core(enableCreationStackTraces);
      DebugProbesImpl.INSTANCE.install$kotlinx_coroutines_core();
      INSTANCE.installSignalHandler();
   }

   internal object DebugProbesTransformer : ClassFileTransformer {
      public override fun transform(
         loader: ClassLoader?,
         className: String,
         classBeingRedefined: Class<*>?,
         protectionDomain: ProtectionDomain,
         classfileBuffer: ByteArray?
      ): ByteArray? {
         if (var1 != null && var2 == "kotlin/coroutines/jvm/internal/DebugProbesKt") {
            AgentInstallationType.INSTANCE.setInstalledStatically$kotlinx_coroutines_core(true);
            return ByteStreamsKt.readBytes(var1.getResourceAsStream("DebugProbesKt.bin"));
         } else {
            return null;
         }
      }
   }
}
