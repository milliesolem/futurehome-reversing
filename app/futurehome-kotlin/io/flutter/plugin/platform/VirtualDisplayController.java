package io.flutter.plugin.platform;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.hardware.display.VirtualDisplay.Callback;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewTreeObserver.OnDrawListener;

class VirtualDisplayController {
   private static String TAG;
   private static Callback callback = new Callback() {
      public void onPaused() {
      }

      public void onResumed() {
      }
   };
   private final AccessibilityEventsDelegate accessibilityEventsDelegate;
   private final Context context;
   private final int densityDpi;
   private final OnFocusChangeListener focusChangeListener;
   SingleViewPresentation presentation;
   private final PlatformViewRenderTarget renderTarget;
   private final int viewId;
   private VirtualDisplay virtualDisplay;

   private VirtualDisplayController(
      Context var1,
      AccessibilityEventsDelegate var2,
      VirtualDisplay var3,
      PlatformView var4,
      PlatformViewRenderTarget var5,
      OnFocusChangeListener var6,
      int var7,
      Object var8
   ) {
      this.context = var1;
      this.accessibilityEventsDelegate = var2;
      this.renderTarget = var5;
      this.focusChangeListener = var6;
      this.viewId = var7;
      this.virtualDisplay = var3;
      this.densityDpi = var1.getResources().getDisplayMetrics().densityDpi;
      SingleViewPresentation var9 = new SingleViewPresentation(var1, this.virtualDisplay.getDisplay(), var4, var2, var7, var6);
      this.presentation = var9;
      var9.show();
   }

   public static VirtualDisplayController create(
      Context var0,
      AccessibilityEventsDelegate var1,
      PlatformView var2,
      PlatformViewRenderTarget var3,
      int var4,
      int var5,
      int var6,
      Object var7,
      OnFocusChangeListener var8
   ) {
      Object var10 = null;
      VirtualDisplayController var9 = (VirtualDisplayController)var10;
      if (var4 != 0) {
         if (var5 == 0) {
            var9 = (VirtualDisplayController)var10;
         } else {
            DisplayManager var11 = (DisplayManager)var0.getSystemService("display");
            var10 = var0.getResources().getDisplayMetrics();
            var3.resize(var4, var5);
            StringBuilder var12 = new StringBuilder("flutter-vd#");
            var12.append(var6);
            VirtualDisplay var13 = var11.createVirtualDisplay(
               var12.toString(), var4, var5, ((DisplayMetrics)var10).densityDpi, var3.getSurface(), 0, callback, null
            );
            if (var13 == null) {
               return null;
            }

            var9 = new VirtualDisplayController(var0, var1, var13, var2, var3, var8, var6, var7);
         }
      }

      return var9;
   }

   private void resize31(View var1, int var2, int var3, Runnable var4) {
      this.renderTarget.resize(var2, var3);
      this.virtualDisplay.resize(var2, var3, this.densityDpi);
      this.virtualDisplay.setSurface(this.renderTarget.getSurface());
      var1.postDelayed(var4, 0L);
   }

   public void clearSurface() {
      this.virtualDisplay.setSurface(null);
   }

   public void dispatchTouchEvent(MotionEvent var1) {
      SingleViewPresentation var2 = this.presentation;
      if (var2 != null) {
         var2.dispatchTouchEvent(var1);
      }
   }

   public void dispose() {
      this.presentation.cancel();
      this.presentation.detachState();
      this.virtualDisplay.release();
      this.renderTarget.release();
   }

   public int getRenderTargetHeight() {
      PlatformViewRenderTarget var1 = this.renderTarget;
      return var1 != null ? var1.getHeight() : 0;
   }

   public int getRenderTargetWidth() {
      PlatformViewRenderTarget var1 = this.renderTarget;
      return var1 != null ? var1.getWidth() : 0;
   }

   public View getView() {
      SingleViewPresentation var1 = this.presentation;
      return var1 == null ? null : var1.getView().getView();
   }

   void onFlutterViewAttached(View var1) {
      SingleViewPresentation var2 = this.presentation;
      if (var2 != null && var2.getView() != null) {
         this.presentation.getView().onFlutterViewAttached(var1);
      }
   }

   void onFlutterViewDetached() {
      SingleViewPresentation var1 = this.presentation;
      if (var1 != null && var1.getView() != null) {
         this.presentation.getView().onFlutterViewDetached();
      }
   }

   void onInputConnectionLocked() {
      SingleViewPresentation var1 = this.presentation;
      if (var1 != null && var1.getView() != null) {
         this.presentation.getView().onInputConnectionLocked();
      }
   }

