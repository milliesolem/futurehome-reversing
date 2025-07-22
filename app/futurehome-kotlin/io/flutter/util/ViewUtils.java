package io.flutter.util;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;
import android.view.ViewGroup;
import androidx.window.layout.WindowMetrics;
import androidx.window.layout.WindowMetricsCalculator._CC;

public final class ViewUtils {
   public static void calculateMaximumDisplayMetrics(Context var0, ViewUtils.DisplayUpdater var1) {
      Activity var2 = getActivity(var0);
      if (var2 != null) {
         WindowMetrics var3 = _CC.getOrCreate().computeMaximumWindowMetrics(var2);
         var1.updateDisplayMetrics(var3.getBounds().width(), var3.getBounds().height(), var0.getResources().getDisplayMetrics().density);
      }
   }

   public static boolean childHasFocus(View var0) {
      return traverseHierarchy(var0, new ViewUtils$$ExternalSyntheticLambda1());
   }

   public static Activity getActivity(Context var0) {
      if (var0 == null) {
         return null;
      } else if (var0 instanceof Activity) {
         return (Activity)var0;
      } else {
         return var0 instanceof ContextWrapper ? getActivity(((ContextWrapper)var0).getBaseContext()) : null;
      }
   }

   public static boolean hasChildViewOfType(View var0, Class<? extends View>[] var1) {
      return traverseHierarchy(var0, new ViewUtils$$ExternalSyntheticLambda0(var1));
   }

   public static boolean traverseHierarchy(View var0, ViewUtils.ViewVisitor var1) {
      if (var0 == null) {
         return false;
      } else if (var1.run(var0)) {
         return true;
      } else {
         if (var0 instanceof ViewGroup) {
            ViewGroup var3 = (ViewGroup)var0;

            for (int var2 = 0; var2 < var3.getChildCount(); var2++) {
               if (traverseHierarchy(var3.getChildAt(var2), var1)) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public interface DisplayUpdater {
      void updateDisplayMetrics(float var1, float var2, float var3);
   }

   public interface ViewVisitor {
      boolean run(View var1);
   }
}
