package io.flutter.plugin.platform;

import android.view.View;
import io.flutter.view.AccessibilityBridge;

public interface PlatformViewsAccessibilityDelegate {
   void attachAccessibilityBridge(AccessibilityBridge var1);

   void detachAccessibilityBridge();

   View getPlatformViewById(int var1);

   boolean usesVirtualDisplay(int var1);
}
