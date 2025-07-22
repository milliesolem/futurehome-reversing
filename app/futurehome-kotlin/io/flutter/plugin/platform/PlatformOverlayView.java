package io.flutter.plugin.platform;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import io.flutter.embedding.android.FlutterImageView;

public class PlatformOverlayView extends FlutterImageView {
   private AccessibilityEventsDelegate accessibilityDelegate;

   public PlatformOverlayView(Context var1) {
      this(var1, 1, 1, null);
   }

   public PlatformOverlayView(Context var1, int var2, int var3, AccessibilityEventsDelegate var4) {
      super(var1, var2, var3, FlutterImageView.SurfaceKind.overlay);
      this.accessibilityDelegate = var4;
   }

   public PlatformOverlayView(Context var1, AttributeSet var2) {
      this(var1, 1, 1, null);
   }

   public boolean onHoverEvent(MotionEvent var1) {
      AccessibilityEventsDelegate var2 = this.accessibilityDelegate;
      return var2 != null && var2.onAccessibilityHoverEvent(var1, true) ? true : super.onHoverEvent(var1);
   }
}
