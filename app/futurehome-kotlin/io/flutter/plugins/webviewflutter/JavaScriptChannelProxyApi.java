package io.flutter.plugins.webviewflutter;

public class JavaScriptChannelProxyApi extends PigeonApiJavaScriptChannel {
   public JavaScriptChannelProxyApi(ProxyApiRegistrar var1) {
      super(var1);
   }

   public ProxyApiRegistrar getPigeonRegistrar() {
      return (ProxyApiRegistrar)super.getPigeonRegistrar();
   }

   @Override
   public JavaScriptChannel pigeon_defaultConstructor(String var1) {
      return new JavaScriptChannel(var1, this);
   }
}
