package io.sentry.android.core;

import io.sentry.protocol.DebugImage;
import java.util.List;
import java.util.Set;

final class NoOpDebugImagesLoader implements IDebugImagesLoader {
   private static final NoOpDebugImagesLoader instance = new NoOpDebugImagesLoader();

   private NoOpDebugImagesLoader() {
   }

   public static NoOpDebugImagesLoader getInstance() {
      return instance;
   }

   @Override
   public void clearDebugImages() {
   }

   @Override
   public List<DebugImage> loadDebugImages() {
      return null;
   }

   @Override
   public Set<DebugImage> loadDebugImagesForAddresses(Set<String> var1) {
      return null;
   }
}
