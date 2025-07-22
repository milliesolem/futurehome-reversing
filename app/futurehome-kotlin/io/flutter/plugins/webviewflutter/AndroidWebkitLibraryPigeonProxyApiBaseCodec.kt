package io.flutter.plugins.webviewflutter

import android.os.Build.VERSION
import android.view.View
import android.webkit.ConsoleMessage
import android.webkit.CookieManager
import android.webkit.DownloadListener
import android.webkit.HttpAuthHandler
import android.webkit.PermissionRequest
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebSettings
import android.webkit.WebStorage
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.GeolocationPermissions.Callback
import android.webkit.WebChromeClient.CustomViewCallback
import android.webkit.WebChromeClient.FileChooserParams
import androidx.webkit.WebResourceErrorCompat
import dev.fluttercommunity.plus.share.Share$$ExternalSyntheticApiModelOutline0
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer

private class AndroidWebkitLibraryPigeonProxyApiBaseCodec(registrar: AndroidWebkitLibraryPigeonProxyApiRegistrar) : AndroidWebkitLibraryPigeonCodec {
   public final val registrar: AndroidWebkitLibraryPigeonProxyApiRegistrar

   init {
      this.registrar = var1;
   }

   @JvmStatic
   fun `writeValue$lambda$0`(var0: Result): Unit {
      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `writeValue$lambda$1`(var0: Result): Unit {
      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `writeValue$lambda$10`(var0: Result): Unit {
      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `writeValue$lambda$11`(var0: Result): Unit {
      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `writeValue$lambda$12`(var0: Result): Unit {
      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `writeValue$lambda$13`(var0: Result): Unit {
      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `writeValue$lambda$14`(var0: Result): Unit {
      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `writeValue$lambda$15`(var0: Result): Unit {
      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `writeValue$lambda$16`(var0: Result): Unit {
      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `writeValue$lambda$17`(var0: Result): Unit {
      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `writeValue$lambda$18`(var0: Result): Unit {
      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `writeValue$lambda$19`(var0: Result): Unit {
      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `writeValue$lambda$2`(var0: Result): Unit {
      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `writeValue$lambda$20`(var0: Result): Unit {
      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `writeValue$lambda$3`(var0: Result): Unit {
      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `writeValue$lambda$4`(var0: Result): Unit {
      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `writeValue$lambda$5`(var0: Result): Unit {
      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `writeValue$lambda$6`(var0: Result): Unit {
      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `writeValue$lambda$7`(var0: Result): Unit {
      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `writeValue$lambda$8`(var0: Result): Unit {
      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `writeValue$lambda$9`(var0: Result): Unit {
      return Unit.INSTANCE;
   }

   protected override fun readValueOfType(type: Byte, buffer: ByteBuffer): Any? {
      if (var1 == -128) {
         val var3: AndroidWebkitLibraryPigeonInstanceManager = this.registrar.getInstanceManager();
         val var4: Any = this.readValue(var2);
         return var3.getInstance(var4 as java.lang.Long);
      } else {
         return super.readValueOfType(var1, var2);
      }
   }

   protected override fun writeValue(stream: ByteArrayOutputStream, value: Any?) {
      if (var2 !is java.lang.Boolean
         && var2 !is ByteArray
         && var2 !is java.lang.Double
         && var2 !is DoubleArray
         && var2 !is FloatArray
         && var2 !is Int
         && var2 !is IntArray
         && var2 !is java.util.List
         && var2 !is java.lang.Long
         && var2 !is LongArray
         && var2 !is java.util.Map
         && var2 !is java.lang.String
         && var2 !is FileChooserMode
         && var2 !is ConsoleMessageLevel
         && var2 != null) {
         if (var2 is WebResourceRequest) {
            this.registrar
               .getPigeonApiWebResourceRequest()
               .pigeon_newInstance(var2 as WebResourceRequest, new AndroidWebkitLibraryPigeonProxyApiBaseCodec$$ExternalSyntheticLambda15());
         } else if (var2 is WebResourceResponse) {
            this.registrar
               .getPigeonApiWebResourceResponse()
               .pigeon_newInstance(var2 as WebResourceResponse, new AndroidWebkitLibraryPigeonProxyApiBaseCodec$$ExternalSyntheticLambda5());
         } else if (VERSION.SDK_INT >= 23 && Share$$ExternalSyntheticApiModelOutline0.m(var2)) {
            this.registrar
               .getPigeonApiWebResourceError()
               .pigeon_newInstance(
                  Share$$ExternalSyntheticApiModelOutline0.m(var2), new AndroidWebkitLibraryPigeonProxyApiBaseCodec$$ExternalSyntheticLambda9()
               );
         } else if (var2 is WebResourceErrorCompat) {
            this.registrar
               .getPigeonApiWebResourceErrorCompat()
               .pigeon_newInstance(var2 as WebResourceErrorCompat, new AndroidWebkitLibraryPigeonProxyApiBaseCodec$$ExternalSyntheticLambda10());
         } else if (var2 is WebViewPoint) {
            this.registrar
               .getPigeonApiWebViewPoint()
               .pigeon_newInstance(var2 as WebViewPoint, new AndroidWebkitLibraryPigeonProxyApiBaseCodec$$ExternalSyntheticLambda11());
         } else if (var2 is ConsoleMessage) {
            this.registrar
               .getPigeonApiConsoleMessage()
               .pigeon_newInstance(var2 as ConsoleMessage, new AndroidWebkitLibraryPigeonProxyApiBaseCodec$$ExternalSyntheticLambda12());
         } else if (var2 is CookieManager) {
            this.registrar
               .getPigeonApiCookieManager()
               .pigeon_newInstance(var2 as CookieManager, new AndroidWebkitLibraryPigeonProxyApiBaseCodec$$ExternalSyntheticLambda13());
         } else if (var2 is WebView) {
            this.registrar
               .getPigeonApiWebView()
               .pigeon_newInstance(var2 as WebView, new AndroidWebkitLibraryPigeonProxyApiBaseCodec$$ExternalSyntheticLambda14());
         } else if (var2 is WebSettings) {
            this.registrar
               .getPigeonApiWebSettings()
               .pigeon_newInstance(var2 as WebSettings, new AndroidWebkitLibraryPigeonProxyApiBaseCodec$$ExternalSyntheticLambda16());
         } else if (var2 is JavaScriptChannel) {
            this.registrar
               .getPigeonApiJavaScriptChannel()
               .pigeon_newInstance(var2 as JavaScriptChannel, new AndroidWebkitLibraryPigeonProxyApiBaseCodec$$ExternalSyntheticLambda17());
         } else if (var2 is WebViewClient) {
            this.registrar
               .getPigeonApiWebViewClient()
               .pigeon_newInstance(var2 as WebViewClient, new AndroidWebkitLibraryPigeonProxyApiBaseCodec$$ExternalSyntheticLambda18());
         } else if (var2 is DownloadListener) {
            this.registrar
               .getPigeonApiDownloadListener()
               .pigeon_newInstance(var2 as DownloadListener, new AndroidWebkitLibraryPigeonProxyApiBaseCodec$$ExternalSyntheticLambda19());
         } else if (var2 is WebChromeClientProxyApi.WebChromeClientImpl) {
            this.registrar
               .getPigeonApiWebChromeClient()
               .pigeon_newInstance(
                  var2 as WebChromeClientProxyApi.WebChromeClientImpl, new AndroidWebkitLibraryPigeonProxyApiBaseCodec$$ExternalSyntheticLambda20()
               );
         } else if (var2 is FlutterAssetManager) {
            this.registrar
               .getPigeonApiFlutterAssetManager()
               .pigeon_newInstance(var2 as FlutterAssetManager, new AndroidWebkitLibraryPigeonProxyApiBaseCodec$$ExternalSyntheticLambda21());
         } else if (var2 is WebStorage) {
            this.registrar
               .getPigeonApiWebStorage()
               .pigeon_newInstance(var2 as WebStorage, new AndroidWebkitLibraryPigeonProxyApiBaseCodec$$ExternalSyntheticLambda22());
         } else if (var2 is FileChooserParams) {
            this.registrar
               .getPigeonApiFileChooserParams()
               .pigeon_newInstance(var2 as FileChooserParams, new AndroidWebkitLibraryPigeonProxyApiBaseCodec$$ExternalSyntheticLambda2());
         } else if (var2 is PermissionRequest) {
            this.registrar
               .getPigeonApiPermissionRequest()
               .pigeon_newInstance(var2 as PermissionRequest, new AndroidWebkitLibraryPigeonProxyApiBaseCodec$$ExternalSyntheticLambda3());
         } else if (var2 is CustomViewCallback) {
            this.registrar
               .getPigeonApiCustomViewCallback()
               .pigeon_newInstance(var2 as CustomViewCallback, new AndroidWebkitLibraryPigeonProxyApiBaseCodec$$ExternalSyntheticLambda4());
         } else if (var2 is View) {
            this.registrar.getPigeonApiView().pigeon_newInstance(var2 as View, new AndroidWebkitLibraryPigeonProxyApiBaseCodec$$ExternalSyntheticLambda6());
         } else if (var2 is Callback) {
            this.registrar
               .getPigeonApiGeolocationPermissionsCallback()
               .pigeon_newInstance(var2 as Callback, new AndroidWebkitLibraryPigeonProxyApiBaseCodec$$ExternalSyntheticLambda7());
         } else if (var2 is HttpAuthHandler) {
            this.registrar
               .getPigeonApiHttpAuthHandler()
               .pigeon_newInstance(var2 as HttpAuthHandler, new AndroidWebkitLibraryPigeonProxyApiBaseCodec$$ExternalSyntheticLambda8());
         }

         if (this.registrar.getInstanceManager().containsInstance(var2)) {
            var1.write(128);
            this.writeValue(var1, this.registrar.getInstanceManager().getIdentifierForStrongReference(var2));
         } else {
            val var4: java.lang.String = var2.getClass().getName();
            val var3: StringBuilder = new StringBuilder("Unsupported value: '");
            var3.append(var2);
            var3.append("' of type '");
            var3.append(var4);
            var3.append("'");
            throw new IllegalArgumentException(var3.toString());
         }
      } else {
         super.writeValue(var1, var2);
      }
   }
}
