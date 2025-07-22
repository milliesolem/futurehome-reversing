package io.sentry.android.replay.util

import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.InsetDrawable
import android.graphics.drawable.VectorDrawable
import android.os.Build.VERSION
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnDrawListener
import androidx.transition.CanvasUtils..ExternalSyntheticApiModelOutline0
import io.sentry.SentryOptions
import io.sentry.android.replay.viewhierarchy.ComposeViewHierarchyNode
import io.sentry.android.replay.viewhierarchy.ViewHierarchyNode
import java.util.ArrayList

internal final val totalPaddingTopSafe: Int
   internal final get() {
      var var1: Int;
      try {
         var1 = var0.getTotalPaddingTop();
      } catch (var3: NullPointerException) {
         var1 = var0.getExtendedPaddingTop();
      }

      return var1;
   }


internal fun View?.addOnDrawListenerSafe(listener: OnDrawListener) {
   if (var0 != null && var0.getViewTreeObserver() != null && var0.getViewTreeObserver().isAlive()) {
      try {
         var0.getViewTreeObserver().addOnDrawListener(var1);
      } catch (var2: IllegalStateException) {
      }
   }
}

internal fun TextLayout?.getVisibleRects(globalRect: Rect, paddingLeft: Int, paddingTop: Int): List<Rect> {
   if (var0 == null) {
      return CollectionsKt.listOf(var1);
   } else {
      val var11: java.util.List = new ArrayList();
      val var7: Int = var0.getLineCount();

      for (int var4 = 0; var4 < var7; var4++) {
         val var8: Int = (int)var0.getPrimaryHorizontal(var4, var0.getLineStart(var4));
         var var6: Int = var0.getEllipsisCount(var4);
         var var9: Int = var0.getLineVisibleEnd(var4);
         var var5: Byte;
         if (var6 > 0) {
            var5 = 1;
         } else {
            var5 = 0;
         }

         var6 = (int)var0.getPrimaryHorizontal(var4, var9 - var6 + var5);
         var5 = var6;
         if (var6 == 0) {
            var5 = var6;
            if (var9 > 0) {
               var5 = (int)var0.getPrimaryHorizontal(var4, var9 - 1) + 1;
            }
         }

         var9 = var0.getLineTop(var4);
         var6 = var0.getLineBottom(var4);
         val var10: Rect = new Rect();
         var10.left = var1.left + var2 + var8;
         var10.right = var10.left + (var5 - var8);
         var10.top = var1.top + var3 + var9;
         var10.bottom = var10.top + (var6 - var9);
         var11.add(var10);
      }

      return var11;
   }
}

internal fun Drawable?.isMaskable(): Boolean {
   var var4: Boolean;
   if (var0 is InsetDrawable) {
      var4 = true;
   } else {
      var4 = var0 is ColorDrawable;
   }

   if (var4) {
      var4 = true;
   } else {
      var4 = var0 is VectorDrawable;
   }

   if (var4) {
      var4 = true;
   } else {
      var4 = var0 is GradientDrawable;
   }

   if (var4) {
      var4 = false;
   } else {
      var4 = true;
      if (var0 is BitmapDrawable) {
         val var3: Bitmap = (var0 as BitmapDrawable).getBitmap();
         if (var3 == null) {
            return false;
         }

         if (!var3.isRecycled() && var3.getHeight() > 10 && var3.getWidth() > 10) {
            var4 = true;
         } else {
            var4 = false;
         }
      }
   }

   return var4;
}

internal fun View.isVisibleToUser(): Pair<Boolean, Rect?> {
   val var2: Boolean = var0.isAttachedToWindow();
   val var4: java.lang.Boolean = false;
   if (!var2) {
      return TuplesKt.to(var4, null);
   } else if (var0.getWindowVisibility() != 0) {
      return TuplesKt.to(var4, null);
   } else {
      var var3: View = var0;

      while (var3 instanceof View) {
         val var1: Float;
         if (VERSION.SDK_INT >= 29) {
            var1 = ExternalSyntheticApiModelOutline0.m(var3);
         } else {
            var1 = 1.0F;
         }

         var3 = var3;
         if (var3.getAlpha() <= 0.0F || var1 <= 0.0F || var3.getVisibility() != 0) {
            return TuplesKt.to(var4, null);
         }

         var3 = var3.getParent();
      }

      val var6: Rect = new Rect();
      return TuplesKt.to(var0.getGlobalVisibleRect(var6, new Point()), var6);
   }
}

internal fun View?.removeOnDrawListenerSafe(listener: OnDrawListener) {
   if (var0 != null && var0.getViewTreeObserver() != null && var0.getViewTreeObserver().isAlive()) {
      try {
         var0.getViewTreeObserver().removeOnDrawListener(var1);
      } catch (var2: IllegalStateException) {
      }
   }
}

internal fun Int.toOpaque(): Int {
   return var0 or -16777216;
}

internal fun View.traverse(parentNode: ViewHierarchyNode, options: SentryOptions) {
   if (var0 is ViewGroup) {
      if (!ComposeViewHierarchyNode.INSTANCE.fromView(var0, var1, var2)) {
         val var7: ViewGroup = var0 as ViewGroup;
         if ((var0 as ViewGroup).getChildCount() != 0) {
            val var6: ArrayList = new ArrayList(var7.getChildCount());
            val var4: Int = var7.getChildCount();

            for (int var3 = 0; var3 < var4; var3++) {
               val var5: View = var7.getChildAt(var3);
               if (var5 != null) {
                  val var8: ViewHierarchyNode = ViewHierarchyNode.Companion.fromView(var5, var1, var7.indexOfChild(var5), var2);
                  var6.add(var8);
                  traverse(var5, var8, var2);
               }
            }

            var1.setChildren(var6);
         }
      }
   }
}
