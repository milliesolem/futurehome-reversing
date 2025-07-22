package io.sentry.internal.modules;

import java.util.Map;

public final class NoOpModulesLoader implements IModulesLoader {
   private static final NoOpModulesLoader instance = new NoOpModulesLoader();

   private NoOpModulesLoader() {
   }

   public static NoOpModulesLoader getInstance() {
      return instance;
   }

   @Override
   public Map<String, String> getOrLoadModules() {
      return null;
   }
}
