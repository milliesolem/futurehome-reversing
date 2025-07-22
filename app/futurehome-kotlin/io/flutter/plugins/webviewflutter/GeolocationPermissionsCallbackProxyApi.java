package io.flutter.plugins.webviewflutter;

import android.webkit.GeolocationPermissions.Callback;

public class GeolocationPermissionsCallbackProxyApi extends PigeonApiGeolocationPermissionsCallback {
   public GeolocationPermissionsCallbackProxyApi(ProxyApiRegistrar var1) {
      super(var1);
   }

   @Override
   public void invoke(Callback var1, String var2, boolean var3, boolean var4) {
      var1.invoke(var2, var3, var4);
   }
}
