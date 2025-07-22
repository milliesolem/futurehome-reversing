package io.sentry.android.ndk;

import io.sentry.protocol.DebugImage;

final class NativeModuleListLoader {
   public static native void nativeClearModuleList();

   public static native DebugImage[] nativeLoadModuleList();

   public void clearModuleList() {
      nativeClearModuleList();
   }

   public DebugImage[] loadModuleList() {
      return nativeLoadModuleList();
   }
}
