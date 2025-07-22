package io.flutter.plugins.webviewflutter

import android.webkit.WebResourceError
import io.flutter.plugin.common.BasicMessageChannel
import kotlin.jvm.functions.Function1

public abstract class PigeonApiWebResourceError {
   public open val pigeonRegistrar: AndroidWebkitLibraryPigeonProxyApiRegistrar

   open fun PigeonApiWebResourceError(var1: AndroidWebkitLibraryPigeonProxyApiRegistrar) {
      this.pigeonRegistrar = var1;
   }

   @JvmStatic
   fun `pigeon_newInstance$lambda$0`(var0: Function1, var1: java.lang.String, var2: Any) {
      if (var2 is java.util.List) {
         val var4: java.util.List = var2 as java.util.List;
         if ((var2 as java.util.List).size() > 1) {
            var2 = Result.Companion;
            var2 = var4.get(0);
            val var3: java.lang.String = var2 as java.lang.String;
            var2 = var4.get(1);
            var0.invoke(
               Result.box-impl(
                  Result.constructor-impl(ResultKt.createFailure(new AndroidWebKitError(var3, var2 as java.lang.String, var4.get(2) as java.lang.String)))
               )
            );
         } else {
            val var5: Result.Companion = Result.Companion;
            var0.invoke(Result.box-impl(Result.constructor-impl(Unit.INSTANCE)));
         }
      } else {
         var2 = Result.Companion;
         var0.invoke(Result.box-impl(Result.constructor-impl(ResultKt.createFailure(AndroidWebkitLibrary_gKt.access$createConnectionError(var1)))));
      }
   }

   public abstract fun description(pigeon_instance: WebResourceError): String {
   }

   public abstract fun errorCode(pigeon_instance: WebResourceError): Long {
   }

   public fun pigeon_newInstance(pigeon_instanceArg: WebResourceError, callback: (Result<Unit>) -> Unit) {
      if (this.getPigeonRegistrar().getIgnoreCallsToDart()) {
         val var9: Result.Companion = Result.Companion;
         var2.invoke(
            Result.box-impl(
               Result.constructor-impl(ResultKt.createFailure(new AndroidWebKitError("ignore-calls-error", "Calls to Dart are being ignored.", "")))
            )
         );
      } else if (this.getPigeonRegistrar().getInstanceManager().containsInstance(var1)) {
         val var8: Result.Companion = Result.Companion;
         Result.constructor-impl(Unit.INSTANCE);
      } else {
         new BasicMessageChannel<>(
               this.getPigeonRegistrar().getBinaryMessenger(),
               "dev.flutter.pigeon.webview_flutter_android.WebResourceError.pigeon_newInstance",
               this.getPigeonRegistrar().getCodec()
            )
            .send(
               CollectionsKt.listOf(
                  new Object[]{this.getPigeonRegistrar().getInstanceManager().addHostCreatedInstance(var1), this.errorCode(var1), this.description(var1)}
               ),
               new PigeonApiWebResourceError$$ExternalSyntheticLambda0(var2, "dev.flutter.pigeon.webview_flutter_android.WebResourceError.pigeon_newInstance")
            );
      }
   }
}
