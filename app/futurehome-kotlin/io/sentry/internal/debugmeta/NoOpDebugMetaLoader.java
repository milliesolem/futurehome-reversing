package io.sentry.internal.debugmeta;

import java.util.List;
import java.util.Properties;

public final class NoOpDebugMetaLoader implements IDebugMetaLoader {
   private static final NoOpDebugMetaLoader instance = new NoOpDebugMetaLoader();

   private NoOpDebugMetaLoader() {
   }

   public static NoOpDebugMetaLoader getInstance() {
      return instance;
   }

   @Override
   public List<Properties> loadDebugMeta() {
      return null;
   }
}
