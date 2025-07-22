package io.flutter.plugins.webviewflutter;

public class WebViewPointProxyApi extends PigeonApiWebViewPoint {
   public WebViewPointProxyApi(ProxyApiRegistrar var1) {
      super(var1);
   }

   @Override
   public long x(WebViewPoint var1) {
      return var1.getX();
   }

   @Override
   public long y(WebViewPoint var1) {
      return var1.getY();
   }
}
