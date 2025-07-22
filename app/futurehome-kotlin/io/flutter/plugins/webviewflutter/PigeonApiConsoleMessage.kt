package io.flutter.plugins.webviewflutter

import android.webkit.ConsoleMessage
import io.flutter.plugin.common.BasicMessageChannel
import kotlin.jvm.functions.Function1

public abstract class PigeonApiConsoleMessage {
   public open val pigeonRegistrar: AndroidWebkitLibraryPigeonProxyApiRegistrar

   open fun PigeonApiConsoleMessage(var1: AndroidWebkitLibraryPigeonProxyApiRegistrar) {
      this.pigeonRegistrar = var1;
   }

   @JvmStatic
   fun `pigeon_newInstance$lambda$0`(var0: Function1, var1: java.lang.String, var2: Any) {
      if (var2 is java.util.List) {
         val var4: java.util.List = var2 as java.util.List;
         if ((var2 as java.util.List).size() > 1) {
            var2 = Result.Companion;
            var2 = var4.get(0);
            var2 = var2 as java.lang.String;
            val var3: Any = var4.get(1);
            var0.invoke(
               Result.box-impl(
                  Result.constructor-impl(ResultKt.createFailure(new AndroidWebKitError(var2, var3 as java.lang.String, var4.get(2) as java.lang.String)))
               )
            );
         } else {
            val var5: Result.Companion = Result.Companion;
            var0.invoke(Result.box-impl(Result.constructor-impl(Unit.INSTANCE)));
         }
      } else {
         val var9: Result.Companion = Result.Companion;
         var0.invoke(Result.box-impl(Result.constructor-impl(ResultKt.createFailure(AndroidWebkitLibrary_gKt.access$createConnectionError(var1)))));
      }
   }

   public abstract fun level(pigeon_instance: ConsoleMessage): ConsoleMessageLevel {
   }

   public abstract fun lineNumber(pigeon_instance: ConsoleMessage): Long {
   }

   public abstract fun message(pigeon_instance: ConsoleMessage): String {
   }

   public fun pigeon_newInstance(pigeon_instanceArg: ConsoleMessage, callback: (Result<Unit>) -> Unit) {
      if (this.getPigeonRegistrar().getIgnoreCallsToDart()) {
         val var11: Result.Companion = Result.Companion;
         var2.invoke(
            Result.box-impl(
               Result.constructor-impl(ResultKt.createFailure(new AndroidWebKitError("ignore-calls-error", "Calls to Dart are being ignored.", "")))
            )
         );
      } else if (this.getPigeonRegistrar().getInstanceManager().containsInstance(var1)) {
         val var10: Result.Companion = Result.Companion;
         Result.constructor-impl(Unit.INSTANCE);
      } else {
         new BasicMessageChannel<>(
               this.getPigeonRegistrar().getBinaryMessenger(),
               "dev.flutter.pigeon.webview_flutter_android.ConsoleMessage.pigeon_newInstance",
               this.getPigeonRegistrar().getCodec()
            )
            .send(
               CollectionsKt.listOf(
                  new Object[]{
                     this.getPigeonRegistrar().getInstanceManager().addHostCreatedInstance(var1),
                     this.lineNumber(var1),
                     this.message(var1),
                     this.level(var1),
                     this.sourceId(var1)
                  }
               ),
               new PigeonApiConsoleMessage$$ExternalSyntheticLambda0(var2, "dev.flutter.pigeon.webview_flutter_android.ConsoleMessage.pigeon_newInstance")
            );
      }
   }

   public abstract fun sourceId(pigeon_instance: ConsoleMessage): String {
   }
}
