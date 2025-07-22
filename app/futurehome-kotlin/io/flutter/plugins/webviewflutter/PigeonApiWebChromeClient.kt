package io.flutter.plugins.webviewflutter

import android.view.View
import android.webkit.ConsoleMessage
import android.webkit.PermissionRequest
import android.webkit.WebView
import android.webkit.GeolocationPermissions.Callback
import android.webkit.WebChromeClient.CustomViewCallback
import android.webkit.WebChromeClient.FileChooserParams
import io.flutter.plugin.common.BasicMessageChannel
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MessageCodec
import io.flutter.plugins.webviewflutter.WebChromeClientProxyApi.WebChromeClientImpl
import kotlin.jvm.functions.Function1

public abstract class PigeonApiWebChromeClient {
   public open val pigeonRegistrar: AndroidWebkitLibraryPigeonProxyApiRegistrar

   open fun PigeonApiWebChromeClient(var1: AndroidWebkitLibraryPigeonProxyApiRegistrar) {
      this.pigeonRegistrar = var1;
   }

   @JvmStatic
   fun `onConsoleMessage$lambda$8`(var0: Function1, var1: java.lang.String, var2: Any) {
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

   @JvmStatic
   fun `onGeolocationPermissionsHidePrompt$lambda$7`(var0: Function1, var1: java.lang.String, var2: Any) {
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

   @JvmStatic
   fun `onGeolocationPermissionsShowPrompt$lambda$6`(var0: Function1, var1: java.lang.String, var2: Any) {
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

   @JvmStatic
   fun `onHideCustomView$lambda$5`(var0: Function1, var1: java.lang.String, var2: Any) {
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

   @JvmStatic
   fun `onJsAlert$lambda$9`(var0: Function1, var1: java.lang.String, var2: Any) {
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

   @JvmStatic
   fun `onJsConfirm$lambda$10`(var0: Function1, var1: java.lang.String, var2: Any) {
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
         } else if (var4.get(0) == null) {
            val var5: Result.Companion = Result.Companion;
            var0.invoke(
               Result.box-impl(
                  Result.constructor-impl(
                     ResultKt.createFailure(new AndroidWebKitError("null-error", "Flutter api returned null value for non-null return value.", ""))
                  )
               )
            );
         } else {
            val var6: Any = var4.get(0);
            val var7: java.lang.Boolean = var6 as java.lang.Boolean;
            var6 as java.lang.Boolean;
            val var11: Result.Companion = Result.Companion;
            var0.invoke(Result.box-impl(Result.constructor-impl(var7)));
         }
      } else {
         val var12: Result.Companion = Result.Companion;
         var0.invoke(Result.box-impl(Result.constructor-impl(ResultKt.createFailure(AndroidWebkitLibrary_gKt.access$createConnectionError(var1)))));
      }
   }

   @JvmStatic
   fun `onJsPrompt$lambda$11`(var0: Function1, var1: java.lang.String, var2: Any) {
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
            var1 = var4.get(0) as java.lang.String;
            var2 = Result.Companion;
            var0.invoke(Result.box-impl(Result.constructor-impl(var1)));
         }
      } else {
         var2 = Result.Companion;
         var0.invoke(Result.box-impl(Result.constructor-impl(ResultKt.createFailure(AndroidWebkitLibrary_gKt.access$createConnectionError(var1)))));
      }
   }

   @JvmStatic
   fun `onPermissionRequest$lambda$3`(var0: Function1, var1: java.lang.String, var2: Any) {
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

   @JvmStatic
   fun `onProgressChanged$lambda$1`(var0: Function1, var1: java.lang.String, var2: Any) {
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

   @JvmStatic
   fun `onShowCustomView$lambda$4`(var0: Function1, var1: java.lang.String, var2: Any) {
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

   @JvmStatic
   fun `onShowFileChooser$lambda$2`(var0: Function1, var1: java.lang.String, var2: Any) {
      if (var2 is java.util.List) {
         val var4: java.util.List = var2;
         if (var2.size() > 1) {
            var2 = Result.Companion;
            var2 = var4.get(0);
            val var3: java.lang.String = var2 as java.lang.String;
            var2 = var4.get(1);
            var0.invoke(
               Result.box-impl(
                  Result.constructor-impl(ResultKt.createFailure(new AndroidWebKitError(var3, var2 as java.lang.String, var4.get(2) as java.lang.String)))
               )
            );
         } else if (var4.get(0) == null) {
            val var5: Result.Companion = Result.Companion;
            var0.invoke(
               Result.box-impl(
                  Result.constructor-impl(
                     ResultKt.createFailure(new AndroidWebKitError("null-error", "Flutter api returned null value for non-null return value.", ""))
                  )
               )
            );
         } else {
            var var6: Any = var4.get(0);
            var2 = var6 as java.util.List;
            var6 = Result.Companion;
            var0.invoke(Result.box-impl(Result.constructor-impl(var2)));
         }
      } else {
         val var12: Result.Companion = Result.Companion;
         var0.invoke(Result.box-impl(Result.constructor-impl(ResultKt.createFailure(AndroidWebkitLibrary_gKt.access$createConnectionError(var1)))));
      }
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

   public fun onConsoleMessage(pigeon_instanceArg: WebChromeClientImpl, messageArg: ConsoleMessage, callback: (Result<Unit>) -> Unit) {
      if (this.getPigeonRegistrar().getIgnoreCallsToDart()) {
         val var4: Result.Companion = Result.Companion;
         var3.invoke(
            Result.box-impl(
               Result.constructor-impl(ResultKt.createFailure(new AndroidWebKitError("ignore-calls-error", "Calls to Dart are being ignored.", "")))
            )
         );
      } else {
         new BasicMessageChannel<>(
               this.getPigeonRegistrar().getBinaryMessenger(),
               "dev.flutter.pigeon.webview_flutter_android.WebChromeClient.onConsoleMessage",
               this.getPigeonRegistrar().getCodec()
            )
            .send(
               CollectionsKt.listOf(new Object[]{var1, var2}),
               new PigeonApiWebChromeClient$$ExternalSyntheticLambda9(var3, "dev.flutter.pigeon.webview_flutter_android.WebChromeClient.onConsoleMessage")
            );
      }
   }

   public fun onGeolocationPermissionsHidePrompt(pigeon_instanceArg: WebChromeClientImpl, callback: (Result<Unit>) -> Unit) {
      if (this.getPigeonRegistrar().getIgnoreCallsToDart()) {
         val var3: Result.Companion = Result.Companion;
         var2.invoke(
            Result.box-impl(
               Result.constructor-impl(ResultKt.createFailure(new AndroidWebKitError("ignore-calls-error", "Calls to Dart are being ignored.", "")))
            )
         );
      } else {
         new BasicMessageChannel<>(
               this.getPigeonRegistrar().getBinaryMessenger(),
               "dev.flutter.pigeon.webview_flutter_android.WebChromeClient.onGeolocationPermissionsHidePrompt",
               this.getPigeonRegistrar().getCodec()
            )
            .send(
               CollectionsKt.listOf(var1),
               new PigeonApiWebChromeClient$$ExternalSyntheticLambda1(
                  var2, "dev.flutter.pigeon.webview_flutter_android.WebChromeClient.onGeolocationPermissionsHidePrompt"
               )
            );
      }
   }

   public fun onGeolocationPermissionsShowPrompt(
      pigeon_instanceArg: WebChromeClientImpl,
      originArg: String,
      callbackArg: Callback,
      callback: (Result<Unit>) -> Unit
   ) {
      if (this.getPigeonRegistrar().getIgnoreCallsToDart()) {
         val var5: Result.Companion = Result.Companion;
         var4.invoke(
            Result.box-impl(
               Result.constructor-impl(ResultKt.createFailure(new AndroidWebKitError("ignore-calls-error", "Calls to Dart are being ignored.", "")))
            )
         );
      } else {
         new BasicMessageChannel<>(
               this.getPigeonRegistrar().getBinaryMessenger(),
               "dev.flutter.pigeon.webview_flutter_android.WebChromeClient.onGeolocationPermissionsShowPrompt",
               this.getPigeonRegistrar().getCodec()
            )
            .send(
               CollectionsKt.listOf(new Object[]{var1, var2, var3}),
               new PigeonApiWebChromeClient$$ExternalSyntheticLambda10(
                  var4, "dev.flutter.pigeon.webview_flutter_android.WebChromeClient.onGeolocationPermissionsShowPrompt"
               )
            );
      }
   }

   public fun onHideCustomView(pigeon_instanceArg: WebChromeClientImpl, callback: (Result<Unit>) -> Unit) {
      if (this.getPigeonRegistrar().getIgnoreCallsToDart()) {
         val var3: Result.Companion = Result.Companion;
         var2.invoke(
            Result.box-impl(
               Result.constructor-impl(ResultKt.createFailure(new AndroidWebKitError("ignore-calls-error", "Calls to Dart are being ignored.", "")))
            )
         );
      } else {
         new BasicMessageChannel<>(
               this.getPigeonRegistrar().getBinaryMessenger(),
               "dev.flutter.pigeon.webview_flutter_android.WebChromeClient.onHideCustomView",
               this.getPigeonRegistrar().getCodec()
            )
            .send(
               CollectionsKt.listOf(var1),
               new PigeonApiWebChromeClient$$ExternalSyntheticLambda3(var2, "dev.flutter.pigeon.webview_flutter_android.WebChromeClient.onHideCustomView")
            );
      }
   }

   public fun onJsAlert(pigeon_instanceArg: WebChromeClientImpl, webViewArg: WebView, urlArg: String, messageArg: String, callback: (Result<Unit>) -> Unit) {
      if (this.getPigeonRegistrar().getIgnoreCallsToDart()) {
         val var6: Result.Companion = Result.Companion;
         var5.invoke(
            Result.box-impl(
               Result.constructor-impl(ResultKt.createFailure(new AndroidWebKitError("ignore-calls-error", "Calls to Dart are being ignored.", "")))
            )
         );
      } else {
         new BasicMessageChannel<>(
               this.getPigeonRegistrar().getBinaryMessenger(),
               "dev.flutter.pigeon.webview_flutter_android.WebChromeClient.onJsAlert",
               this.getPigeonRegistrar().getCodec()
            )
            .send(
               CollectionsKt.listOf(new Object[]{var1, var2, var3, var4}),
               new PigeonApiWebChromeClient$$ExternalSyntheticLambda5(var5, "dev.flutter.pigeon.webview_flutter_android.WebChromeClient.onJsAlert")
            );
      }
   }

   public fun onJsConfirm(pigeon_instanceArg: WebChromeClientImpl, webViewArg: WebView, urlArg: String, messageArg: String, callback: (Result<Boolean>) -> Unit) {
      if (this.getPigeonRegistrar().getIgnoreCallsToDart()) {
         val var6: Result.Companion = Result.Companion;
         var5.invoke(
            Result.box-impl(
               Result.constructor-impl(ResultKt.createFailure(new AndroidWebKitError("ignore-calls-error", "Calls to Dart are being ignored.", "")))
            )
         );
      } else {
         new BasicMessageChannel<>(
               this.getPigeonRegistrar().getBinaryMessenger(),
               "dev.flutter.pigeon.webview_flutter_android.WebChromeClient.onJsConfirm",
               this.getPigeonRegistrar().getCodec()
            )
            .send(
               CollectionsKt.listOf(new Object[]{var1, var2, var3, var4}),
               new PigeonApiWebChromeClient$$ExternalSyntheticLambda11(var5, "dev.flutter.pigeon.webview_flutter_android.WebChromeClient.onJsConfirm")
            );
      }
   }

   public fun onJsPrompt(
      pigeon_instanceArg: WebChromeClientImpl,
      webViewArg: WebView,
      urlArg: String,
      messageArg: String,
      defaultValueArg: String,
      callback: (Result<String?>) -> Unit
   ) {
      if (this.getPigeonRegistrar().getIgnoreCallsToDart()) {
         val var7: Result.Companion = Result.Companion;
         var6.invoke(
            Result.box-impl(
               Result.constructor-impl(ResultKt.createFailure(new AndroidWebKitError("ignore-calls-error", "Calls to Dart are being ignored.", "")))
            )
         );
      } else {
         new BasicMessageChannel<>(
               this.getPigeonRegistrar().getBinaryMessenger(),
               "dev.flutter.pigeon.webview_flutter_android.WebChromeClient.onJsPrompt",
               this.getPigeonRegistrar().getCodec()
            )
            .send(
               CollectionsKt.listOf(new Object[]{var1, var2, var3, var4, var5}),
               new PigeonApiWebChromeClient$$ExternalSyntheticLambda4(var6, "dev.flutter.pigeon.webview_flutter_android.WebChromeClient.onJsPrompt")
            );
      }
   }

   public fun onPermissionRequest(pigeon_instanceArg: WebChromeClientImpl, requestArg: PermissionRequest, callback: (Result<Unit>) -> Unit) {
      if (this.getPigeonRegistrar().getIgnoreCallsToDart()) {
         val var4: Result.Companion = Result.Companion;
         var3.invoke(
            Result.box-impl(
               Result.constructor-impl(ResultKt.createFailure(new AndroidWebKitError("ignore-calls-error", "Calls to Dart are being ignored.", "")))
            )
         );
      } else {
         new BasicMessageChannel<>(
               this.getPigeonRegistrar().getBinaryMessenger(),
               "dev.flutter.pigeon.webview_flutter_android.WebChromeClient.onPermissionRequest",
               this.getPigeonRegistrar().getCodec()
            )
            .send(
               CollectionsKt.listOf(new Object[]{var1, var2}),
               new PigeonApiWebChromeClient$$ExternalSyntheticLambda7(var3, "dev.flutter.pigeon.webview_flutter_android.WebChromeClient.onPermissionRequest")
            );
      }
   }

   public fun onProgressChanged(pigeon_instanceArg: WebChromeClientImpl, webViewArg: WebView, progressArg: Long, callback: (Result<Unit>) -> Unit) {
      if (this.getPigeonRegistrar().getIgnoreCallsToDart()) {
         val var6: Result.Companion = Result.Companion;
         var5.invoke(
            Result.box-impl(
               Result.constructor-impl(ResultKt.createFailure(new AndroidWebKitError("ignore-calls-error", "Calls to Dart are being ignored.", "")))
            )
         );
      } else {
         new BasicMessageChannel<>(
               this.getPigeonRegistrar().getBinaryMessenger(),
               "dev.flutter.pigeon.webview_flutter_android.WebChromeClient.onProgressChanged",
               this.getPigeonRegistrar().getCodec()
            )
            .send(
               CollectionsKt.listOf(new Object[]{var1, var2, var3}),
               new PigeonApiWebChromeClient$$ExternalSyntheticLambda2(var5, "dev.flutter.pigeon.webview_flutter_android.WebChromeClient.onProgressChanged")
            );
      }
   }

   public fun onShowCustomView(pigeon_instanceArg: WebChromeClientImpl, viewArg: View, callbackArg: CustomViewCallback, callback: (Result<Unit>) -> Unit) {
      if (this.getPigeonRegistrar().getIgnoreCallsToDart()) {
         val var5: Result.Companion = Result.Companion;
         var4.invoke(
            Result.box-impl(
               Result.constructor-impl(ResultKt.createFailure(new AndroidWebKitError("ignore-calls-error", "Calls to Dart are being ignored.", "")))
            )
         );
      } else {
         new BasicMessageChannel<>(
               this.getPigeonRegistrar().getBinaryMessenger(),
               "dev.flutter.pigeon.webview_flutter_android.WebChromeClient.onShowCustomView",
               this.getPigeonRegistrar().getCodec()
            )
            .send(
               CollectionsKt.listOf(new Object[]{var1, var2, var3}),
               new PigeonApiWebChromeClient$$ExternalSyntheticLambda8(var4, "dev.flutter.pigeon.webview_flutter_android.WebChromeClient.onShowCustomView")
            );
      }
   }

   public fun onShowFileChooser(
      pigeon_instanceArg: WebChromeClientImpl,
      webViewArg: WebView,
      paramsArg: FileChooserParams,
      callback: (Result<List<String>>) -> Unit
   ) {
      if (this.getPigeonRegistrar().getIgnoreCallsToDart()) {
         val var5: Result.Companion = Result.Companion;
         var4.invoke(
            Result.box-impl(
               Result.constructor-impl(ResultKt.createFailure(new AndroidWebKitError("ignore-calls-error", "Calls to Dart are being ignored.", "")))
            )
         );
      } else {
         new BasicMessageChannel<>(
               this.getPigeonRegistrar().getBinaryMessenger(),
               "dev.flutter.pigeon.webview_flutter_android.WebChromeClient.onShowFileChooser",
               this.getPigeonRegistrar().getCodec()
            )
            .send(
               CollectionsKt.listOf(new Object[]{var1, var2, var3}),
               new PigeonApiWebChromeClient$$ExternalSyntheticLambda6(var4, "dev.flutter.pigeon.webview_flutter_android.WebChromeClient.onShowFileChooser")
            );
      }
   }

   public abstract fun pigeon_defaultConstructor(): WebChromeClientImpl {
   }

   public fun pigeon_newInstance(pigeon_instanceArg: WebChromeClientImpl, callback: (Result<Unit>) -> Unit) {
      if (this.getPigeonRegistrar().getIgnoreCallsToDart()) {
         val var6: Result.Companion = Result.Companion;
         var2.invoke(
            Result.box-impl(
               Result.constructor-impl(ResultKt.createFailure(new AndroidWebKitError("ignore-calls-error", "Calls to Dart are being ignored.", "")))
            )
         );
      } else if (this.getPigeonRegistrar().getInstanceManager().containsInstance(var1)) {
         val var5: Result.Companion = Result.Companion;
         Result.constructor-impl(Unit.INSTANCE);
      } else {
         new BasicMessageChannel<>(
               this.getPigeonRegistrar().getBinaryMessenger(),
               "dev.flutter.pigeon.webview_flutter_android.WebChromeClient.pigeon_newInstance",
               this.getPigeonRegistrar().getCodec()
            )
            .send(
               CollectionsKt.listOf(this.getPigeonRegistrar().getInstanceManager().addHostCreatedInstance(var1)),
               new PigeonApiWebChromeClient$$ExternalSyntheticLambda0(var2, "dev.flutter.pigeon.webview_flutter_android.WebChromeClient.pigeon_newInstance")
            );
      }
   }

   public abstract fun setSynchronousReturnValueForOnConsoleMessage(pigeon_instance: WebChromeClientImpl, value: Boolean) {
   }

   public abstract fun setSynchronousReturnValueForOnJsAlert(pigeon_instance: WebChromeClientImpl, value: Boolean) {
   }

   public abstract fun setSynchronousReturnValueForOnJsConfirm(pigeon_instance: WebChromeClientImpl, value: Boolean) {
   }

   public abstract fun setSynchronousReturnValueForOnJsPrompt(pigeon_instance: WebChromeClientImpl, value: Boolean) {
   }

   public abstract fun setSynchronousReturnValueForOnShowFileChooser(pigeon_instance: WebChromeClientImpl, value: Boolean) {
   }

   public companion object {
      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUpMessageHandlers$lambda$1$lambda$0`(var0: PigeonApiWebChromeClient, var1: Any, var2: BasicMessageChannel.Reply) {
         var1 = (var1 as java.util.List).get(0);
         val var3: Long = var1 as java.lang.Long;

         label12:
         try {
            var0.getPigeonRegistrar().getInstanceManager().addDartCreatedInstance(var0.pigeon_defaultConstructor(), var3);
            var7 = CollectionsKt.listOf(null);
         } catch (var5: java.lang.Throwable) {
            var7 = AndroidWebkitLibrary_gKt.access$wrapError(var5);
            break label12;
         }

         var2.reply(var7);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUpMessageHandlers$lambda$11$lambda$10`(var0: PigeonApiWebChromeClient, var1: Any, var2: BasicMessageChannel.Reply) {
         val var8: java.util.List = var1 as java.util.List;
         var var4: Any = (var1 as java.util.List).get(0);
         var4 = var4 as WebChromeClientProxyApi.WebChromeClientImpl;
         var1 = var8.get(1);
         val var3: Boolean = var1 as java.lang.Boolean;

         label12:
         try {
            var0.setSynchronousReturnValueForOnJsPrompt((WebChromeClientProxyApi.WebChromeClientImpl)var4, var3);
            var7 = CollectionsKt.listOf(null);
         } catch (var5: java.lang.Throwable) {
            var7 = AndroidWebkitLibrary_gKt.access$wrapError(var5);
            break label12;
         }

         var2.reply(var7);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUpMessageHandlers$lambda$3$lambda$2`(var0: PigeonApiWebChromeClient, var1: Any, var2: BasicMessageChannel.Reply) {
         val var8: java.util.List = var1 as java.util.List;
         var var4: Any = (var1 as java.util.List).get(0);
         var4 = var4 as WebChromeClientProxyApi.WebChromeClientImpl;
         var1 = var8.get(1);
         val var3: Boolean = var1 as java.lang.Boolean;

         label12:
         try {
            var0.setSynchronousReturnValueForOnShowFileChooser((WebChromeClientProxyApi.WebChromeClientImpl)var4, var3);
            var7 = CollectionsKt.listOf(null);
         } catch (var5: java.lang.Throwable) {
            var7 = AndroidWebkitLibrary_gKt.access$wrapError(var5);
            break label12;
         }

         var2.reply(var7);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUpMessageHandlers$lambda$5$lambda$4`(var0: PigeonApiWebChromeClient, var1: Any, var2: BasicMessageChannel.Reply) {
         val var4: java.util.List = var1 as java.util.List;
         var1 = (var1 as java.util.List).get(0);
         var1 = var1 as WebChromeClientProxyApi.WebChromeClientImpl;
         val var10: Any = var4.get(1);
         val var3: Boolean = var10 as java.lang.Boolean;

         label12:
         try {
            var0.setSynchronousReturnValueForOnConsoleMessage(var1, var3);
            var7 = CollectionsKt.listOf(null);
         } catch (var5: java.lang.Throwable) {
            var7 = AndroidWebkitLibrary_gKt.access$wrapError(var5);
            break label12;
         }

         var2.reply(var7);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUpMessageHandlers$lambda$7$lambda$6`(var0: PigeonApiWebChromeClient, var1: Any, var2: BasicMessageChannel.Reply) {
         val var8: java.util.List = var1 as java.util.List;
         var var4: Any = (var1 as java.util.List).get(0);
         var4 = var4 as WebChromeClientProxyApi.WebChromeClientImpl;
         var1 = var8.get(1);
         val var3: Boolean = var1 as java.lang.Boolean;

         label12:
         try {
            var0.setSynchronousReturnValueForOnJsAlert((WebChromeClientProxyApi.WebChromeClientImpl)var4, var3);
            var7 = CollectionsKt.listOf(null);
         } catch (var5: java.lang.Throwable) {
            var7 = AndroidWebkitLibrary_gKt.access$wrapError(var5);
            break label12;
         }

         var2.reply(var7);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUpMessageHandlers$lambda$9$lambda$8`(var0: PigeonApiWebChromeClient, var1: Any, var2: BasicMessageChannel.Reply) {
         val var4: java.util.List = var1 as java.util.List;
         var1 = (var1 as java.util.List).get(0);
         var1 = var1 as WebChromeClientProxyApi.WebChromeClientImpl;
         val var10: Any = var4.get(1);
         val var3: Boolean = var10 as java.lang.Boolean;

         label12:
         try {
            var0.setSynchronousReturnValueForOnJsConfirm(var1, var3);
            var7 = CollectionsKt.listOf(null);
         } catch (var5: java.lang.Throwable) {
            var7 = AndroidWebkitLibrary_gKt.access$wrapError(var5);
            break label12;
         }

         var2.reply(var7);
      }

      public fun setUpMessageHandlers(binaryMessenger: BinaryMessenger, api: PigeonApiWebChromeClient?) {
         var var6: MessageCodec;
         label43: {
            if (var2 != null) {
               val var3: AndroidWebkitLibraryPigeonProxyApiRegistrar = var2.getPigeonRegistrar();
               if (var3 != null) {
                  val var4: MessageCodec = var3.getCodec();
                  var6 = var4;
                  if (var4 != null) {
                     break label43;
                  }
               }
            }

            var6 = new AndroidWebkitLibraryPigeonCodec();
         }

         val var7: PigeonApiWebChromeClient.Companion = this;
         val var8: BasicMessageChannel = new BasicMessageChannel(
            var1, "dev.flutter.pigeon.webview_flutter_android.WebChromeClient.pigeon_defaultConstructor", var6
         );
         if (var2 != null) {
            var8.setMessageHandler(new PigeonApiWebChromeClient$Companion$$ExternalSyntheticLambda0(var2));
         } else {
            var8.setMessageHandler(null);
         }

         val var9: BasicMessageChannel = new BasicMessageChannel(
            var1, "dev.flutter.pigeon.webview_flutter_android.WebChromeClient.setSynchronousReturnValueForOnShowFileChooser", var6
         );
         if (var2 != null) {
            var9.setMessageHandler(new PigeonApiWebChromeClient$Companion$$ExternalSyntheticLambda1(var2));
         } else {
            var9.setMessageHandler(null);
         }

         val var10: BasicMessageChannel = new BasicMessageChannel(
            var1, "dev.flutter.pigeon.webview_flutter_android.WebChromeClient.setSynchronousReturnValueForOnConsoleMessage", var6
         );
         if (var2 != null) {
            var10.setMessageHandler(new PigeonApiWebChromeClient$Companion$$ExternalSyntheticLambda2(var2));
         } else {
            var10.setMessageHandler(null);
         }

         val var11: BasicMessageChannel = new BasicMessageChannel(
            var1, "dev.flutter.pigeon.webview_flutter_android.WebChromeClient.setSynchronousReturnValueForOnJsAlert", var6
         );
         if (var2 != null) {
            var11.setMessageHandler(new PigeonApiWebChromeClient$Companion$$ExternalSyntheticLambda3(var2));
         } else {
            var11.setMessageHandler(null);
         }

         val var12: BasicMessageChannel = new BasicMessageChannel(
            var1, "dev.flutter.pigeon.webview_flutter_android.WebChromeClient.setSynchronousReturnValueForOnJsConfirm", var6
         );
         if (var2 != null) {
            var12.setMessageHandler(new PigeonApiWebChromeClient$Companion$$ExternalSyntheticLambda4(var2));
         } else {
            var12.setMessageHandler(null);
         }

         val var5: BasicMessageChannel = new BasicMessageChannel(
            var1, "dev.flutter.pigeon.webview_flutter_android.WebChromeClient.setSynchronousReturnValueForOnJsPrompt", var6
         );
         if (var2 != null) {
            var5.setMessageHandler(new PigeonApiWebChromeClient$Companion$$ExternalSyntheticLambda5(var2));
         } else {
            var5.setMessageHandler(null);
         }
      }
   }
}
