package io.flutter.plugins.urllauncher;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebView.WebViewTransport;
import androidx.core.content.ContextCompat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class WebViewActivity extends Activity {
   public static final String ACTION_CLOSE = "close action";
   static final String ENABLE_DOM_EXTRA = "enableDomStorage";
   static final String ENABLE_JS_EXTRA = "enableJavaScript";
   static final String URL_EXTRA = "url";
   private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver(this) {
      final WebViewActivity this$0;

      {
         this.this$0 = var1;
      }

      public void onReceive(Context var1, Intent var2) {
         if ("close action".equals(var2.getAction())) {
            this.this$0.finish();
         }
      }
   };
   private final IntentFilter closeIntentFilter;
   private final WebViewClient webViewClient = new WebViewClient(this) {
      final WebViewActivity this$0;

      {
         this.this$0 = var1;
      }

      public boolean shouldOverrideUrlLoading(WebView var1, WebResourceRequest var2) {
         var1.loadUrl(var2.getUrl().toString());
         return false;
      }

      public boolean shouldOverrideUrlLoading(WebView var1, String var2) {
         return super.shouldOverrideUrlLoading(var1, var2);
      }
   };
   WebView webview;

   public WebViewActivity() {
      this.closeIntentFilter = new IntentFilter("close action");
   }

   public static Intent createIntent(Context var0, String var1, boolean var2, boolean var3, Bundle var4) {
      return new Intent(var0, WebViewActivity.class)
         .putExtra("url", var1)
         .putExtra("enableJavaScript", var2)
         .putExtra("enableDomStorage", var3)
         .putExtra("com.android.browser.headers", var4);
   }

   public static Map<String, String> extractHeaders(Bundle var0) {
      if (var0 == null) {
         return Collections.emptyMap();
      } else {
         HashMap var2 = new HashMap();

         for (String var3 : var0.keySet()) {
            var2.put(var3, var0.getString(var3));
         }

         return var2;
      }
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      WebView var5 = new WebView(this);
      this.webview = var5;
      this.setContentView(var5);
      Intent var4 = this.getIntent();
      String var6 = var4.getStringExtra("url");
      boolean var2 = var4.getBooleanExtra("enableJavaScript", false);
      boolean var3 = var4.getBooleanExtra("enableDomStorage", false);
      Map var7 = extractHeaders(var4.getBundleExtra("com.android.browser.headers"));
      this.webview.loadUrl(var6, var7);
      this.webview.getSettings().setJavaScriptEnabled(var2);
      this.webview.getSettings().setDomStorageEnabled(var3);
      this.webview.setWebViewClient(this.webViewClient);
      this.webview.getSettings().setSupportMultipleWindows(true);
      this.webview.setWebChromeClient(new WebViewActivity.FlutterWebChromeClient(this));
      ContextCompat.registerReceiver(this, this.broadcastReceiver, this.closeIntentFilter, 2);
   }

   protected void onDestroy() {
      super.onDestroy();
      this.unregisterReceiver(this.broadcastReceiver);
   }

   public boolean onKeyDown(int var1, KeyEvent var2) {
      if (var1 == 4 && this.webview.canGoBack()) {
         this.webview.goBack();
         return true;
      } else {
         return super.onKeyDown(var1, var2);
      }
   }

   class FlutterWebChromeClient extends WebChromeClient {
      final WebViewActivity this$0;

      FlutterWebChromeClient(WebViewActivity var1) {
         this.this$0 = var1;
      }

      public boolean onCreateWindow(WebView var1, boolean var2, boolean var3, Message var4) {
         WebViewClient var6 = new WebViewClient(this) {
            final WebViewActivity.FlutterWebChromeClient this$1;

            {
               this.this$1 = var1;
            }

            public boolean shouldOverrideUrlLoading(WebView var1, WebResourceRequest var2x) {
               this.this$1.this$0.webview.loadUrl(var2x.getUrl().toString());
               return true;
            }

            public boolean shouldOverrideUrlLoading(WebView var1, String var2x) {
               this.this$1.this$0.webview.loadUrl(var2x);
               return true;
            }
         };
         WebView var5 = new WebView(this.this$0.webview.getContext());
         var5.setWebViewClient(var6);
         ((WebViewTransport)var4.obj).setWebView(var5);
         var4.sendToTarget();
         return true;
      }
   }
}
