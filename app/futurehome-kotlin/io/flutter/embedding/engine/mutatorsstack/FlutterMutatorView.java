package io.flutter.embedding.engine.mutatorsstack;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.View.OnFocusChangeListener;
import android.view.ViewTreeObserver.OnGlobalFocusChangeListener;
import android.view.accessibility.AccessibilityEvent;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import io.flutter.embedding.android.AndroidTouchProcessor;
import io.flutter.util.ViewUtils;
import java.util.Iterator;

public class FlutterMutatorView extends FrameLayout {
   OnGlobalFocusChangeListener activeFocusListener;
   private final AndroidTouchProcessor androidTouchProcessor;
   private int left;
   private FlutterMutatorsStack mutatorsStack;
   private int prevLeft;
   private int prevTop;
   private float screenDensity;
   private int top;

   public FlutterMutatorView(Context var1) {
      this(var1, 1.0F, null);
   }

   public FlutterMutatorView(Context var1, float var2, AndroidTouchProcessor var3) {
      super(var1, null);
      this.screenDensity = var2;
      this.androidTouchProcessor = var3;
   }

   private Matrix getPlatformViewMatrix() {
      Matrix var2 = new Matrix(this.mutatorsStack.getFinalMatrix());
      float var1 = this.screenDensity;
      var2.preScale(1.0F / var1, 1.0F / var1);
      var2.postTranslate(-this.left, -this.top);
      return var2;
   }

   public void dispatchDraw(Canvas var1) {
      var1.save();
      var1.concat(this.getPlatformViewMatrix());
      super.dispatchDraw(var1);
      var1.restore();
   }

   public void draw(Canvas var1) {
      var1.save();
      Iterator var3 = this.mutatorsStack.getFinalClippingPaths().iterator();

      while (var3.hasNext()) {
         Path var2 = new Path((Path)var3.next());
         var2.offset(-this.left, -this.top);
         var1.clipPath(var2);
      }

      super.draw(var1);
      var1.restore();
   }

   public boolean onInterceptTouchEvent(MotionEvent var1) {
      return true;
   }

   public boolean onTouchEvent(MotionEvent var1) {
      if (this.androidTouchProcessor == null) {
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

         return this.androidTouchProcessor.onTouchEvent(var1, var4);
      }
   }

   public void readyToDisplay(FlutterMutatorsStack var1, int var2, int var3, int var4, int var5) {
      this.mutatorsStack = var1;
      this.left = var2;
      this.top = var3;
      LayoutParams var6 = new LayoutParams(var4, var5);
      var6.leftMargin = var2;
      var6.topMargin = var3;
      this.setLayoutParams(var6);
      this.setWillNotDraw(false);
   }

   public boolean requestSendAccessibilityEvent(View var1, AccessibilityEvent var2) {
      View var3 = this.getChildAt(0);
      return var3 != null && var3.getImportantForAccessibility() == 4 ? false : super.requestSendAccessibilityEvent(var1, var2);
   }

   public void setOnDescendantFocusChangeListener(OnFocusChangeListener var1) {
      this.unsetOnDescendantFocusChangeListener();
      ViewTreeObserver var2 = this.getViewTreeObserver();
      if (var2.isAlive() && this.activeFocusListener == null) {
         OnGlobalFocusChangeListener var3 = new OnGlobalFocusChangeListener(this, var1, this) {
            final FlutterMutatorView this$0;
            final View val$mutatorView;
            final OnFocusChangeListener val$userFocusListener;

            {
               this.this$0 = var1;
               this.val$userFocusListener = var2x;
               this.val$mutatorView = var3x;
            }

            public void onGlobalFocusChanged(View var1, View var2x) {
               OnFocusChangeListener var3x = this.val$userFocusListener;
               var2x = this.val$mutatorView;
               var3x.onFocusChange(var2x, ViewUtils.childHasFocus(var2x));
            }
         };
         this.activeFocusListener = var3;
         var2.addOnGlobalFocusChangeListener(var3);
      }
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
