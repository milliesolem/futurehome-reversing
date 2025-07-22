package io.flutter.plugin.platform;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.PorterDuff.Mode;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.View.OnFocusChangeListener;
import android.view.ViewTreeObserver.OnGlobalFocusChangeListener;
import android.view.accessibility.AccessibilityEvent;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import io.flutter.Log;
import io.flutter.embedding.android.AndroidTouchProcessor;
import io.flutter.embedding.engine.renderer.FlutterRenderer;
import io.flutter.util.ViewUtils;
import io.flutter.view.AccessibilityBridge$$ExternalSyntheticApiModelOutline0;

public class PlatformViewWrapper extends FrameLayout {
   private static final String TAG = "PlatformViewWrapper";
   private OnGlobalFocusChangeListener activeFocusListener;
   private int left;
   private int prevLeft;
   private int prevTop;
   private PlatformViewRenderTarget renderTarget;
   private int top;
   private AndroidTouchProcessor touchProcessor;

   public PlatformViewWrapper(Context var1) {
      super(var1);
      this.setWillNotDraw(false);
   }

   public PlatformViewWrapper(Context var1, PlatformViewRenderTarget var2) {
      this(var1);
      this.renderTarget = var2;
      Surface var6 = var2.getSurface();
      if (var6 != null && !FlutterRenderer.debugDisableSurfaceClear) {
         Canvas var7 = AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var6);

         try {
            var7.drawColor(0, Mode.CLEAR);
         } finally {
            var6.unlockCanvasAndPost(var7);
         }
      }
   }

   public void draw(Canvas var1) {
      PlatformViewRenderTarget var2 = this.renderTarget;
      if (var2 == null) {
         super.draw(var1);
         Log.e("PlatformViewWrapper", "Platform view cannot be composed without a RenderTarget.");
      } else {
         Surface var3 = var2.getSurface();
         if (!var3.isValid()) {
            Log.e("PlatformViewWrapper", "Platform view cannot be composed without a valid RenderTarget surface.");
         } else {
            Canvas var6 = AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var3);
            if (var6 == null) {
               this.invalidate();
            } else {
               try {
                  var6.drawColor(0, Mode.CLEAR);
                  super.draw(var6);
               } finally {
                  this.renderTarget.scheduleFrame();
                  var3.unlockCanvasAndPost(var6);
               }
            }
         }
      }
   }

   public OnGlobalFocusChangeListener getActiveFocusListener() {
      return this.activeFocusListener;
   }

   public int getRenderTargetHeight() {
      PlatformViewRenderTarget var1 = this.renderTarget;
      return var1 != null ? var1.getHeight() : 0;
   }

   public int getRenderTargetWidth() {
      PlatformViewRenderTarget var1 = this.renderTarget;
      return var1 != null ? var1.getWidth() : 0;
   }

   public ViewParent invalidateChildInParent(int[] var1, Rect var2) {
      this.invalidate();
      return super.invalidateChildInParent(var1, var2);
   }

   public void onDescendantInvalidated(View var1, View var2) {
      super.onDescendantInvalidated(var1, var2);
      this.invalidate();
   }

   public boolean onInterceptTouchEvent(MotionEvent var1) {
      return true;
   }

   public boolean onTouchEvent(MotionEvent var1) {
      if (this.touchProcessor == null) {
         return super.onTouchEvent(var1);
      } else {
         Matrix var4 = new Matrix();
         int var2 = var1.getAction();
         if (var2 != 0) {
            if (var2 != 2) {
               var4.postTranslate(this.left, this.top);
            } else {
               var4.postTranslate(this.prevLeft, this.prevTop);
               this.prevLeft = this.left;
               this.prevTop = this.top;
            }
         } else {
            int var3 = this.left;
            this.prevLeft = var3;
            var2 = this.top;
            this.prevTop = var2;
            var4.postTranslate(var3, var2);
         }

         return this.touchProcessor.onTouchEvent(var1, var4);
      }
   }

   public void release() {
      PlatformViewRenderTarget var1 = this.renderTarget;
      if (var1 != null) {
         var1.release();
         this.renderTarget = null;
      }
   }

   public boolean requestSendAccessibilityEvent(View var1, AccessibilityEvent var2) {
      View var3 = this.getChildAt(0);
      return var3 != null && var3.getImportantForAccessibility() == 4 ? false : super.requestSendAccessibilityEvent(var1, var2);
   }

   public void resizeRenderTarget(int var1, int var2) {
      PlatformViewRenderTarget var3 = this.renderTarget;
      if (var3 != null) {
         var3.resize(var1, var2);
      }
   }

   public void setLayoutParams(LayoutParams var1) {
      super.setLayoutParams(var1);
      this.left = var1.leftMargin;
      this.top = var1.topMargin;
   }

   public void setOnDescendantFocusChangeListener(OnFocusChangeListener var1) {
      this.unsetOnDescendantFocusChangeListener();
      ViewTreeObserver var2 = this.getViewTreeObserver();
      if (var2.isAlive() && this.activeFocusListener == null) {
         OnGlobalFocusChangeListener var3 = new OnGlobalFocusChangeListener(this, var1) {
            final PlatformViewWrapper this$0;
            final OnFocusChangeListener val$userFocusListener;

            {
               this.this$0 = var1;
               this.val$userFocusListener = var2x;
            }

            public void onGlobalFocusChanged(View var1, View var2x) {
               OnFocusChangeListener var4 = this.val$userFocusListener;
               PlatformViewWrapper var3x = this.this$0;
               var4.onFocusChange(var3x, ViewUtils.childHasFocus(var3x));
            }
         };
         this.activeFocusListener = var3;
         var2.addOnGlobalFocusChangeListener(var3);
      }
   }

   public void setTouchProcessor(AndroidTouchProcessor var1) {
      this.touchProcessor = var1;
   }

   public void unsetOnDescendantFocusChangeListener() {
      ViewTreeObserver var2 = this.getViewTreeObserver();
      if (var2.isAlive()) {
         OnGlobalFocusChangeListener var1 = this.activeFocusListener;
         if (var1 != null) {
            this.activeFocusListener = null;
            var2.removeOnGlobalFocusChangeListener(var1);
         }
      }
   }
}
