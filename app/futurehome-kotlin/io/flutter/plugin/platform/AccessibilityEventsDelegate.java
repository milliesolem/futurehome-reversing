package io.flutter.plugin.platform;

import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import io.flutter.view.AccessibilityBridge;

class AccessibilityEventsDelegate {
   private AccessibilityBridge accessibilityBridge;

   public boolean onAccessibilityHoverEvent(MotionEvent var1, boolean var2) {
      AccessibilityBridge var3 = this.accessibilityBridge;
      return var3 == null ? false : var3.onAccessibilityHoverEvent(var1, var2);
   }

   public boolean requestSendAccessibilityEvent(View var1, View var2, AccessibilityEvent var3) {
      AccessibilityBridge var4 = this.accessibilityBridge;
      return var4 == null ? false : var4.externalViewRequestSendAccessibilityEvent(var1, var2, var3);
   }

   void setAccessibilityBridge(AccessibilityBridge var1) {
      this.accessibilityBridge = var1;
   }
}
