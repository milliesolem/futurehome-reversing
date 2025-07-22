package io.flutter.plugins.webviewflutter;

import android.net.Uri;
import android.os.Message;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.GeolocationPermissions.Callback;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.webkit.WebChromeClient.FileChooserParams;
import android.webkit.WebView.WebViewTransport;
import dev.fluttercommunity.plus.share.Share$$ExternalSyntheticApiModelOutline0;

public class WebChromeClientProxyApi extends PigeonApiWebChromeClient {
   public WebChromeClientProxyApi(ProxyApiRegistrar var1) {
      super(var1);
   }

   public ProxyApiRegistrar getPigeonRegistrar() {
      return (ProxyApiRegistrar)super.getPigeonRegistrar();
   }

   @Override
   public WebChromeClientProxyApi.WebChromeClientImpl pigeon_defaultConstructor() {
      return new WebChromeClientProxyApi.WebChromeClientImpl(this);
   }

   @Override
   public void setSynchronousReturnValueForOnConsoleMessage(WebChromeClientProxyApi.WebChromeClientImpl var1, boolean var2) {
      var1.setReturnValueForOnConsoleMessage(var2);
   }

   @Override
   public void setSynchronousReturnValueForOnJsAlert(WebChromeClientProxyApi.WebChromeClientImpl var1, boolean var2) {
      var1.setReturnValueForOnJsAlert(var2);
   }

   @Override
   public void setSynchronousReturnValueForOnJsConfirm(WebChromeClientProxyApi.WebChromeClientImpl var1, boolean var2) {
      var1.setReturnValueForOnJsConfirm(var2);
   }

   @Override
   public void setSynchronousReturnValueForOnJsPrompt(WebChromeClientProxyApi.WebChromeClientImpl var1, boolean var2) {
      var1.setReturnValueForOnJsPrompt(var2);
   }

   @Override
   public void setSynchronousReturnValueForOnShowFileChooser(WebChromeClientProxyApi.WebChromeClientImpl var1, boolean var2) {
      var1.setReturnValueForOnShowFileChooser(var2);
   }

   public static class SecureWebChromeClient extends WebChromeClient {
      WebViewClient webViewClient;

      boolean onCreateWindow(WebView var1, Message var2, WebView var3) {
         if (this.webViewClient == null) {
            return false;
         } else {
            WebViewClient var5 = new WebViewClient(this, var1) {
               final WebChromeClientProxyApi.SecureWebChromeClient this$0;
               final WebView val$view;

               {
                  this.this$0 = var1;
                  this.val$view = var2x;
               }

               public boolean shouldOverrideUrlLoading(WebView var1, WebResourceRequest var2x) {
                  if (!Share$$ExternalSyntheticApiModelOutline0.m(this.this$0.webViewClient, this.val$view, var2x)) {
                     this.val$view.loadUrl(var2x.getUrl().toString());
                  }

                  return true;
               }

               public boolean shouldOverrideUrlLoading(WebView var1, String var2x) {
                  if (!this.this$0.webViewClient.shouldOverrideUrlLoading(this.val$view, var2x)) {
                     this.val$view.loadUrl(var2x);
                  }

                  return true;
               }
            };
            WebView var4 = var3;
            if (var3 == null) {
               var4 = new WebView(var1.getContext());
            }

            var4.setWebViewClient(var5);
            ((WebViewTransport)var2.obj).setWebView(var4);
            var2.sendToTarget();
            return true;
         }
      }

      public boolean onCreateWindow(WebView var1, boolean var2, boolean var3, Message var4) {
         return this.onCreateWindow(var1, var4, new WebView(var1.getContext()));
      }

      public void setWebViewClient(WebViewClient var1) {
         this.webViewClient = var1;
      }
   }

   public static class WebChromeClientImpl extends WebChromeClientProxyApi.SecureWebChromeClient {
      private static final String TAG = "WebChromeClientImpl";
      private final WebChromeClientProxyApi api;
      private boolean returnValueForOnConsoleMessage;
      private boolean returnValueForOnJsAlert;
      private boolean returnValueForOnJsConfirm;
      private boolean returnValueForOnJsPrompt;
      private boolean returnValueForOnShowFileChooser = false;

