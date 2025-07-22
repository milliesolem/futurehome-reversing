package io.flutter.plugins.webviewflutter;

import android.webkit.WebStorage;

public class WebStorageProxyApi extends PigeonApiWebStorage {
   public WebStorageProxyApi(ProxyApiRegistrar var1) {
      super(var1);
   }

   @Override
   public void deleteAllData(WebStorage var1) {
      var1.deleteAllData();
   }

   @Override
   public WebStorage instance() {
      return WebStorage.getInstance();
   }
}
