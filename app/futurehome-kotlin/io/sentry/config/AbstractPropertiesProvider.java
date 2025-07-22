package io.sentry.config;

import io.sentry.util.Objects;
import io.sentry.util.StringUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

abstract class AbstractPropertiesProvider implements PropertiesProvider {
   private final String prefix;
   private final Properties properties;

   protected AbstractPropertiesProvider(String var1, Properties var2) {
      this.prefix = Objects.requireNonNull(var1, "prefix is required");
      this.properties = Objects.requireNonNull(var2, "properties are required");
   }

   protected AbstractPropertiesProvider(Properties var1) {
      this("", var1);
   }

   @Override
   public Map<String, String> getMap(String var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append(this.prefix);
      var2.append(var1);
      var2.append(".");
      String var3 = var2.toString();
      HashMap var7 = new HashMap();

      for (Entry var5 : this.properties.entrySet()) {
         if (var5.getKey() instanceof String && var5.getValue() instanceof String) {
            String var4 = (String)var5.getKey();
            if (var4.startsWith(var3)) {
               String var8 = StringUtils.removeSurrounding((String)var5.getValue(), "\"");
               var7.put(var4.substring(var3.length()), var8);
            }
         }
      }

      return var7;
   }

   @Override
   public String getProperty(String var1) {
      Properties var3 = this.properties;
      StringBuilder var2 = new StringBuilder();
      var2.append(this.prefix);
      var2.append(var1);
      return StringUtils.removeSurrounding(var3.getProperty(var2.toString()), "\"");
   }
}
