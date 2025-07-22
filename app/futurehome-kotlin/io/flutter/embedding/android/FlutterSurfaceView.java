package io.flutter.embedding.android;

import android.content.Context;
import android.graphics.Region;
import android.graphics.Region.Op;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;
import io.flutter.Log;
import io.flutter.embedding.engine.renderer.FlutterRenderer;
import io.flutter.embedding.engine.renderer.FlutterUiDisplayListener;
import io.flutter.embedding.engine.renderer.RenderSurface;

public class FlutterSurfaceView extends SurfaceView implements RenderSurface {
   private static final String TAG = "FlutterSurfaceView";
   private FlutterRenderer flutterRenderer;
   private final FlutterUiDisplayListener flutterUiDisplayListener;
   private boolean isPaused;
   private boolean isSurfaceAvailableForRendering = false;
   private final boolean renderTransparently;
   private final Callback surfaceCallback;

   public FlutterSurfaceView(Context var1) {
      this(var1, null, false);
   }

   public FlutterSurfaceView(Context var1, AttributeSet var2) {
      this(var1, var2, false);
   }

   private FlutterSurfaceView(Context var1, AttributeSet var2, boolean var3) {
      super(var1, var2);
      this.isPaused = false;
      this.surfaceCallback = new Callback(this) {
         final FlutterSurfaceView this$0;

         {
            this.this$0 = var1;
         }

         public void surfaceChanged(SurfaceHolder var1, int var2x, int var3x, int var4) {
            Log.v("FlutterSurfaceView", "SurfaceHolder.Callback.surfaceChanged()");
            if (this.this$0.shouldNotify()) {
               this.this$0.changeSurfaceSize(var3x, var4);
            }
         }

         public void surfaceCreated(SurfaceHolder var1) {
            Log.v("FlutterSurfaceView", "SurfaceHolder.Callback.startRenderingToSurface()");
            this.this$0.isSurfaceAvailableForRendering = true;
            if (this.this$0.shouldNotify()) {
               this.this$0.connectSurfaceToRenderer();
            }
         }

         public void surfaceDestroyed(SurfaceHolder var1) {
            Log.v("FlutterSurfaceView", "SurfaceHolder.Callback.stopRenderingToSurface()");
            this.this$0.isSurfaceAvailableForRendering = false;
            if (this.this$0.shouldNotify()) {
               this.this$0.disconnectSurfaceFromRenderer();
            }
         }
      };
      this.flutterUiDisplayListener = new FlutterUiDisplayListener(this) {
         final FlutterSurfaceView this$0;

         {
            this.this$0 = var1;
         }

         @Override
         public void onFlutterUiDisplayed() {
            Log.v("FlutterSurfaceView", "onFlutterUiDisplayed()");
            this.this$0.setAlpha(1.0F);
            if (this.this$0.flutterRenderer != null) {
               this.this$0.flutterRenderer.removeIsDisplayingFlutterUiListener(this);
            }
         }

         @Override
         public void onFlutterUiNoLongerDisplayed() {
         }
      };
      this.renderTransparently = var3;
      this.init();
   }

   public FlutterSurfaceView(Context var1, boolean var2) {
      this(var1, null, var2);
   }

   private void changeSurfaceSize(int var1, int var2) {
      if (this.flutterRenderer != null) {
         StringBuilder var3 = new StringBuilder("Notifying FlutterRenderer that Android surface size has changed to ");
         var3.append(var1);
         var3.append(" x ");
         var3.append(var2);
         Log.v("FlutterSurfaceView", var3.toString());
         this.flutterRenderer.surfaceChanged(var1, var2);
      } else {
         throw new IllegalStateException("changeSurfaceSize() should only be called when flutterRenderer is non-null.");
      }
   }

   private void connectSurfaceToRenderer() {
      if (this.flutterRenderer != null && this.getHolder() != null) {
         this.flutterRenderer.startRenderingToSurface(this.getHolder().getSurface(), this.isPaused);
      } else {
         throw new IllegalStateException("connectSurfaceToRenderer() should only be called when flutterRenderer and getHolder() are non-null.");
      }
   }

   private void disconnectSurfaceFromRenderer() {
      FlutterRenderer var1 = this.flutterRenderer;
      if (var1 != null) {
         var1.stopRenderingToSurface();
      } else {
         throw new IllegalStateException("disconnectSurfaceFromRenderer() should only be called when flutterRenderer is non-null.");
      }
   }

   private void init() {
      if (this.renderTransparently) {
         this.getHolder().setFormat(-2);
         this.setZOrderOnTop(true);
      }

      this.getHolder().addCallback(this.surfaceCallback);
      this.setAlpha(0.0F);
   }

   private boolean shouldNotify() {
      boolean var1;
      if (this.flutterRenderer != null && !this.isPaused) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public void attachToRenderer(FlutterRenderer var1) {
      Log.v("FlutterSurfaceView", "Attaching to FlutterRenderer.");
      if (this.flutterRenderer != null) {
         Log.v("FlutterSurfaceView", "Already connected to a FlutterRenderer. Detaching from old one and attaching to new one.");
         this.flutterRenderer.stopRenderingToSurface();
         this.flutterRenderer.removeIsDisplayingFlutterUiListener(this.flutterUiDisplayListener);
      }

      this.flutterRenderer = var1;
      this.resume();
   }

   @Override
   public void detachFromRenderer() {
      if (this.flutterRenderer != null) {
         if (this.getWindowToken() != null) {
            Log.v("FlutterSurfaceView", "Disconnecting FlutterRenderer from Android surface.");
            this.disconnectSurfaceFromRenderer();
         }

         this.setAlpha(0.0F);
         this.flutterRenderer.removeIsDisplayingFlutterUiListener(this.flutterUiDisplayListener);
         this.flutterRenderer = null;
      } else {
         Log.w("FlutterSurfaceView", "detachFromRenderer() invoked when no FlutterRenderer was attached.");
      }
   }

   public boolean gatherTransparentRegion(Region var1) {
      if (this.getAlpha() < 1.0F) {
         return false;
      } else {
         int[] var3 = new int[2];
         this.getLocationInWindow(var3);
         int var2 = var3[0];
         var1.op(var2, var3[1], this.getRight() + var2 - this.getLeft(), var3[1] + this.getBottom() - this.getTop(), Op.DIFFERENCE);
         return true;
      }
   }

   @Override
   public FlutterRenderer getAttachedRenderer() {
      return this.flutterRenderer;
   }

   boolean isSurfaceAvailableForRendering() {
      return this.isSurfaceAvailableForRendering;
   }

   @Override
   public void pause() {
      if (this.flutterRenderer == null) {
         Log.w("FlutterSurfaceView", "pause() invoked when no FlutterRenderer was attached.");
      } else {
         this.isPaused = true;
      }
   }

   @Override
   public void resume() {
      FlutterRenderer var1 = this.flutterRenderer;
      if (var1 == null) {
         Log.w("FlutterSurfaceView", "resume() invoked when no FlutterRenderer was attached.");
      } else {
         var1.addIsDisplayingFlutterUiListener(this.flutterUiDisplayListener);
         if (this.isSurfaceAvailableForRendering()) {
            Log.v("FlutterSurfaceView", "Surface is available for rendering. Connecting FlutterRenderer to Android surface.");
            this.connectSurfaceToRenderer();
         }

         this.isPaused = false;
      }
   }
}
