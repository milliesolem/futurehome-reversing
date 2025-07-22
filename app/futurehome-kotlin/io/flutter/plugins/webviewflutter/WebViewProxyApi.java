package io.flutter.plugins.webviewflutter;

import android.hardware.display.DisplayManager;
import android.view.View;
import android.view.ViewParent;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import dev.fluttercommunity.plus.share.Share$$ExternalSyntheticApiModelOutline0;
import io.flutter.embedding.android.FlutterView;
import io.flutter.plugin.platform.PlatformView;
import java.util.Map;
import kotlin.Result;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class WebViewProxyApi extends PigeonApiWebView {
   public WebViewProxyApi(ProxyApiRegistrar var1) {
      super(var1);
   }

   @Override
   public void addJavaScriptChannel(WebView var1, JavaScriptChannel var2) {
      var1.addJavascriptInterface(var2, var2.javaScriptChannelName);
   }

   @Override
   public boolean canGoBack(WebView var1) {
      return var1.canGoBack();
   }

   @Override
   public boolean canGoForward(WebView var1) {
      return var1.canGoForward();
   }

   @Override
   public void clearCache(WebView var1, boolean var2) {
      var1.clearCache(var2);
   }

   @Override
   public void destroy(WebView var1) {
      var1.destroy();
   }

   @Override
   public void evaluateJavascript(WebView var1, String var2, Function1<? super Result<String>, Unit> var3) {
      var1.evaluateJavascript(var2, new WebViewProxyApi$$ExternalSyntheticLambda0(var3));
   }

   public ProxyApiRegistrar getPigeonRegistrar() {
      return (ProxyApiRegistrar)super.getPigeonRegistrar();
   }

   @Override
   public String getTitle(WebView var1) {
      return var1.getTitle();
   }

   @Override
   public String getUrl(WebView var1) {
      return var1.getUrl();
   }

   @Override
   public void goBack(WebView var1) {
      var1.goBack();
   }

   @Override
   public void goForward(WebView var1) {
      var1.goForward();
   }

   @Override
   public void loadData(WebView var1, String var2, String var3, String var4) {
      var1.loadData(var2, var3, var4);
   }

   @Override
   public void loadDataWithBaseUrl(WebView var1, String var2, String var3, String var4, String var5, String var6) {
      var1.loadDataWithBaseURL(var2, var3, var4, var5, var6);
   }

   @Override
   public void loadUrl(WebView var1, String var2, Map<String, String> var3) {
      var1.loadUrl(var2, var3);
   }

   @Override
   public WebView pigeon_defaultConstructor() {
      DisplayListenerProxy var3 = new DisplayListenerProxy();
      DisplayManager var2 = (DisplayManager)this.getPigeonRegistrar().getContext().getSystemService("display");
      var3.onPreWebViewInitialization(var2);
      WebViewProxyApi.WebViewPlatformView var1 = new WebViewProxyApi.WebViewPlatformView(this);
      var3.onPostWebViewInitialization(var2);
      return var1;
   }

   @Override
   public void postUrl(WebView var1, String var2, byte[] var3) {
      var1.postUrl(var2, var3);
   }

   @Override
   public void reload(WebView var1) {
      var1.reload();
   }

   @Override
   public void removeJavaScriptChannel(WebView var1, String var2) {
      var1.removeJavascriptInterface(var2);
   }

   @Override
   public void setBackgroundColor(WebView var1, long var2) {
      var1.setBackgroundColor((int)var2);
   }

   @Override
   public void setDownloadListener(WebView var1, DownloadListener var2) {
      var1.setDownloadListener(var2);
   }

   @Override
   public void setWebChromeClient(WebView var1, WebChromeClientProxyApi.WebChromeClientImpl var2) {
      var1.setWebChromeClient(var2);
   }

   @Override
   public void setWebContentsDebuggingEnabled(boolean var1) {
      WebView.setWebContentsDebuggingEnabled(var1);
   }

   @Override
   public void setWebViewClient(WebView var1, WebViewClient var2) {
      var1.setWebViewClient(var2);
   }

   @Override
   public WebSettings settings(WebView var1) {
      return var1.getSettings();
   }

   public static class WebViewPlatformView extends WebView implements PlatformView {
      private final WebViewProxyApi api;
      private WebChromeClientProxyApi.SecureWebChromeClient currentWebChromeClient;
      private WebViewClient currentWebViewClient;

      WebViewPlatformView(WebViewProxyApi var1) {
         super(var1.getPigeonRegistrar().getContext());
         this.api = var1;
         this.currentWebViewClient = new WebViewClient();
         this.currentWebChromeClient = new WebChromeClientProxyApi.SecureWebChromeClient();
         this.setWebViewClient(this.currentWebViewClient);
         this.setWebChromeClient(this.currentWebChromeClient);
      }

      private FlutterView tryFindFlutterView() {
         Object var1 = this;

         while (var1.getParent() != null) {
            ViewParent var2 = var1.getParent();
            var1 = var2;
            if (var2 instanceof FlutterView) {
               return (FlutterView)var2;
            }
         }

         return null;
      }

      @Override
      public void dispose() {
      }

      @Override
      public View getView() {
         return this;
      }

      public WebChromeClient getWebChromeClient() {
         return this.currentWebChromeClient;
      }

      protected void onAttachedToWindow() {
         super.onAttachedToWindow();
         if (this.api.getPigeonRegistrar().sdkIsAtLeast(26)) {
            FlutterView var1 = this.tryFindFlutterView();
            if (var1 != null) {
               Share$$ExternalSyntheticApiModelOutline0.m(var1, 1);
            }
         }
      }

      protected void onScrollChanged(int var1, int var2, int var3, int var4) {
         super.onScrollChanged(var1, var2, var3, var4);
         this.api.getPigeonRegistrar().runOnMainThread(new WebViewProxyApi$WebViewPlatformView$$ExternalSyntheticLambda1(this, var1, var2, var3, var4));
      }

      public void setWebChromeClient(WebChromeClient var1) {
         super.setWebChromeClient(var1);
         if (var1 instanceof WebChromeClientProxyApi.SecureWebChromeClient) {
            WebChromeClientProxyApi.SecureWebChromeClient var2 = (WebChromeClientProxyApi.SecureWebChromeClient)var1;
            this.currentWebChromeClient = var2;
            var2.setWebViewClient(this.currentWebViewClient);
         } else {
            throw new AssertionError("Client must be a SecureWebChromeClient.");
         }
      }

      public void setWebViewClient(WebViewClient var1) {
         super.setWebViewClient(var1);
         this.currentWebViewClient = var1;
         this.currentWebChromeClient.setWebViewClient(var1);
      }
   }
}
