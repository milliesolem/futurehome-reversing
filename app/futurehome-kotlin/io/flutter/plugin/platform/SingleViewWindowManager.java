package io.flutter.plugin.platform;

import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.view.ViewGroup.LayoutParams;
import androidx.webkit.internal.ApiHelperForN..ExternalSyntheticApiModelOutline4;
import io.flutter.Log;
import io.flutter.view.AccessibilityBridge$$ExternalSyntheticApiModelOutline0;
import j..util.function.Consumer;
import java.util.concurrent.Executor;

abstract class SingleViewWindowManager implements WindowManager {
   private static final String TAG = "PlatformViewsController";
   final WindowManager delegate;
   SingleViewFakeWindowViewGroup fakeWindowRootView;

   SingleViewWindowManager(WindowManager var1, SingleViewFakeWindowViewGroup var2) {
      this.delegate = var1;
      this.fakeWindowRootView = var2;
   }

   public void addCrossWindowBlurEnabledListener(Consumer<Boolean> var1) {
      SingleViewWindowManager$$ExternalSyntheticAPIConversion0.m$1(this.delegate, var1);
   }

   public void addCrossWindowBlurEnabledListener(Executor var1, Consumer<Boolean> var2) {
      SingleViewWindowManager$$ExternalSyntheticAPIConversion0.m(this.delegate, var1, var2);
   }

   public void addView(View var1, LayoutParams var2) {
      SingleViewFakeWindowViewGroup var3 = this.fakeWindowRootView;
      if (var3 == null) {
         Log.w("PlatformViewsController", "Embedded view called addView while detached from presentation");
      } else {
         var3.addView(var1, var2);
      }
   }

   public WindowMetrics getCurrentWindowMetrics() {
      return ExternalSyntheticApiModelOutline4.m(this.delegate);
   }

   @Deprecated
   public Display getDefaultDisplay() {
      return this.delegate.getDefaultDisplay();
   }

   public WindowMetrics getMaximumWindowMetrics() {
      return ExternalSyntheticApiModelOutline4.m$1(this.delegate);
   }

   public boolean isCrossWindowBlurEnabled() {
      return AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(this.delegate);
   }

   public void removeCrossWindowBlurEnabledListener(Consumer<Boolean> var1) {
      SingleViewWindowManager$$ExternalSyntheticAPIConversion0.m(this.delegate, var1);
   }

   public void removeView(View var1) {
      SingleViewFakeWindowViewGroup var2 = this.fakeWindowRootView;
      if (var2 == null) {
         Log.w("PlatformViewsController", "Embedded view called removeView while detached from presentation");
      } else {
         var2.removeView(var1);
      }
   }

   public void removeViewImmediate(View var1) {
      if (this.fakeWindowRootView == null) {
         Log.w("PlatformViewsController", "Embedded view called removeViewImmediate while detached from presentation");
      } else {
         var1.clearAnimation();
         this.fakeWindowRootView.removeView(var1);
      }
   }

   public void updateViewLayout(View var1, LayoutParams var2) {
      SingleViewFakeWindowViewGroup var3 = this.fakeWindowRootView;
      if (var3 == null) {
         Log.w("PlatformViewsController", "Embedded view called updateViewLayout while detached from presentation");
      } else {
         var3.updateViewLayout(var1, var2);
      }
   }
}
