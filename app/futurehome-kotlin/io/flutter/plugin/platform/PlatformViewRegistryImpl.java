package io.flutter.plugin.platform;

import java.util.HashMap;
import java.util.Map;

class PlatformViewRegistryImpl implements PlatformViewRegistry {
   private final Map<String, PlatformViewFactory> viewFactories = new HashMap<>();

   PlatformViewFactory getFactory(String var1) {
      return this.viewFactories.get(var1);
   }

   @Override
   public boolean registerViewFactory(String var1, PlatformViewFactory var2) {
      if (this.viewFactories.containsKey(var1)) {
         return false;
      } else {
         this.viewFactories.put(var1, var2);
         return true;
      }
   }
}
