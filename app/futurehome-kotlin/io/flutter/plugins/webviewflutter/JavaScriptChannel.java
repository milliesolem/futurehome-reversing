package io.flutter.plugins.webviewflutter;

import android.webkit.JavascriptInterface;

public class JavaScriptChannel {
   private final JavaScriptChannelProxyApi api;
   final String javaScriptChannelName;

   public JavaScriptChannel(String var1, JavaScriptChannelProxyApi var2) {
      this.javaScriptChannelName = var1;
      this.api = var2;
   }

   @JavascriptInterface
   public void postMessage(String var1) {
      this.api.getPigeonRegistrar().runOnMainThread(new JavaScriptChannel$$ExternalSyntheticLambda1(this, var1));
   }
}
