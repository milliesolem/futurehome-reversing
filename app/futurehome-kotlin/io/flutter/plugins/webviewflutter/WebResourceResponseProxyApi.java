package io.flutter.plugins.webviewflutter;

import android.webkit.WebResourceResponse;

public class WebResourceResponseProxyApi extends PigeonApiWebResourceResponse {
   public WebResourceResponseProxyApi(ProxyApiRegistrar var1) {
      super(var1);
   }

   @Override
   public long statusCode(WebResourceResponse var1) {
      return var1.getStatusCode();
   }
}
