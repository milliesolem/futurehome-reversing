package io.flutter.plugins.webviewflutter;

import android.webkit.WebChromeClient.CustomViewCallback;

public class CustomViewCallbackProxyApi extends PigeonApiCustomViewCallback {
   public CustomViewCallbackProxyApi(ProxyApiRegistrar var1) {
      super(var1);
   }

   @Override
   public void onCustomViewHidden(CustomViewCallback var1) {
      var1.onCustomViewHidden();
   }
}
