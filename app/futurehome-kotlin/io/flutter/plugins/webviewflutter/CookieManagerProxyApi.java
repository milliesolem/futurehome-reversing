package io.flutter.plugins.webviewflutter;

import android.webkit.CookieManager;
import android.webkit.WebView;
import kotlin.Result;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class CookieManagerProxyApi extends PigeonApiCookieManager {
   public CookieManagerProxyApi(ProxyApiRegistrar var1) {
      super(var1);
   }

   public ProxyApiRegistrar getPigeonRegistrar() {
      return (ProxyApiRegistrar)super.getPigeonRegistrar();
   }

   @Override
   public CookieManager instance() {
      return CookieManager.getInstance();
   }

   @Override
   public void removeAllCookies(CookieManager var1, Function1<? super Result<Boolean>, Unit> var2) {
      var1.removeAllCookies(new CookieManagerProxyApi$$ExternalSyntheticLambda0(var2));
   }

   @Override
   public void setAcceptThirdPartyCookies(CookieManager var1, WebView var2, boolean var3) {
      var1.setAcceptThirdPartyCookies(var2, var3);
   }

   @Override
   public void setCookie(CookieManager var1, String var2, String var3) {
      var1.setCookie(var2, var3);
   }
}
