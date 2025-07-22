package io.flutter.plugins.webviewflutter

import android.webkit.DownloadListener
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import io.flutter.plugin.common.BasicMessageChannel
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MessageCodec
import io.flutter.plugins.webviewflutter.WebChromeClientProxyApi.WebChromeClientImpl
import kotlin.jvm.functions.Function1

public abstract class PigeonApiWebView {
   public open val pigeonRegistrar: AndroidWebkitLibraryPigeonProxyApiRegistrar

   open fun PigeonApiWebView(var1: AndroidWebkitLibraryPigeonProxyApiRegistrar) {
      this.pigeonRegistrar = var1;
   }

   @JvmStatic
   fun `onScrollChanged$lambda$1`(var0: Function1, var1: java.lang.String, var2: Any) {
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

   public abstract fun addJavaScriptChannel(pigeon_instance: WebView, channel: JavaScriptChannel) {
   }

   public abstract fun canGoBack(pigeon_instance: WebView): Boolean {
   }

   public abstract fun canGoForward(pigeon_instance: WebView): Boolean {
   }

   public abstract fun clearCache(pigeon_instance: WebView, includeDiskFiles: Boolean) {
   }

   public abstract fun destroy(pigeon_instance: WebView) {
   }

   public abstract fun evaluateJavascript(pigeon_instance: WebView, javascriptString: String, callback: (Result<String?>) -> Unit) {
   }

   public abstract fun getTitle(pigeon_instance: WebView): String? {
   }

   public abstract fun getUrl(pigeon_instance: WebView): String? {
   }

   public abstract fun goBack(pigeon_instance: WebView) {
   }

   public abstract fun goForward(pigeon_instance: WebView) {
   }

   public abstract fun loadData(pigeon_instance: WebView, data: String, mimeType: String?, encoding: String?) {
   }

   public abstract fun loadDataWithBaseUrl(pigeon_instance: WebView, baseUrl: String?, data: String, mimeType: String?, encoding: String?, historyUrl: String?) {
   }

   public abstract fun loadUrl(pigeon_instance: WebView, url: String, headers: Map<String, String>) {
   }

   public fun onScrollChanged(pigeon_instanceArg: WebView, leftArg: Long, topArg: Long, oldLeftArg: Long, oldTopArg: Long, callback: (Result<Unit>) -> Unit) {
      if (this.getPigeonRegistrar().getIgnoreCallsToDart()) {
         val var11: Result.Companion = Result.Companion;
         var10.invoke(
            Result.box-impl(
               Result.constructor-impl(ResultKt.createFailure(new AndroidWebKitError("ignore-calls-error", "Calls to Dart are being ignored.", "")))
            )
         );
      } else {
         new BasicMessageChannel<>(
               this.getPigeonRegistrar().getBinaryMessenger(),
               "dev.flutter.pigeon.webview_flutter_android.WebView.onScrollChanged",
               this.getPigeonRegistrar().getCodec()
            )
            .send(
               CollectionsKt.listOf(new Object[]{var1, var2, var4, var6, var8}),
               new PigeonApiWebView$$ExternalSyntheticLambda0(var10, "dev.flutter.pigeon.webview_flutter_android.WebView.onScrollChanged")
            );
      }
   }

   public abstract fun pigeon_defaultConstructor(): WebView {
   }

   public fun pigeon_getPigeonApiView(): PigeonApiView {
      return this.getPigeonRegistrar().getPigeonApiView();
   }

   public fun pigeon_newInstance(pigeon_instanceArg: WebView, callback: (Result<Unit>) -> Unit) {
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
               "dev.flutter.pigeon.webview_flutter_android.WebView.pigeon_newInstance",
               this.getPigeonRegistrar().getCodec()
            )
            .send(
               CollectionsKt.listOf(this.getPigeonRegistrar().getInstanceManager().addHostCreatedInstance(var1)),
               new PigeonApiWebView$$ExternalSyntheticLambda1(var2, "dev.flutter.pigeon.webview_flutter_android.WebView.pigeon_newInstance")
            );
      }
   }

   public abstract fun postUrl(pigeon_instance: WebView, url: String, data: ByteArray) {
   }

   public abstract fun reload(pigeon_instance: WebView) {
   }

   public abstract fun removeJavaScriptChannel(pigeon_instance: WebView, name: String) {
   }

   public abstract fun setBackgroundColor(pigeon_instance: WebView, color: Long) {
   }

   public abstract fun setDownloadListener(pigeon_instance: WebView, listener: DownloadListener?) {
   }

   public abstract fun setWebChromeClient(pigeon_instance: WebView, client: WebChromeClientImpl?) {
   }

   public abstract fun setWebContentsDebuggingEnabled(enabled: Boolean) {
   }

   public abstract fun setWebViewClient(pigeon_instance: WebView, client: WebViewClient?) {
   }

   public abstract fun settings(pigeon_instance: WebView): WebSettings {
   }

   public companion object {
      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUpMessageHandlers$lambda$1$lambda$0`(var0: PigeonApiWebView, var1: Any, var2: BasicMessageChannel.Reply) {
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
      fun `setUpMessageHandlers$lambda$11$lambda$10`(var0: PigeonApiWebView, var1: Any, var2: BasicMessageChannel.Reply) {
         val var4: java.util.List = var1 as java.util.List;
         var1 = (var1 as java.util.List).get(0);
         var1 = var1 as WebView;
         var var3: Any = var4.get(1);
         var3 = var3 as java.lang.String;
         var var11: Any = var4.get(2);
         var11 = var11 as ByteArray;

         label12:
         try {
            var0.postUrl(var1, (java.lang.String)var3, (byte[])var11);
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
      fun `setUpMessageHandlers$lambda$13$lambda$12`(var0: PigeonApiWebView, var1: Any, var2: BasicMessageChannel.Reply) {
         var1 = (var1 as java.util.List).get(0);
         var1 = var1 as WebView;

         label12:
         try {
            var5 = CollectionsKt.listOf(var0.getUrl(var1));
         } catch (var3: java.lang.Throwable) {
            var5 = AndroidWebkitLibrary_gKt.access$wrapError(var3);
            break label12;
         }

         var2.reply(var5);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUpMessageHandlers$lambda$15$lambda$14`(var0: PigeonApiWebView, var1: Any, var2: BasicMessageChannel.Reply) {
         var1 = (var1 as java.util.List).get(0);
         var1 = var1 as WebView;

         label12:
         try {
            var5 = CollectionsKt.listOf(var0.canGoBack(var1));
         } catch (var3: java.lang.Throwable) {
            var5 = AndroidWebkitLibrary_gKt.access$wrapError(var3);
            break label12;
         }

         var2.reply(var5);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUpMessageHandlers$lambda$17$lambda$16`(var0: PigeonApiWebView, var1: Any, var2: BasicMessageChannel.Reply) {
         var1 = (var1 as java.util.List).get(0);
         var1 = var1 as WebView;

         label12:
         try {
            var5 = CollectionsKt.listOf(var0.canGoForward(var1));
         } catch (var3: java.lang.Throwable) {
            var5 = AndroidWebkitLibrary_gKt.access$wrapError(var3);
            break label12;
         }

         var2.reply(var5);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUpMessageHandlers$lambda$19$lambda$18`(var0: PigeonApiWebView, var1: Any, var2: BasicMessageChannel.Reply) {
         var1 = (var1 as java.util.List).get(0);
         var1 = var1 as WebView;

         label12:
         try {
            var0.goBack(var1);
            var5 = CollectionsKt.listOf(null);
         } catch (var3: java.lang.Throwable) {
            var5 = AndroidWebkitLibrary_gKt.access$wrapError(var3);
            break label12;
         }

         var2.reply(var5);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUpMessageHandlers$lambda$21$lambda$20`(var0: PigeonApiWebView, var1: Any, var2: BasicMessageChannel.Reply) {
         var1 = (var1 as java.util.List).get(0);
         var1 = var1 as WebView;

         label12:
         try {
            var0.goForward(var1);
            var5 = CollectionsKt.listOf(null);
         } catch (var3: java.lang.Throwable) {
            var5 = AndroidWebkitLibrary_gKt.access$wrapError(var3);
            break label12;
         }

         var2.reply(var5);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUpMessageHandlers$lambda$23$lambda$22`(var0: PigeonApiWebView, var1: Any, var2: BasicMessageChannel.Reply) {
         var1 = (var1 as java.util.List).get(0);
         var1 = var1 as WebView;

         label12:
         try {
            var0.reload(var1);
            var5 = CollectionsKt.listOf(null);
         } catch (var3: java.lang.Throwable) {
            var5 = AndroidWebkitLibrary_gKt.access$wrapError(var3);
            break label12;
         }

         var2.reply(var5);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUpMessageHandlers$lambda$25$lambda$24`(var0: PigeonApiWebView, var1: Any, var2: BasicMessageChannel.Reply) {
         val var4: java.util.List = var1 as java.util.List;
         var1 = (var1 as java.util.List).get(0);
         var1 = var1 as WebView;
         val var10: Any = var4.get(1);
         val var3: Boolean = var10 as java.lang.Boolean;

         label12:
         try {
            var0.clearCache(var1, var3);
            var7 = CollectionsKt.listOf(null);
         } catch (var5: java.lang.Throwable) {
            var7 = AndroidWebkitLibrary_gKt.access$wrapError(var5);
            break label12;
         }

         var2.reply(var7);
      }

      @JvmStatic
      fun `setUpMessageHandlers$lambda$28$lambda$27`(var0: PigeonApiWebView, var1: Any, var2: BasicMessageChannel.Reply) {
         val var4: java.util.List = var1 as java.util.List;
         var var3: Any = (var1 as java.util.List).get(0);
         var3 = var3 as WebView;
         var1 = var4.get(1);
         var0.evaluateJavascript((WebView)var3, var1 as java.lang.String, new PigeonApiWebView$Companion$$ExternalSyntheticLambda15(var2));
      }

      @JvmStatic
      fun `setUpMessageHandlers$lambda$28$lambda$27$lambda$26`(var0: BasicMessageChannel.Reply, var1: Result): Unit {
         var var2: java.lang.Throwable = Result.exceptionOrNull-impl(var1.unbox-impl());
         if (var2 != null) {
            var0.reply(AndroidWebkitLibrary_gKt.access$wrapError(var2));
         } else {
            var2 = (java.lang.Throwable)var1.unbox-impl();
            var var3: Any = var2;
            if (Result.isFailure-impl(var2)) {
               var3 = null;
            }

            var0.reply(AndroidWebkitLibrary_gKt.access$wrapResult(var3 as java.lang.String));
         }

         return Unit.INSTANCE;
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUpMessageHandlers$lambda$3$lambda$2`(var0: PigeonApiWebView, var1: Any, var2: BasicMessageChannel.Reply) {
         val var5: java.util.List = var1 as java.util.List;
         var1 = (var1 as java.util.List).get(0);
         var1 = var1 as WebView;
         val var11: Any = var5.get(1);
         val var3: Long = var11 as java.lang.Long;

         label12:
         try {
            var0.getPigeonRegistrar().getInstanceManager().addDartCreatedInstance(var0.settings(var1), var3);
            var8 = CollectionsKt.listOf(null);
         } catch (var6: java.lang.Throwable) {
            var8 = AndroidWebkitLibrary_gKt.access$wrapError(var6);
            break label12;
         }

         var2.reply(var8);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUpMessageHandlers$lambda$30$lambda$29`(var0: PigeonApiWebView, var1: Any, var2: BasicMessageChannel.Reply) {
         var1 = (var1 as java.util.List).get(0);
         var1 = var1 as WebView;

         label12:
         try {
            var5 = CollectionsKt.listOf(var0.getTitle(var1));
         } catch (var3: java.lang.Throwable) {
            var5 = AndroidWebkitLibrary_gKt.access$wrapError(var3);
            break label12;
         }

         var2.reply(var5);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUpMessageHandlers$lambda$32$lambda$31`(var0: PigeonApiWebView, var1: Any, var2: BasicMessageChannel.Reply) {
         var1 = (var1 as java.util.List).get(0);
         val var3: Boolean = var1 as java.lang.Boolean;

         label12:
         try {
            var0.setWebContentsDebuggingEnabled(var3);
            var6 = CollectionsKt.listOf(null);
         } catch (var4: java.lang.Throwable) {
            var6 = AndroidWebkitLibrary_gKt.access$wrapError(var4);
            break label12;
         }

         var2.reply(var6);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUpMessageHandlers$lambda$34$lambda$33`(var0: PigeonApiWebView, var1: Any, var2: BasicMessageChannel.Reply) {
         val var3: java.util.List = var1 as java.util.List;
         var1 = (var1 as java.util.List).get(0);
         var1 = var1 as WebView;
         val var9: WebViewClient = var3.get(1) as WebViewClient;

         label12:
         try {
            var0.setWebViewClient(var1, var9);
            var6 = CollectionsKt.listOf(null);
         } catch (var4: java.lang.Throwable) {
            var6 = AndroidWebkitLibrary_gKt.access$wrapError(var4);
            break label12;
         }

         var2.reply(var6);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUpMessageHandlers$lambda$36$lambda$35`(var0: PigeonApiWebView, var1: Any, var2: BasicMessageChannel.Reply) {
         val var3: java.util.List = var1 as java.util.List;
         var1 = (var1 as java.util.List).get(0);
         var1 = var1 as WebView;
         var var9: Any = var3.get(1);
         var9 = var9 as JavaScriptChannel;

         label12:
         try {
            var0.addJavaScriptChannel(var1, (JavaScriptChannel)var9);
            var6 = CollectionsKt.listOf(null);
         } catch (var4: java.lang.Throwable) {
            var6 = AndroidWebkitLibrary_gKt.access$wrapError(var4);
            break label12;
         }

         var2.reply(var6);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUpMessageHandlers$lambda$38$lambda$37`(var0: PigeonApiWebView, var1: Any, var2: BasicMessageChannel.Reply) {
         val var3: java.util.List = var1 as java.util.List;
         var1 = (var1 as java.util.List).get(0);
         var1 = var1 as WebView;
         var var9: Any = var3.get(1);
         var9 = var9 as java.lang.String;

         label12:
         try {
            var0.removeJavaScriptChannel(var1, (java.lang.String)var9);
            var6 = CollectionsKt.listOf(null);
         } catch (var4: java.lang.Throwable) {
            var6 = AndroidWebkitLibrary_gKt.access$wrapError(var4);
            break label12;
         }

         var2.reply(var6);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUpMessageHandlers$lambda$40$lambda$39`(var0: PigeonApiWebView, var1: Any, var2: BasicMessageChannel.Reply) {
         val var3: java.util.List = var1 as java.util.List;
         var1 = (var1 as java.util.List).get(0);
         var1 = var1 as WebView;
         val var9: DownloadListener = var3.get(1) as DownloadListener;

         label12:
         try {
            var0.setDownloadListener(var1, var9);
            var6 = CollectionsKt.listOf(null);
         } catch (var4: java.lang.Throwable) {
            var6 = AndroidWebkitLibrary_gKt.access$wrapError(var4);
            break label12;
         }

         var2.reply(var6);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUpMessageHandlers$lambda$42$lambda$41`(var0: PigeonApiWebView, var1: Any, var2: BasicMessageChannel.Reply) {
         val var3: java.util.List = var1 as java.util.List;
         var1 = (var1 as java.util.List).get(0);
         var1 = var1 as WebView;
         val var9: WebChromeClientProxyApi.WebChromeClientImpl = var3.get(1) as WebChromeClientProxyApi.WebChromeClientImpl;

         label12:
         try {
            var0.setWebChromeClient(var1, var9);
            var6 = CollectionsKt.listOf(null);
         } catch (var4: java.lang.Throwable) {
            var6 = AndroidWebkitLibrary_gKt.access$wrapError(var4);
            break label12;
         }

         var2.reply(var6);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUpMessageHandlers$lambda$44$lambda$43`(var0: PigeonApiWebView, var1: Any, var2: BasicMessageChannel.Reply) {
         val var5: java.util.List = var1 as java.util.List;
         var1 = (var1 as java.util.List).get(0);
         var1 = var1 as WebView;
         val var11: Any = var5.get(1);
         val var3: Long = var11 as java.lang.Long;

         label12:
         try {
            var0.setBackgroundColor(var1, var3);
            var8 = CollectionsKt.listOf(null);
         } catch (var6: java.lang.Throwable) {
            var8 = AndroidWebkitLibrary_gKt.access$wrapError(var6);
            break label12;
         }

         var2.reply(var8);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUpMessageHandlers$lambda$46$lambda$45`(var0: PigeonApiWebView, var1: Any, var2: BasicMessageChannel.Reply) {
         var1 = (var1 as java.util.List).get(0);
         var1 = var1 as WebView;

         label12:
         try {
            var0.destroy(var1);
            var5 = CollectionsKt.listOf(null);
         } catch (var3: java.lang.Throwable) {
            var5 = AndroidWebkitLibrary_gKt.access$wrapError(var3);
            break label12;
         }

         var2.reply(var5);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUpMessageHandlers$lambda$5$lambda$4`(var0: PigeonApiWebView, var1: Any, var2: BasicMessageChannel.Reply) {
         val var9: java.util.List = var1 as java.util.List;
         var var3: Any = (var1 as java.util.List).get(0);
         var3 = var3 as WebView;
         var var4: Any = var9.get(1);
         var4 = var4 as java.lang.String;
         val var5: java.lang.String = var9.get(2) as java.lang.String;
         var1 = var9.get(3) as java.lang.String;

         label12:
         try {
            var0.loadData((WebView)var3, (java.lang.String)var4, var5, var1);
            var8 = CollectionsKt.listOf(null);
         } catch (var6: java.lang.Throwable) {
            var8 = AndroidWebkitLibrary_gKt.access$wrapError(var6);
            break label12;
         }

         var2.reply(var8);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUpMessageHandlers$lambda$7$lambda$6`(var0: PigeonApiWebView, var1: Any, var2: BasicMessageChannel.Reply) {
         val var11: java.util.List = var1 as java.util.List;
         var var3: Any = (var1 as java.util.List).get(0);
         val var4: WebView = var3 as WebView;
         var3 = var11.get(1) as java.lang.String;
         var var5: Any = var11.get(2);
         val var7: java.lang.String = var5 as java.lang.String;
         val var6: java.lang.String = var11.get(3) as java.lang.String;
         var5 = var11.get(4) as java.lang.String;
         var1 = var11.get(5) as java.lang.String;

         label12:
         try {
            var0.loadDataWithBaseUrl(var4, (java.lang.String)var3, var7, var6, (java.lang.String)var5, var1);
            var10 = CollectionsKt.listOf(null);
         } catch (var8: java.lang.Throwable) {
            var10 = AndroidWebkitLibrary_gKt.access$wrapError(var8);
            break label12;
         }

         var2.reply(var10);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @JvmStatic
      fun `setUpMessageHandlers$lambda$9$lambda$8`(var0: PigeonApiWebView, var1: Any, var2: BasicMessageChannel.Reply) {
         val var3: java.util.List = var1 as java.util.List;
         var1 = (var1 as java.util.List).get(0);
         var1 = var1 as WebView;
         var var4: Any = var3.get(1);
         var4 = var4 as java.lang.String;
         var var10: Any = var3.get(2);
         var10 = var10 as java.util.Map;

         label12:
         try {
            var0.loadUrl(var1, (java.lang.String)var4, (java.util.Map<java.lang.String, java.lang.String>)var10);
            var7 = CollectionsKt.listOf(null);
         } catch (var5: java.lang.Throwable) {
            var7 = AndroidWebkitLibrary_gKt.access$wrapError(var5);
            break label12;
         }

         var2.reply(var7);
      }

      public fun setUpMessageHandlers(binaryMessenger: BinaryMessenger, api: PigeonApiWebView?) {
         var var6: MessageCodec;
         label128: {
            if (var2 != null) {
               val var3: AndroidWebkitLibraryPigeonProxyApiRegistrar = var2.getPigeonRegistrar();
               if (var3 != null) {
                  val var4: MessageCodec = var3.getCodec();
                  var6 = var4;
                  if (var4 != null) {
                     break label128;
                  }
               }
            }

            var6 = new AndroidWebkitLibraryPigeonCodec();
         }

         val var7: PigeonApiWebView.Companion = this;
         val var8: BasicMessageChannel = new BasicMessageChannel(var1, "dev.flutter.pigeon.webview_flutter_android.WebView.pigeon_defaultConstructor", var6);
         if (var2 != null) {
            var8.setMessageHandler(new PigeonApiWebView$Companion$$ExternalSyntheticLambda0(var2));
         } else {
            var8.setMessageHandler(null);
         }

         val var9: BasicMessageChannel = new BasicMessageChannel(var1, "dev.flutter.pigeon.webview_flutter_android.WebView.settings", var6);
         if (var2 != null) {
            var9.setMessageHandler(new PigeonApiWebView$Companion$$ExternalSyntheticLambda2(var2));
         } else {
            var9.setMessageHandler(null);
         }

         val var10: BasicMessageChannel = new BasicMessageChannel(var1, "dev.flutter.pigeon.webview_flutter_android.WebView.loadData", var6);
         if (var2 != null) {
            var10.setMessageHandler(new PigeonApiWebView$Companion$$ExternalSyntheticLambda6(var2));
         } else {
            var10.setMessageHandler(null);
         }

         val var11: BasicMessageChannel = new BasicMessageChannel(var1, "dev.flutter.pigeon.webview_flutter_android.WebView.loadDataWithBaseUrl", var6);
         if (var2 != null) {
            var11.setMessageHandler(new PigeonApiWebView$Companion$$ExternalSyntheticLambda7(var2));
         } else {
            var11.setMessageHandler(null);
         }

         val var12: BasicMessageChannel = new BasicMessageChannel(var1, "dev.flutter.pigeon.webview_flutter_android.WebView.loadUrl", var6);
         if (var2 != null) {
            var12.setMessageHandler(new PigeonApiWebView$Companion$$ExternalSyntheticLambda8(var2));
         } else {
            var12.setMessageHandler(null);
         }

         val var13: BasicMessageChannel = new BasicMessageChannel(var1, "dev.flutter.pigeon.webview_flutter_android.WebView.postUrl", var6);
         if (var2 != null) {
            var13.setMessageHandler(new PigeonApiWebView$Companion$$ExternalSyntheticLambda9(var2));
         } else {
            var13.setMessageHandler(null);
         }

         val var14: BasicMessageChannel = new BasicMessageChannel(var1, "dev.flutter.pigeon.webview_flutter_android.WebView.getUrl", var6);
         if (var2 != null) {
            var14.setMessageHandler(new PigeonApiWebView$Companion$$ExternalSyntheticLambda10(var2));
         } else {
            var14.setMessageHandler(null);
         }

         val var15: BasicMessageChannel = new BasicMessageChannel(var1, "dev.flutter.pigeon.webview_flutter_android.WebView.canGoBack", var6);
         if (var2 != null) {
            var15.setMessageHandler(new PigeonApiWebView$Companion$$ExternalSyntheticLambda12(var2));
         } else {
            var15.setMessageHandler(null);
         }

         val var16: BasicMessageChannel = new BasicMessageChannel(var1, "dev.flutter.pigeon.webview_flutter_android.WebView.canGoForward", var6);
         if (var2 != null) {
            var16.setMessageHandler(new PigeonApiWebView$Companion$$ExternalSyntheticLambda13(var2));
         } else {
            var16.setMessageHandler(null);
         }

         val var17: BasicMessageChannel = new BasicMessageChannel(var1, "dev.flutter.pigeon.webview_flutter_android.WebView.goBack", var6);
         if (var2 != null) {
            var17.setMessageHandler(new PigeonApiWebView$Companion$$ExternalSyntheticLambda14(var2));
         } else {
            var17.setMessageHandler(null);
         }

         val var18: BasicMessageChannel = new BasicMessageChannel(var1, "dev.flutter.pigeon.webview_flutter_android.WebView.goForward", var6);
         if (var2 != null) {
            var18.setMessageHandler(new PigeonApiWebView$Companion$$ExternalSyntheticLambda11(var2));
         } else {
            var18.setMessageHandler(null);
         }

         val var19: BasicMessageChannel = new BasicMessageChannel(var1, "dev.flutter.pigeon.webview_flutter_android.WebView.reload", var6);
         if (var2 != null) {
            var19.setMessageHandler(new PigeonApiWebView$Companion$$ExternalSyntheticLambda16(var2));
         } else {
            var19.setMessageHandler(null);
         }

         val var20: BasicMessageChannel = new BasicMessageChannel(var1, "dev.flutter.pigeon.webview_flutter_android.WebView.clearCache", var6);
         if (var2 != null) {
            var20.setMessageHandler(new PigeonApiWebView$Companion$$ExternalSyntheticLambda17(var2));
         } else {
            var20.setMessageHandler(null);
         }

         val var21: BasicMessageChannel = new BasicMessageChannel(var1, "dev.flutter.pigeon.webview_flutter_android.WebView.evaluateJavascript", var6);
         if (var2 != null) {
            var21.setMessageHandler(new PigeonApiWebView$Companion$$ExternalSyntheticLambda18(var2));
         } else {
            var21.setMessageHandler(null);
         }

         val var22: BasicMessageChannel = new BasicMessageChannel(var1, "dev.flutter.pigeon.webview_flutter_android.WebView.getTitle", var6);
         if (var2 != null) {
            var22.setMessageHandler(new PigeonApiWebView$Companion$$ExternalSyntheticLambda19(var2));
         } else {
            var22.setMessageHandler(null);
         }

         val var23: BasicMessageChannel = new BasicMessageChannel(
            var1, "dev.flutter.pigeon.webview_flutter_android.WebView.setWebContentsDebuggingEnabled", var6
         );
         if (var2 != null) {
            var23.setMessageHandler(new PigeonApiWebView$Companion$$ExternalSyntheticLambda20(var2));
         } else {
            var23.setMessageHandler(null);
         }

         val var24: BasicMessageChannel = new BasicMessageChannel(var1, "dev.flutter.pigeon.webview_flutter_android.WebView.setWebViewClient", var6);
         if (var2 != null) {
            var24.setMessageHandler(new PigeonApiWebView$Companion$$ExternalSyntheticLambda21(var2));
         } else {
            var24.setMessageHandler(null);
         }

         val var25: BasicMessageChannel = new BasicMessageChannel(var1, "dev.flutter.pigeon.webview_flutter_android.WebView.addJavaScriptChannel", var6);
         if (var2 != null) {
            var25.setMessageHandler(new PigeonApiWebView$Companion$$ExternalSyntheticLambda22(var2));
         } else {
            var25.setMessageHandler(null);
         }

         val var26: BasicMessageChannel = new BasicMessageChannel(var1, "dev.flutter.pigeon.webview_flutter_android.WebView.removeJavaScriptChannel", var6);
         if (var2 != null) {
            var26.setMessageHandler(new PigeonApiWebView$Companion$$ExternalSyntheticLambda23(var2));
         } else {
            var26.setMessageHandler(null);
         }

         val var27: BasicMessageChannel = new BasicMessageChannel(var1, "dev.flutter.pigeon.webview_flutter_android.WebView.setDownloadListener", var6);
         if (var2 != null) {
            var27.setMessageHandler(new PigeonApiWebView$Companion$$ExternalSyntheticLambda1(var2));
         } else {
            var27.setMessageHandler(null);
         }

         val var28: BasicMessageChannel = new BasicMessageChannel(var1, "dev.flutter.pigeon.webview_flutter_android.WebView.setWebChromeClient", var6);
         if (var2 != null) {
            var28.setMessageHandler(new PigeonApiWebView$Companion$$ExternalSyntheticLambda3(var2));
         } else {
            var28.setMessageHandler(null);
         }

         val var29: BasicMessageChannel = new BasicMessageChannel(var1, "dev.flutter.pigeon.webview_flutter_android.WebView.setBackgroundColor", var6);
         if (var2 != null) {
            var29.setMessageHandler(new PigeonApiWebView$Companion$$ExternalSyntheticLambda4(var2));
         } else {
            var29.setMessageHandler(null);
         }

         val var5: BasicMessageChannel = new BasicMessageChannel(var1, "dev.flutter.pigeon.webview_flutter_android.WebView.destroy", var6);
         if (var2 != null) {
            var5.setMessageHandler(new PigeonApiWebView$Companion$$ExternalSyntheticLambda5(var2));
         } else {
            var5.setMessageHandler(null);
         }
      }
   }
}
