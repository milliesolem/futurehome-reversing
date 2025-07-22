package io.flutter.plugins.webviewflutter

import android.util.Log
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MessageCodec

public abstract class AndroidWebkitLibraryPigeonProxyApiRegistrar {
   public final val binaryMessenger: BinaryMessenger

   public final var ignoreCallsToDart: Boolean
      internal set

   public final val instanceManager: AndroidWebkitLibraryPigeonInstanceManager
   private final var _codec: MessageCodec<Any?>?

   public final val codec: MessageCodec<Any?>
      public final get() {
         if (this._codec == null) {
            this._codec = new AndroidWebkitLibraryPigeonProxyApiBaseCodec(this);
         }

         val var1: MessageCodec = this._codec;
         return var1;
      }


   open fun AndroidWebkitLibraryPigeonProxyApiRegistrar(var1: BinaryMessenger) {
      this.binaryMessenger = var1;
      this.instanceManager = AndroidWebkitLibraryPigeonInstanceManager.Companion
         .create(new AndroidWebkitLibraryPigeonInstanceManager.PigeonFinalizationListener(new AndroidWebkitLibraryPigeonInstanceManagerApi(var1)) {
            final AndroidWebkitLibraryPigeonInstanceManagerApi $api;

            {
               this.$api = var1;
            }

            private static final Unit onFinalize$lambda$0(long var0, Result var2) {
               if (Result.isFailure-impl(var2.unbox-impl())) {
                  val var3: StringBuilder = new StringBuilder("Failed to remove Dart strong reference with identifier: ");
                  var3.append(var0);
                  Log.e("PigeonProxyApiRegistrar", var3.toString());
               }

               return Unit.INSTANCE;
            }

            @Override
            public void onFinalize(long var1) {
               this.$api.removeStrongReference(var1, new AndroidWebkitLibraryPigeonProxyApiRegistrar$1$$ExternalSyntheticLambda0(var1));
            }
         });
   }

   public abstract fun getPigeonApiConsoleMessage(): PigeonApiConsoleMessage {
   }

   public abstract fun getPigeonApiCookieManager(): PigeonApiCookieManager {
   }

   public abstract fun getPigeonApiCustomViewCallback(): PigeonApiCustomViewCallback {
   }

   public abstract fun getPigeonApiDownloadListener(): PigeonApiDownloadListener {
   }

   public abstract fun getPigeonApiFileChooserParams(): PigeonApiFileChooserParams {
   }

   public abstract fun getPigeonApiFlutterAssetManager(): PigeonApiFlutterAssetManager {
   }

   public abstract fun getPigeonApiGeolocationPermissionsCallback(): PigeonApiGeolocationPermissionsCallback {
   }

   public abstract fun getPigeonApiHttpAuthHandler(): PigeonApiHttpAuthHandler {
   }

   public abstract fun getPigeonApiJavaScriptChannel(): PigeonApiJavaScriptChannel {
   }

   public abstract fun getPigeonApiPermissionRequest(): PigeonApiPermissionRequest {
   }

   public abstract fun getPigeonApiView(): PigeonApiView {
   }

   public abstract fun getPigeonApiWebChromeClient(): PigeonApiWebChromeClient {
   }

   public abstract fun getPigeonApiWebResourceError(): PigeonApiWebResourceError {
   }

   public abstract fun getPigeonApiWebResourceErrorCompat(): PigeonApiWebResourceErrorCompat {
   }

   public abstract fun getPigeonApiWebResourceRequest(): PigeonApiWebResourceRequest {
   }

   public abstract fun getPigeonApiWebResourceResponse(): PigeonApiWebResourceResponse {
   }

   public abstract fun getPigeonApiWebSettings(): PigeonApiWebSettings {
   }

   public abstract fun getPigeonApiWebStorage(): PigeonApiWebStorage {
   }

   public abstract fun getPigeonApiWebView(): PigeonApiWebView {
   }

   public abstract fun getPigeonApiWebViewClient(): PigeonApiWebViewClient {
   }

