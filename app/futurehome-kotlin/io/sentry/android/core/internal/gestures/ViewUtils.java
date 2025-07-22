package io.sentry.android.core.internal.gestures;

import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.view.View;
import android.view.ViewGroup;
import io.sentry.android.core.SentryAndroidOptions;
import io.sentry.internal.gestures.GestureTargetLocator;
import io.sentry.internal.gestures.UiElement;
import java.util.Iterator;
import java.util.LinkedList;

public final class ViewUtils {
   private static final int[] coordinates = new int[2];

   static UiElement findTarget(SentryAndroidOptions var0, View var1, float var2, float var3, UiElement.Type var4) {
      LinkedList var7 = new LinkedList();
      var7.add(var1);
      UiElement var10 = null;

      while (var7.size() > 0) {
         View var8 = (View)var7.poll();
         if (touchWithinBounds(var8, var2, var3)) {
            if (var8 instanceof ViewGroup) {
               ViewGroup var6 = (ViewGroup)var8;

               for (int var5 = 0; var5 < var6.getChildCount(); var5++) {
                  var7.add(var6.getChildAt(var5));
               }
            }

            Iterator var9 = var0.getGestureTargetLocators().iterator();
            UiElement var12 = var10;

            while (true) {
               var10 = var12;
               if (!var9.hasNext()) {
                  break;
               }

               UiElement var11 = ((GestureTargetLocator)var9.next()).locate(var8, var2, var3, var4);
               if (var11 != null) {
                  if (var4 == UiElement.Type.CLICKABLE) {
                     var12 = var11;
                  } else if (var4 == UiElement.Type.SCROLLABLE) {
                     return var11;
                  }
               }
            }
         }
      }

      return var10;
   }

   public static String getResourceId(View var0) throws NotFoundException {
      int var1 = var0.getId();
      if (var1 != -1 && !isViewIdGenerated(var1)) {
         Resources var2 = var0.getContext().getResources();
         return var2 != null ? var2.getResourceEntryName(var1) : "";
      } else {
         throw new NotFoundException();
      }
   }

   static String getResourceIdWithFallback(View var0) {
      int var1 = var0.getId();

      try {
         return getResourceId(var0);
      } catch (NotFoundException var2) {
         StringBuilder var3 = new StringBuilder("0x");
         var3.append(Integer.toString(var1, 16));
         return var3.toString();
      }
   }

   private static boolean isViewIdGenerated(int var0) {
      boolean var1;
      if ((0xFF000000 & var0) == 0 && (var0 & 16777215) != 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private static boolean touchWithinBounds(View var0, float var1, float var2) {
      boolean var8 = false;
      if (var0 == null) {
         return false;
      } else {
         int[] var9 = coordinates;
         var0.getLocationOnScreen(var9);
         int var3 = var9[0];
         int var6 = var9[1];
         int var5 = var0.getWidth();
         int var4 = var0.getHeight();
         boolean var7 = var8;
         if (!(var1 < var3)) {
            var7 = var8;
            if (!(var1 > var3 + var5)) {
               var7 = var8;
               if (!(var2 < var6)) {
                  var7 = var8;
                  if (!(var2 > var6 + var4)) {
                     var7 = true;
                  }
               }
            }
         }

         return var7;
      }
   }
}
