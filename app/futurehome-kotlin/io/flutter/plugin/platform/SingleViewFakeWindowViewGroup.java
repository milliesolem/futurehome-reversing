package io.flutter.plugin.platform;

import android.content.Context;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.view.WindowManager.LayoutParams;

class SingleViewFakeWindowViewGroup extends ViewGroup {
   private final Rect childRect;
   private final Rect viewBounds = new Rect();

   public SingleViewFakeWindowViewGroup(Context var1) {
      super(var1);
      this.childRect = new Rect();
   }

   private static int atMost(int var0) {
      return MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(var0), Integer.MIN_VALUE);
   }

   protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
      for (int var6 = 0; var6 < this.getChildCount(); var6++) {
         View var8 = this.getChildAt(var6);
         LayoutParams var7 = (LayoutParams)var8.getLayoutParams();
         this.viewBounds.set(var2, var3, var4, var5);
         Gravity.apply(var7.gravity, var8.getMeasuredWidth(), var8.getMeasuredHeight(), this.viewBounds, var7.x, var7.y, this.childRect);
         var8.layout(this.childRect.left, this.childRect.top, this.childRect.right, this.childRect.bottom);
      }
   }

   protected void onMeasure(int var1, int var2) {
      for (int var3 = 0; var3 < this.getChildCount(); var3++) {
         this.getChildAt(var3).measure(atMost(var1), atMost(var2));
      }

      super.onMeasure(var1, var2);
   }
}
