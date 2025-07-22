package io.flutter.plugins.webviewflutter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FlutterAssetManagerProxyApi extends PigeonApiFlutterAssetManager {
   public FlutterAssetManagerProxyApi(ProxyApiRegistrar var1) {
      super(var1);
   }

   @Override
   public String getAssetFilePathByName(FlutterAssetManager var1, String var2) {
      return var1.getAssetFilePathByName(var2);
   }

   public ProxyApiRegistrar getPigeonRegistrar() {
      return (ProxyApiRegistrar)super.getPigeonRegistrar();
   }

   @Override
   public FlutterAssetManager instance() {
      return this.getPigeonRegistrar().getFlutterAssetManager();
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   @Override
   public List<String> list(FlutterAssetManager var1, String var2) {
      try {
         var6 = var1.list(var2);
      } catch (IOException var5) {
         throw new RuntimeException(var5.getMessage());
      }

      if (var6 == null) {
         try {
            return new ArrayList<>();
         } catch (IOException var3) {
            throw new RuntimeException(var3.getMessage());
         }
      } else {
         try {
            return Arrays.asList(var6);
         } catch (IOException var4) {
            throw new RuntimeException(var4.getMessage());
         }
      }
   }
}
