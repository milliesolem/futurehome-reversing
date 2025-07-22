package io.flutter.plugins.webviewflutter;

import android.webkit.PermissionRequest;
import java.util.Arrays;
import java.util.List;

public class PermissionRequestProxyApi extends PigeonApiPermissionRequest {
   public PermissionRequestProxyApi(ProxyApiRegistrar var1) {
      super(var1);
   }

   @Override
   public void deny(PermissionRequest var1) {
      var1.deny();
   }

   @Override
   public void grant(PermissionRequest var1, List<String> var2) {
      var1.grant(var2.toArray(new String[0]));
   }

   @Override
   public List<String> resources(PermissionRequest var1) {
      return Arrays.asList(var1.getResources());
   }
}