   void onInputConnectionUnlocked() {
      SingleViewPresentation var1 = this.presentation;
      if (var1 != null && var1.getView() != null) {
         this.presentation.getView().onInputConnectionUnlocked();
      }
   }

   public void resetSurface() {
      int var2 = this.getRenderTargetWidth();
      int var1 = this.getRenderTargetHeight();
      boolean var3 = this.getView().isFocused();
      SingleViewPresentation.PresentationState var5 = this.presentation.detachState();
      this.virtualDisplay.setSurface(null);
      this.virtualDisplay.release();
      DisplayManager var4 = (DisplayManager)this.context.getSystemService("display");
      StringBuilder var6 = new StringBuilder("flutter-vd#");
      var6.append(this.viewId);
      this.virtualDisplay = var4.createVirtualDisplay(var6.toString(), var2, var1, this.densityDpi, this.renderTarget.getSurface(), 0, callback, null);
      SingleViewPresentation var7 = new SingleViewPresentation(
         this.context, this.virtualDisplay.getDisplay(), this.accessibilityEventsDelegate, var5, this.focusChangeListener, var3
      );
      var7.show();
      this.presentation.cancel();
      this.presentation = var7;
   }

   public void resize(int var1, int var2, Runnable var3) {
      if (var1 == this.getRenderTargetWidth() && var2 == this.getRenderTargetHeight()) {
         this.getView().postDelayed(var3, 0L);
      } else if (VERSION.SDK_INT >= 31) {
         this.resize31(this.getView(), var1, var2, var3);
      } else {
         boolean var4 = this.getView().isFocused();
         SingleViewPresentation.PresentationState var5 = this.presentation.detachState();
         this.virtualDisplay.setSurface(null);
         this.virtualDisplay.release();
         DisplayManager var7 = (DisplayManager)this.context.getSystemService("display");
         this.renderTarget.resize(var1, var2);
         StringBuilder var6 = new StringBuilder("flutter-vd#");
         var6.append(this.viewId);
         this.virtualDisplay = var7.createVirtualDisplay(var6.toString(), var1, var2, this.densityDpi, this.renderTarget.getSurface(), 0, callback, null);
         View var9 = this.getView();
         var9.addOnAttachStateChangeListener(new OnAttachStateChangeListener(this, var9, var3) {
            final VirtualDisplayController this$0;
            final View val$embeddedView;
            final Runnable val$onNewSizeFrameAvailable;

            {
               this.this$0 = var1;
               this.val$embeddedView = var2x;
               this.val$onNewSizeFrameAvailable = var3x;
            }

            public void onViewAttachedToWindow(View var1) {
               VirtualDisplayController.OneTimeOnDrawListener.schedule(this.val$embeddedView, new Runnable(this) {
                  final <unrepresentable> this$1;

                  {
                     this.this$1 = var1;
                  }

                  @Override
                  public void run() {
                     this.this$1.val$embeddedView.postDelayed(this.this$1.val$onNewSizeFrameAvailable, 128L);
                  }
               });
               this.val$embeddedView.removeOnAttachStateChangeListener(this);
            }

            public void onViewDetachedFromWindow(View var1) {
            }
         });
         SingleViewPresentation var8 = new SingleViewPresentation(
            this.context, this.virtualDisplay.getDisplay(), this.accessibilityEventsDelegate, var5, this.focusChangeListener, var4
         );
         var8.show();
         this.presentation.cancel();
         this.presentation = var8;
      }
   }

   static class OneTimeOnDrawListener implements OnDrawListener {
      Runnable mOnDrawRunnable;
      final View mView;

      OneTimeOnDrawListener(View var1, Runnable var2) {
         this.mView = var1;
         this.mOnDrawRunnable = var2;
      }

      static void schedule(View var0, Runnable var1) {
         VirtualDisplayController.OneTimeOnDrawListener var2 = new VirtualDisplayController.OneTimeOnDrawListener(var0, var1);
         var0.getViewTreeObserver().addOnDrawListener(var2);
      }

      public void onDraw() {
         Runnable var1 = this.mOnDrawRunnable;
         if (var1 != null) {
            var1.run();
            this.mOnDrawRunnable = null;
            this.mView.post(new Runnable(this) {
               final VirtualDisplayController.OneTimeOnDrawListener this$0;

               {
                  this.this$0 = var1;
               }

               @Override
               public void run() {
                  this.this$0.mView.getViewTreeObserver().removeOnDrawListener(this.this$0);
               }
            });
         }
      }
   }
}
