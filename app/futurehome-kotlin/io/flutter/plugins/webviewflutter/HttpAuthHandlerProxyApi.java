package io.flutter.plugins.webviewflutter;

import android.webkit.HttpAuthHandler;

public class HttpAuthHandlerProxyApi extends PigeonApiHttpAuthHandler {
   public HttpAuthHandlerProxyApi(ProxyApiRegistrar var1) {
      super(var1);
   }

   @Override
   public void cancel(HttpAuthHandler var1) {
      var1.cancel();
   }

   @Override
   public void proceed(HttpAuthHandler var1, String var2, String var3) {
      var1.proceed(var2, var3);
   }

   @Override
   public boolean useHttpAuthUsernamePassword(HttpAuthHandler var1) {
      return var1.useHttpAuthUsernamePassword();
   }
}