   public abstract fun getPigeonApiWebViewPoint(): PigeonApiWebViewPoint {
   }

   public fun setUp() {
      AndroidWebkitLibraryPigeonInstanceManagerApi.Companion.setUpMessageHandlers(this.binaryMessenger, this.instanceManager);
      PigeonApiCookieManager.Companion.setUpMessageHandlers(this.binaryMessenger, this.getPigeonApiCookieManager());
      PigeonApiWebView.Companion.setUpMessageHandlers(this.binaryMessenger, this.getPigeonApiWebView());
      PigeonApiWebSettings.Companion.setUpMessageHandlers(this.binaryMessenger, this.getPigeonApiWebSettings());
      PigeonApiJavaScriptChannel.Companion.setUpMessageHandlers(this.binaryMessenger, this.getPigeonApiJavaScriptChannel());
      PigeonApiWebViewClient.Companion.setUpMessageHandlers(this.binaryMessenger, this.getPigeonApiWebViewClient());
      PigeonApiDownloadListener.Companion.setUpMessageHandlers(this.binaryMessenger, this.getPigeonApiDownloadListener());
      PigeonApiWebChromeClient.Companion.setUpMessageHandlers(this.binaryMessenger, this.getPigeonApiWebChromeClient());
      PigeonApiFlutterAssetManager.Companion.setUpMessageHandlers(this.binaryMessenger, this.getPigeonApiFlutterAssetManager());
      PigeonApiWebStorage.Companion.setUpMessageHandlers(this.binaryMessenger, this.getPigeonApiWebStorage());
      PigeonApiPermissionRequest.Companion.setUpMessageHandlers(this.binaryMessenger, this.getPigeonApiPermissionRequest());
      PigeonApiCustomViewCallback.Companion.setUpMessageHandlers(this.binaryMessenger, this.getPigeonApiCustomViewCallback());
      PigeonApiView.Companion.setUpMessageHandlers(this.binaryMessenger, this.getPigeonApiView());
      PigeonApiGeolocationPermissionsCallback.Companion.setUpMessageHandlers(this.binaryMessenger, this.getPigeonApiGeolocationPermissionsCallback());
      PigeonApiHttpAuthHandler.Companion.setUpMessageHandlers(this.binaryMessenger, this.getPigeonApiHttpAuthHandler());
   }

   public fun tearDown() {
      AndroidWebkitLibraryPigeonInstanceManagerApi.Companion.setUpMessageHandlers(this.binaryMessenger, null);
      PigeonApiCookieManager.Companion.setUpMessageHandlers(this.binaryMessenger, null);
      PigeonApiWebView.Companion.setUpMessageHandlers(this.binaryMessenger, null);
      PigeonApiWebSettings.Companion.setUpMessageHandlers(this.binaryMessenger, null);
      PigeonApiJavaScriptChannel.Companion.setUpMessageHandlers(this.binaryMessenger, null);
      PigeonApiWebViewClient.Companion.setUpMessageHandlers(this.binaryMessenger, null);
      PigeonApiDownloadListener.Companion.setUpMessageHandlers(this.binaryMessenger, null);
      PigeonApiWebChromeClient.Companion.setUpMessageHandlers(this.binaryMessenger, null);
      PigeonApiFlutterAssetManager.Companion.setUpMessageHandlers(this.binaryMessenger, null);
      PigeonApiWebStorage.Companion.setUpMessageHandlers(this.binaryMessenger, null);
      PigeonApiPermissionRequest.Companion.setUpMessageHandlers(this.binaryMessenger, null);
      PigeonApiCustomViewCallback.Companion.setUpMessageHandlers(this.binaryMessenger, null);
      PigeonApiView.Companion.setUpMessageHandlers(this.binaryMessenger, null);
      PigeonApiGeolocationPermissionsCallback.Companion.setUpMessageHandlers(this.binaryMessenger, null);
      PigeonApiHttpAuthHandler.Companion.setUpMessageHandlers(this.binaryMessenger, null);
   }
}
