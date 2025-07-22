package io.sentry.android.core;

import io.sentry.protocol.DebugImage;
import java.util.List;
import java.util.Set;

public interface IDebugImagesLoader {
   void clearDebugImages();

   List<DebugImage> loadDebugImages();

   Set<DebugImage> loadDebugImagesForAddresses(Set<String> var1);
}
