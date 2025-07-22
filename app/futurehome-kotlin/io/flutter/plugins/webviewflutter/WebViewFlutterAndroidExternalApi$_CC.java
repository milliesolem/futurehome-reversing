package io.flutter.plugins.webviewflutter;

import android.webkit.WebView;
import io.flutter.embedding.engine.FlutterEngine;

// $VF: synthetic class
public final class WebViewFlutterAndroidExternalApi$_CC {
   public static WebView getWebView(FlutterEngine var0, long var1) {
      WebViewFlutterPlugin var3 = (WebViewFlutterPlugin)var0.getPlugins().get(WebViewFlutterPlugin.class);
      if (var3 != null && var3.getInstanceManager() != null) {
         Object var4 = var3.getInstanceManager().getInstance(var1);
         if (var4 instanceof WebView) {
            return (WebView)var4;
         }
      }

      return null;
   }
}
