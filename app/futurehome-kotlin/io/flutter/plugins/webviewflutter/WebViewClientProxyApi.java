package io.flutter.plugins.webviewflutter;

import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.webkit.HttpAuthHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.webkit.WebResourceErrorCompat;
import androidx.webkit.WebViewClientCompat;

public class WebViewClientProxyApi extends PigeonApiWebViewClient {
   public WebViewClientProxyApi(ProxyApiRegistrar var1) {
      super(var1);
   }

   public ProxyApiRegistrar getPigeonRegistrar() {
      return (ProxyApiRegistrar)super.getPigeonRegistrar();
   }

   @Override
   public WebViewClient pigeon_defaultConstructor() {
      return (WebViewClient)(this.getPigeonRegistrar().sdkIsAtLeast(24)
         ? new WebViewClientProxyApi.WebViewClientImpl(this)
         : new WebViewClientProxyApi.WebViewClientCompatImpl(this));
   }

   @Override
   public void setSynchronousReturnValueForShouldOverrideUrlLoading(WebViewClient var1, boolean var2) {
      if (var1 instanceof WebViewClientProxyApi.WebViewClientCompatImpl) {
         ((WebViewClientProxyApi.WebViewClientCompatImpl)var1).setReturnValueForShouldOverrideUrlLoading(var2);
      } else {
         if (!this.getPigeonRegistrar().sdkIsAtLeast(24) || !(var1 instanceof WebViewClientProxyApi.WebViewClientImpl)) {
            throw new IllegalStateException("This WebViewClient doesn't support setting the returnValueForShouldOverrideUrlLoading.");
         }

         ((WebViewClientProxyApi.WebViewClientImpl)var1).setReturnValueForShouldOverrideUrlLoading(var2);
      }
   }

   public static class WebViewClientCompatImpl extends WebViewClientCompat {
      private final WebViewClientProxyApi api;
      private boolean returnValueForShouldOverrideUrlLoading = false;

      public WebViewClientCompatImpl(WebViewClientProxyApi var1) {
         this.api = var1;
      }

      public void doUpdateVisitedHistory(WebView var1, String var2, boolean var3) {
         this.api.getPigeonRegistrar().runOnMainThread(new WebViewClientProxyApi$WebViewClientCompatImpl$$ExternalSyntheticLambda13(this, var1, var2, var3));
      }

      public void onPageFinished(WebView var1, String var2) {
         this.api.getPigeonRegistrar().runOnMainThread(new WebViewClientProxyApi$WebViewClientCompatImpl$$ExternalSyntheticLambda14(this, var1, var2));
      }

      public void onPageStarted(WebView var1, String var2, Bitmap var3) {
         this.api.getPigeonRegistrar().runOnMainThread(new WebViewClientProxyApi$WebViewClientCompatImpl$$ExternalSyntheticLambda16(this, var1, var2));
      }

      public void onReceivedError(WebView var1, int var2, String var3, String var4) {
         this.api
            .getPigeonRegistrar()
            .runOnMainThread(new WebViewClientProxyApi$WebViewClientCompatImpl$$ExternalSyntheticLambda0(this, var1, var2, var3, var4));
      }

      public void onReceivedError(WebView var1, WebResourceRequest var2, WebResourceErrorCompat var3) {
         this.api.getPigeonRegistrar().runOnMainThread(new WebViewClientProxyApi$WebViewClientCompatImpl$$ExternalSyntheticLambda2(this, var1, var2, var3));
      }

      public void onReceivedHttpAuthRequest(WebView var1, HttpAuthHandler var2, String var3, String var4) {
         this.api
            .getPigeonRegistrar()
            .runOnMainThread(new WebViewClientProxyApi$WebViewClientCompatImpl$$ExternalSyntheticLambda1(this, var1, var2, var3, var4));
      }

      public void onReceivedHttpError(WebView var1, WebResourceRequest var2, WebResourceResponse var3) {
         this.api.getPigeonRegistrar().runOnMainThread(new WebViewClientProxyApi$WebViewClientCompatImpl$$ExternalSyntheticLambda3(this, var1, var2, var3));
      }

      public void onUnhandledKeyEvent(WebView var1, KeyEvent var2) {
      }

