package io.sentry.config;

import java.util.List;
import java.util.Map;

public interface PropertiesProvider {
   Boolean getBooleanProperty(String var1);

   Double getDoubleProperty(String var1);

   List<String> getList(String var1);

   Long getLongProperty(String var1);

   Map<String, String> getMap(String var1);

   String getProperty(String var1);

   String getProperty(String var1, String var2);
}
