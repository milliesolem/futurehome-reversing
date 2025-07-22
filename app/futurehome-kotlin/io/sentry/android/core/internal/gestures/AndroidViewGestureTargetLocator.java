package io.sentry.android.core.internal.gestures;

import android.content.res.Resources.NotFoundException;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ScrollView;
import androidx.core.view.ScrollingView;
import io.sentry.android.core.internal.util.ClassUtil;
import io.sentry.internal.gestures.GestureTargetLocator;
import io.sentry.internal.gestures.UiElement;

public final class AndroidViewGestureTargetLocator implements GestureTargetLocator {
   private static final String ORIGIN = "old_view_system";
   private final boolean isAndroidXAvailable;

   public AndroidViewGestureTargetLocator(boolean var1) {
      this.isAndroidXAvailable = var1;
   }

   private UiElement createUiElement(View var1) {
      try {
         String var2 = ViewUtils.getResourceId(var1);
         return new UiElement(var1, ClassUtil.getClassName(var1), var2, null, "old_view_system");
      } catch (NotFoundException var3) {
         return null;
      }
   }

   private static boolean isJetpackScrollingView(View var0, boolean var1) {
      return !var1 ? false : ScrollingView.class.isAssignableFrom(var0.getClass());
   }

   private static boolean isViewScrollable(View var0, boolean var1) {
      if ((isJetpackScrollingView(var0, var1) || AbsListView.class.isAssignableFrom(var0.getClass()) || ScrollView.class.isAssignableFrom(var0.getClass()))
         && var0.getVisibility() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private static boolean isViewTappable(View var0) {
      boolean var1;
      if (var0.isClickable() && var0.getVisibility() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public UiElement locate(Object var1, float var2, float var3, UiElement.Type var4) {
      if (!(var1 instanceof View)) {
         return null;
      } else {
         var1 = var1;
         if (var4 == UiElement.Type.CLICKABLE && isViewTappable(var1)) {
            return this.createUiElement(var1);
         } else {
            return var4 == UiElement.Type.SCROLLABLE && isViewScrollable(var1, this.isAndroidXAvailable) ? this.createUiElement(var1) : null;
         }
      }
   }
}
