package io.sentry.config;

import j..util.concurrent.ConcurrentHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

final class CompositePropertiesProvider implements PropertiesProvider {
   private final List<PropertiesProvider> providers;

   public CompositePropertiesProvider(List<PropertiesProvider> var1) {
      this.providers = var1;
   }

   @Override
   public Map<String, String> getMap(String var1) {
      ConcurrentHashMap var2 = new ConcurrentHashMap();
      Iterator var3 = this.providers.iterator();

      while (var3.hasNext()) {
         var2.putAll(((PropertiesProvider)var3.next()).getMap(var1));
      }

      return var2;
   }

   @Override
   public String getProperty(String var1) {
      Iterator var2 = this.providers.iterator();

      while (var2.hasNext()) {
         String var3 = ((PropertiesProvider)var2.next()).getProperty(var1);
         if (var3 != null) {
            return var3;
         }
      }

      return null;
   }
}
