package io.flutter.plugins.webviewflutter;

import androidx.webkit.WebResourceErrorCompat;

public class WebResourceErrorCompatProxyApi extends PigeonApiWebResourceErrorCompat {
   public WebResourceErrorCompatProxyApi(ProxyApiRegistrar var1) {
      super(var1);
   }

   @Override
   public String description(WebResourceErrorCompat var1) {
      return var1.getDescription().toString();
   }

   @Override
   public long errorCode(WebResourceErrorCompat var1) {
      return var1.getErrorCode();
   }
}