      public void setReturnValueForShouldOverrideUrlLoading(boolean var1) {
         this.returnValueForShouldOverrideUrlLoading = var1;
      }

      public boolean shouldOverrideUrlLoading(WebView var1, WebResourceRequest var2) {
         this.api.getPigeonRegistrar().runOnMainThread(new WebViewClientProxyApi$WebViewClientCompatImpl$$ExternalSyntheticLambda11(this, var1, var2));
         boolean var3;
         if (var2.isForMainFrame() && this.returnValueForShouldOverrideUrlLoading) {
            var3 = true;
         } else {
            var3 = false;
         }

         return var3;
      }

      public boolean shouldOverrideUrlLoading(WebView var1, String var2) {
         this.api.getPigeonRegistrar().runOnMainThread(new WebViewClientProxyApi$WebViewClientCompatImpl$$ExternalSyntheticLambda7(this, var1, var2));
         return this.returnValueForShouldOverrideUrlLoading;
      }
   }

   public static class WebViewClientImpl extends WebViewClient {
      private final WebViewClientProxyApi api;
      private boolean returnValueForShouldOverrideUrlLoading = false;

      public WebViewClientImpl(WebViewClientProxyApi var1) {
         this.api = var1;
      }

      public void doUpdateVisitedHistory(WebView var1, String var2, boolean var3) {
         this.api.getPigeonRegistrar().runOnMainThread(new WebViewClientProxyApi$WebViewClientImpl$$ExternalSyntheticLambda2(this, var1, var2, var3));
      }

      public void onPageFinished(WebView var1, String var2) {
         this.api.getPigeonRegistrar().runOnMainThread(new WebViewClientProxyApi$WebViewClientImpl$$ExternalSyntheticLambda15(this, var1, var2));
      }

      public void onPageStarted(WebView var1, String var2, Bitmap var3) {
         this.api.getPigeonRegistrar().runOnMainThread(new WebViewClientProxyApi$WebViewClientImpl$$ExternalSyntheticLambda0(this, var1, var2));
      }

      public void onReceivedError(WebView var1, int var2, String var3, String var4) {
         this.api.getPigeonRegistrar().runOnMainThread(new WebViewClientProxyApi$WebViewClientImpl$$ExternalSyntheticLambda7(this, var1, var2, var3, var4));
      }

      public void onReceivedError(WebView var1, WebResourceRequest var2, WebResourceError var3) {
         this.api.getPigeonRegistrar().runOnMainThread(new WebViewClientProxyApi$WebViewClientImpl$$ExternalSyntheticLambda10(this, var1, var2, var3));
      }

      public void onReceivedHttpAuthRequest(WebView var1, HttpAuthHandler var2, String var3, String var4) {
         this.api.getPigeonRegistrar().runOnMainThread(new WebViewClientProxyApi$WebViewClientImpl$$ExternalSyntheticLambda12(this, var1, var2, var3, var4));
      }

      public void onReceivedHttpError(WebView var1, WebResourceRequest var2, WebResourceResponse var3) {
         this.api.getPigeonRegistrar().runOnMainThread(new WebViewClientProxyApi$WebViewClientImpl$$ExternalSyntheticLambda14(this, var1, var2, var3));
      }

      public void onUnhandledKeyEvent(WebView var1, KeyEvent var2) {
      }

      public void setReturnValueForShouldOverrideUrlLoading(boolean var1) {
         this.returnValueForShouldOverrideUrlLoading = var1;
      }

      public boolean shouldOverrideUrlLoading(WebView var1, WebResourceRequest var2) {
         this.api.getPigeonRegistrar().runOnMainThread(new WebViewClientProxyApi$WebViewClientImpl$$ExternalSyntheticLambda17(this, var1, var2));
         boolean var3;
         if (var2.isForMainFrame() && this.returnValueForShouldOverrideUrlLoading) {
            var3 = true;
         } else {
            var3 = false;
         }

         return var3;
      }

      public boolean shouldOverrideUrlLoading(WebView var1, String var2) {
         this.api.getPigeonRegistrar().runOnMainThread(new WebViewClientProxyApi$WebViewClientImpl$$ExternalSyntheticLambda5(this, var1, var2));
         return this.returnValueForShouldOverrideUrlLoading;
      }
   }
}
