package io.sentry.config;

import io.sentry.util.StringUtils;
import j..util.concurrent.ConcurrentHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

final class EnvironmentVariablePropertiesProvider implements PropertiesProvider {
   private static final String PREFIX = "SENTRY";

   private String propertyToEnvironmentVariableName(String var1) {
      StringBuilder var2 = new StringBuilder("SENTRY_");
      var2.append(var1.replace(".", "_").replace("-", "_").toUpperCase(Locale.ROOT));
      return var2.toString();
   }

   @Override
   public Map<String, String> getMap(String var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append(this.propertyToEnvironmentVariableName(var1));
      var2.append("_");
      String var7 = var2.toString();
      ConcurrentHashMap var3 = new ConcurrentHashMap();

      for (Entry var5 : System.getenv().entrySet()) {
         String var4 = (String)var5.getKey();
         if (var4.startsWith(var7)) {
            String var8 = StringUtils.removeSurrounding((String)var5.getValue(), "\"");
            if (var8 != null) {
               var3.put(var4.substring(var7.length()).toLowerCase(Locale.ROOT), var8);
            }
         }
      }

      return var3;
   }

   @Override
   public String getProperty(String var1) {
      return StringUtils.removeSurrounding(System.getenv(this.propertyToEnvironmentVariableName(var1)), "\"");
   }
}
