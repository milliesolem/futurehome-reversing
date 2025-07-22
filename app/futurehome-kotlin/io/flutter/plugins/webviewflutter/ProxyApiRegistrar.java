package io.flutter.plugins.webviewflutter;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Build.VERSION;
import android.util.Log;
import io.flutter.plugin.common.BinaryMessenger;

public class ProxyApiRegistrar extends AndroidWebkitLibraryPigeonProxyApiRegistrar {
   private Context context;
   private final FlutterAssetManager flutterAssetManager;

   public ProxyApiRegistrar(BinaryMessenger var1, Context var2, FlutterAssetManager var3) {
      super(var1);
      this.context = var2;
      this.flutterAssetManager = var3;
   }

   public Context getContext() {
      return this.context;
   }

   public FlutterAssetManager getFlutterAssetManager() {
      return this.flutterAssetManager;
   }

   @Override
   public PigeonApiConsoleMessage getPigeonApiConsoleMessage() {
      return new ConsoleMessageProxyApi(this);
   }

   @Override
   public PigeonApiCookieManager getPigeonApiCookieManager() {
      return new CookieManagerProxyApi(this);
   }

   @Override
   public PigeonApiCustomViewCallback getPigeonApiCustomViewCallback() {
      return new CustomViewCallbackProxyApi(this);
   }

   @Override
   public PigeonApiDownloadListener getPigeonApiDownloadListener() {
      return new DownloadListenerProxyApi(this);
   }

   @Override
   public PigeonApiFileChooserParams getPigeonApiFileChooserParams() {
      return new FileChooserParamsProxyApi(this);
   }

   @Override
   public PigeonApiFlutterAssetManager getPigeonApiFlutterAssetManager() {
      return new FlutterAssetManagerProxyApi(this);
   }

   @Override
   public PigeonApiGeolocationPermissionsCallback getPigeonApiGeolocationPermissionsCallback() {
      return new GeolocationPermissionsCallbackProxyApi(this);
   }

   @Override
   public PigeonApiHttpAuthHandler getPigeonApiHttpAuthHandler() {
      return new HttpAuthHandlerProxyApi(this);
   }

   @Override
   public PigeonApiJavaScriptChannel getPigeonApiJavaScriptChannel() {
      return new JavaScriptChannelProxyApi(this);
   }

   @Override
   public PigeonApiPermissionRequest getPigeonApiPermissionRequest() {
      return new PermissionRequestProxyApi(this);
   }

   @Override
   public PigeonApiView getPigeonApiView() {
      return new ViewProxyApi(this);
   }

   @Override
   public PigeonApiWebChromeClient getPigeonApiWebChromeClient() {
      return new WebChromeClientProxyApi(this);
   }

   @Override
   public PigeonApiWebResourceError getPigeonApiWebResourceError() {
      return new WebResourceErrorProxyApi(this);
   }

   @Override
   public PigeonApiWebResourceErrorCompat getPigeonApiWebResourceErrorCompat() {
      return new WebResourceErrorCompatProxyApi(this);
   }

   @Override
   public PigeonApiWebResourceRequest getPigeonApiWebResourceRequest() {
      return new WebResourceRequestProxyApi(this);
   }

   @Override
   public PigeonApiWebResourceResponse getPigeonApiWebResourceResponse() {
      return new WebResourceResponseProxyApi(this);
   }

   @Override
   public PigeonApiWebSettings getPigeonApiWebSettings() {
      return new WebSettingsProxyApi(this);
   }

   @Override
   public PigeonApiWebStorage getPigeonApiWebStorage() {
      return new WebStorageProxyApi(this);
   }

   @Override
   public PigeonApiWebView getPigeonApiWebView() {
      return new WebViewProxyApi(this);
   }

   @Override
   public PigeonApiWebViewClient getPigeonApiWebViewClient() {
      return new WebViewClientProxyApi(this);
   }

   @Override
   public PigeonApiWebViewPoint getPigeonApiWebViewPoint() {
      return new WebViewPointProxyApi(this);
   }

   void logError(String var1, Throwable var2) {
      StringBuilder var3 = new StringBuilder();
      var3.append(var2.getClass().getSimpleName());
      var3.append(", Message: ");
      var3.append(var2.getMessage());
      var3.append(", Stacktrace: ");
      var3.append(Log.getStackTraceString(var2));
      Log.e(var1, var3.toString());
   }

   void runOnMainThread(Runnable var1) {
      Context var2 = this.context;
      if (var2 instanceof Activity) {
         ((Activity)var2).runOnUiThread(var1);
      } else {
         new Handler(Looper.getMainLooper()).post(var1);
      }
   }

   boolean sdkIsAtLeast(int var1) {
      boolean var2;
      if (VERSION.SDK_INT >= var1) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public void setContext(Context var1) {
      this.context = var1;
   }
}
