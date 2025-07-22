package io.flutter.embedding.android;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.Window;
import android.view.WindowInsets;
import androidx.core.view.WindowInsetsCompat.Impl28..ExternalSyntheticApiModelOutline0;
import io.flutter.embedding.engine.renderer.FlutterRenderer;
import io.flutter.util.ViewUtils;
import io.flutter.view.AccessibilityBridge$$ExternalSyntheticApiModelOutline0;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class FlutterViewDelegate {
   public List<Rect> getCaptionBarInsets(Context var1) {
      WindowInsets var2 = this.getWindowInsets(var1);
      return var2 == null ? Collections.emptyList() : AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var2, ExternalSyntheticApiModelOutline0.m$2());
   }

   public WindowInsets getWindowInsets(Context var1) {
      Activity var2 = ViewUtils.getActivity(var1);
      if (var2 == null) {
         return null;
      } else {
         Window var3 = var2.getWindow();
         return var3 == null ? null : AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var3.getDecorView());
      }
   }

   public void growViewportMetricsToCaptionBar(Context var1, FlutterRenderer.ViewportMetrics var2) {
      List var4 = this.getCaptionBarInsets(var1);
      int var3 = var2.viewPaddingTop;
      Iterator var5 = var4.iterator();

      while (var5.hasNext()) {
         var3 = Math.max(var3, ((Rect)var5.next()).bottom);
      }

      var2.viewPaddingTop = var3;
   }
}
