package io.sentry.config;

import io.sentry.SystemOutLogger;
import java.util.ArrayList;
import java.util.Properties;

public final class PropertiesProviderFactory {
   public static PropertiesProvider create() {
      SystemOutLogger var1 = new SystemOutLogger();
      ArrayList var0 = new ArrayList();
      var0.add(new SystemPropertyPropertiesProvider());
      var0.add(new EnvironmentVariablePropertiesProvider());
      String var2 = System.getProperty("sentry.properties.file");
      if (var2 != null) {
         Properties var4 = new FilesystemPropertiesLoader(var2, var1).load();
         if (var4 != null) {
            var0.add(new SimplePropertiesProvider(var4));
         }
      }

      var2 = System.getenv("SENTRY_PROPERTIES_FILE");
      if (var2 != null) {
         Properties var6 = new FilesystemPropertiesLoader(var2, var1).load();
         if (var6 != null) {
            var0.add(new SimplePropertiesProvider(var6));
         }
      }

      Properties var7 = new ClasspathPropertiesLoader(var1).load();
      if (var7 != null) {
         var0.add(new SimplePropertiesProvider(var7));
      }

      Properties var3 = new FilesystemPropertiesLoader("sentry.properties", var1).load();
      if (var3 != null) {
         var0.add(new SimplePropertiesProvider(var3));
      }

      return new CompositePropertiesProvider(var0);
   }
}