      public WebChromeClientImpl(WebChromeClientProxyApi var1) {
         this.returnValueForOnConsoleMessage = false;
         this.returnValueForOnJsAlert = false;
         this.returnValueForOnJsConfirm = false;
         this.returnValueForOnJsPrompt = false;
         this.api = var1;
      }

      public boolean onConsoleMessage(ConsoleMessage var1) {
         this.api.onConsoleMessage(this, var1, new WebChromeClientProxyApi$WebChromeClientImpl$$ExternalSyntheticLambda3());
         return this.returnValueForOnConsoleMessage;
      }

      public void onGeolocationPermissionsHidePrompt() {
         this.api.onGeolocationPermissionsHidePrompt(this, new WebChromeClientProxyApi$WebChromeClientImpl$$ExternalSyntheticLambda1());
      }

      public void onGeolocationPermissionsShowPrompt(String var1, Callback var2) {
         this.api.onGeolocationPermissionsShowPrompt(this, var1, var2, new WebChromeClientProxyApi$WebChromeClientImpl$$ExternalSyntheticLambda5());
      }

      public void onHideCustomView() {
         this.api.onHideCustomView(this, new WebChromeClientProxyApi$WebChromeClientImpl$$ExternalSyntheticLambda10());
      }

      public boolean onJsAlert(WebView var1, String var2, String var3, JsResult var4) {
         if (this.returnValueForOnJsAlert) {
            this.api
               .onJsAlert(
                  this, var1, var2, var3, ResultCompat.asCompatCallback(new WebChromeClientProxyApi$WebChromeClientImpl$$ExternalSyntheticLambda6(this, var4))
               );
            return true;
         } else {
            return false;
         }
      }

      public boolean onJsConfirm(WebView var1, String var2, String var3, JsResult var4) {
         if (this.returnValueForOnJsConfirm) {
            this.api
               .onJsConfirm(
                  this, var1, var2, var3, ResultCompat.asCompatCallback(new WebChromeClientProxyApi$WebChromeClientImpl$$ExternalSyntheticLambda0(this, var4))
               );
            return true;
         } else {
            return false;
         }
      }

      public boolean onJsPrompt(WebView var1, String var2, String var3, String var4, JsPromptResult var5) {
         if (this.returnValueForOnJsPrompt) {
            this.api
               .onJsPrompt(
                  this,
                  var1,
                  var2,
                  var3,
                  var4,
                  ResultCompat.asCompatCallback(new WebChromeClientProxyApi$WebChromeClientImpl$$ExternalSyntheticLambda2(this, var5))
               );
            return true;
         } else {
            return false;
         }
      }

      public void onPermissionRequest(PermissionRequest var1) {
         this.api.onPermissionRequest(this, var1, new WebChromeClientProxyApi$WebChromeClientImpl$$ExternalSyntheticLambda8());
      }

      public void onProgressChanged(WebView var1, int var2) {
         this.api.onProgressChanged(this, var1, var2, new WebChromeClientProxyApi$WebChromeClientImpl$$ExternalSyntheticLambda7());
      }

      public void onShowCustomView(View var1, CustomViewCallback var2) {
         this.api.onShowCustomView(this, var1, var2, new WebChromeClientProxyApi$WebChromeClientImpl$$ExternalSyntheticLambda4());
      }

      public boolean onShowFileChooser(WebView var1, ValueCallback<Uri[]> var2, FileChooserParams var3) {
         boolean var4 = this.returnValueForOnShowFileChooser;
         this.api
            .onShowFileChooser(
               this, var1, var3, ResultCompat.asCompatCallback(new WebChromeClientProxyApi$WebChromeClientImpl$$ExternalSyntheticLambda9(this, var4, var2))
            );
         return var4;
      }

      public void setReturnValueForOnConsoleMessage(boolean var1) {
         this.returnValueForOnConsoleMessage = var1;
      }

      public void setReturnValueForOnJsAlert(boolean var1) {
         this.returnValueForOnJsAlert = var1;
      }

      public void setReturnValueForOnJsConfirm(boolean var1) {
         this.returnValueForOnJsConfirm = var1;
      }

      public void setReturnValueForOnJsPrompt(boolean var1) {
         this.returnValueForOnJsPrompt = var1;
      }

      public void setReturnValueForOnShowFileChooser(boolean var1) {
         this.returnValueForOnShowFileChooser = var1;
      }
   }
}
