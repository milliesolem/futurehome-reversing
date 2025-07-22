package io.flutter.plugins.webviewflutter

import android.webkit.WebChromeClient.FileChooserParams
import io.flutter.plugin.common.BasicMessageChannel
import kotlin.jvm.functions.Function1

public abstract class PigeonApiFileChooserParams {
   public open val pigeonRegistrar: AndroidWebkitLibraryPigeonProxyApiRegistrar

   open fun PigeonApiFileChooserParams(var1: AndroidWebkitLibraryPigeonProxyApiRegistrar) {
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

   public abstract fun acceptTypes(pigeon_instance: FileChooserParams): List<String> {
   }

   public abstract fun filenameHint(pigeon_instance: FileChooserParams): String? {
   }

   public abstract fun isCaptureEnabled(pigeon_instance: FileChooserParams): Boolean {
   }

   public abstract fun mode(pigeon_instance: FileChooserParams): FileChooserMode {
   }

   public fun pigeon_newInstance(pigeon_instanceArg: FileChooserParams, callback: (Result<Unit>) -> Unit) {
      if (this.getPigeonRegistrar().getIgnoreCallsToDart()) {
         val var10: Result.Companion = Result.Companion;
         var2.invoke(
            Result.box-impl(
               Result.constructor-impl(ResultKt.createFailure(new AndroidWebKitError("ignore-calls-error", "Calls to Dart are being ignored.", "")))
            )
         );
      } else if (this.getPigeonRegistrar().getInstanceManager().containsInstance(var1)) {
         val var9: Result.Companion = Result.Companion;
         Result.constructor-impl(Unit.INSTANCE);
      } else {
         new BasicMessageChannel<>(
               this.getPigeonRegistrar().getBinaryMessenger(),
               "dev.flutter.pigeon.webview_flutter_android.FileChooserParams.pigeon_newInstance",
               this.getPigeonRegistrar().getCodec()
            )
            .send(
               CollectionsKt.listOf(
                  new Object[]{
                     this.getPigeonRegistrar().getInstanceManager().addHostCreatedInstance(var1),
                     this.isCaptureEnabled(var1),
                     this.acceptTypes(var1),
                     this.mode(var1),
                     this.filenameHint(var1)
                  }
               ),
               new PigeonApiFileChooserParams$$ExternalSyntheticLambda0(var2, "dev.flutter.pigeon.webview_flutter_android.FileChooserParams.pigeon_newInstance")
            );
      }
   }
}
